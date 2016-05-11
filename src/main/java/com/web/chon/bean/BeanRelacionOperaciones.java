package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceVenta;
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
 * Bean para la Relacion de Operaciones
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRelacionOperaciones implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired private IfaceVenta ifaceVenta;
    @Autowired private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceCatStatusVenta ifaceCatStatusVenta;
    
    private ArrayList<BuscaVenta> lstVenta;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<RelacionOperaciones> model;
    private ArrayList<StatusVenta> listaStatusVenta;
    
    private UsuarioDominio usuario;
    private RelacionOperaciones data;
    
    private String title;
    private String viewEstate;
    
    private int filtro;
    
    private Date fechaFin;
    private Date fechaInicio;
    private BigDecimal totalVenta;

    @PostConstruct
    public void init() {

       
        data = new RelacionOperaciones();
        model = new ArrayList<RelacionOperaciones>();
        
        usuario = context.getUsuarioAutenticado();
        
        /*Validacion de perfil administrador*/
        if(usuario.getPerId() != 1) {
            data.setIdSucursal(usuario.getSucId());
        }

        lstVenta = new ArrayList<BuscaVenta>();
        listaSucursales = new ArrayList<Sucursal>();
        listaStatusVenta = new ArrayList<StatusVenta>();

        listaSucursales = ifaceCatSucursales.getSucursales();
        listaStatusVenta = ifaceCatStatusVenta.getStatusVentas();

        setTitle("Relación de Operaciónes Entrada de Mercancia.");
        setViewEstate("init");

    }

    @Override
    public void searchById() {
        viewEstate = "searchById";

    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus());
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

        model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus());
        getTotalVentaByInterval();
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
        viewEstate = "init";
        lstVenta.clear();
    }

    public void detallesVenta() {
        viewEstate = "searchById";
        lstVenta = ifaceBuscaVenta.getVentaById(data.getIdVentaPk().intValue());
        calculatotalVentaDetalle();

    }

    public void calculatotalVentaDetalle() {
        totalVenta = new BigDecimal(0);

        for (BuscaVenta venta : lstVenta) {
            totalVenta = totalVenta.add(new BigDecimal(venta.getTotal()));
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }
    
    

}
