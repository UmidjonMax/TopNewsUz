package dasturlash.uz.dto.profile;

import dasturlash.uz.enums.ProfileRoleEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProfileFilterDTO {
    private String query; // name, surname, username
    private ProfileRoleEnum role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ProfileRoleEnum getRole() {
        return role;
    }

    public void setRole(ProfileRoleEnum role) {
        this.role = role;
    }

    public LocalDate getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(LocalDate createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public LocalDate getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(LocalDate createdDateTo) {
        this.createdDateTo = createdDateTo;
    }
}
