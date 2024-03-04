package com.questapp.questapp.controller;

import com.questapp.questapp.entities.Comment;
import com.questapp.questapp.entities.Post;
import com.questapp.questapp.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> getComment(@RequestParam(required = false) Optional<Long> commentId){
        if(commentId.isPresent()){
            Optional<Comment> comment = Optional.ofNullable(commentService.findCommentById(commentId.get()));
            if(comment.isPresent()) return new ResponseEntity<Optional<Comment>>(comment, HttpStatus.OK);
            else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            List<Comment> comments = commentService.findAll();
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
        }
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment newComment){
        return commentService.createComment(newComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "commentId") Long commentId, @RequestBody Comment newComment){
        Comment updatedComment = commentService.updateComment(commentId, newComment);
        if(updatedComment != null){
            return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
