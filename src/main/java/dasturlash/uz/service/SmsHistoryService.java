package dasturlash.uz.service;

import dasturlash.uz.entity.SmsHistoryEntity;
import dasturlash.uz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void create(String body, int code, String user) {
        SmsHistoryEntity smsHistory = new SmsHistoryEntity();
        smsHistory.setBody(body);
        smsHistory.setCode(code);
        smsHistory.setToAccount(user);
        smsHistoryRepository.save(smsHistory);
    }

    public boolean isSmsSendToAccount(String account, int code) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findLastByAccount(account);
        if (optional.isEmpty()) {
            return false;
        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getAttemptCount() >= 3) {
            return false;
        }
        if (!entity.getCode().equals(code)) {
            entity.setAttemptCount(entity.getAttemptCount() + 1);
            return false;
        }
        //  20:32:40           =   20:30.40  + 0:2:00
        LocalDateTime extDate = entity.getCreatedDate().plusMinutes(2);
        // now  20:31:30  >  20:32:40    |     now 20:35:30  >  20:32:40
        if (LocalDateTime.now().isAfter(extDate)) {
            return false;
        }

        // 1. JWT
        // 2. attempCount - urunishlar soni
        // 3. login (username,password)
        // Article (1,2,3,4)
        return true;
    }
}
