/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaSalida;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceEntradaSalida {
    public ArrayList<EntradaSalida> getMovimientosByIdCaja(BigDecimal idCaja, String fechaInicio,String fechaFin);
    public int insertaMovimiento(EntradaSalida es);
    public int getNextVal();
}
