package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.AccesMenu;
import com.web.chon.dominio.Rol;
import com.web.chon.service.IfaceAccesMenu;
import com.web.chon.service.IfaceCatRol;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.ViewState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la administracion de pantallas por rolres
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanPermisoRol extends SimpleViewBean<AccesMenu> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IfaceAccesMenu ifaceAccesMenu;
    
    @Autowired
    private IfaceCatRol ifaceRol;
    
    private ArrayList<AccesMenu> selectedAccesMenu;
    private List<AccesMenu> lstAccesMenu;
    private List<Rol> lstRol;
    
    private String title = "";
    
    @Override
    public void initModel() {
        
        data = new AccesMenu();
        
        lstAccesMenu = new ArrayList<AccesMenu>();
        lstRol = ifaceRol.getAll();
        setTitle("Administrar Acceso a pantallas por Rol");
        
    }
    
    @Override
    public String delete() {
        
        return "permisoRol";
    }
    
    @Override
    public String save() {
        try {
            
            for (AccesMenu accesMenu : lstAccesMenu) {
                accesMenu.setIdRol(data.getIdRol());
                
                ifaceAccesMenu.create(accesMenu);
            }
            JsfUtil.addSuccessMessage("Registro insertado.");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar insertar el registro.");
            
        }
        
        return "permisoRol";
    }
    
    public String update() {
        return null;
    }
    
    public void searchById() {
        lstAccesMenu = ifaceAccesMenu.getAccesosMenuPorIdRol(data.getIdRol());
        
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public ArrayList<AccesMenu> getSelectedAccesMenu() {
        return selectedAccesMenu;
    }
    
    public void setSelectedAccesMenu(ArrayList<AccesMenu> selectedAccesMenu) {
        this.selectedAccesMenu = selectedAccesMenu;
    }
    
    public List<AccesMenu> getLstAccesMenu() {
        return lstAccesMenu;
    }
    
    public void setLstAccesMenu(List<AccesMenu> lstAccesMenu) {
        this.lstAccesMenu = lstAccesMenu;
    }
    
    public List<Rol> getLstRol() {
        return lstRol;
    }
    
    public void setLstRol(List<Rol> lstRol) {
        this.lstRol = lstRol;
    }
    
}
