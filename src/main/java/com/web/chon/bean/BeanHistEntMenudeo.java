/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMenudeo;
import com.web.chon.service.IfaceEntradaMenudeoProducto;
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
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanHistEntMenudeo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceEntradaMenudeo ifaceEntradaMenudeo;
    @Autowired private IfaceEntradaMenudeoProducto ifaceEntradaMenudeoProducto;
    
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<EntradaMenudeo> lstEntradaMercancia;
    private ArrayList<EntradaMenudeoProducto> lstEntradaMercanciaProdcuto;
    
    private UsuarioDominio usuario;
    private EntradaMenudeo data;
    
    
    private String title;
    private String viewEstate;
    
    private Date fechaFin;
    private Date fechaInicio;
    
    private int filtro;
    
    private BigDecimal totalKilosDetalle;
    
    @PostConstruct
    public void init() 
    {
        data= new EntradaMenudeo();
        usuario = context.getUsuarioAutenticado();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        filtro =  2;
        data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));
        
        data.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk());
        setTitle("Historial Entrada de Mercancia de Menudeo");
        setViewEstate("init");
    }
    public void back()
    {
        setViewEstate("init");
        lstEntradaMercanciaProdcuto = new ArrayList<EntradaMenudeoProducto>();
        
        //data.reset();
//        fechaInicio = null;
//        fechaFin = null;
//        getEntradaProductoByIntervalDate();
        
    }
    
    public void detallesEntradaProducto() {
        setViewEstate("searchById");

        lstEntradaMercanciaProdcuto = ifaceEntradaMenudeoProducto.getEntradaProductoById(data.getIdEmmPk());
         getTotalKilosProducto();
    }
    
    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk());
                } else {
                    lstEntradaMercancia = new ArrayList<EntradaMenudeo>();
                }
                break;
            case 1:
                data.setFechaFiltroInicio(new Date());
                data.setFechaFiltroFin(new Date());
                break;

            case 2:
                data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
                data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));

                break;
            case 3:
                data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
                data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
                break;
            default:
                data.setFechaFiltroInicio(null);
                data.setFechaFiltroFin(null);
                break;
        }

    }
    
    public void getEntradaProductoByIntervalDate() {
    setFechaInicioFin(filtro);
        System.out.println("Sucursal: "+data.getIdSucursalFk());
    lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk());
   
    }
    public void getTotalKilosProducto() {
        totalKilosDetalle = new BigDecimal(0);
        for (EntradaMenudeoProducto dominio : lstEntradaMercanciaProdcuto) {
            totalKilosDetalle = totalKilosDetalle.add(dominio.getKilosTotales());
        }

    }


    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public EntradaMenudeo getData() {
        return data;
    }

    public void setData(EntradaMenudeo data) {
        this.data = data;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public ArrayList<EntradaMenudeo> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMenudeo> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public ArrayList<EntradaMenudeoProducto> getLstEntradaMercanciaProdcuto() {
        return lstEntradaMercanciaProdcuto;
    }

    public void setLstEntradaMercanciaProdcuto(ArrayList<EntradaMenudeoProducto> lstEntradaMercanciaProdcuto) {
        this.lstEntradaMercanciaProdcuto = lstEntradaMercanciaProdcuto;
    }

    public BigDecimal getTotalKilosDetalle() {
        return totalKilosDetalle;
    }

    public void setTotalKilosDetalle(BigDecimal totalKilosDetalle) {
        this.totalKilosDetalle = totalKilosDetalle;
    }
    
    
}
