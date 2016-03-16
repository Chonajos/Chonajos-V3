/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

/**
 *
 * Interfaz para los los catalogos que hagan un CRUD
 * @author Juan de la Cruz
 *  
 */
public interface BeanSimple {
    
    public String delete();
    
    public String insert();
    
    public String update();
    
    public void searchById();
   
    
}
