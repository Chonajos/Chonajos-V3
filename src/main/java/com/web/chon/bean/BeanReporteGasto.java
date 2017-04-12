package com.web.chon.bean;

import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el reporte de Gastos
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanReporteGasto implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private static final Logger logger = LoggerFactory.getLogger(BeanReporteGasto.class);

    private List<Sucursal> lstSucursal;
    private ArrayList<OperacionesCaja> lstOperacionescajas;

    private Date fechaInicio;
    private Date fechaFin;

    private BigDecimal idSucursal;
    private BigDecimal totalGasto;

    private String title;

    @PostConstruct
    public void init() {

        title = "Reporte de Gastos";
        lstSucursal = new ArrayList<Sucursal>();
        lstSucursal = ifaceCatSucursales.getSucursales();

        fechaInicio = context.getFechaSistema();
        fechaFin = fechaInicio;
        
        idSucursal = new BigDecimal(context.getUsuarioAutenticado().getSucId());

        searchById();

    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        String sFechaInicio = TiempoUtil.getFechaDDMMYYYY(fechaInicio);
        String sFechaFin = TiempoUtil.getFechaDDMMYYYY(fechaFin);

        lstOperacionescajas = ifaceOperacionesCaja.getByIdSucuralAndDate(idSucursal, sFechaInicio, sFechaFin);
        
        calculaTotal();
    }

    private void calculaTotal() {
        totalGasto = new BigDecimal(0);
        for (OperacionesCaja opc : lstOperacionescajas) {
            totalGasto = totalGasto.add(opc.getMonto());
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

    public ArrayList<OperacionesCaja> getLstOperacionescajas() {
        return lstOperacionescajas;
    }

    public void setLstOperacionescajas(ArrayList<OperacionesCaja> lstOperacionescajas) {
        this.lstOperacionescajas = lstOperacionescajas;
    }

    public List<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(List<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getTotalGasto() {
        return totalGasto;
    }

    public void setTotalGasto(BigDecimal totalGasto) {
        this.totalGasto = totalGasto;
    }

}
