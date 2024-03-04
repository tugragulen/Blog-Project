package com.questapp.questapp.services;

import com.questapp.questapp.entities.Comment;
import com.questapp.questapp.entities.User;
import com.questapp.questapp.repos.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment newComment) {
        return commentRepository.save(newComment);
    }
    public Comment updateComment(Long commentId, Comment newComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment foundComment = comment.get();
            foundComment.setText(newComment.getText());
            foundComment.setPost(newComment.getPost());
            commentRepository.save(foundComment);
            return foundComment;
        }
        else{
            return null;
        }
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
