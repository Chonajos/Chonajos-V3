package com.web.chon.bean.mvc;

import com.web.chon.dominio.ValueObject;

/**
 * Clase abstracta con el comportamiento de altas, bajas y cambios.
 *
 * @author Juan
 *
 */
public abstract class CrudBean<T extends ValueObject> implements Bean<T> {

    /**
     * POJO con los datos asociados de la vista.
     */
    protected T data;

    /**
     * Realiza el guardado de la informacion de la vista
     *
     * @return
     */
    public abstract String save();

    /**
     * Realiza el borrado de informacion de la vista
     *
     * @return
     */
    public abstract String delete();

    public T getData() {
        return data;
    }

    public void setData(T pdata) {
        data = pdata;
    }

}
