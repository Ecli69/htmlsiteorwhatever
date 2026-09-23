// Task3.kt — Статус заказа (sealed class)

sealed class OrderStatus {
    object Pending : OrderStatus()
    data class Confirmed(val confirmedAt: String) : OrderStatus()
    data class Shipped(val trackingNumber: String) : OrderStatus()
    data class Delivered(val deliveredAt: String) : OrderStatus()
    data class Cancelled(val reason: String) : OrderStatus()
}

fun describeStatus(status: OrderStatus): String = when (status) {
    is OrderStatus.Pending -> "Заказ ожидает подтверждения"
    is OrderStatus.Confirmed -> "Заказ подтверждён ${status.confirmedAt}"
    is OrderStatus.Shipped -> "Заказ отправлен, трек-номер: ${status.trackingNumber}"
    is OrderStatus.Delivered -> "Заказ доставлен ${status.deliveredAt}"
    is OrderStatus.Cancelled -> "Заказ отменён. Причина: ${status.reason}"
}

fun main() {
    val statuses = listOf(
        OrderStatus.Pending,
        OrderStatus.Confirmed("2024-05-01 10:30"),
        OrderStatus.Shipped("RU123456789"),
        OrderStatus.Delivered("2024-05-05 18:00"),
        OrderStatus.Cancelled("Клиент передумал")
    )

    statuses.forEach { println(describeStatus(it)) }
}

/*
ПРОВЕРКА:
Если закомментировать одну ветку when, компилятор выдаст ошибку:
"'when' expression must be exhaustive. Add the 'is Cancelled' branch or an 'else' branch."
Это ключевая особенность sealed class — компилятор знает все варианты!
*/