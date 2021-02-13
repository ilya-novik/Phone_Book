import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val numbers = mutableListOf<Int>()
    for (i in 1..scanner.nextInt()) {
        numbers.add(scanner.nextInt())
    }
    val p = scanner.nextInt()
    val m = scanner.nextInt()
    var result = "YES"
    for (i in 1..numbers.lastIndex) {
        if ((numbers[i - 1] == p && numbers[i] == m) || (numbers[i - 1] == m && numbers[i] == p)) {
            result = "NO"
        }
    }
    println(result)
}