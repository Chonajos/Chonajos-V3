/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Caja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceCaja {
    public int insertCaja(Caja c);
    public int updateCaja(Caja c);
    public Caja getCajaByIdUsuarioPk(BigDecimal idUsuarioPk);
    public ArrayList<Caja> getCajas();
    public Caja getCajaByIdPk(BigDecimal idCajaPk);
}
