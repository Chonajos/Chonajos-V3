package core.service;

import java.io.Serializable;
import java.util.List;

/**
 * Service generico para catalogos.
 * 
 * @author Juan
 *
 * @param <T> dominio del servicio
 * @param <ID> Id del dominio del servicio
 */
public interface CrudService<T, ID extends Serializable> {

	/**
	 * Obtiene una entidad por su Id
	 * 
	 * @param id llave de la entidad no debe ser {@literal null}.
	 * @return dominio para el Id o {@literal null} si no fue encontrado
	 * @throws IllegalArgumentException si {@code id} es {@literal null}
	 */
	T getById (ID dominio);
	
	/**
	 * Guarda la entidad.
	 * 
	 * @param dominio
	 * @return dominio almacenado
	 */
	int create (T dominio);
	
	/**
	 * Actualiza la entidad.
	 * 
	 * @param dominio
	 * @return dominio actualizado
	 */
	int update (T dominio);
	
	/**
	 * Regresa todas las entidades.
	 * 
	 * @return todos los dominio
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
