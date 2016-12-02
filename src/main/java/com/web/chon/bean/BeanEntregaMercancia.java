package com.web.chon.bean;

import com.web.chon.dominio.EntregaMercancia;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntregaMercancia;
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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
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
 * @author Juan de la cruz
 */
@Component
@Scope("view")
public class BeanEntregaMercancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntregaMercancia ifaceEntregaMercancia;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;

    private ArrayList<Sucursal> lstSucursal;

    private EntregaMercancia data;
    private EntregaMercancia entregaMercancia;
    private ArrayList<EntregaMercancia> model;
//    private ArrayList<EntregaMercancia> lstEntregaMercancia;

    private String title = "";
    private String viewEstate = "";
    private String observaciones = "";
    private BigDecimal empaquesEntregar;
    private BigDecimal kilosEntrega;
    private BigDecimal totalRemanente;
    private BigDecimal totalEntregar;
    private BigDecimal totalEmpaques;

    private Date date;

    private UsuarioDominio usuarioDominio;

    private BigDecimal BIGDECIMAL_UNO = new BigDecimal(1);
    private BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
    private BigDecimal ESTATUS_ENTREGADO = new BigDecimal(3);
    private BigDecimal ESTATUS_PAGADO = new BigDecimal(2);
    private BigDecimal ESTATUS_VENDIDO = new BigDecimal(1);
    private BigDecimal TIPO_VENTA_CREDITO = new BigDecimal(2);

    private Map paramReport = new HashMap();

    private boolean permisosEntrega;
    private boolean validaEmpaques;
    private int INT_UNO = 1;

    private String pathFileJasper = "";
    private String rutaPDF;
    private ByteArrayOutputStream outputStream;

    private String codigoBarras;
    
    @PostConstruct
    public void init() {
        usuarioDominio = context.getUsuarioAutenticado();
        permisosEntrega = false;
        validaEmpaques = false;
        data = new EntregaMercancia();
        entregaMercancia = new EntregaMercancia();
        model = new ArrayList<EntregaMercancia>();
        lstSucursal = ifaceCatSucursales.getSucursales();
        date = context.getFechaSistema();

        setTitle("Entrega Mercancia");
        setViewEstate("init");

    }
    public void searchByBarCode()
    {
        System.out.println("Codigo Barras: " + codigoBarras);
        String diaArray[] = codigoBarras.split("'");
        BigDecimal carro = null;
        String producto = null;
        BigDecimal idEmpaque = null;
        BigDecimal idSucursal = null;
        
        switch (diaArray.length) 
        {
            case 1:
                String segArray[] = codigoBarras.split("-");
                idSucursal = new BigDecimal(segArray[0]);
                carro = new BigDecimal(segArray[1]);
                producto = segArray[2];
                idEmpaque = new BigDecimal(segArray[3]);
                //idTipoConvenioFk = new BigDecimal(segArray[4]);
                break;
            case 5:
                idSucursal = new BigDecimal(diaArray[0]);
                carro = new BigDecimal(diaArray[1]);
                producto = diaArray[2];
                idEmpaque = new BigDecimal(diaArray[3]);
                //idTipoConvenioFk = new BigDecimal(diaArray[4]);
                break;
            default:
                JsfUtil.addErrorMessageClean("Código de Barras Inválido");
                break;
        }
        
        for(EntregaMercancia mercancia : model)
        {
            if(mercancia.getIdCarroFk().intValue()==carro.intValue() && mercancia.getIdProducto().equals(producto) && mercancia.getIdTipoEmpaque().intValue()==idEmpaque.intValue())
            {
                JsfUtil.addSuccessMessageClean("Esté código se encuentra en la lista de entrega de mercancia");
                //System.out.println("Mercancia: "+mercancia.toString());
                //89
                
                if(empaquesEntregar==null)
                {
                    empaquesEntregar = new BigDecimal(0);
                }
                 empaquesEntregar = empaquesEntregar.add(new BigDecimal(1), MathContext.UNLIMITED);
                 System.out.println("Contando : "+empaquesEntregar);
                    //setKilosPromedio
                    //System.out.println("Paquete: "+paquete.toString());
//                    paquete.getKilosEntregados();
//                    paquete.getEmpaquesEntregados();
                
                
            }
            else
            {
                JsfUtil.addErrorMessageClean("Este código de producto no se encuentra en la lista de entrega");
            }
        }
    }

    public void print() {

        int insertEntregaMercancia = 0;
        boolean validaEntregaTotal = true;

        if (model != null && !model.isEmpty()) {
            for (EntregaMercancia dominio : model) {
                if (dominio.getEmpaquesEntregar() != null && dominio.getEmpaquesEntregar().compareTo(BIGDECIMAL_ZERO) != 0) {
                    if (dominio.getEmpaquesRemanente().compareTo(dominio.getEmpaquesEntregar().add(dominio.getEmpaquesEntregados())) == -1) {
                        JsfUtil.addErrorMessage("La cantidad de paquetes o de kilos a entregar es mayor a la de la venta.");
                        return;

                    } else {

                        if (dominio.getEmpaquesRemanente().compareTo(dominio.getEmpaquesEntregar().add(dominio.getEmpaquesEntregados())) == 0 && validaEntregaTotal) {
                            validaEntregaTotal = true;
                        } else {
                            validaEntregaTotal = false;
                        }

                        dominio.setIdUsuario(usuarioDominio.getIdUsuario());
                        insertEntregaMercancia = ifaceEntregaMercancia.insert(dominio);
                        if (insertEntregaMercancia == 1) {
                            System.out.println("Se entrego correctamente");
                            JsfUtil.addSuccessMessage("Se entrego correctamente.");
                            //ejecuta un update al formulario con el id :formContent

                            dominio.getEmpaquesEntregados().add(dominio.getEmpaquesEntregar());

                        } else {
                            System.out.println("Ocurrio un error al hacer la entrega.");
                            JsfUtil.addErrorMessage("Ocurrio un error al hacer la entrega.");
                        }

                    }
                } else if (dominio.getEmpaquesRemanente().compareTo(dominio.getEmpaquesEntregados()) != 0) {
                    System.out.println("false valida entrega total");
                    validaEntregaTotal = false;

                }

            }
        } else {
            JsfUtil.addErrorMessage("Tiene que Bucar un Folio de Venta Valido.");
            validaEntregaTotal = false;
        }

        if (validaEntregaTotal) {
            ifaceVentaMayoreo.updateEstatusVentaByFolioSucursalAndIdSucursal(data.getIdFolioVenta(), new BigDecimal(usuarioDominio.getSucId()), ESTATUS_ENTREGADO);
        }

        if (insertEntregaMercancia == 1) {

            setParameterTicket(data.getIdFolioVenta().intValue());
            generateReport(data.getIdFolioVenta().intValue());

            RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
            buscaFolioVenta();
        }

    }

    public void buscaFolioVenta() {

        BigDecimal folioVenta = data.getIdFolioVenta();
        model = ifaceEntregaMercancia.getByIdSucursalAndFolioSucursal(new BigDecimal(usuarioDominio.getSucId()), data.getIdFolioVenta());

        if (model == null || model.isEmpty()) {
            data.reset();
            data.setIdFolioVenta(folioVenta);
            JsfUtil.addWarnMessage("No se han encontrado registros.");
            permisosEntrega = false;
        } else {

            data.setNombreCliente(model.get(0).getNombreCliente());
            data.setTotalVenta(model.get(0).getTotalVenta());
            data.setTipoVenta(model.get(0).getTipoVenta());
            data.setEstatusVenta(model.get(0).getEstatusVenta());
            data.setIdEstatus(model.get(0).getIdEstatus());
            data.setIdTipoVenta(model.get(0).getIdTipoVenta());
            data.setNombreTipoVenta(model.get(0).getNombreTipoVenta());

            validaEntregaMercancia();

        }

    }

    private void validaEntregaMercancia() {

        switch (data.getIdEstatus().intValue()) {
            case 1:
                if (data.getIdTipoVenta().equals(TIPO_VENTA_CREDITO)) {
                    permisosEntrega = true;
                } else {
                    permisosEntrega = false;
                    JsfUtil.addWarnMessage("No se puede entregar el pedido , necesita pasar a caja para pagarlo.");
                }
                break;
            case 2:
                permisosEntrega = true;
                break;
            case 3:
                JsfUtil.addWarnMessage("El pedido ya fue entregado.");
                permisosEntrega = false;
                break;
            case 4:
                JsfUtil.addWarnMessage("La venta esta cancelada.");
                permisosEntrega = false;
                break;
            default:
                permisosEntrega = false;
                break;
        }

    }

    public void setKilosPromedio(AjaxBehaviorEvent event) {

        InputText input = (InputText) event.getSource();
        if (input.getSubmittedValue() != null && !input.getSubmittedValue().toString().isEmpty()) {
            BigDecimal value = new BigDecimal(input.getSubmittedValue().toString());
            BigDecimal valueTemp = entregaMercancia.getEmpaquesEntregados().add(value);
            BigDecimal kilosPromedio = entregaMercancia.getKilosRemanente().divide(entregaMercancia.getEmpaquesRemanente(), 2, RoundingMode.UP);

            if (valueTemp.compareTo(entregaMercancia.getEmpaquesRemanente()) == 1) {
                JsfUtil.addWarnMessage("No se puden entregar mas paquetes que los de la venta.");
                validaEmpaques = false;
            } else if (entregaMercancia.getEmpaquesRemanente().compareTo(value) == 0) {

                kilosEntrega = entregaMercancia.getKilosRemanente().subtract(entregaMercancia.getKilosEntregados());
                validaEmpaques = true;
            } else {
                kilosEntrega = kilosPromedio.multiply(value);
                validaEmpaques = true;
            }

        } else {
            input.setSubmittedValue("");
            kilosEntrega = BIGDECIMAL_ZERO;

        }

    }

    public void generateReport(int folioVenta) {
        JRExporter exporter = null;

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String temporal = "";
            if (servletContext.getRealPath("") == null) {
                temporal = Constantes.PATHSERVER;
            } else {
                temporal = servletContext.getRealPath("");
            }

            pathFileJasper = temporal + File.separatorChar + "resources" + File.separatorChar + "report" + File.separatorChar + "ticketEntregaMercancia" + File.separatorChar + "entregaMercancia.jasper";

            JasperPrint jp = JasperFillManager.fillReport(getPathFileJasper(), paramReport, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(paramReport, getPathFileJasper());
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            byte[] bytes = outputStream.toByteArray();

            rutaPDF = UtilUpload.saveFileTemp(bytes, "entregaMercancia"+totalRemanente, folioVenta, usuarioDominio.getSucId());

        } catch (Exception exception) {
            System.out.println("Error >" + exception.getMessage());
            JsfUtil.addErrorMessage("Error al Generar el Ticket de salida mercancia.");
        }

    }

    private void setParameterTicket(int folioVenta) {

        ArrayList<String> productos = new ArrayList<String>();
        totalRemanente = BIGDECIMAL_ZERO;
        totalEntregar = BIGDECIMAL_ZERO;
        totalEmpaques = BIGDECIMAL_ZERO;
        for (EntregaMercancia dominio : model) {

            dominio.setEmpaquesEntregar(dominio.getEmpaquesEntregar() == null ? BIGDECIMAL_ZERO : dominio.getEmpaquesEntregar());

            totalEmpaques = totalEmpaques.add(dominio.getEmpaquesRemanente());
            totalRemanente = totalRemanente.add(dominio.getEmpaquesRemanente()).subtract(dominio.getEmpaquesEntregados());
            if (dominio.getEmpaquesEntregar() != null) {
                totalEntregar = totalEntregar.add(dominio.getEmpaquesEntregar());
            }

            dominio.setEmpaquesEntregar(dominio.getEmpaquesEntregar() == null ? BIGDECIMAL_ZERO : dominio.getEmpaquesEntregar());
            productos.add(dominio.getNombreProducto().toUpperCase() + " " + dominio.getNombreEmpaque().toUpperCase());

            BigDecimal remanenteProducto = dominio.getEmpaquesRemanente().subtract(dominio.getEmpaquesEntregar());
            remanenteProducto = remanenteProducto.subtract(dominio.getEmpaquesEntregados());

            productos.add("                                         " + remanenteProducto + "                     " + dominio.getEmpaquesEntregar());

        }

        putValues(TiempoUtil.getFechaDDMMYYYYHHMM(date), productos, folioVenta);

    }

    private void putValues(String dateTime, ArrayList<String> items, int folioVenta) {

        paramReport.clear();

        paramReport.put("fechaEntrega", dateTime);
        paramReport.put("folioVenta", Integer.toString(folioVenta));
        paramReport.put("cliente", data.getNombreCliente());
        paramReport.put("usuarioEntrega", usuarioDominio.getNombreCompleto());
        paramReport.put("productos", items);
        paramReport.put("nombreTicket", "ENTREGA DE MERCANCIA");
        paramReport.put("labelFecha", "Fecha de Entrega:");
        paramReport.put("labelFolio", "Folio de Venta:");
        paramReport.put("telefonos", "Para cualquier duda o comentario estamos a sus órdenes al teléfono:" + usuarioDominio.getTelefonoSucursal());
        paramReport.put("labelSucursal", usuarioDominio.getNombreSucursal());

        paramReport.put("totalRemanente", (totalRemanente.subtract(totalEntregar)).toString());
        paramReport.put("totalSalida", totalEntregar.toString());
        paramReport.put("totalEmpaques", totalEmpaques.toString());
        paramReport.put("comentarios", observaciones);

    }

    public void agregarEntrega() {

        if (validaEmpaques) {
            entregaMercancia.setEmpaquesEntregar(empaquesEntregar);
            entregaMercancia.setKilosEntregar(kilosEntrega);
            entregaMercancia.setObservaciones(observaciones);
            RequestContext.getCurrentInstance().execute("PF('dlg').hide();");
            empaquesEntregar = null;
            kilosEntrega = null;
            observaciones = null;
        }else{
            JsfUtil.addWarnMessage("No se puden entregar.");
        }

    }

    public void setValues() {

        empaquesEntregar = entregaMercancia.getEmpaquesEntregar();
        kilosEntrega = entregaMercancia.getKilosEntregar();
        observaciones = entregaMercancia.getObservaciones();
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

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public EntregaMercancia getData() {
        return data;
    }

    public void setData(EntregaMercancia data) {
        this.data = data;
    }

    public ArrayList<EntregaMercancia> getModel() {
        return model;
    }

    public void setModel(ArrayList<EntregaMercancia> model) {
        this.model = model;
    }

//    public ArrayList<EntregaMercancia> getLstEntregaMercancia() {
//        return lstEntregaMercancia;
//    }
//
//    public void setLstEntregaMercancia(ArrayList<EntregaMercancia> lstEntregaMercancia) {
//        this.lstEntregaMercancia = lstEntregaMercancia;
//    }

    public EntregaMercancia getEntregaMercancia() {
        return entregaMercancia;
    }

    public void setEntregaMercancia(EntregaMercancia entregaMercancia) {
        this.entregaMercancia = entregaMercancia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isPermisosEntrega() {
        return permisosEntrega;
    }

    public void setPermisosEntrega(boolean permisosEntrega) {
        this.permisosEntrega = permisosEntrega;
    }

    public BigDecimal getEmpaquesEntregar() {
        return empaquesEntregar;
    }

    public void setEmpaquesEntregar(BigDecimal empaquesEntregar) {
        this.empaquesEntregar = empaquesEntregar;
    }

    public BigDecimal getKilosEntrega() {
        return kilosEntrega;
    }

    public void setKilosEntrega(BigDecimal kilosEntrega) {
        this.kilosEntrega = kilosEntrega;
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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }
    

}
