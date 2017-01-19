package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.service.IfaceVentaProducto;
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceTipoVenta ifaceTipoVenta;
    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;
    @Autowired
    private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired
    private IfaceMantenimientoPrecio ifaceMantenimientoPrecio;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;

    private static final Logger logger = LoggerFactory.getLogger(BeanVenta.class);

    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<VentaProducto> lstVenta;
    private ArrayList<TipoVenta> lstTipoVenta;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;

    private Credito c;
    private Venta venta;
    private Usuario usuario;
    private Usuario vendedor;
    private Cliente cliente;
    private VentaProducto data;
    private VentaProducto dataEdit;
    private Subproducto subProducto;
    private VentaProducto dataRemove;
    private VentaProducto ventaProducto;
    private UsuarioDominio usuarioDominio;

    private StreamedContent media;

    private Map paramReport = new HashMap();

    private ByteArrayOutputStream outputStream;

    private String number;
    private String rutaPDF;
    private String title = "";
    private String viewEstate = "";
    private String nombreEmpaque = "";
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private String pathFileJasperCredito = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticketCredito.jasper";

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal descuento;
    private BigDecimal dejaACuenta;
    private BigDecimal totalVenta;
    private BigDecimal totalVentaDescuento;
    private BigDecimal INTERES_VENTA = new BigDecimal("0.60");
    private BigDecimal DIAS_PLAZO = new BigDecimal("7");

    private int idSucu;
    private int folioCredito = 0;

    private boolean permisionToEdit;
    private boolean variableInicial;
    private boolean credito;

    private String codigoBarras;
    Date date = new Date();

    @PostConstruct
    public void init() {

        logger.info("Se inicializa el bean : " + BeanVenta.class);
        venta = new Venta();
        usuario = new Usuario();
        vendedor = new Usuario();

        max = new BigDecimal(0);
        min = new BigDecimal(0);
        descuento = new BigDecimal(0);
        dejaACuenta = new BigDecimal(0);
        totalVentaDescuento = new BigDecimal(0);

        folioCredito = 0;

        date = context.getFechaSistema();

        usuarioDominio = context.getUsuarioAutenticado();
        idSucu = usuarioDominio.getSucId();

        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        vendedor.setIdUsuarioPk(usuario.getIdUsuarioPk());
        vendedor.setNombreUsuario(usuario.getNombreUsuario());
        vendedor.setApaternoUsuario(usuario.getApaternoUsuario());
        vendedor.setAmaternoUsuario(usuario.getAmaternoUsuario());

        lstTipoVenta = ifaceTipoVenta.getAll();
        cliente = new Cliente();
        autoCompleteCliente("");
        cliente = ifaceCatCliente.getCreditoClienteByIdCliente(new BigDecimal("1"));

        permisionToEdit = false;

        venta.setIdSucursal(idSucu);

        data = new VentaProducto();
        autoComplete("");

        data.setIdTipoEmpaqueFk(new BigDecimal(-1));
        data.setIdTipoVentaFk(new BigDecimal("1"));
        data.setTipoPago(new BigDecimal(7));

        lstProducto = new ArrayList<Subproducto>();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();

        lstVenta = new ArrayList<VentaProducto>();

        selectedTipoEmpaque();

        variableInicial = false;
        credito = false;

        setTitle("Venta de Productos");
        setViewEstate("init");

        validaCreditoCliente();

    }

    public void searchByBarCode() {
        logger.info("Se hace la Busqueda por codigo de Barras");
        subProducto = new Subproducto();

        if (codigoBarras.length() == 8) {
            searchByBarCode(codigoBarras);
            subProducto = ifaceSubProducto.getSubProductoById(codigoBarras);

        } else {
            JsfUtil.addWarnMessage("Código de Barras Incorrecto");
            logger.info("Codigo de Barras Incorrecto");
        }
    }

    public void searchByBarCode(String idSubProducto) {

        MantenimientoPrecios mantenimentoPrecio = new MantenimientoPrecios();
        BigDecimal pc = new BigDecimal(0);
        mantenimentoPrecio.setPrecioVenta(pc);
        int idEmpaque = data.getIdTipoEmpaqueFk() == null ? 0 : data.getIdTipoEmpaqueFk().intValue();
        mantenimentoPrecio = ifaceMantenimientoPrecio.getMantenimientoPrecioById(idSubProducto.trim(), idEmpaque, idSucu);

        if (mantenimentoPrecio.getPrecioMinimo() == mantenimentoPrecio.getPrecioMaximo()) {
            permisionToEdit = true;

        } else if (mantenimentoPrecio.getPrecioMinimo().intValue() == mantenimentoPrecio.getPrecioMaximo().intValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El precio de este producto no es Ajustable"));
            max = precioProducto(mantenimentoPrecio.getPrecioMaximo());
            min = precioProducto(mantenimentoPrecio.getPrecioMinimo());
            permisionToEdit = true;

        } else {
            max = precioProducto(mantenimentoPrecio.getPrecioMaximo());
            min = precioProducto(mantenimentoPrecio.getPrecioMinimo());
            permisionToEdit = false;
        }

        if (mantenimentoPrecio.getPrecioVenta() == null && variableInicial == false) {
            JsfUtil.addErrorMessage("No se tiene el precio de este prodcuto, favor de contactar al gerente.");
            permisionToEdit = true;
        }

//        RequestContext.getCurrentInstance().execute("document.getElementById('formContent:txtPrecioVenta').focus();");
        data.setIdProductoFk(idSubProducto);
        data.setIdTipoEmpaqueFk(new BigDecimal(idEmpaque));

        data.setPrecioProducto(mantenimentoPrecio.getPrecioVenta() == null ? null : precioProducto(mantenimentoPrecio.getPrecioVenta()));
        data.setPrecioSinInteres(mantenimentoPrecio.getPrecioVenta() == null ? null : mantenimentoPrecio.getPrecioVenta());
    }

    public void selectedTipoEmpaque() {

        for (TipoEmpaque empaque : lstTipoEmpaque) {

            if ((empaque.getNombreEmpaque().equalsIgnoreCase("Kilos") && data.getIdTipoEmpaqueFk().equals(new BigDecimal(-1))) || data.getIdTipoEmpaqueFk().equals(empaque.getIdTipoEmpaquePk())) {
                data.setIdTipoEmpaqueFk(empaque.getIdTipoEmpaquePk());
                setNombreEmpaque(empaque.getNombreEmpaque());
                break;
            }

        }
        variableInicial = true;

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
        lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase(), idSucu);
        return lstUsuario;

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean restarExistencias(VentaProducto producto) {
        ExistenciaMenudeo ex = new ExistenciaMenudeo();
        ex = ifaceExistenciaMenudeo.getExistenciasRepetidasById(producto.getIdProductoFk(), new BigDecimal(venta.getIdSucursal()));
        if (ex.getIdExMenPk() == null) {
            JsfUtil.addErrorMessage("No se encontraron existencias de este producto.");
            return false;
        } else {
            ex.setKilos(ex.getKilos().subtract(producto.getCantidadEmpaque(), MathContext.UNLIMITED));
            if (ifaceExistenciaMenudeo.updateExistenciaMenudeo(ex) != 0) {
                return true;
            } else {
                return false;
            }
        }

    }

    @Override
    public String insert() {

        int idVenta = 0;
        int folioVenta = 0;
        Venta venta = new Venta();
        int folioVentaMayoreo = 0;
        String mensaje = validaCompraCredito();

        try {
            if (!mensaje.equals("")) {
                JsfUtil.addErrorMessage(mensaje);
                return null;
            }
            if (!lstVenta.isEmpty() && lstVenta.size() > 0) {

                idVenta = ifaceVenta.getNextVal();
                folioVenta = ifaceVenta.getFolioByIdSucursal(idSucu);
                folioVentaMayoreo = ifaceVentaMayoreo.getVentaSucursal(new BigDecimal(idSucu));

                if (folioVentaMayoreo > folioVenta) {
                    folioVenta = folioVentaMayoreo;
                }
                folioVenta = folioVenta + 1;

                venta.setIdVentaPk(new BigDecimal(idVenta));
                venta.setIdClienteFk(cliente.getId_cliente());
                
                venta.setIdVendedorFk(vendedor.getIdUsuarioPk());
                venta.setIdUsuarioLogueadoFk(usuario.getIdUsuarioPk());
                venta.setIdSucursal(idSucu);
                venta.setTipoVenta(data.getIdTipoVentaFk());

                int ventaInsertada = ifaceVenta.insertarVenta(venta, folioVenta);
                int productoInsertado = 0;
                if (ventaInsertada != 0) {
                    logger.error("Se inserto la venta");
                    for (VentaProducto producto : lstVenta) {
                        if (restarExistencias(producto)) {
                            productoInsertado = ifaceVentaProducto.insertarVentaProducto(producto, idVenta);
                            logger.error("Se inserto los productos de las ventas");

                        } else {
                            JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");
                            logger.error("No se pudo completar la venta");
                            break;
                        }
                    }

                    if (productoInsertado == 1) {
                        if (!data.getIdTipoVentaFk().equals(new BigDecimal("1"))) {
                            if (insertaCredito(venta)) {
                                logger.info("Se inserto el credito");
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "La venta se realizo correctamente."));
                            } else {
                                logger.info("No se pudo insertar el credito");
                                JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");
                            }
                        }
                    }

                    setParameterTicket(idVenta, folioVenta);

                    generateReport(idVenta, folioVenta);
                    init();

                    lstVenta.clear();
                    totalVenta = new BigDecimal(0);
                    codigoBarras = null;

                    RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");

                } else {
                    JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al insertar la venta."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Necesitas agregar al menos un producto para realizar la venta."));

            }

            return null;

        } catch (StackOverflowError ex) {
            ex.printStackTrace();
            logger.error("Error >" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));
            return null;

        } catch (Exception e) {
            logger.error("Error >" + e.getMessage());
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.toString()));
            return null;

        }
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {

        MantenimientoPrecios mantenimentoPrecio = new MantenimientoPrecios();
        BigDecimal pc = new BigDecimal(0);
        mantenimentoPrecio.setPrecioVenta(pc);

        int idEmpaque = data.getIdTipoEmpaqueFk() == null ? 0 : data.getIdTipoEmpaqueFk().intValue();

        String idSubProducto = subProducto == null ? "" : (subProducto.getIdSubproductoPk() == null ? "" : subProducto.getIdSubproductoPk());

        mantenimentoPrecio = ifaceMantenimientoPrecio.getMantenimientoPrecioById(idSubProducto.trim(), idEmpaque, idSucu);

        if (mantenimentoPrecio.getPrecioMinimo() == mantenimentoPrecio.getPrecioMaximo()) {
            permisionToEdit = true;

        } else if (mantenimentoPrecio.getPrecioMinimo().intValue() == mantenimentoPrecio.getPrecioMaximo().intValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El precio de este producto no es Ajustable"));
            max = precioProducto(mantenimentoPrecio.getPrecioMaximo());
            min = precioProducto(mantenimentoPrecio.getPrecioMinimo());
            permisionToEdit = true;

        } else {
            max = precioProducto(mantenimentoPrecio.getPrecioMaximo());
            min = precioProducto(mantenimentoPrecio.getPrecioMinimo());
            permisionToEdit = false;
        }

        if (mantenimentoPrecio.getPrecioVenta() == null && variableInicial == false) {
            JsfUtil.addErrorMessage("No se tiene el precio de este prodcuto, favor de contactar al gerente.");
            permisionToEdit = true;
        }

//        RequestContext.getCurrentInstance().execute("document.getElementById('formContent:txtPrecioVenta').focus();");
        data.setIdProductoFk(idSubProducto);
        data.setIdTipoEmpaqueFk(new BigDecimal(idEmpaque));

        data.setPrecioProducto(mantenimentoPrecio.getPrecioVenta() == null ? null : precioProducto(mantenimentoPrecio.getPrecioVenta()));
        data.setPrecioSinInteres(mantenimentoPrecio.getPrecioVenta() == null ? null : mantenimentoPrecio.getPrecioVenta());
    }

    public void addProducto() {

        if (data.getPrecioProducto() != null) {
            if (data.getPrecioProducto().intValue() < min.intValue() || data.getPrecioProducto().intValue() > max.intValue()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El precio de venta esta fuera de valores permitidos: Mínimo:" + min + " Máximo: " + max));
                //Verifica si hay existencias disponibles y si hay repetidos
            } else if (verificaExistencia(1) && !addRepetidos()) {

                VentaProducto venta = new VentaProducto();
                TipoEmpaque empaque = new TipoEmpaque();
                DecimalFormat df = new DecimalFormat("#,###.##");

                empaque = getEmpaque(data.getIdTipoEmpaqueFk());

                venta.setCantidadEmpaque(data.getCantidadEmpaque());
                venta.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
                venta.setIdVentaProductoPk(data.getIdVentaProductoPk());
                venta.setKilosVenta(data.getKilosVenta());
                venta.setNombreProducto(subProducto.getNombreSubproducto());
                venta.setIdProductoFk(subProducto.getIdSubproductoPk());
                venta.setNombreEmpaque(empaque.getNombreEmpaque());
                venta.setPrecioSinInteres(data.getPrecioSinInteres());
                venta.setPrecioProducto(data.getPrecioProducto());
                venta.setTotal(venta.getPrecioProducto().multiply(venta.getCantidadEmpaque()));

                lstVenta.add(venta);

                clear();
            }
            calcularTotalVenta();
        } else {
            JsfUtil.addErrorMessage("No se tiene el precio de este prodcuto, favor de contactar al gerente.");
            logger.info("No se tine el precio del producto");
        }
    }

    private void clear() {
        data.reset();

        subProducto = new Subproducto();
        selectedTipoEmpaque();
        variableInicial = false;
        codigoBarras = null;
    }

    //Recibe un 2 si es una modificacion
    private boolean verificaExistencia(int tipo) {
        ExistenciaMenudeo ex = new ExistenciaMenudeo();
        BigDecimal kilosExistentes = BigDecimal.ZERO;
        if (tipo == 2) {
            kilosExistentes = getKilosVenta(dataEdit.getIdProductoFk()).subtract(dataEdit.getCantidadEmpaque()).add(data.getCantidadEmpaque());
        } else {
            kilosExistentes = getKilosVenta(data.getIdProductoFk());
            kilosExistentes = kilosExistentes.add(data.getCantidadEmpaque());
        }

        ex = ifaceExistenciaMenudeo.getExistenciasRepetidasById(subProducto.getIdSubproductoPk(), new BigDecimal(venta.getIdSucursal()));

        if (ex.getIdExMenPk() == null) {
            JsfUtil.addErrorMessage("No se encontraron existencias de este producto");
            return false;
        } else if (kilosExistentes.compareTo(ex.getKilos()) == 1) {
            JsfUtil.addErrorMessageClean("No alcanzan las existencias, solo hay " + ex.getKilos() + " Kilos Disponibles");
            return false;
        } else {
            return true;
        }

    }

    //En caso de que ya ecxista un producto con el mismo id en el carrito de compra y los precios de venta sean iguales se crea un solo registro
    private boolean addRepetidos() {

        for (VentaProducto dominio : lstVenta) {
            if (data.getIdProductoFk().equals(dominio.getIdProductoFk()) && data.getPrecioProducto().equals(dominio.getPrecioProducto())) {
                dominio.setCantidadEmpaque(data.getCantidadEmpaque().add(dominio.getCantidadEmpaque()));
                dominio.setTotal(dominio.getPrecioProducto().multiply(dominio.getCantidadEmpaque()));
                clear();
                return true;

            }
        }
        return false;

    }

    //Obtiene los kilos de venta de un producto que ya estan agregados al carrito 
    private BigDecimal getKilosVenta(String idSubproducto) {
        BigDecimal kiloProducto = BigDecimal.ZERO;

        for (VentaProducto dominio : lstVenta) {
            if (idSubproducto.equals(dominio.getIdProductoFk())) {
                kiloProducto = kiloProducto.add(dominio.getCantidadEmpaque());

            }
        }
        return kiloProducto;

    }

    private void calcularTotalVenta() {
        setTotalVenta(new BigDecimal(0));
        totalVentaDescuento = new BigDecimal(0);
        for (VentaProducto venta : lstVenta) {
            setTotalVenta(getTotalVenta().add(venta.getTotal()));
        }
        totalVentaDescuento = getTotalVenta();
        totalVentaDescuento = totalVentaDescuento.setScale(2, RoundingMode.UP);

        calculaAhorro(null);
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

    private void setParameterTicket(int idVenta, int folioVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");

        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();

        for (VentaProducto venta : lstVenta) {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(venta.getNombreProducto().toUpperCase());
            productos.add("                     " + cantidad + "     " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));

        }
        String totalVentaDescuentoStr = "";
        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);
        if (data.getIdTipoVentaFk().equals(new BigDecimal(2))) {
            totalVentaDescuentoStr = numeroLetra.Convertir(df.format(c.getMontoCredito()), true);
        }

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta, folioVenta, totalVentaDescuentoStr, nf.format(totalVentaDescuento));

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta, int folioVenta, String totalVentaDescuentoStr, String totalDescuento) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        paramReport.clear();
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(folioVenta));
        paramReport.put("cliente", cliente.getNombreCompleto());
        paramReport.put("vendedor", vendedor.getNombreCompletoUsuario());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("estado", "PEDIDO MARCADO");
        paramReport.put("labelFecha", "Fecha de Venta:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());

        //Se agregan los campos que se utiliza en el ticket de credito
        if (!data.getIdTipoVentaFk().equals(new BigDecimal(1))) {

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

    public void editProducto() {

        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdProductoFk());

        autoComplete(dataEdit.getNombreProducto());
        searchById();

        data.setCantidadEmpaque(dataEdit.getCantidadEmpaque());
        data.setIdProductoFk(dataEdit.getIdProductoFk());
        data.setIdTipoEmpaqueFk(dataEdit.getIdTipoEmpaqueFk());
        data.setIdVentaProductoPk(dataEdit.getIdVentaProductoPk());
        data.setKilosVenta(dataEdit.getKilosVenta());
        data.setPrecioSinInteres(dataEdit.getPrecioSinInteres());
        data.setPrecioProducto(dataEdit.getPrecioProducto());
        data.setNombreProducto(dataEdit.getNombreProducto());
        data.setIdProductoFk(dataEdit.getIdProductoFk());

        viewEstate = "update";

    }

    public void updateProducto() {

        TipoEmpaque empaque = new TipoEmpaque();

        if (data.getPrecioProducto() != null) {

            if (verificaExistencia(2)) {

                empaque = getEmpaque(data.getIdTipoEmpaqueFk());

                data.setIdProductoFk(subProducto.getIdSubproductoPk());
                data.setNombreProducto(subProducto.getNombreSubproducto());

                dataEdit.setCantidadEmpaque(data.getCantidadEmpaque());
                dataEdit.setIdProductoFk(data.getIdProductoFk());
                dataEdit.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
                dataEdit.setIdVentaProductoPk(data.getIdVentaProductoPk());
                dataEdit.setKilosVenta(data.getKilosVenta());
                dataEdit.setNombreProducto(data.getNombreProducto());
                dataEdit.setIdProductoFk(data.getIdProductoFk());
                dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());
                dataEdit.setPrecioSinInteres(data.getPrecioSinInteres());
                dataEdit.setPrecioProducto(data.getPrecioProducto());
                dataEdit.setTotal(dataEdit.getPrecioProducto().multiply(dataEdit.getCantidadEmpaque()));

                calcularTotalVenta();
                viewEstate = "init";
                data.reset();
            }
        } else {
            JsfUtil.addErrorMessage("No se tiene el precio de este prodcuto, favor de contactar al gerente.");
        }

    }

    public void cancelarModificacion() {
        data.reset();
        subProducto = new Subproducto();
        viewEstate = "init";

    }

    public void generateReport(int idVenta, int folioVenta) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            if (data.getIdTipoVentaFk().equals(new BigDecimal(1))) {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            } else {
                pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticketCredito.jasper";
            }

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "ventaMenudeo", idVenta, idSucu);
            logger.info("Ticket generado correctamente " + rutaPDF);

        } catch (Exception exception) {
            logger.error("Error al genarar el reporte: " + exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Ticket de Venta.");
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

    //Calcula los precios de cada producto cuando es una venta de credito
    public void calculaPrecioProducto() {
        if (data.getIdTipoVentaFk().intValue() != 1) {

            BigDecimal diasAnio = new BigDecimal(365);

            BigDecimal totalDiasPagar = data.getTipoPago() == null ? DIAS_PLAZO : data.getTipoPago();
            BigDecimal interesDiasPagar = (INTERES_VENTA.divide(diasAnio, 8, RoundingMode.HALF_UP).multiply(totalDiasPagar)).add(new BigDecimal(1));
//            data.setNumeroPagos(totalDiasPagar.divide(DIAS_PLAZO)); descomentar cuando se aplique el pago por semana
            data.setNumeroPagos(new BigDecimal(1));

            for (VentaProducto dominio : lstVenta) {

                dominio.setPrecioProducto(dominio.getPrecioSinInteres().multiply(interesDiasPagar));
                dominio.setTotal(dominio.getPrecioProducto().multiply(dominio.getCantidadEmpaque()));
            }

        } else {

            for (VentaProducto dominio : lstVenta) {
                dominio.setPrecioProducto(dominio.getPrecioSinInteres());
                dominio.setTotal(dominio.getPrecioProducto().multiply(dominio.getCantidadEmpaque()));
            }

        }

        calcularTotalVenta();
    }

    private BigDecimal precioProducto(BigDecimal precioProducto) {

        BigDecimal diasAnio = new BigDecimal(365);
        if (data.getIdTipoVentaFk() != null && data.getIdTipoVentaFk().intValue() != 1) {

            BigDecimal totalConInteres = new BigDecimal("0");
            BigDecimal totalDiasPagar = data.getTipoPago() == null ? new BigDecimal("7") : data.getTipoPago();
            BigDecimal interesDiasPagar = INTERES_VENTA.divide(diasAnio, 8, RoundingMode.HALF_UP).multiply(totalDiasPagar);
            totalConInteres = precioProducto.multiply(interesDiasPagar.add(new BigDecimal("1")));
            totalConInteres = totalConInteres.setScale(2, RoundingMode.HALF_UP);
            return totalConInteres;
        } else {
            return precioProducto;
        }

    }

    public void validaCreditoCliente() {

        BigDecimal tipoPago = new BigDecimal("1");
        BigDecimal idClienteVenta = new BigDecimal("1");

        if (cliente.getId_cliente().equals(idClienteVenta)) {
            credito = false;
            data.setIdTipoVentaFk(tipoPago);
        } else if (cliente.getLimiteCredito() == null) {
            JsfUtil.addWarnMessage("El Cliente no tiene Crédito.");
            data.setIdTipoVentaFk(tipoPago);
            credito = false;
        } else if (cliente.getCreditoDisponible().compareTo(new BigDecimal("0")) == 1) {
            credito = true;
        } else {
            JsfUtil.addWarnMessage("El cliente no tiene credito disponible :" + cliente.getCreditoDisponible());
            data.setIdTipoVentaFk(tipoPago);
            credito = false;
        }
        calculaPrecioProducto();

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

    private boolean insertaCredito(Venta venta) {
        c = new Credito();

        Date fechaActual = context.getFechaSistema();
        Date fechaPromesaPago = TiempoUtil.sumarRestarDias(fechaActual, data.getTipoPago().intValue());

        c.setIdClienteFk(venta.getIdClienteFk());
        //folio de la venta de menudeo (es el folio general no el de sucursal)
        c.setIdVentaMenudeo(venta.getIdVentaPk());
        //Uusuario que realiza la venta
        c.setIdUsuarioCredito(venta.getIdVendedorFk());
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

        //aqui estaba el codigo que calcula el descuento
        c.setMontoCredito(totalVentaDescuento.subtract(dejaACuenta).setScale(2, RoundingMode.UP));
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

    private String validaCompraCredito() {

        String mensaje = "";
        BigDecimal tipoCredito = new BigDecimal("2");

        if (data.getIdTipoVentaFk().equals(tipoCredito)) {

            BigDecimal creditoVenta = totalVenta.subtract(dejaACuenta);

            if (totalVenta.compareTo(dejaACuenta) == -1) {
                return "El monto a cuenta :" + dejaACuenta + " es mayor al total de la venta: " + totalVenta;
            } else if (cliente.getCreditoDisponible().compareTo(creditoVenta) == -1) {

                return "No tiene el credito necesario para realizar la compra. Credito disponible: $" + cliente.getCreditoDisponible() + ", Compra de Credito :$" + creditoVenta + ", a cuenta :$" + dejaACuenta;
            }
        }

        return mensaje;

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

            for (VentaProducto ventaAcuenta : lstVenta) {

                //Si lo que se dejo a cuenta es igual o menor a 0 se sale del for
                if (tempDejaAcuenta.compareTo(zero) <= 0) {
                    break;
                }

                BigDecimal kiloCompra = new BigDecimal(0);
                BigDecimal totalCompraSinInteres = ventaAcuenta.getCantidadEmpaque().multiply(ventaAcuenta.getPrecioSinInteres());
                int compareDejaAcuenta = totalCompraSinInteres.compareTo(tempDejaAcuenta);

                if (compareDejaAcuenta == -1) {

                    kiloCompra = ventaAcuenta.getCantidadEmpaque();
                    descuento = descuento.add(kiloCompra.multiply(ventaAcuenta.getPrecioProducto()).subtract(kiloCompra.multiply(ventaAcuenta.getPrecioSinInteres())));
                    tempDejaAcuenta = tempDejaAcuenta.subtract(kiloCompra.multiply(ventaAcuenta.getPrecioSinInteres()));

                } else if (compareDejaAcuenta == 1) {

                    kiloCompra = tempDejaAcuenta.divide(ventaAcuenta.getPrecioSinInteres(), 2, RoundingMode.UP);
                    tempDejaAcuenta = zero;
                    descuento = descuento.add(kiloCompra.multiply(ventaAcuenta.getPrecioProducto()).subtract(kiloCompra.multiply(ventaAcuenta.getPrecioSinInteres())));

                } else {

                    tempDejaAcuenta = zero;
                    kiloCompra = ventaAcuenta.getCantidadEmpaque();
                    descuento = descuento.add(kiloCompra.multiply(ventaAcuenta.getPrecioProducto()).subtract(kiloCompra.multiply(ventaAcuenta.getPrecioSinInteres())));
                }

            }
            descuento = descuento.setScale(2, RoundingMode.UP);
            //SI el descuento es negativo se pone el valor  cero
            if (descuento.compareTo(zero) == -1) {
                descuento = zero;
            }
            setTotalVentaDescuento(totalVenta.subtract(descuento));

        }
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
    }

    public String getPathFileJasperCredito() {
        return pathFileJasperCredito;
    }

    public void setPathFileJasperCredito(String pathFileJasperCredito) {
        this.pathFileJasperCredito = pathFileJasperCredito;
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

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public int getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(int idSucu) {
        this.idSucu = idSucu;
    }

    public boolean isPermisionToEdit() {
        return permisionToEdit;
    }

    public void setPermisionToEdit(boolean permisionToEdit) {
        this.permisionToEdit = permisionToEdit;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<TipoVenta> getLstTipoVenta() {
        return lstTipoVenta;
    }

    public void setLstTipoVenta(ArrayList<TipoVenta> lstTipoVenta) {
        this.lstTipoVenta = lstTipoVenta;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotalVentaDescuento() {
        return totalVentaDescuento;
    }

    public void setTotalVentaDescuento(BigDecimal totalVentaDescuento) {
        this.totalVentaDescuento = totalVentaDescuento;
    }

    public BigDecimal getDejaACuenta() {
        return dejaACuenta;
    }

    public void setDejaACuenta(BigDecimal dejaACuenta) {
        this.dejaACuenta = dejaACuenta;
    }

    public void setDejaACuenta(ValueChangeEvent ev) {
        BigDecimal valueStr = new BigDecimal(ev.getNewValue().toString());
        setDejaACuenta(valueStr);
        // prevent setter being called again during update-model phase
        ((UIInput) ev.getComponent()).setLocalValueSet(false);
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
    

}
