/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.service.IfaceCatCliente;
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
    private ArrayList<Cliente> model;
    private ArrayList<Cliente> selectedCliente;
    private String title;
    private String viewEstate;
    private Cliente data;
    
    @PostConstruct
    public void init() {

        data = new Cliente();
        model = new ArrayList<Cliente>();
        selectedCliente = new ArrayList<Cliente>();
        model = ifaceCatCliente.getClientes();

        setTitle("Catalogo de Clientes.");
        setViewEstate("init");

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
    public String update() {
        try {
            ifaceCatCliente.updateCliente(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombre() + "."));
        }

        return "clientes";
    }

    @Override
    public void searchById() {
        setTitle("Editar Cliente.");
        setViewEstate("searchById");

    }
    
    public void viewNew() 
    {
        data = new Cliente();
        setTitle("Alta de Clientes");
        setViewEstate("new");
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


}
