package com.example.eCommerce.auth;

import com.example.eCommerce.config.JwtService;
import com.example.eCommerce.user.Role;
import com.example.eCommerce.user.User;
import com.example.eCommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }
    public boolean updateUser(User user) {
        User updateUser = repository.findByEmail(user.getEmail()).orElseThrow(() ->
                new IllegalStateException("product with id " + user.getEmail() + " does not exist")
        );
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setFirstname(user.getFirstname());
        updateUser.setLastname(user.getLastname());
        repository.save(updateUser);
        return true;
    }

    public boolean deleteUser(int id) {
        repository.deleteById(id);
        return true;
    }

    public List<User> getAll() {
        return repository.findAll();
    }


    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();

    }
}
