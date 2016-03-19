
package com.web.chon.negocio;

import com.web.chon.dominio.Entidad;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatEntidad 
{
    public List<Object[]> getEntidades();

    public Object[] getEntidadById(int idEntidad);

    public int deleteEntidad(int idEntidad);

    public int updateEntidad(Entidad enti);

    public int insertEntidad(Entidad enti);
    
}
