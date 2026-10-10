
package com.example.persistncia_dados_sqlite_android;

/**
 * Classe modelo (Model) que representa um Produto.
 * Cada objeto desta classe corresponde a um registro
 * armazenado na tabela de produtos do banco SQLite.
 */
public class Produto {

    // Identificador único do produto (chave primária no banco de dados)
    private long id;

    // Nome do produto
    private String nome;

    // Preço do produto
    private double preco;

    /**
     * Construtor vazio.
     * Utilizado quando se deseja criar um objeto Produto
     * sem definir seus atributos imediatamente.
     */
    public Produto() {
    }

    /**
     * Construtor utilizado para criar objetos recuperados
     * do banco de dados, incluindo o ID já existente.
     *
     * @param id Identificador do produto.
     * @param nome Nome do produto.
     * @param preco Preço do produto.
     */
    public Produto(long id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Construtor utilizado para criar novos produtos antes
     * de serem inseridos no banco de dados.
     *
     * @param nome Nome do produto.
     * @param preco Preço do produto.
     */
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Retorna o identificador do produto.
     *
     * @return ID do produto.
     */
    public long getId() {
        return id;
    }

    /**
     * Define o identificador do produto.
     *
     * @param id Novo ID do produto.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return Nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome Novo nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço do produto.
     *
     * @return Preço do produto.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o preço do produto.
     *
     * @param preco Novo preço do produto.
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Sobrescreve o método toString().
     * Este método define como o objeto será exibido
     * em componentes visuais, como a ListView.
     *
     * @return String contendo nome e preço formatado.
     */
    @Override
    public String toString() {

        // Retorna o nome do produto acompanhado do preço
        // formatado com duas casas decimais.
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}