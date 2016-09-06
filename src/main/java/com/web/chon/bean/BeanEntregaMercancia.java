package com.web.chon.bean;

import com.web.chon.dominio.EntregaMercancia;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntregaMercancia;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
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

    private ArrayList<Sucursal> lstSucursal;

    private EntregaMercancia data;
    private EntregaMercancia entregaMercancia;
    private ArrayList<EntregaMercancia> model;
    private ArrayList<EntregaMercancia> lstEntregaMercanciaSelected;

    private String title = "";
    private String viewEstate = "";
    private String observaciones = "";
    private UsuarioDominio usuarioDominio;

    private BigDecimal BIGDECIMAL_UNO = new BigDecimal(1);
    private BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
    private BigDecimal ESTATUS_PAGADO = new BigDecimal(2);
    private BigDecimal ESTATUS_VENDIDO = new BigDecimal(1);
    private BigDecimal TIPO_VENTA_CREDITO = new BigDecimal(2);
    private BigDecimal kilosEntregados;
    private BigDecimal empaquesEntregados;
    private boolean permisosEntrega;
    private int INT_UNO = 1;

    @PostConstruct
    public void init() {
        usuarioDominio = context.getUsuarioAutenticado();
        permisosEntrega = false;
        data = new EntregaMercancia();
        entregaMercancia = new EntregaMercancia();
        model = new ArrayList<EntregaMercancia>();
        lstSucursal = ifaceCatSucursales.getSucursales();

        setTitle("Entrega Mercancia");
        setViewEstate("init");

    }

    public void entregar() {
        try {
            System.out.println("entrega");

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Error > " + ex.getMessage().toString());
            ex.printStackTrace();
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
            
            System.out.println("data idestatus "+data.getIdEstatus());
            System.out.println("ESTATUS_PAGADO "+ESTATUS_PAGADO);
            if(data.getIdEstatus().equals(ESTATUS_PAGADO)){
                permisosEntrega = true;
                System.out.println("entregar 1");
            }else if(data.getIdEstatus().equals(ESTATUS_VENDIDO) && data.getIdTipoVenta().equals(TIPO_VENTA_CREDITO)){
                permisosEntrega = true;
                System.out.println("entregar 2");
            }else{
                permisosEntrega = false;
                System.out.println(" no entregar");
                JsfUtil.addWarnMessage("No se puede entregar esta mercancia.");
            }
        }

    }

    public void clean() {

    }

    public void setKilosPromedio(AjaxBehaviorEvent event) {
        System.out.println("entregaMercancia " + entregaMercancia.toString());

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

    public ArrayList<EntregaMercancia> getLstEntregaMercanciaSelected() {
        return lstEntregaMercanciaSelected;
    }

    public void setLstEntregaMercanciaSelected(ArrayList<EntregaMercancia> lstEntregaMercanciaSelected) {
        this.lstEntregaMercanciaSelected = lstEntregaMercanciaSelected;
    }

    public EntregaMercancia getEntregaMercancia() {
        return entregaMercancia;
    }

    public void setEntregaMercancia(EntregaMercancia entregaMercancia) {
        this.entregaMercancia = entregaMercancia;
    }

    public BigDecimal getKilosEntregados() {
        return kilosEntregados;
    }

    public void setKilosEntregados(BigDecimal kilosEntregados) {
        this.kilosEntregados = kilosEntregados;
    }

    public BigDecimal getEmpaquesEntregados() {
        return empaquesEntregados;
    }

    public void setEmpaquesEntregados(BigDecimal empaquesEntregados) {
        this.empaquesEntregados = empaquesEntregados;
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

}
