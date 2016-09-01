/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import java.math.BigDecimal;
import java.util.ArrayList;



/**
 *
 * @author JesusAlfredo
 */
public interface IfaceEntMerProPaq {
    public int insertPaquete(EntradaMercanciaProductoPaquete paquete);
    public int eliminarPaquete(BigDecimal id);
    public ArrayList<EntradaMercanciaProductoPaquete> getPaquetesByIdEmp(BigDecimal idEmpPk);
    public int getNextVal();
    public int updatePaquete(BigDecimal idEmPFk);
}
