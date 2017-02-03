/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceRegistroEntradaSalida;
import com.web.chon.service.IfaceUsuario;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanHistorialRegEntSal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    IfaceRegistroEntradaSalida ifaceRegEntSal;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;

    private RegistroEntradaSalida data;
    private ArrayList<RegistroEntradaSalida> model;
    private ArrayList<Usuario> listaUsuarios;
    private Date fechaFin;
    private Date fechaInicio;
    private ArrayList<Sucursal> listaSucursales;
    private String title;
    private String viewEstate;
    private int filtro;
    private MapModel simpleModel;
    private String puntoCentral;
    public static final double R = 6372.8;

    private int faltas = 0;
    private int retardos = 0;

    private final int RETARDOS_EQUIVALE_FALTA = 3;

    @PostConstruct
    public void init() {
        setTitle("Registros");
        setViewEstate("init");
        data = new RegistroEntradaSalida();
        model = new ArrayList<RegistroEntradaSalida>();
        simpleModel = new DefaultMapModel();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaUsuarios = new ArrayList<Usuario>();
        filtro = 2;
        setFechaFin(context.getFechaSistema());
        data.setFechaFiltroInicio(TiempoUtil.getDayOneOfMonth(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndOfMonth(new Date()));
        data.setIdSucursalFk(new BigDecimal(context.getUsuarioAutenticado().getSucId()));
        changeComboUsers();

    }

    public void verUbicaciones() {

        LatLng coord1 = new LatLng(data.getLatitudEntrada(), data.getLongitudEntrada());
        LatLng coord2 = new LatLng(data.getLatitudSalida(), data.getLongitudSalida() + 0.00001);
        puntoCentral = data.getLatitudEntrada() + "," + data.getLongitudEntrada();
        simpleModel.addOverlay(new Marker(coord1, "Entrada"));
        simpleModel.addOverlay(new Marker(coord2, "Salida"));
        simpleModel.getMarkers().get(0).setIcon("http://www.google.com/mapfiles/dd-start.png");
        simpleModel.getMarkers().get(1).setIcon("http://www.google.com/mapfiles/dd-end.png");

    }

    private boolean validateLocation(double lat1, double lon1, double lat2, double lon2, double rangoMaximoPermitido) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        int metros;

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        metros = (int) ((R * c) / 0.00062137);

        System.out.println("metros " + metros);
        System.out.println("rangoMaximoPermitido " + rangoMaximoPermitido);
        if (metros > rangoMaximoPermitido) {
            return false;
        }
        return true;
    }

    public void changeComboUsers() {

        listaUsuarios = ifaceCatUsuario.getUsuariosbyIdSucursal(data.getIdSucursalFk().intValue());
    }

    public void getRegistrosByIntervalDate() {
        retardos = 0;
        faltas = 0;
        model = ifaceRegEntSal.getRegistros(data.getIdUsuarioFk(), data.getFechaFiltroInicio(), data.getFechaFiltroFin());
        int faltaPorRetardos = 0;

        for (RegistroEntradaSalida dominio : model) {
            if (dominio.isFalta()) {
                faltas++;
            } else if (dominio.isRetardo()) {
                retardos++;
            }
            
//            double latitud = new Double(19.370436);
//            double longitud = new Double(-99.091470);
//
//            validateLocation(dominio.getLatitudEntrada(), dominio.getLongitudEntrada(), latitud, longitud, 2000L);
        }

        //Se calculan las faltas que se generan por retardos
        faltaPorRetardos = retardos / RETARDOS_EQUIVALE_FALTA;

        //retardos restantes
        retardos = retardos % RETARDOS_EQUIVALE_FALTA;
        faltas += faltaPorRetardos;

    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public RegistroEntradaSalida getData() {
        return data;
    }

    public void setData(RegistroEntradaSalida data) {
        this.data = data;
    }

    public ArrayList<RegistroEntradaSalida> getModel() {
        return model;
    }

    public void setModel(ArrayList<RegistroEntradaSalida> model) {
        this.model = model;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public String getPuntoCentral() {
        return puntoCentral;
    }

    public void setPuntoCentral(String puntoCentral) {
        this.puntoCentral = puntoCentral;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getRetardos() {
        return retardos;
    }

    public void setRetardos(int retardos) {
        this.retardos = retardos;
    }

}
