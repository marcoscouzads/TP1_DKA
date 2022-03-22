package br.com.marcoscsouza.listadeprodutoscrud.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.marcoscsouza.listadeprodutoscrud.databinding.ActivityMainBinding
import br.com.marcoscsouza.listadeprodutoscrud.db.AppDatabase
import br.com.marcoscsouza.listadeprodutoscrud.rv.ProdutosAdapter


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter = ProdutosAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        rvProdutos()
        botaoFab()

    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.buscaTodos())
    }



    private fun rvProdutos(){
        val rv = binding.rvLista
        rv.adapter = adapter
        adapter.ClicaNoItem = {
            val i = Intent(
                this,
                DetalhesActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(i)
        }
    }

    private fun botaoFab() {
        val fab = binding.fabProduto
        fab.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

}