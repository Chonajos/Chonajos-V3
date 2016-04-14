
package com.web.chon.repository;

import com.web.chon.dominio.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Juan
 */
public interface UsuarioRepository extends JpaRepository<Usuario, BigDecimal>{

       @Query(value = "SELECT * FROM USUARIO", nativeQuery = true)
	ArrayList<Usuario> findByEmail();
    

    
}

//public interface UsuarioRepository {
//
//
//    
//
//    
//}
