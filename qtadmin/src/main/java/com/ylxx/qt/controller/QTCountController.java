package com.ylxx.qt.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.qt.service.IQTCountService;
import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;
import com.ylxx.qt.service.po.UserInfoBean;
import com.ylxx.qt.utils.AESUtil;
import com.ylxx.qt.utils.GetNowDate;

@Controller
@RequestMapping("/root")
public class QTCountController {
	
	@Resource
	private IQTCountService qtcs;

	
	
	// 每日权益
	@RequestMapping(value="/hi")
	public @ResponseBody String getEquity(HttpSession session) throws Exception{
		
		List<TradingAccountFiledBean> list = null;
		
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
		//	list = qtcs.getAll(id);
		}else{
		//	list = qtcs.getAll(ac);
		}
		
		Double value = 0.0;
		String date = null;
		
		String s = "[";
		
		Double amount = 0.0;
		
		for(int i=0;i<list.size();i++){
			value = list.get(i).getPre_Balance()+list.get(i).getPosition_profit();
			date = list.get(i).getTrading_day();
			amount = list.get(i).getCapital();
			if(i<list.size()-1){
				s += "["+"'"+date+"'"+","+value+"],";
			}else{
				s += "["+"'"+date+"'"+","+value+"]]";
			}
		}
		if(s.equals("[")&&amount==0.0){
			return "";
		}else{
			return "["+s+","+"['"+amount+"']"+"]";
		}
	} 
	
	// 每月盈亏
	@RequestMapping(value="/mp")
	public @ResponseBody String getMonthProfit(HttpSession session) throws Exception {
		
		List<TradeFieldBean> list = null;
		
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
			list = qtcs.getCloseProfit(id);
		}else{
			list = qtcs.getCloseProfit(ac);
		}
		
		Double value = 0.0;
		String date = null;
		
		String s = "[";
		
		for(int i=0;i<list.size();i++){
			date = list.get(i).getTd();
			value = list.get(i).getSum();
			
			if(i<list.size()-1){
				s += "["+"'"+date+"'"+","+value+"],";
			}else{
				s += "["+"'"+date+"'"+","+value+"]]";
			}
		}
		if(s.equals("[")){
			return "";
		}else{
			return s; 
		}
	}
	
	// 多空盈亏
	@RequestMapping(value="/dk",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getDKProfit(HttpSession session) throws Exception{
		DecimalFormat df = new DecimalFormat("0.00");
		List<TradeFieldBean> list = null;
		
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
			list = qtcs.queryAll(id);
		}else{
			list = qtcs.queryAll(ac);
		}
		
		Double d1 = 0.0;                          // 多单盈利
		Double d2 = 0.0;                          // 多单亏损
		Double k1 = 0.0;                          // 空单盈利
		Double k2 = 0.0;                          // 空单亏损
		
		String s = "[";
		
		String name1 = "多单盈利";
		String name2 = "多单亏损";
		String name3 = "空单盈利";
		String name4 = "空单亏损";
		
		
		for(int i=0;i<list.size();i++){     //判断是否为空单盈利
			if(!list.get(i).getOffSet_flag().equals("0")){
				if(list.get(i).getDirection().equals("0")){
					if(list.get(i).getProfit()>0){
						k1 += list.get(i).getProfit();
					}else if(list.get(i).getProfit()<0){//盈利小于0，空单亏损
						k2 += list.get(i).getProfit();
					}
				}else if(list.get(i).getDirection().equals("1")){
					if(list.get(i).getProfit()>0){  //多单盈利
						d1 += list.get(i).getProfit();
					}else if(list.get(i).getProfit()<0){  //多单亏损
						d2 += list.get(i).getProfit();
					}
				}
			}
			
		}
		if(d1==0&&d2==0&&k1==0&&k2==0){
			s = "";
		}else{
			s = "[[{"+"\"value\":"+df.format(Math.abs(d1))+","+"\"name\":\""+name1+"\"},"+"{\"value\":"+df.format(Math.abs(k1))+","+"\"name\":\""+name3+"\"},"+"{\"value\":"+df.format(Math.abs(d1))+","+"\"name\":\""+name1+"\"},"+"{\"value\":"+df.format(Math.abs(d2))+","+"\"name\":\""+name2+"\"}],"
				+"[{\"value\":"+df.format(Math.abs(d2))+","+"\"name\":\""+name2+"\"},"+"{\"value\":"+df.format(Math.abs(k2))+","+"\"name\":\""+name4+"\"},"+"{\"value\":"+df.format(Math.abs(k1))+","+"\"name\":\""+name3+"\"},"+"{\"value\":"+df.format(Math.abs(k2))+","+"\"name\":\""+name4+"\"}]]";
			
			//s = "[["+"{value:"+df.format(Math.abs(d1))+","+"name:"+"'"+name1+"'"+"},"+"{value:"+df.format(Math.abs(k1))+","+"name:"+"'"+name3+"'"+"},"+"{value:"+df.format(Math.abs(d1))+","+"name:"+"'"+name1+"'"+"},"+"{value:"+df.format(Math.abs(d2))+","+"name:"+"'"+name2+"'"+"}],"
			//		+"[{value:"+df.format(Math.abs(d2))+","+"name:"+"'"+name2+"'"+"},"+"{value:"+df.format(Math.abs(k2))+","+"name:"+"'"+name4+"'"+"},"+"{value:"+df.format(Math.abs(k1))+","+"name:"+"'"+name3+"'"+"},"+"{value:"+df.format(Math.abs(k2))+","+"name:"+"'"+name4+"'"+"}]]";
		}
		return s;
	}
	
	// 每天持仓
	@RequestMapping(value="/po")
	public @ResponseBody String getPosition(HttpSession session) throws Exception{
		
		List<PositionDetailBean> lpbp = null;
		
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
/*		if(id!=null){
			lpbp = qtcs.getPosition(id);
		}else{
			lpbp = qtcs.getPosition(ac);
		}*/
		
		String date = null;
		Double value1 = 0.0;
		Double value2 = 0.0;
		String date0 = null;
		Double value10 = 0.0;
		Double value20 = 0.0;
		String s = "[";
		
		for(int i=0;i<lpbp.size()-1;i++){
			if(lpbp.get(i).getTrading_day().equals(lpbp.get(i+1).getTrading_day())){
				value1 = lpbp.get(i).getSum_margin();
				value2 = lpbp.get(i+1).getSum_margin();
				date = lpbp.get(i).getTrading_day();
				i = i+1;
			}else {
				if(lpbp.get(i).getDirection().equals("0")){
					value1 = lpbp.get(i).getSum_margin();
					value2 = 0.0;
					date = lpbp.get(i).getTrading_day();
				}else if(lpbp.get(i).getDirection().equals("1")){
					value2 = lpbp.get(i).getSum_margin();
					value1 = 0.0;
					date = lpbp.get(i).getTrading_day();
				}
			}
			
			if(lpbp.get(lpbp.size()-2).getTrading_day().equals(lpbp.get(lpbp.size()-1).getTrading_day())){
				if(i<lpbp.size()-2){
					s += "["+"'"+date+"'"+","+value1+","+value2+"],";
				}else{
					s += "["+"'"+date+"'"+","+value1+","+value2+"]]";
				}
			}else{
				s += "["+"'"+date+"'"+","+value1+","+value2+"],";
			}
			
		}
		if(!lpbp.get(lpbp.size()-2).getTrading_day().equals(lpbp.get(lpbp.size()-1).getTrading_day())){
			if(lpbp.get(lpbp.size()-1).getDirection().equals("0")){
				value10 = lpbp.get(lpbp.size()-1).getSum_margin();
				value20 = 0.0;
				date0 = lpbp.get(lpbp.size()-1).getTrading_day();
			}else if(lpbp.get(lpbp.size()-1).getDirection().equals("1")){
				value20 = lpbp.get(lpbp.size()-1).getSum_margin();
				value10 = 0.0;
				date0 = lpbp.get(lpbp.size()-1).getTrading_day();
			}
			s += "["+"'"+date0+"'"+","+value10+","+value20+"]]";
		}
		if(s.equals("[")){
			return "";
		}else{
			return s;
		}
	} 
	

	
	// 持仓表统计信息
	@RequestMapping(value="/pmsg")
	public @ResponseBody String getPostionMessage(int page,int limit,String beginTime,String endTime,HttpSession session) throws Exception{
		String bT=null;
		String eT=null;
		Integer counts=0;
		String day="7";
		if(beginTime!=null&&beginTime!="") {
			 bT =beginTime.replaceAll("-","");
			 day=null;
		}else {
			bT="";
		}
		if(endTime!=null&&endTime!="") {			
			 eT =endTime.replaceAll("-","");
			 day=null;
		}else {
			eT=GetNowDate.getNowDate();
		}
		DecimalFormat df = new DecimalFormat("0.00");		
		List<PositionDetailBean> list = null;
		
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
			list = qtcs.getPositionMessage(day,id,bT,eT,page,limit);
			counts=qtcs.getPositionMessageCounts(day,id, bT, eT);
		}else{
			list = qtcs.getPositionMessage(day,ac,bT,eT,page,limit);
			counts=qtcs.getPositionMessageCounts(day,ac, bT, eT);
		}
		
		String date = null;
		String cid = null;
		String insid = null;
		String dir = null;
		Double price = 0.0;
		int pos = 0;
		int ypos = 0;
		int tpos = 0;
		int nz = 0;
		Double pct = 0.0;
		Double oct = 0.0;
		Double act = 0.0;
		int vm = 0;
		String hde = null;
		int cv = 0;
		Double cp = 0.0;
		Double op = 0.0;
		Double pp = 0.0;
		Double cs = 0.0;
		Double mg = 0.0;
		Timestamp ut = null;
		Timestamp it = null;
		Double cpt = 0.0;
		Double lsp = 0.0;
		
		String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
		
		for(int i=0;i<list.size();i++){
			date = list.get(i).getTrade_day();   //交易起日
			cid = list.get(i).getCtp_id();      
			insid = list.get(i).getInstrument_id();  //
			dir = list.get(i).getDirection();
			price = list.get(i).getPrice();   
			pos = list.get(i).getPosition();
			ypos = list.get(i).getYd_position();
			tpos = list.get(i).getTd_position();
			nz = list.get(i).getNo_frozen();
			pct = list.get(i).getPosition_cost();
			oct = list.get(i).getOpen_cost();
			act = list.get(i).getAvg_cost();
			vm = list.get(i).getVolumemultiple();
			hde = list.get(i).getHedge();
			cv = list.get(i).getClose_volume();
			cp = list.get(i).getClose_profit();
			op = list.get(i).getOpen_profit();
			pp = list.get(i).getPosition_profit();
			cs = list.get(i).getCommission();
			mg = list.get(i).getMargin();
			ut = list.get(i).getUpdatetime();
			it = list.get(i).getInserttime();
			cpt = list.get(i).getCloseprofit_bytrade();
			lsp = list.get(i).getLast_settlementprice();
			
			if(i<list.size()-1){
				s += "{\"date\":\""+date+"\",\"cid\":\""+cid+"\",\"insid\":\""+insid+"\",\"dir\":\""+dir+"\",\"price\":"+df.format(price)+",\"pos\":"+pos+",\"ypos\":"+ypos+",\"tpos\":"+tpos+",\"nz\":"+nz+",\"pct\":"+df.format(pct)+",\"oct\":"+df.format(oct)+
						",\"act\":"+df.format(act)+",\"vm\":"+vm+",\"hde\":\""+hde+"\",\"cv\":"+cv+",\"cp\":"+df.format(cp)+",\"op\":"+df.format(op)+",\"pp\":"+df.format(pp)+",\"cs\":"+df.format(cs)+",\"mg\":"+df.format(mg)+",\"ut\":\""+ut+"\",\"it\":\""+it+"\",\"cpt\":"+df.format(cpt)+",\"lsp\":"+df.format(lsp)+"},";
			}else{
				s += "{\"date\":\""+date+"\",\"cid\":\""+cid+"\",\"insid\":\""+insid+"\",\"dir\":\""+dir+"\",\"price\":"+df.format(price)+",\"pos\":"+pos+",\"ypos\":"+ypos+",\"tpos\":"+tpos+",\"nz\":"+nz+",\"pct\":"+df.format(pct)+",\"oct\":"+df.format(oct)+
						",\"act\":"+df.format(act)+",\"vm\":"+vm+",\"hde\":\""+hde+"\",\"cv\":"+cv+",\"cp\":"+df.format(cp)+",\"op\":"+df.format(op)+",\"pp\":"+df.format(pp)+",\"cs\":"+df.format(cs)+",\"mg\":"+df.format(mg)+",\"ut\":\""+ut+"\",\"it\":\""+it+"\",\"cpt\":"+df.format(cpt)+",\"lsp\":"+df.format(lsp)+"}]}";
			}
		}
		return s;
	}

	
	// 获取用户的账号
	@RequestMapping(value="/account.do")
	public String getaccount(String select,HttpSession session){
		
		session.setAttribute("uid", select);
		return "user";
	}
	
	// 查询用户
	@RequestMapping(value="/select.do")
	public String SelectUserInfo(String select,Model model){
		
		List<UserInfoBean> list = qtcs.SelectUser(select);
		model.addAttribute("list", list);
		
		return "main";
	}
	
	// 获取用户
	@RequestMapping(value="/getuser.do")
	public String getUserInfo(String select,Model model){
		
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		
		return "main";
	}
	
	// 添加用户
	@RequestMapping(value="/add.do")
	public String AddUserInfo(String uname,String upwd,String pnumber,String remark,Model model) throws Exception{
		String uid = AESUtil.getGUID();
		String pwd = AESUtil.encrypt(upwd, uid);
		if(uname!=null&&pwd!=null&&pnumber!=null&&remark!=null&&!uname.equals("")&&!pwd.equals("")&&!pnumber.equals("")&&!remark.equals("")){
			UserInfoBean user = new UserInfoBean(uid, uname, pwd, pnumber, remark);
			qtcs.addUser(user);
			/*List<UserInfoBean> list = qtcs.getUserList();
			model.addAttribute("list", list);
			return "main";*/
		}
		return "add";
	}
	
	// 获取选择框的值
	@RequestMapping(value="/getChecked.do")
	public String getChecked(HttpSession session,String[] v){
		List<String> ls = new ArrayList<String>();
		if(v!=null&&v.length>0){
			for(int i=0;i<v.length;i++){
				ls.add(v[i]);
			}
		}
		session.setAttribute("ls", ls);
		return "main";
	}
	
	// 删除用户
	@RequestMapping(value="/delete.do")
	public String DeleteUser(HttpSession session,Model model){
		List<String> lc = (List<String>) session.getAttribute("ls");
		if(lc!=null&&lc.size()>0){
			qtcs.deleteUser(lc);
			lc.clear();
		}else{
			List<UserInfoBean> list = qtcs.getUserList();
			model.addAttribute("list", list);
			return "main";
		}
		session.removeAttribute("ls");
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		return "main";
	}
	
	// 查询修改
	@RequestMapping(value="/su.do")
	public String SuUser(String s0,String s1,String s2,String s3,String s4,Model model,HttpSession session) throws Exception{
		String pwd = AESUtil.decrypt(s2, s0);
		session.setAttribute("suid", s0);
		model.addAttribute("name", s1);
		model.addAttribute("pwd", pwd);
		model.addAttribute("pno", s3);
		model.addAttribute("remark", s4);
		return "update";
	}
	
	// 修改用户
	@RequestMapping(value="/update.do")
	public String UpdateUser(String uname,String upwd,String pnumber,String remark,Model model,HttpSession session) throws Exception{
		String suid = (String) session.getAttribute("suid");
		String pwd = AESUtil.encrypt(upwd, suid);
		if(uname!=null&&pwd!=null&&pnumber!=null&&remark!=null&&!uname.equals("")&&!pwd.equals("")&&!pnumber.equals("")&&!remark.equals("")){
			UserInfoBean user = new UserInfoBean(suid,uname, pwd, pnumber, remark);
			qtcs.updateUser(user);
			/*List<UserInfoBean> list = qtcs.getUserList();
			model.addAttribute("list", list);
			return "main";*/
		}
		return "update";
	}
	
	// 用户角色
	@RequestMapping(value="/userrole.do")
	public String getUserRole(String id,String name,Model model,HttpSession session){
		List<RoleBean> list = qtcs.getRole(id);
		session.setAttribute("u_id", id);
		session.setAttribute("u_name", name);
		model.addAttribute("list", list);
		return "role";
	}
	
	// 修改角色
	@RequestMapping(value="/updaterole.do")
	public String updateRoles(HttpSession session,Model model){
		String u_id = (String) session.getAttribute("u_id");
		String u_name = (String) session.getAttribute("u_name");
		if(u_id!=null&&!u_id.equals("")){
			qtcs.deleteRoles(u_id);
		}
		List<RoleBean> lrb = new ArrayList<RoleBean>();
		List<String> lr = (List<String>) session.getAttribute("ls");
		if(lr!=null&&lr.size()>0){
			for(int i=0;i<lr.size();i++){
				RoleBean role = new RoleBean(u_id, u_name, lr.get(i));
				lrb.add(role);
			}
			qtcs.addRoles(lrb);
		}
		session.removeAttribute("ls");
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		return "main";
	}
	
	// 角色权限
	@RequestMapping(value="/rolepower.do")
	public String getRolePower(String rid,String rname,Model model, HttpSession session){
		List<MenuBean> list = qtcs.getPowers(rid);
		session.setAttribute("rid", rid);
		session.setAttribute("rname", rname);
		model.addAttribute("list", list);
		return "rolepower";
	}
	
	// 修改权限
	@RequestMapping(value="/updatepower.do")
	public String updatePowers(HttpSession session,Model model){
		String rid = (String) session.getAttribute("rid");
		if(rid!=null&!rid.equals("")){
			qtcs.deletePowers(rid);
		}
		List<String> lm = (List<String>) session.getAttribute("ls");
		List<MenuBean> lrm = new ArrayList<MenuBean>();
		if(lm!=null&&lm.size()>0){
			for(int i=0;i<lm.size();i++){
				MenuBean menu = new MenuBean(lm.get(i), rid);
				lrm.add(menu);
			}
			qtcs.addPowers(lrm);
		}
		session.removeAttribute("ls");
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		return "main";
	}
	
	// 用户账号
	@RequestMapping(value="/useraccount.do")
	public String getUserAccount(String aid,String aname,Model model,HttpSession session){
		List<UserInfoBean> list = qtcs.getAccount(aid);
		session.setAttribute("a_id", aid);
		session.setAttribute("a_name", aname);
		model.addAttribute("list", list);
		return "account";
	}
	
	// 修改账号
	@RequestMapping(value="/updateaccount.do")
	public String updateAccounts(HttpSession session,Model model){
		String a_id = (String) session.getAttribute("a_id");
		String a_name = (String) session.getAttribute("a_name");
		if(a_id!=null&&!a_id.equals("")){
			qtcs.deleteAccounts(a_id);
		}
		List<String> la = (List<String>) session.getAttribute("ls");
		List<UserInfoBean> lac = new ArrayList<UserInfoBean>();
		if(la!=null&&la.size()>0){
			for(int i=0;i<la.size();i++){
				UserInfoBean user = new UserInfoBean(a_id, la.get(i), a_name);
				lac.add(user);
			}
			qtcs.addAccounts(lac);
		}
		session.removeAttribute("ls");
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		return "main";
	}
	
	
}
