package org.example.menu;

import org.example.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static List<MenuItem> menuItemList = new ArrayList<>();

    private String menuTitle;

    public Menu(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItemList.add(menuItem);
    }

    public void printMenu(Prompt prompt) {
        prompt.println("[" + menuTitle + "]");
        for (int i = 0; i < menuItemList.size(); i++) {
            prompt.println((i + 1) + ". " + menuItemList.get(i).getMenuItemTitle());
        }
    }

    public void execute(Prompt prompt) {
        prompt.pushPath(menuTitle);
        printMenu(prompt);
        try {
            while (true) {
                String input = prompt.input("%s>", prompt.getFullPath());
                if (input.equals("menu")) {
                    printMenu(prompt);
                } else if (input.equals("0")) {
                    return;
                } else {
                    menuItemList.get(Integer.parseInt(input) - 1).execute(prompt);
                }

            }
        } catch (IndexOutOfBoundsException e) {
            prompt.println("Menu - 존재하지 않는 번호입니다.");
        } catch (Exception e) {
            prompt.println("Menu - 잘못된 번호 형식입니다.");
        } finally {
            prompt.popPath();
        }
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
