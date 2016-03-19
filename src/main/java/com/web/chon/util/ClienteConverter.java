package com.web.chon.util;

import com.web.chon.dominio.Cliente;
import com.web.chon.service.IfaceCatCliente;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter implements Converter {

    @Autowired
    IfaceCatCliente ifaceCatCliente;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.equals("null") && value.trim().length() > 0) {

            try {

                Object object = ifaceCatCliente.getClienteById(Integer.parseInt(value));

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

            if (value instanceof Cliente) {

                return String.valueOf(((Cliente) value).getId_cliente());

            } else {

                return String.valueOf(value);

            }
        } else {

            return null;

        }
    }

}
