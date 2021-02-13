fun identity(i: Int) = i
fun half(i: Int) = i / 2
fun zero(i: Int) = 0

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}