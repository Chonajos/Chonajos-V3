/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.PagosBancarios;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioPagosBancarios {
    public int insertaPagoBancario(PagosBancarios pb);
    public int updatePagoBancario(PagosBancarios pb);
    public List<Object[]> getPagosPendientes();
    public List<Object[]>  getByIdTipoLlave(BigDecimal idTipo, BigDecimal idLLave);
    public int getNextVal();
}
