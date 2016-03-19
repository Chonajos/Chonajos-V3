/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApellidoPaterno", query = "SELECT c FROM Cliente c WHERE c.apellidoPaterno = :apellidoPaterno"),
    @NamedQuery(name = "Cliente.findByApellidoMaterno", query = "SELECT c FROM Cliente c WHERE c.apellidoMaterno = :apellidoMaterno"),
    @NamedQuery(name = "Cliente.findByEmpresa", query = "SELECT c FROM Cliente c WHERE c.empresa = :empresa"),
    @NamedQuery(name = "Cliente.findByCalle", query = "SELECT c FROM Cliente c WHERE c.calle = :calle"),
    @NamedQuery(name = "Cliente.findByCp", query = "SELECT c FROM Cliente c WHERE c.cp = :cp"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado"),
    @NamedQuery(name = "Cliente.findBySexo", query = "SELECT c FROM Cliente c WHERE c.sexo = :sexo"),
    @NamedQuery(name = "Cliente.findByFechaNacimiento", query = "SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Cliente.findByTelefonoMovil", query = "SELECT c FROM Cliente c WHERE c.telefonoMovil = :telefonoMovil"),
    @NamedQuery(name = "Cliente.findByTelefonoFijo", query = "SELECT c FROM Cliente c WHERE c.telefonoFijo = :telefonoFijo"),
    @NamedQuery(name = "Cliente.findByExtension", query = "SELECT c FROM Cliente c WHERE c.extension = :extension"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByNumInt", query = "SELECT c FROM Cliente c WHERE c.numInt = :numInt"),
    @NamedQuery(name = "Cliente.findByNumExt", query = "SELECT c FROM Cliente c WHERE c.numExt = :numExt"),
    @NamedQuery(name = "Cliente.findByColonia", query = "SELECT c FROM Cliente c WHERE c.colonia = :colonia"),
    @NamedQuery(name = "Cliente.findByClaveTel", query = "SELECT c FROM Cliente c WHERE c.claveTel = :claveTel"),
    @NamedQuery(name = "Cliente.findByLada", query = "SELECT c FROM Cliente c WHERE c.lada = :lada"),
    @NamedQuery(name = "Cliente.findByDelMun", query = "SELECT c FROM Cliente c WHERE c.delMun = :delMun"),
    @NamedQuery(name = "Cliente.findByCallefiscal", query = "SELECT c FROM Cliente c WHERE c.callefiscal = :callefiscal"),
    @NamedQuery(name = "Cliente.findByNumintfis", query = "SELECT c FROM Cliente c WHERE c.numintfis = :numintfis"),
    @NamedQuery(name = "Cliente.findByNumextfis", query = "SELECT c FROM Cliente c WHERE c.numextfis = :numextfis"),
    @NamedQuery(name = "Cliente.findByColoniafiscal", query = "SELECT c FROM Cliente c WHERE c.coloniafiscal = :coloniafiscal"),
    @NamedQuery(name = "Cliente.findByEstadofiscal", query = "SELECT c FROM Cliente c WHERE c.estadofiscal = :estadofiscal"),
    @NamedQuery(name = "Cliente.findByDelMunFiscal", query = "SELECT c FROM Cliente c WHERE c.delMunFiscal = :delMunFiscal"),
    @NamedQuery(name = "Cliente.findByNextel", query = "SELECT c FROM Cliente c WHERE c.nextel = :nextel"),
    @NamedQuery(name = "Cliente.findByRazon", query = "SELECT c FROM Cliente c WHERE c.razon = :razon"),
    @NamedQuery(name = "Cliente.findByRfc", query = "SELECT c FROM Cliente c WHERE c.rfc = :rfc"),
    @NamedQuery(name = "Cliente.findByCpfiscal", query = "SELECT c FROM Cliente c WHERE c.cpfiscal = :cpfiscal")})
public class Cliente implements Serializable {

