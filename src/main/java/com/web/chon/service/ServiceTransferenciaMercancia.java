/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.TransferenciaMercancia;
import com.web.chon.negocio.NegocioExistenciaProducto;
import com.web.chon.negocio.NegocioTransferenciaMercancia;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceTransferenciaMercancia implements IfaceTransferenciaMercancia{
    NegocioTransferenciaMercancia ejb;
        
        
        public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTransferenciaMercancia) Utilidades.getEJBRemote("ejbTransferenciaMercancia", NegocioTransferenciaMercancia.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(NegocioTransferenciaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public int insertTransferenciaMercancia(TransferenciaMercancia tm) {
        getEjb();
        return ejb.insertaTransferencia(tm);
    }
    
    
}
