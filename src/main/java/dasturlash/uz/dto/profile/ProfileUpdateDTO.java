package dasturlash.uz.dto.profile;

import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public class ProfileUpdateDTO {
    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;
    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;
    @NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    private String username;
    @NotEmpty(message = "Role bo‘sh bo‘lmasligi kerak")
    private List<ProfileRoleEnum> roleList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProfileRoleEnum> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ProfileRoleEnum> roleList) {
        this.roleList = roleList;
    }
}
