package org.example.menu;

import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;

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

    public void execute(Prompt prompt) {
        this.handler.action(prompt);
    }
}


