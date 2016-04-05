/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.BajaClientes;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioBajaClientes 
{
    public int insertCliente(BajaClientes clie);
    public int deleteClienteBajas(int idCliente);
}
