package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Comment;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.repos.CommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public ResponseEntity<?> getComment(@RequestParam(required = false) Optional<Long> commentId){
        if(commentId.isPresent()){
            Optional<Comment> comment = commentRepository.findById(commentId.get());
            if(comment.isPresent()) return ResponseEntity.ok(comment.get());
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Comment> comments = commentRepository.findAll();
            return ResponseEntity.ok(comments);
        }
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment newComment){
        return commentRepository.save(newComment);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable(value = "commentId") Long commentId, @RequestBody Comment newComment){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment foundComment = comment.get();
            foundComment.setText(newComment.getText());
            return foundComment;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
    }
}
