package com.web.chon.bean;

import com.web.chon.core.service.DynamicMenu;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceUsuario;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("session")
public class BeanUsuario {

   private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(BeanUsuario.class);
	  
	@Autowired
	private PlataformaSecurityContext context;
	private String usuarioLogeado;
	private UsuarioDominio usuario;
	private MenuModel menuModel;
	private String usuarioId;
	
	@PostConstruct
	public void init() {
		usuario = context.getUsuarioAutenticado();
		usuarioLogeado = usuario.getUsuNombre();
		setUsuarioId(context.getUsuarioAutenticado().getUsuId());
		menuModel = DynamicMenu.getMenuModel(usuario);		
                System.out.println("init usuario");
	}

	public String getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(String usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public UsuarioDominio getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDominio usuario) {
		this.usuario = usuario;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
}
