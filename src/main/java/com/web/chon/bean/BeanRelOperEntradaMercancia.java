package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Sucursal;
import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceSubProducto;
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
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatStatusVenta ifaceCatStatusVenta;

    private BeanUsuario beanUsuario;
    private RelacionOperaciones data;
    private ArrayList<RelacionOperaciones> model;
    private ArrayList<BuscaVenta> lstVenta;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<StatusVenta> listaStatusVenta;

    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal totalVenta;

    private PaginationLazyDataModel entradaMercancia;

    private PaginationLazyDataModel entradaMercanciaProdcuto;

    @PostConstruct
    public void init() {

        entradaMercancia = new PaginationLazyDataModel<EntradaMercancia2, BigDecimal>(ifaceEntradaMercancia, new EntradaMercancia2());
        entradaMercancia = new PaginationLazyDataModel<EntradaMercanciaProducto, BigDecimal>(ifaceEntradaMercanciaProducto, new EntradaMercanciaProducto());
        model = new ArrayList<RelacionOperaciones>();
        data = new RelacionOperaciones();
        lstVenta = new ArrayList<BuscaVenta>();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        
        listaStatusVenta = new ArrayList<StatusVenta>();
        listaStatusVenta = ifaceCatStatusVenta.getStatusVentas();
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
//                    model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus());
                    getTotalVentaByInterval();
                } else {
                    model = new ArrayList<RelacionOperaciones>();
                    getTotalVentaByInterval();
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

    public void getVentasByIntervalDate() {

        setFechaInicioFin(filtro);
        if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
//            model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus());
            getTotalVentaByInterval();
        } else {
            model = new ArrayList<RelacionOperaciones>();
            getTotalVentaByInterval();
        }

    }

    public void printStatus() {
        getVentasByIntervalDate();

    }

    public void getTotalVentaByInterval() {
        totalVenta = new BigDecimal(0);
        for (RelacionOperaciones dominio : model) {
            totalVenta = totalVenta.add(dominio.getTotalVenta());
        }
    }

    public void cancel() {
        setViewEstate("init");
        lstVenta.clear();
    }

    public void detallesVenta() {
        setViewEstate("searchById");
        lstVenta = ifaceBuscaVenta.getVentaById(data.getIdVentaPk().intValue());
        calculatotalVentaDetalle();

    }

    public void calculatotalVentaDetalle() {
        totalVenta = new BigDecimal(0);

        for (BuscaVenta venta : lstVenta) {
            totalVenta = totalVenta.add(new BigDecimal(venta.getTotal()));
        }
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

    public RelacionOperaciones getData() {
        return data;
    }

    public void setData(RelacionOperaciones data) {
        this.data = data;
    }

    public ArrayList<RelacionOperaciones> getModel() {
        return model;
    }

    public void setModel(ArrayList<RelacionOperaciones> model) {
        this.model = model;
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

    public ArrayList<BuscaVenta> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<BuscaVenta> lstVenta) {
        this.lstVenta = lstVenta;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<StatusVenta> getListaStatusVenta() {
        return listaStatusVenta;
    }

    public void setListaStatusVenta(ArrayList<StatusVenta> listaStatusVenta) {
        this.listaStatusVenta = listaStatusVenta;
    }

    public PaginationLazyDataModel getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(PaginationLazyDataModel entradaMercancia) {
        this.entradaMercancia = entradaMercancia;
    }

    public PaginationLazyDataModel getEntradaMercanciaProdcuto() {
        return entradaMercanciaProdcuto;
    }

    public void setEntradaMercanciaProdcuto(PaginationLazyDataModel entradaMercanciaProdcuto) {
        this.entradaMercanciaProdcuto = entradaMercanciaProdcuto;
    }

    
}
