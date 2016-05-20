/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;

import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoVenta;
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
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
//import static jdk.management.resource.internal.SimpleResourceContext.contexts;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
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
public class BeanVentaMayoreoRapido implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

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

    private BigDecimal TotalVentaGeneral;

    //Variables para Generar el pdf
    private String rutaPDF;
    private Map paramReport = new HashMap();
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;

    private boolean permisionToWrite;
    private String ventaRapida;

    private boolean permisionVentaRapida;
    private boolean ventaRapidaCheck;



    @PostConstruct
    public void init() {
        ventaRapida = "0";
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
        TotalVentaGeneral = new BigDecimal(0);
        data = new VentaProductoMayoreo();
        setTitle("Venta Mayoreo");
        setViewEstate("viewAddProducto");
        lstTipoVenta = ifaceTipoVenta.getAll();
        lstVenta = new ArrayList<VentaProductoMayoreo>();
        permisionToWrite = true;
        totalProductoTemporal = null;

        ventaRapida = (usuario.getIdRolFk()).toString();
        System.out.println("Venta Rapida: " + ventaRapida);
        if (ventaRapida.equals("4")) {

            lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, null, null, null, null);
            ventaRapidaButton = "Rapida";
            permisionVentaRapida = false;
            ventaRapidaCheck = true;

        } else {
            ventaRapida = "2";
            permisionVentaRapida = true;
            ventaRapidaCheck = false;
        }

    }

    

    public void changeVentaRapida() {
        System.out.println("Check:" + ventaRapidaCheck);
        if (ventaRapidaCheck) {
            ventaRapidaButton = "Rapida";
        } else {
            ventaRapidaButton = "";
        }

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
        TotalVentaGeneral = new BigDecimal(0);
        JsfUtil.addWarnMessage("Venta Cancelada");

    }

    public void inserts() throws IOException 
    {
        if (lstVenta.isEmpty()) {

            JsfUtil.addErrorMessageClean("Tu pedido se encuentra vacío favor de agregar productos");
        } else if (usuario.getIdUsuarioPk() == null) {
            changeView();
            JsfUtil.addErrorMessageClean("Favor de Seleccionar un vendedor");

        } else if (cliente == null) {
            changeView();
            JsfUtil.addErrorMessageClean("Favor de Seleccionar un cliente");
        } else if (idTipoVenta == null) {
            changeView();
            JsfUtil.addErrorMessageClean("Favor de Seleccionar un tipo de venta");
        } else 
        {
            BigDecimal idVentaInsert = new BigDecimal(ifaceVentaMayoreo.getNextVal());
            ventaGeneral.setIdVentaMayoreoPk(idVentaInsert);
            ventaGeneral.setIdtipoVentaFk(idTipoVenta);
            ventaGeneral.setIdClienteFk(cliente.getId_cliente());
            ventaGeneral.setIdSucursalFk(idSucu);
            ventaGeneral.setIdVendedorFK(usuario.getIdUsuarioPk());
            int temp = ifaceVentaMayoreo.getVentaSucursal(idSucu);

            ventaGeneral.setVentaSucursal(new BigDecimal(temp + 1));

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
                        if (ifaceNegocioExistencia.updateExistenciaProducto(existencia_actualizada) != 0) {

                            JsfUtil.addSuccessMessageClean("Venta de Productos finalizada");
                        } else {
                            JsfUtil.addErrorMessageClean("Error actualizando existencia de producto: " + producto.getNombreProducto());
                        }
                    } else {
                        JsfUtil.addErrorMessage("Ocurrio un error");
                    }

                }
                setParameterTicket(idVentaInsert.intValue());
                generateReport(ventaGeneral.getVentaSucursal().intValue());
                selectedExistencia = new ExistenciaProducto();
                lstExistencias = new ArrayList<ExistenciaProducto>();
                lstVenta = new ArrayList<VentaProductoMayoreo>();
                data.reset();
                subProducto = new Subproducto();
                setViewEstate("viewAddProducto");
                idTipoVenta = null;
                totalProductoTemporal = null;
                TotalVentaGeneral = new BigDecimal(0);

                //cliente=new Cliente();
            }
             //RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        
            //FacesContext contexts =FacesContext.getCurrentInstance();
            //contexts.getExternalContext().getRequestParameterMap().put("folio", "hola");
            FacesContext.getCurrentInstance().getExternalContext().redirect("buscaVentaMayoreo.xhtml"); 
            //return "buscaVentaMayoreo?displayTargetPopUp&faces-redirect=true&includeViewParams=true&folio=folio";
           }
        
    }

    public void changeViewToAddProducto() {
        setViewEstate("viewAddProducto");
    }

    public void changeView() {
        setViewEstate("viewCarrito");
    }

    public void buscaExistencias() {
        //lstExistencias.clear();
        BigDecimal idEntrada;
        if (entradaMercancia == null) {
            //lstExistencias.clear();
            idEntrada = null;
        } else {
            idEntrada = entradaMercancia.getIdEmPK();
        }

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        if (idproductito != null) {
            lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, idproductito, null, null, null);
            if (lstExistencias.size() == 1) {
                
                selectedExistencia = new ExistenciaProducto();
                selectedExistencia=lstExistencias.get(0);
                
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
            lstExistencias.clear();

        }

    }

    public void addProductoEnd() throws IOException {
        addProducto();
        inserts();
    }

    public void addProducto() {
        if (selectedExistencia == null || data.getCantidadEmpaque().intValue() == 0 || data.getKilosVendidos().intValue() == 0) {
            JsfUtil.addErrorMessage("Seleccione un Producto de la tabla, o agrego una cantidad o peso en 0 Kg.");

        } else //            System.out.println("idSubProducto:" + selectedExistencia.getIdSubProductoFK());
        {
            if (selectedExistencia.getPrecioVenta() == null) {
                JsfUtil.addErrorMessage("No se tiene precio de venta para este producto. Contactar al administrador.");
            } else if (data.getPrecioProducto().intValue() < selectedExistencia.getPrecioMinimo().intValue() || data.getPrecioProducto().intValue() > selectedExistencia.getPrecioMaximo().intValue()) {
                JsfUtil.addErrorMessage("Precio de Venta fuera de Rango \n Precio Maximo =" + selectedExistencia.getPrecioMaximo() + " Precio minimo =" + selectedExistencia.getPrecioMinimo());
            } else if (data.getCantidadEmpaque().intValue() > selectedExistencia.getCantidadPaquetes().intValue()) {
                JsfUtil.addErrorMessage("Cantidad de Empaque insuficiente");
            } else if (data.getKilosVendidos().intValue() > selectedExistencia.getKilosTotalesProducto().intValue()) {
                JsfUtil.addErrorMessage("Cantidad de Kilos insuficiente");
            } //        else if (selectedExistencia.isEstatusBloqueo()) {
            //            JsfUtil.addErrorMessage("Producto Bloqueado, contactar al administrador");
            //        } 
            else if (lstVenta.isEmpty()) {
//                System.out.println("Lista vacia agregando producto");
                add();
                limpia();
            } else {
                for (int i = 0; i < lstVenta.size(); i++) {
                    VentaProductoMayoreo productoRepetido = lstVenta.get(i);
//                    System.out.println("Recorriendo lista Venta");
//                    System.out.println("repetido id: " + productoRepetido.getIdExistenciaFk() + " nuevo: " + selectedExistencia.getIdExistenciaProductoPk());
                    if (productoRepetido.getIdExistenciaFk().equals(selectedExistencia.getIdExistenciaProductoPk()) && productoRepetido.getPrecioProducto().equals(data.getPrecioProducto())) {
//                        System.out.println("Encontro producto repetido verificando existencias");
                        BigDecimal enlista = productoRepetido.getCantidadEmpaque();
                        BigDecimal totalexistencia = selectedExistencia.getCantidadPaquetes();
                        BigDecimal suma = enlista.add(data.getCantidadEmpaque(), MathContext.UNLIMITED);

//                        System.out.println("Total Existencia: " + totalexistencia);
//                        System.out.println("Total en Lista: " + enlista);
//                        System.out.println("Total Suma: " + suma);
//                        System.out.println("Total nuevo: " + data.getCantidadEmpaque());
                        BigDecimal kilosnelista = productoRepetido.getKilosVendidos();
                        BigDecimal kilostotalexistencia = selectedExistencia.getKilosTotalesProducto();
                        BigDecimal kilossuma = kilosnelista.add(data.getKilosVendidos(), MathContext.UNLIMITED);

                        if (suma.intValue() > totalexistencia.intValue() || kilossuma.intValue() > kilostotalexistencia.intValue()) {
                            //System.out.println("No alcanzan existencias");
                            JsfUtil.addErrorMessage("Producto repetido, no alcanzan las existencias.\n Cantidad: " + selectedExistencia.getCantidadPaquetes() + "\nKilos: " + selectedExistencia.getKilosTotalesProducto());
                        } else {
                            lstVenta.remove(i);
                            TotalVentaGeneral = TotalVentaGeneral.subtract(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);

                            productoRepetido.setCantidadEmpaque(suma);
                            productoRepetido.setKilosVendidos(kilossuma);
                            productoRepetido.setTotalVenta(productoRepetido.getPrecioProducto().multiply(productoRepetido.getKilosVendidos(), MathContext.UNLIMITED));
                            TotalVentaGeneral = TotalVentaGeneral.add(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);
                            lstVenta.add(productoRepetido);
                            limpia();
                            //System.out.println("Existencias suficientes, producto actualizado");
                            JsfUtil.addSuccessMessageClean("Producto Actualizado");

                        }
                    } else {
                        System.out.println("No encontro repetidos");
                        if (selectedExistencia.getIdExistenciaProductoPk() != null) {
                            //System.out.println("producto Agregado");
                            add();
                            limpia();
                        }

///
                    }

                }
            }
        }

    }

    public void add() {
        data.setIdExistenciaFk(selectedExistencia.getIdExistenciaProductoPk());
        data.setIdEntradaMercanciaFk(selectedExistencia.getIdEmFK());
        data.setIdTipoEmpaqueFk(selectedExistencia.getIdTipoEmpaqueFK());
        data.setIdSubProductofk(selectedExistencia.getIdSubProductoFK());
        data.setNombreEmpaque(selectedExistencia.getNombreEmpaque());
        data.setNombreProducto(selectedExistencia.getNombreProducto());
        data.setClave(selectedExistencia.getIdentificador());
        data.setTotalVenta(data.getPrecioProducto().multiply(data.getKilosVendidos(), MathContext.UNLIMITED));
        VentaProductoMayoreo productoTemporal = new VentaProductoMayoreo();
        productoTemporal.setIdEntradaMercanciaFk(data.getIdEntradaMercanciaFk());
        productoTemporal.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        productoTemporal.setIdSubProductofk(data.getIdSubProductofk());
        productoTemporal.setNombreEmpaque(data.getNombreEmpaque());
        productoTemporal.setNombreProducto(data.getNombreProducto());
        productoTemporal.setCantidadEmpaque(data.getCantidadEmpaque());
        productoTemporal.setPrecioProducto(data.getPrecioProducto());
        productoTemporal.setClave(data.getClave());
        productoTemporal.setTotalVenta(data.getTotalVenta());
        productoTemporal.setKilosVendidos(data.getKilosVendidos());
        productoTemporal.setIdExistenciaFk(data.getIdExistenciaFk());
        TotalVentaGeneral = TotalVentaGeneral.add(productoTemporal.getTotalVenta(), MathContext.UNLIMITED);
        lstVenta.add(productoTemporal);
        System.out.println("ProductoTemporal: " + productoTemporal.toString());

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

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketVenta" + File.separatorChar + "ticket.jasper";
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            byte[] bytes = outputStream.toByteArray();
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf",folio,usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());

        }

    }

    private void setParameterTicket(int idVenta) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        ArrayList<String> productos = new ArrayList<String>();
        NumeroALetra numeroLetra = new NumeroALetra();
        for (VentaProductoMayoreo venta : lstVenta) {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos.add(venta.getNombreProducto().toUpperCase());
            productos.add("       " + cantidad + "               " + nf.format(venta.getPrecioProducto()) + "    " + nf.format(venta.getTotalVenta()));
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(TotalVentaGeneral), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(TotalVentaGeneral), totalVentaStr, idVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {

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
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());

    }

    public ArrayList<TipoVenta> getLstTipoVenta() {
        return lstTipoVenta;
    }

    public void setLstTipoVenta(ArrayList<TipoVenta> lstTipoVenta) {
        this.lstTipoVenta = lstTipoVenta;
    }

    public void editProducto() {
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
        setViewEstate("viewCarrito");
        JsfUtil.addSuccessMessage("Producto Modificado Correctamente");
    }

    public void remove() {
        TotalVentaGeneral = TotalVentaGeneral.subtract(dataRemove.getTotalVenta(), MathContext.UNLIMITED);
        lstVenta.remove(dataRemove);

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
        return TotalVentaGeneral;
    }

    public void setTotalVentaGeneral(BigDecimal TotalVentaGeneral) {
        this.TotalVentaGeneral = TotalVentaGeneral;
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

    public boolean isVentaRapidaCheck() {
        return ventaRapidaCheck;
    }

    public void setVentaRapidaCheck(boolean ventaRapidaCheck) {

        this.ventaRapidaCheck = ventaRapidaCheck;
        changeVentaRapida();
    }

  

}
