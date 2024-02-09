package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public void setDatabase() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://db-ld296-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123");

        } catch (SQLException e) {
            System.out.println("데이터 베이스 연결 실패");
            e.printStackTrace();
        }
    }
}