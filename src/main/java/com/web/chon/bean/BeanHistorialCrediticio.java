/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.HistorialCrediticio;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCredito;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class BeanHistorialCrediticio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private IfaceCredito ifaceCredito;

    private String title;
    private String viewEstate;
    private ArrayList<HistorialCrediticio> model;
    private ArrayList<Credito> lstCredito;
    private ArrayList<AbonoCredito> lstAbonos;

    private BigDecimal idClienteBeanFk;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        setTitle("Historial Crediticio");
        setViewEstate("init");
        model = new ArrayList<HistorialCrediticio>();
        lstCredito = new ArrayList<Credito>();
        lstAbonos = new ArrayList<AbonoCredito>();
        idClienteBeanFk = new BigDecimal(21);

        

    }

    public void buscar() {
        lstCredito = ifaceCredito.getHistorialCrediticio(idClienteBeanFk, TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin));
        for (Credito c : lstCredito) {
            System.out.println("C: " + c.toString());
        }
    }

    public ArrayList<HistorialCrediticio> getModel() {
        return model;
    }

    public void setModel(ArrayList<HistorialCrediticio> model) {
        this.model = model;
    }

    public ArrayList<Credito> getLstCredito() {
        return lstCredito;
    }

    public void setLstCredito(ArrayList<Credito> lstCredito) {
        this.lstCredito = lstCredito;
    }

    public ArrayList<AbonoCredito> getLstAbonos() {
        return lstAbonos;
    }

    public void setLstAbonos(ArrayList<AbonoCredito> lstAbonos) {
        this.lstAbonos = lstAbonos;
    }

    public BigDecimal getIdClienteBeanFk() {
        return idClienteBeanFk;
    }

    public void setIdClienteBeanFk(BigDecimal idClienteBeanFk) {
        this.idClienteBeanFk = idClienteBeanFk;
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

}
