package MjLee.boardService.controller;

import MjLee.boardService.dto.UserDto;
import MjLee.boardService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    // user delete시 관련 comment,posting 삭제 필요
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userName}")
    public ModelAndView userInfo(@PathVariable String userName, Model model){
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        model.addAttribute(userService.findUser(userDto));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");

        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto){
        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{userName}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto){
        try {
            userService.update(userDto);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{userName}/{userPassWord}") // login을 구현하면 password를 따로 받을 필요 없을 것 같음
    public ResponseEntity<Void> deleteUser(@PathVariable String userName, @PathVariable String userPassWord){
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        userDto.setPassword(userPassWord);

        if(userService.delete(userDto)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
