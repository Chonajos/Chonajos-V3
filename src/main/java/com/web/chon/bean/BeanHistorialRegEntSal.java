package com.web.chon.bean;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.dominio.RetardosFaltas;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceRegistroEntradaSalida;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanHistorialRegEntSal implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Autowired
    IfaceRegistroEntradaSalida ifaceRegEntSal;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;
    
    private Logger logger = LoggerFactory.getLogger(BeanHistorialRegEntSal.class);
    
    private RegistroEntradaSalida data;
    private ArrayList<RegistroEntradaSalida> model;
    private ArrayList<RegistroEntradaSalida> lstModelFaltasRetardos;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<RetardosFaltas> lstRetardosFaltas;
    
    private Date fechaFin;
    private Date fechaInicio;
    private ArrayList<Sucursal> listaSucursales;
    private String title;
    private String viewEstate;
    private int filtro;
    private MapModel simpleModel;
    private String puntoCentral;
    
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String rutaPDF;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V3/faltasRetardos.jasper";
    
    public static final double R = 6372.8;
    
    private int faltas = 0;
    private int retardos = 0;
    private String horasExtras = "00:00";
    private BigDecimal diasDescansoTrabajado = new BigDecimal(0);
    
    private final int RETARDOS_EQUIVALE_FALTA = 3;
    
    @PostConstruct
    public void init() {
        
        setTitle("Registros");
        setViewEstate("init");
        
        data = new RegistroEntradaSalida();
        
        model = new ArrayList<RegistroEntradaSalida>();
        simpleModel = new DefaultMapModel();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaUsuarios = new ArrayList<Usuario>();
        lstRetardosFaltas = new ArrayList<RetardosFaltas>();
        
        filtro = 2;
        setFechaFin(context.getFechaSistema());
        data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));
        data.setIdSucursalFk(new BigDecimal(context.getUsuarioAutenticado().getSucId()));
        changeComboUsers();
        
    }
    
    public void verUbicaciones() {
        
        simpleModel = new DefaultMapModel();
        
        LatLng coord1 = new LatLng(data.getLatitudEntrada(), data.getLongitudEntrada());
        LatLng coord2 = new LatLng(data.getLatitudSalida(), data.getLongitudSalida() + 0.00001);
        
        puntoCentral = data.getLatitudEntrada() + "," + data.getLongitudEntrada();
        simpleModel.addOverlay(new Marker(coord1, "Entrada"));
        simpleModel.addOverlay(new Marker(coord2, "Salida"));
        simpleModel.getMarkers().get(0).setIcon("http://www.google.com/mapfiles/dd-start.png");
        simpleModel.getMarkers().get(1).setIcon("http://www.google.com/mapfiles/dd-end.png");
        
    }
    
    private boolean validateLocation(double lat1, double lon1, double lat2, double lon2, double rangoMaximoPermitido) {
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        int metros;
        
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        metros = (int) ((R * c) / 0.00062137);
        
        System.out.println("metros " + metros);
        System.out.println("rangoMaximoPermitido " + rangoMaximoPermitido);
        if (metros > rangoMaximoPermitido) {
            return false;
        }
        return true;
    }
    
    public void changeComboUsers() {
        
        listaUsuarios = ifaceCatUsuario.getUsuariosbyIdSucursal(data.getIdSucursalFk().intValue());
    }
    
    public void getRegistrosByIntervalDate() {
        retardos = 0;
        faltas = 0;
        String horasExtras = "00:00";
        int diasDescansoTrabajado = 0;
        
        String sinRegistro = "--";
        model = new ArrayList<RegistroEntradaSalida>();
        model = ifaceRegEntSal.getRegistros(data.getIdUsuarioFk(), data.getFechaFiltroInicio(), data.getFechaFiltroFin());
//        int faltaPorRetardos = 0;

        for (RegistroEntradaSalida dominio : model) {
            
            if (dominio.isFalta()) {
                faltas++;
            } else if (dominio.isRetardo()) {
                retardos++;
            }

//            double latitud = new Double(19.370436);
//            double longitud = new Double(-99.091470);
//
//            validateLocation(dominio.getLatitudEntrada(), dominio.getLongitudEntrada(), latitud, longitud, 2000L);
        }

        //Se calculan las faltas que se generan por retardos
//        faltaPorRetardos = retardos / RETARDOS_EQUIVALE_FALTA;
        //retardos restantes
//        retardos = retardos % RETARDOS_EQUIVALE_FALTA;
//        faltas += faltaPorRetardos;
    }
    
    public void viewReport() {
        Random random = new Random();
        //Se genera un numero aleatorio para que no traiga el mismo reporte por la cache
        int numberRandom = random.nextInt(999);
        lstRetardosFaltas = new ArrayList<RetardosFaltas>();
        for (Usuario usuario : listaUsuarios) {
            lstRetardosFaltas.add(getFaltasRetardoByIdUsuario(usuario.getIdUsuarioPk(), usuario.getNombreCompletoUsuario()));
        }
        
        setParameter();
        generateReport(numberRandom, "faltasRetardos.jasper", false);
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        
    }
    
    private void setParameter() {
        JRBeanCollectionDataSource collectionFaltasRetardos = new JRBeanCollectionDataSource(lstRetardosFaltas);
        
        paramReport.put("fecha", TiempoUtil.getMonthYear(data.getFechaFiltroInicio()));
        paramReport.put("nombreSucursal", context.getUsuarioAutenticado().getNombreSucursal());
        paramReport.put("lstFaltasRetardos", collectionFaltasRetardos);
        
    }
    
    public void generateReport(int folio, String nombreTipoTicket, boolean emptyDataSource) {
        JRExporter exporter = null;
        
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }
            
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "registroAsistencia" + File.separatorChar + nombreTipoTicket;
            JasperPrint jp = null;
            
            jp = JasperFillManager.fillReport(pathFileJasper, paramReport);
            
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, pathFileJasper);
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            byte[] bytes = outputStream.toByteArray();
            
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ReporteFaltasRetardos", folio, context.getUsuarioAutenticado().getSucId());
            
        } catch (Exception exception) {
            logger.error("Error al generar el reporte " + exception.getMessage(), "Error ", exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Reporte.");
        }
        
    }
    
    public RetardosFaltas getFaltasRetardoByIdUsuario(BigDecimal idUsuario, String nombreCompleto) {

//        int faltaPorRetardos = 0;
        RetardosFaltas retardosFaltas = new RetardosFaltas();

        retardos = 0;
        faltas = 0;
        horasExtras = "00:00";
        diasDescansoTrabajado = new BigDecimal(0);
        
        lstModelFaltasRetardos = new ArrayList<RegistroEntradaSalida>();
        lstModelFaltasRetardos = ifaceRegEntSal.getRegistros(idUsuario, data.getFechaFiltroInicio(), data.getFechaFiltroFin());
        
        for (RegistroEntradaSalida dominio : lstModelFaltasRetardos) {
            diasDescansoTrabajado = diasDescansoTrabajado.add(dominio.getDiasTrabajdosDescanso());
            horasExtras = TiempoUtil.sumarHorasFormatohhmm(horasExtras, dominio.getHoraExtra());
            if (dominio.isFalta()) {
                faltas++;
            } else if (dominio.isRetardo()) {
                retardos++;
            }
            
        }

        //Se calculan las faltas que se generan por retardos
//        faltaPorRetardos = retardos / RETARDOS_EQUIVALE_FALTA;
        //retardos restantes
//        retardos = retardos % RETARDOS_EQUIVALE_FALTA;
//        faltas += faltaPorRetardos;
        retardosFaltas.setNombreUsuario(nombreCompleto);
        retardosFaltas.setFaltas(faltas);
        retardosFaltas.setRetardos(retardos);
        retardosFaltas.setDiasDescansoTrabajado(diasDescansoTrabajado);
        retardosFaltas.setHorasExtras(horasExtras);
        
        return retardosFaltas;
        
    }
    
    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }
    
    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }
    
    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public Date getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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
    
    public RegistroEntradaSalida getData() {
        return data;
    }
    
    public void setData(RegistroEntradaSalida data) {
        this.data = data;
    }
    
    public ArrayList<RegistroEntradaSalida> getModel() {
        return model;
    }
    
    public void setModel(ArrayList<RegistroEntradaSalida> model) {
        this.model = model;
    }
    
    public int getFiltro() {
        return filtro;
    }
    
    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }
    
    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }
    
    public String getPuntoCentral() {
        return puntoCentral;
    }
    
    public void setPuntoCentral(String puntoCentral) {
        this.puntoCentral = puntoCentral;
    }
    
    public int getFaltas() {
        return faltas;
    }
    
    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
    
    public int getRetardos() {
        return retardos;
    }
    
    public void setRetardos(int retardos) {
        this.retardos = retardos;
    }
    
    public String getRutaPDF() {
        return rutaPDF;
    }
    
    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }
    
}
