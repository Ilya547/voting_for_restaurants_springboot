package ru.javaops.bootjava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.AuthUser;
import ru.javaops.bootjava.model.User;

@Slf4j
@Service
public class UserService {
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (!(authentication instanceof AuthUser)) {
            //
            throw new AuthenticationServiceException("User is not auth");
        }
        AuthUser authUser = (AuthUser) authentication;
        log.info("get {}", authUser);
        return authUser.getUser();
    }
}
