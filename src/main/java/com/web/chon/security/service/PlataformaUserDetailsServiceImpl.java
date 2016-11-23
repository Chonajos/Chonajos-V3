package com.web.chon.security.service;

import com.web.chon.dominio.Menu;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.negocio.NegocioUsuarioLogin;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Juan
 */
@Service(value = "userDetailsService")
public class PlataformaUserDetailsServiceImpl implements PlataformaUserDetailsService {

    private NegocioUsuarioLogin usuarioRepository;

    private Map<Menu, Set<Menu>> menu;
    private List<Object[]> listMenu;
    private LinkedHashSet<Menu> subMenusDTO;
    private LinkedHashSet<Menu> subBMenusDTO;
    private LinkedHashSet<Menu> subCMenusDTO;
    private Set<String> allowedUrl;
    private LinkedHashSet<Menu> listMenuDTO = new LinkedHashSet<Menu>();
    int nivel = 0, contNivel = 0;
    private String menuNivel = "";
    private String subMenuNivel = "";
    private String subMenuNivelTres = "";

    public void getEjb() {
        if (usuarioRepository == null) {
            try {
                usuarioRepository = (NegocioUsuarioLogin) Utilidades.getEJBRemote("ejbUsuarioLogin", NegocioUsuarioLogin.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(PlataformaUserDetailsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        getEjb();
        
        final List<Object[]> usuario = usuarioRepository.getUser(username);
        if (usuario == null || usuario.isEmpty()) {
            
            throw new UsernameNotFoundException(username + ": no encontrado");
        }

        listMenuDTO.clear();

        return setValueToUsuarioDominio(usuario);
    }

    private UsuarioDominio setValueToUsuarioDominio(List<Object[]> lstObject) {
        UsuarioDominio dominio = new UsuarioDominio();
        
        for (Object[] obj : lstObject) {

            dominio.setUsuId(obj[0].toString());
            dominio.setUsuNombre(obj[1].toString());
            dominio.setUsuPaterno(obj[2].toString());
            dominio.setUsuMaterno(obj[3] == null ? "":obj[3].toString());
            dominio.setUsuPassword(obj[4].toString());
            dominio.setUsuFecAlta((Date) obj[5]);
            dominio.setPerId(obj[6] == null ? null: Integer.parseInt(obj[6].toString()));
            dominio.setSucId(obj[7] == null ? null: Integer.parseInt(obj[7].toString()));
            dominio.setPerDescripcion(obj[8].toString());
            dominio.setIdUsuario(new BigDecimal(obj[9].toString()));
            dominio.setNombreSucursal(obj[10] == null ? "":obj[10].toString());
            dominio.setTelefonoSucursal(obj[11] == null ? "":obj[11].toString());
            
            menu = new TreeMap<Menu, Set<Menu>>();
            allowedUrl = new HashSet<String>();
            
            ConstuctMenu(obj[6].toString());
            
            dominio.setMenu(listMenuDTO);
            dominio.setAllowedUrl(allowedUrl);
            
        }

        return dominio;
    }

    private void ConstuctMenu(String idPerfil) {
        
        getEjb();
        Menu menuDTO = null;
        Menu subMenuDTO = null;
        Menu subBMenuDTO = null;
        Menu subCMenuDTO = null;
        listMenu = new ArrayList<Object[]>();
        listMenu = this.usuarioRepository.getMenuByUser(idPerfil);
        for (Object[] menuLs : listMenu) {

            nivel = StringUtils.countOccurrencesOf(menuLs[3].toString(), ".");

            if (nivel == 0) {

                contNivel++;
                menuDTO = new Menu();
                menuDTO.setId(Long.parseLong(menuLs[0].toString()));
                menuDTO.setDescripcion(menuLs[1].toString());
                menuDTO.setTipo(Integer.parseInt(menuLs[0].toString()));
                menuDTO.setNivel(menuLs[3].toString());
                menuDTO.setUrl(menuLs[4].toString());
                menuDTO.setTarget("menu" + contNivel);

                menuNivel = menuLs[3].toString();
                subMenusDTO = new LinkedHashSet<Menu>();
                allowedUrl.add(menuDTO.getUrl());

            } else if (nivel == 1) {
                contNivel++;
                subMenuDTO = new Menu();
                subMenuDTO.setId(Long.parseLong(menuLs[0].toString()));
                subMenuDTO.setDescripcion(menuLs[1].toString());
                subMenuDTO.setTipo(Integer.parseInt(menuLs[2].toString()));
                subMenuDTO.setNivel(menuLs[3].toString());
                subMenuDTO.setUrl(menuLs[4].toString());
                subMenuDTO.setTarget("menu" + contNivel);

                subMenuNivel = menuLs[3].toString();
                allowedUrl.add(subMenuDTO.getUrl());

                if (subMenuDTO.getNivel().toString().contains(menuNivel)) {
                    subMenusDTO.add(subMenuDTO);
                }

                if (subMenuDTO.getTipo() == 1) {
                    subBMenusDTO = new LinkedHashSet<Menu>();
                }

            } else if (nivel == 2) {
                contNivel++;
                subBMenuDTO = new Menu();
                subBMenuDTO.setId(Long.parseLong(menuLs[0].toString()));
                subBMenuDTO.setDescripcion(menuLs[1].toString());
                subBMenuDTO.setTipo(Integer.parseInt(menuLs[2].toString()));
                subBMenuDTO.setNivel(menuLs[3].toString());
                subBMenuDTO.setUrl(menuLs[4].toString());
                subBMenuDTO.setTarget("menu" + contNivel);

                subMenuNivelTres = menuLs[3].toString();
                allowedUrl.add(subBMenuDTO.getUrl());

                if (subBMenuDTO.getTipo() == 1) {
                    subCMenusDTO = new LinkedHashSet<Menu>();
                }

                if (subBMenuDTO.getNivel().toString().contains(subMenuNivel)) {

                    subBMenusDTO.add(subBMenuDTO);
                }

            } else if (nivel == 3) {

                subCMenuDTO = new Menu();
                subCMenuDTO.setId(Long.parseLong(menuLs[0].toString()));
                subCMenuDTO.setDescripcion(menuLs[1].toString());
                subCMenuDTO.setTipo(Integer.parseInt(menuLs[0].toString()));
                subCMenuDTO.setNivel(menuLs[3].toString());
                subCMenuDTO.setUrl(menuLs[4].toString());

                allowedUrl.add(subCMenuDTO.getUrl());
                subCMenusDTO.add(subCMenuDTO);
            }

            if (menuDTO != null) {
                if (subMenuDTO != null && subMenuDTO.getNivel().toString().contains(menuNivel)) {
                    menuDTO.setSubMenu(subMenusDTO);

                    if (subBMenuDTO != null && subBMenuDTO.getNivel().toString().contains(subMenuNivel)) {
                        subMenuDTO.setSubMenu(subBMenusDTO);

                        if (subCMenuDTO != null && subCMenuDTO.getNivel().toString().contains(subMenuNivelTres)) {
                            subBMenuDTO.setSubMenu(subCMenusDTO);

                        }
                    }
                }

                listMenuDTO.add(menuDTO);
            }

        }
    }

}
