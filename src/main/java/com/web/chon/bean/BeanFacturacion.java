/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceFacturacion;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
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
import mx.bigdata.sat.cfdi.v32.schema.Comprobante;
import mx.bigdata.sat.cfdi.v32.schema.ObjectFactory;
import org.primefaces.event.RowEditEvent;
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
public class BeanFacturacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private IfaceFacturacion ifaceFacturacion;

    //--Variables Generales Bean--//
    private String title;
    private String viewEstate;
    private UsuarioDominio usuario;
    private int filtro;
    private int tipoFactura;
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
    private BigDecimal folioVentaG;
    private ArrayList<FacturaPDFDomain> modelo;
    private FacturaPDFDomain data;
    private VentaMayoreo ventaMayoreo;
    private boolean ventaMenudeo;
    private boolean statusButtonFacturar;
    private DatosFacturacion datosEmisor;
    private DatosFacturacion datosCliente;
    private ArrayList<DatosFacturacion> listaDatosEmisor;
    private BigDecimal idEmisorPk;
    private BigDecimal totalVenta;
    private BigDecimal descuento;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal total;
    private int filtroMostrador;
    private boolean disableBotonBuscarVenta;
    private boolean disableTextFolioVenta;

    private ArrayList<VentaProductoMayoreo> selectedProductosVentas;

    //--Variables Para Generar PDF--//
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "";
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private BigDecimal idClienteVentaMostradorPk;
    private VentaProductoMayoreo dataEdit;
    private VentaProductoMayoreo dataRemove;
    

    ObjectFactory of;
    Comprobante comp;

    @PostConstruct
    public void init() {
        disableBotonBuscarVenta = false;
        disableTextFolioVenta = false;
        filtroMostrador = 1;
        totalVenta = new BigDecimal(0);
        of = new ObjectFactory();
        comp = of.createComprobante();
        idClienteVentaMostradorPk = new BigDecimal(1103);
        datosEmisor = new DatosFacturacion();
        datosCliente = new DatosFacturacion();
        usuario = context.getUsuarioAutenticado();
        setTitle("Facturación Electrónica");
        setViewEstate("init");
        lstCliente = new ArrayList<Cliente>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data = new FacturaPDFDomain();
        ventaMayoreo = new VentaMayoreo();
        listaDatosEmisor = new ArrayList<DatosFacturacion>();

        //====INICIALIZACIÓN DE LOS DATOS DE COMPROBANTE DIGITAL===//
        comp.setVersion("3.2");
        comp.setSerie("A");
        comp.setFolio("11639");
        comp.setFecha(context.getFechaSistema());
        cliente = new Cliente();
        buscarDatosEmisor();

    }
    public void agregarProducto()
    {
        
    }

    public void removeProductoSugerido()
    {
        
    }
    public void buscarDatosCliente() {
        if (filtroMostrador == 2) {
            datosCliente = ifaceFacturacion.getDatosFacturacionByIdCliente(idClienteVentaMostradorPk);
            System.out.println("222222222");
        } else {
            if (ventaMayoreo != null && ventaMayoreo.getIdClienteFk() != null) {
                datosCliente = ifaceFacturacion.getDatosFacturacionByIdCliente(ventaMayoreo.getIdClienteFk());
            System.out.println("11111111111");
            }
        }

    }

    public void buscarDatosEmisor() {
        listaDatosEmisor = ifaceFacturacion.getDatosFacturacionByIdSucursal(new BigDecimal(usuario.getSucId()));
    }

    public void buscarFolioVenta() {
        totalVenta = new BigDecimal(0);

        ventaMayoreo = ifaceVentaMayoreo.getVentaMayoreoByFolioidSucursalFk(folioVentaG, new BigDecimal(usuario.getSucId()));

        ventaMenudeo = false;
        //SE HACE LA BUSQUEDA A MENUDEO
        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
            ventaMayoreo = ifaceBuscaVenta.getVentaByfolioAndIdSuc(folioVentaG, usuario.getSucId());
            ventaMenudeo = true;

        }

        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
            JsfUtil.addErrorMessageClean("No se encontró ese folio, podría ser de otra sucursal.");
        } else if (ventaMayoreo.getIdtipoVentaFk().intValue() == 1) {
            statusButtonFacturar = true;

            switch (ventaMayoreo.getIdStatusFk().intValue()) {
                case 1:
                    statusButtonFacturar = true;
                    JsfUtil.addErrorMessageClean("No puedes facturar la venta, no ha sido pagada");
                    break;
                case 2:
                    statusButtonFacturar = false;

                    break;
                case 3:
                    statusButtonFacturar = false;
                    break;
                case 4:
                    statusButtonFacturar = true;
                    JsfUtil.addErrorMessageClean("No puedes facturar una venta cancelada");
                    break;
                default:
                    statusButtonFacturar = true;
                    JsfUtil.addErrorMessageClean("Ha ocurrido un error, contactar al administrador.");
                    break;
            }
        } else {
            JsfUtil.addWarnMessageClean("No puedes cobrar una venta de crédito, ir a la sección abonar crédito, subir imagen");
            statusButtonFacturar = true;

        }
        buscarDatosCliente();
    }

    public void descargarXML() {

    }

    public void descargarPDF() {

    }

    public void cancelarFactura() {

    }

    public void enviarFactura() {

    }

    public void buscarFacturas() {

    }

    public void changeViewGenerarFactura() {

        setViewEstate("generate");
        setTitle("Generar Factura");
    }

    public void backView() {
        setViewEstate("init");

    }

    public void changeViewMostrador() {
        if (filtroMostrador == 1) {
            setViewEstate("generate");
            disableBotonBuscarVenta = false;
            disableTextFolioVenta = false;
        } else {
            setViewEstate("generateVentaMostrador");
            disableBotonBuscarVenta = true;
            disableTextFolioVenta = true;
        }
        buscarDatosCliente();

    }

    public void onRowEdit(RowEditEvent event) {
        dataEdit = new VentaProductoMayoreo();
        dataEdit = (VentaProductoMayoreo) event.getObject();

        System.out.println("editado " + dataEdit.toString());
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void emitirFactura() {

    }

    public void generarFactura() {
        /* Aqui va todo el codigo de Juan para generar la factura
        hasta la inserción en la base de datos.
         */
    }

    private void setParameters() {
        //paramReport.put("razonSocialEmpresa",);
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

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

    private void getTimbrado() throws ParserConfigurationException {
//        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        
//        try {	
//         File inputFile = new File("input.txt");
//         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//         Document doc = dBuilder.parse(inputFile);
//         doc.getDocumentElement().normalize();
//         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//         NodeList nList = doc.getElementsByTagName("student");
//         
//         System.out.println("----------------------------");
//         for (int temp = 0; temp < nList.getLength(); temp++) 
//         {
//            Node nNode = nList.item(temp);
//            System.out.println("\nCurrent Element :" 
//               + nNode.getNodeName());
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//               Element eElement = (Element) nNode;
//               System.out.println("Student roll no : " 
//                  + eElement.getAttribute("rollno"));
//               System.out.println("First Name : " 
//                  + eElement
//                  .getElementsByTagName("firstname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Last Name : " 
//               + eElement
//                  .getElementsByTagName("lastname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Nick Name : " 
//               + eElement
//                  .getElementsByTagName("nickname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Marks : " 
//               + eElement
//                  .getElementsByTagName("marks")
//                  .item(0)
//                  .getTextContent());
//            }
//         }
//      } catch (Exception e) {
//         e.printStackTrace();
//      }
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

    public ArrayList<FacturaPDFDomain> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<FacturaPDFDomain> modelo) {
        this.modelo = modelo;
    }

    public FacturaPDFDomain getData() {
        return data;
    }

    public void setData(FacturaPDFDomain data) {
        this.data = data;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(int tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public BigDecimal getFolioVentaG() {
        return folioVentaG;
    }

    public void setFolioVentaG(BigDecimal folioVentaG) {
        this.folioVentaG = folioVentaG;
    }

    public VentaMayoreo getVentaMayoreo() {
        return ventaMayoreo;
    }

    public void setVentaMayoreo(VentaMayoreo ventaMayoreo) {
        this.ventaMayoreo = ventaMayoreo;
    }

    public DatosFacturacion getDatosEmisor() {
        return datosEmisor;
    }

    public void setDatosEmisor(DatosFacturacion datosEmisor) {
        this.datosEmisor = datosEmisor;
    }

    public DatosFacturacion getDatosCliente() {
        return datosCliente;
    }

    public void setDatosCliente(DatosFacturacion datosCliente) {
        this.datosCliente = datosCliente;
    }

    public ArrayList<DatosFacturacion> getListaDatosEmisor() {
        return listaDatosEmisor;
    }

    public void setListaDatosEmisor(ArrayList<DatosFacturacion> listaDatosEmisor) {
        this.listaDatosEmisor = listaDatosEmisor;
    }

    public boolean isVentaMenudeo() {
        return ventaMenudeo;
    }

    public void setVentaMenudeo(boolean ventaMenudeo) {
        this.ventaMenudeo = ventaMenudeo;
    }

    public boolean isStatusButtonFacturar() {
        return statusButtonFacturar;
    }

    public void setStatusButtonFacturar(boolean statusButtonFacturar) {
        this.statusButtonFacturar = statusButtonFacturar;
    }

    public BigDecimal getIdEmisorPk() {
        return idEmisorPk;
    }

    public void setIdEmisorPk(BigDecimal idEmisorPk) {
        this.idEmisorPk = idEmisorPk;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public ArrayList<VentaProductoMayoreo> getSelectedProductosVentas() {
        return selectedProductosVentas;
    }

    public void setSelectedProductosVentas(ArrayList<VentaProductoMayoreo> selectedProductosVentas) {
        this.selectedProductosVentas = selectedProductosVentas;
    }

    public int getFiltroMostrador() {
        return filtroMostrador;
    }

    public void setFiltroMostrador(int filtroMostrador) {
        this.filtroMostrador = filtroMostrador;
    }

    public BigDecimal getIdClienteVentaMostradorPk() {
        return idClienteVentaMostradorPk;
    }

    public void setIdClienteVentaMostradorPk(BigDecimal idClienteVentaMostradorPk) {
        this.idClienteVentaMostradorPk = idClienteVentaMostradorPk;
    }

    public boolean isDisableBotonBuscarVenta() {
        return disableBotonBuscarVenta;
    }

    public void setDisableBotonBuscarVenta(boolean disableBotonBuscarVenta) {
        this.disableBotonBuscarVenta = disableBotonBuscarVenta;
    }

    public boolean isDisableTextFolioVenta() {
        return disableTextFolioVenta;
    }

    public void setDisableTextFolioVenta(boolean disableTextFolioVenta) {
        this.disableTextFolioVenta = disableTextFolioVenta;
    }

    public VentaProductoMayoreo getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(VentaProductoMayoreo dataEdit) {
        this.dataEdit = dataEdit;
    }

    public VentaProductoMayoreo getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(VentaProductoMayoreo dataRemove) {
        this.dataRemove = dataRemove;
    }
    

}
