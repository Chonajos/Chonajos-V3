/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.TipoEmpaque;
import java.util.ArrayList;

/**
 * Interface para el servicio de empaque
 * @author Juan de la Cruz
 */
public interface IfaceEmpaque {
    
    /**
     * Obtiene una lista de TipoEmpaque que existen en la bd
     * @return ArrayList<TipoEmpaque>
     */
    public ArrayList<TipoEmpaque> getEmpaques();
    
    /**
     * Obtiene un empaque por medio de su id
     * @param idEmpaque
     * @return TipoEmpaque
     */
    public TipoEmpaque getEmpaqueById(int idEmpaque);
    
    /**
     * Elimina un empaque por medio de su id
     * @param idEmpaque
     * @return 0 si no se ejecuto la eliminacion y 1 si se realizo
     */
    public int deleteEmpaque(int idEmpaque);
    
    /**
     * Modifica un empaque
     * @param tipoEmpaque
     * @return 0 si no se ejecuto la modificacion y 1 si se realizo
     */
    public int updateEmpaque(TipoEmpaque tipoEmpaque);
    
    /**
     * Inserta un empaque a la bd
     * @param producto
     * @return 0 si no se ejecuto la insercion y 1 si se realizo
     */
    public int insertarEmpaque(TipoEmpaque producto );
}
