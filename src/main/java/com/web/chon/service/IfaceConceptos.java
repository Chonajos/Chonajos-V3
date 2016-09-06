/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ConceptosES;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceConceptos {
    public ArrayList<ConceptosES> getConceptosByTipoOperacion(BigDecimal idTipoOperacionFk);
}
