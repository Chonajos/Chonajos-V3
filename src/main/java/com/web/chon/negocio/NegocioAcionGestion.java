package com.web.chon.negocio;

import com.web.chon.dominio.AcionGestion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioAcionGestion {

    public int insert(AcionGestion acionGestion);

    public int update(AcionGestion acionGestion);

    public int delete(BigDecimal idAcionGestion);

    public List<Object[]> getAll();

    public List<Object[]> getByIdResultadoGestion(BigDecimal idResultadoGestion);

    public List<Object[]> getById(BigDecimal idAcionGestion);

    public int getNextVal();

}
