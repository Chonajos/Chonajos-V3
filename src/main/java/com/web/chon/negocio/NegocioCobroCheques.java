/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.CobroCheques;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioCobroCheques {
    public int insertarDocumento(CobroCheques cc);
    public int nextVal();
}
