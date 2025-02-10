package MjLee.boardService.controller;

import MjLee.boardService.dto.PostingDto;
import MjLee.boardService.service.CommentService;
import MjLee.boardService.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        try{
            if(postingService.checkLoginByUserName(postingDto)){
                Long count;
                if(postingService.readAllPosting() == null){
                    count = 0L;
                }
                else{
                    count = (long) (postingService.readAllPosting().size());
                }

                postingDto.setPostingCount(count+1);

                postingService.save(postingDto);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePosting(@RequestBody PostingDto postingDto){
        try{
            if(postingService.checkLoginByPostingCount(postingDto)){
                try{
                    postingService.update(postingDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
            if(postingService.checkLoginByPostingCount(postingDto)){
                commentService.deletePosting(postingCount);

                try{
                    postingService.delete(postingDto);
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
