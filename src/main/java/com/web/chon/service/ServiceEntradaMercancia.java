package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.Pagina;

import com.web.chon.negocio.NegocioEntradaMercancia;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercancia implements IfaceEntradaMercancia {

    NegocioEntradaMercancia ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMercancia) Utilidades.getEJBRemote("ejbEntradaMercancia", NegocioEntradaMercancia.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) {

        getEjb();
        return ejb.insertEntradaMercancia(entrada);

    }

    @Override
    public int buscaMaxMovimiento(EntradaMercancia2 entrada) {
        getEjb();
        try {
            System.out.println("Entrada Service: " + entrada.toString());
            return ejb.buscaMaxMovimiento(entrada);
        } catch (Exception ex) {
            //Logger.getLogger(NegocioEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<EntradaMercancia2> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor) {
        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMercancia2> lstEntradaMercancia2 = new ArrayList<EntradaMercancia2>();

        lstObject = ejb.getEntradaProductoByIntervalDate(fechaInicio, fechaFin, idSucursal, idProvedor);

        for (Object[] obj : lstObject) {

            EntradaMercancia2 dominio = new EntradaMercancia2();
            dominio.setIdEmPK(new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFK(obj[1] == null ? null:new BigDecimal(obj[1].toString()));
            dominio.setMovimiento(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setFecha(obj[3] == null ? null : (Date) obj[3]);
            dominio.setRemision(obj[4] == null ? null : obj[4].toString());
            dominio.setIdSucursalFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setFolio(obj[6] == null ? null : obj[6].toString());
            dominio.setKilosTotales(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setKilosTotalesProvedor(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setNombreProvedor(obj[10] == null ? "" : obj[10].toString());
            dominio.setNombreSucursal(obj[11] == null ? "" : obj[11].toString());

            lstEntradaMercancia2.add(dominio);
        }

        return lstEntradaMercancia2;
    }

    @Override
    public Pagina<EntradaMercancia2> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercancia2> findAllDominio(EntradaMercancia2 filters, int first, int pageSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia2 getById(BigDecimal dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia2 getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(EntradaMercancia2 dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(EntradaMercancia2 dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EntradaMercancia2> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EntradaMercancia2> getSubEntradaByNombre(String nombre) {
        System.out.println("Entro a Servicio, clave: "+nombre);
        try {
            ArrayList<EntradaMercancia2> lstEntradas = new ArrayList<EntradaMercancia2>();
          
            getEjb();
           List<Object[]> object = ejb.getSubEntradaByNombre(nombre);

            for (Object[] obj : object) {

                EntradaMercancia2 entrada = new EntradaMercancia2();
                entrada.setIdEmPK(obj[0] == null ? null:new BigDecimal(obj[0].toString()));
                entrada.setFolio(obj[1] == null ? "" : obj[1].toString());

                lstEntradas.add(entrada);
            }

            return lstEntradas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

}
