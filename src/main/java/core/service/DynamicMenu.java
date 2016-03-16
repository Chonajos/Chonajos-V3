package core.service;

/**
 *
 * @author Juan
 */
import com.web.chon.dominio.Menu;
import java.util.ArrayList;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

public class DynamicMenu {

    public static MenuModel getMenuModel(ArrayList<Menu> lstMenu) {
        MenuModel model = new DefaultMenuModel();

        for (Menu menu : lstMenu) {

            addMenu(model, menu);
        }

        model.generateUniqueIds();

        return model;
    }

    private static void addMenu(MenuModel model, Menu menu) {

        DefaultSubMenu subMenus = new DefaultSubMenu(menu.getDescripcion());
        DefaultMenuItem items;
        DefaultSubMenu subCMenus = null;

        for (Menu subMenu : menu.getSubMenu()) {

            if (subMenu.getTipo() == 1) {

                items = new DefaultMenuItem(subMenu.getDescripcion());
                items.setUrl(subMenu.getUrl());
                items.setAjax(true);

                //Se inicia con la constuccion del menu en 3 nivel
                DefaultSubMenu dSubBMenu = new DefaultSubMenu(subMenu.getDescripcion());
                DefaultMenuItem itemB;

                for (Menu subBMenu : subMenu.getSubMenu()) {

                    itemB = new DefaultMenuItem(subBMenu.getDescripcion());
                    itemB.setUrl(subBMenu.getUrl());
                    itemB.setAjax(true);

                    if (subBMenu.getTipo() == 1) {

                        //Se inicia con la constuccion del menu en 4 nivel
                        subCMenus = new DefaultSubMenu(subBMenu.getDescripcion());
                        DefaultMenuItem itemC;

                        for (Menu subCMenuDTO : subBMenu.getSubMenu()) {

                            itemC = new DefaultMenuItem(subCMenuDTO.getDescripcion());
                            itemC.setUrl(subCMenuDTO.getUrl());
                            itemC.setAjax(true);

                            subCMenus.addElement(itemC);

                        }

                    }

                    if (subCMenus != null) {
                        dSubBMenu.addElement(subCMenus);
                    } else {
                        dSubBMenu.addElement(itemB);
                    }

                }

                subMenus.addElement(dSubBMenu);

            } else {

                items = new DefaultMenuItem(subMenu.getDescripcion());
                items.setUrl(subMenu.getUrl());
                items.setAjax(true);
                subMenus.addElement(items);
            }

        }

        model.addElement(subMenus);

    }

}
