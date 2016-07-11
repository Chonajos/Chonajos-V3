/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Competidor;
import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.dominio.Subproducto;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCompetidores;
import com.web.chon.service.IfacePreciosCompetencias;
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

/**
 *
 * @author marcogante
 */
@Component
@Scope("view")
public class BeanCompetidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceCompetidores ifaceCompetidores;
    @Autowired
    private IfacePreciosCompetencias ifacePreciosCompetencias;
    @Autowired
    private PlataformaSecurityContext context;

    private String title = "";
    private String viewEstate = "";

    private Subproducto subProducto;

    private PreciosCompetencia data;
    private PreciosCompetencia dataEdit;
    private PreciosCompetencia dataRemove;
    
   

    private Competidor dataCompetidor;
    private Competidor dataEditCompetidor;
    private Competidor dataRemoveCompetidor;

    private ArrayList<Competidor> listaCompetidor;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<PreciosCompetencia> listaPrecios;
    private ArrayList<PreciosCompetencia> modelo;
    private ArrayList<Competidor> modeloCompetidor;

    private String fecha;

    @PostConstruct
    public void init() {
        setTitle("Registro Precios de Venta Competencia");
        setViewEstate("init");
        listaCompetidor = ifaceCompetidores.getCometidores();
        data = new PreciosCompetencia();
        dataCompetidor = new Competidor();
        subProducto = new Subproducto();
        modelo = new ArrayList<PreciosCompetencia>();
        fecha = TiempoUtil.getFechaDDMMYYYY(new Date());
        modelo = ifacePreciosCompetencias.getPreciosCompetencias(fecha);

    }
    public void back()
    {
        
    }
    public void verCompetidores()
    {
       setViewEstate("viewCompetidores"); 
       modeloCompetidor = ifaceCompetidores.getCometidores();
    }
    public void editRegistroCompetidor()
    {
        
    }
    public void removeCompetidor(){
        
    }

    public void remove() {
        if (ifacePreciosCompetencias.deletePrecioCompetencia(dataRemove) != 0) {
            modelo.remove(dataRemove);
            JsfUtil.addSuccessMessageClean("Registro Eliminado");
        } else {
            JsfUtil.addSuccessMessageClean("Ocurrio un error al intentar borrar el registro");
        }
        modelo = ifacePreciosCompetencias.getPreciosCompetencias(fecha);
    }

    public void editRegistro() {
        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubProductoPk());
        data.setIdPcPk(dataEdit.getIdPcPk());
        data.setIdCompetidorFk(dataEdit.getIdCompetidorFk());
        data.setNombreCompetidor(dataEdit.getNombreCompetidor());
        data.setNombreProducto(dataEdit.getNombreProducto());
        data.setPrecioVenta(dataEdit.getPrecioVenta());
        setViewEstate("update");
    }

    public void nuevoCompetidor() {
        BigDecimal idCompetidor = new BigDecimal(ifaceCompetidores.getNextVal());
        dataCompetidor.setIdCompetidorPk(idCompetidor);
        
        if (dataCompetidor.getNombreCompetidor().equals("")) {
            JsfUtil.addErrorMessageClean("Agregar un nombre");
        } else if (ifaceCompetidores.insertCompetidor(dataCompetidor) != 0) {
            listaCompetidor = ifaceCompetidores.getCometidores();

            JsfUtil.addSuccessMessageClean("Competidor Registrado Correctamente");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un problema");
        }

    }

    public void updateProducto() {
        dataEdit.setIdCompetidorFk(data.getIdCompetidorFk());
        dataEdit.setIdPcPk(data.getIdPcPk());
        dataEdit.setIdSubProductoPk(subProducto.getIdSubproductoPk());
        dataEdit.setNombreCompetidor(data.getNombreCompetidor());
        dataEdit.setNombreProducto(data.getNombreProducto());
        dataEdit.setPrecioVenta(data.getPrecioVenta());
        if (ifacePreciosCompetencias.updateCompetencia(dataEdit) != 0) {
            JsfUtil.addSuccessMessageClean("Registro Editado Correctamente");
            subProducto = new Subproducto();
            data.reset();
        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un problema al intentar editar el registro");
        }
        modelo = ifacePreciosCompetencias.getPreciosCompetencias(fecha);
        setViewEstate("init");

    }

    public void cancel() {
        data.reset();
        subProducto = new Subproducto();
        viewEstate = "init";

    }

    public void registrar() {
        if (data.getPrecioVenta() == null || data.getIdCompetidorFk() == null) {
            JsfUtil.addErrorMessageClean("Por favor Ingresar los datos");
        } else {
            BigDecimal idPrecioCompetidor = new BigDecimal(ifacePreciosCompetencias.getNextVal());
            data.setIdPcPk(idPrecioCompetidor);
            data.setIdSubProductoPk(subProducto.getIdSubproductoPk());
            //System.out.println("Bean ==============>" + data.toString());
            PreciosCompetencia pc = new PreciosCompetencia();
            data.setFechaRegistro(context.getFechaSistema());
            pc = ifacePreciosCompetencias.getPreciosCompetenciasByCompetidorProducto(data);
            //System.out.println("PC=> " + pc.toString());
            if (pc.getIdPcPk() == null) 
            {
                if (ifacePreciosCompetencias.insertPreciosCompetencias(data) != 0) {
                    data.reset();
                    subProducto = new Subproducto();
                    modelo = ifacePreciosCompetencias.getPreciosCompetencias(fecha);
                    data.reset();
                    subProducto = new Subproducto();
                    JsfUtil.addSuccessMessageClean("Precio de Venta Registrado Correctamente");
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema");
                }
            } else {
                //System.out.println("=========="+data.toString());
                data.setIdPcPk(pc.getIdPcPk());
                if (ifacePreciosCompetencias.updateCompetencia(data) != 0) {
                    JsfUtil.addSuccessMessageClean("Registro Editado Correctamente");
                    data.reset();
                    subProducto = new Subproducto();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema al intentar editar---- el registro");
                }
                modelo = ifacePreciosCompetencias.getPreciosCompetencias(fecha);
            }
        }
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

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

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public PreciosCompetencia getData() {
        return data;
    }

    public void setData(PreciosCompetencia data) {
        this.data = data;
    }

    public ArrayList<Competidor> getListaCompetidor() {
        return listaCompetidor;
    }

    public void setListaCompetidor(ArrayList<Competidor> listaCompetidor) {
        this.listaCompetidor = listaCompetidor;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<PreciosCompetencia> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(ArrayList<PreciosCompetencia> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    public Competidor getDataCompetidor() {
        return dataCompetidor;
    }

    public void setDataCompetidor(Competidor dataCompetidor) {
        this.dataCompetidor = dataCompetidor;
    }

    public ArrayList<PreciosCompetencia> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<PreciosCompetencia> modelo) {
        this.modelo = modelo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public PreciosCompetencia getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(PreciosCompetencia dataEdit) {
        this.dataEdit = dataEdit;
    }

    public PreciosCompetencia getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(PreciosCompetencia dataRemove) {
        this.dataRemove = dataRemove;
    }

    public Competidor getDataEditCompetidor() {
        return dataEditCompetidor;
    }

    public void setDataEditCompetidor(Competidor dataEditCompetidor) {
        this.dataEditCompetidor = dataEditCompetidor;
    }

    public Competidor getDataRemoveCompetidor() {
        return dataRemoveCompetidor;
    }

    public void setDataRemoveCompetidor(Competidor dataRemoveCompetidor) {
        this.dataRemoveCompetidor = dataRemoveCompetidor;
    }

    public ArrayList<Competidor> getModeloCompetidor() {
        return modeloCompetidor;
    }

    public void setModeloCompetidor(ArrayList<Competidor> modeloCompetidor) {
        this.modeloCompetidor = modeloCompetidor;
    }
    

}
