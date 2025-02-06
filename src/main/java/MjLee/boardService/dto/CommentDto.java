package MjLee.boardService.dto;

public class CommentDto {
    String text;
    String userName;
    Long postingCount;

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
}
