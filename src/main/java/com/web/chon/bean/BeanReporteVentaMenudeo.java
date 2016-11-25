package com.web.chon.bean;

import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceVentaProducto;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el reporte de ventas de menudeo
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanReporteVentaMenudeo implements Serializable, BeanSimple {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;
    
    private ArrayList<Sucursal> lstSucursales;
    private ArrayList<VentaProducto> model;
    
    private UsuarioDominio usuario;
    
    private String title;
    private String viewEstate;
    private String strFechaInicio;
    private String strFechaFin;
    
    private BigDecimal totalVenta;
    private BigDecimal totalCosto;
    private BigDecimal comisionExistencia;
    private BigDecimal ZERO = new BigDecimal(0);

    //-- Variables del bean para realizar la consulta---//
    private Date fechaFin;
    private Date fechaInicio;
    private BigDecimal idSucursal;
    
    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        /*Validacion de perfil administrador*/
        idSucursal = new BigDecimal(usuario.getSucId());
        lstSucursales = ifaceCatSucursales.getSucursales();
        totalVenta = new BigDecimal(0);
        
        searchById();
        
        setTitle("Reporte Ventas Menudeo");
        setViewEstate("init");
        
    }
    
    @Override
    public void searchById() {
        
        if (fechaInicio != null && fechaFin != null) {
            strFechaInicio = TiempoUtil.getFechaDDMMYYYY(fechaInicio);
            strFechaFin = TiempoUtil.getFechaDDMMYYYY(fechaFin);
        } else {
            strFechaInicio = null;
            strFechaFin = null;
        }
        
        model = ifaceVentaProducto.getReporteVenta(strFechaInicio, strFechaFin, idSucursal);
        getTotales();
        
    }
    
    private void getTotales() {
        
        totalCosto = ZERO;
        totalVenta = ZERO;
        comisionExistencia = ZERO;
        BigDecimal totalExistencia = ZERO;
        
        for (VentaProducto dominio : model) {
            totalCosto = totalCosto.add(dominio.getCostoMerma().multiply(dominio.getEntrada()));
            totalVenta = totalVenta.add(dominio.getTotal());   
            comisionExistencia = comisionExistencia.add((dominio.getTotal()).subtract(dominio.getCostoMerma().multiply(dominio.getEntrada()))).add(dominio.getExistencia().multiply(dominio.getPrecioProducto()));
        }
        
    }
    
    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String insert() {
        
        return null;
    }
    
    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getViewEstate() {
        return viewEstate;
    }
    
    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
    
    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    public ArrayList<Sucursal> getLstSucursales() {
        return lstSucursales;
    }
    
    public void setLstSucursales(ArrayList<Sucursal> lstSucursales) {
        this.lstSucursales = lstSucursales;
    }
    
    public ArrayList<VentaProducto> getModel() {
        return model;
    }
    
    public void setModel(ArrayList<VentaProducto> model) {
        this.model = model;
    }
    
    public UsuarioDominio getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }
    
    public BigDecimal getIdSucursal() {
        return idSucursal;
    }
    
    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    public BigDecimal getTotalCosto() {
        return totalCosto;
    }
    
    public void setTotalCosto(BigDecimal totalCosto) {
        this.totalCosto = totalCosto;
    }
    
    public BigDecimal getComisionExistencia() {
        return comisionExistencia;
    }
    
    public void setComisionExistencia(BigDecimal comisionExistencia) {
        this.comisionExistencia = comisionExistencia;
    }
    
}
