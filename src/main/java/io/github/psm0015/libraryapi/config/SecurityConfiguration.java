package io.github.psm0015.libraryapi.config;

import io.github.psm0015.libraryapi.security.CustomUserDetailsService;
import io.github.psm0015.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(
                        configurer -> configurer.loginPage("/login").permitAll()
                )
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return  web -> {
            web.ignoring().requestMatchers(
                    "/v2/api-docs/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/actuator/**"
            );
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetalisService(UsuarioService uService){

//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("321"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
        return new CustomUserDetailsService(uService);
    }

}
