/*账户信息统计&&签约信息统计*/
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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('#d1').val( dialog.today() );
	//列参数;
	//账户信息统计
    var cols = [
            { title:'日期', name:'accDate',width:60,align:'right' },
            { title:'时间', name:'nowTime',width:60, align:'right'},
            { title:'机构名称', name:'ognm',width:80, align:'left'},
            { title:'机构号', name:'ogcd',width:60, align:'left'},
            { title:'活期累积量', name:'hqam',width:60,align:'right'},
            { title:'定期累积量', name:'dqam',width:60, align:'right'},
            { title:'总累积量', name:'amnt',width:30, align:'right'}
    ],
    //签约信息统计
       cols1 = [															//签约结构
              { title:'日期', name:'accDate',width:60,align:'right' },
              { title:'时间', name:'nowTime',width:60, align:'right'},
              { title:'签约机构名称', name:'ognm',width:80, align:'left'},
              { title:'机构号', name:'ogcd',width:60, align:'left'},
              { title:'累计签约人数', name:'qydt',width:60,align:'right'}
      ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    getofcd();
    function getofcd(){
    	 $.ajax({
			url:'/fx/getuserOgcd.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				var str='';
				if(data.code==01){
					userdata= data.codeMessage ;
					
					str+='<option ogcd='+userdata.ogcd+'>'+userdata.ognm+'</option>'
					$('.fitnma').html( str );
				}else{
					
				}
			}
		});
    }
    Lists();
    renpage();
    function Lists(){
		var url,da,cols2;
		if( tit=='账户信息统计'){
		    url='accum/accuminfo.do';
		    da={
				   'userKey':userkey,
				   'date':$('#d1').val()
		    }
		    cols2=cols;
		}else{
		//签约信息统计
			url='accum/accumQY.do';
			da={
					'userKey':userkey,
					'dataBegin':$('#d1').val(),
					'pageNo':1,
					'pageSize':10
		   	}
			cols2=cols1;
		}
	   getlist(url,da,cols2);
   }
    //点击查询；
    $('.quoquery').click(function(){
    	Lists();
    });
    function getlist(obj,obj1,obj2){
    	 $.ajax({
			url:obj,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj1 ),
			async:false,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					if(obj=='accum/accumQY.do'){
						dialog.ren({'cols':obj2,'wh':wh,'userdata':userdata.list});
						listnumtotal=userdata.total;
					}else{
						dialog.ren({'cols':obj2,'wh':wh,'userdata':userdata});
					}
				}else{
					dialog.ren({'cols':obj2,'wh':wh,'userdata':''});
				}
			}
		});
    }
    $('.toexcel').click(function(){
		if( $('.box tbody tr').length>1 ){
			$(".mmg-body").table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: tit + new Date().toISOString().replace(/[\-\:\.]/g, ""),
				fileext: ".xls",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true
			});
		}
	})
	function renpage(){
    	layui.use(['laypage', 'layer'], function(){
    		  var laypage = layui.laypage,layer = layui.layer;
    		  //完整功能
    		  laypage.render({
    		    elem: 'page'
    		    ,count:listnumtotal
    		    ,theme: '#5a8bff'
    		    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    		    ,jump: function(obj,first){
    		    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
    		    		getlist('accum/accumQY.do',
    					{
							'userKey':userkey,
							'dataBegin':$('#d1').val(),
							'pageNo':obj.curr,
    		    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    	    	
    				   	},cols1);
    		    	}	
    		    }
    		  });
    	});
    }
})