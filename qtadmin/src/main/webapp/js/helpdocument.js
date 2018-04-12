var data=[
		  {'one':{'href':'#h_1','name':'1.易宽软件介绍'},
		   'two':[{'href':'#h_1.1','name':'1.1软件界面','three':[]},
		          {'href':'#h_1.2','name':'1.2系统菜单','three':[]},
		          {'href':'#h_1.3','name':'1.3功能菜单','three':[{'href':'#h_1.3.1','name':'1.3.1账户信息'},
		                                                      {'href':'#h_1.3.2','name':'1.3.2自动交易'},
		                                                      {'href':'#h_1.3.3','name':'1.3.3银期转账'},
		                                                      {'href':'#h_1.3.4','name':'1.3.4日志'},
		                                                      {'href':'#h_1.3.5','name':'1.3.5行情'},
		                                                      {'href':'#h_1.3.6','name':'1.3.6委托'},
		                                                      {'href':'#h_1.3.7','name':'1.3.7合约移仓任务与合约移仓'},
		                                                      {'href':'#h_1.3.8','name':'1.3.8持仓'},
		                                                      {'href':'#h_1.3.9','name':'1.3.9合约列表（标准合约与自选合约'},
		                                                      {'href':'#h_1.3.10','name':'1.3.10成交统计'},
		                                                      {'href':'#h_1.3.11','name':'1.3.11策略任务'},
		                                                      {'href':'#h_1.3.12','name':'1.3.12历史结算单'},
		                                                      {'href':'#h_1.3.13','name':'1.3.13统计图表'},
		                                                      {'href':'#h_1.3.15','name':'1.3.15在线问答'},
		                                                      {'href':'#h_1.3.16','name':'1.3.16期货公司维护'},
		                                                      
		                                                     ]},
		           {'href':'#h_1.4','name':'1.4状态栏','three':[{'href':'#h_1.4.1','name':'1.4.1行情/交易服务器连接状态'},
		                                                      {'href':'#h_1.4.2','name':'1.4.2交易所时间显示'},
		                                                      {'href':'#h_1.4.3','name':'1.4.3期货开户方'}
		                                                    ]}                                          
		         ]
		  },
		  {'one':{'href':'#h_2','name':'2.易宽软件操作 '},
		   'two':[{'href':'#h_2.1','name':'2.1如何进行窗口布局','three':[]},
		          {'href':'#h_2.2','name':'2.2如何快速下单、撤单','three':[{'href':'#h_2.2.1','name':'2.2.1网格快速下单'},
		                                                      {'href':'#h_2.2.2','name':'2.2.2持仓列表快速下单'},
		                                                      {'href':'#h_2.2.3','name':'2.2.3下单板快捷下单'},
		                                                      {'href':'#h_2.2.4','name':'2.2.4网格快速撤单'},
		                                                      {'href':'#h_2.2.5','name':'2.2.5委托列表快速撤单'},
		                                                     ]},
		          {'href':'#h_2.3','name':'2.3如何进行自动（网格乒乓）交易','three':[]},
		          {'href':'#h_2.4','name':'2.4如何进行合约移仓','three':[{'href':'#h_2.4.1','name':'2.4.1手动移仓操作	'},
		                                                      {'href':'#h_2.4.2','name':'2.4.2自动移仓操作'},
		                                                      
		                                                    ]}, 
		          {'href':'#h_2.5','name':'2.5如何快速平仓','three':[{'href':'#h_2.5.1','name':'2.5.1持仓列表快速平仓	'},
		                                                      {'href':'#h_2.5.2','name':'2.5.2网格交易界面快速全平	'},
		                                                       {'href':'#h_2.5.3','name':'2.5.3委托列表快速平仓'},
		                                                    ]},   
		          {'href':'#h_2.6','name':'2.6如何进行EXCEL交易策略编辑','three':[]}, 
		          {'href':'#h_2.7','name':'2.7如何防范自成交','three':[]}, 
		         ]
		  },
		  {'one':{'href':'#h_3','name':'3.易宽软件系统设置 '},
		   'two':[{'href':'#h_3.1','name':'3.1个性化设置','three':[{'href':'#h_3.1.1','name':'3.1.1快捷键设置	'},
		                                                      {'href':'#h_3.1.2','name':'3.1.2参数设置'},
		                                                      {'href':'#h_3.1.3','name':'3.1.3消息提示设置'},
		                                                      {'href':'#h_3.1.4','name':'3.1.4表格全局设置'},
		                                                      ]},
		          {'href':'#h_3.2','name':'3.2系统设置','three':[{'href':'#h_3.2.1','name':'3.2.1修改密码'},
		                                                      {'href':'#h_3.2.2','name':'3.2.2修改默认手数'},
		                                                      {'href':'#h_3.2.3','name':'3.2.3风控设置'},
		                                                      {'href':'#h_3.2.4','name':'3.2.4条件设置'},
		                                                       
		                                                     ]},
		          {'href':'#h_3.3','name':'3.3界面风格设置','three':[]},
		          {'href':'#h_3.4','name':'3.4界面显示设置','three':[]}, 
		          {'href':'#h_3.5','name':'3.5字体、颜色设置','three':[]},   
		          
		         ]
		  }
	] 
	var str='<ul class="help_list">';
	for(var i=0,onel=data.length;i<onel;i++){
		str+='<li><a href='+data[i].one.href+'>'+data[i].one.name+'</a><i class="icon icon_down"></i><ul class="help_list_group">'
		for(var j=0,twol=data[i].two.length;j<twol;j++){
			var icon_do=data[i].two[j].three.length>0?"icon_down":" ";
			str+='<li><a href='+data[i].two[j].href+'>'+data[i].two[j].name+'</a><i class="icon '+icon_do+'"></i><ul class="help_list_group2">'
			if( data[i].two[j].three.length>0){
				for(var k=0,threel=data[i].two[j].three.length;k<threel;k++){
					str+='<li><a href='+data[i].two[j].three[k].href+'>'+data[i].two[j].three[k].name+'</a></li>'
				}
			}
			str+='</ul></li>'
		}
		str+='</ul></li>'
	}
	str+='</ul>'
	$('.help_nav').html( str );
	$('.help_list>li').click(function(){
		if(window.event){ 
            event.cancelBubble  = true;
        }else{
            event.stopPropagation();
        }
		 $(this).siblings().children('.help_list_group').hide(300);
	     $(this).siblings().children('.icon').addClass('icon_down').removeClass('icon_up');
		 if($(this).children('.icon').hasClass('icon_down')){
			$(this).children('.icon').removeClass('icon_down').addClass('icon_up');
			$(this).children('.help_list_group').show(300);
		}else{
			$(this).children('.icon').removeClass('icon_up').addClass('icon_down');
			$(this).children('.help_list_group').hide(300);
	    }
		
		
	})
	$('.help_list_group>li').click(function(){
		if(window.event){ 
            event.cancelBubble  = true;
        }else{
            event.stopPropagation();
        }
		if($(this).find('.help_list_group2 li').length>0){
			 if($(this).children('.icon').hasClass('icon_down')){
				$(this).children('.icon').removeClass('icon_down').addClass('icon_up');
				$(this).children('.help_list_group2').show(300);
			}else{
				$(this).children('.icon').removeClass('icon_up').addClass('icon_down');
				$(this).children('.help_list_group2').hide(300);
		    }
		}
		 
       
	})
	$('.help_list_group2>li').click(function(){
		if(window.event){ 
            event.cancelBubble  = true;
        }else{
            event.stopPropagation();
        }
	});
	$('.help_list li').click(function(){
		var loca_hrf=decodeURI ( location.href.split('=')[1].split('#')[0] );  //如果是从搜索界面过来，点击其他hash，消除高亮部分；
		$('.help_list li a').css('color','#8C9596');
		$(this).children('a').css('color','#498EFC');
		if( loca_hrf ){
			 var obj=document.getElementsByClassName("help_main")[0];
			 var str1=obj.innerHTML.replace(/<mark\s+style=\"color\:blue\;\">[\u4e00-\u9fa5]+<\/mark>/gi,loca_hrf);
			 obj.innerHTML=str1;
		}
	});
	//动态判断锚点的偏移
	$('.help_list li').click(function(){
		var id=$(this).children('a').attr('href');
		//$('.helpdocument .help_main '+id+'').css('padding-top','170px')
	});
	//滚动条
	$(".help_list").panel({iWheelStep:32});
	 
 