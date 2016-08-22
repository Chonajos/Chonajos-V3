/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;
import com.web.chon.dominio.AbonoDocumentos;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioAbonoDocumento {
    public int insert(AbonoDocumentos abonoDocumento);
    public int update(AbonoDocumentos abonoDocumentos);
    public int delete(BigDecimal idAbonoDocumento);
    public List<Object[]> getById(BigDecimal idAbonoDocumento) ;
    public List<Object[]> getAll();
    public int getNextVal();
    public List<Object[]> getChequesPendientes(String fechaInicio, String fechaFin,BigDecimal idSucursal,BigDecimal idClienteFk,BigDecimal filtro,BigDecimal filtroStatus);
    public BigDecimal getTotalAbonadoByIdDocumento(BigDecimal idDocumentoFk);
    
}
