package MjLee.boardService.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posting")
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_posting")
    Long id;

    @Column
    String title;

    @Column
    String content;

    @Column(name = "posting_count")
    Long count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany
    @JoinColumn(name = "id_comment")
    List<Comment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long postingCount) {
        this.count = postingCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
