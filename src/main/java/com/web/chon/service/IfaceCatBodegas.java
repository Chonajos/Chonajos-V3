/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Bodega;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcogante
 */
public interface IfaceCatBodegas {
     public ArrayList<Bodega> getBodegas();
    
    public List<Bodega[]> getBodegaById(int idBodega);

    public int deleteBodega(int idBodega);

    public int updateBodega(Bodega bo);

    public int insertBodega(Bodega bo);
    
    public int getNextVal ();
}
