package helpers;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookiesHelper {

    private CookiesHelper(){

    }

    public static String getName(Cookie[] cookies){
        return Arrays.stream(cookies).filter(x->x.getName().equals("role")).findFirst().map(Cookie::getValue).orElse(null);
    }

    public static String getRole(Cookie[] cookies){
        return Arrays.stream(cookies).filter(x->x.getName().equals("role")).findFirst().map(c -> Roles.valueOf(c.getValue()).toString()).orElse(null);
    }

}