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
import com.web.chon.dominio.Provedor;
import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceCatCodigosPostales;
import com.web.chon.service.IfaceCatEntidad;
import com.web.chon.service.IfaceCatMunicipio;
import com.web.chon.service.IfaceCatProvedores;
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
public class BeanCatProvedor extends SimpleViewBean<Provedor> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(Provedor.class);
    private String title;
    private String viewEstate;
    private boolean permissionToWrite;
    private boolean permissionToWriteStatus;
    private boolean permissionToEdit;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceCatEntidad ifaceCatEntidad;
    @Autowired
    private IfaceCatMunicipio ifaceCatMunicipio;
    @Autowired
    private IfaceCatCodigosPostales ifaceCatCodigosPostales;

    private ArrayList<CodigoPostal> lista_codigos_postales;
    private ArrayList<CodigoPostal> lista_codigos_postales_2;
    private ArrayList<Entidad> lista_entidades;
    private ArrayList<Municipios> lista_municipios;
    private ArrayList<Entidad> lista_entidades_2;
    private ArrayList<Municipios> lista_municipios_2;

    @Override
    public void initModel() {
        data = new Provedor();
        model = new PaginationLazyDataModel<Provedor, BigDecimal>(ifaceCatProvedores, new Provedor());
        setTitle("Catálogo de Provedores");
        setViewEstate("init");

        lista_entidades = new ArrayList<Entidad>();
        lista_entidades_2 = new ArrayList<Entidad>();
        lista_entidades = ifaceCatEntidad.getEntidades();
        lista_entidades_2 = ifaceCatEntidad.getEntidades();
        lista_municipios = new ArrayList<Municipios>();
        lista_municipios_2 = new ArrayList<Municipios>();
        lista_codigos_postales = new ArrayList<CodigoPostal>();
        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById("1");
        lista_codigos_postales_2 = new ArrayList<CodigoPostal>();
        lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalById("1");

        permissionToWriteStatus = true;

    }

    public void viewNew() {

        data = new Provedor();
        setTitle("Alta de Provedores");
        setViewEstate("new");
        permissionToWrite = false;

    }

    public void backView() {
        setTitle("Catalogo de Provedores");
        setViewEstate("init");
        data = new Provedor();

    }

    public void buscaMunicipios() {
        lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFK().intValue());
        buscaColonias();
    }

    public void buscaMunicipios2() {

        lista_municipios_2 = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFiscalFK().intValue());
        buscaColonias2();
    }

    public void buscaColonias() {

        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostal());

        if (lista_codigos_postales.isEmpty()) {

            lista_entidades = ifaceCatEntidad.getEntidades();
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFK().intValue());
            data.setIdEntidadFK(new BigDecimal(0));
            data.setIdMunicipioFK(new BigDecimal(0));
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById("");
            data.setCodigoPostal("");
            //data.setID_CP(-1);
        } else {
            data.setIdEntidadFK(new BigDecimal(lista_codigos_postales.get(0).getIdEntidad()));
            data.setIdMunicipioFK(new BigDecimal(lista_codigos_postales.get(0).getIdMunicipio()));
            data.setIdCPProveFK(new BigDecimal(lista_codigos_postales.get(0).getId_cp()));
            lista_municipios = ifaceCatMunicipio.getMunicipios((data.getIdEntidadFK().intValue()));
        }

    }

    public void buscaColonias2() {

        lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostalFiscal());

        if (lista_codigos_postales_2.isEmpty()) {

            lista_entidades_2 = ifaceCatEntidad.getEntidades();
            lista_municipios_2 = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFiscalFK().intValue());
            data.setIdEntidadFiscalFK(new BigDecimal(0));
            data.setIdMunicipioFiscalFK(new BigDecimal(0));
            lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalById("");
            data.setCodigoPostalFiscal("");
            System.out.println("Lista Vacia");

        } else {
            System.out.println("Lista llena");
            data.setIdEntidadFiscalFK(new BigDecimal(lista_codigos_postales_2.get(0).getIdEntidad()));
            data.setIdMunicipioFiscalFK(new BigDecimal(lista_codigos_postales_2.get(0).getIdMunicipio()));
            data.setIdCPFiscalProveFK(new BigDecimal(lista_codigos_postales_2.get(0).getId_cp()));
            lista_municipios_2 = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFiscalFK().intValue());
        }

    }

    public void ActualizaCodigoPostal() {
        for (int i = 0; i < lista_codigos_postales.size(); i++) {
            if (lista_codigos_postales.get(i).getId_cp() == data.getIdCPProveFK().intValue()) {
                data.setCodigoPostal(lista_codigos_postales.get(i).getNumeropostal());
            }
        }

    }

    public void ActualizaCodigoPostalFiscal() {
        for (int i = 0; i < lista_codigos_postales_2.size(); i++) {
            if (lista_codigos_postales_2.get(i).getId_cp() == data.getIdCPFiscalProveFK().intValue()) {
                data.setCodigoPostalFiscal(lista_codigos_postales_2.get(i).getNumeropostal());
            }
        }

    }

    public void buscaColoniasMun() {
        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalByIdMun(data.getIdMunicipioFK().intValue());
        data.setCodigoPostal(lista_codigos_postales.get(0).getNumeropostal());
    }

    public void buscaColoniasMun2() {
        lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalByIdMun(data.getIdMunicipioFiscalFK().intValue());
        data.setCodigoPostalFiscal(lista_codigos_postales_2.get(0).getCodigoPostalFiscal());
    }

    @Override
    protected Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void viewDetails() {
        buscaMunicipios();
        buscaMunicipios2();
        setTitle("Detalles de Provedor");
        setViewEstate("viewDetails");
        permissionToWrite = true;
        permissionToEdit = false;
    }

    public void searchById() {
        buscaMunicipios();
        buscaMunicipios2();
        setTitle("Editar Provedor");
        setViewEstate("searchById");
        permissionToWrite = false;
        permissionToEdit = true;
        if (data.getIdStatusFK().intValue() == 2) {
            permissionToWriteStatus = false;
        } else {
            permissionToWriteStatus = true;
        }

    }

    @Override
    public String searchDatabyId() {
        buscaMunicipios();
        buscaMunicipios2();
        setTitle("Editar Provedor");
        setViewEstate("searchById");
        permissionToWrite = false;
        permissionToEdit = true;
        if (data.getIdStatusFK().intValue() == 2) {
            permissionToWriteStatus = false;
        } else {
            permissionToWriteStatus = true;
        }
        return "provedor";
    }

    @Override
    public String save() {
        try {

            int id_cliente_next_val = ifaceCatProvedores.getNextVal();
            data.setIdProvedorPK(new BigDecimal(id_cliente_next_val));
            if (ifaceCatProvedores.insertProvedor(data) == 0) {
                JsfUtil.addErrorMessage("No se pudo registrar el provedor");
            } else {
                JsfUtil.addSuccessMessage("El provedor se ha dado de alta correctamente");
            }

        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            JsfUtil.addErrorMessage("Ha ocurrido un error grave, contactar al administrador");
        }

        backView();

        return "provedor";
    }

    public String update() {
        try {
            if (ifaceCatProvedores.updateProvedor(data) == 0) {
                JsfUtil.addErrorMessage("No se pudo editar el provedor");
            } else {
                JsfUtil.addSuccessMessage("El provedor se ha editado correctamente");
            }

        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            JsfUtil.addErrorMessage("Ha ocurrido un error grave, contactar al administrador");
        }
        backView();
        return "provedor";

    }

    @Override
    public String delete() {
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

    public boolean isPermissionToEdit() {
        return permissionToEdit;
    }

    public void setPermissionToEdit(boolean permissionToEdit) {
        this.permissionToEdit = permissionToEdit;
    }

    public IfaceCatProvedores getIfaceCatProvedores() {
        return ifaceCatProvedores;
    }

    public void setIfaceCatProvedores(IfaceCatProvedores ifaceCatProvedores) {
        this.ifaceCatProvedores = ifaceCatProvedores;
    }

    public ArrayList<Entidad> getLista_entidades() {
        return lista_entidades;
    }

    public void setLista_entidades(ArrayList<Entidad> lista_entidades) {
        this.lista_entidades = lista_entidades;
    }

    public IfaceCatEntidad getIfaceCatEntidad() {
        return ifaceCatEntidad;
    }

    public void setIfaceCatEntidad(IfaceCatEntidad ifaceCatEntidad) {
        this.ifaceCatEntidad = ifaceCatEntidad;
    }

    public IfaceCatMunicipio getIfaceCatMunicipio() {
        return ifaceCatMunicipio;
    }

    public void setIfaceCatMunicipio(IfaceCatMunicipio ifaceCatMunicipio) {
        this.ifaceCatMunicipio = ifaceCatMunicipio;
    }

    public ArrayList<Municipios> getLista_municipios() {
        return lista_municipios;
    }

    public void setLista_municipios(ArrayList<Municipios> lista_municipios) {
        this.lista_municipios = lista_municipios;
    }

    public IfaceCatCodigosPostales getIfaceCatCodigosPostales() {
        return ifaceCatCodigosPostales;
    }

    public void setIfaceCatCodigosPostales(IfaceCatCodigosPostales ifaceCatCodigosPostales) {
        this.ifaceCatCodigosPostales = ifaceCatCodigosPostales;
    }

    public ArrayList<CodigoPostal> getLista_codigos_postales() {
        return lista_codigos_postales;
    }

    public void setLista_codigos_postales(ArrayList<CodigoPostal> lista_codigos_postales) {
        this.lista_codigos_postales = lista_codigos_postales;
    }

    public ArrayList<CodigoPostal> getLista_codigos_postales_2() {
        return lista_codigos_postales_2;
    }

    public void setLista_codigos_postales_2(ArrayList<CodigoPostal> lista_codigos_postales_2) {
        this.lista_codigos_postales_2 = lista_codigos_postales_2;
    }

    public ArrayList<Entidad> getLista_entidades_2() {
        return lista_entidades_2;
    }

    public void setLista_entidades_2(ArrayList<Entidad> lista_entidades_2) {
        this.lista_entidades_2 = lista_entidades_2;
    }

    public ArrayList<Municipios> getLista_municipios_2() {
        return lista_municipios_2;
    }

    public void setLista_municipios_2(ArrayList<Municipios> lista_municipios_2) {
        this.lista_municipios_2 = lista_municipios_2;
    }

}
