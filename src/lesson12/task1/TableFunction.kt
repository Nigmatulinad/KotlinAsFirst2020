@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import java.lang.Double.MAX_VALUE
import java.lang.Double.MIN_VALUE
import kotlin.math.abs

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
    private val table = mutableMapOf<Double, Double>()

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
        return if (table.containsKey(x)) {
            table + (x to y)
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
    fun remove(x: Double): Boolean {
        return if (!table.containsKey(x)) false
        else {
            table.remove(x)
            true
        }
    }

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
        if (table.isEmpty()) throw IllegalStateException()
        var difference = MAX_VALUE
        var actual = MIN_VALUE
        for ((arg) in table) {
            if (abs(x - arg) <= difference) {
                difference = abs(x - arg)
                if (actual < arg) actual = arg
            }
        }
        return actual to table[actual]!!
    }

    fun closest(list: List<Double>, x: Double): Pair<Double, Double> {
        var min = MAX_VALUE
        var max = MIN_VALUE
        for (i in list.indices) {
            if (list[i] < x && abs(list[i] - x) < min) min = list[i]
            if (list[i] > x && abs(list[i] - x) < max) max = list[i]
        }
        return min to max
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
        val list = table.toList()
        when {
            (table.isEmpty()) -> throw IllegalStateException()
            (table.containsKey(x)) -> return table[x]!!
            (table.size == 1) -> return list[0].second
        }
        val args = mutableListOf<Double>()
        var more = 0
        var less = 0
        for ((first) in list) args + first
        args.sortedBy { it }
        for ((el) in list) {
            if (el > x) more++
            else less++
        }
        val x1 = closest(args, x).first
        val x2 = closest(args, x).second
        return when {
            less == 0 ->
                table[x2]!! + (x - args[args.size - 2]) * (table[args[args.size - 1]]!! - table[args[args.size - 2]]!!) / (args[args.size - 2] - args[args.size - 1])

            more == 0 -> table[args[0]]!! + (x - args[1]) * (table[args[0]]!! - table[args[1]]!!) / (args[0] - args[1])

            else -> table[x1]!! + (x - x1) * (table[x2]!! - table[x1]!!) / (x1 - x2)
        }
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TableFunction) return false
        return this.table.hashCode() == other.table.hashCode()
    }

    override fun hashCode(): Int = javaClass.hashCode()
}