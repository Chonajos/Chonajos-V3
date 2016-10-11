/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.service.IfaceVentaMayoreoProducto;
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
    private IfaceVentaMayoreoProducto ifaceVentaMayoreoProducto;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatStatusVenta ifaceCatStatusVenta;
    @Autowired
    private IfaceTipoVenta ifaceTipoVenta;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private IfaceCredito ifaceCredito;

    private UsuarioDominio usuario;
    private ArrayList<VentaProductoMayoreo> lstVenta;
    private ArrayList<TipoVenta> lstTipoVenta;
    private VentaMayoreo data;
    private ArrayList<VentaMayoreo> listaVentasMayoreo;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<StatusVenta> listaStatusVenta;
    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;

    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private boolean enableCalendar;
    private Subproducto subProducto;
    private BigDecimal idStatusBean;
    private BigDecimal idSucursalBean;
    private BigDecimal idTipoVentaBean;

    private BigDecimal totalVenta;
    private BigDecimal totalUtilidad;
    private BigDecimal totalVentaDetalle;
    private BigDecimal porcentajeUtilidad;
    private static final BigDecimal TIPO = new BigDecimal(1);

    @PostConstruct
    public void init() {
        data = new VentaMayoreo();
        usuario = context.getUsuarioAutenticado();
        filtro = 1;
        data.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        listaVentasMayoreo = new ArrayList<VentaMayoreo>();
        listaSucursales = new ArrayList<Sucursal>();
        listaStatusVenta = new ArrayList<StatusVenta>();
        lstTipoVenta = ifaceTipoVenta.getAll();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaStatusVenta = ifaceCatStatusVenta.getStatusVentas();
        setTitle("Relación de Operaciónes Venta Mayoreo");
        fechaFiltroInicio = context.getFechaSistema();
        fechaFiltroFin = context.getFechaSistema();
        setViewEstate("init");
        idSucursalBean = new BigDecimal(usuario.getSucId());

        //getVentasByIntervalDate();
    }

    public void verificarCombo() {
        if (filtro == -1) {
            //se habilitan los calendarios.
            fechaFiltroInicio = null;
            fechaFiltroFin = null;
            enableCalendar = false;
        } else {
            switch (filtro) {
                case 1:
                    fechaFiltroInicio = context.getFechaSistema();
                    fechaFiltroFin = context.getFechaSistema();
                    break;

                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(context.getFechaSistema());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(context.getFechaSistema());

                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(context.getFechaSistema());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(context.getFechaSistema());
                    break;
                default:
                    fechaFiltroInicio = null;
                    fechaFiltroFin = null;
                    break;
            }
            enableCalendar = true;
        }
    }

    public void buscar() {
        if (fechaFiltroInicio == null || fechaFiltroFin == null) {
            JsfUtil.addErrorMessageClean("Favor de ingresar un rango de fechas");
        } else {
            if (subProducto == null) {
                subProducto = new Subproducto();
                subProducto.setIdProductoFk("");
            }
            listaVentasMayoreo = ifaceVentaMayoreo.getVentasByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursalBean, idStatusBean, idTipoVentaBean, subProducto.getIdSubproductoPk());
            getTotalVentaByInterval();
        }
    }
//    public void setFechaInicioFin(int filter) {
//
//        switch (filter) {
//            case 4:
//                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
//                    model = ifaceVentaMayoreo.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus(), data.getIdTipoVenta());
//                    getTotalVentaByInterval();
//                } else {
//                    model = new ArrayList<VentaMayoreo>();
//                    getTotalVentaByInterval();
//                }
//                break;
//            case 1:
//                data.setFechaFiltroInicio(new Date());
//                data.setFechaFiltroFin(new Date());
//                break;
//
//            case 2:
//                data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
//                data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));
//
//                break;
//            case 3:
//                data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
//                data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
//                break;
//            default:
//                data.setFechaFiltroInicio(null);
//                data.setFechaFiltroFin(null);
//                break;
//        }
//
//    }

    public void printVenta() {

    }

