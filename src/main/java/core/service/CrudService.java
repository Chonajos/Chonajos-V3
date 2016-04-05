package core.service;

import java.io.Serializable;
import java.util.List;

/**
 * Service generico para catalogos.
 * 
 * @author Juan
 *
 * @param <T> DTO del servicio
 * @param <ID> Id del DTO del servicio
 */
public interface CrudService<T, ID extends Serializable> {

	/**
	 * Obtiene una entidad por su Id
	 * 
	 * @param id llave de la entidad no debe ser {@literal null}.
	 * @return dto para el Id o {@literal null} si no fue encontrado
	 * @throws IllegalArgumentException si {@code id} es {@literal null}
	 */
	T getById (ID id);
	
	/**
	 * Guarda la entidad.
	 * 
	 * @param dto
	 * @return dto almacenado
	 */
	T create (T dto);
	
	/**
	 * Actualiza la entidad.
	 * 
	 * @param dto
	 * @return dto actualizado
	 */
	T update (T dto);
	
	/**
	 * Regresa todas las entidades.
	 * 
	 * @return todos los dto
	 */
	List<T> getAll();
	
	/**
	 * Elimina una entidad por Id
	 * 
	 * @param id no debe ser {@literal null}.
	 * @throws IllegalArgumentException en caso de que el {@code id} sea {@literal null}
	 */
	void delete(ID id);
}
