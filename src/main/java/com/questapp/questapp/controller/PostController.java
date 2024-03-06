package com.questapp.questapp.controller;

import com.questapp.questapp.dtos.CreatePostDTO;
import com.questapp.questapp.dtos.UpdatePostDTO;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.services.PostService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getPost(@RequestParam(required = false) Optional<Long> userId){
        if(userId.isPresent()){
            Optional<List<Post>> post = Optional.ofNullable(postService.findPostsByUserId(userId.get()));
            if(post.isPresent()) return new ResponseEntity<>(post.get(), HttpStatus.OK);
            else {
                System.out.println("Kullanıcı yok");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            List<Post> posts = postService.findAllPosts();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId){
        Optional<Post> post = Optional.ofNullable(postService.findPostById(postId));
        if (post.isPresent()) return new ResponseEntity<>(post, HttpStatus.OK);
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDTO newPost){
        Post addedPost = postService.createPost(newPost);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable(value = "postId") Long postId, @RequestBody UpdatePostDTO newPost){
        Post updatedPost = postService.updatePost(postId, newPost);
        if(updatedPost != null){
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        else{
            System.out.println("Post bulunamadı.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        postService.deleteById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
