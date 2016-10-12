package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceCuentasBancarias;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTipoAbono;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.UtilUpload;
import com.web.chon.util.TiempoUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
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
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BeanBuscaVentaMayoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private IfaceCuentasBancarias ifaceCuentasBancarias;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;

    private ArrayList<CuentaBancaria> listaCuentas;

    private VentaMayoreo ventaMayoreo;
    private ArrayList<VentaProductoMayoreo> listaProductos;

    private Usuario usuario;
    private Caja caja;
    private OperacionesCaja opcaja;

    private String title;
    private String viewEstate;
    private BigDecimal totalVenta;
    private Map paramReport = new HashMap();
    private boolean statusButtonPagar;
    private int idVentaTemporal; //utilizado para comprobacion de venta
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;
    private StreamedContent media;
    private UsuarioDominio usuarioDominio;
    private BigDecimal recibido;
    private BigDecimal cambio;

    private static final BigDecimal entradaSalida = new BigDecimal(1);
    private static final BigDecimal statusOperacion = new BigDecimal(1);
    private static final BigDecimal concepto = new BigDecimal(9);

    //-------------- Variables para Registrar Pago ----------//
    private BigDecimal idTipoPagoFk;
    private BigDecimal folioVenta;

    //-------------- Variables para Registrar Pago ----------//
    @PostConstruct
    public void init() {
        FacesContext contexts = FacesContext.getCurrentInstance();
        String folio = contexts.getExternalContext().getRequestParameterMap().get("folio");
        System.out.println("FolioPasado: " + folio);
        ventaMayoreo = new VentaMayoreo();
        listaProductos = new ArrayList<VentaProductoMayoreo>();

        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(usuarioDominio.getSucId());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());

        setTitle("Pagar Venta de Mayoreo");
        setViewEstate("init");
        statusButtonPagar = true;
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuarioPk());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdConceptoFk(concepto);
        opcaja.setIdUserFk(usuario.getIdUsuarioPk());
        opcaja.setEntradaSalida(entradaSalida);
        opcaja.setIdStatusFk(statusOperacion);
    }

    public void calculaCambio() {
        cambio = recibido.subtract(totalVenta, MathContext.UNLIMITED);
    }

    public void generateReport() {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", idVentaTemporal, usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }

    }

    private void setParameterTicket(int idVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
//        for (BuscaVenta venta : model) {
//            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
//            productos.add(venta.getNombreSubproducto().toUpperCase());
//            productos.add("       " + cantidad + "               " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));
//        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {

        //System.out.println(data.getFechaVenta());
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(idVenta));
        //paramReport.put("cliente", data.getNombreCliente());
        //paramReport.put("vendedor", data.getNombreVendedor());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("labelFecha", "Fecha de Pago:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("estado", "PEDIDO PAGADO");
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());

    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
    }

    public void downloadFile() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + getNameFilePdf());

            OutputStream output = response.getOutputStream();
            output.write(outputStream.toByteArray());
            output.close();

            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
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

    public void updateVenta() 
    {
//        if (opcaja.getIdCajaFk() != null) {
//
//            System.out.println("Status Venta :" + data.getStatusFK());
//            if (data.getStatusFK() == 2) {
//                JsfUtil.addErrorMessageClean("Error, la venta ya se encuentra pagada");
//            } else if (data.getIdVenta().intValue() != idVentaTemporal) {
//                JsfUtil.addErrorMessageClean("No coincide el numero de venta");
//            } else if (ifaceBuscaVenta.updateStatusVentaMayoreo(data.getIdVenta().intValue(), usuario.getIdUsuarioPk().intValue()) == 1) {
//                System.out.println("Se cambió el estatus");
//
//                opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
//                opcaja.setMonto(totalVenta);
//                if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
//                    setParameterTicket(data.getFolioSucursal().intValue());
//                    generateReport();
//                    data.setNombreCliente("");
//                    data.setNombreVendedor("");
//                    data.setIdVenta(new BigDecimal(0));
//                    statusButtonPagar = true;
//                    data.reset();
//                    model = null;
//                    totalVenta = null;
//                    RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
//                } else {
//                    JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
//                }
//            } else {
//                System.out.println("Error al cambiar estaus de la venta");
//            }
//        } else {
//            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja asignada, no se puede realizar el cobro");
//        }

        //return "buscaVentas";
    }

    @Override
    public void searchById() {
        statusButtonPagar = false;

        ventaMayoreo = ifaceVentaMayoreo.getVentaMayoreoByFolioidSucursalFk(folioVenta, new BigDecimal(usuario.getIdSucursal()));
        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) 
        {
            JsfUtil.addErrorMessageClean("No se encontró ese folio, podría ser de otra sucursal.");
        } else 
        {
            if (ventaMayoreo.getIdtipoVentaFk().intValue() == 1) 
            {
                switch (ventaMayoreo.getIdStatusFk().intValue()) {
                    case 1:
                        statusButtonPagar = false;
                        break;
                    case 2:
                        statusButtonPagar = true;
                        JsfUtil.addErrorMessageClean("No puedes volver a cobrar la venta.");
                        break;
                    case 3:
                        statusButtonPagar = true;
                        JsfUtil.addErrorMessageClean("No puedes cobrar una venta entregada.");
                        break;
                    case 4:
                        statusButtonPagar = true;
                        JsfUtil.addErrorMessageClean("No puedes cobrar una venta cancelada.");
                        break;
                    default:
                        statusButtonPagar = true;
                        JsfUtil.addErrorMessageClean("Ha ocurrido un error, contactar al administrador.");
                        break;
                }
            }
            else
            {
                JsfUtil.addErrorMessageClean("No puedes cobrar una venta de crédito, ir a la sección abonar crédito.");
       
            }
        }

