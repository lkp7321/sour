package com.ylxx.fx.service.accumu.businesspara;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

/**
 * 批量转账查询
 * @author lz130
 *
 */
public interface MGAPQueryBatchTransferSerivce {
	String getSearch(String filename,String number,Integer pageNo,Integer pageSize);
	String getBatchTransfer(String trsn,String trid,String bhid,String chnl,String rqdt,String rqtm,String trtl,String ttyn,String autl,String number,String filename,String count,String amut)throws MalformedURLException, ServiceException, RemoteException;
}
