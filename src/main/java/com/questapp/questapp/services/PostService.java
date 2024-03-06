package com.questapp.questapp.services;

import com.questapp.questapp.dtos.CreatePostDTO;
import com.questapp.questapp.dtos.UpdatePostDTO;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(CreatePostDTO newPost) {
        User user = userService.findUserById(newPost.getUserId());
        if(user == null){
            return null;
        }
        Post toSavePost = new Post(newPost);
        toSavePost.setUser(user);
        return postRepository.save(toSavePost);
    }

    public Post updatePost(Long postId, UpdatePostDTO newPost) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post foundPost = post.get();
            if((newPost.getText() != null)){
                foundPost.setText(newPost.getText());
            }
            if((newPost.getTitle() != null)){
                foundPost.setTitle(newPost.getTitle());
            }
            postRepository.save(foundPost);
            return foundPost;
        }
        else{
            return null;
        }
    }

    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> findPostsByUserId(Long userId) {
        User user = userService.findUserById(userId);
        if (user != null){
            return postRepository.findByUserId(userId);
        }
        return null;
    }

    public List<Post> getAllPost(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }
}
