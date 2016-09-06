package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatUsuario;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BeanBuscaVenta implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    
    @Autowired private IfaceCatUsuario ifaceCatUsuario;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired private IfaceCaja ifaceCaja;
    
    private ArrayList<BuscaVenta> model;
    private ArrayList<BuscaVenta> selectedVenta;
    
    private BuscaVenta data;
    private Usuario usuario;
    private UsuarioDominio usuarioDominio;
    private Caja caja;
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    
    private Map paramReport = new HashMap();

    private String title;
    private String number;
    private String rutaPDF;
    private String viewEstate;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    
    private BigDecimal totalVenta;
    
    private boolean statusButtonPagar;
    
    private int idVentaTemporal; //utilizado para comprobacion de venta 
    private static final BigDecimal TIPO = new BigDecimal(1);

    @PostConstruct
    public void init() 
    {
        data = new BuscaVenta();
        model = new ArrayList<BuscaVenta>();
        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(usuarioDominio.getSucId());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        setTitle("Búsqueda de Ventas de Menudeo");
        setViewEstate("init");
        caja = new Caja();
        statusButtonPagar = true;
        reloadCaja();
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
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", data.getIdVenta().intValue(), usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }

    }

    private void setParameterTicket(int idVenta,int folioVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
        for (BuscaVenta venta : model) {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(venta.getNombreSubproducto().toUpperCase());
            productos.add("       " + cantidad + "               " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta, folioVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta,int folioVenta) {

        System.out.println(data.getFechaVenta());

        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(folioVenta));
        paramReport.put("cliente", data.getNombreCliente());
        paramReport.put("vendedor", data.getNombreVendedor());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("labelFecha", "Fecha de Pago:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("estado", "PEDIDO PAGADO");
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("telefonos", usuarioDominio.getTelefonoSucursal());
        
         paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:"+usuarioDominio.getTelefonoSucursal());
        
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
        if (data.getStatusFK() == 2) 
        {
            JsfUtil.addErrorMessageClean("Error la venta con el folio: "+data.getFolioSucursal()+" Ya se encuentra pagada");
          
        } else if (data.getIdVenta().intValue() != idVentaTemporal) 
        {
           JsfUtil.addErrorMessageClean("Error!, No coincide el numero de venta  :" + data.getFolioSucursal() + " con la búsqueda.");

        } else if (data.getStatusFK() == 4)
        {
            JsfUtil.addErrorMessage("Error la venta se encuentra cancelada");
        }
        else {
            try {

                ifaceBuscaVenta.updateVenta(data.getIdVenta().intValue(), usuario.getIdUsuarioPk().intValue());
                searchById();
                JsfUtil.addSuccessMessageClean("La venta se ha pagado exitosamente");
                caja.setMontoMenudeo(caja.getMontoMenudeo().add(totalVenta, MathContext.UNLIMITED));
                caja.setMonto(caja.getMonto().add(totalVenta, MathContext.UNLIMITED));
                ifaceCaja.updateMontoCaja(caja);
                reloadCaja();
                setParameterTicket(data.getIdVenta().intValue(),data.getFolioSucursal().intValue());
                generateReport();
                RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar pagar la venta con el folio:" + data.getIdVenta() + "."));
            }

        }

        //return "buscaVentas";
    }
    
    public void reloadCaja()
    {
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuarioPk(),TIPO);
    }

    @Override
    public void searchById() {
        statusButtonPagar = false;
        System.out.println("id de folio"+data.getFolioSucursal());
        System.out.println("id de sucursal"+usuario.getIdSucursal());
        model = ifaceBuscaVenta.getVentaByfolioAndIdSuc(data.getFolioSucursal().intValue(), usuario.getIdSucursal());
        if (model.isEmpty()) {
            data.setNombreCliente("");
            data.setNombreVendedor("");
            data.setIdVenta(new BigDecimal(0));
            statusButtonPagar = true;
            JsfUtil.addWarnMessage("No se encontraron Registros.");

        } else {
            data.setNombreCliente(model.get(0).getNombreCliente());
            data.setNombreVendedor(model.get(0).getNombreVendedor());
            data.setStatusFK(model.get(0).getStatusFK());
            data.setIdVenta(model.get(0).getIdVenta());
            data.setIdSucursalFk(model.get(0).getIdSucursalFk());
            idVentaTemporal = data.getIdVenta().intValue();
            calculatotalVenta();
            if (data.getStatusFK() == 2) {
                statusButtonPagar = true;
            }
            System.out.println("data:" + data.getIdSucursalFk());
            System.out.println("usuario: " + usuario.getIdSucursal());
            if (data.getIdSucursalFk().equals(new BigDecimal(usuario.getIdSucursal()))) {

                statusButtonPagar = false;
            } else {
                JsfUtil.addWarnMessage("No puedes cobrar el folio de otra sucursal.");
                statusButtonPagar = true;
            }
        }

    }

    public void calculatotalVenta() {
        totalVenta = new BigDecimal(0);

        for (BuscaVenta venta : model) {
            totalVenta = totalVenta.add(venta.getTotal());
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
