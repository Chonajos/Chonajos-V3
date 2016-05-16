package com.web.chon.negocio;
import com.web.chon.dominio.Correos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatCorreos 
{
    public int insertCorreo(Correos co);
    public int deleteCorreos(Correos co);
    public List<Object[]> SearchCorreosbyidClientPk(BigDecimal idClientepk);
    public int updateCorreos(Correos co);
    
}
