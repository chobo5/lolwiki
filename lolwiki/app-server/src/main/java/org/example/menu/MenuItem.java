package org.example.menu;

import org.example.menu.handler.MenuItemHandler;

public abstract class MenuItem {
    private MenuItemHandler handler;
    String menuTitle;
    public MenuItem(MenuItemHandler handler, String menuTitle) {
        this.handler = handler;
        this.menuTitle = menuTitle;
    }
}
