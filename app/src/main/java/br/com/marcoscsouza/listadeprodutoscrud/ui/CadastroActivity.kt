package br.com.marcoscsouza.listadeprodutoscrud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.marcoscsouza.listadeprodutoscrud.R
import br.com.marcoscsouza.listadeprodutoscrud.databinding.ActivityCadastroBinding
import br.com.marcoscsouza.listadeprodutoscrud.db.AppDatabase
import br.com.marcoscsouza.listadeprodutoscrud.db.Produto
import br.com.marcoscsouza.listadeprodutoscrud.db.ProdutoDao

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"

        val btSalvar = binding.btSalvar

        btSalvar.setOnClickListener {
            val novoProduto = produtoCriado()
            produtoDao.salva(novoProduto)
            finish()
        }
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

    }

    override fun onResume() {
        super.onResume()

        produtoDao.buscaPorId(produtoId)?.let {
            title = "Editar Produto"
            binding.cadastroProdutoNome.setText(it.nome)
            binding.cadastroProdutoDescricao.setText(it.descricao)
        }
    }

    private fun produtoCriado(): Produto {

        val campoNome = binding.cadastroProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.cadastroProdutoDescricao
        val descricao = campoDescricao.text.toString()

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao
        )
    }




}