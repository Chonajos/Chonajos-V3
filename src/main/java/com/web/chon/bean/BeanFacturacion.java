/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author jramirez
 */
@Component
@Scope("view")
public class BeanFacturacion implements Serializable 
{
    
    private static final long serialVersionUID = 1L;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatCliente ifaceCatCliente;

    //--Variables Generales Bean--//
    private String title;
    private String viewEstate;
    private UsuarioDominio usuario;
    private int filtro;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private boolean enableCalendar;
    private BigDecimal idStatusFk;
    private ArrayList<Cliente> lstCliente;
    private Cliente cliente;
    private BigDecimal folioFactura;
    private BigDecimal folioVenta;
    private BigDecimal idSucursalFk;
    private ArrayList<Sucursal> listaSucursales;
    
    //--Variables Para Generar PDF--//
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "";
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    
    
    
    
    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
    }
    
    public void buscarFacturas()
    {
        
    }
    public void changeViewGenerarFactura()
    {
        
    }
    public void generarFactura()
    {
        /* Aqui va todo el codigo de Juan para generar la factura
        hasta la inserción en la base de datos.
        */
    }
    
    private void setParameters()
    {
        //paramReport.put("razonSocialEmpresa",);
    }
    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }
    
    public void verificarCombo() 
    {
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
                   
                    break;
                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(new Date());
                    
                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
                   
                    break;
                default:
                    fechaFiltroFin = null;
                    fechaFiltroFin = null;
                    
                    break;
            }
            enableCalendar = true;
        }
    }
    private void getTimbrado() throws ParserConfigurationException
    {
        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        try {	
         File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("student");
         
         System.out.println("----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) 
         {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" 
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Student roll no : " 
                  + eElement.getAttribute("rollno"));
               System.out.println("First Name : " 
                  + eElement
                  .getElementsByTagName("firstname")
                  .item(0)
                  .getTextContent());
               System.out.println("Last Name : " 
               + eElement
                  .getElementsByTagName("lastname")
                  .item(0)
                  .getTextContent());
               System.out.println("Nick Name : " 
               + eElement
                  .getElementsByTagName("nickname")
                  .item(0)
                  .getTextContent());
               System.out.println("Marks : " 
               + eElement
                  .getElementsByTagName("marks")
                  .item(0)
                  .getTextContent());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
    }
 //   private void setParameterTicket(Venta v) {

//        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
//        DecimalFormat df = new DecimalFormat("###.##");
//        Date date = new Date();
//        ArrayList<String> productos = new ArrayList<String>();
//        NumeroALetra numeroLetra = new NumeroALetra();
//        for (VentaProducto vp : v.getLstVentaProducto()) {
//            String cantidad = vp.getCantidadEmpaque() + " Kilos ";
//            productos.add(vp.getNombreProducto().toUpperCase());
//            productos.add("       " + cantidad + "               " + nf.format(vp.getPrecioProducto()) + "    " + nf.format(vp.getTotal()));
//        }
//
//        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);
//
//        paramReport.put("fechaVenta", TiempoUtil.getFechaDDMMYYYYHHMM(fechaImpresion));
//        paramReport.put("noVenta", v.getFolio().toString());
//        paramReport.put("cliente", v.getNombreCliente());
//        paramReport.put("vendedor", v.getNombreVendedor());
//        paramReport.put("productos", productos);
//        paramReport.put("ventaTotal", nf.format(totalVenta).toString());
//        paramReport.put("totalLetra", totalVentaStr);
//        if (!v.getIdStatusVenta().equals(2)) {
//            paramReport.put("labelFecha", "Fecha de Venta: K-");
//        } else {
//            paramReport.put("labelFecha", "Fecha de Pago: K-");
//        }
//        paramReport.put("labelFolio", "Folio de Venta: K-");
//        paramReport.put("estado", v.getNombreEstatus());
//        paramReport.put("labelSucursal", v.getNombreSucursal());
//        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());
//
//        //Se agregan los campos que se utiliza en el ticket de credito
//        if (!v.getIdTipoVenta().equals(new BigDecimal(1))) {
//
//            String totalVentaDescuentoStr = numeroLetra.Convertir(df.format(v.getMontoCredito()), true);
//            paramReport.put("numeroCliente", v.getIdClienteFk().toString());
//            paramReport.put("labelFecha", "Fecha de Venta:");
//            paramReport.put("fechaPromesaPago", TiempoUtil.getFechaDDMMYYYY(v.getFechaPromesaPago()));
//            paramReport.put("beneficiario", "FIDENCIO TORRES REYNOSO");
//            paramReport.put("totalCompraDescuento", nf.format(v.getMontoCredito()));
//            paramReport.put("totalDescuentoLetra", totalVentaDescuentoStr);
//            paramReport.put("aCuenta", "-" + nf.format(v.getaCuenta()));
//            paramReport.put("descuentoVenta", "-" + df.format(0));
//            paramReport.put("foliCredito", v.getIdCredito() == null ? null : v.getIdCredito().toString());
//
//            //Imprime el calendario de pagos
//            Date dateTemp = v.getFechaVenta();
//            ArrayList calendario = new ArrayList();
//            String montoAbono = nf.format(v.getMontoCredito().divide(v.getNumeroPagos() == null ? BigDecimal.ZERO : v.getNumeroPagos(), 2, RoundingMode.UP));
//            String item = "N. Pago   Fecha de Pago   Monto";
//            calendario.add(item);
//            if (v.getaCuenta().intValue() > 0) {
//                item = "    0            " + TiempoUtil.getFechaDDMMYYYY(date) + "    " + nf.format(v.getaCuenta());
//                calendario.add(item);
//
//                paramReport.put("msgAcuenta", "Favor de pasar a caja para su pago inicial de: " + nf.format(v.getaCuenta()));
//            } else {
//                paramReport.put("msgAcuenta", "");
//            }
//
//            int plaso = (v.getPlazos().divide(v.getNumeroPagos())).intValue();
//            int pagos = v.getNumeroPagos().intValue();
//            for (int y = 0; y < pagos; y++) {
//
//                dateTemp = TiempoUtil.sumarRestarDias(dateTemp, plaso);
//                item = "    " + (y + 1) + "            " + TiempoUtil.getFechaDDMMYYYY(dateTemp) + "     " + montoAbono;
//                calendario.add(item);
//
//            }
//            paramReport.put("calendarioPago", calendario);
//        }

  //  }
    public void generateReport() {
//        JRExporter exporter = null;
//        try {
//            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//
//            String temporal = "";
//            if (servletContext.getRealPath("") == null) {
//                temporal = Constantes.PATHSERVER;
//            } else {
//                temporal = servletContext.getRealPath("");
//            }
//            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "facturas" + File.separatorChar + "ticketCredito.jasper";          
//            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
//
//            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
//            exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//
//            byte[] bytes = outputStream.toByteArray();
//            rutaPDF = UtilUpload.saveFileTemp(bytes, "factura", v.getIdVentaPk().intValue(), idSucursalImpresion.intValue());
//
//        } catch (Exception exception) {
//            System.out.println("Error >" + exception.getMessage());
//
//        }
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

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public void setPathFileJasper(String pathFileJasper) {
        this.pathFileJasper = pathFileJasper;
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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getFolioFactura() {
        return folioFactura;
    }

    public void setFolioFactura(BigDecimal folioFactura) {
        this.folioFactura = folioFactura;
    }

    public BigDecimal getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(BigDecimal folioVenta) {
        this.folioVenta = folioVenta;
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
    
    
    
    
    
}
