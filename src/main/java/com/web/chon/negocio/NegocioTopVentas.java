/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioTopVentas {
    public List<Object[]> getMayoreo(String fechaInicio, String fechaFin);
    public List<Object[]> getMenudeo(String fechaInicio, String fechaFin);
    
}
