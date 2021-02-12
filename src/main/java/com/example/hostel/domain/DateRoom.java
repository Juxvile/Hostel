package com.example.hostel.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class DateRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer countPeople;

    @NotNull
    @FutureOrPresent(message = "Нельзя выбрать прошлую дату")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate;

    @NotNull
    @FutureOrPresent(message = "Нельзя выбрать прошлую дату")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate leaveDate;

    private String comment;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id")
    private Room room;
}
