/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.HistorialCrediticio;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jramirez
 */
@Component
@Scope("view")
public class BeanHistorialCrediticio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired private PlataformaSecurityContext context;

    private String title;
    private String viewEstate;
    private ArrayList<HistorialCrediticio> model;
    private ArrayList<Credito> lstCredito;
    private ArrayList<AbonoCredito> lstAbonos;

    private BigDecimal idClienteBeanFk;
    private Date fechaInicio;
    private Date fechaFin;
    private Cliente cliente;
    private ArrayList<Cliente> lstCliente;
    private static final BigDecimal CERO=new BigDecimal(0);
    private BigDecimal saldoAnterior;
    private BigDecimal saldoActual;
    private BigDecimal totalCargos;
    private BigDecimal totalAbonos;
    private UsuarioDominio usuarioDominio;
    
    
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String number;
    private String rutaPDF;
    private String pathFileJasper = "";
    private Logger logger = LoggerFactory.getLogger(BeanBuscaCredito.class);

    
    @PostConstruct
    public void init() {
        setTitle("Historial Crediticio");
        
        setViewEstate("init");
        model = new ArrayList<HistorialCrediticio>();
        lstCredito = new ArrayList<Credito>();
        lstAbonos = new ArrayList<AbonoCredito>();
        cliente = new Cliente();
        saldoAnterior = CERO;
        totalCargos=CERO;
        totalAbonos=CERO;
        usuarioDominio = context.getUsuarioAutenticado();
        

    }
    public void generarReporte()
    {
         Random random = new Random();
        //Se genera un numero aleatorio para que no traiga el mismo reporte por la cache
        int numberRandom = random.nextInt(999);
        if (cliente != null) {
            setParameterTicketCredito();
            generateReport(numberRandom, "historialCrediticio.jasper");
            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        }else{
            JsfUtil.addErrorMessage("No se puede generar el estado de cuenta.");
        }
    }
    private void setParameterTicketCredito() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("$#,###.##");
        Date date = new Date();
        for(HistorialCrediticio hc:model)
        {
            System.out.println("HC: "+hc.toString());
        }
        JRBeanCollectionDataSource listaAbonosCargos = new JRBeanCollectionDataSource(model);
        paramReport.put("creditoDisponible", df.format(cliente.getCreditoDisponible()));
        paramReport.put("creditoUtilizado", df.format(cliente.getUtilizadoTotal()));
        paramReport.put("cliente", cliente.getNombreCompleto());
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("nombreSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("lstCargosAbonos", listaAbonosCargos);
        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());
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

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketAbonos" + File.separatorChar + nombreTipoTicket;
            JasperPrint jp = null;
            
            jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport);
            
            
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "HistorialCrediticio", folio, usuarioDominio.getSucId());

        } catch (Exception exception) {
            logger.error("Error al generar el reporte "+exception.getMessage(),"Error ",exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Reporte.");
        }

    }

    public void buscar() {
        lstCredito.clear();
        model.clear();
        lstAbonos.clear();
        saldoAnterior = CERO;
        totalCargos=CERO;
        totalAbonos=CERO;
        System.out.println("Cliente: "+cliente.getId_cliente());
        

        lstCredito = ifaceCredito.getHistorialCrediticio(cliente.getId_cliente(), TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin));
        for (Credito c : lstCredito) {
            HistorialCrediticio hc = new HistorialCrediticio();
            hc.setFolioCargo(c.getIdCreditoPk());
            hc.setFechaCargo(c.getFechaInicioCredito());
            hc.setImporteCargo(c.getMontoCredito());
            model.add(hc);
        }
        lstAbonos = ifaceAbonoCredito.getHistorialCrediticio(cliente.getId_cliente(), TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin));

