
// Criar um sistema de gerenciamento de tarefas em Kotlin utilizando as seguintes tecnologias:
// Criar uma data class com as seguinte propriedades:
// - id: Int
// - title: String
// - description: String
// - isCompleted: Boolean
// - createdAt: DateTime
//
// deve se utilizar companion object para criar um id único para cada tarefa
//
// deve implementar  uma classe TaskManager que deve implementar as seguintes funcionalidades:
// - adicionar uma tarefa
// - listar todas as tarefas
// - buscar uma tarefa pelo id
// - atualizar status da tarefa
// - deletar uma tarefa pelo id
// - filtrar tarefas por status (concluída ou não)
//
// utilizar  require para validar os dados de entrada
// - titulo não pode ser vazio
// - A tarefa existe antes de realizar operações como atualizar ou excluir.(Não entendi)
//
// - A tarefa não pode ser concluída se já estiver concluída.
// utilize sealed classes para representar o status da tarefa
//  - Success
//  - Error
//  converter task para string formatada
// obter  a contagem de tarefas concluídas e não concluídas diretamente na lista de tarefas




fun main() {
 println("Bem-vindo ao sistema !")
}