package com.wable.harmonika.domain.profile.entity;


import com.wable.harmonika.domain.group.entity.Group;
import com.wable.harmonika.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private String descriptions;

    // Getters and setters
}
