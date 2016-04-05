package com.web.chon.dominio;

import java.util.List;

public class Pagina<T> {

    private List<T> content;

    private Long totalElements;

    public Pagina(List<T> content, Long totalElements) {

        this.content = content;
        this.totalElements = totalElements;
    }

    /**
     * Regresa contenido como {@link List}.
     *
     * @return
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Regresa el total de elementos
     *
     * @return el total de elementos
     */
    public long getTotalElements() {
        return totalElements;
    }
}
