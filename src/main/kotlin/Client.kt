import kotlinx.coroutines.*


// Класс клиента
class Client(val username: String, val password: String): IClient {
    private var status: Status = Status.EMPTY // Статус заказа
    private val order: Order = Order()


    // Составление заказа
    override fun makeOrder(){
        if (order.list.isNotEmpty()) {
            println("У вас уже составлен заказ")
            println("Вы можете добавить блюдо к своему текущему заказу через 2 пункт меню")
            return
        }
        addToList()
    }


    // Добавление блюда к заказу
    override fun addToOrder() {
        if (order.list.isEmpty()) {
            println("Вы пока что ничего не заказывали")
            println("Вы можете заказать блюдо через 1 пункт меню")
            return
        }
        addToList()
    }


    // Отмена заказа
    override fun cancel() {
        if (order.list.isEmpty()) {
            println("Пока нет заказа")
            return
        }
        clearAll()
        println("Заказ отменен")
    }


    // Оплата заказа
    override fun pay() {
        // Определяем статус заказа
        findStatus()
        when (status) {
            Status.EMPTY -> println("Пока нет заказа")
            Status.GETTING_READY -> println("Заказ готовится")
            // Заказ оплачивается только при полной готовности
            Status.READY -> {
                println("Заказ готов")
                var s = 0
                for (i in 0..<order.list.size) {
                    s += order.list[i].price
                }
                println("С вас $s")
                val choice = Functions.printList("Оплатить?", mutableListOf("Да", "Нет"))
                if (choice == 1) {
                    Admin.sum += s
                    println("Заказ оплачен")
                    // Стираем данные о текущем заказе
                    clearAll()
                    println("Спасибо за визит!")
                }
            }
        }
    }


    // Изменяем статус заказа
    fun printStatus() {
        findStatus()
        if (status == Status.EMPTY) {
            println("Вы пока ничего не заказывали")
            return
        }
        for (i in order.list) {
            when (i.status) {
                Status.EMPTY -> println("Блюдо ${i.name} не было заказано")
                Status.GETTING_READY -> println("Блюдо ${i.name} все еще готовится")
                Status.READY -> println("Блюдо ${i.name} уже готово")
            }
        }
    }


    // Стирание всех данных о заказе
    private fun clearAll() {
        for (i in order.jobs) {
            // Отмена корутин
            i.cancel()
        }
        order.list.clear()
        order.jobs.clear()
        this.status = Status.EMPTY
    }


    // Выбор блюда для готовки
    private fun addToList() {
        val m = Menu.getInstance()
        var a = Menu.printMenu(true)
        a -= 1
        if (m[a].amount <= 0) {
            println("К сожалению данное блюдо закончилось")
            println("Вы можете выбрать что-нибудь другое")
            return
        }
        // Создаем новый экземпляр блюда и добавляем его в заказ
        val d = Dish(m[a].name, 1, m[a].price, m[a].duration)
        order.list.add(d)
        m[a].amount -= 1
        println("Заказ принят")
        if (d.duration == 1) {
            println("Блюдо ${d.name} будет готово в течение 1 минуты")
        }
        else {
            println("Блюдо ${d.name} будет готово в течение ${d.duration} минут")
        }
        d.status = Status.GETTING_READY

        // Готовка блюда
        CoroutineScope(Dispatchers.IO).launch {
            cooking(d)
        }
    }


    // Многопоточная функция готовки
    private suspend fun cooking(a: Dish) = coroutineScope{
        val job = CoroutineScope(Dispatchers.IO).launch{
            delay((a.duration * 60 * 1000).toLong())
            a.status = Status.READY
        }
        order.jobs.add(job)
    }


    // Определяет статус всего заказа
    private fun findStatus() {
        if (order.list.isEmpty()) {
            status = Status.EMPTY
            return
        }
        for (i in order.list) {
            if (i.status == Status.GETTING_READY) {
                status = Status.GETTING_READY
                return
            }
        }
        status = Status.READY
    }
}