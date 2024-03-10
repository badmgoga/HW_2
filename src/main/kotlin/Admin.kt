import java.util.*

class Admin(val username: String, val password: String): IAdmin {
    private val scanner = Scanner(System.`in`)

    companion object {
        // Выручка
        var sum: Int = 0
    }


    // Добавление блюда
    override fun add() {
        val m = Menu.getInstance()

        println("Название блюда: ")
        val name = scanner.nextLine()
        val amount = Functions.inputInt("Количество доступных блюд: ")
        val price = Functions.inputInt("Цена блюда: ")
        val duration = Functions.inputInt("Длительность готовки блюда (в минутах): ")

        m.add(Dish(name, amount, price, duration))
        println("Добавлено новое блюдо")
    }


    // Изменение блюда
    override fun change() {
        val m = Menu.getInstance()

        // Выбор блюда для изменения
        val item: Int = Menu.printMenu(true)
        val name = m[item - 1].name
        println("Название блюда: $name")
        val amount = Functions.inputInt("Количество доступных блюд: ", min=0)
        val price = Functions.inputInt("Цена блюда: ")
        val duration = Functions.inputInt("Длительность готовки блюда (в минутах): ")

        m[item - 1] = Dish(name, amount, price, duration)
        println("Изменено блюдо $name")
    }


    // Удаление блюда
    override fun delete() {
        val m = Menu.getInstance()

        val item: Int = Menu.printMenu(true)
        val name = m[item - 1].name

        m.removeAt(item - 1)
        println("Удалено блюдо $name")
    }
}