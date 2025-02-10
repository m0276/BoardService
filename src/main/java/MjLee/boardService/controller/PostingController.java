package MjLee.boardService.controller;

import MjLee.boardService.dto.PostingDto;
import MjLee.boardService.entity.Posting;
import MjLee.boardService.service.CommentService;
import MjLee.boardService.service.LoginService;
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
@RequestMapping("/board/posting")
public class PostingController {

    PostingService postingService;
    CommentService commentService;


    @Autowired
    public PostingController(PostingService postingService, CommentService commentService) {
        this.postingService = postingService;
        this.commentService = commentService;
    }

    @GetMapping
    public ModelAndView postings(Model model){
        model.addAttribute(postingService.readAllPosting());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postings");

        return modelAndView;
    }

    @GetMapping("{postingCount}")
    public ModelAndView titleAndContent(@PathVariable Long postingCount,Model model){
        PostingDto postingDto = new PostingDto();
        postingDto.setPostingCount(postingCount);
        model.addAttribute(postingService.readPosting(postingDto));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("posting");

        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Void> createPosting(@RequestBody PostingDto postingDto){
        Long count;
        if(postingService.readAllPosting() == null){
            count = 0L;
        }
        else{
            count = (long) (postingService.readAllPosting().size());
        }

        postingDto.setPostingCount(count+1);

        postingService.save(postingDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePosting(@RequestBody PostingDto postingDto){
        try{
            if(postingService.findLoginByPostingCount(postingDto)){
                try{
                    postingService.update(postingDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{postingCount}")
    public ResponseEntity<Void> deletePosting(@PathVariable Long postingCount){
        PostingDto postingDto = new PostingDto();
        postingDto.setPostingCount(postingCount);
        try{
            if(postingService.findLoginByPostingCount(postingDto)){
                commentService.deletePosting(postingCount);

                try{
                    postingService.delete(postingDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
