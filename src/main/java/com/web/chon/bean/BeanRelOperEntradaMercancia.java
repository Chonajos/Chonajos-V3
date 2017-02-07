package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.FileDominio;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntMerProPaq;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoCovenio;
import com.web.chon.util.Constantes;
import com.web.chon.util.FileUtils;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la Relacion de Operaciones para entrada de mercancias
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRelOperEntradaMercancia implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BeanRelOperEntradaMercancia.class);
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceTipoCovenio ifaceCovenio;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceEntMerProPaq ifaceEntMerProPaq;

    private ArrayList<Provedor> lstProvedor;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<EntradaMercancia> lstEntradaMercancia;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;
    private EntradaMercancia data;
    private EntradaMercanciaProducto dataProducto;
    private EntradaMercanciaProducto dataProductoAutoAjuste;
    private EntradaMercanciaProducto dataProductoNuevo;
    private EntradaMercanciaProducto dataProductEdit;
    private UsuarioDominio usuario;
    private Subproducto subProducto;
    private EntradaMercanciaProductoPaquete dataPaquete;
    private EntradaMercanciaProductoPaquete dataPaqueteEliminar;
    
    private FacesContext facesContext;

    private String title;
    private String viewEstate;

    private BigDecimal totalKilos;

    // ----- Variables para Filtros ----//
    private BigDecimal idSucursal;
    private BigDecimal idProvedor;
    private boolean enableCalendar;
    private Date fechaFin;
    private Date fechaInicio;
    private Date fechaFiltroFin;
    private Date fechaFiltroInicio;
    private int filtro;
    private Provedor provedor;
    private BigDecimal carro;

    //---Variables de Impresión----//
    private Map paramReport = new HashMap();
    private String rutaPDF;
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private int idSucu;
    private int expande;

    private List<FileDominio> filesUser = new ArrayList<FileDominio>();

    private InputStream inputStream;
    private int fileSaved = 0;
    private DefaultStreamedContent download;
    private String path = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V3/src/main/webapp/resources/video";//path de prueba comentar cuando se suba al servidor
    private File[] files = null;
    private String carpetaCarro = "";
    private String carpetaProducto = "";
    private String destPath;

    private UploadedFile file;

    private byte[] bytes;

    private String pathFileJasper = "";

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data = new EntradaMercancia();
        dataProducto = new EntradaMercanciaProducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        provedor = new Provedor();
        filtro = 2;
        verificarCombo();
        enableCalendar = true;

        dataPaquete = new EntradaMercanciaProductoPaquete();
        dataPaqueteEliminar = new EntradaMercanciaProductoPaquete();
        dataProductoAutoAjuste = new EntradaMercanciaProducto();
        /*Validacion de perfil administrador*/
        data.setIdSucursalFK(new BigDecimal(usuario.getSucId()));
        idSucursal = new BigDecimal(usuario.getSucId());
        listaTiposConvenio = new ArrayList<TipoConvenio>();
        listaTiposConvenio = ifaceCovenio.getTipos();
        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(new BigDecimal(usuario.getSucId()));
        setTitle("Relación de Operaciónes Entrada de Mercancia Mayoreo");
        setViewEstate("init");
        dataProductoNuevo = new EntradaMercanciaProducto();
        lstProvedor = ifaceCatProvedores.getProvedores();
        buscar();

    }

    public void imprimirCodigoBarras() {
        rutaPDF = null;
        System.out.println("dataProducto: " + dataProducto.toString());
        String codigo = "";
        int folio = 0;
        int idSucursal = 0;
        BigDecimal carro = new BigDecimal(0);
        String nombreProducto = "";
        String nombreEmpaque = "";
        for (EntradaMercancia entrada : lstEntradaMercancia) {
            if (dataProducto.getIdEmFK().intValue() == entrada.getIdEmPK().intValue()) {
                carro = entrada.getIdCarroSucursal();
                nombreEmpaque = dataProducto.getNombreEmpaque();
                nombreProducto = dataProducto.getNombreProducto();
                codigo += entrada.getIdSucursalFK().toString() + "-";
                idSucursal = entrada.getIdSucursalFK().intValue();
                folio = dataProducto.getIdEmpPK().intValue();
                codigo += entrada.getIdCarroSucursal().toString() + "-";
                codigo += dataProducto.getIdSubProductoFK().toString() + "-";
                codigo += dataProducto.getIdTipoEmpaqueFK().toString() + "-";
                codigo += dataProducto.getIdTipoConvenio().toString();
            }
        }
        System.out.println("Codigo: " + codigo);
        paramReport.put("codigo", codigo);
        paramReport.put("carro", carro.toString());
        paramReport.put("producto", nombreProducto);
        paramReport.put("empaque", nombreEmpaque);

        generateReportBarCode(idSucursal, folio);
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
    }

    public void generateReportBarCode(int idSucursal, int folio) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "codigoBarras" + File.separatorChar + "codigoBarras.jasper";
            Context initContext;
            Connection con = null;
            try {
                javax.sql.DataSource datasource = null;
                Context initialContext = new InitialContext();
                datasource = (DataSource) initialContext.lookup("DataChon");

                try {
                    con = datasource.getConnection();
                    //System.out.println("datsource" + con.toString());
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
            rutaPDF = UtilUpload.saveFileTemp(bytes, "barCode", folio, idSucursal);
            con.close();
        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            exception.getStackTrace();
        }

    }

    public void cerrarEntrada() {
        data.setIdStatusFk(new BigDecimal(2));
        if (ifaceEntradaMercancia.update(data) == 1) {
            JsfUtil.addSuccessMessageClean("Carro Cerrado con Éxito");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un problema el cerrar el caro");
        }

    }

    public void imprimirEntrada() {
        setParameterTicket(data);
        generateReport(data.getIdCarroSucursal().intValue());
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");

    }

    private void setParameterTicket(EntradaMercancia em) {
        System.out.println("======================Entrada: " + em.toString());
        paramReport.put("nombreSucursal", usuario.getNombreSucursal());
        paramReport.put("carro", em.getIdCarroSucursal().toString());
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(em.getFecha()));
        paramReport.put("fechaPago", TiempoUtil.getFechaDDMMYYYYHHMM(em.getFechaPago()));
        paramReport.put("folio", em.getFolio());
        paramReport.put("provedor", em.getNombreProvedor());
        paramReport.put("kilosProvedor", em.getKilosTotalesProvedor().toString());
        paramReport.put("kilosBodega", em.getKilosTotales().toString());
        paramReport.put("comentariosGenerales", em.getComentariosGenerales());
        paramReport.put("nombreRecibidor", em.getNombreRecibidor());
        paramReport.put("cantidadProvedor", em.getCantidadEmpaquesProvedor());
        paramReport.put("cantidadBodega", em.getCantidadEmpaquesReales());
        paramReport.put("ID_EM_PK", em.getIdEmPK().toString());

        System.out.println("==================" + em.getIdEmPK());
        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());

    }

    public String getNombreProvedor(EntradaMercancia em) {

        for (Provedor prove : lstProvedor) {

            if (prove.getIdProvedorPK().intValue() == em.getIdProvedorFK().intValue()) {
                return prove.getNombreProvedor();
            }

        }
        return "";
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
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "entradaMayoreo" + File.separatorChar + "EntradaMayoreoV1.jasper";
            Context initContext;
            Connection con = null;
            try {
                javax.sql.DataSource datasource = null;
                Context initialContext = new InitialContext();
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

    public void autoAjustar() {
        //1.- Ajustar Existencias con los Paquetes nuevos
        System.out.println("DataAjuste: " + dataProductoAutoAjuste);
        ExistenciaProducto exis = ifaceNegocioExistencia.getExistenciaByIdEmpFk(dataProductoAutoAjuste.getIdEmpPK());
        exis.setCantidadPaquetes(dataProductoAutoAjuste.getCantPaquetes());
        exis.setKilosTotalesProducto(dataProductoAutoAjuste.getKilosPaquetes());
        System.out.println("-------------------------------------------------");
        System.out.println(exis.toString());
        if (ifaceNegocioExistencia.updateCantidadKilo(exis) == 1) {
            //dataProductoAutoAjuste.setIdBodegaFK();
            dataProductoAutoAjuste.setKilosTotalesProducto(dataProductoAutoAjuste.getKilosReales());
            dataProductoAutoAjuste.setCantidadPaquetes(dataProductoAutoAjuste.getCantidadReales());
            if (ifaceEntradaMercanciaProducto.update(dataProductoAutoAjuste) == 1) {
                ifaceEntMerProPaq.updatePaquete(dataProductoAutoAjuste.getIdEmpPK());
                EntradaMercancia em = ifaceEntradaMercancia.getEntradaByIdEmPFk(dataProductoAutoAjuste.getIdEmpPK());
                BigDecimal to = new BigDecimal(0);
                BigDecimal ca = new BigDecimal(0);
                for (EntradaMercanciaProducto p : em.getListaProductos()) {
                    to = to.add(p.getKilosTotalesProducto(), MathContext.UNLIMITED);
                    ca = ca.add(p.getCantidadPaquetes(), MathContext.UNLIMITED);
                }

                em.setKilosTotales(to);
                em.setCantidadEmpaquesReales(ca);
                ifaceEntradaMercancia.updateEntradaMercancia(em);
                buscar();

                JsfUtil.addSuccessMessageClean("Se han actualizado los inventarios correctamente con auto-ajuste");
            } else {
                JsfUtil.addErrorMessageClean("Ha ocurrido un error al actualizar entrada de mercancia con auto-ajuste");
            }
        } else {
            JsfUtil.addErrorMessageClean("Ha ocurrido un error al actualizar inventarios con auto-ajuste");
        }

    }

    public void cancelarPaquete() {
        System.out.println("DataPaqueteEliminar: " + dataPaqueteEliminar.toString());
        if (ifaceEntMerProPaq.eliminarPaquete(dataPaqueteEliminar.getIdEmPP()) == 1) {
            JsfUtil.addSuccessMessageClean("Paquete Eliminado con Éxito");
            buscar();
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error");
        }

    }

    public void verificarCombo() {
        if (filtro == -1) {
            //se habilitan los calendarios.
            //fechaFiltroInicio = null;
            //fechaFiltroFin = null;
            enableCalendar = false;
        } else {
            switch (filtro) {
                case 1:
                    fechaFiltroInicio = new Date();
                    fechaFiltroFin = new Date();
                    carro = null;
                    break;
                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(new Date());
                    carro = null;
                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
                    carro = null;
                    break;
                default:
                    fechaFiltroFin = null;
                    fechaFiltroFin = null;
                    carro = null;
                    break;
            }
            enableCalendar = true;
        }
    }

    public void buscar() {
        if ((fechaFiltroInicio == null || fechaFiltroFin == null) && carro == null) {
            JsfUtil.addErrorMessageClean("Favor de ingresar un rango de fechas");
        } else {
            if (provedor == null) {
                provedor = new Provedor();
                provedor.setIdProvedorPK(null);
            }
            BigDecimal idProvedor = provedor == null ? null : provedor.getIdProvedorPK();
            if (carro != null) {
                fechaInicio = fechaFiltroInicio;
                fechaFin = fechaFiltroFin;
                fechaFiltroFin = null;
                fechaFiltroInicio = null;
            }
            lstEntradaMercancia = ifaceEntradaMercancia.getEntradaProductoByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal, idProvedor, carro);
            verificarCombo();
        }
    }

    public void registrarPaquete() {
        System.out.println("Paquete: " + dataPaquete.toString());
        if (dataPaquete.getPesoNeto() == null || dataPaquete.getPaquetes() == null || dataPaquete.getKilos() == null) {
            JsfUtil.addErrorMessageClean("LLenar todos los datos del paquete");
        } else {
            dataPaquete.setIdEmPP(new BigDecimal(ifaceEntMerProPaq.getNextVal()));
            dataPaquete.setIdEmpFK(dataProducto.getIdEmpPK());
            dataPaquete.setIdStatusFk(new BigDecimal(1));
            if (ifaceEntMerProPaq.insertPaquete(dataPaquete) == 1) {
                JsfUtil.addSuccessMessageClean("Paquete agregado correctamente");
                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un problema al agregar el paquete");
            }
        }

    }

    public void calculaPesoNetoPaquete() {
        if (dataPaquete.getKilos() != null && dataPaquete.getTara() != null) {

            BigDecimal h = dataPaquete.getKilos().subtract(dataPaquete.getTara(), MathContext.UNLIMITED);
            //dataProducto.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED));
            int t = h.compareTo(new BigDecimal(0));
            if (t == 0 || t < 0) {
                JsfUtil.addErrorMessageClean("Cantidad en kilos igual a cero o negativo");
            } else {
                dataPaquete.setPesoNeto(h);
            }
        }
    }

    public void calculaPesoNeto() {
        System.out.println("Entro a metodo");
        if (dataProductoNuevo.getKilosTotalesProducto() != null && dataProductoNuevo.getPesoTara() != null) {

            BigDecimal h = dataProductoNuevo.getKilosTotalesProducto().subtract(dataProductoNuevo.getPesoTara(), MathContext.UNLIMITED);
            //dataProducto.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED));

            int t = h.compareTo(new BigDecimal(0));
            if (t == 0 || t < 0) {
                JsfUtil.addErrorMessageClean("Cantidad en kilos igual a cero o negativo");
            } else {
                dataProductoNuevo.setPesoNeto(h);
            }
        }
    }

    public void agregarProducto() {
        dataProductoNuevo.setIdEmpPK(new BigDecimal(ifaceEntradaMercanciaProducto.getNextVal()));
        dataProductoNuevo.setIdEmFK(data.getIdEmPK());
        dataProductoNuevo.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        dataProductoNuevo.setKilosTotalesProducto(dataProductoNuevo.getPesoNeto());
        System.out.println("Nuevo Producto: " + dataProductoNuevo.toString());
        if (ifaceEntradaMercanciaProducto.insertEntradaMercanciaProducto(dataProductoNuevo) != 0) {
            ExistenciaProducto ep = new ExistenciaProducto();
            ep.setIdExistenciaProductoPk(new BigDecimal(ifaceNegocioExistencia.getNextVal()));
            ep.setIdSubProductoFK(dataProductoNuevo.getIdSubProductoFK());
            ep.setIdTipoEmpaqueFK(dataProductoNuevo.getIdTipoEmpaqueFK());
            ep.setKilosTotalesProducto(dataProductoNuevo.getKilosTotalesProducto());
            ep.setCantidadPaquetes(dataProductoNuevo.getCantidadPaquetes());
            //ep.setComentarios(producto.getComentarios());
            ep.setIdBodegaFK(dataProductoNuevo.getIdBodegaFK());
            ep.setIdTipoConvenio(dataProductoNuevo.getIdTipoConvenio());
            ep.setPrecio(dataProductoNuevo.getPrecio());
            ep.setKilospromprod(dataProductoNuevo.getKilospromprod());
            ep.setIdSucursal(data.getIdSucursalFK());
            ep.setIdProvedor(data.getIdProvedorFK());
            ep.setIdEntradaMercanciaProductoFK(dataProductoNuevo.getIdEmpPK());
            if (ifaceNegocioExistencia.insertExistenciaProducto(ep) == 1) {
                JsfUtil.addSuccessMessageClean("El producto se ha agregado correctamente");
                EntradaMercancia em = ifaceEntradaMercancia.getEntradaByIdEmPFk(dataProductoNuevo.getIdEmpPK());
                BigDecimal to = new BigDecimal(0);
                BigDecimal ca = new BigDecimal(0);
                for (EntradaMercanciaProducto p : em.getListaProductos()) {
                    to = to.add(p.getKilosTotalesProducto(), MathContext.UNLIMITED);
                    ca = ca.add(p.getCantidadPaquetes(), MathContext.UNLIMITED);
                }

                em.setKilosTotales(to);
                em.setCantidadEmpaquesReales(ca);
                ifaceEntradaMercancia.updateEntradaMercancia(em);
                buscar();

            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al insertar existencias de producto nuevo");
            }

        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un problema al insertar el nuevo producto");
        }
    }

    public void editarProducto() {

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void cancelarProducto() {
        //Primero eliminamos la existencia:
        ExistenciaProducto ep = new ExistenciaProducto();
        ep.setIdEntradaMercanciaProductoFK(dataProducto.getIdEmpPK());

        VentaProductoMayoreo venta = new VentaProductoMayoreo();
        venta = ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(dataProducto.getIdEmpPK());
        if (venta.getTotalVenta().intValue() == 0) {
            if (ifaceNegocioExistencia.deleteExistenciaProducto(ep) == 1) {
                System.out.println("Se elimino la existencia producto");
                if (ifaceEntradaMercanciaProducto.deleteEntradaMercanciaProducto(dataProducto) == 1) {
                    JsfUtil.addSuccessMessageClean("Se ha eliminado el producto correctamente");
                    buscar();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al intentar borrar el producto");
                }
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al eliminar la existencia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Ya se tienen ventas registradas, no se puede eliminar");
        }
    }

    public void cancelarEntrada() {
        boolean bandera = false;
        if (!data.getListaProductos().isEmpty()) {
            for (EntradaMercanciaProducto items : data.getListaProductos()) {
                ExistenciaProducto ep = new ExistenciaProducto();
                ep.setIdEntradaMercanciaProductoFK(items.getIdEmpPK());
                VentaProductoMayoreo venta = new VentaProductoMayoreo();
                venta = ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(items.getIdEmpPK());
                if (venta.getTotalVenta().intValue() == 0) {
                    if (ifaceNegocioExistencia.deleteExistenciaProducto(ep) == 1) {
                        System.out.println("Se elimino la existencia producto");
                        if (ifaceEntradaMercanciaProducto.deleteEntradaMercanciaProducto(items) == 1) {
                            JsfUtil.addSuccessMessageClean("Se ha eliminado el producto correctamente");
                            buscar();
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrio un error al intentar borrar el producto");
                            bandera = true;
                        }
                    } else {
                        bandera = true;
                    }
                } else {
                    bandera = true;
                    JsfUtil.addErrorMessageClean("Ya se tienen ventas registradas de este producto, no se puede eliminar");
                }
            }

        }
        if (bandera) {
            System.out.println("Al menos un producto tiene ventas y no se puede eliminar.");
            JsfUtil.addErrorMessageClean("Al menos un producto del carro tiene ventas, no se puede eliminar");
        } else {
            System.out.println("Se han eliminado todos los productos del carro");
            if (ifaceEntradaMercancia.deleteEntradaMercancia(data) == 1) {
                JsfUtil.addSuccessMessageClean("Se ha cancelado la entrada de mercancia correctamente");
                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al cancelar la entrada de mercancia");
            }

        }
        System.out.println("Despues de eliminar los items eliminar la entrada general");

    }

    public void onRowEdit(RowEditEvent event) {

        dataProductEdit = (EntradaMercanciaProducto) event.getObject();
        boolean cambioProducto = false;
        BigDecimal cantidadAnterior = new BigDecimal(0);
        BigDecimal kilosAnterior = new BigDecimal(0);
        EntradaMercanciaProducto epAnterio = ifaceEntradaMercanciaProducto.getEntradaMercanciaProductoByIdEmpPk(dataProductEdit.getIdEmpPK());
        cantidadAnterior = epAnterio.getCantidadPaquetes();
        kilosAnterior = epAnterio.getKilosTotalesProducto();

        if (dataProductEdit.getIdSubProductoFK().equals(dataProductEdit.getSubProducto().getIdSubproductoPk())) {
            System.out.println("son iguales");
        } else {
            System.out.println("son diferentes");
            dataProductEdit.setIdSubProductoFK(dataProductEdit.getSubProducto().getIdSubproductoPk());
            cambioProducto = true;
        }
        boolean totalVentas = false;
        if (cambioProducto == true) {
            VentaProductoMayoreo venta = new VentaProductoMayoreo();
            venta = ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(dataProductEdit.getIdEmpPK());

            if (venta.getTotalVenta().intValue() == 0) {
                totalVentas = false;
                System.out.println("No existen ventas");
            } else {
                totalVentas = true;
                System.out.println("Existen Ventas");
            }
        }
        if (totalVentas == false) {
            if (ifaceEntradaMercanciaProducto.update(dataProductEdit) == 1) {
                JsfUtil.addSuccessMessageClean("Se actualizo correctamente el producto");
                System.out.println("Se actualizo correctamente");
                ExistenciaProducto ep = new ExistenciaProducto();
                ep = ifaceNegocioExistencia.getExistenciaByIdEmpFk(dataProductEdit.getIdEmpPK());
                ep.setIdSubProductoFK(dataProductEdit.getIdSubProductoFK());
                ep.setIdTipoEmpaqueFK(dataProductEdit.getIdTipoEmpaqueFK());
                ep.setIdBodegaFK(dataProductEdit.getIdBodegaFK());
                ep.setIdTipoConvenio(dataProductEdit.getIdTipoConvenio());
                ep.setIdEntradaMercanciaProductoFK(dataProductEdit.getIdEmpPK());
                ep.setConvenio(dataProductEdit.getPrecio());
                System.out.println("Entrada Anterior kilos: " + kilosAnterior);
                System.out.println("Entrada Anterior Cantidad: " + cantidadAnterior);
                System.out.println("Entrada Nueva kilos: " + dataProductEdit.getKilosTotalesProducto());
                System.out.println("Entrada Nueva Cantidad: " + dataProductEdit.getCantidadPaquetes());
                ExistenciaProducto e = ifaceNegocioExistencia.getExistenciaByIdEmpFk(dataProductEdit.getIdEmpPK());
                System.out.println("Existencia Anterior kilos: " + e.getKilosTotalesProducto());
                System.out.println("Existencia Anterior Cantidad: " + e.getCantidadPaquetes());
                e.setKilosTotalesProducto(e.getKilosTotalesProducto().subtract((kilosAnterior.subtract(dataProductEdit.getKilosTotalesProducto(), MathContext.UNLIMITED)), MathContext.UNLIMITED));
                e.setCantidadPaquetes(e.getCantidadPaquetes().subtract((cantidadAnterior.subtract(dataProductEdit.getCantidadPaquetes(), MathContext.UNLIMITED)), MathContext.UNLIMITED));
                System.out.println("Existencia Nueovs kilos: " + e.getKilosTotalesProducto());
                System.out.println("Existencia Nuevos Cantidad: " + e.getCantidadPaquetes());
                ep.setKilosTotalesProducto(e.getKilosTotalesProducto());
                ep.setCantidadPaquetes(e.getCantidadPaquetes());
                ep.setIdExistenciaProductoPk(e.getIdExistenciaProductoPk());
                System.out.println("--------------Existencia Actualizar------------");
                System.out.println(ep.toString());
                if (ifaceNegocioExistencia.update(ep) == 1) {
                    JsfUtil.addSuccessMessageClean("Actualización de datos correcta");
                    recalcularKilosEmpaques(dataProductEdit);
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un problema al actualizar existencias");
                }
                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un problema al actualizar entrada de mercancia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Producto con ventas registradas, no se puede cambiar");
        }

    }

    public void recalcularKilosEmpaques(EntradaMercanciaProducto dataProductEdit) {
        BigDecimal ca = new BigDecimal(0);
        BigDecimal ki = new BigDecimal(0);
        for (EntradaMercancia entrada : lstEntradaMercancia) {
            if (entrada.getIdEmPK().intValue() == dataProductEdit.getIdEmFK().intValue()) {

                for (EntradaMercanciaProducto producto : entrada.getListaProductos()) {
                    ca = ca.add(producto.getCantidadPaquetes(), MathContext.UNLIMITED);
                    ki = ki.add(producto.getKilosTotalesProducto(), MathContext.UNLIMITED);

                }
                entrada.setKilosTotales(ki);
                entrada.setCantidadEmpaquesReales(ca);
                ifaceEntradaMercancia.updateEntradaMercancia(entrada);
                break;
            }

        }

    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void cancel() {
        setViewEstate("init");
        //lstEntradaMercanciaProdcuto = new ArrayList<EntradaMercanciaProducto>();
        lstEntradaMercancia = new ArrayList<EntradaMercancia>();
        data.reset();
        provedor = null;
        filtro = -1;
        fechaInicio = null;
        fechaFin = null;
        buscar();

    }

//    public void detallesEntradaProducto() 
//    {
//        setViewEstate("searchById");
//
//        lstEntradaMercanciaProdcuto = ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(data.getIdEmPK());
//        getTotalKilosProducto();
//    }
//    public void getTotalKilosProducto() {
//        totalKilos = new BigDecimal(0);
//        for (EntradaMercanciaProducto dominio : lstEntradaMercanciaProdcuto) 
//        {
//            totalKilos = totalKilos.add(dominio.getKilosTotalesProducto());
//        }
//
//    }
    public ArrayList<Provedor> autoCompleteProvedor(String nombreProvedor) {
        lstProvedor = ifaceCatProvedores.getProvedorByNombreCompleto(nombreProvedor.toUpperCase());
        return lstProvedor;

    }

    public void expandedRow(int expanded) {
        setExpande(expanded);
    }

    public void handleFileUpload(FileUploadEvent event) {

        EntradaMercanciaProducto item = (EntradaMercanciaProducto) event.getComponent().getAttributes().get("item");

        String fileName = event.getFile().getFileName().trim();
        String pathServer = null;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (servletContext.getRealPath("") == null) {
            pathServer = Constantes.PATHSERVER;
        } else {
            pathServer = path;
        }

        if (existFileOnFolder(fileName)) {
            JsfUtil.addErrorMessage("Archivo existente, seleccione otro.");
        } else {
            UploadedFile uploadedFile = (UploadedFile) event.getFile();
            InputStream inputStr = null;
            InputStream inputStrBd = null;
            try {
                logger.debug("selecciona archivo");
                inputStr = uploadedFile.getInputstream();
                inputStrBd = uploadedFile.getInputstream();
            } catch (IOException e) {
                JsfUtil.addErrorMessage("No se permite guardar valores nulos. ");
                manageException(e);
            }
            try {

                FileUtils.creaCarpeta(pathServer + "/" + "entradaProducto" + "/");

            } catch (Exception e) {

            }

            destPath = pathServer + "/" + "entradaProducto" + "/" + fileName;

            try {

                FileUtils.guardaArchivo(destPath, inputStr);
                bytes = IOUtils.toByteArray(inputStrBd);
                item.setVideoByte(bytes);

                FacesMessage message = new FacesMessage("exito", "El archivo "
                        + event.getFile().getFileName().trim()
                        + " fue cargado.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                fileSaved = 1;

                String strPath = "";
                strPath = ".." + File.separatorChar + "resources" + File.separatorChar + "video" + File.separatorChar + "entradaProducto" + File.separatorChar + fileName;

                item.setUrlVideo(strPath);

                if (ifaceEntradaMercanciaProducto.updateVideo(item) > 0) {
                    JsfUtil.addSuccessMessage("Video Guardado Correctamente.");
                } else {
                    JsfUtil.addErrorMessage("No se pudo Guardar el Video.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ** Archivo ****
     */
    private boolean existFileOnFolder(String fileName) {
        boolean exist = false;
        getFiles();
        for (FileDominio o : filesUser) {
            if (fileName.equals(o.getFileName())) {
                exist = true;
            }
        }
        return exist;
    }

    public void getFiles() {
        File folder = new File(path + carpetaCarro.trim());
        if (folder.exists()) {
            files = new File(path + carpetaCarro.trim()).listFiles();
            if (files.length > 0) {
                filesUser.clear();
                for (File file : files) {
                    FileDominio fileDto = new FileDominio();
                    if (file.isFile()) {
                        fileDto.setFileName(file.getName());
                        filesUser.add(fileDto);
                    }
                }
            }
        } else {
            try {
                FileUtils.creaCarpeta(path + carpetaCarro.trim() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existFile(String filePath) throws Exception {
        File file = new File(filePath);
        return file.exists();
    }

    public void descarga(Subproducto dominio) throws Exception {
        try {

            if (dominio.getUrlImagenSubproducto() != null && !dominio.getUrlImagenSubproducto().isEmpty()) {
                File file = new File(dominio.getUrlImagenSubproducto().trim());
                InputStream input = new FileInputStream(file);
                setDownload(new DefaultStreamedContent(input, file.getName(), file.getName()));
            }

        } catch (FileNotFoundException e) {
            JsfUtil.addErrorMessage(e.toString());
        }

    }

    public void deleteFile(String filePath) throws Exception {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String insert() {

        return "relacionOperaciones";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void searchById() {
        setViewEstate("searchById");

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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public EntradaMercancia getData() {
        return data;
    }

    public void setData(EntradaMercancia data) {
        this.data = data;
    }

    public ArrayList<EntradaMercancia> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public Provedor getProvedor() {
        return provedor;
    }

    public void setProvedor(Provedor provedor) {
        this.provedor = provedor;
    }

    public ArrayList<Provedor> getLstProvedor() {
        return lstProvedor;
    }

    public void setLstProvedor(ArrayList<Provedor> lstProvedor) {
        this.lstProvedor = lstProvedor;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public EntradaMercanciaProducto getDataProducto() {
        return dataProducto;
    }

    public void setDataProducto(EntradaMercanciaProducto dataProducto) {
        this.dataProducto = dataProducto;
    }

    public EntradaMercanciaProducto getDataProductEdit() {
        return dataProductEdit;
    }

    public void setDataProductEdit(EntradaMercanciaProducto dataProductEdit) {
        this.dataProductEdit = dataProductEdit;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }

    public ArrayList<TipoConvenio> getListaTiposConvenio() {
        return listaTiposConvenio;
    }

    public void setListaTiposConvenio(ArrayList<TipoConvenio> listaTiposConvenio) {
        this.listaTiposConvenio = listaTiposConvenio;
    }

    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public EntradaMercanciaProductoPaquete getDataPaquete() {
        return dataPaquete;
    }

    public void setDataPaquete(EntradaMercanciaProductoPaquete dataPaquete) {
        this.dataPaquete = dataPaquete;
    }

    public EntradaMercanciaProducto getDataProductoNuevo() {
        return dataProductoNuevo;
    }

    public void setDataProductoNuevo(EntradaMercanciaProducto dataProductoNuevo) {
        this.dataProductoNuevo = dataProductoNuevo;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdProvedor() {
        return idProvedor;
    }

    public void setIdProvedor(BigDecimal idProvedor) {
        this.idProvedor = idProvedor;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
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

    public EntradaMercanciaProductoPaquete getDataPaqueteEliminar() {
        return dataPaqueteEliminar;
    }

    public void setDataPaqueteEliminar(EntradaMercanciaProductoPaquete dataPaqueteEliminar) {
        this.dataPaqueteEliminar = dataPaqueteEliminar;
    }

    public EntradaMercanciaProducto getDataProductoAutoAjuste() {
        return dataProductoAutoAjuste;
    }

    public void setDataProductoAutoAjuste(EntradaMercanciaProducto dataProductoAutoAjuste) {
        this.dataProductoAutoAjuste = dataProductoAutoAjuste;
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

    public int getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(int idSucu) {
        this.idSucu = idSucu;
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

    public BigDecimal getCarro() {
        return carro;
    }

    public void setCarro(BigDecimal carro) {
        this.carro = carro;
    }

    public int getExpande() {
        return expande;
    }

    public void setExpande(int expande) {
        this.expande = expande;
    }

    public DefaultStreamedContent getDownload() {
        return download;
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
