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

    public void execute(Prompt prompt) {
        prompt.pushPath(menuGroupTitle);
        for (int i = 0; i < menuList.size(); i++) {
            prompt.println((i + 1) + ". " + menuList.get(i).getMenuTitle());
        }
        try {
            int input = prompt.intInput("%s>", prompt.getFullPath());
            menuList.get(input).execute(prompt);
        } catch (IndexOutOfBoundsException e) {
            prompt.println("존재하지 않는 번호입니다.");
        } catch (Exception e) {
            prompt.println("잘못된 번호 형식입니다.");
        }
    }

}
