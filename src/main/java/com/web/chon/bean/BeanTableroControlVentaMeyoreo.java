package com.web.chon.bean;

import com.web.chon.dominio.CarroDetalle;
import com.web.chon.dominio.CarroDetalleGeneral;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.OperacionesVentasMayoreo;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceVentaMayoreo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el tablero de control de ventas
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanTableroControlVentaMeyoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;

    private ArrayList<Sucursal> lstSucursal = new ArrayList<Sucursal>();
    private ArrayList<EntradaMercancia> lstCarros = new ArrayList<EntradaMercancia>();
    private ArrayList<Provedor> lstProvedor = new ArrayList<Provedor>();
    private ArrayList<CarroDetalleGeneral> lstCarroDetalleGeneral = new ArrayList<CarroDetalleGeneral>();
    private ArrayList<CarroDetalle> lstCarroDetalle = new ArrayList<CarroDetalle>();
    private ArrayList<OperacionesVentasMayoreo> lstOperacionesVentasMayoreo = new ArrayList<OperacionesVentasMayoreo>();

    private CarroDetalleGeneral carroDetalleGeneral;

    private BigDecimal idSucursal;
    private BigDecimal idProvedor;
    private BigDecimal carroSucursal;
    private BigDecimal totalVentaGeneral;
    private BigDecimal totalComisionGeneral;
    private BigDecimal totalVentaDetalle;
    private BigDecimal totalComisionDetalle;
    private BigDecimal totalSaldoPorCobrar;
    private BigDecimal totalEmpaquesDetalle;
    private BigDecimal totalKilosDetalle;

    private UsuarioDominio usuario;
    private String title;
    private String viewEstate;
    private String tipoReporte;

    private Date fechaUltimaVenta;

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();
        lstSucursal = ifaceCatSucursales.getSucursales();
        idProvedor = null;
        idSucursal = new BigDecimal(usuario.getSucId());
        lstProvedor = ifaceCatProvedores.getProvedoresByIdSucursal(idSucursal);
        lstCarros = ifaceEntradaMercancia.getCarrosByIdSucursalAnsIdProvedor(idSucursal, null);
        tipoReporte = "cliente";
        carroDetalleGeneral = new CarroDetalleGeneral();

        setTitle("Reportes de Ventas.");
        setViewEstate("init");

    }

    public void getProvedores() {
        lstProvedor = ifaceCatProvedores.getProvedoresByIdSucursal(idSucursal);
    }

    public void getCarros() {
        lstCarros = ifaceEntradaMercancia.getCarrosByIdSucursalAnsIdProvedor(idSucursal, idProvedor);
    }

    @Override
    public void searchById() {
        viewEstate = "searchById";
        setTitle("REPORTE DE VENTAS DEL PROVEDOR " + carroDetalleGeneral.getNombreProvedor().toUpperCase() + " CARRO " + carroDetalleGeneral.getCarro() + " REMISION " + carroDetalleGeneral.getIdentificador());
        lstCarroDetalle = ifaceVentaMayoreo.getDetalleVentasCarro(idSucursal, carroDetalleGeneral.getCarro());
        lstOperacionesVentasMayoreo = ifaceVentaMayoreo.getReporteVentasByCarroAndIdSucursalAndTipoVenta(carroDetalleGeneral.getCarro(), idSucursal, null);

        if (lstCarroDetalle != null && !lstCarroDetalle.isEmpty()) {
            fechaUltimaVenta = lstCarroDetalle.get(0).getFecha();
        } else {
            fechaUltimaVenta = null;
        }
        calcularTotalesDetalle();

    }

    public void buscar() {
        lstCarroDetalleGeneral = ifaceEntradaMercancia.getReporteGeneralCarro(idSucursal, idProvedor, carroSucursal);
        calcularTotalesGeneral();

    }

    //Calcula el total de la venta del detalle general asi como la comision
    private void calcularTotalesGeneral() {
        totalComisionGeneral = new BigDecimal(0);
        totalVentaGeneral = new BigDecimal(0);

        for (CarroDetalleGeneral dominio : lstCarroDetalleGeneral) {

            totalComisionGeneral = totalComisionGeneral.add(dominio.getComision());
            totalVentaGeneral = totalVentaGeneral.add(dominio.getVenta());

        }

    }

    //Calcula el total de la venta del detalle general asi como la comision
    private void calcularTotalesDetalle() {
        totalComisionDetalle = new BigDecimal(0);
        totalVentaDetalle = new BigDecimal(0);
        totalSaldoPorCobrar = new BigDecimal(0);
        totalEmpaquesDetalle = new BigDecimal(0);
        totalKilosDetalle = new BigDecimal(0);

        for (CarroDetalle dominio : lstCarroDetalle) {
            totalComisionDetalle = totalComisionDetalle.add(dominio.getComisio().subtract(dominio.getSaldoPorCobrar()));
            totalVentaDetalle = totalVentaDetalle.add(dominio.getTotalVenta());
            totalSaldoPorCobrar = totalSaldoPorCobrar.add(dominio.getSaldoPorCobrar());
            totalEmpaquesDetalle = totalEmpaquesDetalle.add(dominio.getPaquetesVendidos());
            totalKilosDetalle = totalKilosDetalle.add(dominio.getKilosVendidos());

        }

    }

    public void back() {
        viewEstate = "init";
        setTitle("Reportes de Ventas.");
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {

        return null;
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public IfaceVentaMayoreo getIfaceVentaMayoreo() {
        return ifaceVentaMayoreo;
    }

    public void setIfaceVentaMayoreo(IfaceVentaMayoreo ifaceVentaMayoreo) {
        this.ifaceVentaMayoreo = ifaceVentaMayoreo;
    }

    public IfaceCatSucursales getIfaceCatSucursales() {
        return ifaceCatSucursales;
    }

    public void setIfaceCatSucursales(IfaceCatSucursales ifaceCatSucursales) {
        this.ifaceCatSucursales = ifaceCatSucursales;
    }

    public IfaceEntradaMercancia getIfaceEntradaMercancia() {
        return ifaceEntradaMercancia;
    }

    public void setIfaceEntradaMercancia(IfaceEntradaMercancia ifaceEntradaMercancia) {
        this.ifaceEntradaMercancia = ifaceEntradaMercancia;
    }

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public ArrayList<EntradaMercancia> getLstCarros() {
        return lstCarros;
    }

    public void setLstCarros(ArrayList<EntradaMercancia> lstCarros) {
        this.lstCarros = lstCarros;
    }

    public ArrayList<Provedor> getLstProvedor() {
        return lstProvedor;
    }

    public void setLstProvedor(ArrayList<Provedor> lstProvedor) {
        this.lstProvedor = lstProvedor;
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

    public BigDecimal getCarroSucursal() {
        return carroSucursal;
    }

    public void setCarroSucursal(BigDecimal carroSucursal) {
        this.carroSucursal = carroSucursal;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<CarroDetalleGeneral> getLstCarroDetalleGeneral() {
        return lstCarroDetalleGeneral;
    }

    public void setLstCarroDetalleGeneral(ArrayList<CarroDetalleGeneral> lstCarroDetalleGeneral) {
        this.lstCarroDetalleGeneral = lstCarroDetalleGeneral;
    }

    public BigDecimal getTotalVentaGeneral() {
        return totalVentaGeneral;
    }

    public void setTotalVentaGeneral(BigDecimal totalVentaGeneral) {
        this.totalVentaGeneral = totalVentaGeneral;
    }

    public BigDecimal getTotalComisionGeneral() {
        return totalComisionGeneral;
    }

    public void setTotalComisionGeneral(BigDecimal totalComisionGeneral) {
        this.totalComisionGeneral = totalComisionGeneral;
    }

    public ArrayList<CarroDetalle> getLstCarroDetalle() {
        return lstCarroDetalle;
    }

    public void setLstCarroDetalle(ArrayList<CarroDetalle> lstCarroDetalle) {
        this.lstCarroDetalle = lstCarroDetalle;
    }

    public BigDecimal getTotalVentaDetalle() {
        return totalVentaDetalle;
    }

    public void setTotalVentaDetalle(BigDecimal totalVentaDetalle) {
        this.totalVentaDetalle = totalVentaDetalle;
    }

    public BigDecimal getTotalComisionDetalle() {
        return totalComisionDetalle;
    }

    public void setTotalComisionDetalle(BigDecimal totalComisionDetalle) {
        this.totalComisionDetalle = totalComisionDetalle;
    }

    public CarroDetalleGeneral getCarroDetalleGeneral() {
        return carroDetalleGeneral;
    }

    public void setCarroDetalleGeneral(CarroDetalleGeneral carroDetalleGeneral) {
        this.carroDetalleGeneral = carroDetalleGeneral;
    }

    public BigDecimal getTotalSaldoPorCobrar() {
        return totalSaldoPorCobrar;
    }

    public void setTotalSaldoPorCobrar(BigDecimal totalSaldoPorCobrar) {
        this.totalSaldoPorCobrar = totalSaldoPorCobrar;
    }

    public BigDecimal getTotalEmpaquesDetalle() {
        return totalEmpaquesDetalle;
    }

    public void setTotalEmpaquesDetalle(BigDecimal totalEmpaquesDetalle) {
        this.totalEmpaquesDetalle = totalEmpaquesDetalle;
    }

    public BigDecimal getTotalKilosDetalle() {
        return totalKilosDetalle;
    }

    public void setTotalKilosDetalle(BigDecimal totalKilosDetalle) {
        this.totalKilosDetalle = totalKilosDetalle;
    }

    public Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    public ArrayList<OperacionesVentasMayoreo> getLstOperacionesVentasMayoreo() {
        return lstOperacionesVentasMayoreo;
    }

    public void setLstOperacionesVentasMayoreo(ArrayList<OperacionesVentasMayoreo> lstOperacionesVentasMayoreo) {
        this.lstOperacionesVentasMayoreo = lstOperacionesVentasMayoreo;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }
    
    

}
