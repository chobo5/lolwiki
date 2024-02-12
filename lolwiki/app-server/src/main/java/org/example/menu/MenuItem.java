package org.example.menu;

import org.example.menu.handler.MenuItemHandler;

public class MenuItem {
    private MenuItemHandler handler;
    private String menuItemTitle;
    public MenuItem(MenuItemHandler handler, String menuItemTitle) {
        this.handler = handler;
        this.menuItemTitle = menuItemTitle;
    }

    public String getMenuItemTitle() {
        return menuItemTitle;
    }
}


