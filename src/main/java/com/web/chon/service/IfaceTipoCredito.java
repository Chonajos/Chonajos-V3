
package com.web.chon.service;

import com.web.chon.dominio.Municipios;
import com.web.chon.dominio.TipoCredito;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceTipoCredito {
    
    public ArrayList<TipoCredito> getAll();
    
    public TipoCredito getById(BigDecimal idTipoCredito);
    
    public int delete(BigDecimal idTipoCredito);
    
    public int update(TipoCredito tipoCredito);
    
    public int insert(TipoCredito tipoCredito);
    
    
}
