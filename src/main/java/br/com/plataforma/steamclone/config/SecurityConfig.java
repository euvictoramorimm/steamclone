package br.com.plataforma.steamclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.plataforma.steamclone.service.CustomUserDetailsService; 
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
            )
            .authorizeHttpRequests(auth -> auth
                // 1. Coisas públicas (todo mundo vê)
                .requestMatchers("/", "/css/**", "/h2-console/**", "/login", "/registrar").permitAll()
                
                // 2. Coisas SÓ DE ADMIN (Adicionar, Editar, Excluir, Salvar)
                .requestMatchers("/jogos/novo", "/jogos/editar/**", "/jogos/deletar/**", "/jogos/salvar").hasAuthority("ADMIN")
                
                // 3. Coisas de USUÁRIO LOGADO (Comprar, Ver Biblioteca)
                // (O Admin também consegue fazer isso pq ele está autenticado)
                .anyRequest().authenticated() 
            )
            .userDetailsService(customUserDetailsService)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/jogos/salvar", "/registrar", "/comprar/**")); 

        return http.build();
    }
}