/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.TransferenciaMercancia;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTransferenciaMercancia;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author marcogante
 */
@Component
@Scope("view")
public class beanTransferenciaMerca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
     @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceTransferenciaMercancia ifaceNegocioTransferenciaMercancia;
    
    private ArrayList<Sucursal> listaSucursalesNueva;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Bodega> listaBodegasNueva;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TransferenciaMercancia> listaTransferencias;
    
    private TransferenciaMercancia data;
    private Subproducto subProducto;
    private ExistenciaProducto expro;
    
    private String title = "";
    private String viewEstate = "";
    private String idSubProductoFK;
    
    private BigDecimal idSucursalFK;
    private BigDecimal idTipoEmpaque;
    private BigDecimal idProvedorFK;
    private BigDecimal idBodegaFK;
    private BigDecimal idExistenciaFK;
    
    private boolean permisionToPush;
    private boolean permisionToTrans;
    
    

    @PostConstruct
    public void init() {
        
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursalesNueva = new ArrayList<Sucursal>();
        listaProvedores = new ArrayList<Provedor>();
        
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaSucursalesNueva = ifaceCatSucursales.getSucursales();
        
        listaProvedores = ifaceCatProvedores.getProvedores();
        permisionToPush = true;
        permisionToTrans = true;
        expro = new ExistenciaProducto();

        listaTransferencias = new ArrayList<TransferenciaMercancia>();

        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegas();

        listaBodegasNueva = new ArrayList<Bodega>();
        listaBodegasNueva = ifaceCatBodegas.getBodegas();

        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        data = new TransferenciaMercancia();

        subProducto = new Subproducto();

        setTitle("Transferencia de Mercancia");
        setViewEstate("init");

    }

    public void transferir() {
        if (data.getCantidadMovida() == null || data.getCantidadMovida().intValue()<1 || data.getCantidadMovida().intValue() > data.getCantidad().intValue()) {
            JsfUtil.addErrorMessage("Error!", "Cantidad a transferir insuficiente");
        } else {
            data.setIdExistenciaProductoFK(idExistenciaFK);
            if (ifaceNegocioTransferenciaMercancia.insertTransferenciaMercancia(data) == 0) {
                JsfUtil.addErrorMessage("Error!", "Ocurrio un error al insertar la Transferencia");

            } else 
            {

                int cantidad_a_restar = expro.getCantidadEmpaque().intValue();
                int restador = data.getCantidadMovida().intValue();
                int cantidad_a_restar_kilos = expro.getKilosExistencia().intValue();
                
                TipoEmpaque pesoEmpaque = ifaceEmpaque.getEmpaqueById(idTipoEmpaque.intValue());
                
                data.setKilosMovios(new BigDecimal(pesoEmpaque.getPesoKiloEmpaque().intValue() * restador));
                
                int restador_kilos = data.getKilosMovios().intValue();
                
                expro.setCantidadEmpaque(new BigDecimal(cantidad_a_restar - restador));
                expro.setKilosExistencia(new BigDecimal(cantidad_a_restar_kilos - restador_kilos));

                if (ifaceNegocioExistencia.updateExistenciaProducto(expro) == 0) 
                {
                    //System.out.println("Ocurrio un error al restar");
                    JsfUtil.addErrorMessage("Error!", "Cantidad a transferir insuficiente");
                } else {
                    //System.out.println("se Resto del original");

                    ExistenciaProducto ep = new ExistenciaProducto();
                    
                    ep.setCantidadEmpaque(new BigDecimal(restador));
                    ep.setIdBodegaFk(data.getIdBodegaNueva());
                    //ep.setIdProvedorFk(idProvedorFK);
                    ep.setIdSubProductoFk(idSubProductoFK);
                    ep.setIdSucursalFk(data.getIdSucursalNuevaFK());
                    //ep.setIdTipoEmpaque(idTipoEmpaque);
                    //ep.setKilosEmpaque(new BigDecimal(restador_kilos));
                    ep.setKilosExistencia(new BigDecimal(restador_kilos));

                    //System.out.println("Fredddy: "+ep.toString());
                    //siguiente paso insertar o actualizar el registro en donde se paso la mercancia.
                    int resultado = ifaceNegocioExistencia.insertExistenciaProducto(ep);

                    switch (resultado) {
                        case 0:
                            JsfUtil.addErrorMessage("Error!", "Ocurrio un error al actualizar existencias");
                            break;
                        case 2:
                            
                            ArrayList<ExistenciaProducto> existente = new ArrayList<ExistenciaProducto>();

                            existente = ifaceNegocioExistencia.getExistenciaProductoId(data.getIdSucursalNuevaFK(), idSubProductoFK, idTipoEmpaque, data.getIdBodegaNueva(), idProvedorFK);
                            
                            ExistenciaProducto expro = new ExistenciaProducto();
                            expro = existente.get(0);

                            int cantidadEmpaque = expro.getCantidadEmpaque().intValue();
                           // int kiltoproempaque = expro.getKilosEmpaque().intValue();
                            int kilosTotales = expro.getKilosExistencia().intValue();
                            ep.setCantidadEmpaque(new BigDecimal(ep.getCantidadEmpaque().intValue() + cantidadEmpaque));
                           // ep.setKilosEmpaque(new BigDecimal(ep.getKilosEmpaque().intValue() + kiltoproempaque));
                            ep.setKilosExistencia(new BigDecimal(ep.getKilosExistencia().intValue() + kilosTotales));
                            ep.setIdExistenciaProductoPk(expro.getIdExistenciaProductoPk());

                            if (ifaceNegocioExistencia.updateExistenciaProducto(ep) == 0) {
                                JsfUtil.addErrorMessage("Error!", "Ocurrio un error al actualizar existencias ya registradas");
                            } else {
                                clean();
                                JsfUtil.addSuccessMessage("Transferencia Correcta!");
                            }
                            break;
                        default:
                            clean();
                            JsfUtil.addSuccessMessage("Transferencia Correcta!");
                            break;
                    }
                }
            }
        }
    }

    public void clean() {
        
        data.reset();
        
        idSucursalFK = null;
        idSubProductoFK = null;
        idTipoEmpaque = null;
        idProvedorFK = null;
        idBodegaFK = null;
        idExistenciaFK = null;
        subProducto = null;
        permisionToPush = true;

    }

    public void buscar() {
        ArrayList<ExistenciaProducto> existente = new ArrayList<ExistenciaProducto>();
        
        existente = ifaceNegocioExistencia.getExistenciaProductoId(idSucursalFK, idSubProductoFK, idTipoEmpaque, idBodegaFK, idProvedorFK);
        
        if (existente == null || existente.isEmpty()) {
            JsfUtil.addErrorMessage("Error!", "No se encontraron existencias");

        } else {
            
            expro = existente.get(0);
            
            data.setCantidad(expro.getCantidadEmpaque());
            data.setKilos(expro.getKilosExistencia());
            
            idExistenciaFK = expro.getIdExistenciaProductoPk();

        }

    }

    public void changePermision() {
        if (data.getCantidadMovida() == null) {
            permisionToTrans = true;
        } else {
            permisionToTrans = false;
        }
    }

    public void permision() {
        
        idSubProductoFK = subProducto.getIdSubproductoPk();
        data.setCantidad(null);
        data.setKilos(null);
        
        if (idSucursalFK == null || idProvedorFK == null || idBodegaFK == null || idSubProductoFK == null || idTipoEmpaque == null) {
            data.reset();
            
            System.out.println(idBodegaFK + ":" + idProvedorFK + ":" + idSubProductoFK + ":" + idSucursalFK + "" + idTipoEmpaque + "");
            permisionToPush = true;

        } else {
            permisionToPush = false;

        }
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

    public IfaceSubProducto getIfaceSubProducto() {
        return ifaceSubProducto;
    }

    public void setIfaceSubProducto(IfaceSubProducto ifaceSubProducto) {
        this.ifaceSubProducto = ifaceSubProducto;
    }

    public IfaceEmpaque getIfaceEmpaque() {
        return ifaceEmpaque;
    }

    public void setIfaceEmpaque(IfaceEmpaque ifaceEmpaque) {
        this.ifaceEmpaque = ifaceEmpaque;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
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

    public TransferenciaMercancia getData() {
        return data;
    }

    public void setData(TransferenciaMercancia data) {
        this.data = data;
    }

    public String getIdSubProductoFK() {
        return idSubProductoFK;
    }

    public void setIdSubProductoFK(String idSubProductoFK) {
        this.idSubProductoFK = idSubProductoFK;
    }

    public BigDecimal getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(BigDecimal idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    public BigDecimal getIdProvedorFK() {
        return idProvedorFK;
    }

    public void setIdProvedorFK(BigDecimal idProvedorFK) {
        this.idProvedorFK = idProvedorFK;
    }

    public BigDecimal getIdBodegaFK() {
        return idBodegaFK;
    }

    public void setIdBodegaFK(BigDecimal idBodegaFK) {
        this.idBodegaFK = idBodegaFK;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public ArrayList<TransferenciaMercancia> getListaTransferencias() {
        return listaTransferencias;
    }

    public void setListaTransferencias(ArrayList<TransferenciaMercancia> listaTransferencias) {
        this.listaTransferencias = listaTransferencias;
    }

    public ArrayList<Sucursal> getListaSucursalesNueva() {
        return listaSucursalesNueva;
    }

    public void setListaSucursalesNueva(ArrayList<Sucursal> listaSucursalesNueva) {
        this.listaSucursalesNueva = listaSucursalesNueva;
    }

    public ArrayList<Bodega> getListaBodegasNueva() {
        return listaBodegasNueva;
    }

    public void setListaBodegasNueva(ArrayList<Bodega> listaBodegasNueva) {
        this.listaBodegasNueva = listaBodegasNueva;
    }

    public BigDecimal getIdSucursalFK() {
        return idSucursalFK;
    }

    public void setIdSucursalFK(BigDecimal idSucursalFK) {
        this.idSucursalFK = idSucursalFK;
    }

    public boolean isPermisionToPush() {
        return permisionToPush;
    }

    public void setPermisionToPush(boolean permisionToPush) {
        this.permisionToPush = permisionToPush;
    }

    public boolean isPermisionToTrans() {
        return permisionToTrans;
    }

    public void setPermisionToTrans(boolean permisionToTrans) {
        this.permisionToTrans = permisionToTrans;
    }

    public IfaceNegocioExistencia getIfaceNegocioExistencia() {
        return ifaceNegocioExistencia;
    }

    public void setIfaceNegocioExistencia(IfaceNegocioExistencia ifaceNegocioExistencia) {
        this.ifaceNegocioExistencia = ifaceNegocioExistencia;
    }

    public IfaceTransferenciaMercancia getIfaceNegocioTransferenciaMercancia() {
        return ifaceNegocioTransferenciaMercancia;
    }

    public void setIfaceNegocioTransferenciaMercancia(IfaceTransferenciaMercancia ifaceNegocioTransferenciaMercancia) {
        this.ifaceNegocioTransferenciaMercancia = ifaceNegocioTransferenciaMercancia;
    }

    public BigDecimal getIdExistenciaFK() {
        return idExistenciaFK;
    }

    public void setIdExistenciaFK(BigDecimal idExistenciaFK) {
        this.idExistenciaFK = idExistenciaFK;
    }

    
}
