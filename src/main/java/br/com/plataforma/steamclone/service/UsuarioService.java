package br.com.plataforma.steamclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.plataforma.steamclone.model.Usuario;
import br.com.plataforma.steamclone.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Vamos configurar isso já já

    public Usuario criarNovoUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar!
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        if(usuario.getSaldoCarteira() == null) {
            usuario.setSaldoCarteira(0.0);
        }
        return usuarioRepository.save(usuario);
    }
}