package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.MGAPQueryBatchTransferMapper;
import com.ylxx.fx.service.accumu.businesspara.MGAPQueryBatchTransferSerivce;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.InPutHeadMsgList;
import com.ylxx.fx.utils.MGAPServerAdd_PortType;
import com.ylxx.fx.utils.MGAPServerAdd_ServiceLocator;
import com.ylxx.fx.utils.OutPutHeadMsgListHolder;
import com.ylxx.fx.utils.ResultDomain;
@Service("MGAPQueryBatchTransferSerivce")
public class MGAPQueryBatchTransferSerivceImpl implements MGAPQueryBatchTransferSerivce {
	private static final Logger log = LoggerFactory.getLogger(MGAPQueryBatchTransferSerivceImpl.class);
	@Resource
	public MGAPQueryBatchTransferMapper mGAPQueryBatchTransferMapper;
	@Override
	public String getSearch(String filename, String number,Integer pageNo,Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Map<String, Object>> list = null;
		try {
			list = mGAPQueryBatchTransferMapper.getSearch(filename, number);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
		if(page.getList()!=null && page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
			String value = (String)page.getList().get(i).get("STAT");
				if(value.equals("S")){
					page.getList().get(i).put("STAT","成功");
				}else if(value.equals("F")){
					page.getList().get(i).put("STAT","失败");
				}else if(value.equals("R")){
					 page.getList().get(i).put("STAT","异常");
				}else if(value.equals("8")){
					page.getList().get(i).put("STAT","结果文件上传成功");
				}else{
					page.getList().get(i).put("STAT",value);
				}
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	
	//发送
	public String getBatchTransfer(String trsn,String trid,String bhid,String chnl,String rqdt,String rqtm,String trtl,String ttyn,String autl,String number,String filename,String count,String amut) throws MalformedURLException, ServiceException, RemoteException {
		MGAPServerAdd_ServiceLocator  mgapserverAdd=new MGAPServerAdd_ServiceLocator();
		String address="http://127.0.0.1:8080/MGAPServerAdd/services/MGAPServerAdd";
//			String address = FxipCommMethod
//			.readCfig(FxipCommMethod.getAPP_HOME()
//					+ "config/NpsAndPesAddress.properties",
//					"BatchTransfer");
//					
		URL url=null;
		
		url=new URL(address);
		MGAPServerAdd_PortType porttype=null;
		porttype=mgapserverAdd.getMGAPServerAddSOAP(url);
		InPutHeadMsgList inputMsg=setInValue(trsn,trid,bhid,chnl,rqdt,rqtm,trtl,ttyn,autl);
		OutPutHeadMsgListHolder outMsg=new OutPutHeadMsgListHolder();
		
		StringHolder lcno=new StringHolder("");
		StringHolder trdt=new StringHolder("");
		StringHolder trtm=new StringHolder("");
		porttype.MGAPBatchTransfer(inputMsg, number, filename, count, amut,"", outMsg, lcno, trdt, trtm);
		//System.out.print(outMsg.value.getSTATUS());
		String status=outMsg.value.getSTATUS();
		return status;
				
	}
	//取得前台发送的报文头的信息
		public InPutHeadMsgList setInValue(String trsn,String trid,String bhid,String chnl,String rqdt,String rqtm,String trtl,String ttyn,String autl){
			InPutHeadMsgList inputMsg=new InPutHeadMsgList();
			inputMsg.setTRSN(null==trsn?"":trsn);
			inputMsg.setTRID(null==trid?"":trid);
			inputMsg.setBHID(null==bhid?"":bhid);
			inputMsg.setCHNL(null==chnl?"":chnl);
			inputMsg.setRQDT(null==rqdt?"":rqdt);
			inputMsg.setRQTM(null==rqtm?"":rqtm);
			inputMsg.setTRTL(null==trtl?"":trtl);
			inputMsg.setTTYN(null==ttyn?"":ttyn);
			inputMsg.setAUTL(null==autl?"":autl);				
			return inputMsg;				
		}	
}
