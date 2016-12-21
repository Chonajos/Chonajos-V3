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
import org.primefaces.event.RowEditEvent;
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
    private EntregaMercancia entregaEliminar;
    private EntregaMercancia entregaEditar;
    private ArrayList<EntregaMercancia> model;
    private ArrayList<EntregaMercancia> lstEntregaMercanciaTemporal;

    private String title = "";
    private String viewEstate = "";
    private String observaciones = "";
    private BigDecimal empaquesEntregar;
    private BigDecimal kilosEntrega;
    private BigDecimal totalRemanente;
    private BigDecimal totalEntregar;
    private BigDecimal totalEmpaques;

    private Date date;
    private BigDecimal cantidad;

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

    BigDecimal carro = null;
    String producto = null;
    BigDecimal idEmpaque = null;
    BigDecimal idSucursal = null;

    private BigDecimal listaP_TotalP;
    private BigDecimal listaP_TotalEPE;
    private BigDecimal listaP_TotalEE;
    
    private BigDecimal listaE_TotalEE;

    @PostConstruct
    public void init() {
        listaP_TotalP = BIGDECIMAL_ZERO;
        listaP_TotalEPE = BIGDECIMAL_ZERO;
        listaP_TotalEE = BIGDECIMAL_ZERO;
        listaE_TotalEE = BIGDECIMAL_ZERO;
        usuarioDominio = context.getUsuarioAutenticado();
        permisosEntrega = true;
        validaEmpaques = false;
        data = new EntregaMercancia();
        entregaMercancia = new EntregaMercancia();
        model = new ArrayList<EntregaMercancia>();
        lstEntregaMercanciaTemporal = new ArrayList<EntregaMercancia>();
        lstSucursal = ifaceCatSucursales.getSucursales();
        date = context.getFechaSistema();

        setTitle("Entrega Mercancia");
        setViewEstate("init");

    }
    
    public void eliminarProducto()
    {
        lstEntregaMercanciaTemporal.remove(entregaEliminar);
        sumaTotales();
    }

    public void entregar() {
        if (!lstEntregaMercanciaTemporal.isEmpty()) {
            
            boolean bandera = false;
            for (EntregaMercancia e : lstEntregaMercanciaTemporal) {
                e.setIdUsuario(usuarioDominio.getIdUsuario());
                
                if (ifaceEntregaMercancia.insert(e) != 1) {
                    bandera = true;
                    break;
                } else {
                    bandera = false;
                    JsfUtil.addSuccessMessageClean("Mercancia entregada correctamente");
                }
            }
            if (bandera) {
                JsfUtil.addErrorMessageClean("Ocurrió un problema al entregar la mercancia");
            } else {
                JsfUtil.addSuccessMessageClean("Mercancia entregada correctamente");
                setParameterTicket(data.getIdFolioVenta().intValue());
                generateReport(data.getIdFolioVenta().intValue());
                RequestContext.getCurrentInstance().execute("window.frames.miFrame.print();");
                buscaFolioVenta();
                verificarPedido();
                lstEntregaMercanciaTemporal.clear();
                lstEntregaMercanciaTemporal = new ArrayList<EntregaMercancia>();
                cantidad = null;
                codigoBarras = null;
                buscaFolioVenta();
                sumaTotales();
            }
        } else {
            JsfUtil.addErrorMessageClean("Favor de agregar al menos un producto para la entrega");
        }
    }

    public void verificarPedido() {
        BigDecimal suma = new BigDecimal(0);
        for (EntregaMercancia e : model) {
            suma = suma.add(e.getEmpaquesRemanente().subtract(e.getEmpaquesEntregados(), MathContext.UNLIMITED), MathContext.UNLIMITED);
        }
        if (suma.intValue() == 0) {
            ifaceVentaMayoreo.updateEstatusVentaByFolioSucursalAndIdSucursal(data.getIdFolioVenta(), new BigDecimal(usuarioDominio.getSucId()), ESTATUS_ENTREGADO);
            //buscaFolioVenta();
            lstEntregaMercanciaTemporal.clear();
            lstEntregaMercanciaTemporal = new ArrayList<EntregaMercancia>();
            cantidad = null;
            codigoBarras = null;
            JsfUtil.addSuccessMessageClean("El pedido se ha entregado");
        }
    }


    public void verificarBarCode() {
        codigoBarras = codigoBarras.trim();
        if (codigoBarras != null && !codigoBarras.isEmpty()) {
            String diaArray[] = codigoBarras.split("'");
            switch (diaArray.length) {
                case 1:
                    String segArray[] = codigoBarras.split("-");
                    idSucursal = new BigDecimal(segArray[0]);
                    carro = new BigDecimal(segArray[1]);
                    producto = segArray[2];
                    idEmpaque = new BigDecimal(segArray[3]);
                    //idTipoConvenioFk = new BigDecimal(segArray[4]);
                    buscarBarcodeListaPedido();
                    break;
                case 5:
                    idSucursal = new BigDecimal(diaArray[0]);
                    carro = new BigDecimal(diaArray[1]);
                    producto = diaArray[2];
                    idEmpaque = new BigDecimal(diaArray[3]);
                    //idTipoConvenioFk = new BigDecimal(diaArray[4]);
                    buscarBarcodeListaPedido();
                    break;
                default:
                    JsfUtil.addErrorMessageClean("Código de Barras Inválido");

                    break;
            }
        } else {
            JsfUtil.addErrorMessageClean("Favor de ingresar un código de barras");
        }
    }

    public void buscarBarcodeListaPedido() {
        /*
        Esta función busca el codigo de barras en la lista del pedido.
         */
        if (cantidad == null ||cantidad.intValue()==0) 
        {
            cantidad = new BigDecimal(1);
           
        }
       
        for (EntregaMercancia mercancia : model) //recorre
        {
            if (mercancia.getIdCarroFk().intValue() == carro.intValue() && mercancia.getIdProducto().equals(producto) && mercancia.getIdTipoEmpaque().intValue() == idEmpaque.intValue()) {
                agregarAlista(mercancia);
              
                permisosEntrega = false;
                break;
            } else {
                
                permisosEntrega = true;
                JsfUtil.addErrorMessageClean("Este código de barras no existe en la lista del pedido");
            }
        }

    }
    public void onRowEdit(RowEditEvent event) {
        entregaEditar = (EntregaMercancia) event.getObject();
        
        System.out.println(entregaEditar.toString());
        for (EntregaMercancia repetido : lstEntregaMercanciaTemporal) {
            
                if (repetido.getIdCarroFk().intValue() == entregaEditar.getIdCarroFk().intValue() && repetido.getIdProducto().equals(entregaEditar.getIdProducto()) && repetido.getIdTipoEmpaque().intValue() == entregaEditar.getIdTipoEmpaque().intValue()) {
                   
                    System.out.println(repetido.getEmpaquesRemanente());
                    System.out.println(repetido.getEmpaquesEntregados());
                    
                    if(entregaEditar.getEmpaquesEntregar().compareTo(repetido.getEmpaquesRemanente())>0)
                    {
                        repetido.setEmpaquesEntregar(repetido.getEmpaquesRemanente());
                        JsfUtil.addErrorMessageClean("No puedes agregar mas empaques del pedido");
                        break;
                    }
                }
        }
        
        sumaTotales();
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void agregarAlista(EntregaMercancia ent) {
        if (!lstEntregaMercanciaTemporal.isEmpty()) {
            boolean bandera = false;
            //la lista cotiene datos verificar si hay alguno repetido
            // si no hay repetidos
            for (EntregaMercancia repetido : lstEntregaMercanciaTemporal) {
                if (repetido.getIdCarroFk().intValue() == carro.intValue() && repetido.getIdProducto().equals(producto) && repetido.getIdTipoEmpaque().intValue() == idEmpaque.intValue()) {

                    agregarRepetido(repetido, ent);
                    bandera = false;
                    break;
                } else {
                    bandera = true;
                }
            }//fin for
            if (bandera) {
                agregarNuevo(ent);
            }
        } else {
            //agregar nuevo.
            agregarNuevo(ent);

        }
    }

    public void agregarRepetido(EntregaMercancia repetido, EntregaMercancia mercancia) {
        if ((repetido.getEmpaquesEntregar().add(cantidad, MathContext.UNLIMITED)).compareTo((mercancia.getEmpaquesRemanente().subtract(mercancia.getEmpaquesEntregados(), MathContext.UNLIMITED))) > 0) {
            JsfUtil.addErrorMessageClean("No puedes entregar mas paquetes del pedido");
        } else {
            repetido.setEmpaquesEntregar(repetido.getEmpaquesEntregar().add(cantidad, MathContext.UNLIMITED));
            repetido.setEmpaquesRemanente(mercancia.getEmpaquesRemanente().subtract(mercancia.getEmpaquesEntregados(), MathContext.UNLIMITED));
            repetido.setEmpaquesEntregados(mercancia.getEmpaquesEntregados());
            System.out.println("Encontro Repetido");
            JsfUtil.addSuccessMessageClean("Producto Actualizado");
            sumaTotales();
        }

    }

    public void agregarNuevo(EntregaMercancia mercancia) {
        if (cantidad.compareTo((mercancia.getEmpaquesRemanente().subtract(mercancia.getEmpaquesEntregados(), MathContext.UNLIMITED))) > 0) {
            JsfUtil.addErrorMessageClean("No puedes entregar mas paquetes del pedido");
        } else {
            EntregaMercancia e = new EntregaMercancia();
            e.setIdCarroFk(mercancia.getIdCarroFk());
            e.setIdProducto(mercancia.getIdProducto());
            e.setIdTipoEmpaque(mercancia.getIdTipoEmpaque());
            e.setNombreProducto(mercancia.getNombreProducto());
            e.setNombreEmpaque(mercancia.getNombreEmpaque());
            e.setEmpaquesEntregar(cantidad);
            e.setEmpaquesRemanente(mercancia.getEmpaquesRemanente().subtract(mercancia.getEmpaquesEntregados(), MathContext.UNLIMITED));
            e.setEmpaquesEntregados(mercancia.getEmpaquesEntregados());
            e.setIdVPMayoreo(mercancia.getIdVPMayoreo());
            e.setIdVPMenudeo(mercancia.getIdVPMenudeo());
            lstEntregaMercanciaTemporal.add(e);
            JsfUtil.addSuccessMessageClean("Producto Agregado");
            cantidad = null;
            codigoBarras = null;
            sumaTotales();
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
            JsfUtil.addWarnMessageClean("No se han encontrado registros.");
            permisosEntrega = true;
        } else {

            data.setNombreCliente(model.get(0).getNombreCliente());
            data.setTotalVenta(model.get(0).getTotalVenta());
            data.setTipoVenta(model.get(0).getTipoVenta());
            data.setEstatusVenta(model.get(0).getEstatusVenta());
            data.setIdEstatus(model.get(0).getIdEstatus());
            data.setIdTipoVenta(model.get(0).getIdTipoVenta());
            data.setNombreTipoVenta(model.get(0).getNombreTipoVenta());
            validaEntregaMercancia();
            sumaTotales();

        }

    }
    private void sumaTotales()
    {
        
        listaP_TotalP = BIGDECIMAL_ZERO;
        listaP_TotalEPE = BIGDECIMAL_ZERO;
        listaP_TotalEE = BIGDECIMAL_ZERO;
        listaE_TotalEE = BIGDECIMAL_ZERO;
        
        for(EntregaMercancia e:model)
        {
            listaP_TotalP = listaP_TotalP.add(e.getEmpaquesRemanente(), MathContext.UNLIMITED);
            listaP_TotalEPE = listaP_TotalEPE.add(e.getEmpaquesRemanente().subtract(e.getEmpaquesEntregados(), MathContext.UNLIMITED), MathContext.UNLIMITED);
            listaP_TotalEE = listaP_TotalEE.add(e.getEmpaquesEntregados(), MathContext.UNLIMITED);
        }
        for(EntregaMercancia e:lstEntregaMercanciaTemporal)
        {
            listaE_TotalEE = listaE_TotalEE.add(e.getEmpaquesEntregar(), MathContext.UNLIMITED);
        }
    }

    private void validaEntregaMercancia() {

        switch (data.getIdEstatus().intValue()) {
            case 1:
                if (data.getIdTipoVenta().equals(TIPO_VENTA_CREDITO)) {
                    permisosEntrega = false;
                } else {
                    permisosEntrega = true;
                    JsfUtil.addWarnMessageClean("No se puede entregar el pedido , necesita pasar a caja para pagarlo.");
                }
                break;
            case 2:
                permisosEntrega = false;
                break;
            case 3:
                JsfUtil.addWarnMessageClean("El pedido ya fue entregado.");
                permisosEntrega = true;
                break;
            case 4:
                JsfUtil.addWarnMessageClean("La venta esta cancelada.");
                permisosEntrega = true;
                break;
            default:
                permisosEntrega = true;
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
                JsfUtil.addWarnMessageClean("No se puden entregar mas paquetes que los de la venta.");
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

            rutaPDF = UtilUpload.saveFileTemp(bytes, "entregaMercancia" + listaP_TotalEPE, folioVenta, usuarioDominio.getSucId());

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
        for (EntregaMercancia dominio : lstEntregaMercanciaTemporal) {
//            System.out.println("==========Domino=========");
//            System.out.println(dominio);
//            dominio.setEmpaquesEntregar(dominio.getEmpaquesEntregar() == null ? BIGDECIMAL_ZERO : dominio.getEmpaquesEntregar());
//            totalEmpaques = totalEmpaques.add(dominio.getEmpaquesEntregar());
//            totalRemanente = totalRemanente.add(dominio.getEmpaquesRemanente()).subtract(dominio.getEmpaquesEntregados());
//            if (dominio.getEmpaquesEntregar() != null) {
//                totalEntregar = totalEntregar.add(dominio.getEmpaquesEntregar());
//            }
//            dominio.setEmpaquesEntregar(dominio.getEmpaquesEntregar() == null ? BIGDECIMAL_ZERO : dominio.getEmpaquesEntregar());
//          

            productos.add(dominio.getNombreProducto().toUpperCase() + " " + dominio.getNombreEmpaque().toUpperCase());
            BigDecimal remanenteProducto = dominio.getEmpaquesRemanente().subtract(dominio.getEmpaquesEntregar());
            //remanenteProducto = remanenteProducto.subtract(dominio.getEmpaquesEntregados());
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

        paramReport.put("totalRemanente", (listaP_TotalEPE.subtract(listaE_TotalEE)).toString());
        paramReport.put("totalSalida", listaE_TotalEE.toString());
        paramReport.put("totalEmpaques", listaP_TotalP.toString());
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
        } else {
            JsfUtil.addWarnMessageClean("No se puden entregar.");
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public ArrayList<EntregaMercancia> getLstEntregaMercanciaTemporal() {
        return lstEntregaMercanciaTemporal;
    }

    public void setLstEntregaMercanciaTemporal(ArrayList<EntregaMercancia> lstEntregaMercanciaTemporal) {
        this.lstEntregaMercanciaTemporal = lstEntregaMercanciaTemporal;
    }

    public BigDecimal getListaP_TotalP() {
        return listaP_TotalP;
    }

    public void setListaP_TotalP(BigDecimal listaP_TotalP) {
        this.listaP_TotalP = listaP_TotalP;
    }

    public BigDecimal getListaP_TotalEPE() {
        return listaP_TotalEPE;
    }

    public void setListaP_TotalEPE(BigDecimal listaP_TotalEPE) {
        this.listaP_TotalEPE = listaP_TotalEPE;
    }

    public BigDecimal getListaP_TotalEE() {
        return listaP_TotalEE;
    }

    public void setListaP_TotalEE(BigDecimal listaP_TotalEE) {
        this.listaP_TotalEE = listaP_TotalEE;
    }

    public BigDecimal getListaE_TotalEE() {
        return listaE_TotalEE;
    }

    public void setListaE_TotalEE(BigDecimal listaE_TotalEE) {
        this.listaE_TotalEE = listaE_TotalEE;
    }

    public EntregaMercancia getEntregaEliminar() {
        return entregaEliminar;
    }

    public void setEntregaEliminar(EntregaMercancia entregaEliminar) {
        this.entregaEliminar = entregaEliminar;
    }

    public EntregaMercancia getEntregaEditar() {
        return entregaEditar;
    }

    public void setEntregaEditar(EntregaMercancia entregaEditar) {
        this.entregaEditar = entregaEditar;
    }
    
    

}
