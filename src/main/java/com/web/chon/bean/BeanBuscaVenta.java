package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.UtilUpload;
import com.web.chon.util.TiempoUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BeanBuscaVenta implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    private ArrayList<BuscaVenta> model;
    private String title;
    private String viewEstate;
    private BigDecimal totalVenta;
    private BuscaVenta data;
    private Map paramReport = new HashMap();
    private ArrayList<BuscaVenta> dataModel; //Modelo con un solo Objeto
    private boolean statusButtonPagar;
    private int idVentaTemporal; //utilizado para comprobacion de venta
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
   private ByteArrayOutputStream outputStream;
   private StreamedContent media;

    private ArrayList<BuscaVenta> selectedVenta;

    @PostConstruct
    public void init() {
        data = new BuscaVenta();
        model = new ArrayList<BuscaVenta>();
//        dataModel = ifaceBuscaVenta.getVentaById(0);

        setTitle("Búsqueda de Ventas");
        setViewEstate("init");
        statusButtonPagar = true;
    }
    
    
    public void generateReport() 
    {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            
            String temporal ="";
            if(servletContext.getRealPath("")==null)
            {
                temporal = Constantes.PATHSERVER;
            }
            else
            {
                temporal = servletContext.getRealPath("");
            }
            
            pathFileJasper = temporal+ File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//          exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf");
            System.out.println("ruta de jasper :" + pathFileJasper);
            System.out.println("ruta de jasper :" + rutaPDF);
        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            System.out.println("ruta de jasper :" + pathFileJasper);
            System.out.println("ruta de jasper :" + rutaPDF);
        }

    }
    
    private void setParameterTicket(int idVenta) 
    {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
        for (BuscaVenta venta : model) 
        {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(venta.getNombreSubproducto().toUpperCase());
            productos.add("       "+cantidad + "               " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {

        System.out.println(data.getFechaVenta());
        //
        //Utilerias.getFechaDDMMYYYYHHMM(model.get(0).getFechaVenta())
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(idVenta));
        paramReport.put("cliente", data.getNombreCliente());
        paramReport.put("vendedor", data.getNombreVendedor());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("estado", "PEDIDO PAGADO");
        //paramReport.put("bodega", idSucu);

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

    public void updateVenta() {
        if (data.getStatusFK() == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La venta :" + data.getIdVenta() + " Ya se encuentra pagada."));

        }
        if (data.getIdVenta().intValue() != idVentaTemporal) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No coincide el numero de venta  :" + data.getIdVenta() + " con la búsqueda."));

        } else {
            try {
                ifaceBuscaVenta.updateCliente(data.getIdVenta().intValue());
                searchById();
                
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Venta Pagada"));
                setParameterTicket(data.getIdVenta().intValue());
                
                generateReport();
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar pagar la venta con el folio:" + data.getIdVenta() + "."));
            }

        }

        //return "buscaVentas";
    }

    @Override
    public void searchById() {
        statusButtonPagar = false;

        model = ifaceBuscaVenta.getVentaById(data.getIdVenta().intValue());
        if (model.isEmpty()) 
        {
           data.setNombreCliente("");
           data.setNombreVendedor("");
           data.setIdVenta(new BigDecimal(0));
           statusButtonPagar = true;

        } else 
        {
            data.setNombreCliente(model.get(0).getNombreCliente());
            data.setNombreVendedor(model.get(0).getNombreVendedor());
            data.setStatusFK(model.get(0).getStatusFK());
            data.setIdVenta(model.get(0).getIdVenta());
            idVentaTemporal = data.getIdVenta().intValue();
            calculatotalVenta();
            if(data.getStatusFK()==2)
            {
                statusButtonPagar=true;
            }
            /*if (data.getIdVenta() == 0) 
            {
                statusButtonPagar = true;
                calculatotalVenta();
            }*/
            

        }

    }

    public void calculatotalVenta() {
        totalVenta = new BigDecimal(0);

        for (BuscaVenta venta : model) {
            totalVenta = totalVenta.add(new BigDecimal(venta.getTotal()));
        }
    }

    public ArrayList<BuscaVenta> getModel() {
        return model;
    }

    public void setModel(ArrayList<BuscaVenta> model) {
        this.model = model;
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

    public BuscaVenta getData() {
        return data;
    }

    public void setData(BuscaVenta data) {
        this.data = data;
    }

    public ArrayList<BuscaVenta> getSelectedVenta() {
        return selectedVenta;
    }

    public void setSelectedVenta(ArrayList<BuscaVenta> selectedVenta) {
        this.selectedVenta = selectedVenta;
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
}
