package dasturlash.uz.repository;

import dasturlash.uz.entity.SmsJwtEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SmsJwtRepository extends CrudRepository<SmsJwtEntity, UUID> {
    @Query("from SmsJwtEntity order by createdDate limit 1")
    SmsJwtEntity findLastToken();
}
