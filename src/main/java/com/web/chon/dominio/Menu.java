/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

/**
 *
 * @author Juan
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Menu implements Serializable, Comparable<Menu> {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String descripcion;
    private String url;
    private int tipo;
    private String nivel;
    private Set<Menu> subMenu = new HashSet<Menu>(0);
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Set<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(Set<Menu> subMenu) {
        this.subMenu = subMenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "id=" + id + ", descripcion=" + descripcion + ", url="
                + url + ", tipo=" + tipo + ", nivel=" + nivel + ", subMenu="
                + subMenu + "";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
        result = prime * result + tipo;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Menu other = (Menu) obj;
        if (descripcion == null) {
            if (other.descripcion != null) {
                return false;
            }
        } else if (!descripcion.equals(other.descripcion)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (nivel == null) {
            if (other.nivel != null) {
                return false;
            }
        } else if (!nivel.equals(other.nivel)) {
            return false;
        }
        if (tipo != other.tipo) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Menu o) {
        // TODO Auto-generated method stub
        return descripcion.compareTo(o.descripcion);
    }

}
