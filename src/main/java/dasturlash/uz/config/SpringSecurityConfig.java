package dasturlash.uz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static String[] openApiList = {"/v1/attach/**",
            "/v1/auth/**",
            "/v1/category/lang",
            "/v1/region/lang",
            "/v1/article/last-n-by-section/*",
            "/v1/article/last-twelwe",
            "/v1/article/by-category/**",
            "/v1/article/by-region/**",
            "/v1/article/detail/*",
            "/v1/article/section-small/*",
            "/v1/article/most-read/*",
            "/v1/article/view-count/*",
            "/v1/article/shared-count/*",
            "/v1/article/filter"
    };


    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchining identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization - Foydalanuvchining tizimdagi huquqlarini tekshirish.
        // Ya'ni foydalanuvchi murojat qilayotgan API-larni ishlatishga ruxsati bor yoki yo'qligini tekshirishdir.
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(openApiList).permitAll()
                    .requestMatchers("/v1/category/admin", "/v1/category/admin/**").hasRole("ADMIN")
                    .requestMatchers("/v1/section/admin", "/v1/category/admin/**").hasRole("ADMIN")
                    .requestMatchers("/v1/region/admin", "/v1/region/admin/**").hasRole("ADMIN")
                    .requestMatchers("/v1/profile/admin", "/v1/profile/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/v1/attach/admin/**").hasRole("ADMIN")
                    .requestMatchers("/v1/article/**").hasRole("ADMIN")
                    .requestMatchers("v1/article/moderator", "/v1/article/moderator/**").hasRole("MODERATOR")
                    .requestMatchers("v1/article/publisher/status").hasRole("PUBLISHER")
                    .anyRequest()
                    .authenticated();
        }).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());
        
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
