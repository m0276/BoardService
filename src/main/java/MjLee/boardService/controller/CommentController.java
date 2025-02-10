package MjLee.boardService.controller;

import MjLee.boardService.dto.CommentDto;
import MjLee.boardService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/board/comment")
public class CommentController {

    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posting/{postingCount}")
    public ModelAndView postingComment(@PathVariable Long postingCount, Model model){
        model.addAttribute(commentService.findCommentByPosting(postingCount));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postingComment");
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Void> creatComment(@RequestBody CommentDto commentDto){
        commentService.save(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentDto commentDto){
        try{
            if(commentService.checkLoginOfUser(commentDto)){
                try{
                    commentService.update(commentDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postingCount}/{userName}/{commentIndex}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postingCount, @PathVariable String userName, @PathVariable int commentIndex){
        CommentDto commentDto = new CommentDto();
        commentDto.setPostingCount(postingCount);
        commentDto.setUserName(userName);
        commentDto.setCommentIndex(commentIndex);

        try{
            if(commentService.checkLoginOfUser(commentDto)){
                try{
                    commentService.delete(commentDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
