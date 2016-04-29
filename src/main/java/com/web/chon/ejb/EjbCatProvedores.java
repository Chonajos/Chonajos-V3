/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Provedor;
import com.web.chon.negocio.NegocioCatProvedores;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbCatProvedores")
public class EjbCatProvedores implements NegocioCatProvedores {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getProvedores() {
        try {

            System.out.println("EJB_GET_Provedores");
            Query query = em.createNativeQuery("select ID_PROVEDOR_PK,NOMBRE_PROVEDOR,A_PATERNO_PROVE,A_MATERNO_PROVE,NICKNAME from provedores");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatProvedores.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

    }

    @Override
    public List<Object[]> getProvedoresDetalle(int first, int pageSize) {

        try {
            System.out.println("EJB_getProvedoresDetalle");

            Query query = em.createNativeQuery("select * from (select p.* ,en.ID_ENTIDAD_PK, en.NOMBRE_ENTIDAD,m.ID_MUNICIPIO_PK,m.NOMBRE_MUNICIPIO,\n"
                    + "			en1.ID_ENTIDAD_PK as idEntidadFiscal, en1.NOMBRE_ENTIDAD as nombreEntidadFiscal,\n"
                    + "      m.ID_MUNICIPIO_PK as idMunicipioFiscal,m1.NOMBRE_MUNICIPIO as nombreMunicipioFiscal,\n"
                    + "			cp.NOMBRE_COLONIA,cp.CODIGO_POSTAL,cp1.NOMBRE_COLONIA as nombreColoniaFiscal,\n"
                    + "      cp1.CODIGO_POSTAL as codigoPostalFiscal,st.DESCRIPCION_STATUS,\n"
                    + "row_number() over (order by p.id_provedor_pk ASC) rn\n"
                    + "from Provedores p\n"
                    + "INNER JOIN CODIGOS_POSTALES cp\n"
                    + "  on p.id_cp_prove_fk=cp.ID_PK\n"
                    + "INNER JOIN Municipios m\n"
                    + "  on cp.ID_MUNICIPIO_FK=m.id_municipio_pk\n"
                    + "INNER JOIN ENTIDAD en\n"
                    + "  on en.ID_ENTIDAD_PK=m.ID_ENTIDAD_FK\n"
                    + "INNER JOIN CODIGOS_POSTALES cp1\n"
                    + "  on p.id_cp_fiscal_prove_fk=cp1.ID_PK\n"
                    + "INNER JOIN Municipios m1\n"
                    + "  on cp.ID_MUNICIPIO_FK=m1.id_municipio_pk\n"
                    + "INNER JOIN ENTIDAD en1\n"
                    + "  on en1.ID_ENTIDAD_PK=m1.ID_ENTIDAD_FK\n"
                    + "INNER JOIN STATUS st\n"
                    + "  on p.id_status_fk = st.id_pk)\n"
                    + "where rn between ? and ? + ?");
            query.setParameter(1, first);
            query.setParameter(2, first);
            query.setParameter(3, pageSize);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getProvedorId(BigDecimal idProvedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getSizeListProvedores() {
        try {
            Query query = em.createNativeQuery("select count(id_provedor_pk) from provedores");
            BigDecimal value = (BigDecimal) query.getSingleResult();
            System.out.printf(value.toString());
            return value.longValue();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0l;
        }

    }

    @Override
    public int deleteProvedor(BigDecimal idProvedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateProvedor(Provedor prove) {

        System.out.println("EJB_UPDATE_PROVEDOR");
        try {
            System.out.println("freddy : " + prove);
            Query query = em.createNativeQuery("UPDATE  PROVEDORES SET NOMBRE_PROVEDOR = ? ,A_PATERNO_PROVE = ? ,A_MATERNO_PROVE = ? ,EMPRESA = ?,CALLE_PROVE = ?,SEXO_PROVE = ?,FECHA_NACIMIENTO_PROVE = ?"
                    + ",TELEFONO_MOVIL_PROVE = ?,TELEFONO_FIJO_PROVE = ? ,EXTENSION_PROVE = ? ,NUM_INT_PROVE = ?,NUM_EXT_PROVE = ?,CLAVECELULAR_PROVE = ?,"
                    + "LADACELULAR_PROVE = ?,ID_CP_PROVE_FK = ?,CALLEFISCAL_PROVE = ?,NUMINTFIS_PROVE = ? ,NUMEXTFIS_PROVE = ?,ID_CP_FISCAL_PROVE_FK = ?,"
                    + "NEXTEL_PROVE = ?,RAZON_PROVE = ?,RFC_PROVE = ?,LADAOFICINA_PROVE = ?,CLAVEOFICINA_PROVE = ?,NEXTELCLAVE_PROVE = ?,ID_STATUS_FK=?,NICKNAME=?,CORREO=? where ID_PROVEDOR_PK = ?");

            query.setParameter(1, prove.getNombreProvedor());
            query.setParameter(2, prove.getAmaternoProve());
            query.setParameter(3, prove.getAmaternoProve());
            query.setParameter(4, prove.getEmpresa());
            query.setParameter(5, prove.getCalleProve());
            query.setParameter(6, prove.getSexoProve());
            query.setParameter(7, prove.getFechaNacimientoProve());
            query.setParameter(8, prove.getTelefonoMovilProve());
            query.setParameter(9, prove.getTelefonoFijoMovil());
            query.setParameter(10, prove.getExtensionProve());
            query.setParameter(11, prove.getNumIntProve());
            query.setParameter(12, prove.getNumExtProve());
            query.setParameter(13, prove.getClaveCelularProve());
            query.setParameter(14, prove.getLadaCelularProve());
            query.setParameter(15, prove.getIdCPProveFK() == null ? "100000" : prove.getIdCPProveFK());
            query.setParameter(16, prove.getCalleFiscalProve());
            query.setParameter(17, prove.getNumIntFiscalProve());
            query.setParameter(18, prove.getNumExtFiscalProve());
            query.setParameter(19, prove.getIdCPFiscalProveFK() == null ? "100000" : prove.getIdCPFiscalProveFK());
            query.setParameter(20, prove.getNextelProve());
            query.setParameter(21, prove.getRazonProve());
            query.setParameter(22, prove.getRfcProve());
            query.setParameter(23, prove.getLadaOficinaProve());
            query.setParameter(24, prove.getClaveOficinaProve());
            query.setParameter(25, prove.getNextelClaveProve());
            query.setParameter(26, prove.getIdStatusFK());
            query.setParameter(27, prove.getNickName());
            query.setParameter(28, prove.getCorreo());
            query.setParameter(29, prove.getIdProvedorPK());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int insertProvedor(Provedor prove) {
        System.out.println("EJB_INSERTA_PROVEDOR");
        try {

            Query querySel = em.createNativeQuery("SELECT * FROM PROVEDORES WHERE NICKNAME = '" + prove.getNickName() + "' ");

            List<Object[]> resultList = null;
            resultList = querySel.getResultList();

            if (resultList.isEmpty()) {

                System.out.println("freddy : " + prove);
                Query query = em.createNativeQuery("INSERT INTO PROVEDORES (ID_PROVEDOR_PK,NOMBRE_PROVEDOR,A_PATERNO_PROVE,A_MATERNO_PROVE,EMPRESA,CALLE_PROVE,SEXO_PROVE,FECHA_NACIMIENTO_PROVE\n"
                        + ",TELEFONO_MOVIL_PROVE,TELEFONO_FIJO_PROVE,EXTENSION_PROVE,NUM_INT_PROVE,NUM_EXT_PROVE,CLAVECELULAR_PROVE,\n"
                        + "LADACELULAR_PROVE,ID_CP_PROVE_FK,CALLEFISCAL_PROVE,NUMINTFIS_PROVE,NUMEXTFIS_PROVE,ID_CP_FISCAL_PROVE_FK,\n"
                        + "NEXTEL_PROVE,RAZON_PROVE,RFC_PROVE,LADAOFICINA_PROVE,CLAVEOFICINA_PROVE,NEXTELCLAVE_PROVE,ID_STATUS_FK,\n"
                        + "FECHA_ALTA,NICKNAME,CORREO)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,sysdate,?,?)");
                query.setParameter(1, prove.getIdProvedorPK());
                query.setParameter(2, prove.getNombreProvedor());
                query.setParameter(3, prove.getAmaternoProve());
                query.setParameter(4, prove.getAmaternoProve());
                query.setParameter(5, prove.getEmpresa());
                query.setParameter(6, prove.getCalleProve());
                query.setParameter(7, prove.getSexoProve());
                query.setParameter(8, prove.getFechaNacimientoProve());
                query.setParameter(9, prove.getTelefonoMovilProve());
                query.setParameter(10, prove.getTelefonoFijoMovil());
                query.setParameter(11, prove.getExtensionProve());
                query.setParameter(12, prove.getNumIntProve());
                query.setParameter(13, prove.getNumExtProve());
                query.setParameter(14, prove.getClaveCelularProve());
                query.setParameter(15, prove.getLadaCelularProve());
                query.setParameter(16, prove.getIdCPProveFK() == null ? "100000" : prove.getIdCPProveFK());
                query.setParameter(17, prove.getCalleFiscalProve());
                query.setParameter(18, prove.getNumIntFiscalProve());
                query.setParameter(19, prove.getNumExtFiscalProve());
                query.setParameter(20, prove.getIdCPFiscalProveFK() == null ? "100000" : prove.getIdCPFiscalProveFK());
                query.setParameter(21, prove.getNextelProve());
                query.setParameter(22, prove.getRazonProve());
                query.setParameter(23, prove.getRfcProve());
                query.setParameter(24, prove.getLadaOficinaProve());
                query.setParameter(25, prove.getClaveOficinaProve());
                query.setParameter(26, prove.getNextelClaveProve());
                query.setParameter(27, prove.getIdStatusFK());
                query.setParameter(27, prove.getNickName());
                query.setParameter(28, prove.getCorreo());
                return query.executeUpdate();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_PROVEDOR.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getProvedorByNombreCompleto(String nombreCompleto) {

        Query query = em.createNativeQuery("SELECT * FROM PROVEDORES WHERE UPPER(NOMBRE_PROVEDOR ||' '|| A_PATERNO_PROVE ||' '|| A_MATERNO_PROVE )  LIKE UPPER('%" + nombreCompleto + "%')");

        return query.getResultList();

    }

    @Override
    public List<Object[]> getById(BigDecimal idProvedor) {
          Query query = em.createNativeQuery("SELECT * FROM PROVEDORES WHERE ID_PROVEDOR_PK = ?");
          query.setParameter(1, idProvedor);
        return query.getResultList();
    }

}
