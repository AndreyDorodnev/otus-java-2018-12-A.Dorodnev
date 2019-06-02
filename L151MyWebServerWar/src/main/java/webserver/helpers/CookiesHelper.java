package webserver.helpers;

import database.model.Roles;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookiesHelper {

    private CookiesHelper(){

    }

    public static String getName(Cookie[] cookies){
        Cookie cookie = Arrays.stream(cookies).filter(x->x.getName().equals("name")).findFirst().get();
        if(cookie!=null){
            return cookie.getValue();
        }
        return null;
    }

    public static String getRole(Cookie[] cookies){
        Cookie cookie = Arrays.stream(cookies).filter(x->x.getName().equals("role")).findFirst().get();
        if(cookie!=null){
            return Roles.valueOf(cookie.getValue()).toString();
        }
        return null;
    }

}