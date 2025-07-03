package dasturlash.uz.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentCreateDTO {
    @NotBlank(message = "Content required")
    private String content;
    @NotBlank(message = "ArticleId required")
    private String articleId;
    private String replyId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
