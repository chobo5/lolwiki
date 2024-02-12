package org.example.menu;

import org.example.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class MenuGroup {


    private static List<Menu> menuList = new ArrayList<>();

    public MenuGroup() {

    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public void execute(Prompt prompt) {
        prompt.println("[홈 메뉴]");
        for (int i = 0; i < menuList.size(); i++) {
            prompt.println((i + 1) + ". " + menuList.get(i).getMenuTitle());
        }
    }

}
