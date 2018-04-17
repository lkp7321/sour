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
		dialog:'dialog',
		'wdatepicker':'My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','wdatepicker'],function($,mmGrid,niceScroll,dialog,wdatepicker){
	var wh=$(window).height()-180+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	 //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,userdata;
    //列参数;
    var cols = [
        { title:'干预器ID', name:'ctid' ,width:100, align:'left' },
        { title:'干预器名称', name:'ctnm' ,width:100, align:'left'},
        { title:'币种名称', name:'exnm' ,width:120, align:'left'},
        { title:'钞汇标志', name:'cxfg' ,width:100, align:'center'},
        { title:'买干预点差', name:'nebp' ,width:100, align:'right'},
        { title:'卖干预上限', name:'nesp' ,width:80, align:'right'},
        { title:'状态', name:'stfg' ,width:80, align:'center'},
        { title:'起始时间', name:'bgtm' ,width:150, align:'right'},
        { title:'截止时间', name:'edtm' ,width:150, align:'right'},
        { title:'价格类型', name:'tpnm' ,width:100, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'}
         
    ];

      //请求货币对下拉列表
	  $.ajax({
			url:'/fx/allExnm.do',
			type:'get',
			async:false,
			dataType:'json',
			contentType:'application/json;charset=utf-8',
			success:function(data){
				var maxdata=JSON.parse(data.codeMessage),
				str='<option value="所有">所有</option>';
				if(data.code==00){
					for(var i in maxdata){
						str+='<option value='+maxdata[i].EXCD+'>'+maxdata[i].EXNM+'</option>'
					}
					$('.exnm select').html(str);
				}else if(data.code==01){
					 
				}
			}
		});
	  var exnm=$('.exnm option:selected').text();
	  //请求列表
	  getList(exnm);
	  //根据货币对的变化重新请求列表
	  $('.exnm select').change(function(){
		  exnm=$('.exnm option:selected').text();
		  getList(exnm);
	  })
	  
	  //封装请求列表
	  function getList(obj){
		  $.ajax({
  	    	url:'/fx/allCmmCtrlPri.do',
  	        type:'post',
  	    	contentType:'application/json',
  	    	data:obj,
  	    	async:false,
  	    	dataType:'json',
  	    	success:function(data){
  	    		if(data.code==00){
  	    			 ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
  	    		}else if(data.code==01){
  	    			 ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
  	    		}else if(data.code==02){
  	    			//查询异常
  	    		}
  	    	  }
  	      });
		  
	  }
    //点击添加
	  $('.add').click(function(){
		  dialog.classionsetAdd('classionset')
		  $('#sT,#eT',parent.document).val(now);
		  //请求货币对
		  $.ajax({
				url:'/fx/curExnm.do',
				type:'get',
				async:false,
				dataType:'json',
				contentType:'application/json;charset=utf-8',
				success:function(data){
					var maxdata=JSON.parse(data.codeMessage),
					str='';
					if(data.code==00){
						for(var i in maxdata){
							str+='<option value='+maxdata[i].EXCD+'>'+maxdata[i].EXNM+'</option>'
						}
						$('.currname',parent.document).html(str);
					}else if(data.code==01){
						 
					}
				}
			});
		  //请求价格类型
		  $.ajax({
				url:'/fx/curTpnm.do',
				type:'get',
				async:false,
				dataType:'json',
				contentType:'application/json;charset=utf-8',
				success:function(data){
					var maxdata=JSON.parse(data.codeMessage),
					str='';
					if(data.code==00){
						for(var i in maxdata){
							str+='<option value='+maxdata[i].TPFG+'>'+maxdata[i].TPNM+'</option>'
						}
						$('.priceType',parent.document).html(str);
					}else if(data.code==01){
						 
					}
				}
			});
		  
	  }) 
	//点击修改
	  $('.modify').click(function(){
		  if( $('.box tbody tr').hasClass('selected') ){
			  dialog.classionsetAdd('classionset')
			  $('.interID',parent.document).val($('.box tr.selected td:eq(1) span').text());
			  $('.intername',parent.document).val($('.box tr.selected td:eq(2) span').text());
			  $('.priceType option:selected',parent.document).text($('.box tr.selected td:eq(10) span').text());
			  $('.priceType option:selected',parent.document).val($('.box tr.selected td:eq(10)').attr('tpfg'));
			  $('.priceType',parent.document).attr('disabled','disabled');
			  $('.term',parent.document).val($('.box tr.selected td:eq(11) span').text()).attr('disabled','disabled');
			  $('.currname option:selected',parent.document).text($('.box tr.selected td:eq(3)').text());
			  $('.currname',parent.document).attr('disabled','disabled');
			  $('.buy',parent.document).val($('.box tr.selected td:eq(5) span').text());
			  $('.sell',parent.document).val($('.box tr.selected td:eq(6) span').text());
			  $('#sT',parent.document).val($('.box tr.selected td:eq(8) span').text());
			  $('#eT',parent.document).val($('.box tr.selected td:eq(9) span').text());
			  $('.money input',parent.document).attr('disabled','disabled');
			  var stfg=$('.box tr.selected td:eq(7) span').text(),
			      cxfg=$('.box tr.selected td:eq(4) span').text();
			  
			  if(stfg=='停用' ){
  				$('.intervene input[data-tit="stop"]',parent.document).prop('checked','checked');
  			  }else{
  				$('.intervene input[data-tit="start"]',parent.document).prop('checked','checked');
  		      }
			  if(cxfg=='汇' ){
  				$('.money input[data-tit="stop"]',parent.document).prop('checked','checked');
  			  }else{
  				$('.money input[data-tit="start"]',parent.document).prop('checked','checked');
  		      }
		  }else{
  			dialog.choicedata('请先选择一条数据!','classionset');
  		 }
	  
	  }) 
	//点击添加修改的保存按钮
	$('body',parent.document).on('click','.classionset .preserve',function(){
		var ctid=$('.interID',parent.document).val(), 
		    ctnm=$('.intername',parent.document).val(), 
		    tpfg=$('.priceType option:selected',parent.document).val(),  
		    term=$('.term',parent.document).val(), 
		    exnm=$('.currname option:selected',parent.document).text(), 
		    excd=$('.currname option:selected',parent.document).val(), 
		    nebp=$('.buy',parent.document).val(), 
		    nesp=$('.sell',parent.document).val(), 
		    bgtm=$('#sT',parent.document).val(), 
		    edtm=$('#eT',parent.document).val(),cxfg,stfg,vo,url; 
		    if($('.money input[type="radio"]:checked',parent.document).data('tit')=='start'){
		    	cxfg="2";
		    }else{
		    	cxfg="1";
		    }
		    if($('.intervene input[type="radio"]:checked',parent.document).data('tit')=='start'){
		    	stfg="0";
		    }else{
		    	stfg="1";
		    }
		    
		    if( $('.term',parent.document).attr('disabled')=="disabled"){
		    	 vo={"userKey":userKey,
			 		  "cmmCtrlpri":{"ctid":ctid,"ctnm":ctnm,"tpfg":tpfg,"term":term,"exnm":exnm,"nebp":nebp,"nesp":nesp,"bgtm":bgtm,"edtm":edtm,"cxfg":cxfg,"stfg":stfg}	
		 		    }
		    	 url="updateCmmCtrlpri.do";
		    }else{
		    	 vo={"userKey":userKey,
		 		     "cmmCtrlpri":{"ctid":ctid,"ctnm":ctnm,"tpfg":tpfg,"term":term,"exnm":exnm,"excd":excd,"nebp":nebp,"nesp":nesp,"bgtm":bgtm,"edtm":edtm,"cxfg":cxfg,"stfg":stfg}	
		 		    }
		    	 url="addCmmCtrlpri.do";
		    }
		    
		   
		    
		
		    $.ajax({
		    	url:url,
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(vo),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			$('.classionset .preserve',parent.document).closest('.zhezhao').remove();
		    			exnm=$('.exnm option:selected').text();
		    			getList(exnm);
		    			dialog.choicedata(data.codeMessage,'classionset');
		    		}else if(data.code==01){
		    			//异常 
		    			dialog.choicedata(data.codeMessage,'classionset');
		    		} 
		    	  }
	       });  	
	
	
	})
	//点击启用 停用按钮
  	$('.hands_open,.hands_stop').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据再进行此操作','classionset');
		}else{
			 $('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  if($(v).find('td').eq(4).find('span').text()=='钞'){
					  var cxfg='2';
				  }else{
					  var cxfg='1';
				  }
				  arr.push({
					  'ctid':$(v).find('td').eq(1).find('span').text(),
					  'exnm':$(v).find('td').eq(3).find('span').text(),
					  'tpfg':$(v).find('td').eq(10).attr('tpfg'),
					  'term':$(v).find('td').eq(11).find('span').text(),
					  'cxfg':cxfg,
					  'ctnm':$(v).find('td').eq(2).find('span').text()
					   
				  });
				}
			});
			 
		 	
		 var usfg=$(this).text(),
		     PriceVo ={'userKey':userKey,'usfg':usfg,'cmmCtrlpris':arr};	 
		    $.ajax({
		    	url:'/fx/upCmmCtrlPriStfg.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(PriceVo),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			getList(exnm);
		    			dialog.choicedata('更新成功','classionset');
		    		}else if(data.code==01){
		    			//异常 
		    			dialog.choicedata('更新失败','classionset');
		    		} 
		    	  }
		      });  	
		}
	});


  	 //关闭弹窗
	$('body',parent.document).on('click','.classionset .sure',function(){
		$(this).closest('.zhezhao').remove();
	})
	//删除窗的取消按钮
	$('body',parent.document).on('click','.cancel,.close',function(){
		 $(this).closest('.zhezhao').remove();
	});
	//时间控件
    $('body',parent.document).on('click','#eT,#sT',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-240,top:-60}
			 
		}); 	
	});
	//比较时间的大小
	function comptime(obj,obj1) {
	    var beginTime = obj;
	    var endTime = obj1;
	    beginTime = beginTime.substring(0,4) + '-' + beginTime.substring(4,6) + '-' + beginTime.substring(6,8)+' '+beginTime.substring(9,16);
	    endTime = endTime.substring(0,4) + '-' + endTime.substring(4,6) + '-' + endTime.substring(6,8)+' '+endTime.substring(9, 16);
	    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	    
	  	if (a < 0||a==0) {
	  		return false;
	  		//dialog.choicedasta('截止时间应大于起始时间!','quoteparameter');
	    } else if (a > 0) {
	        return true;
	    }
	}
	//获取当前的时间
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}

	var myDate = new Date();
	//获取当前年
	var year=myDate.getFullYear();
	//获取当前月
	var month=myDate.getMonth()+1;
	//获取当前日
	var date=myDate.getDate(); 
	var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();  

	var now=year+''+p(month)+""+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
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
				horizrailenabled:true
		  });
		   mmg.on('loadSuccess',function(e,data){
			 
			if(obj.userdata.length!=0){
				$('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq('3').attr('excd',obj.userdata[i].excd);
					 $(v).find('td').eq('10').attr('tpfg',obj.userdata[i].tpfg);
				 });
			}   
			 
		 }) 
	  }
    
});