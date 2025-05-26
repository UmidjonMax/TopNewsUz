package dasturlash.uz.dto.profile;

import jakarta.validation.constraints.NotBlank;

public class ProfileLoginDTO {
    @NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    private String username;
    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
