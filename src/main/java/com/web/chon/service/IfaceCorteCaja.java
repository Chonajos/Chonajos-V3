/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CorteCaja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceCorteCaja {
    public  CorteCaja getCorteByFecha(String fecha);
    public int insertCorte(CorteCaja cc);
    public ArrayList<CorteCaja> getCortesByIdDestinoFk(BigDecimal idDestinoFK);
}
