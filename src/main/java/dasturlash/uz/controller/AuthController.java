package dasturlash.uz.controller;

import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.dto.profile.ProfileLoginDTO;
import dasturlash.uz.dto.profile.ProfileUpdateDTO;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    // TODO body (smsCode,phone) like regEmailVerification
    @GetMapping("/registration/sms/verification")
    public ResponseEntity<String> registrationSms(@PathVariable("token") String token) {
        return ResponseEntity.ok(authService.regVerification(token));
    }

    @GetMapping("/registration/email/verification/{jwt}")
    public ResponseEntity<String> registration(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.regVerification(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody ProfileLoginDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}
