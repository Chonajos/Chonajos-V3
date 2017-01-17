/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.DominioCajas;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceOperacionesCaja;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jramirez
 */
@Component
@Scope("view")
public class BeanCajas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private String title;
    private String viewEstate;
    private UsuarioDominio usuario;
    private BigDecimal idCajaBean;
    private BigDecimal idSucursal;
    private BigDecimal totalEntradasEfectivo;
    private BigDecimal totalEntradasCheques;
    private BigDecimal totalEntradasCuentas;
    private BigDecimal totalSalidasEfectivo;
    private BigDecimal totalSalidasCheques;
    private BigDecimal totalSalidasCuentas;

    private ArrayList<Caja> listaCajas;
    private ArrayList<OperacionesCaja> lstOperacionesEntrada;
    private ArrayList<OperacionesCaja> lstOperacionesSalida;
    private ArrayList<DominioCajas> listaGeneral;
    private ArrayList<Sucursal> listaSucursal;
    private CorteCaja corteAnterior;
    

    private static final BigDecimal entrada = new BigDecimal(1);
    private static final BigDecimal salida = new BigDecimal(2);

    @PostConstruct
    public void init() {
        setTitle("Relación de Operaciones de Cajas");
        setViewEstate("init");
        listaCajas = new ArrayList<Caja>();
        listaCajas = ifaceCaja.getCajas();
        lstOperacionesEntrada = new ArrayList<OperacionesCaja>();
        lstOperacionesSalida = new ArrayList<OperacionesCaja>();
        listaSucursal = new ArrayList<Sucursal>();
        listaSucursal = ifaceCatSucursales.getSucursales();
        listaGeneral = new ArrayList<DominioCajas>();
        corteAnterior= new CorteCaja();
        
        buscar();

    }

    public void buscar() {
        for (Caja c : listaCajas) {
            DominioCajas dc = new DominioCajas();
            dc.setNombreCaja(c.getNombre());
            BigDecimal cheques = new BigDecimal(0);
            BigDecimal efectivo = new BigDecimal(0);
            BigDecimal cuentas = new BigDecimal(0);
            //dc.setNombreSucursal(idSucursal);

            lstOperacionesEntrada = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), entrada, null);
            lstOperacionesSalida = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), salida, null);
            for (OperacionesCaja ope : lstOperacionesEntrada) {

                if (ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13) {
                    efectivo = efectivo.add(ope.getMonto(), MathContext.UNLIMITED);
                } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {
                    cheques = cheques.add(ope.getMonto(), MathContext.UNLIMITED);
                } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {
                    cuentas = cuentas.add(ope.getMonto(), MathContext.UNLIMITED);
                }
            }
            for (OperacionesCaja ope : lstOperacionesSalida) {
                if (ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 1 || ope.getIdConceptoFk().intValue() == 3 || ope.getIdConceptoFk().intValue() == 4 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13 || ope.getIdConceptoFk().intValue() == 2 || ope.getIdConceptoFk().intValue() == 25 || ope.getIdConceptoFk().intValue() == 24
                        || ope.getIdConceptoFk().intValue() == 21 || ope.getIdConceptoFk().intValue() == 20 || ope.getIdConceptoFk().intValue() == 23
                        || ope.getIdConceptoFk().intValue() == 22) {
                    efectivo = efectivo.subtract(ope.getMonto(), MathContext.UNLIMITED);
                } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {
                    cheques = cheques.subtract(ope.getMonto(), MathContext.UNLIMITED);
                } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {
                    cuentas = cuentas.subtract(ope.getMonto(), MathContext.UNLIMITED);
                }

            }
            corteAnterior = ifaceCorteCaja.getLastCorteByCaja(c.getIdCajaPk());
            
            System.out.println("Corte Anterior: ");
            System.out.println(corteAnterior.toString());
            dc.setAperturaEfectivo(corteAnterior.getSaldoNuevo());
            dc.setAperturaCuentas(corteAnterior.getMontoChequesNuevos());
            dc.setAperturaCheques(corteAnterior.getMontoCuentaNuevo());
            
            dc.setCheques(cheques.add(dc.getAperturaCheques(), MathContext.UNLIMITED));
            dc.setCuentas(cuentas.add(dc.getCuentas(), MathContext.UNLIMITED));
            dc.setEfectivo(efectivo.add(dc.getEfectivo(), MathContext.UNLIMITED));

            listaGeneral.add(dc);
        }
        // lstOperacionesEntrada = ifaceOperacionesCaja.getOperaciones(idCajaBean, entrada, null);
    }

    

    public BigDecimal getIdCajaBean() {
        return idCajaBean;
    }

    public void setIdCajaBean(BigDecimal idCajaBean) {
        this.idCajaBean = idCajaBean;
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesEntrada() {
        return lstOperacionesEntrada;
    }

    public void setLstOperacionesEntrada(ArrayList<OperacionesCaja> lstOperacionesEntrada) {
        this.lstOperacionesEntrada = lstOperacionesEntrada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesSalida() {
        return lstOperacionesSalida;
    }

    public void setLstOperacionesSalida(ArrayList<OperacionesCaja> lstOperacionesSalida) {
        this.lstOperacionesSalida = lstOperacionesSalida;
    }

    public BigDecimal getTotalEntradasEfectivo() {
        return totalEntradasEfectivo;
    }

    public void setTotalEntradasEfectivo(BigDecimal totalEntradasEfectivo) {
        this.totalEntradasEfectivo = totalEntradasEfectivo;
    }

    public BigDecimal getTotalEntradasCheques() {
        return totalEntradasCheques;
    }

    public void setTotalEntradasCheques(BigDecimal totalEntradasCheques) {
        this.totalEntradasCheques = totalEntradasCheques;
    }

    public BigDecimal getTotalEntradasCuentas() {
        return totalEntradasCuentas;
    }

    public void setTotalEntradasCuentas(BigDecimal totalEntradasCuentas) {
        this.totalEntradasCuentas = totalEntradasCuentas;
    }

    public BigDecimal getTotalSalidasEfectivo() {
        return totalSalidasEfectivo;
    }

    public void setTotalSalidasEfectivo(BigDecimal totalSalidasEfectivo) {
        this.totalSalidasEfectivo = totalSalidasEfectivo;
    }

    public BigDecimal getTotalSalidasCheques() {
        return totalSalidasCheques;
    }

    public void setTotalSalidasCheques(BigDecimal totalSalidasCheques) {
        this.totalSalidasCheques = totalSalidasCheques;
    }

    public BigDecimal getTotalSalidasCuentas() {
        return totalSalidasCuentas;
    }

    public void setTotalSalidasCuentas(BigDecimal totalSalidasCuentas) {
        this.totalSalidasCuentas = totalSalidasCuentas;
    }

    public ArrayList<DominioCajas> getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(ArrayList<DominioCajas> listaGeneral) {
        this.listaGeneral = listaGeneral;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public ArrayList<Sucursal> getListaSucursal() {
        return listaSucursal;
    }

    public void setListaSucursal(ArrayList<Sucursal> listaSucursal) {
        this.listaSucursal = listaSucursal;
    }

}
