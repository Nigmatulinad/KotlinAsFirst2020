@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.digitNumber
import lesson3.task1.revert
import javax.lang.model.element.NestingKind
import kotlin.math.sqrt

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = TODO()

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size


/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> = TODO()

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int = TODO()

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty()) return 0
    var xN = x
    var sum = p[0]
    for (i in 1 until p.size) {
        sum += p[i] * xN
        xN *= x
    }
    return sum
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isEmpty()) return list
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val list = mutableListOf<Int>()
    var divider = 2
    var number = n
    while (number != 1) {
        if (number % divider == 0) {
            list.add(divider)
            number /= divider
            divider = 2
        } else divider++
    }
    return list
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var number = n
    while (number != 0) {
        list += number % base
        number /= base
    }
    return list.reversed()
}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val digit = convert(n, base)
    val latin = "abcdefghijklmnopqrstuvwxyz"
    val ans = StringBuilder()
    for (el in digit) {
        if (el < 10) ans.append(el.toString())
        else ans.append(latin[el - 10].toString())
    }
    return ans.toString()
}

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var ans = digits.last()
    var sepung = base
    if (digits.size == 1) return digits[0]
    for (i in 1 until digits.size - 1) sepung *= base
    for (i in 1 until digits.size) {
        ans += digits[i] * sepung
        sepung /= base
    }
    return ans
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var ans = ""
    var number = ""
    var m = n
    while (m > 0) {
        number = when (m > 0) {
            m >= 1000 -> "M"
            m >= 900 -> "CM"
            m >= 500 -> "D"
            m >= 400 -> "CD"
            m >= 100 -> "C"
            m >= 90 -> "XC"
            m >= 50 -> "L"
            m >= 40 -> "XL"
            m >= 10 -> "X"
            m >= 9 -> "IX"
            m >= 5 -> "V"
            m >= 4 -> "IV"
            else -> "I"
        }
        ans += number
        m -= when (m > 0) {
            m >= 1000 -> 1000
            m >= 900 -> 900
            m >= 500 -> 500
            m >= 400 -> 400
            m >= 100 -> 100
            m >= 90 -> 90
            m >= 50 -> 50
            m >= 40 -> 40
            m >= 10 -> 10
            m >= 9 -> 9
            m >= 5 -> 5
            m >= 4 -> 4
            else -> 1
        }
    }
    return ans
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var russian = ""
    val first = n / 1000
    val last = n % 1000
    if (n > 999) {
        when (first / 100) {
            0 -> russian += ""
            1 -> russian += "сто"
            2 -> russian += "двести"
            3 -> russian += "триста"
            4 -> russian += "четыреста"
            5 -> russian += "пятьсот"
            6 -> russian += "шестьсот"
            7 -> russian += "семьсот"
            8 -> russian += "восемьсот"
            9 -> russian += "девятьсот"
        }
        if (first % 100 != 0 && first / 100 != 0) russian += " "
        if (first % 100 in 10..19) when (first % 100) {
            10 -> russian += "десять тысяч"
            11 -> russian += "одиннадцать тысяч"
            12 -> russian += "двенадцать тысяч"
            13 -> russian += "тринадцать  тысяч"
            14 -> russian += "четырнадцать тысяч"
            15 -> russian += "пятнадцать тысяч"
            16 -> russian += "шестнадцать тысяч"
            17 -> russian += "семнадцать тысяч"
            18 -> russian += "восемнадцать тысяч"
            19 -> russian += "девятнадцать тысяч"
        } else {
            when ((first / 10) % 10) {
                0 -> russian += ""
                2 -> russian += "двадцать"
                3 -> russian += "тридцать"
                4 -> russian += "сорок"
                5 -> russian += "пятьдесят"
                6 -> russian += "шестьдесят"
                7 -> russian += "семьдесят"
                8 -> russian += "восемьдесят"
                9 -> russian += "девяносто"
            }
            if (first % 10 != 0 && first / 10 % 10 != 0) russian += " "
            when (first % 10) {
                0 -> russian += " тысяч"
                1 -> russian += "одна тысяча"
                2 -> russian += "две тысячи"
                3 -> russian += "три тысячи"
                4 -> russian += "четыре тысячи"
                5 -> russian += "пять тысяч"
                6 -> russian += "шесть тысяч"
                7 -> russian += "семь тысяч"
                8 -> russian += "восемь тысяч"
                9 -> russian += "девять тысяч"
            }
        }
        if (last != 0) russian += " "
    }
    when (last / 100) {
        0 -> russian += ""
        1 -> russian += "сто"
        2 -> russian += "двести"
        3 -> russian += "триста"
        4 -> russian += "четыреста"
        5 -> russian += "пятьсот"
        6 -> russian += "шестьсот"
        7 -> russian += "семьсот"
        8 -> russian += "восемьсот"
        9 -> russian += "девятьсот"
    }
    if (last % 100 != 0 && last / 100 != 0) russian += " "
    if (last % 100 in 10..19) when (last % 100) {
        10 -> russian += "десять"
        11 -> russian += "одиннадцать"
        12 -> russian += "двенадцать"
        13 -> russian += "тринадцать"
        14 -> russian += "четырнадцать"
        15 -> russian += "пятнадцать"
        16 -> russian += "шестнадцать"
        17 -> russian += "семнадцать"
        18 -> russian += "восемнадцать"
        19 -> russian += "девятнадцать"
    } else {
        when ((last / 10) % 10) {
            0 -> russian += ""
            2 -> russian += "двадцать"
            3 -> russian += "тридцать"
            4 -> russian += "сорок"
            5 -> russian += "пятьдесят"
            6 -> russian += "шестьдесят"
            7 -> russian += "семьдесят"
            8 -> russian += "восемьдесят"
            9 -> russian += "девяносто"
        }
        if (last % 10 != 0 && last / 10 % 10 != 0) russian += " "
        when (last % 10) {
            0 -> russian += ""
            1 -> russian += "один"
            2 -> russian += "два"
            3 -> russian += "три"
            4 -> russian += "четыре"
            5 -> russian += "пять"
            6 -> russian += "шесть"
            7 -> russian += "семь"
            8 -> russian += "восемь"
            9 -> russian += "девять"
        }
    }
    return russian
}