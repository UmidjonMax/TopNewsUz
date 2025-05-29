package dasturlash.uz.repository;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.profile.ProfileFilterDTO;
import dasturlash.uz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ProfileEntity> filter(ProfileFilterDTO filter, int page, int size) {

        StringBuilder condition = new StringBuilder(" where visible = true ");
        Map<String, Object> params = new HashMap<>();
        if (filter.getQuery() != null) {
            condition.append(" and (lower(p.name) like :query or lower(p.surname) like :query or lower(p.username) like :query) ");
            params.put("query", "%" + filter.getQuery().toLowerCase() + "%");
        }
        if (filter.getRole() != null) { // ROLE_ADMIN
            condition.append(" and pr.roles =:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) { // 01.01.2021 - 01.01.2024
            condition.append(" and p.createdDate between :fromDate  and :toDate ");
            params.put("fromDate", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN));
            params.put("toDate", LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX));
        } else if (filter.getCreatedDateFrom() != null) { // 01.01.2021
            condition.append(" and p.createdDate >= :fromDate");
            params.put("fromDate", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN));
        } else if (filter.getCreatedDateTo() != null) { // 01.01.2024
            condition.append(" and p.createdDate <= :toDate ");
            params.put("toDate", LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX));
        }
        //
        StringBuilder selectBuilder = new StringBuilder("Select p From ProfileEntity p ");
        StringBuilder countBuilder = new StringBuilder("Select count(*) From ProfileEntity p ");
        if (filter.getRole() != null) { // ROLE_ADMIN
            selectBuilder.append(" inner join p.roleList as pr ");
            countBuilder.append(" inner join p.roleList as pr ");
        }
        //
        selectBuilder.append(condition);
        countBuilder.append(condition);
        //
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(page * size); // offset

        List<ProfileEntity> profileList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

//        return new PageImpl<>(profileList, PageRequest.of(page, size), totalCount);
        return new FilterResultDTO<>(profileList, totalCount);
    }
}
