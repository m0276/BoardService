package MjLee.boardService.service;

import MjLee.boardService.dto.PostingDto;
import MjLee.boardService.entity.Comment;
import MjLee.boardService.entity.Posting;
import MjLee.boardService.repository.PostingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class PostingService {
    PostingRepository postingRepository;
    UserService userService;
    CommentService commentService;

    @Autowired
    public PostingService(PostingRepository postingRepository, UserService userService, CommentService commentservice) {
        this.postingRepository = postingRepository;
        this.userService = userService;
        this.commentService = commentservice;
    }

    public void save(PostingDto postingDto){
        Posting posting = new Posting();
        posting.setTitle(posting.getTitle());
        posting.setContent(posting.getContent());
        posting.setCount(postingDto.getPostingCount());
        posting.setUser(userService.findByName(postingDto.getUserName()));

        postingRepository.save(posting);
    }

    public void update(PostingDto postingDto){
        Posting posting;
        if(postingRepository.findByCount(postingDto.getPostingCount()).isPresent()){
            posting = postingRepository.findByCount(postingDto.getPostingCount()).get();
        }
        else{
            throw new RuntimeException();
        }

        if(postingDto.getTitle().isEmpty() && postingDto.getContent().isEmpty()) throw new RuntimeException();
        else if(postingDto.getContent().isEmpty()) posting.setTitle(postingDto.getTitle());
        else posting.setContent(postingDto.getContent());
    }

    public void delete(PostingDto postingDto){
        if(postingRepository.findByCount(postingDto.getPostingCount()).isPresent()){
            postingRepository.deleteByCount(postingDto.getPostingCount());
        }
        else{
            throw new RuntimeException();
        }
    }

    public void deleteUser(String userName){
        postingRepository.findAll().removeIf(posting -> posting.getUser().getName().equals(userName));
    }

    public List<Posting> readAllPosting(){
        if(!postingRepository.findAll().isEmpty()){
           return postingRepository.findAll();
        }

        return null;
    }

    public List<Comment> readPostingComment(PostingDto postingDto){
        if(!commentService.findCommentByPosting(postingDto.getPostingCount()).isEmpty())
            return commentService.findCommentByPosting(postingDto.getPostingCount());
        return null;
    }

    Posting findByPostingCount(Long count){
        if(postingRepository.findByCount(count).isPresent()) return postingRepository.findByCount(count).get();
        else throw new RuntimeException();
    }
}
