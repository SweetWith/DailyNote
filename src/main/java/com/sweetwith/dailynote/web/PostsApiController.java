package com.sweetwith.dailynote.web;

import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.service.posts.PostService;
import com.sweetwith.dailynote.web.dto.PostRequestDto;
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
        // TEMP
        User user = new User();
        //
        return postService.getPostListAll(user);
    }

    @PostMapping("/post")
    public Long registerPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.registerPost(postRequestDto);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted Post Data by id : [" + id + "]", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
