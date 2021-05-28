package com.sweetwith.dailynote.service.hashtag;

import com.sweetwith.dailynote.domain.hashtag.HashTag;
import com.sweetwith.dailynote.domain.hashtag.HashTagRepository;
import com.sweetwith.dailynote.domain.hashtag.PostHashTag;
import com.sweetwith.dailynote.domain.hashtag.PostHashTagRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.service.posts.PostService;
import com.sweetwith.dailynote.util.Constants;
import com.sweetwith.dailynote.web.dto.PostRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HashTagTest {

    @Autowired    HashTagRepository hashTagRepository;
    @Autowired    UserRepository userRepository;
    @Autowired    PostHashTagRepository postHashTagRepository;
    @Autowired    PostService postService;

    PostRequestDto postRequestDto;
    Post post;
    User user;
    HashTag hashTag;

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

        post = new Post(postRequestDto);
        hashTag = new HashTag("GOOD");
    }

    @Test
    public void test(){
        Long postId = postService.registerPost(postRequestDto); //Post 생성
        hashTagRepository.save(hashTag); // hastTag 생성
        PostHashTag postHashTag1 = new PostHashTag(post, hashTag); // 연결 table 생성
        PostHashTag postHashTag2 = new PostHashTag(post, hashTag);

        PostHashTag saved1 = postHashTagRepository.save(postHashTag1);//  저장
        PostHashTag saved2 = postHashTagRepository.save(postHashTag2);//  저장

        System.out.println("saved.toString() = " + saved1.toString());

        List<PostHashTag> postHashTagByPost = postHashTagRepository.findPostHashTagByPost(post);
        System.out.println("hashTagsByPost.toString() = " + postHashTagByPost.toString());
    }

}
