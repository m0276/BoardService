package MjLee.boardService.service;

import MjLee.boardService.dto.UserDto;
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
        if(userRepository.findByName(userDto.getUserName()).isPresent()){
            if(userRepository.findByName(userDto.getUserName()).get().getPassword().equals(userDto.getPassword())){
                userRepository.deleteByName(userDto.getUserName());
                return true;
            }
        }

        return false;
    }

    public void readUserPosting(UserDto userDto){
        if(userRepository.findByName(userDto.getUserName()).isEmpty()) return;

        System.out.println(userRepository.findByName(userDto.getUserName()).get().getPostings());
    }

    public void readUserComment(UserDto userDto){
        if(userRepository.findByName(userDto.getUserName()).isEmpty()) return;
        System.out.println(userRepository.findByName(userDto.getUserName()).get().getComments());
    }

    User findByName(String userName) {
        if(userRepository.findByName(userName).isPresent())
        return userRepository.findByName(userName).get();
        else throw new RuntimeException();
    }
}
