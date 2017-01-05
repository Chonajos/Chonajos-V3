package com.web.chon.bean;

import com.web.chon.dominio.Apartado;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.Documento;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceApartado;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceCuentasBancarias;
import com.web.chon.service.IfaceDocumentos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfacePagosBancarios;
import com.web.chon.service.IfaceTipoAbono;
import com.web.chon.service.IfaceVentaMayoreo;
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
public class BeanBuscaVentaMayoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private IfaceCuentasBancarias ifaceCuentasBancarias;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceDocumentos ifaceDocumentos;
    @Autowired
    private IfacePagosBancarios ifacePagosBancarios;
    @Autowired
    private IfaceApartado ifaceApartado;

    private ArrayList<CuentaBancaria> listaCuentas;

    private VentaMayoreo ventaMayoreo;
    private ArrayList<VentaProductoMayoreo> listaProductos;

    private Usuario usuario;
    private Caja caja;
    private OperacionesCaja opcaja;

    private String title;
    private String viewEstate;

    private Map paramReport = new HashMap();
    private boolean statusButtonPagar;
    private int idVentaTemporal; //utilizado para comprobacion de venta
    private String rutaPDF;
    private String number;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper";
    private ByteArrayOutputStream outputStream;
    private StreamedContent media;
    private UsuarioDominio usuarioDominio;
    private BigDecimal recibido;
    private BigDecimal cambio;

    //CONSTANTES PARA PAGAR VENTA MAYOREO
    private static final BigDecimal ENTRADASALIDA = new BigDecimal(1);
    private static final BigDecimal STATUSOPERACIONREALIZADA = new BigDecimal(1);
    private static final BigDecimal STATUSOPERACIONPENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUSOPERACIONRECHAZADA = new BigDecimal(3);

    //private static final BigDecimal concepto = new BigDecimal(9);
    private static final BigDecimal CONCEPTOMAYOREOEFECTIVO = new BigDecimal(9);
    private static final BigDecimal CONCEPTOMAYOREOCHEQUES = new BigDecimal(27);
    private static final BigDecimal CONCEPTOMAYOREODEPOSITO = new BigDecimal(28);
    private static final BigDecimal CONCEPTOMAYOREOTRANSFERENCIA = new BigDecimal(29);

    //--- Datos para Cheque ---//
    private static final BigDecimal DOCUMENTOACTIVO = new BigDecimal(1);
    private static final BigDecimal DOCUMENTOTIPOCHEQUE = new BigDecimal(1);

    //CONSTANTES PARA PAGAR VENTA MENUDEO
    private static final BigDecimal CONCEPTOMENUDEOEFECTIVO = new BigDecimal(8);
    private static final BigDecimal CONCEPTOMENUDEOCHEQUES = new BigDecimal(33);
    private static final BigDecimal CONCEPTOMENUDEODEPOSITO = new BigDecimal(32);
    private static final BigDecimal CONCEPTOMENUDEOTRANSFERENCIA = new BigDecimal(34);

    //-------------- Variables para Registrar Pago ----------//
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

    //Es true si es una venta de menudeo y false si es una venta de mayoreo
    private boolean ventaMenudeo;
    private PagosBancarios pagoBancario;
    private BigDecimal idCuentaDestinoBean;
    private boolean value1;
    private boolean permisionApartado;

    private Apartado apartado;
    private BigDecimal montoApartado;
    private BigDecimal montoTotal;
    private boolean mensajeApartado;

    //-------------- Variables para Registrar Pago ----------//
    @PostConstruct
    public void init() {
        permisionApartado = true;
        mensajeApartado = false;
        value1 = false;
        apartado = new Apartado();
        //cambio = new BigDecimal(0);
        //recibido = new BigDecimal(0);
        idTipoPagoFk = new BigDecimal(1);
        FacesContext contexts = FacesContext.getCurrentInstance();
        String folio = contexts.getExternalContext().getRequestParameterMap().get("folio");
        ventaMayoreo = new VentaMayoreo();
        listaProductos = new ArrayList<VentaProductoMayoreo>();

        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(usuarioDominio.getSucId());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        listaCuentas = ifaceCuentasBancarias.getCuentas();

        setTitle("Pagar Venta de Mayoreo");
        setViewEstate("init");
        statusButtonPagar = true;
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuarioPk());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        //opcaja.setIdConceptoFk(concepto);
        opcaja.setIdUserFk(usuario.getIdUsuarioPk());
        opcaja.setEntradaSalida(ENTRADASALIDA);
        opcaja.setIdStatusFk(STATUSOPERACIONREALIZADA);
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getIdSucursal()));
        lstTipoAbonos = ifaceTipoAbono.getAll();
        pagoBancario = new PagosBancarios();
    }

    public void habilitaApartado() {
        if (value1) {
            permisionApartado = false;

        } else {
            permisionApartado = true;

        }

    }

    public void verificarTipo() {
        if (ventaMenudeo) {
            switch (idTipoPagoFk.intValue()) {
                case 1:
                    opcaja.setIdConceptoFk(CONCEPTOMENUDEOEFECTIVO);
                    break;
                case 2:
                    opcaja.setIdConceptoFk(CONCEPTOMENUDEOTRANSFERENCIA);

                    break;
                case 3:
                    opcaja.setIdConceptoFk(CONCEPTOMENUDEOCHEQUES);

                    break;
                case 4:
                    opcaja.setIdConceptoFk(CONCEPTOMENUDEODEPOSITO);
                    break;
                default:
                    break;

            }

        } else {
            switch (idTipoPagoFk.intValue()) {

                case 1:
                    opcaja.setIdConceptoFk(CONCEPTOMAYOREOEFECTIVO);
                    break;
                case 2:
                    opcaja.setIdConceptoFk(CONCEPTOMAYOREOTRANSFERENCIA);
                    break;
                case 3:
                    opcaja.setIdConceptoFk(CONCEPTOMAYOREOCHEQUES);
                    break;
                case 4:
                    opcaja.setIdConceptoFk(CONCEPTOMAYOREODEPOSITO);
                    break;
                default:
                    break;

            }

        }

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
        cambio = recibido.subtract(ventaMayoreo.getTotalVenta(), MathContext.UNLIMITED);
    }

    public void generateReport(BigDecimal idVentaMayoreoPK) {
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
            rutaPDF = UtilUpload.saveFileTemp(bytes, "ticketMayoreo", idVentaMayoreoPK.intValue(), usuarioDominio.getSucId());

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
        for (VentaProductoMayoreo producto : ventaMayoreo.getListaProductos()) {
            if (!ventaMenudeo) {
                String cantidad = producto.getCantidadEmpaque() + " - " + producto.getKilosVendidos() + "Kg.";
                productos.add(producto.getNombreProducto() + " " + producto.getNombreEmpaque() + " ->" + producto.getClave() + "(" + producto.getFolioCarro() + ")");
                productos.add("  " + cantidad + "                     " + nf.format(producto.getPrecioProducto()) + "    " + nf.format(producto.getTotalVenta()));
            } else {
                String cantidad = producto.getCantidadEmpaque() + " " + producto.getNombreEmpaque();
                productos.add(producto.getNombreProducto().toUpperCase());
                productos.add("       " + cantidad + "               " + nf.format(producto.getPrecioProducto()) + "    " + nf.format(producto.getTotalVenta()));
            }
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(ventaMayoreo.getTotalVenta()), true);

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, nf.format(ventaMayoreo.getTotalVenta()), totalVentaStr, idVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, String total, String totalVentaStr, int idVenta) {

        //System.out.println(data.getFechaVenta());
        paramReport.put("fechaVenta", dateTime);
        paramReport.put("noVenta", Integer.toString(idVenta));
        paramReport.put("cliente", ventaMayoreo.getNombreCliente());
        paramReport.put("vendedor", ventaMayoreo.getNombreVendedor());
        paramReport.put("productos", items);
        paramReport.put("ventaTotal", total);
        paramReport.put("totalLetra", totalVentaStr);
        paramReport.put("labelFecha", "Fecha de Pago:");
        paramReport.put("labelFolio", "Folio de Venta:");
        if (mensajeApartado) {
            paramReport.put("estado", "PEDIDO APARTADO");
        } else {
            paramReport.put("estado", "PEDIDO PAGADO");
        }

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

    public String validarCampos() {
        String cadena = "";
        switch (idTipoPagoFk.intValue()) {
            case 1:
                System.out.println("Pago en Efectivo sin campos requeridos");
                break;
            case 2:
                System.out.println("Pago en Transferencia Bancaria");
                if (idCuentaDestinoFk == null || referencia == null || conceptoTransferencia == null || fechaTransferencia == null) {
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
                System.out.println("Pago en Cheques");
                /*
                Campos Requeridos
                importe
                numero de cheque
                fecha de cobro
                librador
                banco emisor
                 */
                if (ventaMayoreo.getTotalVenta() == null || numeroCheque == null || fechaCobro == null || librador == null || banco == null) {
                    cadena = "Faltan algunos campos en Pago con cheques";
                }
                break;
            case 4:
                System.out.println("Pago en Deposito Bancario");
                if (idCuentaDestinoFk == null || folioElectronico == null || fechaTransferencia == null) {
                    cadena = "Faltan algunos datos en Deposito a Cuentas Bancarias";
                }
                break;
            default:
                break;

        }
        return cadena;
    }

    public void insertarPago() {
        verificarTipo();
        //System.out.println("Se cambió el estatus");
        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcaja.setMonto(ventaMayoreo.getTotalVenta());
        opcaja.setComentarios("Folio: " + ventaMayoreo.getVentaSucursal());
        if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
            if (idTipoPagoFk.intValue() == 3) {
                Documento d = new Documento();
                d.setIdDocumentoPk(new BigDecimal(ifaceDocumentos.getNextVal()));
                d.setFechaCobro(fechaCobro);
                d.setIdClienteFk(ventaMayoreo.getIdClienteFk());
                d.setIdStatusFk(DOCUMENTOACTIVO);
                d.setIdTipoDocumento(DOCUMENTOTIPOCHEQUE);
                d.setMonto(ventaMayoreo.getTotalVenta());
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
                    System.out.println("Error al ingresar ");
                    JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el documento por cobrar");
                }
            }
            System.out.println("----------------: " + opcaja.toString());
            pagoBancario.setIdCajaFk(opcaja.getIdCajaFk());
            pagoBancario.setComentarios("");
            pagoBancario.setFechaDeposito(fechaTransferencia);
            pagoBancario.setFechaTranferencia(fechaTransferencia);
            pagoBancario.setFolioElectronico(folioElectronico);
            pagoBancario.setIdConceptoFk(opcaja.getIdConceptoFk());
            pagoBancario.setIdCuentaFk(idCuentaDestinoFk);
            pagoBancario.setIdStatusFk(new BigDecimal(2));
            pagoBancario.setIdTipoFk(idTipoPagoFk);
            pagoBancario.setIdTransBancariasPk(new BigDecimal(ifacePagosBancarios.getNextVal()));
            pagoBancario.setIdUserFk(usuario.getIdUsuarioPk());
            pagoBancario.setMonto(ventaMayoreo.getTotalVenta());
            pagoBancario.setReferencia(referencia);
            pagoBancario.setIdOperacionCajaFk(opcaja.getIdOperacionesCajaPk());
            pagoBancario.setIdTipoTD(new BigDecimal(3));
            pagoBancario.setIdLlaveFk(ventaMayoreo.getIdVentaMayoreoPk());

            System.out.println("Pago Bancario Bean Busca Venta Mayoreo: " + pagoBancario.toString());
            if (idTipoPagoFk.intValue() == 2 || idTipoPagoFk.intValue() == 4) {
                if (ifacePagosBancarios.insertaPagoBancario(pagoBancario) == 1) {
                    System.out.println("Se ingreso correctamente un deposito bancario");
                } else {
                    System.out.println("Ocurrio un error");
                }
            }
            setParameterTicket(ventaMayoreo.getVentaSucursal().intValue());
            generateReport(ventaMayoreo.getIdVentaMayoreoPk());
            ventaMayoreo.reset();
//                    data.setNombreCliente("");
//                    data.setNombreVendedor("");
//                    data.setIdVenta(new BigDecimal(0));
            statusButtonPagar = true;
//                    data.reset();
//                    model = null;
//                    totalVenta = null;
            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de la venta");
        }

    }

    //PAGAR VENTA MAYOREO
    public void updateVenta() {
        int update = 0;
        if (opcaja.getIdCajaFk() != null) {
            String cadena = validarCampos();
            if (cadena.equals("")) {
                // Verificar maximo de abonos
                switch (montoTotal.compareTo(montoApartado.add(ventaMayoreo.getTotalVenta(), MathContext.UNLIMITED))) {
                    case 0:
                        System.out.println("Son iguales");
                         {
                            System.out.println("No se ejecuta el metodo de actualizacion de ventas");
                            if (ventaMenudeo) {
                                System.out.println("venta menudeo");
                                update = ifaceBuscaVenta.updateVenta(ventaMayoreo.getIdVentaMayoreoPk().intValue(), usuario.getIdUsuarioPk().intValue());
                            } else {
                                update = ifaceBuscaVenta.updateStatusVentaMayoreo(ventaMayoreo.getIdVentaMayoreoPk().intValue(), usuario.getIdUsuarioPk().intValue());
                                System.out.println("venta mayoreo");
                            }
                        }
                        insertarPago();
                        break;
                    case 1:
                        System.out.println("Monto Total es Mayor...osea que le faltan abonos");
                        apartado.setIdApartadoPk(new BigDecimal(ifaceApartado.getNextVal()));
                        apartado.setIdVentaMayoreoFk(ventaMayoreo.getIdVentaMayoreoPk());
                        apartado.setIdStatus(new BigDecimal(1));
                        apartado.setMonto(ventaMayoreo.getTotalVenta());
                        apartado.setIdCajeroFk(usuario.getIdUsuarioPk());
                        ifaceApartado.insert(apartado);
                        mensajeApartado = true;
                        insertarPago();
                        break;
                    case -1:
                        System.out.println("Abonos superan la suma de la venta total");
                        cadena = "";
                        JsfUtil.addErrorMessageClean("Abonos superan la suma de la venta total");
                        break;
                }

            } else {
                JsfUtil.addErrorMessageClean(cadena);
            }
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja asignada, no se puede realizar el cobro");
        }

    }

    @Override
    public void searchById() {
        statusButtonPagar = false;

        ventaMayoreo = ifaceVentaMayoreo.getVentaMayoreoByFolioidSucursalFk(folioVenta, new BigDecimal(usuario.getIdSucursal()));

        ventaMenudeo = false;
        //SE HACE LA BUSQUEDA A MENUDEO
        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
            ventaMayoreo = ifaceBuscaVenta.getVentaByfolioAndIdSuc(folioVenta, usuario.getIdSucursal());
            ventaMenudeo = true;
            montoApartado = ifaceApartado.montoApartado(ventaMayoreo.getIdVentaMayoreoPk(), new BigDecimal(2));
            montoTotal = ventaMayoreo.getTotalVenta();
        }

        if (ventaMayoreo == null || ventaMayoreo.getIdVentaMayoreoPk() == null) {
            JsfUtil.addErrorMessageClean("No se encontró ese folio, podría ser de otra sucursal.");
        } else if (ventaMayoreo.getIdtipoVentaFk().intValue() == 1) {
            montoApartado = ifaceApartado.montoApartado(ventaMayoreo.getIdVentaMayoreoPk(), new BigDecimal(1));
            montoTotal = ventaMayoreo.getTotalVenta();
            switch (ventaMayoreo.getIdStatusFk().intValue()) {
                case 1:
                    statusButtonPagar = false;
                    break;
                case 2:
                    statusButtonPagar = true;
                    JsfUtil.addErrorMessageClean("No puedes volver a cobrar la venta.");
                    break;
                case 3:
                    statusButtonPagar = true;
                    JsfUtil.addErrorMessageClean("No puedes cobrar una venta entregada.");
                    break;
                case 4:
                    statusButtonPagar = true;
                    JsfUtil.addErrorMessageClean("No puedes cobrar una venta cancelada.");
                    break;
                default:
                    statusButtonPagar = true;
                    JsfUtil.addErrorMessageClean("Ha ocurrido un error, contactar al administrador.");
                    break;
            }
        } else {
            JsfUtil.addErrorMessageClean("No puedes cobrar una venta de crédito, ir a la sección abonar crédito.");
            statusButtonPagar = true;
        }

