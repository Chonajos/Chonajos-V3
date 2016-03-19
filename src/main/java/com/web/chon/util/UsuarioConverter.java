package com.web.chon.util;

import com.web.chon.dominio.Usuario;
import com.web.chon.service.IfaceCatUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter implements Converter {

    @Autowired
    IfaceCatUsuario ifaceCatUsuario;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.equals("null") && value.trim().length() > 0) {

            try {

                System.out.println("value converter: "+value);
                Object object = ifaceCatUsuario.getUsuariosById(Integer.parseInt(value));

                return object;

            } catch (Exception e) {

                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error" + e.getStackTrace(), "******ss****" + e.getStackTrace()));
            }
        } else {

            return null;

        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {

            if (value instanceof Usuario) {

                return String.valueOf(((Usuario) value).getIdUsuarioPk());

            } else {

                return String.valueOf(value);

            }
        } else {

            return null;

        }
    }

}
