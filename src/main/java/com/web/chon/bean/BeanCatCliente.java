package com.web.chon.bean;

import com.web.chon.dominio.BajaClientes;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.CodigoPostal;
import com.web.chon.dominio.Correos;
import com.web.chon.dominio.Entidad;
import com.web.chon.dominio.Motivos;
import com.web.chon.dominio.Municipios;
import com.web.chon.service.IfaceBajaCliente;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatCodigosPostales;
import com.web.chon.service.IfaceCatCorreos;
import com.web.chon.service.IfaceCatEntidad;
import com.web.chon.service.IfaceCatMotivos;
import com.web.chon.service.IfaceCatMunicipio;
import com.web.chon.util.JsfUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanCatCliente implements BeanSimple {

    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceCatEntidad ifaceCatEntidad;
    @Autowired
    private IfaceCatMunicipio ifaceCatMunicipio;
    @Autowired
    private IfaceCatCodigosPostales ifaceCatCodigosPostales;
    @Autowired
    private IfaceCatCorreos ifaceCatCorreos;
    @Autowired
    private IfaceCatMotivos ifaceCatMotivos;
    @Autowired
    private IfaceBajaCliente ifaceBajaCliente;

    private ArrayList<CodigoPostal> lista_codigos_postales;
    private ArrayList<CodigoPostal> lista_codigos_postales_2;
    private ArrayList<Cliente> model;
    private ArrayList<Entidad> lista_entidades;
    private ArrayList<Municipios> lista_municipios;
    private ArrayList<Entidad> lista_entidades_2;
    private ArrayList<Municipios> lista_municipios_2;
    private int selectedEntidad;
    private ArrayList<Cliente> selectedCliente;
    private String title;
    private String viewEstate;
    private Cliente data;
    private boolean permissionToWrite;
    private boolean permissionToWriteStatus;

    private boolean permissionToEdit;
    private int estado;
    private int estado_fis;

    private ArrayList<Motivos> lista_motivos;
    private BajaClientes bajaCliente;
    private String banderaTipoCliente;

    @PostConstruct
    public void init() {

        banderaTipoCliente = "pf";
        data = new Cliente();
        model = new ArrayList<Cliente>();
        bajaCliente = new BajaClientes();
        permissionToWrite = false;
        permissionToEdit = true;
        lista_entidades = new ArrayList<Entidad>();
        lista_entidades_2 = new ArrayList<Entidad>();
        selectedCliente = new ArrayList<Cliente>();
        selectedEntidad = 1;
        model = ifaceCatCliente.getClientes();
        setTitle("Catálogo de Clientes");
        setViewEstate("init");

        lista_entidades = ifaceCatEntidad.getEntidades();
        lista_entidades_2 = ifaceCatEntidad.getEntidades();
        data.setPais("MEXICO");
        data.setIdStatusFk(new BigDecimal(1));
    }

    public void changeView() {
        System.out.println("Entro a Metodo:" + data.getTipoPersona());
        if (data.getTipoPersona().equals("1")) {
            banderaTipoCliente = "pf";

        } else {
            banderaTipoCliente = "pm";
        }
    }

    @Override
    public String delete() {
        try {
            if (ifaceBajaCliente.insertCliente(bajaCliente) == 0) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El cliente :" + data.getNombre() + " ya está en baja."));
            } else {
                data.setIdStatusFk(new BigDecimal(2));
                ifaceCatCliente.updateCliente(data);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El cliente se ha dado de baja correctamente"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + data.getNombre() + "."));
        }

        return "clientes";
    }

    @Override
    public String insert() {
        data.setIdClientePk(new BigDecimal(ifaceCatCliente.getNextVal()));

        if (ifaceCatCliente.insertCliente(data) == 1) {
            JsfUtil.addSuccessMessageClean("Cliente Agregado con Éxito");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar cliente");
        }
        backView();
        return "clientes";

    }

    public void backView() {
        setTitle("Catalogo de Clientes");
        setViewEstate("init");
        data = new Cliente();
        selectedCliente = null;

    }


    @Override
    public String update() {
        if (ifaceCatCliente.updateCliente(data) == 1){
            JsfUtil.addSuccessMessageClean("Cliente Actualizado con Éxito");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error al Actualizar cliente");
        }

        backView();
        return "clientes";

    }

    @Override
    public void searchById()
    {
        buscaMunicipios(1);
        setTitle("Editar Cliente");
        setViewEstate("searchById");
        permissionToWrite = false;
        permissionToEdit = true;
        if (data.getIdStatusFk().intValue() == 2) {
            permissionToWriteStatus = false;
        } else {
            permissionToWriteStatus = true;
        }

    }

    public void viewDetails() {
        buscaMunicipios(1);

        setTitle("Detalles de Cliente");
        setViewEstate("viewDetails");
        permissionToWrite = true;
        permissionToEdit = false;
    }

    public void viewDelete() {
        setTitle("Baja de Clientes");
        setViewEstate("deleteCliente");
        permissionToWrite = true;
        permissionToEdit = false;
        buscaMotivos();
        bajaCliente.setId_baja_cliente(data.getIdClientePk());
    }

    public void viewNew() {

        data = new Cliente();
        data.setTipoPersona("1");
        setTitle("Alta de Clientes");
        setViewEstate("new");
        permissionToWrite = false;
        selectedCliente = null;
    }

    public void buscaMunicipios(int edit) {
        //int idEstado = (data.getIdEntidadFk() == null || data.getIdEntidadFk() == "") ? 0 : Integer.parseInt(data.getIdEntidadFk());
        
        if (data.getIdEntidadFk() != null) {
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk() ==null ? 0:  data.getIdEntidadFk().intValue() );
        } else {
            lista_municipios = null;
        }

        if (edit != 1) {
            data.setIdMunicipioFk(null);
        }

        buscaColoniasMun(1);
    }

