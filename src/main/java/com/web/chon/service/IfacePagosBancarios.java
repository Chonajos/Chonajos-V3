/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.PagosBancarios;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfacePagosBancarios {
    public int insertaPagoBancario(PagosBancarios pb);
    public int updatePagoBancario(PagosBancarios pb);
    public ArrayList<PagosBancarios> getPagosPendientes(BigDecimal idSucursalFk);
    public PagosBancarios getByIdTipoLlave(BigDecimal idTipo, BigDecimal idLLave);
    public int getNextVal();
    
}
