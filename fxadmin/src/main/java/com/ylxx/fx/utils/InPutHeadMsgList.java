/**
 * InPutHeadMsgList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ylxx.fx.utils;

public class InPutHeadMsgList  implements java.io.Serializable {
    private java.lang.String TRSN;

    private java.lang.String TRID;

    private java.lang.String BHID;

    private java.lang.String CHNL;

    private java.lang.String RQDT;

    private java.lang.String RQTM;

    private java.lang.String TRTL;

    private java.lang.String TTYN;

    private java.lang.String AUTL;

    public InPutHeadMsgList() {
    }

    public InPutHeadMsgList(
           java.lang.String TRSN,
           java.lang.String TRID,
           java.lang.String BHID,
           java.lang.String CHNL,
           java.lang.String RQDT,
           java.lang.String RQTM,
           java.lang.String TRTL,
           java.lang.String TTYN,
           java.lang.String AUTL) {
           this.TRSN = TRSN;
           this.TRID = TRID;
           this.BHID = BHID;
           this.CHNL = CHNL;
           this.RQDT = RQDT;
           this.RQTM = RQTM;
           this.TRTL = TRTL;
           this.TTYN = TTYN;
           this.AUTL = AUTL;
    }


    /**
     * Gets the TRSN value for this InPutHeadMsgList.
     * 
     * @return TRSN
     */
    public java.lang.String getTRSN() {
        return TRSN;
    }


    /**
     * Sets the TRSN value for this InPutHeadMsgList.
     * 
     * @param TRSN
     */
    public void setTRSN(java.lang.String TRSN) {
        this.TRSN = TRSN;
    }


    /**
     * Gets the TRID value for this InPutHeadMsgList.
     * 
     * @return TRID
     */
    public java.lang.String getTRID() {
        return TRID;
    }


    /**
     * Sets the TRID value for this InPutHeadMsgList.
     * 
     * @param TRID
     */
    public void setTRID(java.lang.String TRID) {
        this.TRID = TRID;
    }


    /**
     * Gets the BHID value for this InPutHeadMsgList.
     * 
     * @return BHID
     */
    public java.lang.String getBHID() {
        return BHID;
    }


    /**
     * Sets the BHID value for this InPutHeadMsgList.
     * 
     * @param BHID
     */
    public void setBHID(java.lang.String BHID) {
        this.BHID = BHID;
    }


    /**
     * Gets the CHNL value for this InPutHeadMsgList.
     * 
     * @return CHNL
     */
    public java.lang.String getCHNL() {
        return CHNL;
    }


    /**
     * Sets the CHNL value for this InPutHeadMsgList.
     * 
     * @param CHNL
     */
    public void setCHNL(java.lang.String CHNL) {
        this.CHNL = CHNL;
    }


    /**
     * Gets the RQDT value for this InPutHeadMsgList.
     * 
     * @return RQDT
     */
    public java.lang.String getRQDT() {
        return RQDT;
    }


    /**
     * Sets the RQDT value for this InPutHeadMsgList.
     * 
     * @param RQDT
     */
    public void setRQDT(java.lang.String RQDT) {
        this.RQDT = RQDT;
    }


    /**
     * Gets the RQTM value for this InPutHeadMsgList.
     * 
     * @return RQTM
     */
    public java.lang.String getRQTM() {
        return RQTM;
    }


    /**
     * Sets the RQTM value for this InPutHeadMsgList.
     * 
     * @param RQTM
     */
    public void setRQTM(java.lang.String RQTM) {
        this.RQTM = RQTM;
    }


    /**
     * Gets the TRTL value for this InPutHeadMsgList.
     * 
     * @return TRTL
     */
    public java.lang.String getTRTL() {
        return TRTL;
    }


    /**
     * Sets the TRTL value for this InPutHeadMsgList.
     * 
     * @param TRTL
     */
    public void setTRTL(java.lang.String TRTL) {
        this.TRTL = TRTL;
    }


    /**
     * Gets the TTYN value for this InPutHeadMsgList.
     * 
     * @return TTYN
     */
    public java.lang.String getTTYN() {
        return TTYN;
    }


    /**
     * Sets the TTYN value for this InPutHeadMsgList.
     * 
     * @param TTYN
     */
    public void setTTYN(java.lang.String TTYN) {
        this.TTYN = TTYN;
    }


    /**
     * Gets the AUTL value for this InPutHeadMsgList.
     * 
     * @return AUTL
     */
    public java.lang.String getAUTL() {
        return AUTL;
    }


    /**
     * Sets the AUTL value for this InPutHeadMsgList.
     * 
     * @param AUTL
     */
    public void setAUTL(java.lang.String AUTL) {
        this.AUTL = AUTL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InPutHeadMsgList)) return false;
        InPutHeadMsgList other = (InPutHeadMsgList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.TRSN==null && other.getTRSN()==null) || 
             (this.TRSN!=null &&
              this.TRSN.equals(other.getTRSN()))) &&
            ((this.TRID==null && other.getTRID()==null) || 
             (this.TRID!=null &&
              this.TRID.equals(other.getTRID()))) &&
            ((this.BHID==null && other.getBHID()==null) || 
             (this.BHID!=null &&
              this.BHID.equals(other.getBHID()))) &&
            ((this.CHNL==null && other.getCHNL()==null) || 
             (this.CHNL!=null &&
              this.CHNL.equals(other.getCHNL()))) &&
            ((this.RQDT==null && other.getRQDT()==null) || 
             (this.RQDT!=null &&
              this.RQDT.equals(other.getRQDT()))) &&
            ((this.RQTM==null && other.getRQTM()==null) || 
             (this.RQTM!=null &&
              this.RQTM.equals(other.getRQTM()))) &&
            ((this.TRTL==null && other.getTRTL()==null) || 
             (this.TRTL!=null &&
              this.TRTL.equals(other.getTRTL()))) &&
            ((this.TTYN==null && other.getTTYN()==null) || 
             (this.TTYN!=null &&
              this.TTYN.equals(other.getTTYN()))) &&
            ((this.AUTL==null && other.getAUTL()==null) || 
             (this.AUTL!=null &&
              this.AUTL.equals(other.getAUTL())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTRSN() != null) {
            _hashCode += getTRSN().hashCode();
        }
        if (getTRID() != null) {
            _hashCode += getTRID().hashCode();
        }
        if (getBHID() != null) {
            _hashCode += getBHID().hashCode();
        }
        if (getCHNL() != null) {
            _hashCode += getCHNL().hashCode();
        }
        if (getRQDT() != null) {
            _hashCode += getRQDT().hashCode();
        }
        if (getRQTM() != null) {
            _hashCode += getRQTM().hashCode();
        }
        if (getTRTL() != null) {
            _hashCode += getTRTL().hashCode();
        }
        if (getTTYN() != null) {
            _hashCode += getTTYN().hashCode();
        }
        if (getAUTL() != null) {
            _hashCode += getAUTL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InPutHeadMsgList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.huarong.soap/wsdl/MGAPServerAdd/", "InPutHeadMsgList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRSN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TRSN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TRID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BHID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BHID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CHNL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CHNL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RQDT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RQDT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RQTM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RQTM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRTL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TRTL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TTYN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TTYN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AUTL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AUTL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
