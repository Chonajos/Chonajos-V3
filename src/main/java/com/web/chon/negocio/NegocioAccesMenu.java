
package com.web.chon.negocio;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioAccesMenu {

    public List<Object[]> getAccesosMenuPorIdRol(BigDecimal idRol);
    public int delete(BigDecimal idRol,BigDecimal idMenu);
    public int create(BigDecimal idRol,BigDecimal idMenu);
    public List<Object[]> exist(BigDecimal idRol,BigDecimal idMenu);


}
