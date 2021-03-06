package org.newdevelopment.ca.authorization;

import org.newdevelopment.ca.authorization.util.AuthContext;
import org.newdevelopment.ca.authorization.util.ThreadLocalAuthContext;
import org.newdevelopment.ca.data.exception.AuthorizationException;
import org.newdevelopment.ca.data.model.CreateAcctRequest;
import org.newdevelopment.ca.data.model.User;
import org.newdevelopment.ca.data.model.UserAuth;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.newdevelopment.ca.data.util.AppConstants.*;

@Service
public class AuthHelper {

    private JWTAuthService jwtAuthService = JWTAuthService.getInstance();

    public Boolean isAuthorized() throws AuthorizationException {
        Boolean authorized = getAuthContext().getAuthorizationResult();

        if (authorized == null) {
            throw new AuthorizationException(BAD_TOKEN, HttpStatus.BAD_REQUEST);
        }

        return authorized;
    }

    public String getToken() throws AuthorizationException {
        String token = getAuthContext().getToken();

        if (token == null) {
            throw new AuthorizationException(BAD_TOKEN, HttpStatus.BAD_REQUEST);
        }

        return token;
    }

    public String getUsername() throws AuthorizationException {
        String username = getAuthContext().getUsername();

        //should be impossible to reach this point without having thrown an exception during token validation
        if (username == null) {
            throw new AuthorizationException(BAD_TOKEN, HttpStatus.BAD_REQUEST);
        }

        return username;
    }

    public String generateNewToken(UserAuth userAuth) {
        return jwtAuthService.generateToken(userAuth);
    }

    private AuthContext getAuthContext() throws AuthorizationException {
        if (ThreadLocalAuthContext.getAuthContext() == null) {
            throw new AuthorizationException(MISSING_INTERCEPTOR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ThreadLocalAuthContext.getAuthContext();
    }

}
