package MjLee.boardService.service;

import MjLee.boardService.dto.CommentDto;
import MjLee.boardService.dto.UserDto;
import MjLee.boardService.entity.Comment;
import MjLee.boardService.repository.CommentRepository;
import MjLee.boardService.repository.PostingRepository;
import MjLee.boardService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class CommentService {

    CommentRepository commentRepository;
    UserService userService;
    //PostingService postingService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        //this.postingService = postingService;
    }

    public void save(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setUser(userService.findByName(commentDto.getUserName()));
        comment.setPosting(userService.findByPostingCount(commentDto.getUserName(),commentDto.getPostingCount()));
        comment.setCount(userService.findByPostingCount(commentDto.getUserName(),commentDto.getPostingCount()).getComments().size()+1);
        commentRepository.save(comment);
    }

    public void update(CommentDto commentDto){
        Comment comment = null;

        List<Comment> list = commentRepository.findByPostingCount(commentDto.getPostingCount());
        for(Comment c : list){
            if(c.getUser().getName().equals(commentDto.getUserName())){
                comment = c;
                break;
            }
        }

        if(comment == null) throw new RuntimeException();

        comment.setText(commentDto.getText());
        commentRepository.save(comment);
    }

    public void delete(CommentDto commentDto){
        Comment comment = null;

        List<Comment> list = commentRepository.findByPostingCount(commentDto.getPostingCount());
        for(Comment c : list){
            if(c.getUser().getName().equals(commentDto.getUserName())){
                comment = c;
                break;
            }
        }

        if(comment == null) throw new RuntimeException();

        commentRepository.delete(comment);
    }

    public void deleteUser(String userName){
        commentRepository.deleteByUserName(userName);
    }

    public void deletePosting(Long postingCount){
        commentRepository.deleteByPostingCount(postingCount);
    }

    public List<Comment> findCommentByUser(CommentDto commentDto){
        return commentRepository.findByUserName(commentDto.getUserName());
    }

    public List<Comment> findCommentByPosting(Long postingCount){
        return commentRepository.findByPostingCount(postingCount);
    }

    public boolean checkLoginOfUser(CommentDto commentDto){
        if(commentRepository.findByPostingCount(commentDto.getPostingCount()).isEmpty()){
            throw new RuntimeException();
        }

        if(commentRepository.findByPostingCount(commentDto.getPostingCount()).get(commentDto.getCommentIndex())
                .getUser().getName().equals(commentDto.getUserName()))
            return commentRepository.findByPostingCount(commentDto.getPostingCount()).get(commentDto.getCommentIndex())
                    .getUser().isLogin();

        throw new RuntimeException();
    }
}
