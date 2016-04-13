package com.web.chon.bean;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el Mantenimiento a Precios
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanMantenimientoPrecio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceMantenimientoPrecio ifaceMantenimientoPrecio;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    
    private ArrayList<Sucursal> listaSucursales;

    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;

    private String title = "";
    private String viewEstate = "";
    private String idProductoSelecionado = "";
    private boolean update;
    private MantenimientoPrecios data;
    private Subproducto subproducto;

    @PostConstruct
    public void init() {
        
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data = new MantenimientoPrecios();
        subproducto = new Subproducto();
        lstProducto = new ArrayList<Subproducto>();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();

        setTitle("Mantenimiento de Precios.");
        setViewEstate("init");

    }

    public String updatePrecio() {

        if (update) 
        {
            if (ifaceMantenimientoPrecio.updateMantenimientoPrecio(data) == 1) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se puede Modificar el Registro."));
            }
        } else {
            insertarPrecio();
        }
        return "mantenimientoPrecios";
    }
    public void print()
    {
        subproducto = new Subproducto();
        int temporal = data.getIdSucursal();
        data = new MantenimientoPrecios();
        data.setIdSucursal(temporal);
        
    }

    public String insertarPrecio() {

        if (ifaceMantenimientoPrecio.insertarMantenimientoPrecio(data) == 1) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro Insertado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se puede Insertar el Registro."));
        }
        return "mantenimientoPrecios";
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void searchById() {
        int idEmpaque = data.getIdTipoEmpaquePk() == null ? 0 : data.getIdTipoEmpaquePk().intValue();
        String idSubProducto = subproducto.getIdSubproductoPk() == null ? "" : subproducto.getIdSubproductoPk();
        int idSucursal = data.getIdSucursal();
        System.out.println("BeanIdSucu : "+data.getIdSucursal());
        data = ifaceMantenimientoPrecio.getMantenimientoPrecioById(idSubProducto, idEmpaque,idSucursal);
   
        if (data.getIdSubproducto() != null && (!data.getIdSubproducto().equals(""))) {
            update = true;
        } else 
        {
            update = false;
        }
        data.setIdSucursal(idSucursal);
        data.setIdSubproducto(idSubProducto);
        data.setIdTipoEmpaquePk(new BigDecimal(idEmpaque));
       
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

    public MantenimientoPrecios getData() {
        return data;
    }

    public void setData(MantenimientoPrecios data) {
        this.data = data;
    }

    public String getIdProductoSelecionado() {
        return idProductoSelecionado;
    }

    public void setIdProductoSelecionado(String idProductoSelecionado) {
        this.idProductoSelecionado = idProductoSelecionado;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public Subproducto getSubproducto() {
        return subproducto;
    }

    public void setSubproducto(Subproducto subproducto) {
        System.out.println("set subproducto:" + subproducto.toString());
        this.subproducto = subproducto;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }
     public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }
    

}
