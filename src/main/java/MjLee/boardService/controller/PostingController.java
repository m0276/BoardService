package MjLee.boardService.controller;

import MjLee.boardService.dto.PostingDto;
import MjLee.boardService.entity.Posting;
import MjLee.boardService.service.CommentService;
import MjLee.boardService.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@RestController
@RequestMapping("/posting")
public class PostingController {
    PostingService postingService;
    CommentService commentService;

    @Autowired
    public PostingController(PostingService postingService, CommentService commentService) {
        this.postingService = postingService;
        this.commentService = commentService;
    }

    // 글이 올라온 순서를 지정할 Long 변수 필요
    Long count = 1L;

    @GetMapping
    public ModelAndView postings(Model model){
        model.addAttribute(postingService.readAllPosting());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postings");

        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Void> createPosting(@RequestBody PostingDto postingDto){
        postingDto.setPostingCount(count);
        postingService.save(postingDto);
        count++;
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePosting(@RequestBody PostingDto postingDto){
        try{
            postingService.update(postingDto);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{postingCount}")
    public ResponseEntity<Void> deletePosting(@PathVariable Long postingCount){
        PostingDto postingDto = new PostingDto();
        postingDto.setPostingCount(postingCount);
        commentService.deletePosting(postingCount);

        try{
            postingService.delete(postingDto);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
