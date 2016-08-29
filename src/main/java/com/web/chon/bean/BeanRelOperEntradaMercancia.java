package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
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
import com.web.chon.service.IfaceCatStatusVenta;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceProducto;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoCovenio;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la Relacion de Operaciones para entrada de mercancias
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRelOperEntradaMercancia implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceTipoCovenio ifaceCovenio;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;

    private ArrayList<Provedor> lstProvedor;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<EntradaMercancia> lstEntradaMercancia;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;

    private EntradaMercancia data;
    private EntradaMercanciaProducto dataProducto;
    private EntradaMercanciaProducto dataProductoNuevo;
    private EntradaMercanciaProducto dataProductEdit;
    private UsuarioDominio usuario;
    private Subproducto subProducto;
    private EntradaMercanciaProductoPaquete dataPaquete;

    private String title;
    private String viewEstate;

    private BigDecimal totalKilos;

    // ----- Variables para Filtros ----//
    private BigDecimal idSucursal;
    private BigDecimal idProvedor;
    private boolean enableCalendar;
    private Date fechaFin;
    private Date fechaInicio;
    private Date fechaFiltroFin;
    private Date fechaFiltroInicio;
    private int filtro;
    private Provedor provedor;

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        data = new EntradaMercancia();
        dataProducto = new EntradaMercanciaProducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        provedor = new Provedor();
        filtro = 1;
        verificarCombo();
        enableCalendar = true;
        buscar();

        dataPaquete = new EntradaMercanciaProductoPaquete();

        /*Validacion de perfil administrador*/
