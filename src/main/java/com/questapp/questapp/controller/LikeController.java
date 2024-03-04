package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Like;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<?> getLike(@RequestParam(required = false) Optional<Long> likeId){
        if(likeId.isPresent()){
            Optional<Like> like = Optional.ofNullable(likeService.findLikeById(likeId.get()));
            if(like.isPresent()) return ResponseEntity.ok(like.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Like> likes = likeService.findAllLikes();
            return ResponseEntity.ok(likes);
        }
    }

    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody Like newLike){
        Like addedLike = likeService.createLike(newLike);
        return new ResponseEntity<Like>(addedLike, HttpStatus.CREATED);
    }


    @DeleteMapping("/{likeId}")
    public ResponseEntity<?> ResponseEntity(@PathVariable Long likeId){
        likeService.deleteLikeById(likeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
