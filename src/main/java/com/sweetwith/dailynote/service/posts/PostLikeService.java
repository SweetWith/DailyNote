package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.like.PostLike;
import com.sweetwith.dailynote.domain.like.PostLikeRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostLikeService {
    private PostLikeRepository postLikeRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    // CREATE
    private void registerLike(Post post, User user) {
        PostLike postLike = new PostLike(post, user);
        postLikeRepository.save(postLike);
    }

    // READ
    public List<PostLike> getPostLikeByPost(Post post) {
        return postLikeRepository.findByPost(post);
    }

    // UPDATE
    public void pushPostLike(Post post, User user) {
        if (isAlreadyLike(post, user))
            deletePostLike(post, user);
        else
            registerLike(post, user);
    }

    private boolean isAlreadyLike(Post post, User user) {
        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, user);
        if(byPostAndUser.isPresent())
            return true;
        return false;
    }

    // DELETE
    private void deletePostLike(Post post, User user) {
        postLikeRepository.deleteByPostAndUser(post, user);
    }
}
