package com.nicetravel.repository;

import com.nicetravel.entity.EventTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EventsRepository extends JpaRepository<EventTour, Integer> {

}
