/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;




import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.negocio.NegocioPreciosCompetidores;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServicePreciosCompetencias implements IfacePreciosCompetencias
{
    NegocioPreciosCompetidores ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioPreciosCompetidores) Utilidades.getEJBRemote("ejbPreciosCompetidores", NegocioPreciosCompetidores.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServicePreciosCompetencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getNextVal() {
        getEjb();
      return ejb.getNextVal();
    }

    @Override
    public int insertPreciosCompetencias(PreciosCompetencia pc) {
      getEjb();
      return ejb.insertPreciosCompetidores(pc);
    }

    @Override
    public ArrayList<PreciosCompetencia> getPreciosCompetencias(String fecha) {
       getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<PreciosCompetencia> lista= new ArrayList<PreciosCompetencia>();
        lstObject = ejb.getPreciosCompetidores(fecha);
        for (Object[] obj : lstObject) {
            PreciosCompetencia dominio = new PreciosCompetencia();
            dominio.setIdPcPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdCompetidorFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setIdSubProductoPk(obj[2] == null ? null : obj[2].toString());
            dominio.setFechaRegistro(obj[3] == null ? null : (Date) obj[3]);
            dominio.setPrecioVenta(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setNombreProducto(obj[5] == null ? "" : obj[5].toString());
            dominio.setNombreCompetidor(obj[6] == null ? "" : obj[6].toString());
            lista.add(dominio);
        }
        return lista;
    }

    @Override
    public int deletePrecioCompetencia(PreciosCompetencia pc) {
       getEjb();
      return ejb.deletePreciosCompetidores(pc);
    }

    @Override
    public int updateCompetencia(PreciosCompetencia pc) {
       getEjb();
      return ejb.updatePreciosCompetidores(pc);
    }

    @Override
    public PreciosCompetencia getPreciosCompetenciasByCompetidorProducto(PreciosCompetencia pc) {
       List<Object[]> lstObject = null;
        PreciosCompetencia dominio = new PreciosCompetencia();
        getEjb();
        lstObject = ejb.getPreciosCompetenciasByCompetidorProducto(pc);

        getEjb();
        for (Object[] obj : lstObject) {

            dominio.setIdPcPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdCompetidorFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setIdSubProductoPk(obj[2] == null ? null : obj[2].toString());
            dominio.setFechaRegistro(obj[3] == null ? null : (Date) obj[3]);
            dominio.setPrecioVenta(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setNombreProducto(obj[5] == null ? "" : obj[5].toString());
            dominio.setNombreCompetidor(obj[6] == null ? "" : obj[6].toString());
            
        }
        return dominio;

    
    }
        
    
}
