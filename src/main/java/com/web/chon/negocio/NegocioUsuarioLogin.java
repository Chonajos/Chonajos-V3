/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.AnalisisMercado;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioUsuarioLogin {

    public List<Object[]> getMenuByUser(String perfilId);

    public List<Object[]> getUser(String idUser);

}
