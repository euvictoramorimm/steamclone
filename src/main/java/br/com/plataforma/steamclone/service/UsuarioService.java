package br.com.plataforma.steamclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.plataforma.steamclone.model.Usuario;
import br.com.plataforma.steamclone.repository.UsuarioRepository;
import java.util.Optional;
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private N8nService n8nService; 

    public Usuario criarNovoUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        if(usuario.getSaldoCarteira() == null) {
            usuario.setSaldoCarteira(0.0);
        }

        usuario.setPapel("USER"); 

        Usuario usuarioSalvo = usuarioRepository.save(usuario); // Salva primeiro
        
        // Avisa o N8n que nasceu um novo cliente!
        n8nService.notificarCadastro(usuarioSalvo); // <--- CHAMADA NOVA
        
        return usuarioSalvo;
    }
    @Override 
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }    
}