//    public void buscaMunicipios2() {
//
//        lista_municipios_2 = ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstadoFiscal()));
//        buscaColonias2();
//    }
    public void buscaColonias() {

        lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostal());

        if (lista_codigos_postales.isEmpty()) {

            lista_entidades = ifaceCatEntidad.getEntidades();
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk().intValue());
            data.setIdEntidadFk(null);
            data.setIdMunicipioFk(null);
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById("");
            //data.setID_CP(-1);
        } else {

            data.setIdEntidadFk(new BigDecimal(lista_codigos_postales.get(0).getIdEntidad()));
            data.setIdMunicipioFk(new BigDecimal(lista_codigos_postales.get(0).getIdMunicipio()));
            data.setIdCodigoPostalFk(new BigDecimal(lista_codigos_postales.get(0).getId_cp()));
            lista_municipios = ifaceCatMunicipio.getMunicipios(data.getIdEntidadFk().intValue());
        }

    }

    public void buscaColoniasMun(int edit) {
        //int idMunicipio = (data.getMunicipio() == null || data.getMunicipio() == "") ? 0 : Integer.parseInt(data.getMunicipio());

        if (data.getIdMunicipioFk() != null) {
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalByIdMun(data.getIdMunicipioFk()==null ? 0: data.getIdMunicipioFk().intValue());
        } else {
            lista_codigos_postales = null;
        }

        if (edit != 1) {
            data.setCodigoPostal(null);
        }
    }

    public void buscaMotivos() {
        lista_motivos = ifaceCatMotivos.getMotivos();
    }

    public void ActualizaCodigoPostal() {
        for (int i = 0; i < lista_codigos_postales.size(); i++) {
            if (lista_codigos_postales.get(i).getId_cp() == data.getIdColoniaFk().intValue()) {
                data.setCodigoPostal(lista_codigos_postales.get(i).getNumeropostal());
            }
        }

    }

    public Cliente getCliente() {
        return data;
    }

    public void setCliente(Cliente cliente) {
        this.data = cliente;
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

    public ArrayList<Cliente> getModel() {
        return model;
    }

    public void setModel(ArrayList<Cliente> model) {
        this.model = model;
    }

    public ArrayList<Cliente> getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(ArrayList<Cliente> selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public Cliente getData() {
        return data;
    }

    public void setData(Cliente data) {
        this.data = data;
    }

    public int getSelectedEntidad() {
        return selectedEntidad;
    }

    public void setSelectedEntidad(int selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public ArrayList<Municipios> getLista_municipios() {
        return lista_municipios;
    }

    public void setLista_municipios(ArrayList<Municipios> lista_municipios) {
        this.lista_municipios = lista_municipios;
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

    public ArrayList<CodigoPostal> getLista_codigos_postales() {
        return lista_codigos_postales;
    }

    public void setLista_codigos_postales(ArrayList<CodigoPostal> lista_codigos_postales) {
        this.lista_codigos_postales = lista_codigos_postales;
    }

    public ArrayList<Entidad> getLista_entidades() {
        return lista_entidades;
    }

    public void setLista_entidades(ArrayList<Entidad> lista_entidades) {
        this.lista_entidades = lista_entidades;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado_fis() {
        return estado_fis;
    }

    public void setEstado_fis(int estado_fis) {
        this.estado_fis = estado_fis;
    }

    public IfaceCatCliente getIfaceCatCliente() {
        return ifaceCatCliente;
    }

    public void setIfaceCatCliente(IfaceCatCliente ifaceCatCliente) {
        this.ifaceCatCliente = ifaceCatCliente;
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

    public IfaceCatCodigosPostales getIfaceCatCodigosPostales() {
        return ifaceCatCodigosPostales;
    }

    public void setIfaceCatCodigosPostales(IfaceCatCodigosPostales ifaceCatCodigosPostales) {
        this.ifaceCatCodigosPostales = ifaceCatCodigosPostales;
    }

    public ArrayList<CodigoPostal> getLista_codigos_postales_2() {
        return lista_codigos_postales_2;
    }

    public void setLista_codigos_postales_2(ArrayList<CodigoPostal> lista_codigos_postales_2) {
        this.lista_codigos_postales_2 = lista_codigos_postales_2;
    }

    
    public boolean isPermissionToWrite() {
        return permissionToWrite;
    }

    public void setPermissionToWrite(boolean permissionToWrite) {
        this.permissionToWrite = permissionToWrite;
    }

    public boolean isPermissionToEdit() {
        return permissionToEdit;
    }

    public void setPermissionToEdit(boolean permissionToEdit) {
        this.permissionToEdit = permissionToEdit;
    }

    public ArrayList<Motivos> getLista_motivos() {
        return lista_motivos;
    }

    public void setLista_motivos(ArrayList<Motivos> lista_motivos) {
        this.lista_motivos = lista_motivos;
    }

    public IfaceBajaCliente getIfaceBajaCliente() {
        return ifaceBajaCliente;
    }

    public void setIfaceBajaCliente(IfaceBajaCliente ifaceBajaCliente) {
        this.ifaceBajaCliente = ifaceBajaCliente;
    }

    public BajaClientes getBajaCliente() {
        return bajaCliente;
    }

    public void setBajaCliente(BajaClientes bajaCliente) {
        this.bajaCliente = bajaCliente;
    }

    public boolean isPermissionToWriteStatus() {
        return permissionToWriteStatus;
    }

    public void setPermissionToWriteStatus(boolean permissionToWriteStatus) {
        this.permissionToWriteStatus = permissionToWriteStatus;
    }

    public String getBanderaTipoCliente() {
        return banderaTipoCliente;
    }

    public void setBanderaTipoCliente(String banderaTipoCliente) {
        this.banderaTipoCliente = banderaTipoCliente;
    }

}
