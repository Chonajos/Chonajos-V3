package com.web.chon.util;

/**
 * Maquina de estados Definicion de estados para usar en el renderizado de las
 * ventanas
 *
 * @author Juan
 */
public enum ViewState {
    INIT("init"),
    NEW("new"),
    SEARCH("search"),
    UPDATE("update"),
    MODIFY("modify");

    private String val;

    ViewState(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
