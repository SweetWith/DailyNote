package com.sweetwith.dailynote.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    HashTag save(HashTag hashTag);

    Optional<HashTag> findById(Long id);
    Optional<HashTag> findByTagName(String tagName);
}
