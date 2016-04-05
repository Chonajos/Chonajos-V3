package com.web.chon.bean.mvc;

import com.web.chon.dominio.ValueObject;
import java.util.List;

import org.primefaces.model.LazyDataModel;

/**
 * Clase abstracta con el comportamiento de consultas con paginacion.
 *
 * @author Juan
 *
 */
public abstract class SearchController<T extends ValueObject> extends CrudBean<T> {

    protected List<T> rows = null;

    protected List<T> selectedRows = null;

    /**
     * Propiedad para la definción del modelo de paginacion
     */
    protected LazyDataModel<T> model;

    /**
     * Realiza la inicializacion de modelo para la paginacion
     *
     * @return
     */
    public abstract void initModel();

    /**
     * Realiza la busqueda de datos para llenar un grid paginado
     *
     * @return
     */
    public abstract String search();

    /**
     * Realiza la consulta de datos por los datos identificdores o llave
     *
     * @return
     */
    public abstract String searchDatabyId();

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> prows) {
        rows = prows;
    }

    public List<T> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<T> pselectedRows) {
        selectedRows = pselectedRows;
    }

    public LazyDataModel<T> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<T> model) {
        this.model = model;
    }

}
