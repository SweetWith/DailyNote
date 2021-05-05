package com.sweetwith.dailynote.web;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.service.posts.PostService;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostsApiController {
    private final PostService postService;

    @GetMapping("/post")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @PostMapping("/post")
    public Long registerPost(@RequestBody Post post) {
        return postService.registerPost(post.getTitle(), post.getContent(), post.getUser());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted Post Data by id : [" + id + "]", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
