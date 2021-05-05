package com.sweetwith.dailynote.service.postlike;

import com.sweetwith.dailynote.domain.like.PostLike;
import com.sweetwith.dailynote.domain.like.PostLikeRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
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
    @Autowired PostLikeService postLikeService;
    @Autowired PostService postService;

    @Test
    @DisplayName("PUSH PUSH LIKE")
    public void pushPostLike(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";

        // TODO - Get User Object
        User user = new User("aaaaaa","bbbbbb");
        user.setId(1L);

        // when
        Long postId = postService.registerPost(title,content,user);
        Post post = new Post(postService.getPostDetail(postId));
        postLikeService.pushPostLike(post,user);
        postLikeService.pushPostLike(post,user);
        postLikeService.pushPostLike(post,user);
        postLikeService.pushPostLike(post,user);
    }

    @Test
    @DisplayName("Bring the number of like one post")
    public void getPostLike(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";

        // TODO - Get User Object
        User user1 = new User("aaaaaa","bbbbbb");
        user1.setId(1L);
        User user2 = new User("aaaaaa","bbbbbb");
        user2.setId(2L);
        User user3 = new User("aaaaaa","bbbbbb");
        user3.setId(3L);

        // when
        Long postId = postService.registerPost(title,content,user1);
        Post post = new Post(postService.getPostDetail(postId));
        postLikeService.pushPostLike(post,user1);
        postLikeService.pushPostLike(post,user2);
        postLikeService.pushPostLike(post,user3);

        List<PostLike> postLikes = postLikeService.getPostLikeByPost(post);
        Assertions.assertThat(postLikes.size()).isEqualTo(3);
    }
}
