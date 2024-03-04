package com.questapp.questapp.services;

import com.questapp.questapp.entities.Post;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).get();
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

    public Post updatePost(Long postId, Post newPost) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post foundPost = post.get();
            foundPost.setTitle(newPost.getTitle());
            foundPost.setText(newPost.getText());
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
}
