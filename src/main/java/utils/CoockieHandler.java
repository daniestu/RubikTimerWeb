package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoockieHandler {
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static String findCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String coockieValue = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    coockieValue = cookie.getValue();
                }
            }
        }

        return coockieValue;
    }

    public static void addCoockie(HttpServletResponse response, String name, String value, String comment) {
        Cookie usernameCookie = new Cookie(name, value);
        usernameCookie.setMaxAge(365 * 24 * 60 * 60);
        usernameCookie.setComment(comment);
        usernameCookie.setPath("/");
        response.addCookie(usernameCookie);
    }
}

