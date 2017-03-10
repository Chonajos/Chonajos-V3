/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import advanswsdl_pkg.AdvanswsdlLocator;
import advanswsdl_pkg.RespuestaTimbre2;
import com.web.chon.dominio.CatalogoSat;
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
import com.web.chon.service.IfaceCatalogoSat;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import mx.bigdata.sat.cfdi.examples.CFDv32;
import mx.bigdata.sat.cfdi.examples.CFDv32Factory;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante;
import mx.bigdata.sat.cfdi.v32.schema.ObjectFactory;
import mx.bigdata.sat.security.KeyLoaderEnumeration;
import mx.bigdata.sat.security.factory.KeyLoaderFactory;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.util.io.pem.PemWriter;
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
    @Autowired
    private IfaceCatalogoSat ifaceCatalogoSat;

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
    private BigDecimal importeParcialidad;
    private String banderaImporteParcialidad;
    private String metodoPago;
    private String tipoPago;
    private String formaPago;
    private BigDecimal tipoDocumento;
    private String moneda;

    private ArrayList<CatalogoSat> listaMetodosPago;
    private ArrayList<CatalogoSat> listaFormaPago;
    private ArrayList<CatalogoSat> listaTipoDocumento;
    private ArrayList<CatalogoSat> listaMonedas;

    private static final BigDecimal TIPO_METODO_PAGO = new BigDecimal(2);
    private static final BigDecimal TIPO_FORMA_PAGO = new BigDecimal(1);
    private static final BigDecimal TIPO_MONEDA = new BigDecimal(3);
    private static final BigDecimal TIPO_DOCUMENTO = new BigDecimal(4);
    private static final BigDecimal CERO = new BigDecimal(0);
    
    //nunca cambia, lo proporciono el pac
    private static String API_KEY = "1e550c937ba7cf3f65a6136ae86add2b";
    private String pathFacturaClienteComprobante;
    private String pathFacturaClienteTimbrado;

    ObjectFactory of;
    Comprobante comp;

    @PostConstruct
    public void init() {
        banderaImporteParcialidad = "false";
        disableBotonBuscarVenta = false;
        disableTextFolioVenta = false;
        filtroMostrador = 1;
        totalVenta = CERO;
        descuento = CERO;
        subTotal = CERO;
        iva = CERO;
        total = CERO;

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

    }
    public void calcularTotales()
    {
        for(CatalogoSat cs:listaTipoDocumento)
        {
           
            if(cs.getIdCatalogoSatPk().intValue()==tipoDocumento.intValue())
            {
                subTotal = (ventaMayoreo.getTotalVenta().setScale(2, RoundingMode.CEILING)).subtract(descuento.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                System.out.println("subTotal : "+subTotal);
                iva = (cs.getValor().setScale(2, RoundingMode.CEILING)).multiply(subTotal.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                System.out.println("Iva:  : "+iva);
                iva = iva.setScale(2, RoundingMode.CEILING).divide(new BigDecimal(100).setScale(2, RoundingMode.CEILING));
                System.out.println("Iva Dividido:  : "+iva);
                total =(iva.setScale(2, RoundingMode.CEILING)).add(subTotal.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                System.out.println("Total: "+total);
            }
                    
        }
    }
    public void agregarDescuento()
    {
        
    }

    public void agregarProducto() {

    }

    public void removeProductoSugerido() {

    }

    public void buscarDatosCliente() {
        if (filtroMostrador == 2) {
            datosCliente = ifaceFacturacion.getDatosFacturacionByIdCliente(idClienteVentaMostradorPk);

        } else {
            if (ventaMayoreo != null && ventaMayoreo.getIdClienteFk() != null) {
                datosCliente = ifaceFacturacion.getDatosFacturacionByIdCliente(ventaMayoreo.getIdClienteFk());

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

    public void habilitarImporteParcial() {
        System.out.println("forma de pago: "+formaPago);
        if (formaPago.equals("02") || formaPago.equals("03")) 
        {
            banderaImporteParcialidad = "true";
            
        } else {
            banderaImporteParcialidad = "false";
        }
    }

    public void backView() {
        setViewEstate("init");
        setTitle("Facturación Electrónica");

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
    
    public void timbrar() {
        try {
            RespuestaTimbre2 respuesta = new RespuestaTimbre2();
            AdvanswsdlLocator service = new AdvanswsdlLocator();

            URL url = new URL(pathFacturaClienteTimbrado);
            InputStreamReader in = new InputStreamReader(url.openStream());

            String xmlCfdi = IOUtils.toString(in);

            advanswsdl_pkg.AdvanswsdlPortType port = service.getadvanswsdlPort();
            respuesta = port.timbrar2(API_KEY, xmlCfdi);

            System.out.println("url " + url.openStream());
            System.out.println("codes:  " + respuesta.getCode());
            System.out.println("mensajes: " + respuesta.getMessage());
            System.out.println("subcodes:  " + respuesta.getSubCode());
            System.out.println("CFDI:  " + respuesta.getCFDI());
            System.out.println("timbres:  " + respuesta.getCFDI());

        } catch (MalformedURLException ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BeanRelOperMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirDatosFactura() {
        System.out.println("Certificado: " + comp.getCertificado());
        System.out.println("Condiciones de Pago: " + comp.getCondicionesDePago());
        System.out.println("Folio: " + comp.getFolio());
        System.out.println("Folio Fiscal Origen: " + comp.getFolioFiscalOrig());
        System.out.println("Forma de Pago: " + comp.getFormaDePago());
        System.out.println("Lugar de Expedicion: " + comp.getLugarExpedicion());
        System.out.println("Metodo de Pago: " + comp.getMetodoDePago());
        System.out.println("Moneda: " + comp.getMoneda());
        System.out.println("Motivo Descuento: " + comp.getMotivoDescuento());
        System.out.println("No Certificado: " + comp.getNoCertificado());
        System.out.println("Num Cta Pago: " + comp.getNumCtaPago());
        System.out.println("Sello: " + comp.getSello());
        System.out.println("Serie: " + comp.getSerie());
        System.out.println("Folio Fiscal Origen: " + comp.getSerieFolioFiscalOrig());
        System.out.println("Tipo Cambio: " + comp.getTipoCambio());
        System.out.println("Tipo Comprobante: " + comp.getTipoDeComprobante());
        System.out.println("Version: " + comp.getVersion());
        System.out.println("Descuento: " + comp.getDescuento());
        System.out.println("Fecha: " + comp.getFecha());
        System.out.println("Certificado: " + comp.getCertificado());
        System.out.println("---------------------DATOS DE EMISOR------------------------------");
        System.out.println("Nombre Emisor: "+comp.getEmisor().getNombre());
        System.out.println("RFC Emisor: "+comp.getEmisor().getRfc());
        System.out.println("Domicilo Fiscal Emisor:");
        System.out.println("    Calle: "+comp.getEmisor().getDomicilioFiscal().getCalle());
        System.out.println("    Codigo Postal: "+comp.getEmisor().getDomicilioFiscal().getCodigoPostal());
        System.out.println("    Colonia: "+comp.getEmisor().getDomicilioFiscal().getColonia());
        System.out.println("    Estado: "+comp.getEmisor().getDomicilioFiscal().getEstado());
        System.out.println("    Municipio: "+comp.getEmisor().getDomicilioFiscal().getMunicipio());
        System.out.println("    Localidad: "+comp.getEmisor().getDomicilioFiscal().getLocalidad());
        System.out.println("    No. Exterior: "+comp.getEmisor().getDomicilioFiscal().getNoExterior());
        System.out.println("    No. Interior "+comp.getEmisor().getDomicilioFiscal().getNoInterior());
        System.out.println("    Pais: "+comp.getEmisor().getDomicilioFiscal().getPais());
        System.out.println("    Referencia: "+comp.getEmisor().getDomicilioFiscal().getReferencia());
        
        System.out.println("Expedido en: "+comp.getEmisor().getExpedidoEn());
        
        System.out.println("---------------------DATOS CLIENTE------------------------------");
        System.out.println("Nombre Receptor: "+comp.getReceptor().getNombre());
        System.out.println("RFC Receptor: "+comp.getReceptor().getRfc());
        System.out.println("Domicilo Fiscal Receptor:");
        System.out.println("    Calle: "+comp.getReceptor().getDomicilio().getCalle());
        System.out.println("    Codigo Postal: "+comp.getReceptor().getDomicilio().getCodigoPostal());
        System.out.println("    Colonia: "+comp.getReceptor().getDomicilio().getColonia());
        System.out.println("    Estado: "+comp.getReceptor().getDomicilio().getEstado());
        System.out.println("    Municipio: "+comp.getReceptor().getDomicilio().getMunicipio());
        System.out.println("    Localidad: "+comp.getReceptor().getDomicilio().getLocalidad());
        System.out.println("    No. Exterior: "+comp.getReceptor().getDomicilio().getNoExterior());
        System.out.println("    No. Interior "+comp.getReceptor().getDomicilio().getNoInterior());
        System.out.println("    Pais: "+comp.getReceptor().getDomicilio().getPais());
        System.out.println("    Referencia: "+comp.getReceptor().getDomicilio().getReferencia());
    }
    public void crearFactura() throws Exception
    {
        for(DatosFacturacion df:listaDatosEmisor)
        {
            if(df.getIdDatosFacturacionPk().intValue()==idEmisorPk.intValue())
            {
                datosEmisor.setCalle(df.getCalle()==null ? "":df.getCalle());
                datosEmisor.setClavePublica(df.getClavePublica()==null ? "":df.getClavePublica());
                datosEmisor.setCodigoPostal(df.getCodigoPostal()==null ? "":df.getCodigoPostal());
                datosEmisor.setColonia(df.getColonia()==null ? "":df.getColonia());
                datosEmisor.setCorreo(df.getCorreo()==null ? "":df.getCorreo());
                datosEmisor.setEstado(df.getEstado()==null ? "":df.getEstado());
                datosEmisor.setField(df.getField()==null ? "":df.getField());
                datosEmisor.setIdClienteFk(df.getIdClienteFk()==null ? null:df.getIdClienteFk());
                datosEmisor.setIdCodigoPostalFk(df.getIdCodigoPostalFk()==null ? null:df.getIdCodigoPostalFk());
                datosEmisor.setLocalidad(df.getLocalidad()==null ? "":df.getLocalidad());
                datosEmisor.setMunicipio(df.getMunicipio()==null ? "":df.getMunicipio());
                datosEmisor.setNombre(df.getNombre()==null ? "":df.getNombre());
                datosEmisor.setNumExt(df.getNumExt()==null ? "":df.getNumExt());
                datosEmisor.setNumInt(df.getNumInt()==null ? "":df.getNumInt());
                datosEmisor.setPais(df.getPais()==null ? "":df.getPais());
                datosEmisor.setRazonSocial(df.getRazonSocial()==null ? "":df.getRazonSocial());
                datosEmisor.setRegimen(df.getRegimen()==null ? "":df.getRegimen());
                datosEmisor.setRfc(df.getRfc()==null ? "":df.getRfc());
                datosEmisor.setRuta_certificado(df.getRuta_certificado()==null ? "":df.getRuta_certificado());
                datosEmisor.setRuta_certificado_cancel(df.getRuta_certificado_cancel()==null ? "":df.getRuta_certificado_cancel());
                datosEmisor.setRuta_llave_privada(df.getRuta_llave_privada()==null ? "":df.getRuta_llave_privada());
                datosEmisor.setRuta_llave_privada_cancel(df.getRuta_llave_privada_cancel()==null ? "":df.getRuta_llave_privada_cancel());
                datosEmisor.setTelefono(df.getTelefono()==null ? "":df.getTelefono());
            }
        }
        pathFacturaClienteComprobante  = Constantes.PATHLOCALFACTURACION + File.separatorChar + "Clientes" + File.separatorChar + datosCliente.getRfc()+ File.separatorChar+"COMPROBANTE" + File.separatorChar + File.separatorChar + datosCliente.getRfc()+"_"+ventaMayoreo.getIdSucursalFk().toString()+"_"+ventaMayoreo.getVentaSucursal().toString()+".xml";
        
        pathFacturaClienteTimbrado  = Constantes.PATHLOCALFACTURACION + File.separatorChar + "Clientes" + File.separatorChar + datosCliente.getRfc()+ File.separatorChar+"TIMBRADO" + File.separatorChar + File.separatorChar + datosCliente.getRfc()+"_"+ventaMayoreo.getIdSucursalFk().toString()+"_"+ventaMayoreo.getVentaSucursal().toString()+".xml";
        
        CFDv32 cfd = new CFDv32(createComprobante(), "mx.bigdata.sat.cfdi.examples");
        //Cargamos el archivo con la llave
        
        PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(datosEmisor.getRuta_llave_privada()), datosEmisor.getClavePublica()).getKey();
        //Cargamos el archivo con el certificado
        
        System.out.println("Se cargaron exitosamente los archivos");
        X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(datosEmisor.getRuta_certificado())).getKey();
        
        mx.bigdata.sat.cfdi.v32.schema.Comprobante sellado = cfd.sellarComprobante(key, cert);
        
        FileOutputStream outFile = new FileOutputStream(pathFacturaClienteComprobante);
        imprimirDatosFactura();
        cfd.validar();
        System.out.println("Se valido el cdf ");
        cfd.verificar();
        System.out.println("Se verifico el cfd ");
        cfd.guardar(outFile);
        System.out.println("Se guardo el cdf ");
        System.out.println("cadema original " + cfd.getCadenaOriginal());
        
    }
    
    

    public  Comprobante createComprobante() 
    {
        //====INICIALIZACIÓN DE LOS DATOS DE COMPROBANTE DIGITAL===//
        comp.setVersion("3.2");
        comp.setSerie("A");
        comp.setFolio(ventaMayoreo.getVentaSucursal().toString());
        comp.setFecha(context.getFechaSistema());
        for(CatalogoSat cs:listaFormaPago)
        {
            
            if(cs.getCodigo().toString().equals(formaPago))
            {
                comp.setFormaDePago(cs.getDescripcion());
            }
        }
        
        comp.setSubTotal(subTotal);
        comp.setMetodoDePago(metodoPago);
        comp.setDescuento(descuento);
        comp.setTotal(total);
        for(CatalogoSat cs:listaTipoDocumento)
        {
            
            if(cs.getIdCatalogoSatPk().equals(tipoDocumento))
            {
                comp.setTipoDeComprobante(cs.getCodigo());
            }
        }
        
        
        comp.setLugarExpedicion(datosEmisor.getCodigoPostal());
        comp.setMoneda(moneda);
        comp.setNoCertificado("00001000000404327545");//es el numero del certificado
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
        uf.setColonia(datosCliente.getColonia());
        uf.setMunicipio(datosCliente.getMunicipio());
        uf.setEstado(datosCliente.getEstado());
        uf.setPais(datosCliente.getPais());
        uf.setCodigoPostal(datosCliente.getCodigoPostal());

        //NO SON OBLIGATORIOS NO SE OCUPAN POR AHORA
//    uf.setNoExterior("16 EDF 3"); 
//    uf.setNoInterior("DPTO 101"); 
        receptor.setDomicilio(uf);
        return receptor;
    }

    private Comprobante.Conceptos createConceptos(ObjectFactory of) {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        List<Comprobante.Conceptos.Concepto> list = cps.getConcepto();
        for (VentaProductoMayoreo producto : ventaMayoreo.getListaProductos()) {
            Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
            c1.setCantidad(producto.getKilosVendidos());
            c1.setUnidad("KILOS");
            c1.setDescripcion(producto.getNombreProducto());
            c1.setValorUnitario(producto.getPrecioProducto());
            c1.setImporte(producto.getTotalVenta());
            list.add(c1);
        }

        
        return cps;
    }

    private  Comprobante.Impuestos createImpuestos(ObjectFactory of) {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        t1.setImporte(iva);
        t1.setImpuesto("IVA");
        t1.setTasa(new BigDecimal("16.00"));
        list.add(t1);
//        Comprobante.Impuestos.Traslados.Traslado t2 = of.createComprobanteImpuestosTrasladosTraslado();
//        t2.setImporte(new BigDecimal("22.07"));
//        t2.setImpuesto("IVA");
//        t2.setTasa(new BigDecimal("16.00"));
//        list.add(t2);
        imps.setTraslados(trs);
        return imps;
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

    
    

}
