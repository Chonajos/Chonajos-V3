package com.web.chon.dominio;

import com.web.chon.security.domain.PlataformaUsuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 *
 * @author Juan
 */
public class UsuarioDominio extends ValueObject implements PlataformaUsuario {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String usuId;
    private String usuNombre;
    private String usuPaterno;
    private String usuMaterno;
    private String usuPassword;
    private String nombreSucursal;
    private Date usuFecAlta;
    private int perId;
    private String perDescripcion;
    private int sucId;
    private String telefonoSucursal;
    private BigDecimal idUsuario;
    private Set<Menu> menu = new HashSet<Menu>();
    private Set<String> allowedUrl = new HashSet<String>();

    public Set<String> getAllowedUrl() {
        return allowedUrl;
    }

    public void setAllowedUrl(Set<String> allowedUrl) {
        this.allowedUrl = allowedUrl;
    }

    @Override
    public String toString() {
        return "UsuarioDominio{" + "usuId=" + usuId + ", usuNombre=" + usuNombre + ", usuPaterno=" + usuPaterno + ", usuMaterno=" + usuMaterno + ", usuPassword=" + usuPassword + ", usuFecAlta=" + usuFecAlta + ", perId=" + perId + ", perDescripcion=" + perDescripcion + ", sucId=" + sucId + ", idUsuario=" + idUsuario + ", menu=" + menu + ", allowedUrl=" + allowedUrl + '}';
    }


    @Override
    public void reset() {
        usuId = null;
        usuNombre = null;
        usuPaterno = null;
        usuMaterno = null;
        usuPassword = null;
        usuFecAlta = null;
        perId = 0;
        sucId = 0;
    }

    @Override
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        result.add(new GrantedAuthorityImpl("1"));

        return result;
    }

    @Override
    @Transient
    public String getPassword() {
        return usuPassword;
    }

    @Override
    @Transient
    public String getUsername() {
        return usuId;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    public String getUsuId() {
        return usuId;
    }

    public void setUsuId(String usuId) {
        this.usuId = usuId;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuPaterno() {
        return usuPaterno;
    }

    public void setUsuPaterno(String usuPaterno) {
        this.usuPaterno = usuPaterno;
    }

    public String getUsuMaterno() {
        return usuMaterno;
    }

    public void setUsuMaterno(String usuMaterno) {
        this.usuMaterno = usuMaterno;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public Date getUsuFecAlta() {
        return usuFecAlta;
    }

    public void setUsuFecAlta(Date usuFecAlta) {
        this.usuFecAlta = usuFecAlta;
    }

    public int getPerId() {
        return perId;
    }

    public void setPerId(int perId) {
        this.perId = perId;
    }

    public int getSucId() {
        return sucId;
    }

    public void setSucId(int sucId) {
        this.sucId = sucId;
    }

    @Transient
    public String getNombreCompleto() {
        return usuNombre + " " + usuPaterno + " " + usuMaterno;
    }

    public String getPerDescripcion() {
        return perDescripcion;
    }

    public void setPerDescripcion(String perDescripcion) {
        this.perDescripcion = perDescripcion;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Set<Menu> getMenu() {
        return menu;
    }

    public void setMenu(Set<Menu> menu) {
        this.menu = menu;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(String telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }
    
    
    
}
