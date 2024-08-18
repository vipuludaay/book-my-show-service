package org.vip.bookmyshow.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.Show;
import org.vip.bookmyshow.models.ShowSeat;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Query("select ss from ShowSeat ss where ss.id in ?1")
    List<ShowSeat> findAllByIdInWithoutLock(List<Long> seatIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ShowSeat> findAllByIdIn(List<Long> seatIds);

}