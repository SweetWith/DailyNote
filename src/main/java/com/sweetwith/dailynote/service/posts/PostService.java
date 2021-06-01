package com.sweetwith.dailynote.service.posts;

import com.sweetwith.dailynote.domain.hashtag.HashTag;
import com.sweetwith.dailynote.domain.hashtag.PostHashTag;
import com.sweetwith.dailynote.domain.hashtag.PostHashTagRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.util.Constants;
import com.sweetwith.dailynote.web.dto.PostRequestDto;
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
    private PostHashTagRepository postHashTagRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostHashTagRepository postHashTagRepository) {
        this.postRepository = postRepository;
        this.postHashTagRepository = postHashTagRepository;
    }

    // CREATE
    public Long registerPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        return postRepository.save(post).getId();
    }

    // READ
    public PostResponseDto getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        return new PostResponseDto(post.get());
    }

    public List<HashTag> getHashTagsByPostId(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        List<PostHashTag> postHashTags = postHashTagRepository.findPostHashTagByPost(post.get());

        List<HashTag> ret = new ArrayList<>();
        for (PostHashTag postHashTag : postHashTags ) {
            ret.add(postHashTag.getHashTag());
        }
        return ret;
    }

    public List<PostResponseDto> getPostListAll(User user) {
        List<Post> posts = postRepository.findAll();

        // Order by CreatedDate as DESC
        posts.sort(Comparator
                .comparing((Post post) -> post.getCreatedDate().toLocalDate())
                .reversed()
                .thenComparing(Comparator
                        .comparing((Post post) -> post.getCreatedDate().toLocalTime())
                        .reversed()));

        // TODO -> popSecretPost(posts, user);
        // TODO -> NOT FRIEND POST DELETE

        return TransferPostsDto(posts);
    }

    public void popSecretPost(List<Post> posts, User user){
        for (Post post: posts) {
            if(!post.getUser().equals(user) && post.getReadAuthority() == Constants.READ_AUTHORITY_ONLY_ME)
                posts.remove(post);
        }
    }

    public void popNotFriendPost(List<Post> posts, User user){

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
