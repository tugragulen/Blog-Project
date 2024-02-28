package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Like;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.repos.LikeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeRepository likeRepository;
    public LikeController(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @GetMapping
    public ResponseEntity<?> getLike(@RequestParam(required = false) Optional<Long> likeId){
        if(likeId.isPresent()){
            Optional<Like> like = likeRepository.findById(likeId.get());
            if(like.isPresent()) return ResponseEntity.ok(like.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Like> likes = likeRepository.findAll();
            return ResponseEntity.ok(likes);
        }
    }

    @PostMapping
    public Like createLike(@RequestBody Like newLike){
        return likeRepository.save(newLike);
    }


    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId){
        likeRepository.deleteById(likeId);
    }
}
