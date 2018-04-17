package com.ylxx.fx.utils;

import com.letpower.framework.business.service.ServiceException;

public class MGAPServerAdd_ServiceLocator extends org.apache.axis.client.Service implements MGAPServerAdd_Service {

    public MGAPServerAdd_ServiceLocator() {
    }


    public MGAPServerAdd_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MGAPServerAdd_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException{
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MGAPServerAddSOAP
    private java.lang.String MGAPServerAddSOAP_address = "http://197.3.176.62:8080/MGAPServerAdd/services/MGAPServerAdd/";

    public java.lang.String getMGAPServerAddSOAPAddress() {
        return MGAPServerAddSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MGAPServerAddSOAPWSDDServiceName = "MGAPServerAddSOAP";

    public java.lang.String getMGAPServerAddSOAPWSDDServiceName() {
        return MGAPServerAddSOAPWSDDServiceName;
    }

    public void setMGAPServerAddSOAPWSDDServiceName(java.lang.String name) {
        MGAPServerAddSOAPWSDDServiceName = name;
    }

    public MGAPServerAdd_PortType getMGAPServerAddSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MGAPServerAddSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMGAPServerAddSOAP(endpoint);
    }

    public MGAPServerAdd_PortType getMGAPServerAddSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MGAPServerAddSOAPStub _stub = new MGAPServerAddSOAPStub(portAddress, this);
            _stub.setPortName(getMGAPServerAddSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMGAPServerAddSOAPEndpointAddress(java.lang.String address) {
        MGAPServerAddSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * @throws javax.xml.rpc.ServiceException 
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (MGAPServerAdd_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
               MGAPServerAddSOAPStub _stub = new MGAPServerAddSOAPStub(new java.net.URL(MGAPServerAddSOAP_address), this);
                _stub.setPortName(getMGAPServerAddSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MGAPServerAddSOAP".equals(inputPortName)) {
            return getMGAPServerAddSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPServerAdd");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://com.huarong.soap/MGAPServerAdd/", "MGAPServerAddSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MGAPServerAddSOAP".equals(portName)) {
            setMGAPServerAddSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
