package com.sweetwith.dailynote.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
    // Create
    Profile save(Profile profile);

    // Read
    Optional<Profile> findById(Long id);

    // update
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Profile as p SET p.name =?2 , p.email =?3 WHERE p.Id =?1")
    void updateNameAndEmail(Long id, String name, String email);

    // delete
    void deleteById(Long id);
}
