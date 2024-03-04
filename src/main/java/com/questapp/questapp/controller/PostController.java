package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam(required = false) Optional<Long> id){
        if(id.isPresent()){
            Optional<Post> post = Optional.ofNullable(postService.findPostById(id.get()));
            if(post.isPresent()) return ResponseEntity.ok(post.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Post> posts = postService.findAllPosts();
            return ResponseEntity.ok(posts);
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post newPost){
        Post addedPost = postService.createPost(newPost);
        return ResponseEntity.ok(addedPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable(value = "postId") Long postId, @RequestBody Post newPost){
        Post updatedPost = postService.updatePost(postId, newPost);
        if(updatedPost != null){
            return ResponseEntity.ok(updatedPost);
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId){
        postService.deleteById(postId);
        return (ResponseEntity) ResponseEntity.ok();
    }
}
