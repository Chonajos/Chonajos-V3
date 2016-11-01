package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.Documento;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceCuentasBancarias;
import com.web.chon.service.IfaceDocumentos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfacePagosBancarios;
import com.web.chon.service.IfaceTipoAbono;
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
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceCuentasBancarias ifaceCuentasBancarias;
    @Autowired
    IfacePagosBancarios ifacePagosBancarios;

    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;

    private ArrayList<BuscaVenta> model;
    @Autowired
    private IfaceDocumentos ifaceDocumentos;
    private ArrayList<BuscaVenta> selectedVenta;

    private BuscaVenta data;
    private Usuario usuario;
    private UsuarioDominio usuarioDominio;
    private Caja caja;
    private OperacionesCaja opcaja;

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
    private static final BigDecimal entradaSalida = new BigDecimal(1);
    private static final BigDecimal statusOperacion = new BigDecimal(1);
    //private static final BigDecimal concepto = new BigDecimal(8);

    private static final BigDecimal conceptoMenudeoEfectivo = new BigDecimal(8);
    private static final BigDecimal conceptoMenudeoCheques = new BigDecimal(33);
    private static final BigDecimal conceptoMenudeoDeposito = new BigDecimal(32);
    private static final BigDecimal conceptoMenudeoTransferencia = new BigDecimal(34);

    private static final BigDecimal DOCUMENTOACTIVO = new BigDecimal(1);
    private static final BigDecimal DOCUMENTOTIPOCHEQUE = new BigDecimal(1);

    //-- Variables para Realizar Pago ---///
    private BigDecimal idTipoPagoFk;
    private BigDecimal folioVenta;
    private ArrayList<TipoAbono> lstTipoAbonos;
    private String viewCheque;
    private BigDecimal montoAbono;
    private BigDecimal numeroCheque;
    private String librador;
    private Date fechaCobro;
    private String banco;
    private String factura;
    private String conceptoTransferencia;
    private String referencia;
    private Date fechaTransferencia;
    private BigDecimal idCuentaDestinoFk;
    private BigDecimal recibi;
    private BigDecimal folioElectronico;
    private String cuenta;
    private BigDecimal recibido;
    private BigDecimal cambio;
    private ArrayList<CuentaBancaria> listaCuentas;

    private PagosBancarios pagoBancario;
    private BigDecimal idCuentaDestinoBean;

    @PostConstruct
    public void init() {
        pagoBancario = new PagosBancarios();
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
        statusButtonPagar = true;
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuarioPk());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        //opcaja.setIdConceptoFk(concepto);
        opcaja.setIdUserFk(usuario.getIdUsuarioPk());
        opcaja.setEntradaSalida(entradaSalida);
        opcaja.setIdStatusFk(statusOperacion);
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getIdSucursal()));
        listaCuentas = ifaceCuentasBancarias.getCuentas();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        idTipoPagoFk = new BigDecimal(1);
        setViewCheque("init");
    }

    public void addView() {

        if (idTipoPagoFk.intValue() == 3) {
            setViewCheque("true");
        } else if (idTipoPagoFk.intValue() == 2) {
            setViewCheque("trans");
        } else if (idTipoPagoFk.intValue() == 4) {
            setViewCheque("deposito");
        } else {
            setViewCheque("init");
        }

    }

    public void calculaCambio() {
        cambio = new BigDecimal(0);
        System.out.println("Cambio: " + cambio);
        System.out.println("Recibido: " + recibido);
        System.out.println("Total Venta: " + totalVenta);
        cambio = recibido.subtract(totalVenta, MathContext.UNLIMITED);
        System.out.println("Cambio: " + cambio);
    }

    public void verificarTipo() {
        switch (idTipoPagoFk.intValue()) {
            case 1:
                System.out.println("Se ejecuto cobro en efectivo");
                opcaja.setIdConceptoFk(conceptoMenudeoEfectivo);
                break;
            case 2:
                System.out.println("Se ejecuto cobro en transferencia");
                opcaja.setIdConceptoFk(conceptoMenudeoTransferencia);

                break;
            case 3:
                System.out.println("Se ejecuto cobro en cheque");
                opcaja.setIdConceptoFk(conceptoMenudeoCheques);

                break;
            case 4:
                System.out.println("Se ejecuto cobro en deposito bancario");
                opcaja.setIdConceptoFk(conceptoMenudeoDeposito);
                break;
            default:
                System.out.println("Se ejecuto cobro en efectivo");
                break;

        }

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

    private void setParameterTicket(int idVenta, int folioVenta) {

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

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta, int folioVenta) {

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

        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());

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

    public void updateVenta() {
        if (data.getStatusFK() == 2) {
            JsfUtil.addErrorMessageClean("Error la venta con el folio: " + data.getFolioSucursal() + " Ya se encuentra pagada");

        } else if (data.getIdVenta().intValue() != idVentaTemporal) {
            JsfUtil.addErrorMessageClean("Error!, No coincide el numero de venta  :" + data.getFolioSucursal() + " con la búsqueda.");

        } else if (data.getStatusFK() == 4) {
            JsfUtil.addErrorMessage("Error la venta se encuentra cancelada");
        } else if (opcaja.getIdCajaFk() != null) {
            try {
                verificarTipo();
                System.out.println("Se cambió el estatus");

                ifaceBuscaVenta.updateVenta(data.getIdVenta().intValue(), usuario.getIdUsuarioPk().intValue());
                searchById();
                JsfUtil.addSuccessMessageClean("La venta se ha pagado exitosamente");
                opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                opcaja.setMonto(totalVenta);

                if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {

                    pagoBancario.setIdCajaFk(opcaja.getIdCajaFk());
                    pagoBancario.setComentarios("");
                    pagoBancario.setFechaDeposito(fechaTransferencia);
                    pagoBancario.setFechaTranferencia(fechaTransferencia);
                    pagoBancario.setFolioElectronico(folioElectronico);
                    pagoBancario.setIdConceptoFk(idTipoPagoFk);
                    pagoBancario.setIdCuentaFk(idCuentaDestinoBean);
                    pagoBancario.setIdStatusFk(new BigDecimal(2));
                    pagoBancario.setIdTipoFk(idTipoPagoFk);
                    pagoBancario.setIdTransBancariasPk(new BigDecimal(ifacePagosBancarios.getNextVal()));
                    pagoBancario.setIdUserFk(usuario.getIdUsuarioPk());
                    pagoBancario.setMonto(totalVenta);
                    pagoBancario.setReferencia(referencia);
                    pagoBancario.setIdOperacionCajaFk(opcaja.getIdOperacionesCajaPk());
                    if (idTipoPagoFk.intValue() == 2 || idTipoPagoFk.intValue() == 4) {
                        if (ifacePagosBancarios.insertaPagoBancario(pagoBancario) == 1) {
                            System.out.println("Se ingreso correctamente un deposito bancario");
                        } else {
                            System.out.println("Ocurrio un error");
                        }
                    }
                    if (idTipoPagoFk.intValue() == 3) {
                        Documento d = new Documento();
                        d.setIdDocumentoPk(new BigDecimal(ifaceDocumentos.getNextVal()));
                        //d.setIdAbonoFk(ac.getIdAbonoCreditoPk());
                        d.setFechaCobro(fechaCobro);
                        //Credito c = ifaceCredito.getById(ac.getIdCreditoFk());
                        d.setIdClienteFk(model.get(0).getIdClienteFk());
                        d.setIdStatusFk(DOCUMENTOACTIVO);
                        d.setIdTipoDocumento(DOCUMENTOTIPOCHEQUE);
                        d.setMonto(totalVenta);
                        d.setNumeroCheque(numeroCheque);
                        d.setFactura(factura);
                        d.setBanco(banco);
                        d.setLibrador(librador);
                        d.setIdFormaCobroFk(new BigDecimal(1));
                        System.out.println("Documento: " + d.toString());
                        //--- Insertar Documento -- //
                        if (ifaceDocumentos.insertarDocumento(d) == 1) {
                            System.out.println("Se ingreso corractamente el documento por cobrar");

                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el documento por cobrar");
                        }
                    }

                    setParameterTicket(data.getIdVenta().intValue(), data.getFolioSucursal().intValue());
                    generateReport();
                    RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                    JsfUtil.addSuccessMessageClean("Se ha cobrado exitosamente la venta");
                    data.reset();
                    model.clear();
                    statusButtonPagar = true;

                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al agregar operacion en caja, contactar al administrador");
                }

            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar pagar la venta con el folio:" + data.getIdVenta() + "."));
            }
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no tiene asignada una caja para cobrar este folio.");
        }

        //return "buscaVentas";
    }

    @Override
    public void searchById() {
        statusButtonPagar = false;
        System.out.println("id de folio" + data.getFolioSucursal());
        System.out.println("id de sucursal" + usuario.getIdSucursal());
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
            data.setIdClienteFk(totalVenta);
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

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public OperacionesCaja getOpcaja() {
        return opcaja;
    }

    public void setOpcaja(OperacionesCaja opcaja) {
        this.opcaja = opcaja;
    }

    public BigDecimal getIdTipoPagoFk() {
        return idTipoPagoFk;
    }

    public void setIdTipoPagoFk(BigDecimal idTipoPagoFk) {
        this.idTipoPagoFk = idTipoPagoFk;
    }

    public BigDecimal getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(BigDecimal folioVenta) {
        this.folioVenta = folioVenta;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public String getViewCheque() {
        return viewCheque;
    }

    public void setViewCheque(String viewCheque) {
        this.viewCheque = viewCheque;
    }

    public BigDecimal getMontoAbono() {
        return montoAbono;
    }

    public void setMontoAbono(BigDecimal montoAbono) {
        this.montoAbono = montoAbono;
    }

    public BigDecimal getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(BigDecimal numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getLibrador() {
        return librador;
    }

    public void setLibrador(String librador) {
        this.librador = librador;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getConceptoTransferencia() {
        return conceptoTransferencia;
    }

    public void setConceptoTransferencia(String conceptoTransferencia) {
        this.conceptoTransferencia = conceptoTransferencia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(Date fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

    public BigDecimal getIdCuentaDestinoFk() {
        return idCuentaDestinoFk;
    }

    public void setIdCuentaDestinoFk(BigDecimal idCuentaDestinoFk) {
        this.idCuentaDestinoFk = idCuentaDestinoFk;
    }

    public BigDecimal getRecibi() {
        return recibi;
    }

    public void setRecibi(BigDecimal recibi) {
        this.recibi = recibi;
    }

    public BigDecimal getFolioElectronico() {
        return folioElectronico;
    }

    public void setFolioElectronico(BigDecimal folioElectronico) {
        this.folioElectronico = folioElectronico;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public ArrayList<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<CuentaBancaria> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public Map getParamReport() {
        return paramReport;
    }

    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }

    public PagosBancarios getPagoBancario() {
        return pagoBancario;
    }

    public void setPagoBancario(PagosBancarios pagoBancario) {
        this.pagoBancario = pagoBancario;
    }

    public BigDecimal getIdCuentaDestinoBean() {
        return idCuentaDestinoBean;
    }

    public void setIdCuentaDestinoBean(BigDecimal idCuentaDestinoBean) {
        this.idCuentaDestinoBean = idCuentaDestinoBean;
    }

}
