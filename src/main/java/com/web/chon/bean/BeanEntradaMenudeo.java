/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMenudeo;
import com.web.chon.service.IfaceEntradaMenudeoProducto;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanEntradaMenudeo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private IfaceCatProvedores ifaceCatProvedores;
    @Autowired private IfaceEmpaque ifaceEmpaque;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceEntradaMenudeo ifaceEntradaMenudeo;
    @Autowired private IfaceEntradaMenudeoProducto ifaceEntradaMenudeoProducto;
    @Autowired private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired private IfaceMantenimientoPrecio ifaceMantenimientoPrecio;
    
    
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<EntradaMenudeoProducto> listaMenudeoProducto;

    private EntradaMenudeo data;
    private MantenimientoPrecios mantenimiento;
    private EntradaMenudeoProducto dataProducto;
    private EntradaMenudeoProducto dataRemove;
    private EntradaMenudeoProducto dataEdit;
    private Subproducto subProducto;
    private UsuarioDominio usuario;

    private String title = "";
    private String viewEstate = "";

    private boolean permisionToGenerate;
    private boolean permisionToPush;

    private BigDecimal kilosEntradaReales;

    @PostConstruct
    public void init() {
        setTitle("Registro Entrada de Mercancia");
        setViewEstate("init");
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = new ArrayList<Provedor>();
        listaProvedores = ifaceCatProvedores.getProvedores();
        listaMenudeoProducto = new ArrayList<EntradaMenudeoProducto>();

        dataProducto = new EntradaMenudeoProducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        data = new EntradaMenudeo();

        usuario = context.getUsuarioAutenticado();
        if (usuario.getPerId() != 1) {

            data.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        }
        data.setIdUsuario(usuario.getIdUsuario());
        permisionToGenerate = true;
        permisionToPush = false;
        kilosEntradaReales = new BigDecimal(0);

    }

    public void inserts() {
        int idEntradaMercanciaMenudeo = 0;
        int folio = 0;
        EntradaMenudeo entrada_mercancia = new EntradaMenudeo();
        idEntradaMercanciaMenudeo = ifaceEntradaMenudeo.getNextVal();
        entrada_mercancia.setIdEmmPk(new BigDecimal(idEntradaMercanciaMenudeo));
        entrada_mercancia.setIdProvedorFk(data.getIdProvedorFk());
        entrada_mercancia.setIdSucursalFk(data.getIdSucursalFk());
        entrada_mercancia.setKilosTotales(kilosEntradaReales);
        entrada_mercancia.setKilosProvedor(data.getKilosProvedor());
        entrada_mercancia.setComentarios(data.getComentarios());
        folio = ifaceEntradaMenudeo.getFolio(data.getIdSucursalFk());
        entrada_mercancia.setFolio(new BigDecimal(folio + 1));
        entrada_mercancia.setIdUsuario(data.getIdUsuario());

        if (ifaceEntradaMenudeo.insertEntradaMercancia(entrada_mercancia) != 0) {
            System.out.println("Se inserto correctamente");
            for (int i = 0; i < listaMenudeoProducto.size(); i++) {
                EntradaMenudeoProducto mp = new EntradaMenudeoProducto();
                mp = listaMenudeoProducto.get(i);
                mp.setIdEmmFk(entrada_mercancia.getIdEmmPk());
                mp.setIdEmmpPk(new BigDecimal(ifaceEntradaMenudeoProducto.getNextVal()));
                System.out.println("EmP:" + mp.toString());
                if (ifaceEntradaMenudeoProducto.insertEntradaMercanciaProducto(mp) != 0) {
                    System.out.println("Se ingreso correctamente");
                    
                    //una vez ingresada verificar si ya existe en la tabla existencias.
                    ExistenciaMenudeo ex = new ExistenciaMenudeo();
                    ex = ifaceExistenciaMenudeo.getExistenciasRepetidasById(mp.getIdSubproductoFk(),entrada_mercancia.getIdSucursalFk());
                    
                    if(ex.getIdExMenPk()==null)
                    {
                        System.out.println("No se encontraron repetidos");
                        //no se encontraron repetidos por lo tanto insertar
                        int idExistencia = ifaceExistenciaMenudeo.getNexVal();
                        ExistenciaMenudeo existencia = new ExistenciaMenudeo();
                        existencia.setCantidadEmpaque(mp.getCantidadEmpaque());
                        existencia.setIdExMenPk(new BigDecimal(idExistencia));
                        //existencia.setIdStatusFk(kilosEntradaReales);
                        existencia.setIdSubProductoPk(mp.getIdSubproductoFk());
                        existencia.setIdSucursalFk(entrada_mercancia.getIdSucursalFk());
                        existencia.setIdTipoEmpaqueFK(mp.getIdtipoEmpaqueFk());
                        existencia.setKilos(mp.getKilosTotales());
                        
                        if(ifaceExistenciaMenudeo.insertaExistenciaMenudeo(existencia)!=0)
                        {
                            //primero buscar si ya existe en mantenimiento de precios
                            MantenimientoPrecios mant  = new MantenimientoPrecios();
                            mant =ifaceMantenimientoPrecio.getMantenimientoPrecioById(mp.getIdSubproductoFk(), mp.getIdtipoEmpaqueFk().intValue(), entrada_mercancia.getIdSucursalFk().intValue());
                            
                            
                            System.out.println("Se inserto correcatemente en existencias");
                            if(insertaMantenimiento(mp,entrada_mercancia)){
                                System.out.println("Se inserto correcatemente en mantenimiento");
                                JsfUtil.addSuccessMessageClean("Entrada de Mercancia Correcto");
                            }else
                            {
                               System.out.println("Ocurrio algun error");
                            JsfUtil.addErrorMessageClean("Ocurrio un problema, contactar al administrador");
                     
                            }
                            
                        }
                        else
                        {
                            System.out.println("Ocurrio algun error");
                            JsfUtil.addErrorMessageClean("Ocurrio un problema, contactar al administrador");
                    
                        }
                    }
                    else
                    {
                        System.out.println("Se encontraron repetidos");
                        ex.setCantidadEmpaque(ex.getCantidadEmpaque().add(mp.getCantidadEmpaque(), MathContext.UNLIMITED));
                        ex.setKilos(ex.getKilos().add(mp.getKilosTotales(), MathContext.UNLIMITED));
                        if(ifaceExistenciaMenudeo.updateExistenciaMenudeo(ex)!=0)
                        {
                            System.out.println("Se actualizo con exito");
                            JsfUtil.addSuccessMessageClean("Entrada de Mercancia Correcto");
                        }
                        else
                        {
                            JsfUtil.addErrorMessageClean("Ocurrio un problema, contactar al administrador");
                    
                            System.out.println("Ocurrio un erro al actualizar producto repetido");
                        }
                    }
                    
                    
                    
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema, contactar al administrador");
                    System.out.println("Ocurrio algun error");
                    break;
                }

            }//fin for
            data.reset();
            dataProducto.reset();
            setViewEstate("init");
            permisionToGenerate = true;
            permisionToPush = false;
            kilosEntradaReales = new BigDecimal(0);
            listaMenudeoProducto.clear();
        }

    }
    
    public boolean insertaMantenimiento(EntradaMenudeoProducto mp,EntradaMenudeo em)
    {
       mantenimiento = new MantenimientoPrecios();
       mantenimiento.setIdSubproducto(mp.getIdSubproductoFk());
       mantenimiento.setIdTipoEmpaquePk(mp.getIdtipoEmpaqueFk());
       mantenimiento.setIdSucursal(em.getIdSucursalFk().intValue());
       mantenimiento.setCostoReal(mp.getPrecio());
       if(ifaceMantenimientoPrecio.insertarMantenimientoPrecio(mantenimiento)!=0){
           System.out.println("Se ingreso en la tabla mantenimiento de precios");
           return true;
       }else{
           System.out.println("Ocurrio un error al ingresar en mantenimiento de precios");
           return false;
       }
       
    }

    public void addProducto() {
        if (repetido() == false) {

            EntradaMenudeoProducto p = new EntradaMenudeoProducto();
            TipoEmpaque empaque = new TipoEmpaque();
            p.setIdSubproductoFk(subProducto.getIdSubproductoPk());
            p.setNombreProducto(subProducto.getNombreSubproducto());
            p.setIdtipoEmpaqueFk(dataProducto.getIdtipoEmpaqueFk());
            empaque = getEmpaque(dataProducto.getIdtipoEmpaqueFk());
            p.setNombreEmpaque(empaque.getNombreEmpaque());
            p.setCantidadEmpaque(dataProducto.getCantidadEmpaque());
            p.setPrecio(dataProducto.getPrecio());
            p.setKilosTotales(dataProducto.getKilosTotales());
            kilosEntradaReales = kilosEntradaReales.add(p.getKilosTotales(), MathContext.UNLIMITED);
            data.setKilosTotales(kilosEntradaReales);
            p.setComentarios(dataProducto.getComentarios());
            listaMenudeoProducto.add(p);
            permisionToGenerate = false;
            dataProducto.reset();
            subProducto = new Subproducto();
        } else {
            permisionToGenerate = false;
            dataProducto.reset();
            subProducto = new Subproducto();
        }

    }

    public boolean repetido() {
        if (listaMenudeoProducto.isEmpty()) {
            System.out.println("Lista Vacia");
            return false;

        } else {
            for (int i = 0; i < listaMenudeoProducto.size(); i++) {
                EntradaMenudeoProducto p = new EntradaMenudeoProducto();
                p = listaMenudeoProducto.get(i);
                dataProducto.setIdSubproductoFk(subProducto.getIdSubproductoPk());
                System.out.println("P: " + p.toString());
                System.out.println("DataProducto: " + dataProducto.toString());

                if (p.getIdtipoEmpaqueFk().equals(dataProducto.getIdtipoEmpaqueFk()) && p.getPrecio().equals(dataProducto.getPrecio()) && p.getIdSubproductoFk().equals(dataProducto.getIdSubproductoFk())) {
                    System.out.println("Entro repetido");
                    p.setCantidadEmpaque(p.getCantidadEmpaque().add(dataProducto.getCantidadEmpaque(), MathContext.UNLIMITED));
                    p.setKilosTotales(p.getKilosTotales().add(dataProducto.getKilosTotales(), MathContext.UNLIMITED));
                    System.out.println("Ya sumo");
                    return true;
                }

            }
            return false;

        }
    }

    public void updateProducto() {
        EntradaMenudeoProducto p = new EntradaMenudeoProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        dataEdit.setIdSubproductoFk(subProducto.getIdSubproductoPk());
        dataEdit.setNombreProducto(subProducto.getNombreSubproducto());
        dataEdit.setIdtipoEmpaqueFk(dataProducto.getIdtipoEmpaqueFk());
        empaque = getEmpaque(dataProducto.getIdtipoEmpaqueFk());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());
        dataEdit.setCantidadEmpaque(dataProducto.getCantidadEmpaque());
        dataEdit.setPrecio(dataProducto.getPrecio());
        dataEdit.setKilosTotales(dataProducto.getKilosTotales());
        p.setKilosTotales(dataProducto.getKilosTotales());
        dataEdit.setComentarios(dataProducto.getComentarios());
        kilosEntradaReales = kilosEntradaReales.add(dataProducto.getKilosTotales(), MathContext.UNLIMITED);
        viewEstate = "init";
        subProducto = new Subproducto();
        dataProducto.reset();
    }

    public void cancel() {
        kilosEntradaReales = kilosEntradaReales.add(dataEdit.getKilosTotales(), MathContext.UNLIMITED);
        dataProducto.reset();
        subProducto = new Subproducto();
        viewEstate = "init";
        

    }

    public void editProducto() {
        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubproductoFk());
        dataProducto.setIdSubproductoFk(dataEdit.getIdSubproductoFk());
        dataProducto.setNombreProducto(dataEdit.getNombreProducto());
        dataProducto.setIdtipoEmpaqueFk(dataEdit.getIdtipoEmpaqueFk());
        dataProducto.setNombreEmpaque(dataEdit.getNombreEmpaque());
        dataProducto.setCantidadEmpaque(dataEdit.getCantidadEmpaque());
        dataProducto.setPrecio(dataEdit.getPrecio());
        dataProducto.setKilosTotales(dataEdit.getKilosTotales());
        dataProducto.setComentarios(dataEdit.getComentarios());
        viewEstate = "update";
        kilosEntradaReales = kilosEntradaReales.subtract(dataEdit.getKilosTotales(), MathContext.UNLIMITED);
    }

    public void remove() {
        kilosEntradaReales = kilosEntradaReales.subtract(dataRemove.getKilosTotales(), MathContext.UNLIMITED);
        listaMenudeoProducto.remove(dataRemove);
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

    public ArrayList<Provedor> getListaProvedores() {
        return listaProvedores;
    }

    public void setListaProvedores(ArrayList<Provedor> listaProvedores) {
        this.listaProvedores = listaProvedores;
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

    public EntradaMenudeo getData() {
        return data;
    }

    public void setData(EntradaMenudeo data) {
        this.data = data;
    }

    public EntradaMenudeoProducto getDataProducto() {
        return dataProducto;
    }

    public void setDataProducto(EntradaMenudeoProducto dataProducto) {
        this.dataProducto = dataProducto;
    }

    public EntradaMenudeoProducto getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(EntradaMenudeoProducto dataRemove) {
        this.dataRemove = dataRemove;
    }

    public EntradaMenudeoProducto getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(EntradaMenudeoProducto dataEdit) {
        this.dataEdit = dataEdit;
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

    public boolean isPermisionToGenerate() {
        return permisionToGenerate;
    }

    public void setPermisionToGenerate(boolean permisionToGenerate) {
        this.permisionToGenerate = permisionToGenerate;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public boolean isPermisionToPush() {
        return permisionToPush;
    }

    public void setPermisionToPush(boolean permisionToPush) {
        this.permisionToPush = permisionToPush;
    }

    public ArrayList<EntradaMenudeoProducto> getListaMenudeoProducto() {
        return listaMenudeoProducto;
    }

    public void setListaMenudeoProducto(ArrayList<EntradaMenudeoProducto> listaMenudeoProducto) {
        this.listaMenudeoProducto = listaMenudeoProducto;
    }

    public BigDecimal getKilosEntradaReales() {
        return kilosEntradaReales;
    }

    public void setKilosEntradaReales(BigDecimal kilosEntradaReales) {
        this.kilosEntradaReales = kilosEntradaReales;
    }

    public MantenimientoPrecios getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(MantenimientoPrecios mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
    

}
