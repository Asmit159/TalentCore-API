package com.luv2code.springboot.cruddemo.security;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        userService.register(request.getUsername(), request.getPassword());
        return "User registered successfully";
    }
}
