package org.vip.bookmyshow.repositories;

import jakarta.persistence.LockModeType;
import jakarta.persistence.PessimisticLockScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.vip.bookmyshow.models.ShowSeat;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Query("select ss from ShowSeat ss where ss.id in ?1")
    public List<ShowSeat> findAllByIdInWithoutLock(List<Long> seatIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<ShowSeat> findAllByIdIn(List<Long> seatIds);

}
