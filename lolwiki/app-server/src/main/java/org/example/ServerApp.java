package org.example;

import org.example.menu.Menu;
import org.example.menu.MenuGroup;
import org.example.menu.MenuItem;
import org.example.menu.handler.LeagueListHandler;
import org.example.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerApp {
    MenuGroup homeMenu;

    public static void main(String[] args) {
        System.out.println("Hello world!");
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
        homeMenu = new MenuGroup();
        homeMenu.addMenu(new Menu("리그", new MenuItem(new LeagueListHandler(), "리그 목록")));
        homeMenu.addMenu(new Menu("구단", new MenuItem(new LeagueListHandler(), "구단 목록")));
        homeMenu.addMenu(new Menu("선수", new MenuItem(new LeagueListHandler(), "선수 목록")));
    }

    public void connectClient() {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트 연결됨");
                run(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(Socket socket) {
        try(DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Prompt prompt = new Prompt(in, out);) {
            while (true) {
                homeMenu.execute(prompt);
            }
        } catch (Exception e) {

        }
    }
}