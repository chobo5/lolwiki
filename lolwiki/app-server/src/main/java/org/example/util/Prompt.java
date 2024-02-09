package org.example.util;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

//클라이언트에서 보낸 입력을 받는다.
//클라이언트에게 출력한다.
public class Prompt {

    private DataInputStream in;
    private DataOutputStream out;
    private StringWriter stringWriter = new StringWriter();// 문자열을 담는 역할, 즉 문자열을 내부 버퍼에 쓴다.
    private PrintWriter printWriter = new PrintWriter(stringWriter); //stringWriter에 담긴 문자열을 출력한다. 줄바꿈문자를 자동으로 변환해줌

    private Stack<String> breadcrumb = new Stack<>();
    public Prompt(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
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

    public void printPath() {
        for (String path : breadcrumb) {
            print(path);
        }
        print(">");
    }
}
