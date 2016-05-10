package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoCovenio;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class BeanExistencias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired private IfaceEmpaque ifaceEmpaque;
    @Autowired private IfaceTipoCovenio ifaceCovenio;
    @Autowired private IfaceCatBodegas ifaceCatBodegas;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private IfaceCatProvedores ifaceCatProvedores;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired private IfaceNegocioExistencia ifaceNegocioExistencia;

    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> model;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<EntradaMercancia2> lstEntradaMercancia;

    private ExistenciaProducto data;
    private Subproducto subProducto;
    private EntradaMercancia2 entradaMercancia;

    private String title = "";
    private String viewEstate = "";

    private int filtro;

    @PostConstruct
    public void init() {

        listaBodegas = new ArrayList<Bodega>();
        listaSucursales = new ArrayList<Sucursal>();
        listaProvedores = new ArrayList<Provedor>();
        listaTiposConvenio = new ArrayList<TipoConvenio>();

        data = new ExistenciaProducto();
        subProducto = new Subproducto();
        entradaMercancia = new EntradaMercancia2();

        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = ifaceCatProvedores.getProvedores();

        model = ifaceNegocioExistencia.getExistencias(null, null, null, null, null, null, null);

        listaBodegas = ifaceCatBodegas.getBodegas();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        listaTiposConvenio = ifaceCovenio.getTipos();

        setViewEstate("init");
        setTitle("Existencias");
        filtro = 1;
    }

    public void buscaExistencias() {
        BigDecimal idEntrada;
        if (entradaMercancia == null) {
            idEntrada = null;
        } else {
            idEntrada = entradaMercancia.getIdEmPK();
            if(idEntrada != null){subProducto = null;}
        }

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        model = ifaceNegocioExistencia.getExistencias(data.getIdSucursal(), data.getIdBodegaFK(), data.getIdProvedor(), idproductito, data.getIdTipoEmpaqueFK(), data.getIdTipoConvenio(), idEntrada);

    }

    public String updatePrecio() {
        if (validateMaxMin()) {
            if (ifaceNegocioExistencia.updatePrecio(data) == 1) {
                JsfUtil.addSuccessMessage("Actualización Exitosa.");
                return "existencias";
            } else {
                JsfUtil.addSuccessMessage("Error al Realizar la Operaión.");
                return null;
            }
        } else {
            JsfUtil.addErrorMessage("Error en la Validación de Datos Minimo : " + data.getPrecioMinimo() + " Maximo: " + data.getPrecioMaximo());
            return null;
        }

    }

    private boolean validateMaxMin() {

        if (data.getPrecioVenta().doubleValue() > (data.getPrecioMaximo().doubleValue())) {
            return false;
        } else if (data.getPrecioVenta().doubleValue() < data.getPrecioMinimo().doubleValue()) {
            return false;
        }

        return true;
    }

    public ArrayList<EntradaMercancia2> autoCompleteMercancia(String clave) {
        lstEntradaMercancia = ifaceEntradaMercancia.getSubEntradaByNombre(clave.toUpperCase());
        return lstEntradaMercancia;
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void searchById() {
        setViewEstate("search");
        setTitle("Mantenimiento de Precio");
    }

    public void cancel() {
        init();
    }

    public void onRowEdit(RowEditEvent event) {

        data = (ExistenciaProducto) event.getObject();

        System.out.println("editado " + data.toString());
        updatePrecio();

    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void resetValuesFilter(){
        data.setIdSucursal(null);
        data.setIdBodegaFK(null);
        data.setIdProvedor(null);
        data.setIdTipoEmpaqueFK(null);
        data.setIdTipoConvenio(null);
        entradaMercancia = new EntradaMercancia2();
        buscaExistencias();

    }
    
    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<ExistenciaProducto> getModel() {
        return model;
    }

    public void setModel(ArrayList<ExistenciaProducto> model) {
        this.model = model;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<Provedor> getListaProvedores() {
        return listaProvedores;
    }

    public void setListaProvedores(ArrayList<Provedor> listaProvedores) {
        this.listaProvedores = listaProvedores;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }

    public ArrayList<TipoConvenio> getListaTiposConvenio() {
        return listaTiposConvenio;
    }

    public void setListaTiposConvenio(ArrayList<TipoConvenio> listaTiposConvenio) {
        this.listaTiposConvenio = listaTiposConvenio;
    }

    public ArrayList<EntradaMercancia2> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia2> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public ExistenciaProducto getData() {
        return data;
    }

    public void setData(ExistenciaProducto data) {
        this.data = data;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public EntradaMercancia2 getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(EntradaMercancia2 entradaMercancia) {
        this.entradaMercancia = entradaMercancia;
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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    
}
