package com.sweetwith.dailynote.service.hashtag;

import com.sweetwith.dailynote.domain.hashtag.HashTag;
import com.sweetwith.dailynote.domain.hashtag.HashTagRepository;
import com.sweetwith.dailynote.domain.hashtag.PostHashTag;
import com.sweetwith.dailynote.domain.hashtag.PostHashTagRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HashTagService {
    private HashTagRepository hashTagRepository;
    private PostHashTagRepository postHashTagRepository;

    @Autowired
    public HashTagService(HashTagRepository hashTagRepository, PostHashTagRepository postHashTagRepository){
        this.hashTagRepository = hashTagRepository;
        this.postHashTagRepository = postHashTagRepository;
    }

    // CREATE
    public Long registerHashTag(String tagName){
        if(isDuplicateTagName(tagName) != true)
            return hashTagRepository.findByTagName(tagName).get().getId();

        HashTag hashTag = new HashTag(tagName);
        return hashTagRepository.save(hashTag).getId();
    }

    public boolean isDuplicateTagName(String tagName){
        if(hashTagRepository.findByTagName(tagName).isPresent())
            return true;
        return false;
    }

    public List<Post> getPostsByHashTagId(Long hashTagId){
        HashTag hashTag = hashTagRepository.findById(hashTagId).get();
        List<PostHashTag> postHashTags = postHashTagRepository.findPostHashTagByHashTag(hashTag);

        List<Post> ret = new ArrayList<>();
        for (PostHashTag postHashTag : postHashTags ) {
            ret.add(postHashTag.getPost());
        }
        return ret;
    }
}
