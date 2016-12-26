package com.web.chon.bean;

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
    @Autowired private PlataformaSecurityContext context;

    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> model;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<EntradaMercancia> lstEntradaMercancia;

    private ExistenciaProducto data;
    private Subproducto subProducto;
    private EntradaMercancia entradaMercancia;

    private String title = "";
    private String viewEstate = "";

    private BigDecimal totalKilos;
    private BigDecimal totalCajas;
    private BigDecimal totalCosto;
    private BigDecimal totalPrecioMinimo;
    private BigDecimal totalPrecioVenta;
    private BigDecimal ZERO = new BigDecimal(0);

    private UsuarioDominio usuario;
    private int filtro;

    @PostConstruct
    public void init() {

        listaBodegas = new ArrayList<Bodega>();
        listaSucursales = new ArrayList<Sucursal>();
        listaProvedores = new ArrayList<Provedor>();
        listaTiposConvenio = new ArrayList<TipoConvenio>();
        usuario = context.getUsuarioAutenticado();

        data = new ExistenciaProducto();
        subProducto = new Subproducto();
        entradaMercancia = new EntradaMercancia();

        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = ifaceCatProvedores.getProvedores();

        listaBodegas = ifaceCatBodegas.getBodegas();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        listaTiposConvenio = ifaceCovenio.getTipos();

        data.setIdSucursal(new BigDecimal(usuario.getSucId()));
        setViewEstate("init");
        setTitle("Existencias");
        filtro = 1;
        buscaExistencias();

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
        model = ifaceNegocioExistencia.getExistencias(data.getIdSucursal(), data.getIdBodegaFK(), data.getIdProvedor(), idproductito, data.getIdTipoEmpaqueFK(), data.getIdTipoConvenio(), idEntrada,null);
        getTotalCajasKilosAndCostoExistencia();

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

        data = (ExistenciaProducto) event.getObject();
        updatePrecio();

    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void resetValuesFilter() {
        data.setIdSucursal(null);
        data.setIdBodegaFK(null);
        data.setIdProvedor(null);
        data.setIdTipoEmpaqueFK(null);
        data.setIdTipoConvenio(null);
        entradaMercancia = new EntradaMercancia();
        buscaExistencias();

    }

    public void getTotalCajasKilosAndCostoExistencia() {

        totalPrecioMinimo = ZERO;
        totalPrecioVenta = ZERO;
        totalCosto = ZERO;
        totalCajas = ZERO;
        totalKilos = ZERO;

        for (ExistenciaProducto dominio : model) {

            //Obtiene el total de kilos y cajas que hay en existencias
            totalCajas = totalCajas.add(dominio.getCantidadPaquetes());
            totalKilos = totalKilos.add(dominio.getKilosTotalesProducto());

            //Calcula existencias en costo
            dominio = calculaCostos(dominio);
            totalCosto = totalCosto.add(dominio.getInventariocosto());
            totalPrecioVenta = totalPrecioVenta.add(dominio.getInventarioPrecioVenta());
            totalPrecioMinimo = totalPrecioMinimo.add(dominio.getInventarioPrecioMinimo());

        }
    }

    private ExistenciaProducto calculaCostos(ExistenciaProducto dominio) {
        BigDecimal precioMinimo = ZERO;
        BigDecimal precioVenta = ZERO;
        if (!dominio.getCantidadPaquetes().equals(ZERO)) {
            precioMinimo = dominio.getPrecioMinimo() == null ? ZERO : dominio.getPrecioMinimo();
            precioVenta = dominio.getPrecioVenta() == null ? ZERO : dominio.getPrecioVenta();
            switch (dominio.getIdTipoConvenio().intValue()) {
                case 1://costo
                    dominio.setInventariocosto(dominio.getConvenio().multiply(dominio.getKilosTotalesProducto()));
                    break;
                default:// default 2 y 3 pacto y comision
                    dominio.setInventariocosto(precioMinimo.multiply(dominio.getKilosTotalesProducto()));
                    break;
            }

            dominio.setInventarioPrecioVenta(precioVenta.multiply(dominio.getKilosTotalesProducto()));
            dominio.setInventarioPrecioMinimo(precioMinimo.multiply(dominio.getKilosTotalesProducto()));

        } else {

            dominio.setInventariocosto(ZERO);
            dominio.setInventarioPrecioVenta(ZERO);
            dominio.setInventarioPrecioMinimo(ZERO);
        }

        return dominio;
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

    public BigDecimal getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(BigDecimal totalCosto) {
        this.totalCosto = totalCosto;
    }

    public BigDecimal getTotalPrecioMinimo() {
        return totalPrecioMinimo;
    }

    public void setTotalPrecioMinimo(BigDecimal totalPrecioMinimo) {
        this.totalPrecioMinimo = totalPrecioMinimo;
    }

    public BigDecimal getTotalPrecioVenta() {
        return totalPrecioVenta;
    }

    public void setTotalPrecioVenta(BigDecimal totalPrecioVenta) {
        this.totalPrecioVenta = totalPrecioVenta;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    
}