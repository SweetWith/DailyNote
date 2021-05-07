package com.sweetwith.dailynote.service.reply;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.reply.Reply;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.service.posts.PostService;
import com.sweetwith.dailynote.web.dto.ReplyRequestDto;
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
public class ReplyServiceTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReplyService replyService;

    User user;
    Post post;
    Long postId;
    ReplyRequestDto replyRequestDto1;
    ReplyRequestDto replyRequestDto2;

    @Before
    public void setup() {
        user = new User("id01", "pw01");
        userRepository.save(user);

        post = new Post("title01", "content01", user);
        postId = postRepository.save(post).getId();

        replyRequestDto1 = ReplyRequestDto.builder()
                .content("reply11111")
                .post(post)
                .user(user)
                .build();

        replyRequestDto2 = ReplyRequestDto.builder()
                .content("reply222222")
                .post(post)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Register Reply")
    public void registerReply() {
        replyService.registerReply(replyRequestDto1);

        Reply reply = replyService.getReplyByPost(post).get(0);
        Assertions.assertThat(reply.getPost()).isEqualTo(replyRequestDto1.getPost());
        Assertions.assertThat(reply.getUser()).isEqualTo(replyRequestDto1.getUser());
        Assertions.assertThat(reply.getContent()).isEqualTo(replyRequestDto1.getContent());
    }

    @Test
    @DisplayName("Register Reply on Reply")
    public void register2Reply() {
        Long replyId = replyService.registerReply(replyRequestDto1);
        replyService.registerReply(replyRequestDto2);

        ReplyRequestDto replyOnReply = ReplyRequestDto.builder()
                .content("replyOnReply")
                .post(post)
                .user(user)
                .replyParentId(replyId)
                .build();

        replyService.registerReply(replyOnReply);

        List<Reply> replyByPost = replyService.getReplyByPost(post);

        Assertions.assertThat(replyByPost.get(0).getContent()).isEqualTo(replyRequestDto1.getContent());
        Assertions.assertThat(replyByPost.get(1).getContent()).isEqualTo(replyOnReply.getContent());
        Assertions.assertThat(replyByPost.get(2).getContent()).isEqualTo(replyRequestDto2.getContent());

    }


    @Test
    @DisplayName("Update Reply")
    public void updateReply() {
        String changeContent = "reply22222";

        Long replyNo = replyService.registerReply(replyRequestDto1);

        replyService.updateContent(replyNo, changeContent , user);

        Reply reply = replyService.getReplyByPost(post).get(0);
        Assertions.assertThat(reply.getContent()).isEqualTo(changeContent);
    }

    @Test
    @DisplayName("Update reply invalid User Authority")
    public void update2Reply() {
        String changeContent = "reply22222";

        User user2 = new User("id22", "pw22");
        userRepository.save(user2);

        Long replyNo = replyService.registerReply(replyRequestDto1);

        int ret = replyService.updateContent(replyNo, changeContent , user2);
        Assertions.assertThat(ret).isEqualTo(0);

        Reply reply = replyService.getReplyByPost(post).get(0);
        Assertions.assertThat(reply.getContent()).isEqualTo(replyRequestDto1.getContent());
    }

    @Test
    @DisplayName("Delete reply")
    public void deleteReply(){
        Long replyNo = replyService.registerReply(replyRequestDto1);

        replyService.deleteReply(replyNo, user);

        Reply reply = replyService.getReplyByPost(post).get(0);
        Assertions.assertThat(reply.getIsDeleted()).isEqualTo(true);
    }

    @Test
    @DisplayName("Delete reply invalid User Authority")
    public void delete2Reply(){
        Long replyNo = replyService.registerReply(replyRequestDto1);

        User user2 = new User("id22", "pw22");
        userRepository.save(user2);

        int ret = replyService.deleteReply(replyNo, user2);
        Assertions.assertThat(ret).isEqualTo(0);

        Reply reply = replyService.getReplyByPost(post).get(0);
        Assertions.assertThat(reply.getIsDeleted()).isEqualTo(false);
    }


}
