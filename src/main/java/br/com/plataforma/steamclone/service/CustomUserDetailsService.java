package br.com.plataforma.steamclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // <-- IMPORT NOVO
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.plataforma.steamclone.repository.UsuarioRepository;
import br.com.plataforma.steamclone.model.Usuario;
import java.util.List; // <-- Mudou de ArrayList para List

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        
        // AQUI ESTÁ A MÁGICA:
        // Pegamos o papel do banco ("ADMIN" ou "USER") e transformamos em um "Crachá" do Spring
        var autoridade = new SimpleGrantedAuthority(usuario.getPapel());

        return new User(usuario.getEmail(), usuario.getSenha(), List.of(autoridade));
    }
}