package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import org.assertj.core.api.Assertions;
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
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    @DisplayName("POST 등록하고 가져와서 정확한지 확인.")
    public void searchByPostID() {
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";
        Long userId = 10L;

        // when
        Long postId = postService.registerPost(title,content,userId);
        PostResponseDto ret = postService.getPostDetail(postId);

        // then
        Assertions.assertThat(ret.getId()).isEqualTo(postId);
        Assertions.assertThat(ret.getTitle()).isEqualTo(title);
        Assertions.assertThat(ret.getContent()).isEqualTo(content);
        Assertions.assertThat(ret.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("틀린 Post ID로 검색시 Error 발생.")
    public void searchInvalidPostID(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";
        Long userId = 10L;

        // when
        Long postId = postService.registerPost(title,content,userId);

        // then
        NoSuchElementException e = org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId+1));
    }

    @Test
    @DisplayName("모든 Post를 가져오되 수정한 날이 빠른 순으로 가져온다.")
    public void getAllPostsOrderByModifyDateAsDESC(){

        // given
        postService.registerPost("title1","content1",10L);
        postService.registerPost("title2","content2",5L);

        // when
        List<PostResponseDto> list = postService.getPostList();
        
        // then
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("title2");
        Assertions.assertThat(list.get(0).getUserId()).isEqualTo(5L);

        Assertions.assertThat(list.get(1).getTitle()).isEqualTo("title1");
        Assertions.assertThat(list.get(1).getUserId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Post 값을 수정해봄.")
    public void modifyPostValue(){
        //given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";
        Long userId = 10L;
        Long postId = postService.registerPost(title,content,userId);

        // when
        String title2 = "TEST2_TITLE";
        String content2 = "TEST2_CONTENT";
        postService.modifyPost(postId, title2, content2);

        // then
        PostResponseDto ret = postService.getPostDetail(postId);
        Assertions.assertThat(ret.getTitle()).isEqualTo(title2);
        Assertions.assertThat(ret.getContent()).isEqualTo(content2);
        Assertions.assertThat(ret.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Post 삭제후 Post ID로 검색시 Error 발생.")
    public void deletePostID(){
        // given
        String title = "TEST_TITLE";
        String content = "TEST_CONTENT";
        Long userId = 10L;

        // when
        Long postId = postService.registerPost(title,content,userId);
        postService.deletePost(postId);

        // then
        NoSuchElementException e = org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                () -> postService.getPostDetail(postId+1));
    }

}
