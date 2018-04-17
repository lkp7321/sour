/*交易信息统计*/
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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('#d1,#d2').val( dialog.today() );
	//列参数;
    var cols = [
            { title:'机构号', name:'ogcd',width:60,align:'left' },
            { title:'交易机构名称', name:'ognm',width:60, align:'left'},
            { title:'买入黄金克数', name:'sumbyku',width:80, align:'right'},
            { title:'买入金额', name:'sumbycy',width:60, align:'right'},
            { title:'卖出黄金克数', name:'sumslku',width:60,align:'right'},
            { title:'卖出金额', name:'sumslcy',width:60, align:'right'},
            { title:'提取黄金克数', name:'sumswku',width:30, align:'right'}
    ];
    getofcd();
    function getofcd(){
    	 $.ajax({
			url:'/fx/getuserOgcd.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:false,
			success:function(data){
				var str='';
				if(data.code==01){
					userdata= data.codeMessage ;
					
					str+='<option ogcd='+userdata.ogcd+' leve='+userdata.leve+'>'+userdata.ognm+'</option>'
					$('.fitnma').html( str );
				}else{
					
				}
			}
		});
    }
    dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    //点击查询；
    $('.serch').click(function(){
    	 getlist();
    });
    function getlist(){
    	var da={
			dateBegin:$('#d1').val(),
			dateEnd:$('#d2').val(),
			trd_ogcd:{
				ogcd:$('.fitnma option:selected').attr('ogcd'),
				leve:$('.fitnma option:selected').attr('leve'),
				ognm:$('.fitnma option:selected').text()
			}
    	}
    	 $.ajax({
			url:'/fx/accum/accumtrans.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( da ),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
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
})