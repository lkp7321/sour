$(function(){
    // 设置结束时间的最大可取值为today
    $('#d2').unbind("click");
    $('#d2').bind("click",function(){
        WdatePicker({
            dateFmt:'yyyyMMdd ',
            isShowClear:false,
            isShowOk:false,
            qsEnabled:false,
            minDate:'#F{$dp.$D(\'d1\')}',
            maxDate:'#F{\'%y-%M-%d\'}',
            onpicking:function(dp){
            	$(this).prev().text(' ');
            }
        });
    });
});
	// 开始时间值改变时,改变结束时间值得范围
	function setEndTimeDurationWhileStartTimeChange(){
	    var start = $('#d1').val(); // 格式：2017-05-01 
	   		
	    	
	    // 如果选中了开始时间
	    if(start != "" && start != null){
	        // endMaxDate = 开始时间+29天
	       // start = start.replace(/-/g,"/");
	        var endMaxDate = new Date(start.slice(0,4)+'/'+start.slice(4,6)+'/'+start.slice(6,8) );
	        endMaxDate.setDate(endMaxDate.getDate()+30); 
	 		
	       
	    
	        // today = 今天
	        var today = new Date();
	        today.setHours(0);    
	        today.setMinutes(0);    
	        today.setSeconds(0);    
	        today.setMilliseconds(0);
	       
	        // 如果endMaxDate>today
	        if(endMaxDate.getTime()>today.getTime()){
	            // 设置结束时间的最大可取值为today
	            $('#d2').unbind("click");
	            $('#d2').bind("click",function(){
	                WdatePicker({
	                    readOnly:true,
	                    dateFmt:'yyyyMMdd ',
	                    minDate:'#F{$dp.$D(\'d1\')}',
	                    maxDate:'#F{\'%y-%M-%d\'}',
			            onpicking:function(dp){
			            	$(this).prev().text(' ');
			            }
	                });
	            });
	        }else{
	            // 设置结束时间的最大可取值为endMaxDate
	            $('#d2').unbind("click");
	            $('#d2').bind("click",function(){
	                WdatePicker({
	                    readOnly:true,
	                    dateFmt:'yyyyMMdd ',
	                    minDate:'#F{$dp.$D(\'d1\')}',
	                    maxDate:'#F{$dp.$D(\'d1\',{d:30})}',
			            onpicking:function(dp){
			            	$(this).prev().text(' ');
			            }
	                });
	            });
	        }
	    }
	    // 如果清空了开始时间,重设结束时间的范围,到今天为止
	    else{
	        // 设置结束时间的最大可取值为today
	        $('#d2').unbind("click");
	        $('#d2').bind("click",function(){
	            WdatePicker({
	                readOnly:true,
	                dateFmt:'yyyyMMdd ',
	                minDate:'#F{$dp.$D(\'d1\')}',
	                maxDate:'#F{\'%y-%M-%d\'}',
		            onpicking:function(dp){
		            	$(this).prev().text(' ');
		            }
	            });
	        });
	    }
	}

