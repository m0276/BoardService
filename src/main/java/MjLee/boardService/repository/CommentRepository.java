package MjLee.boardService.repository;

import MjLee.boardService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserName(String userName);
    List<Comment> findByPostingCount(Long count);

    void deleteByUserName(String userName);

    void deleteByPostingCount(Long count);
}
