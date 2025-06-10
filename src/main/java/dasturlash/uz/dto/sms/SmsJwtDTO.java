package dasturlash.uz.dto.sms;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SmsJwtDTO {
    private String token;
    private LocalDateTime createdDate;
}
