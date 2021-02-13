import kotlin.Exception

fun main(args: Array<String>) {
    val speed = readLine()!!.toInt()
    val limit = readLine()!!
    try {
        calculateExceeding(speed, limit.toInt())
    } catch (e: Exception) {
        calculateExceeding(speed)
    }
}

fun calculateExceeding(speed: Int, limit: Int = 60) {
    println(
        if (speed > limit) "Exceeds the limit by ${speed - limit} kilometers per hour"
        else "Within the limit"
    )
}