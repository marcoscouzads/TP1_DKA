package br.com.marcoscsouza.listadeprodutoscrud.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.marcoscsouza.listadeprodutoscrud.R
import br.com.marcoscsouza.listadeprodutoscrud.databinding.ActivityDetalhesBinding
import br.com.marcoscsouza.listadeprodutoscrud.db.AppDatabase
import br.com.marcoscsouza.listadeprodutoscrud.db.Produto

class DetalhesActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

        val btEditar = binding.btEditar
        btEditar.setOnClickListener {

            Intent(this, CadastroActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID,produtoId)
                startActivity(this)
            }
        }

        val btDeletar = binding.btDeletar
        btDeletar.setOnClickListener {
            produto?.let { produtoDao.remove(it) }
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        produto = produtoDao.buscaPorId(produtoId)
        produto?.let {
            with(binding){
                activityDetalhesProdutoNome.text = it.nome
                activityDetalhesProdutoDescricao.text = it.descricao
            }

        } ?: finish()
    }

}