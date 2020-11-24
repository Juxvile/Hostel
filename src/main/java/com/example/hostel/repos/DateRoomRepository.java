package com.example.hostel.repos;

import com.example.hostel.domain.DateRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DateRoomRepository extends JpaRepository <DateRoom, Long> {
    List <DateRoom> findByEntryDate(LocalDate entryDate);
    List <DateRoom> findByLeaveDate(LocalDate leaveDate);
    List <DateRoom> findByEntryDateBetweenAndLeaveDate(
            DateRoom entryDate,
            DateRoom leaveDate
    );

    @Query("select a from DateRoom a where a.entryDate <= :entryDate")
    List <DateRoom> findAllWithEntryDateBefore(
            @Param("entryDate") LocalDate entryDate);
}
