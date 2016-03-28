package com.web.chon.bean;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.Utilerias;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la venta de productos
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanVenta implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVenta ifaceVenta;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    IfaceMantenimientoPrecio ifaceMantenimientoPrecio;
    @Autowired
    BeanUsuario beanUsuario;
    private ArrayList<VentaProducto> lstVenta;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Cliente> lstCliente;

    private VentaProducto ventaProducto;
    private Subproducto subProducto;
    private VentaProducto dataRemove;
    private VentaProducto dataEdit;
    private Usuario usuario;
    private Cliente cliente;

    private Map paramReport = new HashMap();

    private String title = "";
    private String nombreEmpaque = "";
    private String viewEstate = "";
    private BigDecimal totalVenta;
    private VentaProducto data;
    private Venta venta;
    private String line = "_______________________________________________\n";
    private String contentTicket = "              COMERCIALIZADORA Y \n"
            + "             EXPORTADORA CHONAJOS\n"
            + "                 S DE RL DE CV\n"
            + "                55-56-40-58-46\n"
            + "                   Bod.  Q85\n"
            + "                 VALE DE VENTA\n"
            + "                {{dateTime}}\n"
            + "Vale No. {{valeNum}}     \n"
            + "C:{{cliente}}\n"
            + "Vendedor:{{vendedor}}\n"
            + "BULT/CAJ    PRODUCTO     PRECIO      TOTAL\n"
            + "{{items}}\n"
            + line
            + "VENTA:        {{total}}\n"
            + "{{totalLetra}}\n\n\n"
            + "               P A G A D O\n\n"
            + "\n" + (char) 27 + (char) 112 + (char) 0 + (char) 10 + (char) 100 + "\n"
            + (char) 27 + "m";

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";

    @PostConstruct
    public void init() {
        usuario = beanUsuario.getUsuario();
        venta = new Venta();
        data = new VentaProducto();
        data.setIdTipoEmpaqueFk(new BigDecimal(-1));
        lstProducto = new ArrayList<Subproducto>();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        lstVenta = new ArrayList<VentaProducto>();

        selectedTipoEmpaque();
        setTitle("Venta de Productos.");
        setViewEstate("init");

    }

    public void selectedTipoEmpaque() {

        for (TipoEmpaque empaque : lstTipoEmpaque) {

            if ((empaque.getNombreEmpaque().equals("Kilos") && data.getIdTipoEmpaqueFk().equals(new BigDecimal(-1))) || data.getIdTipoEmpaqueFk().equals(empaque.getIdTipoEmpaquePk())) {
                data.setIdTipoEmpaqueFk(empaque.getIdTipoEmpaquePk());
                setNombreEmpaque(empaque.getNombreEmpaque());
                break;
            }

        }

        searchById();

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public ArrayList<Usuario> autoCompleteVendedor(String nombreUsuario) {
        lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase());
        return lstUsuario;

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void inserts() {
        int idVenta = 0;
        Venta venta = new Venta();
        try {
            if (!lstVenta.isEmpty() && lstVenta.size() > 0) {

                idVenta = ifaceVenta.getNextVal();

                venta.setIdVentaPk(new BigDecimal(idVenta));
                venta.setIdClienteFk(new BigDecimal(cliente.getId_cliente()));
                venta.setIdVendedorFk(usuario.getIdUsuarioPk());
                int ventaInsertada = ifaceVenta.insertarVenta(venta);
                if (ventaInsertada != 0) {
                    for (VentaProducto producto : lstVenta) {

                        ifaceVentaProducto.insertarVentaProducto(producto, idVenta);
                    }
                    imprimirTicket(idVenta);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "La venta se realizo correctamente."));
                    generateReport();

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al insertar la venta."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Necesitas agregar al menos un producto para realizar la venta."));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));
        }

    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {

        MantenimientoPrecios mantenimentoPrecio = new MantenimientoPrecios();
        int idEmpaque = data.getIdTipoEmpaqueFk() == null ? 0 : data.getIdTipoEmpaqueFk().intValue();
        String idSubProducto = subProducto == null ? "" : (subProducto.getIdSubproductoPk() == null ? "" : subProducto.getIdSubproductoPk());
        mantenimentoPrecio = ifaceMantenimientoPrecio.getMantenimientoPrecioById(idSubProducto.trim(), idEmpaque);

        data.setIdProductoFk(idSubProducto);
        data.setIdTipoEmpaqueFk(new BigDecimal(idEmpaque));
        data.setPrecioProducto(mantenimentoPrecio.getPrecioVenta() == null ? null : mantenimentoPrecio.getPrecioVenta().toBigInteger());
    }

    public void addProducto() {

        VentaProducto venta = new VentaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        DecimalFormat df = new DecimalFormat("#,###.##");
        empaque = getEmpaque(data.getIdTipoEmpaqueFk());

        venta.setCantidadEmpaque(data.getCantidadEmpaque());

        venta.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        venta.setIdVentaProductoPk(data.getIdVentaProductoPk());
        venta.setKilosVenta(data.getKilosVenta());
        venta.setPrecioProducto(data.getPrecioProducto());
        venta.setNombreProducto(subProducto.getNombreSubproducto());
        venta.setIdProductoFk(subProducto.getIdSubproductoPk());
        venta.setNombreEmpaque(empaque.getNombreEmpaque());

        venta.setTotal(new BigDecimal(venta.getPrecioProducto()).multiply(venta.getCantidadEmpaque()));
        lstVenta.add(venta);
        calcularTotalVenta();

        data.reset();
        subProducto = new Subproducto();
        selectedTipoEmpaque();

    }

    private void calcularTotalVenta() {
        setTotalVenta(new BigDecimal(0));
        for (VentaProducto venta : lstVenta) {
            setTotalVenta(getTotalVenta().add(venta.getTotal()));
        }
    }

    private TipoEmpaque getEmpaque(BigDecimal idEmpaque) {
        TipoEmpaque empaque = new TipoEmpaque();

        for (TipoEmpaque tipoEmpaque : lstTipoEmpaque) {
            if (tipoEmpaque.getIdTipoEmpaquePk().equals(idEmpaque)) {
                empaque = tipoEmpaque;
                break;
            }
        }
        return empaque;
    }

    public void remove() {
        lstVenta.remove(dataRemove);
        calcularTotalVenta();
    }

    private void imprimirTicket(int idVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
        for (VentaProducto venta : lstVenta) {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(cantidad + " " + venta.getNombreProducto() + " " + nf.format(venta.getPrecioProducto()) + " " + nf.format(venta.getTotal()));
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues(Utilerias.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta);

    }

    public void imprimir() throws IOException {
//        JasperReport jasperReport;
//        JasperPrint jasperPrint;
        try {
            //se carga el reporte
            File f = new File("C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper");
            f.getCanonicalPath();

            System.out.println("ruta" + f.getAbsolutePath());
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramReport, new JREmptyDataSource());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.exportReport();

//            jasperReport = (JasperReport) JRLoader.loadObject(f);
////           jasperReport = (JasperReport) JRLoader.loadObject("ticket.jasper");
//            //se procesa el archivo jasper
//            jasperPrint = JasperFillManager.fillReport(jasperReport, paramReport, new JREmptyDataSource());
//            //impresion de reportes
//            // TRUE: muestra la ventana de dialogo "preferencias de impresion"
//            JasperPrintManager.
//            JasperPrintManager.getStreamContentFromOutputStream(jasperPrint, false);
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }
    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {

        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(idVenta));
        paramReport.put("cliente", cliente.getNombreCombleto());
        paramReport.put("vendedor", usuario.getNombreCompletoUsuario());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("estado", "POR ENTREGAR");

    }

    public void imprimirDefault() {

        byte[] bytes;
        bytes = contentTicket.getBytes();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(bytes, flavor, null);
        System.out.println(contentTicket);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(doc, attributeSet);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No existen impresoras instaladas."));
        }

    }

    public void editProducto() {

        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdProductoFk());
        autoComplete(dataEdit.getNombreProducto());
        searchById();
        data.setCantidadEmpaque(dataEdit.getCantidadEmpaque());
        data.setIdProductoFk(dataEdit.getIdProductoFk());
        data.setIdTipoEmpaqueFk(dataEdit.getIdTipoEmpaqueFk());
        data.setIdVentaProductoPk(dataEdit.getIdVentaProductoPk());
        data.setKilosVenta(dataEdit.getKilosVenta());
        data.setPrecioProducto(dataEdit.getPrecioProducto());
        data.setNombreProducto(dataEdit.getNombreProducto());
        data.setIdProductoFk(dataEdit.getIdProductoFk());

        viewEstate = "update";

        System.out.println("data :" + data.toString());

    }

    public void updateProducto() {

        TipoEmpaque empaque = new TipoEmpaque();
        empaque = getEmpaque(data.getIdTipoEmpaqueFk());

        dataEdit.setCantidadEmpaque(data.getCantidadEmpaque());
        dataEdit.setIdProductoFk(data.getIdProductoFk());
        dataEdit.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        dataEdit.setIdVentaProductoPk(data.getIdVentaProductoPk());
        dataEdit.setKilosVenta(data.getKilosVenta());
        dataEdit.setPrecioProducto(data.getPrecioProducto());
        dataEdit.setNombreProducto(data.getNombreProducto());
        dataEdit.setIdProductoFk(data.getIdProductoFk());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());

        dataEdit.setTotal(new BigDecimal(dataEdit.getPrecioProducto()).multiply(dataEdit.getCantidadEmpaque()));

        calcularTotalVenta();
        viewEstate = "init";
        data.reset();
    }

    public void cancel() {
        subProducto = new Subproducto();

        viewEstate = "init";

        data.reset();
    }

    public void generateReport() {

        JasperReport compiledTemplate = null;
        JRExporter exporter = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream input = null;
        BufferedOutputStream output = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        try {

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "window.print();");
            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "alert('hola mundo');");

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            exporter.exportReport();

            input = new ByteArrayInputStream(outputStream.toByteArray());
            input = new ByteArrayInputStream(outputStream.toByteArray());

            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(outputStream.toByteArray().length));
            response.setHeader("Content-Disposition", "inline; filename=\"fileName.pdf\"");
            output = new BufferedOutputStream(response.getOutputStream(), 75);

            byte[] buffer = new byte[75];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();

        } catch (Exception exception) {
            /* ... */
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception exception) {
                /* ... */
            }
        }
        facesContext.responseComplete();
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
//            log.error(e.getMessage(), e);
        }
    }

    public ArrayList<VentaProducto> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<VentaProducto> lstVenta) {
        this.lstVenta = lstVenta;
    }

    public VentaProducto getVentaProducto() {
        return ventaProducto;
    }

    public void setVentaProducto(VentaProducto ventaProducto) {
        this.ventaProducto = ventaProducto;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
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

    public VentaProducto getData() {
        return data;
    }

    public void setData(VentaProducto data) {
        this.data = data;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public VentaProducto getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(VentaProducto dataRemove) {
        this.dataRemove = dataRemove;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getContentTicket() {
        return contentTicket;
    }

    public void setContentTicket(String contentTicket) {
        this.contentTicket = contentTicket;
    }

    public VentaProducto getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(VentaProducto dataEdit) {
        this.dataEdit = dataEdit;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @Override
    public String insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
