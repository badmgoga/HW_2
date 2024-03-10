import java.util.*

private val scanner = Scanner(System.`in`)

// список всех админов
val admins : MutableList<Admin> = mutableListOf(Admin("admin", "LIKE A BOSS"))
// список всех посетителей
val clients : MutableList<Client> = mutableListOf(Client("client1", "2004"),
    Client("client2", "Хачу хача"), Client("Пароль", "Логин"))


fun main() {
    mainMenu()
}


// Главное меню
fun mainMenu() {
    // Список названий функций
    val list = mutableListOf("Админ", "Посетитель", "Выйти из приложения")
    while (true) {
        println("Добро пожаловать в систему управления заказами в ресторане")

        val choice = Functions.printList("Кто вы?", list)
        when (choice) {
            1 -> {
                println("Введите свои логин и пароль")
                print("Логин: ")
                val username = scanner.nextLine()
                print("Пароль: ")
                val password = scanner.nextLine()
                val admin = authenticateAdmin(username, password)
                if (admin == null) {
                    println("Ошибка аутентификации. Неверное имя пользователя или пароль.")
                    continue
                }
                println("Добро пожаловать, администратор ${admin.username}!")
                adminMenu(admin)
            }
            2 -> {
                entranceMenu()
            }
            else -> {
                print("Закрытие приложения...")
                break
            }
        }
    }
}


// Функция поиска админа с заданными логином и паролем
fun authenticateAdmin(username: String, password: String): Admin? {
    return admins.find { it.username == username && it.password == password }
}

// Функция поиска клиента с заданными логином и паролем
fun authenticateClient(username: String, password: String): Client? {
    return clients.find { it.username == username && it.password == password }
}

// Функция поиска клиента с заданным логином
fun authenticateClientName(username: String): Client? {
    return clients.find { it.username == username }
}


// Меню админа
fun adminMenu(admin: Admin) {
    val list = mutableListOf("Вывести меню", "Добавить блюдо", "Изменить блюдо", "Удалить блюдо", "Выручка", "Посетителей всего", "Выход из личного кабинета")
    while (true) {
        val choice = Functions.printList("Все функции:", list)
        when (choice) {
            1 -> {
                Menu.printMenu(false)
                // Требуем ввода 1, чтобы можно было рассмотреть меню
                Functions.inputInt("Выберите цифру 1, чтобы выйти в меню", 1, 1)
                continue
            }
            2 -> {
                admin.add()
                }
            3 -> {
                admin.change()
            }
            4 -> {
                admin.delete()
            }
            5 -> {
                println("Сумма выручки равна ${Admin.sum}")
            }
            6 -> {
                println("Пользователей в системе: ${clients.size}")
            }
            else -> {
                println("Выход...")
                return
            }
        }
    }
}


// Меню входа и регистрации
fun entranceMenu() {
    val list = mutableListOf("Регистрация", "Вход", "Выйти на главное меню")
    while (true) {
        val choice = Functions.printList("Все функции:", list)
        when (choice) {
            1 -> {
                println("Введите свои логин и пароль")
                print("Логин: ")
                val username = scanner.nextLine()
                val a = authenticateClientName(username)
                if (a != null) {
                    println("Ошибка аутентификации. Пользователь с именем $username уже зарегистрирован")
                    continue
                }
                print("Пароль: ")
                val password = scanner.nextLine()
                val client = Client(username, password)
                clients.add(client)
                println("Добро пожаловать, посетитель ${username}!")
                clientMenu(client)
                break
            }
            2 -> {
                println("Введите свои логин и пароль")
                print("Логин: ")
                val username = scanner.nextLine()
                print("Пароль: ")
                val password = scanner.nextLine()
                val a = authenticateClient(username, password)
                if (a == null) {
                    println("Ошибка аутентификации. Неверное имя пользователя или пароль.")
                    continue
                }
                println("Добро пожаловать, посетитель ${a.username}!")
                clientMenu(a)
                break
            }
            else -> {
                break
            }
        }
    }
}

// Меню клиента
fun clientMenu(client: Client) {
    val list = mutableListOf("Сделать заказ", "Добавить блюдо к заказу", "Отменить заказ", "Оплатить заказ", "Вывести заказ", "Выход из личного кабинета")
    while (true) {
        val choice = Functions.printList("Все функции:", list)
        when (choice) {
            1 -> {
                client.makeOrder()
            }
            2 -> {
                client.addToOrder()
            }
            3 -> {
                client.cancel()
            }
            4 -> {
                client.pay()
            }
            5 -> {
                client.printStatus()
            }
            6 -> {
                println("Выход...")
                return
            }
        }
    }
}
