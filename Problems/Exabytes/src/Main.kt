import java.math.BigInteger

fun main() {
    var exabyte = BigInteger("1")
    for (i in 1..63) exabyte *= BigInteger("2")
    println(BigInteger(readLine()!!) * exabyte)
}