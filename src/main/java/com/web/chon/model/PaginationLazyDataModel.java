package com.web.chon.model;

import com.web.chon.dominio.Pagina;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.chon.core.service.PaginacionService;
import java.io.Serializable;

public class PaginationLazyDataModel<T, ID extends Serializable> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1L;
    private PaginacionService<T, ID> service;
    private T filtros;

    public PaginationLazyDataModel(PaginacionService<T, ID> service, T filters) {
        super();
        filtros = filters;
        this.service = service;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        Pagina<T> page = null;

        if (filtros != null) {

            if (sortOrder == SortOrder.UNSORTED || (sortField == null || sortField.trim().equals(""))) {

                page = service.findAllDominio(filtros, first, pageSize);
            } else {

                page = service.findAllDominio(filtros, first, pageSize);

            }
            this.setRowCount((int) page.getTotalElements());
            this.setPageSize(pageSize);

        }
        return page.getContent();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public T getRowData(String rowKey) {
        return service.getById(rowKey);
    }

    public T getFiltros() {
        return filtros;
    }

    public void setFiltros(T filtros) {
        this.filtros = filtros;
    }
}
