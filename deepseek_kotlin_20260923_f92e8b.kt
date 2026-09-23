// Task2.kt — Магазин (data class)

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String = "general"
)

data class Order(
    val orderId: Int,
    val customerName: String,
    val items: List<Product>
)

fun Order.totalPrice(): Double = items.sumOf { it.price }

fun main() {
    val product1 = Product(1, "Хлеб", 45.0, "food")
    val product2 = Product(2, "Молоко", 80.0, "food")
    val product3 = Product(3, "Наушники", 3500.0, "electronics")
    val product4 = Product(4, "Ручка", 25.0)

    val order1 = Order(101, "Алексей", listOf(product1, product2, product4))
    val order2 = Order(102, "Мария", listOf(product3, product1))

    for (order in listOf(order1, order2)) {
        println("Заказ #${order.orderId}")
        println("Клиент: ${order.customerName}")
        println("Товары:")
        order.items.forEach { println("  $it") }
        println("Итого: ${order.totalPrice()} руб.")
        println("-".repeat(40))
    }

    // Акционная версия товара (скидка 20%)
    val discounted = product3.copy(price = product3.price * 0.8)
    println("Исходный товар:      $product3")
    println("Со скидкой 20%:      $discounted")
}