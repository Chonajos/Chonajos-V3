package com.web.chon.service;

import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.negocio.NegocioEntradaMenudeo;
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
public class ServiceEntradaMenudeo implements IfaceEntradaMenudeo {

    NegocioEntradaMenudeo ejb;

    @Autowired
    private IfaceEntradaMenudeoProducto ifaceEntradaMenudeoProducto;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMenudeo) Utilidades.getEJBRemote("ejbEntradaMenudeo", NegocioEntradaMenudeo.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMenudeo entrada) {
        getEjb();
        return ejb.insertEntradaMenudeo(entrada);
    }

    @Override
    public int buscaMaxMovimiento(EntradaMenudeo entrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getFolio(BigDecimal idSucursal) {
        getEjb();
        try {
            return ejb.getFolio(idSucursal);

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<EntradaMenudeo> getEntradaProductoByIntervalDate(Date fechaFiltroInicio, Date fechaFiltroFin, BigDecimal idSucursal, String idSubproductoPk) {
        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMenudeo> lstEntradaMercancia2 = new ArrayList<EntradaMenudeo>();
        lstObject = ejb.getEntradaProductoByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal, idSubproductoPk);

        for (Object[] obj : lstObject) {

            EntradaMenudeo dominio = new EntradaMenudeo();
            dominio.setIdEmmPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setFecha(obj[2] == null ? null : (Date) obj[2]);
            dominio.setIdSucursalFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setIdStatusFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setKilosTotales(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setKilosProvedor(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            dominio.setComentarios(obj[7] == null ? "" : obj[7].toString());
            dominio.setFolio(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setIdUsuario(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setNombreProvedor(obj[10] == null ? "" : obj[10].toString());
            dominio.setApPaternoProvedor(obj[11] == null ? "" : obj[11].toString());
            dominio.setApMaternoProvedor(obj[12] == null ? "" : obj[12].toString());
            dominio.setNombreUsuarioRecibe(obj[13] == null ? "" : obj[13].toString());
            dominio.setNombreSucursal(obj[14] == null ? "" : obj[14].toString());

            ArrayList<EntradaMenudeoProducto> lstDetalle = new ArrayList<EntradaMenudeoProducto>();
            lstDetalle = ifaceEntradaMenudeoProducto.getEntradaProductoById(dominio.getIdEmmPk());
            dominio.setListaDetalleProducto(lstDetalle);
            BigDecimal sumaKilos = new BigDecimal(0);
            BigDecimal sumaCostos = new BigDecimal(0);
            for (EntradaMenudeoProducto item : lstDetalle) {
                sumaKilos = sumaKilos.add(item.getKilosTotales(), MathContext.UNLIMITED);
                sumaCostos = sumaCostos.add(item.getPrecio(), MathContext.UNLIMITED);
                dominio.setSumaCostos(sumaCostos);
                dominio.setSumaKilos(sumaKilos);
            }

            lstEntradaMercancia2.add(dominio);
        }
        return lstEntradaMercancia2;

    }

    @Override
    public int cancelarEntrada(BigDecimal folioCancelar) {
        getEjb();

        int result = ejb.cancelarEntrada(folioCancelar);
        return result;
    }

}
