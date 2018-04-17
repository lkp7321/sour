require.config({
	baseUrl:'/fx/js',
	shim:{
		"niceScroll":{
			"deps":['jquery'],
			'exports':'niceScroll'
		},
		"template":{
			"deps":['jquery'],
			'exports':'template' 
		},
		"scrollbar":{
			"deps":['jquery'],
			'exports':'scrollbar'
		},
		'dialog':{
			"deps":['jquery'],
			'exports':'dialog'
		}
	},
    paths: {
        jquery: 'js_files/jquery-1.9.1.min',
        template:'js_files/template',
        niceScroll:'js_files/jquery.nicescroll.min',
        dialog:'dialog'
    }
});
require(['template','jquery','niceScroll','dialog'], function(template,$,niceScroll,dialog) {
	var listdata=sessionStorage.getItem('menu') ,
		product=sessionStorage.getItem('product'),setinnum=5;
	     
	$('body').on('click','.index .twosure',function(){
		clearInterval( ti );
		location.href="http://127.0.0.1:8080/fx/";
	});
	//点击关闭按钮;
	$('.lockbtn').click(function(){
		//console.log( sessionStorage.getItem('menu').userKey )
		$('#myfro input').remove();
		$('#myfro').append('<input type="text" name = "userKey" value='+ sessionStorage.getItem('menu').userKey+' style="position:relative;top:-400px;">' );
		
		sessionStorage.removeItem('menu');
		sessionStorage.removeItem('product');
		sessionStorage.removeItem('usnm');
		
		$('.lockbtn').submit();
	});
//	if(!listdata||!product){
//		dialog.choicedata('您还未登录,请先去登录<br/>'+setinnum+'秒后自动跳转&nbsp;<a href="/fx/" tit="立即跳转">立即跳转</a>','index');
//		var ti=setInterval(function(){
//			--setinnum;
//			if(setinnum==0){
//				location.href="http://127.0.0.1:8080/fx/";
//			}else{
//				$('.index p').html('您还未登录,请先去登录<br/>'+setinnum+'秒后自动跳转&nbsp;<a href="/fx/" tit="立即跳转">立即跳转</a>');
//			}
//		},1000);
//	}else{
		$('.username').text( sessionStorage.getItem('usnm') );
		$('.topright h6').text('友利银行');
		//左侧列表根据请求回来的数据进行更换右侧iframe
		if(listdata!=''){
			var menulist=JSON.parse( listdata).menuList;

			if( menulist[0].menu2[0].menKey1.nm=='欢迎页面' ){
				$('#ifra').attr('src', menulist[0].menu2[1].menKey1.ur );
			}else{
				$('#ifra').attr('src', menulist[0].menu2[0].menKey1.ur );
			}
			
			$('.siderbars').html(  template( $('.tem').html(),JSON.parse( listdata) ) );
			
			//隐藏没有的页面；
			/*$('.siderbars>li').each(function(){
				if( $(this).children('a').text()=='结售汇限制' ){	//隐藏结售汇限制；
					$(this).closest('li').hide();
				}
				if( $(this).children('a').text()=='万得报价' ){	//隐藏万得报价；
					$(this).closest('li').hide();
				}
			});
			$('.list-group-ul>li').each(function(){
				if( $(this).children('a').text()=='欢迎页面' ){	//隐藏欢迎页面；
					$(this).closest('li').hide();
				}
				if( $(this).children('a').text()=='特殊点差设置' ){	//隐藏特殊点差设置；
					$(this).closest('li').hide();
				}
				if( $(this).children('a').text()=='贵金属点差查询' ){	//隐藏贵金属点差查询；
					$(this).closest('li').hide();
				}
				if( $(this).children('a').text()=='历史价格查询' ){	//隐藏贵金属点差查询；
					$(this).closest('li').hide();
				}
			});*/
		}
	//}
	//点击三级li
	$('.list-group-thr').click(function(e){
		var evt=e||window.event;
		if(evt.stopPropagation ){
			evt.stopPropagation();
		}else{
			evt.cancelBubble=true;
		}
		if( $(this).children('.icol').hasClass('ico-carset') ){
			$(this).children('.icol').removeClass('ico-carset').addClass('ico-carsetup');
			$(this).children('.list-group-thrul').show(300);
		}else{
			$(this).children('.icol').addClass('ico-carset').removeClass('ico-carsetup');
			$(this).children('.list-group-thrul').hide(300);
		}
	});
	//点击三级中的li;
	$('.list-group-thr li').click(function(e){
		var evt=e||window.event;
		if(evt.stopPropagation ){
			evt.stopPropagation();
		}else{
			evt.cancelBubble=true;
		}
	});
	//二级里的li,切换页面；
	$('.list-group-ul li:not(".list-group-thr")').click(function(e){
		var evt=e||window.event;
		if(evt.stopPropagation ){
			evt.stopPropagation();
		}else{
			evt.cancelBubble=true;
		}
		var t=$(this).find('a').attr('ur');
			//更改右侧列表；
		    $('#ifra').attr('src',t);
	});
//		function checklist(t,tt,ttt){
//			var page='';
//				//判断二级；
//				switch(t){
//					case '用户信息':page='touser.do';break;
//					case '用户复核':page='touserFg.do';break;
//					case '角色管理':page='torole.do';break;
//					case '角色复核':page='toroleFg.do';break;
//					case '密码设置':page='toupPswd.do';break;
//					case '重置密码':page='toupPswd.do';break;
//					case '登录异常用户':page='toerror.do';break;
//	 
//					case '系统状态':page='tosystemstate.do';break;
//					case '币种管理':page='tomanamon.do';break;
//					//case '货币对管理':page='tocuurpair.do';break;
//					case '错误码管理':page='toerrcode.do';break;
//					case '机构管理':page='tooutfit.do';break;
//					case '平盘通道控制':page='toflatchan.do';break;
//					case '价格校验器设置':page='topricevalida.do';break;
//					case '品种管理':page='toproductsmana.do';break;
//					case '品种对管理':page='tovarie.do';break;
//					
//					//case '报价参数设置':page='toquotepar.do';break;
//					case '报价频率设置':page='toquotefrequ.do';break;
//					case '价格频率设置':page='toquotefrequ.do';break;
//					//case '产品干预':page='productintervene';break;
//					case '产品停牌查询':page='toproductstop.do';break;
//					case '手工停牌':page='tohandstop.do';break;
//					//case '产品校验参数':page='toproductcali.do';break;
//					case '产品均价校验器':page='toproductpri.do';break;
//					case '产品点差查询':page='toproductpoint.do';break;
//					case '手工报价录入':page='toquoteentry.do';break;
//					case '手工报价审核':page='toquoteaudit.do';break;
//					case '手工报价启用':page='toquoteenabled.do';break;
//					case '报价操作查询':page='toquoteoperation.do';break;
//					case '点差干预上限设置':page='tointervenemax.do';break;
//					case '点差干预设置':page='tointerveneset.do';break;
//					case '分类干预设置':page='tointerveneset.do';break;
//					case '分类干预上限设置':page='tointervenemax.do';break;
//					
//					
//					case '总分行价格':page='totalbranch.do';break;
//					case '客户价格':page='tocustom.do';break;
//					
//					
//					case '敞口流水查询':page='toopenwater.do';break;
//					case '敞口规则设置':page='toopenrule.do';break;
//					case '平盘规则设置':page='toplatrule.do';break;
//					case '平盘损益查询':page='toplatprofit.do';break;
//					case '特殊账户分类':case '特殊帐号分类':page='toaccountclass.do';break;
//					case '特殊账户收集':case '特殊帐号收集':page='toaccountcollect.do';break;
//					
//					case '挂单流水查询':page='toguadanwater.do';break;
//					//case '交易流水查询':page='todealwater.do';break;
//					case '最大优惠设置':page='tomaxpre.do';break;
//					//case '分行优惠设置':page='tobranchdiscount.do';break;
//					case '交易参数设置':page='todealparamter.do';break;
//					case '外币兑换流水查询':page='toforecha.do';break;
//					//case '出入金流水查询':page='toentryandexit.do';break;
//					case '客户签约信息':page='tocustomersign.do';break;
//					case '分行优惠查询':page='tobranchdis.do';break;
//					case '日历规则':page='tocalendarrule.do';break;
//					case '交易产品日历':page='todealprodut.do';break;
//					case '日历规则展示':page='tocalshow.do';break;
//					
//					case '客户级别管理':page='tocustomer.do';break;
//					
//					case '成交流水监控':page='todealwate.do';break;
//					case '平盘交易监控':page='toplatdeal.do';break;
//					case '异常数据监控':page='toabnor.do';break;
//					case '轧差敞口监控':page='tonetexposure.do';break;
//					case '累加敞口监控':page='tocumulativeexp.do';break;
//					//case '分行价格监控':page='tohandexp.do';break;
//					case '客户价格监控':page='tocuspriexp.do';break;
//					
//					case '敞口监控':page='toopenmonitor.do';break;
//					
//					//case '手工敞口登记':page='toopenreg.do';break;
//					case '手工敞口抹账':page='toopenacco.do';break;
//					case '手工敞口抹帐':page='toopenacco.do';break;
//					case '平盘交易查询':page='toflattrad.do';break;
//					case '清算汇总查询':page='toliquidate.do';break;
//					case '不明交易处理':page='tounknowntrans.do';break;
//					case '异常平盘处理':page='toabnormalplate.do';break;
//					case '平盘待处理查询':page='toflatload.do';break;
//					case '对账交易复核':page='tocheckbill.do';break;
//					
//					//case '产品参数':page='toproductpar.do';break;
//					case '产品参数':page='todealparamter.do';break;
//					case '最大优惠':page='tomaxpre.do';break;
//					///case '最大优惠':page='tobigdiscount.do';break;
//					//case '分行优惠设置':page='tohandparset.do';break;
//					//case '报价参数设置':page='toofferpataset.do';break;
//					case '利率配置':page='tointerestrate.do';break;
//					case '分红日':page='tobonusday.do';break;
//					case '活动专户管理':page='toactiveaccount.do';break;
//					case '活动专户查询':page='toactiveaccountsear.do';break;
//					case '积存金点差设置':page='toreserveset.do';break;
//					
//					case '国债流水查询':page='togoverloan.do';break;
//					case '现金流流水查询':page='tocashflow.do';break;
//					case '实盘流水查询':page='tofirmflow.do';break;
//					case '国债RPC请求':page='togoverrpc.do';break;
//					case '现金流RPC请求':page='tocashrpc.do';break;
//					case '外汇RPC请求':page='toforeignrpc.do';break;
//					
//					case '平盘交易':page='toflat.do';break;
//					case '客户交易':page='tocusto.do';break;
//					case '交易明细信息':page='todealdetail.do';break;
//					case '交易异常信息':page='todealabnor.do';break;
//					
//					case '系统参数设置':page='tosysparset.do';break;
//					//case '交易流水查询':page='todealwa.do';break;
//					case '异常交易监控':page='toabnormal.do';break;
//					case 'MT4用户组设置':page='toMT4users.do';break;
//					case '异常交易处理':page='toabnorhandle.do';break;
//					
//					case '批量出金核对':page='todatchgoldche.do';break;
//					case '批量出金复核':page='todatchgoldrev.do';break;
//					case '余额并账录入':page='tobalaninput.do';break;
//					case '余额并账复核':page='tobalanrev.do';break;
//					case '客户未平头寸录入':page='tocusinchinput.do';break;
//					case '客户未平头寸复核':page='tocusinchrev.do';break;
//					case 'MT4日终报表':page='toMT4report.do';break;
//					
//					case '审计管理':page='toauditmana.do';break;
//					//case '交叉盘计算':page='tooverlapp.do';break;
//					case '策略字典管理':page='topolicydicti.do';break;
//					case '历史价格查询':page='tohistorysear.do';break;
//					
//					case '账户交易价格监控':page="tohandexp2.do";break;
//					case '外汇点差查询':page="toforeignexc.do";break;
//					
//					case '价格源监控':page="topricemnit.do";break;
//					case '利率市场查询':page="tointerrate.do";break;
//					
//					case '价格使用申请':page="topriceuse.do";break;
//					case '价格使用审批':page="topriceuse.do";break;
//					case '价格接口配置':page="topriceuse.do";break;
//					
//					case '告警级别设置':page="toalarm.do";break;
//					case '告警用户设置':page="toalarmuser.do";break;
//					case '告警方式配置':page="toalarmmode.do";break;
//					case '告警事件查询':page="toalarmsearh.do";break;
//					case '告警代码管理':page="toalarmcode.do";break;
//					
//					case '外币敞口监控':page='toforcurexpmonitor.do';break;
//					case '结售汇敞口监控':page='tojieshopenmonitor.do';break;
//					case '贵金属敞口监控':page='topermetopenmonitor.do';break;
//					
//					case '原油手工平盘':page='tocrudeoilflat.do';break;
//					case '原油手工平盘流水查询':page='tocrudeoilflatwater.do';break;
//					case '贵金属追保':page='togjsinsurance.do';break;
//					case '贵金属强平':page='togjsstrongflat.do';break;
//					case '账户外汇追保':page='towhinsurance.do';break;
//					case '账户外汇强平':page='towhstrongflat.do';break;
//					
//					case '签约信息查询':page='tosigninformation.do';break;
//					case '持仓信息查询':page='topositionnews.do';break;
//					case '贵金属交易查询':page='toprecioustrading.do';break;
//					case '营销查询':page='tomarketing.do';break;
//					case '调整日配置信息查询':page='toadjustmentday.do';break;
//					case 'K线查询':page='toklinequrey.do';break;
//					case '账户余额查询':page='toaccountbalance.do';break;
//					
//					//case '校验器设置':page='tocalibrator.do';break;
//					//case '货币对配置':page='tocurpairconf.do';break;
//					//case '手工快速停牌':page='tohandstop.do';break;
//					case '手工快速停牌':page='tohandqukst.do';break;
//					case '价格接收设置':page='topriceget.do';break;
//					case '价格接收展示':page='toprigetsh.do';break;
//					case '价格加工设置':page='toprimachiset.do';break;
//					case '价格加工展示':page='toprimachshow.do';break;
//					case '价格源注册':page='toprisoureg.do';break;
//					
//					case '产品设置':page='toproductset.do';break;
//					//case '货币对配置':page='tocuurpairconfig.do';break;
//					case '产品加工信息':page='topromach.do';break;
//					case '交易开闭市管理':page='totradeocmana.do';break;
//					case '产品源监控':page='toprosource.do';break;
//					case '校验器设置':page="tovalida.do";break;
//					
//					case '撤销与修改流水查询':page="tomodifywater.do";break;
//					case '机构优惠':page="toagentdiscount.do";break;
//					case '客户等级优惠':page="touserlevaldiscount.do";break;
//					case '渠道优惠':page="tochanneldiscount.do";break;
//					case '卡bin优惠':page="tocardbin.do";break;
//					case '交易折美元优惠':page="todollardiscount.do";break;
//					
//					case '登记外管局流水查询':page='toregistsafe.do';break;
//					case '国别代码对照查询':page='toconuntrycode.do';break;
//					case '电子柜员管理':page='toelectrict.do';break;
//					case '外管局当前折算汇率查询':page='tocuurcon.do';break;
//					case '客户优惠查询':page='tocusdiscount.do';break;
//					case '东方支付流水查询':page='toesternay.do';break;
//					
//					case '签约流水查询':page='tosignflod.do';break;
//					case '定期申购签约解约查询':page='toregsigncon.do';break;
//					case '定期申购历史价格查询':page='toreghistor.do';break;
//					
//					case '修改客户等级':page='tomodifyuserleval.do';break;
//					case '添加客户等级':page='toadduserleval.do';break;
//					case '批量转账明细':page="tobattrandetails.do";break;
//					case '批量转账':page="tobattran.do";break;
//					case '金生金查询':page="togoldandgold.do";break;
//					case '电商积存金':page="toecbusiness.do";break;
//					case '更换客户号':page="tochangeusernum.do";break;
//					case '添加优惠规则':page="toadddiscountrules.do";break;
//					
//					case '账户信息统计':page="toaccounttotal.do";break;
//					case '交易信息统计':page="todealtotal.do";break;
//					case '签约信息统计':page="tosigninfo.do";break;
//					default :page='404';break;
//				}
//			
//				if(tt=='轧差敞口监控'){	
//					switch(t){
//						case '总敞口监控':page='tototalex.do';break;		//实盘-ga差-总敞口
//						case '分类敞口监控':page='tocumulat.do';break;
//					}
//				}else if(tt=='累加敞口监控'){
//					switch(t){
//						case '总敞口监控':page='tototalopen.do';break;	//实盘-累加-总敞口
//						case '分类敞口监控':page='toclassionopen.do';break;
//					}
//				}else if( tt=='报价平台管理'){
//					switch(t){
//						case '货币对管理':page='tocuurpairs.do';break;
//						case '交叉盘计算':page='tooverlapp.do';break;
//					}
//				}else if( tt=='账户交易'){
//					switch(t){
//						case '货币对管理':page='tocuurpairst.do';break;
//						case '交叉盘计算':page='tooverlapp1.do';break;
//						case '产品校验参数':page='toproductcali1.do';break;	//报价--账户交易-产品校验
//					}
//				}else if( tt=='系统管理'){
//					switch(t){
//						case '货币对管理':page='tocuurpair.do';break;
//					}
//				}else if( tt=='报价管理' ){
//					switch(t){
//						case '货币对管理':page='tocuurpair.do';break;
//						 case '产品校验参数':page='toproductcali1.do';break;		//账户交易-报价管理-产品校验
//					}
//				}else if( tt=='监控管理'){
//					switch(t){
//						case '分行价格监控':page='tohandexp.do';break;	//实盘-分行价格监控；
//						case '总敞口监控':page='tototalex.do';break;		//结售汇-总敞口监控
//					}
//				}else if( tt=='产品管理'){
//					 
//					switch(t){
//						case '分行价格监控':page='tohandexp1.do';break;
//						case '货币对配置':page='tocuurpairconfig.do';break;
//						case '产品校验参数':page='toproductcali2.do';break; 	//报价平台-产品管理-产品校验参数
//					}
//				}else if( tt=='价格源管理' ){
//					switch(t){
//						case '货币对配置':page='tocurpairconf.do';break;
//					}	
//				}else if( tt=='价格管理'){			//外汇-价格管理-产品&&纸黄金-价格管理
//					switch(t){
//					 case '产品校验参数':page='toproductcali.do';break;
//					}
//				}
//				
//				if( product=='个人外汇买卖平台'){
//					switch(t){
//					 case '分行优惠设置':page='tobranchdiscount.do';break;
//					 case '报价参数设置':page='toquotepar.do';break;
//					 case '交易流水查询':page='todealwater.do';break;
//					 case '手工敞口登记':page='toopenreg.do';break;
//					 case '出入金流水查询':page='toentryandexit.do';break;
//					}
//				}else if( product=='纸黄金交易管理平台'){
//					switch(t){
//					    case '分行优惠设置':page='tobranchdiscount.do';break;
//					    //case '分行优惠设置':page='tohandparset.do';break;
//					    case '报价参数设置':page='toquotepar.do';break;
//					    case '交易流水查询':page='todealwater.do';break;
//					    case '手工敞口登记':page='topagegoldregiste.do';break;
//					    case '总敞口':page="tototalexposure.do";break;
//						case '分类敞口':page="toclassionopen.do";break;
//					}
//				}else if( product=='积存金管理平台' ){
//					switch(t){
//						case '报价参数设置':page='toofferpataset.do';break;
//						case '分行优惠设置':page='tohandparset.do';break;
//						case '交易流水查询':page='toreverdealwater.do';break;
//						case '手工敞口登记':page='topagegoldregiste.do';break;
//						case '总敞口':page="tototalopen1.do";break;
//						case '分类敞口':page="toclassionopen1.do";break;
//					}
//				}else if( product=='保证金前置' ){
//					switch(t){
//						case '交易流水查询':page='todealwa.do';break;
//					}	
//				}else if( product=='报价平台' ){
//					 
//					switch(t){
//						case '报价参数设置':page='toquotepar.do';break;
//					}	
//				   if( ttt=='价格源管理'){
//						switch(t){
//						   case '点差干预上限设置':page='tointervenemax.do';break;
//						   case '分类干预设置':page='classionset.do';break;//报价平台 价格源管理 分类干预设置
//							 
//						}
//					}else if( ttt=='产品管理'){
//						switch(t){
//						   case '分类干预设置':page='tointerveneset.do';break;//报价平台 产品管理 分类干预设置
//						   case '点差干预上限设置':page='tointervenmaxquo.do';break; //报价平台 产品管理 分类干预上限设置
//						   case '报价参数设置':page='toquotepar.do';break; //报价平台 产品管理 报价参数设置
//						}
//						
//					}else if(ttt=='账户交易'){
//						switch(t){
//					        case '报价参数设置':page='toquoquoteparset.do';break; //报价平台 账户交易 报价参数设置
//						}
//					}
//				}else if( product=='账户交易' ){
//					switch(t){
//					case '交易流水查询':page='todealrecord.do';break;
//					case '手工敞口登记':page='toopenreg.do';break;
//					case '货币对管理':page='tocuurpairst.do';break;
//					case '交叉盘计算':page='tooverlapp1.do';break;
//					case '报价参数设置':page='toquotepar.do';break;
//				   }	
//			    }else if( product=='个人结售汇' ){
//					switch(t){
//					case '交易流水查询':page='todealwater1.do';break;
//					case '分行优惠设置':page='tobranchdiscount.do';break;
//				   }	
//			    }
//				
//				return page;
//		}
		//点击一级li进行列表展开和三角的上下切换；
		$('.siderbars>li').click(function(){
			
			$(this).find('.list-group-thrul').hide();
			$(this).find('.list-group-thrul').prev('i').removeClass('ico-carsetup').addClass('ico-carset');
			$(this).siblings().children('.user').hide(300);
			$(this).siblings().children('.icol').addClass('ico-carset').removeClass('ico-carsetup');
			if( $(this).children('.icol').hasClass('ico-carset') ){
				$(this).children('.icol').removeClass('ico-carset').addClass('ico-carsetup');
				$(this).children('.user').show(300);
			}else{
				$(this).children('.icol').addClass('ico-carset').removeClass('ico-carsetup');
				$(this).children('.user').hide(300);
			}
		});
		//点击收缩；
		$('.log2').click(function(){
			var wi,
				num=$(".botright #ifra").contents().find(".boxtop tr td").length;
			
			if( !$('.botleft').attr('shous') ){
				$('.botleft').attr('shous','shous');
				
				$('.botleft').animate({
					width:50+'px'
				},30);
				console.log( $('.botleft').width() )
				$('.botright').animate({
					marginLeft:5.43*0.85+'rem'
				},30);
				/*wi=$('body').width()-50;
				//console.log( wi )
				$(".botright #ifra").contents().find(".boxtop").css('width',wi+'px');
				//$(".botright #ifra").contents().find(".boxtop td").css('width',wi/num+'px');
*/			}else{
				$('.botleft').removeAttr('shous');
				$('.botleft').animate({
					width: 21.43*0.85+'rem'
				},30);
				$('.botright').animate({
					marginLeft:21.43*0.85+'rem'
				},30);
				/*//wi=$('body').width()-21.43*0.85;
				//console.log( wi )
				$(".botright #ifra").contents().find(".boxtop").css('width',wi+'px');*/
			}
			
			
		});
		//{"userKey":"ttGh6z3e20171208100236Administrator","Administrator":[{"one":"用户管理","two":[{"key":"欢迎页面"},{"key":"用户信息"},{"key":"用户复核"},{"key":"角色管理"},{"key":"角色复核"},{"key":"密码设置"},{"key":"登录异常用户"}]},{"one":"交易管理","two":[{"key":"最大优惠设置"},{"key":"分行优惠设置"},{"key":"交易参数设置"},{"key":"日历规则"},{"key":"交易流水查询"},{"key":"交易产品日历"},{"key":"分行优惠查询"},{"key":"日历规则展示"},{"key":"撤销与修改流水查询"}]},{"one":"监控管理","two":[{"key":"分行价格监控"},{"key":"客户价格监控"},{"key":"总敞口监控"}]},{"one":"价格管理","two":[{"key":"手工报价录入"},{"key":"手工报价审核"},{"key":"手工报价启用"},{"key":"报价操作查询"}]},{"one":"前置系统管理","two":[{"key":"登记外管局流水查询"},{"key":"国别代码对照查询"},{"key":"电子柜员管理"},{"key":"外管局当前折算汇率查询"},{"key":"客户优惠查询"},{"key":"东方支付流水查询"}]},{"one":"个人结售汇优惠","two":[{"key":"机构优惠"},{"key":"客户等级优惠"},{"key":"渠道优惠"},{"key":"卡bin优惠"},{"key":"交易折美元优惠"}]},{"one":"结售汇限制","two":[{"key":"结汇售汇限制"}]}]}
});

