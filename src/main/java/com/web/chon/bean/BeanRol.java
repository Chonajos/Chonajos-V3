package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.Rol;
import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceCatRol;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.ViewState;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el catlogo de Roles
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRol extends SimpleViewBean<Rol> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatRol ifaceCatRol;

    private ArrayList<Rol> selectedRol;
    private List<Rol> lstRol;

    private String title = "";
    public String viewEstate = "";

    @Override
    public void initModel() {

        data = new Rol();

        lstRol = new ArrayList<Rol>();
        selectedRol = new ArrayList<Rol>();

        lstRol = ifaceCatRol.getAll();

        setTitle("Catalogo de Roles");

    }

    @Override
    public String delete() {
        if (!selectedRol.isEmpty()) {
            for (Rol rol : selectedRol) {
                try {
                    if (ifaceCatRol.delete(rol.getIdRolPk()) == 1) {

                        JsfUtil.addSuccessMessage("Registro eliminado.");
                    } else {
                        JsfUtil.addErrorMessage("Ocurrio un error al intentar eliminar el registro :" + rol.getNombreRol() + ".");
                    }

                } catch (Exception ex) {
                    JsfUtil.addErrorMessage("Ocurrio un error al intentar eliminar el registro :" + rol.getNombreRol() + ".");

                }
            }
        } else {
            JsfUtil.addWarnMessage("Elija un registro a eliminar.");

        }

        return "roles";
    }

    @Override
    public String save() {
        try {
            if (ifaceCatRol.create(data) == 1) {
                JsfUtil.addSuccessMessage("Registro insertado.");

            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al intentar insertar el registro :" + data.getNombreRol() + ".");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar insertar el registro :" + data.getNombreRol() + ".");

        }
        backView();
        return "roles";
    }

    public String update() {
        try {
            if (ifaceCatRol.update(data) == 1) {
                JsfUtil.addSuccessMessage("Registro modificado.");
            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro :" + data.getNombreRol() + ".");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro :" + data.getNombreRol() + ".");
        }

        return "roles";
    }

    public void searchById() {
        setTitle("Editar Roles");
        state = ViewState.SEARCH;

    }

    public void viewNew() {
        data = new Rol();
        setTitle("Alta de Roles");
        actionNew();

    }

    public void backView() {
        setTitle("Catalogo de Bodegas");
        selectedRol = new ArrayList<Rol>();
        actionBack();
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

    public ArrayList<Rol> getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(ArrayList<Rol> selectedRol) {
        this.selectedRol = selectedRol;
    }

    public List<Rol> getLstRol() {
        return lstRol;
    }

    public void setLstRol(List<Rol> lstRol) {
        this.lstRol = lstRol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
