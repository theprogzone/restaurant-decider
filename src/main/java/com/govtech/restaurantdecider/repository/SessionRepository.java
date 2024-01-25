package com.govtech.restaurantdecider.repository;

import com.govtech.restaurantdecider.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUser_IdAndIsActive(Long id, boolean isActive);

    Optional<Session> findBySessionId(String sessionId);

    Optional<Session> findBySessionIdAndIsActive(String sessionId, boolean isActive);

}
