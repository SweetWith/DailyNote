package com.sweetwith.dailynote.service.postlike;

import com.sweetwith.dailynote.domain.like.PostLike;
import com.sweetwith.dailynote.domain.like.PostLikeRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.service.posts.PostLikeService;
import com.sweetwith.dailynote.service.posts.PostService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
    @Autowired PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    Post post;
    User user;

    @Before
    public void setup() {
        user = new User("id01", "pw01");
        userRepository.save(user);

        post = new Post("TEST_TITLE", "TEST_CONTENT", user);
        postRepository.save(post);
    }

    @Test
    @DisplayName("PUSH PUSH LIKE")
    public void pushPostLike(){
        postLikeService.pushPostLike(post,user);
        Assertions.assertThat(postLikeService.getPostLikeByPost(post).size()).isEqualTo(1);

        postLikeService.pushPostLike(post,user);
        Assertions.assertThat(postLikeService.getPostLikeByPost(post).size()).isEqualTo(0);

        postLikeService.pushPostLike(post,user);
        Assertions.assertThat(postLikeService.getPostLikeByPost(post).size()).isEqualTo(1);

        postLikeService.pushPostLike(post,user);
        Assertions.assertThat(postLikeService.getPostLikeByPost(post).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Bring the number of like one post")
    public void getPostLike(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";

        User user2 = new User("id02", "pw02");
        userRepository.save(user2);
        User user3 = new User("id03", "pw03");
        userRepository.save(user3);

        postLikeService.pushPostLike(post,user);
        postLikeService.pushPostLike(post,user2);
        postLikeService.pushPostLike(post,user3);

        List<PostLike> postLikes = postLikeService.getPostLikeByPost(post);
        Assertions.assertThat(postLikes.size()).isEqualTo(3);
    }
}
