package com.web.chon.negocio;

import com.web.chon.dominio.TipoEmpaque;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * Interface para el ejb del catalogo de empaque
 * @author Juan
 */
@Remote
public interface NegocioEmpaque {
    
    /**
     * Obtiene una lista de objetos con todos los empaques que etsan en la bd
     * @return una lista de objetos
     */
    public List<Object[]> getEmpaques();
    /**
     * Elimina un empaque por medio de su id
     * @param idProducto
     * @return regresa un 1 si se realizo la eliminacion y 0 si fallo
     */
    public int deleteEmpaque(int idProducto);
    
    /**
     * Inserta un nuevo empaque
     * @param tipoEmpaque
     * @return regresa un 1 si se realizo la insercion y 0 si fallo
     */
    public int insertarEmpaque(TipoEmpaque tipoEmpaque);
    
    /**
     * Modifica un empaque
     * @param tipoEmpaque
     * @return regresa un 1 si se realizo la modificacion y 0 si fallo
     */
    public int updateEmpaque(TipoEmpaque tipoEmpaque);
    
    /**
     * Obtiene un empaque por medio de su id 
     * @param idEmpaque
     * @return objeto con los datos de empaque
     */
    public Object[] getEmpaqueByIdEmpaque(int idEmpaque);
   
    

}
