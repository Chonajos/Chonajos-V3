package core.service;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;

import com.web.chon.dominio.Pagina;

public interface PaginacionService<T, ID extends Serializable> extends CrudService<T, ID> {
	
	/**
	 * Regresa una {@link Pagina} de entidades en base a las restricciones del objeto Pageable
	 * 
	 * @param pageable
	 * @return una pagina de entidades.
	 */
	Pagina<T> findAll(Pageable pageable);

}
