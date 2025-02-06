package MjLee.boardService.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    Long id;

    @Column
    String text;

    @ManyToOne
    @JoinColumn(name = "posting_id")
    Posting posting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
