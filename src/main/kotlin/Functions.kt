import java.util.*

// Функции, использующиеся в программе не один раз
class Functions {
    companion object {
        private val scanner = Scanner(System.`in`)

        // Выводит список, из которого надо выбрать один пункт
        fun printList(message: String, list: MutableList<String>): Int {
            println(message)
            list.forEachIndexed { index, item ->
                println("${index + 1}.$item")
            }
            return inputInt("Выберите пункт: ", min=1, max = list.size)
        }


        // Ввод неотрицательного числа
        fun inputInt(message: String = "", min:Int = 1, max:Int = Int.MAX_VALUE): Int{
            while (true) {
                try {
                    if (message != "") {
                        println(message)
                    }
                    val a = scanner.nextLine()
                    val number: Int = a.toInt()
                    if (number in min..max) {
                        return number
                    }
                    else if (number > max) {
                        println("Вероятно, вы выбрали пункт, которого нет ")
                    }
                    else if (number < 0){
                        println("Вероятно, вы ввели неположительное число")
                    }
                } catch (e: Exception) {
                    println("Вероятно, вы ввели недопустимый формат данных ")
                }
            }
        }
    }
}