//          for (AbonoCredito ac : lstAbonos) 
//        {
//            boolean bandera = true;
//            HistorialCrediticio hc = new HistorialCrediticio();
//            hc.setFolioAbono(ac.getIdAbonoCreditoPk());
//            hc.setFechaAbono(ac.getFechaAbono());
//            hc.setImporteAbono(ac.getMontoAbono());
//            model.add(hc);
//        }
        for (AbonoCredito ac : lstAbonos) 
        {
            boolean bandera = true;
            HistorialCrediticio hc = new HistorialCrediticio();
            hc.setFolioAbono(ac.getIdAbonoCreditoPk());
            hc.setFechaAbono(ac.getFechaAbono());
            hc.setFechaCargo(ac.getFechaAbono());
            hc.setImporteAbono(ac.getMontoAbono());
            model.add(hc);
            
//            for (HistorialCrediticio temp : model) 
//            {
//                if (TiempoUtil.getFechaDDMMYYYY(hc.getFechaAbono()).equals(TiempoUtil.getFechaDDMMYYYY(temp.getFechaCargo()))) {
//                    temp.setFolioAbono(ac.getIdAbonoCreditoPk());
//                    temp.setFechaAbono(ac.getFechaAbono());
//                    temp.setImporteAbono(ac.getMontoAbono());
//                    bandera = false;
//                }
//                if(temp.getFechaCargo()==null)
//                {
//                    temp.setFechaCargo(temp.getFechaAbono());
//                }
//            }
//            if (bandera) {
//                model.add(hc);
//            }
        }

        Collections.sort(model, new Comparator<HistorialCrediticio>() {
            @Override
            public int compare(HistorialCrediticio o1, HistorialCrediticio o2) {
                    return o1.getFechaCargo().compareTo(o2.getFechaCargo());
                
            }

        });
calculaSaldos();
    }
    public void calculaSaldos()
    {
        saldoActual = CERO;
        BigDecimal tempAbonos = ifaceAbonoCredito.getTotalAbonos(cliente.getId_cliente(), TiempoUtil.getFechaDDMMYYYY(fechaInicio));
        BigDecimal tempCargos = ifaceCredito.getTotalCargos(cliente.getId_cliente(), TiempoUtil.getFechaDDMMYYYY(fechaInicio));
        System.out.println("Temp-Cargos: "+tempCargos);
        System.out.println("Temp-Abonos: "+tempAbonos);
        
        saldoAnterior = tempAbonos.subtract(tempCargos, MathContext.UNLIMITED);
        System.out.println("Saldo Anterior: "+saldoAnterior);
        System.out.println("Saldo Actual: "+saldoActual);
        saldoActual =saldoAnterior;
        totalCargos=CERO;
        totalAbonos=CERO;
        
        for (HistorialCrediticio temp : model) 
            {
                //0-10
                saldoActual = saldoActual.subtract(temp.getImporteCargo() == null ? CERO :temp.getImporteCargo(), MathContext.UNLIMITED);
                //-10+5
                saldoActual = saldoActual.add(temp.getImporteAbono()== null ? CERO :temp.getImporteAbono(), MathContext.UNLIMITED);
                //5
                temp.setSaldos(saldoActual);
                totalAbonos = totalAbonos.add(temp.getImporteAbono()== null ? CERO :temp.getImporteAbono(), MathContext.UNLIMITED);
                totalCargos = totalCargos.add(temp.getImporteCargo() == null ? CERO :temp.getImporteCargo(), MathContext.UNLIMITED);
            }
        System.out.println("Saldo Anterior: "+saldoAnterior);
        System.out.println("Saldo Actual: "+saldoActual);
        
    }

    public void ordenarLista() {

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public ArrayList<HistorialCrediticio> getModel() {
        return model;
    }

    public void setModel(ArrayList<HistorialCrediticio> model) {
        this.model = model;
    }

    public ArrayList<Credito> getLstCredito() {
        return lstCredito;
    }

    public void setLstCredito(ArrayList<Credito> lstCredito) {
        this.lstCredito = lstCredito;
    }

    public ArrayList<AbonoCredito> getLstAbonos() {
        return lstAbonos;
    }

    public void setLstAbonos(ArrayList<AbonoCredito> lstAbonos) {
        this.lstAbonos = lstAbonos;
    }

    public BigDecimal getIdClienteBeanFk() {
        return idClienteBeanFk;
    }

    public void setIdClienteBeanFk(BigDecimal idClienteBeanFk) {
        this.idClienteBeanFk = idClienteBeanFk;
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

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getTotalCargos() {
        return totalCargos;
    }

    public void setTotalCargos(BigDecimal totalCargos) {
        this.totalCargos = totalCargos;
    }

    public BigDecimal getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(BigDecimal totalAbonos) {
        this.totalAbonos = totalAbonos;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
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
    
    
}
