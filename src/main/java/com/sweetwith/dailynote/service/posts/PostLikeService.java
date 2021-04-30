package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.like.PostLike;
import com.sweetwith.dailynote.domain.like.PostLikeRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostLikeService {
    private PostLikeRepository postLikeRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    public void registerLike(Long postId) {
        Post post = new Post();
        post.setId(postId);

        PostLike postLike = new PostLike();
        postLike.setPost(post);

        postLikeRepository.save(postLike);
    }

    public void deletePostLike(Long postId){
        postLikeRepository.deleteById(postId);
    }

    public List<PostLike> getPostLikeByPostId(Long postId) {
        Post post = new Post();
        post.setId(postId);
        return postLikeRepository.findByPost(post);
    }

    public List<PostLike> getPostLikeByUserId(Long userId){
        // TODO
        return new ArrayList<>();
    }

    public void pushPostLike(Long postId, Long userId) {
        // If the user has already clicked Like.
        if(isAlreadyLike(postId,userId))
            deletePostLike(postId);
        else
            registerLike(postId);
    }

    public boolean isAlreadyLike(Long postId, Long userId){
        // TODO
        // PostId와 UserId로 Search 해서 있으면 true, 아니면 false
        return true;
    }

}
