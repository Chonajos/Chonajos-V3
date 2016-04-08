package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.Pagina;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.negocio.NegocioEntradaProductoCentral;
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
 * @author Juan
 */
@Service
public class ServiceEntradaProductoCentral implements IfaceEntradaProductoCentral {

    NegocioEntradaProductoCentral ejb;

    @Override
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia) 
    {

        getEjb();
        return ejb.saveEntradaProductoCentral(entradaMercancia);
    }

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaProductoCentral) Utilidades.getEJBRemote("ejbEntradaProductoCentral", NegocioEntradaProductoCentral.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Pagina<EntradaMercancia> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public EntradaMercancia getById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia create(EntradaMercancia dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia update(EntradaMercancia dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EntradaMercancia> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercancia> findAllDominio(EntradaMercancia filters, int first, int pageSize) {
        
        System.out.println("service");
//        
//        if (filters.getProFechaDeInicio() != null) {
//            
//    		fechaIni = TiempoUtil.fechaTextoDiaMesAÃ±o(filters.getProFechaDeInicio());
//    		
//        } else {
//            
//        	fechaIni = ConstantSIHO.fechaInicial;
//        }
//        
//    	
//        List<Object[]> lstProyectos = ejb.getFilters(filters, fechaIni, first, pageSize);
//        long size = Long.valueOf((proyectosRepositoryWrapper.getCountFilters(filters, fechaIni)).toString());
//        
//        for (Object[] obj : lstProyectos) {
//            
//        	CatalogoProyectosDto catalogoProyectosDto = new CatalogoProyectosDto();
//            
//        	catalogoProyectosDto.setProId(Long.valueOf(obj[0].toString().trim()));
//        	catalogoProyectosDto.setDescResponsables(obj[1].toString().trim());
//        	catalogoProyectosDto.setProEstatus(obj[2].toString().trim());
//        	catalogoProyectosDto.setProNombreCortoProy(obj[3].toString());
//        	catalogoProyectosDto.setProNombreProy(obj[4].toString());
//        	catalogoProyectosDto.setProNombreCuenta(obj[8].toString());
//        	catalogoProyectosDto.setProTipoDeVenta(obj[10].toString());
//        	catalogoProyectosDto.setProFechaInicio((Date) obj[5]);
//        	catalogoProyectosDto.setProFechaFin((Date) obj[6]);
//            lstCatalogoProyectosDto.add(catalogoProyectosDto);
//        }

        System.out.println("sertice entrada central");
        ArrayList<EntradaMercancia> lstEntrada = new ArrayList<EntradaMercancia>();
       EntradaMercancia e = new EntradaMercancia();
       e.setCantidadToneladas(new BigDecimal(154));
       e.setIdEntrada(new BigDecimal(1));
       EntradaMercancia a = new EntradaMercancia();
        a.setIdEntrada(new BigDecimal(2));
       a.setCantidadToneladas(new BigDecimal(45));
       a.setIdEntrada(new BigDecimal(452));
       
       
       lstEntrada.add(e);
       lstEntrada.add(a);
       
       for(EntradaMercancia entrada: lstEntrada){
           System.out.println("service entrada:"+ entrada.getIdEntrada());
       }
       return new Pagina<EntradaMercancia>(lstEntrada, 2l);
        
    }

}
