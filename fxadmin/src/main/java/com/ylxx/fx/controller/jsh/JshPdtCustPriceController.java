package com.ylxx.fx.controller.jsh;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.service.jsh.IJshPdtCustPriceService;
import com.ylxx.fx.service.po.jsh.JshPdtCustPriceVo;


//import com.ylxx.fx.controller.WebSocketMessageUtil;
//import com.ylxx.fx.service.po.jsh.WebSocketMessage;
//import com.ylxx.fx.utils.LoginUsers;

@Controller
public class JshPdtCustPriceController { 
	private static final Logger log = LoggerFactory.getLogger(JshPdtCustPriceController.class);
	@Resource(name="jshPdtCustPriceService")
	private IJshPdtCustPriceService jshPdtCustPriceService;
	 /**
	  * 查询客户价公告板
	  * @param exnm trfg pageNo pageSize 
	  * @return  查询成功00 查询无结果01 查询异常02
	  */ 
	@RequestMapping(value="/selJshPdtCustPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selJshPdtCustPrice(@RequestBody JshPdtCustPriceVo pdtCustPrice) throws Exception {
		return jshPdtCustPriceService.selJshPdtCustPrice(pdtCustPrice.getExnm() ,pdtCustPrice.getTrfg(),pdtCustPrice.getPageNo(),pdtCustPrice.getPageSize());
	}
	/**
	 * 开启新的定时器
	 * @return
	 */
//	@RequestMapping(value="/selJshPdtCustPriceStart.do", produces="text/html;charset=UTF-8")
//	@ResponseBody
//	public String aaBB(HttpServletRequest req) {
//		log.info(">>>通知启用定时器");
//		Timer timer = LoginUsers.getLoginUser().getTimes((String)req.getSession().getAttribute("id"));
//		if(timer == null) {
//			timer = new Timer();
//			timerCust(timer);
//		}
//		return "";
//	}
//	@RequestMapping(value="/selJshPdtCustPriceClose.do", produces="text/html;charset=UTF-8")
//	@ResponseBody
//	public String aaCC(HttpServletRequest req) {
//		log.info(">>>通知关闭定时器");
//		String userKey = (String)req.getSession().getAttribute("id");
//		Timer time = LoginUsers.getLoginUser().getTimes(userKey);
//		time.cancel();
//		LoginUsers.getLoginUser().deleteTimes(userKey);
//		return "";
//	}
//	public void timerCust(Timer timer) {
//		
//        timer.schedule(new TimerTask() {
//            public void run() {
//        		String pagelist = jshPdtCustPriceService.selJshPdtCustPrice("", "", 1, 10);
//            	WebSocketMessage msg = new WebSocketMessage();
//    	        msg.setDistination("/topic/lancy/testWebSocket/new");	// 每1秒推送一个条消息：广播地址
//    	        msg.setData(pagelist);
//    	        WebSocketMessageUtil.addMessage(msg);
//            }
//        }, 0, 5000);// 设定指定的时间time,此处为1000毫秒
//    }
	 /**
	  * 新增货币对
	  * @param String userKey exnm trfg
	  * @param JshPdtCustPriceVo pdtCustPrice
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/insJshPdtCustPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insJshPdtCustPrice(@RequestBody JshPdtCustPriceVo pdtCustPrice) {
		return jshPdtCustPriceService.insJshPdtCustPrice(pdtCustPrice);
	}
	 /**
	  * 更新货币对
	  * @param String userKey exnm trfg 
	  * @param JshPdtCustPriceVo pdtCustPrice
	  * @return 更新失败01 更新成功00
	  */
	@RequestMapping(value="/updJshPdtCustPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updJshPdtCustPrice(@RequestBody JshPdtCustPriceVo pdtCustPrice) {
		return jshPdtCustPriceService.updJshPdtCustPrice(pdtCustPrice);
	}
	 /**
	  * 删除货币对
	  * @param String userKey exnm pageNo pageSize
	  * @param JshPdtCustPriceVo pdtCustPrice
	  * @return 删除失败01 删除成功00
	  */
	@RequestMapping(value="/delJshPdtCustPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String delJshPdtCustPrice(@RequestBody JshPdtCustPriceVo pdtCustPrice) {
		return jshPdtCustPriceService.delJshPdtCustPrice(pdtCustPrice);
	}
	 /**
	  * 是否存在此货币对
	  * @param String exnm
	  * @return 该货币对已存在00  该货币对可以使用 01  查重失败02
	  */
	@RequestMapping(value="/selCustExnmExist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selCustExnmExist(@RequestBody JshPdtCustPriceVo pdtCustPrice) {
		return jshPdtCustPriceService.selCustExnmExist(pdtCustPrice);
	}
	 /**
	  * 查詢已有货币对
	  * @return HashMap
	  */
	@RequestMapping(value="/selJshCustExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selJshCustExnm() {
		return jshPdtCustPriceService.selJshCustExnm();
	}
}
