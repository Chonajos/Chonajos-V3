package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Producto;
import com.web.chon.service.IfaceProducto;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el catlogo de productos
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanSubProducto implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceProducto ifaceProducto;
    
    private ArrayList<Subproducto> model;
    private ArrayList<Subproducto> selectedSubProducto;
    private ArrayList<Producto> lstProducto;

    private String title = "";
    public String viewEstate = "";
    public Subproducto data;
    
    //---Variables de Impresión----//
    private Map paramReport = new HashMap();
    private String rutaPDF;
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private int idSucu;
    private String pathFileJasper = "";

    @PostConstruct
    public void init() {

        data = new Subproducto();
        model = new ArrayList<Subproducto>();
        lstProducto = new ArrayList<Producto>();
        selectedSubProducto = new ArrayList<Subproducto>();
        lstProducto = ifaceProducto.getProductos();
        model = ifaceSubProducto.getSubProductos();
        setTitle("Catalogo de Productos");
        setViewEstate("init");

    }

    @Override
    public String delete() {

        if (!selectedSubProducto.isEmpty()) {
            for (Subproducto producto : selectedSubProducto) {
                try {
                    if (ifaceSubProducto.deleteSubProducto(producto.getIdSubproductoPk()) == 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro eliminado."));
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + producto.getNombreSubproducto() + "."));
                    }

                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + producto.getNombreSubproducto() + "."));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Elija un registro a eliminar."));
        }
        return "producto";
    }
    
    public void imprimirCodigoBarras()
    {
        
        String codigo = "";
        codigo = data.getIdSubproductoPk();
        paramReport.put("codigo", codigo);
        paramReport.put("nombreProducto", data.getNombreSubproducto());
        generateReportBarCode(1,Integer.parseInt(codigo));
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
    }
    public void generateReportBarCode(int idSucursal,int folio) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "codigoBarras" + File.separatorChar + "codigoBarrasMenudeo.jasper";
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
            rutaPDF = UtilUpload.saveFileTemp(bytes, "barCodeMenudeo", folio, idSucursal);
            con.close();
        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            exception.getStackTrace();
        }

    }

    @Override
    public String insert() {
        try {
            data.setIdSubproductoPk(data.getIdProductoFk().concat(TiempoUtil.rellenaEspacios(ifaceSubProducto.getLastIdProducto(data.getIdProductoFk()))));
            data.setNombreSubproducto(getDescripcionCategoria(data.getIdProductoFk()) + " " + data.getNombreSubproducto());
            ifaceSubProducto.insertarSubProducto(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado."));
        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar insertar el registro :" + data.getNombreSubproducto() + "."));
        }

        return "producto";
    }

    @Override
    public String update() {

        try {
            ifaceSubProducto.updateSubProducto(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombreSubproducto() + "."));
        }

        return "producto";
    }

    private String getDescripcionCategoria(String idCategoria) {
        String nombreCategoria = "";

        for (Producto producto : lstProducto) {

            if (producto.getIdProductoPk().trim().equals(idCategoria)) {
                nombreCategoria = producto.getNombreProducto();
                break;
            }
        }

        return nombreCategoria;

    }

    @Override
    public void searchById() {
        setTitle("Editar Productos");
        setViewEstate("searchById");

    }

    public void viewNew() {
        data = new Subproducto();
        setTitle("Alta de Producto");
        setViewEstate("new");
    }

    public void backView() {
        setTitle("Catalogo de Producto");
        setViewEstate("init");
    }

    public ArrayList<Subproducto> getModel() {
        return model;
    }

    public void setModel(ArrayList<Subproducto> model) {
        this.model = model;
    }

    public Subproducto getData() {
        return data;
    }

    public void setData(Subproducto data) {
        this.data = data;
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

    public ArrayList<Subproducto> getSelectedSubProducto() {
        return selectedSubProducto;
    }

    public void setSelectedSubProducto(ArrayList<Subproducto> selectedSubProducto) {
        this.selectedSubProducto = selectedSubProducto;
    }

    public ArrayList<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Producto> lstProducto) {
        this.lstProducto = lstProducto;
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

    public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
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

 
    
}
