package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
import com.web.chon.util.Utilerias;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;

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

    @Autowired
    private IfaceVenta ifaceVenta;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;

    private BeanUsuario beanUsuario;
    private RelacionOperaciones data;
    private ArrayList<RelacionOperaciones> model;
    private ArrayList<BuscaVenta> lstVenta;

    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal totalVenta;

    @PostConstruct
    public void init() {

        model = new ArrayList<RelacionOperaciones>();
        data = new RelacionOperaciones();
        lstVenta = new ArrayList<BuscaVenta>();
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
        viewEstate = "searchById";

    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin());
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
                data.setFechaFiltroInicio(Utilerias.getDayOneOfMonth(new Date()));
                data.setFechaFiltroFin(Utilerias.getDayEndOfMonth(new Date()));
                break;
            case 3:
                data.setFechaFiltroInicio(Utilerias.getDayOneYear(new Date()));
                data.setFechaFiltroFin(Utilerias.getDayEndYear(new Date()));
                break;
            default:
                data.setFechaFiltroInicio(null);
                data.setFechaFiltroFin(null);
                break;
        }

    }

    public void getVentasByIntervalDate() 
    {
        setFechaInicioFin(filtro);
        if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {

            model = ifaceVenta.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin());
            getTotalVentaByInterval();
        } else {
            model = new ArrayList<RelacionOperaciones>();
            getTotalVentaByInterval();
        }

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

}
