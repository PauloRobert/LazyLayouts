# Aula 3 - Listas com LazyColumn, LazyRow e LazyGrid no Jetpack Compose

Este projeto foi desenvolvido durante a **Aula 3** da disciplina, com o objetivo de praticar o uso de listas no Android com **Jetpack Compose**. Nessa aula, o foco foi entender como criar interfaces modernas, organizadas e mais performáticas usando componentes `Lazy`.

O projeto mostra na prática como trabalhar com listas verticais, horizontais e em grade, além de usar `ViewModel`, `StateFlow`, `Toast` e carregamento de imagens com `Coil`.

## O que eu aprendi nessa aula

Durante essa aula, eu aprendi que as listas `Lazy` são muito importantes no Android porque elas **renderizam somente os itens que aparecem na tela**. Isso ajuda bastante na performance do aplicativo.

Também aprendi a:

- organizar melhor a arquitetura da tela usando `ViewModel`;
- criar um estado para guardar os dados da interface;
- exibir listas dinâmicas com dados gerados pelo app;
- montar cards reutilizáveis para mostrar os produtos;
- alternar entre diferentes tipos de layout com abas simples;
- usar `Toast` para dar retorno ao clicar em um item;
- carregar imagens da internet com `Coil`.

## Conceitos trabalhados

### 1. `LazyColumn`
Foi usado para exibir uma lista vertical de produtos. Esse componente é ideal para feeds, listas de itens e telas com rolagem para baixo.

### 2. `LazyRow`
Foi usado para exibir uma lista horizontal. Esse tipo de lista é muito útil para carrosséis, destaques e sugestões.

### 3. `LazyVerticalGrid`
Foi usado para mostrar os produtos em formato de grade com duas colunas. Esse layout é bom para catálogos e telas com muitos itens visuais.

### 4. `ViewModel`
Foi utilizado para separar a lógica de dados da interface. Assim, a tela fica mais organizada e segue uma arquitetura melhor para o Android moderno.

### 5. `StateFlow`
Foi usado para armazenar o estado da lista de produtos e atualizar a interface de forma reativa.

### 6. `Toast`
Foi usado para mostrar uma mensagem quando o usuário clica em um card de produto.

### 7. `Coil`
Foi usado para carregar imagens diretamente de uma URL e exibi-las nos cards.

### 8. Lista dinâmica
A lista de produtos foi criada de forma dinâmica no `ViewModel`, gerando vários itens automaticamente para testar os layouts.

### 9. Performance com listas `Lazy`
O principal aprendizado foi entender que as listas `Lazy` ajudam a melhorar a performance porque não carregam todos os elementos ao mesmo tempo.

## O que foi produzido no projeto

Neste projeto foi construída uma tela com uma pequena navegação por abas:

- **Coluna**: mostra os produtos em uma lista vertical;
- **Linha**: mostra os produtos em uma lista horizontal;
- **Grade**: mostra os produtos em duas colunas.

Cada produto é exibido em um card com:

- imagem;
- nome;
- preço;
- ação ao clicar.

Quando o usuário toca em um card, aparece um `Toast` informando qual produto foi clicado.

## Estrutura principal do projeto

A parte principal da aplicação está em `MainActivity.kt`, onde foram criados:

- o `MainActivity`;
- o modelo `Produto`;
- o estado `ProdutoState`;
- o `ProdutoViewModel`;
- os componentes `ProdutoCard`, `ListaSimples`, `ListaRow`, `ListaGrid` e `MainScreen`.

## Dependências utilizadas

As principais dependências adicionadas no projeto foram:

```toml
[versions]
coil = "2.5.0"
lifecycle = "2.7.0"

[libraries]
coil-compose = { module = "io.coil-kt:coil-compose", version.ref = "coil" }
lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycle" }
```

E no `build.gradle.kts`:

```kotlin
implementation(libs.coil.compose)
implementation(libs.lifecycle.viewmodel.compose)
```

### Para que servem essas dependências?

- **Coil**: ajuda a carregar imagens da internet dentro da interface Compose;
- **Lifecycle ViewModel Compose**: permite usar `ViewModel` de forma integrada com Jetpack Compose.

## Como executar o projeto

1. Abra o projeto no **Android Studio**.
2. Aguarde a sincronização do Gradle.
3. Conecte um celular ou abra um emulador.
4. Execute o app com **Run**.

## Resultado final

Ao final da aula, foi criado um aplicativo que demonstra três formas diferentes de exibir listas no Jetpack Compose, usando uma estrutura mais organizada com `ViewModel` e estado reativo.

Esse projeto ajudou a entender melhor como montar telas modernas no Android de forma simples, reutilizável e performática.

## Observação

Este projeto foi feito como atividade de estudo da **Aula 3** e serve como base para praticar o uso de listas no Jetpack Compose.

