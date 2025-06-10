package dasturlash.uz.service;

import dasturlash.uz.dto.sms.SmsJwtDTO;
import dasturlash.uz.dto.sms.SmsRequestDTO;
import dasturlash.uz.entity.SmsJwtEntity;
import dasturlash.uz.repository.SmsJwtRepository;
import dasturlash.uz.util.JWTUtil;
import dasturlash.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsSendService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Autowired
    private SmsJwtRepository smsJwtRepository;

    public void sendRegistrationSMS(String phone) {
        Integer smsCode = RandomUtil.fiveDigit();
        String body = "Bu Eskiz dan test";
        // send
        sendSms(phone, body);
        // save to db
        String jwtToken = JWTUtil.encode(phone, smsCode);
        smsHistoryService.create(body, smsCode, phone);
    }

    // TODO login method. save token in some db and refresh/change in 29 days
    private String smsJwt(){
        RestTemplate restTemplate = new RestTemplate();

        // So‘rov uchun ma'lumot
        Map<String, String> request = new HashMap<>();
        request.put("email", "umidjonmax0001@gmail.com");
        request.put("password", "O4lJZv07qEPLjWVgtVFxMH4gN61SQQwFNIIB1yln");

        // HTTP so‘rovning turi va sarlavhasi


        String response;
        SmsJwtEntity lastToken = smsJwtRepository.findLastToken();
        // POST so‘rov yuborish
        SmsJwtEntity jwtEntity = new SmsJwtEntity();
        if (lastToken == null || lastToken.getCreatedDate().plusDays(29).isBefore(LocalDateTime.now())) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
//
//            response = restTemplate.exchange(
//                    "https://notify.eskiz.uz/api/auth/login",
//                    HttpMethod.POST,
//                    entity,
//                    String.class
//            ).getBody();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<Map> mapResponse = restTemplate.exchange(
                    "https://notify.eskiz.uz/api/auth/login",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

// response.getBody() -> bu yerda Map keladi
            Map<String, Object> responseBody = mapResponse.getBody();
            Map<String, String> data = (Map<String, String>) responseBody.get("data");
            response = data.get("token");

            jwtEntity.setToken(response);
            jwtEntity.setCreatedDate(LocalDateTime.now());
            smsJwtRepository.save(jwtEntity);
        }else {
            response = lastToken.getToken();
        }

        SmsJwtDTO body = new SmsJwtDTO();
        body.setToken(jwtEntity.getToken());
        body.setCreatedDate(LocalDateTime.now());
        // Javobni qaytarish
        return response;
    }
    private void sendSms(String phone, String message) {
        SmsRequestDTO body = new SmsRequestDTO();
        body.setMobile_phone(phone);
        body.setMessage(message);

        String jwt = smsJwt();

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + jwt);

        RequestEntity<SmsRequestDTO> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        restTemplate.exchange(request, String.class);
    }


}
