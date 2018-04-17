/*积存金&&总敞口-分类敞口*/
 require.config({
    	baseUrl:'/fx/js',
    	shim:{
    		'mmGrid':{
    			deps:['jquery'],
    			exports:'mmGrid'
    		},
    		'dialog':{
    			deps:['jquery'],
    			exports:'dialog'
    		},
    		'nicescroll':{
    			deps:['jquery'],
    			exports:'nicescroll'
    		}
    	},
    	paths:{
    		'jquery':'js_files/jquery-1.9.1.min',
    		'mmGrid':'js_files/mmGrid',
    		'dialog':'dialog',
    		'nicescroll':'js_files/jquery.nicescroll.min'
    	}
    });
    require(['jquery','mmGrid','dialog','nicescroll'],function($,mmGrid,dialog,nicescroll){
    	var wh=($(window).height()-180-26*2-15*2)/3+"px",
    		ww=$(window).width()-250+"px",
    		wp=($(window).width()-250-30)+'px';
    	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    	$('.boxtop1,.boxtop2,.boxtop3').css('width',ww);
    	$('.boxtop2 p,.boxtop3 p').css('width',wp);
    	
    	//列参数
        var cols = [
            { title:'品种对', name:'exnm' ,width:80, align:'left' },
            { title:'名称', name:'nmex' ,width:140, align:'left'},
            { title:'数量', name:'lamt' ,width:70, align:'left'},
            { title:'金额', name:'ramt' ,width:70, align:'right'},
            { title:'成本汇率', name:'cbhl' ,width:80, align:'right'},
            { title:'浮动损益', name:'fdsy' ,width:80, align:'right'},
            { title:'买入价格', name:'neby' ,width:80, align:'right'},
            { title:'卖出价格', name:'nesl' ,width:80, align:'right'}
        ],
        cols1=[
           { title:'品种对', name:'exnm' ,width:80, align:'left' },
           { title:'名称', name:'nmex' ,width:100, align:'left'},
           { title:'数量', name:'lamt' ,width:150, align:'left'},
           { title:'金额', name:'ramt' ,width:80, align:'right'},
           { title:'成本汇率', name:'cbhl' ,width:120, align:'right'},
           { title:'对冲损益', name:'zcyk' ,width:100, align:'right'},
           { title:'中间价格', name:'nemd' ,width:80, align:'right'}
        ],
        cols2=[
          { title:'产品名称', name:'prnm' ,width:100, align:'left' },
          { title:'品种对', name:'exnm' ,width:100, align:'left'},
          { title:'买入价', name:'neby' ,width:80, align:'right'},
          { title:'中间价', name:'nemd' ,width:80, align:'right'},
          { title:'卖出价', name:'nesl' ,width:80, align:'right'},
          { title:'更新时间', name:'mdtm' ,width:120, align:'right'},
          { title:'市场', name:'mknm' ,width:80, align:'left'},
          { title:'报价状态', name:'stfg' ,width:80, align:'center'}
         ];
        //获取市场报价、总敞口、折算敞口；
        getFn();
        getFn1();
        function getFn(){
        	$.ajax({
        		url:'/fx/accum/allckTotalData.do',//市场报价
        		type:'get',
        		contentType:'application/json',
        		async:true,
        		success:function(data){
        			if(data.code==01){
        				userdata= data.codeMessage ;
        				ren1(userdata);
        			}else if(data.code==00){
        				ren1('');
        			}
        		}
        	});
        }
      //获取总敞口and折算敞口
        function getFn1(){  
        	 $.ajax({
         		url:'/fx/accum/allckTotal.do',
         		type:'post',
         		contentType:'application/json',
         		async:true,
         		success:function(data){
         			if(data.code==01){
         				userdata= data.codeMessage ;
         				ren(userdata);
         			}else if(data.code==00){
         				ren('');
         			}
         		}
         	});
        }
        //获取总浮动损益和人民币总盈利；
        $.ajax({
    		url:'/fx/accum/getckTotal.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage;
    				$('.totalfloating').text(userdata.zfdsy);
    				$('.RMBtotal').text(userdata.rmbzyl);
    			}else if(data.code==02){
    				dialog.choicedata(data.codeMessage,'total1');
    			}
    		}
    	});

    	//点击提取损益;
    	$('.extractpl').click(function(){
    		$('.boxtop2 .box tr').each(function(i,v){
    			if(  $(v).find('td').eq(0).find('span').text()==$('.typerm select option:selected').attr('value')  ){
    				$(v).siblings().removeClass('aa');
    				$(v).addClass('aa');
    			}
    		});
    		 dialog.cancelDate('您确定要更新对外损益吗?','total1');
    	});
    	//点击删除数据 确认+ajax;
    	$('body',parent.document).on('click','.total1 .confirm',function(){
    		var obj={
    				'exnm':$('.boxtop2 .box tr.aa td:eq(0) span').text(),
        			'excd':$('.boxtop2 .box tr.aa td:eq(0)').attr('excd'),
        			'nemd':$('.boxtop2 .box tr.aa td:eq(0)').attr('nemd'),
        			'lamt':$('.boxtop2 .box tr.aa td:eq(2) span').text(),
        			'ramt':$('.boxtop2 .box tr.aa td:eq(3) span').text(),
        			'cbhl':$('.boxtop2 .box tr.aa td:eq(4) span').text(),
        			'zcyk':$('.boxtop2 .box tr.aa td:eq(5) span').text()
    		};
    		getex( obj );
    	});
    	function getex( obj ){
    		    var tqsy=obj.ramt-(-1)*obj.lamt*obj.nemd,
    		    	ramt=(-1)*obj.lamt*obj.nemd;
    			$.ajax({
    				url:'/fx/accum/upckTotal.do',
    				type:'post',
    				contentType:'application/json',
    				async:true,
    				data:JSON.stringify({
    					'userKey':userkey,
    					'ckTotalBean':{
	    					exnm:obj.exnm,
	    					excd:obj.excd,
	    					nemd:obj.nemd,
	    					zcyk:tqsy,
	    					lamt:obj.lamt,
	    					bramt:obj.ramt,
	    					bzcyk:obj.zcyk,
	    					bcbhl:obj.cbhl,
	    					ramt:ramt
	    				}
    				}),
    				success:function(data){
    					$('.total1 .confirm',parent.document).closest('.zhezhao').remove();
    					if(data.code==01){
    						dialog.choicedata(data.codeMessage,'total1');
    						  getFn();
    						  getFn1();
    					}else if(data.code==02){
    						dialog.choicedata(data.codeMessage,'total1');
    					}
    				}
    			});
    	}
    	//总敞口高度问题；
    	function ren1(obj1){
    		$('.boxtop3 .boxp').html(' ');
        	$('#ascrail2000').remove();
        	$('.boxtop3 .boxp').append('<div class="box"></div>');
     
        	var mmg=$('.boxtop3 .box').mmGrid({
    			height:wh
    			,cols: cols2
    			,items:obj1
    	        , nowrap:true
    	        ,fullWidthRows:true
    	        ,showBackboard:true
    	        ,loadingText:'正在载入'
    		});
        	mmg.on('loadSuccess',function(){
        		setInterval(function(){
        			$.ajax({	///折算敞口和总敞口；
		         		url:'/fx/accum/allckTotal.do',
		         		type:'post',
		         		contentType:'application/json',
		         		async:true,
		         		success:function(data){
		         			if(data.code==01){
		         				userdata= data.codeMessage ;
		         				var userdatas=new Array()
	         					userdatas[0]=userdata[4];
	         					userdatas[1]=userdata[5];
	         					var uds = new Array();
	         					uds[0]=userdata[0];
	         					uds[1]=userdata[1];
	         					uds[2]=userdata[2];
	         					uds[3]=userdata[3];
		         				$('.boxtop2 .box tbody tr').each(function(i,v){	//折算敞口
		         					if($(v).find('td:eq(6) span').text()*1<userdatas[i].nemd*1){
			    						$(v).find('span').css('color','#FF0000');
			    					}else if($(v).find('td:eq(6) span').text()*1>userdatas[i].nemd*1){
			    						$(v).find('span').css('color','#00FF00');
			    					}else{
			    						$(v).find('span').css('color','#000000');
			    					}
		        					$(v).find('td').eq(0).attr('excd',userdatas[i].excd);
		        					$(v).find('td').eq(0).attr('nemd',userdatas[i].nemd);
		        					$(v).find('td').eq(0).attr('tqsy',userdatas[i].tsy);
		        					
		        					$(v).find('td:eq(0) span').text( userdatas[i].exnm );
   			       					$(v).find('td:eq(1) span').text( userdatas[i].nmex );
   			       					$(v).find('td:eq(2) span').text( userdatas[i].lamt );
   			       					$(v).find('td:eq(3) span').text( userdatas[i].ramt );
   			       					$(v).find('td:eq(4) span').text( userdatas[i].cbhl );
   			       					$(v).find('td:eq(5) span').text( userdatas[i].zcyk );
   			       					$(v).find('td:eq(6) span').text( userdatas[i].nemd );
		        				});
		         				
		         				$('.boxtop1 .box tbody tr').each(function(i,v){  //改变总敞口的实时变动；
		         					if($(v).find('td:eq(7) span').text()*1<uds[i].nesl*1){
			    						$(v).find('span').css('color','#FF0000');
			    					}else if($(v).find('td:eq(7) span').text()*1>uds[i].nesl*1){
			    						$(v).find('span').css('color','#00FF00');
			    					}else{
			    						$(v).find('span').css('color','#000000');
			    					}
		        					$(v).find('td:eq(0) span').text( uds[i].exnm );
   			       					$(v).find('td:eq(1) span').text( uds[i].nmex );
   			       					$(v).find('td:eq(2) span').text( uds[i].lamt );
   			       					$(v).find('td:eq(3) span').text( uds[i].ramt );
   			       					$(v).find('td:eq(4) span').text( uds[i].cbhl );
   			       					$(v).find('td:eq(5) span').text( uds[i].fdsy );
   			       					$(v).find('td:eq(6) span').text( uds[i].neby );
			       					$(v).find('td:eq(7) span').text( uds[i].nesl );
		        				});
		         			}else if(data.code==00){
		         				ren('');
		         			}
		         		}
        			});
        			$.ajax({	///总浮动损益和人民币盈利
        	    		url:'/fx/accum/getckTotal.do',
        	    		type:'post',
        	    		contentType:'application/json',
        	    		async:true,
        	    		success:function(data){
        	    			if(data.code==01){
        	    				userdata=data.codeMessage;
        	    				$('.totalfloating').text(userdata.zfdsy);
        	    				$('.RMBtotal').text(userdata.rmbzyl);
        	    			}else if(data.code==02){
        	    				dialog.choicedata(data.codeMessage,'total1');
        	    			}
        	    		}
        	    	});
   				 $.ajax({
   			    		url:'/fx/accum/allckTotalData.do',
   			    		type:'get',
   			    		contentType:'application/json',
   			    		async:false,
   			    		success:function(data){
   			    			if(data.code==01){
   			       				userdata=data.codeMessage;
   			       			$('.boxtop3 .box tbody tr').each(function(i,v){
		       					if($(v).find('td:eq(3) span').text()*1<userdata[i].nemd*1){
		    						$(v).find('span').css('color','#FF0000');
		    					}else if($(v).find('td:eq(3) span').text()*1>userdata[i].nemd*1){
		    						$(v).find('span').css('color','#00FF00');
		    					}else{
		    						$(v).find('span').css('color','#000000');
		    					}
   			       					$(v).find('td:eq(0) span').text( userdata[i].prnm);
   			       					$(v).find('td:eq(1) span').text( userdata[i].exnm);
   			       					$(v).find('td:eq(2) span').text( userdata[i].neby);
   			       					$(v).find('td:eq(3) span').text( userdata[i].nemd);
   			       					$(v).find('td:eq(4) span').text( userdata[i].nesl);
   			       					$(v).find('td:eq(5) span').text( userdata[i].mdtm);
   			       					$(v).find('td:eq(6) span').text( userdata[i].mknm);
			       					$(v).find('td:eq(7) span').text( userdata[i].stfg);
   			       				});
   			    			}else if(data.code==01){
   			       				ren1('');
   			       			}
   			    		}
   			    	}); 
   			},1000);
   		 });
    	}
    	function ren(obj1){   			
    			$('.boxtop1 .boxp').html(' ');
    			$('.boxtop2 .boxp').html(' ');
    	    	$('#ascrail2000').remove();
    	    	$('.boxtop1 .boxp').append('<div class="box"></div>');
    	    	$('.boxtop2 .boxp ').append('<div class="box"></div>');
    	    	
    			var mmg2 = $('.boxtop2 .box').mmGrid({
    				height:wh
    				,cols: cols1
    				,items:obj1.slice(4,6)
    		        , nowrap:true
    		        ,fullWidthRows:true
    		        ,showBackboard:true
    		        ,loadingText:'正在载入'
    			});
    			var mmg = $('.boxtop1 .box').mmGrid({
    				height:wh
    				,cols: cols
    				,items:obj1.slice(0,4)
    		        , nowrap:true
    		        ,fullWidthRows:true
    		        ,showBackboard:true
    		        ,loadingText:'正在载入'
    			});
    			mmg2.on('loadSuccess',function(){
    				var a=obj1.slice(4,6);
    				$('.boxtop2 .box tbody tr').each(function(i,v){
    					$(v).find('td').eq(0).attr('excd',a[i].excd);
    					$(v).find('td').eq(0).attr('nemd',a[i].nemd);
    					$(v).find('td').eq(0).attr('tqsy',a[i].tsy);
    				});
    			});
    		//}
    		$(".boxtop1 .mmg-bodyWrapper,.boxtop2 .mmg-bodyWrapper,.boxtop3 .mmg-bodyWrapper").niceScroll({
    			touchbehavior:false,
    			cursorcolor:"#666",
    			cursoropacitymax:0.7,
    			cursorwidth:6,
    			background:"#ccc",
    			autohidemode:false,
    			horizrailenabled:true
    	  });
    	}
    	//选择一条数据的确定按钮;
    	$('body',parent.document).on('click','.total1 .twosure,.total1 .cancel',function(){
    		$(this).closest('.zhezhao').remove();
    	});
    });