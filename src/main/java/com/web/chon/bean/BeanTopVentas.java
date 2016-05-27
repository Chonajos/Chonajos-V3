/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.topVentas;
import com.web.chon.service.IfaceTopVentas;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanTopVentas implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceTopVentas ifaceTopVentas;
    private String title = "";
    private String titleView = "";
    public String viewEstate = "";
    private ArrayList<topVentas> modelMayoreo;
    private ArrayList<topVentas> modelMenudeo;
    private String radio;
    private Date fechaInicio;
    private Date fechaFin;
    private int filtro;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;

    @PostConstruct
    public void init() {
        radio = "mayoreo";

        modelMayoreo = new ArrayList<topVentas>();
        modelMenudeo = new ArrayList<topVentas>();

        modelMayoreo = ifaceTopVentas.getMayoreo(fechaInicio, fechaFin);
        modelMenudeo = ifaceTopVentas.getMenudeo(fechaInicio, fechaFin);
        setTitleView("Ventas Mayoreo");
        setTitle("Top de Ventas");
        setViewEstate("Mayoreo");

    }

    public void changeView() {
        if (radio.equals("mayoreo")) {
            setTitleView("Ventas Mayoreo");
            setViewEstate("Mayoreo");
            radio = "mayoreo";

        } else {
            setTitleView("Ventas Menudeo");
            setViewEstate("Menudeo");
            radio = "menudeo";
            System.out.println("Entro aqui");
        }
    }

    public void getTopVentasByIntervalDate() {
        setFechaInicioFin(filtro);
        modelMayoreo = ifaceTopVentas.getMayoreo(getFechaFiltroInicio(), getFechaFiltroFin());
        modelMenudeo = ifaceTopVentas.getMenudeo(getFechaFiltroInicio(), getFechaFiltroFin());

    }

    public void setFechaInicioFin(int filter) {

        switch (filter) {
            case 4:
                System.out.println("Fecha Inicio: " + getFechaFiltroInicio());
                System.out.println("Fecha Fin: " + getFechaFiltroFin());
                if (getFechaFiltroInicio() != null && getFechaFiltroFin() != null) {
                    modelMayoreo = ifaceTopVentas.getMayoreo(getFechaFiltroInicio(), getFechaFiltroFin());
                    modelMenudeo = ifaceTopVentas.getMenudeo(getFechaFiltroInicio(), getFechaFiltroFin());

                } else {
                    modelMayoreo = ifaceTopVentas.getMayoreo(null, null);
                    modelMenudeo = ifaceTopVentas.getMenudeo(null, null);
                }
                break;
            case 1:
                setFechaFiltroInicio(new Date());
                setFechaFiltroFin(new Date());
                break;

            case 2:
                setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
                setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));

                break;
            case 3:
                setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
                setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
                break;
            default:
                setFechaFiltroInicio(null);
                setFechaFiltroFin(null);
                break;
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

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
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

    public ArrayList<topVentas> getModelMayoreo() {
        return modelMayoreo;
    }

    public void setModelMayoreo(ArrayList<topVentas> modelMayoreo) {
        this.modelMayoreo = modelMayoreo;
    }

    public ArrayList<topVentas> getModelMenudeo() {
        return modelMenudeo;
    }

    public void setModelMenudeo(ArrayList<topVentas> modelMenudeo) {
        this.modelMenudeo = modelMenudeo;
    }

    public String getTitleView() {
        return titleView;
    }

    public void setTitleView(String titleView) {
        this.titleView = titleView;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
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

}
