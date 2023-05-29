package com.example.eCommerce.auth;

import com.example.eCommerce.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/update")
    public ResponseEntity update(
            @RequestBody User user,
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        return ResponseEntity.ok(service.updateUser(user));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(
            @RequestParam int id,
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        return ResponseEntity.ok(service.deleteUser(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity getAll( @NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/user/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email,
                                         @NonNull HttpServletRequest request,
                                         @NonNull HttpServletResponse response) {
        // Set the necessary headers for CORS
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        // Call your service method to retrieve the user by email
        User user = service.getUserByEmail(email);

        if (user != null) {
            // Return the user in the response body
            return ResponseEntity.ok(user);
        } else {
            // Return a 404 Not Found response if the user is not found
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
