package com.web.chon.repository;

import com.web.chon.entities.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Juan
 */
public interface UsuarioRepository extends CrudRepository<Usuario, BigDecimal> {

//    @Query(value = "SELECT * FROM USUARIO", nativeQuery = true)
//    public ArrayList<Usuario> findByEmail();
    public void anotar();

}

//public interface UsuarioRepository {
//
//
//    
//
//    
//}