//    public void getVentasByIntervalDate() {
//
//        setFechaInicioFin(filtro);
//
//        listaVentasMayoreo = ifaceVentaMayoreo.getVentasByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursal(), data.getIdStatus(), data.getIdTipoVenta());
//        getTotalVentaByInterval();
//    }
    public void getTotalVentaByInterval() {
        totalVenta = new BigDecimal(0);
        totalUtilidad = new BigDecimal(0);
        porcentajeUtilidad = new BigDecimal(0);
        BigDecimal cien = new BigDecimal(100);
        for (VentaMayoreo dominio : listaVentasMayoreo) {
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

    public void cancelarVenta() {
        AbonoCredito ac = new AbonoCredito();
        ac = ifaceAbonoCredito.getByIdVentaMayoreoFk(data.getIdVentaMayoreoPk());
        if (ac != null && ac.getIdAbonoCreditoPk() != null) 
        {
            JsfUtil.addErrorMessageClean("Este crédito ya cuenta con abonos, no se puede cancelar");
        
        } else 
        {
            if (data.getIdStatusFk().intValue() != 4 && data.getIdStatusFk().intValue() != 2) {
                boolean banderaError = false;
                lstVenta = ifaceVentaMayoreoProducto.buscaVentaCancelar(data.getVentaSucursal(), data.getIdSucursalFk());
                for (VentaProductoMayoreo producto : lstVenta) {
                    BigDecimal cantidad = producto.getCantidadEmpaque();
                    BigDecimal kilos = producto.getKilosVendidos();
                    BigDecimal idExistencia = producto.getIdExistenciaFk();
                    BigDecimal idBodega = producto.getIdBodegaFk();
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
                    ep.setIdBodegaFK(idBodega);
                    if (ifaceNegocioExistencia.updateCantidadKilo(ep) == 1) {
                        System.out.println("Regreso Producto Correctamente");
                    } else {
                        banderaError = true;
                    }

                }

                if (ifaceVentaMayoreo.cancelarVentaMayoreo(data.getIdVentaMayoreoPk(), usuario.getIdUsuario(), data.getComentariosCancel()) != 0 && banderaError == false) {

                    Credito c = new Credito();
                    c = ifaceCredito.getCreditosByIdVentaMayoreo(data.getIdVentaMayoreoPk());
                    System.out.println("Credito: "+c.toString());
                    if (c != null && c.getIdCreditoPk() != null) 
                    {
                        if (ifaceCredito.eliminarCreditoByIdCreditoPk(c.getIdCreditoPk()) == 1) {
                            JsfUtil.addSuccessMessageClean("Se ha cancelado la venta  y credito correctamente ");
                            data.setIdStatusFk(null);
                            lstVenta.clear();
                            buscar();
                        } else 
                        {
                            JsfUtil.addErrorMessageClean("Ha ocurrido un error al eliminar el credito");
                        }
                    }
                    else
                    {
                        JsfUtil.addSuccessMessageClean("Se ha cancelado la venta correctamente");
                        data.setIdStatusFk(null);
                        lstVenta.clear();
                        buscar();
                    }

                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un error al intentar cancelar la venta.");
                }
            } else {
                JsfUtil.addErrorMessageClean("No puedes volver a cancelar la venta, o cancelar una venta ya pagada");

            }
            
            }
    }

//    public void calculatotalVentaDetalle() 
//    {
//        totalVentaDetalle = new BigDecimal(0);
//
//        for (BuscaVenta venta : lstVenta) {
//            totalVentaDetalle = totalVentaDetalle.add(venta.getTotal());
//        }
//    }
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

    public VentaMayoreo getData() {
        return data;
    }

    public void setData(VentaMayoreo data) {
        this.data = data;
    }

    public ArrayList<VentaMayoreo> getListaVentasMayoreo() {
        return listaVentasMayoreo;
    }

    public void setListaVentasMayoreo(ArrayList<VentaMayoreo> listaVentasMayoreo) {
        this.listaVentasMayoreo = listaVentasMayoreo;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public BigDecimal getIdStatusBean() {
        return idStatusBean;
    }

    public void setIdStatusBean(BigDecimal idStatusBean) {
        this.idStatusBean = idStatusBean;
    }

    public BigDecimal getIdSucursalBean() {
        return idSucursalBean;
    }

    public void setIdSucursalBean(BigDecimal idSucursalBean) {
        this.idSucursalBean = idSucursalBean;
    }

    public BigDecimal getIdTipoVentaBean() {
        return idTipoVentaBean;
    }

    public void setIdTipoVentaBean(BigDecimal idTipoVentaBean) {
        this.idTipoVentaBean = idTipoVentaBean;
    }

    public ArrayList<VentaProductoMayoreo> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<VentaProductoMayoreo> lstVenta) {
        this.lstVenta = lstVenta;
    }

}
