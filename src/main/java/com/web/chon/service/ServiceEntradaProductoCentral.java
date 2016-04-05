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
 * @author freddy
 */
@Service
public class ServiceEntradaProductoCentral implements IfaceEntradaProductoCentral {

    NegocioEntradaProductoCentral ejb;

    @Override
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia) {

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

}
