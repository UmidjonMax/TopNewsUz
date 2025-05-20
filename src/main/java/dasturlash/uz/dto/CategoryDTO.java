package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private boolean visible;
    private LocalDateTime createdDate;
}
