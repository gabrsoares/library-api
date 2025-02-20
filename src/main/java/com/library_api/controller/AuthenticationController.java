package com.library_api.controller;

import com.library_api.model.user.AuthenticationDTO;
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

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        //o spring já faz esse processo de autenticação automaticamente através da classe Auth
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO data){
        if(repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        var encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        repository.save(user);

        return ResponseEntity.ok().build();
    }
}
