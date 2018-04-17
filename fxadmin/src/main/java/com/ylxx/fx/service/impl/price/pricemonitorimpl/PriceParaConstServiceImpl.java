package com.ylxx.fx.service.impl.price.pricemonitorimpl;
/**
 * 产品校验参数
 */
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.price.pricemonitor.PriceParaConstMapper;
import com.ylxx.fx.service.po.PdtChkparaForAcc;
import com.ylxx.fx.service.po.PdtPointForAcc;
import com.ylxx.fx.service.po.PdtPointTForAcc;
import com.ylxx.fx.service.price.pricemonitor.PriceParaConstService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
@Service("priceParaConstService")
public class PriceParaConstServiceImpl implements PriceParaConstService{

	@Resource
	private PriceParaConstMapper priceParaConstMap;
	private static final Logger log = LoggerFactory.getLogger(PriceParaConstServiceImpl.class);
	private  ReadStringCodeConfig  readString=new ReadStringCodeConfig();
	private  String  stringCode="";
	/**
	 * 外汇点差下拉框
	 */
	public List<Map<String, String>> getItems(String ptid) {
		
		return priceParaConstMap.selectItems(ptid);
	}

	/**
	 * 获取外汇点差数据
	 */
	public List<PdtPointForAcc> getAllPdtPoint(String userKey, String ptid, String ptid1) {
		
		List<PdtPointForAcc> list1 = new ArrayList<PdtPointForAcc>();
		try {
			List<PdtPointForAcc> list = priceParaConstMap.selectAllPdtPoint(ptid, ptid1);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					PdtPointForAcc pdtpoint = new PdtPointForAcc();
					pdtpoint.setTpfg(list.get(i).getTpnm());//价格类型
					pdtpoint.setTerm(list.get(i).getTerm());//期限
					pdtpoint.setExnm(list.get(i).getExnm());//货币对名称
					pdtpoint.setExcd(list.get(i).getExcd());//货币对编号
					pdtpoint.setPrtp(list.get(i).getPrtp().equals("0")?"双边价":list.get(i).getPrtp().equals("1")?"中间价":"未知");//报价模式
					pdtpoint.setBhbd(list.get(i).getBhbd());//总分行买入点差
					pdtpoint.setBhsd(list.get(i).getBhsd());//总分行卖出点差
					pdtpoint.setCubd(list.get(i).getCubd());//总行对客户买入点差
					pdtpoint.setCusd(list.get(i).getCusd());//总行对客户卖出点差
					pdtpoint.setQtcy(list.get(i).getQtcy());//价格生命周期
					if(null!=list.get(i).getUphz()&&!"".equals(list.get(i).getUphz())){
						pdtpoint.setUphz(list.get(i).getUphz());//更新频率
					}
					pdtpoint.setCxfg(list.get(i).getCxfg().equals("2")?"钞":"汇");
					pdtpoint.setUnit(list.get(i).getUnit());//单位
					list1.add(pdtpoint);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list1;
	}

	/**
	 * 修改外汇点差
	 */
	public boolean upPoint(String userKey, String prod, PdtPointTForAcc pdtPoint) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = false;
		int a = 0;
		try {
			a = priceParaConstMap.updatePoint(prod, pdtPoint);
			if(a>0){
				log.info("修改外汇点差成功");
				flag = true;
			}else{
				flag = false;
				log.error("修改外汇点差失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}
	
	/**
	 * 点差生效
	 */
	public void SendSocketB1() {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("pdtRParaConstForAccIp", "pdtRParaConstForAccPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "<RELOAD>POINT</RELOAD>";
		log.info("点差生效ip:" + strip + " port:" + strport+content);
//		byte[] buffer ;
		Socket socket= null;
		PrintWriter out = null;
		BufferedReader in= null;
		try {
			socket = new Socket(strip,strport);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), stringCode)), true);
			out.println(content);
			out.flush();
			log.info("点差生效发送报文成功： "+content);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(out!=null){
				out.close();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
	//校验生效
	public void SendSocketB() {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("pdtRParaConstForAccIp", "pdtRParaConstForAccPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "<RELOAD>CHKPARA</RELOAD>";
		log.info("校验生效ip:" + strip + " port:" + strport + content);
//		byte[] buffer ;
		Socket socket= null;
		PrintWriter out = null;
		BufferedReader in= null;
//		strip = "197.3.103.177";
//		strport = 6801;
		try {
			socket = new Socket(strip,strport);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), stringCode)), true);
			out.println(content);
			out.flush();
			log.info("校验生效发送报文成功： "+content);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(out!=null){
				out.close();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * 获取产品校验数据
	 */
	public List<PdtChkparaForAcc> getAllPdtChkpara(String userKey, String prod) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<PdtChkparaForAcc> list = null;
		try {
			if(curUser.getProd().equals("P999")){
				list = priceParaConstMap.selectAllPdtChkpara(prod);
			}else if(curUser.getProd().equals("P007")){
				list = priceParaConstMap.selectAllPdtChkpara(curUser.getProd());
			}
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setCxfg(list.get(i).getCxfg().equals("2")?"钞":"汇");
					list.get(i).setUsfg(list.get(i).getUsfg().equals("0")?"启用":"停用");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 全部启用
	 */
	public boolean startAll(String userKey, String pdtinfoid) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			if(curUser.getProd().equals("P999")){
				a = priceParaConstMap.openAll(pdtinfoid);
			}else if(curUser.getProd().equals("P007")){
				a = priceParaConstMap.openAll(curUser.getProd());
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 全部停用
	 */
	public boolean endAll(String userKey, String pdtinfoid) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			if(curUser.getProd().equals("P999")){
				a = priceParaConstMap.stopAll(pdtinfoid);
			}else if(curUser.getProd().equals("P007")){
				a = priceParaConstMap.stopAll(curUser.getProd());
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
