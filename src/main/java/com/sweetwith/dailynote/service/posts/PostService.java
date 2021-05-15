package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // CREATE
    public Long registerPost(String title, String content, User user) {
        Post post = new Post(title, content, user);
        return postRepository.save(post).getId();
    }

    // READ
    public PostResponseDto getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        return new PostResponseDto(post.get());
    }

    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAll();

        // Order by ModifiedData as DESC
        posts.sort(Comparator
                .comparing((Post post) -> post.getModifiedDate().toLocalDate())
                .thenComparing(Comparator
                        .comparing((Post post) -> post.getModifiedDate().toLocalTime())
                        .reversed()));
        return TransferPostsDto(posts);
    }


    public List<PostResponseDto> getPostListByUserId(User user) {
        List<Post> posts = postRepository.findByUser(user);
        return TransferPostsDto(posts);
    }

    // UPDATE
    public void modifyPost(Long id, String title, String content) {
        postRepository.updateTitleAndContent(id, title, content);
    }

    // DELETE
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // MAPPER
    public List<PostResponseDto> TransferPostsDto(List<Post> posts) {
        List<PostResponseDto> ret = new ArrayList<>();
        for (Post post : posts) {
            ret.add(new PostResponseDto(post));
        }
        return ret;
    }
}
