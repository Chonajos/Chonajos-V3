package com.web.chon.util;

import com.web.chon.dominio.Provedor;
import com.web.chon.service.IfaceCatProvedores;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvedorConverter implements Converter {

    @Autowired
    IfaceCatProvedores ifaceCatProvedores;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.equals("null") && value.trim().length() > 0) {

            try {
                if((value.matches("[+-]?\\d*(\\.\\d+)?") && value.equals("") == false)){
                    Object object = ifaceCatProvedores.getById(new BigDecimal(value));
                    return object;
                }else{
                    return null;
                }
                
                

            } catch (Exception e) {

                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error >" + e.getStackTrace(), " - " + e.getStackTrace()));
            }
        } else {

            return null;

        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {

            if (value instanceof Provedor) {

                return String.valueOf(((Provedor) value).getIdProvedorPK());

            } else {

                return String.valueOf(value);

            }
        } else {

            return null;

        }
    }

}
