package com.web.chon.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Clase para crear objetos Specification que son creados para crear consultas
 * con Criteria de JPA para solicitudes.
 *
 * @author Juan
 *
 */
public class PageableBuilder {

    /**
     * Regresa un nuevo objeto que especifica una pagina de resultado.
     *
     * @param first Numero de pagina.
     * @param pageSize Total de elementos por pagina
     * @return
     */
    public static Pageable constructPageSpecification(int first, int pageSize) {

        final Integer maxLimitAllowed = getCheckedLimit(pageSize);

        PageRequest page = new PageRequest(first / maxLimitAllowed, maxLimitAllowed);

        return page;
    }

    public static Pageable constructPageSpecification(int first, int pageSize,
            Sort sort) {

        final Integer maxLimitAllowed = getCheckedLimit(pageSize);

        PageRequest page = new PageRequest(first / maxLimitAllowed, maxLimitAllowed, sort);

        return page;
    }

    private static Integer getCheckedLimit(final Integer limit) {

        final Integer maxLimitAllowed = 200;
        // default al limite maximo
        Integer checkedLimit = maxLimitAllowed;

        if (limit != null && limit > 0) {
            checkedLimit = limit;
        } else if (limit != null) {
            // caso ilimitado: 0 o menos
            checkedLimit = null;
        }

        return checkedLimit;
    }

}
