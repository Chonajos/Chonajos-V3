package com.web.chon.dominio;

/**
 *
 * @author Juan
 */
public class FileDominio extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }
}
