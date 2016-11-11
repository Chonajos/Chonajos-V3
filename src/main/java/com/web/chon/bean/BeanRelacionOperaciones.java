package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
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
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatStatusVenta ifaceCatStatusVenta;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;
    @Autowired
    private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Venta> listaVentas;
    private ArrayList<StatusVenta> listaStatusVenta;
    private ArrayList<VentaProducto> listaProductoCancel;
    private ArrayList<Subproducto> lstProducto;

    private UsuarioDominio usuario;
    private Venta ventaImpresion;
    private Venta ventaCancelar;
    private Caja caja;

    private String title;
    private String viewEstate;

    private int filtro;

    private BigDecimal totalVenta;

    //----Variables para la reimpresion de ticket----//
    private int idVentaTemporal; //utilizado para comprobacion de venta
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private BigDecimal idSucursalImpresion;
    private String nombreSucursalImpresion;
    private String statusVentaImpresion;
    private Date fechaImpresion;
    //----Variables para la reimpresion de ticket----//

    //-- Variables del bean para realizar la consulta---//
    private String idProducto;
    private BigDecimal idStatusVenta;
    private BigDecimal idSucursal;
    private BigDecimal idTipoVenta;
    private Date fechaFin;
    private Date fechaInicio;
    private Date fechaFiltroFin;
    private Date fechaFiltroInicio;
    private boolean enableCalendar;
    private String comentarioCancelacion;

    private Subproducto subProducto;
    private static final BigDecimal TIPO = new BigDecimal(1);

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        /*Validacion de perfil administrador*/
        idSucursal = new BigDecimal(usuario.getSucId());
        listaVentas = new ArrayList<Venta>();
        listaSucursales = new ArrayList<Sucursal>();
        listaStatusVenta = new ArrayList<StatusVenta>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaStatusVenta = ifaceCatStatusVenta.getStatusVentas();
        ventaImpresion = new Venta();
        ventaCancelar = new Venta();
        filtro = 1;
        idTipoVenta = null;
        comentarioCancelacion = "";
        totalVenta = new BigDecimal(0);
        verificarCombo();

        setTitle("Relación de Operaciónes Venta Menudeo");
        setViewEstate("init");

    }

    public void generateReport(Venta v) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            if (v.getIdTipoVenta().equals(new BigDecimal(1))) {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            } else {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticketCredito.jasper";
            }

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "operacionMenudeo", v.getIdVentaPk().intValue(), idSucursalImpresion.intValue());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }
    }

    private void setParameterTicket(Venta v) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
        for (VentaProducto vp : v.getLstVentaProducto()) {
            String cantidad = vp.getCantidadEmpaque() + " Kilos ";
            productos.add(vp.getNombreProducto().toUpperCase());
            productos.add("       " + cantidad + "               " + nf.format(vp.getPrecioProducto()) + "    " + nf.format(vp.getTotal()));
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        paramReport.put("fechaVenta", TiempoUtil.getFechaDDMMYYYYHHMM(fechaImpresion));
        paramReport.put("noVenta", v.getFolio().toString());
        paramReport.put("cliente", v.getNombreCliente());
        paramReport.put("vendedor", v.getNombreVendedor());
        paramReport.put("productos", productos);
        paramReport.put("ventaTotal", nf.format(totalVenta).toString());
        paramReport.put("totalLetra", totalVentaStr);
        if (!v.getIdStatusVenta().equals(2)) {
            paramReport.put("labelFecha", "Fecha de Venta: K-");
        } else {
            paramReport.put("labelFecha", "Fecha de Pago: K-");
        }
        paramReport.put("labelFolio", "Folio de Venta: K-");
        paramReport.put("estado", v.getNombreEstatus());
        paramReport.put("labelSucursal", v.getNombreSucursal());
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());

        //Se agregan los campos que se utiliza en el ticket de credito
        if (!v.getIdTipoVenta().equals(new BigDecimal(1))) {

            String totalVentaDescuentoStr = numeroLetra.Convertir(df.format(v.getMontoCredito()), true);
            paramReport.put("numeroCliente", v.getIdClienteFk().toString());
            paramReport.put("labelFecha", "Fecha de Venta:");
            paramReport.put("fechaPromesaPago", TiempoUtil.getFechaDDMMYYYY(v.getFechaPromesaPago()));
            paramReport.put("beneficiario", "FIDENCIO TORRES REYNOSO");
            paramReport.put("totalCompraDescuento", nf.format(v.getMontoCredito()));
            paramReport.put("totalDescuentoLetra", totalVentaDescuentoStr);
            paramReport.put("aCuenta", "-" + nf.format(v.getaCuenta()));
            paramReport.put("descuentoVenta", "-" + df.format(0));
            paramReport.put("foliCredito", v.getIdCredito() == null ? null : v.getIdCredito().toString());

            //Imprime el calendario de pagos
            Date dateTemp = v.getFechaVenta();
            ArrayList calendario = new ArrayList();
            String montoAbono = nf.format(v.getMontoCredito().divide(v.getNumeroPagos() == null ? BigDecimal.ZERO : v.getNumeroPagos(), 2, RoundingMode.UP));
            String item = "N. Pago   Fecha de Pago   Monto";
            calendario.add(item);
            if (v.getaCuenta().intValue() > 0) {
                item = "    0            " + TiempoUtil.getFechaDDMMYYYY(date) + "    " + nf.format(v.getaCuenta());
                calendario.add(item);

                paramReport.put("msgAcuenta", "Favor de pasar a caja para su pago inicial de: " + nf.format(v.getaCuenta()));
            } else {
                paramReport.put("msgAcuenta", "");
            }

            int plaso = (v.getPlazos().divide(v.getNumeroPagos())).intValue();
            int pagos = v.getNumeroPagos().intValue();
            for (int y = 0; y < pagos; y++) {

                dateTemp = TiempoUtil.sumarRestarDias(dateTemp, plaso);
                item = "    " + (y + 1) + "            " + TiempoUtil.getFechaDDMMYYYY(dateTemp) + "     " + montoAbono;
                calendario.add(item);

            }
            paramReport.put("calendarioPago", calendario);
        }

    }

    @Override
    public void searchById() {
        viewEstate = "searchById";

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
                    fechaFiltroInicio = new Date();
                    fechaFiltroFin = new Date();
                    break;

                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(new Date());

                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
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
            listaVentas = ifaceVenta.getVentasByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal, idStatusVenta, subProducto.getIdSubproductoPk(), idTipoVenta);
            getTotalVentaByInterval();
        }
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void getTotalVentaByInterval() {
        totalVenta = new BigDecimal(0);
        for (Venta v : listaVentas) {
            if (v.getIdStatusVenta().intValue() != 4 || v.getIdStatusVenta().intValue() != 5) {
                totalVenta = totalVenta.add(v.getTotalVenta(), MathContext.UNLIMITED);
            }
        }
    }

    public void cancelarVenta() {
        switch (ventaCancelar.getIdStatusVenta().intValue()) {
            case 4:
                JsfUtil.addErrorMessageClean("No puedes volver a cancelar la venta");
                break;
            default:
                //Verifica si tiene abonos en caso de que sea una venta a creditos
                ArrayList<AbonoCredito> lstAbonoCredito = new ArrayList<AbonoCredito>();
                lstAbonoCredito = ifaceAbonoCredito.getAbonosByIdCredito(ventaCancelar.getIdCredito());

                if (lstAbonoCredito == null || lstAbonoCredito.isEmpty()) {

                    boolean bandera = false;
                    Credito c = new Credito();
                    c = ifaceCredito.getCreditosByIdVentaMenudeo(totalVenta);
                    //Verifica si es una venta de contado

                    listaProductoCancel = ifaceVentaProducto.getVentasProductoByIdVenta(ventaCancelar.getIdVentaPk());
                    for (VentaProducto vp : listaProductoCancel) {
                        ExistenciaMenudeo em = new ExistenciaMenudeo();
                        em = ifaceExistenciaMenudeo.getExistenciasRepetidasById(vp.getIdProductoFk(), new BigDecimal(ventaCancelar.getIdSucursal()));
                        BigDecimal kilosExistencia = new BigDecimal(0);
                        kilosExistencia = em.getKilos();
                        kilosExistencia = kilosExistencia.add(vp.getCantidadEmpaque(), MathContext.UNLIMITED);
                        em.setKilos(kilosExistencia);
                        if (ifaceExistenciaMenudeo.updateExistenciaMenudeo(em) != 0) {
                            System.out.println("se regresaron existencias con exito");
                        } else {
                            System.out.println("Ocurrio un problema al regresas existencias");
                            bandera = true;
                            break;
                        }
                    }
                    if (ifaceVenta.cancelarVenta(ventaCancelar.getIdVentaPk().intValue(), usuario.getIdUsuario().intValue(), comentarioCancelacion) != 0 && bandera == false) {
                        if (ventaCancelar.getIdTipoVenta().equals(new BigDecimal(2))) {
                            int creditoBorrado = 0;
                            creditoBorrado = ifaceCredito.delete(ventaCancelar.getIdCredito());
                            if (creditoBorrado == 1) {
                                JsfUtil.addSuccessMessageClean("Venta Cancelada, se han regresado existencias y dinero en caja correctamente");
                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrió un error al Borrar el Credito, Contactar al Administrador.");
                            }
                        }

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al intentar cancelar la venta.");
                    }
                    break;
                } else {
                    JsfUtil.addErrorMessageClean("No se Puede Cancelar una Venta de Credito en la Cual ya se Registraron Abonos.");
                }
        }
    }

    public void imprimirVenta() {

        idSucursalImpresion = new BigDecimal(ventaImpresion.getIdSucursal());
        fechaImpresion = ventaImpresion.getFechaVenta();
        //System.out.println("Estatus Venta: "+statusVentaImpresion);
        calculatotalVentaDetalle(ventaImpresion.getLstVentaProducto());
        setParameterTicket(ventaImpresion);
        generateReport(ventaImpresion);

    }

    public void calculatotalVentaDetalle(ArrayList<VentaProducto> vp) {
        totalVenta = new BigDecimal(0);

        for (VentaProducto item : vp) {
            totalVenta = totalVenta.add(item.getTotal(), MathContext.UNLIMITED);
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

    public int getIdVentaTemporal() {
        return idVentaTemporal;
    }

    public void setIdVentaTemporal(int idVentaTemporal) {
        this.idVentaTemporal = idVentaTemporal;
    }

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
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

    public Map getParamReport() {
        return paramReport;
    }

    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }

    public BigDecimal getIdSucursalImpresion() {
        return idSucursalImpresion;
    }

    public void setIdSucursalImpresion(BigDecimal idSucursalImpresion) {
        this.idSucursalImpresion = idSucursalImpresion;
    }

    public String getNombreSucursalImpresion() {
        return nombreSucursalImpresion;
    }

    public void setNombreSucursalImpresion(String nombreSucursalImpresion) {
        this.nombreSucursalImpresion = nombreSucursalImpresion;
    }

    public String getStatusVentaImpresion() {
        return statusVentaImpresion;
    }

    public void setStatusVentaImpresion(String statusVentaImpresion) {
        this.statusVentaImpresion = statusVentaImpresion;
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public ArrayList<VentaProducto> getListaProductoCancel() {
        return listaProductoCancel;
    }

    public void setListaProductoCancel(ArrayList<VentaProducto> listaProductoCancel) {
        this.listaProductoCancel = listaProductoCancel;
    }

    public ArrayList<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(ArrayList<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getIdStatusVenta() {
        return idStatusVenta;
    }

    public void setIdStatusVenta(BigDecimal idStatusVenta) {
        this.idStatusVenta = idStatusVenta;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
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

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public Venta getVentaImpresion() {
        return ventaImpresion;
    }

    public void setVentaImpresion(Venta ventaImpresion) {
        this.ventaImpresion = ventaImpresion;
    }

    public Venta getVentaCancelar() {
        return ventaCancelar;
    }

    public void setVentaCancelar(Venta ventaCancelar) {
        this.ventaCancelar = ventaCancelar;
    }

    public String getComentarioCancelacion() {
        return comentarioCancelacion;
    }

    public void setComentarioCancelacion(String comentarioCancelacion) {
        this.comentarioCancelacion = comentarioCancelacion;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

   
    
    

}
