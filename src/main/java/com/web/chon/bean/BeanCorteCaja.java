/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceVenta;
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanCorteCaja {
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired private IfaceVenta ifaceVenta;
    
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    
    private String title;
    private String viewEstate;
    
    private UsuarioDominio usuario;
    private CorteCaja data;
    
    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private Date fecha;
    
    
    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data= new CorteCaja();
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()), new BigDecimal(1));
        setTitle("Corte de Caja");
        setViewEstate("init");
        fecha = new Date();
        
    }
    public void generarCorte()
    {
        TiempoUtil.getFechaDDMMYYYY(fecha);
        BigDecimal totalVentasMenudeo =  ifaceVenta.getTotalVentasByDay(TiempoUtil.getFechaDDMMYYYY(fecha));
        System.out.println("Total Menudeo: "+totalVentasMenudeo);
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
    }

    
    
    public String getViewEstate() {
        return viewEstate;
    }

    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getIdSucursalBean() {
        return idSucursalBean;
    }

    public void setIdSucursalBean(BigDecimal idSucursalBean) {
        this.idSucursalBean = idSucursalBean;
    }

    public BigDecimal getIdCajaBean() {
        return idCajaBean;
    }

    public void setIdCajaBean(BigDecimal idCajaBean) {
        this.idCajaBean = idCajaBean;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CorteCaja getData() {
        return data;
    }

    public void setData(CorteCaja data) {
        this.data = data;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }
    
    
    
    
    
}
