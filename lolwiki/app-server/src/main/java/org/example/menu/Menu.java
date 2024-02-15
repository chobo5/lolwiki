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
        prompt.pushPath(menuTitle);
        prompt.println("[" + menuTitle + "]");
        for (int i = 0; i < menuItemList.size(); i++) {
            prompt.println((i + 1) + ". " + menuItemList.get(i).getMenuItemTitle());
        }
        try {
            int input = prompt.intInput("%s>", prompt.getFullPath());
//            menuItemList.get(input).execute(prompt);
        } catch (IndexOutOfBoundsException e) {
            prompt.println("존재하지 않는 번호입니다.");
        } catch (Exception e) {
            prompt.println("잘못된 번호 형식입니다.");
        }
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
