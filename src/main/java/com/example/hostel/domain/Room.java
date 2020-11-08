package com.example.hostel.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long roomNumber;
    @NotBlank
    private Long maxPeople;

//    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
}
