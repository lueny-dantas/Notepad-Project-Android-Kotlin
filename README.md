# Notepad-Project-Android-Kotlin
App que permite criar, alterar, remover e visualizar notas em uma lista no formato de grid.

# 🔨 Funcionalidades do projeto
- O Notepad permite criar notas com título, descrição e imagem.
- Dados são atualizados de forma assíncrona, todas as alterações realizadas, são armazenadas localmente e assim que existir conexão com a intert os dados são enviados para uma Mock Web API (local), entenda como configurar a API [aqui](www.google.com).

# ✨ Ilustração
![ezgif com-gif-maker (4)](https://user-images.githubusercontent.com/98789294/210692082-26ac85f6-aaf7-4081-a0f3-83e17aeaf2af.gif)


# ✔️ Técnicas e tecnologias utilizadas

* Room: armazenar os dados em banco de dados;
  - Migrations: modificar a estrutura atual do banco de dados para atender novas necessidades;
* Retrofit: cliente HTTP para realizar requisições Web via HTTP;
* Coroutines: realizar operações assíncronas, como acesso ao banco de dados ou comunicação HTTP;
* Centralização do acesso ao banco de dados e API através da classe Repository.
* UUID: gerar ids "únicos" e descentralizados;
* flags: sinalizações para identificar estados de objetos, como ativos/desativados;
* Coil: carregar imagens a partir de URLs;
* View Binding: buscar views do layout de forma segura.
* RecyclerView: listagem das notas em grid;
* ConstraintLayout: ViewGroup para implementar layouts;

# 🛠️ Abrir e rodar o projeto
Após baixar o projeto, você pode abrir com o Android Studio. Para isso, na tela de launcher clique em:

* Open an Existing Project (ou alguma opção similar)
* Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo)
* Por fim clique em OK

O Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App.
