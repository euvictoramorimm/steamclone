package br.com.plataforma.steamclone.model;

public class Usuario {
    private Long ID;
    private String nome;
    private String email;
    private String senha;

    //Getters e Setters

    public Long getID() {
        return this.ID;
    }

    public void setId() {
        this.ID = ID;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {

            if (novoNome == null || novoNome.isEmpty()) {
        System.out.println("ERRO: Nome de usuário não pode ser nulo ou vazio.");
    } else {
        // 2. ATRIBUIÇÃO: O "guarda" coloca o 'novoNome' no cofre
        // 'this.nomeDeUsuario' (o atributo da classe)
        // recebe o valor de 'novoNome' (o parâmetro)

        this.nomeDeUsuario = novoNome;  
    }
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }




}