package dasturlash.uz.config;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Service
    public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private ProfileRepository profileRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(username);
            if (optional.isEmpty()) {
                throw new AppBadException("User name not found");
            }
            ProfileEntity profile = optional.get();
            return new CustomUserDetails(profile.getId(),
                    profile.getUsername(),
                    profile.getPassword(), profile.getStatus());
        }
    }


}
