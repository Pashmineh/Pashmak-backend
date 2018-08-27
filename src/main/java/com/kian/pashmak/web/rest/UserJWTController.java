package com.kian.pashmak.web.rest;

import com.kian.pashmak.domain.User;
import com.kian.pashmak.repository.UserRepository;
import com.kian.pashmak.security.SecurityUtils;
import com.kian.pashmak.security.jwt.JWTConfigurer;
import com.kian.pashmak.security.jwt.TokenProvider;
import com.kian.pashmak.service.UserService;
import com.kian.pashmak.service.dto.LoginDTO;
import com.kian.pashmak.web.rest.vm.LoginVM;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public UserJWTController(TokenProvider tokenProvider, UserRepository userRepository, UserService userService, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<LoginDTO> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        user.setPushToken(loginVM.getToken());
        user.setPlatform(loginVM.getPlatform());
        userRepository.save(user);
        LoginDTO loginDTO= new LoginDTO();
        loginDTO.setName(user.getFirstName());
        loginDTO.setAvatar(user.getAvatar());
        loginDTO.setLastName(user.getLastName());
        loginDTO.setToken(jwt);

        return new ResponseEntity<>(loginDTO, httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
