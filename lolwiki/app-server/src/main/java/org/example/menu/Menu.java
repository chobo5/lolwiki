package org.example.menu;

import org.example.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static List<MenuItem> menuItemList = new ArrayList<>();

    private String menuTitle;

    public Menu(String menuTitle, MenuItem... menuItems) {
        this.menuTitle = menuTitle;

        for (MenuItem item : menuItems) {
            menuItemList.add(item);
        }

    }

    public void execute(Prompt prompt) {
        prompt.println("[" + menuTitle + "]");
        for (int i = 0; i < menuItemList.size(); i++) {
            prompt.println((i + 1) + ". " + menuItemList.get(i).getMenuItemTitle());
        }
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
