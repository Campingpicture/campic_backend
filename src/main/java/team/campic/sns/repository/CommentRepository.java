package team.campic.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.campic.sns.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
