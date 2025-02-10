package MjLee.boardService.repository;

import MjLee.boardService.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {
    List<Posting> findAll();
    Optional<Posting> findByCount(Long postingCount);
    void deleteByCount(Long postingCount);

    Optional<Posting> findByPostingUserName(String name);
    //void deleteByPostingUser(String userName);
}
