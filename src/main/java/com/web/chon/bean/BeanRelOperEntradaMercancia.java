package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Sucursal;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceProducto;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    @Autowired private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceCatProvedores ifaceCatProvedores;
    
    private ArrayList<Provedor> lstProvedor;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<EntradaMercancia2> lstEntradaMercancia;
    private ArrayList<EntradaMercanciaProducto> lstEntradaMercanciaProdcuto;
    
    private Provedor provedor;
    private EntradaMercancia2 data;

    private String title;
    private String viewEstate;
    
    private BigDecimal totalKilos;
    
    private Date fechaFin;
    private Date fechaInicio;
    
    private int filtro;
    
    @PostConstruct
    public void init() {

        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();

        data = new EntradaMercancia2();

        provedor = new Provedor();

        getEntradaProductoByIntervalDate();

        setTitle("Relación de Operaciónes.");
        setViewEstate("init");

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
        BigDecimal idProvedor = provedor == null ? null : provedor.getIdProvedorPK();

        lstEntradaMercancia = ifaceEntradaMercancia.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFK(), idProvedor);


    }

    public void cancel() {
        setViewEstate("init");
        lstEntradaMercanciaProdcuto = new ArrayList<EntradaMercanciaProducto>();
        lstEntradaMercancia = new ArrayList<EntradaMercancia2>();
        data.reset();
        provedor = null;
        filtro = -1;
        fechaInicio = null;
        fechaFin = null;
        getEntradaProductoByIntervalDate();

    }

    public void detallesEntradaProducto() {
        setViewEstate("searchById");

        lstEntradaMercanciaProdcuto = ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(data.getIdEmPK());
        getTotalKilosProducto();
    }

    public void getTotalKilosProducto() {
        totalKilos = new BigDecimal(0);
        for (EntradaMercanciaProducto dominio : lstEntradaMercanciaProdcuto) {
            totalKilos = totalKilos.add(dominio.getKilosTotalesProducto());
        }

    }

    public ArrayList<Provedor> autoCompleteProvedor(String nombreProvedor) {
        lstProvedor = ifaceCatProvedores.getProvedorByNombreCompleto(nombreProvedor.toUpperCase());
        return lstProvedor;

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String insert() {

        return "relacionOperaciones";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void searchById() {
        setViewEstate("searchById");

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

    public Provedor getProvedor() {
        return provedor;
    }

    public void setProvedor(Provedor provedor) {
        this.provedor = provedor;
    }

    public ArrayList<Provedor> getLstProvedor() {
        return lstProvedor;
    }

    public void setLstProvedor(ArrayList<Provedor> lstProvedor) {
        this.lstProvedor = lstProvedor;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

}
