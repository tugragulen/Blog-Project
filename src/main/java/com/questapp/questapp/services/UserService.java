package com.questapp.questapp.services;

import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User findUserById(Long userId){
        return userRepository.findById(userId).get();
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(Long userId, User newUser){
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

    public void deleteById(Long userId) {
         userRepository.deleteById(userId);
    }
}
