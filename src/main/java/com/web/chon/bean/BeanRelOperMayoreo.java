/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.RelacionOperacionesMayoreo;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class BeanRelOperMayoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatStatusVenta ifaceCatStatusVenta;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private IfaceTipoVenta ifaceTipoVenta;
    @Autowired
    private PlataformaSecurityContext context;
    private UsuarioDominio usuario;
    private ArrayList<BuscaVenta> lstVenta;
    private ArrayList<TipoVenta> lstTipoVenta;
    private RelacionOperacionesMayoreo data;
    private ArrayList<RelacionOperacionesMayoreo> model;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<StatusVenta> listaStatusVenta;
    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal totalVenta;
    private BigDecimal totalUtilidad;
    private BigDecimal totalVentaDetalle;
    private BigDecimal porcentajeUtilidad;

    @PostConstruct
    public void init() {
        data = new RelacionOperacionesMayoreo();
        usuario = context.getUsuarioAutenticado();
        filtro = 1;

        data.setIdSucursal(new BigDecimal(usuario.getSucId()));
        model = new ArrayList<RelacionOperacionesMayoreo>();

        listaSucursales = new ArrayList<Sucursal>();
        listaStatusVenta = new ArrayList<StatusVenta>();
        lstTipoVenta = ifaceTipoVenta.getAll();

        listaSucursales = ifaceCatSucursales.getSucursales();
        listaStatusVenta = ifaceCatStatusVenta.getStatusVentas();

        setTitle("Relación de Operaciónes Venta Mayoreo");
        setViewEstate("init");
        getVentasByIntervalDate();

    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    model = ifaceVentaMayoreo.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus(), data.getIdTipoVenta());
                    getTotalVentaByInterval();
                } else {
                    model = new ArrayList<RelacionOperacionesMayoreo>();
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

    public void printStatus() {
        getVentasByIntervalDate();

    }

    public void getVentasByIntervalDate() {

        setFechaInicioFin(filtro);

        model = ifaceVentaMayoreo.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus(), data.getIdTipoVenta());
        getTotalVentaByInterval();
    }

    public void getTotalVentaByInterval() {
        totalVenta = new BigDecimal(0);
        totalUtilidad = new BigDecimal(0);
        porcentajeUtilidad = new BigDecimal(0);
        BigDecimal cien = new BigDecimal(100);
        for (RelacionOperacionesMayoreo dominio : model) {
            totalVenta = totalVenta.add(dominio.getTotalVenta());
            totalUtilidad = totalUtilidad.add(dominio.getGanciaVenta());
        }

        if (totalVenta.intValue() != 0) {
            porcentajeUtilidad = (totalUtilidad.multiply(cien)).divide(totalVenta, 2, RoundingMode.UP);
        }
    }

    public void cancel() {
        viewEstate = "init";
        lstVenta.clear();
    }

    public void detallesVenta() {
        viewEstate = "searchById";
        lstVenta = ifaceBuscaVenta.getVentaMayoreoById(data.getVentaSucursal().intValue(), data.getIdSucursal().intValue());
        calculatotalVentaDetalle();

    }

    public void cancelarVenta() {
        if (data.getIdStatus().intValue() != 4) {
            lstVenta = ifaceBuscaVenta.buscaVentaCancelar(data.getVentaSucursal().intValue(), data.getIdSucursal().intValue());
            for (BuscaVenta producto : lstVenta) {
                BigDecimal cantidad = producto.getCantidadEmpaque();
                BigDecimal kilos = producto.getKilosVendidos();
                BigDecimal idExistencia = producto.getIdExistenciaFk();

                //Obtenemos la existencia real del producto.

                ArrayList<ExistenciaProducto> exis = new ArrayList<ExistenciaProducto>();

                //public ArrayList<ExistenciaProducto> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor,String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio,BigDecimal idEmPK);
                exis = ifaceNegocioExistencia.getExistenciasCancelar(idExistencia);
                //Primero obtenemos la cantidad de kilos y paquetes en Existencias
                //sumamos los kilos y paquetes al nuevo update.
                cantidad = cantidad.add(exis.get(0).getCantidadPaquetes(), MathContext.UNLIMITED);
                kilos = kilos.add(exis.get(0).getKilosTotalesProducto(), MathContext.UNLIMITED);

                //Creamos el nuevo objeto para hacer el update
                ExistenciaProducto ep = new ExistenciaProducto();
                ep.setCantidadPaquetes(cantidad);
                ep.setKilosTotalesProducto(kilos);
                ep.setIdExistenciaProductoPk(idExistencia);

                if (ifaceNegocioExistencia.updateExistenciaProducto(ep) == 1) {
                    System.out.println("Regreso Producto Correctamente");
                }

            }

            if (ifaceBuscaVenta.cancelarVentaMayoreo(data.getIdVentaPk().intValue(), usuario.getIdUsuario().intValue(), data.getComentariosCancel()) != 0) {
                JsfUtil.addSuccessMessageClean("Venta Cancelada");
                data.setIdStatus(null);
                lstVenta.clear();
                getVentasByIntervalDate();

            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al intentar cancelar la venta.");
            }
        } else {
            JsfUtil.addErrorMessageClean("No puedes volver a cancelar la venta");

        }
    }

    public void calculatotalVentaDetalle() {
        totalVentaDetalle = new BigDecimal(0);

        for (BuscaVenta venta : lstVenta) {
            totalVentaDetalle = totalVentaDetalle.add(venta.getTotal());
        }
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        viewEstate = "searchById";
    }

    public RelacionOperacionesMayoreo getData() {
        return data;
    }

    public void setData(RelacionOperacionesMayoreo data) {
        this.data = data;
    }

    public ArrayList<RelacionOperacionesMayoreo> getModel() {
        return model;
    }

    public void setModel(ArrayList<RelacionOperacionesMayoreo> model) {
        this.model = model;
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

    public ArrayList<TipoVenta> getLstTipoVenta() {
        return lstTipoVenta;
    }

    public void setLstTipoVenta(ArrayList<TipoVenta> lstTipoVenta) {
        this.lstTipoVenta = lstTipoVenta;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotalVentaDetalle() {
        return totalVentaDetalle;
    }

    public void setTotalVentaDetalle(BigDecimal totalVentaDetalle) {
        this.totalVentaDetalle = totalVentaDetalle;
    }

    public BigDecimal getTotalUtilidad() {
        return totalUtilidad;
    }

    public void setTotalUtilidad(BigDecimal totalUtilidad) {
        this.totalUtilidad = totalUtilidad;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

}
