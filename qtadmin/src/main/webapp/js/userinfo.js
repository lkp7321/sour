//require.config({
//	shim:{
//		'jquery.niceScroll': {
//　　　　　　deps: ['jquery'],
//　　　　　　exports: 'jquery.niceScroll'
//　　　　},
//		'mmGrid':{
//			deps:['jquery'],
//			exports:'mmGrid'
//		}
//	},
//	paths:{
//		jquery:'jquery-1.9.1.min',
//		mmGrid:'mmGrid',
//		niceScroll:'jquery.nicescroll.min'
//	}
//})
require(['jquery','mmGrid','niceScroll'],function($,mmGrid,niceScroll){
	// 列
    var cols = [
        { title:'用户名称', name:'username' ,width:100, align:'center' },
        { title:'角色', name:'role' ,width:100, align:'center'},
        { title:'机构名称', name:'from' ,width:150, align:'center'},
        { title:'真实姓名', name:'realname' ,width:80, align:'center'},
        { title:'移动电话', name:'phone' ,width:120, align:'center'},
        { title:'电话', name:'tel' ,width:100, align:'center'},
        { title:'传真', name:'cz' ,width:80, align:'center'},
        { title:'email', name:'email' ,width:100, align:'center'},
        { title:'mac/ip', name:'mac' ,width:80, align:'center'},
        { title:'备注', name:'beizhu',width:80, align:'center'},
        { title:'用户状态', name:'status' ,width:80, align:'center'}
    ];
	var mmg = $('#box').mmGrid({
			width:'1180px'
			,height:"580px"
			, cols: cols
            , url: '../../js/data.json'
            , method: 'get'
            , nowrap:true
          	/* , checkCol:true */
            , fullWidthRows:true
            ,showBackboard:false
       
       });
       
   	mmg.on('loadSuccess',function(e, data){
   		var str='';
      	$('thead tr th').each(function(i,v){
       	   str+="<li data-tit="+$(v).attr('class')+"><input	type='checkbox'/>"+$(v).find('.mmg-title').text()+"</li>"
       	})
      	str+="<button>隐藏</button>"
   		$('.setlist').html( str );
   	})

    $(".mmg-bodyWrapper").niceScroll({
			touchbehavior:false,
			cursorcolor:"#666",
			cursoropacitymax:0.7,
			cursorwidth:6,
			background:"#ccc",
			autohidemode:false
	});
})

/*
 * $(function(){ //列 var cols = [ { title:'用户名称', name:'username' ,width:100,
 * align:'center' }, { title:'角色', name:'role' ,width:100, align:'center'}, {
 * title:'机构名称', name:'from' ,width:150, align:'center'}, { title:'真实姓名',
 * name:'realname' ,width:80, align:'center'}, { title:'移动电话', name:'phone'
 * ,width:120, align:'center'}, { title:'电话', name:'tel' ,width:100,
 * align:'center'}, { title:'传真', name:'cz' ,width:80, align:'center'}, {
 * title:'email', name:'email' ,width:100, align:'center'}, { title:'mac/ip',
 * name:'mac' ,width:80, align:'center'}, { title:'备注', name:'beizhu',width:80,
 * align:'center'}, { title:'用户状态', name:'status' ,width:80, align:'center'} ];
 * var mmg = $('#box').mmGrid({ width:'1180px' ,height:"580px" , cols: cols ,
 * url: '../../js/data.json' , method: 'get' , nowrap:true , checkCol:true ,
 * fullWidthRows:true ,showBackboard:false
 * 
 * });
 * 
 * mmg.on('loadSuccess',function(e, data){ var str=''; $('thead tr
 * th').each(function(i,v){ str+="<li data-tit="+$(v).attr('class')+"><input
 * type='checkbox'/>"+$(v).find('.mmg-title').text()+"</li>" }) str+="<button>隐藏</button>"
 * $('.setlist').html( str ); }) $('.sett').click(function(e){ var
 * evt=e||window.event; if(evt.stopPropagation ){ evt.stopPropagation(); }else{
 * evt.cancelBubble=true; } $('.setlist').show(300); })
 * $('.setlist').click(function(e){ var evt=e||window.event;
 * if(evt.stopPropagation ){ evt.stopPropagation(); }else{
 * evt.cancelBubble=true; } })
 * 
 * 
 * $(document).click(function(){ $('.setlist').hide(300); })
 * $(".mmg-bodyWrapper").niceScroll({ touchbehavior:false, cursorcolor:"#666",
 * cursoropacitymax:0.7, cursorwidth:6, background:"#ccc", autohidemode:false
 * });
 *  })
 */
