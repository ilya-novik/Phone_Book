fun solution(products: List<String>, product: String) {
    for (i in products.indices) {
        if (product == products[i]) print("$i ")
    }
}