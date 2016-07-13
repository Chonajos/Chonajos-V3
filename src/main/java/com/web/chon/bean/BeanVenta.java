package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.ExistenciaMenudeo;
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
import java.math.BigInteger;
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

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
    
    @Autowired private IfaceVenta ifaceVenta;
    @Autowired private IfaceCredito ifaceCredito;
    @Autowired private IfaceEmpaque ifaceEmpaque;
    @Autowired private IfaceTipoVenta ifaceTipoVenta;
    @Autowired private IfaceCatCliente ifaceCatCliente;
    @Autowired private IfaceCatUsuario ifaceCatUsuario;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceVentaProducto ifaceVentaProducto;
    @Autowired private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired private IfaceMantenimientoPrecio ifaceMantenimientoPrecio;

    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<VentaProducto> lstVenta;
    private ArrayList<TipoVenta> lstTipoVenta;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    
    private Venta venta;
    private Usuario usuario;
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

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal totalVenta;
    private BigDecimal INTERES_VENTA = new BigDecimal("0.60");

    private int idSucu;
    
    private boolean permisionToEdit;
    private boolean variableInicial; 
    
    @PostConstruct
    public void init() {
        
        max = new BigDecimal(0);
        min = new BigDecimal(0);
        
        context.getFechaSistema();
        usuario = new Usuario();
        
        usuarioDominio = context.getUsuarioAutenticado();
        
        idSucu = usuarioDominio.getSucId();
        
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        
        lstTipoVenta = ifaceTipoVenta.getAll();
        cliente = ifaceCatCliente.getClienteById(1);
        
        permisionToEdit = false;
        
        venta = new Venta();
        venta.setIdSucursal(idSucu);
        
        data = new VentaProducto();
        data.setIdTipoEmpaqueFk(new BigDecimal(-1));
        data.setTipoPago(new BigDecimal("1"));
        
        lstProducto = new ArrayList<Subproducto>();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();

        lstVenta = new ArrayList<VentaProducto>();
        
        selectedTipoEmpaque();
        
        variableInicial = false;
        
        setTitle("Venta de Productos");
        setViewEstate("init");
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

        try {
            if (!lstVenta.isEmpty() && lstVenta.size() > 0) {

                idVenta = ifaceVenta.getNextVal();
                folioVenta = ifaceVenta.getFolioByIdSucursal(idSucu);
                
                venta.setIdVentaPk(new BigDecimal(idVenta));
                venta.setIdClienteFk(cliente.getId_cliente());
                venta.setIdVendedorFk(usuario.getIdUsuarioPk());
                venta.setIdSucursal(idSucu);
                
                int ventaInsertada = ifaceVenta.insertarVenta(venta, folioVenta);

                if (ventaInsertada != 0) {
                    for (VentaProducto producto : lstVenta) {
                        if (restarExistencias(producto)) {
                            if(ifaceVentaProducto.insertarVentaProducto(producto, idVenta) == 1){
                                //credito clientes
                                if(insertaCredito(venta)){
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "La venta se realizo correctamente."));
                                }else{
                                    JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");                                }
                            }

                        } else {
                            JsfUtil.addErrorMessage("Error al Realizar la Venta, favor de cancelar la venta y volver a realizarla. Si los problemas persisten contactar al administrado.");
                            break;
                        }
                    }
                    
                    setParameterTicket(idVenta, folioVenta);
                    
                    generateReport(idVenta, folioVenta);
                    cancel();
                    
                    lstVenta.clear();
                    totalVenta = new BigDecimal(0);
                    
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));
            return null;

        } catch (Exception e) {
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

        data.setIdProductoFk(idSubProducto);
        data.setIdTipoEmpaqueFk(new BigDecimal(idEmpaque));

        data.setPrecioProducto(mantenimentoPrecio.getPrecioVenta() == null ? null : precioProducto(mantenimentoPrecio.getPrecioVenta()));
        data.setPrecioSinInteres(mantenimentoPrecio.getPrecioVenta() == null ? null : mantenimentoPrecio.getPrecioVenta());
    }

    public void addProducto() {
        if (data.getPrecioProducto() != null) {
            if (data.getPrecioProducto().intValue() < min.intValue() || data.getPrecioProducto().intValue() > max.intValue()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El precio de venta esta fuera de valores permitidos: Mínimo:" + min + " Máximo: " + max));

            } else if (verificaExistencia()) {
                
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
                calcularTotalVenta();
                data.reset();
                
                subProducto = new Subproducto();
                selectedTipoEmpaque();
                variableInicial = false;
            }

        } else {
            JsfUtil.addErrorMessage("No se tiene el precio de este prodcuto, favor de contactar al gerente.");
        }

    }

    private boolean verificaExistencia() {
        ExistenciaMenudeo ex = new ExistenciaMenudeo();
        ex = ifaceExistenciaMenudeo.getExistenciasRepetidasById(subProducto.getIdSubproductoPk(), new BigDecimal(venta.getIdSucursal()));
        if (ex.getIdExMenPk() == null) {
            JsfUtil.addErrorMessage("No se encontraron existencias de este producto");
            return false;
        } else {
            if ((data.getCantidadEmpaque().compareTo(ex.getKilos())) == 1) {
                JsfUtil.addErrorMessageClean("No alcanzan las existencias, solo hay " + ex.getKilos() + " Kilos Disponibles");
                return false;
            } else {
                return true;
            }
        }

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

    private void setParameterTicket(int idVenta, int folioVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();

        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();

        for (VentaProducto venta : lstVenta) {

            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(venta.getNombreProducto().toUpperCase());
            productos.add("                       " + cantidad + "     " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotal()));

        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta, folioVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta, int folioVenta) {

        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(folioVenta));
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
            if (verificaExistencia()) {
                
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
                dataEdit.setPrecioSinInteres(data.getPrecioProducto());
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

    public void cancel() {
        data.reset();
        subProducto = new Subproducto();
        viewEstate = "init";
        init();

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

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", idVenta, idSucu);

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

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
            BigDecimal numPagos = data.getNumeroPagos() == null ? new BigDecimal("1") : data.getNumeroPagos();
            BigDecimal totalDiasPagar = numPagos.multiply(data.getTipoPago());
            BigDecimal interesDiasPagar = (INTERES_VENTA.divide(diasAnio, 8, RoundingMode.HALF_UP).multiply(totalDiasPagar)).add(new BigDecimal(1));
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
        if (data.getIdTipoVentaFk() != null &&  data.getIdTipoVentaFk().intValue() != 1) {

            BigDecimal numPagos = data.getNumeroPagos() == null ? new BigDecimal("1") : data.getNumeroPagos();
            BigDecimal totalConInteres = new BigDecimal("0");
            BigDecimal totalDiasPagar = numPagos.multiply(data.getTipoPago());
            BigDecimal interesDiasPagar = INTERES_VENTA.divide(diasAnio, 8, RoundingMode.HALF_UP).multiply(totalDiasPagar);
            totalConInteres = precioProducto.multiply(interesDiasPagar.add(new BigDecimal("1")));
            totalConInteres = totalConInteres.setScale(2, RoundingMode.HALF_UP);
            return totalConInteres;
        } else {
            return precioProducto;
        }

    }

    public void validaCreditoCliente() {
        System.out.println("cliente.getLimiteCredito() " + cliente.getLimiteCredito());
        BigDecimal tipoPago = new BigDecimal("1");
        if (cliente.getLimiteCredito() == null) {
            JsfUtil.addWarnMessage("El Cliente no tiene Crédito.");
            data.setTipoPago(tipoPago);
        } else {
            JsfUtil.addSuccessMessage("El credito del cliente es de :" + cliente.getLimiteCredito());
        }
    }


    public void validaPrecioMinimoMaximo(AjaxBehaviorEvent event) {
        InputText input = (InputText) event.getSource();
        System.out.println("validar");
        if (input.getSubmittedValue() != null && !input.getSubmittedValue().toString().isEmpty()) {
            BigDecimal value = new BigDecimal(input.getSubmittedValue().toString());
            if (value.compareTo(min) == -1) {

                System.out.println("el valor min es mayor");
                input.setSubmittedValue(min);

            } else if (value.compareTo(max) == 1) {
                System.out.println("el valor value es mayor");
                input.setSubmittedValue(max);
            } else {
                input.setSubmittedValue(value);
            }

        } else {
            input.setSubmittedValue("");

        }

    }
    
    private boolean insertaCredito(Venta venta) {
        Credito credito = new Credito();
        Date fechaActual = context.getFechaSistema();
        Date fechaPromesaPago = TiempoUtil.sumarRestarDias(fechaActual, (data.getTipoPago().multiply(data.getNumeroPagos()).intValue()));
        
        credito.setIdClienteFk(venta.getIdClienteFk());
        //folio de la venta de menudeo (es el folio general no el de sucursal)
        credito.setIdVentaMenudeo(venta.getIdVentaPk());
        //Uusuario que realiza la venta
        credito.setIdUsuarioCredito(venta.getIdVendedorFk());
        //tipo de pago semanal, mensual etc
        credito.setIdTipoCreditoFk(data.getTipoPago());
        //estatus de credito en activo =1
        credito.setEstatusCredito(new BigDecimal("1"));
        //Numero de veces que el cliente a prometido pagar, al realizar la venta seria la primera promesa
        credito.setNumeroPromesaPago(new BigDecimal("1"));
        //el dia de la compra a credito
        credito.setFechaInicioCredito(fechaActual);
        // Fecha en la que el cliente promete liquidar los pagos del credito
        credito.setFechaPromesaPago(fechaPromesaPago);
        //el procentaje de interes que se le cobrara al cliente por la compra
        credito.setTazaInteres(INTERES_VENTA);
        //Numero de pagos que el cliente debera realizar
        credito.setPlasos(data.getNumeroPagos());

        if (ifaceCredito.insert(credito) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public String getNameFilePdf() {
        return "reporte_dummy.pdf";
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

}
