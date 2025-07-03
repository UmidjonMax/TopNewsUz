package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRoleEnum;

import java.util.List;

public class JwtDTO {
    private String username;
    private Integer code;
    private List<ProfileRoleEnum> roles;

    public JwtDTO(String username, Integer code, List<ProfileRoleEnum> roles) {
        this.username = username;
        this.code = code;
        this.roles = roles;
    }

    public JwtDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ProfileRoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<ProfileRoleEnum> roles) {
        this.roles = roles;
    }
}
