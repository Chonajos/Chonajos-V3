package com.web.chon.model;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Sort;
import com.web.chon.dominio.Pagina;
import static com.web.chon.model.PageableBuilder.constructPageSpecification;
import core.service.PaginacionService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PaginacionLazyDataModel<T, ID extends Serializable> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1L;

    private PaginacionService<T, ID> service;

    public PaginacionLazyDataModel(PaginacionService<T, ID> service) {
        this.service = service;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        final Pagina<T> page;

        if (sortOrder == SortOrder.UNSORTED || (sortField == null || sortField.trim().equals(""))) {
            page = service.findAll(constructPageSpecification(first, pageSize));
        } else {
            Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
            page = service.findAll(constructPageSpecification(first, pageSize, sort));
        }

        this.setRowCount((int) page.getTotalElements());
        this.setPageSize(pageSize);

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

}
