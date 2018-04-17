/**
 * MGAPServerAddSOAPStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ylxx.fx.utils;

import javax.xml.rpc.Service;

public class MGAPServerAddSOAPStub extends org.apache.axis.client.Stub implements MGAPServerAdd_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[7];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPAccRegisterChange");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUAC"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "PTID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "LCNO"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRDT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRTM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "PTNM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPTransfer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUAC"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CYEN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SLAM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TONM"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TOAC"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CATY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SCUNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SNAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TELO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "POSTS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDB"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "LCNO"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRDT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRTM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CBLV"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPTransferCancel");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OTSN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OLDT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "LCNO"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRDT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRTM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUAC"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SLAM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TOAC"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CBLV"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPBatchTransfer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "BNUM"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "FILE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "COUT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AMUT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "LCNO"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRDT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRTM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPQueryBatchTransfer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "BNUM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "STAT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "FILE"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "COUT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AMUT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SNUM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SSAM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPQueryTransferHis");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CUAC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "STDT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EDDT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CYNM"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRFG"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "STAR"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "PNUM"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "COUNT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "RNUM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutQueryTransferHisList"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutQueryTransferHisList"), QueryTransferHis[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "QueryTransferHis"));
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MGAPTradeForComp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "InPutHeadMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"), InPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CSOF"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SOAC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CTOF"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TOAC"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SLNM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "BYNM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "BYAM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SLAM"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AKPC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "FVDA"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXDA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "OutPutHeadMsg"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList"), OutPutHeadMsgList.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "LCNO"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRDT"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TRTM"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EXPC"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CBLV"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "AVPC"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

    }

    public MGAPServerAddSOAPStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public MGAPServerAddSOAPStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public MGAPServerAddSOAPStub(Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList");
            cachedSerQNames.add(qName);
            cls = InPutHeadMsgList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutPutHeadMsgList");
            cachedSerQNames.add(qName);
            cls = OutPutHeadMsgList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "OutQueryTransferHisList");
            cachedSerQNames.add(qName);
            cls = QueryTransferHis[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "QueryTransferHis");
            qName2 = new javax.xml.namespace.QName("", "QueryTransferHis");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "QueryTransferHis");
            cachedSerQNames.add(qName);
            cls = QueryTransferHis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void MGAPAccRegisterChange(InPutHeadMsgList inPutHeadMsg, java.lang.String CUNO, javax.xml.rpc.holders.StringHolder CUAC, java.lang.String PTID, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder PTNM) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPAccRegisterChange");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPAccRegisterChange"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, CUNO, CUAC.value, PTID, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                CUAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CUAC"));
            } catch (java.lang.Exception _exception) {
                CUAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CUAC")), java.lang.String.class);
            }
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                LCNO.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "LCNO"));
            } catch (java.lang.Exception _exception) {
                LCNO.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "LCNO")), java.lang.String.class);
            }
            try {
                TRDT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRDT"));
            } catch (java.lang.Exception _exception) {
                TRDT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRDT")), java.lang.String.class);
            }
            try {
                TRTM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRTM"));
            } catch (java.lang.Exception _exception) {
                TRTM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRTM")), java.lang.String.class);
            }
            try {
                PTNM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "PTNM"));
            } catch (java.lang.Exception _exception) {
                PTNM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "PTNM")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPTransfer(InPutHeadMsgList inPutHeadMsg, javax.xml.rpc.holders.StringHolder CUAC, java.lang.String CYEN, javax.xml.rpc.holders.StringHolder SLAM, java.lang.String CUNO, java.lang.String TONM, javax.xml.rpc.holders.StringHolder TOAC, java.lang.String CATY, java.lang.String SCUNO, java.lang.String SNAME, java.lang.String TELO, java.lang.String POSTS, java.lang.String EXDB, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder CBLV) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPTransfer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPTransfer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, CUAC.value, CYEN, SLAM.value, CUNO, TONM, TOAC.value, CATY, SCUNO, SNAME, TELO, POSTS, EXDB, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                CUAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CUAC"));
            } catch (java.lang.Exception _exception) {
                CUAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CUAC")), java.lang.String.class);
            }
            try {
                SLAM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SLAM"));
            } catch (java.lang.Exception _exception) {
                SLAM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SLAM")), java.lang.String.class);
            }
            try {
                TOAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TOAC"));
            } catch (java.lang.Exception _exception) {
                TOAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TOAC")), java.lang.String.class);
            }
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                LCNO.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "LCNO"));
            } catch (java.lang.Exception _exception) {
                LCNO.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "LCNO")), java.lang.String.class);
            }
            try {
                TRDT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRDT"));
            } catch (java.lang.Exception _exception) {
                TRDT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRDT")), java.lang.String.class);
            }
            try {
                TRTM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRTM"));
            } catch (java.lang.Exception _exception) {
                TRTM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRTM")), java.lang.String.class);
            }
            try {
                CBLV.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CBLV"));
            } catch (java.lang.Exception _exception) {
                CBLV.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CBLV")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPTransferCancel(InPutHeadMsgList inPutHeadMsg, java.lang.String OTSN, java.lang.String OLDT, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder CUAC, javax.xml.rpc.holders.StringHolder SLAM, javax.xml.rpc.holders.StringHolder TOAC, javax.xml.rpc.holders.StringHolder CBLV) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPTransferCancel");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPTransferCancel"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, OTSN, OLDT, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                LCNO.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "LCNO"));
            } catch (java.lang.Exception _exception) {
                LCNO.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "LCNO")), java.lang.String.class);
            }
            try {
                TRDT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRDT"));
            } catch (java.lang.Exception _exception) {
                TRDT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRDT")), java.lang.String.class);
            }
            try {
                TRTM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRTM"));
            } catch (java.lang.Exception _exception) {
                TRTM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRTM")), java.lang.String.class);
            }
            try {
                CUAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CUAC"));
            } catch (java.lang.Exception _exception) {
                CUAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CUAC")), java.lang.String.class);
            }
            try {
                SLAM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SLAM"));
            } catch (java.lang.Exception _exception) {
                SLAM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SLAM")), java.lang.String.class);
            }
            try {
                TOAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TOAC"));
            } catch (java.lang.Exception _exception) {
                TOAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TOAC")), java.lang.String.class);
            }
            try {
                CBLV.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CBLV"));
            } catch (java.lang.Exception _exception) {
                CBLV.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CBLV")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPBatchTransfer(InPutHeadMsgList inPutHeadMsg, java.lang.String BNUM, java.lang.String FILE, java.lang.String COUT, java.lang.String AMUT, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPBatchTransfer");   
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPBatchTransfer"));
       
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, BNUM, FILE, COUT, AMUT, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                LCNO.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "LCNO"));
            } catch (java.lang.Exception _exception) {
                LCNO.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "LCNO")), java.lang.String.class);
            }
            try {
                TRDT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRDT"));
            } catch (java.lang.Exception _exception) {
                TRDT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRDT")), java.lang.String.class);
            }
            try {
                TRTM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRTM"));
            } catch (java.lang.Exception _exception) {
                TRTM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRTM")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPQueryBatchTransfer(InPutHeadMsgList inPutHeadMsg, javax.xml.rpc.holders.StringHolder BNUM, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder STAT, javax.xml.rpc.holders.StringHolder FILE, javax.xml.rpc.holders.StringHolder COUT, javax.xml.rpc.holders.StringHolder AMUT, javax.xml.rpc.holders.StringHolder SNUM, javax.xml.rpc.holders.StringHolder SSAM) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPQueryBatchTransfer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPQueryBatchTransfer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, BNUM.value, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                BNUM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "BNUM"));
            } catch (java.lang.Exception _exception) {
                BNUM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "BNUM")), java.lang.String.class);
            }
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                STAT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "STAT"));
            } catch (java.lang.Exception _exception) {
                STAT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "STAT")), java.lang.String.class);
            }
            try {
                FILE.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "FILE"));
            } catch (java.lang.Exception _exception) {
                FILE.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "FILE")), java.lang.String.class);
            }
            try {
                COUT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "COUT"));
            } catch (java.lang.Exception _exception) {
                COUT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "COUT")), java.lang.String.class);
            }
            try {
                AMUT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "AMUT"));
            } catch (java.lang.Exception _exception) {
                AMUT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "AMUT")), java.lang.String.class);
            }
            try {
                SNUM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SNUM"));
            } catch (java.lang.Exception _exception) {
                SNUM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SNUM")), java.lang.String.class);
            }
            try {
                SSAM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SSAM"));
            } catch (java.lang.Exception _exception) {
                SSAM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SSAM")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPQueryTransferHis(InPutHeadMsgList inPutHeadMsg, java.lang.String CUAC, java.lang.String STDT, java.lang.String EDDT, java.lang.String CYNM, java.lang.String TRFG, java.lang.String STAR, java.lang.String PNUM, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder COUNT, javax.xml.rpc.holders.StringHolder RNUM, OutQueryTransferHisListHolder outQueryTransferHisList) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPQueryTransferHis");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPQueryTransferHis"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, CUAC, STDT, EDDT, CYNM, TRFG, STAR, PNUM, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                COUNT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "COUNT"));
            } catch (java.lang.Exception _exception) {
                COUNT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "COUNT")), java.lang.String.class);
            }
            try {
                RNUM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "RNUM"));
            } catch (java.lang.Exception _exception) {
                RNUM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "RNUM")), java.lang.String.class);
            }
            try {
                outQueryTransferHisList.value = (QueryTransferHis[]) _output.get(new javax.xml.namespace.QName("", "OutQueryTransferHisList"));
            } catch (java.lang.Exception _exception) {
                outQueryTransferHisList.value = (QueryTransferHis[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutQueryTransferHisList")), QueryTransferHis[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void MGAPTradeForComp(InPutHeadMsgList inPutHeadMsg, java.lang.String CSOF, java.lang.String SOAC, java.lang.String CTOF, javax.xml.rpc.holders.StringHolder TOAC, javax.xml.rpc.holders.StringHolder SLNM, javax.xml.rpc.holders.StringHolder BYNM, javax.xml.rpc.holders.StringHolder BYAM, javax.xml.rpc.holders.StringHolder SLAM, java.lang.String AKPC, javax.xml.rpc.holders.StringHolder FVDA, java.lang.String EXDA, OutPutHeadMsgListHolder outPutHeadMsg, javax.xml.rpc.holders.StringHolder LCNO, javax.xml.rpc.holders.StringHolder TRDT, javax.xml.rpc.holders.StringHolder TRTM, javax.xml.rpc.holders.StringHolder EXPC, javax.xml.rpc.holders.StringHolder CBLV, javax.xml.rpc.holders.StringHolder AVPC) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.huarong.soap/MGAPServerAdd/MGAPTradeForComp");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPTradeForComp"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {inPutHeadMsg, CSOF, SOAC, CTOF, TOAC.value, SLNM.value, BYNM.value, BYAM.value, SLAM.value, AKPC, FVDA.value, EXDA});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                TOAC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TOAC"));
            } catch (java.lang.Exception _exception) {
                TOAC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TOAC")), java.lang.String.class);
            }
            try {
                SLNM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SLNM"));
            } catch (java.lang.Exception _exception) {
                SLNM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SLNM")), java.lang.String.class);
            }
            try {
                BYNM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "BYNM"));
            } catch (java.lang.Exception _exception) {
                BYNM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "BYNM")), java.lang.String.class);
            }
            try {
                BYAM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "BYAM"));
            } catch (java.lang.Exception _exception) {
                BYAM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "BYAM")), java.lang.String.class);
            }
            try {
                SLAM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "SLAM"));
            } catch (java.lang.Exception _exception) {
                SLAM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "SLAM")), java.lang.String.class);
            }
            try {
                FVDA.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "FVDA"));
            } catch (java.lang.Exception _exception) {
                FVDA.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "FVDA")), java.lang.String.class);
            }
            try {
                outPutHeadMsg.value = (OutPutHeadMsgList) _output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg"));
            } catch (java.lang.Exception _exception) {
                outPutHeadMsg.value = (OutPutHeadMsgList) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "OutPutHeadMsg")), OutPutHeadMsgList.class);
            }
            try {
                LCNO.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "LCNO"));
            } catch (java.lang.Exception _exception) {
                LCNO.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "LCNO")), java.lang.String.class);
            }
            try {
                TRDT.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRDT"));
            } catch (java.lang.Exception _exception) {
                TRDT.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRDT")), java.lang.String.class);
            }
            try {
                TRTM.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "TRTM"));
            } catch (java.lang.Exception _exception) {
                TRTM.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "TRTM")), java.lang.String.class);
            }
            try {
                EXPC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "EXPC"));
            } catch (java.lang.Exception _exception) {
                EXPC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "EXPC")), java.lang.String.class);
            }
            try {
                CBLV.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "CBLV"));
            } catch (java.lang.Exception _exception) {
                CBLV.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "CBLV")), java.lang.String.class);
            }
            try {
                AVPC.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "AVPC"));
            } catch (java.lang.Exception _exception) {
                AVPC.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "AVPC")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
