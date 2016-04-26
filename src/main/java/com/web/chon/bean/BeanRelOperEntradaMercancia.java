package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceProducto;
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
 * Bean para la Relacion de Operaciones para entrada de mercancias
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRelOperEntradaMercancia implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceProducto ifaceProducto;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatStatusVenta ifaceCatStatusVenta;
    private EntradaMercancia2 data;
    private ArrayList<Sucursal> listaSucursales;

    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;

    private ArrayList<EntradaMercancia2> lstEntradaMercancia;
    private ArrayList<EntradaMercanciaProducto> lstEntradaMercanciaProdcuto;

    @PostConstruct
    public void init() {

        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();

        data = new EntradaMercancia2();

        setTitle("Relación de Operaciónes.");
        setViewEstate("init");

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {

        return "relacionOperaciones";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        setViewEstate("searchById");

    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    lstEntradaMercancia = ifaceEntradaMercancia.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFK(), data.getIdProvedorFK());
                } else {

                    lstEntradaMercancia = new ArrayList<EntradaMercancia2>();
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
        if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
            lstEntradaMercancia = ifaceEntradaMercancia.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFK(), data.getIdProvedorFK());
        } else {
            lstEntradaMercancia = new ArrayList<EntradaMercancia2>();
        }

    }

    public void cancel() {
        setViewEstate("init");
        lstEntradaMercanciaProdcuto = new ArrayList<EntradaMercanciaProducto>();
        lstEntradaMercancia = new ArrayList<EntradaMercancia2>();
        data.reset();
        filtro = -1;
        

    }

    public void detallesEntradaProducto() {
        setViewEstate("searchById");
        lstEntradaMercanciaProdcuto = ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(data.getIdEmPK());

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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<EntradaMercanciaProducto> getLstEntradaMercanciaProdcuto() {
        return lstEntradaMercanciaProdcuto;
    }

    public void setLstEntradaMercanciaProdcuto(ArrayList<EntradaMercanciaProducto> lstEntradaMercanciaProdcuto) {
        this.lstEntradaMercanciaProdcuto = lstEntradaMercanciaProdcuto;
    }

    public EntradaMercancia2 getData() {
        return data;
    }

    public void setData(EntradaMercancia2 data) {
        this.data = data;
    }

    public ArrayList<EntradaMercancia2> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia2> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

}
