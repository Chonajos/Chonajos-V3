/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.OperacionesVentasMayoreo;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreo;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceVentaMayoreo implements IfaceVentaMayoreo {
    
    NegocioVentaMayoreo ejb;
    
    @Autowired
    IfaceVentaMayoreoProducto ifaceVentaMayoreoProducto;
    
    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVentaMayoreo) Utilidades.getEJBRemote("ejbVentaMayoreo", NegocioVentaMayoreo.class.getName());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int insertarVenta(VentaMayoreo venta) {
        getEjb();
        return ejb.insertarVenta(venta);
    }
    
    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }
    
    @Override
    public ArrayList<VentaMayoreo> getVentasByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, BigDecimal idTipoVenta, String idSubProductoFk) {
        getEjb();
        ArrayList<VentaMayoreo> lstVenta = new ArrayList<VentaMayoreo>();
        List<Object[]> lstObject = ejb.getVentasByInterval(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin), idSucursal, idStatusVenta, idTipoVenta);
        BigDecimal ganacias = new BigDecimal(0);
        for (Object[] obj : lstObject) {
            
            VentaMayoreo venta = new VentaMayoreo();
            venta.setIdVentaMayoreoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFK(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            venta.setFechaVenta(obj[3] == null ? null : (Date) obj[3]);
            venta.setFechaPromesaPago(obj[4] == null ? null : (Date) obj[4]);
            venta.setIdStatusFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            venta.setFechaPago(obj[6] == null ? null : (Date) obj[6]);
            venta.setIdSucursalFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            venta.setIdtipoVentaFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            venta.setVentaSucursal(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            venta.setIdCajeroFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            venta.setIdCancelUser(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            venta.setFechaCancelacion((Date) obj[12]);
            venta.setNombreCliente(obj[13] == null ? "" : obj[13].toString());
            venta.setNombreCliente(obj[14].toString());
            venta.setNombreVendedor(obj[15].toString());
            venta.setTotalVenta(obj[16] == null ? new BigDecimal(0) : new BigDecimal(obj[16].toString()));
            venta.setNombreTipoVenta(obj[17].toString());
            ganacias = obj[18] == null ? new BigDecimal(0) : new BigDecimal(obj[18].toString());
            venta.setGanciaVenta(venta.getTotalVenta().subtract(ganacias));
            venta.setListaProductos(ifaceVentaMayoreoProducto.getProductosbyIdVmFk(venta.getIdVentaMayoreoPk()));
            lstVenta.add(venta);
        }
        
        return lstVenta;
    }
    
    @Override
    public int getVentaSucursal(BigDecimal idSucursal) {
        getEjb();
        return ejb.getVentaSucursal(idSucursal);
    }
    
    @Override
    public int updateEstatusVentaByFolioSucursalAndIdSucursal(BigDecimal folioSucursal, BigDecimal idSucursal, BigDecimal estatusVenta) {
        getEjb();
        
        return ejb.updateEstatusVentaByFolioSucursalAndIdSucursal(folioSucursal, idSucursal, estatusVenta);
        
    }
    
    @Override
    public int cancelarVentaMayoreo(BigDecimal idVenta, BigDecimal idUsuario, String comentarios) {
        getEjb();
        return ejb.cancelarVentaMayoreo(idVenta, idUsuario, comentarios);
        
    }
    
    @Override
    public VentaMayoreo getVentaMayoreoByFolioidSucursalFk(BigDecimal idFolio, BigDecimal idSucursal) {
        getEjb();
        System.out.println("Entro a ServiceVentaMayoreo: Folio: " + idFolio + "IdSucursal: " + idSucursal);
        List<Object[]> Object = ejb.getVentaMayoreoByFolioidSucursalFk(idFolio, idSucursal);
        VentaMayoreo venta = new VentaMayoreo();
        for (Object[] obj : Object) {
            BigDecimal total = new BigDecimal(0);
            
            venta.setIdVentaMayoreoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFK(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            venta.setFechaVenta(obj[3] == null ? null : (Date) obj[3]);
            venta.setFechaPromesaPago(obj[4] == null ? null : (Date) obj[4]);
            venta.setIdStatusFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            venta.setFechaPago(obj[6] == null ? null : (Date) obj[6]);
            venta.setIdSucursalFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            venta.setIdtipoVentaFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            venta.setVentaSucursal(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            venta.setIdCajeroFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            venta.setIdCancelUser(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            venta.setFechaCancelacion(obj[12] == null ? null : (Date) obj[12]);
            venta.setComentariosCancel(obj[13] == null ? "" : obj[13].toString());
            venta.setNombreCliente(obj[14] == null ? "" : obj[14].toString());
            venta.setNombreVendedor(obj[15] == null ? "" : obj[15].toString());
            venta.setNombreTipoVenta(obj[16] == null ? "" : obj[16].toString());
            venta.setNombreEstatus(obj[17] == null ? "" : obj[17].toString());
            //ganacias = obj[18] == null ? new BigDecimal(0) : new BigDecimal(obj[18].toString());
            //venta.setGanciaVenta(venta.getTotalVenta().subtract(ganacias));
            venta.setListaProductos(ifaceVentaMayoreoProducto.getProductosbyIdVmFk(venta.getIdVentaMayoreoPk()));
            for (VentaProductoMayoreo producto : venta.getListaProductos()) {
                total = total.add(producto.getTotalVenta(), MathContext.UNLIMITED);
            }
            venta.setTotalVenta(total);
            
        }
        
        return venta;
        
    }
    
    @Override
    public ArrayList<OperacionesVentasMayoreo> getReporteVentasByCarroAndIdSucursalAndTipoVenta(BigDecimal carro, BigDecimal idSucursal, BigDecimal idTipoVenta) {
        try {
            getEjb();
            
            List<Object[]> lstObjectPrincipal = new ArrayList<Object[]>();
            List<Object[]> lstObjectSecundario = new ArrayList<Object[]>();
            lstObjectPrincipal = ejb.getReporteVentas(carro, idSucursal, idTipoVenta);
            lstObjectSecundario = ejb.getDetalleReporteVentas(carro, idSucursal, idTipoVenta);
            ArrayList<OperacionesVentasMayoreo> lstPrincipal = new ArrayList<OperacionesVentasMayoreo>();
            ArrayList<OperacionesVentasMayoreo> lstDetalle = new ArrayList<OperacionesVentasMayoreo>();
           
            for (Object[] obj : lstObjectSecundario) {
                OperacionesVentasMayoreo dominio = new OperacionesVentasMayoreo();
                
                dominio.setCarroSucursal(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                dominio.setIdSubproducto(obj[1] == null ? null : obj[1].toString());
                dominio.setNombreSubProducto(obj[2] == null ? null : obj[2].toString());
                dominio.setIdTipoEmpaque(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                dominio.setNombreEmpaque(obj[4] == null ? null : obj[4].toString());
                dominio.setEmpaquesEntrada(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                dominio.setKiloEntrada(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                dominio.setEmpaqueVendidos(obj[7] == null ? new BigDecimal(0) : new BigDecimal(obj[7].toString()));
                dominio.setKiloVendidos(obj[8] == null ? new BigDecimal(0) : new BigDecimal(obj[8].toString()));
                dominio.setPrecioVenta(obj[9] == null ? new BigDecimal(0) : new BigDecimal(obj[9].toString()));
                dominio.setConvenio(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                dominio.setIdTipoConvenio(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                dominio.setFechaVenta(obj[12] == null ? null : (Date) obj[12]);
                dominio.setStrFechaVenta(TiempoUtil.getFechaDDMMYYYYHHMM(dominio.getFechaVenta()));
                dominio.setIdCliente(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
                dominio.setNombreCliente(obj[14] == null ? "" : obj[14].toString());
                lstDetalle.add(dominio);
                
            }
            for (Object[] obj : lstObjectPrincipal) {
                OperacionesVentasMayoreo dominio = new OperacionesVentasMayoreo();
                ArrayList<OperacionesVentasMayoreo> lstSecundario = new ArrayList<OperacionesVentasMayoreo>();
                
                dominio.setCarroSucursal(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                dominio.setIdSubproducto(obj[1] == null ? null : obj[1].toString());
                dominio.setNombreSubProducto(obj[2] == null ? null : obj[2].toString());
                dominio.setIdTipoEmpaque(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                dominio.setNombreEmpaque(obj[4] == null ? null : obj[4].toString());
                dominio.setEmpaquesEntrada(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                dominio.setKiloEntrada(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                dominio.setEmpaqueVendidos(obj[7] == null ? new BigDecimal(0) : new BigDecimal(obj[7].toString()));
                dominio.setKiloVendidos(obj[8] == null ? new BigDecimal(0) : new BigDecimal(obj[8].toString()));
                dominio.setPrecioVenta(obj[9] == null ? new BigDecimal(0) : new BigDecimal(obj[9].toString()));
                dominio.setConvenio(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                dominio.setIdTipoConvenio(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                
                lstSecundario = new ArrayList<OperacionesVentasMayoreo>();
                for (OperacionesVentasMayoreo detalle : lstDetalle) {
                    
                    if (detalle.getIdSubproducto().equals(dominio.getIdSubproducto()) && detalle.getIdTipoEmpaque().equals(dominio.getIdTipoEmpaque()) && detalle.getConvenio().equals(dominio.getConvenio())) {
                        lstSecundario.add(detalle);
                    }
                }
                
                dominio.setLstOperacionesVentasMayoreo(lstSecundario);
                lstPrincipal.add(dominio);
                
            }
            
            return lstPrincipal;
        } catch (Exception ex) {
            
            System.out.println("Error >" + ex.getMessage());
            return null;
        }
        
    }
    
}
