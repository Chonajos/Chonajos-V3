package com.web.chon.service;

import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioVenta;
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
 * Servicio para el modulo de Ventas
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceVenta implements IfaceVenta {

    NegocioVenta ejb;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVenta) Utilidades.getEJBRemote("ejbVenta", NegocioVenta.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVenta(Venta venta, int folioVenta) {
        getEjb();
        return ejb.insertarVenta(venta, folioVenta);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<Venta> getVentasByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, String idProducto, BigDecimal idTipoVenta,BigDecimal idCliente) {
        getEjb();
        ArrayList<Venta> lstVenta = new ArrayList<Venta>();
        BigDecimal count = new BigDecimal(0);
        BigDecimal B_CONTADO = new BigDecimal(1);
        String S_CONTADO ="CONTADO";
        String S_CREDITO ="CREDITO";
        List<Object[]> lstObject = ejb.getVentasByInterval(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin), idSucursal, idStatusVenta, idProducto, idTipoVenta,idCliente);
        for (Object[] obj : lstObject) {
            Venta venta = new Venta();
            venta.setIdVentaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            venta.setFechaVenta((Date) obj[3]);
            venta.setIdStatusVenta(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            venta.setIdSucursal(Integer.parseInt(obj[5] == null ? "0" : obj[5].toString()));
            venta.setNombreCliente(obj[6] == null ? null : obj[6].toString());
            venta.setNombreVendedor(obj[7] == null ? null : obj[7].toString());
            venta.setTotalVenta(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            venta.setFolio(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            venta.setNombreSucursal(obj[10] == null ? null : obj[10].toString());
            venta.setNombreEstatus(obj[11] == null ? null : obj[11].toString());
            // se agregan estos valores en 1 para que no de error en creditos borrados 
            venta.setIdTipoVenta(obj[12] == null ? new BigDecimal(1) : new BigDecimal(obj[12].toString()));
            venta.setDescripcionTipoVenta(venta.getIdTipoVenta().equals(B_CONTADO) ? S_CONTADO:S_CREDITO);
            venta.setIdCredito(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            venta.setFechaPromesaPago(obj[14] == null ? new Date() : (Date) obj[14]);
            venta.setMontoCredito(obj[15] == null ? new BigDecimal(1) : new BigDecimal(obj[15].toString()));
            venta.setNumeroPagos(obj[16] == null ? new BigDecimal(1) : new BigDecimal(obj[16].toString()));
            venta.setPlazos(obj[17] == null ? new BigDecimal(1) : new BigDecimal(obj[17].toString()));
            venta.setaCuenta(obj[18] == null ? new BigDecimal(0) : new BigDecimal(obj[18].toString()));

            ArrayList<VentaProducto> listaProductos = new ArrayList<VentaProducto>();
            listaProductos = ifaceVentaProducto.getVentasProductoByIdVenta(venta.getIdVentaPk());
            venta.setLstVentaProducto(listaProductos);
            count = count.add(new BigDecimal(1), MathContext.UNLIMITED);
            venta.setCount(count);
            lstVenta.add(venta);
        }

        return lstVenta;
    }

    @Override
    public int getFolioByIdSucursal(int idSucursal) {
        getEjb();
        return (ejb.getFolioByIdSucursal(idSucursal));
    }

    @Override
    public int cancelarVenta(int idVenta, int idUsuario, String comentarios) {
        getEjb();
        return ejb.cancelarVenta(idVenta, idUsuario, comentarios);
    }

    @Override
    public BigDecimal getTotalVentasByDay(String fecha) {
        getEjb();
        return ejb.getTotalVentasByDay(fecha);

    }

}
