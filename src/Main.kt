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


data class Task(
    val id: Int,
    val title: String?,
    val description: String?,
    var isCompleted: Boolean?,
    val createdAt: String
)

fun Int?.isNullOrNegative(): Boolean {
    return this == null || this <= 0
}

fun String?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}


interface TaskInterface<T : Task> {
    fun addTask(task: Task)
    fun listAllTasks(): List<Task>
    fun taskById(id: Int): Task?
    fun updateTaskStatus(id: Int, isCompleted: Boolean)
    fun deleteTask(id: Int): Boolean
    fun filterTasksByStatus(isCompleted: Boolean): List<Task>
    fun countCompletedTasks(): Int
    fun countNotCompletedTasks(): Int
}


class TaskManager : TaskInterface<Task> {

    private val tasks = mutableListOf<Task>()

    companion object {
        private var idCounter = 1
        fun generateId(): Int {
            return idCounter++
        }
    }


    override fun addTask(task: Task) {
        tasks.add(task)
    }

    override fun listAllTasks(): List<Task> {
        return tasks.toList()
    }

    override fun taskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    override fun updateTaskStatus(id: Int, isCompleted: Boolean) {
        return tasks.find { it.id == id }?.let {
            if (it.isCompleted == true) {
                throw IllegalArgumentException("A tarefa já está concluída")
            } else {
                it.isCompleted = true
            }
        } ?: throw IllegalArgumentException("A tarefa não existe")

    }

    override fun deleteTask(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }

    override fun filterTasksByStatus(isCompleted: Boolean): List<Task> {
        return tasks.filter { it.isCompleted == true }
    }

    override fun countCompletedTasks(): Int {
        return tasks.count { it.isCompleted == true }
    }

    override fun countNotCompletedTasks(): Int {
        return tasks.count { it.isCompleted == false }
    }

}


fun formatDate(date: String): String {
    val dateInMillis = date.toLongOrNull()
    return if (dateInMillis != null) {
        val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        dateFormat.format(java.util.Date(dateInMillis))
    } else {
        "Data inválida"
    }
}


fun createTask(): Task {
    val id = TaskManager.generateId()


    var title: String? = null
    println("Digite o titulo da tarefa")
    while (title.isNullOrEmpty()) {
        print("Titulo:   ")
        title = readlnOrNull()
        if (title.isNullOrEmpty()) {
            println("O titulo não pode ser vazio")
        }
    }

    var description: String? = null
    println("Digite descrição da tarefa")
    while (description.isNullOrEmpty()) {
        print("descrição:  ")
        description = readlnOrNull()
        if (description.isNullOrEmpty()) {
            println("A descrição não pode ser vazia")
        }
    }

    var isCompleted: Boolean? = null
    println("Digite o status da tarefa (OK/NO)")
    while (isCompleted == null) {
        print("Status: ")
        val entrada = readlnOrNull()?.uppercase()

        isCompleted = when (entrada) {
            "OK" -> true
            "NO" -> false
            else -> {
                println("Entrada inválida! Digite OK ou NO.")
                null
            }
        }
    }



    val createdAt = formatDate(System.currentTimeMillis().toString())


    return Task(id, title, description, isCompleted, createdAt)


}


fun printTask(task: Task) {
    println(
        """
        +------------------------------------+--+--+
        | ID: ${task.id}                     |  |  |
        | Titulo: ${task.title}              |  |  |
        | Descrição: ${task.description}     |  |  |
        | Status: ${task.isCompleted}         |  |  |
        | Criado em: ${task.createdAt}       |  |  |
        +--------------------------------+--+--+
    """.trimIndent()
    )
}


fun printTasks(tasks: List<Task>) {
    tasks.forEach { task ->
        println(
            """
        +------------------------------------+--+--+
        | ID: ${task.id}                     |  |  |
        | Titulo: ${task.title}              |  |  |
        | Descrição: ${task.description}     |  |  |
        | Status: ${task.isCompleted}         |  |  |
        | Criado em: ${task.createdAt}       |  |  |
        +--------------------------------+--+--+
    """.trimIndent()
        )
    }
}


enum class action {
    DESCONHECIDA,
    ADD,
    UPDATE,
    DELETE,
    SEARCH,
    LIST,
    FILTER,
    COUNT_COMPLETED,
    COUNT_NOT_COMPLETED,
    EXIT
}


