package dasturlash.uz.dto.article;

import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.validation.constraints.NotNull;

public class ArticleChangeStatusDTO {
    @NotNull(message = "Status required")
    private ArticleStatusEnum status;

    public ArticleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ArticleStatusEnum status) {
        this.status = status;
    }
}
