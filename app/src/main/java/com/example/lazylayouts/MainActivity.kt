package com.example.lazylayouts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lazylayouts.ui.theme.LazyLayoutsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


// Activity principal que configura o tema e layout da aplicação com abas.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyLayoutsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Chama a tela principal que permite alternar entre Column/Row/Grid.
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Modelo simples que representa um produto com id, nome, preço e URL da imagem.
data class Produto(val id: Int, val nome: String, val preco: Double, val imagem: String)

// Estado imutável que encapsula a lista de produtos da UI.
data class ProdutoState(val produtos: List<Produto> = emptyList())

// Enumeração que define os tipos de visualização disponíveis (Coluna, Linha, Grade).
enum class ViewType { COLUMN, ROW, GRID }

class ProdutoViewModel : ViewModel() {
    // StateFlow público que expõe o estado atual da lista de produtos.
    private val _state = MutableStateFlow(ProdutoState())
    val state: StateFlow<ProdutoState> = _state

    // Carrega dados ao inicializar o ViewModel.
    init {
        gerarProdutos()
    }

    // Gera uma lista de exemplo de 60 produtos com dados aleatórios.
    private fun gerarProdutos() {
        val listaProdutos = List(60) {
            Produto(
                id = it,
                nome = "Produto $it",
                preco = (10..100).random().toDouble(),
                imagem = "https://picsum.photos/200?random=$it"
            )
        }
        _state.value = ProdutoState(produtos = listaProdutos)
    }
}

@Composable
// Componente que exibe uma card com a imagem, nome e preço de um produto.
fun ProdutoCard(produto: Produto, onCLick: (Produto) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {onCLick(produto)}
    ) {
        Row() {
            AsyncImage(
                model = produto.imagem,
                contentDescription = produto.nome,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(60.dp))
            )
            Column() {
                Text(
                    "Nome: ${produto.nome}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                Text(
                    "Valor: ${produto.preco}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }

    }
}

@Composable
// Lista vertical que exibe produtos em uma coluna usando LazyColumn.
fun ListaSimples(modifier: Modifier = Modifier, viewModel: ProdutoViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val contexto = LocalContext.current

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            state.produtos,
            key = { produto -> produto.id }
        )
        { produto ->
            ProdutoCard(produto, onCLick = {
                Toast.makeText(
                    contexto,
                    "Clicou em ${produto.nome}",
                    Toast.LENGTH_LONG).show()
            })
        }
    }

}

@Composable
// Lista horizontal que exibe produtos em uma linha usando LazyRow.
fun ListaRow(modifier: Modifier = Modifier, viewModel: ProdutoViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val contexto = LocalContext.current

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.produtos, key = { it.id }) { produto ->
            ProdutoCard(produto, onCLick = {
                Toast.makeText(contexto, "Clicou em ${it.nome}", Toast.LENGTH_LONG).show()
            })
        }
    }
}

@Composable
// Grade que exibe produtos em 2 colunas usando LazyVerticalGrid.
fun ListaGrid(modifier: Modifier = Modifier, viewModel: ProdutoViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val contexto = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.produtos, key = { it.id }) { produto ->
            ProdutoCard(produto, onCLick = {
                Toast.makeText(contexto, "Clicou em ${it.nome}", Toast.LENGTH_LONG).show()
            })
        }
    }
}

@Composable
// Tela principal que permite alternar entre Coluna, Linha e Grade usando abas simples.
fun MainScreen(modifier: Modifier = Modifier, viewModel: ProdutoViewModel = viewModel()) {
    // Estado local para controlar qual aba está selecionada.
    val selected = remember { mutableStateOf<ViewType>(ViewType.COLUMN) }

    Column(modifier = modifier) {
        // Barra de abas simples
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            // Aba "Coluna"
            val isColumn = selected.value == ViewType.COLUMN
            Text(
                text = "Coluna",
                fontWeight = if (isColumn) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .clickable { selected.value = ViewType.COLUMN }
                    .background(if (isColumn) androidx.compose.ui.graphics.Color.LightGray else androidx.compose.ui.graphics.Color.Transparent)
                    .padding(8.dp)
            )

            // Aba "Linha"
            val isRow = selected.value == ViewType.ROW
            Text(
                text = "Linha",
                fontWeight = if (isRow) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .clickable { selected.value = ViewType.ROW }
                    .background(if (isRow) androidx.compose.ui.graphics.Color.LightGray else androidx.compose.ui.graphics.Color.Transparent)
                    .padding(8.dp)
            )

            // Aba "Grade"
            val isGrid = selected.value == ViewType.GRID
            Text(
                text = "Grade",
                fontWeight = if (isGrid) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .clickable { selected.value = ViewType.GRID }
                    .background(if (isGrid) androidx.compose.ui.graphics.Color.LightGray else androidx.compose.ui.graphics.Color.Transparent)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Conteúdo que muda conforme a aba selecionada
        when (selected.value) {
            ViewType.COLUMN -> ListaSimples(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
            ViewType.ROW -> ListaRow(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
            ViewType.GRID -> ListaGrid(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(60) { index -> Text("Item $index") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyLayoutsTheme {
        // Preview mostrando a tela principal com as abas
        MainScreen()
    }
}

