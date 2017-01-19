package com.web.chon.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Juan de la Cruz
 */
public class Usuario {

    private static final long serialVersionUID = 1L;
    private BigDecimal idUsuarioPk;
    private String nombreUsuario;
    private String apaternoUsuario;
    private String amaternoUsuario;
    private String contrasenaUsuario;
    private String confirmaUsuario;
    private Character sexoUsuario;
    private Long telefonoMovilUsuario;
    private Long telefonoFijoUsuario;
    private String idNextelUsuario;
    private String correoUsuario;
    private Short numeroInteriorUsuario;
    private Short numeroExterioUsuario;
    private String referenciaDirecionUsuario;
    private String calleUsuario;
    private String coloniaUsuario;
    private Short diasCreditoUsuario;
    private String rfcUsuario;
    private Long creditoLimiteUsuario;
    private String claveUsuario;
    private String sitioWeb;
    private String razonSocialUsuario;
    private Date fechaAltaUsuario;
    private String latitudUsuario;
    private String longitudUsuario;
    private BigDecimal idRolFk;
    private BigDecimal status;
    private String nombreCompletoUsuario;

    private int idSucursal;

    //Datos para el login y el menu
    private String perDescripcion;
    private Set<Menu> menu = new HashSet<Menu>();
    private Set<String> allowedUrl = new HashSet<String>();
    private String mensaje;

    public Usuario() {
    }

    public Usuario(BigDecimal idUsuarioPk) {
        this.idUsuarioPk = idUsuarioPk;
    }

