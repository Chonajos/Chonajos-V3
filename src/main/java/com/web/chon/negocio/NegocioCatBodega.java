/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Bodega;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioCatBodega 
{
    public List<Object[]> getBodegas();
    
    public List<Object[]> getBodegaById(int idBodega);

    public int deleteBodega(int idBodega);

    public int updateBodega(Bodega bo);

    public int insertBodega(Bodega bo);
    
    public int getNextVal ();
    
}
