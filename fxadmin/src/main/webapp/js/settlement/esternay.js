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
		WdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','WdatePicker','dialog'],function($,mmGrid,niceScroll,WdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
    $('#d1,#d2').val( dialog.today() );
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	//列参数;
    var cols = [
            { title:'流水号', name:'lcno',width:80,align:'left' },
            { title:'客户姓名', name:'cunm',width:80, align:'left'},
            { title:'国别', name:'cont',width:80, align:'left'},
            { title:'证件类型', name:'idtp',width:80, align:'center'},
            { title:'证件号码', name:'idno',width:80,align:'left' },
            { title:'卡号', name:'cuac',width:80, align:'left'},
            { title:'交易类型', name:'trtp',width:80, align:'center'},
            { title:'结售汇交易种类', name:'extp',width:80, align:'center'},
            { title:'结售汇交易项目代码', name:'extr',width:120,align:'right' },
            { title:'购汇用途', name:'copycout',width:80, align:'left'},
            { title:'外币名称', name:'fonm',width:80, align:'left'},
            { title:'外币金额', name:'fram',width:80, align:'right'},
            { title:'人民币金额', name:'cash',width:80,align:'right' },
            { title:'交易价格', name:'expc',width:80, align:'right'},
            { title:'钞汇标志', name:'cxfg',width:80, align:'center'}
    ];
    getlist({
		'dgfieldbegin':dialog.today(),
		'dgfieldend':dialog.today(),
		'comStcd':$('#byExnm option:selected').attr('value'),
		'pageNo':1,
		'pageSize':10
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selcDfzfWater.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击查询；
    $('.search').click(function(){
    	getlist({
    		'dgfieldbegin':$('#d1').val(),
    		'dgfieldend':$('#d2').val(),
    		'comStcd':$('#byExnm option:selected').attr('value'),
    		'pageNo':1,
    		'pageSize':10
        });
    });
  //导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "dgfieldbegin" value='+$('#d1').val()+'><input type="text" name = "dgfieldend" value='+$('#d2').val()+'>'+
		  		  '<input type="text" name = "comStcd" value='+$('#byExnm option:selected').attr('value')+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
   });
})