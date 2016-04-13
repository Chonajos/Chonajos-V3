package com.web.chon.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan
 */
@Component
@Scope("view")
public class BeanInit implements Serializable {

    private MenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Catalogos");

        DefaultMenuItem firstItem = new DefaultMenuItem("Categoria");
        firstItem.setUrl("/views/categoria.jr");
        firstSubmenu.addElement(firstItem);

        DefaultMenuItem secondItem = new DefaultMenuItem("Producto");
        secondItem.setUrl("/views/producto.jr");
        firstSubmenu.addElement(secondItem);

        DefaultMenuItem thirdItem = new DefaultMenuItem("Usuario");
        thirdItem.setUrl("/views/usuario.jr");
        firstSubmenu.addElement(thirdItem);

        DefaultMenuItem fourthItem = new DefaultMenuItem("Empaque");
        fourthItem.setUrl("/views/empaque.jr");
        firstSubmenu.addElement(fourthItem);
        
        DefaultMenuItem fifthItem = new DefaultMenuItem("Sucursales");
        fifthItem.setUrl("/views/sucursales.jr");
        firstSubmenu.addElement(fifthItem);

        model.addElement(firstSubmenu);

        DefaultSubMenu SecondtSubmenu = new DefaultSubMenu("Productos");

        firstItem = new DefaultMenuItem("Mantenimiento Precios");
        firstItem.setUrl("/views/mantenimientoPrecios.jr");

        SecondtSubmenu.addElement(firstItem);

        model.addElement(SecondtSubmenu);

        DefaultSubMenu thirdSubmenu = new DefaultSubMenu("Ventas");

        firstItem = new DefaultMenuItem("Realizar Pedido");
        firstItem.setUrl("/views/venta.jr");
        thirdSubmenu.addElement(firstItem);
        
        firstItem = new DefaultMenuItem("Pagar Pedido");
        firstItem.setUrl("/views/buscaVenta.jr");
        thirdSubmenu.addElement(firstItem);
        
        firstItem = new DefaultMenuItem("Relación de Operaciónes");
        firstItem.setUrl("/views/relacionOperaciones.jr");
        thirdSubmenu.addElement(firstItem);

        model.addElement(thirdSubmenu);

        DefaultSubMenu fourthSubmenu = new DefaultSubMenu("Clientes");
        firstItem = new DefaultMenuItem("Cliente");
        firstItem.setUrl("/views/clientes.jr");
        fourthSubmenu.addElement(firstItem);
        
        model.addElement(fourthSubmenu);
        
        DefaultSubMenu fifthSubmenu = new DefaultSubMenu("Provedores");
        firstItem = new DefaultMenuItem("Provedor");
        firstItem.setUrl("/views/provedor.jr");
        fifthSubmenu.addElement(firstItem);
        
        model.addElement(fifthSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

}
