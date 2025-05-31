package dasturlash.uz.service;

import dasturlash.uz.dto.CodeInfoDTO;
import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.JWTUtil;
import dasturlash.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        emailSenderService.sendRegistrationEmail(profile.getUsername());
        //response
        return  "Tasdiqlash kodi ketdi";
    }

    public

    public String regEmailVerification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt); // decode qilish
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

}
