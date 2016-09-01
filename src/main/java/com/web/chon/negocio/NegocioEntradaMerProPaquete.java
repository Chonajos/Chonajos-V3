/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioEntradaMerProPaquete {
    public int insertPaquete(EntradaMercanciaProductoPaquete paquete);
    public List<Object[]>  getPaquetesById(BigDecimal id);
    public int getNextVal();
    public int eliminarPaquete(BigDecimal id);
    public int updatePaquete(BigDecimal idEmpFk);
}
