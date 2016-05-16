/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.BajaClientes;
import java.math.BigDecimal;

/**
 *
 * @author freddy
 */
public interface IfaceBajaCliente 
{
    public int insertCliente(BajaClientes cliente);
    public int deleteCliente(BigDecimal idCliente);
}
