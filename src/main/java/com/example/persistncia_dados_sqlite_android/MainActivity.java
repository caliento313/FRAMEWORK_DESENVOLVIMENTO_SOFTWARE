package com.example.persistncia_dados_sqlite_android;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Activity principal da aplicação.
 * Responsável pelo cadastro e exibição dos produtos
 * armazenados no banco de dados SQLite.
 */
public class MainActivity extends AppCompatActivity {

    // Campos para entrada de dados do produto
    private EditText editNome;
    private EditText editPreco;

    // Botão responsável por salvar o produto
    private Button btnSalvar;

    // Lista utilizada para exibir os produtos cadastrados
    private ListView listViewProdutos;

    // Classe responsável pelas operações do banco de dados
    private ProdutoDbHelper dbHelper;

    // Adaptador utilizado para preencher a ListView
    private ArrayAdapter<Produto> adapter;

    // Lista que armazenará os produtos recuperados do banco
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela principal
        setContentView(R.layout.activity_main);

        // Associação dos componentes da interface às variáveis Java
        editNome = findViewById(R.id.editNome);
        editPreco = findViewById(R.id.editPreco);
        btnSalvar = findViewById(R.id.btnSalvar);
        listViewProdutos = findViewById(R.id.listViewProdutos);

        // Inicializa o banco de dados
        dbHelper = new ProdutoDbHelper(this);

        // Carrega os produtos já cadastrados
        atualizarListaProdutos();

        // Evento de clique do botão Salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
            }
        });
    }

    /**
     * Método responsável por validar os dados informados
     * e salvar o produto no banco de dados.
     */
    private void salvarProduto() {

        // Obtém os valores digitados pelo usuário
        String nome = editNome.getText().toString().trim();
        String precoStr = editPreco.getText().toString().trim();

        // Validação do nome do produto
        if (nome.length() < 3) {
            Toast.makeText(
                    this,
                    "Nome inválido. Mínimo de 3 caracteres.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double preco = 0.0;

        try {
            // Converte o preço informado para tipo double
            if (!precoStr.isEmpty()) {
                preco = Double.parseDouble(
                        precoStr.replace(",", ".")
                );
            }
        } catch (NumberFormatException e) {
            // Caso ocorra erro na conversão
            preco = 0.0;
        }

        // Validação do preço
        if (preco <= 0) {
            Toast.makeText(
                    this,
                    "Preço inválido. Informe um valor maior que zero.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Cria objeto Produto com os dados informados
        Produto novoProduto = new Produto(nome, preco);

        // Insere o produto no banco de dados
        long id = dbHelper.inserirProduto(novoProduto);

        // Verifica se a inserção ocorreu com sucesso
        if (id != -1) {

            Toast.makeText(
                    this,
                    "Produto salvo com sucesso!",
                    Toast.LENGTH_SHORT
            ).show();

            // Limpa os campos para novo cadastro
            editNome.setText("");
            editPreco.setText("");

            // Retorna o foco para o campo nome
            editNome.requestFocus();

            // Atualiza a lista exibida na tela
            atualizarListaProdutos();

        } else {

            Toast.makeText(
                    this,
                    "Erro ao salvar no banco de dados.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Recupera todos os produtos do banco de dados
     * e atualiza a ListView.
     */
    private void atualizarListaProdutos() {

        // Busca os produtos cadastrados
        listaProdutos = dbHelper.listarProdutos();

        // Cria o adaptador para exibição da lista
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaProdutos
        );

        // Associa o adaptador à ListView
        listViewProdutos.setAdapter(adapter);
    }
}