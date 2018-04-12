		function encode(s){
		  return s.replace(/&/g,"&").replace(/</g,"<").replace(/>/g,">").replace(/([\\\.\*\[\]\(\)\$\^])/g,"\\$1");
		}
		function decode(s){
		  return s.replace(/\\([\\\.\*\[\]\(\)\$\^])/g,"$1").replace(/>/g,">").replace(/</g,"<").replace(/&/g,"&");
		}
		function highlight(s){
		  if (s.length==0){
		    alert('搜索关键词未填写！');
		    return false;
		  }
		  s=encode(s);
		  var obj=document.getElementsByClassName("help_main")[0];
		  var t=obj.innerHTML.replace(/<span\s+class=.?highlight.?>([^<>]*)<\/span>/gi,"$1");
		  obj.innerHTML=t;
		  var cnt=loopSearch(s,obj);
		  t=obj.innerHTML
		  var r=/{searchHL}(({(?!\/searchHL})|[^{])*){\/searchHL}/g
		  t=t.replace(r,"<span class='highlight'>$1</span>");
		  obj.innerHTML=t;
		  
		 /// alert("搜索到关键词"+cnt+"处")   cnt,是搜索关键词次数;
		  
		  var sear_first=$('.highlight').first().closest('a').text(),
		  	  sear_hash,
		  	  sear_detailed,str,seardetailed,
		  	  loca_luj;  ///1.搜索的是a标签内的；2.不是a的话；
		 
		  if( cnt>0 ){
			  if( !sear_first ){
				  sear_first=$('.highlight').first().parent().prevAll('a')[0].innerText;  ///搜索内容
				  sear_detail=$('.highlight').first().parent().text();
				  sear_hash=$('.highlight').first().parent().prevAll('a')[0].getAttribute('name');///要跳转的地方的name;
				  sear_detailed=$('.highlight').first().parent().prevAll('a')[0].nextSibling.nextSibling.nextSibling.nextSibling.innerText;	///之后搜索的详细内容；
				  seardetailed=$('.highlight').first().parent().prevAll('a')[0].nextSibling.nextSibling.innerText;
			  }else{
				  sear_first=$('.highlight').first().closest('a').text();  ///搜索内容
				  sear_detail='';
				  sear_hash=$('.highlight').first().closest('a').attr('name');
				  sear_detailed=$('.highlight').first().closest('a').next().text();
				  seardetailed=$('.highlight').first().closest('a').next().next().text();
			  }
			  
			  sear_first=sear_first.replace(/[\d|\.]/g,'');		///替换搜索内容中的 数字，小数点为空；
			  loca_luj=location.origin;///当前路径;
			  
			  str="<a href="+loca_luj+"/qtadmin/page/onlinehelp/helpdocument.jsp?txt="+encodeURI(s)+'#'+sear_hash+"><b>"+sear_first+"</b></a><p>"+sear_detail+seardetailed+sear_detailed+"</p>";
			  str+="<a href="+loca_luj+"/qtadmin/page/onlinehelp/helpdocument.jsp?txt="+encodeURI(s)+'#'+sear_hash+" class='seeloo'>点击查看其他"+cnt+"条结果</a>";
			  $('.sear_wendang').html( str );  //赋值；
		  }
		  return cnt;
		}
		function loopSearch(s,obj){
		  var cnt=0;
		  if (obj.nodeType==3){
		    cnt=replace(s,obj);
		    return cnt;
		  }
		  for (var i=0,c;c=obj.childNodes[i];i++){
		    if (!c.className||c.className!="highlight")
		      cnt+=loopSearch(s,c);
		  }
		  return cnt;
		}
		function replace(s,dest){
		  var r=new RegExp(s,"g");
		  var tm=null;
		  var t=dest.nodeValue;
		  var cnt=0;
		  if (tm=t.match(r)){
		    cnt=tm.length;
		    t=t.replace(r,"{searchHL}"+decode(s)+"{/searchHL}")
		    dest.nodeValue=t;
		  }
		  return cnt;
		}