fun main() {

    val taskManager = TaskManager()

    var select: Int? = null

    while (select != action.EXIT.ordinal) {

        printTasks( taskManager.listAllTasks())

        println(
            """    +------------------------------------+--+--+
    | MENU TAREFAS                       |  |  |
    +------------------------------------+--+--+
    | 1 - ADICIONAR TAREFA               |  |  |
    | 2 - ATUALIZAR TAREFA               |  |  |
    | 3 - DELETAR TAREFA                 |  |  |
    | 4 - BUSCAR TAREFAS                 |  |  |
    | 5 - LISTAR TAREFAS                 |  |  |
    | 6 - FILTRAR TAREFAS                |  |  |
    | 7 - CONTAR TAREFAS CONCLUÍDAS      |  |  |
    | 8 - CONTAR TAREFAS NÃO CONCLUÍDAS  |  |  |
    | 9 - SAIR                           |  |  |
    +------------------------------------+--+--+
    """
        )

        println("Selecione uma opção:")
        print("->   ")
        select = readlnOrNull()?.toIntOrNull()



        when (select) {
            action.ADD.ordinal -> {
                val task = createTask()
                taskManager.addTask(task)
                println("Tarefa adicionada com sucesso!")
            }

            action.LIST.ordinal -> {
                val tasks = taskManager.listAllTasks()
                if (tasks.isNotEmpty()) {
                    println("Lista de tarefas:")
                    printTasks(tasks)
                } else {
                    println("Nenhuma tarefa encontrada.")
                }
            }

            action.SEARCH.ordinal -> {
                println("Digite o ID da tarefa:")
                print("->   ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id != null) {
                    val task = taskManager.taskById(id)
                    if (task != null) {
                        println("Tarefa encontrada:")
                        printTask(task)
                    } else {
                        println("Tarefa não encontrada.")
                    }
                } else {
                    println("ID inválido.")
                }
            }

            action.UPDATE.ordinal -> {
                println("Digite o ID da tarefa a ser atualizada:")
                print("->   ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id != null) {
                    println("Digite o novo status da tarefa (OK/NO):")
                    print("->   ")
                    val status = readlnOrNull()
                    if (status == "OK") {
                        taskManager.updateTaskStatus(id, true)
                        println("Tarefa atualizada com sucesso!")
                    } else if (status == "NO") {
                        taskManager.updateTaskStatus(id, false)
                        println("Tarefa atualizada com sucesso!")
                    } else {
                        println("Status inválido.")
                    }
                } else {
                    println("ID inválido.")
                }
            }

            action.DELETE.ordinal -> {
                println("Digite o ID da tarefa a ser deletada:")
                print("->   ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id != null) {
                    if (taskManager.deleteTask(id)) {
                        println("Tarefa deletada com sucesso!")
                    } else {
                        println("Tarefa não encontrada.")
                    }
                } else {
                    println("ID inválido.")
                }
            }

            action.FILTER.ordinal -> {
                println("Digite o status da tarefa (OK/NO):")
                print("->   ")
                val status = readlnOrNull()
                if (status == "OK") {
                    val tasks = taskManager.filterTasksByStatus(true)
                    if (tasks.isNotEmpty()) {
                        println("Tarefas concluídas:")
                        printTasks(tasks)
                    } else {
                        println("Nenhuma tarefa concluída encontrada.")
                    }
                } else if (status == "NO") {
                    val tasks = taskManager.filterTasksByStatus(false)
                    if (tasks.isNotEmpty()) {
                        println("Tarefas não concluídas:")
                        printTasks(tasks)
                    } else {
                        println("Nenhuma tarefa não concluída encontrada.")
                    }
                } else {
                    println("Status inválido.")
                }
            }

            action.COUNT_COMPLETED.ordinal -> {
                val count = taskManager.countCompletedTasks()
                println("Total de tarefas concluídas: $count")
            }

            action.COUNT_NOT_COMPLETED.ordinal -> {
                val count = taskManager.countNotCompletedTasks()
                println("Total de tarefas não concluídas: $count")
            }

            action.EXIT.ordinal -> {
                println("Saindo do sistema...")
                break
            }

            else -> {
                println("Opção inválida. Tente novamente.")
            }


        }


    }


}