package com.example.hostel.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String review;

    @ManyToOne
    @JoinColumn(name = "reviews")
    private Room room;

    @ManyToOne
    private User user;

}
