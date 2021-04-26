package com.example.hostel.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private Integer roomNumber;

    @NotNull
    @Min(0)
    private Integer maxPeople;

    @NotNull
    private Integer rooms;

    @NotBlank
    private String description;

    @NotNull
    private Integer price;


    private String filename;

    private String filename2;

    private String filename3;

    private String filename4;

    private String filename5;

    private Status status;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set <Reviews> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set <DateRoom> dateRoom;

}
