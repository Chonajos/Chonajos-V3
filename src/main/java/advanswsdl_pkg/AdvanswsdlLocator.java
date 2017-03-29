/**
 * AdvanswsdlLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package advanswsdl_pkg;

public class AdvanswsdlLocator extends org.apache.axis.client.Service implements advanswsdl_pkg.Advanswsdl {

    public AdvanswsdlLocator() {
    }


    public AdvanswsdlLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AdvanswsdlLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for advanswsdlPort
    //PRODUCCIÓN
    //private java.lang.String advanswsdlPort_address = "https://app.advans.mx/ws/awscfdi.php?wsdl";
      //PRUEBAS
    private java.lang.String advanswsdlPort_address = "https://dev.advans.mx:443/ws/awscfdi.php";
    

    public java.lang.String getadvanswsdlPortAddress() {
        return advanswsdlPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String advanswsdlPortWSDDServiceName = "advanswsdlPort";

    public java.lang.String getadvanswsdlPortWSDDServiceName() {
        return advanswsdlPortWSDDServiceName;
    }

    public void setadvanswsdlPortWSDDServiceName(java.lang.String name) {
        advanswsdlPortWSDDServiceName = name;
    }

    public advanswsdl_pkg.AdvanswsdlPortType getadvanswsdlPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(advanswsdlPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getadvanswsdlPort(endpoint);
    }

    public advanswsdl_pkg.AdvanswsdlPortType getadvanswsdlPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            advanswsdl_pkg.AdvanswsdlBindingStub _stub = new advanswsdl_pkg.AdvanswsdlBindingStub(portAddress, this);
            _stub.setPortName(getadvanswsdlPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setadvanswsdlPortEndpointAddress(java.lang.String address) {
        advanswsdlPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (advanswsdl_pkg.AdvanswsdlPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                advanswsdl_pkg.AdvanswsdlBindingStub _stub = new advanswsdl_pkg.AdvanswsdlBindingStub(new java.net.URL(advanswsdlPort_address), this);
                _stub.setPortName(getadvanswsdlPortWSDDServiceName());
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
        if ("advanswsdlPort".equals(inputPortName)) {
            return getadvanswsdlPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:advanswsdl", "advanswsdl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:advanswsdl", "advanswsdlPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("advanswsdlPort".equals(portName)) {
            setadvanswsdlPortEndpointAddress(address);
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
