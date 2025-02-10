package MjLee.boardService.service;
import java.util.*;
import MjLee.boardService.dto.UserDto;
import MjLee.boardService.entity.Comment;
import MjLee.boardService.entity.Posting;
import MjLee.boardService.entity.User;
import MjLee.boardService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserDto userDto){
        User user = new User();
        user.setName(userDto.getUserName());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }

    public void update(UserDto userDto){
        User user;
        if(userRepository.findByName(userDto.getUserName()).isPresent())
            user = userRepository.findByName(userDto.getUserName()).get();
        else throw new RuntimeException();

        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }

    public boolean delete(UserDto userDto){
        if (userRepository.findByName(userDto.getUserName()).isPresent()) {
            userRepository.deleteByName(userDto.getUserName());
            return true;
        }

        return false;
    }

    public User findUser(UserDto userDto){
        if(userRepository.findByName(userDto.getUserName()).isEmpty()) return null;
        return userRepository.findByName(userDto.getUserName()).get();
    }

    public List<Posting> findUserPosting(UserDto userDto){
        if(userRepository.findByName(userDto.getUserName()).isEmpty()) return null;
        return (userRepository.findByName(userDto.getUserName()).get().getPostings());
    }

    public List<Comment> findUserComment(UserDto userDto){
        if(userRepository.findByName(userDto.getUserName()).isEmpty()) return null;
        return (userRepository.findByName(userDto.getUserName()).get().getComments());
    }

    User findByName(String userName) {
        if(userRepository.findByName(userName).isPresent())
        return userRepository.findByName(userName).get();
        else throw new RuntimeException();
    }

    Posting findByPostingCount(String name, Long postCount){
        if(userRepository.findByName(name).isEmpty()) return null;
        List<Posting> list = (userRepository.findByName(name).get().getPostings());
        for(Posting posting : list){
            if(posting.getCount().equals(postCount)){
                return posting;
            }
        }

        return null;
    }
}
