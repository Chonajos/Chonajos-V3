package com.web.chon.bean;

import com.web.chon.dominio.Producto;
import com.web.chon.service.IfaceProducto;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el catlogo de productos
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanProducto implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceProducto ifaceProducto;

    private ArrayList<Producto> model;
    private ArrayList<Producto> selectedProducto;

    private String title = "";
    public String viewEstate = "";
    public Producto data;

    @PostConstruct
    public void init() {

        data = new Producto();
        model = new ArrayList<Producto>();
        selectedProducto = new ArrayList<Producto>();
        model = ifaceProducto.getProductos();

        setTitle("Catalogo de Categorías.");
        setViewEstate("init");

    }

    @Override
    public String delete() {

        if (!selectedProducto.isEmpty()) {
            for (Producto producto : selectedProducto) {
                try {
                    ifaceProducto.deleteProducto(producto.getIdProductoPk());
                    
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro eliminado."));
                    send();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + data.getNombreProducto() + "."));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Elija un registro a eliminar."));
        }

        return "categoria";
    }

    @Override
    public String insert() {
        try {
            data.setIdProductoPk(TiempoUtil.rellenaEspacios(ifaceProducto.getLastIdCategoria()));
            ifaceProducto.insertarProducto(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar insertar el registro :" + data.getNombreProducto() + "."));
        }
        backView();
        return "categoria";
    }

    @Override
    public String update() {

        try {
            ifaceProducto.updateProducto(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombreProducto() + "."));
        }

        return "categoria";
    }

    @Override
    public void searchById() {
        setTitle("Editar Categoría.");
        setViewEstate("searchById");

        System.out.println("data" + data.getDescripcionProducto());

    }

    public void viewNew() {
        data = new Producto();
        setTitle("Alta de Categorías.");
        setViewEstate("new");
    }

    public void backView() {
        setTitle("Catalogo de Categorías.");
        setViewEstate("init");
    }

    public ArrayList<Producto> getModel() {
        return model;
    }

    public void setModel(ArrayList<Producto> model) {
        this.model = model;
    }

    public Producto getData() {
        return data;
    }

    public void setData(Producto data) {
        this.data = data;
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

    public ArrayList<Producto> getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(ArrayList<Producto> selectedProducto) {
        this.selectedProducto = selectedProducto;
    }
    
   
       private final static String CHANNEL = "/notify";
     
    private String summary;
     
    private String detail;
     
    public String getSummary() {
        return "hola ";
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
     
    public String getDetail() {
        return "detail";
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
     
    public void send() {
        try{
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, new FacesMessage(StringEscapeUtils.escapeHtml(summary), StringEscapeUtils.escapeHtml(detail)));
        System.out.println("terminado");
        }catch(Exception e){
            System.out.println("error"+e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void printer(){
        System.out.println("Impreso");
    }
            

}
