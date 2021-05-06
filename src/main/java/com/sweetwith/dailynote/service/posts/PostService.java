package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
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
    private PostLikeService postLikeService;

    @Autowired
    public PostService(PostRepository postRepository, PostLikeService postLikeService) {
        this.postRepository = postRepository;
        this.postLikeService = postLikeService;
    }

    public Long registerPost(String title, String content, Long userId) {
        Post post = new Post(title, content, userId);
        return postRepository.save(post).getId();
    }

    public PostResponseDto getPostDetail(Long id) {
        Optional<Post> post = postRepository.findById(id);

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

    public void pushPostLike(Long postId, Long userId){
        postLikeService.pushPostLike(postId,userId);
    }

    public List<PostResponseDto> getPostListByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return TransferPostsDto(posts);
    }

    public void modifyPost(Long id, String title, String content) {
        postRepository.updateTitleAndContent(id, title, content);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // NEED MAPPER?
    public List<PostResponseDto> TransferPostsDto(List<Post> posts) {
        List<PostResponseDto> ret = new ArrayList<>();
        for (Post post : posts) {
            ret.add(new PostResponseDto(post));
        }
        return ret;
    }
}
