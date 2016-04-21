/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.TransferenciaMercancia;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioTransferenciaMercancia 
{
    public int insertaTransferencia(TransferenciaMercancia tm);
    
    
    
}
