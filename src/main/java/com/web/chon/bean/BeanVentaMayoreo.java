/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;

import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.service.IfaceVentaMayoreoProducto;
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
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanVentaMayoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVenta ifaceVenta;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceTipoVenta ifaceTipoVenta;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceVentaMayoreoProducto ifaceVentaMayoreoProducto;
    @Autowired
    private IfaceCredito ifaceCredito;

    private ArrayList<TipoVenta> lstTipoVenta;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> lstExistencias;
    private ArrayList<VentaProductoMayoreo> lstVenta;
    private ExistenciaProducto selectedExistencia;

    private UsuarioDominio usuarioDominio;

    private Cliente cliente;
    private Usuario usuario;
    private Credito c;
    private Subproducto subProducto;
    private VentaProductoMayoreo data;
    private EntradaMercancia entradaMercancia;
    private ExistenciaProducto ep;
    private VentaMayoreo ventaGeneral;
    private VentaProductoMayoreo dataRemove;
    private VentaProductoMayoreo dataEdit;

    private BigDecimal totalVenta;
    private BigDecimal idSucu;
    private BigDecimal idExistencia;
    private BigDecimal idTipoVenta;
    private BigDecimal totalProductoTemporal;

    private String title = "";
    private String viewEstate = "";
    private String ventaRapidaButton = "";

    private BigDecimal totalVentaGeneral;

    //Variables para Generar el pdf
    private String rutaPDF;
    private Map paramReport = new HashMap();
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;

    private boolean permisionToWrite;
    private String ventaRapida;

    private boolean permisionVentaRapida;

    private BigDecimal recibido;
    private BigDecimal cambio;

    private boolean pruaba;
    private boolean charLine = true;

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal descuento;
    private BigDecimal dejaACuenta;
    ;
    private BigDecimal totalVentaDescuento;
    private BigDecimal INTERES_VENTA = new BigDecimal("0.60");
    private BigDecimal DIAS_PLAZO = new BigDecimal("7");

    private int folioCredito = 0;

    private boolean permisionToEdit;
    private boolean variableInicial;
    private boolean credito;

    private Date date;

    @PostConstruct
    public void init() {

//        pruaba = false;
        ventaRapida = "0";
        credito = false;
        date = context.getFechaSistema();
        cliente = new Cliente();
        cliente = ifaceCatCliente.getClienteById(1);
        ventaGeneral = new VentaMayoreo();
        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        idSucu = new BigDecimal(usuarioDominio.getSucId());
        
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(idSucu.intValue());
        usuario.setIdRolFk(new BigDecimal(usuarioDominio.getPerId()));
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        
        totalVentaGeneral = new BigDecimal(0);
        data = new VentaProductoMayoreo();
        setTitle("Venta Mayoreo");
        setViewEstate("viewAddProducto");
        lstTipoVenta = ifaceTipoVenta.getAll();
        lstVenta = new ArrayList<VentaProductoMayoreo>();
        permisionToWrite = true;
        totalProductoTemporal = null;

        data.setIdTipoVenta(new BigDecimal("1"));
        data.setTipoPago(new BigDecimal(7));
        max = new BigDecimal(0);
        min = new BigDecimal(0);
        descuento = new BigDecimal(0);
        dejaACuenta = new BigDecimal(0);
        totalVentaDescuento = new BigDecimal(0);
        permisionToEdit = false;

        ventaRapida = (usuario.getIdRolFk()).toString();

        if (ventaRapida.equals("4")) {

            lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, null, null, null, null);
            ventaRapidaButton = "Rapida";
            permisionVentaRapida = false;

        } else {
            ventaRapida = "2";
            permisionVentaRapida = true;
            lstExistencias = new ArrayList<ExistenciaProducto>();
        }

    }

    public void calculaCambio() {
        cambio = recibido.subtract(totalVentaGeneral, MathContext.UNLIMITED);
    }

    public void habilitarBotones() {
        permisionToWrite = false;
        data.setPrecioProducto(selectedExistencia.getPrecioVenta());
    }

    public void calculaTotalTemporal() {

        totalProductoTemporal = data.getKilosVendidos().multiply(data.getPrecioProducto(), MathContext.UNLIMITED);
    }

    public void cancelarPedido() {

        selectedExistencia = null;
        lstExistencias = new ArrayList<ExistenciaProducto>();
        lstVenta = new ArrayList<VentaProductoMayoreo>();
        data.reset();
        subProducto = new Subproducto();
        setViewEstate("viewAddProducto");
        idTipoVenta = null;
        permisionToWrite = true;
        cliente = ifaceCatCliente.getClienteById(1);
        totalProductoTemporal = null;
        totalVentaGeneral = new BigDecimal(0);
        JsfUtil.addWarnMessage("Venta Cancelada");
        init();

    }

    public String inserts() {
        int idVenta = 0;

        String mensaje = validaCompraCredito();
        try {
            if (!mensaje.equals("")) {
                JsfUtil.addErrorMessage(mensaje);
                return null;
            }
            if (lstVenta.isEmpty()) {

                JsfUtil.addErrorMessageClean("Tu pedido se encuentra vacío favor de agregar productos");
            } else if (usuario.getIdUsuarioPk() == null) {
                changeView();
                JsfUtil.addErrorMessageClean("Favor de Seleccionar un vendedor");

            } else if (cliente == null) {
                changeView();
                JsfUtil.addErrorMessageClean("Favor de Seleccionar un cliente");
            } else if (data.getIdTipoVenta() == null) {
                changeView();
                JsfUtil.addErrorMessageClean("Favor de Seleccionar un tipo de venta");
            } else {
                BigDecimal idVentaInsert = new BigDecimal(ifaceVentaMayoreo.getNextVal());
                ventaGeneral.setIdVentaMayoreoPk(idVentaInsert);
                ventaGeneral.setIdtipoVentaFk(data.getIdTipoVenta());
                ventaGeneral.setIdClienteFk(cliente.getId_cliente());
                ventaGeneral.setIdSucursalFk(idSucu);
                ventaGeneral.setIdVendedorFK(usuario.getIdUsuarioPk());
                int folioMayoreo = 0;
                int folioMenudeo =0;
                folioMayoreo = ifaceVentaMayoreo.getVentaSucursal(idSucu);
                folioMenudeo = ifaceVenta.getFolioByIdSucursal(idSucu.intValue());
             
                
                if(folioMenudeo>folioMayoreo)
                {
                    folioMayoreo = folioMenudeo;
                }
               

                ventaGeneral.setVentaSucursal(new BigDecimal(folioMayoreo + 1));
                ventaGeneral.setIdStatusFk(new BigDecimal(1));

                //System.out.println("Venta General: " + ventaGeneral.toString());
                if (ifaceVentaMayoreo.insertarVenta(ventaGeneral) != 0) {
                    //si la venta se ingreso el siguiente paso es ingresar los productos.
                    for (VentaProductoMayoreo producto : lstVenta) {
                        producto.setIdVentaMayoreoFk(idVentaInsert);
                        BigDecimal idVentaProducto = new BigDecimal(ifaceVentaMayoreoProducto.getNextVal());
                        //System.out.println("IdVentaProducto: " + idVentaProducto);
                        producto.setIdVentaMayProdPk(idVentaProducto);
                        //System.out.println("Producto: " + producto.toString());
                        if (ifaceVentaMayoreoProducto.insertarVentaMayoreoProducto(producto) != 0) {

                            List<ExistenciaProducto> lista = ifaceNegocioExistencia.getExistenciaById(producto.getIdExistenciaFk());
                            ExistenciaProducto existencia = new ExistenciaProducto();
                            existencia = lista.get(0);
                            ExistenciaProducto existencia_actualizada = new ExistenciaProducto();
                            existencia_actualizada.setIdExistenciaProductoPk(existencia.getIdExistenciaProductoPk());
                            existencia_actualizada.setCantidadPaquetes(existencia.getCantidadPaquetes().subtract(producto.getCantidadEmpaque(), MathContext.UNLIMITED));
                            existencia_actualizada.setKilosTotalesProducto(existencia.getKilosTotalesProducto().subtract(producto.getKilosVendidos(), MathContext.UNLIMITED));
                            existencia_actualizada.setIdBodegaFK(existencia.getIdBodegaFK());
                            if (ifaceNegocioExistencia.updateCantidadKilo(existencia_actualizada) != 0) 
                            {

                                if (!data.getIdTipoVenta().equals(new BigDecimal("1"))) {
                                    if (insertaCredito(ventaGeneral)) {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "La venta se realizo correctamente."));
                                    } else {
                                        JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");
                                    }
                                }
//                                JsfUtil.addSuccessMessageClean("Venta de Productos finalizada");
                            } else {
                                JsfUtil.addErrorMessageClean("Error actualizando existencia de producto: " + producto.getNombreProducto());
                            }
                        } else {
                            JsfUtil.addErrorMessage("Ocurrio un error");
                        }

                    }
                    setParameterTicket(ventaGeneral.getVentaSucursal().intValue(), ventaGeneral.getVentaSucursal().intValue());
                    generateReport(ventaGeneral.getVentaSucursal().intValue());
                    selectedExistencia = new ExistenciaProducto();
                    lstExistencias = new ArrayList<ExistenciaProducto>();
                    lstVenta = new ArrayList<VentaProductoMayoreo>();
                    data.reset();
                    subProducto = new Subproducto();
                    setViewEstate("viewAddProducto");
                    idTipoVenta = null;
                    totalProductoTemporal = null;
                    totalVentaGeneral = new BigDecimal(0);
                    init();

                }
                RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");

            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));
            return null;

        }

    }

    public void changeViewToAddProducto() {
        setViewEstate("viewAddProducto");
    }

    public void changeView() {
        setViewEstate("viewCarrito");
    }

    public void buscaExistencias() {
        BigDecimal idEntrada;
        lstExistencias = new ArrayList<ExistenciaProducto>();

        if (entradaMercancia == null) {
            idEntrada = null;
        } else {
            idEntrada = entradaMercancia.getIdEmPK();
        }

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        if (idproductito != null) {
            lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, idproductito, null, null, null);
            if (lstExistencias.size() == 1) {

                selectedExistencia = new ExistenciaProducto();
                selectedExistencia = lstExistencias.get(0);

                habilitarBotones();
            } else {
                selectedExistencia = new ExistenciaProducto();
            }
            if (lstExistencias.isEmpty()) {
                JsfUtil.addWarnMessage("No se encontraron existencias de este producto");
            }
        } else {

            data.setCantidadEmpaque(null);
            data.setPrecioProducto(null);
            data.setKilosVendidos(null);
            selectedExistencia = new ExistenciaProducto();
            permisionToWrite = true;
            if (ventaRapida.equals("4")) {

                lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, null, null, null, null);
            } else {
                lstExistencias.clear();
            }

        }

        setPrecioInteres();

    }

    public void addProductoEnd() {
        addProducto();
        inserts();
    }

    public void addProducto() {
        if (selectedExistencia == null || data.getKilosVendidos().compareTo(BigDecimal.ZERO) == 0) {
            JsfUtil.addErrorMessage("Seleccione un Producto de la tabla o peso en 0 Kg.");

        } else //            System.out.println("idSubProducto:" + selectedExistencia.getIdSubProductoFK());
         if (selectedExistencia.getPrecioVenta() == null) {
                JsfUtil.addErrorMessage("No se tiene precio de venta para este producto. Contactar al administrador.");
            } else if (data.getPrecioProducto().intValue() < selectedExistencia.getPrecioMinimo().intValue() || data.getPrecioProducto().intValue() > selectedExistencia.getPrecioMaximo().intValue()) {
                JsfUtil.addErrorMessage("Precio de Venta fuera de Rango \n Precio Maximo =" + selectedExistencia.getPrecioMaximo() + " Precio minimo =" + selectedExistencia.getPrecioMinimo());
            } else if (data.getCantidadEmpaque().intValue() > selectedExistencia.getCantidadPaquetes().intValue()) {
                JsfUtil.addErrorMessage("Cantidad de Empaque insuficiente");
            } else if (data.getKilosVendidos().intValue() > selectedExistencia.getKilosTotalesProducto().intValue()) {
                JsfUtil.addErrorMessage("Cantidad de Kilos insuficiente");
            } else if (lstVenta.isEmpty()) {

                add();
                limpia();
            } else {
                for (int i = 0; i < lstVenta.size(); i++) {
                    VentaProductoMayoreo productoRepetido = lstVenta.get(i);
                    if (productoRepetido.getIdExistenciaFk().equals(selectedExistencia.getIdExistenciaProductoPk()) && productoRepetido.getPrecioProducto().equals(data.getPrecioProducto())) {
                        BigDecimal enlista = productoRepetido.getCantidadEmpaque();
                        BigDecimal totalexistencia = selectedExistencia.getCantidadPaquetes();
                        BigDecimal suma = enlista.add(data.getCantidadEmpaque(), MathContext.UNLIMITED);
                        BigDecimal kilosnelista = productoRepetido.getKilosVendidos();
                        BigDecimal kilostotalexistencia = selectedExistencia.getKilosTotalesProducto();
                        BigDecimal kilossuma = kilosnelista.add(data.getKilosVendidos(), MathContext.UNLIMITED);

                        if (suma.intValue() > totalexistencia.intValue() || kilossuma.intValue() > kilostotalexistencia.intValue()) {

                            JsfUtil.addErrorMessage("Producto repetido, no alcanzan las existencias.\n Cantidad: " + selectedExistencia.getCantidadPaquetes() + "\nKilos: " + selectedExistencia.getKilosTotalesProducto());
                        } else {
                            lstVenta.remove(i);
                            totalVentaGeneral = totalVentaGeneral.subtract(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);

                            productoRepetido.setCantidadEmpaque(suma);
                            productoRepetido.setKilosVendidos(kilossuma);
                            productoRepetido.setTotalVenta(productoRepetido.getPrecioProducto().multiply(productoRepetido.getKilosVendidos(), MathContext.UNLIMITED));
                            productoRepetido.setPrecioSinInteres(data.getPrecioSinInteres());

                            totalVentaGeneral = totalVentaGeneral.add(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);
                            lstVenta.add(productoRepetido);
                            limpia();
                            //System.out.println("Existencias suficientes, producto actualizado");
                            JsfUtil.addSuccessMessageClean("Producto Actualizado");
                        }
                    } else {
                        System.out.println("No encontro repetidos");
                        if (selectedExistencia.getIdExistenciaProductoPk() != null) {

                            add();
                            limpia();
                        }

///
                    }

                }
            }
        calculaAhorro(null);

    }

    public void add() {
        data.setIdExistenciaFk(selectedExistencia.getIdExistenciaProductoPk());
        data.setIdEntradaMercanciaFk(selectedExistencia.getIdEmFK());
        data.setIdTipoEmpaqueFk(selectedExistencia.getIdTipoEmpaqueFK());
        data.setIdSubProductofk(selectedExistencia.getIdSubProductoFK());
        data.setNombreEmpaque(selectedExistencia.getNombreEmpaque());
        data.setNombreProducto(selectedExistencia.getNombreProducto());
        data.setClave(selectedExistencia.getIdentificador());
        data.setPrecioSinInteres(selectedExistencia.getPrecioSinIteres());
        data.setTotalVenta(data.getPrecioProducto().multiply(data.getKilosVendidos(), MathContext.UNLIMITED));
        data.setFolioCarro(selectedExistencia.getCarroSucursal());
        VentaProductoMayoreo productoTemporal = new VentaProductoMayoreo();

        productoTemporal.setIdEntradaMercanciaFk(data.getIdEntradaMercanciaFk());
        productoTemporal.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        productoTemporal.setIdSubProductofk(data.getIdSubProductofk());
        productoTemporal.setNombreEmpaque(data.getNombreEmpaque());
        productoTemporal.setNombreProducto(data.getNombreProducto());
        productoTemporal.setCantidadEmpaque(data.getCantidadEmpaque());
        productoTemporal.setPrecioProducto(data.getPrecioProducto());
        productoTemporal.setPrecioSinInteres(data.getPrecioSinInteres());
        productoTemporal.setClave(data.getClave());
        productoTemporal.setTotalVenta(data.getTotalVenta());
        productoTemporal.setKilosVendidos(data.getKilosVendidos());
        productoTemporal.setIdExistenciaFk(data.getIdExistenciaFk());
        productoTemporal.setFolioCarro(data.getFolioCarro());

        totalVentaGeneral = totalVentaGeneral.add(productoTemporal.getTotalVenta(), MathContext.UNLIMITED);
        lstVenta.add(productoTemporal);

    }

    public void limpia() {
        selectedExistencia = new ExistenciaProducto();
        lstExistencias = new ArrayList<ExistenciaProducto>();
        data.reset();

        JsfUtil.addSuccessMessageClean("Producto Agregado al Pedido Correctamente");
        subProducto = new Subproducto();
        setViewEstate("viewAddProducto");
        totalProductoTemporal = null;

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

            if (data.getIdTipoVenta().equals(new BigDecimal(1))) {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            } else {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticketCredito.jasper";
            }

//            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", folio, usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }

    }

    private void setParameterTicket(int idVenta, int folioVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");

        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();

        for (VentaProductoMayoreo venta : lstVenta) {
            String cantidad = venta.getCantidadEmpaque() + " - "+venta.getKilosVendidos()+"Kg.";
            productos.add(venta.getNombreProducto().toUpperCase()+" "+ venta.getNombreEmpaque());
            productos.add("  " + cantidad + "                     " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotalVenta()));
        }

        String totalVentaDescuentoStr = "";
        String totalVentaStr = numeroLetra.Convertir(df.format(totalVentaGeneral), true);
        if (data.getIdTipoVenta().equals(new BigDecimal(2))) {
            totalVentaDescuentoStr = numeroLetra.Convertir(df.format(c.getMontoCredito()), true);
        }

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(context.getFechaSistema()), productos, nf.format(totalVentaGeneral), totalVentaStr, idVenta, folioVenta, totalVentaDescuentoStr, nf.format(totalVentaDescuento));

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta, int folioVenta, String totalVentaDescuentoStr, String totalDescuento) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        paramReport.clear();
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(idVenta));
        paramReport.put("cliente", cliente.getNombreCombleto());
        paramReport.put("vendedor", usuario.getNombreCompletoUsuario());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("estado", "PEDIDO MARCADO");
        paramReport.put("labelFecha", "Fecha de Venta:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());

        //Se agregan los campos que se utiliza en el ticket de credito
        if (!data.getIdTipoVenta().equals(new BigDecimal(1))) {

            paramReport.put("numeroCliente", cliente.getId_cliente().toString());
            paramReport.put("fechaPromesaPago", TiempoUtil.getFechaDDMMYYYY(c.getFechaPromesaPago()));
            paramReport.put("beneficiario", "FIDENCIO TORRES REYNOSO");
            paramReport.put("totalCompraDescuento", "$" + df.format(c.getMontoCredito()));
            paramReport.put("totalDescuentoLetra", totalVentaDescuentoStr);
            paramReport.put("aCuenta", "-$" + df.format(dejaACuenta));
            paramReport.put("descuentoVenta", "-$" + df.format(descuento));
            paramReport.put("foliCredito", Integer.toString(folioCredito));

            //Imprime el calendario de pagos
            Date dateTemp = date;
            ArrayList calendario = new ArrayList();
            String montoAbono = df.format(c.getMontoCredito().divide(c.getNumeroPagos() == null ? BigDecimal.ZERO : c.getNumeroPagos(), 2, RoundingMode.UP));
            String item = "N. Pago   Fecha de Pago   Monto";
            calendario.add(item);
            if (dejaACuenta.intValue() > 0) {
                item = "    0            " + TiempoUtil.getFechaDDMMYYYY(date) + "    $" + df.format(dejaACuenta);
                calendario.add(item);

                paramReport.put("msgAcuenta", "Favor de pasar a caja para su pago inicial de: $" + df.format(dejaACuenta));
            } else {
                paramReport.put("msgAcuenta", "");
            }

            int plaso = (c.getPlasos().divide(c.getNumeroPagos())).intValue();
            int pagos = c.getNumeroPagos().intValue();
            for (int y = 0; y < pagos; y++) {

                dateTemp = TiempoUtil.sumarRestarDias(dateTemp, plaso);
                item = "    " + (y + 1) + "            " + TiempoUtil.getFechaDDMMYYYY(dateTemp) + "    $" + montoAbono;
                calendario.add(item);

            }
            paramReport.put("calendarioPago", calendario);
        }
    }

    public ArrayList<TipoVenta> getLstTipoVenta() {
        return lstTipoVenta;
    }

    public void setLstTipoVenta(ArrayList<TipoVenta> lstTipoVenta) {
        this.lstTipoVenta = lstTipoVenta;
    }

    public void editProducto() {
        selectedExistencia = new ExistenciaProducto();
        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubProductofk());
        data.setCantidadEmpaque(dataEdit.getCantidadEmpaque());
        data.setKilosVendidos(dataEdit.getKilosVendidos());
        data.setPrecioProducto(dataEdit.getPrecioProducto());
        data.setTotalVenta(data.getKilosVendidos().multiply(data.getPrecioProducto(), MathContext.UNLIMITED));
        selectedExistencia.setIdExistenciaProductoPk(dataEdit.getIdExistenciaFk());
        String idproductitoedit = subProducto == null ? null : subProducto.getIdSubproductoPk();
        lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, idproductitoedit, null, null, null);

        setViewEstate("update");
    }

    public void updateProducto() {
        
        dataEdit.setCantidadEmpaque(data.getCantidadEmpaque());
        dataEdit.setKilosVendidos(data.getKilosVendidos());
        dataEdit.setTotalVenta(data.getKilosVendidos().multiply(data.getPrecioProducto(), MathContext.UNLIMITED));
        dataEdit.setPrecioProducto(data.getPrecioProducto());
        dataEdit.setIdExistenciaFk(selectedExistencia.getIdExistenciaProductoPk());
        setViewEstate("viewCarrito");
        JsfUtil.addSuccessMessage("Producto Modificado Correctamente");
        totalVentaGeneral = new BigDecimal(0);
        for (VentaProductoMayoreo producto : lstVenta) {
            totalVentaGeneral = totalVentaGeneral.add(producto.getTotalVenta(), MathContext.UNLIMITED);

        }
        selectedExistencia = null;
        lstExistencias = new ArrayList<ExistenciaProducto>();
        data.reset();
        subProducto = new Subproducto();
        setViewEstate("viewAddProducto");

        totalProductoTemporal = null;

    }

    public void remove() {
        totalVentaGeneral = totalVentaGeneral.subtract(dataRemove.getTotalVenta(), MathContext.UNLIMITED);
        lstVenta.remove(dataRemove);

    }

    public void validaCreditoCliente() {

        BigDecimal tipoPago = new BigDecimal("1");
        BigDecimal idClienteVenta = new BigDecimal("1");
        if (cliente.getId_cliente().equals(idClienteVenta)) {
            credito = false;
            data.setIdTipoVenta(tipoPago);
        } else if (cliente.getLimiteCredito() == null) {
            JsfUtil.addWarnMessage("El Cliente no tiene Crédito.");
            data.setIdTipoVenta(tipoPago);
            credito = false;
        } else if (cliente.getCreditoDisponible().compareTo(new BigDecimal("0")) == 1) {
            credito = true;
        } else {
            JsfUtil.addWarnMessage("El cliente no tiene credito disponible :" + cliente.getCreditoDisponible());
            data.setIdTipoVenta(tipoPago);
            credito = false;
        }
        calculaPrecioProducto();

    }

    //Calcula los precios de cada producto cuando es una venta de credito
    public void calculaPrecioProducto() {

        if (data.getIdTipoVenta().intValue() != 1) {

            BigDecimal diasAnio = new BigDecimal(365);

            BigDecimal totalDiasPagar = data.getTipoPago() == null ? DIAS_PLAZO : data.getTipoPago();
            data.setNumeroPagos(totalDiasPagar.divide(DIAS_PLAZO));
        }

        for (VentaProductoMayoreo dominio : lstVenta) {

            dominio.setPrecioProducto(precioProducto(dominio.getPrecioSinInteres()));
            dominio.setTotalVenta(dominio.getPrecioProducto().multiply(dominio.getKilosVendidos()));
        }
        for (ExistenciaProducto dominio : lstExistencias) {

            dominio.setPrecioVenta(precioProducto(dominio.getPrecioSinIteres()));

        }

        calcularTotalVenta();
    }

    private void calcularTotalVenta() {
        setTotalVentaGeneral(new BigDecimal(0));
        totalVentaDescuento = new BigDecimal(0);
        for (VentaProductoMayoreo dominio : lstVenta) {
            setTotalVentaGeneral(getTotalVentaGeneral().add(dominio.getTotalVenta()));
        }
        totalVentaDescuento = getTotalVentaGeneral();
        totalVentaDescuento = totalVentaDescuento.setScale(2, RoundingMode.UP);

        calculaAhorro(null);
    }

    public void calculaAhorro(AjaxBehaviorEvent event) {
        InputText input;

        if (event != null) {
            input = (InputText) event.getSource();
            dejaACuenta = new BigDecimal(input.getSubmittedValue().toString());
        }

        BigDecimal zero = new BigDecimal(0);
        descuento = new BigDecimal(0);
        //Calcula el descuento por dejar a cuenta
        if (dejaACuenta.compareTo(zero) == 1) {
            BigDecimal tempDejaAcuenta = dejaACuenta;

            for (VentaProductoMayoreo dominio : lstVenta) {

                //Si lo que se dejo a cuenta es igual o menor a 0 se sale del for
                if (tempDejaAcuenta.compareTo(zero) <= 0) {
                    break;
                }

                BigDecimal kiloCompra = new BigDecimal(0);
                BigDecimal totalCompraSinInteres = dominio.getKilosVendidos().multiply(dominio.getPrecioSinInteres());
                int compareDejaAcuenta = totalCompraSinInteres.compareTo(tempDejaAcuenta);

                if (compareDejaAcuenta == -1) {

                    kiloCompra = dominio.getKilosVendidos();
                    descuento = descuento.add(kiloCompra.multiply(dominio.getPrecioProducto()).subtract(kiloCompra.multiply(dominio.getPrecioSinInteres())));
                    tempDejaAcuenta = tempDejaAcuenta.subtract(kiloCompra.multiply(dominio.getPrecioSinInteres()));

                } else if (compareDejaAcuenta == 1) {

                    kiloCompra = tempDejaAcuenta.divide(dominio.getPrecioSinInteres(), 2, RoundingMode.UP);
                    tempDejaAcuenta = zero;
                    descuento = descuento.add(kiloCompra.multiply(dominio.getPrecioProducto()).subtract(kiloCompra.multiply(dominio.getPrecioSinInteres())));

                } else {

                    tempDejaAcuenta = zero;
                    kiloCompra = dominio.getKilosVendidos();
                    descuento = descuento.add(kiloCompra.multiply(dominio.getPrecioProducto()).subtract(kiloCompra.multiply(dominio.getPrecioSinInteres())));
                }

            }

            descuento = descuento.setScale(2, RoundingMode.UP);
            setTotalVentaDescuento(totalVentaGeneral.subtract(descuento));

        }
    }

    public void validaPrecioMinimoMaximo(AjaxBehaviorEvent event) {
        InputText input = (InputText) event.getSource();
        if (input.getSubmittedValue() != null && !input.getSubmittedValue().toString().isEmpty()) {
            BigDecimal value = new BigDecimal(input.getSubmittedValue().toString());
            if (value.compareTo(min) == -1) {
                input.setSubmittedValue(min);

            } else if (value.compareTo(max) == 1) {
                input.setSubmittedValue(max);
            } else {
                input.setSubmittedValue(value);
            }

        } else {
            input.setSubmittedValue("");

        }

        if (data.getKilosVendidos() != null && data.getPrecioProducto() != null) {
            calculaTotalTemporal();
        }

    }

    public void validarNumeroPagos(AjaxBehaviorEvent event) {
        InputText input = (InputText) event.getSource();
        try {

            if (input.getSubmittedValue() != null && !input.getSubmittedValue().toString().isEmpty()) {

                BigDecimal value = new BigDecimal(input.getSubmittedValue().toString());
                int tipoPago = data.getTipoPago() == null ? 7 : data.getTipoPago().intValue();
                int resto = (tipoPago % value.intValue());

                if (resto > 0) {
                    input.setSubmittedValue(new BigDecimal(tipoPago).divide(DIAS_PLAZO));
                } else if (tipoPago < (value.intValue() * DIAS_PLAZO.intValue())) {
                    input.setSubmittedValue(new BigDecimal(tipoPago).divide(DIAS_PLAZO));
                } else if (tipoPago == value.intValue()) {
                    input.setSubmittedValue(new BigDecimal(tipoPago).divide(DIAS_PLAZO));
                } else {
                    input.setSubmittedValue(value);
                }
            } else {
                input.setSubmittedValue("");

            }

        } catch (Exception ex) {
            input.setSubmittedValue("");
        }

    }

    private void setPrecioInteres() {

        for (ExistenciaProducto dominio : lstExistencias) {

            dominio.setPrecioVenta(precioProducto(dominio.getPrecioVenta()));

        }

    }

    private BigDecimal precioProducto(BigDecimal precioProducto) {

        BigDecimal diasAnio = new BigDecimal(365);

        if ((data.getIdTipoVenta() != null && data.getIdTipoVenta().intValue() != 1) && precioProducto != null) {
            BigDecimal totalConInteres = new BigDecimal("0");
            BigDecimal totalDiasPagar = data.getTipoPago() == null ? new BigDecimal("7") : data.getTipoPago();
            BigDecimal interesDiasPagar = INTERES_VENTA.divide(diasAnio, 8, RoundingMode.HALF_UP).multiply(totalDiasPagar);
            totalConInteres = precioProducto.multiply(interesDiasPagar.add(new BigDecimal("1")));
            totalConInteres = totalConInteres.setScale(0, RoundingMode.HALF_UP);
            return totalConInteres;
        } else {
            return precioProducto;
        }

    }

    private String validaCompraCredito() {

        String mensaje = "";
        BigDecimal tipoCredito = new BigDecimal("2");

        if (data.getIdTipoVenta().equals(tipoCredito)) {

            BigDecimal creditoVenta = totalVentaGeneral.subtract(dejaACuenta);

            if (totalVentaGeneral.compareTo(dejaACuenta) == -1) {
                return "El monto a cuenta :" + dejaACuenta + " es mayor al total de la venta: " + totalVenta;
            } else if (cliente.getCreditoDisponible().compareTo(creditoVenta) == -1) {

                return "No tiene el credito necesario para realizar la compra. Credito disponible: $" + cliente.getCreditoDisponible() + ", Compra de Credito :$" + creditoVenta + ", a cuenta :$" + dejaACuenta;
            }
        }

        return mensaje;

    }

    private boolean insertaCredito(VentaMayoreo venta) {
        c = new Credito();
        Date fechaActual = date;
        Date fechaPromesaPago = TiempoUtil.sumarRestarDias(fechaActual, data.getTipoPago().intValue());

        c.setIdClienteFk(venta.getIdClienteFk());
        //folio de la venta de menudeo (es el folio general no el de sucursal)
        c.setIdVentaMayoreo(venta.getIdVentaMayoreoPk());
        //Uusuario que realiza la venta
        c.setIdUsuarioCredito(venta.getIdVendedorFK());
        //tipo de pago semanal, mensual etc
        c.setIdTipoCreditoFk(data.getTipoPago());
        //estatus de credito en activo =1
        c.setEstatusCredito(new BigDecimal("1"));
        //Numero de veces que el cliente a prometido pagar, al realizar la venta seria la primera promesa
        c.setNumeroPromesaPago(new BigDecimal("1"));
        //el dia de la compra a credito
        c.setFechaInicioCredito(fechaActual);
        // Fecha en la que el cliente promete liquidar los pagos del credito
        c.setFechaPromesaPago(fechaPromesaPago);
        //el procentaje de interes que se le cobrara al cliente por la compra
        c.setTazaInteres(INTERES_VENTA);
        //Lo que el cliente deja a cuenta
        c.setDejaCuenta(dejaACuenta);
        //Estatus si se a cobrado lo que el usuario a dejado a cuenta al momento de la venta es 0 (que no se a cobrado).
        c.setStatusACuenta(new BigDecimal("0"));

        if (dejaACuenta == null || dejaACuenta.intValue() == 0) {
            c.setMontoCredito(totalVentaGeneral.setScale(2, RoundingMode.UP));
        } else {
            c.setMontoCredito(totalVentaDescuento.subtract(dejaACuenta).setScale(2, RoundingMode.UP));
        }
        //Numero de pagos que el cliente debera realizar
        c.setPlasos(data.getTipoPago());
        //El numero de dias del plaso
        c.setNumeroPagos(data.getNumeroPagos());

        folioCredito = ifaceCredito.insert(c);

        if (folioCredito != 0) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal getTotalVentaDescuento() {
        return totalVentaDescuento;
    }

    public void setTotalVentaDescuento(BigDecimal totalVentaDescuento) {
        this.totalVentaDescuento = totalVentaDescuento;
    }

    public VentaProductoMayoreo getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(VentaProductoMayoreo dataRemove) {
        this.dataRemove = dataRemove;
    }

    public VentaProductoMayoreo getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(VentaProductoMayoreo dataEdit) {
        this.dataEdit = dataEdit;
    }

    public ExistenciaProducto getEp() {
        return ep;
    }

    public void setEp(ExistenciaProducto ep) {
        this.ep = ep;
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public ArrayList<Usuario> autoCompleteVendedor(String nombreUsuario) {
        lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase(), idSucu.intValue());
        return lstUsuario;

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public BigDecimal getTotalVentaGeneral() {
        return totalVentaGeneral;
    }

    public void setTotalVentaGeneral(BigDecimal totalVentaGeneral) {
        this.totalVentaGeneral = totalVentaGeneral;
    }

    public ArrayList<ExistenciaProducto> getLstExistencias() {
        return lstExistencias;
    }

    public void setLstExistencias(ArrayList<ExistenciaProducto> lstExistencias) {
        this.lstExistencias = lstExistencias;
    }

    public ArrayList<VentaProductoMayoreo> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<VentaProductoMayoreo> lstVenta) {
        this.lstVenta = lstVenta;
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public BigDecimal getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(BigDecimal idSucu) {
        this.idSucu = idSucu;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public VentaProductoMayoreo getData() {
        return data;
    }

    public void setData(VentaProductoMayoreo data) {
        this.data = data;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public EntradaMercancia getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(EntradaMercancia entradaMercancia) {
        this.entradaMercancia = entradaMercancia;
    }

    public BigDecimal getIdExistencia() {
        return idExistencia;
    }

    public void setIdExistencia(BigDecimal idExistencia) {
        this.idExistencia = idExistencia;
    }

    public ExistenciaProducto getSelectedExistencia() {
        return selectedExistencia;
    }

    public void setSelectedExistencia(ExistenciaProducto selectedExistencia) {
        this.selectedExistencia = selectedExistencia;
    }

    public VentaMayoreo getVentaGeneral() {
        return ventaGeneral;
    }

    public void setVentaGeneral(VentaMayoreo ventaGeneral) {
        this.ventaGeneral = ventaGeneral;
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

    public boolean isPermisionToWrite() {
        return permisionToWrite;
    }

    public void setPermisionToWrite(boolean permisionToWrite) {
        this.permisionToWrite = permisionToWrite;
    }

    public BigDecimal getTotalProductoTemporal() {
        return totalProductoTemporal;
    }

    public void setTotalProductoTemporal(BigDecimal totalProductoTemporal) {
        this.totalProductoTemporal = totalProductoTemporal;
    }

    public String getVentaRapida() {
        return ventaRapida;
    }

    public void setVentaRapida(String ventaRapida) {
        this.ventaRapida = ventaRapida;
    }

    public String getVentaRapidaButton() {
        return ventaRapidaButton;
    }

    public void setVentaRapidaButton(String ventaRapidaButton) {
        this.ventaRapidaButton = ventaRapidaButton;
    }

    public boolean isPermisionVentaRapida() {
        return permisionVentaRapida;
    }

    public void setPermisionVentaRapida(boolean permisionVentaRapida) {
        this.permisionVentaRapida = permisionVentaRapida;
    }

    public BigDecimal getRecibido() {
        return recibido;
    }

    public void setRecibido(BigDecimal recibido) {
        this.recibido = recibido;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public boolean isPruaba() {
        System.out.println("prubea is:" + pruaba);
        return pruaba;
    }

    public void setPruaba(boolean pruaba) {
        System.out.println("prubea set:" + pruaba);
        this.pruaba = pruaba;
    }

    public boolean isCharLine() {
        return charLine;
    }

    public void setCharLine(boolean charLine) {
        this.charLine = charLine;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    public boolean isPermisionToEdit() {
        return permisionToEdit;
    }

    public void setPermisionToEdit(boolean permisionToEdit) {
        this.permisionToEdit = permisionToEdit;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getDejaACuenta() {
        return dejaACuenta;
    }

    public void setDejaACuenta(BigDecimal dejaACuenta) {
        this.dejaACuenta = dejaACuenta;
    }

}
