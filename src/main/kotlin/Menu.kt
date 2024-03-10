// Класс Menu реализован по паттерну проектирования Singleton


// Приватный конструктор
class Menu private constructor() {
    companion object {
        // Объект одиночки
        private val menu: MutableList<Dish> = mutableListOf(
            Dish("Плов", 20, 300, 15),
            Dish("Борщ", 10, 250, 20),
            Dish("Хлеб", 50, 10, 1),
            Dish("Чай", 100, 50, 2)
        )


        // Метод, управляющий доступом к экземпляру одиночки
        fun getInstance(): MutableList<Dish> {
            return menu
        }


        // Вывод меню
        fun printMenu(choice: Boolean): Int {
            println(" Меню:\tВремя:\tОсталось:\tЦена: \t")
            menu.forEachIndexed { index, item ->
                println("${index + 1}.${item.name}\t${item.duration}\t\t${item.amount}\t\t${item.price}")
            }
            if (!choice) {
                return 0
            }
            // Если choice == true, то надо выбрать пункт меню
            return Functions.inputInt("Выберите пункт меню: ",1,  menu.size)
        }
    }
}