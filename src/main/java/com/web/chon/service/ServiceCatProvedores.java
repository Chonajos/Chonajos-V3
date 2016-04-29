/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Pagina;
import com.web.chon.dominio.Provedor;
import com.web.chon.negocio.NegocioCatProvedores;

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
 * @author freddy
 */
@Service
public class ServiceCatProvedores implements IfaceCatProvedores {

    NegocioCatProvedores ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatProvedores) Utilidades.getEJBRemote("ejbCatProvedores", NegocioCatProvedores.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int deleteProvedor(BigDecimal idProvedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateProvedor(Provedor pro) {
        try {
            getEjb();

            return ejb.updateProvedor(pro);

        } catch (Exception ex) {
            Logger.getLogger(NegocioCatProvedores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insertProvedor(Provedor pro) {
        try {
            getEjb();

            return ejb.insertProvedor(pro);

        } catch (Exception ex) {
            Logger.getLogger(NegocioCatProvedores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getNextVal() {

        getEjb();
        return ejb.getNextVal();

    }

    @Override
    public Pagina<Provedor> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<Provedor> findAllDominio(Provedor filters, int first, int pageSize) {

        System.out.println("ServiceCatProvedores FindAllDominio");
        getEjb();

        long size = ejb.getSizeListProvedores();
        if (first != 0) {
            first++;
        }
        List<Object[]> listaProvedores = ejb.getProvedoresDetalle(first, pageSize);
        ArrayList<Provedor> CatListaProvedores = new ArrayList();

        for (Object[] obj : listaProvedores) {
            Provedor s = new Provedor();
            s.setIdProvedorPK(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            s.setNombreProvedor(obj[1] == null ? "" : obj[1].toString());
            s.setApaternoProve(obj[2] == null ? "" : obj[2].toString());
            s.setAmaternoProve(obj[3] == null ? "" : obj[3].toString());
            s.setEmpresa(obj[4] == null ? "" : obj[4].toString());
            s.setCalleProve(obj[5] == null ? "" : obj[5].toString());
            String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
            s.setSexoProve(auxiliar_sexo.charAt(0));
            s.setFechaNacimientoProve((Date) obj[7]);
            s.setTelefonoMovilProve(Integer.parseInt(obj[8] == null ? "0" : obj[8].toString()));
            s.setTelefonoFijoMovil(Integer.parseInt(obj[9] == null ? "0" : obj[9].toString()));
            s.setExtensionProve(Integer.parseInt(obj[10] == null ? "0" : obj[10].toString()));
            s.setNumIntProve(Integer.parseInt(obj[11] == null ? "0" : obj[11].toString()));
            s.setNumExtProve(Integer.parseInt(obj[12] == null ? "0" : obj[12].toString()));
            s.setClaveCelularProve(Integer.parseInt(obj[13] == null ? "0" : obj[13].toString()));
            s.setLadaCelularProve(Integer.parseInt(obj[14] == null ? "0" : obj[14].toString()));
            s.setIdCPFiscalProveFK(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
            s.setCalleFiscalProve(obj[16] == null ? "" : obj[16].toString());
            s.setNumIntFiscalProve(Integer.parseInt(obj[17] == null ? "0" : obj[17].toString()));
            s.setNumExtProve(Integer.parseInt(obj[18] == null ? "0" : obj[18].toString()));
            s.setIdCPFiscalProveFK(obj[19] == null ? null : new BigDecimal(obj[19].toString()));
            s.setNextelProve(Integer.parseInt(obj[20] == null ? "0" : obj[20].toString()));
            s.setRazonProve(obj[21] == null ? "" : obj[21].toString());
            s.setRfcProve(obj[22] == null ? "" : obj[22].toString());
            s.setLadaOficinaProve(Integer.parseInt(obj[23] == null ? "0" : obj[23].toString()));
            s.setClaveOficinaProve(Integer.parseInt(obj[24] == null ? "0" : obj[24].toString()));
            s.setIdStatusFK(obj[26] == null ? null : new BigDecimal(obj[26].toString()));
            s.setFechaAlta((Date) obj[27]);
            s.setNickName(obj[28] == null ? "" : obj[28].toString());
            s.setCorreo(obj[29] == null ? "" : obj[29].toString());
            s.setIdEntidadFK(obj[30] == null ? null : new BigDecimal(obj[30].toString()));
            s.setNombreEntidad(obj[31] == null ? "" : obj[31].toString());
            s.setIdMunicipioFK(obj[32] == null ? null : new BigDecimal(obj[32].toString()));
            s.setNombreMunicipio(obj[33] == null ? "" : obj[33].toString());
            s.setIdEntidadFiscalFK(obj[34] == null ? null : new BigDecimal(obj[34].toString()));
            s.setNombreEntidadFiscal(obj[35] == null ? "" : obj[35].toString());
            s.setIdMunicipioFiscalFK(obj[36] == null ? null : new BigDecimal(obj[36].toString()));
            s.setNombreMunicipioFiscal(obj[37] == null ? "" : obj[37].toString());
            s.setNombreColonia(obj[38] == null ? "" : obj[38].toString());
            s.setCodigoPostal(obj[39] == null ? "" : obj[39].toString());
            s.setNombreColoniaFiscal(obj[40] == null ? "" : obj[40].toString());
            s.setCodigoPostalFiscal(obj[41] == null ? "" : obj[41].toString());
            s.setNombreStatus(obj[42] == null ? "" : obj[42].toString());

            CatListaProvedores.add(s);
        }
        return new Pagina<Provedor>(CatListaProvedores, size);

    }

    @Override
    public Provedor getById(BigDecimal dominio) {
        getEjb();
        List<Object[]> lstObject = ejb.getById(dominio);
        Provedor provedor = new Provedor();
        for (Object[] obj : lstObject) {
            provedor.setIdProvedorPK(new BigDecimal(obj[0].toString()));
            provedor.setNombreProvedor(obj[1].toString());
            provedor.setApaternoProve(obj[2] == null ? "" : obj[2].toString());
            provedor.setAmaternoProve(obj[3] == null ? "" : obj[3].toString());
        }

        return provedor;
    }

    @Override
    public int create(Provedor dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Provedor dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Provedor> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Provedor> getProvedores() {

        try {
            System.out.println("ServiceCatProvedores getSucursales()");
            getEjb();
            List<Object[]> lstObject = ejb.getProvedores();
            ArrayList<Provedor> lista_provedores = new ArrayList<Provedor>();
            for (Object[] obj : lstObject) {

                Provedor p = new Provedor();
                p.setIdProvedorPK(new BigDecimal(obj[0].toString()));
                p.setNombreProvedor(obj[1] == null ? "" : obj[1].toString());
                p.setApaternoProve(obj[2] == null ? "" : obj[2].toString());
                p.setAmaternoProve(obj[3] == null ? "" : obj[3].toString());
                p.setNickName(obj[4] == null ? "" : obj[4].toString());
                lista_provedores.add(p);
                // si se necesitan mas valores poner los demas por ahora solo 
                // necesito estos datos.
            }
            return lista_provedores;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatProvedores.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public List<Object[]> getProvedorlId(BigDecimal idProvedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Provedor getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Provedor> getProvedorByNombreCompleto(String nombreCompleto) {
        getEjb();

        ArrayList<Provedor> lstProvedor = new ArrayList<Provedor>();
        List<Object[]> lstObject = ejb.getProvedorByNombreCompleto(nombreCompleto);

        for (Object[] obj : lstObject) {

            Provedor dominio = new Provedor();
            dominio.setIdProvedorPK(new BigDecimal(obj[0].toString()));
            dominio.setNombreProvedor(obj[1].toString());
            dominio.setApaternoProve(obj[2] == null ? "" : obj[2].toString());
            dominio.setAmaternoProve(obj[3] == null ? "" : obj[3].toString());

            lstProvedor.add(dominio);

        }

        return lstProvedor;

    }
}
