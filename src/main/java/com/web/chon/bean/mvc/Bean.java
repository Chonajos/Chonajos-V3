package com.web.chon.bean.mvc;

import com.web.chon.dominio.ValueObject;

/**
 * Interfaz para bean de la aplicación.
 *
 * @author Juan
 *
 */
public interface Bean<T extends ValueObject> {

    /**
     * Reiniciar los valores de la vista
     */
    public void reset();

    /**
     * Realiza la inicializacion de la informaciòn necesario de la vista
     *
     * @return
     */
    public void init();

}
