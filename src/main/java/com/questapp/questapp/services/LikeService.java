package com.questapp.questapp.services;

import com.questapp.questapp.entities.Like;
import com.questapp.questapp.repos.LikeRepository;

import java.util.List;

public class LikeService {
    private LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like findLikeById(Long likeId) {
        return likeRepository.findById(likeId).get();
    }

    public List<Like> findAllLikes() {
        return likeRepository.findAll();
    }

    public Like createLike(Like newLike) {
        return likeRepository.save(newLike);
    }

    public void deleteLikeById(Long likeId) {
        deleteLikeById(likeId);
    }
}
