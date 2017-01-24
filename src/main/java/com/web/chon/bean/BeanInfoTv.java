package com.web.chon.bean;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el Mantenimiento a Precios
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("session")
public class BeanInfoTv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;

    private ArrayList<MantenimientoPrecios> model;

    private String viewEstate = "";
    private UsuarioDominio usuarioDominio;
    private ArrayList<Subproducto> lstProductos;
    private ArrayList<Subproducto> lstTempShow;
    private Subproducto subproducto;
    private int cont;
    private int registroShow = 5;

    @PostConstruct
    public void init() {

        usuarioDominio = context.getUsuarioAutenticado();
        lstProductos = new ArrayList<Subproducto>();
        lstTempShow = new ArrayList<Subproducto>();

        cont = 0;
        generarListaMostrar();

    }

    public void generarListaMostrar() {
        lstTempShow.clear();
        lstProductos = ifaceSubProducto.getSubProductosIdSucursal(new BigDecimal(usuarioDominio.getSucId()));
        for (int i = cont; i <= cont + registroShow && i <= lstProductos.size() - 1; i++) {

            lstTempShow.add(lstProductos.get(i));
        }

        cont += registroShow + 1;

        if (cont >= lstProductos.size()) {
            cont = 0;
        }

    }

    public StreamedContent getProductImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        String imageType = "image/jpg";

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String idSubProducto = context.getExternalContext().getRequestParameterMap().get("idSubproducto");
            for (Subproducto dominio : lstTempShow) {
                if (dominio.getIdSubproductoPk().equals(idSubProducto)) {
                    subproducto = dominio;
                    break;
                }

            }

            byte[] image = subproducto.getFichero();
            return new DefaultStreamedContent(new ByteArrayInputStream(image), imageType, subproducto.getIdSubproductoPk());

        }
    }

    public String getViewEstate() {
        return viewEstate;
    }

    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public ArrayList<MantenimientoPrecios> getModel() {
        return model;
    }

    public void setModel(ArrayList<MantenimientoPrecios> model) {
        this.model = model;
    }

    public ArrayList<Subproducto> getLstProductos() {
        return lstProductos;
    }

    public void setLstProductos(ArrayList<Subproducto> lstProductos) {
        this.lstProductos = lstProductos;
    }

    public ArrayList<Subproducto> getLstTempShow() {
        return lstTempShow;
    }

    public void setLstTempShow(ArrayList<Subproducto> lstTempShow) {
        this.lstTempShow = lstTempShow;
    }

    public Subproducto getSubproducto() {
        return subproducto;
    }

    public void setSubproducto(Subproducto subproducto) {
        this.subproducto = subproducto;
    }

}