    @Column(name = "CLAVECELULAR")
    private BigInteger clavecelular;
    @Column(name = "LADACELULAR")
    private BigInteger ladacelular;
    @Size(max = 20)
    @Column(name = "LADAOFICINA")
    private String ladaoficina;
    @Size(max = 20)
    @Column(name = "CLAVEOFICINA")
    private String claveoficina;
    @Size(max = 20)
    @Column(name = "NEXTELCLAVE")
    private String nextelclave;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CLIENTE")
    private BigDecimal idCliente;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 50)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Size(max = 20)
    @Column(name = "EMPRESA")
    private String empresa;
    @Size(max = 60)
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "CP")
    private Integer cp;
    @Size(max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "SEXO")
    private Character sexo;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "TELEFONO_MOVIL")
    private BigInteger telefonoMovil;
    @Column(name = "TELEFONO_FIJO")
    private BigInteger telefonoFijo;
    @Column(name = "EXTENSION")
    private BigInteger extension;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Correo electrónico no válido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NUM_INT")
    private BigInteger numInt;
    @Column(name = "NUM_EXT")
    private BigInteger numExt;
    @Size(max = 50)
    @Column(name = "COLONIA")
    private String colonia;
    @Column(name = "CLAVE_TEL")
    private BigInteger claveTel;
    @Column(name = "LADA")
    private BigInteger lada;
    @Size(max = 50)
    @Column(name = "DEL_MUN")
    private String delMun;
    @Size(max = 50)
    @Column(name = "CALLEFISCAL")
    private String callefiscal;
    @Column(name = "NUMINTFIS")
    private BigInteger numintfis;
    @Column(name = "NUMEXTFIS")
    private BigInteger numextfis;
    @Size(max = 50)
    @Column(name = "COLONIAFISCAL")
    private String coloniafiscal;
    @Size(max = 50)
    @Column(name = "ESTADOFISCAL")
    private String estadofiscal;
    @Size(max = 50)
    @Column(name = "DEL_MUN_FISCAL")
    private String delMunFiscal;
    @Column(name = "NEXTEL")
    private BigInteger nextel;
    @Size(max = 100)
    @Column(name = "RAZON")
    private String razon;
    @Size(max = 20)
    @Column(name = "RFC")
    private String rfc;
    @Size(max = 20)
    @Column(name = "CPFISCAL")
    private String cpfiscal;

    public Cliente() {
    }

    public Cliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public BigInteger getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(BigInteger telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public BigInteger getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(BigInteger telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public BigInteger getExtension() {
        return extension;
    }

    public void setExtension(BigInteger extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getNumInt() {
        return numInt;
    }

    public void setNumInt(BigInteger numInt) {
        this.numInt = numInt;
    }

    public BigInteger getNumExt() {
        return numExt;
    }

    public void setNumExt(BigInteger numExt) {
        this.numExt = numExt;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public BigInteger getClaveTel() {
        return claveTel;
    }

    public void setClaveTel(BigInteger claveTel) {
        this.claveTel = claveTel;
    }

    public BigInteger getLada() {
        return lada;
    }

    public void setLada(BigInteger lada) {
        this.lada = lada;
    }

    public String getDelMun() {
        return delMun;
    }

    public void setDelMun(String delMun) {
        this.delMun = delMun;
    }

    public String getCallefiscal() {
        return callefiscal;
    }

    public void setCallefiscal(String callefiscal) {
        this.callefiscal = callefiscal;
    }

    public BigInteger getNumintfis() {
        return numintfis;
    }

    public void setNumintfis(BigInteger numintfis) {
        this.numintfis = numintfis;
    }

    public BigInteger getNumextfis() {
        return numextfis;
    }

    public void setNumextfis(BigInteger numextfis) {
        this.numextfis = numextfis;
    }

    public String getColoniafiscal() {
        return coloniafiscal;
    }

    public void setColoniafiscal(String coloniafiscal) {
        this.coloniafiscal = coloniafiscal;
    }

    public String getEstadofiscal() {
        return estadofiscal;
    }

    public void setEstadofiscal(String estadofiscal) {
        this.estadofiscal = estadofiscal;
    }

    public String getDelMunFiscal() {
        return delMunFiscal;
    }

    public void setDelMunFiscal(String delMunFiscal) {
        this.delMunFiscal = delMunFiscal;
    }

    public BigInteger getNextel() {
        return nextel;
    }

    public void setNextel(BigInteger nextel) {
        this.nextel = nextel;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCpfiscal() {
        return cpfiscal;
    }

    public void setCpfiscal(String cpfiscal) {
        this.cpfiscal = cpfiscal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Cliente[ idCliente=" + idCliente + " ]";
    }

    public BigInteger getClavecelular() {
        return clavecelular;
    }

    public void setClavecelular(BigInteger clavecelular) {
        this.clavecelular = clavecelular;
    }

    public BigInteger getLadacelular() {
        return ladacelular;
    }

    public void setLadacelular(BigInteger ladacelular) {
        this.ladacelular = ladacelular;
    }

    public String getLadaoficina() {
        return ladaoficina;
    }

    public void setLadaoficina(String ladaoficina) {
        this.ladaoficina = ladaoficina;
    }

    public String getClaveoficina() {
        return claveoficina;
    }

    public void setClaveoficina(String claveoficina) {
        this.claveoficina = claveoficina;
    }

    public String getNextelclave() {
        return nextelclave;
    }

    public void setNextelclave(String nextelclave) {
        this.nextelclave = nextelclave;
    }
    
}
