// by zhangxixnu 2010-06-21  welcome to visit my personal website http://www.zhangxinxu.com/
// textSearch.js v1.0 鏂囧瓧锛屽叧閿瓧鐨勯〉闈㈢函瀹㈡埛绔悳绱�
// 2010-06-23 淇澶氬瓧姣嶆绱㈡爣绛剧牬纰庣殑闂
// 2010-06-29 淇椤甸潰娉ㄩ噴鏄剧ず鐨勯棶棰�
// 2013-05-07 淇缁х画鎼滅礌鍏抽敭瀛楀寘鍚箣鍓嶆悳绱㈠叧閿瓧娌℃湁缁撴灉鐨勯棶棰�
// 涓嶈浣曠鎯呭喌锛屽姟蹇呬繚鐣欎綔鑰呯讲鍚嶃€� 


(function($){
	$.fn.textSearch = function(str,options){
		var defaults = {
			divFlag: true,
			divStr: " ",
			markClass: "",
			markColor: "red",
			nullReport: true,
			callback: function(){
				return false;	
			}
		};
		var sets = $.extend({}, defaults, options || {}), clStr;
		if(sets.markClass){
			clStr = "class='"+sets.markClass+"'";	
		}else{
			clStr = "style='color:"+sets.markColor+";'";
		}
		
		//瀵瑰墠涓€娆￠珮浜鐞嗙殑鏂囧瓧杩樺師		
		$("span[rel='mark']").each(function() {
			var text = document.createTextNode($(this).text());	
			$(this).replaceWith($(text));
		});
		
		
		//瀛楃涓叉鍒欒〃杈惧紡鍏抽敭瀛楄浆鍖�
		$.regTrim = function(s){
			var imp = /[\^\.\\\|\(\)\*\+\-\$\[\]\?]/g;
			var imp_c = {};
			imp_c["^"] = "\\^";
			imp_c["."] = "\\.";
			imp_c["\\"] = "\\\\";
			imp_c["|"] = "\\|";
			imp_c["("] = "\\(";
			imp_c[")"] = "\\)";
			imp_c["*"] = "\\*";
			imp_c["+"] = "\\+";
			imp_c["-"] = "\\-";
			imp_c["$"] = "\$";
			imp_c["["] = "\\[";
			imp_c["]"] = "\\]";
			imp_c["?"] = "\\?";
			s = s.replace(imp,function(o){
				return imp_c[o];					   
			});	
			return s;
		};
		$(this).each(function(){
			var t = $(this);
			str = $.trim(str);
			if(str === ""){
				///alert("鍏抽敭瀛椾负绌�");	
				return false;
			}else{
				//灏嗗叧閿瓧push鍒版暟缁勪箣涓�
				var arr = [];
				if(sets.divFlag){
					arr = str.split(sets.divStr);	
				}else{
					arr.push(str);	
				}
			}
			var v_html = t.html();
			//鍒犻櫎娉ㄩ噴
			v_html = v_html.replace(/<!--(?:.*)\-->/g,"");
			
			//灏咹TML浠ｇ爜鏀涓篐TML鐗囨鍜屾枃瀛楃墖娈碉紝鍏朵腑鏂囧瓧鐗囨鐢ㄤ簬姝ｅ垯鏇挎崲澶勭悊锛岃€孒TML鐗囨缃箣涓嶇悊
			var tags = /[^<>]+|<(\/?)([A-Za-z]+)([^<>]*)>/g;
			var a = v_html.match(tags), test = 0;
			$.each(a, function(i, c){
				if(!/<(?:.|\s)*?>/.test(c)){//闈炴爣绛�
					//寮€濮嬫墽琛屾浛鎹�
					$.each(arr,function(index, con){
						if(con === ""){return;}
						var reg = new RegExp($.regTrim(con), "g");
						if(reg.test(c)){
							//姝ｅ垯鏇挎崲
							c = c.replace(reg,"鈾�"+con+"鈾€");
							test = 1;
						}
					});
					c = c.replace(/鈾�/g,"<mark "+clStr+">").replace(/鈾€/g,"</mark>");
					a[i] = c;
				}
			});
			//灏嗘敮绂绘暟缁勯噸鏂扮粍鎴愬瓧绗︿覆
			var new_html = a.join("");
			
			$(this).html(new_html);
			
			if(test === 0 && sets.nullReport){
				///alert("娌℃湁鎼滅储缁撴灉");	
				return false;
			}
			
			//鎵ц鍥炶皟鍑芥暟
			sets.callback();
		});
	};
})(jQuery);