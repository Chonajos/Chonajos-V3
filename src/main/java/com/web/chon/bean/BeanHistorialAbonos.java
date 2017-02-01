/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Documento;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceDocumentos;
import com.web.chon.service.IfaceOperacionesCaja;
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

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanHistorialAbonos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceOperacionesCaja IfaceOperacionesCaja;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfacePagosBancarios ifacePagosBancarios;
    @Autowired
    private IfaceDocumentos ifaceDocumentos;

    private ArrayList<Cliente> lstCliente;
    private ArrayList<Usuario> lstUsuariosCaja;
    private ArrayList<AbonoCredito> lstAbonosCreditos;
    private AbonoCredito data;
    private ArrayList<TipoAbono> lstTipoAbonos;

    private BigDecimal idClienteBeanFk;
    private BigDecimal idCobradorFk;
    private BigDecimal idTipoAbonoFk;
    private BigDecimal idAbonoPk;
    private BigDecimal idCreditoFk;

    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;

    private String title;
    private String viewEstate;

    private Cliente cliente;
    private UsuarioDominio usuarioDominio;

    private boolean enableCalendar;
    private BigDecimal total;
    AbonoCredito abono;

   
    
    private static final BigDecimal CONCEPTO_ABONO = new BigDecimal(7);
    
    private static final BigDecimal EFECTIVO = new BigDecimal(1);
    private static final BigDecimal TRANSFERENCIA = new BigDecimal(2);
    private static final BigDecimal CHEQUE = new BigDecimal(3);
    private static final BigDecimal DEPOSITO = new BigDecimal(4);
    private static final BigDecimal OPERACIONABONOS_CREDITO = new BigDecimal(4);
    
    
    private static final BigDecimal STATUS_REALIZADA = new BigDecimal(1);
    private static final BigDecimal STATUS_PENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUS_RECHAZADA = new BigDecimal(3);
    private static final BigDecimal STATUS_CANCELADA = new BigDecimal(4);

    private static final BigDecimal SALIDA = new BigDecimal(2);

    //------------variables para generar el ticket----------//
    private ArrayList<SaldosDeudas> modelo;
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String number;
    private String rutaPDF;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";

    //------------variables para generar el ticket----------//
    //--Datos para Operaciones Caja---//
    private OperacionesCaja opcaja;
    private Caja caja;
    ArrayList<AbonoCredito> padres;

    //--Datos para Operaciones Caja---//
    @PostConstruct
    public void init() {
        padres = new ArrayList<AbonoCredito>();
        abono = new AbonoCredito();
        total = new BigDecimal(0);
        setTitle("Historial de Abonos");
        setViewEstate("init");
        lstCliente = new ArrayList<Cliente>();
        lstUsuariosCaja = new ArrayList<Usuario>();
        lstUsuariosCaja = IfaceOperacionesCaja.getResponsables(new BigDecimal(-1));
        lstAbonosCreditos = new ArrayList<AbonoCredito>();
        lstTipoAbonos = new ArrayList<TipoAbono>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        idClienteBeanFk = null;
        usuarioDominio = context.getUsuarioAutenticado();
        idCobradorFk = usuarioDominio.getIdUsuario();
        fechaFiltroInicio = context.getFechaSistema();
        fechaFiltroFin = context.getFechaSistema();
        cliente = new Cliente();
        buscar();
        //idTipoAbonoFk = null;
//        if (cliente == null) {
//            cliente = new Cliente();
//        }
//        lstAbonosCreditos = ifaceAbonoCredito.getHistorialAbonos(cliente.getId_cliente(), idCobradorFk, fechaFiltroInicio, fechaFiltroFin, idTipoAbonoFk, idAbonoPk, idCreditoFk);
//        sumaTotal();
//        sumaAbonitos();
        modelo = new ArrayList<SaldosDeudas>();
        opcaja = new OperacionesCaja();

    }

    private void setParameterTicket(AbonoCredito ac, BigDecimal saldoActual) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        NumeroALetra numeroLetra = new NumeroALetra();
        String totalVentaStr = numeroLetra.Convertir(df.format(ac.getMontoAbono()), true);
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());
        paramReport.put("labelEstatus", "Abono Pagado");
        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("numeroCliente", ac.getIdClienteFk().toString());
        paramReport.put("nombreCliente", ac.getNombreCliente());
        paramReport.put("recibimos", nf.format(ac.getMontoAbono()));
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("fechaProximoPago", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaAbono()));
        paramReport.put("montoliquidar", nf.format(saldoActual));
        //paramReport.put("montoMinimo", nf.format(saldoActual));
        paramReport.put("nombreAtendedor", ac.getNombreCajero());
        /* Parametros para ticket de Cheque*/
        switch (ac.getIdtipoAbonoFk().intValue()) {
            case 1:
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                //System.out.println("Efectivo sin Datos extra");
                break;
            case 2:
                //System.out.println("Entro a Ticket Transferencia");
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("nreferencia", ac.getReferencia());
                paramReport.put("concepto", ac.getConcepto());
                paramReport.put("fechaTrans", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaTransferencia()));
                break;
            case 3:
                //System.out.println("Entro a Cheque");
                paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                paramReport.put("numeroCheque", ac.getNumeroCheque().toString());
                paramReport.put("banco", ac.getBanco());
                paramReport.put("fechaCobro", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaCobro()));
                break;
            case 4:
                //paramReport.put("folio", ac.getIdAbonoCreditoPk().toString());
                //System.out.println("Entro a Ticket Deposito Bancario");
                paramReport.put("folioElectronico", ac.getFolioElectronico() == null ? "-----" : ac.getFolioElectronico().toString());
                //paramReport.put("cuenta", idCuentaDestinoBean.toString());
                paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(ac.getFechaCobro()));
                break;
