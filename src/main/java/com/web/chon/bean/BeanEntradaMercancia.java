/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.web.chon.service.IfaceTipoCovenio;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author fredy
 */
@Component
@Scope("view")
public class BeanEntradaMercancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceTipoCovenio ifaceCovenio;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private PlataformaSecurityContext context;
    private UsuarioDominio usuario;

    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<String> labels;
    private ArrayList<EntradaMercanciaProducto> listaMercanciaProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<ExistenciaProducto> existencia_repetida;

    private EntradaMercancia data;
    private EntradaMercanciaProducto dataProducto;
    private EntradaMercanciaProducto dataRemove;
    private EntradaMercanciaProducto dataEdit;
    private Subproducto subProducto;

    private String title = "";
    private String viewEstate = "";
    private String labelCompra;

    private int movimiento;
    private int year;

    private BigDecimal totalKilos;
    private BigDecimal kilos;
    private BigDecimal cantidadReal;

    private boolean permisionPacto;
    private boolean permisionComision;
    private boolean permisionPrecio;
    private boolean permisionToGenerate;
    private boolean permisionToPush;
    private boolean permisionToEditProducto;

    @PostConstruct
    public void init() {
        permisionToEditProducto = false;
        labels = new ArrayList<String>();
        labels.add("Precio");
        labels.add("Precio %");
        labels.add("Pacto");

        permisionToPush = true;
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = new ArrayList<Provedor>();
        listaProvedores = ifaceCatProvedores.getProvedores();
        listaMercanciaProducto = new ArrayList<EntradaMercanciaProducto>();

        dataProducto = new EntradaMercanciaProducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        data = new EntradaMercancia();
        usuario = context.getUsuarioAutenticado();
        data.setIdSucursalFK(new BigDecimal(usuario.getSucId()));
        data.setIdUsuario(usuario.getIdUsuario());
        listaTiposConvenio = new ArrayList<TipoConvenio>();
        listaTiposConvenio = ifaceCovenio.getTipos();
        setTitle("Registro Entrada de Mercancia");
        setViewEstate("init");
        movimiento = 0;
        year = 0;
        permisionPacto = true;
        permisionComision = true;
        permisionPrecio = false;
        labelCompra = "Precio";
        permisionToGenerate = true;
        kilos = new BigDecimal(0);
        existencia_repetida = new ArrayList<ExistenciaProducto>();
        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalFK());
        cantidadReal = new BigDecimal(0);
    }
    public void sumaDias()
    {
        Date hoy = context.getFechaSistema();
        data.setFechaPago(TiempoUtil.sumarRestarDias(hoy, data.getDiasPago().intValue()));
        System.out.println("Fecha de Pago: "+data.getFechaPago());
    }

    public void permisions() {

        labelCompra = labels.get(dataProducto.getIdTipoConvenio().intValue() - 1);
        permisionPrecio = false;
    }

    public void calculaPesoNeto() {
        if (dataProducto.getKilosTotalesProducto() != null && dataProducto.getPesoTara() != null) {

            BigDecimal h = dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED);
            //dataProducto.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED));

            int t = h.compareTo(new BigDecimal(0));
            if (t == 0 || t < 0) {
                JsfUtil.addErrorMessageClean("Cantidad en kilos igual a cero o negativo");
            } else {
                dataProducto.setPesoNeto(h);
            }
        }
    }

    public void calculaCantidadReal() 
    {
        cantidadReal = new BigDecimal(0);
        for (EntradaMercanciaProducto p : listaMercanciaProducto) 
        {
            cantidadReal = cantidadReal.add(p.getCantidadPaquetes(), MathContext.UNLIMITED);
        }
    }

    public void inserts() {

        int idEntradaMercancia = 0;
        int idCarroSucursal = 0;
        EntradaMercancia entrada_mercancia = new EntradaMercancia();

        try {
            if (!listaMercanciaProducto.isEmpty() && listaMercanciaProducto.size() > 0) {
                calculaCantidadReal();
                idEntradaMercancia = ifaceEntradaMercancia.getNextVal();
                idCarroSucursal = ifaceEntradaMercancia.getCarroSucursal(data.getIdSucursalFK());
                entrada_mercancia.setIdEmPK(new BigDecimal(idEntradaMercancia));
                entrada_mercancia.setIdUsuario(data.getIdUsuario());
                entrada_mercancia.setIdProvedorFK(data.getIdProvedorFK());
                entrada_mercancia.setIdSucursalFK(data.getIdSucursalFK());
                entrada_mercancia.setAbreviacion(data.getAbreviacion());
                entrada_mercancia.setMovimiento(data.getMovimiento());
                entrada_mercancia.setRemision(data.getRemision());
                entrada_mercancia.setFecha(data.getFecha());
                entrada_mercancia.setFolio(data.getFolio());
                entrada_mercancia.setCantidadEmpaquesProvedor(data.getCantidadEmpaquesProvedor());
                System.out.println("CantidadReal ===" +cantidadReal);
                entrada_mercancia.setCantidadEmpaquesReales(cantidadReal);
                entrada_mercancia.setKilosTotales(kilos);
                System.out.println("kilos ===" +kilos);
                entrada_mercancia.setKilosTotalesProvedor(data.getKilosTotalesProvedor());
                entrada_mercancia.setComentariosGenerales(data.getComentariosGenerales());
                entrada_mercancia.setFechaRemision(data.getFechaRemision());
                entrada_mercancia.setIdCarroSucursal(new BigDecimal(idCarroSucursal + 1));
                entrada_mercancia.setFechaPago(data.getFechaPago());
                System.out.println("=============Entrada=================");
                System.out.println(data.toString());
                System.out.println("Entrada: "+entrada_mercancia.toString());

                int mercanciaOrdenada = ifaceEntradaMercancia.insertEntradaMercancia(entrada_mercancia);
                if (mercanciaOrdenada != 0) {
                    for (int i = 0; i < listaMercanciaProducto.size(); i++) {
                        EntradaMercanciaProducto producto = new EntradaMercanciaProducto();
                        producto = listaMercanciaProducto.get(i);
                        int idEnTMerPro = ifaceEntradaMercanciaProducto.getNextVal();
                        producto.setIdEmpPK(new BigDecimal(idEnTMerPro));
                        producto.setIdEmFK(new BigDecimal(idEntradaMercancia));
                        producto.setKilospromprod(producto.getKilosTotalesProducto().divide(producto.getCantidadPaquetes(), 2, RoundingMode.HALF_EVEN));
                        producto.setKilosProProvedor(producto.getKilosTotalesProducto());
                        producto.setEmpaquesProProvedor(producto.getCantidadPaquetes());
                        //int idEntradaMercanciaProducto = ifaceEntradaMercanciaProducto.getNextVal();
                        if (ifaceEntradaMercanciaProducto.insertEntradaMercanciaProducto(producto) != 0) {
                            //BUSCAR SI YA EXISTE EN LA TABLA EXISTENCIA PRODUCTO.
                            ExistenciaProducto ep = new ExistenciaProducto();
                            ep.setIdSubProductoFK(producto.getIdSubProductoFK());
                            ep.setIdTipoEmpaqueFK(producto.getIdTipoEmpaqueFK());
                            ep.setKilosTotalesProducto(producto.getKilosTotalesProducto());
                            ep.setCantidadPaquetes(producto.getCantidadPaquetes());
                            ep.setComentarios(producto.getComentarios());
                            ep.setIdBodegaFK(producto.getIdBodegaFK());
                            ep.setIdTipoConvenio(producto.getIdTipoConvenio());
                            ep.setPrecio(producto.getPrecio());
                            ep.setKilospromprod(producto.getKilospromprod());
                            ep.setIdSucursal(entrada_mercancia.getIdSucursalFK());
                            ep.setIdProvedor(entrada_mercancia.getIdProvedorFK());
                            ep.setIdEntradaMercanciaProductoFK(new BigDecimal(idEnTMerPro));
                            if (ifaceNegocioExistencia.insertExistenciaProducto(ep) == 1) {
                                JsfUtil.addSuccessMessageClean("¡Registro de Mercancias correcto !");
                            } else {
                                JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar la mercancia en existencias");
                            }

                        } else {
                            JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar un producto de la entrada de Mercancia");
                        }

                    } //fin for

                    data.reset();
                    listaMercanciaProducto.clear();
                    dataProducto.reset();
                    kilos = new BigDecimal(0);
                    setViewEstate("init");
                    permisionToPush = true;
                    permisionToGenerate = true;
                    reset();

                } else {
                    JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar la mercancia");
                }
            } else {
                JsfUtil.addErrorMessage("Error!", "Necesitas agregar al menos un producto para realizar la orden de venta.");

            }

        } catch (StackOverflowError ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("Error!", "Ocurrio un error .");
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error!", "Ocurrio un error ");
            e.printStackTrace();

        }
    }

    public void reset() {
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalFK());
        data.setRemision(null);
        data.setFolio(null);
        data.setAbreviacion(null);
        permisionToPush = true;

    }

    public void remove() {

        //kilos = kilos.subtract(dataRemove.getKilosTotalesProducto(), MathContext.UNLIMITED);
        listaMercanciaProducto.remove(dataRemove);
        sumaTotales();

    }

    public void editProducto() {
        permisionToEditProducto = true;
        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubProductoFK());
        dataProducto.setIdSubProductoFK(dataEdit.getIdSubProductoFK());
        dataProducto.setNombreProducto(dataEdit.getNombreProducto());
        dataProducto.setIdTipoEmpaqueFK(dataEdit.getIdTipoEmpaqueFK());
        dataProducto.setNombreEmpaque(dataEdit.getNombreEmpaque());
        dataProducto.setCantidadPaquetes(dataEdit.getCantidadPaquetes());
        dataProducto.setPrecio(dataEdit.getPrecio());
        dataProducto.setKilosTotalesProducto(dataEdit.getKilosTotalesProducto());
        dataProducto.setComentarios(dataEdit.getComentarios());
        dataProducto.setIdBodegaFK(dataEdit.getIdBodegaFK());
        dataProducto.setPesoTara(dataEdit.getPesoTara());
        dataProducto.setIdTipoConvenio(dataEdit.getIdTipoConvenio());
        dataProducto.setNombreTipoConvenio(dataEdit.getNombreTipoConvenio());
        viewEstate = "update";
        dataProducto.setPesoNeto(dataEdit.getPesoNeto());

        //kilos = kilos.subtract(dataEdit.getPesoNeto(), MathContext.UNLIMITED);
    }

    public void sumaTotales() {
        kilos = new BigDecimal(0);
        for (EntradaMercanciaProducto producto : listaMercanciaProducto) {
            kilos = kilos.add(producto.getPesoNeto(), MathContext.UNLIMITED);
        }
    }

    public void cancel() {
        dataProducto.reset();
        subProducto = new Subproducto();
        viewEstate = "init";
    }

    public void updateProducto() {
        permisionToEditProducto = false;
        EntradaMercanciaProducto p = new EntradaMercanciaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        dataEdit.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        dataEdit.setNombreProducto(subProducto.getNombreSubproducto());
        dataEdit.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());
        dataEdit.setIdTipoConvenio(dataProducto.getIdTipoConvenio());
        TipoConvenio to = new TipoConvenio();
        to = getTipoConvenio(dataProducto.getIdTipoConvenio());
        dataEdit.setIdBodegaFK(dataProducto.getIdBodegaFK());
        Bodega b = new Bodega();
        b = getBodega(dataProducto.getIdBodegaFK());
        p.setNombreBodega(b.getNombreBodega());
        dataEdit.setNombreBodega(p.getNombreBodega());
        dataEdit.setNombreTipoConvenio(to.getNombreTipoConvenio());
        dataEdit.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        dataEdit.setPrecio(dataProducto.getPrecio());
        dataEdit.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());
        p.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto().subtract((dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara()), MathContext.UNLIMITED));
        dataEdit.setComentarios(dataProducto.getComentarios());
        dataEdit.setPesoTara(dataProducto.getPesoTara());
        dataEdit.setPesoNeto(dataProducto.getPesoNeto());
        kilos = kilos.add(dataProducto.getPesoNeto(), MathContext.UNLIMITED);
        viewEstate = "init";
        subProducto = new Subproducto();
        dataProducto.reset();
        sumaTotales();

    }

    public void buscaMovimiento() {
        data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
        movimiento = ifaceEntradaMercancia.buscaMaxMovimiento(data);
        year = TiempoUtil.getYear(data.getFechaFiltroInicio());
        movimiento = movimiento + 1;
        updateAbreviacion();
        if (data.getRemision() == null) {
            data.setRemision("S/R");
        }
        data.setMovimiento(new BigDecimal(movimiento));
        data.setFolio(data.getAbreviacion() + "-" + year + "-" + movimiento + ":" + data.getRemision());
        permisionToPush = false;

    }

    public void addProducto() {
        EntradaMercanciaProducto p = new EntradaMercanciaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        TipoConvenio to = new TipoConvenio();
        p.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        p.setNombreProducto(subProducto.getNombreSubproducto());
        p.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        p.setNombreEmpaque(empaque.getNombreEmpaque());
        p.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        p.setPrecio(dataProducto.getPrecio());
        p.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract((dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara()), MathContext.UNLIMITED));
        p.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());
        
        if (p.getPesoNeto().compareTo(new BigDecimal(0)) <= 0) 
        {
            JsfUtil.addErrorMessageClean("El peso de la tara es mayor que el producto");
        } else 
        {
//kilos = kilos.add(p.getKilosTotalesProducto(), MathContext.UNLIMITED);
            //data.setKilosTotales(kilos);
            p.setComentarios(dataProducto.getComentarios());
            to = getTipoConvenio(dataProducto.getIdTipoConvenio());
            p.setNombreTipoConvenio(to.getNombreTipoConvenio());
            p.setIdTipoConvenio(dataProducto.getIdTipoConvenio());
            Bodega b = new Bodega();
            b = getBodega(dataProducto.getIdBodegaFK());
            p.setNombreBodega(b.getNombreBodega());
            p.setIdBodegaFK(dataProducto.getIdBodegaFK());
            p.setPesoTara(dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara());
            //p.setPesoNeto(dataProducto.getPesoNeto());
            boolean bandera = false;
            for (EntradaMercanciaProducto producto : listaMercanciaProducto) {
                if (producto.getIdSubProductoFK().equals(p.getIdSubProductoFK()) && producto.getIdTipoEmpaqueFK().intValue() == p.getIdTipoEmpaqueFK().intValue() && producto.getIdTipoConvenio().intValue() == p.getIdTipoConvenio().intValue() && producto.getIdBodegaFK().intValue() == p.getIdBodegaFK().intValue()) {
                    if (producto.getPrecio().setScale(2, RoundingMode.CEILING).compareTo(p.getPrecio().setScale(2, RoundingMode.CEILING)) == 0) {
                        bandera = true;
                    }
                }
            }
            if (!bandera) {
                listaMercanciaProducto.add(p);
                permisionToGenerate = false;
                dataProducto.reset();
                subProducto = new Subproducto();
                listaTiposConvenio = ifaceCovenio.getTipos();
                //listaBodegas = ifaceCatBodegas.getBodegas();
                JsfUtil.addSuccessMessageClean("Producto agregado correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Este Producto ya se encuentra en la lista, modificar existente");
            }
            sumaTotales();
        }
        

    }

    private Bodega getBodega(BigDecimal idBodega) {
        Bodega b = new Bodega();

        for (Bodega bodeguita : listaBodegas) {
            if (bodeguita.getIdBodegaPK().equals(idBodega)) {
                b = bodeguita;
                break;
            }
        }
        return b;
    }

    private TipoEmpaque getEmpaque(BigDecimal idEmpaque) {
        TipoEmpaque empaque = new TipoEmpaque();

        for (TipoEmpaque tipoEmpaque : lstTipoEmpaque) {
            if (tipoEmpaque.getIdTipoEmpaquePk().equals(idEmpaque)) {
                empaque = tipoEmpaque;
                break;
            }
        }
        return empaque;
    }

    private TipoConvenio getTipoConvenio(BigDecimal idTipoConvenio) {
        TipoConvenio compra = new TipoConvenio();

        for (TipoConvenio tipoOrden : listaTiposConvenio) {
            if (tipoOrden.getIdTcPK().equals(idTipoConvenio)) {
                compra = tipoOrden;
                break;
            }
        }
        return compra;
    }

    public void updateAbreviacion() {

        for (Provedor p : listaProvedores) {
            // System.out.println("p: "+p.getIdProvedorPK());
            //System.out.println("d: "+data.getIdProvedorFK().intValue());
            if (p.getIdProvedorPK().intValue() == data.getIdProvedorFK().intValue()) {
                data.setAbreviacion(p.getNickName());
            }

        }

    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getYear() {
        return year;
    }

    //Getters y Setters
    public void setYear(int year) {
        this.year = year;
    }

    public IfaceCatSucursales getIfaceCatSucursales() {
        return ifaceCatSucursales;
    }

    public void setIfaceCatSucursales(IfaceCatSucursales ifaceCatSucursales) {
        this.ifaceCatSucursales = ifaceCatSucursales;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public IfaceCatProvedores getIfaceCatProvedores() {
        return ifaceCatProvedores;
    }

    public void setIfaceCatProvedores(IfaceCatProvedores ifaceCatProvedores) {
        this.ifaceCatProvedores = ifaceCatProvedores;
    }

    public ArrayList<Provedor> getListaProvedores() {
        return listaProvedores;
    }

    public void setListaProvedores(ArrayList<Provedor> listaProvedores) {
        this.listaProvedores = listaProvedores;
    }

    public EntradaMercancia getData() {
        return data;
    }

    public void setData(EntradaMercancia data) {
        this.data = data;
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

    public IfaceEntradaMercancia getIfaceEntradaMercancia() {
        return ifaceEntradaMercancia;
    }

    public void setIfaceEntradaMercancia(IfaceEntradaMercancia ifaceEntradaMercancia) {
        this.ifaceEntradaMercancia = ifaceEntradaMercancia;
    }

    public ArrayList<EntradaMercanciaProducto> getListaMercanciaProducto() {
        return listaMercanciaProducto;
    }

    public void setListaMercanciaProducto(ArrayList<EntradaMercanciaProducto> listaMercanciaProducto) {
        this.listaMercanciaProducto = listaMercanciaProducto;
    }

    public EntradaMercanciaProducto getDataProducto() {
        return dataProducto;
    }

    public void setDataProducto(EntradaMercanciaProducto dataProducto) {
        this.dataProducto = dataProducto;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }

    public IfaceEmpaque getIfaceEmpaque() {
        return ifaceEmpaque;
    }

    public void setIfaceEmpaque(IfaceEmpaque ifaceEmpaque) {
        this.ifaceEmpaque = ifaceEmpaque;
    }

    public boolean isPermisionToPush() {
        return permisionToPush;
    }

    public void setPermisionToPush(boolean permisionToPush) {
        this.permisionToPush = permisionToPush;
    }

    public EntradaMercanciaProducto getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(EntradaMercanciaProducto dataRemove) {
        this.dataRemove = dataRemove;
    }

    public EntradaMercanciaProducto getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(EntradaMercanciaProducto dataEdit) {
        this.dataEdit = dataEdit;
    }

    public IfaceSubProducto getIfaceSubProducto() {
        return ifaceSubProducto;
    }

    public void setIfaceSubProducto(IfaceSubProducto ifaceSubProducto) {
        this.ifaceSubProducto = ifaceSubProducto;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

    public IfaceTipoCovenio getIfaceCovenio() {
        return ifaceCovenio;
    }

    public void setIfaceCovenio(IfaceTipoCovenio ifaceCovenio) {
        this.ifaceCovenio = ifaceCovenio;
    }

    public boolean isPermisionPacto() {
        return permisionPacto;
    }

    public void setPermisionPacto(boolean permisionPacto) {
        this.permisionPacto = permisionPacto;
    }

    public boolean isPermisionComision() {
        return permisionComision;
    }

    public void setPermisionComision(boolean permisionComision) {
        this.permisionComision = permisionComision;
    }

    public boolean isPermisionPrecio() {
        return permisionPrecio;
    }

    public void setPermisionPrecio(boolean permisionPrecio) {
        this.permisionPrecio = permisionPrecio;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public String getLabelCompra() {
        return labelCompra;
    }

    public void setLabelCompra(String labelCompra) {
        this.labelCompra = labelCompra;
    }

    public IfaceEntradaMercanciaProducto getIfaceEntradaMercanciaProducto() {
        return ifaceEntradaMercanciaProducto;
    }

    public void setIfaceEntradaMercanciaProducto(IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto) {
        this.ifaceEntradaMercanciaProducto = ifaceEntradaMercanciaProducto;
    }

    public boolean isPermisionToGenerate() {
        return permisionToGenerate;
    }

    public void setPermisionToGenerate(boolean permisionToGenerate) {
        this.permisionToGenerate = permisionToGenerate;
    }

    public IfaceCatBodegas getIfaceCatBodegas() {
        return ifaceCatBodegas;
    }

    public void setIfaceCatBodegas(IfaceCatBodegas ifaceCatBodegas) {
        this.ifaceCatBodegas = ifaceCatBodegas;
    }

    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public ArrayList<TipoConvenio> getListaTiposConvenio() {
        return listaTiposConvenio;
    }

    public void setListaTiposConvenio(ArrayList<TipoConvenio> listaTiposConvenio) {
        this.listaTiposConvenio = listaTiposConvenio;
    }

    public boolean isPermisionToEditProducto() {
        return permisionToEditProducto;
    }

    public void setPermisionToEditProducto(boolean permisionToEditProducto) {
        this.permisionToEditProducto = permisionToEditProducto;
    }

    public ArrayList<ExistenciaProducto> getExistencia_repetida() {
        return existencia_repetida;
    }

    public void setExistencia_repetida(ArrayList<ExistenciaProducto> existencia_repetida) {
        this.existencia_repetida = existencia_repetida;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

}
