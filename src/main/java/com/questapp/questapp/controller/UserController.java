package com.questapp.questapp.controller;

import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(required = false) Optional<Long> userId){
        if(userId.isPresent()){
            Optional<User> user = userRepository.findById(userId.get());
            if(user.isPresent()) return ResponseEntity.ok(user.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        }
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable(value = "userId") Long userId, @RequestBody User newUser){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setUserPassword(newUser.getUserPassword());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
     }

}
