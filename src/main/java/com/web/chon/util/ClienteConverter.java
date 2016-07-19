package com.web.chon.util;

import com.web.chon.dominio.Cliente;
import com.web.chon.service.IfaceCatCliente;
import java.math.BigDecimal;
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
        BigDecimal idCliente = validarNumero(value);

        if (value != null && !value.equals("null") && value.trim().length() > 0) {

            try {

                Object object = ifaceCatCliente.getCreditoClienteByIdCliente(idCliente);

                return object;

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

            if (value instanceof Cliente) {

                return String.valueOf(((Cliente) value).getId_cliente());

            } else {

                return String.valueOf(value);

            }
        } else {

            return null;

        }
    }

    private BigDecimal validarNumero(String value) {
        try {

            BigDecimal monto = new BigDecimal(value);

            return monto;

        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

}
