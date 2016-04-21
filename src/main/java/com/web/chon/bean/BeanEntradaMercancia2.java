/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.TipoOrdenCompra;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoOrdenCompra;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author fredy
 */
@Component
@Scope("view")
public class BeanEntradaMercancia2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    private ArrayList<Sucursal> listaSucursales;

    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    private ArrayList<Bodega> listaBodegas;

    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    private ArrayList<Provedor> listaProvedores;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceTipoOrdenCompra ifaceTipoOrdenCompra;
    private ArrayList<TipoOrdenCompra> listaTiposOrden;

    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;

    private EntradaMercancia2 data;
    private EntradaMercanciaProducto dataProducto;
    private EntradaMercanciaProducto dataRemove;
    private EntradaMercanciaProducto dataEdit;
    private Subproducto subProducto;
    private ArrayList<EntradaMercanciaProducto> listaMercanciaProducto;

    private ArrayList<TipoEmpaque> lstTipoEmpaque;

    private String title = "";
    private String viewEstate = "";

    private int movimiento;
    private int year;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;

    private boolean permisionToPush;
    private BigDecimal totalKilos;

    private boolean permisionPacto;
    private boolean permisionComision;
    private boolean permisionPrecio;
    private boolean permisionToGenerate;
    private ArrayList<String> labels;

    private String labelCompra;

    @PostConstruct
    public void init() {
        labels = new ArrayList<String>();
        labels.add("Precio");
        labels.add("Ingresa el % de comisión");
        labels.add("Ingresa el precio por pacto por unidad");
        permisionToPush = true;
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = new ArrayList<Provedor>();
        listaProvedores = ifaceCatProvedores.getProvedores();
        listaMercanciaProducto = new ArrayList<EntradaMercanciaProducto>();
        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegas();
        dataProducto = new EntradaMercanciaProducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        data = new EntradaMercancia2();
        listaTiposOrden = new ArrayList<TipoOrdenCompra>();
        listaTiposOrden = ifaceTipoOrdenCompra.getTipos();
        setTitle("Entrada de Mercancia");
        setViewEstate("init");
        movimiento = 0;
        year = 0;
        permisionPacto = true;
        permisionComision = true;
        permisionPrecio = false;
        labelCompra = "Ingresa el Precio";
        permisionToGenerate = true;
    }

    public void permisions() {

        labelCompra = labels.get(dataProducto.getIdTipo().intValue() - 1);
        permisionPrecio = false;
    }

    public void inserts() {

        int idEntradaMercancia = 0;
        EntradaMercancia2 entrada_mercancia = new EntradaMercancia2();

        try {
            if (!listaMercanciaProducto.isEmpty() && listaMercanciaProducto.size() > 0) {
                idEntradaMercancia = ifaceEntradaMercancia.getNextVal();
                entrada_mercancia.setIdEmPK(new BigDecimal(idEntradaMercancia));
                entrada_mercancia.setIdProvedorFK(data.getIdProvedorFK());
                entrada_mercancia.setIdSucursalFK(data.getIdSucursalFK());
                entrada_mercancia.setAbreviacion(data.getAbreviacion());
                entrada_mercancia.setMovimiento(data.getMovimiento());
                entrada_mercancia.setRemision(data.getRemision());
                entrada_mercancia.setFecha(data.getFecha());
                entrada_mercancia.setIdSucursalFK(new BigDecimal(1));
                entrada_mercancia.setFolio(data.getFolio());
                entrada_mercancia.setKilosTotales(data.getKilosTotales());
                entrada_mercancia.setKilosTotalesProvedor(data.getKilosTotalesProvedor());
                int mercanciaOrdenada = ifaceEntradaMercancia.insertEntradaMercancia(entrada_mercancia);
                if (mercanciaOrdenada != 0) {
                    for (int i = 0; i < listaMercanciaProducto.size(); i++) {
                        EntradaMercanciaProducto producto = new EntradaMercanciaProducto();
                        producto = listaMercanciaProducto.get(i);
                        producto.setIdEmFK(new BigDecimal(idEntradaMercancia));
                        ifaceEntradaMercanciaProducto.insertEntradaMercancia(producto);
                        ExistenciaProducto ep = new ExistenciaProducto();
                        ep.setIdSubProductoFk(producto.getIdSubProductoFK());
                        ep.setIdSucursalFk(data.getIdSucursalFK());
                        ep.setIdTipoEmpaque(producto.getIdTipoEmpaqueFK());
                        ep.setKilosEmpaque(producto.getKilosTotalesProducto());
                        ep.setKilosExistencia(producto.getKilosTotalesProducto());
                        ep.setIdBodegaFk(producto.getIdBodegaFK());
                        ep.setCantidadEmpaque(producto.getCantidadPaquetes());
                        ep.setIdProvedorFk(data.getIdProvedorFK());
                        int resultado = ifaceNegocioExistencia.insertExistenciaProducto(ep);

                        switch (resultado) {
                            case 0:
                                JsfUtil.addErrorMessage("Error!", "Ocurrio un error al actualizar existencias");
                                break;
                            case 2:
                                ArrayList<ExistenciaProducto> existente = new ArrayList<ExistenciaProducto>();
                                System.out.println("Actualizando existencias....");
                                existente = ifaceNegocioExistencia.getExistenciaProductoId(data.getIdSucursalFK(), producto.getIdSubProductoFK(), producto.getIdTipoEmpaqueFK(), producto.getIdBodegaFK(),data.getIdProvedorFK());

                                ExistenciaProducto expro = new ExistenciaProducto();
                                expro = existente.get(0);

                                int cantidadEmpaque = expro.getCantidadEmpaque().intValue();
                                int kiltoproempaque = expro.getKilosEmpaque().intValue();
                                int kilosTotales = expro.getKilosExistencia().intValue();
                                ep.setCantidadEmpaque(new BigDecimal(ep.getCantidadEmpaque().intValue() + cantidadEmpaque));
                                ep.setKilosEmpaque(new BigDecimal(ep.getKilosEmpaque().intValue() + kiltoproempaque));
                                ep.setKilosExistencia(new BigDecimal(ep.getKilosExistencia().intValue() + kilosTotales));
                                ep.setIdExistenciaProductoPk(expro.getIdExistenciaProductoPk());

                                if (ifaceNegocioExistencia.updateExistenciaProducto(ep) == 0) {
                                    JsfUtil.addErrorMessage("Error!", "Ocurrio un error al actualizar existencias ya registradas");
                                } else {
                                    JsfUtil.addSuccessMessage("Actualización de existencias correcto!");
                                }
                                break;
                            default:
                                JsfUtil.addSuccessMessage("Actualización de existencias correcto!");
                                break;
                        }
                    }
                    data.reset();
                    
                    listaMercanciaProducto.clear();
                    dataProducto.reset();
                    //dataEdit.reset();
                    //dataRemove.reset();
                    setViewEstate("init");
                    permisionToPush = true;
                    permisionToGenerate = true;
                    reset();

                } else {
                    JsfUtil.addErrorMessage("Error!", "Ocurrio un error al insertar la venta.");
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
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.toString()));

        }
    }

    public void reset() {
        data.setRemision(null);
        data.setFolio(null);
        data.setAbreviacion(null);
        permisionToPush = true;
    }

    public void remove() {

        listaMercanciaProducto.remove(dataRemove);
        System.out.println("Eliminado");
    }

    public void editProducto() {

        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubProductoFK());
        //autoComplete(dataEdit.getNombreProducto());
        //searchById();

        dataProducto.setIdSubProductoFK(dataEdit.getIdSubProductoFK());
        dataProducto.setNombreProducto(dataEdit.getNombreProducto());
        dataProducto.setIdTipoEmpaqueFK(dataEdit.getIdTipoEmpaqueFK());
        dataProducto.setNombreEmpaque(dataEdit.getNombreEmpaque());
        dataProducto.setCantidadPaquetes(dataEdit.getCantidadPaquetes());
        dataProducto.setPrecio(dataEdit.getPrecio());
        dataProducto.setKilosTotalesProducto(dataEdit.getKilosTotalesProducto());
        dataProducto.setComentarios(dataEdit.getComentarios());
        dataProducto.setIdBodegaFK(dataEdit.getIdBodegaFK());
        viewEstate = "update";
        System.out.println("datadataProducto :" + dataProducto.toString());

    }

    public void cancel() {
        dataProducto.reset();
        subProducto = new Subproducto();
        viewEstate = "init";

    }

    public void updateProducto() {

        EntradaMercanciaProducto p = new EntradaMercanciaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        dataEdit.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        dataEdit.setNombreProducto(subProducto.getNombreSubproducto());
        dataEdit.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());
        dataEdit.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        dataEdit.setPrecio(dataProducto.getPrecio());
        dataEdit.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());
        dataEdit.setComentarios(dataProducto.getComentarios());
        viewEstate = "init";
        subProducto = new Subproducto();
        dataProducto.reset();

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
        TipoOrdenCompra to = new TipoOrdenCompra();
        p.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        p.setNombreProducto(subProducto.getNombreSubproducto());
        p.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        p.setNombreEmpaque(empaque.getNombreEmpaque());
        p.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        p.setPrecio(dataProducto.getPrecio());
        p.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());
        int kilos = data.getKilosTotalesProvedor().intValue();
        kilos = kilos + p.getKilosTotalesProducto().intValue();
        data.setKilosTotales(new BigDecimal(kilos));
        p.setComentarios(dataProducto.getComentarios());
        to = getTipoOrden(dataProducto.getIdTipo());
        p.setNombreTipoOrdenCompra(to.getNombreTipoOrdenCompra());
        p.setIdTipo(dataProducto.getIdTipo());
        Bodega b = new Bodega();
        b = getBodega(dataProducto.getIdBodegaFK());
        p.setNombreBodega(b.getNombreBodega());
        p.setIdBodegaFK(dataProducto.getIdBodegaFK());
        listaMercanciaProducto.add(p);

        permisionToGenerate = false;
        dataProducto.reset();
        subProducto = new Subproducto();
        listaTiposOrden = ifaceTipoOrdenCompra.getTipos();
        listaBodegas = ifaceCatBodegas.getBodegas();

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

    private TipoOrdenCompra getTipoOrden(BigDecimal idTipo) {
        TipoOrdenCompra compra = new TipoOrdenCompra();

        for (TipoOrdenCompra tipoOrden : listaTiposOrden) {
            if (tipoOrden.getIdTocPK().equals(idTipo)) {
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

    public EntradaMercancia2 getData() {
        return data;
    }

    public void setData(EntradaMercancia2 data) {
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

    public IfaceTipoOrdenCompra getIfaceTipoOrdenCompra() {
        return ifaceTipoOrdenCompra;
    }

    public void setIfaceTipoOrdenCompra(IfaceTipoOrdenCompra ifaceTipoOrdenCompra) {
        this.ifaceTipoOrdenCompra = ifaceTipoOrdenCompra;
    }

    public ArrayList<TipoOrdenCompra> getListaTiposOrden() {
        return listaTiposOrden;
    }

    public void setListaTiposOrden(ArrayList<TipoOrdenCompra> listaTiposOrden) {
        this.listaTiposOrden = listaTiposOrden;
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

    public IfaceNegocioExistencia getIfaceNegocioExistencia() {
        return ifaceNegocioExistencia;
    }

    public void setIfaceNegocioExistencia(IfaceNegocioExistencia ifaceNegocioExistencia) {
        this.ifaceNegocioExistencia = ifaceNegocioExistencia;
    }

}
