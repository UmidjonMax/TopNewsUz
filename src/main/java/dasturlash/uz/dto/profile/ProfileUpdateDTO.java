package dasturlash.uz.dto.profile;

import dasturlash.uz.enums.ProfileStatusEnum;

import java.time.LocalDateTime;

public class ProfileUpdateDTO {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private ProfileStatusEnum status;
    private boolean visible;
    private LocalDateTime createdDate;
}