//        if (usuario.getPerId() != 1) {
//            data.setIdSucursalFK(new BigDecimal(usuario.getSucId()));
//        }
        listaTiposConvenio = new ArrayList<TipoConvenio>();
        listaTiposConvenio = ifaceCovenio.getTipos();
        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(new BigDecimal(usuario.getSucId()));

        setTitle("Relación de Operaciónes Entrada de Mercancia Mayoreo");
        setViewEstate("init");
        dataProductoNuevo = new EntradaMercanciaProducto();

    }

    public void verificarCombo() {
        if (filtro == -1) {
            //se habilitan los calendarios.
            fechaFiltroInicio = null;
            fechaFiltroFin = null;
            enableCalendar = false;
        } else {
            switch (filtro) {
                case 1:
                    fechaFiltroInicio = new Date();
                    fechaFiltroFin = new Date();
                    break;
                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(new Date());
                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
                    break;
                default:
                    fechaFiltroFin = null;
                    fechaFiltroFin = null;
                    break;
            }
            enableCalendar = true;
        }
    }

    public void buscar() {
        if (fechaFiltroInicio == null || fechaFiltroFin == null) {
            JsfUtil.addErrorMessageClean("Favor de ingresar un rango de fechas");
        } else {
            if (provedor == null) {
                provedor = new Provedor();
                provedor.setIdProvedorPK(null);
            }
            BigDecimal idProvedor = provedor == null ? null : provedor.getIdProvedorPK();
            lstEntradaMercancia = ifaceEntradaMercancia.getEntradaProductoByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal, idProvedor);

        }
    }

    public void registrarPaquete() {

    }

    public void calculaPesoNeto() {
        System.out.println("Entro a metodo");
        if (dataProductoNuevo.getKilosTotalesProducto() != null && dataProductoNuevo.getPesoTara() != null) {

            BigDecimal h = dataProductoNuevo.getKilosTotalesProducto().subtract(dataProductoNuevo.getPesoTara(), MathContext.UNLIMITED);
            //dataProducto.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED));

            int t = h.compareTo(new BigDecimal(0));
            if (t == 0 || t < 0) {
                JsfUtil.addErrorMessageClean("Cantidad en kilos igual a cero o negativo");
            } else {
                dataProductoNuevo.setPesoNeto(h);
            }
        }
    }

    public void agregarProducto() {
        dataProductoNuevo.setIdEmpPK(new BigDecimal(ifaceEntradaMercanciaProducto.getNextVal()));
        dataProductoNuevo.setIdEmFK(data.getIdEmPK());
        dataProductoNuevo.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        dataProductoNuevo.setKilosTotalesProducto(dataProductoNuevo.getPesoNeto());
        System.out.println("Nuevo Producto: " + dataProductoNuevo.toString());

        if (ifaceEntradaMercanciaProducto.insertEntradaMercanciaProducto(dataProductoNuevo) != 0) {
            ExistenciaProducto ep = new ExistenciaProducto();
            ep.setIdEmFK(dataProductoNuevo.getIdEmFK());
            ep.setIdSubProductoFK(dataProductoNuevo.getIdSubProductoFK());
            ep.setIdTipoEmpaqueFK(dataProductoNuevo.getIdTipoEmpaqueFK());
            ep.setKilosTotalesProducto(dataProductoNuevo.getKilosTotalesProducto());
            ep.setCantidadPaquetes(dataProductoNuevo.getCantidadPaquetes());
            //ep.setComentarios(producto.getComentarios());
            ep.setIdBodegaFK(dataProductoNuevo.getIdBodegaFK());
            ep.setIdTipoConvenio(dataProductoNuevo.getIdTipoConvenio());
            ep.setPrecio(dataProductoNuevo.getPrecio());
            ep.setKilospromprod(dataProductoNuevo.getKilospromprod());
            ep.setIdSucursal(data.getIdSucursalFK());
            ep.setIdProvedor(data.getIdProvedorFK());
            ep.setIdEntradaMercanciaProductoFK(dataProductoNuevo.getIdEmpPK());
            if (ifaceNegocioExistencia.insertExistenciaProducto(ep) == 1) {
                JsfUtil.addSuccessMessageClean("El producto se ha agregado correctamente");
                buscar();

            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al insertar existencias de producto nuevo");
            }

        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un problema al insertar el nuevo producto");
        }
    }

    public void imprimirEntrada() {

    }

    public void editarProducto() {

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void cancelarProducto() {
        //Primero eliminamos la existencia:
        ExistenciaProducto ep = new ExistenciaProducto();
        ep.setIdEntradaMercanciaProductoFK(dataProducto.getIdEmpPK());
        if (ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(dataProducto.getIdEmpPK()).intValue() == 0) {
            if (ifaceNegocioExistencia.deleteExistenciaProducto(ep) == 1) {
                System.out.println("Se elimino la existencia producto");
                if (ifaceEntradaMercanciaProducto.deleteEntradaMercanciaProducto(dataProducto) == 1) {
                    JsfUtil.addSuccessMessageClean("Se ha eliminado el producto correctamente");
                    buscar();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al intentar borrar el producto");
                }
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al eliminar la existencia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Ya se tienen ventas registradas, no se puede eliminar");
        }
    }

    public void cancelarEntrada() {
        boolean bandera = false;
        if (!data.getListaProductos().isEmpty()) {
            for (EntradaMercanciaProducto items : data.getListaProductos()) {
                ExistenciaProducto ep = new ExistenciaProducto();
                ep.setIdEntradaMercanciaProductoFK(items.getIdEmpPK());
                if (ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(items.getIdEmpPK()).intValue() == 0) {
                    if (ifaceNegocioExistencia.deleteExistenciaProducto(ep) == 1) {
                        System.out.println("Se elimino la existencia producto");
                        if (ifaceEntradaMercanciaProducto.deleteEntradaMercanciaProducto(items) == 1) {
                            JsfUtil.addSuccessMessageClean("Se ha eliminado el producto correctamente");
                            buscar();
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrio un error al intentar borrar el producto");
                            bandera = true;
                        }
                    } else {
                        bandera = true;
                    }
                } else {
                    bandera = true;
                    JsfUtil.addErrorMessageClean("Ya se tienen ventas registradas de este producto, no se puede eliminar");
                }
            }

        }
        if (bandera) {
            System.out.println("Al menos un producto tiene ventas y no se puede eliminar.");
            JsfUtil.addErrorMessageClean("Al menos un producto del carro tiene ventas, no se puede eliminar");
        } else {
            System.out.println("Se han eliminado todos los productos del carro");
            if (ifaceEntradaMercancia.deleteEntradaMercancia(data) == 1) {
                JsfUtil.addSuccessMessageClean("Se ha cancelado la entrada de mercancia correctamente");
                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al cancelar la entrada de mercancia");
            }

        }
        System.out.println("Despues de eliminar los items eliminar la entrada general");

    }

    public void onRowEdit(RowEditEvent event) {

        dataProductEdit = (EntradaMercanciaProducto) event.getObject();
        boolean cambioProducto = false;
        BigDecimal cantidadAnterior = new BigDecimal(0);
        BigDecimal kilosAnterior = new BigDecimal(0);
        EntradaMercanciaProducto epAnterio = ifaceEntradaMercanciaProducto.getEntradaMercanciaProductoByIdEmpPk(dataProductEdit.getIdEmpPK());
        cantidadAnterior = epAnterio.getCantidadPaquetes();
        kilosAnterior = epAnterio.getKilosTotalesProducto();

        if (dataProductEdit.getIdSubProductoFK().equals(dataProductEdit.getSubProducto().getIdSubproductoPk())) {
            System.out.println("son iguales");
        } else {
            System.out.println("son diferentes");
            dataProductEdit.setIdSubProductoFK(dataProductEdit.getSubProducto().getIdSubproductoPk());
            cambioProducto = true;
        }
        boolean totalVentas = false;
        if (cambioProducto == true) {
            if (ifaceEntradaMercanciaProducto.getTotalVentasByIdEMP(dataProductEdit.getIdEmpPK()).intValue() == 0) {
                totalVentas = false;
                System.out.println("No existen ventas");
            } else {
                totalVentas = true;
                System.out.println("Existen Ventas");
            }
        }
        if (totalVentas == false) {
            if (ifaceEntradaMercanciaProducto.update(dataProductEdit) == 1) {
                JsfUtil.addSuccessMessageClean("Se actualizo correctamente el producto");

                System.out.println("Se actualizo correctamente");
                ExistenciaProducto ep = new ExistenciaProducto();
                ep.setIdSubProductoFK(dataProductEdit.getIdSubProductoFK());
                ep.setIdTipoEmpaqueFK(dataProductEdit.getIdTipoEmpaqueFK());
                ep.setIdBodegaFK(dataProductEdit.getIdBodegaFK());
                ep.setIdTipoConvenio(dataProductEdit.getIdTipoConvenio());
                ep.setIdEntradaMercanciaProductoFK(dataProductEdit.getIdEmpPK());
                System.out.println("Entrada Anterior kilos: " + kilosAnterior);
                System.out.println("Entrada Anterior Cantidad: " + cantidadAnterior);
                System.out.println("Entrada Nueva kilos: " + dataProductEdit.getKilosTotalesProducto());
                System.out.println("Entrada Nueva Cantidad: " + dataProductEdit.getCantidadPaquetes());
                ExistenciaProducto e = ifaceNegocioExistencia.getExistenciaByIdEmpFk(dataProductEdit.getIdEmpPK());
                System.out.println("Existencia Anterior kilos: " + e.getKilosTotalesProducto());
                System.out.println("Existencia Anterior Cantidad: " + e.getCantidadPaquetes());
                e.setKilosTotalesProducto(e.getKilosTotalesProducto().subtract((kilosAnterior.subtract(dataProductEdit.getKilosTotalesProducto(), MathContext.UNLIMITED)), MathContext.UNLIMITED));
                e.setCantidadPaquetes(e.getCantidadPaquetes().subtract((cantidadAnterior.subtract(dataProductEdit.getCantidadPaquetes(), MathContext.UNLIMITED)), MathContext.UNLIMITED));
                System.out.println("Existencia Nueovs kilos: " + e.getKilosTotalesProducto());
                System.out.println("Existencia Nuevos Cantidad: " + e.getCantidadPaquetes());

                if (ifaceNegocioExistencia.updateExistenciaProducto(ep) == 1) {
                    JsfUtil.addSuccessMessageClean("Actualización de datos correcta");
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un problema al actualizar existencias");
                }

                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un problema al actualizar entrada de mercancia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Producto con ventas registradas, no se puede cambiar");
        }

    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("cancel");

    }

    public void cancel() {
        setViewEstate("init");
        //lstEntradaMercanciaProdcuto = new ArrayList<EntradaMercanciaProducto>();
        lstEntradaMercancia = new ArrayList<EntradaMercancia>();
        data.reset();
        provedor = null;
        filtro = -1;
        fechaInicio = null;
        fechaFin = null;
        buscar();

    }

//    public void detallesEntradaProducto() 
//    {
//        setViewEstate("searchById");
//
//        lstEntradaMercanciaProdcuto = ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(data.getIdEmPK());
//        getTotalKilosProducto();
//    }
//    public void getTotalKilosProducto() {
//        totalKilos = new BigDecimal(0);
//        for (EntradaMercanciaProducto dominio : lstEntradaMercanciaProdcuto) 
//        {
//            totalKilos = totalKilos.add(dominio.getKilosTotalesProducto());
//        }
//
//    }
    public ArrayList<Provedor> autoCompleteProvedor(String nombreProvedor) {
        lstProvedor = ifaceCatProvedores.getProvedorByNombreCompleto(nombreProvedor.toUpperCase());
        return lstProvedor;

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String insert() {

        return "relacionOperaciones";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void searchById() {
        setViewEstate("searchById");

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public EntradaMercancia getData() {
        return data;
    }

    public void setData(EntradaMercancia data) {
        this.data = data;
    }

    public ArrayList<EntradaMercancia> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public Provedor getProvedor() {
        return provedor;
    }

    public void setProvedor(Provedor provedor) {
        this.provedor = provedor;
    }

    public ArrayList<Provedor> getLstProvedor() {
        return lstProvedor;
    }

    public void setLstProvedor(ArrayList<Provedor> lstProvedor) {
        this.lstProvedor = lstProvedor;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public EntradaMercanciaProducto getDataProducto() {
        return dataProducto;
    }

    public void setDataProducto(EntradaMercanciaProducto dataProducto) {
        this.dataProducto = dataProducto;
    }

    public EntradaMercanciaProducto getDataProductEdit() {
        return dataProductEdit;
    }

    public void setDataProductEdit(EntradaMercanciaProducto dataProductEdit) {
        this.dataProductEdit = dataProductEdit;
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

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public EntradaMercanciaProductoPaquete getDataPaquete() {
        return dataPaquete;
    }

    public void setDataPaquete(EntradaMercanciaProductoPaquete dataPaquete) {
        this.dataPaquete = dataPaquete;
    }

    public EntradaMercanciaProducto getDataProductoNuevo() {
        return dataProductoNuevo;
    }

    public void setDataProductoNuevo(EntradaMercanciaProducto dataProductoNuevo) {
        this.dataProductoNuevo = dataProductoNuevo;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdProvedor() {
        return idProvedor;
    }

    public void setIdProvedor(BigDecimal idProvedor) {
        this.idProvedor = idProvedor;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

}
