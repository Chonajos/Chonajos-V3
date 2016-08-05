package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceTipoAbono;
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
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanConsultaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCredito ifaceCredito;
    @Autowired
    IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private PlataformaSecurityContext context;

    private BigDecimal idCliente;
    private String nombreCompletoCliente;
    private String rfcCliente;
    private BigDecimal creditoAutorizado;
    private BigDecimal creditoUtilizado;
    private BigDecimal creditoDisponible;
    private String tipoCliente;
    private String title;
    private String viewEstate;
    private BigDecimal totalCreditos;
    private BigDecimal totalAbonado;

    private ArrayList<SaldosDeudas> modelo;
    private ArrayList<TipoAbono> lstTipoAbonos;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<AbonoCredito> chequesPendientes;
    private ArrayList<AbonoCredito> selectedchequesPendientes;
    private SaldosDeudas dataAbonar;
    private AbonoCredito abono;
    private Cliente cliente;
    private String viewCheque;

    private UsuarioDominio usuarioDominio;
    private boolean bandera;
    private Date fechaMasProximaPago;
    private BigDecimal minimoPago;
    private BigDecimal saldoParaLiquidar;

    //------------variables para generar el ticket----------//
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String number;
    private String rutaPDF;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";

    private int numFiltro;
    private int numDias;

    //------------variables para generar el ticket----------//
    //---Constantes---//
    private static BigDecimal CREDITOFINALIZADO = new BigDecimal(2);
    private static BigDecimal CREDITOACTIVO = new BigDecimal(1);
    private static BigDecimal ABONOREALIZADO = new BigDecimal(1);
    private static BigDecimal ABONOPENDIENTE = new BigDecimal(2);
    //----Constantes--//

    @PostConstruct
    public void init() {
        minimoPago = new BigDecimal(0);
        bandera = false;
        fechaMasProximaPago = new Date();
        abono = new AbonoCredito();
        usuarioDominio = context.getUsuarioAutenticado();
        dataAbonar = new SaldosDeudas();
        modelo = new ArrayList<SaldosDeudas>();
        chequesPendientes = new ArrayList<AbonoCredito>();
        selectedchequesPendientes = new ArrayList<AbonoCredito>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        saldoParaLiquidar = new BigDecimal(0);
        setTitle("Consulta de Creditos");
        setViewEstate("init");
        setViewCheque("init");
    }

    private void setParameterTicket(AbonoCredito ac, Cliente c) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        NumeroALetra numeroLetra = new NumeroALetra();
        String totalVentaStr = numeroLetra.Convertir(df.format(ac.getMontoAbono()), true);
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("labelEstatus", "Abono Pagado");
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("numeroCliente", c.getId_cliente().toString());
        paramReport.put("nombreCliente", cliente.getNombreCombleto());
        paramReport.put("recibimos", nf.format(ac.getMontoAbono()));
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("fechaProximoPago", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("montoliquidar", nf.format(saldoParaLiquidar.subtract(ac.getMontoAbono(), MathContext.UNLIMITED)));
        paramReport.put("montoMinimo", nf.format(ac.getMontoAbono()));
        paramReport.put("nombreAtendedor", usuarioDominio.getNombreCompleto());
        /* Parametros para ticket de Cheque*/
        switch (ac.getIdtipoAbonoFk().intValue()) {
            case 1:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                break;
            case 2:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("nreferencia", ac.getReferencia());
                paramReport.put("concepto", ac.getConcepto());
                paramReport.put("fechaTrans", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaTransferencia()));
                break;
            case 3:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("numeroCheque", ac.getNumeroCheque().toString());
                paramReport.put("banco", ac.getBanco());
                paramReport.put("fechaCobro", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaCobro()));
            case 5:
                paramReport.put("labelEstatus", "PAGO A CUENTA");
                paramReport.put("folio", ac.getIdCreditoFk().toString());
                paramReport.put("montoliquidar", nf.format(saldoParaLiquidar));

                break;
            default:
                break;
        }

        /* Parametros para ticket de Cheque*/
 /* Parametros para ticket de Transferencia*/

 /* Parametros para ticket de Transferencia*/
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());

    }

    public void generateReport(int folio, String nombreTipoTicket) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }
            System.out.println("reporte de credito contado");
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketAbonos" + File.separatorChar + nombreTipoTicket;

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", folio, usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Ticket de Venta.");
        }

    }

    public void backView() {
        setTitle("Consulta Crédito");
        setViewEstate("init");
    }

    public void consultaCredito() {
        modelo = ifaceCredito.getCreditosByEstatus(numFiltro, numDias);

    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompletoCliente() {
        return nombreCompletoCliente;
    }

    public void setNombreCompletoCliente(String nombreCompletoCliente) {
        this.nombreCompletoCliente = nombreCompletoCliente;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public BigDecimal getCreditoAutorizado() {
        return creditoAutorizado;
    }

    public void setCreditoAutorizado(BigDecimal creditoAutorizado) {
        this.creditoAutorizado = creditoAutorizado;
    }

    public BigDecimal getCreditoUtilizado() {
        return creditoUtilizado;
    }

    public void setCreditoUtilizado(BigDecimal creditoUtilizado) {
        this.creditoUtilizado = creditoUtilizado;
    }

    public BigDecimal getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(BigDecimal creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
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

    public ArrayList<SaldosDeudas> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<SaldosDeudas> modelo) {
        this.modelo = modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SaldosDeudas getDataAbonar() {
        return dataAbonar;
    }

    public void setDataAbonar(SaldosDeudas dataAbonar) {
        this.dataAbonar = dataAbonar;
    }

    public BigDecimal getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(BigDecimal totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public AbonoCredito getAbono() {
        return abono;
    }

    public void setAbono(AbonoCredito abono) {
        this.abono = abono;
    }

    public String getViewCheque() {
        return viewCheque;
    }

    public void setViewCheque(String viewCheque) {
        this.viewCheque = viewCheque;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public ArrayList<AbonoCredito> getChequesPendientes() {
        return chequesPendientes;
    }

    public void setChequesPendientes(ArrayList<AbonoCredito> chequesPendientes) {
        this.chequesPendientes = chequesPendientes;
    }

    public ArrayList<AbonoCredito> getSelectedchequesPendientes() {
        return selectedchequesPendientes;
    }

    public void setSelectedchequesPendientes(ArrayList<AbonoCredito> selectedchequesPendientes) {
        this.selectedchequesPendientes = selectedchequesPendientes;
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

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public void setPathFileJasper(String pathFileJasper) {
        this.pathFileJasper = pathFileJasper;
    }

    public Date getFechaMasProximaPago() {
        return fechaMasProximaPago;
    }

    public void setFechaMasProximaPago(Date fechaMasProximaPago) {
        this.fechaMasProximaPago = fechaMasProximaPago;
    }

    public BigDecimal getMinimoPago() {
        return minimoPago;
    }

    public void setMinimoPago(BigDecimal minimoPago) {
        this.minimoPago = minimoPago;
    }

    public BigDecimal getSaldoParaLiquidar() {
        return saldoParaLiquidar;
    }

    public void setSaldoParaLiquidar(BigDecimal saldoParaLiquidar) {
        this.saldoParaLiquidar = saldoParaLiquidar;
    }

    public int getNumFiltro() {
        return numFiltro;
    }

    public void setNumFiltro(int numFiltro) {
        this.numFiltro = numFiltro;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

}
