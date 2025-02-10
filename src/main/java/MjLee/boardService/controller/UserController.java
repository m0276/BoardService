package MjLee.boardService.controller;

import MjLee.boardService.dto.UserDto;
import MjLee.boardService.service.CommentService;
import MjLee.boardService.service.LoginService;
import MjLee.boardService.service.PostingService;
import MjLee.boardService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/board/user")
public class UserController {
    UserService userService;
    PostingService postingService;
    CommentService commentService;
    LoginService loginService;

    @Autowired
    public UserController(UserService userService, PostingService postingService, CommentService commentService, LoginService loginService) {
        this.userService = userService;
        this.postingService = postingService;
        this.commentService = commentService;
        this.loginService = loginService;
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

    @PutMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDto userDto){
        if(loginService.login(userDto)) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody UserDto userDto){
        try{
            loginService.logout(userDto);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName){

        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        if(userService.findUser(userDto).isLogin()){
            commentService.deleteUser(userName);
            postingService.deleteUser(userName);

            if(userService.delete(userDto)) return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
