package com.wable.harmonika.domain.group.entity;


import com.wable.harmonika.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters and setters
}
