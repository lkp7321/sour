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
        { title:'停牌标志', name:'stfg' ,width:100, align:'center' },
        { title:'停牌器ID', name:'stid' ,width:100, align:'left'},
        { title:'市场名称', name:'mknm' ,width:100, align:'left'},
        { title:'价格类型', name:'tpnm' ,width:100, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'},
        { title:'停牌器名称', name:'stnm' ,width:120, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
        
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),str,
        loginuser={'usnm':usnm,'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,userdata;
  //请求市场
    $.ajax({
		url:'/fx/queryMktinfo.do',
		type:'get',
		async:false,
		dataType:'json',
		contentType:'application/json;charset=utf-8',
	    success:function(data){
			var str='<option value="all" selected="selected">所有</option>';
			    handdata=JSON.parse(data.codeMessage);
			if(data.code==00){
				for(var i in handdata){
					str+='<option value='+handdata[i].MKID +'>'+handdata[i].MKNM+'</option>'
				}
				$('.market select').html(str);
			}else if(data.code==01){
				 
			}
		}
	});
    //请求币种
    $.ajax({
		url:'/fx/queryhqsExnms.do',
		type:'get',
		async:false,
		dataType:'json',
		contentType:'application/json;charset=utf-8',
		success:function(data){
			var str='<option value="all" selected="selected">所有</option>',
			    handdata=JSON.parse(data.codeMessage);
			if(data.code==00){
				for(var i in handdata){
					str+='<option value='+handdata[i].EXCD +'>'+handdata[i].EXNM+'</option>'
				}
				$('.currencylist select').html(str);
			}else if(data.code==01){
				 
			}
		}
	});
    //请求列表
    var mkid=$(".market option:selected").val(),
        excd=$(".currencylist option:selected").val();
    getHandstop({'mkid':'all','excd':'all'});
    	
   
    //根据市场select的选中值的改变请求列表
    $('.market select').change(function(){
    	    mkid=$(".market option:selected").val(),
    	    excd=$(".currencylist option:selected").val();
    	getHandstop({'mkid':mkid,'excd':excd});
    })
    //根据币种select的选中值的改变请求列表
    $('.currencylist select').change(function(){
            mkid=$(".market option:selected").val(),
	        excd=$(".currencylist option:selected").val();
    	getHandstop({'mkid':mkid,'excd':excd});
     })
    
   //开牌框停牌框弹出
	$('.hands_open,.hands_stop').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据！','handqukst');
		}else{
			 $('.box tr').each(function(i,v){
				 if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'stid':$(v).find('td').eq(2).find('span').text(),
					  'exnm':$(v).find('td').eq(7).find('span').text(),
					  'mkid':$(v).find('td').eq(3).attr('mkid'),
					  'excd':$(v).find('td').eq(7).attr('excd'),
					  'tpfg':$(v).find('td').eq(4).attr('tpfg'),
					  'term':$(v).find('td').eq(5).find('span').text(),
					  'mknm':$(v).find('td').eq(3).find('span').text(),
					  'stnm':$(v).find('td').eq(6).find('span').text()
				  });
				}
			});
			 
		 	
		  var usfg=$(this).text(),url;
		   if(usfg=='开牌'){
			   url='open.do'
		   }else{
			   url=' close.do'
		   }
		 
		     
		    $.ajax({
		    	url:url,
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify({"userKey":userKey,"cmmStopers":arr}),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			getHandstop({'mkid':mkid,'excd':excd});
		    			dialog.choicedata(data.codeMessage,'handqukst');
		    		}else if(data.code==01){
		    			//异常 
		    		} 
		    	  }
		      });  	
		}
	});
  
	
	
	$('body',parent.document).on('click','.handqukst .sure',function(){
		//关闭选择一条数据;
	   $('.zhezhao',parent.document).remove();
	});
	//根据数据请求列表的封装
	function getHandstop(obj){
		$.ajax({
	    	url:'/fx/selecthqsCmmStoper.do',
	        type:'post',
	        data:JSON.stringify(obj),
	    	contentType:'application/json',
	    	async:false,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==00){
	    			 
	    			if(JSON.parse(data.codeMessage).length==0){
	    				ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    			}else{
	    				ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
	    			}
	    			
	    		}else if(data.code==01){
	    			  ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    		}else if(data.code==02){
	    			//查询异常
	    		}
	    	  }
	      });
		
	};
	 //table的渲染
	function ren(obj){
		 $('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:false
		  });
		   mmg.on('loadSuccess',function(e,data){
			 
			if(obj.userdata.length!=0){
				$('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq('3').attr('mkid',obj.userdata[i].mkid);
					 $(v).find('td').eq('7').attr('excd',obj.userdata[i].excd);
					 $(v).find('td').eq('4').attr('tpfg',obj.userdata[i].tpfg);
				 });
			}   
			 
		 }) 
	  }
 
 
})