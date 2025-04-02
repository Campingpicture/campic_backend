package team.campic.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.campic.sns.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}