    public Usuario(BigDecimal idUsuarioPk, String nombreUsuario, String apaternoUsuario) {
        this.idUsuarioPk = idUsuarioPk;
        this.nombreUsuario = nombreUsuario;
        this.apaternoUsuario = apaternoUsuario;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdUsuarioPk() {
        return idUsuarioPk;
    }

    public void setIdUsuarioPk(BigDecimal idUsuarioPk) {
        this.idUsuarioPk = idUsuarioPk;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApaternoUsuario() {
        return apaternoUsuario;
    }

    public void setApaternoUsuario(String apaternoUsuario) {
        this.apaternoUsuario = apaternoUsuario;
    }

    public String getAmaternoUsuario() {
        return amaternoUsuario;
    }

    public void setAmaternoUsuario(String amaternoUsuario) {
        this.amaternoUsuario = amaternoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getConfirmaUsuario() {
        return confirmaUsuario;
    }

    public void setConfirmaUsuario(String confirmaUsuario) {
        this.confirmaUsuario = confirmaUsuario;
    }

    public Character getSexoUsuario() {
        return sexoUsuario;
    }

    public void setSexoUsuario(Character sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    public Long getTelefonoMovilUsuario() {
        return telefonoMovilUsuario;
    }

    public void setTelefonoMovilUsuario(Long telefonoMovilUsuario) {
        this.telefonoMovilUsuario = telefonoMovilUsuario;
    }

    public Long getTelefonoFijoUsuario() {
        return telefonoFijoUsuario;
    }

    public void setTelefonoFijoUsuario(Long telefonoFijoUsuario) {
        this.telefonoFijoUsuario = telefonoFijoUsuario;
    }

    public String getIdNextelUsuario() {
        return idNextelUsuario;
    }

    public void setIdNextelUsuario(String idNextelUsuario) {
        this.idNextelUsuario = idNextelUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public Short getNumeroInteriorUsuario() {
        return numeroInteriorUsuario;
    }

    public void setNumeroInteriorUsuario(Short numeroInteriorUsuario) {
        this.numeroInteriorUsuario = numeroInteriorUsuario;
    }

    public Short getNumeroExterioUsuario() {
        return numeroExterioUsuario;
    }

    public void setNumeroExterioUsuario(Short numeroExterioUsuario) {
        this.numeroExterioUsuario = numeroExterioUsuario;
    }

    public String getReferenciaDirecionUsuario() {
        return referenciaDirecionUsuario;
    }

    public void setReferenciaDirecionUsuario(String referenciaDirecionUsuario) {
        this.referenciaDirecionUsuario = referenciaDirecionUsuario;
    }

    public String getCalleUsuario() {
        return calleUsuario;
    }

    public void setCalleUsuario(String calleUsuario) {
        this.calleUsuario = calleUsuario;
    }

    public String getColoniaUsuario() {
        return coloniaUsuario;
    }

    public void setColoniaUsuario(String coloniaUsuario) {
        this.coloniaUsuario = coloniaUsuario;
    }

    public Short getDiasCreditoUsuario() {
        return diasCreditoUsuario;
    }

    public void setDiasCreditoUsuario(Short diasCreditoUsuario) {
        this.diasCreditoUsuario = diasCreditoUsuario;
    }

    public String getRfcUsuario() {
        return rfcUsuario;
    }

    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    public Long getCreditoLimiteUsuario() {
        return creditoLimiteUsuario;
    }

    public void setCreditoLimiteUsuario(Long creditoLimiteUsuario) {
        this.creditoLimiteUsuario = creditoLimiteUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getRazonSocialUsuario() {
        return razonSocialUsuario;
    }

    public void setRazonSocialUsuario(String razonSocialUsuario) {
        this.razonSocialUsuario = razonSocialUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public String getLatitudUsuario() {
        return latitudUsuario;
    }

    public void setLatitudUsuario(String latitudUsuario) {
        this.latitudUsuario = latitudUsuario;
    }

    public String getLongitudUsuario() {
        return longitudUsuario;
    }

    public void setLongitudUsuario(String longitudUsuario) {
        this.longitudUsuario = longitudUsuario;
    }

    public BigDecimal getIdRolFk() {
        return idRolFk;
    }

    public void setIdRolFk(BigDecimal idRolFk) {
        this.idRolFk = idRolFk;
    }

    public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
        this.nombreCompletoUsuario = nombreCompletoUsuario;
    }

    public String getNombreCompletoUsuario() {
        if (nombreCompletoUsuario == null || nombreCompletoUsuario.trim().equals("")) {
            nombreUsuario = nombreUsuario == null ? "" : nombreUsuario;
            apaternoUsuario = apaternoUsuario == null ? "" : apaternoUsuario;
            amaternoUsuario = amaternoUsuario == null ? "" : amaternoUsuario;
            nombreCompletoUsuario = nombreUsuario + " " + apaternoUsuario + " " + amaternoUsuario;
            
            return nombreCompletoUsuario.trim();
        } else {
            return nombreCompletoUsuario.trim();
        }
    }

    public Set<Menu> getMenu() {
        return menu;
    }

    public void setMenu(Set<Menu> menu) {
        this.menu = menu;
    }

    public Set<String> getAllowedUrl() {
        return allowedUrl;
    }

    public void setAllowedUrl(Set<String> allowedUrl) {
        this.allowedUrl = allowedUrl;
    }

    public String getPerDescripcion() {
        return perDescripcion;
    }

    public void setPerDescripcion(String perDescripcion) {
        this.perDescripcion = perDescripcion;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuarioPk=" + idUsuarioPk + ", nombreUsuario=" + nombreUsuario + ", apaternoUsuario=" + apaternoUsuario + ", amaternoUsuario=" + amaternoUsuario + ", contrasenaUsuario=" + contrasenaUsuario + ", confirmaUsuario=" + confirmaUsuario + ", sexoUsuario=" + sexoUsuario + ", telefonoMovilUsuario=" + telefonoMovilUsuario + ", telefonoFijoUsuario=" + telefonoFijoUsuario + ", idNextelUsuario=" + idNextelUsuario + ", correoUsuario=" + correoUsuario + ", numeroInteriorUsuario=" + numeroInteriorUsuario + ", numeroExterioUsuario=" + numeroExterioUsuario + ", referenciaDirecionUsuario=" + referenciaDirecionUsuario + ", calleUsuario=" + calleUsuario + ", coloniaUsuario=" + coloniaUsuario + ", diasCreditoUsuario=" + diasCreditoUsuario + ", rfcUsuario=" + rfcUsuario + ", creditoLimiteUsuario=" + creditoLimiteUsuario + ", claveUsuario=" + claveUsuario + ", sitioWeb=" + sitioWeb + ", razonSocialUsuario=" + razonSocialUsuario + ", fechaAltaUsuario=" + fechaAltaUsuario + ", latitudUsuario=" + latitudUsuario + ", longitudUsuario=" + longitudUsuario + ", idRolFk=" + idRolFk + ", status=" + status + ", nombreCompletoUsuario=" + nombreCompletoUsuario + ", idSucursal=" + idSucursal + ", perDescripcion=" + perDescripcion + ", menu=" + menu + ", allowedUrl=" + allowedUrl + '}';
    }

}
