package com.web.chon.dominio;

import java.io.Serializable;

/**
 * Interface que desacopla el uso de Dominos especificos y permite extender la
 * funcionalidad en la vista de primefaces y en parametros
 *
 * @author Juan
 * @param <V>
 */
public abstract class ValueObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Firma abstracta que obliga a la definicion del metodo toString en cada
     * (Domino)
     *
     * @return
     */
    public abstract String toString();

    /**
     * Firma abstracta que obliga a la definicion del metodo reset que limpia
     * los elementos de cada (Domino)
     */
    public abstract void reset();

}
