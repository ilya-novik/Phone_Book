import java.lang.Math.toDegrees
import java.util.*
import kotlin.math.acos
import kotlin.math.hypot

fun main() {
    val scanner = Scanner(System.`in`)
    val a1 = scanner.nextDouble()
    val a2 = scanner.nextDouble()
    val b1 = scanner.nextDouble()
    val b2 = scanner.nextDouble()
    val cosine = (a1 * b1 + b2 * a2) / (hypot(a1, a2) * hypot(b1, b2))
    println(toDegrees(acos(cosine)))
}