package org.example.springbootstripe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login").permitAll()  // Permitir registro y login
                        .anyRequest().authenticated()  // Autenticar cualquier otra petición
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Página de login personalizada
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")  // Reemplazar con tu propia clave secreta
                        .tokenValiditySeconds(1209600)  // Validez del token en segundos (14 días)
                )
                .csrf(csrf -> csrf.disable());  // Deshabilitar CSRF si es necesario

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
