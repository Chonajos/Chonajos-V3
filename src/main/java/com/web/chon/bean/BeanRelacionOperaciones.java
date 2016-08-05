package com.web.chon.bean;

import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.StatusVenta;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
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

    @Autowired private IfaceVenta ifaceVenta;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceCatStatusVenta ifaceCatStatusVenta;
    @Autowired private IfaceVentaProducto ifaceVentaProducto;
    @Autowired private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired private IfaceSubProducto ifaceSubProducto;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Venta> listaVentas;
    private ArrayList<StatusVenta> listaStatusVenta;
    private ArrayList<VentaProducto> listaProductoCancel;
    private ArrayList<Subproducto> lstProducto;

    private UsuarioDominio usuario;

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
    private Date fechaFin;
    private Date fechaInicio;
    private Date fechaFiltroFin;
    private Date fechaFiltroInicio;
    private boolean enableCalendar;
    
    private Subproducto subProducto;

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
        filtro = 1;
        setTitle("Relación de Operaciónes Venta Menudeo");
        setViewEstate("init");
    }

//    public void addComent() 
//    {
//        data.setComentarioCancel(data.getComentarioCancel());
//        System.out.println("Comentarios: " + data.getComentarioCancel());
//    }
//    public void generateReport() {
//        JRExporter exporter = null;
//
//        try {
//            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//
//            String temporal = "";
//            if (servletContext.getRealPath("") == null) {
//                temporal = Constantes.PATHSERVER;
//            } else {
//                temporal = servletContext.getRealPath("");
//            }
//
//            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
//            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
//
//            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
//            exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//
//            byte[] bytes = outputStream.toByteArray();
//            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", data.getIdVentaPk().intValue(), idSucursalImpresion.intValue());
//
//        } catch (Exception exception) {
//            System.out.println("Error >" + exception.getMessage());
//
//        }
//
//    }
//    private void setParameterTicket(int idVenta) {
//
//        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
//        DecimalFormat df = new DecimalFormat("###.##");
//        Date date = new Date();
//        ArrayList<String> productos = new ArrayList<String>();
//        NumeroALetra numeroLetra = new NumeroALetra();
//        for (BuscaVenta venta : lstVenta) {
//            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
//            productos.add(venta.getNombreSubproducto().toUpperCase());
//            productos.add("       " + cantidad + "               " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));
//        }
//
//        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);
//
//        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta);
//
//    }
//    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {
//
//        System.out.println(data.getFechaVenta());
//
//        paramReport.put("fechaVenta", TiempoUtil.getFechaDDMMYYYYHHMM(fechaImpresion));
//        paramReport.put("noVenta", Integer.toString(idVenta));
//        paramReport.put("cliente", data.getNombreCliente());
//        paramReport.put("vendedor", data.getNombreVendedor());
//        paramReport.put("productos", items);
//        paramReport.put("ventaTotal", total);
//        paramReport.put("totalLetra", totalVentaStr);
//        paramReport.put("labelFecha", "Fecha de Pago:");
//        paramReport.put("labelFolio", "Folio de Venta:");
//        paramReport.put("estado", statusVentaImpresion);
//        paramReport.put("labelSucursal", nombreSucursalImpresion);
//        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());
//
//    }
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
            switch (filtro)
            {
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
    public void buscar()
    {
        if(fechaFiltroInicio==null || fechaFiltroFin==null)
        {
            JsfUtil.addErrorMessageClean("Favor de ingresar un rango de fechas");
        }
        else
        {
            if(subProducto==null)
            {
                subProducto = new Subproducto();
                subProducto.setIdProductoFk("");
            }
             listaVentas = ifaceVenta.getVentasByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal, idStatusVenta,subProducto.getIdSubproductoPk());
           
        }
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }
    



//    public void getTotalVentaByInterval() 
//    {
//        totalVenta = new BigDecimal(0);
//        for (RelacionOperaciones dominio : model) {
//            totalVenta = totalVenta.add(dominio.getTotalVenta());
//        }
//    }
//    public void cancel() 
//    {
//        viewEstate = "init";
//        lstVenta.clear();
//        getTotalVentaByInterval();
//    }
//    public void cancelarVenta() {
//        if (data.getIdStatus() != 4) {
//            System.out.println("comentarios: " + data.getComentarioCancel());
//            if (ifaceBuscaVenta.cancelarVenta(data.getIdVentaPk().intValue(), usuario.getIdUsuario().intValue(), data.getComentarioCancel()) != 0) {
//
//                listaProductoCancel = ifaceVentaProducto.getVentasProductoByIdVenta(data.getIdVentaPk().intValue());
//                for (VentaProducto vp : listaProductoCancel) {
//
//                    System.out.println("Bean==========" + vp.toString());
//                    ExistenciaMenudeo em = new ExistenciaMenudeo();
//                    em = ifaceExistenciaMenudeo.getExistenciasRepetidasById(vp.getIdProductoFk(), new BigDecimal(data.getIdSucursal()));
//                    System.out.println("Bean ::::::::::::" + em.toString());
//                    BigDecimal kilosExistencia = em.getKilos();
//                    kilosExistencia = kilosExistencia.add(vp.getKilosVenta());
//                    em.setKilos(kilosExistencia);
//                    if (ifaceExistenciaMenudeo.updateExistenciaMenudeo(em) != 0) {
//                        System.out.println("se regresaron existencias con exito");
//
//                    } else {
//                        System.out.println("Ocurrio un problema");
//                        break;
//                    }
//                }
//                JsfUtil.addSuccessMessageClean("Venta Cancelada");
//                data.setIdStatus(0);
//                getVentasByIntervalDate();
//
//            } else {
//                JsfUtil.addErrorMessageClean("Ocurrió un error al intentar cancelar la venta.");
//            }
//        } else {
//            JsfUtil.addErrorMessageClean("No puedes volver a cancelar la venta");
//
//        }
//    }
//    public void imprimirVenta() {
//        lstVenta = ifaceBuscaVenta.getVentaById(data.getIdVentaPk().intValue());
//        idSucursalImpresion = new BigDecimal(data.getIdSucursal());
//        fechaImpresion = lstVenta.get(0).getFechaVenta();
//
//        statusVentaImpresion = lstVenta.get(0).getNombreStatus();
//        nombreSucursalImpresion = lstVenta.get(0).getNombreSucursal();
//
//        //System.out.println("Estatus Venta: "+statusVentaImpresion);
//        calculatotalVentaDetalle();
//        setParameterTicket(data.getFolioSucursal());
//        generateReport();
//        getTotalVentaByInterval();
//
//    }
//    public void detallesVenta() {
//        viewEstate = "searchById";
//        lstVenta = ifaceBuscaVenta.getVentaById(data.getIdVentaPk().intValue());
//        calculatotalVentaDetalle();
//
//    }
//    public void calculatotalVentaDetalle() {
//        totalVenta = new BigDecimal(0);
//
//        for (BuscaVenta venta : lstVenta) {
//            totalVenta = totalVenta.add(venta.getTotal());
//        }
//    }
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

    
}
