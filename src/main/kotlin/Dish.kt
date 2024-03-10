// Класс блюд

data class Dish (var name: String,
                 var amount: Int,
                 var price: Int,
                 var duration: Int,
                 var status: Status = Status.EMPTY)