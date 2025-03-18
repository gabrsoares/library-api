package com.library_api.controller;

import com.library_api.exception.InvalidBodyFormatException;
import com.library_api.exception.NoPermissionException;
import com.library_api.exception.RegisteredUserException;
import com.library_api.exception.UserNotFoundException;
import com.library_api.infra.security.TokenService;
import com.library_api.model.user.*;
import com.library_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;

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
    public ResponseEntity<LoginTokenDTO> login (@RequestBody @Valid AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginTokenDTO(token));
        } catch (Exception ex){
            throw new UserNotFoundException();
        }

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register (@RequestBody @Valid RegisterDTO data){
        if(repository.findByLogin(data.login()) != null) throw new RegisteredUserException();

        var encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        repository.save(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete (@RequestBody @Valid LoginValueDTO data){

        User user = (User) this.repository.findByLogin(data.login());

        if(user == null) throw new UserNotFoundException();
        if(user.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) throw new NoPermissionException();

        this.repository.delete(user);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();

    }
}
