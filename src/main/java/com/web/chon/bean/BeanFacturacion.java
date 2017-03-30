/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import advanswsdl_pkg.AdvanswsdlLocator;
import advanswsdl_pkg.RespuestaCancelacion;
import advanswsdl_pkg.RespuestaTimbre2;
import com.web.chon.dominio.CatalogoSat;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatalogoSat;
import com.web.chon.service.IfaceDatosFacturacion;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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
import mx.bigdata.sat.cfdi.examples.CFDv32;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante;
import mx.bigdata.sat.cfdi.v32.schema.ObjectFactory;
import mx.bigdata.sat.security.KeyLoaderEnumeration;
import mx.bigdata.sat.security.factory.KeyLoaderFactory;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.RowEditEvent;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import com.web.chon.service.IfaceFacturas;
import com.web.chon.service.IfaceProductoFacturado;
import com.web.chon.util.SendEmail;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Random;
import javax.activation.FileDataSource;

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
    private IfaceDatosFacturacion ifaceDatosFacturacion;
    @Autowired
    private IfaceCatalogoSat ifaceCatalogoSat;
    @Autowired
    private IfaceFacturas ifaceFacturas;
    @Autowired
    private IfaceProductoFacturado ifaceProductoFacturado;

    //--Variables Generales Bean--//
    private String title;
    private String viewEstate;
    private UsuarioDominio usuario;
    private int tipoFactura;

    private int filtro;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private boolean enableCalendar;

    private int filtroPublico;
    private Date fechaFiltroInicioPublico;
    private Date fechaFiltroFinPublico;
    private boolean enableCalendarPublico;

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
    private Cliente datosCliente;
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
    private StreamedContent file;

    private ArrayList<VentaProductoMayoreo> selectedProductosVentas;
    private ArrayList<VentaProductoMayoreo> listaVentaPublico;
    private ArrayList<VentaProductoMayoreo> listaProductosReporte;

    //--Variables Para Generar PDF--//
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "";
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private BigDecimal idClienteVentaMostradorPk;
    private VentaProductoMayoreo dataEdit;
    private VentaProductoMayoreo dataRemove;
    private BigDecimal importeParcialidad;
    private String banderaImporteParcialidad;
    private String banderanumCuenta;
    private String metodoPago;
    private String tipoPago;
    private String formaPago;
    private BigDecimal tipoDocumento;
    private String moneda;

    private ArrayList<CatalogoSat> listaMetodosPago;
    private ArrayList<CatalogoSat> listaFormaPago;
    private ArrayList<CatalogoSat> listaTipoDocumento;
    private ArrayList<CatalogoSat> listaMonedas;
    private ArrayList<ProductoFacturado> lstProductosFacturado;

    private static final BigDecimal TIPO_METODO_PAGO = new BigDecimal(2);
    private static final BigDecimal TIPO_FORMA_PAGO = new BigDecimal(1);
    private static final BigDecimal TIPO_MONEDA = new BigDecimal(3);
    private static final BigDecimal TIPO_DOCUMENTO = new BigDecimal(4);
    private static final BigDecimal CERO = new BigDecimal(0);

    //nunca cambia, lo proporciono el pac PRUEBAS
    private static String API_KEY = "1e550c937ba7cf3f65a6136ae86add2b";
    //private static String API_KEY = "7ecfe40342406c370e09029125103ac3";
    private String pathFacturaClienteComprobante;
    private String pathFacturaClienteTimbrado;

    private FacturaPDFDomain nuevaFactura;
    private byte[] bytes;

    ObjectFactory of;
    Comprobante comp;
    private BigDecimal numeroCuenta;
    private BigDecimal importeVentaPublico;

    private String correo;

    @PostConstruct
    public void init() {
        nuevaFactura = new FacturaPDFDomain();
        modelo = new ArrayList<FacturaPDFDomain>();
        banderaImporteParcialidad = "false";
        banderanumCuenta = "false";
        disableBotonBuscarVenta = false;
        disableTextFolioVenta = false;
        filtroMostrador = 1;
        totalVenta = CERO;
        descuento = CERO;
        subTotal = CERO;
        iva = CERO;
        total = CERO;
        listaProductosReporte = new ArrayList<VentaProductoMayoreo>();
        of = new ObjectFactory();
        comp = of.createComprobante();
        idClienteVentaMostradorPk = new BigDecimal(1);
        datosEmisor = new DatosFacturacion();
        datosCliente = new Cliente();
        usuario = context.getUsuarioAutenticado();
        setTitle("Facturación Electrónica");
        setViewEstate("init");
        lstCliente = new ArrayList<Cliente>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data = new FacturaPDFDomain();
        ventaMayoreo = new VentaMayoreo();
        listaDatosEmisor = new ArrayList<DatosFacturacion>();

        cliente = new Cliente();
        buscarDatosEmisor();

        //--inicializacion de listas-----//
        listaMetodosPago = new ArrayList<CatalogoSat>();
        listaFormaPago = new ArrayList<CatalogoSat>();
        listaTipoDocumento = new ArrayList<CatalogoSat>();
        listaMonedas = new ArrayList<CatalogoSat>();

        listaMetodosPago = ifaceCatalogoSat.getCatalogo(TIPO_METODO_PAGO);
        listaFormaPago = ifaceCatalogoSat.getCatalogo(TIPO_FORMA_PAGO);
        listaMonedas = ifaceCatalogoSat.getCatalogo(TIPO_MONEDA);
        listaTipoDocumento = ifaceCatalogoSat.getCatalogo(TIPO_DOCUMENTO);
        idSucursalFk = new BigDecimal(usuario.getSucId());
        filtro = 1;
        tipoDocumento = new BigDecimal(17);
        verificarCombo();
        buscarFacturas();
        selectedProductosVentas = new ArrayList<VentaProductoMayoreo>();
        filtroPublico = 3;

    }

    public int verificarImportePublico() {
        //funcion para verificar los productos seleccionados y su monto, por si se edita un producto 
        //o se deselecciona 
        BigDecimal temporal = new BigDecimal(0);
        for (VentaProductoMayoreo producto : selectedProductosVentas) {
            temporal = temporal.add(producto.getTotalVenta(), MathContext.UNLIMITED);

        }
        if (importeVentaPublico.compareTo(temporal) == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void calculaImportePublico() {
        // System.out.println("Calcular Importe Público");
        selectedProductosVentas.clear();
        BigDecimal importePublicoTemporal = importeVentaPublico;
        for (VentaProductoMayoreo producto : listaVentaPublico) {
            if (importePublicoTemporal.compareTo(new BigDecimal(0)) > 0) { // si el importe es mayor que cero
                if (importePublicoTemporal.compareTo(producto.getTotalVenta()) >= 0) {
                    /*El importe de la parcilidad es mayor al de ese producto
                    agregar el producto a la lista de seleccionados
                     */
                    //System.out.println("Temporal importe Publico: " + importePublicoTemporal);
                    importePublicoTemporal = importePublicoTemporal.subtract(producto.getTotalVenta(), MathContext.UNLIMITED);
                    // System.out.println("Temporal importe Publico: " + importePublicoTemporal);
                    producto.setKilosVendidos(producto.getKilosVendidos());
                    producto.setTotalVenta(producto.getTotalVenta());
                    selectedProductosVentas.add(producto);
                } else {
                    /*
                    xkilos = xImporte
                    x      = importePublicoTemporal;
                     */
                    BigDecimal kilosT = new BigDecimal(0);
                    BigDecimal division = new BigDecimal(0);

                    kilosT = importePublicoTemporal.setScale(2, RoundingMode.CEILING).multiply(producto.getKilosVendidos().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                    kilosT.setScale(2, RoundingMode.CEILING);
                    // System.out.println("KilosT: " + kilosT);
                    // System.out.println("Producto: " + producto.getTotalVenta());
                    division = (kilosT).divide(producto.getTotalVenta(), 2, RoundingMode.CEILING);
                    producto.setKilosVendidos(division);
                    producto.setTotalVenta(importePublicoTemporal);
                    selectedProductosVentas.add(producto);

                    importePublicoTemporal = importePublicoTemporal.subtract(producto.getTotalVenta(), MathContext.UNLIMITED);
                }
            }
        }//fin for
        //System.out.println("Total Publico Temporal: " + importePublicoTemporal);
        BigDecimal t = new BigDecimal(0);
        for (VentaProductoMayoreo vpm : selectedProductosVentas) {
            t = t.add(vpm.getTotalVenta(), MathContext.UNLIMITED);
        }

        ventaMayoreo.setTotalVenta(t);
        ventaMayoreo.setListaProductos(selectedProductosVentas);

        calcularTotales();
        if (selectedProductosVentas != null && selectedProductosVentas.isEmpty()) {
            statusButtonFacturar = true;
        } else {
            statusButtonFacturar = false;
        }
    }

    public void calculaParcialidad() {
        buscarFolioVenta();
        if (verificarMontoParicalidad() == 1) {

            BigDecimal temporalParcialidad = importeParcialidad;
            ArrayList<VentaProductoMayoreo> lstTemporalProductosFacturar = new ArrayList<VentaProductoMayoreo>();
            for (VentaProductoMayoreo producto : ventaMayoreo.getListaProductos()) {
                if (temporalParcialidad.compareTo(new BigDecimal(0)) > 0) {
                    if (temporalParcialidad.compareTo(producto.getTotalVenta()) >= 0) {
                        //el importe de la parcilidad es mayor al de ese producto
                        //  System.out.println("Total Venta: " + producto.getTotalVenta());
                        // System.out.println("Temporal Parcialidad: " + temporalParcialidad);
                        temporalParcialidad = temporalParcialidad.subtract(producto.getTotalVenta(), MathContext.UNLIMITED);
                        // System.out.println("Temporal Parcialidad: " + temporalParcialidad);
                        producto.setKilosVendidos(producto.getKilosVendidos());
                        producto.setTotalVenta(producto.getTotalVenta());
                        lstTemporalProductosFacturar.add(producto);

                    } else {
                        /*
                xkilos = xImporte
                x      = temporalParcialidad;
                
                         */
                        BigDecimal kilosT = new BigDecimal(0);
                        BigDecimal division = new BigDecimal(0);

                        kilosT = temporalParcialidad.setScale(2, RoundingMode.CEILING).multiply(producto.getKilosVendidos().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                        kilosT.setScale(2, RoundingMode.CEILING);
                        // System.out.println("KilosT: " + kilosT);
                        // System.out.println("Producto: " + producto.getTotalVenta());
                        division = (kilosT).divide(producto.getTotalVenta(), 2, RoundingMode.CEILING);
                        producto.setKilosVendidos(division);
                        producto.setTotalVenta(temporalParcialidad);
                        lstTemporalProductosFacturar.add(producto);

                        temporalParcialidad = temporalParcialidad.subtract(producto.getTotalVenta(), MathContext.UNLIMITED);

                        //el importe de la parcialidad es menor
                        //recalcular todo :S 
                    }
                }

            }
            ventaMayoreo.getListaProductos().clear();
            ventaMayoreo.setListaProductos(lstTemporalProductosFacturar);

            //System.out.println("Importe Parcialidad: " + importeParcialidad);
            ventaMayoreo.setTotalVenta(importeParcialidad);
            calcularTotales();
        } else {
            JsfUtil.addErrorMessageClean("El monto de la parcialidad supera el monto a liquidar de la venta, o el monto debe ser mayor a cero");
        }
    }

    public void buscaVentasNoFacturadas() {
        //System.out.println("Entro a Ventas no facturadas");
        listaVentaPublico = ifaceProductoFacturado.getProductosNoFacturados(new BigDecimal(1), idSucursalFk, TiempoUtil.getFechaDDMMYYYY(fechaFiltroInicioPublico), TiempoUtil.getFechaDDMMYYYY(fechaFiltroFinPublico));

    }

    public int verificarMontoParicalidad() {
        BigDecimal t = new BigDecimal(0);
        if (importeParcialidad.compareTo(t) < 1) {
            return 0;
        }
        for (VentaProductoMayoreo p : ventaMayoreo.getListaProductos()) {
            t = t.add(p.getTotalVenta(), MathContext.UNLIMITED);
        }
        if (importeParcialidad.compareTo(t) > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void calculaKilosPorFacturar() {
        //condiciones
        /*
        1.- No debe exceder el importe total de la venta
        2.- No negativos
        3.- Calcular total de kilos por producto en base al monto máximo
        4.- Traer de la tabla los productos facturados de esa venta y restarlos.
         */
        lstProductosFacturado = new ArrayList<ProductoFacturado>();
        lstProductosFacturado = ifaceProductoFacturado.getByIdTipoFolioFk(ventaMayoreo.getIdtipoVentaFk(), ventaMayoreo.getVentaSucursal());
        BigDecimal importeTemporalTotal = new BigDecimal(0);
        if (!lstProductosFacturado.isEmpty()) {
            for (ProductoFacturado pf : lstProductosFacturado) {
                BigDecimal kilosTemporal;
                BigDecimal importeTemporal;

                for (VentaProductoMayoreo vp : ventaMayoreo.getListaProductos()) {

                    //BigDecimal importeTemporal = new BigDecimal(0);
                    //BigDecimal cantidadTemporal = new BigDecimal(0);
                    if (pf.getIdLlaveFk().intValue() == vp.getIdVentaMayProdPk().intValue()) {

                        kilosTemporal = vp.getKilosVendidos().subtract(pf.getKilos(), MathContext.UNLIMITED);
                        importeTemporal = vp.getTotalVenta().subtract(pf.getImporte(), MathContext.UNLIMITED);
//                        System.out.println("Kilos Totales de Venta: " + vp.getKilosVendidos());
//                        System.out.println("Kilos Facturados: " + pf.getKilos());
//                        System.out.println("Kilos por Facturar: " + kilosTemporal);
//
//                        System.out.println("Importe Total de la Venta: " + vp.getTotalVenta());
//                        System.out.println("Importe Facturado: " + pf.getImporte());
//                        System.out.println("Importe por Facturar: " + importeTemporal);
//                        System.out.println("===============================================");
                        vp.setKilosVendidos(kilosTemporal);
                        vp.setTotalVentaSinIva(importeTemporal);
                        vp.setTotalVenta(importeTemporal);
                        importeTemporalTotal = importeTemporalTotal.add(importeTemporal, MathContext.UNLIMITED);
                        //importeTemporal = vp.getTotalVenta().subtract(pf.getImporte(), MathContext.UNLIMITED);
                    }
                }

            }
            ventaMayoreo.setTotalVenta(importeTemporalTotal);
        }//fin if lista vacia
        else {

        }

        //System.out.println("Total de Venta: -----" + importeTemporalTotal);
    }

    public void calcularTotales() {

        BigDecimal importeSinIva = new BigDecimal(0);
        BigDecimal importeConIva = new BigDecimal(0);

        ArrayList<VentaProductoMayoreo> lstproductosTemporal = new ArrayList<VentaProductoMayoreo>();
        for (CatalogoSat cs : listaTipoDocumento) {

            if (cs.getIdCatalogoSatPk().intValue() == tipoDocumento.intValue()) {
                BigDecimal totalSinIvaFOR = new BigDecimal(0);
                for (VentaProductoMayoreo vmp : ventaMayoreo.getListaProductos()) {

                    if (vmp.getKilosVendidos().compareTo(new BigDecimal(0)) > 0) {
                        BigDecimal iva = vmp.getTotalVenta().multiply(cs.getValor(), MathContext.UNLIMITED);

                        vmp.setTotalVentaSinIva(vmp.getTotalVenta().subtract(iva.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED));
                        vmp.setPrecioProductoSinIva(vmp.getTotalVentaSinIva().setScale(2, RoundingMode.CEILING).divide(vmp.getKilosVendidos(), 2, RoundingMode.CEILING));
                        totalSinIvaFOR = totalSinIvaFOR.add(vmp.getTotalVentaSinIva(), MathContext.UNLIMITED);
                        lstproductosTemporal.add(vmp);
                    }
                }
                ventaMayoreo.getListaProductos().clear();
                ventaMayoreo.setListaProductos(lstproductosTemporal);
                selectedProductosVentas.clear();
                for (VentaProductoMayoreo p : ventaMayoreo.getListaProductos()) {
                    selectedProductosVentas.add(p);
                }

                ventaMayoreo.setTotalVentaSinIva(totalSinIvaFOR);
                //impore sin iva
                importeConIva = ventaMayoreo.getTotalVenta();
                importeSinIva = ventaMayoreo.getTotalVentaSinIva();
                //importe con iva
//                System.out.println("Importe Con Iva: " + importeConIva);
//                System.out.println("Importe Sin Iva: " + importeSinIva);

                if (cs.getValor().compareTo(new BigDecimal(0)) == 0) {
                    //se va a usar un IVA de 0 %

                    subTotal = (importeConIva.setScale(2, RoundingMode.CEILING)).subtract(descuento.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                    //System.out.println("Subtotal Calculado con IVA");
                } else {
                    //se va a usar un IVA de 16 %
                    subTotal = (importeSinIva.setScale(2, RoundingMode.CEILING)).subtract(descuento.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                    //System.out.println("Subtotal Calculado sin IVA");
                }
                //System.out.println("subTotal : " + subTotal);
                iva = (cs.getValor().multiply(new BigDecimal(100), MathContext.UNLIMITED).setScale(2, RoundingMode.CEILING)).multiply(ventaMayoreo.getTotalVenta().subtract(descuento.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED).setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                // System.out.println("Iva:  : " + iva);

                iva = iva.setScale(2, RoundingMode.CEILING).divide(new BigDecimal(100), 2, RoundingMode.CEILING);
                // System.out.println("Iva Dividido:  : " + iva);
                total = (iva.setScale(2, RoundingMode.CEILING)).add(subTotal.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                // System.out.println("Total: " + total);
            }

        }
    }

    public void agregarDescuento() {

    }

    public void agregarProducto() {

    }

    public void removeProductoSugerido() {
        listaVentaPublico.remove(dataRemove);

    }

    public void buscarDatosCliente(int v) {

        switch (v) {
            case 1:
                if (ventaMayoreo != null && ventaMayoreo.getIdClienteFk() != null) {
                    datosCliente = new Cliente();
                    datosCliente = ifaceCatCliente.getClienteById(ventaMayoreo.getIdClienteFk());
                }
                break;
            case 2:
                datosCliente = new Cliente();
                datosCliente = ifaceCatCliente.getClienteById(idClienteVentaMostradorPk);
                break;
            case 3:
                if (datosCliente != null && datosCliente.getIdClientePk() != null) {
                    datosCliente = ifaceCatCliente.getClienteById(datosCliente.getIdClientePk());
                }
                break;
            default:
                JsfUtil.addErrorMessageClean("Ocurrio un error al buscar los datos del cliente");
                break;
        }

    }

    public StreamedContent getProductImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (data.getFichero() == null) {
            JsfUtil.addErrorMessageClean("Error al descargar el arvhivo");
            return file;
        } else {
            file = null;
            byte[] datos = data.getFichero();
            InputStream stream = new ByteArrayInputStream(datos);
            file = new DefaultStreamedContent(stream, "xml", data.getRfcCliente() + "_" + data.getFolioFiscal() + ".xml");
            return file;
        }

    }

    public void buscarDatosEmisor() {
        listaDatosEmisor = ifaceDatosFacturacion.getDatosFacturacionByIdSucursal(new BigDecimal(usuario.getSucId()));
    }

    public void buscarFolioVenta() {
        ventaMayoreo = new VentaMayoreo();
        datosCliente = new Cliente();
        totalVenta = new BigDecimal(0);
        if (filtroMostrador == 1) {
            ventaMayoreo = ifaceVentaMayoreo.getVentaMayoreoByFolioidSucursalFk(folioVentaG, new BigDecimal(usuario.getSucId()));
            //ventaMayoreo.setIdtipoVentaFk(new BigDecimal(1));
            ventaMayoreo.setBanderaVentaMenudeo(false);
            ventaMenudeo = false;
            //SE HACE LA BUSQUEDA A MENUDEO
            if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
                ventaMayoreo = ifaceBuscaVenta.getVentaByfolioAndIdSuc(folioVentaG, usuario.getSucId());
                ventaMenudeo = true;
                ventaMayoreo.setBanderaVentaMenudeo(true);
                //ventaMayoreo.setIdtipoVentaFk(new BigDecimal(2));

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
                JsfUtil.addWarnMessageClean("Venta de Cŕedito");
                statusButtonFacturar = false;

            }
            for (VentaProductoMayoreo vpm : ventaMayoreo.getListaProductos()) {
                vpm.setIdTipoVenta(new BigDecimal(1));
            }
            if (ventaMayoreo.isBanderaVentaMenudeo()) {
                for (VentaProductoMayoreo vpm : ventaMayoreo.getListaProductos()) {
                    vpm.setKilosVendidos(vpm.getCantidadEmpaque());
                    vpm.setIdTipoVenta(new BigDecimal(2));
                }
            }

            buscarDatosCliente(1);
            calculaKilosPorFacturar();
            calcularTotales();
        } else {
            //buscar productos de ventas de credito no facturados
            ventaMayoreo.setListaProductos(ifaceProductoFacturado.getProductosNoFacturadosAbonos(new BigDecimal(1), folioVentaG));
        }
    }

    public void descargarXML() {

    }

    public void descargarPDF() {

    }

    public void cancelarFactura() {
        try {
//            String UUID = "FFFFFFFF-3861-A0BE-2044-76E36DDD09E0";

            try {
                String rutaTimbradoData = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + data.getRfcCliente() + File.separatorChar + "TIMBRADO" + File.separatorChar + data.getNombreArchivoTimbrado();
                File inputFile = new File(rutaTimbradoData);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("tfd:TimbreFiscaldigital");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        //System.out.println("Version : " + eElement.getAttribute("version"));
                        data.setUuid(eElement.getAttribute("UUID"));
                    }
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("DAta: " + data.toString());

            String pathCancelar = Constantes.PATHLOCALFACTURACION + "Empresas" + File.separatorChar + data.getRfcEmisor() + File.separatorChar;
            //
            Path pathKey = Paths.get(pathCancelar + data.getKeyCancelar());
            System.out.println("PathKey: " + pathKey);
            byte[] keyPem = Files.readAllBytes(pathKey);
            String strKeyPem = new String(keyPem, Charset.defaultCharset());
            System.out.println("keypem " + strKeyPem);

            Path pathCert = Paths.get(pathCancelar + data.getCertificadoCancelar());
            System.out.println("pathCert: " + pathCert);
            byte[] cerPem = Files.readAllBytes(pathCert);
            String strCerPem = new String(cerPem, Charset.defaultCharset());
            System.out.println("CERpem " + strCerPem);

            RespuestaCancelacion respuesta = new RespuestaCancelacion();
            AdvanswsdlLocator service = new AdvanswsdlLocator();

            System.out.println("se acrgaron variable");

            advanswsdl_pkg.AdvanswsdlPortType port = service.getadvanswsdlPort();
            System.out.println("Antes de Cancelar");
            respuesta = port.cancelar(API_KEY, data.getRfcEmisor(), data.getUuid(), strKeyPem, strCerPem);
            System.out.println("despues de cancelar");
            System.out.println("codes:  " + respuesta.getCode());
            System.out.println("mensajes: " + respuesta.getMessage());
            System.out.println("subcodes:  " + respuesta.getSubCode());
            System.out.println("Acuse:  " + respuesta.getAcuse());

            data.setIdStatusFk(new BigDecimal(2));
            if (ifaceFacturas.update(data.getIdFacturaPk(), data.getIdStatusFk()) >= 1) {
                System.out.println("Entro aqui");
                if (ifaceProductoFacturado.deleteByIdFacturaFk(data.getIdFacturaPk()) >= 1) {
                    JsfUtil.addSuccessMessageClean("Se ha cancelado la factura con éxito");
                    buscarFacturas();
                    System.out.println("Entro aqui 2");
                } else {
                    JsfUtil.addErrorMessageClean("Ha ocurrido un error al cancelar la factura");
                    System.out.println("Entro aqui 3");
                }
            } else {
                JsfUtil.addErrorMessageClean("Se cancelo la factura, error al actualizar estatus");
            }

        } catch (IOException ex) {
            Logger.getLogger(BeanFacturacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BeanFacturacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void enviarFactura() {

        abreXML();
        String filePathPDF = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + data.getRfcCliente() + File.separatorChar + "TIMBRADO" + File.separatorChar + data.getNombreArchivoTimbrado();
        String filePathXML = rutaPDF;

        String mensaje = "Gracias por comprar en chonajos;\n"
                + "adjuntamos archivos digitales de su factura.\n\n"
                + "Saludos";

        String asunto = "Factura";

        ArrayList<String> lstCorreoPara = new ArrayList<String>();

        if (correo != null) {
            String correos[] = correo.split(";");
            for (int i = 0; i >= correos.length; i++) {
                lstCorreoPara.add(correos[i]);
            }

        }

        FileDataSource pdf = new FileDataSource(filePathPDF);
        FileDataSource xml = new FileDataSource(filePathXML);

        ArrayList<FileDataSource> lstFileDataSource = new ArrayList<FileDataSource>();

        lstFileDataSource.add(xml);
        lstFileDataSource.add(pdf);

        SendEmail.sendAdjunto(asunto, mensaje, lstFileDataSource, "juancruzh91@gmail.com", lstCorreoPara);

    }

    public void buscarFacturas() {
        modelo.clear();
        if (cliente != null && cliente.getIdClientePk() != null) {
            modelo = ifaceFacturas.getFacturasBy(cliente.getIdClientePk(), idSucursalFk, folioFactura, TiempoUtil.getFechaDDMMYYYY(fechaFiltroInicio), TiempoUtil.getFechaDDMMYYYY(fechaFiltroFin), idStatusFk);
        } else {
            modelo = ifaceFacturas.getFacturasBy(null, idSucursalFk, folioFactura, TiempoUtil.getFechaDDMMYYYY(fechaFiltroInicio), TiempoUtil.getFechaDDMMYYYY(fechaFiltroFin), idStatusFk);

        }

    }

    public void abreXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //DocumentBuilder builder = factory.newDocumentBuilder();

        try {
            String rutaTimbradoData = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + data.getRfcCliente() + File.separatorChar + "TIMBRADO" + File.separatorChar + data.getNombreArchivoTimbrado();
            paramReport.put("cadena", data.getCadena());
            paramReport.put("numeroFactura", data.getNumeroFactura());
            BigDecimal totalTemporal = new BigDecimal(0);
            String folioQR = "";
            String rfcReceptorQR = "";
            paramReport.put("iva1", data.getIva1());
            File inputFile = new File(rutaTimbradoData);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("cfdi:Comprobante");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("LugarExpedicion", eElement.getAttribute("LugarExpedicion"));
                    paramReport.put("NumCtaPago", eElement.getAttribute("NumCtaPago"));
                    paramReport.put("formaPago", eElement.getAttribute("metodoDePago"));
                    String fp = eElement.getAttribute("metodoDePago");

                    for (CatalogoSat cs : listaFormaPago) {
                        if (cs.getCodigo().equals(fp)) {
                            comp.setFormaDePago(cs.getDescripcion());
                            paramReport.put("nombreFormaPago", cs.getDescripcion());
                        }
                    }
                    for (CatalogoSat cs : listaMetodosPago) {
                        if (cs.getCodigo().equals(fp)) {
                            comp.setFormaDePago(cs.getDescripcion());
                            paramReport.put("nombreFormaPago", cs.getDescripcion());
                        }

                    }

                    paramReport.put("tipoDeComprobante", eElement.getAttribute("tipoDeComprobante"));

                    totalTemporal = new BigDecimal(eElement.getAttribute("total"));

                    paramReport.put("total", totalTemporal);
                    paramReport.put("labelIva", "0%");
                    //$P{labelIva}

                    paramReport.put("Moneda", eElement.getAttribute("Moneda"));
                    BigDecimal descuento = new BigDecimal(eElement.getAttribute("descuento"));
                    paramReport.put("descuento", descuento);
                    BigDecimal subTotalTemporal = new BigDecimal(eElement.getAttribute("subTotal"));
                    paramReport.put("subTotal", subTotalTemporal);

                    paramReport.put("certificado", eElement.getAttribute("certificado"));
                    //System.out.println("Certificado : " + eElement.getAttribute("certificado"));
                    paramReport.put("noCertificado", eElement.getAttribute("noCertificado"));
                    //System.out.println("no. Certificado : " + eElement.getAttribute("noCertificado"));
                    paramReport.put("selloDigital", eElement.getAttribute("sello"));
                    //System.out.println("sello : " + eElement.getAttribute("sello"));

                    // folioQR = eElement.getAttribute("uuid");
                    //System.out.println("folio : " + eElement.getAttribute("folio"));
                    paramReport.put("fecha", eElement.getAttribute("fecha"));
                    //System.out.println("Fecha : " + eElement.getAttribute("fecha"));
                    paramReport.put("serie", eElement.getAttribute("serie"));
                    //System.out.println("version : " + eElement.getAttribute("serie"));
                    paramReport.put("version", eElement.getAttribute("version"));
                    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    DecimalFormat df = new DecimalFormat("###.##");
                    NumeroALetra numeroLetra = new NumeroALetra();
                    BigDecimal temporal = new BigDecimal(eElement.getAttribute("total"));
                    String totalVentaStr = numeroLetra.Convertir(df.format(temporal), true);
                    paramReport.put("importeLetra", totalVentaStr);

                }
            }
            nList = doc.getElementsByTagName("cfdi:Emisor");

            String rfcEmisor = "";
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("nombreEmisor", eElement.getAttribute("nombre"));
                    //System.out.println("Nombre : " + eElement.getAttribute("nombre"));
                    rfcEmisor = eElement.getAttribute("rfc");
                    paramReport.put("rfcEmisor", eElement.getAttribute("rfc"));
                    //System.out.println("rfc : " + eElement.getAttribute("rfc"));

                }
            }

            nList = doc.getElementsByTagName("cfdi:DomicilioFiscal");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("codigoPostalEmisor", eElement.getAttribute("codigoPostal"));
                    //System.out.println("Codigo Postal : " + eElement.getAttribute("codigoPostal"));
                    paramReport.put("estadoEmisor", eElement.getAttribute("estado"));
                    //System.out.println("Estado : " + eElement.getAttribute("estado"));
                    paramReport.put("municipioEmisor", eElement.getAttribute("municipio"));
                    //System.out.println("Municipio : " + eElement.getAttribute("municipio"));
                    paramReport.put("localidadEmisor", eElement.getAttribute("localidad"));
                    //System.out.println("Localidad : " + eElement.getAttribute("localidad"));
                    paramReport.put("coloniaEmisor", eElement.getAttribute("colonia"));
                    // System.out.println("Colonia : " + eElement.getAttribute("colonia"));
                    paramReport.put("noExteriorEmisor", eElement.getAttribute("noExterior"));
                    // System.out.println("N° Exterior : " + eElement.getAttribute("noExterior"));
                    paramReport.put("calleEmisor", eElement.getAttribute("calle"));
                    // System.out.println("Calle : " + eElement.getAttribute("calle"));

                }
            }
            nList = doc.getElementsByTagName("cfdi:RegimenFiscal");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("Regimen : " + eElement.getAttribute("Regimen"));
                    paramReport.put("regimen", eElement.getAttribute("Regimen"));

                }
            }
            nList = doc.getElementsByTagName("cfdi:Receptor");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("nombreReceptor", eElement.getAttribute("nombre"));
                    //System.out.println("Nombre : " + eElement.getAttribute("nombre"));
                    paramReport.put("rfcReceptor", eElement.getAttribute("rfc"));
                    rfcReceptorQR = eElement.getAttribute("rfc");
                    //System.out.println("rfc : " + eElement.getAttribute("rfc"));

                }
            }

            nList = doc.getElementsByTagName("cfdi:Domicilio");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("codigoPostalReceptor", eElement.getAttribute("codigoPostal"));
                    //System.out.println("Codigo Postal : " + eElement.getAttribute("codigoPostal"));
                    paramReport.put("estadoReceptor", eElement.getAttribute("estado"));
                    //System.out.println("Estado : " + eElement.getAttribute("estado"));
                    paramReport.put("municipioReceptor", eElement.getAttribute("municipio"));
                    //System.out.println("Municipio : " + eElement.getAttribute("municipio"));
                    paramReport.put("localidadReceptor", eElement.getAttribute("localidad"));
                    //System.out.println("Localidad : " + eElement.getAttribute("localidad"));
                    paramReport.put("coloniaReceptor", eElement.getAttribute("colonia"));
                    //System.out.println("Colonia : " + eElement.getAttribute("colonia"));
                    paramReport.put("noExteriorReceptor", eElement.getAttribute("noExterior"));
                    //System.out.println("N° Exterior : " + eElement.getAttribute("noExterior"));
                    paramReport.put("calleReceptor", eElement.getAttribute("calle"));
                    //System.out.println("Calle : " + eElement.getAttribute("calle"));

                }
            }

            NodeList feeds = doc.getElementsByTagName("cfdi:Conceptos");
            for (int i = 0; i < feeds.getLength(); i++) {
                Node mainNode = feeds.item(i);
                if (mainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstElement = (Element) mainNode;
                    //System.out.println("First element " + firstElement.getTagName());
                    NodeList forumidNameList = firstElement.getElementsByTagName("cfdi:Concepto");
                    for (int j = 0; j < forumidNameList.getLength(); ++j) {
                        Element eElement = (Element) forumidNameList.item(j);
                        VentaProductoMayoreo vpm = new VentaProductoMayoreo();
                        vpm.setTotalVenta(new BigDecimal(eElement.getAttribute("importe")));
                        //System.out.println("Importe : " + eElement.getAttribute("importe"));
                        vpm.setPrecioProducto(new BigDecimal(eElement.getAttribute("valorUnitario")));
                        // System.out.println("Valor Unitario : " + eElement.getAttribute("valorUnitario"));
                        vpm.setNombreProducto(eElement.getAttribute("descripcion"));
                        //System.out.println("Descripcion : " + eElement.getAttribute("descripcion"));
                        vpm.setNombreEmpaque(eElement.getAttribute("unidad"));
                        //System.out.println("unidad : " + eElement.getAttribute("unidad"));
                        vpm.setKilosVendidos(new BigDecimal(eElement.getAttribute("cantidad")));
                        // System.out.println("cantidad : " + eElement.getAttribute("cantidad"));
                        listaProductosReporte.add(vpm);
                    }

                }
            }
            JRBeanCollectionDataSource listaProductos = new JRBeanCollectionDataSource(listaProductosReporte);
            paramReport.put("lstProductos", listaProductos);

            nList = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
            //System.out.println("-----------Timbre Fiscal-----------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    paramReport.put("version", eElement.getAttribute("version"));
                    //System.out.println("Version : " + eElement.getAttribute("version"));
                    folioQR = eElement.getAttribute("UUID");
                    paramReport.put("folio", eElement.getAttribute("UUID"));
                    paramReport.put("UUID", eElement.getAttribute("UUID"));
                    //System.out.println("UUID : " + eElement.getAttribute("UUID"));
                    paramReport.put("FechaTimbrado", eElement.getAttribute("FechaTimbrado"));
                    //System.out.println("FechaTimbrado : " + eElement.getAttribute("FechaTimbrado"));
                    paramReport.put("selloCFD", eElement.getAttribute("selloCFD"));
                    //System.out.println("selloCFD : " + eElement.getAttribute("selloCFD"));
                    paramReport.put("noCertificadoSAT", eElement.getAttribute("noCertificadoSAT"));
                    //System.out.println("noCertificadoSAT : " + eElement.getAttribute("noCertificadoSAT"));
                    paramReport.put("selloSAT", eElement.getAttribute("selloSAT"));
                    //System.out.println("selloSAT : " + eElement.getAttribute("selloSAT"));

                }
            }
            String c = totalTemporal.toString();
            String[] parts = c.split("\\.");
            String parte1 = parts[0];
            String parte2 = parts[1];

            String q = "";
            String q2 = "";
            int cantidadCeros1 = 10 - parte1.length();
            while (cantidadCeros1 > 0) {
                q = q + "0";
                cantidadCeros1 = cantidadCeros1 - 1;
            }
            q = q + parte1;
            int cantidadCeros2 = 6 - parte2.length();
            while (cantidadCeros2 > 0) {
                q2 = q2 + "0";
                cantidadCeros2 = cantidadCeros2 - 1;
            }
            q2 = parte2 + q2;
            String totalQRCombinado = q + "." + q2;

            String cadenaQR = "?re=" + rfcEmisor + "&rr=" + rfcReceptorQR + "&tt=" + totalQRCombinado + "&id=" + folioQR + "";

            paramReport.put("cadenaQR", cadenaQR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Random random = new Random();
        //Se genera un numero aleatorio para que no traiga el mismo reporte por la cache
        int numberRandom = random.nextInt(999);

        generateReport(data, usuario.getIdUsuario(), new BigDecimal(numberRandom));
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
    }

    public String verificarDatos() {
        /* 1.- Verificar Datos de Empresa;
           2.- Verificar Datos de Cliente;
           3.- Verificar Productos de Facturación; (que no este vacia la lista)
        
         */
        boolean bandera = false;
        String mensaje = "";
        for (DatosFacturacion empresa : listaDatosEmisor) {
            if (empresa.getIdDatosFacturacionPk().intValue() == idEmisorPk.intValue()) {

                if (empresa.getCalle() == null) {
                    bandera = true;
                    mensaje += " Falta Calle Emisor,";
                }
                if (empresa.getClavePublica() == null) {
                    bandera = true;
                    mensaje += " Falta Clave Publica Emisor,";
                }
                if (empresa.getCodigoPostal() == null) {
                    bandera = true;
                    mensaje += " Falta Codigo Postal Emisor,";
                }
                if (empresa.getEstado() == null) {
                    bandera = true;
                    mensaje += " Falta Estado Emisor ,";
                }
                if (empresa.getLocalidad() == null) {
                    bandera = true;
                    mensaje += " Falta Localidad Emisor,";
                }
                if (empresa.getMunicipio() == null) {
                    bandera = true;
                    mensaje += " Falta Municipio Emisor,";
                }
                if (empresa.getNumExt() == null) {
                    bandera = true;
                    mensaje += "Falta Número Exterior Emisor,";
                }
//                if (empresa.getNumInt() == null) {
//                    bandera = true;
//                    mensaje += "Falta Número Interior Emisor,";
//                }
                if (empresa.getPais() == null) {
                    bandera = true;
                    mensaje += "Falta Pais Emisor,";
                }
                if (empresa.getRazonSocial() == null) {
                    bandera = true;
                    mensaje += "Falta Razón Social Emisor,";
                }
                if (empresa.getRegimen() == null) {
                    bandera = true;
                    mensaje += "Falta Regimen Emisor,";
                }
                if (empresa.getRfc() == null) {
                    bandera = true;
                    mensaje += " Falta RCF Emisor,";
                }
                if (empresa.getRuta_certificado() == null) {
                    bandera = true;
                    mensaje += " Falta Certificado .cer Emisor,";
                }
                if (empresa.getRuta_llave_privada() == null) {
                    bandera = true;
                    mensaje += "  Falta Llave Privada .key Emisor, ";
                }//fin if
            }
        }//fin for

        //Verificando Datos de Cliente
        //Verificar Venta:
//        System.out.println("---------------------------------------");
//        System.out.println(ventaMayoreo.toString());
//        System.out.println(ventaMayoreo.getListaProductos());
//        System.out.println("Tamaño: " + ventaMayoreo.getListaProductos().size());
//        System.out.println("----------------------------------------");
        if (selectedProductosVentas != null && !selectedProductosVentas.isEmpty()) {
            ventaMayoreo.setIdVentaMayoreoPk(selectedProductosVentas.get(0).getFolioVenta());
            ventaMayoreo.setVentaSucursal(selectedProductosVentas.get(0).getFolioVenta());
            ventaMayoreo.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        }

        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
            mensaje += "  Ese folio de venta no existe.";
        }
        if (ventaMayoreo.getListaProductos() == null || ventaMayoreo.getListaProductos().isEmpty() || ventaMayoreo.getListaProductos().size() == 0) {
            mensaje += "  Ese folio de venta ya se encuentra facturado totalmente";
            //System.out.println("Entro a validacion");
        }
        //System.out.println("Total: " + total);
        if (total.setScale(2, RoundingMode.CEILING).compareTo(new BigDecimal(0).setScale(2, RoundingMode.CEILING)) < 1) {
            mensaje += "  El total de la factura es igual o menor que cero";
        }
        //System.out.println("Mensaje: " + mensaje);
        return mensaje;

    }

    public void changeViewGenerarFactura() {
        statusButtonFacturar = true;
        datosCliente = new Cliente();
        ventaMayoreo = new VentaMayoreo();
        setViewEstate("generate");
        setTitle("Generar Factura");
        // calcularTotales();
    }

    public void habilitarImporteParcial() {
        //System.out.println("forma de pago: " + formaPago);
        if (formaPago.equals("02") || formaPago.equals("03")) {
            banderaImporteParcialidad = "true";

        } else {
            banderaImporteParcialidad = "false";
        }
    }

    public void habilitarNumcuenta() {
        //System.out.println("Método de pago: " + metodoPago);
        if (!metodoPago.equals("01")) {
            banderanumCuenta = "true";

        } else {
            banderanumCuenta = "false";
        }
    }

    public void backView() {
        setViewEstate("init");
        setTitle("Facturación Electrónica");
        modelo.clear();
        buscarFacturas();

    }

    public void changeViewMostrador() {
        ventaMayoreo = new VentaMayoreo();
        statusButtonFacturar = true;
        if (filtroMostrador == 1) {
            setViewEstate("generate");
            disableBotonBuscarVenta = false;
            disableTextFolioVenta = false;
        } else {
            setViewEstate("generateVentaMostrador");
            disableBotonBuscarVenta = true;
            disableTextFolioVenta = true;
        }
        buscarDatosCliente(2);
        verificarComboPublico();
        buscaVentasNoFacturadas();

    }

    public void onRowEdit(RowEditEvent event) {
        dataEdit = new VentaProductoMayoreo();

        dataEdit = (VentaProductoMayoreo) event.getObject();
        dataEdit.setTotalVenta(dataEdit.getPrecioProducto().multiply(dataEdit.getKilosVendidos(), MathContext.UNLIMITED));

        //System.out.println("editado " + dataEdit.toString());
        calculaImportePublico();
    }

    public void onRowCancel(RowEditEvent event) {
        //System.out.println("cancel");

    }

    public int insertarFactura() throws IOException {
        nuevaFactura.setIdFacturaPk(new BigDecimal(ifaceFacturas.getNextVal()));
        int n = ifaceFacturas.getLastNumeroFactura() + 1;
        nuevaFactura.setNumeroFactura(Integer.toString(n));

        nuevaFactura.setIdClienteFk(datosCliente.getIdClientePk());
        nuevaFactura.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        nuevaFactura.setFechaCertificacion(new Date());
        nuevaFactura.setComentarios("Pruebas de Factucion");
        //nuevaFactura.setFichero(fichero);
        nuevaFactura.setIdUsuarioFk(usuario.getIdUsuario());
        nuevaFactura.setIdStatusFk(new BigDecimal(1));

        if (ventaMayoreo.isBanderaVentaMenudeo()) {
            nuevaFactura.setIdTipoLlaveFk(new BigDecimal(1));
        } else {
            nuevaFactura.setIdTipoLlaveFk(new BigDecimal(2));

        }
        nuevaFactura.setIdLlaveFk(ventaMayoreo.getIdVentaMayoreoPk());
        nuevaFactura.setNombreArchivoTimbrado(datosCliente.getRfc() + "_" + ventaMayoreo.getIdSucursalFk().toString() + "_" + ventaMayoreo.getVentaSucursal().toString() + ".xml");
        nuevaFactura.setRfcEmisor(datosEmisor.getRfc());
        nuevaFactura.setRfcCliente(datosCliente.getRfc());

        Path path = Paths.get(pathFacturaClienteTimbrado);
        byte[] data = Files.readAllBytes(path);
        nuevaFactura.setFichero(data);
        nuevaFactura.setImporte(ventaMayoreo.getTotalVenta());
        nuevaFactura.setDescuento(descuento);
        nuevaFactura.setIva1(iva);

        if (ifaceFacturas.insert(nuevaFactura) == 1) {
            //Convertir el archivo a Bytes y Guardarlo
            if (selectedProductosVentas != null && !selectedProductosVentas.isEmpty()) {

            }
            for (VentaProductoMayoreo vmp : ventaMayoreo.getListaProductos()) {
                ProductoFacturado pf = new ProductoFacturado();
                pf.setCantidad(new BigDecimal(0));
                pf.setIdFacturaFk(nuevaFactura.getIdFacturaPk());
                pf.setIdTipoLlaveFk(vmp.getIdTipoVenta());
                pf.setIdLlaveFk(vmp.getIdVentaMayProdPk());
                pf.setIdProductoFacturadoPk(new BigDecimal(ifaceProductoFacturado.getNextVal()));
                pf.setImporte(vmp.getTotalVenta());
                pf.setKilos(vmp.getKilosVendidos());
                ifaceProductoFacturado.insert(pf);
            }
            //Generar PDF
            JsfUtil.addSuccessMessageClean("Se ha generado la factura exitosamente");
            return 1;
        } else {

            JsfUtil.addErrorMessageClean("Ocurrió un error al insertar la factura");
            return 0;
        }

    }

    public int timbrar() {
        int bandera = 0;
//        String xmlPruebasTimbrado = "<?xml version='1.0' encoding='UTF-8'?>\n"
//                + "<cfdi:Comprobante xmlns:cfdi='http://www.sat.gob.mx/cfd/3' xmlns:tfd='http://www.sat.gob.mx/TimbreFiscalDigital' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd' version='3.2' serie='A' folio='15861' fecha='2017-03-15T09:33:14' sello='TdH9hapysPLZOLUe2fnsgLpXR8n7Xvh9VJ+yHHqjaYHLHmI6uhJi+N7IfwbVpK0KDIAs6XWQSt4iQzhn+lLiMn92SouG5QQJSA3aylrcs+vUi8hj0uoe2YA6V/XIc0GDfiHuOrEx/Rk3UWLwpJAO9ORSKx0PtYy3ANedvX2cSio=' formaDePago='PAGO EN UNA SOLA EXHIBICION' noCertificado='20001000000200001428' certificado='MIIEYTCCA0mgAwIBAgIUMjAwMDEwMDAwMDAyMDAwMDE0MjgwDQYJKoZIhvcNAQEFBQAwggFcMRowGAYDVQQDDBFBLkMuIDIgZGUgcHJ1ZWJhczEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMSkwJwYJKoZIhvcNAQkBFhphc2lzbmV0QHBydWViYXMuc2F0LmdvYi5teDEmMCQGA1UECQwdQXYuIEhpZGFsZ28gNzcsIENvbC4gR3VlcnJlcm8xDjAMBgNVBBEMBTA2MzAwMQswCQYDVQQGEwJNWDEZMBcGA1UECAwQRGlzdHJpdG8gRmVkZXJhbDESMBAGA1UEBwwJQ295b2Fjw6FuMTQwMgYJKoZIhvcNAQkCDCVSZXNwb25zYWJsZTogQXJhY2VsaSBHYW5kYXJhIEJhdXRpc3RhMB4XDTEzMDUwNzE2MDEyOVoXDTE3MDUwNzE2MDEyOVowgdsxKTAnBgNVBAMTIEFDQ0VNIFNFUlZJQ0lPUyBFTVBSRVNBUklBTEVTIFNDMSkwJwYDVQQpEyBBQ0NFTSBTRVJWSUNJT1MgRU1QUkVTQVJJQUxFUyBTQzEpMCcGA1UEChMgQUNDRU0gU0VSVklDSU9TIEVNUFJFU0FSSUFMRVMgU0MxJTAjBgNVBC0THEFBQTAxMDEwMUFBQSAvIEhFR1Q3NjEwMDM0UzIxHjAcBgNVBAUTFSAvIEhFR1Q3NjEwMDNNREZOU1IwODERMA8GA1UECxMIcHJvZHVjdG8wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKS/beUVy6E3aODaNuLd2S3PXaQre0tGxmYTeUxa55x2t/7919ttgOpKF6hPF5KvlYh4ztqQqP4yEV+HjH7yy/2d/+e7t+J61jTrbdLqT3WD0+s5fCL6JOrF4hqy//EGdfvYftdGRNrZH+dAjWWml2S/hrN9aUxraS5qqO1b7btlAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQACPXAWZX2DuKiZVv35RS1WFKgT2ubUO9C+byfZapV6ZzYNOiA4KmpkqHU/bkZHqKjR+R59hoYhVdn+ClUIliZf2ChHh8s0a0vBRNJ3IHfA1akWdzocYZLXjz3m0Er31BY+uS3qWUtPsONGVDyZL6IUBBUlFoecQhP9AO39er8zIbeU2b0MMBJxCt4vbDKFvT9i3V0Puoo+kmmkf15D2rBGR+drd8H8Yg8TDGFKf2zKmRsgT7nIeou6WpfYp570WIvLJQY+fsMp334D05Up5ykYSAxUGa30RdUzA4rxN5hT+W9whWVGD88TD33Nw55uNRUcRO3ZUVHmdWRG+GjhlfsD' subTotal='11040.00' descuento='0' Moneda='MXN' total='11040.00' tipoDeComprobante='egreso' metodoDePago='01' LugarExpedicion='09040'><cfdi:Emisor rfc='AAA010101AAA' nombre='COMERCIALIZADORA Y EXPORTADORA CHONAJOS S DE RL DE CV'><cfdi:DomicilioFiscal calle='AVENIDA TRABAJADORES SOCIALES EJE 5 ZONA V SECCION 5 NAVE 2' noExterior='BODEGA Q 85' colonia='Central de Abasto' localidad='MEXICO' municipio='Iztapalapa' estado='Ciudad de México' pais='MEXICO' codigoPostal='09040'/><cfdi:RegimenFiscal Regimen='REGIMEN GENERAL DE LEY'/></cfdi:Emisor><cfdi:Receptor rfc='VEHC761003U32' nombre='Juan  De la cruz  Sistemas'><cfdi:Domicilio calle='CALLESITA' colonia='Maguey Blanco' municipio='Ixmiquilpan' estado='Hidalgo' pais='MEXICO' codigoPostal='42320'/></cfdi:Receptor><cfdi:Conceptos><cfdi:Concepto cantidad='150' unidad='KILOS' descripcion='Ajo Fancy' valorUnitario='56' importe='8400'/><cfdi:Concepto cantidad='30' unidad='KILOS' descripcion='Ajo CHILENO 6X' valorUnitario='63' importe='1890'/><cfdi:Concepto cantidad='15' unidad='KILOS' descripcion='Morita' valorUnitario='50' importe='750'/></cfdi:Conceptos><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado impuesto='IVA' tasa='16.00' importe='0'/></cfdi:Traslados></cfdi:Impuestos><cfdi:Complemento><tfd:TimbreFiscalDigital xsi:schemaLocation='http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/TimbreFiscalDigital/TimbreFiscalDigital.xsd' version='1.0' UUID='FFFFFFFF-3D6D-180C-2272-6941C55B4D59' FechaTimbrado='2017-03-15T09:33:26' selloCFD='TdH9hapysPLZOLUe2fnsgLpXR8n7Xvh9VJ+yHHqjaYHLHmI6uhJi+N7IfwbVpK0KDIAs6XWQSt4iQzhn+lLiMn92SouG5QQJSA3aylrcs+vUi8hj0uoe2YA6V/XIc0GDfiHuOrEx/Rk3UWLwpJAO9ORSKx0PtYy3ANedvX2cSio=' noCertificadoSAT='30001000000100000801' selloSAT='C0FgUTSh++khoKQ5QTXIql5U/mHgu++H5Niga9ldPt9fB9Y5mqjxMI+QRmQlFGImxVYKVQyiMF4tcpnzMp8Ue5zDNmjYsMk+bjkyZy5jCBaH9AXZAqOGwvbI5y570pUV5WEzltQ3SzUUKdLx3hLh/KOYQwBD4RvefmzOkzMsbMY='/></cfdi:Complemento></cfdi:Comprobante>";

        try {
            RespuestaTimbre2 respuesta = new RespuestaTimbre2();
            AdvanswsdlLocator service = new AdvanswsdlLocator();
            pathFacturaClienteComprobante = Constantes.PATHLOCALFACTURACIONINVERTIDA + "Clientes" + File.separatorChar + datosCliente.getRfc() + File.separatorChar + "COMPROBANTE" + File.separatorChar + datosCliente.getRfc() + "_" + ventaMayoreo.getIdSucursalFk().toString() + "_" + ventaMayoreo.getVentaSucursal().toString() + ".xml";
            URL url = new URL(pathFacturaClienteComprobante);
            InputStreamReader in = new InputStreamReader(url.openStream());
            String xmlCfdi = IOUtils.toString(in);
            advanswsdl_pkg.AdvanswsdlPortType port = service.getadvanswsdlPort();
            respuesta = port.timbrar2(API_KEY, xmlCfdi);
            if (respuesta.getCode().equals(200)) {
                //Solictud de timbrado procesada.

                try {
                    PrintWriter writer = new PrintWriter(pathFacturaClienteTimbrado, "UTF-8");
                    writer.println(respuesta.getCFDI());
                    writer.close();
                    //System.out.println("Se Guardo Correctamente el archivo Timbrado");
                    if (insertarFactura() == 1) {
                        bandera = 1;
                    }
                } catch (IOException e) {
                    //System.out.println("Error al generar archivo Timbrado");
                    JsfUtil.addErrorMessageClean("Ocurrió un error al generar archivo timbrado");
                    bandera = 0;
                }
            } else {
                JsfUtil.addErrorMessageClean("Error: " + respuesta.getMessage());
                bandera = 0;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            bandera = 0;
        } catch (IOException ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            bandera = 0;
        } catch (Exception ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            bandera = 0;
        }
        return bandera;
    }

    public void imprimirDatosFactura() {
//        System.out.println("Certificado: " + comp.getCertificado());
//        System.out.println("Condiciones de Pago: " + comp.getCondicionesDePago());
//        System.out.println("Folio: " + comp.getFolio());
//        System.out.println("Folio Fiscal Origen: " + comp.getFolioFiscalOrig());
//        System.out.println("Forma de Pago: " + comp.getFormaDePago());
//        System.out.println("Lugar de Expedicion: " + comp.getLugarExpedicion());
//        System.out.println("Metodo de Pago: " + comp.getMetodoDePago());
//        System.out.println("Moneda: " + comp.getMoneda());
//        System.out.println("Motivo Descuento: " + comp.getMotivoDescuento());
//        System.out.println("No Certificado: " + comp.getNoCertificado());
//        System.out.println("Num Cta Pago: " + comp.getNumCtaPago());
//        System.out.println("Sello: " + comp.getSello());
//        System.out.println("Serie: " + comp.getSerie());
//        System.out.println("Folio Fiscal Origen: " + comp.getSerieFolioFiscalOrig());
//        System.out.println("Tipo Cambio: " + comp.getTipoCambio());
//        System.out.println("Tipo Comprobante: " + comp.getTipoDeComprobante());
//        System.out.println("Version: " + comp.getVersion());
//        System.out.println("Descuento: " + comp.getDescuento());
//        System.out.println("Fecha: " + comp.getFecha());
//        System.out.println("Certificado: " + comp.getCertificado());
//        System.out.println("---------------------DATOS DE EMISOR------------------------------");
//        System.out.println("Nombre Emisor: " + comp.getEmisor().getNombre());
//        System.out.println("RFC Emisor: " + comp.getEmisor().getRfc());
//        System.out.println("Domicilo Fiscal Emisor:");
//        System.out.println("    Calle: " + comp.getEmisor().getDomicilioFiscal().getCalle());
//        System.out.println("    Codigo Postal: " + comp.getEmisor().getDomicilioFiscal().getCodigoPostal());
//        System.out.println("    Colonia: " + comp.getEmisor().getDomicilioFiscal().getColonia());
//        System.out.println("    Estado: " + comp.getEmisor().getDomicilioFiscal().getEstado());
//        System.out.println("    Municipio: " + comp.getEmisor().getDomicilioFiscal().getMunicipio());
//        System.out.println("    Localidad: " + comp.getEmisor().getDomicilioFiscal().getLocalidad());
//        System.out.println("    No. Exterior: " + comp.getEmisor().getDomicilioFiscal().getNoExterior());
//        System.out.println("    No. Interior " + comp.getEmisor().getDomicilioFiscal().getNoInterior());
//        System.out.println("    Pais: " + comp.getEmisor().getDomicilioFiscal().getPais());
//        System.out.println("    Referencia: " + comp.getEmisor().getDomicilioFiscal().getReferencia());
//
//        System.out.println("Expedido en: " + comp.getEmisor().getExpedidoEn());
//
//        System.out.println("---------------------DATOS CLIENTE------------------------------");
//        System.out.println("Nombre Receptor: " + comp.getReceptor().getNombre());
//        System.out.println("RFC Receptor: " + comp.getReceptor().getRfc());
//        System.out.println("Domicilo Fiscal Receptor:");
//        System.out.println("    Calle: " + comp.getReceptor().getDomicilio().getCalle());
//        System.out.println("    Codigo Postal: " + comp.getReceptor().getDomicilio().getCodigoPostal());
//        System.out.println("    Colonia: " + comp.getReceptor().getDomicilio().getColonia());
//        System.out.println("    Estado: " + comp.getReceptor().getDomicilio().getEstado());
//        System.out.println("    Municipio: " + comp.getReceptor().getDomicilio().getMunicipio());
//        System.out.println("    Localidad: " + comp.getReceptor().getDomicilio().getLocalidad());
//        System.out.println("    No. Exterior: " + comp.getReceptor().getDomicilio().getNoExterior());
//        System.out.println("    No. Interior " + comp.getReceptor().getDomicilio().getNoInterior());
//        System.out.println("    Pais: " + comp.getReceptor().getDomicilio().getPais());
//        System.out.println("    Referencia: " + comp.getReceptor().getDomicilio().getReferencia());
//   
    }

    public String crearFactura() throws Exception {
        String men = verificarDatos();
        //System.out.println("MensajeBean: " + men);
        if (men.equals("")) {
            for (DatosFacturacion df : listaDatosEmisor) {
                if (df.getIdDatosFacturacionPk().intValue() == idEmisorPk.intValue()) {
                    datosEmisor.setCalle(df.getCalle() == null ? "" : df.getCalle());
                    datosEmisor.setClavePublica(df.getClavePublica() == null ? "" : df.getClavePublica());
                    datosEmisor.setCodigoPostal(df.getCodigoPostal() == null ? "" : df.getCodigoPostal());
                    datosEmisor.setColonia(df.getColonia() == null ? "" : df.getColonia());
                    datosEmisor.setCorreo(df.getCorreo() == null ? "" : df.getCorreo());
                    datosEmisor.setEstado(df.getEstado() == null ? "" : df.getEstado());
                    datosEmisor.setField(df.getField() == null ? "" : df.getField());
                    datosEmisor.setIdClienteFk(df.getIdClienteFk() == null ? null : df.getIdClienteFk());
                    datosEmisor.setIdCodigoPostalFk(df.getIdCodigoPostalFk() == null ? null : df.getIdCodigoPostalFk());
                    datosEmisor.setLocalidad(df.getLocalidad() == null ? "" : df.getLocalidad());
                    datosEmisor.setMunicipio(df.getMunicipio() == null ? "" : df.getMunicipio());
                    datosEmisor.setNombre(df.getNombre() == null ? "" : df.getNombre());
                    datosEmisor.setNumExt(df.getNumExt() == null ? "" : df.getNumExt());
                    datosEmisor.setNumInt(df.getNumInt() == null ? "" : df.getNumInt());
                    datosEmisor.setPais(df.getPais() == null ? "" : df.getPais());
                    datosEmisor.setRazonSocial(df.getRazonSocial() == null ? "" : df.getRazonSocial());
                    datosEmisor.setRegimen(df.getRegimen() == null ? "" : df.getRegimen());
                    datosEmisor.setRfc(df.getRfc() == null ? "" : df.getRfc());
                    datosEmisor.setRuta_certificado(df.getRuta_certificado() == null ? "" : Constantes.PATHLOCALFACTURACION + "Empresas" + File.separatorChar + df.getRfc() + File.separatorChar + df.getRuta_certificado());
                    datosEmisor.setRuta_certificado_cancel(df.getRuta_certificado_cancel() == null ? "" : df.getRuta_certificado_cancel());
                    datosEmisor.setRuta_llave_privada(df.getRuta_llave_privada() == null ? "" : Constantes.PATHLOCALFACTURACION + "Empresas" + File.separatorChar + df.getRfc() + File.separatorChar + df.getRuta_llave_privada());
                    datosEmisor.setRuta_llave_privada_cancel(df.getRuta_llave_privada_cancel() == null ? "" : df.getRuta_llave_privada_cancel());
                    datosEmisor.setTelefono(df.getTelefono() == null ? "" : df.getTelefono());
                }
            }

            String pathRFC = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + datosCliente.getRfc();

            File folderClienteRFC = new File(pathRFC);
            File folderClienteComprobante = new File(pathRFC + File.separatorChar + "COMPROBANTE");
            File folderClienteTimbrado = new File(pathRFC + File.separatorChar + "TIMBRADO");

            if (!folderClienteRFC.exists()) {
                folderClienteRFC.mkdirs();
            }
            if (!folderClienteComprobante.exists()) {
                folderClienteComprobante.mkdirs();
            }
            if (!folderClienteTimbrado.exists()) {
                folderClienteTimbrado.mkdirs();
            }

            pathFacturaClienteComprobante = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + datosCliente.getRfc() + File.separatorChar + "COMPROBANTE" + File.separatorChar + datosCliente.getRfc() + "_" + ventaMayoreo.getIdSucursalFk().toString() + "_" + ventaMayoreo.getVentaSucursal().toString() + ".xml";
            pathFacturaClienteTimbrado = Constantes.PATHLOCALFACTURACION + "Clientes" + File.separatorChar + datosCliente.getRfc() + File.separatorChar + "TIMBRADO" + File.separatorChar + datosCliente.getRfc() + "_" + ventaMayoreo.getIdSucursalFk().toString() + "_" + ventaMayoreo.getVentaSucursal().toString() + ".xml";

            CFDv32 cfd = new CFDv32(createComprobante(), "mx.bigdata.sat.cfdi.examples");
            //Cargamos el archivo con la llave

            //System.out.println("Ruta llave: " + datosEmisor.getRuta_llave_privada());
            //System.out.println("Ruta Certificado: " + datosEmisor.getRuta_certificado());
            PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(datosEmisor.getRuta_llave_privada()), datosEmisor.getClavePublica()).getKey();
            //Cargamos el archivo con el certificado
            String[] ce;
            //System.out.println("***************");
            ce = datosEmisor.getRuta_certificado().split("\\.");
            comp.setNoCertificado(ce[0]);//es el numero del certificado

            // System.out.println("Se cargaron exitosamente los archivos");
            X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(datosEmisor.getRuta_certificado())).getKey();

            mx.bigdata.sat.cfdi.v32.schema.Comprobante sellado = cfd.sellarComprobante(key, cert);

            FileOutputStream outFile = new FileOutputStream(pathFacturaClienteComprobante);
            imprimirDatosFactura();
            cfd.validar();
//        //System.out.println("Se valido el cdf ");
            cfd.verificar();
            // System.out.println("Se verifico el cfd ");
            cfd.guardar(outFile);
            // System.out.println("Se guardo el cdf ");
            // System.out.println("cadema original " + cfd.getCadenaOriginal());

            nuevaFactura.setCadena(cfd.getCadenaOriginal());
            outFile.close();

            if (timbrar() == 1) {
                datosCliente = new Cliente();
                ventaMayoreo = new VentaMayoreo();
                if (selectedProductosVentas != null) {
                    selectedProductosVentas.clear();
                }
                importeParcialidad = null;
                formaPago = "01";
                subTotal = null;
                total = null;
            }

            return "facturacion";
        }//fin if validaciones
        else {
            JsfUtil.addErrorMessageClean("Error: " + men);
            return null;
        }

    }

    public Comprobante createComprobante() {
        //====INICIALIZACIÓN DE LOS DATOS DE COMPROBANTE DIGITAL===//
        comp.setVersion("3.2");
        comp.setSerie("A");
        comp.setFolio(ventaMayoreo.getVentaSucursal().toString());
        context.getFechaSistema();
        Date f = TiempoUtil.getFechaDDMMYYYYDate(context.getFechaSistema());

        //System.out.println("Fecha de Sistema: " + f);
        comp.setFecha(context.getFechaSistema());
        for (CatalogoSat cs : listaFormaPago) {

            if (cs.getCodigo().toString().equals(formaPago)) {
                comp.setFormaDePago(cs.getDescripcion());
                paramReport.put("nombreFormaPago", cs.getDescripcion());
            }
        }

        comp.setSubTotal(subTotal);
        comp.setMetodoDePago(metodoPago);
        comp.setDescuento(descuento);
        comp.setTotal(total);
        for (CatalogoSat cs : listaTipoDocumento) {

            if (cs.getIdCatalogoSatPk().equals(tipoDocumento)) {
                comp.setTipoDeComprobante(cs.getCodigo());
            }
        }

        comp.setLugarExpedicion(datosEmisor.getCodigoPostal());
        comp.setMoneda(moneda);
        //datosEmisor.getRuta_certificado()

        comp.setNumCtaPago(numeroCuenta == null ? null : numeroCuenta.toString());
        comp.setEmisor(createEmisor(of));
        comp.setReceptor(createReceptor(of));
        comp.setConceptos(createConceptos(of));
        comp.setImpuestos(createImpuestos(of));

        return comp;

    }

    private Comprobante.Emisor createEmisor(ObjectFactory of) {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre(datosEmisor.getRazonSocial());

        emisor.setRfc(datosEmisor.getRfc());
//        emisor.setRfc("CEC031212QF1");
        mx.bigdata.sat.cfdi.v32.schema.TUbicacionFiscal uf = of.createTUbicacionFiscal();
        uf.setCalle(datosEmisor.getCalle());
        uf.setNoExterior(datosEmisor.getNumExt());

        uf.setColonia(datosEmisor.getColonia());
        uf.setLocalidad(datosEmisor.getLocalidad());
        uf.setMunicipio(datosEmisor.getMunicipio());
        uf.setEstado(datosEmisor.getEstado());
        uf.setPais(datosEmisor.getPais());
        uf.setCodigoPostal(datosEmisor.getCodigoPostal());
        emisor.setDomicilioFiscal(uf);

        //EXPEDIDO EN NO ES OBLIGATORIO
//    mx.bigdata.sat.cfdi.v32.schema.TUbicacion u = of.createTUbicacion();
//    u.setCalle("AV. UNIVERSIDAD");
//    u.setCodigoPostal("09040");
//    u.setColonia("CENTRAL DE ABASTOS"); 
//    u.setEstado("DISTRITO FEDERAL");
//    u.setNoExterior("Q85");
//    u.setPais("Mexico"); 
//    emisor.setExpedidoEn(u);
//REGIMEN FISCAL
        Comprobante.Emisor.RegimenFiscal regimenFiscal = new Comprobante.Emisor.RegimenFiscal();
        regimenFiscal.setRegimen("REGIMEN GENERAL DE LEY");
        ArrayList<Comprobante.Emisor.RegimenFiscal> lstRegimenFiscal = new ArrayList<Comprobante.Emisor.RegimenFiscal>();
        lstRegimenFiscal.add(regimenFiscal);

//        emisor.setRegimenFiscal(lstRegimenFiscal);
        emisor.getRegimenFiscal().add(regimenFiscal);

        return emisor;
    }

    private Comprobante.Receptor createReceptor(ObjectFactory of) {

        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setNombre(datosCliente.getNombre());
        receptor.setRfc(datosCliente.getRfc());

        mx.bigdata.sat.cfdi.v32.schema.TUbicacion uf = of.createTUbicacion();
        uf.setCalle(datosCliente.getCalle());
        uf.setColonia(datosCliente.getNombreColonia());
        uf.setMunicipio(datosCliente.getNombreMunicipio());
        uf.setEstado(datosCliente.getNombreEstado());
        uf.setPais(datosCliente.getPais());
        uf.setLocalidad(datosCliente.getLocalidad());
        uf.setCodigoPostal(datosCliente.getCodigoPostal());

        //NO SON OBLIGATORIOS NO SE OCUPAN POR AHORA
        uf.setNoExterior(datosCliente.getNumExterior());
        uf.setNoInterior(datosCliente.getNumInterior());
        receptor.setDomicilio(uf);
        return receptor;
    }

    private Comprobante.Conceptos createConceptos(ObjectFactory of) {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        List<Comprobante.Conceptos.Concepto> list = cps.getConcepto();
        if (selectedProductosVentas != null && !selectedProductosVentas.isEmpty()) {
            ventaMayoreo.setListaProductos(selectedProductosVentas);
        }
        for (VentaProductoMayoreo producto : ventaMayoreo.getListaProductos()) {
            Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
            c1.setCantidad(producto.getKilosVendidos());
            c1.setUnidad("KILOS");
            c1.setDescripcion(producto.getNombreProducto());
            c1.setValorUnitario(producto.getPrecioProductoSinIva());
            c1.setImporte(producto.getTotalVentaSinIva());
            list.add(c1);
        }

        return cps;
    }

    private Comprobante.Impuestos createImpuestos(ObjectFactory of) {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        t1.setImporte(iva);
        t1.setImpuesto("IVA");

        for (CatalogoSat cs : listaTipoDocumento) {
            if (cs.getIdCatalogoSatPk().intValue() == tipoDocumento.intValue()) {
                t1.setTasa((cs.getValor().multiply(new BigDecimal(100), MathContext.UNLIMITED)).setScale(2, RoundingMode.CEILING));
            }
        }

        list.add(t1);
//        Comprobante.Impuestos.Traslados.Traslado t2 = of.createComprobanteImpuestosTrasladosTraslado();
//        t2.setImporte(new BigDecimal("22.07"));
//        t2.setImpuesto("IVA");
//        t2.setTasa(new BigDecimal("16.00"));
//        list.add(t2);
        imps.setTraslados(trs);
        return imps;
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

                    break;
                default:
                    fechaFiltroFin = null;
                    fechaFiltroFin = null;

                    break;
            }
            enableCalendar = true;
        }

    }

    public void verificarComboPublico() {

        if (filtroPublico == -1) {
            //se habilitan los calendarios.
            //fechaFiltroInicio = null;
            //fechaFiltroFin = null;
            enableCalendarPublico = false;
        } else {
            switch (filtroPublico) {
                case 1:
                    fechaFiltroInicioPublico = new Date();
                    fechaFiltroFinPublico = new Date();
                    //System.out.println("1");
                    break;
                case 2:
                    fechaFiltroInicioPublico = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFinPublico = TiempoUtil.getDayEndOfMonth(new Date());
                    // System.out.println("2");
                    break;
                case 3:
                    List<String> listaFechas = TiempoUtil.getintervalWeekDDMMYYYYbyDay(new Date());
                    for (String item : listaFechas) {
                        //  System.out.println(item);
                    }

                    fechaFiltroInicioPublico = TiempoUtil.getFechaDDMMYYYY(listaFechas.get(0));

                    fechaFiltroFinPublico = TiempoUtil.getFechaDDMMYYYY(listaFechas.get(6));
                    //fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
                    //System.out.println("3");
                    break;
                default:
                    fechaFiltroFinPublico = null;
                    fechaFiltroInicioPublico = null;

                    break;
            }
            enableCalendarPublico = true;
        }

        // buscaVentasNoFacturadas();
    }

    public void generateReport(FacturaPDFDomain fac, BigDecimal idSucu, BigDecimal random) {
        JRExporter exporter = null;
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }
            JasperPrint jp = null;
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "facturas" + File.separatorChar + "factura.jasper";
            //JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport);
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "factura", idSucu.intValue(), random.intValue());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }
    }

    public void getCorreoCliente() {
        Cliente c = ifaceCatCliente.getClienteById(data.getIdClienteFk());
        DatosFacturacion df = ifaceDatosFacturacion.getByRfc(data.getRfcEmisor());

        if (c != null && c.getCorreo() != null) {
            correo = c.getCorreo();
        }
        
       if(df != null && df.getCorreo() != null){
           if(correo != null && !correo.isEmpty()){
               correo+=";"+df.getCorreo();
           }else{
               correo=df.getCorreo();
           }
       }
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

    public Cliente getDatosCliente() {
        return datosCliente;
    }

    public void setDatosCliente(Cliente datosCliente) {
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

    public BigDecimal getImporteParcialidad() {
        return importeParcialidad;
    }

    public void setImporteParcialidad(BigDecimal importeParcialidad) {
        this.importeParcialidad = importeParcialidad;
    }

    public String getBanderaImporteParcialidad() {
        return banderaImporteParcialidad;
    }

    public void setBanderaImporteParcialidad(String banderaImporteParcialidad) {
        this.banderaImporteParcialidad = banderaImporteParcialidad;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(BigDecimal tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public ArrayList<CatalogoSat> getListaMetodosPago() {
        return listaMetodosPago;
    }

    public void setListaMetodosPago(ArrayList<CatalogoSat> listaMetodosPago) {
        this.listaMetodosPago = listaMetodosPago;
    }

    public ArrayList<CatalogoSat> getListaFormaPago() {
        return listaFormaPago;
    }

    public void setListaFormaPago(ArrayList<CatalogoSat> listaFormaPago) {
        this.listaFormaPago = listaFormaPago;
    }

    public ArrayList<CatalogoSat> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(ArrayList<CatalogoSat> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public ArrayList<CatalogoSat> getListaMonedas() {
        return listaMonedas;
    }

    public void setListaMonedas(ArrayList<CatalogoSat> listaMonedas) {
        this.listaMonedas = listaMonedas;
    }

    public String getPathFacturaClienteComprobante() {
        return pathFacturaClienteComprobante;
    }

    public void setPathFacturaClienteComprobante(String pathFacturaClienteComprobante) {
        this.pathFacturaClienteComprobante = pathFacturaClienteComprobante;
    }

    public String getPathFacturaClienteTimbrado() {
        return pathFacturaClienteTimbrado;
    }

    public void setPathFacturaClienteTimbrado(String pathFacturaClienteTimbrado) {
        this.pathFacturaClienteTimbrado = pathFacturaClienteTimbrado;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public ArrayList<VentaProductoMayoreo> getListaProductosReporte() {
        return listaProductosReporte;
    }

    public void setListaProductosReporte(ArrayList<VentaProductoMayoreo> listaProductosReporte) {
        this.listaProductosReporte = listaProductosReporte;
    }

    public FacturaPDFDomain getNuevaFactura() {
        return nuevaFactura;
    }

    public void setNuevaFactura(FacturaPDFDomain nuevaFactura) {
        this.nuevaFactura = nuevaFactura;
    }

    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getBanderanumCuenta() {
        return banderanumCuenta;
    }

    public void setBanderanumCuenta(String banderanumCuenta) {
        this.banderanumCuenta = banderanumCuenta;
    }

    public ArrayList<ProductoFacturado> getLstProductosFacturado() {
        return lstProductosFacturado;
    }

    public void setLstProductosFacturado(ArrayList<ProductoFacturado> lstProductosFacturado) {
        this.lstProductosFacturado = lstProductosFacturado;
    }

    public ArrayList<VentaProductoMayoreo> getListaVentaPublico() {
        return listaVentaPublico;
    }

    public void setListaVentaPublico(ArrayList<VentaProductoMayoreo> listaVentaPublico) {
        this.listaVentaPublico = listaVentaPublico;
    }

    public int getFiltroPublico() {
        return filtroPublico;
    }

    public void setFiltroPublico(int filtroPublico) {
        this.filtroPublico = filtroPublico;
    }

    public Date getFechaFiltroInicioPublico() {
        return fechaFiltroInicioPublico;
    }

    public void setFechaFiltroInicioPublico(Date fechaFiltroInicioPublico) {
        this.fechaFiltroInicioPublico = fechaFiltroInicioPublico;
    }

    public Date getFechaFiltroFinPublico() {
        return fechaFiltroFinPublico;
    }

    public void setFechaFiltroFinPublico(Date fechaFiltroFinPublico) {
        this.fechaFiltroFinPublico = fechaFiltroFinPublico;
    }

    public boolean isEnableCalendarPublico() {
        return enableCalendarPublico;
    }

    public void setEnableCalendarPublico(boolean enableCalendarPublico) {
        this.enableCalendarPublico = enableCalendarPublico;
    }

    public BigDecimal getImporteVentaPublico() {
        return importeVentaPublico;
    }

    public void setImporteVentaPublico(BigDecimal importeVentaPublico) {
        this.importeVentaPublico = importeVentaPublico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
