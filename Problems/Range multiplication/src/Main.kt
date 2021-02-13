val lambda: (Long, Long) -> Long = { a, b ->
    var result = 1L
    for (i in a..b) {
        result *= i
    }
    result
}