package org.example.util;

import java.io.*;
import java.sql.Date;
import java.util.Scanner;
import java.util.Stack;

//클라이언트에서 보낸 입력을 받는다.
//클라이언트에게 출력한다.
public class Prompt implements AutoCloseable{

    private DataInputStream in;
    private DataOutputStream out;
    private StringWriter stringWriter = new StringWriter();// 문자열을 담는 역할, 즉 문자열을 내부 버퍼에 쓴다.
    private PrintWriter printWriter = new PrintWriter(stringWriter); //stringWriter에 담긴 문자열을 출력한다. 줄바꿈문자를 자동으로 변환해줌

    private Stack<String> breadcrumb = new Stack<>();
    public Prompt(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public String input(String str, Object... args) {
        try {
            printf(str, args);
            end();
            return in.readUTF();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int intInput(String str, Object ...args) {
        return Integer.parseInt(this.input(str, args));
    }

    public Date dateInput(String str, Object ...args) {
        return Date.valueOf(this.input(str, args));
    }

    public void print(String str) {
        printWriter.print(str);
    }

    public void println(String str) {
        printWriter.println(str);
    }

    public void printf(String str, Object... args) {
        printWriter.printf(str, args);
    }

    public void pushPath(String title) {
        breadcrumb.push(title);
    }

    public void popPath(String title) {
        breadcrumb.pop();
    }

    public String getFullPath() {
        return String.join("/", breadcrumb.toArray(new String[0]));
    }

    public void end() throws Exception {
        //PrintWriter를 통해 출력한 내용은 StringWriter에 쌓여있다.
        //StringWriter에 쌓여있는 문자열을 꺼낸다.
        StringBuffer buf = stringWriter.getBuffer();
        String content = buf.toString();

        //StringWriter에 쌓여있는 문자열을 꺼낸후 버퍼를 초기화시킨다.
        buf.setLength(0);
        // 버퍼에서 꺼낸 문자열을 클라이언트로 전송한다.
        out.writeUTF(content);
    }
    @Override
    public void close() throws Exception {
        printWriter.close();
        stringWriter.close();
    }
}
