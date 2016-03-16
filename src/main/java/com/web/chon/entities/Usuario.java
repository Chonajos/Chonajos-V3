/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "USUARIO", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findByClaveUsuario", query = "SELECT u FROM Usuario u WHERE u.claveUsuario = :claveUsuario"),
    @NamedQuery(name = "Usuario.findByContrasenaUsuario", query = "SELECT u FROM Usuario u WHERE u.contrasenaUsuario = :contrasenaUsuario"),
    @NamedQuery(name = "Usuario.findByClaveUsuarioAndContrasenaUsuario", query = "SELECT u FROM Usuario u WHERE u.claveUsuario = :claveUsuario and u.contrasenaUsuario = :contrasenaUsuario")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO_PK")
    private BigDecimal idUsuarioPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "APATERNO_USUARIO")
    private String apaternoUsuario;
    @Size(max = 120)
    @Column(name = "AMATERNO_USUARIO")
    private String amaternoUsuario;
    @Size(max = 255)
    @Column(name = "CONTRASENA_USUARIO")
    private String contrasenaUsuario;
    @Column(name = "SEXO_USUARIO")
    private Character sexoUsuario;
    @Column(name = "TELEFONO_MOVIL_USUARIO")
    private Long telefonoMovilUsuario;
    @Column(name = "TELEFONO_FIJO_USUARIO")
    private Long telefonoFijoUsuario;
    @Size(max = 15)
    @Column(name = "ID_NEXTEL_USUARIO")
    private String idNextelUsuario;
    @Size(max = 255)
    @Column(name = "CORREO_USUARIO")
    private String correoUsuario;
    @Column(name = "NUMERO_INTERIOR_USUARIO")
    private Short numeroInteriorUsuario;
    @Column(name = "NUMERO_EXTERIO_USUARIO")
    private Short numeroExterioUsuario;
    @Size(max = 255)
    @Column(name = "REFERENCIA_DIRECION_USUARIO")
    private String referenciaDirecionUsuario;
    @Size(max = 255)
    @Column(name = "CALLE_USUARIO")
    private String calleUsuario;
    @Size(max = 255)
    @Column(name = "COLONIA_USUARIO")
    private String coloniaUsuario;
    @Column(name = "DIAS_CREDITO_USUARIO")
    private Short diasCreditoUsuario;
    @Size(max = 13)
    @Column(name = "RFC_USUARIO")
    private String rfcUsuario;
    @Column(name = "CREDITO_LIMITE_USUARIO")
    private Long creditoLimiteUsuario;
    @Size(max = 10)
    @Column(name = "CLAVE_USUARIO")
    private String claveUsuario;
    @Size(max = 120)
    @Column(name = "SITIO_WEB")
    private String sitioWeb;
    @Size(max = 100)
    @Column(name = "RAZON_SOCIAL_USUARIO")
    private String razonSocialUsuario;
    @Column(name = "FECHA_ALTA_USUARIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAltaUsuario;
    @Size(max = 50)
    @Column(name = "LATITUD_USUARIO")
    private String latitudUsuario;
    @Size(max = 50)
    @Column(name = "LONGITUD_USUARIO")
    private String longitudUsuario;
    @JoinColumn(name = "ID_MUNICIPIO_FK", referencedColumnName = "ID_MUNICIPIO_PK")
    @ManyToOne
    private Municipios idMunicipioFk;
    @JoinColumn(name = "ID_ROL_FK", referencedColumnName = "ID_ROL_PK")
    @ManyToOne
    private Rol idRolFk;
    @OneToMany(mappedBy = "idClienteFk")
    private List<Venta> ventaList;
    @OneToMany(mappedBy = "idVendedorFk")
    private List<Venta> ventaList1;
    @OneToMany(mappedBy = "idUsuarioFk")
    private List<Camion> camionList;
    @OneToMany(mappedBy = "idUsuarioFk")
    private List<CuentaBancaria> cuentaBancariaList;
    @OneToMany(mappedBy = "idUsuarioSucursalFk")
    private List<Sucursal> sucursalList;

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

    public Municipios getIdMunicipioFk() {
        return idMunicipioFk;
    }

    public void setIdMunicipioFk(Municipios idMunicipioFk) {
        this.idMunicipioFk = idMunicipioFk;
    }

    public Rol getIdRolFk() {
        return idRolFk;
    }

    public void setIdRolFk(Rol idRolFk) {
        this.idRolFk = idRolFk;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @XmlTransient
    public List<Venta> getVentaList1() {
        return ventaList1;
    }

    public void setVentaList1(List<Venta> ventaList1) {
        this.ventaList1 = ventaList1;
    }

    @XmlTransient
    public List<Camion> getCamionList() {
        return camionList;
    }

    public void setCamionList(List<Camion> camionList) {
        this.camionList = camionList;
    }

    @XmlTransient
    public List<CuentaBancaria> getCuentaBancariaList() {
        return cuentaBancariaList;
    }

    public void setCuentaBancariaList(List<CuentaBancaria> cuentaBancariaList) {
        this.cuentaBancariaList = cuentaBancariaList;
    }

    @XmlTransient
    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {
        this.sucursalList = sucursalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioPk != null ? idUsuarioPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuarioPk == null && other.idUsuarioPk != null) || (this.idUsuarioPk != null && !this.idUsuarioPk.equals(other.idUsuarioPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Usuario[ idUsuarioPk=" + idUsuarioPk + " ]";
    }
    
}
