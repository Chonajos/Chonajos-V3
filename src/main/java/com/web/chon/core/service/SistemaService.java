
package com.web.chon.core.service;

import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface SistemaService {

	public Date getFechaSistema();
}
