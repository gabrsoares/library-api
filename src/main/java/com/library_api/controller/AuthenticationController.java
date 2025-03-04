package com.library_api.controller;

import com.library_api.exception.RegisteredUserException;
import com.library_api.exception.UserNotFoundException;
import com.library_api.infra.security.TokenService;
import com.library_api.model.user.AuthenticationDTO;
import com.library_api.model.user.LoginTokenDTO;
import com.library_api.model.user.RegisterDTO;
import com.library_api.model.user.User;
import com.library_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            System.out.println(auth);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginTokenDTO(token));
        } catch (Exception ex){
            throw new UserNotFoundException();
        }

    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO data){
        if(repository.findByLogin(data.login()) != null) throw new RegisteredUserException();
        var encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        repository.save(user);

        return ResponseEntity.ok().build();
    }
}
