package MjLee.boardService.dto;

import java.util.UUID;

public class PostingDto {
    String userName;
    String title;
    String content;
    Long postingCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public Long getPostingCount() {
        return postingCount;
    }

    public void setPostingCount(Long postingCount) {
        this.postingCount = postingCount;
    }
}
