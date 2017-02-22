package com.web.chon.bean;

import com.web.chon.dominio.CarroDetalle;
import com.web.chon.dominio.CarroDetalleGeneral;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.OperacionesVentasMayoreo;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.Constantes;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.UtilUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el tablero de control de ventas
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanTableroControlVentaMeyoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;

    private ArrayList<Sucursal> lstSucursal = new ArrayList<Sucursal>();
    private ArrayList<EntradaMercancia> lstCarros = new ArrayList<EntradaMercancia>();
    private ArrayList<Provedor> lstProvedor = new ArrayList<Provedor>();
    private ArrayList<CarroDetalleGeneral> lstCarroDetalleGeneral = new ArrayList<CarroDetalleGeneral>();
    private ArrayList<CarroDetalle> lstCarroDetalle = new ArrayList<CarroDetalle>();
    private ArrayList<OperacionesVentasMayoreo> lstOperacionesVentasMayoreo = new ArrayList<OperacionesVentasMayoreo>();

    private CarroDetalleGeneral carroDetalleGeneral;

    private BigDecimal idSucursal;
    private BigDecimal idProvedor;
    private BigDecimal totalVentaGeneral;
    private BigDecimal totalComisionGeneral;
    private BigDecimal totalVentaDetalle;
    private BigDecimal totalComisionDetalle;
    private BigDecimal totalSaldoPorCobrar;
    private BigDecimal totalEmpaquesDetalle;
    private BigDecimal totalKilosDetalle;
    private BigDecimal totalEntradaEmpaquesDetalle;
    private BigDecimal totalEntradaKilosDetalle;
    private BigDecimal costoCarro;
    private BigDecimal valorCarro;
    private BigDecimal inventarioCosto;
    private BigDecimal inventarioVenta;

    private UsuarioDominio usuario;
    private String title;
    private String viewEstate;
    private String tipoReporte;
    private String strFechaFin;
    private String strFechaInicio;
    private String carroSucursal;

    private Date fechaUltimaVenta;
    private Date fechaFin;
    private Date fechaInicio;

    private BigDecimal estausCarro;

    private ByteArrayOutputStream outputStream;
    private Map paramReport = new HashMap();
    private String number;
    private String rutaPDF;
    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V3/ticket.jasper";

    private Logger logger = LoggerFactory.getLogger(BeanAbonarCredito.class);

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();
        lstSucursal = ifaceCatSucursales.getSucursales();
        idProvedor = null;
        idSucursal = new BigDecimal(usuario.getSucId());
        lstProvedor = ifaceCatProvedores.getProvedoresByIdSucursal(idSucursal);
        lstCarros = ifaceEntradaMercancia.getCarrosByIdSucursalAnsIdProvedor(idSucursal, null);
        tipoReporte = "cliente";
        carroDetalleGeneral = new CarroDetalleGeneral();

        fechaInicio = context.getFechaSistema();
        fechaFin = context.getFechaSistema();

        setTitle("Reportes de Ventas.");
        setViewEstate("init");

    }

    public void getProvedores() {
        lstProvedor = ifaceCatProvedores.getProvedoresByIdSucursal(idSucursal);
    }

    public void getCarros() {
        lstCarros = ifaceEntradaMercancia.getCarrosByIdSucursalAnsIdProvedor(idSucursal, idProvedor);
    }

    @Override
    public void searchById() {
        viewEstate = "searchById";
        setTitle("REPORTE DE VENTAS DEL PROVEDOR " + carroDetalleGeneral.getNombreProvedor().toUpperCase() + " CARRO " + carroDetalleGeneral.getCarro() + " REMISION " + carroDetalleGeneral.getIdentificador());
        lstCarroDetalle = ifaceVentaMayoreo.getDetalleVentasCarro(idSucursal, carroDetalleGeneral.getCarro());
        lstOperacionesVentasMayoreo = ifaceVentaMayoreo.getReporteVentasByCarroAndIdSucursalAndTipoVenta(carroDetalleGeneral.getCarro(), idSucursal, null);

        if (lstCarroDetalle != null && !lstCarroDetalle.isEmpty()) {
            fechaUltimaVenta = lstCarroDetalle.get(0).getFecha();
        } else {
            fechaUltimaVenta = null;
        }
        calcularTotalesDetalle();

    }

    public void buscar() {

        BigDecimal bCarroSucursal = carroSucursal == null ? null : new BigDecimal(carroSucursal);
        int diasDiferencia = 0;

        //Si el no se a selecionado un carro ni un rango de fecha se ponen el rango de fechas con el dia de hoy
        if (carroSucursal == null && (fechaInicio == null || fechaFin == null)) {
            fechaFin = context.getFechaSistema();
            fechaInicio = context.getFechaSistema();
        }

        strFechaInicio = TiempoUtil.getFechaDDMMYYYY(fechaInicio);
        strFechaFin = TiempoUtil.getFechaDDMMYYYY(fechaFin);
        if (!strFechaFin.equals("") && !strFechaInicio.equals("")) {
            diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaInicio, fechaFin);
        }

        //validamos que el ramgo de fecha no sobrepase 90 dias esto solo aplica es un filtro por todos los carros
        if (diasDiferencia > 90 && carroSucursal == null) {
            JsfUtil.addErrorMessage("No se puede realizar una busqueda con un intervalo de fechas mayor a 90 dias.");
        } else {
            lstCarroDetalleGeneral = ifaceEntradaMercancia.getReporteGeneralCarro(idSucursal, idProvedor, bCarroSucursal, strFechaInicio, strFechaFin, estausCarro);
            calcularTotalesGeneral();
        }

    }

    //Calcula el total de la venta del detalle general asi como la comision
    private void calcularTotalesGeneral() {
        totalComisionGeneral = new BigDecimal(0);
        totalVentaGeneral = new BigDecimal(0);

        for (CarroDetalleGeneral dominio : lstCarroDetalleGeneral) {

            totalComisionGeneral = totalComisionGeneral.add(dominio.getComision());
            totalVentaGeneral = totalVentaGeneral.add(dominio.getVenta());

        }

    }

    //Calcula el total de la venta del detalle general asi como la comision
    private void calcularTotalesDetalle() {
        totalComisionDetalle = new BigDecimal(0);
        totalVentaDetalle = new BigDecimal(0);
        totalSaldoPorCobrar = new BigDecimal(0);
        totalEmpaquesDetalle = new BigDecimal(0);
        totalKilosDetalle = new BigDecimal(0);
        totalEntradaEmpaquesDetalle = new BigDecimal(0);
        totalEntradaKilosDetalle = new BigDecimal(0);
        costoCarro = new BigDecimal(0);
        valorCarro = new BigDecimal(0);
        inventarioCosto = new BigDecimal(0);
        inventarioVenta = new BigDecimal(0);
        ArrayList<BigDecimal> lstExist = new ArrayList<BigDecimal>();
        for (CarroDetalle dominio : lstCarroDetalle) {
            totalComisionDetalle = totalComisionDetalle.add(dominio.getComisio().subtract(dominio.getSaldoPorCobrar()));
            totalVentaDetalle = totalVentaDetalle.add(dominio.getTotalVenta());
            totalSaldoPorCobrar = totalSaldoPorCobrar.add(dominio.getSaldoPorCobrar());

            totalEmpaquesDetalle = totalEmpaquesDetalle.add(dominio.getPaquetesVendidos());
            totalKilosDetalle = totalKilosDetalle.add(dominio.getKilosVendidos());

        }

        for (OperacionesVentasMayoreo dominio : lstOperacionesVentasMayoreo) {
            //Calcula los costos del carro
            switch (dominio.getIdTipoConvenio().intValue()) {
                case 1://Para costo
                    costoCarro = costoCarro.add(dominio.getConvenio().multiply(dominio.getKiloEntrada()));
                    break;
                default://Para comision y pacto
                    costoCarro = costoCarro.add(dominio.getKiloEntrada().multiply(dominio.getPrecioMinimo()));
                    break;
            }

            valorCarro = valorCarro.add(dominio.getKiloEntrada().multiply(dominio.getPrecioMinimo()));
            inventarioCosto = totalVentaDetalle.subtract(costoCarro);
            inventarioVenta = totalVentaDetalle.subtract(valorCarro);
            totalEntradaEmpaquesDetalle = totalEntradaEmpaquesDetalle.add(dominio.getEmpaquesEntrada());
            totalEntradaKilosDetalle = totalEntradaKilosDetalle.add(dominio.getKiloEntrada());
        }

    }

    public void back() {
        viewEstate = "init";
        setTitle("Reportes de Ventas.");
    }

    public List<String> autocompleteCarro(String carro) {
        List<String> lstReturn = new ArrayList<String>();
        for (EntradaMercancia dominio : lstCarros) {

            if (carro.trim().equals("")) {
                lstReturn.add(dominio.getIdCarroSucursal().toString());
            } else if (dominio.getIdCarroSucursal().toString().contains(carro)) {
                lstReturn.add(dominio.getIdCarroSucursal().toString());
            }

        }

        return lstReturn;
    }

    public void cerrarCarro() {

        if (ifaceEntradaMercancia.cerrarCarro(carroDetalleGeneral.getIdEntradaMercancia()) == 1) {
            JsfUtil.addSuccessMessage("Carro Cerrado Correctamente.");
            buscar();
        } else {
            JsfUtil.addErrorMessage("Error al Cerrar el Carro.");
        }

    }

    public void reporte() {
        Random random = new Random();
        //Se genera un numero aleatorio para que no traiga el mismo reporte por la cache
        int numberRandom = random.nextInt(999);
        if (tipoReporte.equals("producto")) {
            setParameterTicketCredito();
            generateReport(numberRandom, "producto.jasper");
            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
        } else {
            generateReport(numberRandom, "cliente.jasper");
        }
    }

    private void setParameterTicketCredito() {

        Date date = new Date();

        if (tipoReporte.equals("producto")) {
            JRBeanCollectionDataSource collectionProducto = new JRBeanCollectionDataSource(lstOperacionesVentasMayoreo);
            paramReport.put("lstProducto", collectionProducto);
        } else {
            JRBeanCollectionDataSource listaCliente = new JRBeanCollectionDataSource(lstCarroDetalle);
            paramReport.put("lstcliente", listaCliente);
        }

        paramReport.put("fecha", TiempoUtil.getFechaDDMMYYYYHHMM(date));
        paramReport.put("fechaRemision", TiempoUtil.getFechaDDMMYYYYHHMM(carroDetalleGeneral.getFecha()));
        paramReport.put("nombreSucursal", usuario.getNombreSucursal());
        paramReport.put("descripcionCarro", title);

        paramReport.put("leyenda", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuario.getTelefonoSucursal());

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

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "tableroControl" + File.separatorChar + nombreTipoTicket;
            JasperPrint jp = null;

            jp = JasperFillManager.fillReport(pathFileJasper, paramReport);

            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, pathFileJasper);
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "ReporteControl", folio, usuario.getSucId());

        } catch (Exception exception) {
            logger.error("Error al generar el reporte " + exception.getMessage(), "Error ", exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Reporte.");
        }

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {

        return null;
    }

    @Override
    public String update() {
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

    public IfaceVentaMayoreo getIfaceVentaMayoreo() {
        return ifaceVentaMayoreo;
    }

    public void setIfaceVentaMayoreo(IfaceVentaMayoreo ifaceVentaMayoreo) {
        this.ifaceVentaMayoreo = ifaceVentaMayoreo;
    }

    public IfaceCatSucursales getIfaceCatSucursales() {
        return ifaceCatSucursales;
    }

    public void setIfaceCatSucursales(IfaceCatSucursales ifaceCatSucursales) {
        this.ifaceCatSucursales = ifaceCatSucursales;
    }

    public IfaceEntradaMercancia getIfaceEntradaMercancia() {
        return ifaceEntradaMercancia;
    }

    public void setIfaceEntradaMercancia(IfaceEntradaMercancia ifaceEntradaMercancia) {
        this.ifaceEntradaMercancia = ifaceEntradaMercancia;
    }

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public ArrayList<EntradaMercancia> getLstCarros() {
        return lstCarros;
    }

    public void setLstCarros(ArrayList<EntradaMercancia> lstCarros) {
        this.lstCarros = lstCarros;
    }

    public ArrayList<Provedor> getLstProvedor() {
        return lstProvedor;
    }

    public void setLstProvedor(ArrayList<Provedor> lstProvedor) {
        this.lstProvedor = lstProvedor;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdProvedor() {
        return idProvedor;
    }

    public void setIdProvedor(BigDecimal idProvedor) {
        this.idProvedor = idProvedor;
    }

    public String getCarroSucursal() {
        return carroSucursal;
    }

    public void setCarroSucursal(String carroSucursal) {
        this.carroSucursal = carroSucursal;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<CarroDetalleGeneral> getLstCarroDetalleGeneral() {
        return lstCarroDetalleGeneral;
    }

    public void setLstCarroDetalleGeneral(ArrayList<CarroDetalleGeneral> lstCarroDetalleGeneral) {
        this.lstCarroDetalleGeneral = lstCarroDetalleGeneral;
    }

    public BigDecimal getTotalVentaGeneral() {
        return totalVentaGeneral;
    }

    public void setTotalVentaGeneral(BigDecimal totalVentaGeneral) {
        this.totalVentaGeneral = totalVentaGeneral;
    }

    public BigDecimal getTotalComisionGeneral() {
        return totalComisionGeneral;
    }

    public void setTotalComisionGeneral(BigDecimal totalComisionGeneral) {
        this.totalComisionGeneral = totalComisionGeneral;
    }

    public ArrayList<CarroDetalle> getLstCarroDetalle() {
        return lstCarroDetalle;
    }

    public void setLstCarroDetalle(ArrayList<CarroDetalle> lstCarroDetalle) {
        this.lstCarroDetalle = lstCarroDetalle;
    }

    public BigDecimal getTotalVentaDetalle() {
        return totalVentaDetalle;
    }

    public void setTotalVentaDetalle(BigDecimal totalVentaDetalle) {
        this.totalVentaDetalle = totalVentaDetalle;
    }

    public BigDecimal getTotalComisionDetalle() {
        return totalComisionDetalle;
    }

    public void setTotalComisionDetalle(BigDecimal totalComisionDetalle) {
        this.totalComisionDetalle = totalComisionDetalle;
    }

    public CarroDetalleGeneral getCarroDetalleGeneral() {
        return carroDetalleGeneral;
    }

    public void setCarroDetalleGeneral(CarroDetalleGeneral carroDetalleGeneral) {
        this.carroDetalleGeneral = carroDetalleGeneral;
    }

    public BigDecimal getTotalSaldoPorCobrar() {
        return totalSaldoPorCobrar;
    }

    public void setTotalSaldoPorCobrar(BigDecimal totalSaldoPorCobrar) {
        this.totalSaldoPorCobrar = totalSaldoPorCobrar;
    }

    public BigDecimal getTotalEmpaquesDetalle() {
        return totalEmpaquesDetalle;
    }

    public void setTotalEmpaquesDetalle(BigDecimal totalEmpaquesDetalle) {
        this.totalEmpaquesDetalle = totalEmpaquesDetalle;
    }

    public BigDecimal getTotalKilosDetalle() {
        return totalKilosDetalle;
    }

    public void setTotalKilosDetalle(BigDecimal totalKilosDetalle) {
        this.totalKilosDetalle = totalKilosDetalle;
    }

    public Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    public ArrayList<OperacionesVentasMayoreo> getLstOperacionesVentasMayoreo() {
        return lstOperacionesVentasMayoreo;
    }

    public void setLstOperacionesVentasMayoreo(ArrayList<OperacionesVentasMayoreo> lstOperacionesVentasMayoreo) {
        this.lstOperacionesVentasMayoreo = lstOperacionesVentasMayoreo;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getTotalEntradaEmpaquesDetalle() {
        return totalEntradaEmpaquesDetalle;
    }

    public void setTotalEntradaEmpaquesDetalle(BigDecimal totalEntradaEmpaquesDetalle) {
        this.totalEntradaEmpaquesDetalle = totalEntradaEmpaquesDetalle;
    }

    public BigDecimal getTotalEntradaKilosDetalle() {
        return totalEntradaKilosDetalle;
    }

    public void setTotalEntradaKilosDetalle(BigDecimal totalEntradaKilosDetalle) {
        this.totalEntradaKilosDetalle = totalEntradaKilosDetalle;
    }

    public BigDecimal getCostoCarro() {
        return costoCarro;
    }

    public void setCostoCarro(BigDecimal costoCarro) {
        this.costoCarro = costoCarro;
    }

    public BigDecimal getValorCarro() {
        return valorCarro;
    }

    public void setValorCarro(BigDecimal valorCarro) {
        this.valorCarro = valorCarro;
    }

    public BigDecimal getInventarioCosto() {
        return inventarioCosto;
    }

    public void setInventarioCosto(BigDecimal inventarioCosto) {
        this.inventarioCosto = inventarioCosto;
    }

    public BigDecimal getInventarioVenta() {
        return inventarioVenta;
    }

    public void setInventarioVenta(BigDecimal inventarioVenta) {
        this.inventarioVenta = inventarioVenta;
    }

    public BigDecimal getEstausCarro() {
        return estausCarro;
    }

    public void setEstausCarro(BigDecimal estausCarro) {
        this.estausCarro = estausCarro;
    }

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

}
