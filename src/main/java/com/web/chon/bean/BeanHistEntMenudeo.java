/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMenudeo;
import com.web.chon.service.IfaceEntradaMenudeoProducto;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceSubProducto;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanHistEntMenudeo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntradaMenudeo ifaceEntradaMenudeo;
    @Autowired
    private IfaceEntradaMenudeoProducto ifaceEntradaMenudeoProducto;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<EntradaMenudeo> lstEntradaMercancia;
    private ArrayList<EntradaMenudeoProducto> lstEntradaMercanciaProdcuto;

    private UsuarioDominio usuario;
    private EntradaMenudeo data;
    private EntradaMenudeo dataSelected;

    private String title;
    private String viewEstate;

    private Date fechaFin;
    private Date fechaInicio;

    private int filtro;
    private Subproducto subProducto;
    private BigDecimal totalKilosDetalle;

    private boolean enableCalendar;

    //variables para PDF
    private Map paramReport = new HashMap();

    private String rutaPDF;
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;

    @PostConstruct
    public void init() {
        //se pone por default el filtro en mes actual
        // se deshabilita en calendario cuando hay una seleccion
        //solo se habilita cuando pongas ingresar fecha mannual
        filtro = 2;
        enableCalendar = true;
        subProducto = new Subproducto();

        data = new EntradaMenudeo();
        dataSelected = new EntradaMenudeo();
        usuario = context.getUsuarioAutenticado();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();

        data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));
        subProducto = new Subproducto();
        data.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        lstEntradaMercancia = new ArrayList<EntradaMenudeo>();
        lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk(), subProducto.getIdSubproductoPk());
        setTitle("Historial Entrada de Mercancia de Menudeo");
        setViewEstate("init");
    }

    public void verificarCombo() {
        if (filtro == -1) {
            //se habilitan los calendarios.
            data.setFechaFiltroInicio(null);
            data.setFechaFiltroFin(null);
            enableCalendar = false;
        } else {
            switch (filtro) {
                case 1:
                    data.setFechaFiltroInicio(new Date());
                    data.setFechaFiltroFin(new Date());
                    break;

                case 2:
                    data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
                    data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));

                    break;
                case 3:
                    data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
                    data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
                    break;
                default:
                    data.setFechaFiltroInicio(null);
                    data.setFechaFiltroFin(null);
                    break;
            }
            enableCalendar = true;
        }
    }

    public void buscar() {
        if (data.getFechaFiltroInicio() == null || data.getFechaFiltroFin() == null) {
            JsfUtil.addErrorMessageClean("Favor de ingresar un rango de fechas");
        } else {
            if (subProducto == null) {
                subProducto = new Subproducto();
                subProducto.setIdProductoFk("");
            }
            lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk(), subProducto.getIdSubproductoPk());

        }
    }

    public void back() {
        setViewEstate("init");
        lstEntradaMercanciaProdcuto = new ArrayList<EntradaMenudeoProducto>();

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void detallesEntradaProducto() {
        setViewEstate("searchById");

        lstEntradaMercanciaProdcuto = ifaceEntradaMenudeoProducto.getEntradaProductoById(data.getIdEmmPk());
        getTotalKilosProducto();
    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                if (data.getFechaFiltroInicio() != null && data.getFechaFiltroFin() != null) {
                    System.out.println("Bandera");
                    lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk(), subProducto.getIdSubproductoPk());
                } else {
                    lstEntradaMercancia = new ArrayList<EntradaMenudeo>();
                }
                break;
            case 1:
                data.setFechaFiltroInicio(new Date());
                data.setFechaFiltroFin(new Date());
                break;

            case 2:
                data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
                data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));

                break;
            case 3:
                data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
                data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
                break;
            default:
                data.setFechaFiltroInicio(null);
                data.setFechaFiltroFin(null);
                break;
        }

    }

    public void getEntradaProductoByIntervalDate() {
        setFechaInicioFin(filtro);
        lstEntradaMercancia = ifaceEntradaMenudeo.getEntradaProductoByIntervalDate(data.getFechaFiltroInicio(), data.getFechaFiltroFin(), data.getIdSucursalFk(), subProducto.getIdSubproductoPk());

    }

    public void getTotalKilosProducto() {
        totalKilosDetalle = new BigDecimal(0);
        for (EntradaMenudeoProducto dominio : lstEntradaMercanciaProdcuto) {
            totalKilosDetalle = totalKilosDetalle.add(dominio.getKilosTotales());
        }

    }

    public void imprimir() {
        setParameterTicket(dataSelected);
        generateReport(dataSelected.getFolio().intValue());
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");

    }

    public void cancelar() {

        ArrayList<EntradaMenudeoProducto> lstEntradaProducto = dataSelected.getListaDetalleProducto();
        //Se recorre la lista de productos de la entrada a cancelar
        for (EntradaMenudeoProducto emp : lstEntradaProducto) {
            ExistenciaMenudeo existenciaMenudeo = null;
            ArrayList<ExistenciaMenudeo> lstExistenciaMenudeo = ifaceExistenciaMenudeo.getExistenciasMenudeoByIdSucursalAndIdSubproducto(dataSelected.getIdSucursalFk(), emp.getIdSubproductoFk());

            //Se recorre aunque no es necesario se podria modificar el metod del servicio para que solo regrese un solo objeto no una lista
            for (ExistenciaMenudeo exm : lstExistenciaMenudeo) {
                existenciaMenudeo = exm;
            }

            //Si el producto no es nulo ni vacio se procede a restar existencias
            if (existenciaMenudeo != null) {
                existenciaMenudeo.setCantidadEmpaque(existenciaMenudeo.getCantidadEmpaque().subtract(emp.getCantidadEmpaque()));
                existenciaMenudeo.setKilos(existenciaMenudeo.getKilos().subtract(emp.getKilosTotales()));
                ifaceExistenciaMenudeo.updateExistenciaMenudeo(existenciaMenudeo);
            }
        }

        if (ifaceEntradaMenudeo.cancelarEntrada(dataSelected.getIdEmmPk()) == 1) {
            JsfUtil.addSuccessMessage("Cancelacion Exitosa.");

        } else {
            JsfUtil.addErrorMessage("No se Cancelo la Entrada.");
        }
        
        buscar();

    }

    private void setParameterTicket(EntradaMenudeo em) {

        paramReport.put("nombreSucursal", em.getNombreSucursal());
        paramReport.put("nombreProvedor", em.getNombreProvedor()+" "+em.getApPaternoProvedor()+" "+em.getApMaternoProvedor());
        paramReport.put("kilosProvedor", em.getKilosProvedor().toString());
        paramReport.put("kilosReales", em.getKilosTotales().toString());
        paramReport.put("comentarios", em.getComentarios());
        paramReport.put("fechaEntrada", TiempoUtil.getFechaDDMMYYYYHHMM(em.getFecha()));
        paramReport.put("nombreRecibidor", em.getNombreUsuarioRecibe());
        paramReport.put("folio", em.getFolio().toString());
        paramReport.put("ID_EMM_FK", em.getIdEmmPk().toString());

        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());

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

            String pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "entradaMercancia" + File.separatorChar + "ReporteEntradaMenudeo.jasper";

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
            JasperPrint jp = JasperFillManager.fillReport(pathFileJasper, paramReport, con);
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, pathFileJasper);
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "entradaMercanciaMenudeo", folio, usuario.getSucId());
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public EntradaMenudeo getData() {
        return data;
    }

    public void setData(EntradaMenudeo data) {
        this.data = data;
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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public ArrayList<EntradaMenudeo> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMenudeo> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public ArrayList<EntradaMenudeoProducto> getLstEntradaMercanciaProdcuto() {
        return lstEntradaMercanciaProdcuto;
    }

    public void setLstEntradaMercanciaProdcuto(ArrayList<EntradaMenudeoProducto> lstEntradaMercanciaProdcuto) {
        this.lstEntradaMercanciaProdcuto = lstEntradaMercanciaProdcuto;
    }

    public BigDecimal getTotalKilosDetalle() {
        return totalKilosDetalle;
    }

    public void setTotalKilosDetalle(BigDecimal totalKilosDetalle) {
        this.totalKilosDetalle = totalKilosDetalle;
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

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public EntradaMenudeo getDataSelected() {
        return dataSelected;
    }

    public void setDataSelected(EntradaMenudeo dataSelected) {
        this.dataSelected = dataSelected;
    }

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
    }
}
