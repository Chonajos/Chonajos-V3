
package com.web.chon.util;

import com.web.chon.dominio.Subproducto;
import com.web.chon.service.ServiceSubProducto;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component

public class ProductoConverter implements Converter {
	
	@Autowired ServiceSubProducto serviceSubProducto;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
            
		if (value != null && !value.equals("null") && value.trim().length() > 0) {

			try {
                                
				Object object = serviceSubProducto.getSubProductoById(value);	        
                                
				return object;

			} catch (Exception e) {

				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error"+e.getStackTrace(), "******ss****"+e.getStackTrace()));
			}
		} else {

			return null;

		}
	
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		 
		if(value != null) {

			if(value instanceof Subproducto){

				return String.valueOf(((Subproducto) value).getNombreSubproducto());

			}else{

				return String.valueOf(value);

			}
		}
		else {

			return null;

		}
	}

}
