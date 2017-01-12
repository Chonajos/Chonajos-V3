package com.web.chon.bean;

import com.web.chon.dominio.AjusteExistenciaMayoreo;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAjusteExistenciaMayoreo;
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
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanAjustesExistenciasMayoreo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceTipoCovenio ifaceCovenio;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceAjusteExistenciaMayoreo ifaceAjusteExistenciaMayoreo;

    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> model;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<EntradaMercancia> lstEntradaMercancia;

    private UsuarioDominio usuario;
    private ExistenciaProducto data;
    private Subproducto subProducto;
    private ExistenciaProducto dataEdit;
    private EntradaMercancia entradaMercancia;
    private AjusteExistenciaMayoreo ajusteExistenciaMayoreo;

    private String title = "";
    private String viewEstate = "";

    private BigDecimal totalKilos;
    private BigDecimal totalCajas;

    private int filtro;

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();

        listaBodegas = new ArrayList<Bodega>();
        listaSucursales = new ArrayList<Sucursal>();
        listaProvedores = new ArrayList<Provedor>();
        listaTiposConvenio = new ArrayList<TipoConvenio>();

        data = new ExistenciaProducto();
        subProducto = new Subproducto();
        entradaMercancia = new EntradaMercancia();

        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = ifaceCatProvedores.getProvedores();

        //1 ESTATUS DE CARRO ES ACTIVO
        model = ifaceNegocioExistencia.getExistencias(new BigDecimal(usuario.getSucId()), null, null, null, null, null, null, null, new BigDecimal(1));
        getTotalCajasKilos();

        data.setIdSucursal(new BigDecimal(usuario.getSucId()));

        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursal());
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
            if (idEntrada != null) {
                subProducto = null;
            }
        }

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        model = ifaceNegocioExistencia.getExistencias(data.getIdSucursal(), data.getIdBodegaFK(), data.getIdProvedor(), idproductito, data.getIdTipoEmpaqueFK(), data.getIdTipoConvenio(), idEntrada, data.getCarroSucursal(),new BigDecimal(1));
        getTotalCajasKilos();

    }

    public String updatePrecio() {
        if (validateMaxMin()) {
            if (ifaceNegocioExistencia.updatePrecio(dataEdit) == 1) {
                JsfUtil.addSuccessMessage("Actualización Exitosa.");
                return "existencias";
            } else {
                JsfUtil.addSuccessMessage("Error al Realizar la Operaión.");
                return null;
            }
        } else {
            JsfUtil.addErrorMessage("Error en la Validación de Datos Minimo : " + dataEdit.getPrecioMinimo() + " Maximo: " + dataEdit.getPrecioMaximo());
            return null;
        }

    }

    private boolean validateMaxMin() {

        if (dataEdit.getPrecioVenta().doubleValue() > (dataEdit.getPrecioMaximo().doubleValue())) {
            return false;
        } else if (dataEdit.getPrecioVenta().doubleValue() < dataEdit.getPrecioMinimo().doubleValue()) {
            return false;
        }

        return true;
    }

    public ArrayList<EntradaMercancia> autoCompleteMercancia(String clave) {
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

        ExistenciaProducto existenciaMayoreoOld = new ExistenciaProducto();
        ExistenciaProducto existenciaProductoNew = new ExistenciaProducto();
        ExistenciaProducto ExistenciaProducto = new ExistenciaProducto();

//        if (existenciaProductoNew.getSalidaEntrada() == null) {
//            JsfUtil.addErrorMessage("Selecione si es una salida o es una entrada.");
//            return;
//        }
        ajusteExistenciaMayoreo = new AjusteExistenciaMayoreo();

        existenciaProductoNew = (ExistenciaProducto) event.getObject();
        ExistenciaProducto = ifaceNegocioExistencia.getExistenciaById(existenciaProductoNew.getIdExistenciaProductoPk());

        if (ExistenciaProducto != null && ExistenciaProducto.getIdExistenciaProductoPk() != null) {
            existenciaMayoreoOld = ExistenciaProducto;
        }

        if (existenciaProductoNew.getSalidaEntrada().trim().equalsIgnoreCase("Entrada")) {
            System.out.println("enreada");
            existenciaProductoNew.setKilosTotalesProducto(existenciaProductoNew.getKilosTotalesProducto().add(existenciaProductoNew.getKilosAjustar()));
            existenciaProductoNew.setCantidadPaquetes(existenciaProductoNew.getCantidadPaquetes().add(existenciaProductoNew.getEmpaquesAjustar()));
        } else {
            System.out.println("salida");
            existenciaProductoNew.setKilosTotalesProducto(existenciaProductoNew.getKilosTotalesProducto().subtract(existenciaProductoNew.getKilosAjustar()));
            existenciaProductoNew.setCantidadPaquetes(existenciaProductoNew.getCantidadPaquetes().subtract(existenciaProductoNew.getEmpaquesAjustar()));
        }

        if (ifaceNegocioExistencia.update(existenciaProductoNew) != 0) {
            System.out.println("Existencia modificada");
            ajusteExistenciaMayoreo.setEmpaqueAjustados(existenciaProductoNew.getCantidadPaquetes());
            ajusteExistenciaMayoreo.setEmpaqueAnterior(existenciaMayoreoOld.getCantidadPaquetes());
            ajusteExistenciaMayoreo.setFechaAjuste(context.getFechaSistema());
            ajusteExistenciaMayoreo.setIdExpFk(existenciaProductoNew.getIdExistenciaProductoPk());
            ajusteExistenciaMayoreo.setIdUsuarioAjusteFK(usuario.getIdUsuario());
            ajusteExistenciaMayoreo.setKilosAjustados(existenciaProductoNew.getKilosTotalesProducto());
            ajusteExistenciaMayoreo.setKilosAnteior(existenciaMayoreoOld.getKilosTotalesProducto());
            ajusteExistenciaMayoreo.setObservaciones(existenciaProductoNew.getObservaciones());
            ajusteExistenciaMayoreo.setMotivoAjuste(existenciaProductoNew.getMotivoAjuste());

            if (ifaceAjusteExistenciaMayoreo.insert(ajusteExistenciaMayoreo) == 0) {
                JsfUtil.addErrorMessage("Error al Modificar el Registro.");
            }
        }
        model.clear();
        existenciaMayoreoOld = new ExistenciaProducto();
        existenciaProductoNew = new ExistenciaProducto();
        ExistenciaProducto = new ExistenciaProducto();
        buscaExistencias();
        JsfUtil.addSuccessMessage("Se Modifico el Registro Exitosamente.");

    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void resetValuesFilter() {

        if (usuario.getPerId() == 1) {
            data.setIdSucursal(null);
        }

        listaBodegas = ifaceCatBodegas.getBodegas();

        data.setIdBodegaFK(null);
        data.setIdProvedor(null);
        data.setIdTipoEmpaqueFK(null);
        data.setIdTipoConvenio(null);

        entradaMercancia = new EntradaMercancia();
        buscaExistencias();

    }

    public void getTotalCajasKilos() {

        totalCajas = new BigDecimal(0);
        totalKilos = new BigDecimal(0);

        for (ExistenciaProducto dominio : model) {

            totalCajas = totalCajas.add(dominio.getCantidadPaquetes());
            totalKilos = totalKilos.add(dominio.getKilosTotalesProducto());

        }
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

    public ArrayList<EntradaMercancia> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia> lstEntradaMercancia) {
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

    public EntradaMercancia getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(EntradaMercancia entradaMercancia) {
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

    public BigDecimal getTotalCajas() {
        return totalCajas;
    }

    public void setTotalCajas(BigDecimal totalCajas) {
        this.totalCajas = totalCajas;
    }

}
