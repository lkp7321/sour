package com.ylxx.qt.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylxx.qt.service.AccountService;
import com.ylxx.qt.service.po.AccountBean;
import com.ylxx.qt.service.po.UserAccountBean;
import com.ylxx.qt.utils.AESUtil;

@Controller
@RequestMapping(value="/account")
public class AccountController {
	
	@Resource
	private AccountService accountService;
	
	//查询所有的账户信息
	@RequestMapping(value = "/getAccount.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String  showAccount(HttpServletRequest request) throws Exception{
		 String userid=(String) request.getSession().getAttribute("userid");
		 List<AccountBean> accountList=	accountService.queryAccountByUserID(userid);
		ObjectMapper mapper=new ObjectMapper();
		String json=	mapper.writeValueAsString(accountList);
		return "{\"code\":0,\"msg\":\"\",\"count\":"+accountList.size()+",\"data\":"+json+"}";
	}
	
	//根据账户ID查询一条账户信息记录
	@RequestMapping(value = "/getOneAccount.do",produces ="plain/text; charset=UTF-8")
	public @ResponseBody String   showOneAccount(HttpServletRequest request) throws Exception{
		 String userid=(String) request.getSession().getAttribute("id");
		 List<AccountBean> accountList=accountService.queryOneAccountByAccountID(userid);
		 ObjectMapper mapper = new ObjectMapper();
		 String json = mapper.writeValueAsString(accountList);
		 
		 return "{\"code\":0,\"msg\":\"\",\"count\":"+accountList.size()+",\"data\":"+json+"}";
	
	}	 
	
	
	
