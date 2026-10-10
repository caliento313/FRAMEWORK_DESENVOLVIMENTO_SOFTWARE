package com.example.persistncia_dados_sqlite_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento do banco de dados SQLite.
 * Ela cria a tabela de produtos e fornece métodos para
 * inserir e consultar registros.
 */
public class ProdutoDbHelper extends SQLiteOpenHelper {

    // Nome do arquivo do banco de dados
    private static final String DATABASE_NAME = "produtos.db";

    // Versão atual do banco
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela
    public static final String TABLE_PRODUTOS = "produtos";

    // Nome das colunas da tabela
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PRECO = "preco";

    /**
     * Comando SQL responsável pela criação da tabela.
     * A coluna ID é auto incremental e funciona como
     * chave primária da tabela.
     */
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT NOT NULL, " +
                    COLUMN_PRECO + " REAL NOT NULL" +
                    ");";

    /**
     * Construtor da classe.
     *
     * @param context Contexto da aplicação.
     */
    public ProdutoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Executado automaticamente quando o banco é criado
     * pela primeira vez.
     *
     * @param db Instância do banco SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    /**
     * Executado quando a versão do banco é alterada.
     * Remove a tabela antiga e cria uma nova.
     *
     * @param db Instância do banco SQLite.
     * @param oldVersion Versão anterior.
     * @param newVersion Nova versão.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);

        // Recria a estrutura do banco
        onCreate(db);
    }

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param produto Produto a ser cadastrado.
     * @return ID do registro inserido ou -1 em caso de erro.
     */
    public long inserirProduto(Produto produto) {

        // Obtém acesso para escrita no banco
        SQLiteDatabase db = this.getWritableDatabase();

        // Estrutura utilizada para armazenar os valores
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOME, produto.getNome());
        values.put(COLUMN_PRECO, produto.getPreco());

        // Insere os dados na tabela
        long id = db.insert(TABLE_PRODUTOS, null, values);

        // Fecha a conexão com o banco
        db.close();

        return id;
    }

    /**
     * Recupera todos os produtos registrados no banco
     * e retorna uma lista de objetos Produto.
     *
     * @return Lista de produtos cadastrados.
     */
    public List<Produto> listarProdutos() {

        List<Produto> listaProdutos = new ArrayList<>();

        // Obtém acesso para leitura
        SQLiteDatabase db = this.getReadableDatabase();

        // Colunas que serão consultadas
        String[] columns = {
                COLUMN_ID,
                COLUMN_NOME,
                COLUMN_PRECO
        };

        // Executa a consulta ordenando pelo ID decrescente
        Cursor cursor = db.query(
                TABLE_PRODUTOS,
                columns,
                null,
                null,
                null,
                null,
                COLUMN_ID + " DESC"
        );

        // Verifica se existem registros
        if (cursor != null && cursor.moveToFirst()) {

            do {

                // Obtém os valores de cada coluna
                long id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(COLUMN_ID)
                );

                String nome = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_NOME)
                );

                double preco = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COLUMN_PRECO)
                );

                // Cria um objeto Produto com os dados recuperados
                Produto produto = new Produto(id, nome, preco);

                // Adiciona o produto à lista
                listaProdutos.add(produto);

            } while (cursor.moveToNext());

            // Fecha o cursor após a utilização
            cursor.close();
        }

        // Fecha a conexão com o banco
        db.close();

        return listaProdutos;
    }
}