//        if (model.isEmpty()) {
//            data.setNombreCliente("");
//            data.setNombreVendedor("");
//            data.setIdVenta(new BigDecimal(0));
//            data.setFolioSucursal(null);
//            statusButtonPagar = true;
//
//            JsfUtil.addWarnMessageClean("No se encontraron Registros.");
//
//        } else {
//
//            data.setNombreCliente(model.get(0).getNombreCliente());
//            data.setNombreVendedor(model.get(0).getNombreVendedor());
//            data.setStatusFK(model.get(0).getIdStatus().intValue());
//            data.setFolioSucursal(model.get(0).getFolioSucursal());
//            data.setIdVenta(model.get(0).getIdVenta());
//            data.setIdSucursalFk(model.get(0).getIdSucursalFk());
//            data.setNombreStatus(model.get(0).getNombreStatus());
//            idVentaTemporal = data.getIdVenta().intValue();
//            calculatotalVenta();
//            if (data.getIdSucursalFk().equals(new BigDecimal(usuario.getIdSucursal()))) {
//
//                statusButtonPagar = false;
//            } else 
//            {
//                JsfUtil.addWarnMessageClean("No puedes cobrar el folio de otra sucursal.");
//                statusButtonPagar = true;
//            }
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

    public Map getParamReport() {
        return paramReport;
    }

    public void setParamReport(Map paramReport) {
        this.paramReport = paramReport;
    }

    public BigDecimal getIdTipoPagoFk() {
        return idTipoPagoFk;
    }

    public void setIdTipoPagoFk(BigDecimal idTipoPagoFk) {
        this.idTipoPagoFk = idTipoPagoFk;
    }

    public VentaMayoreo getVentaMayoreo() {
        return ventaMayoreo;
    }

    public void setVentaMayoreo(VentaMayoreo ventaMayoreo) {
        this.ventaMayoreo = ventaMayoreo;
    }

    public ArrayList<VentaProductoMayoreo> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<VentaProductoMayoreo> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
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

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean isPermisionApartado() {
        return permisionApartado;
    }

    public void setPermisionApartado(boolean permisionApartado) {
        this.permisionApartado = permisionApartado;
    }

    public BigDecimal getMontoApartado() {
        return montoApartado;
    }

    public void setMontoApartado(BigDecimal montoApartado) {
        this.montoApartado = montoApartado;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

}
