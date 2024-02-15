package org.example;

import org.example.menu.Menu;
import org.example.menu.MenuGroup;
import org.example.menu.MenuItem;
import org.example.menu.handler.league.LeagueListHandler;
import org.example.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    MenuGroup mainMenu;

    ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new ServerApp().run();
    }

    public ServerApp() {
        prepareDatabase();
        prepareMenus();
    }

    public void prepareDatabase() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://db-ld296-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123");

        } catch (SQLException e) {
            System.out.println("데이터 베이스 연결 실패");
            e.printStackTrace();
        }
    }

    public void prepareMenus() {
        mainMenu = new MenuGroup("메인");
        mainMenu.addMenu(new Menu("리그", new MenuItem(new LeagueListHandler(), "리그 목록")));
        mainMenu.addMenu(new Menu("구단", new MenuItem(new LeagueListHandler(), "구단 목록")));
        mainMenu.addMenu(new Menu("선수", new MenuItem(new LeagueListHandler(), "선수 목록")));
        mainMenu.addMenu(new Menu("My", new MenuItem(new LeagueListHandler(), "리그 목록")));
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트 연결됨");
                executorService.execute(() -> processRequest(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processRequest(Socket socket) {
        try(DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Prompt prompt = new Prompt(in, out);) {
            while (true) {
                mainMenu.execute(prompt);
            }
        } catch (Exception e) {

        }
    }
}