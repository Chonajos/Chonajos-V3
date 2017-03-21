package com.web.chon.ejb;

import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioSubProducto;
//import java.io.IOException;
//import java.io.OutputStream;
import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.sql.DataSource;
//import oracle.jdbc.OracleDriver;
//import oracle.jdbc.OracleResultSet;
//import oracle.sql.BLOB;
//import org.hibernate.Session;
//import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
//import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 *
 * Ejb para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbSubProducto")
public class EjbSubProducto implements NegocioSubProducto {
    
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
    
    private final static Logger logger = LoggerFactory.getLogger(EjbSubProducto.class);
    
    @Override
    public List<Object[]> getSubProductos() {
        try {
            
            Query query = em.createNativeQuery("SELECT * FROM SUBPRODUCTO SUB INNER JOIN PRODUCTO PRO ON PRO.ID_PRODUCTO_PK = SUB.ID_PRODUCTO_FK ORDER BY ID_PRODUCTO_FK,ID_SUBPRODUCTO_PK ASC");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            
            return resultList;
            
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }
    
    @Override
    public int deleteSubProducto(String idSubProducto) {
        try {
            Query query = em.createNativeQuery("DELETE SUBPRODUCTO where ID_SUBPRODUCTO_PK = ?");
            query.setParameter(1, idSubProducto);
            
            return query.executeUpdate();
            
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }
        
    }
    
    @Override
    public int insertarSubProducto(Subproducto subProducto) {
        
//        com.web.chon.entities.Subproducto subpro = new com.web.chon.entities.Subproducto();
//        com.web.chon.entities.Producto producto = new com.web.chon.entities.Producto();
//        
//        producto.setIdProductoPk(subProducto.getIdProductoFk());
//        subpro.setIdSubproductoPk("00010069");
//        subpro.setNombreSubproducto(subProducto.getNombreSubproducto());
//        subpro.setDescripcionSubproducto(subProducto.getDescripcionSubproducto());
//        subpro.setIdProductoFk(producto);
//        subpro.setUrlImagenSubproducto(subProducto.getUrlImagenSubproducto());
//        em.persist(subpro);
//        return 0;
        
        try {
            Query query = em.createNativeQuery("INSERT INTO SUBPRODUCTO (ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO,DESCRIPCION_SUBPRODUCTO,ID_PRODUCTO_FK,URL_IMAGEN_SUBPRODUCTO) values(?,?,?,?,?)");
            query.setParameter(1, subProducto.getIdSubproductoPk());
            query.setParameter(2, subProducto.getNombreSubproducto());
            query.setParameter(3, subProducto.getDescripcionSubproducto());
            query.setParameter(4, subProducto.getIdProductoFk());
            query.setParameter(5, subProducto.getUrlImagenSubproducto());
            
            return query.executeUpdate();
            
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }
        
    }
    
    @Override
    public int updateSubProducto(Subproducto subProducto) {
        try {
            
            System.out.println("udate :" + subProducto.toString());
            Query query = em.createNativeQuery("UPDATE SUBPRODUCTO set NOMBRE_SUBPRODUCTO = ?,DESCRIPCION_SUBPRODUCTO =?,ID_PRODUCTO_FK = ?, URL_IMAGEN_SUBPRODUCTO = ? where ID_SUBPRODUCTO_PK = ?");
            query.setParameter(1, subProducto.getNombreSubproducto());
            query.setParameter(2, subProducto.getDescripcionSubproducto());
            query.setParameter(3, subProducto.getIdProductoFk());
            query.setParameter(4, subProducto.getUrlImagenSubproducto());
            query.setParameter(5, subProducto.getIdSubproductoPk());
            
            return query.executeUpdate();
            
        } catch (Exception ex) {
            
            logger.error("Error > " + ex.getMessage());
            return 0;
        }
    }
    
    @Override
    public int getLastIdProducto(String idCategoria) {
        Query query = em.createNativeQuery("SELECT MAX(SUBSTR(ID_SUBPRODUCTO_PK,5,8)+1 ) ID_SUBPRODUCTO_PK from SUBPRODUCTO where ID_PRODUCTO_FK = ?");
        query.setParameter(1, idCategoria);
        String lastId = query.getSingleResult().toString();
        return Integer.parseInt(lastId);
    }
    
    @Override
    public List<Object[]> getProductoById(String idProducto) {
        Query query = em.createNativeQuery("SELECT * FROM SUBPRODUCTO WHERE ID_SUBPRODUCTO_PK = ?");
        query.setParameter(1, idProducto);
        
        return query.getResultList();
    }
    
    @Override
    public List<Object[]> getSubProductoByNombre(String idProducto) {
        Query query = em.createNativeQuery("SELECT ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO FROM SUBPRODUCTO WHERE UPPER(NOMBRE_SUBPRODUCTO) LIKE '%" + idProducto + "%'");
        
        return query.getResultList();
    }
    
    @Override
    public List<Object[]> getSubProductosIdSucursal(BigDecimal idSucursal) {
        try {
            
            Query query = em.createNativeQuery("SELECT ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO,DESCRIPCION_SUBPRODUCTO,URL_IMAGEN_SUBPRODUCTO ,ID_PRODUCTO_FK,PRO.NOMBRE_PRODUCTO,MTP.PRECIO_VENTA,FICHERO FROM SUBPRODUCTO SUB LEFT JOIN PRODUCTO PRO ON PRO.ID_PRODUCTO_PK = SUB.ID_PRODUCTO_FK LEFT JOIN MANTENIMIENTO_PRECIO MTP ON MTP.ID_SUBPRODUCTO_FK = SUB.ID_SUBPRODUCTO_PK AND MTP.ID_SUCURSAL_FK =? WHERE SUB.FICHERO IS NOT NULL AND MTP.PRECIO_VENTA IS NOT NULL ORDER BY ID_PRODUCTO_FK,ID_SUBPRODUCTO_PK ASC");
            query.setParameter(1, idSucursal);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            
            return resultList;
            
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }
    
    public void insertarDocumento(String id, byte[] fichero) throws SQLException {
        
        Query querys = em.createNativeQuery("update SUBPRODUCTO SET FICHERO = ? WHERE ID_SUBPRODUCTO_PK = ?");
        querys.setParameter(1, fichero);
        querys.setParameter(2, id);
        querys.executeUpdate();
        
    }

//     
//     public void insertarDocumentoo(String id, byte[] fichero) throws SQLException { //con blob por si se necesita solo se comento
//
//        Context ctx = null;
//        Hashtable ht = new Hashtable();
//        ht.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
//        ht.put(Context.PROVIDER_URL,"t3://127.0.0.1:80");
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        try {
//            ctx = new InitialContext(ht);
//            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("DataChon");
//            conn = ds.getConnection();
//            
//
//        } catch (Exception e) {
//            // a failure occurred
//        } finally {
//
//        }
//
// 
//        String resultado = null;
//        String query = "update SUBPRODUCTO SET FICHERO = ? WHERE ID_SUBPRODUCTO_PK = ?";
//        PreparedStatement ps = conn.prepareStatement(query);
//
//        BLOB blob = BLOB.createTemporary(conn, true, BLOB.DURATION_SESSION);
//
//        OutputStream blob_os = blob.setBinaryStream(0);
//
//        try {
//            blob_os.write(fichero);
//            blob_os.flush();
//            System.out.println("blob : " + blob);
//            ps.setBytes(1, fichero);
//            ps.setString(2, id);
//            ps.executeUpdate();
//            ps.close();
//        } catch (IOException ex) {
//            Logger.getLogger(EjbSubProducto.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
