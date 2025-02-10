package MjLee.boardService.dto;

public class CommentDto {
    String text;
    String userName;
    Long postingCount;
    int commentIndex;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPostingCount() {
        return postingCount;
    }

    public void setPostingCount(Long postingCount) {
        this.postingCount = postingCount;
    }

    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }
}
