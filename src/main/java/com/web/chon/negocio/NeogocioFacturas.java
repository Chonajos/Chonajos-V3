/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;
import com.web.chon.dominio.FacturaPDFDomain;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NeogocioFacturas {
    public int insert(FacturaPDFDomain factura);
    public int delete(FacturaPDFDomain factura);
    public int update(BigDecimal  idFacturaFk,BigDecimal idStatusFk);
    public List<Object[]> getFacturaByIdPk(BigDecimal idFacturaPk);
    public List<Object[]> getFacturaByIdNumeroFac(BigDecimal idFacturaPk);
    public List<Object[]> getFacturasBy(BigDecimal idClienteFk,BigDecimal idSucursalFk,BigDecimal idFolioVentaFk,String fechaInicio,String fechaFin);
    public int getNextVal();
    public int getLastNumeroFactura();
    public int insertarDocumento(BigDecimal id, byte[] fichero) throws SQLException;
}
