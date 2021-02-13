fun main() {
    var number = readLine()!!
    number = if ('.' in number) {
        number.substringAfter('.')
    } else {
        "0"
    }
    println(number[0])
}