package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.Documento;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceCuentasBancarias;
import com.web.chon.service.IfaceDocumentos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceOperacionesCuentas;
import com.web.chon.service.IfacePagosBancarios;
import com.web.chon.service.IfaceTipoAbono;
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
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanBuscaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired private IfaceCatCliente ifaceCatCliente;
    @Autowired private IfaceCredito ifaceCredito;
    @Autowired private IfaceTipoAbono ifaceTipoAbono;
    @Autowired private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired private IfaceDocumentos ifaceDocumentos;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceCaja ifaceCaja;
    @Autowired private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired private IfaceOperacionesCuentas ifaceOperacionesCuentas;
    @Autowired private IfaceCuentasBancarias ifaceCuentasBancarias;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfacePagosBancarios ifacePagosBancarios;
    
    private Logger logger = LoggerFactory.getLogger(BeanBuscaCredito.class);

    private BigDecimal idCliente;
    private String nombreCompletoCliente;
    private String rfcCliente;
    private BigDecimal creditoAutorizado;
    private BigDecimal creditoUtilizado;
    private BigDecimal creditoDisponible;
    private String tipoCliente;
    private String title;
    private String viewEstate;
    private BigDecimal totalCreditos;
    private BigDecimal totalAbonado;

    private ArrayList<SaldosDeudas> modelo;
    private ArrayList<TipoAbono> lstTipoAbonos;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<AbonoCredito> chequesPendientes;
    private ArrayList<AbonoCredito> selectedchequesPendientes;
    private ArrayList<CuentaBancaria> listaCuentas;
    private SaldosDeudas saldosDeudasEdit;

    private SaldosDeudas dataAbonar;
    private AbonoCredito abono;
    private Cliente cliente;
    private String viewCheque;
    private Caja caja;

    private UsuarioDominio usuarioDominio;
    private boolean bandera;
    private Date fechaMasProximaPago;
    private BigDecimal minimoPago;
    private BigDecimal saldoParaLiquidar;

    //------------variables para generar el ticket----------//
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String number;
    private String rutaPDF;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";

    //------------variables para generar el ticket----------//
    //---Constantes---//
    private static final BigDecimal CREDITOFINALIZADO = new BigDecimal(2);
    private static final BigDecimal CREDITOACTIVO = new BigDecimal(1);
    private static final BigDecimal ABONOREALIZADO = new BigDecimal(1);
    private static final BigDecimal ABONOPENDIENTE = new BigDecimal(2);
    private static final BigDecimal DOCUMENTOACTIVO = new BigDecimal(1);
    private static final BigDecimal DOCUMENTOTIPOCHEQUE = new BigDecimal(1);
    private static final BigDecimal CERO = new BigDecimal(0).setScale(2, RoundingMode.CEILING);

    //----Constantes--//
    private static final BigDecimal TIPO = new BigDecimal(1);

    //--Datos para Operaciones Caja---//
    private OperacionesCaja opcaja;
    private static final BigDecimal entradaSalida = new BigDecimal(1);
    private static final BigDecimal statusOperacionRealizada = new BigDecimal(1);
    private static final BigDecimal statusOperacionPendiente = new BigDecimal(2);
    private static final BigDecimal statusOperacionRechazada = new BigDecimal(3);

    private static final BigDecimal concepto = new BigDecimal(7);
    private static final BigDecimal conceptoMontoCheques = new BigDecimal(12);

    private static final BigDecimal conceptoAbonoEfectivo = new BigDecimal(7);
    private static final BigDecimal conceptoAbonoTransferencia = new BigDecimal(12);
    private static final BigDecimal conceptoAbonoCheque = new BigDecimal(30);
    private static final BigDecimal conceptoAbonoDeposito = new BigDecimal(31);

    //--Variables para datos Bancarios---//
    private static final BigDecimal entradaCuenta = new BigDecimal(1);
    private static final BigDecimal idStatusCuenta = new BigDecimal(1);
    private static final BigDecimal idConceptoCuenta = new BigDecimal(15);
    private OperacionesCuentas opcuenta;
    private BigDecimal idCuentaDestinoBean;
    private BigDecimal comboFiltro;
    private boolean habilitaBotones;
    private boolean botonCancelar;
    private boolean botonActualizar;
    private BigDecimal filtroIdSucursalFk;
    private ArrayList<Sucursal> listaSucursales;

    private BigDecimal idSucursal;

    private PagosBancarios pagoBancario;

    @PostConstruct
    public void init() {
        pagoBancario = new PagosBancarios();
        botonCancelar = true;
        botonActualizar = true;
        caja = new Caja();
        idCuentaDestinoBean = new BigDecimal(0);
        minimoPago = new BigDecimal(0);
        bandera = false;
        fechaMasProximaPago = new Date();
        abono = new AbonoCredito();
        abono.setIdtipoAbonoFk(new BigDecimal(1));
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();

        usuarioDominio = context.getUsuarioAutenticado();
        dataAbonar = new SaldosDeudas();
        modelo = new ArrayList<SaldosDeudas>();
        chequesPendientes = new ArrayList<AbonoCredito>();
        selectedchequesPendientes = new ArrayList<AbonoCredito>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        saldoParaLiquidar = new BigDecimal(0);
        setTitle("Abono de Créditos");
        setViewEstate("init");
        setViewCheque("init");
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuarioDominio.getIdUsuario());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuarioDominio.getIdUsuario());
        opcaja.setEntradaSalida(entradaSalida);
        idSucursal = new BigDecimal(usuarioDominio.getSucId());

        opcaja.setIdSucursalFk(new BigDecimal(usuarioDominio.getSucId()));
        //-- Datos para Transferencias Bancarias o Dépositos Bancarios--//
        listaCuentas = ifaceCuentasBancarias.getCuentas();
        opcuenta = new OperacionesCuentas();
        opcuenta.setIdUserFk(usuarioDominio.getIdUsuario());
        opcuenta.setIdStatusFk(idStatusCuenta);
        opcuenta.setEntradaSalida(entradaCuenta);
        opcuenta.setIdConceptoFk(idConceptoCuenta);
        comboFiltro = new BigDecimal(1);
        habilitaBotones = true;
        idCuentaDestinoBean = new BigDecimal(1);
        filtroIdSucursalFk = new BigDecimal(usuarioDominio.getSucId());
    }

    public void cancelarAbonar() {
        abono.reset();
        for (int i = 0; i < modelo.size(); i++) {
            SaldosDeudas item = modelo.get(i);
            item.setAbonarTemporal(null);
        }
        botonCancelar = true;
        botonActualizar = true;
        habilitaBotones = false;
        abono.setIdtipoAbonoFk(new BigDecimal(1));
        addView();
        JsfUtil.addSuccessMessageClean("Se ha cancelado el abono");

    }

    public void actualizarCreditos() {
        BigDecimal totalAabonar = new BigDecimal(0);
        boolean bandera = false;
        for (SaldosDeudas fila : modelo) {
            if (fila.getAbonarTemporal() != null) {
                totalAabonar = totalAabonar.add(fila.getAbonarTemporal(), MathContext.UNLIMITED);
                if (fila.getSaldoLiquidar().setScale(2, RoundingMode.CEILING).compareTo(fila.getAbonarTemporal().setScale(2, RoundingMode.CEILING)) == -1) {
                    bandera = true;
                }
            }
        }
        if (abono.getMontoAbono().setScale(2, RoundingMode.CEILING).equals(totalAabonar.setScale(2, RoundingMode.CEILING))) {
            if (!bandera) {
                abonarCreditos();
                botonCancelar = true;
                botonActualizar = true;
                habilitaBotones = false;
            } else {
                JsfUtil.addErrorMessageClean("No puedes abonar un credito con mas dinero del que liquida");
            }
        } else {
            JsfUtil.addErrorMessageClean("La suma de los abonos no corresponde con el monto del abono total = $" + abono.getMontoAbono());
        }
        //Vertificar el monto total del abono que no sobre y que no falte ni un peso
        // verificar el combo hacia que forma se haran los abonos.

    }

    public void onRowEdit(RowEditEvent event) {
        saldosDeudasEdit = new SaldosDeudas();
        saldosDeudasEdit = (SaldosDeudas) event.getObject();

    }

    public void onRowCancel(RowEditEvent event) {

    }

    private void setParameterTicket(AbonoCredito ac, Cliente c) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        NumeroALetra numeroLetra = new NumeroALetra();
        String totalVentaStr = numeroLetra.Convertir(df.format(ac.getMontoAbono()), true);
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("labelEstatus", "Abono Pagado");
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("numeroCliente", c.getId_cliente().toString());
        paramReport.put("nombreCliente", c.getNombreCompleto());
        paramReport.put("recibimos", nf.format(ac.getMontoAbono()));
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("fechaProximoPago", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("montoliquidar", nf.format(saldoParaLiquidar.subtract(ac.getMontoAbono(), MathContext.UNLIMITED)));
        paramReport.put("montoMinimo", nf.format(ac.getMontoAbono()));
        paramReport.put("nombreAtendedor", usuarioDominio.getNombreCompleto());
        /* Parametros para ticket de Cheque*/
        switch (ac.getIdtipoAbonoFk().intValue()) {
            case 1:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                break;
            case 2:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("nreferencia", ac.getReferencia());
                paramReport.put("concepto", ac.getConcepto());
                paramReport.put("fechaTrans", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaTransferencia()));
                break;
            case 3:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("numeroCheque", ac.getNumeroCheque().toString());
                paramReport.put("banco", ac.getBanco());
                paramReport.put("fechaCobro", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaCobro()));
                break;
            case 4:
                paramReport.put("folioElectronico", ac.getFolioElectronico() == null ? "-----" : ac.getFolioElectronico().toString());
                paramReport.put("cuenta", idCuentaDestinoBean.toString());
                paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaCobro()));
                break;
            default:
                JsfUtil.addErrorMessageClean("Ocurrio un error contactar al administrador");
                break;
        }

        /* Parametros para ticket de Cheque*/
 /* Parametros para ticket de Transferencia*/

 /* Parametros para ticket de Transferencia*/
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());

    }

    public void generateReport(int folio, String nombreTipoTicket) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketAbonos" + File.separatorChar + nombreTipoTicket;
            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport);
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketPdf", folio, usuarioDominio.getSucId());

        } catch (Exception exception) {
            logger.error("Error al generar el reporte ","Error ",exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Reporte.");
        }

    }

    public void buscaCheques() {

        chequesPendientes = ifaceAbonoCredito.getByIdCredito(dataAbonar.getFolioCredito());

        setTitle("Cheques por cobrar");
        setViewEstate("cheques");
    }

    public void activaBandera() {
        bandera = true;
    }

    public void addView() {

        if (abono.getIdtipoAbonoFk().intValue() == 3) {
            setViewCheque("true");
        } else if (abono.getIdtipoAbonoFk().intValue() == 2) {
            setViewCheque("trans");
        } else if (abono.getIdtipoAbonoFk().intValue() == 4) {
            setViewCheque("deposito");
        } else {
            setViewCheque("init");
        }

    }

    public void clearlista() {
        for (int i = 0; i < modelo.size(); i++) {
            SaldosDeudas item = modelo.get(i);
            item.setAbonarTemporal(CERO);
        }
    }

    public String validarCampos() {
        String cadena = "";
        switch (abono.getIdtipoAbonoFk().intValue()) {
            case 1:

                break;
            case 2:
                
                if (idCuentaDestinoBean == null || abono.getReferencia() == null || abono.getConcepto() == null || abono.getFechaTransferencia() == null) {
                    cadena = "Faltan algunos campos en transferencia bancaria";
                }
                /*Campos Requeridos
                idCuentaBancariaFk
                numero de referencia
                concepto
                fechaTransferencia
                 */
                break;
            case 3:
                /*
                Campos Requeridos
                importe
                numero de cheque
                fecha de cobro
                librador
                banco emisor
                 */
                if (abono.getMontoAbono() == null || abono.getNumeroCheque() == null || abono.getFechaCobro() == null || abono.getLibrador() == null || abono.getBanco() == null) {
                    cadena = "Faltan algunos campos en Pago con cheques";
                }
                break;
            case 4:

                if (idCuentaDestinoBean == null || abono.getFolioElectronico() == null || abono.getFechaTransferencia() == null) {
                    cadena = "Faltan algunos datos en Deposito a Cuentas Bancarias";
                }
                break;
            default:
                break;

        }
        return cadena;
    }

    public void prepararAbono() {
        String cadena = validarCampos();
        if (verificarMaximoAbono()) {
            if (cadena.equals("")) {
                JsfUtil.addSuccessMessageClean("Abono Preparado");
                BigDecimal to = abono.getMontoAbono().setScale(2, RoundingMode.CEILING);
                clearlista();
                if (to != null) {
                    switch (comboFiltro.intValue()) {
                        case 1:

                            for (int i = 0; i < modelo.size(); i++) {
                                SaldosDeudas item = modelo.get(i);

                                BigDecimal deuda = item.getSaldoAtrasado().setScale(2, RoundingMode.CEILING);

                                if (deuda.compareTo(to) < 1) {
                                    to = to.subtract(item.getSaldoAtrasado().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    item.setAbonarTemporal(item.getSaldoAtrasado().setScale(2, RoundingMode.CEILING));
                                } else {
                                    item.setAbonarTemporal(to.setScale(2, RoundingMode.CEILING));
                                    to = to.subtract(to.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    break;

                                }

                            }
                            break;
                        case 2:
                            for (int i = 0; i < modelo.size(); i++) {
                                SaldosDeudas item = modelo.get(i);

                                BigDecimal deuda = item.getMinimoPago().setScale(2, RoundingMode.CEILING);

                                if (deuda.compareTo(to) < 1) {
                                    to = to.subtract(item.getMinimoPago().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    item.setAbonarTemporal(item.getMinimoPago().setScale(2, RoundingMode.CEILING));
                                } else {
                                    item.setAbonarTemporal(to.setScale(2, RoundingMode.CEILING));
                                    to = to.subtract(to.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    break;

                                }

                            }

                            break;
                        case 3:

                            for (int i = 0; i < modelo.size(); i++) {
                                SaldosDeudas item = modelo.get(i);
                                BigDecimal deuda = item.getSaldoLiquidar().setScale(2, RoundingMode.CEILING);

                                if (deuda.compareTo(to) < 1) {
                                    to = to.subtract(item.getSaldoLiquidar().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    item.setAbonarTemporal(item.getSaldoLiquidar().setScale(2, RoundingMode.CEILING));
                                } else {
                                    item.setAbonarTemporal(to.setScale(2, RoundingMode.CEILING));
                                    to = to.subtract(to.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
                                    break;

                                }
                            }
                            break;
                        default:
                            JsfUtil.addErrorMessageClean("Ocurrio un error, contactar al administrador");
                            break;
                    }
                    
                    //llamar a funcion llenadora de datos
                    abonarRestante(to);
                    botonCancelar = false;
                    botonActualizar = false;
                    habilitaBotones = true;
                } else {
                    JsfUtil.addErrorMessageClean("Primero debes agregar la forma de pago");
                }
            } else {
                JsfUtil.addErrorMessageClean(cadena);
            }
        } else {
            JsfUtil.addErrorMessageClean("No puedes abonar con mas dinero del saldo a liquidar, aún no no existe saldo a favor");
        }
    }

    public boolean verificarMaximoAbono() {
        if (abono.getMontoAbono().setScale(2, RoundingMode.CEILING).compareTo(saldoParaLiquidar.setScale(2, RoundingMode.CEILING)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    public void abonarRestante(BigDecimal saldoSobrante) {

        for (int i = 0; i < modelo.size(); i++) {
            SaldosDeudas item = modelo.get(i);
            BigDecimal minimoPago = CERO;
            minimoPago = item.getMinimoPago().setScale(2, RoundingMode.CEILING);
            if (minimoPago.compareTo(saldoSobrante.setScale(2, RoundingMode.CEILING)) <= 0) {
                //falta opci{on que valide el maximo a abonar.
                if (item.getAbonarTemporal().add(minimoPago, MathContext.UNLIMITED).compareTo(item.getSaldoLiquidar()) <= 0) {
                    item.setAbonarTemporal(item.getAbonarTemporal().add(minimoPago, MathContext.UNLIMITED));
                    saldoSobrante = saldoSobrante.subtract(minimoPago, MathContext.UNLIMITED);
                }
            } else if (saldoSobrante.compareTo(CERO) == 1) {
                item.setAbonarTemporal(item.getAbonarTemporal().add(saldoSobrante, MathContext.UNLIMITED));
                saldoSobrante = saldoSobrante.subtract(minimoPago, MathContext.UNLIMITED);
            }
        }
    }

    public void abonarCreditos() {
        if (abono.getIdtipoAbonoFk() != null && opcaja.getIdCajaFk() != null) {
            AbonoCredito ac = new AbonoCredito();
            
            ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
            ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());

            switch (abono.getIdtipoAbonoFk().intValue()) {
                case 1:

                    for (SaldosDeudas sd : modelo) {
                        if (sd.getAbonarTemporal() != null && sd.getAbonarTemporal().setScale(2, RoundingMode.CEILING) != CERO) {
                            ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                            ac.setIdCreditoFk(sd.getFolioCredito());
                            ac.setMontoAbono(sd.getAbonarTemporal());
                            ac.setEstatusAbono(ABONOREALIZADO);

                            if ((sd.getTotalAbonado().setScale(2, RoundingMode.CEILING)).add(ac.getMontoAbono().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED).compareTo(sd.getSaldoTotal()) == 0) {

                                if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                                    JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                                } else {
                                    JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                                }
                            } else {
                                logger.info("Aun no se libera el credito","Info");
                                
                            }
                            //insertar el abono
                            if (ifaceAbonoCredito.insert(ac) == 1) {
                                JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");

                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                            }
                        }

                    } //fin for

                    ac.setMontoAbono(abono.getMontoAbono());
                    opcaja.setIdConceptoFk(conceptoAbonoEfectivo);
                    opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                    opcaja.setMonto(ac.getMontoAbono());
                    opcaja.setIdStatusFk(statusOperacionRealizada);
                    opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());

                    if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                        JsfUtil.addSuccessMessageClean("Se ha registrado el abono correctamente");
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                    }
                    finAbonar(ac);

                    break;
                case 2:
                    for (SaldosDeudas sd : modelo) {
                        if (sd.getAbonarTemporal() != null && sd.getAbonarTemporal().setScale(2, RoundingMode.CEILING) != CERO) {
                            ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                            ac.setIdCreditoFk(sd.getFolioCredito());
                            ac.setMontoAbono(sd.getAbonarTemporal());
                            ac.setEstatusAbono(ABONOREALIZADO);
                            ac.setConcepto(abono.getConcepto());
                            ac.setReferencia(abono.getReferencia());
                            ac.setFechaTransferencia(abono.getFechaTransferencia());
                            if ((sd.getTotalAbonado().setScale(2, RoundingMode.CEILING)).add(ac.getMontoAbono().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED).compareTo(sd.getSaldoTotal()) == 0) {
                                if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                                    JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                                } else {
                                    JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                                }
                            } else {
                                logger.info("Aun no se libera el credito");
                            }
                            //insertar el abono
                            if (ifaceAbonoCredito.insert(ac) == 1) {
                                JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");

                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                            }
                        }

                    } //fin for
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    opcaja.setIdConceptoFk(conceptoAbonoTransferencia);
                    opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                    opcaja.setMonto(ac.getMontoAbono());
                    opcaja.setIdStatusFk(statusOperacionRealizada);
                    opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());

                    if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                        JsfUtil.addSuccessMessageClean("Se ha registrado el abono correctamente");
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                    }
                    ac.setMontoAbono(abono.getMontoAbono());
                    pagoBancario.setIdCajaFk(opcaja.getIdCajaFk());
                    pagoBancario.setComentarios("");
                    pagoBancario.setFechaDeposito(ac.getFechaTransferencia());
                    pagoBancario.setFechaTranferencia(ac.getFechaTransferencia());
                    pagoBancario.setFolioElectronico(ac.getFolioElectronico());
                    pagoBancario.setIdConceptoFk(opcaja.getIdConceptoFk());
                    pagoBancario.setIdCuentaFk(idCuentaDestinoBean);
                    pagoBancario.setIdStatusFk(new BigDecimal(2));
                    pagoBancario.setIdTipoFk(ac.getIdtipoAbonoFk());
                    pagoBancario.setIdTransBancariasPk(new BigDecimal(ifacePagosBancarios.getNextVal()));
                    pagoBancario.setIdUserFk(usuarioDominio.getIdUsuario());
                    pagoBancario.setMonto(ac.getMontoAbono());
                    pagoBancario.setReferencia(ac.getReferencia());
                    pagoBancario.setIdOperacionCajaFk(opcaja.getIdOperacionesCajaPk());
                    pagoBancario.setIdTipoTD(new BigDecimal(1));
                    pagoBancario.setIdLlaveFk(ac.getIdAbonoCreditoPk());
                    if (ac.getIdtipoAbonoFk().intValue() == 2 || ac.getIdtipoAbonoFk().intValue() == 4) {
                        if (ifacePagosBancarios.insertaPagoBancario(pagoBancario) != 1) {
                            JsfUtil.addErrorMessageClean("Ocurrió un error al ingresar pago bancario");
                        }
                    }
                    finAbonar(ac);
                    break;
                case 3:
                    for (SaldosDeudas sd : modelo) {
                        if (sd.getAbonarTemporal() != null && sd.getAbonarTemporal().setScale(2, RoundingMode.CEILING) != CERO) {
                            ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                            ac.setIdCreditoFk(sd.getFolioCredito());
                            ac.setMontoAbono(sd.getAbonarTemporal());
                            ac.setEstatusAbono(ABONOREALIZADO);
                            ac.setNumeroCheque(abono.getNumeroCheque());
                            ac.setLibrador(abono.getLibrador());
                            ac.setFechaCobro(abono.getFechaCobro());
                            ac.setBanco(abono.getBanco());
                            ac.setFactura(abono.getFactura());
                            if ((sd.getTotalAbonado().setScale(2, RoundingMode.CEILING)).add(ac.getMontoAbono().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED).compareTo(sd.getSaldoTotal()) == 0) {
                                if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                                    JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                                } else {
                                    JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                                }
                            }
                            //insertar el abono
                            if (ifaceAbonoCredito.insert(ac) == 1) {
                                JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");

                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                            }
                        }

                    } //fin for
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    opcaja.setIdConceptoFk(conceptoAbonoCheque);
                    opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                    opcaja.setMonto(ac.getMontoAbono());
                    opcaja.setIdStatusFk(statusOperacionRealizada);
                    opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());

                    if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                        JsfUtil.addSuccessMessageClean("Se ha registrado el abono correctamente");
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                    }
                    //--- Insertar Documento -- //
                    Documento d = new Documento();
                    d.setIdDocumentoPk(new BigDecimal(ifaceDocumentos.getNextVal()));
                    d.setIdAbonoFk(ac.getIdAbonoCreditoPk());
                    d.setFechaCobro(ac.getFechaCobro());
                    Credito c = ifaceCredito.getById(ac.getIdCreditoFk());
                    d.setIdClienteFk(c.getIdClienteFk());
                    d.setIdStatusFk(DOCUMENTOACTIVO);
                    d.setIdTipoDocumento(DOCUMENTOTIPOCHEQUE);
                    d.setMonto(ac.getMontoAbono());
                    d.setNumeroCheque(ac.getNumeroCheque());
                    d.setFactura(ac.getFactura());
                    d.setBanco(ac.getBanco());
                    d.setLibrador(ac.getLibrador());
                    d.setIdFormaCobroFk(new BigDecimal(1));
                    d.setIdTipoD(new BigDecimal(4));
                    d.setIdLlave(ac.getIdAbonoCreditoPk());

                    //--- Insertar Documento -- //
                    if (ifaceDocumentos.insertarDocumento(d) == 1) {

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el documento por cobrar");
                    }

                    finAbonar(ac);

                    break;
                case 4:
                    for (SaldosDeudas sd : modelo) {
                        if (sd.getAbonarTemporal() != null && sd.getAbonarTemporal().setScale(2, RoundingMode.CEILING) != CERO) {
                            ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                            ac.setIdCreditoFk(sd.getFolioCredito());
                            ac.setMontoAbono(sd.getAbonarTemporal());
                            ac.setFolioElectronico(abono.getFolioElectronico());
                            ac.setReferencia(abono.getFolioElectronico().toString());

                            for (CuentaBancaria cu : listaCuentas) {
                                if (idCuentaDestinoBean.intValue() == cu.getIdCuentaBancariaPk().intValue()) {
                                    ac.setBanco(cu.getNombreBanco());
                                }
                            }

                            ac.setBanco(abono.getBanco());
                            ac.setFechaTransferencia(abono.getFechaTransferencia());
                            ac.setEstatusAbono(ABONOREALIZADO);
                            if ((sd.getTotalAbonado().setScale(2, RoundingMode.CEILING)).add(ac.getMontoAbono().setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED).compareTo(sd.getSaldoTotal()) == 0) {

                                if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                                    JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                                } else {
                                    JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                                }
                            }

                            //insertar el abono
                            if (ifaceAbonoCredito.insert(ac) == 1) {
                                JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");

                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                            }
                        }

                    } //fin for
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    opcaja.setIdConceptoFk(conceptoAbonoDeposito);
                    opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                    opcaja.setMonto(ac.getMontoAbono());
                    opcaja.setIdStatusFk(statusOperacionRealizada);
                    opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());
                    if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                        JsfUtil.addSuccessMessageClean("Se ha registrado el abono correctamente");
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                    }
                    ac.setMontoAbono(abono.getMontoAbono());
                    pagoBancario.setIdCajaFk(opcaja.getIdCajaFk());
                    pagoBancario.setComentarios("");
                    pagoBancario.setFechaDeposito(ac.getFechaTransferencia());
                    pagoBancario.setFechaTranferencia(ac.getFechaTransferencia());
                    pagoBancario.setFolioElectronico(ac.getFolioElectronico());
                    pagoBancario.setIdConceptoFk(opcaja.getIdConceptoFk());
                    pagoBancario.setIdCuentaFk(idCuentaDestinoBean);
                    pagoBancario.setIdStatusFk(new BigDecimal(2));
                    pagoBancario.setIdTipoFk(ac.getIdtipoAbonoFk());
                    pagoBancario.setIdTransBancariasPk(new BigDecimal(ifacePagosBancarios.getNextVal()));
                    pagoBancario.setIdUserFk(usuarioDominio.getIdUsuario());
                    pagoBancario.setMonto(ac.getMontoAbono());
                    pagoBancario.setReferencia(ac.getReferencia());
                    pagoBancario.setIdOperacionCajaFk(opcaja.getIdOperacionesCajaPk());
                    pagoBancario.setIdTipoTD(new BigDecimal(1));
                    pagoBancario.setIdLlaveFk(ac.getIdAbonoCreditoPk());
                    if (ac.getIdtipoAbonoFk().intValue() == 2 || ac.getIdtipoAbonoFk().intValue() == 4) {
                        if (ifacePagosBancarios.insertaPagoBancario(pagoBancario) != 1) {
                            JsfUtil.addErrorMessageClean("Ocurrio un error al registrar pago bancario");
                        }
                    }
                    finAbonar(ac);
                    break;
                default:
                    JsfUtil.addErrorMessageClean("Ocurrió un error");
                    break;
            }

        } else {
            JsfUtil.addErrorMessageClean("Selecione un tipo de abono, o no tiene caja para realizar este movimiento");
        }

    }

    public void finAbonar(AbonoCredito ac) {
        setParameterTicket(ac, cliente);
        String nombreReporte = "";
        switch (ac.getIdtipoAbonoFk().intValue()) {
            case 1:
                nombreReporte = "abono.jasper";
                break;
            case 2:
                nombreReporte = "abonoTransferencia.jasper";
                break;
            case 3:
                nombreReporte = "abonoCheque.jasper";
                break;
            case 4:
                nombreReporte = "abonoDeposito.jasper";
                break;
            default:
                break;
        }
        generateReport(ac.getIdAbonoCreditoPk().intValue(), nombreReporte);
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        abono.reset();
        dataAbonar.reset();
        saldoParaLiquidar = new BigDecimal(0);
        searchByIdCliente();

    }

    public void abonar() {
        AbonoCredito ac = new AbonoCredito();
        if (abono.getIdtipoAbonoFk() != null && opcaja.getIdCajaFk() != null) {
            switch (abono.getIdtipoAbonoFk().intValue()) {
                /*
            ===============
            1.- Contado
            2.- Transferencia
            3.- Cheque
            4.- Deposito 
            5.- A cuenta
            ===============
            Estatus de los Creditos
            1.- Activo
            2.- Finalizado
            ===============
            Estatus de Abonos
            1.- Se realizo
            2.- Esta pendiente (cheques)
                 */
                case 1:
                    if (abono.getMontoAbono() == null) {
                        JsfUtil.addErrorMessageClean("ingrese un monto de abono");
                        break;
                    }
                    ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                    ac.setIdCreditoFk(dataAbonar.getFolioCredito());
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    ac.setEstatusAbono(ABONOREALIZADO);

                    /*variable temporal creada para sumar el total Abonado y el nuevo abono
                  en caso de que sea igual o mayor el temporal al saldo de la cuenta, liberar el credito.
                     */
                    BigDecimal temporal = dataAbonar.getTotalAbonado().add(abono.getMontoAbono(), MathContext.UNLIMITED);

                    if ((temporal).compareTo(dataAbonar.getSaldoTotal()) >= 0) {

                        if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                            JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                        } else {
                            JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                        }
                    }
                    if (ifaceAbonoCredito.insert(ac) == 1) {
                        JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");
                        opcaja.setIdConceptoFk(concepto);
                        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                        opcaja.setMonto(ac.getMontoAbono());
                        opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());

                        if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                            setParameterTicket(ac, cliente);
                            generateReport(ac.getIdAbonoCreditoPk().intValue(), "abono.jasper");
                            abono.reset();
                            dataAbonar.reset();
                            saldoParaLiquidar = new BigDecimal(0);
                            searchByIdCliente();
                            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                        }

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                    }

                    break;
                case 2:
                    if (abono.getMontoAbono() == null || abono.getReferencia() == null || abono.getConcepto() == null || abono.getFechaTransferencia() == null) {
                        JsfUtil.addErrorMessageClean("Ingrese el valor en todos los campos");
                        break;
                    }
                    ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                    ac.setIdCreditoFk(dataAbonar.getFolioCredito());
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    ac.setEstatusAbono(ABONOREALIZADO);
                    ac.setConcepto(abono.getConcepto());
                    ac.setReferencia(abono.getReferencia());
                    ac.setFechaTransferencia(abono.getFechaTransferencia());

                    /*variable temporal creada para sumar el total Abonado y el nuevo abono
                  en caso de que sea igual o mayor el temporal al saldo de la cuenta, liberar el credito.
                     */
                    BigDecimal temporal1 = dataAbonar.getTotalAbonado().add(abono.getMontoAbono(), MathContext.UNLIMITED);
                    if ((temporal1).compareTo(dataAbonar.getSaldoTotal()) >= 0) {

                        if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                            JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                        } else {
                            JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                        }

                    }
                    if (ifaceAbonoCredito.insert(ac) == 1) {

                        opcuenta.setIdOperacionCuenta(new BigDecimal(ifaceOperacionesCuentas.getNextVal()));
                        opcuenta.setIdStatusFk(idStatusCuenta);
                        opcuenta.setMonto(ac.getMontoAbono());
                        opcuenta.setIdCuentaFk(idCuentaDestinoBean);
                        opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + "| FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());
                        if (ifaceOperacionesCuentas.insertaOperacion(opcuenta) == 1) {
                            JsfUtil.addSuccessMessageClean("Se ha recibido el la Transferencia Correctamente");
                            setParameterTicket(ac, cliente);
                            generateReport(ac.getIdAbonoCreditoPk().intValue(), "abonoTransferencia.jasper");
                            abono.reset();
                            dataAbonar.reset();
                            saldoParaLiquidar = new BigDecimal(0);
                            searchByIdCliente();
                            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrió un error al recibir el Depósito");
                        }
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                    }

                    break;
                case 3:

                    if (abono.getMontoAbono() == null || abono.getNumeroCheque() == null || abono.getLibrador() == null || abono.getFechaCobro() == null || abono.getBanco() == null || abono.getFactura() == null) {
                        JsfUtil.addErrorMessageClean("Ingrese el valor en todos los campos");
                        break;
                    }
                    ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                    ac.setIdCreditoFk(dataAbonar.getFolioCredito());
                    ac.setMontoAbono(abono.getMontoAbono());
                    ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    ac.setEstatusAbono(ABONOREALIZADO);
                    ac.setNumeroCheque(abono.getNumeroCheque());
                    ac.setLibrador(abono.getLibrador());
                    ac.setFechaCobro(abono.getFechaCobro());
                    ac.setBanco(abono.getBanco());
                    ac.setFactura(abono.getFactura());
                    /*variable temporal creada para sumar el total Abonado y el nuevo abono
                  en caso de que sea igual o mayor el temporal al saldo de la cuenta, liberar el credito.
                     */
                    if (ifaceAbonoCredito.insert(ac) == 1) {
                        JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");
                        Documento d = new Documento();
                        d.setIdDocumentoPk(new BigDecimal(ifaceDocumentos.getNextVal()));
                        d.setIdAbonoFk(ac.getIdAbonoCreditoPk());
                        d.setFechaCobro(ac.getFechaCobro());
                        Credito c = ifaceCredito.getById(ac.getIdCreditoFk());
                        d.setIdClienteFk(c.getIdClienteFk());
                        d.setIdStatusFk(DOCUMENTOACTIVO);
                        d.setIdTipoDocumento(DOCUMENTOTIPOCHEQUE);
                        d.setMonto(ac.getMontoAbono());
                        d.setNumeroCheque(ac.getNumeroCheque());
                        d.setFactura(ac.getFactura());
                        d.setBanco(ac.getBanco());
                        d.setLibrador(ac.getLibrador());
                        d.setIdFormaCobroFk(new BigDecimal(1));
                        
                        BigDecimal temporal2 = dataAbonar.getTotalAbonado().add(abono.getMontoAbono(), MathContext.UNLIMITED);
                        if ((temporal2).compareTo(dataAbonar.getSaldoTotal()) >= 0) {

                            if (ifaceCredito.updateStatus(ac.getIdCreditoFk(), CREDITOFINALIZADO) == 1) {
                                JsfUtil.addSuccessMessage("Se ha liquidado el crédito total  con el folio: " + ac.getIdCreditoFk() + " exitosamente");
                            } else {
                                JsfUtil.addErrorMessageClean("Se ha producido un error al liquidar todo el folio de credito: " + ac.getIdCreditoFk());
                            }
                        }
                        if (ifaceDocumentos.insertarDocumento(d) == 1) {
                            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                            opcaja.setIdConceptoFk(conceptoMontoCheques);
                            opcaja.setMonto(ac.getMontoAbono());
                            opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());

                            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                                setParameterTicket(ac, cliente);
                                generateReport(ac.getIdAbonoCreditoPk().intValue(), "abonoCheque.jasper");
                                abono.reset();
                                dataAbonar.reset();
                                saldoParaLiquidar = new BigDecimal(0);
                                searchByIdCliente();
                                setViewCheque("init");

                                RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                            } else {
                                JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                            }
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar documento por cobrar");
                        }

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al intentar registrar el abono con el folio: " + ac.getIdAbonoCreditoPk());
                    }

                    break;
                case 4:
                    break;
                case 5:
                    Credito c = new Credito();

                    c.setIdCreditoPk(dataAbonar.getFolioCredito());
                    c.setStatusACuenta(new BigDecimal(1));
                    if (ifaceCredito.updateACuenta(c) == 1) {
                        JsfUtil.addSuccessMessageClean("Monto a Cuenta Registrado");
                        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
                        opcaja.setMonto(dataAbonar.getSaldoACuenta());
                        opcaja.setIdConceptoFk(concepto);
                        opcaja.setComentarios("FA: " + ac.getIdAbonoCreditoPk() + " | FC: " + ac.getIdCreditoFk() + " | C:" + cliente.getNombreCompleto());
                        if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                            ac.setIdCreditoFk(c.getIdCreditoPk());
                            ac.setMontoAbono(dataAbonar.getSaldoACuenta());
                            ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                            setParameterTicket(ac, cliente);
                            generateReport(ac.getIdCreditoFk().intValue(), "abonoCuenta.jasper");
                            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
                        }

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un problema");
                    }
                    break;
            }
            saldoParaLiquidar = new BigDecimal(0);
            abono = new AbonoCredito();
            searchByIdCliente();
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja asignada o no selecciono un tipo de abono");
        }
    }

    public void pagarCheques() {
        if (!selectedchequesPendientes.isEmpty()) {
            for (AbonoCredito abonoCheque : selectedchequesPendientes) {
                abonoCheque.setEstatusAbono(new BigDecimal(1));
                BigDecimal totalAbonado = new BigDecimal(0);
                BigDecimal totalVenta = new BigDecimal(0);
                if (ifaceAbonoCredito.update(abonoCheque) == 1) {
                    //enseguida buscar si ya se libero el credito de ese abono.
                    modelo = ifaceCredito.getCreditosActivos(cliente.getId_cliente(), null, filtroIdSucursalFk);
                    for (SaldosDeudas sd : modelo) {
                        if (sd.getFolioCredito().intValue() == abonoCheque.getIdCreditoFk().intValue()) {
                            totalVenta = sd.getSaldoTotal();
                            totalAbonado = totalAbonado.add(sd.getTotalAbonado(), MathContext.UNLIMITED);
                        }
                    }
                    if (totalAbonado.compareTo(totalVenta) >= 0) {
                        ifaceCredito.updateStatus(abonoCheque.getIdCreditoFk(), CREDITOFINALIZADO);
                        JsfUtil.addSuccessMessageClean("Se ha liquidado el crédito exitosamente");
                        // Se liquido con un cheque todo ese credito 
                    }
                    JsfUtil.addSuccessMessageClean("Cheque Cobrado Existosamente");
                    chequesPendientes = ifaceAbonoCredito.getByIdCredito(dataAbonar.getFolioCredito());
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema al cobrar los cheques");
                }
            }
            selectedchequesPendientes = new ArrayList<AbonoCredito>();
            saldoParaLiquidar = new BigDecimal(0);
            searchByIdCliente();
        } else {
            JsfUtil.addErrorMessageClean("No existen cheques activos o no se ha seleccionado ninguno");
        }

    }


    public void backView() {
        setTitle("Historial de Compras");
        setViewEstate("init");
        setViewCheque("init");
    }

    public void searchByIdCliente() {
        saldoParaLiquidar = new BigDecimal(0);
        abono.reset();
        dataAbonar.reset();

        abono.setIdtipoAbonoFk(new BigDecimal(1));
        botonActualizar = true;
        botonCancelar = true;

        cliente = ifaceCatCliente.getCreditoClienteByIdCliente(cliente.getId_cliente());
        if (cliente != null && cliente.getId_cliente() != null) {
            modelo = ifaceCredito.getCreditosActivos(cliente.getId_cliente(), null, filtroIdSucursalFk);
            for (SaldosDeudas sd : modelo) {
                saldoParaLiquidar = saldoParaLiquidar.add(sd.getSaldoLiquidar(), MathContext.UNLIMITED);
            }
            habilitaBotones = false;

        } else {
            JsfUtil.addWarnMessage("El cliente no cuenta con creditos activos");
            modelo = new ArrayList<SaldosDeudas>();
            habilitaBotones = true;

        }
        addView();

    }

    public void reporteCredito() {
        if (cliente != null) {
            setParameterTicketCredito();
            generateReport(cliente.getId_cliente().intValue(), "creditos.jasper");
            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        }else{
            JsfUtil.addErrorMessage("No se puede generar el estado de cuenta.");
        }

    }

    private void setParameterTicketCredito() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("$#,###.##");
        Date date = new Date();

        JRBeanCollectionDataSource collectionCredito = new JRBeanCollectionDataSource(modelo);

        paramReport.put("creditoDisponible", df.format(cliente.getCreditoDisponible()));
        paramReport.put("creditoUtilizado", df.format(cliente.getUtilizadoTotal()));
        paramReport.put("cliente", cliente.getNombreCompleto());
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("nombreSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("lstCredito", collectionCredito);
        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());

    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompletoCliente() {
        return nombreCompletoCliente;
    }

    public void setNombreCompletoCliente(String nombreCompletoCliente) {
        this.nombreCompletoCliente = nombreCompletoCliente;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public BigDecimal getCreditoAutorizado() {
        return creditoAutorizado;
    }

    public void setCreditoAutorizado(BigDecimal creditoAutorizado) {
        this.creditoAutorizado = creditoAutorizado;
    }

    public BigDecimal getCreditoUtilizado() {
        return creditoUtilizado;
    }

    public void setCreditoUtilizado(BigDecimal creditoUtilizado) {
        this.creditoUtilizado = creditoUtilizado;
    }

    public BigDecimal getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(BigDecimal creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
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

    public ArrayList<SaldosDeudas> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<SaldosDeudas> modelo) {
        this.modelo = modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SaldosDeudas getDataAbonar() {
        return dataAbonar;
    }

    public void setDataAbonar(SaldosDeudas dataAbonar) {
        this.dataAbonar = dataAbonar;
    }

    public BigDecimal getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(BigDecimal totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public AbonoCredito getAbono() {
        return abono;
    }

    public void setAbono(AbonoCredito abono) {
        this.abono = abono;
    }

    public String getViewCheque() {
        return viewCheque;
    }

    public void setViewCheque(String viewCheque) {
        this.viewCheque = viewCheque;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public ArrayList<AbonoCredito> getChequesPendientes() {
        return chequesPendientes;
    }

    public void setChequesPendientes(ArrayList<AbonoCredito> chequesPendientes) {
        this.chequesPendientes = chequesPendientes;
    }

    public ArrayList<AbonoCredito> getSelectedchequesPendientes() {
        return selectedchequesPendientes;
    }

    public void setSelectedchequesPendientes(ArrayList<AbonoCredito> selectedchequesPendientes) {
        this.selectedchequesPendientes = selectedchequesPendientes;
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

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public void setPathFileJasper(String pathFileJasper) {
        this.pathFileJasper = pathFileJasper;
    }

    public Date getFechaMasProximaPago() {
        return fechaMasProximaPago;
    }

    public void setFechaMasProximaPago(Date fechaMasProximaPago) {
        this.fechaMasProximaPago = fechaMasProximaPago;
    }

    public BigDecimal getMinimoPago() {
        return minimoPago;
    }

    public void setMinimoPago(BigDecimal minimoPago) {
        this.minimoPago = minimoPago;
    }

    public BigDecimal getSaldoParaLiquidar() {
        return saldoParaLiquidar;
    }

    public void setSaldoParaLiquidar(BigDecimal saldoParaLiquidar) {
        this.saldoParaLiquidar = saldoParaLiquidar;
    }

    public ArrayList<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<CuentaBancaria> listaCuentas) {
        this.listaCuentas = listaCuentas;
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

    public OperacionesCuentas getOpcuenta() {
        return opcuenta;
    }

    public void setOpcuenta(OperacionesCuentas opcuenta) {
        this.opcuenta = opcuenta;
    }

    public BigDecimal getIdCuentaDestinoBean() {
        return idCuentaDestinoBean;
    }

    public void setIdCuentaDestinoBean(BigDecimal idCuentaDestinoBean) {
        this.idCuentaDestinoBean = idCuentaDestinoBean;
    }

    public SaldosDeudas getSaldosDeudasEdit() {
        return saldosDeudasEdit;
    }

    public void setSaldosDeudasEdit(SaldosDeudas saldosDeudasEdit) {
        this.saldosDeudasEdit = saldosDeudasEdit;
    }

    public BigDecimal getComboFiltro() {
        return comboFiltro;
    }

    public void setComboFiltro(BigDecimal comboFiltro) {
        this.comboFiltro = comboFiltro;
    }

    public boolean isHabilitaBotones() {
        return habilitaBotones;
    }

    public void setHabilitaBotones(boolean habilitaBotones) {
        this.habilitaBotones = habilitaBotones;
    }

    public boolean isBotonCancelar() {
        return botonCancelar;
    }

    public void setBotonCancelar(boolean botonCancelar) {
        this.botonCancelar = botonCancelar;
    }

    public boolean isBotonActualizar() {
        return botonActualizar;
    }

    public void setBotonActualizar(boolean botonActualizar) {
        this.botonActualizar = botonActualizar;
    }

    public BigDecimal getFiltroIdSucursalFk() {
        return filtroIdSucursalFk;
    }

    public void setFiltroIdSucursalFk(BigDecimal filtroIdSucursalFk) {
        this.filtroIdSucursalFk = filtroIdSucursalFk;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

}
