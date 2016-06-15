/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.AjusteExistenciaMenudeo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioAjusteExistenciaMenudeo {

    public int insert(AjusteExistenciaMenudeo data);

    public List<Object[]> getAll();

    public List<Object[]> getAllByIdSucursal(BigDecimal idSucursal);

}
