package com.sweetwith.dailynote.domain.profile;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import com.sweetwith.dailynote.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Profile extends BaseTimeEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    private User user;

    @Builder
    public Profile(String name, String email, User user) {
        this.name = name;
        this.email = email;
        this.user = user;
    }
}