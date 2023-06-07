package com.myproject.MyProject1.rest;

import com.myproject.MyProject1.configuration.JwtToken;
import com.myproject.MyProject1.dto.RegisterUser;
import com.myproject.MyProject1.dto.RequestToken;
import com.myproject.MyProject1.dto.ResponseToken;
import com.myproject.MyProject1.dto.UserGrid;
import com.myproject.MyProject1.entity.User;
import com.myproject.MyProject1.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseToken>  login(@RequestBody RequestToken dto){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(token);

        }catch (Exception exception){

        }
        LocalDate now = LocalDate.now();
        System.out.println(now.getDayOfMonth());
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String role = userService.getAccountRole(dto.getUsername());
        String token = jwtToken.generateToken(dto.getUsername(), "secret-key-for-my-project", role,"MyProjectWebUI","Testing");
        ResponseToken response = new ResponseToken(dto.getUsername(), role, token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUser> register (@RequestBody RegisterUser dto){
        RegisterUser registerUser = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);

    }
    @GetMapping("/list-user")
    public ResponseEntity<Page> listUser(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search){
        Page response = userService.getListUser(page,search);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@Valid @RequestBody RegisterUser dto){
        RegisterUser response = userService.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