//            case 5:
//                paramReport.put("labelEstatus", "PAGO A CUENTA");
//                paramReport.put("folio", ac.getIdCreditoFk().toString());
            //paramReport.put("montoliquidar", nf.format(saldoParaLiquidar));

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
            System.out.println("reporte de credito contado");
            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketAbonos" + File.separatorChar + nombreTipoTicket;

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "abonoPDF", folio, usuarioDominio.getSucId());
            System.out.println("Ruutttaaa: " + rutaPDF);
        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Ticket de Venta.");
        }

    }

    public void cancelarAbono() {
        //cambiar estatus de abono a cancelado = 2
        //cambiar el estatus de credito a o finalizado
        //insertar una operacion de caja de salida ( abono credito) 
        //en caso de haber documento por cobrar cambiar el estatus del documento por cobrar a cancelado

        System.out.println("Abono a Eliminar: " + abono.toString());
        //ifaceAbonoCredito.delete(abono.getIdAbonoCreditoPk());

        //ifaceCredito.activarCredito(abono.getIdCreditoFk());
        opcaja = new OperacionesCaja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuarioDominio.getIdUsuario());
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuarioDominio.getIdUsuario());
        opcaja.setEntradaSalida(SALIDA);
        opcaja.setIdSucursalFk(new BigDecimal(usuarioDominio.getSucId()));
        opcaja.setMonto(abono.getMontoAbono());
        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcaja.setIdStatusFk(STATUS_REALIZADA);
        opcaja.setIdConceptoFk(CONCEPTO_ABONO);
        opcaja.setIdTipoOperacionFk(OPERACIONABONOS_CREDITO);
        
        abono.setEstatusAbono(new BigDecimal(2));

        PagosBancarios pb = new PagosBancarios();
        switch (abono.getIdtipoAbonoFk().intValue()) {
            case 1:
                //System.out.println("Cancelar Abono en Efectivo");
                //insertar operacion en caja de salida 
                //verificar si el credito se libero. si se libero volver a activarlo
                //cancelar el abono de credito
                opcaja.setComentarios("Cancelación de Abono");
                opcaja.setIdFormaPago(EFECTIVO);
                ifaceOperacionesCaja.insertaOperacion(opcaja);
                for (AbonoCredito abonito : abono.getListaAbonitos()) {
                    abonito.setEstatusAbono(new BigDecimal(2));
                    ifaceAbonoCredito.update(abonito);
                    ifaceCredito.updateStatus(abonito.getIdCreditoFk(), new BigDecimal(1));
                }
                JsfUtil.addSuccessMessageClean("Se ha cancelado el abono con éxito");
                break;
            case 2:
                System.out.println("Cancelar Abono en Transferencia");
                System.out.println("id-" + abono.getIdAbonoCreditoPk());

                for (AbonoCredito aa : abono.getListaAbonitos()) {
                    pb = ifacePagosBancarios.getByIdTipoLlave(new BigDecimal(1), aa.getIdAbonoCreditoPk());
                    if (pb != null && pb.getIdTransBancariasPk() != null) {
                        break;
                    }
                }
                if (pb != null && pb.getIdTransBancariasPk() != null) {
                    if (pb.getIdStatusFk().intValue() == 2) {
                        System.out.println("No ha sido aceptado");
                        opcaja.setComentarios("Cancelación de Abono");
                        opcaja.setIdFormaPago(TRANSFERENCIA);
                        ifaceOperacionesCaja.insertaOperacion(opcaja);
                    }
                    pb.setIdStatusFk(new BigDecimal(1));
                    ifacePagosBancarios.updatePagoBancario(pb);
                    for (AbonoCredito abonito : abono.getListaAbonitos()) {
                        abonito.setEstatusAbono(new BigDecimal(2));
                        ifaceAbonoCredito.update(abonito);
                        ifaceCredito.updateStatus(abonito.getIdCreditoFk(), new BigDecimal(1));
                    }
                    //ifaceAbonoCredito.update(abono);
                    //ifaceCredito.updateStatus(abono.getIdCreditoFk(), new BigDecimal(1));
                    JsfUtil.addSuccessMessageClean("Se ha cancelado el abono con éxito");
                } else {
                    JsfUtil.addErrorMessageClean("Error, no se puede cancelar el abono");
                }
                /*Verificar si ya fue aceptado o no el la transferencia
                case 1: aceptada:
                No insertar operaciones en caja
                case 2: no aceptada:
                Insertar operación de salida comentario de Cancelación de Abono
                 */
                break;
            case 3:
                Documento d = new Documento();
                d = ifaceDocumentos.getDocumentoByTipoLlave(new BigDecimal(4), abono.getIdAbonoCreditoPk());

                if (d != null && d.getIdDocumentoPk() != null) {
                    if (d.getIdStatusFk().intValue() == 1) {
                        opcaja.setComentarios("Cancelación de Abono");
                        opcaja.setIdFormaPago(CHEQUE);
                        ifaceOperacionesCaja.insertaOperacion(opcaja);
                        d.setIdStatusFk(new BigDecimal(3));
                        ifaceDocumentos.updateDocumentoById(d);
                        for (AbonoCredito abonito : abono.getListaAbonitos()) {
                            abonito.setEstatusAbono(new BigDecimal(2));
                            ifaceAbonoCredito.update(abonito);
                            ifaceCredito.updateStatus(abonito.getIdCreditoFk(), new BigDecimal(1));
                        }
                        //ifaceAbonoCredito.update(abono);
                        //ifaceCredito.updateStatus(abono.getIdCreditoFk(), new BigDecimal(1));
                        JsfUtil.addSuccessMessageClean("Se ha cancelado el abono con éxito");
                    } else {
                        JsfUtil.addErrorMessageClean("Error 2, no se puede cancelar el abono");
                    }

                } else {
                    JsfUtil.addErrorMessageClean("Error 1, no se puede cancelar el abono");

                }

                break;
            case 4:
                System.out.println("Cancelar Abono en Depósito");
                for (AbonoCredito aa : abono.getListaAbonitos()) {
                    pb = ifacePagosBancarios.getByIdTipoLlave(new BigDecimal(1), aa.getIdAbonoCreditoPk());
                    if (pb != null && pb.getIdTransBancariasPk() != null) {
                        break;
                    }
                }
                if (pb != null && pb.getIdTransBancariasPk() != null) {
                    if (pb.getIdStatusFk().intValue() == 2) {
                        System.out.println("No ha sido aceptado");
                        opcaja.setComentarios("Cancelación de Abono");
                        opcaja.setIdFormaPago(DEPOSITO);
                        ifaceOperacionesCaja.insertaOperacion(opcaja);
                    }
                    pb.setIdStatusFk(new BigDecimal(1));
                    ifacePagosBancarios.updatePagoBancario(pb);
                    for (AbonoCredito abonito : abono.getListaAbonitos()) {
                        abonito.setEstatusAbono(new BigDecimal(2));
                        ifaceAbonoCredito.update(abonito);
                        ifaceCredito.updateStatus(abonito.getIdCreditoFk(), new BigDecimal(1));
                    }
                    //ifaceAbonoCredito.update(abono);
                    //ifaceCredito.updateStatus(abono.getIdCreditoFk(), new BigDecimal(1));
                    JsfUtil.addSuccessMessageClean("Se ha cancelado el abono con éxito");
                } else {
                    JsfUtil.addErrorMessageClean("Error, no se puede cancelar el abono");
                }

                break;
            default:
                break;
        }

        buscar();
    }

    public void printVenta() {
        System.out.println("DATA: " + data.toString());

        BigDecimal totalAbono = new BigDecimal(0);
        BigDecimal totalDeuda = new BigDecimal(0);
        BigDecimal saldoActual;
        //System.out.println("------" + data.getIdClienteFk());
        // System.out.println("------" + data.getIdAbonoCreditoPk());
        modelo = ifaceCredito.getCreditosActivos(data.getIdClienteFk(), data.getIdAbonoCreditoPk(), null);
        for (SaldosDeudas saldo : modelo) {
            totalAbono = totalAbono.add(saldo.getTotalAbonado(), MathContext.UNLIMITED);
            totalDeuda = totalDeuda.add(saldo.getSaldoTotal(), MathContext.UNLIMITED);
        }
        saldoActual = totalDeuda.subtract(totalAbono, MathContext.UNLIMITED);
        setParameterTicket(data, saldoActual);
        String nombreReporte = "";
        switch (data.getIdtipoAbonoFk().intValue()) {
            case 1:
                System.out.println("Efectivo");
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
                System.out.println("Ocurrio un error");
                break;
        }
        generateReport(data.getIdAbonoCreditoPk().intValue(), nombreReporte);
        RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
    }

    public void buscar() {
        lstAbonosCreditos = new ArrayList<AbonoCredito>();
        padres = new ArrayList<AbonoCredito>();
        if (cliente == null) {
            cliente = new Cliente();
        }
        lstAbonosCreditos = ifaceAbonoCredito.getHistorialAbonos(cliente.getId_cliente(), idCobradorFk, fechaFiltroInicio, fechaFiltroFin, idTipoAbonoFk, idAbonoPk, idCreditoFk);
        generaPadres();
        generaHijos();
        sumaTotal();

    }

    public void generaPadres() {
        //Generar Padres
        for (AbonoCredito ab : lstAbonosCreditos) {
            AbonoCredito abonoPadre = new AbonoCredito();
            if (padres.isEmpty()) {
                abonoPadre.setIdAbonoCreditoPk(ab.getIdAbonoCreditoPk());
                abonoPadre.setNombreCliente(ab.getNombreCliente());
                abonoPadre.setNombreCajero(ab.getNombreCajero());
                abonoPadre.setIdCreditoFk(ab.getIdCreditoFk());
                abonoPadre.setFechaAbono(ab.getFechaAbono());
                abonoPadre.setNombreAbono(ab.getNombreAbono());
                abonoPadre.setMontoAbono(ab.getMontoAbono());
                abonoPadre.setIdUsuarioFk(ab.getIdUsuarioFk());
                abonoPadre.setIdtipoAbonoFk(ab.getIdtipoAbonoFk());
                abonoPadre.setEstatusAbono(ab.getEstatusAbono());
                abonoPadre.setNumeroCheque(ab.getNumeroCheque());
                abonoPadre.setLibrador(ab.getLibrador());
                abonoPadre.setFechaCobro(ab.getFechaCobro());
                abonoPadre.setBanco(ab.getBanco());
                abonoPadre.setFactura(ab.getFactura());
                abonoPadre.setReferencia(ab.getReferencia());
                abonoPadre.setConcepto(ab.getConcepto());
                abonoPadre.setFechaTransferencia(ab.getFechaTransferencia());
                abonoPadre.setIdClienteFk(ab.getIdClienteFk());
                abonoPadre.setFolioElectronico(ab.getFolioElectronico());
                abonoPadre.setNumeroAbono(ab.getNumeroAbono());
                padres.add(abonoPadre);
            } else {
                int count = 0;
                for (AbonoCredito a : padres) {
                    abonoPadre.setIdAbonoCreditoPk(ab.getIdAbonoCreditoPk());
                    abonoPadre.setNombreCliente(ab.getNombreCliente());
                    abonoPadre.setNombreCajero(ab.getNombreCajero());
                    abonoPadre.setIdCreditoFk(ab.getIdCreditoFk());
                    abonoPadre.setFechaAbono(ab.getFechaAbono());
                    abonoPadre.setNombreAbono(ab.getNombreAbono());
                    abonoPadre.setMontoAbono(ab.getMontoAbono());
                    abonoPadre.setIdUsuarioFk(ab.getIdUsuarioFk());
                    abonoPadre.setIdtipoAbonoFk(ab.getIdtipoAbonoFk());
                    abonoPadre.setEstatusAbono(ab.getEstatusAbono());
                    abonoPadre.setNumeroCheque(ab.getNumeroCheque());
                    abonoPadre.setLibrador(ab.getLibrador());
                    abonoPadre.setFechaCobro(ab.getFechaCobro());
                    abonoPadre.setBanco(ab.getBanco());
                    abonoPadre.setFactura(ab.getFactura());
                    abonoPadre.setReferencia(ab.getReferencia());
                    abonoPadre.setConcepto(ab.getConcepto());
                    abonoPadre.setFechaTransferencia(ab.getFechaTransferencia());
                    abonoPadre.setIdClienteFk(ab.getIdClienteFk());
                    abonoPadre.setFolioElectronico(ab.getFolioElectronico());
                    abonoPadre.setNumeroAbono(ab.getNumeroAbono());
                    if (a.getNumeroAbono().intValue() == ab.getNumeroAbono().intValue()) {
                        count++;
                    }
                }//fin for
                if (count == 0) {
                    padres.add(abonoPadre);
                }

            }//fin else
        }//fin for

    }

    public void generaHijos() {
        //Generar Hijos
        for (AbonoCredito pa : padres) {
            ArrayList<AbonoCredito> hijos = new ArrayList<AbonoCredito>();

            BigDecimal monto = new BigDecimal(0);
            for (int i = 0; i < lstAbonosCreditos.size(); i++) {
                AbonoCredito abono = new AbonoCredito();
                abono = lstAbonosCreditos.get(i);
                if (pa.getNumeroAbono().intValue() == abono.getNumeroAbono().intValue()) {
                    hijos.add(abono);
                    monto = monto.add(abono.getMontoAbono(), MathContext.UNLIMITED);
                }

            }
            pa.setMontoAbono(monto);
            pa.setListaAbonitos(hijos);
        }
        lstAbonosCreditos = padres;
    }

    public void sumaTotal() {
        total = new BigDecimal(0);
        for (AbonoCredito abono : lstAbonosCreditos) {
            total = total.add(abono.getMontoAbono(), MathContext.UNLIMITED);
        }
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public BigDecimal getIdClienteBeanFk() {
        return idClienteBeanFk;
    }

    public void setIdClienteBeanFk(BigDecimal idClienteBeanFk) {
        this.idClienteBeanFk = idClienteBeanFk;
    }

    public BigDecimal getIdCobradorFk() {
        return idCobradorFk;
    }

    public void setIdCobradorFk(BigDecimal idCobradorFk) {
        this.idCobradorFk = idCobradorFk;
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

    public ArrayList<Usuario> getLstUsuariosCaja() {
        return lstUsuariosCaja;
    }

    public void setLstUsuariosCaja(ArrayList<Usuario> lstUsuariosCaja) {
        this.lstUsuariosCaja = lstUsuariosCaja;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public BigDecimal getIdTipoAbonoFk() {
        return idTipoAbonoFk;
    }

    public void setIdTipoAbonoFk(BigDecimal idTipoAbonoFk) {
        this.idTipoAbonoFk = idTipoAbonoFk;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public ArrayList<AbonoCredito> getLstAbonosCreditos() {
        return lstAbonosCreditos;
    }

    public void setLstAbonosCreditos(ArrayList<AbonoCredito> lstAbonosCreditos) {
        this.lstAbonosCreditos = lstAbonosCreditos;
    }

    public BigDecimal getIdAbonoPk() {
        return idAbonoPk;
    }

    public void setIdAbonoPk(BigDecimal idAbonoPk) {
        this.idAbonoPk = idAbonoPk;
    }

    public BigDecimal getIdCreditoFk() {
        return idCreditoFk;
    }

    public void setIdCreditoFk(BigDecimal idCreditoFk) {
        this.idCreditoFk = idCreditoFk;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public AbonoCredito getAbono() {
        return abono;
    }

    public void setAbono(AbonoCredito abono) {
        this.abono = abono;
    }

    public AbonoCredito getData() {
        return data;
    }

    public void setData(AbonoCredito data) {
        this.data = data;
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

}
