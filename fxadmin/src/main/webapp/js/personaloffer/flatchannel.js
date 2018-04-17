/*平盘通道控制*/
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
        { title:'产品名称', name:'prcd' ,width:150, align:'left' },
        { title:'货币对名称', name:'exnm' ,width:200, align:'left'},
        { title:'通道状态', name:'stat' ,width:250, align:'center'},
    ];
    var usnm=sessionStorage.getItem('usnm'),
    	product=sessionStorage.getItem('product'),
    	userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	str,productName,
    	loginuser={'usnm':usnm,'prod':product};
    
    //请求列表
    getPPchannel(userkey);
	//启用框停用框弹出
	$('.flat_open,.flat_close').click(function(){
		var a=0,arr=[],stat;
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据再进行此操作','flatchannel');
		}else{
			$('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'exnm':$(v).find('td').eq(2).find('span').text(),
			      });
				}
			});
		  var usfg=$(this).text();
		      if(usfg=='开启'){
		    	  stat=0;
		      }else{
		    	  stat=1;
		      }
		  var sysVo={'userKey':userkey,'list':arr,'s':stat};	 
		    $.ajax({
		    	url:'/fx/changeChannel.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(sysVo),
		    	async:true,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==01){
		    			dialog.choicedata(data.codeMessage,'flatchannel');
		    			getPPchannel(userkey);
		    		}else if(data.code==00){
		    			//异常 
		    			dialog.choicedata(data.codeMessage,'flatchannel');
		    		} 
		    	  }
		      });  	
		}
	});
	$('body',parent.document).on('click','.flatchannel .sure',function(){
		//关闭选择一条数据;
	  $(this).closest('.zhezhao').remove();
	});
	//封装请求列表
	function getPPchannel(obj){
		$.ajax({
	    	url:'/fx/getPPchannel.do',
	        type:'post',
	    	contentType:'application/json',
	    	data:obj,
	    	async:true,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==01){
	    			 dialog.ren({'cols':cols,'wh':wh,'userdata':data.codeMessage,'checked':true});
	    		}else if(data.code==00){
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    		}
	    	  }
	      });
		
	};
})