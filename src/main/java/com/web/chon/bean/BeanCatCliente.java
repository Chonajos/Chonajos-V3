/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Entidad;
import com.web.chon.dominio.Municipios;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatEntidad;
import com.web.chon.service.IfaceCatMunicipio;
import java.util.ArrayList;
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
public class BeanCatCliente implements BeanSimple
{
 
    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceCatEntidad ifaceCatEntidad;
    @Autowired
    private IfaceCatMunicipio ifaceCatMunicipio;
    
    
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
    private int estado;
    private int estado_fis;

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
    
    
    @PostConstruct
    public void init() {
            
        estado=1;
        estado_fis=1;
        
        data = new Cliente();
        model = new ArrayList<Cliente>();
        lista_entidades = new ArrayList<Entidad>();
        lista_municipios = new ArrayList<Municipios>();
        
        lista_entidades_2 = new ArrayList<Entidad>();
        lista_municipios_2 = new ArrayList<Municipios>();
        
        
        selectedCliente = new ArrayList<Cliente>();
        selectedEntidad = 1;
        model = ifaceCatCliente.getClientes();
        //lista_entidades = ifaceCatEntidad.getEntidades();

        setTitle("Catalogo de Clientes.");
        setViewEstate("init");
        
        lista_entidades = ifaceCatEntidad.getEntidades();      
        lista_entidades_2 = ifaceCatEntidad.getEntidades(); 
    }

    public ArrayList<Entidad> getLista_entidades()
    {
        return lista_entidades;
    }

    public void setLista_entidades(ArrayList<Entidad> lista_entidades) {
        this.lista_entidades = lista_entidades;
    }

    @Override
    public String delete() {
        if (!selectedCliente.isEmpty()) 
        {
            for (Cliente cl : selectedCliente) 
            {
                try
                {
                    ifaceCatCliente.deleteCliente(cl.getId_cliente());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro eliminado."));
                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + data.getNombre() + "."));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Elija un registro a eliminar."));
        }

        return "clientes";
    }

    @Override
    public String insert() {
        
         try 
         {
            System.out.println("data" + data.toString());
            if (ifaceCatCliente.insertCliente(data) == 0) 
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error"));
            } else 
            {
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
    public void backView() 
    {
        setTitle("Catalogo de Clientes");
        setViewEstate("init");
    }

    @Override
    public String update() 
    {
        try {
            ifaceCatCliente.updateCliente(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombre() + "."));
        }

        return "clientes";
    }

    @Override
    public void searchById() 
    {
        System.out.println("data :"+data.toString());
             
        setTitle("Editar Cliente.");
        setViewEstate("searchById");

    }
    
    public void viewNew() 
    {

        data = new Cliente();
        setTitle("Alta de Clientes");
        setViewEstate("new");
    }
    public void buscaMunicipios() 
    {
  
        lista_municipios =ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstado()));
        //data = new Cliente();
        //setTitle("Alta de Clientes");
        //setViewEstate("new");
    }
    public void buscaMunicipios2() 
    {
        
        lista_municipios_2 =ifaceCatMunicipio.getMunicipios(Integer.parseInt(data.getEstadoFiscal()));
        //data = new Cliente();
        //setTitle("Alta de Clientes");
        //setViewEstate("new");
    }
    public Cliente getCliente() 
    {
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


}
