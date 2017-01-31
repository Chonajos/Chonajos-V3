/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.ConceptosES;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioConceptos {
    public List<Object[]> getConceptosByTipoOperacion(BigDecimal idTipoOperacionFk);
    public List<Object[]> getConceptos();
    public int insertConcepto(ConceptosES c);
    public int getNextVal();
    public int updateConcepto(ConceptosES c);
    public List<Object[]> getConceptosByIdCategoria(BigDecimal id);
    
}
