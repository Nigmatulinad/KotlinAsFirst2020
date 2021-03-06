@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {
    private val table = mutableMapOf<Double, Double>().toSortedMap()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        return if (!table.containsKey(x)) {
            table += (x to y)
            true
        } else {
            table[x] = y
            false
        }
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean = table.remove(x) != null


    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table.toList()

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        val list = table.toList()
        if (list.isEmpty()) throw IllegalStateException()
        val y = table[x]
        if (list.size == 1) return list[0]
        if (y != null) return x to y
        if (x < list[0].first) return list[0]
        if (x > list.last().first) return list.last()
        val (left, right) = find(list, x)
        return if (right.first - x < x - left.first) right else left
    }

    private fun find(list: List<Pair<Double, Double>>, x: Double): Pair<Pair<Double, Double>, Pair<Double, Double>> {
        var ans = (0.0 to 0.0) to (0.0 to 0.0)
        for (i in 1 until list.size) {
            if (x < list[i].first) {
                ans = list[i - 1] to list[i]
                break
            }
        }
        return ans
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        val list = table.toList().toMutableList()
        val y = table[x]
        when {
            (table.isEmpty()) -> throw IllegalStateException()
            (y != null) -> return y
            (table.size == 1) -> return list[0].second
        }
        return when {
            x < list[0].first -> list[1].second + (x - list[1].first) * (list[0].first - list[1].first) / (list[0].first - list[1].first)
            x > list.last().first -> {
                list.reverse()
                list[1].second + (x - list[1].first) * (list[0].first - list[1].first) / (list[0].first - list[1].first)
            }
            else -> {
                val (x1, x2) = find(list, x)
                x1.second + (x - x2.first) * (x1.second - x2.second) / (x2.first - x1.first)
            }
        }
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TableFunction) return false
        return this.table == other.table
    }

    override fun hashCode(): Int = table.hashCode()
}