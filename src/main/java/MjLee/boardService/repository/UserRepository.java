package MjLee.boardService.repository;

import MjLee.boardService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    void deleteByName(String name);
    Optional<User> findByName(String userName);
}
