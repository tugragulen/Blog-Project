package com.questapp.questapp.controller;

import com.questapp.questapp.entities.User;
import com.questapp.questapp.services.UserService;
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
            if(user.isPresent()) return ResponseEntity.ok(user.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        User addedUser = userService.createUser(newUser);
        return ResponseEntity.ok(addedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId, @RequestBody User newUser){
        User updatedUser = userService.updateUser(userId, newUser);
        if (updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return (ResponseEntity<?>) ResponseEntity.ok();
     }

}
