package team.campic.sns.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.campic.sns.entity.Like;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long postId);
}
