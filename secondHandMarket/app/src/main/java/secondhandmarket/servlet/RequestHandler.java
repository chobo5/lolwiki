package secondhandmarket.servlet;

import java.lang.reflect.Method;

public class RequestHandler {
    Object controller;
    Method handler;

    public RequestHandler(Object controller, Method handler) {
        this.controller = controller;
        this.handler = handler;
    }

    public RequestHandler() {
    }
}
