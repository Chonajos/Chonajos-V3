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
import com.web.chon.dominio.VentaProductoMayoreo;
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
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.inputtext.InputText;
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

    private BigDecimal BIGDECIMAL_UNO = new BigDecimal(1);
    private BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
    private int INT_UNO = 1;

    @PostConstruct
    public void init() {
        usuarioDominio = context.getUsuarioAutenticado();
        data = new TransferenciaMercancia();

        lstSucursal = ifaceCatSucursales.getSucursales();
        data.setIdSucursalOrigen(new BigDecimal(usuarioDominio.getSucId()));
        searchBodega();

        setTitle("Transferencia de Mercancia");
        setViewEstate("init");
        permisionToWrite = true;

    }

    public void transferir() {

        if (validaTransferencia()) {
            ifaceNegocioExistencia.insertExistenciaProducto(existenciaProducto);
        }

    }

    public boolean validaTransferencia() {

        //Se valida que no se vallan nulos los datos
        if (data.getKilosMovios() == null || data.getCantidadMovida() == null || data.getKilosMovios().compareTo(BIGDECIMAL_UNO) == -INT_UNO || data.getCantidadMovida().compareTo(BIGDECIMAL_UNO) == -INT_UNO) {
            JsfUtil.addErrorMessage("Kilos a mover o Empaques van vacios.");
            return false;

        }

        //se valida que no se pueda transferir kilos y empaques mayores a los existentes
        if (data.getKilosMovios().compareTo(existenciaProducto.getKilosTotalesProducto()) <= 0 && data.getCantidadMovida().compareTo(existenciaProducto.getCantidadPaquetes()) <= 0) {
            return true;
        } else {
            JsfUtil.addErrorMessage("No se puede transferir. Cantidad empaque o cantidad de kilos a mover es mayor al existente.");
            return false;
        }

    }

    public void setKilosPromedio(AjaxBehaviorEvent event) {

        InputText input;
        BigDecimal empaques = new BigDecimal(0);

        if (event != null) {
            input = (InputText) event.getSource();
            empaques = new BigDecimal(input.getSubmittedValue().toString());

            if (empaques.compareTo(BIGDECIMAL_ZERO) == 1) {
                if (empaques.compareTo(existenciaProducto.getCantidadPaquetes()) == 0) {

                    data.setKilosMovios(existenciaProducto.getKilosTotalesProducto());
                } else {
                    data.setKilosMovios((existenciaProducto.getKilosTotalesProducto().divide(existenciaProducto.getCantidadPaquetes(), 2, RoundingMode.UP)).multiply(empaques));
                }
            }

        }
    }

    public void clean() {

        data.reset();

    }

    public void searchBodega() {
        lstBodega = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalOrigen());
        if (lstBodega != null && !lstBodega.isEmpty()) {
            data.setIdBodegaOrigen(lstBodega.get(0).getIdBodegaPK());
        }

        searchEcistencia();
    }

    public void searchEcistencia() {
        lstExistenciaProducto = ifaceNegocioExistencia.getExistencias(data.getIdSucursalOrigen(), data.getIdBodegaOrigen(), null, null, null, null, null);
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
