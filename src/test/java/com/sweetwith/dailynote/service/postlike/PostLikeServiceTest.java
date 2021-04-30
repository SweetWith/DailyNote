package com.sweetwith.dailynote.service.postlike;

import com.sweetwith.dailynote.domain.like.PostLike;
import com.sweetwith.dailynote.domain.like.PostLikeRepository;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.service.posts.PostLikeService;
import com.sweetwith.dailynote.service.posts.PostService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostLikeServiceTest {
    @Autowired PostLikeRepository postLikeRepository;
    @Autowired PostLikeService postLikeService;
    @Autowired PostRepository postRepository;
    @Autowired PostService postService;


    @Test
    @DisplayName("PostLike 등록후 조회")
    public void registerPostLikeAndGetPostLike(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";
        Long userId = 10L;

        // when
        Long postId = postService.registerPost(title,content,userId);

        postLikeService.registerLike(postId);
        postLikeService.registerLike(postId);
        postLikeService.registerLike(postId);

        List<PostLike> postLikes = postLikeService.getPostLikeByPostId(postId);
        Assertions.assertThat(postLikes.size()).isEqualTo(3);
    }

}
