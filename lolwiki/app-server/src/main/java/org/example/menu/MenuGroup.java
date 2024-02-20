package org.example.menu;

import org.example.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class MenuGroup {

    private String menuGroupTitle;
    private static List<Menu> menuList = new ArrayList<>();

    public MenuGroup(String menuGroupTitle) {
        this.menuGroupTitle = menuGroupTitle;
    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public void printMenu(Prompt prompt) {
        prompt.println("[" + menuGroupTitle + "]");
        for (int i = 0; i < menuList.size(); i++) {
            prompt.println((i + 1) + ". " + menuList.get(i).getMenuTitle());
        }
    }
    public void execute(Prompt prompt) {
        prompt.pushPath(menuGroupTitle);
        printMenu(prompt);
        try {
            String input = prompt.input("%s>", prompt.getFullPath());
            if (input.equals("menu")) {
                System.out.println(menuList.size());
                printMenu(prompt);
            } else if (input.equals("0")) {
                return;
            } else {
                menuList.get(Integer.parseInt(input) - 1).execute(prompt);
            }
        } catch (IndexOutOfBoundsException e) {
            prompt.println("MenuGroup - 존재하지 않는 번호입니다.");
        } catch (Exception e) {
            prompt.println("MenuGroup - 잘못된 번호 형식입니다.");
        } finally {
            prompt.popPath();
        }
    }

}
