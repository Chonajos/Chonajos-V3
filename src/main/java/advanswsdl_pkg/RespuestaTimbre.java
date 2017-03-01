/**
 * RespuestaTimbre.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package advanswsdl_pkg;

public class RespuestaTimbre  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String subCode;

    private java.lang.String message;

    private java.lang.String timbre;

    public RespuestaTimbre() {
    }

    public RespuestaTimbre(
           java.lang.String code,
           java.lang.String subCode,
           java.lang.String message,
           java.lang.String timbre) {
           this.code = code;
           this.subCode = subCode;
           this.message = message;
           this.timbre = timbre;
    }


    /**
     * Gets the code value for this RespuestaTimbre.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this RespuestaTimbre.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the subCode value for this RespuestaTimbre.
     * 
     * @return subCode
     */
    public java.lang.String getSubCode() {
        return subCode;
    }


    /**
     * Sets the subCode value for this RespuestaTimbre.
     * 
     * @param subCode
     */
    public void setSubCode(java.lang.String subCode) {
        this.subCode = subCode;
    }


    /**
     * Gets the message value for this RespuestaTimbre.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this RespuestaTimbre.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the timbre value for this RespuestaTimbre.
     * 
     * @return timbre
     */
    public java.lang.String getTimbre() {
        return timbre;
    }


    /**
     * Sets the timbre value for this RespuestaTimbre.
     * 
     * @param timbre
     */
    public void setTimbre(java.lang.String timbre) {
        this.timbre = timbre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaTimbre)) return false;
        RespuestaTimbre other = (RespuestaTimbre) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.subCode==null && other.getSubCode()==null) || 
             (this.subCode!=null &&
              this.subCode.equals(other.getSubCode()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.timbre==null && other.getTimbre()==null) || 
             (this.timbre!=null &&
              this.timbre.equals(other.getTimbre())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getSubCode() != null) {
            _hashCode += getSubCode().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getTimbre() != null) {
            _hashCode += getTimbre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaTimbre.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:advanswsdl", "RespuestaTimbre"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SubCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timbre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Timbre"));
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
