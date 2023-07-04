package team.mb.mbserver.global.jwt;

public interface JwtProperties {
    String SECRET = "ftjygfdhhtujgb";
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
