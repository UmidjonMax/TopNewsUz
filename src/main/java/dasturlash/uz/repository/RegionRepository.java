package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update RegionEntity set key =: key, nameUz =: nameUz, nameRu=: nameRu, nameEn =: nameEn where id =: id")
    public int updateRegion(@Param("id") Integer id,@Param("key") String key,
                            @Param("nameUz") String nameUz,@Param("nameRu") String nameRu,@Param("nameEn") String nameEn);
}
