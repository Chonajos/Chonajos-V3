/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.topVentas;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceTopVentas {
    public ArrayList<topVentas> getMayoreo(Date fechaInicio, Date fechaFin);
    public ArrayList<topVentas> getMenudeo(Date fechaInicio, Date fechaFin);
    
}
