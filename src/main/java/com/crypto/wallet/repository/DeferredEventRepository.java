package com.crypto.wallet.repository;

import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.EventStatus;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeferredEventRepository extends JpaRepository<DeferredEvent,String> {
    @Query("SELECT e FROM deferevents e WHERE e.eventStatus IN :status AND e.deferUntil < :currentTime")
    List<DeferredEvent> findEligibleDeferredEvents(@PathParam("status")List<EventStatus> status,
                                                   @PathParam("currenttime") long currentTime);

}
