package dasturlash.uz.service;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.dto.profile.ProfileLoginDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsSendService smsSendService;


    public String register(RegistrationDTO dto){
        Optional<ProfileEntity> existOpt = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(existOpt.isPresent()){
            ProfileEntity existProfile = existOpt.get();
            if(existProfile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)){
                profileRoleService.deleteRolesByProfileId(existProfile.getId());
                profileRepository.deleteById(existProfile.getId());
            }else {
                throw new AppBadException("Username already in use");
            }
        }
        //create
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        profileRepository.save(profile);
        //create profile role
        profileRoleService.create(profile.getId(), ProfileRoleEnum.ROLE_USER);
        //send
        String phoneRegex = "^(\\+998|998)[0-9]{9}$";
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern phonePattern = Pattern.compile(phoneRegex);
        if (emailPattern.matcher(profile.getUsername()).matches()) {
            //send email
            emailSenderService.sendRegistrationEmail(profile.getUsername());
        }
        else if (phonePattern.matcher(profile.getUsername()).matches()) {
            //send sms
            smsSendService.sendRegistrationSMS(profile.getUsername());
        }
        //response
        return  "Tasdiqlash kodi ketdi";
    }

    public ProfileDTO login(ProfileLoginDTO dto){
        Optional<ProfileEntity> existOpt = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(existOpt.isEmpty()){
            throw new AppBadException("Username or password is incorrect");
        }
        ProfileEntity existProfile = existOpt.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), existProfile.getPassword())){
            throw new AppBadException("Username or password is incorrect");
        }
        if (existProfile.getStatus().equals(ProfileStatusEnum.BLOCKED)){
            throw new AppBadException("Username is blocked");
        }
        if (existProfile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)){
            throw new AppBadException("Activate your profile");
        }
        ProfileDTO profiledto = new ProfileDTO();
        profiledto.setName(existProfile.getName());
        profiledto.setSurname(existProfile.getSurname());
        profiledto.setUsername(existProfile.getUsername());
        profiledto.setRoleList(profileRoleService.getByProfileId(existProfile.getId()));
        profiledto.setJwt(existProfile.getUsername());
        return profiledto;
    }

    public String regVerification(String jwt) {
        JwtDTO jwtDTO = null;
        try {
            jwtDTO = JWTUtil.decode(jwt);
        } catch (ExpiredJwtException e) {
            throw new AppBadException("JWT Expired");
        } catch (JwtException e) {
            throw new AppBadException("JWT Not Valid");
        } // decode qilish
        String username = jwtDTO.getUsername();
        Integer code = jwtDTO.getCode();

        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if (existOptional.isEmpty()) {
            throw new AppBadException("Username not found");
        }
        ProfileEntity profile = existOptional.get();
        if (!profile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
            throw new AppBadException("Username in wrong status");
        }
        // sms code ni tekshirish
        if (emailHistoryService.isSmsSendToAccount(username, code)) {
            profile.setStatus(ProfileStatusEnum.ACTIVE);
            profileRepository.save(profile);
            return "Verification successfully completed";
        }
        throw new AppBadException("Not completed");
    }

//    public String regSmsVerification(String jwt) {
//        JwtDTO jwtDTO = null;
//        try {
//            jwtDTO = JWTUtil.decode(jwt);
//        } catch (ExpiredJwtException e) {
//            throw new AppBadException("JWT Expired");
//        } catch (JwtException e) {
//            throw new AppBadException("JWT Not Valid");
//        } // decode qilish
//        String username = jwtDTO.getUsername();
//        Integer code = jwtDTO.getCode();
//
//        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(username);
//        if (existOptional.isEmpty()) {
//            throw new AppBadException("Username not found");
//        }
//        ProfileEntity profile = existOptional.get();
//        if (!profile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
//            throw new AppBadException("Username in wrong status");
//        }
//        // sms code ni tekshirish
//        if (emailHistoryService.isSmsSendToAccount(username, code)) {
//            profile.setStatus(ProfileStatusEnum.ACTIVE);
//            profileRepository.save(profile);
//            return "Verification successfully completed";
//        }
//        throw new AppBadException("Not completed");
//    }
}