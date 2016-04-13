/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.negocio.NegocioEntradaMercancia;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercancia implements IfaceEntradaMercancia{
    NegocioEntradaMercancia ejb;

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
