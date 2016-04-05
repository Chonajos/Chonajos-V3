package com.web.chon.bean.mvc;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.chon.dominio.ValueObject;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.ViewState;

/**
 * Clase abstracta con la defincion para patron de comportamientos de altas,
 * bajas, cambios y consultas con paginacion
 *
 * @author Juan
 *
 */
public abstract class SimpleViewBean<T extends ValueObject> extends SearchController<T> {

    private final static Logger logger = LoggerFactory.getLogger(SimpleViewBean.class);
    protected ViewState state;

    @PostConstruct
    public void init() {
        // Asignar el modelo
        initModel();

        state = ViewState.INIT;

    }

    /**
     * Obtener el logger en ejecucion de un controller
     *
     * @return
     */
    protected abstract Logger getLogger();

    @Override
    public void reset() {

        data.reset();
    }

    /**
     * Manejo de excepciones en la vista
     *
     */
    protected void manageException(Exception ex) {

        Throwable cause;

        cause = ex.getCause();
        while (cause != null) {
            if (cause.getClass().getName()
                    .equals("javax.persistence.PersistenceException")) {
                JsfUtil.addErrorMessage("Se detectó un error al ejecutar la petición.",
                        cause.getLocalizedMessage() == null
                                ? cause.getMessage()
                                : cause.getLocalizedMessage());
                break;
            } else if (cause.getClass().getName()
                    .equals("org.eclipse.persistence.exceptions.OptimisticLockException")) {
                JsfUtil.addErrorMessage("Se detectó un error al ejecutar la petición.",
                        cause.getLocalizedMessage() == null
                                ? cause.getMessage()
                                : cause.getLocalizedMessage());
                break;
            } else if (cause.getClass().getName()
                    .equals("org.springframework.dao.DataIntegrityViolationException")) {
                JsfUtil.addErrorMessage("El Id. ya existe, favor de validar.",
                        cause.getLocalizedMessage() == null
                                ? cause.getMessage()
                                : cause.getLocalizedMessage());
                break;
            } else if (cause.getClass().getName()
                    .equals("javax.validation.ValidationException")) {
                JsfUtil.addErrorMessage("Se detectó un error al ejecutar la petición.",
                        cause.getLocalizedMessage() == null
                                ? cause.getMessage()
                                : cause.getLocalizedMessage());
                break;
            } else {
                cause = cause.getCause();
            }
        }

        if (cause == null) {
            cause = ex.getCause();

            JsfUtil.addErrorMessage("Se detectó un error al ejecutar la petición.",
                    ex.getMessage());
        }
        getLogger().error("Error", ex);
    }

    /**
     * Obtener el valor solicitado de propiedad del archivo de configuraciòn
     * message.properties
     *
     * @param string nombre de la propiedad del archivo message.prperties
     * @return
     */
    protected String getString(String string) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        String value = null;
        try {
            value = bundle.getString(string);
        } catch (MissingResourceException ex) {
            logger.error("No se encontro la propiedad " + string);
        }

        return value != null ? value : "??" + string + "??";
    }

    public ViewState getState() {
        return state;
    }

    public void setState(ViewState state) {
        this.state = state;
    }

    public void actionNew() {
        state = ViewState.NEW;
        reset();
    }

    public void actionSearching() {
        state = ViewState.SEARCH;
        reset();
    }

    public void actionSearch() {
        state = ViewState.INIT;
    }

    public void actionUpdate() {
        state = ViewState.UPDATE;
    }

    public void actionModify() {
        state = ViewState.MODIFY;
    }

    public void actionBack() {
        state = ViewState.INIT;
    }

}
