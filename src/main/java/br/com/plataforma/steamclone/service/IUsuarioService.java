package br.com.plataforma.steamclone.service;

import br.com.plataforma.steamclone.model.Usuario;
import java.util.Optional;

public interface IUsuarioService {
    
    // Método 1: Criar usuário (já existia)
    Usuario criarNovoUsuario(Usuario usuario); 
    
    // Método 2: Buscar por email (usado no login)
    Optional<Usuario> buscarUsuarioPorEmail(String email);
}