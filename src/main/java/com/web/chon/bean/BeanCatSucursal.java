/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.CodigoPostal;
import com.web.chon.dominio.Entidad;
import com.web.chon.dominio.Municipios;
import com.web.chon.dominio.Sucursal;
import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceCatCodigosPostales;
import com.web.chon.service.IfaceCatEntidad;
import com.web.chon.service.IfaceCatMunicipio;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanCatSucursal extends SimpleViewBean<Sucursal> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(Sucursal.class);
    private String title;
    private String viewEstate;

    private boolean permissionToWrite;
    private boolean permissionToWriteStatus;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    private ArrayList<Entidad> lista_entidades;
    @Autowired
    private IfaceCatEntidad ifaceCatEntidad;
    @Autowired
    private IfaceCatMunicipio ifaceCatMunicipio;
    private ArrayList<Municipios> lista_municipios;
    @Autowired
    private IfaceCatCodigosPostales ifaceCatCodigosPostales;
    private ArrayList<CodigoPostal> lista_codigos_postales;

    @Override
    public void initModel() {

        data = new Sucursal();
        
        model = new PaginationLazyDataModel<Sucursal, BigDecimal>(ifaceCatSucursales, new Sucursal());
        setTitle("Catálogo de Sucursales");
        setViewEstate("init");
        lista_entidades = new ArrayList<Entidad>();
        lista_entidades = ifaceCatEntidad.getEntidades();
        lista_municipios = new ArrayList<Municipios>();
        lista_codigos_postales = new ArrayList<CodigoPostal>();
        permissionToWriteStatus = true;
    }

    public void searchById() {
        buscaMunicipios();
        setTitle("Editar Sucursal");
        setViewEstate("searchById");
        permissionToWrite = false;
        permissionToWriteStatus = false;
        System.out.println("Data: "+data);

    }

    public void viewNew() {
        data = new Sucursal();
        data.setStatusSucursal(true);
        setTitle("Alta de Sucursales");
        setViewEstate("new");
        permissionToWrite = false;
        permissionToWriteStatus = true;

    }

    public void viewDetails() {
        buscaMunicipios();
        setTitle("Detalles de Sucursal");
        setViewEstate("viewDetails");
        permissionToWrite = true;
        permissionToWriteStatus = true;
        System.out.println("Data: "+data);

    }

    public void backView() {
        setTitle("Catálogo de Sucursales");
        setViewEstate("init");
        data = new Sucursal();

    }

    public String update() {
        try {
            if (data.isStatusSucursal()) {
                data.setIdStatusSucursalfk(new BigDecimal(1));

            } else {
                data.setIdStatusSucursalfk(new BigDecimal(2));
            }

            if (ifaceCatSucursales.updateSucursal(data) == 0) {
                JsfUtil.addErrorMessage("No se pudo editar la sucursal");
            } else {
                JsfUtil.addSuccessMessage("La Sucursal se ha editado correctamente");
            }

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ha ocurrido un error grave, contactar al administrador");
        }
        backView();
        return "sucursales";

    }

    public void buscaMunicipios() {
        lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk().intValue());
        buscaColonias();
    }

    public void buscaColoniasMun() {
        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalByIdMun(data.getIdMunicipioFK().intValue());
        data.setCodigoPostal(lista_codigos_postales.get(0).getNumeropostal());
    }

    public void ActualizaCodigoPostal() {
        for (int i = 0; i < lista_codigos_postales.size(); i++) {
            if (lista_codigos_postales.get(i).getId_cp() == data.getCpSucursal().intValue()) {
                data.setCodigoPostal(lista_codigos_postales.get(i).getNumeropostal());
            }
        }

    }

    public void buscaColonias() {

        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostal());

        if (lista_codigos_postales.isEmpty()) 
        {

            lista_entidades = ifaceCatEntidad.getEntidades();

            if (data.getIdEntidadFk() == null) 
            {
                data.setIdEntidadFk(new BigDecimal(0));
            }
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk().intValue());
            data.setIdEntidadFk(null);
            data.setIdMunicipioFK(null);
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById("");
            data.setCodigoPostal("");
        } 
        else 
        {
            data.setIdEntidadFk(new BigDecimal(lista_codigos_postales.get(0).getIdEntidad()));
            data.setIdMunicipioFK(new BigDecimal(lista_codigos_postales.get(0).getIdMunicipio()));
            data.setCpSucursal(new BigDecimal(lista_codigos_postales.get(0).getId_cp()));
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk().intValue());
        }

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String searchDatabyId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String save() {
        data.setIdStatusSucursalfk(new BigDecimal(1));

        try {
            data.setIdSucursalPk(new BigDecimal(ifaceCatSucursales.getNextVal()));
            System.out.println("Sucursal Bean: " + data.toString());
            if (ifaceCatSucursales.insertSucursal(data) == 0) {
                JsfUtil.addErrorMessage("No se pudo registrar la sucursal");

            } else {
                JsfUtil.addSuccessMessage("La Sucursal se ha agregado correctamente");
            }

        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            JsfUtil.addErrorMessage("Ha ocurrido un error grave, contactar al administrador");

        }

        backView();

        return "sucursales";

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

    public boolean isPermissionToWrite() {
        return permissionToWrite;
    }

    public void setPermissionToWrite(boolean permissionToWrite) {
        this.permissionToWrite = permissionToWrite;
    }

    public boolean isPermissionToWriteStatus() {
        return permissionToWriteStatus;
    }

    public void setPermissionToWriteStatus(boolean permissionToWriteStatus) {
        this.permissionToWriteStatus = permissionToWriteStatus;
    }

    public ArrayList<Entidad> getLista_entidades() {
        return lista_entidades;
    }

    public void setLista_entidades(ArrayList<Entidad> lista_entidades) {
        this.lista_entidades = lista_entidades;
    }

    public ArrayList<Municipios> getLista_municipios() {
        return lista_municipios;
    }

    public void setLista_municipios(ArrayList<Municipios> lista_municipios) {
        this.lista_municipios = lista_municipios;
    }

    public ArrayList<CodigoPostal> getLista_codigos_postales() {
        return lista_codigos_postales;
    }

    public void setLista_codigos_postales(ArrayList<CodigoPostal> lista_codigos_postales) {
        this.lista_codigos_postales = lista_codigos_postales;
    }

}
