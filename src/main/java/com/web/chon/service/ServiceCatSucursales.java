/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Pagina;
import com.web.chon.dominio.Sucursal;
import com.web.chon.negocio.NegocioCatSucursales;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceCatSucursales implements IfaceCatSucursales {

    NegocioCatSucursales ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Sucursal> getSucursales() {
        try {
            ArrayList<Sucursal> lista_sucursales = new ArrayList<Sucursal>();
            ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            List<Object[]> lstObject = ejb.getSucursales();

            for (Object[] obj : lstObject) {
                Sucursal s = new Sucursal();
                s.setIdSucursalPk(new BigDecimal(obj[0].toString()));
                s.setNombreSucursal((obj[1] == null ? "" : obj[1].toString()));
                lista_sucursales.add(s);

            }
            return lista_sucursales;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public List<Object[]> getSucursalId(BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteSucursal(BigDecimal idSucursal) {
        try {
            ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            return ejb.deleteSucursal(idSucursal);
        } catch (Exception ex) {
            Logger.getLogger(NegocioCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateSucursal(Sucursal sucu) {
        try {
            ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            return ejb.updateSucursal(sucu);

        } catch (Exception ex) {
            Logger.getLogger(NegocioCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insertSucursal(Sucursal sucu) {
        try {
            ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            return ejb.insertSucursal(sucu);
        } catch (Exception ex) {
            Logger.getLogger(NegocioCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getNextVal() 
    {
        
       getEjb();
       return ejb.getNextVal();
    }

    @Override
    public Pagina<Sucursal> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<Sucursal> findAllDominio(Sucursal filters, int first, int pageSize) {

        System.out.println("ServiceCatSucursales FindAllDominio");
        getEjb();
        
        long size = ejb.getSizeListSucursales();
        System.out.println("size "+size);
        System.out.println("first "+first +"  paguezise "+pageSize);
        if(first!=0)
        {
            first++;
        }
        List<Object[]> listaSucursales = ejb.getSucursalesDetalle(first,pageSize);
        ArrayList<Sucursal> CatListaSucursales = new ArrayList();

        
        for (Object[] obj : listaSucursales) {
            Sucursal s = new Sucursal();
            s.setIdSucursalPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            s.setNombreSucursal(obj[1] == null ? "" : obj[1].toString());
            s.setCalleSucursal(obj[2] == null ? "" : obj[2].toString());
            s.setCpSucursal(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            s.setTelefonoSucursal(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            s.setNumInt(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            s.setNumExt(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            s.setIdStatusSucursalfk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            if(s.getIdSucursalPk().intValue()==1)
            {
                
                s.setStatusSucursal(false);
            }
            else
            {
                s.setStatusSucursal(true);
            }
            s.setNombreStatus(obj[8] == null ? "" : obj[8].toString());
            s.setIdEntidadFk(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            s.setNombreEntidad(obj[10] == null ? "" : obj[10].toString());
            s.setIdMunicipioFK(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            s.setNombreMunicipio(obj[12] == null ? "" : obj[12].toString());
            s.setNombreColonia(obj[13] == null ? "" : obj[13].toString());
            s.setCodigoPostal(obj[14] == null ? "" : obj[14].toString());
            CatListaSucursales.add(s);
        }
        return new Pagina<Sucursal>(CatListaSucursales, size);

    }

    @Override
    public Sucursal getById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sucursal create(Sucursal dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sucursal update(Sucursal dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sucursal> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
