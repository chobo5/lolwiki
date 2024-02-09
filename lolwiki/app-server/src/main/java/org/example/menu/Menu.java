package org.example.menu;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    private List<MenuItem> menuItemList = new ArrayList<>();

    private String menuTitle;

    public Menu(String menuTitle, MenuItem... menuItems) {
        this.menuTitle = menuTitle;

        for (MenuItem item : menuItems) {
            menuItemList.add(item);
        }

    }
}
