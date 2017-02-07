package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.ViewState;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el catlogo de de Bodegas
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanBodega extends SimpleViewBean<Bodega> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatBodegas ifaceCatBodega;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private ArrayList<Bodega> selectedBodega;
    private ArrayList<Sucursal> lstSucursal;

    private UsuarioDominio usuario;

    private String title = "";
    public String viewEstate = "";

    @Override
    public void initModel() {

        usuario = context.getUsuarioAutenticado();

        lstSucursal = new ArrayList<Sucursal>();
        lstSucursal = ifaceCatSucursales.getSucursales();

        data = new Bodega();
        model = new PaginationLazyDataModel<Bodega, BigDecimal>(ifaceCatBodega, data);

        selectedBodega = new ArrayList<Bodega>();

        setTitle("Catalogo de Bodegas");

    }

    @Override
    public String delete() {
        if (!selectedBodega.isEmpty()) {
            for (Bodega bodega : selectedBodega) {
                try {
                    ifaceCatBodega.delete(bodega.getIdBodegaPK());

                    JsfUtil.addSuccessMessage("Registro eliminado.");

                } catch (Exception ex) {
                    JsfUtil.addErrorMessage("Ocurrio un error al intentar eliminar el registro :" + bodega.getNombreBodega() + ".");

                }
            }
        } else {
            JsfUtil.addWarnMessage("Elija un registro a eliminar.");

        }

        return "bodega";
    }

    @Override
    public String save() {
        try {
            if (ifaceCatBodega.create(data) == 1) {
                JsfUtil.addSuccessMessage("Registro insertado.");

            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al intentar insertar el registro :" + data.getNombreBodega() + ".");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar insertar el registro :" + data.getNombreBodega() + ".");

        }
        backView();
        return "bodega";
    }

    public String update() {
        try {
            if (ifaceCatBodega.update(data) == 1) {
                JsfUtil.addSuccessMessage("Registro modificado.");
            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro :" + data.getNombreBodega() + ".");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro :" + data.getNombreBodega() + ".");
        }

        return "bodega";
    }

    public void searchById() {
        setTitle("Editar Bodega");
        state = ViewState.SEARCH;

    }

    public void viewNew() {
        data = new Bodega();
        
        if (usuario.getPerId() != 1) {
            data.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        }
        
        setTitle("Alta de Bodegas");
        actionNew();

    }

    public void backView() {
        initModel();
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

    public ArrayList<Bodega> getSelectedBodega() {
        return selectedBodega;
    }

    public void setSelectedBodega(ArrayList<Bodega> selectedBodega) {
        this.selectedBodega = selectedBodega;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

}
