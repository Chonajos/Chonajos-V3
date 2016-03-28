/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;


import java.util.List;

/**
 *
 * @author freddy
 */
public interface NegocioCatCodigosPostales 
{
      public List<Object[]> getCodigosByCP(String cp);
    
}
