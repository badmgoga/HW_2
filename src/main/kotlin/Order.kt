import kotlinx.coroutines.Job

// Класс заказа
class Order {
    // Список блюд
    val list: MutableList<Dish> = mutableListOf()
    // Список Jobs
    val jobs: MutableList<Job> = mutableListOf()
}