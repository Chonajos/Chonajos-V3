/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.AbonoDocumentos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceAbonoDocumentos {

    public int insert(AbonoDocumentos abonoDocumentos);

    public int update(AbonoDocumentos abonoDocumentos);

    public int delete(BigDecimal idAbonoDocumento);

    public List<AbonoDocumentos> getById(BigDecimal idAbonoDocumento);

    public List<AbonoDocumentos> getAll();

    public int getNextVal();
    
    public ArrayList<AbonoDocumentos> getCheques(Date fechaInicio, Date fechaFin, BigDecimal idSucursalFk, BigDecimal idClienteFk,BigDecimal filtro,BigDecimal filtroStatus);

    public BigDecimal getTotalAbonadoByIdDocumento(BigDecimal idDocumentoFk);

}
