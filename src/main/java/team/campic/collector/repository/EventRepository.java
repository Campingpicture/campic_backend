package team.campic.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.campic.collector.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
