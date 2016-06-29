/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Competidor;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCompetidor {
    public List<Object[]> getCometidores();
    public int getNextVal();
    public int insertCompetidor(Competidor c);
    public int updateCompetidor(Competidor c);
    public List<Object[]> getCometidoresById(BigDecimal idCompetidor);
}