//        if (model.isEmpty()) {
//            data.setNombreCliente("");
//            data.setNombreVendedor("");
//            data.setIdVenta(new BigDecimal(0));
//            data.setFolioSucursal(null);
//            statusButtonPagar = true;
//
//            JsfUtil.addWarnMessageClean("No se encontraron Registros.");
//
//        } else {
//
//            data.setNombreCliente(model.get(0).getNombreCliente());
//            data.setNombreVendedor(model.get(0).getNombreVendedor());
//            data.setStatusFK(model.get(0).getIdStatus().intValue());
//            data.setFolioSucursal(model.get(0).getFolioSucursal());
//            data.setIdVenta(model.get(0).getIdVenta());
//            data.setIdSucursalFk(model.get(0).getIdSucursalFk());
//            data.setNombreStatus(model.get(0).getNombreStatus());
//            idVentaTemporal = data.getIdVenta().intValue();
//            calculatotalVenta();

           

//            if (data.getIdSucursalFk().equals(new BigDecimal(usuario.getIdSucursal()))) {
//
//                statusButtonPagar = false;
//            } else 
//            {
//                JsfUtil.addWarnMessageClean("No puedes cobrar el folio de otra sucursal.");
//                statusButtonPagar = true;
//            }
        

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

    public IfaceBuscaVenta getIfaceBuscaVenta() {
        return ifaceBuscaVenta;
    }

    public void setIfaceBuscaVenta(IfaceBuscaVenta ifaceBuscaVenta) {
        this.ifaceBuscaVenta = ifaceBuscaVenta;
    }

    public boolean isStatusButtonPagar() {
        return statusButtonPagar;
    }

    public void setStatusButtonPagar(boolean statusButtonPagar) {
        this.statusButtonPagar = statusButtonPagar;
    }

    public int getIdVentaTemporal() {
        return idVentaTemporal;
    }

    public void setIdVentaTemporal(int idVentaTemporal) {
        this.idVentaTemporal = idVentaTemporal;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
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

    public BigDecimal getRecibido() {
        return recibido;
    }

    public void setRecibido(BigDecimal recibido) {
        this.recibido = recibido;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public ArrayList<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<CuentaBancaria> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public OperacionesCaja getOpcaja() {
        return opcaja;
    }

    public void setOpcaja(OperacionesCaja opcaja) {
        this.opcaja = opcaja;
    }

    public Map getParamReport() {
        return paramReport;
    }

    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }

    public BigDecimal getIdTipoPagoFk() {
        return idTipoPagoFk;
    }

    public void setIdTipoPagoFk(BigDecimal idTipoPagoFk) {
        this.idTipoPagoFk = idTipoPagoFk;
    }

    public VentaMayoreo getVentaMayoreo() {
        return ventaMayoreo;
    }

    public void setVentaMayoreo(VentaMayoreo ventaMayoreo) {
        this.ventaMayoreo = ventaMayoreo;
    }

    public ArrayList<VentaProductoMayoreo> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<VentaProductoMayoreo> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public BigDecimal getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(BigDecimal folioVenta) {
        this.folioVenta = folioVenta;
    }

}
