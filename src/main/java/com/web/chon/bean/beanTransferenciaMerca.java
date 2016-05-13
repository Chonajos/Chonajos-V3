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
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
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
    private ArrayList<Subproducto> lstProducto;
    private ExistenciaProducto selectedExistencia;
    
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
    private ArrayList<ExistenciaProducto> lstExistencias;
    private Usuario usuario;
    
    private UsuarioDominio usuarioDominio;
    private BigDecimal idSucu;
    @Autowired
    private PlataformaSecurityContext context;
    private boolean permisionToWrite;
    
    
    

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        idSucu = new BigDecimal(usuarioDominio.getSucId());
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(idSucu.intValue());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursalesNueva = new ArrayList<Sucursal>();
        listaProvedores = new ArrayList<Provedor>();
        
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaSucursalesNueva = ifaceCatSucursales.getSucursales();
        
        listaProvedores = ifaceCatProvedores.getProvedores();
        permisionToPush = true;
        permisionToTrans = true;
        expro = new ExistenciaProducto();
        //lstExistencias = ifaceNegocioExistencia.getExistenciasbyIdSubProducto("00000000");
        

        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegas();

        listaBodegasNueva = new ArrayList<Bodega>();
        listaBodegasNueva = ifaceCatBodegas.getBodegas();

        
        data = new TransferenciaMercancia();
        
        if(usuarioDominio.getPerId() != 1) {
            
            data.setIdSucursalNuevaFK(new BigDecimal(usuarioDominio.getSucId()));
        }

        subProducto = new Subproducto();

        setTitle("Transferencia de Mercancia");
        setViewEstate("init");
        permisionToWrite=true;

    }
    
    public void datosAmover()
    {
        
    }

    public void transferir() 
    {
       if (selectedExistencia == null || data.getCantidadMovida().intValue() == 0) {
            JsfUtil.addErrorMessage("Seleccione un Producto de la tabla, o agrego una cantidad a mover");

        }
       else
       {

          if (data.getCantidadMovida().intValue() > selectedExistencia.getCantidadPaquetes().intValue()) {
             JsfUtil.addErrorMessage("Cantidad de Empaque insuficiente");
          }else if(selectedExistencia.getIdBodegaFK().equals(data.getIdBodegaNueva())){
             JsfUtil.addErrorMessage("No se puede transferir a la misma bodega");
          }else
          {
              TransferenciaMercancia tm = new TransferenciaMercancia();
              ExistenciaProducto ep = new ExistenciaProducto();
              ep.setIdEmFK(idSucu);
              //ep.setIdSubProductoFK();
              ep.setIdTipoEmpaqueFK(idTipoEmpaque);
              ep.setKilosTotalesProducto(idTipoEmpaque);
              ep.setCantidadPaquetes(idTipoEmpaque);
              //ep.
              
              JsfUtil.addSuccessMessage("todo ok se procede a modificar existencias");
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
    
    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }
    

    public void buscaExistencias() {

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        if (idproductito != null) 
        {
            lstExistencias = ifaceNegocioExistencia.getExistencias(idSucu, null, null, idproductito, null, null, null);
            if(lstExistencias.isEmpty())
            {
                JsfUtil.addWarnMessage("No se encontraron existencias de este producto");
            }
        }else
        {
            
            //data.setCantidadEmpaque(null);
            //data.setPrecioProducto(null);
            //data.setKilosVendidos(null);
            permisionToWrite=false;
            selectedExistencia = new ExistenciaProducto();
            
            lstExistencias.clear();
            
        }
        
    }
    public void habilitarBotones()
    {
        permisionToTrans=false;
        permisionToWrite=false;
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

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ExistenciaProducto getExpro() {
        return expro;
    }

    public void setExpro(ExistenciaProducto expro) {
        this.expro = expro;
    }

    public ArrayList<ExistenciaProducto> getLstExistencias() {
        return lstExistencias;
    }

    public void setLstExistencias(ArrayList<ExistenciaProducto> lstExistencias) {
        this.lstExistencias = lstExistencias;
    }

    public ExistenciaProducto getSelectedExistencia() {
        return selectedExistencia;
    }

    public void setSelectedExistencia(ExistenciaProducto selectedExistencia) {
        this.selectedExistencia = selectedExistencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public BigDecimal getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(BigDecimal idSucu) {
        this.idSucu = idSucu;
    }

    public boolean isPermisionToWrite() {
        return permisionToWrite;
    }

    public void setPermisionToWrite(boolean permisionToWrite) {
        this.permisionToWrite = permisionToWrite;
    }

    

    
}
