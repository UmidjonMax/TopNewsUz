package dasturlash.uz.repository;

import dasturlash.uz.entity.EmailHistoryEntity;
import dasturlash.uz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {

    Optional<SmsHistoryEntity> findTopByToAccountOrderByCreatedDateDesc(String account);

    @Query("from SmsHistoryEntity where toAccount = ?1 order by createdDate desc limit 1")
    Optional<SmsHistoryEntity > findLastByAccount(String account);

}
