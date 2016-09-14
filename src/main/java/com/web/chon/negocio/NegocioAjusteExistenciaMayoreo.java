
package com.web.chon.negocio;

import com.web.chon.dominio.AjusteExistenciaMayoreo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioAjusteExistenciaMayoreo {

    public int insert(AjusteExistenciaMayoreo data);

    public List<Object[]> getAll();

    public List<Object[]> getAllByIdSucursal(BigDecimal idSucursal);

}
