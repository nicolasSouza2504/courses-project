package br.com.courses.handler.requesthandler.security.jwt;

public class JWTContext {

    private static final ThreadLocal<String> jwtToken = new ThreadLocal<>();

    public static void setJwt(String token) {
        jwtToken.set(token);
    }

    public static String getJwt() {
        return jwtToken.get();
    }

    public static void clear() {
        jwtToken.remove();
    }

}
