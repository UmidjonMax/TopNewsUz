package dasturlash.uz.service;

import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        profileRoleService.create(profile.getId(), ProfileRoleEnum.ROLE_USER);
        emailSenderService.sendSimpleMessage("Ishga qabul", """
                Assalomu Aleykum Ozodbek!
                Biz sizni junior java-developer lavozimiga ishga taklif qilmoqchimiz, agar rozi bo'lsangiz oyligimiz $5000 dan boshlanadi
                """, dto.getUsername());
        return  "Tasdiqlash kodi ketdi";
    }
}
