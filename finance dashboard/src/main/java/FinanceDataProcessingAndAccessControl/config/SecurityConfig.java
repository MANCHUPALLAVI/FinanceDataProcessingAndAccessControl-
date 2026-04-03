package FinanceDataProcessingAndAccessControl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails analyst = org.springframework.security.core.userdetails.User
                .withUsername("analyst")
                .password("{noop}analyst123")
                .roles("ANALYST")
                .build();

        UserDetails viewer = org.springframework.security.core.userdetails.User
                .withUsername("viewer")
                .password("{noop}viewer123")
                .roles("VIEWER")
                .build();
        return new InMemoryUserDetailsManager(admin, analyst, viewer);
    }
}
