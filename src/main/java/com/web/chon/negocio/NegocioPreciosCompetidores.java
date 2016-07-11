/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.PreciosCompetencia;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioPreciosCompetidores {
    public List<Object[]> getPreciosCompetidores(String fecha);
    public int getNextVal();
    public int insertPreciosCompetidores(PreciosCompetencia pc);
    public int updatePreciosCompetidores(PreciosCompetencia pc);
    public int deletePreciosCompetidores(PreciosCompetencia pc);
    public List<Object[]> getCometidoresById(BigDecimal idPreciosCompetidores);
    public List<Object[]> getPreciosCompetenciasByCompetidorProducto(PreciosCompetencia pc);
}
