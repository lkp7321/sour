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
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1,#d2').val( dialog.today);
	//列参数;
    var cols = [
            { title:'交易流水号', name:'lcno',width:120,align:'left' },
            { title:'交易日期', name:'trdt',width:80, align:'right'},
            { title:'交易时间', name:'trtm',width:80, align:'right'},
            { title:'交易账号', name:'trac',width:80, align:'left'},
            { title:'卡号', name:'cuac',width:80, align:'left'},
            { title:'折美元金额', name:'usam',width:80, align:'right'},
            { title:'交易状态', name:'stcd',width:80, align:'center'}
    ];
    dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
    
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),
        startDate=$('#d1').val(),
	    endDate=$('#d2').val(),
	    status=$('.chestate option:selected').val(),
	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':1,'pageSize':10},
	    userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    $('.chestate').change(function(){
    	status=$('.chestate option:selected').text();
    	if(status=="已核对"){
    		$('.cancel,.check').removeAttr('disabled').css('background','#5a8bff');
    	}else{
    		$('.cancel,.check').attr('disabled','disabled').css('background','rgba(0, 0, 0, 0.498039)');
    	}
    })
    
    $('.query').click(function(){
    	
    	var startDate=$('#d1').val(),
    	    endDate=$('#d2').val(),
    	    status=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':1,'pageSize':10};
    
    	getAccountList(KondorVo);
    });
    
  //核对提交和核对取消的处理
	$('.submit,.cancel,.check').click(function(){
		var a=0,arr=[];
	    var text=$(this).text(),url;
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			if(text=="核对提交"){
				dialog.choicedata('请先勾选一条数据再核对提交','datchgoldche');
			}else if(text=="核对取消"){
				dialog.choicedata('请先勾选一条数据再核对取消','datchgoldche');
			}else if(text=="复核"){
				dialog.choicedata('请先勾选一条数据再复核','datchgoldche');
			}
			
			
		}else{
			 $('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'lcno':$(v).find('td').eq('1').find('span').text(),
					  'trac':$(v).find('td').eq('4').find('span').text(),
					  'cuac':$(v).find('td').eq('5').find('span').text(),
					  'stcd':$(v).find('td').eq('7').find('span').text()
					   
			      });
				}
			 });
		 $.each(arr,function(i,e){
			 
			 if(e.stcd=="MT4出金成功"){
				 e.stcd='1';
			 }else if(e.stcd=="已核对"){
				 e.stcd='2';
			 }else if(e.stcd=="出金失败"){
				 e.stcd='R';
			 }
			  
		 })
			 
		   if(text=='核对提交'){
			   url='hedui.do'
		   }else if(text=='核对取消'){
			   url='cancelHedui.do'
		   }else if(text=='复核'){
			   url='fuhe.do'
		   }
		 
		    PriceVo={'user':usnm,'prod':product,'list':arr};	 
		    $.ajax({
		    	url:url,
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(PriceVo),
		    	async:true,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			 startDate=$('#d1').val(),
		    			 endDate=$('#d2').val(),
		    			 status=$('.chestate option:selected').val(),
		    			 KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':1,'pageSize':10};
		    			 getAccountList(KondorVo);  
		    			dialog.choicedata(data.codeMessage,'datchgoldche');
		    		}else if(data.code==01){
		    			//异常 
		    			dialog.choicedata(data.codeMessage,'datchgoldche');
		    		} 
		    	  }
		      });  	
		}
	});
    $('body',parent.document).on('click','.datchgoldche .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('body',parent.document).on('click','.datchgoldche .sure,.close,.cance,.cancel',function(){
    	$('.zhezhao',parent.document).remove();
    });
    
    //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	startDate=$('#d1').val(),
    	    endDate=$('#d2').val(),
    	    status=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':Nopage,'pageSize':10};
 	        getAccountList(KondorVo);
 	     }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	startDate=$('#d1').val(),
    	    endDate=$('#d2').val(),
    	    status=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':Nopage,'pageSize':10};
 	        getAccountList(KondorVo);
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	startDate=$('#d1').val(),
    	    endDate=$('#d2').val(),
    	    status=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':Nopage,'pageSize':10};
 	        getAccountList(KondorVo);
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	startDate=$('#d1').val(),
    	    endDate=$('#d2').val(),
    	    status=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'endDate':endDate,'status':status,'pageNo':Nopage,'pageSize':10};
 	        getAccountList(KondorVo);
	    }
	}); 
  //封装请求主列表数据
    function getAccountList(obj){
    	$.ajax({
    		url:'/fx/seleOutAccountList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    		    if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
    			 $('.page').remove();
 				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==02){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
    			}
    		}
    	});
        
    };
});
