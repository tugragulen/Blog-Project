package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam(required = false) Optional<Long> id){
        if(id.isPresent()){
            Optional<Post> post = postRepository.findById(id.get());
            if(post.isPresent()) return ResponseEntity.ok(post.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Post> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        }
    }

    @PostMapping
    public Post createPost(@RequestBody Post newPost){
        return postRepository.save(newPost);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable(value = "postId") Long postId, @RequestBody Post newPost){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post foundPost = post.get();
            foundPost.setText(newPost.getText());
            foundPost.setTitle(newPost.getTitle());
            postRepository.save(foundPost);
            return foundPost;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postRepository.deleteById(postId);
    }
}
