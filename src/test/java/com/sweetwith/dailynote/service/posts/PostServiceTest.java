package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.util.Constants;
import com.sweetwith.dailynote.web.dto.PostRequestDto;
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

    PostRequestDto postRequestDto;
    Post post;
    User user;

    @Before
    public void setup() {
        user = new User("id01", "pw01");
        userRepository.save(user);

        postRequestDto = PostRequestDto.builder()
                .title("TEST_TITLE")
                .content("TEST_CONTENT")
                .user(user)
                .readAuthority(Constants.READ_AUTHORITY_ALL)
                .build();
    }

    @Test
    @DisplayName("Register and get POST to make sure it's correct.")
    public void searchByPostID() {
        Long postId = postService.registerPost(postRequestDto);
        PostResponseDto ret = postService.getPostDetail(postId);

        Assertions.assertThat(ret.getId()).isEqualTo(postId);
        Assertions.assertThat(ret.getTitle()).isEqualTo(postRequestDto.getTitle());
        Assertions.assertThat(ret.getContent()).isEqualTo(postRequestDto.getContent());
        Assertions.assertThat(ret.getUser()).isEqualTo(postRequestDto.getUser());
    }

    @Test
    @DisplayName("Error occurs when searching with wrong Post ID.")
    public void searchInvalidPostID() {
        Long postId = postService.registerPost(postRequestDto);

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId + 1));
    }

    @Test
    @DisplayName("All posts are fetched, but the modified date is fetched in the order of the earliest.")
    public void getAllPostsOrderByModifyDateAsDESC() {

        PostRequestDto postRequestDto2 = PostRequestDto.builder()
                .title("title2")
                .content("content2")
                .user(user)
                .readAuthority(Constants.READ_AUTHORITY_ALL)
                .build();

        // given
        postService.registerPost(postRequestDto);
        postService.registerPost(postRequestDto2);

        // when
        List<PostResponseDto> list = postService.getPostListAll(user);

        // then
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("TEST_TITLE");
        Assertions.assertThat(list.get(1).getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("Modify the Post value.")
    public void modifyPostValue() {
        Long postId = postService.registerPost(postRequestDto);

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
        Long postId = postService.registerPost(postRequestDto);

        postService.deletePost(postId);

        NoSuchElementException e = org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId));
    }

}
