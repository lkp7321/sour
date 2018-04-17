require.config({
	baseUrl:'/fx/js',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
				},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		}
	},
	paths:{
		jquery:'./js_files/jquery-1.9.1.min',
		mmGrid:'./js_files/mmGrid',
		niceScroll:'./js_files/jquery.nicescroll.min',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
	   ww=$(window).width()-260+"px";

    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
        { title:'产品编号', name:'ptid' ,width:100, align:'left' },
        { title:'产品名称', name:'ptnm' ,width:100, align:'left'},
        { title:'价格类型', name:'qtty' ,width:100, align:'left'},
        { title:'报价频率（秒）', name:'frqy' ,width:100, align:'right'},
        { title:'产品使用标志', name:'usfg' ,width:100, align:'center'},
         
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),
        loginuser={'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
   
    //请求列表
    getFrequency(userKey);
	//修改框弹出
	$('.modify').click(function(){
		var ptid=$('.box tr.selected td:eq(0) span').text(),
		    ptnm=$('.box tr.selected td:eq(1) span').text(),
		    qtty=$('.box tr.selected td:eq(2) span').text(),
		    frqy=$('.box tr.selected td:eq(3) span').text(),
		    term=$('.box tr.selected td:eq(4) span').text();
		if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;+获取当前selected的数据;
           dialog.quofrequmodify();
           $('.freq_num input',parent.document).val(ptid).attr({'readonly':'true','disabled':'disabled'});
           $('.freq_name input',parent.document).val(ptnm).attr({'readonly':'true','disabled':'disabled'});
           $('.freq_type input',parent.document).val(qtty).attr({'readonly':'true','disabled':'disabled'});
           $('.freq_quote input',parent.document).val(frqy);  
           if(term=='停用' ){
				$('.freq_use input[data-tit="stop"]',parent.document).prop('checked','checked');
			}else if(term=='开启' ){
				$('.freq_use input[data-tit="start"]',parent.document).prop('checked','checked');
			} 
           //报价频率失焦；
           $('.dia_freq .freq_quote input',parent.document).on('blur',function(){
        	  if( $(this).val()==''){
        		  $('.dia_freq .freq_quote .formtip',parent.document).remove();
        		  $('.dia_freq .freq_quote',parent.document).append('<i class="formtip">报价频率不能为空!</i>');
        	  }else{
        		  $('.dia_freq .freq_quote .formtip',parent.document).remove();
        	  }
           });
		}else{
			dialog.choicedata('请先选择一条数据!','quotefrequency');
		}
	});
	//点击修改弹窗保存;
	$('body',parent.document).on('click','.freq_save',function(){
		   loginuser={'usnm':usnm,'prod':product};
		   var ptid= $('.freq_num input',parent.document).val(),
		       ptnm=$('.freq_name input',parent.document).val(),
		       qtty= $('.freq_type input',parent.document).val(),
		       frqy= $('.freq_quote input',parent.document).val(),usfg,QutFreqtVo;
		      if($('.freq_use input[type="radio"]:checked',parent.document).data('tit')=='start'){
			      usfg=0;
		       }else{
		          usfg=1;
		       }
		   QutFreqtVo ={  'pdtinfo':{ 'ptid':ptid, 'ptnm': ptnm,  'qtty': qtty,  'frqy': frqy, 'usfg':usfg },  
				          'userKey':userKey
				       }
		   //报价频率失焦；
    	  if( $('.dia_freq .freq_quote input',parent.document).val()==''){
    		  $('.dia_freq .freq_quote .formtip',parent.document).remove();
    		  $('.dia_freq .freq_quote',parent.document).append('<i class="formtip">报价频率不能为空!</i>');
    	  }else{
    		  $('.dia_freq .freq_quote .formtip',parent.document).remove();
    	  }
		  if( $('.dia_freq .freq_quote .formtip',parent.document).length==0){
		   $.ajax({
		    	url:'/fx/updateQutFreqt.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(QutFreqtVo),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			$('.freq_save',parent.document).closest('.zhezhao').remove();
		    			dialog.choicedata(data.codeMessage,'quotefrequency');
		    		}else if(data.code==01){
		    			dialog.choicedata(data.codeMessage,'quotefrequencyerror'); 
		    		} 
		    	  }
		      });
		  }
	})

   //点击修改弹窗的关闭和取消;
	$('body',parent.document).on('click','.freq_close',function(){
		$('.zhezhao',parent.document).remove();
	})
	$('body',parent.document).on('click','.freq_cancel',function(){
		$('.zhezhao',parent.document).remove();
	})
	
	 //关闭成功提示窗的  重新请求列表	
	$('body',parent.document).on('click','.quotefrequency .sure',function(){
		$(this,parent.document).closest('.zhezhao').remove();
		getFrequency(userKey);
	});
	 //关闭修改失败 删除失败提示窗 不 重新请求列表	
	$('body',parent.document).on('click','.quotefrequencyerror .sure',function(){
		$(this,parent.document).closest('.zhezhao').remove();
	});
	//封装请求列表
	function getFrequency(obj){
		$.ajax({
	    	url:'/fx/getQutFreqt.do',
	        type:'post',
	    	contentType:'application/json',
	    	//data:JSON.stringify(obj),
	    	data:obj,
	    	async:false,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==00){
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':[JSON.parse(data.codeMessage)]});
	    		}else if(data.code==01){
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':''});
	    		}else if(data.code==02){
	    			//查询异常
	    		}
	    	  }
	      });
		
	}
	
})