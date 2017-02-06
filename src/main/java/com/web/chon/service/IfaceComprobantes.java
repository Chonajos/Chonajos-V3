/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ComprobantesDigitales;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jramirez
 */
public interface IfaceComprobantes {
    public int getNextVal();
    public int insertaComprobante(ComprobantesDigitales cd);
    public int updateComprobante(ComprobantesDigitales cd);
    public int deleteComprobante(ComprobantesDigitales cd);
    public int insertarImagen(BigDecimal id, byte[] fichero) throws SQLException;
    public ArrayList<ComprobantesDigitales> getComprobanteByIdTipoLlave(BigDecimal idTipoFk, BigDecimal idLlave);
    
}
