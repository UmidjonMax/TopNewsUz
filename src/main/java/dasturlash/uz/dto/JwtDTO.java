package dasturlash.uz.dto;

public class JwtDTO {
    private String username;
    private Integer code;

    public JwtDTO(String username, Integer code) {
        this.username = username;
        this.code = code;
    }

    public JwtDTO() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }


    public Integer getCode() {
        return code;
    }

}
