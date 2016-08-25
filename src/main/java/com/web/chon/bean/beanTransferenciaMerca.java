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
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la cruz juna
 */
@Component
@Scope("view")
public class beanTransferenciaMerca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceTransferenciaMercancia ifaceNegocioTransferenciaMercancia;

    private ArrayList<Bodega> lstBodega;
    private ArrayList<Sucursal> lstSucursal;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> lstExistenciaProducto;
    private ArrayList<TransferenciaMercancia> lstTransferenciaMercancia;

    private TransferenciaMercancia data;
    private ExistenciaProducto existenciaProducto;

    private String title = "";
    private String viewEstate = "";

    private UsuarioDominio usuarioDominio;

    private boolean permisionToWrite;

    @PostConstruct
    public void init() {
        usuarioDominio = context.getUsuarioAutenticado();
        data = new TransferenciaMercancia();

        lstSucursal = ifaceCatSucursales.getSucursales();
        data.setIdSucursalNuevaFK(new BigDecimal(usuarioDominio.getSucId()));
        searchBodega();

        setTitle("Transferencia de Mercancia");
        setViewEstate("init");
        permisionToWrite = true;

    }

    public void transferir() {

    }

    public void clean() {

        data.reset();

    }

    public void searchBodega() {
        lstBodega = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalNuevaFK());
        if (lstBodega != null && !lstBodega.isEmpty()) {
            data.setIdBodegaNueva(lstBodega.get(0).getIdBodegaPK());
        }

        searchEcistencia();
    }

    public void searchEcistencia() {
        lstExistenciaProducto = ifaceNegocioExistencia.getExistencias(data.getIdSucursalNuevaFK(), data.getIdBodegaNueva(), null, null, null, null, null);
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public void buscaExistencias() {

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

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public boolean isPermisionToWrite() {
        return permisionToWrite;
    }

    public void setPermisionToWrite(boolean permisionToWrite) {
        this.permisionToWrite = permisionToWrite;
    }

    public ArrayList<Bodega> getLstBodega() {
        return lstBodega;
    }

    public void setLstBodega(ArrayList<Bodega> lstBodega) {
        this.lstBodega = lstBodega;
    }

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public ArrayList<ExistenciaProducto> getLstExistenciaProducto() {
        return lstExistenciaProducto;
    }

    public void setLstExistenciaProducto(ArrayList<ExistenciaProducto> lstExistenciaProducto) {
        this.lstExistenciaProducto = lstExistenciaProducto;
    }

    public ArrayList<TransferenciaMercancia> getLstTransferenciaMercancia() {
        return lstTransferenciaMercancia;
    }

    public void setLstTransferenciaMercancia(ArrayList<TransferenciaMercancia> lstTransferenciaMercancia) {
        this.lstTransferenciaMercancia = lstTransferenciaMercancia;
    }

    public ExistenciaProducto getExistenciaProducto() {
        return existenciaProducto;
    }

    public void setExistenciaProducto(ExistenciaProducto existenciaProducto) {
        this.existenciaProducto = existenciaProducto;
    }

}
