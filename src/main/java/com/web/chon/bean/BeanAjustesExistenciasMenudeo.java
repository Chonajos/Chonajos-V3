package com.web.chon.bean;

import com.web.chon.dominio.AjusteExistenciaMenudeo;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAjusteExistenciaMenudeo;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoCovenio;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class BeanAjustesExistenciasMenudeo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired
    private IfaceAjusteExistenciaMenudeo ifaceAjusteExistenciaMenudeo;

    private List<ExistenciaMenudeo> model;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;

    private ExistenciaMenudeo data;
    private UsuarioDominio usuario;
    private Subproducto subProducto;
    private AjusteExistenciaMenudeo ajusteExistenciaMenudeo;

    private String title = "";
    private String viewEstate = "";

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();

        data = new ExistenciaMenudeo();
        
        data.setIdSucursalFk(new BigDecimal(String.valueOf(usuario.getSucId())));

        listaSucursales = ifaceCatSucursales.getSucursales();

        model = ifaceExistenciaMenudeo.getExistenciasMenudeoByIdSucursal(data.getIdSucursalFk());

        lstTipoEmpaque = ifaceEmpaque.getEmpaques();

        setViewEstate("init");
        setTitle("Ajuste de Existencias Menudeo");
    }

    public void buscaExistencias() {
        if(subProducto != null){
            model = ifaceExistenciaMenudeo.getExistenciasMenudeoByIdSucursalAndIdSubproducto(data.getIdSucursalFk(),subProducto.getIdSubproductoPk());
        }else{
            model = ifaceExistenciaMenudeo.getExistenciasMenudeoByIdSucursal(data.getIdSucursalFk());
        }
        
    }

    public void onRowEdit(RowEditEvent event) {

        ExistenciaMenudeo existenciaMenudeoOld = new ExistenciaMenudeo();
        ExistenciaMenudeo existenciaMenudeoNew = new ExistenciaMenudeo();
        ajusteExistenciaMenudeo = new AjusteExistenciaMenudeo();
        
        existenciaMenudeoNew = (ExistenciaMenudeo) event.getObject();
        existenciaMenudeoOld = ifaceExistenciaMenudeo.getExistenciasMenudeoById(existenciaMenudeoNew.getIdExMenPk());

        if (ifaceExistenciaMenudeo.updateExistenciaMenudeo(existenciaMenudeoNew) != 0) {

            ajusteExistenciaMenudeo.setEmpaqueAjustados(existenciaMenudeoNew.getCantidadEmpaque());
            ajusteExistenciaMenudeo.setEmpaqueAnterior(existenciaMenudeoOld.getCantidadEmpaque());
            ajusteExistenciaMenudeo.setFechaAjuste(context.getFechaSistema());
            ajusteExistenciaMenudeo.setIdExistenciaMenudeoFK(existenciaMenudeoNew.getIdExMenPk());
            ajusteExistenciaMenudeo.setIdSucursalFK(existenciaMenudeoNew.getIdSucursalFk());
            ajusteExistenciaMenudeo.setIdTipoEmpaqueFK(existenciaMenudeoNew.getIdTipoEmpaqueFK());
            ajusteExistenciaMenudeo.setIdUsuarioAjusteFK(usuario.getIdUsuario());
            ajusteExistenciaMenudeo.setKilosAjustados(existenciaMenudeoNew.getKilos());
            ajusteExistenciaMenudeo.setKilosAnteior(existenciaMenudeoOld.getKilos());
            ajusteExistenciaMenudeo.setObservaciones(existenciaMenudeoNew.getObservaciones());
            ajusteExistenciaMenudeo.setMotivoAjuste(existenciaMenudeoNew.getMotivoAjuste());

            if (ifaceAjusteExistenciaMenudeo.insert(ajusteExistenciaMenudeo) == 0) {
                JsfUtil.addErrorMessage("Error al Modificar el Registro.");
            }
        }

        JsfUtil.addSuccessMessage("Se Modifico el Registro Existosamente.");
    }

    public void onRowCancel(RowEditEvent event) {

        JsfUtil.addWarnMessage("Se Cancelo la Modificación.");

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public List<ExistenciaMenudeo> getModel() {
        return model;
    }

    public void setModel(List<ExistenciaMenudeo> model) {
        this.model = model;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }

    public ExistenciaMenudeo getData() {
        return data;
    }

    public void setData(ExistenciaMenudeo data) {
        this.data = data;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
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

}
