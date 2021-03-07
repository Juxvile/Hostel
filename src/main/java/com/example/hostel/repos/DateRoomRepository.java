package com.example.hostel.repos;

import com.example.hostel.domain.DateRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DateRoomRepository extends JpaRepository <DateRoom, Long> {

    @Query("select dr from DateRoom dr where dr.entryDate <= :leaveDate and dr.leaveDate >= :entryDate")
    List <DateRoom> findAllBetweenDates(
            @Param("entryDate") LocalDate entryDate,
            @Param("leaveDate") LocalDate leaveDate
    );

    List <DateRoom> findUserById (Long id);

}
