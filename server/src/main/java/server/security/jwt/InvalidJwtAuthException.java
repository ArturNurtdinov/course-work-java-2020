package server.security.jwt;

public class InvalidJwtAuthException extends RuntimeException {
    public InvalidJwtAuthException(String expired_or_invalid_token) {
        super(expired_or_invalid_token);
    }
}
