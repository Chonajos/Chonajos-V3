
package com.web.chon.bean;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.service.IfaceBuscaVenta;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BeanBuscaVenta implements Serializable, BeanSimple 
{
    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceBuscaVenta ifaceBuscaVenta;
    private ArrayList<BuscaVenta> model;
    private String title;
    private String viewEstate;
    private BuscaVenta data;
    private BuscaVenta dataModel; //Modelo con un solo Objeto
    private boolean statusButtonPagar;
    private int idVentaTemporal; //utilizado para comprobacion de venta

   
    private ArrayList<BuscaVenta> selectedVenta;

   
    @PostConstruct
    public void init() 
    { 
        data = new BuscaVenta();
        model = new ArrayList<BuscaVenta>();
        dataModel = ifaceBuscaVenta.getVentaById(0);   
        model.add(dataModel);
        setTitle("Búsqueda de Ventas");
        setViewEstate("init");
        statusButtonPagar=true;
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateVenta() 
    {
        if(data.getStatusFK()==2)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La venta :" + data.getIdVenta() + " Ya se encuentra pagada."));
     
        }
        if(data.getIdVenta()!=idVentaTemporal)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No coincide el numero de venta  :" + data.getIdVenta() + " con la búsqueda."));
     
        }
        else
        {
            try 
            {
                ifaceBuscaVenta.updateCliente(data.getIdVenta());
                searchById();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Venta Pagada"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar pagar la venta con el folio:" + data.getIdVenta() + "."));
            }
            
        }

        //return "buscaVentas";
    }

    @Override
    public void searchById() 
    {
        statusButtonPagar=false;
        data = ifaceBuscaVenta.getVentaById(data.getIdVenta());   
        model = new ArrayList<BuscaVenta>();  
        model.add(data);
        idVentaTemporal=data.getIdVenta();
        if(data.getIdVenta()==0)
        {
            statusButtonPagar=true;
        }
    }
    

    public ArrayList<BuscaVenta> getModel() {
        return model;
    }

    public void setModel(ArrayList<BuscaVenta> model) {
        this.model = model;
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

    public BuscaVenta getData() {
        return data;
    }

    public void setData(BuscaVenta data) {
        this.data = data;
    }

    public ArrayList<BuscaVenta> getSelectedVenta() {
        return selectedVenta;
    }

    public void setSelectedVenta(ArrayList<BuscaVenta> selectedVenta) {
        this.selectedVenta = selectedVenta;
    }
    public BuscaVenta getDataModel() {
        return dataModel;
    }

    public void setDataModel(BuscaVenta dataModel) {
        this.dataModel = dataModel;
    }
    
    public IfaceBuscaVenta getIfaceBuscaVenta() {
        return ifaceBuscaVenta;
    }

    public void setIfaceBuscaVenta(IfaceBuscaVenta ifaceBuscaVenta) {
        this.ifaceBuscaVenta = ifaceBuscaVenta;
    }

    public boolean isStatusButtonPagar() {
        return statusButtonPagar;
    }

    public void setStatusButtonPagar(boolean statusButtonPagar) {
        this.statusButtonPagar = statusButtonPagar;
    }
    public int getIdVentaTemporal() {
        return idVentaTemporal;
    }

    public void setIdVentaTemporal(int idVentaTemporal) {
        this.idVentaTemporal = idVentaTemporal;
    }


    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
