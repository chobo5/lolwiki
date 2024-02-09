package org.example.menu;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuGroup {


    private List<Menu> menuList = new ArrayList<>();

    public MenuGroup(Menu... menus) {
        for (Menu menu : menus) {
            menuList.add(menu);
        }

    }

}
