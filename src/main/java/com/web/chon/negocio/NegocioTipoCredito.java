
package com.web.chon.negocio;

import com.web.chon.dominio.TipoCredito;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioTipoCredito {

    public int insert(TipoCredito tipoCredito);

    public int update(TipoCredito tipoCredito);

    public int delete(BigDecimal idTipoCredito);

    public List<Object[]> getAll();

    public List<Object[]> getById(BigDecimal idTipoCredito);

}
