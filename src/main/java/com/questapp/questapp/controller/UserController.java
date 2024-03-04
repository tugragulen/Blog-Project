package com.questapp.questapp.controller;

import com.questapp.questapp.entities.User;
import com.questapp.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(required = false) Optional<Long> userId){
        if(userId.isPresent()){
            Optional<User> user = Optional.ofNullable(userService.findUserById(userId.get()));
            if(user.isPresent()) return new ResponseEntity<>(user.get(), HttpStatus.OK);
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<User> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        User addedUser = userService.createUser(newUser);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId, @RequestBody User newUser){
        User updatedUser = userService.updateUser(userId, newUser);
        if (updatedUser != null){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        else{
            System.out.println("Kullanıcı bulunamadı.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
     }

}
