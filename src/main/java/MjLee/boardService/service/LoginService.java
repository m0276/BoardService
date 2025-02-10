package MjLee.boardService.service;

import MjLee.boardService.dto.UserDto;
import MjLee.boardService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(UserDto userDto){
        if (userRepository.findByName(userDto.getUserName()).isPresent()) {
            if (userRepository.findByName(userDto.getUserName()).get().getPassword().equals(userDto.getPassword())) {
                userRepository.findByName(userDto.getUserName()).get().setLogin(true);
                userRepository.save(userRepository.findByName(userDto.getUserName()).get());
                return true;
            }
        }

        return false;
    }

    public void logout(UserDto userDto){
        if (userRepository.findByName(userDto.getUserName()).isPresent()) {
            userRepository.findByName(userDto.getUserName()).get().setLogin(false);
            userRepository.save(userRepository.findByName(userDto.getUserName()).get());
        }
        else throw new RuntimeException();
    }
}
