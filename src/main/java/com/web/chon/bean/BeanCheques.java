/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceTipoAbono;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
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
public class BeanCheques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    
    private UsuarioDominio usuario;
    private AbonoCredito abonoCreditoPagar;
    
    private ArrayList<AbonoCredito> selectedchequesPendientes;
    private ArrayList<AbonoCredito> listaAbonosAtrasdos;
    private ArrayList<AbonoCredito> listaAbonosHoy;
    private ArrayList<AbonoCredito> listaAbonosSemana;
    private ArrayList<AbonoCredito> listaAbonos;
    private ArrayList<Sucursal> listaSucursales;
    
    private BigDecimal idSucursalFk;
    
    Date fechaInicio;
    Date fechaFin;

    //---Variables de la vista---///
    private String title;
    private String viewEstate;
    //---Variables de la vista---///

    //---Constantes---//
    private static BigDecimal CREDITOFINALIZADO = new BigDecimal(2);
    private static BigDecimal CREDITOACTIVO = new BigDecimal(1);
    private static BigDecimal ABONOREALIZADO = new BigDecimal(1);
    private static BigDecimal ABONOPENDIENTE = new BigDecimal(2);
    //----Constantes--//

    private StringBuffer query;
    private Map paramReport = new HashMap();
    private String rutaPDF;
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private int idSucu;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    
    @PostConstruct
    public void init() {
        fechaInicio = new Date();
        fechaFin = new Date();

//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
//        cal.clear(Calendar.MINUTE);
//        cal.clear(Calendar.SECOND);
//        cal.clear(Calendar.MILLISECOND);
//        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//        Date dia = cal.getTime();
//        System.out.println("La semana Empieza en: " + dia);
//         fechaInicio= new Date();
//        Date fechaFin=dia;
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        usuario = context.getUsuarioAutenticado();
        listaAbonosAtrasdos = new ArrayList<AbonoCredito>();
        listaAbonosHoy = new ArrayList<AbonoCredito>();
        listaAbonosSemana = new ArrayList<AbonoCredito>();
        selectedchequesPendientes = new ArrayList<AbonoCredito>();
        listaAbonos = new ArrayList<AbonoCredito>();
        idSucursalFk = new BigDecimal(usuario.getSucId());
        System.out.println("IdSucursal Logueado: " + idSucursalFk);
        listaAbonosAtrasdos = ifaceAbonoCredito.getChequesPendientes(fechaInicio, fechaFin, idSucursalFk);
        generarQuery();
        
        setTitle("Relación de Cheques No Cobrados ");
        setViewEstate("init");
    }
    
    public void descargar() {
        
        if (listaAbonosAtrasdos.isEmpty()) {
            JsfUtil.addErrorMessageClean("No se tienen registro reporte no generado");
        } else {
            JsfUtil.addSuccessMessage("Reporte Generado");
            setParameterTicket();
            generateReport(4);
            downloadFile();
            
        }
        
    }
    
    public void pagarCheques() {
        if (!selectedchequesPendientes.isEmpty()) {
            for (AbonoCredito abonoCheque : selectedchequesPendientes) {
                BigDecimal totalAbonado = new BigDecimal(0);
                System.out.println("===============================================");
                System.out.println("Cheque: " + abonoCheque);
                abonoCheque.setEstatusAbono(new BigDecimal(1));
                BigDecimal totalAbonadoTemporal = new BigDecimal(0);
                if (ifaceAbonoCredito.update(abonoCheque) == 1) {
                    //enseguida buscar si ya se libero el credito de ese abono.
                    Credito cTemporal = new Credito();
                    cTemporal = ifaceCredito.getTotalAbonado(abonoCheque.getIdCreditoFk());
                    totalAbonadoTemporal = cTemporal.getTotalAbonado().add(abonoCheque.getMontoAbono(), MathContext.UNLIMITED);
                    if (totalAbonadoTemporal.compareTo(cTemporal.getMontoCredito()) >= 0) {
                        ifaceCredito.updateStatus(abonoCheque.getIdCreditoFk(), CREDITOFINALIZADO);
                        JsfUtil.addSuccessMessageClean("Se ha liquidado el crédito exitosamente");
                        // Se liquido con un cheque todo ese credito 
                    }
                    JsfUtil.addSuccessMessageClean("Cheque Cobrado Existosamente");
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema al cobrar los cheques");
                }
            }
            selectedchequesPendientes = new ArrayList<AbonoCredito>();
            listaAbonosAtrasdos = ifaceAbonoCredito.getChequesPendientes(fechaInicio, fechaFin, idSucursalFk);
        } else {
            JsfUtil.addErrorMessageClean("No existen cheques activos o no se ha seleccionado ninguno");
        }
        
    }

    public void buscaCheques() {
        System.out.println("idSucursal: " + idSucursalFk);
        listaAbonosAtrasdos = ifaceAbonoCredito.getChequesPendientes(fechaInicio, fechaFin, idSucursalFk);
        generarQuery();
        System.out.println("QueryBEAN: " + query);
    }
    
    private void setParameterTicket() {

//        paramReport.put("nombreSucursal", usuario.getNombreSucursal());
//        paramReport.put("nombreProvedor", getNombreProvedor());
//        paramReport.put("kilosProvedor", em.getKilosProvedor().toString());
//        paramReport.put("kilosReales", em.getKilosTotales().toString());
        System.out.println("Query: " + query);
        paramReport.put("labelSucursal", usuario.getNombreSucursal());
        paramReport.put("query", query.toString());
//        paramReport.put("nombreRecibidor", usuario.getNombreCompleto());
//        paramReport.put("folio", em.getFolio().toString());
//        paramReport.put("ID_EMM_FK", em.getIdEmmPk().toString());
//        System.out.println("telefonos =======" +usuario.getTelefonoSucursal());
//        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());
//        

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
            
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "Cheques" + File.separatorChar + "RChequesNoCobrados.jasper";
//            Connection conn=null;
//            try 
//            {
//                Class.forName("oracle.jdbc.OracleDriver");
//                conn = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.1.77:1521/xe", "choniTest", "choniTest");
//                System.out.println("Se conecto PERRO !");
//            } catch (SQLException ex) 
//            {
//                ex.getStackTrace();
//            } catch (ClassNotFoundException ex) {
//                ex.getStackTrace();
//            }
            Context initContext;
            Connection con = null;
            try {
                
                javax.sql.DataSource datasource = null;
                
                Context initialContext = new InitialContext();

                // "jdbc/MyDBname" >> is a JNDI Name of DataSource on weblogic
                datasource = (DataSource) initialContext.lookup("DataChon");
                
                try {
                    con = datasource.getConnection();
                    System.out.println("datsource" + con.toString());
                } catch (SQLException ex) {
                    Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NamingException ex) {
                Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, con);
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();
            
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", folio, idSucu);
            con.close();
        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            exception.getStackTrace();
        }
        
    }
    
    public void downloadFile() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            Date hoy = new Date();
            TiempoUtil.getFechaDDMMYYYY(hoy);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + "reporteCheques" + TiempoUtil.getFechaDDMMYYYY(hoy) + ".pdf");
            OutputStream output = response.getOutputStream();
            output.write(outputStream.toByteArray());
            output.close();
            
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
        }
    }
    
    public void generarQuery() {
        
        StringBuffer cadena = new StringBuffer("select ab.* from ABONO_CREDITO ab "
                + "inner join USUARIO u "
                + "on u.ID_USUARIO_PK = ab.ID_USUARIO_FK "
                + "WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')< '" + TiempoUtil.getFechaDDMMYYYY(fechaInicio) + "' "
                + "and ab.ESTATUS=2 and ab.TIPO_ABONO_FK=3 ");
        
        if (idSucursalFk == null || idSucursalFk.equals("")) {
            cadena.append(" order by ab.FECHA_COBRO asc");
            
        } else {
            cadena.append(" and u.ID_SUCURSAL_FK='" + idSucursalFk + "' order by ab.FECHA_COBRO asc");
        }
        query = cadena;
    }
    
    public ArrayList<AbonoCredito> getListaAbonosAtrasdos() {
        return listaAbonosAtrasdos;
    }
    
    public void setListaAbonosAtrasdos(ArrayList<AbonoCredito> listaAbonosAtrasdos) {
        this.listaAbonosAtrasdos = listaAbonosAtrasdos;
    }
    
    public ArrayList<AbonoCredito> getListaAbonosHoy() {
        return listaAbonosHoy;
    }
    
    public void setListaAbonosHoy(ArrayList<AbonoCredito> listaAbonosHoy) {
        this.listaAbonosHoy = listaAbonosHoy;
    }
    
    public ArrayList<AbonoCredito> getListaAbonosSemana() {
        return listaAbonosSemana;
    }
    
    public void setListaAbonosSemana(ArrayList<AbonoCredito> listaAbonosSemana) {
        this.listaAbonosSemana = listaAbonosSemana;
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
    
    public UsuarioDominio getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }
    
    public AbonoCredito getAbonoCreditoPagar() {
        return abonoCreditoPagar;
    }
    
    public void setAbonoCreditoPagar(AbonoCredito abonoCreditoPagar) {
        this.abonoCreditoPagar = abonoCreditoPagar;
    }
    
    public ArrayList<AbonoCredito> getSelectedchequesPendientes() {
        return selectedchequesPendientes;
    }
    
    public void setSelectedchequesPendientes(ArrayList<AbonoCredito> selectedchequesPendientes) {
        this.selectedchequesPendientes = selectedchequesPendientes;
    }
    
    public ArrayList<AbonoCredito> getListaAbonos() {
        return listaAbonos;
    }
    
    public void setListaAbonos(ArrayList<AbonoCredito> listaAbonos) {
        this.listaAbonos = listaAbonos;
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
    
    public String getRutaPDF() {
        return rutaPDF;
    }
    
    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
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
    
    public Map getParamReport() {
        return paramReport;
    }
    
    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }
    
    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }
    
    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }
    
    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }
    
    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }
    
    public StringBuffer getQuery() {
        return query;
    }
    
    public void setQuery(StringBuffer query) {
        this.query = query;
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
    }
    
}
