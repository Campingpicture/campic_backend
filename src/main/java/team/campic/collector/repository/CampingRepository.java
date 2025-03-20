package team.campic.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.campic.collector.entity.CampingEntity;

public interface CampingRepository extends JpaRepository<CampingEntity, Long> {
}
