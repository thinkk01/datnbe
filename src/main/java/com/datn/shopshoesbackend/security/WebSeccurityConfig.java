package com.datn.shopshoesbackend.security;

import com.datn.shopshoesbackend.domain.constant.RoleConst;
import com.datn.shopshoesbackend.jwt.JwtAuthenticationFilter;
import com.datn.shopshoesbackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSeccurityConfig {
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    AccountRepository accountRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) accountRepository.findAccountByUsername(username);
    }
    private static final String[] PUBLIC_MATCHERS = {
            "/api/site/**"
    };
    private static final String[] PRIVATE_ADMIN = {
            "/api/admin/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8888", "http://localhost:1111"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("http://localhost:8888");
        configuration.addAllowedOrigin("http://localhost:1111");
        configuration.addAllowedOrigin("https://curious-sorbet-6cf019.netlify.app/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.GET,PRIVATE_ADMIN).hasRole(RoleConst.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST,PRIVATE_ADMIN).hasRole(RoleConst.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT,PRIVATE_ADMIN).hasRole(RoleConst.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE,PRIVATE_ADMIN).hasRole(RoleConst.ROLE_ADMIN)
                        .anyRequest().authenticated()
                );
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailService)
//                .passwordEncoder(passwordEncoder());
//        return auth;
//    }

}
