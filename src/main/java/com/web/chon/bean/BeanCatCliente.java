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
import javax.faces.event.ActionEvent;
import org.apache.commons.lang.StringEscapeUtils;
import org.primefaces.event.RowEditEvent;
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
    private ArrayList<Correos> lista_emails;

    private ArrayList<Correos> emails_del_cliente;
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
        lista_emails = new ArrayList<Correos>();
        lista_motivos = new ArrayList<Motivos>();
        Correos c2 = new Correos();
        c2.setTipo("Trabajo");
        lista_emails.add(c2);

        lista_entidades = new ArrayList<Entidad>();
        lista_entidades_2 = new ArrayList<Entidad>();
        selectedCliente = new ArrayList<Cliente>();
        selectedEntidad = 1;
        model = ifaceCatCliente.getClientes();

        setTitle("Catalogo de Clientes");
        setViewEstate("init");

        lista_entidades = ifaceCatEntidad.getEntidades();
        lista_entidades_2 = ifaceCatEntidad.getEntidades();
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
                data.setStatus_cliente(new BigDecimal(2));
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

        System.out.println("=====================Entro a Insetar=====================");
        try {
            BigDecimal id_cliente_next_val = new BigDecimal(ifaceCatCliente.getNextVal());
            data.setId_cliente(id_cliente_next_val);
            Correos c = new Correos();
            c.setCorreo(data.getEmail());
            lista_emails.add(c);

            System.out.println("Cliente Bean: " + data.toString());
            if (ifaceCatCliente.insertCliente(data) == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al registrar"));

            } else {
                for (int i = 0; i < lista_emails.size(); i++) {
                    Correos temporal = (Correos) lista_emails.get(i);

                    if (temporal.getCorreo() == null) //agregar solo correos != null 
                    {
                        System.out.println("Cadena vacia no agregada");
                    } else {
                        temporal.setId_cliente_fk(id_cliente_next_val);
                        System.out.println("Correo #: " + i + " : " + temporal.toString());
                        if (ifaceCatCorreos.insertCorreo(temporal) == 0) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ya se encuentra registrado ese correo."));
                        }
                    }

                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado."));

            }

        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar insertar el registro :"));
        }

        backView();

        return "clientes";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void backView() {
        setTitle("Catalogo de Clientes");
        setViewEstate("init");
        data = new Cliente();
        selectedCliente = null;

    }

    public void addCorreo() {
        Correos c = new Correos();
        if (data.getEmails() == null) {
            data.setEmails(lista_emails);
        } else {

            //Falta condición para no agregar cadenas vacias
            data.getEmails().add(c);
        }

    }

    @Override
    public String update() {
        try {
            System.out.println("update controller");
            Correos ctemporal = new Correos();
            ctemporal.setIdcorreo(data.getEmails().get(0).getIdcorreo());
            ctemporal.setCorreo(data.getEmail());
            ctemporal.setTipo(data.getEmails().get(0).getTipo());

            if (ifaceCatCliente.updateCliente(data) == 1) {
                if (!data.getEmails().isEmpty()) {
                    ifaceCatCorreos.updateCorreos(ctemporal);
                }
                JsfUtil.addSuccessMessageClean("Datos Actualizados Correctamente");
            } else {

                JsfUtil.addErrorMessage("Ocurrio un problema al actualizar el cliente");
            }

        } catch (Exception ex) {

            JsfUtil.addErrorMessage("Ocurrio un problema al actualizar el cliente");
        }

        backView();
        return "clientes";

    }

    @Override
    public void searchById() {
        buscaMunicipios(1);
        setTitle("Editar Cliente");
        setViewEstate("searchById");
        permissionToWrite = false;
        permissionToEdit = true;
        if (data.getStatus_cliente().intValue() == 2) {
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
        bajaCliente.setId_baja_cliente(data.getId_cliente());
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
        int idEstado = (data.getEstado_id() == null || data.getEstado_id() == "") ? 0 : Integer.parseInt(data.getEstado_id());
        if (data.getEstado_id() != null) {
            lista_municipios = ifaceCatMunicipio.getMunicipios(idEstado);
        } else {
            lista_municipios = null;
        }

        if (edit != 1) {
            data.setMunicipio(null);
            data.setMunicipioFiscal(null);
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
            lista_municipios = ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstado_id()));
            data.setEstado_id(null);
            data.setMunicipio(null);
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalById("");
            //data.setID_CP(-1);
        } else {

            data.setEstado_id(Integer.toString(lista_codigos_postales.get(0).getIdEntidad()));
            data.setMunicipio(Integer.toString(lista_codigos_postales.get(0).getIdMunicipio()));
            data.setID_CP(new BigDecimal(lista_codigos_postales.get(0).getId_cp()));
            lista_municipios = ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstado_id()));
        }

    }

//    public void buscaColonias2() {
//
//        lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostalFiscal());
//
//        if (lista_codigos_postales_2.isEmpty()) {
//
//            lista_entidades_2 = ifaceCatEntidad.getEntidades();
//            lista_municipios_2 = ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstadoFiscal()));
//            data.setEstadoFiscal(null);
//            data.setMunicipioFiscal(null);
//            lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalById("");
//            System.out.println("Lista Vacia");
//
//        } else {
//            System.out.println("Lista llena");
//            data.setEstadoFiscal(Integer.toString(lista_codigos_postales_2.get(0).getIdEntidad()));
//            data.setMunicipioFiscal(Integer.toString(lista_codigos_postales_2.get(0).getIdMunicipio()));
//            data.setID_CP_FISCAL(new BigDecimal(lista_codigos_postales_2.get(0).getId_cp()));
//            lista_municipios_2 = ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstadoFiscal()));
//        }
//
//    }
    public void buscaColoniasMun(int edit) {
        int idMunicipio = (data.getMunicipio() == null || data.getMunicipio() == "") ? 0 : Integer.parseInt(data.getMunicipio());

        if (data.getMunicipio() != null) {
            lista_codigos_postales = ifaceCatCodigosPostales.getCodigoPostalByIdMun(idMunicipio);
        } else {
            lista_codigos_postales = null;
        }

        if (edit != 1) {
            data.setCodigoPostal(null);
        }
    }

    public void buscaColoniasMun2() {
        int idMunicipio = (data.getMunicipio() == null || data.getMunicipio() == "") ? 0 : Integer.parseInt(data.getMunicipioFiscal());
        if (data.getMunicipioFiscal() != null) {
            lista_codigos_postales_2 = ifaceCatCodigosPostales.getCodigoPostalByIdMun(idMunicipio);

        } else {
            lista_codigos_postales_2 = null;
        }
        data.setCodigoPostalFiscal(null);

    }

    public void buscaMotivos() {
        lista_motivos = ifaceCatMotivos.getMotivos();
    }

    public void ActualizaCodigoPostal() {
        for (int i = 0; i < lista_codigos_postales.size(); i++) {
            if (lista_codigos_postales.get(i).getId_cp() == data.getID_CP().intValue()) {
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

    public List<Correos> getLista_emails() {
        return lista_emails;
    }

    public void setLista_emails(List<Correos> lista_emails) {
        this.lista_emails = (ArrayList<Correos>) lista_emails;
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
