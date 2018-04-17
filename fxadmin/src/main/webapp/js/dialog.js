/*
 * 弹出框，iframe中的都可调用
 * */
define(['jquery'],function($){
	var imgsrc='<img src="/fx/img/up.png" class="up"/><img src="/fx/img/lower.png" class="lower"/>';
	function render(cols,obj,obj1){
		//公共渲染方法
		var mmg = $(obj).mmGrid({
				height:"73%"
				, cols: cols
	            , url: '/fx/js/personaloffer/data.json'
	            , method: 'get'
	            , fullWidthRows:true
	            ,showBackboard:true
	  	});
      $('.mmg-bodyWrapper').niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:false
		});
	}
	//check just twitce time
	function checknum(obj){
		var maxnum,maxnext,maxnnex,wrongnum=0;
		for(var i=0;i<obj.length;i++){
			maxnum=obj.substring(i,i+1);
			maxnext=obj.substring(i+1,i+2);
			maxnnex=obj.substring(i+2,i+3);
			if(maxnum==maxnext){
				if(maxnum==maxnnex){
					wrongnum++;
				}
			}else{
				i++;
			}
		}
		if(wrongnum>=1){
			return false;
		}else{
			return true;
		}
	}
	var add=function(obj){
		//添加弹框；
		var strr;
		var str='<div class="zhezhao userinfoadd">'+
					'<div class="cent abs">'+
						'<div class="top pubhead"><span>用户-添加</span><i class="userinfoclose close">×</i></div>'+
						'<div class="cer">'+
							'<div class="one"><p><span>用  户  名<r>*</r></span><input type="text" class="uername"/></p><p><span>用户密码<r>*</r></span><input type="password" class="psd" /></p></div>'+
							'<div class="two"><p><span>真实姓名<r>*</r></span><input type="text" class="realname"/></p><p><span>确认密码<r>*</r></span><input type="password" class="surepsd"/></p></div>'+
							'<div class="thr">'+
								'<p><span>移动电话<r>*</r></span><input type="text" class="phone" /></p>'+
								'<p class="juese"><span>角&nbsp;&nbsp;色<r>*</r></span><select></select></p>'+
							'</div>'+
							'<div class="four"><p>'+
								'<span>固定电话<r>*</r></span><input type="text" class="tel"/></p>'+
								'<p><span>传&nbsp;&nbsp;真</span><input type="text" class="fax"/></p>'+
							'</div>'+
							'<div class="five">'+
								'<p><span>EMAIL<r>*</r></span><input type="text" class="email"></p>'+
								'<p><span>用 户 IP</span><input type="text" class="macip"/></p>'+
							'</div>'+
							'<div class="six">'+
								'<p><span>机&nbsp;&nbsp;构<r>*</r></span></p>'+
								'<div class="sel_mo">'+
									'<select></select>'+
								'</div>'+
								/*'<p><span style="opacity:0;width:67px;">备&nbsp;&nbsp;注</span><input type="text" /></p>'+*/
								'<p>'+
									'<span>备&nbsp;注</span><input type="text" class="rmrk" />'+
								'</p>'+
							'</div>'+
						'</div>'+
						'<div class="centbot bot"><button class="userinfosav">保存</button><button class="userinfocance">取消</button></div>'+
					'</div>'+
				'</div>';
		//修改弹出框；
		var str1='<div class="zhezhao userinfochange">'+
					'<div class="cent changesent abs">'+
						'<div class="top pubhead"><span>用户-更新</span><i class="userinfoclose close">×</i></div>'+
						'<div class="cer">'+
							'<p><span>用  户  名</span><span class="usnm">秋天</span></p >'+
							'<div class="thr">'+
								'<p><span>真实姓名<r>*</r></span><input type="text" class="cmpt"/></p >'+
								'<p class="juese"><span>角&nbsp;&nbsp;色<r>*</r></span>'+
								'</p ><div class="sel_mo1">'+
									'<select></select>'+
								'</div>'+
							'</div>'+
							'<div class="four">'+
								'<p><span>移动电话<r>*</r></span><input type="text" class="mbtl"/></p >'+
								'<p><span>传&nbsp;&nbsp;真</span><input type="text" class="ufax" /></p >'+
							'</div>'+
							'<div class="five">'+
								'<p><span>固定电话<r>*</r></span><input type="text" class="telp"></p >'+
								'<p><span>用  户  IP</span><input type="text" class="macip" /></p >'+
							'</div>'+
							'<div class="one">'+
								'<p><span>EMAIL<r>*</r></span><input type="text" class="mail"></p >'+
							'</div>'+
						'<div class="six">'+
							'<p><span>机&nbsp;&nbsp;构<r>*</r></span></p >'+
							'<div class="sel_mo">'+
									'<select></select>'+
							'</div>'+
							/*'<p><span></span><input type="text" /></p >'+*/
							'<p><span>备&nbsp;注</span><input type="text" class="rmrk" /></p >'+
							'</div>'+
						'</div>'+
						'<div class="centbot bot"><button class="useraddfosav">保存</button><button class="userinfocance">取消</button></div>'+
						'</div></div>';
		switch(obj){
			case 'user':strr=str;break;
			case 'change':strr=str1;break;
		}
		bodyapp(strr);
	}
	//请选择一条数据的弹窗
	var choicedata=function(obj,obj1,a){ 
		//请先选择一条数据弹框;a用来判断是否是提示成功还是请选择一条数据；
		var str='<div class="zhezhao choicedialog '+obj1+'" data-tit='+a+'>'+
		    	   '<div class="choicecent abs"> <div class="pubhead">'+
				     '<span><r>提示:</r></sapn></div>'+
				 	'<p>'+obj+'</p>'+
				 	'<button class="sure twosure">确定</button>'+
				'</div></div>';
		bodyapp(str);
	}
	//删除和重置数据的弹窗
	var cancelDate=function(str,obj,obj1){
		var str='<div class="zhezhao '+obj+'">'+
                  '<div class="dia dia_reset abs" data-tit='+obj1+'>'+
                    '<div class="top_icon">!</div>'+
                      '<p class="dia_text">'+str+'</p>'+
                      '<div class="bot_btn ">'+
                      '<button class="confirm">确定</button>'+
                      '<button class="cancel">取消</button>'+
                    '</div>'+
                 '</div>'+
                '</div>'     
         bodyapp(str);                   
	}
	//复核数据的弹窗
	var checkDate=function(obj){
		 //角色复核;
		var str='<div class="zhezhao '+obj+'"> '+
		         ' <div class="dia_check abs">'+
			        '<div class="checktop pubhead">'+
				      '<span>用户状态-复核</span>'+
				    ' <i class="che_close close">×</i>'+
			       '</div>'+
			'<div class="checkcont">'+
				' <p class="checkname">用 户 名: <input type="text" readonly= "true " / value="cuijie"></p>'+
				' <p class="agentname">机构名称: <input type="text" readonly= "true "  / value="中国民生银行清算中心"></p>'+
				 '<p class="state">状&nbsp;&nbsp;态: <input type="radio" value="通过"  name="checkRadio" class="checkpass" checked="checked"/>通过<input type="radio" class="chenopass" value="未通过" name="checkRadio"/>未通过</p> '+
			'</div>'+
			'<div class="checkbot">'+
				'<button class="che_save">保存</button>'+
				'<button class="che_cancel">取消</button>'+
			 '</div>'+
			 '</div>'+
		 '</div>'
		  bodyapp(str);   
		 
	}
	/*角色管理添加的弹窗*/
	var rolemanaadd=function(obj,obj1){
		var a;
		if(obj=='add'){
			a='<input type="text" class="numb">';
		}else{
			a='<input type="text" class="numb" readonly="true">';
		}
		var str='<div class="zhezhao '+obj1+'">'
						 +'<div class="roledialog abs">'
						 	+'<div class="roletop pubhead '+obj+'"><span>角色-编辑</span><i class="rolemanaclose close">×</i></div>'
						 	+'<div class="cer">'
						 		+'<p class="numbp"><span>编&nbsp;&nbsp;号:<r>*</r></span>'+a+'</p>'
						 		+'<p><span>角色名称:<r>*</r></span><input type="text" class="rolename"></p>'
						 		+'<p class="cheradio"><span>使用标志:<r>*</r></span><input type="radio" name="a" data-tit="usestart">启用&nbsp;<input type="radio" name="a" data-tit="usestop" checked="checked"/>停用</p>'
						 		+'<p><span>描&nbsp;&nbsp;述:<r>*</r></span><input type="text" class="desc"></p>'
						 	+'</div>'
						 	+'<div class="bot centbot"><button class="rolemanasav">保存</button><button class="rolemanacance">取消</button></div>'
						 +'</div>'
					+'</div>'
		bodyapp(str);
	}
	/*系统管理-系统状态*/
	var systemadd=function(obj,obj1,obj2){
		var a,b;
		if(obj=='add'){
			a='添加卡号';  
			b='<input type="text" class="card"/>';
		}else{
			a='修改卡号'; 
			b='<span class="cardnum">'+obj2+'</span>';
		}
		var str='<div class="zhezhao '+obj1+'">'+
					'<div class="box abs" data-stat='+obj+'>'+
					'<div class="top pubhead">'+ 
					   '<span>系统状态--'+a+'</span><i class="close">×</i>'+
					'</div>'+
					'<div class="cen">'+
						'<p><san>交易卡号: </span>'+b+'</p>'+
						'<p><span>使用状态: </span><input type="radio" data-tit="star" name="aa" class="start" checked="checked">启用&nbsp;&nbsp;<input type="radio" class="nostart" data-tit="end" name="aa">停用</p>'+
					'</div>'+
					'<div class="bot centbot"><button class="sav">保存</button><button class="cancel">取消</button></div>'+
		    	  '</div></div>';
		bodyapp(str);  
	}
	/*机构管理*/
	var outfitadd=function(obj,obj1){
		var a,b,c;
		if(obj1=='add'){
			a='添加';  
			b='<select class="grademark"><option>请选择</option><option value="1">一级机构</option><option value="2">二级机构</option></select>';
			c='<span>自定义分行编号</span>';
		}else{
			a='修改'; 
			b='<input type="text" class="grademark"/>';
			c='<span>自定义一级分行编号</span>';
		}
		var str='<div class="zhezhao '+obj+'">'+
					'<div class="box abs">'+
					'<div class="top pubhead">'+ 
					   '<span>机构管理--'+a+'</span><i class="close">×</i>'+
					'</div>'+
					'<div class="cenr">'+
						'<p><span>机 构 编号</span><input type="text" class="outfitnum"/></p>'+
						'<p><span>机构名称</span><input type="text" class="outname"/></p>'+
						'<p><span>使用标志</span><input type="radio" value="0" name="a"/>启用&nbsp;<input type="radio" name="a" value="1" checked="checked"/>停用</p>'+
						'<p><span>等级标志</span>'+b+'</p>'+
						'<p>'+c+'<input disabled="true" type="text" class="custom"/></p>'+
						'<p><span>上级机构编号</span><select class="tsmall"></select></p>'+
					'</div>'+
					'<div class="bot"><button class="sav">保存</button><button class="cancel">取消</button></div>'+
		    	  '</div></div>';
		bodyapp(str);  
	}
	var outfitMerge=function(obj,obj1){
		var str='<div class="zhezhao '+obj+'">'+
		        '<div class="box abs '+obj1+'">'+
		        '<div class="top pubhead">'+ 
		        '<span>机构管理--合并</span><i class="close">×</i>'+
		        '</div>'+
			    '<div class="cenr">'+
				'<p><span>被撤机构</span><input type="text" class="removedagent" disabled="disabled"/></p>'+
				'<p><span></span>请选择目标机构</p>'+
			    '<p><span>合并到</span><select class="mergeagent"><option>请选择</option></select></p>'+
			    '<p><span></span>移动成功后自动删除被撤机构</p>'+
		'</div>'+
		'<div class="bot"><button class="merge">合并</button><button class="cancel">取消</button></div>'+
	  '</div></div>';
        bodyapp(str);  
	}
	/*快速搜索*/
	var serchData=function(txt){
		  if($.trim(txt)!=""){
             $(".mmGrid .mmg-bodyWrapper tr ").hide().filter(":contains('"+txt+"')").show();
          }else{
             $(".mmGrid .mmg-bodyWrapper tr ").show();
          }
	  };
	 /*客户级别管理*/
	var customeradd=function(obj,obj1){
		var a;
		if(obj1=='add'){
			a='添加';  
		}else{
			a='修改'; 
		}
		var str='<div class="zhezhao '+obj+'">'+
					'<div class="box abs '+obj1+'">'+
					'<div class="top pubhead">'+ 
					   '<span>客户级别--'+a+'</span><i class="close">×</i>'+
					'</div>'+
					'<div class="cenr">'+
						'<p><span>客户级别</span><input type="text" class="customerlevel"></p>'+
						'<p><span>级别名称</span><input type="text" class="levelname"></p>'+
					'</div>'+
					'<div class="bot centbot"><button class="sav">保存</button><button class="cancel">取消</button></div>'+
		    	  '</div></div>';
		bodyapp(str);  
	}
	//报价参数设置
	var priceModify=function(obj,obj1){
		var strr,a='',c;
		if(obj=="quoteparameter"  || obj=="intervenemax" || obj=="productstop"){
			if(obj1==1){
				strr= '<li class="li" data-index="four">校验</li><li data-index="five">停牌</li><li data-index="six">点差</li>';			
			}else if(obj1==0){
				strr='<li class="li" data-index="one">产品市场源头选择</li><li data-index="two">策略</li><li data-index="three">干预</li><li data-index="four">校验</li><li data-index="five">停牌</li><li data-index="six">点差</li>';
			}else if(obj1==2){
				strr='<li class="li" data-index="one">产品市场源头选择</li>';
			}else{
				strr='<li class="li" data-index="one">产品市场源头选择</li><li data-index="two">策略</li><li data-index="three">干预</li><li data-index="four">校验</li><li data-index="five">停牌</li><li data-index="six">点差</li>';
			}
			b='';
			c='display:none';
		}else if(obj=='offerparaset'){
			strr='<li data-index="two">策略</li><li data-index="six">点差</li>'
			b='';
			c='display:none';
			if(obj1=='0'){
				strr='<li data-index="six">点差</li>'
				b='';
				c='display:none';
			}
		}else if( obj=='productcalibrate1'){
			strr= '<li class="li" data-index="four">校验</li>';	
			b='';
			c='display:none';
		}else if( obj=='foreignexc'){
			strr='<li data-index="six">点差</li>'
			a="<div>点差分类:<span class='dotsort'></span></div>";
			b="display:none";
			c='';
		}else{
			strr= '<li class="li" data-index="four">校验</li><li data-index="five">停牌</li><li data-index="six">点差</li>'
			b='';
			c='display:none';
		}
		
		var str='<div class="zhezhao '+obj+'">'+
		             '<div class="dia_price abs">'+
					     '<div class="pricetop pubhead">'+
				            '<span>产品品种参数设置</span>'+
				            '<i class="price_close close">×</i>'+
			              '</div>'+
			              '<div class="pricecont">'+
			                 '<div class="priceconttop">'+
			                     '<div class="hbdname">货币对名称:<span></span></div>'+
			                     '<div class="productname">产品名称:<span></span></div>'+
			                     '<div class="privetype">价格类型:<span></span></div>'+
			                     '<div class="chnote">钞汇标志:<input type="radio" name="priceradi" class="priceradi0" tit="2" disabled="true"/>钞<input type="radio" name="priceradi" class="priceradi1" tit="1" disabled="true"/>汇</div>'+
			                     a+
			                 '</div>'+
			                 '<div class="pricecontmain">'+
			                    '<ul class="pricemaintab">'+strr+
                                '</ul>'+
                                '<div style="display:none;" class="prisource tabdiv one">'+
                                     '<div class="prisoleft">'+
                                         '<div class="marketsource sourcechoice">'+
                                            '<div class="markettop">产品市场源头选择</div>'+
                                            '<div class="marketcont">'+
                                            '</div>'+
                                          '</div>'+
                                          '<div class="sourcebtns">'+
                                              '<div class="priceadd">添加</div>'+
                                              '<div class="pricedelete">删除</div>'+
                                          '</div>'+
                                     '</div>'+
                                     '<div class="prisoright">'+
                                           '<div class="marketsource marktset">'+
                                            '<div class="marketline"></div>'+
                                            '<div class="markettop"><span class="span1">产品市场</span><span class="span2">市场权重设置</span></div>'+
                                            '<div class="marketcont marketcontr">'+
                                            '</div>'+
                                          '</div>'+
                                          '<div class="sourcebtns">'+
                                              '<div class="priceadd">上移</div>'+
                                              '<div class="pricedelete">下移</div>'+
                                          '</div>'+
                                    ' </div>'+
                                     '<div class="pribottom"><button class="pintbtn price_sav">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+
                                
                                '</div>'+
                                '<div style="display:none;" class="pristrategy tabdiv two">'+
                                      '<div class="pristrleft">'+
                                        ' <div class="currentstra"><span>当前策略：</span><div>市场优先</div></div>'+
                                         '<div class="marketsource">'+
                                            '<div class="markettop">系统支持策略</div>'+
                                            '<div class="marketcont marketcontr">'+                                               
                                            '</div>'+
                                          '</div>'+
                                          '<div class="sourcebtns">'+
                                           '</div>'+
                                      '</div>'+
                                      '<div class="pristrright">'+
                                         '<div class="marketsource">'+
                                            '<div class="markettop">修改后策略</div>'+
                                            '<div class="marketcont marketcontr">'+
                                            '</div>'+
                                          '</div>'+
                                       '</div>'+
                                       '<div class="pribottom"><button class="pintbtn price_sav">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+  
                                '</div>'+
                                '<div style="display:none;" class="priintervene tabdiv three">'+
                                     '<div class="pripubdivs pintleft">'+
                                        '<div><span>干预ID</span><input type="text" readonly="true" class="ctid"/></div>'+
                                        '<div><span>买入价干预点数</span><input type="text" class="nebp"/></div>'+
                                        '<div><span>起始时间</span><input type="text" id="sT"/><img src="/fx/img/calander.png" id="simg"/></div>'+
                                        '<div class="usestfg"><span>干预标志</span><input type="radio" tit="0" class="stfg0" name="stfg">干预<input type="radio" tit="1" class="stfg1" name="stfg">不干预</div>'+
                                    '</div>'+
                                    '<div class="pripubdivs pintright">'+
                                        '<div><span>干预器名称</span><input type="text" readonly="true" class="ctnm"/></div>'+
                                        '<div><span>卖出价干预点数</span><input type="text" class="nesp" /></div>'+
                                        '<div><span>截止时间</span><input type="text" id="eT"/><img src="/fx/img/calander.png" id="eimg"/></div>'+
                                    '</div>'+
                                    '<div class="pribottom"><button class="pintbtn price_save">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+
                                '</div>'+
                                '<div style="display:none;" class="pricheck tabdiv four">'+
                                     '<div class="pripubdivs pricleft">'+
                                        '<div><span>买入价</span><input type="text" class="byeprice"/></div>'+
                                        '<div><span>中间价两次浮动点差</span><input type="text" class="cenpricedot"/></div>'+
                                        '<div><span>价格多少次未变为无效</span><input type="text" class="pricenouse"/></div>'+
                                    '</div>'+
                                    '<div class="pripubdivs pricright">'+
                                        '<div><span>最大波动点差</span><input type="text" class="bigshavedot"/></div>'+
                                        '<div><span>合法波动次数</span><input type="text" class="goodnum"/></div>'+
                                        '<div><span>使用标志</span><input type="radio" name="priceradio" class="priceradio0" tit="0">启用<input type="radio" name="priceradio" class="priceradio1" tit="1"/>停用</div>'+
                                     '</div>'+
                                    '<div class="pribottom"><button class="pintbtn price_sav">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+  
                                '</div>'+
                                '<div style="display:none;" class="prisuspension tabdiv five">'+
                                    '<div class="pripubdivs prisleft">'+
                                        '<div><span>停牌器ID</span><input type="text" class="suspenid" value="SC99"/></div>'+
                                        '<div><span>停牌器名称</span><input type="text" class="suspenname" value="手工快速停盘"/></div>'+
                                        '<div><span>停牌标志</span><input type="radio" name="priceradio2" class="priceradio2" tit="0"/>正常<input type="radio" name="priceradio2" class="priceradio3"  tit="1"/>停牌</div>'+
                                    '</div><div style="clear:both"></div>'+
                                    '<div class="pribottom"><button class="pintbtn price_sav">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+  
                                
                                '</div>'+
                                '<div style="display:none;" class="prispread tabdiv six">'+
                                     '<div class="pripubdivs prispleft">'+
                                        '<div><span>价格生命周期(秒)</span><input type="text" class="pricelive"/></div>'+
                                        '<div style='+b+'><span>总行对分行买入差点</span><input type="text" class="buyindot"/></div>'+
                                        '<div ><span>总行对客户买入差点</span><input type="text" class="bytoutdot"/></div>'+
                                        '<div style='+c+'><span>更新频率(毫秒)</span><input type="text" class="refretime"/></div>'+
                                    '</div>'+
                                    '<div class="pripubdivs prispright">'+
                                        '<div><span>报价模式</span><input type="radio" name="priceradio4" class="priceradio4" tit="0"/>双边价<input type="radio" name="priceradio4" class="priceradio5" tit="1"/>中间价</div>'+
                                        '<div style='+b+'><span>总行对分行卖出差点</span><input type="text" class="sallhand"/></div>'+
                                        '<div><span>总行对客户卖出差点</span><input type="text" class="sallcust"/></div>'+
                                     '</div>'+
                                    '<div class="pribottom"><button class="pintbtn price_save">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+  
                                
                                 '</div>'+
                               
                                
			                 '</div>'+
			              '</div>'+
			            
			         '</div>'+   
		        '</div>';
		bodyapp(str);  
	}
	//报价频率设置
	var quofrequmodify=function(){
		var str='<div class="zhezhao"> '+
		         ' <div class="dia_freq abs">'+
			        '<div class="freqtop pubhead">'+
				      '<span>价格频率编辑</span>'+
				    ' <i class="freq_close close">×</i>'+
			       '</div>'+
			'<div class="freqcont">'+
				' <p class="freq_num">产品编号: <input type="text"/ value="P001"></p>'+
				' <p class="freq_name">产品名称: <input type="text"/ value="个人实盘"></p>'+
				' <p class="freq_type">价格类型: <input type="text"/ value="SPT"></p>'+
				' <p class="freq_quote">报价频率: <input type="number"/ value="3"></p>'+
				' <p class="freq_use">使用标志: <input type="radio" name="quoradio" data-tit="start" checked="checked"/>启用<input type="radio" class="freqstop" data-tit="stop" name="quoradio"/>停用</p> '+
			'</div>'+
			'<div class="freqbot">'+
				'<button class="freq_save">保存</button>'+
				'<button class="freq_cancel">取消</button>'+
			 '</div>'+
			 '</div>'+
		 '</div>'
		  bodyapp(str);   
	}
	//产品均价校验器的添加和修改
	var propricemodify=function(obj){
		var a,b,c,d,e,f;
		if(obj=='add'){
			a='添加';
			b='<button class="prop_add">添加</button>';
			c='<select></select>';
			d='<input type="text" value="0"/>';
			e='<select></select>';
			f='<input type="radio" name="priceradio0" class="priceradi0" data-tit="2"/>钞<input type="radio" name="priceradio0" checked="checked" class="priceradi1" data-tit="1"/>汇';
		}else{
			a='修改';
			b='<button class="freq_save">保存</button>';
			c='<input type="text" readonly="true"/>';
			d='<input type="text" readonly="true"/>';
			e='<input type="text" readonly="true"/>';
			f='<input type="radio" name="priceradio0" class="priceradi0" data-tit="2"/>钞<input type="radio" name="priceradio0"  class="priceradi1" data-tit="1"/>汇';
		}
		var str='<div class="zhezhao">'+ 
		           '<div class="dia_proprice abs">'+
		             '<div class="proptop pubhead">'+
				      '<span>产品均价校验器-'+a+'</span>'+
				      '<i class="prop_close close">×</i>'+
			         '</div>'+
			        '<div class="propcont pripubdivs">'+
				       '<div class="pricetype"><span>价格类型</span>'+c+'</div>'+
                       '<div class="priceterm"><span>期限</span>'+d+'</div>'+
                       '<div class="pcurrency"><span>货币对</span>'+e+'</div>'+
                       '<div class="pricesign"><span>钞汇标志</span>'+f+'</div>'+
			           '<div class="pricepoint"><span>浮动点差</span><input type="text" value="0"/></div>'+
			           '<div class="usesifn"><span>使用标志</span><input type="radio" name="priceradio" class="priceradio0" data-tit="0"/>启用<input type="radio" name="priceradio" class="priceradio1" checked="true" data-tit="1"/>停用</div>'+
			        '</div>'+
			        '<div class="propbot">'+b+
				       '<button class="prop_cancel">取消</button>'+
			        '</div>'+
		           '<div>'+
		        '</div>'
		bodyapp(str);
	}
	//手工报价录入的修改
	var quoteentrymodify=function(obj){
		var str='<div class="zhezhao '+obj+'">'+
		           '<div class="dia_quoteentry  abs">'+
		             '<div class="quoteentop pubhead">'+
				      '<span>手工报价录入-修改</span>'+
				      '<i class="quoteen_close close">×</i>'+
			         '</div>'+
			        '<div class="quoteencont pripubdivs">'+
			           '<div class="mkid"><span>市场编号</span><input type="text" readonly="true"/></div>'+
				       '<div class="tpnm"><span>价格类型</span><input type="text" readonly="true"/></div>'+
                       '<div class="term"><span>期限</span><input type="text" readonly="true"/></div>'+
                       '<div class="exnm"><span>货币对</span><input type="text" readonly="true"/></div>'+
                       '<div class="cxfg"><span>钞汇标志</span><input type="radio" name="priceradio" class="priceradio0" data-tit="2"/>钞<input type="radio" name="priceradio" class="priceradio1" data-tit="1"/>汇</div>'+
			           '<div class="neby"><span>买入价</span><input type="text"/></div>'+
			           '<div class="nesl"><span>卖出价</span><input type="text"/></div>'+
			           '<div class="nemd"><span>中间价</span><input type="text" readonly="true"/></div>'+
			        '</div>'+
			        '<div class="quoteenbot">'+
				       '<button class="quoteen_submit">提交</button>'+
				       '<button class="quoteen_cancel">取消</button>'+
			        '</div>'+
		           '<div>'+
		        '</div>'
		bodyapp(str);
	}
	//报价平台 价格源管理  干预上限设置的添加
	var intermaxmodify=function(obj){
		if(obj=='intermax'){
			var str1='<div class="exname"><span>价格类型</span><select><option></option></select></div>'+
				     '<div class="term"><span>期限</span><input type="text" value="0"/></div>'+
				     '<div class="money"><span>钞汇标志</span><input type="radio" name="moneyradio" data-tit="start" value="2"  class="priceradio"/>钞<input type="radio" name="moneyradio" data-tit="stop" value="1" checked="checked" class="priceradio"/>汇</div>';
		}else{
			var str1='<div class="maxprid"><span>产品名称</span><input type="text"  value="个人实盘"/></div>';
		}
		
		var str='<div class="zhezhao '+obj+'">'+
		           '<div class="dia_intermax abs">'+
		             '<div class="intermaxtop pubhead">'+
				      '<span>产品价格干预器总控</span>'+
				      '<i class="intermax_close close">×</i>'+
			         '</div>'+
			        '<div class="intermaxcont pripubdivs">'+
				       ''+str1+''+
                       '<div class="maxname"><span>货币对名称</span><select><option>请选择</option></select></div>'+
                       '<div class="maxcontrol"><span>总控开关</span><input type="radio" name="priceradio" data-tit="start"  class="priceradio"/>启用<input type="radio" name="priceradio" data-tit="stop" checked="checked" class="priceradio"/>停用</div>'+
			           '<div class="buymax"><span>买入价干预上限</span><input type="text" value="0"/></div>'+
			           '<div class="sallmax"><span>卖出价干预上限</span><input type="text" value="0"/></div>'+
			        '</div>'+
			        '<div class="intermaxbot">'+
				       '<button class="intermax_save">保存</button>'+
				       '<button class="intermax_cancel">取消</button>'+
			        '</div>'+
		           '<div>'+
		        '</div>'
		bodyapp(str);
	}
	/*敞口规则设置*/
	var openadd=function(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a='<p><span>产品名称</span><input type="text" class="produname" disabled="disabled"/></p><p><span>敞口编号</span><input type="text" class="opennumber"/></p>';
			b='<input type="text" class="openlevel" value="0"/>'+imgsrc+'';
		}else{
			a='<p><span>产品名称</span><input type="text" class="produname" disabled="disabled"/></p><p><span>敞口编号</span><input type="text" class="opennumber" disabled="disabled"/></p>';
			b='<input type="text" class="openlevel" value="0"/><img src="/fx/img/up.png" class="up"/><img src="/fx/img/lower.png" class="lower"/>';
		}
		var str='<div class="zhezhao '+obj+'">'+
					'<div class="box abs">'+
					'<div class="top pubhead" data-tit='+obj1+'>'+ 
					   '<span>平盘规则管理</span><i class="close">×</i>'+
					'</div>'+
					'<div class="cenr">'+
						'<div>'+a+'</div>'+
						'<div><p><span>敞口名称</span><input type="text" class="openname"/></p><p><span>规则级别</span>'+b+'</p></div>'+
						'<div><p class="usestate"><span>可用状态</span> <input type="radio" name="aa" data-tit="stop" checked="checked"/>停用 <input type="radio" name="aa" data-tit="start"/>开启<input type="radio" name="aa" data-tit="end"/>禁用</p></div>'+
						'<div class="four">'+
						'<div class="likesel">'+
						'<select class="firstsel"></select></div>'+
						'<span class="equalto">等于</span><select class="equal"><option value="<">小于</option><option value="<=">小于等于</option><option value="=">等于</option><option value=">">大于</option><option value=">=">大于等于</option></select>'+
						'<div class="likesel">'+
						'<select class="lastsel"></select></div>'+
						'<input type="input" id="val"/><button class="increate">增加</button></div>'+
						'<div class="five">'+
							'<div class="twolist">'+
							 	'<div class="twop"><p>规则表达式</p><p>规则描述</p></div>'+
								'<div class="lrlist"><ul class="llist"></ul>'+
								'<ul class="rlist"></ul></div>'+
							'</div>'+
							'<button class="remove">删除</button>'+
					'</div>'+
					'<div class="bot centbot"><button class="sav">保存</button><button class="cancel">取消</button></div>'+
		    	  '</div></div>';
		bodyapp(str);  
	}
	var accountcollectadd=function(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a='添加';
			b='';
		}else{
			a='修改';
			b="disabled=disabled";
		}
		var str='<div class="zhezhao '+obj+'"> '+
		        ' <div class="box abs '+obj1+'">'+
			        '<div class="top pubhead">'+
				      '<span>特殊账户收集--'+a+'</span>'+
				    ' <i class="close">×</i>'+
			        '</div>'+
					'<div class="cenr">'+
					   ' <div class="accountclass"><span>账户类型:</span><select '+b+'><option>请选择</option></select>'+
					    '</div>'+
					    '<p><span>卡号:</span><input type="text" class="card"></p>'+
					   ' <p class="accountstate"><span>账号状态:</span><input type="radio" checked="checked" name="aa" data-tit="start">启用<input type="radio" name="aa" data-tit="stop">停用</p>'+
					'</div>'+
					'<div class="bot">'+
						'<button class="save">保存</button>'+
						'<button class="cancel">取消</button>'+
					 '</div>'+
			 	'</div>'+
		 '</div>';
		  bodyapp(str);  
	}
	var accountclassadd=function(obj,obj1,obj2){
		var a,b;
		if(obj1=='add'){
			a='添加';
			if( obj2=='P002' || obj2=='P003'){
				b='non';
			}
		}else{
			a='修改';
		}
		console.log( obj2 )
		var str='<div class="zhezhao '+obj+'"> '+
		        ' <div class="box abs '+obj1+'">'+
			        '<div class="top pubhead">'+
				      '<span>特殊类型--'+a+'</span>'+
				    ' <i class="close">×</i>'+
			        '</div>'+
					'<div class="cenr">'+
					   ' <div class="accountclass"><span>类型名称:</span><input type="text" class="typename"/>'+
					    '</div>'+
					    '<p class='+b+'><span>类型编号:</span><input type="text" class="card"></p>'+
					   ' <p class="accountstate"><span>状态:</span><input type="radio" checked="checked" name="aa" data-tit="start">启用<input type="radio" name="aa" data-tit="stop">停用</p>'+
					'</div>'+
					'<div class="bot">'+
						'<button class="save">保存</button>'+
						'<button class="cancel">取消</button>'+
					 '</div>'+
			 	'</div>'+
		 '</div>';
		  bodyapp(str);  
	};
	var platruleadd=function(obj){
		var str='<div class="zhezhao '+obj+'">'
				   +'<div class="box abs">'
				   		+'<div class="pubhead"><span>平盘规则管理---敞口名称<r>其他</r>不能改为自动平盘</span><i class="close">×</i></div>'
				   +'<div class="cenr">'
				   		+'<div class="one">'
							+'<p><span>敞口名称</span><input type="text" readonly="true" class="platopenname"></p>'
							+'<p><span>货币对</span><input type="text" readonly="true" class="moneypa"></p>'
						+'</div>'
						+'<div class="two">'
							+'<p class="plattype"><span>平盘方式</span><input type="radio" data-tit="auto"  name="aa" checked="checked">自动<input type="radio" data-tit="hand" name="aa">手工</p>'
							+'<p><span>达限方式</span><select class="endlimit">'
							+'<option value="01">金额达限</option><option value="01">金额达限</option><option value="02">止盈点数</option><option value="03">止损点数</option><option value="04">止盈/止损点数</option><option value="05">止盈金额</option><option value="06">止损金额</option><option value="07">止盈/止损金额</option><option value="08">所有组合</option></select></p>'
						+'</div>'
						+'<div class="thr">'
							+'<p><span>达限金额</span><input type="text" class="endmoney"></p>'
							+'<p><span>平盘金额</span><input type="text" class="platmoney"></p>'
						+'</div>'
						+'<div class="four">'
							+'<p><span>止盈点数</span><input type="text" value="1" class="endbeatdot">'+imgsrc+'</p>'
							+'<p><span>止损点数</span><input type="text" value="1" class="endlossdot">'+imgsrc+'</p>'
						+'</div>'
						+'<div class="fiv">'
							+'<p><span>止盈金额</span><input type="text" class="endbeatmon"></p>'
							+'<p><span>止损金额</span><input type="text" class="endlossmon"></p>'
						+'</div>'
						+'<div class="six">'
							+'<p class="couuse"><span>可用状态</span><input type="radio" data-tit="start" name="bb" checked="checked">启用<input type="radio" data-tit="stop" name="bb">停用</p>'
							+'<p><span>可重复次数</span><input type="text" value="0" class="repeatnum">'+imgsrc+'</p>'
						+'</div>'
				   +'</div>'
				   +'<div class="bot"><button class="sav">保存</button><button class="cancel">取消</button></div></div></div>';
		bodyapp(str); 		   
	}
	var dealparamteradd=function(obj){
		var str='<div class="zhezhao">'
					+'<div class="box abs '+obj+'">'
						+'<div class="pubhead"><span>交易参数-参数修改</span><i class="close">×</i></div>'
						+'<div class="cer">'
							+'<p><span>参数编号</span><input type="text" readonly="true" class="paranum"></p>'
							+'<p><span>参数说明</span><input type="text" readonly="true" class="pararmrk"></p>'
							+'<p><span>参数值</span><input type="text" class="paravalu"></p>'
							+'<p class="parastatu"><span>参数状态</span><input type="radio" tit="0" class="start" name="aa">启用<input type="radio" tit="1" class="stop" name="aa">停用</p>'
						+'</div>' 
						+'<div class="bot"><button class="save">保存</button><button class="cancel">取消</button></div>'
					+'</div>'
				+'</div>'
		bodyapp(str); 		  
	}
	var calendarruleadd=function(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='提交';
		}else{
			a='更新';
			b='更改';
		}
		var str='<div class="zhezhao '+obj+'">'
				+'<div class="box abs">'
					+ '<div class="pubhead"><span>交易规则-'+a+'</span><i class="close">×</i></div>'
					 +'<div class="cenr">'
					    +'<p><span>规则等级:</span><r>*</r><select class="short"><option value="">请选择</option><option value="1">每周每日</option><option value="3">年度特殊日</option></select></p>'
					   	+'<p><span>规则名称:</span><r>*</r><input class="calendarName" type="text"/></p>'
					    +'<p class="isquantian"><span>是否全天:</span><input type="radio" name="aa" data-tit="yes" value="1"/>是<input type="radio" name="aa" data-tit="no" value="0" checked="checked"/>否</p>'
					    +'<p><span>起始时间:</span><r>*</r><input value="00:00:00" type="text" id="d241" class="wdate beginTime"/></p>'
					    +'<p><span>终止时间:</span><r>*</r><input value="23:59:59" type="text" id="d242" class="wdate endTime"/></p>'
					    +'<p><span>起始日期:</span><r>*</r><input type="text" id="d1" class="wdate beginDate"/></p>'
					    +'<p><span>终止日期:</span><r>*</r><input type="text" id="d2" class="wdate endDate"/></p>'
					    +'<p class="beginWeek"><span>起始星期:</span><r>*</r>'
					    	+'<select class="tsmall" >'
					    		+'<option value="0">星期日</option>'
					    		+'<option value="1">星期一</option>'
					    		+'<option value="2">星期二</option>'
					    		+'<option value="3">星期三</option>'
					    		+'<option value="4">星期四</option>'
					    		+'<option value="5">星期五</option>'
					    		+'<option value="6">星期六</option>'
					    	+'</select>'
					    +'</p>'
					    +'<p class="endWeek"><span>终止星期:</span><r>*</r>'
					    	+'<select class="tsmall">'
					    		+'<option value="0">星期日</option>'
					    		+'<option value="1">星期一</option>'
					    		+'<option value="2">星期二</option>'
					    		+'<option value="3">星期三</option>'
					    		+'<option value="4">星期四</option>'
					    		+'<option value="5">星期五</option>'
					    		+'<option value="6">星期六</option>'
					    	+'</select>'
					    +'</p>'
					    +'<p class="flag"><span>操作:</span><input type="radio" name="bb" data-tit="yes" value="0" checked="checked"/>开盘<input type="radio" name="bb" data-tit="no" value="1" />闭盘</p></div>'
					 +'<div class="bot"><button class="sav">'+b+'</button><button class="cance">取消</button></div>'
				+'</div>'
		+'</div>';
		bodyapp(str); 
	}
	var dealproductadd=function(){
		
	}
	var dealpromodi=function(obj,obj1){
		switch(obj1){
			case 'P001':obj1='个人外汇买卖平台';break;
			case 'P002':obj1='纸黄金交易管理平台';break;
			case 'P003':obj1='积存金管理平台';break;
			case 'P004':obj1='个人结售汇';break;
			case 'P007':obj1='账户交易';break;
			case 'P009':obj1='保证金前置';break;
			case 'P998':obj1='RV前置';break;
			case 'P999':obj1='报价平台';break;
			case 'JH01':obj1='友利银行';break;
		}
		var str='<div class="zhezhao '+obj+'">'+
					'<div class="box abs">'+
						'<div class="pubhead">'+
							'<span>交易产品规则-更新</span>'+
							'<i class="close">×</i>'+
						'</div>'+
						'<div class="centbox">'+
							'<p><span>产品名称:&nbsp;</span><i>'+obj1+'</i></p>'+
							'<p><span>交易码:<rr>*</rr></span><select id="product_sel1"><option>指定汇率买卖交易</option></select></p>'+
							'<p><span>规则等级:<rr>*</rr></span><select id="product_sel2"><option levetype="1">每周每日</option><option levetype="2">每周特殊日</option><option levetype="3">年度特殊日</option></select></p>'+
							'<p><span>产品名称:<rr>*</rr></span><select id="product_sel3"><option>网银结售汇</option></select></p>'+
						'</div>'+
						'<div class="centbot">'+
							'<button class="updata">更新</button><button class="cancel">取消</button>'+
						'</div>'+
					'</div>'+
				'</div>';
		bodyapp(str); 
	}
	var productsmana=function(obj,obj1){
		var a;
		if(obj1=='add'){
			a='添加';
		}else{
			a='修改';
		}
		var str='<div class="zhezhao '+obj+'"><div class="box abs">'+
				'<div class="pubhead"><span>品种管理-'+a+'</span><i class="close">×</i></div>'+
				'<div class="cer">'+
					'<p><span>品种代码:</span><input type="text" class="breedcode"></p>'+
					'<p><span>品种英文名称:</span><input type="text" class="breedEname"></p>'+
					'<p><span>品种中文名称:</span><input type="text" class="breedCname"></p>'+
					'<p><span>备注:</span><input type="text" class="remarks"></p>'+
					'<p><span>启用标志:</span><input type="radio" value="0" data-tit="start"  name="aa" >启用<input type="radio" value="1" name="aa" data-tit="stop" checked="checked">停用</p>'+
				'</div>'+
				'<div class="centbot"><button class="addsav">保存</button><button class="addcance">取消</button></div>'+
'</div>';
	bodyapp(str); 
	}
	
	function varietymana(obj,obj1){
		var a,str='';
		if(obj=='add'){
			a='添加';
		}else{
			a='修改';
		}
		var str='<div class="zhezhao '+obj+'"><div class="box abs">'
				  +'<div class="pubhead"><span>品种对管理-'+a+'</span><i class="close">×</i></div>'
				 +' <div class="cer">'
				  	  +'<p><span>品种对名称</span><select class="first"><option>KAG</option><option>RMB</option></select><select class="two"><option>KAG</option><option>RMB</option></select></p>'
				  	  +'<p><span>品种对编号</span><input type="text"></p>'
				  	  +'<p><span>品种对中文名称</span><input type="text"></p>'
				  	  +'<p><span>价格位点数</span><input type="text"></p>'
				  	  +'<p><span>品种对类型</span><input type="text"></p>'
				  +'</div>'
				 +'<div class="centbot"><button class="addsav">保存</button><button class="addcance">取消</button></div></div></div>';
		bodyapp(str); 		
	}
	function resetpsd( obj){
		var str='<div class="zhezhao '+obj+'"><div class="abs box">'
					+'<div class="pubhead"><span>修改密码</span><i class="close">×</i></div>'
                      +'<div class="cer psdset_input">'
				  	  +'<p><r>&nbsp;原密码</r><input type="password" id="old"></p>'
				  	  +'<p><r>&nbsp;新密码</r><input type="password" id="new"></p>'
				  	  +'<p><r>重复密码</r><input type="password" id="repeat"></p>'
				  +'</div>'
				 +'<div class="psdset_btn"><button class="addsav">保存</button><button class="addcance">取消</button></div></div></div>'
				+'</div>';
		bodyapp(str); 	
	}
	//权限新增;
	function poweradd(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a='<button class="addsav">全选</button><button class="addrole">增加</button><button class="addrolecance">取消</button>';
		}else if(obj1=='review'){
			a='<button class="addsav">全选</button><button class="passrole">通过</button><button class="nopassrole">未通过</button><button class="addrolecance">取消</button>';
		}else if( obj1=='see'){
			a='';
		}else{
			a='<button class="addsav">全选</button><button class="addrole">删除</button><button class="addrolecance">取消</button>';
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box">'
					+'<div class="pubhead" data-tit='+obj1+'><span>角色菜单-编辑</span><i class="close">×</i></div>'
				 	+'<div class="cer">'
				  	+'<table>'
				  	 	+'<thead><tr><th>修改</th><th>编号</th><th>菜单级别</th><th>使用</th></tr></thead>'
				  	 	+'<tbody><tr class="lockhead"><th>修改</th></tr></tbody>'
				  	+'</table>'
				  +'</div>'
				 +'<div class="pageTest"></div>'
				 +'<div class="centbot">'+a+'</div></div>'
				+'</div>';
		bodyapp( $(str) ); 
	}
	//参数设置；
	function productmodify(obj,obj1){
		var str='<div class="zhezhao '+obj+'"><div class="abs box">'
				+'<div class="pubhead" data-tit='+obj1+'><span>产品参数-修改</span><i class="productclose close">×</i></div>'
				+'<div class="cer">'
					+'<p><span>参数编号</span><span class="paranum"></span></p>'
					+'<p><span>参数说明</span><input type="text" class="pararmrk"/></p>'
					+'<p><span>参数值</span><input type="text" class="paravalue"/></p>'
					+'<p><span>参数准状态</span><input type="radio" name="productpraaa" data-tit="start" class="start"/>启用<input type="radio" data-tit="stop" name="productpraaa" class="stop"/>停用</p>'
				+'</div>'
				+'<div class="centbot"><button class="propasav">保存</button><button class="propacance">取消</button></div></div></div>'
				+'</div>';
		bodyapp( $(str) ); 
	}
	//最大优惠；
	function bigdiscount(obj,obj1){
		var str='<div class="zhezhao '+obj+'"><div class="abs box">'
				+'<div class="pubhead" data-tit='+obj1+'><span>最大优惠-添加</span><i class="bigdisclose close">×</i></div>'
				+'<div class="cer">'
					+'<p><span>机构号</span><span class="outnum"></span></p>'
					+'<p><span>机构名称</span><span class="outname"></span></p>'
					+'<p class="variename"><span>品种对名称</span><select>'					
					+'<option>KAGRMB</option><option>KAURMB</option><option>OAGUSD</option><option>OAUUSD</option><option>USDRMB</option>'
					+'<select></p>'
					+'<p><span>最大优惠点数</span><input type="text" class="bigdot"/></p>'
				+'</div>'
				+'<div class="centbot"><button class="propasav">保存</button><button class="propacance">取消</button></div></div></div>'
				+'</div>';
		bodyapp( $(str) ); 
	}
	function handparsetdia(obj,obj1){
		var str='<div class="zhezhao '+obj+'"><div class="abs box">'
				+'<div class="pubhead" data-tit='+obj1+'><span>最大优惠-添加</span><i class="bigdisclose close">×</i></div>'
				+'<div class="cer">'
					+'<p><span>机构号</span><span class="outnum"></span></p>'
					+'<p><span>机构名称</span><span class="outname"></span></p>'
					+'<p class="variename"><span>品种对名称</span><select>'					
					+'<option>KAGRMB</option><option>KAURMB</option><option>OAGUSD</option><option>OAUUSD</option><option>USDRMB</option>'
					+'<select></p>'
					+'<p><span>最大优惠点数</span><input type="text" class="bigdot"/></p>'
				+'</div>'
				+'<div class="centbot"><button class="propasav">保存</button><button class="propacance">取消</button></div></div></div>'
				+'</div>';
		bodyapp( $(str) ); 
	}
	var eventUtil = {
	        add : function(el, type, handler) {
	            if(el.addEventListener) {
	                el.addEventListener(type, handler, false);
	            }else if( el.attachEvent ) {
	                el.attachEvent("on"+type, handler);
	            }else{
	                el["on"+type] = handler;
	            }
	        },
	        off : function(el, type, handler) {
	            if( el.removeEventListener ) {
	                el.removeEventListener(type, handler, false)
	            }else if( el.detachEvent ) {
	                el.detachEvent(type, handler);
	            }else{
	                el["on"+type] = null;
	            }
	        }
	    };
	//渲染函数;
	function ren(obj){
		//console.log( obj.userdata )
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
    }
    //显示当前日期
	function today(){
	      var today=new Date();
	      var h=today.getFullYear();
	      var m=today.getMonth()+1;
	      var d=today.getDate();
	      m= m<10?"0"+m:m;      
	      d= d<10?"0"+d:d;  
	      return h+""+m+""+d;
	    }
	//渲染下拉列表框；
	function renlist(obj){
		$.ajax({
			url:obj.url,
			type:'post',
			contentType:'application/json',
			data:obj.listuser,
			async:false,
			success:function(data){
				var str='<option>所有</option>',listdata;
				if( data.code==00){
					 //当前没有数据；
				}else if(data.code==01){
					listdata=JSON.parse(data.codeMessage);
					for(var i=0,num=listdata.length;i<num;i++){
						str+='<option>'+listdata[i][obj.prcd]+'</option>';
					}
					obj.objec.html(str);
				}
			}
		});
	}
	function openlevel(obj){
		var str='<div class="zhezhao '+obj+'"><div class="abs box1">'
		+'<div class="pubhead"><span>敞口规则优先级</span><i class="close">×</i></div>'
		+'<div class="cer">'+
			'<div class="before"><p class="adjust">调整前</p><ul></ul></div>'+
		    '<div class="after"><p class="adjust">调整后</p><ul></ul></div>'+
		    '<div class="btns"><button class="top">上移</button><button class="bott">下移</button></div>'
		+'</div>'
		+'<div class="centbot"><button class="levelsav">保存</button><button class="levelcance">取消</button></div></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function productrate(obj,obj1){
		var a='',b='',c='',d;
		if(obj1=='add'){
			a='添加';
		    b='<div><span>类型</span><select class="type1"><option tit="a">请选择</option><option tit="G">份额</option><option tit="D">利率</option></select></div>';
		    d='';c='';
		}else{
			a='修改';
			b='';
			d="disabled=true";
			c='<p><span>类型</span><input type="text" '+d+' class="type2"/></p>';

		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box1">'
		+'<div class="pubhead" tit='+obj1+'><span>'+a+'产品利率配置</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+b
			+'<p><span>利率产品代码</span><input type="text" '+d+' class="intrapro"/></p>'
			+'<p><span>产品描述</span><input type="text" '+d+' class="prormrk"/></p>'
			+'<p><span>产品期限</span><input type="text" '+d+' class="proterm"/></p>'
			+'<p><span>计息方式</span><input type="text" '+d+' class="intercode"/></p>'
			+'<p><span>起存克数</span><input type="text" class="stargams"/></p>'
			+c
			+'<p><span>利率</span><input type="text" class="interrate"/></p>'
			+'<p><span>份额</span><input type="text" class="share"/></p>'
		+'</div>'
		+'<div class="centbot"><button class="save">保存</button><button class="cance">取消</button></div></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function dealproadd(obj, obj1){
		switch(obj1){
			case 'P001':obj1='个人外汇买卖平台';break;
			case 'P002':obj1='纸黄金交易管理平台';break;
			case 'P003':obj1='积存金管理平台';break;
			case 'P004':obj1='个人结售汇';break;
			case 'P007':obj1='账户交易';break;
			case 'P009':obj1='保证金前置';break;
			case 'P998':obj1='RV前置';break;
			case 'P999':obj1='报价平台';break;
			case 'JH01':obj1='友利银行';break;
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box3">'
			+'<div class="pubhead"><span>交易产品规则-添加</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<p class="calfam">产品名称:<span>'+obj1+'</span></p>'
				+'<p class="calfam">交易码:<span style="color:red;">*</span></p>'
				+'<div class="dealtop">'
					+'<div class="dtleft"><p>交易码名称</p><ul></ul></div>'
					+'<div class="btns"><button class="dtadd">添加</button><button class="dtbot">删除</button><button class="allche">全选</button><button class="allrem">全删</button></div>'
					+'<div class="dtright"><p>交易码名称</p><ul></ul></div>'
				+'</div>'
				+'<p class="calfam">日历规则组</p>'
				+'<div class="dealbot">'
					+'<div class="dbleft"><p>规则等级</p><select class="rulelevel"><option tit="">请选择</option><option tit="1">每周每日</option><option tit="2">每周特殊日</option><option tit="3">年度特殊日</option></select>'
					+'<p>日历规则</p><select class="calanderule"></select>'
					+'</div>'
					+'<div class="bbns"><button class="dbadd">添加</button><button class="dbrem">删除</button></div>'
					+'<div class="dbright"><p>日历规则</p><ul></ul></div>'
				+'</div>'
			+'</div>'
			+'<div class="centbot"><button class="dealsave">保存</button><button class="dealcance">取消</button></div></div>'
			+'</div>';
		bodyapp( $(str) );
	}
	function activeacuntmodify(obj){
		var str='<div class="zhezhao '+obj+'"><div class="abs box1">'
		+'<div class="pubhead"><span>添加对公账户</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>机构名称</span><select class="outfitname" onmousedown="if(this.options.length>6){this.size=7}" onblur="this.size=0" onchange="this.size=0" size="0"></select></div>'
			+'<p><span>产品编码</span><input type="text" class="productcode" disabled="true"/></p>'
			+'<p><span>专户名称</span><input type="text" class="specialname"/></p>'
		+'</div>'
		+'<div class="centbot"><button class="save">保存</button><button class="cance">取消</button></div></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function branchsearfn(obj){
		var a,
			product=sessionStorage.getItem('product');
		if( product=='P004'){
			a='<thead><tr><th>条件</th><th>优惠点数</th></tr></thead>';
		}else{
			a='<thead><tr><th>条件</th><th>VALUE</th><th>优惠点数</th></tr></thead>';
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>优惠规则编辑</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<table>'+a+'<tbody></tbody></table>'
		+'</div>'
		+'<div class="centbot"></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function flatdealwater( obj ){
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>平盘交易流水复核</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>行内流水号:</span><input type="text" readonly="true" class="inlinenum"></div>'
			+'<div><span>买入币种:</span><input type="text" readonly="true" class="byeincuur"><span>买入金额:</span><input type="text" readonly="true" class="byeinmoney"></div>'
			+'<div><span>卖出币种:</span><input type="text" readonly="true" class="sailcuur"><span>卖出金额:</span><input type="text" readonly="true" class="sailmoney"></div>'
			+'<div><span>成交汇率:</span><input type="text" readonly="true" class="dealrate"><span>平盘对手:</span><input type="text" readonly="true" class="flatopponent"></div>'
			+'<div><span>平盘日期:</span><input type="text" readonly="true" class="flatdata"><span>平盘时间:</span><input type="text" readonly="true" class="flattime"></div>'
			+'<div><span>平盘成交日期:</span><input type="text" readonly="true" class="flatdealdata"><span>交易状态:</span><input type="text" readonly="true" class="dealstate"></div>'
		+'</div>'
		+'<div class="centbot"><button class="succss">成功</button><button class="faile">失败</button></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function branchdisfn(obj,obj2,obj3){
		var a,b,c,str1='',str2='',
			allpags,aa,product=sessionStorage.getItem('product');
		box1=obj3;
		if( product=='P004'){
			aa='<thead><tr><th>条件</th><th>优惠点数</th></tr></thead>';
		}else{
			aa='<thead><tr><th>条件</th><th>VALUE</th><th>优惠点数</th></tr></thead>';
		}
		if( obj2=='F00'){
			a='<p><span>最大优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			b='<p><span>条件:</span></p>';
		}else if(obj2=='F01'){
			for(var i=0,num=box1.length;i<num;i++){
				str1+='<option cuty='+box1[i].QDNO+'>'+box1[i].QDNM+'</option>'
			}
			a='<p><span>渠道优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			b='<p><span>条件:</span><select class="condition">'+str1+'</select></p>';
			c=" ";
		}else if( obj2=='F02' ){
			a='<p><span>币别对优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			b='<p><span>条件:</span><input type="text" value="0" disabled="disabled"></p>';
			c="dissab";
		}else if( obj2=='F03'){
			a='<p><span>金额优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			b='<p><span>条件:</span><input type="text" value="0" class="tiaojian1"><=金额<<input type="text" class="tiaojian2"></p>';
			c=" ";
		}else if( obj2=='F05'){
			a='<p><span>收购优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			b='<p><span>条件:</span><input type="text" value="0" class="tiaojian5" disabled="disabled"></p>';
			c="dissab";
		}else if( obj2=='F04'){
			a='<p><span>客户优惠</span><input type="text" value='+obj2+' disabled="disabled"></p>';
			/*if( product=='P004' ){
				
			}else{
				box1=obj3.box1;
			}*/
			for(var i=0,num=box1.length;i<num;i++){
				str1+='<option cuty='+box1[i].CUTY+'>'+box1[i].TYNM+'</option>'
			}
			b='<p><span>条件:</span><select class="condition">'+str1+'</select></p>';
			c=" ";
		}
		
		var str='<div class="zhezhao '+obj+'"><div class="abs box3">'
		+'<div class="pubhead"><span>优惠规则编辑</span><i class="close">×</i></div>'
		+'<div class="cer">'
				+'<div>'+a+'<p><span>分行机构:</span><input type="text" readonly="true" class="branchorg"></p></div>'
				+'<div>'+b+'<p><span>修改值:</span><input type="text" class="changeval" value="0"></p></div>'
				+'<div class="btns"><p><button class="add '+c+'">添加</button><button class="remove '+c+'">删除</button></p><p><button class="succss">保存</button><button class="cance">取消</button></p></div>'
				+'<div><table class="datalist">'+aa+'<tbody>'+str2+'</tbody></table></div>'
			+'</div>'
		+'<div class="centbot"></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function changeda( obj,obj1 ){
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>请求流水修改</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>交易流水号</span><input type="text" readonly="true" class="dealnum"></div>'
			+'<div><span>请求流水号</span><input type="text" readonly="true" class="asknum"></div>'
			+'<div><span>产品编号</span><input type="text" readonly="true" class="pronum"></div>'
			+'<div><span>交易类型</span><input type="text" readonly="true" class="dealrate"></div>'
			+'<div><span>请求类型</span><input type="text" readonly="true" class="asktype"></div>'
			+'<div><span>重试次数</span><input type="text" readonly="true" class="rtime"></div>'
			+'<div><span>处理状态</span><input type="radio" name="aa" tit='+0+' class="r1">未处理<input type="radio" name="aa" tit='+obj1+' class="r2">'+obj1+'</div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">成功</button><button class="faile">失败</button></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function cuurpairfn(obj,obj2){
		var a;
		if( obj2=='add'){
			a='添加';
		}else{
			a='修改';
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>品种对-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>品种对名称</span><select class="fir"></select><select class="seco"></select></div>'
			+'<div><span>品种对编号</span><input type="text" readonly="true" class="asknum"></div>'
			+'<div><span>品种对中文名称</span><input type="text" readonly="true" class="pronum"></div>'
			+'<div><span>价格位点数</span><input type="text" class="dealrate" value="0"></div>'
			+'<div class="cuutype"><span>品种对类型</span><input type="radio" name="aa" disabled tit="0" class="r1">直盘<input type="radio" name="aa" disabled tit="1" class="r2">交叉盘</div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">保存</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) ); 
	}
	function maxprefn( obj,obj2 ){
		var a,b;
		if( obj2=='add'){
			a='添加';
			b='<select class="kagkau"></select>';
		}else{
			a='修改';
			b='<span class="kagkau"></span>';
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>最大优惠-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer '+obj2+'">'
			+'<div><span>机构号</span><input type="text" readonly="true" class="fitname"></div>'
			+'<div><span>机构名称</span><input type="text" readonly="true" class="asknum"></div>'
			+'<div><span>品种对名称</span>'+b+'</div>'
			+'<div><span>最大优惠点数</span><input type="text" class="dealrate"></div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">保存</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function addalarmFn1(obj,obj1){
		var a,b;
		if( obj1=='add'){
			a='添加';
			b='';
		}else{
			a='修改';
			b="readonly=true";
		}
		var str='<div class="zhezhao alarm1 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>告警级别-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>告警ID</span><input type="text" '+b+' class="fitname"></div>'
			+'<div class="rmrk"><span>告警描述</span><input type="text" class="asknum"></div>'
			+'<div><span>闪屏</span><input type="radio" name="a1" tit="0" class="a1">启用&nbsp;&nbsp;<input type="radio" name="a1" tit="1" class="a2" checked="checked">停用</div>'
			+'<div><span>声音</span><input type="radio" name="a2" tit="0" class="a3">启用&nbsp;&nbsp;<input type="radio" name="a2" tit="1" class="a4" checked="checked">停用</div>'
			+'<div><span>短信</span><input type="radio" name="a3" tit="0" class="a5">启用&nbsp;&nbsp;<input type="radio" name="a3" tit="1" class="a6" checked="checked">停用</div>'
			+'<div><span>电话</span><input type="radio" name="a4" tit="0" class="a7">启用&nbsp;&nbsp;<input type="radio" name="a4" tit="1" class="a8" checked="checked">停用</div>'
			+'<div><span>长时间闪屏</span><input type="radio" name="a5" tit="0" class="a9">启用&nbsp;&nbsp;<input type="radio" name="a5" tit="1" class="a10" checked="checked">停用</div>'
			+'<div><span>时间间隔(秒)</span><input type="text" class="dealrate"></div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">添加</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function addalarmFn2(obj,obj1){
		var a,b;
		if( obj1=='add'){
			a='添加';
			b=" ";
		}else{
			a='修改';
			b="readonly=true";
		}
		var str='<div class="zhezhao alarm2 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>告警用户-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>用户ID(内部生成)</span><input type="text" readonly="true" class="fitname"></div>'
			+'<div class="rmrk"><span>用户名称</span><input type="text" class="asknum" '+b+'></div>'
			+'<div><span>用户IP</span><input type="text" class="IP"></div>'
			+'<div><span>声音</span><input type="radio" name="a2" tit="0" class="a3">启用&nbsp;&nbsp;<input type="radio" name="a2" tit="1" class="a4" checked="checked">停用</div>'
			+'<div><span>电话(外拨使用)</span><input type="text" class="call"></div>'
			+'<div><span>手机(短信使用)</span><input type="text" class="phone"></div>'
			+'<div><span>使用状态</span><input type="radio" name="a5" tit="0" class="a9">启用&nbsp;&nbsp;<input type="radio" name="a5" tit="1" class="a10" checked="checked">停用</div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">添加</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function addalarmFn3(obj,obj1){
		var a,b;
		if( obj1=='add'){
			a='添加';
			b=" ";
		}else{
			a='修改';
			b="readonly=true";
		}
		var str='<div class="zhezhao alarm3 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>告警代码-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>告警代码</span><input type="text"  '+b+' class="fitname"></div>'
			+'<div class="rmrk"><span>告警描述</span><input type="text" class="asknum"></div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">添加</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function addalarmFn4(obj,obj1){
		var a,b,c;
		if( obj1=='add'){
			a='添加';
			b=" ";
			c='<select onmousedown="if(this.options.length>6){this.size=7}" onblur="this.size=0" onchange="this.size=0" size="0"></select>';
		}else{
			a='修改';
			b="readonly=true";
			c="<input type='text' disabled='true'>";
		}
		var str='<div class="zhezhao alarm4 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>告警错误通知用户-'+a+'</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div class="errcode"><span>告警错误码</span>'+c+'</div>'
			+'<div><span>错误描述</span><input type="text" disabled="true" class="errrmrk"/></div>'
			+'<div class="usernm"><span>用户姓名</span>'+c+'</div>'
			+'<div><span>告警级别</span><select class="polilevel"></select></div>'
			+'</div>'
		+'<div class="centbot"><button class="succss">保存</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function priceappcli(obj){
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
			+'<div class="pubhead"><span>价格申请器</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<div><span>申请人</span><input type="text" class="dealnum"></div>'
				+'<div><span>单位</span><input type="text" class="asknum"></div>'
				+'<div><span>联系电话</span><input type="text" class="pronum"></div>'
				+'<div><span>Email</span><input type="text" class="dealrate"></div>'
				+'<div><span>IP地址</span><input type="text" class="asktype"></div>'
				+'<div><span>最大连接数</span><input type="text" class="rtime" value="1"><i class="up"></i><i class="lower"></i></div>'
				+'<div><span>接口名称</span><input type="text" class="intname"></div>'
				+'<div><span class="intspan">接口说明</span><textarea class="intrmrk"></textarea></div>'
			 +'</div>'
		+'<div class="centbot"><button class="succss">保存</button><button class="faile">重填</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function pricemodify( obj ){
		var str='<div class="zhezhao priceuse3 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>价格使用审批-修改</span><i class="close">×</i></div>'
		+'<div class="cer">'
			+'<div><span>申请人</span><input type="text" class="dealnum" disabled="true"></div>'
			+'<div><span>单位</span><input type="text" class="asknum"  disabled="true"></div>'
			+'<div><span>联系电话</span><input type="text" class="pronum"  disabled="true"></div>'
			+'<div><span>Email</span><input type="text" class="dealrate"  disabled="true"></div>'
			+'<div><span>IP地址</span><input type="text" class="asktype"  disabled="true"></div>'
			+'<div><span>接口名称</span><input type="text" class="intname" disabled="true"></div>'
			+'<div><span>申请ID</span><input type="text" disabled="true" class="appID"></div>'
			+'<div><span>状态</span><input type="radio" name="aa" class="a1" tit="1">审批&nbsp;<input type="radio" tit="0" checked="checked" name="aa" class="a2">未通过</div>'
		 +'</div>' 
		+'<div class="centbot"><button class="succss">保存</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function priceconfig( obj ){
		var str='<div class="zhezhao priceuse2 '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>价格发布配置</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<div class="topp">'
				   +'<p><span>接口名称</span><input type="text" disabled="true" class="rpname"></p>'
				   +'<p><span>用户IP</span><input type="text" disabled="true" class="IP"></p>'
				   +'<p><span>接口ID</span><input type="text" disabled="true" class="ID"></p>'
				   +'<p><span>接口密码</span><input type="text" class="rppsd"></p>'
				+'</div>'
				+'<div>'
					+'<select class="choic" onmousedown="if(this.options.length>6){this.size=7}" onblur="this.size=0" onchange="this.size=0" size="0"></select>'
					+'<button class="add">添加</button>'
					+'<button class="rel">删除</button>'
				+'</div>'
				+'<div class="lrbox">'
					+'<div class="lft">'
						+'<p>币种名称</p>'
						+'<ul class="cuurname"></ul>'
					+'</div>'
					+'<div class="rightt">'
					+'<p>产品名称-币种名称</p><p class="hi">币种名称</p>'
					+'<ul class="productname"></ul>'
				+'</div>'
			+'</div>'
		 +'</div>' 
		+'<div class="centbot"><button class="success">保存</button><button class="faile">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function discountAdd(obj,str1,str2){
		if(str1=='机构优惠'){
			var text='<div><span>币种:</span><i>*</i><select class="currency"><option></option></select></div>';
		}else if(str1=='客户等级优惠'){
			var text='<div><span>客户等级:</span><i>*</i><input type="text" disabled="disabled" class="userleval"/></div><div><span>币种:</span><i>*</i><select class="currency"><option></option></select></div>';
		}else if(str1=='渠道优惠'){
			var text='<div><span>渠道:</span><i>*</i><input type="text" disabled="disabled" class="channel"/></div><div><span>币种:</span><i>*</i><select class="currency"><option></option></select></div>';
		}else if(str1=='卡bin优惠'){
			var text='<div><span>卡bin:</span><i>*</i><input type="text" class="channel"/></div>';
		}else if(str1=='交易折美元优惠'){
			var text='<div><span>起点金额:</span><i>*</i><input type="text" disabled="disabled" class="minmoney"/></div><div><span>终点金额:</span><i>*</i><input type="text"  class="maxmoney"/></div>';
		}else{
			var text='';
		}
		
		var str='<div class="zhezhao discountpub '+obj+'">'+
		        '<div class="abs box">'+
		        '<div class="pubhead"><span>'+str1+'-更新</span><i class="close">×</i></div>'+
			    '<div class="pripubdivs">'+
                    '<div><span>机构号:</span><i>*</i><input type="text" disabled="disabled" class="agentnum"/></input></div>'+text+''+
                    '<div><span>客户买入外币优惠折扣:</span><i>*</i><input type="text" class="buy"/></div>'+
                    '<div><span>客户卖入外币优惠折扣:</span><i>*</i><input type="text" class="sell"/></div>'+
                    '<div><span>起始日期:</span><i>*</i><input type="text" id="d1"/></div>'+
                    '<div><span>结束日期:</span><i>*</i><input type="text" id="d2"/></div>'+
                 '</div>'+
            	'<div class="dispubbot"><button class="preserve">'+str2+'</button><button class="cancel">取消</button></div>'+
		        '</div>'+
		        '</div>'; 
       bodyapp( $(str) );
	}
	function agentChoose(){
		var str='<div class="zhezhao discountpub agentchoose">'+
		        '<div class="abs box">'+
		        '<div class="pubhead"><span>个人结售汇-更新</span><i class="close">×</i></div>'+
			    '<div class="pripubdivs">'+
                    '<div><span>机构号:</span><input type="text" class="agentnum"/></div>'+
                    '<div><span>机构名称:</span><input type="text" class="agentname"/></div>'+
                    '<div><button class="qurey">查询</button></div>'+
                    '<div><button class="currentagent">选择当前机构</button></div>'+
                '</div>'+
            	'<div class="agentmain"><table></table></div>'+
            	'<div class="agentbot"></div>'+
		        '</div>'+
		        '</div>'; 
       bodyapp( $(str) );
	}
	function electrictfn( obj ){		
	/*	var str='<div class="'+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>电子柜员-获取柜员机构</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<div class="topp">'
				   +'<p><span>机构号&nbsp;</span><input type="text" class="rpname"></p>'
				   +'<p><span>机构名称&nbsp;</span><input type="text" class="IP"><button class="sear">查询</button></p>'
				   +'<p><button class="choicfit">选择当前机构</button></p>'
				+'</div>'
				+'<div class="lrbox">'
					+'<div class="lft">'
						+'<p>柜员机构号</p>'
						+'<ul class="cuurname"></ul>'
					+'</div>'
					+'<div class="rightt">'
					+'<p>机构名称</p>'
					+'<ul class="productname"></ul>'
				+'</div>'
			+'</div>'
		 +'</div>' 
		+'<div class="centbot"></div></div>'
		+'</div>';*/
		var str='<div class="discountpub exectric agentchoose">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>个人结售汇-更新</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
            '<div><span>机构号:</span><input type="text" class="agentnum"/></div>'+
            '<div><span>机构名称:</span><input type="text" class="agentname"/></div>'+
            '<div><button class="qurey">查询</button></div>'+
            '<div><button class="currentagent">选择当前机构</button></div>'+
        '</div>'+
    	'<div class="agentmain"><table></table></div>'+
    	'<div class="agentbot"></div>'+
        '</div>'+
        '</div>'; 
		bodyapp( $(str) );
	}
	/*function activeacunt( obj ){
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>电子柜员-获取桂圆机构</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<div class="topp">'
				   +'<p><span>机构名称&nbsp;</span><select class="fitname"></select></p>'
				   +'<p><span>产品编码&nbsp;</span><input type="text" disabled="disabled" class="pronum"></p>'
				   +'<p><span>专户名称&nbsp;</span><input type="text" class="specialnam"></p>'
				+'</div>'
			+'</div>'
		+'<div class="centbot"><button class="success">保存</button><button class="cancel">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}*/
	function electricadd(obj,obj1){
		var a,b,c;
		if(obj1=='add'){
			a="保存";
			b='添加';
			c='';
		}else{
			a='修改';
			b='修改';
			c='disabled=true';
		}
		var str='<div class="zhezhao '+obj+'"><div class="abs box2">'
		+'<div class="pubhead"><span>电子柜员-'+b+'</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<div><span>客户号</span><input type="text" class="counterem" '+c+'/></div>'
				+'<div class="stat"><span>状态</span><input type="radio" name="aa" class="chec" checked="true" tit="0">启用 <input type="radio" class="chec1" name="aa" tit="1">停用</div>'
				+'<div><span>结汇优惠</span><input type="text" class="jhyh"></div>'
				+'<div><span>购汇优惠</span><input type="text" class="ghyh"></div>'
				+'<div><span>机构</span><input type="text" disabled="true" class="outfit"><button class="choice">选择</button></div>'
			+'</div>'
		+'<div class="centbot"><button class="success">'+a+'</button><button class="cancel">取消</button></div></div>'
		+'</div>';
		bodyapp( $(str) );
	}
	function bonusdAdd(obj){
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>添加分红日</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
	        '<div><span>分红日:</span><input type="text" id="d1"/></div>'+
        '</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function pricegetadd( obj ){
		var str='<div class="zhezhao dealproduct '+obj+'"><div class="abs box3">'
			+'<div class="pubhead"><span>价格接收规则-添加</span><i class="close">×</i></div>'
			+'<div class="cer">'
				+'<p class="calfam">市场名称:<select class="markna"></select></p>'
				+'<p class="calfam">货币对:<span style="color:red;">*</span></p>'
				+'<div class="dealtop">'
					+'<div class="dtleft"><p>货币对</p><ul></ul></div>'
					+'<div class="btns"><button class="dtadd">添加</button><button class="dtbot">删除</button><button class="allche">全选</button><button class="allrem">全删</button></div>'
					+'<div class="dtright"><p>货币对</p><ul></ul></div>'
				+'</div>'
				+'<p class="calfam">日历规则组</p>'
				+'<div class="dealbot">'
					+'<div class="dbleft"><p>规则等级</p><select class="rulelevel"><option tit="">所有</option><option tit="1">每周每日</option><option tit="2">每周特殊日</option><option tit="3">年度特殊日</option></select>'
					+'<p>日历规则</p><select class="calanderule"></select>'
					+'</div>'
					+'<div class="bbns"><button class="dbadd">添加</button><button class="dbrem">删除</button></div>'
					+'<div class="dbright"><p>日历规则</p><ul></ul></div>'
				+'</div>'
			+'</div>'
			+'<div class="centbot"><button class="dealsave">保存</button><button class="dealcance">取消</button></div></div>'
			+'</div>';
		bodyapp( $(str) );
	}
	function modifyUserleval(obj){
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>客户等级修改</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
	        '<div><span>客户号:</span><input type="text" class="usernum" disabled="disabled"/></div>'+
	        '<div><span>卡号:</span><input type="text" class="cardnum" disabled="disabled"/></div>'+
	        '<div><span>客户等级:</span><input type="text" class="userleval"/></div>'+
        '</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function addUserleval(obj,str){
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>'+str+'</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
	        '<div><span>目前最高的客户等级:</span><input type="text" class="maxleval" disabled="disabled"/></div>'+
	        '<div><span>BP映射:</span><input type="text" class="bp"/></div>'+
	    '</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	//分类干预设置的添加
	function classionsetAdd(obj){
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>手工干预设置</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
	        '<div>'+
		        '<div><span>干预ID:</span><input type="text" class="interID" value="CC99" disabled="disabled"/></div>'+
		        '<div><span>干预器名称:</span><input type="text" class="intername" value="手工干预器" disabled="disabled"/></div>'+
	        '</div>'+
	        '<div>'+
	            '<div><span>价格类型:</span><select class="priceType"><option></option></select></div>'+
	            '<div><span>期限:</span><input type="text" class="term" value="0"/></div>'+
	        '</div>'+
            '<div>'+
                 '<div><span>货币对名称:</span><select class="currname"><option></option></select></div>'+
                 '<div class="money"><span>钞汇标志</span><input type="radio" name="money" data-tit="start"/>钞<input type="radio" name="money" data-tit="stop" checked="checked"/>汇</div>'+
	        '</div>'+
            '<div>'+
               '<div><span>买入价干预点差:</span><input type="text" class="buy" value="0"/></div>'+
	           '<div><span>卖出价干预点差:</span><input type="text" class="sell" value="0"/></div>'+
	        '</div>'+
             '<div>'+
               '<div><span>起始时间</span><input type="text" id="sT"/><img src="/fx/img/calander.png" id="simg"/></div>'+
               '<div><span>截止时间</span><input type="text" id="eT"/><img src="/fx/img/calander.png" id="simg"/></div>'+
            '</div>'+
	        '<div>'+
	            '<div class="intervene"><span>干预标志</span><input type="radio" name="intervene" data-tit="start"/>干预<input type="radio" name="intervene" data-tit="stop" checked="checked"/>不干预</div>'+
		        '<div><span></span><input type="text" class="tip" value="截止时间要大于起始时间"/></div>'+
		    '</div>'+
	    '</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function electrictellfn(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=true';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>电子柜员-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	        '<div><span>柜员ID:</span><input type="text" class="usernum" '+b+'/></div>'+
	        '<div><span>柜员名称:</span><input type="text" class="acuntname" /></div>'+
	        '<div><span>柜员密码:</span><input type="text" class="acuntpsd"/></div>'+
	        '<div><span>柜员类型:</span><input type="radio" name="aa" class="r1" checked="checked" tit="0"/> 操作柜员&nbsp;&nbsp;<input type="radio" name="aa" class="r2" tit="1"/>管理柜员</div>'+
	        '<div><span>外管局机构:</span><input type="text" class="outfit"/></div>'+
	        '<div><span>柜员渠道:</span><select class="countchannel">'+
	        	'<option tit="1101">网络银行</option>'+
		        '<option tit="1103">手机银行</option>'+
		        '<option tit="1105">本行自助终端</option>'+
	        '</select></div>'+
	        '<div><span>柜员机构:</span><input type="text" class="countfit" readonly="true"/><button class="choice">选择</button></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function pricegmodi( obj ){
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>价格接收规则-更新</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	        '<div><span>市场名称:</span><select class="markna"></select></div>'+
	        '<div><span>货币对:</span><select class="cuurpair"></select></div>'+
	        '<div><span>规则等级:</span><select class="ruleslev">'+
		        	'<option tit="">所有</option>'+
		        	'<option tit="1">每周每日</option>'+
		        	'<option tit="2">每周特殊日</option>'+
		        	'<option tit="3">年度特殊日</option>'+
	        	'</select></div>'+
	        '<div><span>日历规则:</span><select class="carrule" ></select></div>'+
        '</div>'+
        '<div class="dist"><button class="update">更新</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function primachisetadd( obj,obj1 ){
		var a;
		if(obj1=='add'){
			a="添加";
			b='提交';
		}else{
			a="修改";
			b='更新';
		}
		var str='<div class="zhezhao '+obj+'">'+
	        '<div class="abs box">'+
	        '<div class="pubhead"><span>价格加工规则-'+a+'</span><i class="close">×</i></div>'+
		    '<div class="prip">'+
		        '<div><span>产品名称:</span><select class="markna"></select></div>'+
		        '<div><span>规则等级:</span><select class="ruleslev">'+
			        	'<option tit="">所有</option>'+
			        	'<option tit="1">每周每日</option>'+
			        	'<option tit="2">每周特殊日</option>'+
			        	'<option tit="3">年度特殊日</option>'+
		        	'</select></div>'+
		        '<div><span>日历规则:</span><select class="carrule" ></select></div>'+
	        '</div>'+
	        '<div class="dist"><button class="update">'+b+'</button><button class="cancel">取消</button></div>'+
	        '</div>'+
	        '</div>'; 
	        bodyapp( $(str) );
	}
	function adddiscountAdd(obj){
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>添加优惠规则</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs">'+
	        '<div><span>机构号:</span><input type="text" class="ogcd"/></div>'+
        '</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function discountRuleEditAdd(obj,str2){
		if(str2=='between'){
			var str1='<div><span>较小值:</span><input type="small" class="small"/></div>'+
			         '<div><span>较大值:</span><input type="text" class="big"/></div>'+
                     '<div><span>优惠点数:</span><input type="text" class="dispoint"/></div>';
		}else{
			var str1='<div><span>值:</span><input type="text" class="numvalue"/></div>'+
	                 '<div><span>优惠点数:</span><input type="text" class="dispoint"/></div>';
		}
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>添加条件值</span><i class="close">×</i></div>'+
	    '<div class="pripubdivs '+str2+'">'+str1+'</div>'+
        '<div class="dispubbot"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function discountRuleEdit(obj,obj1){
		if(obj1=='add'){
			var str1='<select class="condition"><option value="choose">请选择</option><option value="bigger">大于</option><option value="smaller">小于</option><option value="equals">等于</option><option value="between">区间</option></select>';
		}else{
			var str1='<select class="condition" style="background-color: #EEEEEE;" disabled="disabled"><option></option></select>';
		}
		var str='<div class="zhezhao discountpub '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>优惠规则编辑</span><i class="close">×</i></div>'+
        '<div class="editbox '+obj1+'">'+ 
           '<div class="one">'+ 
               '<p><span>机构号：</span><input type="text" class="ogcd" disabled="disabled"/></p>'+
               '<p><span>优惠编码：</span><input type="text" class="disnum" disabled="disabled"/> </p>'+
               '<p><span>优惠名称：</span><input type="text" class="disname"/><span class="maxnum">(最多输入10个汉字)</span></p>'+
               '<p><span>最大优惠<span class="maxnum">（必输）</span>：</span><input type="text" class="maxdis"/></p>'+
           '</div>'+
           '<div class="two">'+ 
		       '<p><span>条件：</span>'+str1+'</p>'+
		       '<p><span>默认<span class="maxnum">（必输）</span>：</span><input type="text" class="static"/></p>'+
		       '<p><span>变量<span class="maxnum">（必输）</span>：</span><input type="text" class="variable"/></p>'+
		       '<p class="dispubbot">'+
		           '<button class="disadd">添加</button>'+
		           '<button class="disdel">删除</button>'+
		           '<button class="preserve">保存</button>'+
		           '<button class="cancel">取消</button>'+
		       '</p>'+
           '</div>'+
            
        '</div>'+
	    '<div class="pripubdivs">'+
	        '<p><span>条件</span><span>值</span><span>优惠点数</span></p>'+
	        '<div class="divsmain">'+ 
	        
	        '</div>'+
	        '<div class="kline1"></div><div class="kline2"></div>'+
	    '</div>'+
	    '<textarea rows="4" cols="123">set();</textarea>'+
	    '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function cuurencypairs( obj ){
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>货币对-更新</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	'<div><span>货币对名称:</span><select class="cuur1" disabled="disabled"></select><select class="cuur2" disabled="disabled"></select></div>'+
	        '<div><span>货币对编号:</span><input type="text" class="cuurnum" disabled="disabled"/></div>'+
	        '<div><span>货币对中文名称:</span><input type="text" class="chiname" disabled="disabled"/></div>'+
	        '<div><span>价格位点数:</span><input type="text" class="cuurdot" disabled="disabled"/></div>'+
	        '<div><span>放大倍率:</span><input type="text" class="bigstat"/></div>'+
	        '<div><span>计算系数:</span><input type="text" class="comnum"/></div>'+
	        '<div><span>货币对类型:</span><input type="radio" tit="0" class="r1" name="cuura" disabled="disabled"/> 直盘&nbsp;&nbsp;<input tit="1" type="radio" name="cuura"  class="r2" disabled="disabled"/> 交叉盘</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function prosetfn(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>产品设置-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	'<div><span>产品编号:</span><input type="text" '+b+' class="pronum"></div>'+
	        '<div><span>货币简码:</span><input type="text" class="cuurnum" '+b+'/></div>'+
	        '<div><span>产品名称:</span><input type="text" class="proname" '+b+'/></div>'+
	        '<div><span>价格频率:</span><input type="text" class="prihz"/></div>'+
	        '<div><span>开通状态:</span><input type="radio" name="r1" class="r1" checked="true" tit="0"/>开通&nbsp;&nbsp;<input type="radio" name="r1" class="r2" tit="1"/>关闭</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function promachadd(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>产品加工-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	'<div><span>产品编号:</span><input type="text" '+b+' class="pronum"></div>'+
	    	 '<div><span>产品类型:</span><input type="radio" name="r1" class="r1" checked="true" tit="0"/>非加工&nbsp;&nbsp;<input type="radio" name="r1" class="r2" tit="1"/>加工</div>'+
		     '<div><span>总分行价格池:</span><input type="radio" name="r2" class="r11" checked="true" tit="0"/>停用&nbsp;&nbsp;<input type="radio" name="r2" class="r22" tit="1"/>使用</div>'+
		     '<div><span>客户价格池:</span><input type="radio" name="r3" class="rr1" checked="true" tit="0"/>停用&nbsp;&nbsp;<input type="radio" name="r3" class="rr2" tit="1"/>使用</div>'+
		     '<div><span>历史价格表:</span><input type="radio" name="r4" class="re1" checked="true" tit="0"/>停用&nbsp;&nbsp;<input type="radio" name="r4" class="re2" tit="1"/>使用</div>'+
		     '<div><span>开通状态:</span><input type="radio" name="r5" class="rw1" tit="0"/>开通&nbsp;&nbsp;<input type="radio" name="r5" checked="true" class="rw2" tit="1"/>关闭</div>'+  
	         '<div><span>产品编码-手报:</span><input type="text" class="proexnm"/></div>'+
	         '<div><span>备注:</span><input type="text" class="rmrk"/></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function mt4add( obj,obj1 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>产品参数-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	'<div><span>组号:</span><input type="text" '+b+' class="pronum"></div>'+
	    	 '<div><span>组名:</span><input type="text" class="gruname"/></div>'+
		     '<div><span>描述:</span><input type="text" class="rmrk"/></div>'+
		     '<div><span>使用状态:</span><input type="radio" name="r3" class="rr1" checked="true" tit="0"/>启用&nbsp;&nbsp;<input type="radio" name="r3" class="rr2" tit="1"/>停用</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function mt4add1( obj,obj1 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>产品参数-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	'<div><span>参数编号:</span><input type="text" '+b+' class="pronum"></div>'+
	    	 '<div><span>参数说明:</span><input type="text" '+b+' class="gruname"/></div>'+
		     '<div><span>参数值:</span><input type="text" class="rmrk"/></div>'+
		     '<div><span>参数状态:</span><input type="radio" name="r3" class="rr1" checked="true" tit="0"/>启用&nbsp;&nbsp;<input type="radio" name="r3" class="rr2" tit="1"/>停用</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">保存</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	//报价参数设置
	var priceModify1=function(obj,obj1){
		var strr='<li class="li" data-index="five">校验</li>';	
		var str='<div class="zhezhao '+obj+'">'+
		             '<div class="dia_price abs">'+
					     '<div class="pricetop pubhead">'+
				            '<span>产品品种参数设置</span>'+
				            '<i class="price_close close">×</i>'+
			              '</div>'+
			              '<div class="pricecont">'+
			                 '<div class="priceconttop">'+
			                     '<div class="hbdname">货币对名称:<span></span></div>'+
			                     '<div class="productname">期限:<span></span></div>'+
			                     '<div class="privetype">价格类型:<span></span></div>'+
			                     '<div class="chnote">市场:<span></span></div>'+
			                 '</div>'+
			                 '<div class="pricecontmain">'+
			                    '<ul class="pricemaintab">'+strr+
                                '</ul>'+    
                                '<div style="display:none;" class="prisuspension tabdiv five">'+
                                    '<div class="pripubdivs prisleft">'+
                                        '<div><span>买入价</span><input type="text" class="suspe"/></div>'+
                                        '<div><span>单边浮动范围</span><input type="text" class="suspen"/></div>'+
                                       '</div><div style="clear:both"></div>'+
                                    '<div class="pribottom"><button class="pintbtn price_sav">保存</button> <button class="pintbtn price_cancel">关闭</button> </div>'+  
                                '</div>'+
			                 '</div>'+
			              '</div>'+
			            
			         '</div>'+   
		        '</div>';
		bodyapp(str);  
	}
	function adjustadd( obj,obj1 ){
		var a,b,c;
		if(obj1=='add'){
			a="添加调整日配置信息";
			b='<button class="add_sav">添加</button>';
			c='';
		}else{
			a="调整日信息查看";
			b='<button class="mod_sav">修改</button><button class="rem_sav">删除</button>';
			c='disabled=disabled'
		}
		var str='<div class="zhezhao '+obj+'">'+
	        '<div class="abs box">'+
		        '<div class="pubhead"><span>'+a+'</span><i class="close">×</i></div>'+
			    '<div class="prip">'+
			    	'<div><p><span>交易币种:</span><select class="dealcuur" '+c+'></select></p><p class="adjustda"><span>调整日</span><input type="text" class="adjustday" readonly="true" '+c+'/><img src="/fx/img/calander.png" id="simg"/></p></div>'+
			    	 '<div><p class="ljpri"><span>上期结算价格:</span><input type="text"/></p></div>'+
			    	 '<div><p class="jslsbid"><span>上期结算价格bid点差:</span><input type="text"/></p><p class="jslaask"><span>上期结算价格ask点差:</span><input type="text"/></p></div>'+
			    	 '<div><p class="chanjslsbid hid"><span>上期调整结算bid价格:</span><input type="text" disabled="disabled"/></p><p class="chanjslsask hid"><span>上期调整结算ask价格:</span><input type="text" disabled="disabled"/></p></div>'+
			    	
			    	 '<div><p class="nextjspri"><span>下期结算价格:</span><input type="text" /></p></div>'+
			    	 '<div><p class="nextjslsbid"><span>下期结算价格bid点差:</span><input type="text"/></p><p class="nextjslsask"><span>下期结算价格ask点差:</span><input type="text"/></p></div>'+
			    	 '<div><p class="chanjslsbidpri hid"><span>下期调整结算bid价格:</span><input type="text" disabled="disabled"/></p><p class="chanjslsaskpri hid"><span>下期调整结算ask价格:</span><input type="text" disabled="disabled"/></p></div>'+

			    	 '<div><p class="lamabiddot"><span>上期市场价格bid点差:</span><input type="text"/></p><p class="lamaaskdot"><span>上期市场价格ask点差:</span><input type="text"/></p></div>'+
	
			    	 '<div><p class="nebiddot"><span>下期市场价格bid点差:</span><input type="text"/></p><p class="nemaaskdot"><span>下期市场价格ask点差:</span><input type="text"/></p></div>'+
		        '</div>'+
		        '<div class="dist">'+b+'<button class="sav_cancel">取消</button></div>'+
		        '</div>'+
	        '</div>'; 
        bodyapp( $(str) );
	}
	function getnowTime(obj,obj1){
		var data=new Date(),
			year=data.getFullYear(),
			month=data.getMonth()+1,
			day=data.getDate(),str='';
		str=str+year;
		
		if(month<10){
			str+='0'+month
		}else{
			str+=month
		}
		if(day<10){
			str+='0'+day
		}else{
			str+=day
		}
		$(obj).val(str);
		$(obj1).val(str);
	}
	/*-----------------------youyi-----------------------*/
	function customfn( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>货币对:</span><input type="text" placeholder="请输入货币对" class="cuurpair"></div>'+
	    	 '<div class="chebox"><span>可交易:</span><input type="radio" class="gruname"checked="checked" name="aa" value="0"/>可交易<input type="radio" name="aa" class="nogruname" value="1"/>不可交易</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function dtparadd( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>编号:</span><input type="text" placeholder="请输入编号" '+b+' class="codenum"></div>'+
	    	 '<div><span>分类编号:</span><input type="text" placeholder="请输入分类编号" '+b+' class="clasnum"></div>'+
	    	 '<div><span>参数名称:</span><input type="text" placeholder="请输入参数名称" '+b+' class="sysstat"></div>'+
	    	 '<div><span>系统参数:</span><input type="text" placeholder="请输入系统参数" class="valu"></div>'+
	    	 '<div class="che_btn"><span>状态:</span><input type="radio" name="aa" vlue="0" checked="checked" class="stat"/>启用<input type="radio"  name="aa" vlue="1" class="end"/>停用</div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function cuurfn( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='disabled=disabled';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>币种英文名:</span><input type="text" placeholder="请输入币种英文名" '+b+' class="codenum"></div>'+
	    	 '<div><span>币种代码:</span><input type="text" placeholder="请输入币种代码" '+b+' class="clasnum"></div>'+
	    	 '<div><span>币种中文名:</span><input type="text" placeholder="请输入币种中文名" '+b+' class="sysstat"></div>'+
	    	 '<div class="che_btn"><span>状态:</span><input type="radio" name="ab" vlue="0" checked="checked" class="stat"/>启用<input type="radio"  name="ab" vlue="1" class="end"/>停用</div>'+
	    	 '<div><span>描述:</span><input type="text" placeholder="请输入描述"  class="remk"></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function cuurmsgfn( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
			c='<select class="onesel"></select><select><option>RMB</option></select>'
		}else{
			a="修改";
			b='disabled=disabled';
			c='<input type="text" class="codenum" '+b+'>'
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>币种对:</span>'+c+'</div>'+
	    	 '<div><span>币种对代码:</span><ss class="clasnum"></ss></div>'+
	    	 '<div><span>币种对中文名:</span><input type="text" placeholder="请输入币种对中文名" class="sysstat"></div>'+
	    	 '<div class="che_btn"><span>价格位点数:</span><input type="text" class="pricedot" placeholder="请输入价格位点数"/></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function ratemanafn( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>币种:</span><select class="onesel"></select></div>'+
	    	 '<div><span>名称:</span><ss class="namecode"></ss></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function priceparaadd( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='';
		}else{
			a="修改";
			b='';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>货币对:</span><input type="text" placeholder="请输入货币对" class="cuurpair"/></div>'+
	    	 '<div><span>中间价:</span><input type="text" placeholder="请输入中间价" class="cenpri"></div>'+
	    	 '<div><span>点差:</span><input type="text" placeholder="请输入点差" class="point"></div>'+
	    	 '<div><span>偏移量:</span><input type="text" placeholder="请输入偏移量" class="offset"></div>'+
	    	 '<div><span>有效时间:</span><input type="text" placeholder="请输入有效时间" class="valtime"></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function blacklistaadd( obj,obj1,obj2 ){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='none';
		}else{
			a="修改";
			b='';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>'+obj2+'-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div style="display:'+b+'"><span>名单编号:</span><input type="text" class="user_seqn"/></div>'+
	    	 '<div><span>客户中文名:</span><input type="text" placeholder="请输入客户中文名" class="cunm"/></div>'+
	    	 '<div><span>客户英文名:</span><input type="text" placeholder="请输入客户英文名" class="cuen"></div>'+
	    	 '<div><span>证件种类:</span><select class="idty"><option valu="ZR01">身份证</option></select></div>'+
	    	 '<div><span>证件号码:</span><input type="text" placeholder="请输入证件号码" class="idno"></div>'+
	    	 '<div><span>地址:</span><input type="text" placeholder="请输入地址" class="addr"></div>'+
	    	 '<div><span>交易日期:</span><input type="text" placeholder="请选择交易日期" id="sT" class="trdt" readonly="true"><img src="/fx/img/calander.png" id="simg"/></div>'+
	    	 '<div><span>交易时间:</span><input type="text" placeholder="请选择交易时间" class="trtm" readonly="true"><img src="/fx/img/calander.png" id="simg"/></div>'+
	    	 '<div><span>更新日期:</span><input type="text" placeholder="请选择更新日期" class="mddt" readonly="true"><img src="/fx/img/calander.png" id="simg"/></div>'+
	    	 '<div><span>更新时间:</span><input type="text" placeholder="请选择更新时间" class="mdtm" readonly="true"><img src="/fx/img/calander.png" id="simg"/></div>'+
	    	 '<div><span>电话:</span><input type="text" placeholder="请输入电话" class="tlno"></div>'+
	    	 '<div><span>手机号:</span><input type="text" placeholder="请输入手机号" class="mbno"></div>'+
	    	 '<div><span>传真号:</span><input type="text" placeholder="请输入传真号" class="fxno"></div>'+
	    	 '<div><span>邮编:</span><input type="text" placeholder="请输入邮编" class="mlno"></div>'+
	    	 '<div><span>邮箱:</span><input type="text" placeholder="请输入邮箱" class="mail"></div>'+
	    	 '<div><span>新交易标识:</span><input type="text" placeholder="请输入新交易标识" class="ntst"></div>'+
	    	 '<div><span>身份标识:</span><input type="text" placeholder="请输入身份标识" class="idst"></div>'+
	    	 '<div><span>交易柜员:</span><input type="text" placeholder="请输入交易柜员" class="trtl"></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+a+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function diCountriesAdd(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='保存';
		}else{
			a="修改";
			b='修改';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>国别-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>国别:</span><input type="text" placeholder="请输入国别" class="cname"></div>'+
	    	 '<div><span>别名:</span><input type="text" placeholder="请输入别名" class="cmbccout"></div>'+
	    	 '<div><span>外管别名:</span><input type="text" placeholder="请输入外管别名" class="cout"></div>'+
	    	 '<div><span>国别备注:</span><input type="text" placeholder="请输入国别备注" class="copycout"></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+b+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function tellerAdd(obj,obj1){
		var a,b;
		if(obj1=='add'){
			a="添加";
			b='保存';
		}else{
			a="修改";
			b='修改';
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>柜员管理-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>机构号:</span><input type="text" placeholder="请输入机构号" class="ogcd"></div>'+
	    	 '<div><span>交易柜员:</span><input type="text" placeholder="请输入交易柜员" class="trtl"></div>'+
	    	 '<div><span>密码:</span><input type="text" placeholder="请输入密码" class="pass"></div>'+
	    	 '<div><span>柜员类型:</span><input type="text" placeholder="请输入柜员类型" class="tltp"></div>'+
	    	 '<div><span>请求方机构代码:</span><input type="text" placeholder="请输入请求方机构代码" class="bhid"></div>'+
	    	 '<div><span>交易渠道:</span><input type="text" placeholder="请输入交易渠道" class="chnl"></div>'+
        '</div>'+
        '<div class="dist"><button class="preserve">'+b+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function errorCodeAdd(obj,obj1){
		var a,b,c;
		if(obj1=='add'){
			a="添加";
			b='保存';
			c="";
		}else{
			a="修改";
			b='修改';
			c="disabled=disabled";
		}
		var str='<div class="zhezhao '+obj+'">'+
        '<div class="abs box dtparpublic">'+
        '<div class="pubhead"><span>错误码管理-'+a+'</span><i class="close">×</i></div>'+
	    '<div class="prip">'+
	    	 '<div><span>错误码:</span><input type="text" placeholder="请输入错误码" class="ercd" '+c+'></div>'+
	    	 '<div><span>外部说明:</span><input type="text" placeholder="请输入外部说明" class="ertx"></div>'+
	    	 '<div><span>内部说明:</span><input type="text" placeholder="请输入内部说明" class="erin"></div>'+
	    	  
        '</div>'+
        '<div class="dist"><button class="preserve">'+b+'</button><button class="cancel">取消</button></div>'+
        '</div>'+
        '</div>'; 
        bodyapp( $(str) );
	}
	function bodyapp(str){ 
		$('body', parent.document).append(str);
	}
	return{
		"add":add,
		"choicedata":choicedata,
		"rolemanaadd":rolemanaadd,
		"cancelDate":cancelDate,
        "checkDate":checkDate,
		"serchData":serchData,
		"render":render,
		"systemadd":systemadd,
		"outfitadd":outfitadd,
		"outfitMerge":outfitMerge,
 		"customeradd":customeradd,
 		"openadd":openadd,
 		"quofrequmodify":quofrequmodify,
 		"priceModify":priceModify,
 		"accountcollectadd":accountcollectadd,
 		"accountclassadd":accountclassadd,
 		"platruleadd":platruleadd,        //平盘规则；
 		"propricemodify":propricemodify,
 		"quoteentrymodify":quoteentrymodify,
 		"dealparamteradd":dealparamteradd,
 		"intermaxmodify":intermaxmodify,
 		"calendarruleadd":calendarruleadd,
 		"productsmana":productsmana,
 		"dealpromodi":dealpromodi,
 		"varietymana":varietymana,
 		"resetpsd":resetpsd,
 		"poweradd":poweradd,
 		"productmodify":productmodify,     //参数设置中的产品参数
 		"bigdiscount":bigdiscount,
 		"handparsetdia":handparsetdia,     //初始化优惠规则;
 		"checknum":checknum,   //判断不能有三个连续形同字符的；
 		"ren":ren,
 		"renlist":renlist,
 		"today":today,
 		'openlevel':openlevel,
 		'productrate':productrate,
 		'activeacuntmodify':activeacuntmodify,
 		'branchsearfn':branchsearfn,
 		'dealproadd':dealproadd,
 		'flatdealwater':flatdealwater,
 		'branchdisfn':branchdisfn,
 		'changeda':changeda,
 		'cuurpairfn':cuurpairfn,
 		'maxprefn':maxprefn,
 		'addalarmFn1':addalarmFn1,
 		'addalarmFn2':addalarmFn2,
 		'addalarmFn3':addalarmFn3,
 		'addalarmFn4':addalarmFn4,
 		'priceappcli':priceappcli,
 		'pricemodify':pricemodify,
 		'priceconfig':priceconfig,
 		'electrictfn':electrictfn,
 		'electricadd':electricadd,
 		'discountAdd':discountAdd,
 		'agentChoose':agentChoose,
 		'bonusdAdd':bonusdAdd,
 		'pricegetadd':pricegetadd,
 		'modifyUserleval':modifyUserleval,
 		'addUserleval':addUserleval,
 		'electrictellfn':electrictellfn,
 		'pricegmodi':pricegmodi,
 		'primachisetadd':primachisetadd,
 		'adddiscountAdd':adddiscountAdd,
 		'discountRuleEdit':discountRuleEdit,
 		'cuurencypairs':cuurencypairs,
 		'prosetfn':prosetfn,
 		'promachadd':promachadd,
 		'discountRuleEditAdd':discountRuleEditAdd,
 		'mt4add1':mt4add1,
 		'mt4add':mt4add,
 		'priceModify1':priceModify1,
 		'getnowTime':getnowTime,
 		"classionsetAdd":classionsetAdd,
 		'adjustadd':adjustadd,
 		'customfn':customfn,
 		'dtparadd':dtparadd,
 		'cuurfn':cuurfn,
 		'cuurmsgfn':cuurmsgfn,
 		'ratemanafn':ratemanafn,
 		'priceparaadd':priceparaadd,
 		'blacklistaadd':blacklistaadd,
 		'diCountriesAdd':diCountriesAdd,
 		'tellerAdd':tellerAdd,
 		'errorCodeAdd':errorCodeAdd
	};
});
