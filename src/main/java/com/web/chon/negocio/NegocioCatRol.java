
package com.web.chon.negocio;

import com.web.chon.dominio.Rol;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioCatRol {

    public List<Object[]> getAll();

    public List<Object[]> getById(int idBodega);

    public int delete(int idRol);

    public int update(Rol rol);

    public int insert(Rol rol);

    public int getNextVal();


}
