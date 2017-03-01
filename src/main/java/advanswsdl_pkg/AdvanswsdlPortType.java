/**
 * AdvanswsdlPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package advanswsdl_pkg;

public interface AdvanswsdlPortType extends java.rmi.Remote {

    /**
     * Método para timbrado de CFDI's
     */
    public advanswsdl_pkg.RespuestaTimbre timbrar(java.lang.String credential, java.lang.String cfdi) throws java.rmi.RemoteException;

    /**
     * Método para timbrado de comprobantes de retención
     */
    public advanswsdl_pkg.RespuestaTimbre timbrarRetencion(java.lang.String credential, java.lang.String xml) throws java.rmi.RemoteException;

    /**
     * Método alternativo para timbrado de CFDI's en el que regresa
     * el CFDI completo en vez de solo el timbre.
     */
    public advanswsdl_pkg.RespuestaTimbre2 timbrar2(java.lang.String credential, java.lang.String cfdi) throws java.rmi.RemoteException;

    /**
     * Método alternativo para timbrado de comprobantes de retención
     * en el que regresa el documento completo en vez de solo el timbre.
     */
    public advanswsdl_pkg.RespuestaTimbre2 timbrarRetencion2(java.lang.String credential, java.lang.String xml) throws java.rmi.RemoteException;

    /**
     * Método alternativo para timbrado de CFDI's con restricción
     * de hora.
     */
    public advanswsdl_pkg.RespuestaTimbre timbrar3(java.lang.String credential, java.lang.String cfdi) throws java.rmi.RemoteException;

    /**
     * Método para cancelación de CFDI's
     */
    public advanswsdl_pkg.RespuestaCancelacion cancelar(java.lang.String credential, java.lang.String rfc_emisor, java.lang.String uuid, java.lang.String key, java.lang.String cer) throws java.rmi.RemoteException;

    /**
     * Método para cancelación de comprobantes de retención
     */
    public advanswsdl_pkg.RespuestaCancelacion cancelarRetencion(java.lang.String credential, java.lang.String rfc_emisor, java.lang.String uuid, java.lang.String key, java.lang.String cer) throws java.rmi.RemoteException;

    /**
     * Método para cancelación de CFDI's
     */
    public advanswsdl_pkg.RespuestaCancelacion cancelarPFXSync(java.lang.String credential, java.lang.String rfc_emisor, java.lang.String uuid, java.lang.String pfx, java.lang.String pfx_password) throws java.rmi.RemoteException;

    /**
     * Método para consultar estado de los timbres
     */
    public advanswsdl_pkg.RespuestaConsulta consultar(java.lang.String credential, java.lang.String uuid) throws java.rmi.RemoteException;

    /**
     * Método para consultar estado de los timbres (Responde CFDI
     * completo)
     */
    public advanswsdl_pkg.RespuestaConsulta consultar_cfdi(java.lang.String credential, java.lang.String uuid) throws java.rmi.RemoteException;

    /**
     * Método para consultar la hora del sistema
     */
    public advanswsdl_pkg.RespuestaConsultaHora consultar_hora() throws java.rmi.RemoteException;

    /**
     * Método para validar si un certificado es válido al momento
     * de timbrar
     */
    public advanswsdl_pkg.RespuestaValidarCertificado validar_certificado(java.lang.String credential, java.lang.String cer) throws java.rmi.RemoteException;
}
