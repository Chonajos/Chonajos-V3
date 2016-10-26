/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Provedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioCatProvedores 
{
    
public List<Object[]> getProvedores();
    
    public List<Object[]> getProvedoresDetalle(int first, int pageSize);

    public List<Object[]> getProvedorId(BigDecimal idProvedor);
    
    public Long getSizeListProvedores();

    public int deleteProvedor(BigDecimal idProvedor);

    public int updateProvedor(Provedor prove);

    public int insertProvedor(Provedor prove);
    
    public int getNextVal ();
    
    public List<Object[]> getProvedorByNombreCompleto(String nombreCompleto);
    
    public List<Object[]> getById(BigDecimal idProvedor);
    
    public List<Object[]> getProvedoresByIdSucursal(BigDecimal idSucursal);
    
}
