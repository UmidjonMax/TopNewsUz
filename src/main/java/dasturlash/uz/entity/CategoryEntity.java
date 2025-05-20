package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private boolean visible;
    private LocalDateTime createdDate;
}
