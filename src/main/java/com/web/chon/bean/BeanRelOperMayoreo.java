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
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.service.IfaceVentaMayoreoProducto;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
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

    //Variables para Generar el pdf
    private String rutaPDF;
    private Map paramReport = new HashMap();
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;
    private boolean charLine = true;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal descuento;
    private BigDecimal dejaACuenta;
    private BigDecimal totalVentaDescuento;
    private BigDecimal INTERES_VENTA = new BigDecimal("0.60");
    private BigDecimal DIAS_PLAZO = new BigDecimal("7");
    private int folioCredito = 0;
    private Credito c;
    private boolean variableInicial;
    private boolean credito;
    

    private Date date;
    

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

        descuento = new BigDecimal(0);
        dejaACuenta = new BigDecimal(0);
        totalVentaDescuento = new BigDecimal(0);
        //getVentasByIntervalDate();
        c = new Credito();
        buscar();
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

    public void printVenta() {
        c = new Credito();
        c = ifaceCredito.getCreditosByIdVentaMayoreo(data.getIdVentaMayoreoPk());
        if (c.getDejaCuenta() == null) {
            c.setDejaCuenta(new BigDecimal(0));
        }
        if (c.getMontoCredito() == null) {
            c.setMontoCredito(new BigDecimal(0));
        }
        System.out.println("================Credito: " + c.toString());
        setParameterTicket(data.getIdVentaMayoreoPk().intValue(), data.getVentaSucursal().intValue());
        generateReport(data.getVentaSucursal().intValue());
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");

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
        if (ac != null && ac.getIdAbonoCreditoPk() != null) {
            JsfUtil.addErrorMessageClean("Este crédito ya cuenta con abonos, no se puede cancelar");

        } else {
            if (data.getIdStatusFk().intValue() != 4 && data.getIdStatusFk().intValue() != 2 && data.getIdStatusFk().intValue() != 3) {
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
                    System.out.println("Credito: " + c.toString());
                    if (c != null && c.getIdCreditoPk() != null) {
                        if (ifaceCredito.eliminarCreditoByIdCreditoPk(c.getIdCreditoPk()) == 1) {
                            JsfUtil.addSuccessMessageClean("Se ha cancelado la venta  y credito correctamente ");
                            data.setIdStatusFk(null);
                            lstVenta.clear();
                            buscar();
                        } else {
                            JsfUtil.addErrorMessageClean("Ha ocurrido un error al eliminar el credito");
                        }
                    } else {
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

    public void generateReport(int folio) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            if (data.getIdtipoVentaFk().equals(new BigDecimal(1))) {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            } else {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticketCredito.jasper";
            }

//            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "relacionMayoreo", folio, usuario.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }

    }

    private void setParameterTicket(int idVenta, int folioVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");

        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();

        for (VentaProductoMayoreo venta : data.getListaProductos()) {
            String cantidad = venta.getCantidadEmpaque() + " - " + venta.getKilosVendidos() + "Kg.";
            productos.add(venta.getNombreProducto().toUpperCase() + " " + venta.getNombreEmpaque());
            productos.add("  " + cantidad + "                     " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotalVenta()));
        }

        String totalVentaDescuentoStr = "";
        String totalVentaStr = numeroLetra.Convertir(df.format(data.getTotalVenta()), true);
        if (data.getIdtipoVentaFk().equals(new BigDecimal(2))) {
            totalVentaDescuentoStr = numeroLetra.Convertir(df.format(c.getMontoCredito()), true);
        }

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(data.getFechaVenta()), productos, nf.format(data.getTotalVenta()), totalVentaStr, idVenta, folioVenta, totalVentaDescuentoStr, nf.format(c.getMontoCredito()));

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta, int folioVenta, String totalVentaDescuentoStr, String totalDescuento) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        paramReport.clear();
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(folioVenta));
        paramReport.put("cliente", data.getNombreCliente());
        paramReport.put("vendedor", data.getNombreVendedor());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        switch (data.getIdStatusFk().intValue()) {
            case 1:
                paramReport.put("estado", "PEDIDO MARCADO");
                break;
            case 2:
                paramReport.put("estado", "PEDIDO PAGADO");
                break;
            case 3:
                paramReport.put("estado", "PEDIDO ENTREGADO");
                break;
            case 4:
                paramReport.put("estado", "PEDIDO CANCELADO");
                break;
            default:
                paramReport.put("estado", "ERROR TICKET");
                JsfUtil.addErrorMessageClean("Ocurrió un error");
        }
        paramReport.put("labelFecha", "Fecha de Venta:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());
        paramReport.put("labelSucursal", usuario.getNombreSucursal());

        //Se agregan los campos que se utiliza en el ticket de credito
        if (!data.getIdtipoVentaFk().equals(new BigDecimal(1))) {

            paramReport.put("numeroCliente", data.getIdClienteFk().toString());
            paramReport.put("fechaPromesaPago", TiempoUtil.getFechaDDMMYYYY(c.getFechaPromesaPago()));
            paramReport.put("beneficiario", "FIDENCIO TORRES REYNOSO");
            paramReport.put("totalCompraDescuento", "$" + df.format(c.getMontoCredito()));
            paramReport.put("totalDescuentoLetra", totalVentaDescuentoStr);

            paramReport.put("aCuenta", "-$" + df.format(c.getDejaCuenta()));
            paramReport.put("descuentoVenta", "-$" + df.format(descuento));
            paramReport.put("foliCredito", Integer.toString(c.getIdCreditoPk().intValue()));
            //Imprime el calendario de pagos
            date = data.getFechaVenta();
            Date dateTemp = date;
            ArrayList calendario = new ArrayList();
            String montoAbono = df.format(c.getMontoCredito().divide(c.getNumeroPagos() == null ? BigDecimal.ZERO : c.getNumeroPagos(), 2, RoundingMode.UP));
            String item = "N. Pago   Fecha de Pago   Monto";
            calendario.add(item);
            if (c.getDejaCuenta().intValue() > 0) {
                item = "    0            " + TiempoUtil.getFechaDDMMYYYY(date) + "    $" + df.format(c.getDejaCuenta());
                calendario.add(item);
                paramReport.put("msgAcuenta", "Favor de pasar a caja para su pago inicial de: $" + df.format(c.getDejaCuenta()));
            } else {
                paramReport.put("msgAcuenta", "");
            }

            int plaso = (c.getPlasos().divide(c.getNumeroPagos())).intValue();
            int pagos = c.getNumeroPagos().intValue();
            for (int y = 0; y < pagos; y++) {

                dateTemp = TiempoUtil.sumarRestarDias(dateTemp, plaso);
                item = "    " + (y + 1) + "            " + TiempoUtil.getFechaDDMMYYYY(dateTemp) + "    $" + montoAbono;
                calendario.add(item);

            }
            paramReport.put("calendarioPago", calendario);
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

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public Map getParamReport() {
        return paramReport;
    }

    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public void setPathFileJasper(String pathFileJasper) {
        this.pathFileJasper = pathFileJasper;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public boolean isCharLine() {
        return charLine;
    }

    public void setCharLine(boolean charLine) {
        this.charLine = charLine;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getDejaACuenta() {
        return dejaACuenta;
    }

    public void setDejaACuenta(BigDecimal dejaACuenta) {
        this.dejaACuenta = dejaACuenta;
    }

    public BigDecimal getTotalVentaDescuento() {
        return totalVentaDescuento;
    }

    public void setTotalVentaDescuento(BigDecimal totalVentaDescuento) {
        this.totalVentaDescuento = totalVentaDescuento;
    }

    public BigDecimal getINTERES_VENTA() {
        return INTERES_VENTA;
    }

    public void setINTERES_VENTA(BigDecimal INTERES_VENTA) {
        this.INTERES_VENTA = INTERES_VENTA;
    }

    public BigDecimal getDIAS_PLAZO() {
        return DIAS_PLAZO;
    }

    public void setDIAS_PLAZO(BigDecimal DIAS_PLAZO) {
        this.DIAS_PLAZO = DIAS_PLAZO;
    }

    public int getFolioCredito() {
        return folioCredito;
    }

    public void setFolioCredito(int folioCredito) {
        this.folioCredito = folioCredito;
    }

    public boolean isVariableInicial() {
        return variableInicial;
    }

    public void setVariableInicial(boolean variableInicial) {
        this.variableInicial = variableInicial;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Credito getC() {
        return c;
    }

    public void setC(Credito c) {
        this.c = c;
    }

}