     //账号新增
   @RequestMapping(value = "/addAccount.do",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public  @ResponseBody String addAccount(HttpServletRequest request,  HttpServletResponse response)throws Exception{
	   //创建账户表的实体类
	   String accountId = request.getParameter("userid");  //获得账户ID
	   AccountBean newAccount = new AccountBean();
	   newAccount.setUserid(request.getParameter("userid"));
	   //密码的明文
	   String UUID= (String) request.getSession().getAttribute("userid");//获取用户唯一标识userid,该标识是由UUID生成
	   //期货公司ID
	   newAccount.setCtpbrokerid(request.getParameter("ctpbrokerid"));
	   //目前暂时给该字段赋值为“7”
	   newAccount.setInorout("7");
	   newAccount.setMarginnumber(request.getParameter("marginnumber"));
	   //加密后的保证金监控中心的密码
	   String marginpwd = AESUtil.encrypt(request.getParameter("marginpassword"), UUID);
	   newAccount.setMarginpassword(marginpwd);
	   //是否参与排名
	   newAccount.setIsrank(request.getParameter("isrank"));
	   
	   //创建session
	   HttpSession session = request.getSession();
	   String Username = (String) session.getAttribute("name");  //登录用户的用户名
	   String Userid = (String) session.getAttribute("userid");
	   
	   //创建账户关系表的实体类
	   UserAccountBean newUserAccount = new UserAccountBean();
	   //设置账户关系表的UUID
	   newUserAccount.setUserid(Userid);
	   //设置账户关系表的投资者ID
	   newUserAccount.setInvestor(request.getParameter("userid"));
	   //设置账户关系表的用户名
	   newUserAccount.setUsername(Username);
	   
	 //先查询已拥有账号的数量：如果小于10，可以继续新增.否则,无法正常新增
		String userid=(String) request.getSession().getAttribute("userid");
		List<AccountBean> newAccountList = accountService.queryAccountByUserID(userid);
		// 封装操作结果，包括success是否成功和msg消息提示
		JSONObject result = new JSONObject();   //新增
		if (newAccountList.size() <= 9){
			boolean isHaving =false;
			boolean isOtherHaving = false;//是否被别人添加的标识
			List<AccountBean> accountList = accountService.queryOneAccountByAccountID(accountId);//用来存放查询新增时传来的账号是否已经在【账户表】存在
			//说明账户表已有该数据,说明该账号已经添加过了。
			if (accountList.size() == 1){  
			
				List<UserAccountBean> userAccList1 = accountService.queryUserAccountByUserName(Username);//存放用户账户关系的表，判断该用户是否已经与此账号绑定
				//如果账户关系表中有，再判断该账号是与其他用户关联，还是与本用户关联
				if ( userAccList1.size() > 0){
					for (int v = 0;v < userAccList1.size();v++ ){
						if (accountId.equals(userAccList1.get(v).getInvestor()) ){
							isHaving=true;
							break;
						}
					}
					if(isHaving){
						result.put("success", false);
						result.put("msg", "您已添加该账户，请不要重复添加");
						return result.toJSONString();
					}
					
				
				List<UserAccountBean> userAccList2 = accountService.queryUserAccountByInvestor(accountId);//存放根据投资者账号查出的关系
				if ( userAccList2.size() >= 1){
					for (int p=0;p < userAccList2.size();p++){
						if ( Username.equals(userAccList2.get(p).getUsername())  ){
						}else {
							isOtherHaving = true;
							break;
						}
					}
					if(isOtherHaving){
						result.put("success", false);
						result.put("msg", "该账号已被其他用户添加，请确认您的输入是否有误！");
						return result.toJSONString();
					}
						
				}
				
			else{
						   try {
							   	
								int linkResultInfo1 = accountService.addUserAccount(newUserAccount);//用户账户关系表中记录新增
								if (linkResultInfo1 == 1 ) {
									result.put("success", true);
									result.put("msg", "账户添加成功！");
									return result.toJSONString();
								} else {
									result.put("success", false);
									result.put("msg", "账户添加失败！");
									return result.toJSONString();
								}
						   		}catch (Exception e) {
									e.printStackTrace();
									result.put("success", false);
									result.put("msg", "账户添加失败！");
									return result.toJSONString();
								}
						
								}
				}
				}else {
					try {
					int resultInfo2 = accountService.addAccount(newAccount);   //账户表中记录新增
					int linkResultInfo2 = accountService.addUserAccount(newUserAccount);//用户账户关系表中记录新增
					
					if (linkResultInfo2 == 1 && resultInfo2 == 1) {
						result.put("success", true);
						result.put("msg", "添加成功！");
						return result.toJSONString();
					} else {
						result.put("success", false);
						result.put("msg", "添加失败！");
						return result.toJSONString();
					}
					} catch (Exception e) {
					e.printStackTrace();
					result.put("success", false);
					result.put("msg", "添加失败！");
					return result.toJSONString();
				}
			}
			
			
		}else{
			result.put("success", false);
			result.put("msg", "您最多只能添加10个账户！");
			return result.toJSONString();
		}
		

		result.put("success",false);
		result.put("msg", "发生未知错误，请联系系统管理员！");
		return result.toJSONString();
	}
   
   
   // 更新账户
   @RequestMapping(value = "/updateAccount.do",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public @ResponseBody String updateAccount(HttpServletRequest request,  HttpServletResponse response)throws Exception{
	   AccountBean newAccount = new AccountBean();
	   //账户ID
	   String Userid = request.getParameter("userid"); 
	   //密钥
	   String UUID= (String) request.getSession().getAttribute("userid");//获取用户唯一标识userid,该标识是由UUID生成
	   //创建一个list,存放数据库查询得到的数据记录
	    List<AccountBean> accountList = accountService.queryOneAccountByAccountID(Userid);
	    newAccount.setUserid(request.getParameter("userid"));
	    
	    //页面传来的交易密码
	    /*String oldUserpwd = request.getParameter("userpassword");
	   if (accountList.get(0).getUserpassword().equals(oldUserpwd) ){
		 newAccount.setUserpassword(oldUserpwd); //将传来的值直接放进去   
	   }else {
		   String userpwd = AESUtil.encrypt(request.getParameter("userpassword"), UUID);
		 newAccount.setUserpassword(userpwd); 
	   }  */
	   
	   newAccount.setCtpbrokerid(request.getParameter("ctpbrokerid"));
	   newAccount.setInorout("7");
	   //页面传来的保证金监控中心的账号
	   newAccount.setMarginnumber(request.getParameter("marginnumber"));
	   //页面传来的保证金中心的密码
	   String oldMarginpwd = request.getParameter("marginpassword");
	   
	   if (accountList.get(0).getMarginpassword() == null ){
		   String marginpwd = "";
	   		newAccount.setMarginpassword(marginpwd);	
	   }else {
		   if (accountList.get(0).getMarginpassword().equals(oldMarginpwd)) {
			   newAccount.setMarginpassword(oldMarginpwd);
		   }else{
			   String marginpwd = AESUtil.encrypt(request.getParameter("marginpassword"), UUID);
			   newAccount.setMarginpassword(marginpwd);   
		   }
	   }
	  
	   //是否参与排名
	   newAccount.setIsrank(request.getParameter("isrank"));
	   
	   // 封装操作结果，包括success是否成功和msg消息提示
			JSONObject result = new JSONObject();
			try {
				int resultInfo = accountService.updateAccount(newAccount);
				if (resultInfo == 1) {
					result.put("success",true);
					result.put("msg","修改成功");
					
				
				} else {
					result.put("success", false);
					result.put("msg","修改失败");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("msg", "修改失败");
				
			}
			return   result.toJSONString();
				
	}
   
   	//	删除账户
   @RequestMapping(value = "/deleteAccount.do",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
   	public @ResponseBody String deleteAccount(HttpServletRequest request,HttpServletResponse response,String userid) throws Exception{
	String investor = request.getParameter("userid"); 
	try {
		accountService.deleteUserAccount(investor);//删除账户，只是删除了用户账户关系表中的关系记录
		return "删除成功";
	} catch (Exception e) {
		return "删除失败";
		
	}
	
   }
}
