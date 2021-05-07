package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
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
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostService postService;

    Post post;
    User user;

    @Before
    public void setup() {
        user = new User("id01", "pw01");
        userRepository.save(user);

        post = new Post("TEST_TITLE", "TEST_CONTENT", user);
    }

    @Test
    @DisplayName("Register and get POST to make sure it's correct.")
    public void searchByPostID() {
        Long postId = postService.registerPost(post.getTitle(), post.getContent(), user);
        PostResponseDto ret = postService.getPostDetail(postId);

        Assertions.assertThat(ret.getId()).isEqualTo(postId);
        Assertions.assertThat(ret.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(ret.getContent()).isEqualTo(post.getContent());
        Assertions.assertThat(ret.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("Error occurs when searching with wrong Post ID.")
    public void searchInvalidPostID() {
        Long postId = postService.registerPost(post.getTitle(), post.getContent(), user);

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId + 1));
    }

    @Test
    @DisplayName("All posts are fetched, but the modified date is fetched in the order of the earliest.")
    public void getAllPostsOrderByModifyDateAsDESC() {

        // given
        postService.registerPost("title1", "content1", user);
        postService.registerPost("title2", "content2", user);

        // when
        List<PostResponseDto> list = postService.getPostList();

        // then
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("title2");
        Assertions.assertThat(list.get(1).getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("Modify the Post value.")
    public void modifyPostValue() {
        Long postId = postService.registerPost(post.getTitle(), post.getContent(), user);

        String title2 = "TEST2_TITLE";
        String content2 = "TEST2_CONTENT";
        postService.modifyPost(postId, title2, content2);

        PostResponseDto ret = postService.getPostDetail(postId);
        Assertions.assertThat(ret.getTitle()).isEqualTo(title2);
        Assertions.assertThat(ret.getContent()).isEqualTo(content2);
    }

    @Test
    @DisplayName("Error occurs when searching by Post ID after deleting a post.")
    public void deletePostID() {
        Long postId = postService.registerPost(post.getTitle(), post.getContent(), user);

        postService.deletePost(postId);

        NoSuchElementException e = org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId));
    }

}
