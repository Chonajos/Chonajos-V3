/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Correos;
import java.util.ArrayList;

/**
 *
 * @author fredddy
 */
public interface IfaceCatCorreos 
{
    public int insertCorreo(Correos co);
    public int deleteCorreos(Correos co);
    public ArrayList<Correos> SearchCorreosbyidClientPk(int idClientepk);
    public int updateCorreos(Correos co);
}
