/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.ComprobantesDigitales;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NegocioComprobantes {
    public int getNextVal();
    public int insertaComprobante(ComprobantesDigitales cd);
    public int updateComprobante(ComprobantesDigitales cd);
    public int deleteComprobante(ComprobantesDigitales cd);
    public List<Object[]> getComprobanteByIdTipoLlave(BigDecimal idTipoFk, BigDecimal idLlave);
    
}
