package br.com.plataforma.model;

public class Usuario {
    private String nomeDeUsuario;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) 
    

        

    }

    public String getNomeDeUsuario() {
        return this.nomeDeUsuario;
    }

    public void setNomeDeUsuario(String novoNome) {
        if (novoNome == null || novoNome.isEmpty()) {
            System.out.println("Nome de usuário não pode ser nulo ou vazio.");
        } else {
            this.nomeDeUsuario = novoNome;
        }
    }
    
}
