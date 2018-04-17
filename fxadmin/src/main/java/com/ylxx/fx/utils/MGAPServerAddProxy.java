package com.ylxx.fx.utils;

public class MGAPServerAddProxy implements MGAPServerAdd_PortType {
  private String _endpoint = null;
  private MGAPServerAdd_PortType mGAPServerAdd_PortType = null;
  
  public MGAPServerAddProxy() {
    _initMGAPServerAddProxy();
  }
  
  public MGAPServerAddProxy(String endpoint) {
    _endpoint = endpoint;
    _initMGAPServerAddProxy();
  }
  
  private void _initMGAPServerAddProxy() {
    try {
      mGAPServerAdd_PortType = (new MGAPServerAdd_ServiceLocator()).getMGAPServerAddSOAP();
      if (mGAPServerAdd_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mGAPServerAdd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mGAPServerAdd_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mGAPServerAdd_PortType != null)
      ((javax.xml.rpc.Stub)mGAPServerAdd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public MGAPServerAdd_PortType getMGAPServerAdd_PortType() {
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    return mGAPServerAdd_PortType;
  }
  
  public void MGAPAccRegisterChange(InPutHeadMsgList inPutHeadMsg, java.lang.String CUNO, javax.xml.rpc.holders.StringHolder CUAC, java.lang.String PTID, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder PTNM) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPAccRegisterChange(inPutHeadMsg, CUNO, CUAC, PTID, EXDA, outPutHeadMsg, LCNO, TRDT, TRTM, PTNM);
  }
  
  public void MGAPTransfer(InPutHeadMsgList inPutHeadMsg, javax.xml.rpc.holders.StringHolder CUAC, java.lang.String CYEN, javax.xml.rpc.holders.StringHolder SLAM, java.lang.String CUNO, java.lang.String TONM, javax.xml.rpc.holders.StringHolder TOAC, java.lang.String CATY, java.lang.String SCUNO, java.lang.String SNAME, java.lang.String TELO, java.lang.String POSTS, java.lang.String EXDB, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder CBLV) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPTransfer(inPutHeadMsg, CUAC, CYEN, SLAM, CUNO, TONM, TOAC, CATY, SCUNO, SNAME, TELO, POSTS, EXDB, EXDA, outPutHeadMsg, LCNO, TRDT, TRTM, CBLV);
  }
  
  public void MGAPTransferCancel(InPutHeadMsgList inPutHeadMsg, java.lang.String OTSN, java.lang.String OLDT, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder CUAC, javax.xml.rpc.holders.StringHolder SLAM, javax.xml.rpc.holders.StringHolder TOAC, javax.xml.rpc.holders.StringHolder CBLV) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPTransferCancel(inPutHeadMsg, OTSN, OLDT, EXDA, outPutHeadMsg, LCNO, TRDT, TRTM, CUAC, SLAM, TOAC, CBLV);
  }
  
  public void MGAPBatchTransfer(InPutHeadMsgList inPutHeadMsg, java.lang.String BNUM, java.lang.String FILE, java.lang.String COUT, java.lang.String AMUT, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPBatchTransfer(inPutHeadMsg, BNUM, FILE, COUT, AMUT, EXDA, outPutHeadMsg, LCNO, TRDT, TRTM);
  }
  
  public void MGAPQueryBatchTransfer(InPutHeadMsgList inPutHeadMsg, javax.xml.rpc.holders.StringHolder BNUM, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder STAT, javax.xml.rpc.holders.StringHolder FILE, javax.xml.rpc.holders.StringHolder COUT, javax.xml.rpc.holders.StringHolder AMUT, javax.xml.rpc.holders.StringHolder SNUM, javax.xml.rpc.holders.StringHolder SSAM) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPQueryBatchTransfer(inPutHeadMsg, BNUM, EXDA, outPutHeadMsg, STAT, FILE, COUT, AMUT, SNUM, SSAM);
  }
  
  public void MGAPQueryTransferHis(InPutHeadMsgList inPutHeadMsg, java.lang.String CUAC, java.lang.String STDT, java.lang.String EDDT, java.lang.String CYNM, java.lang.String TRFG, java.lang.String STAR, java.lang.String PNUM, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder COUNT, javax.xml.rpc.holders.StringHolder RNUM, OutQueryTransferHisListHolder outQueryTransferHisList) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPQueryTransferHis(inPutHeadMsg, CUAC, STDT, EDDT, CYNM, TRFG, STAR, PNUM, EXDA, outPutHeadMsg, COUNT, RNUM, outQueryTransferHisList);
  }
  
  public void MGAPTradeForComp(InPutHeadMsgList inPutHeadMsg, java.lang.String CSOF, java.lang.String SOAC, java.lang.String CTOF, javax.xml.rpc.holders.StringHolder TOAC, javax.xml.rpc.holders.StringHolder SLNM, javax.xml.rpc.holders.StringHolder BYNM, javax.xml.rpc.holders.StringHolder BYAM, javax.xml.rpc.holders.StringHolder SLAM, java.lang.String AKPC, javax.xml.rpc.holders.StringHolder FVDA, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder EXPC, javax.xml.rpc.holders.StringHolder CBLV, javax.xml.rpc.holders.StringHolder AVPC) throws java.rmi.RemoteException{
    if (mGAPServerAdd_PortType == null)
      _initMGAPServerAddProxy();
    mGAPServerAdd_PortType.MGAPTradeForComp(inPutHeadMsg, CSOF, SOAC, CTOF, TOAC, SLNM, BYNM, BYAM, SLAM, AKPC, FVDA, EXDA, outPutHeadMsg, LCNO, TRDT, TRTM, EXPC, CBLV, AVPC);
  }
  
  
}