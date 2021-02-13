package phonebook

import java.io.File
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    val directory = File("C:/Users/ilya/IdeaProjects/directory.txt").readLines().toMutableList()
    val findList = File("C:/Users/ilya/IdeaProjects/find.txt").readLines()

    val linearSearchTime = linearSearch(directory, findList)
    jumpSearchWithSort(directory, findList, linearSearchTime)
    binarySearchWithSort(directory, findList)
    hashTable(directory, findList)
}

fun timeToString(timeStart: Long, timeEnd: Long): String {
    return String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeEnd - timeStart)
}

fun linearSearch(
    directory: MutableList<String>,
    findList: List<String>,
    addTime: Long = 0,
    firstLine: Boolean = true
): Long {
    if (firstLine) println("Start searching (linear search)...")
    val startTime = System.currentTimeMillis()
    var peopleFound = 0
    for (line in directory) {
        if (line.substringAfter(" ") in findList) peopleFound++
    }
    val timePassed = System.currentTimeMillis()
    println(
        "Found $peopleFound / ${findList.size} entries. Time taken: ${
            timeToString(
                startTime,
                timePassed + addTime
            )
        }"
    )
    if (firstLine) println()
    return timePassed - startTime
}

fun jumpSearchWithSort(directory: MutableList<String>, findList: List<String>, linearSearchTime: Long) {
    println("Start searching (bubble sort + jump search)...")
    val bubbleSortTime = bubbleSort(directory, linearSearchTime)
    val searchTime: Long
    if (bubbleSortTime > linearSearchTime * 10) {
        searchTime = linearSearch(directory, findList, bubbleSortTime, false)
        println("Sorting time: ${timeToString(0, bubbleSortTime)} - STOPPED, moved to linear search")
    } else {
        val startTime = System.currentTimeMillis()
        var peopleFound = 0
        for (toFind in findList) {
            var previousIndex = 0
            loop@ for (currentIndex in 0..directory.size step sqrt(directory.size.toDouble()).roundToInt()) {
                if (toFind in directory[previousIndex].substringAfter(" ")..directory[currentIndex - 1].substringAfter(" ")) {
                    for (index in currentIndex - 1 downTo previousIndex) {
                        if (directory[index].substringAfter(" ") == toFind) {
                            peopleFound++
                            break@loop
                        }
                    }
                }
                previousIndex = currentIndex
            }
        }
        val timePassed = System.currentTimeMillis()
        searchTime = timePassed - startTime
        println(
            "Found $peopleFound / ${findList.size} entries. Time taken: ${
                timeToString(
                    startTime,
                    timePassed + bubbleSortTime
                )
            }"
        )
        println("Sorting time: ${timeToString(0, bubbleSortTime)}")
    }
    println("Searching time: ${timeToString(0, searchTime)}")
    println()
}

fun binarySearchWithSort(unsortedDirectory: MutableList<String>, findList: List<String>) {
    println("Start searching (quick sort + binary search)...")
    var startTime = System.currentTimeMillis()
    val sortedDirectory = quickSort(unsortedDirectory)
    var timePassed = System.currentTimeMillis()
    val sortingTime = timePassed - startTime

    startTime = System.currentTimeMillis()
    var peopleFound = 0
    for (toFind in findList) {
        if (binarySearch(sortedDirectory, toFind)) peopleFound++
    }
    timePassed = System.currentTimeMillis()
    val searchingTime = timePassed - startTime

    println(
        "Found $peopleFound / ${findList.size} entries. Time taken: ${timeToString(0, sortingTime + searchingTime)}"
    )
    println("Sorting time: ${timeToString(0, sortingTime)}")
    println("Searching time: ${timeToString(0, searchingTime)}")
    println()
}

fun binarySearch(directory: MutableList<String>, toFind: String): Boolean {
    if (directory.isEmpty()) return false
    if (directory.size == 1) return directory.first().substringAfter(" ") == toFind

    val middle = directory.lastIndex / 2
    if (directory[middle].substringAfter(" ") == toFind) return true

    val subList = if (toFind < directory[middle].substringAfter(" ")) {
        directory.subList(0, middle)
    } else {
        directory.subList(middle + 1, directory.size)
    }

    return binarySearch(subList, toFind)
}

fun bubbleSort(directory: MutableList<String>, linearSearchTime: Long): Long {
    val startTime = System.currentTimeMillis()
    var timePassed = 0L
    loop@ for (k in directory.indices) {
        var switches = 0
        for (i in 1..directory.lastIndex) {
            if (directory[i - 1].substringAfter(" ") > directory[i].substringAfter(" ")) {
                val string = directory[i - 1]
                directory[i - 1] = directory[i]
                directory[i] = string
                switches++
            }
            timePassed = System.currentTimeMillis()
            if (timePassed - startTime > linearSearchTime * 10) {
                break@loop
            }
        }
    }
    return timePassed - startTime
}

fun quickSort(directory: MutableList<String>): MutableList<String> {
    if (directory.size <= 1) return directory
    if (directory.size == 2) {
        return if (directory[0].substringAfter(" ") > directory[1].substringAfter(" ")) mutableListOf(
            directory[1],
            directory[0]
        )
        else directory
    }

    val pivot = directory.last()
    val lessThanPivot = mutableListOf<String>()
    val moreThanPivot = mutableListOf<String>()
    for (i in directory) {
        if (i == pivot) continue
        if (i.substringAfter(" ") < pivot.substringAfter(" ")) {
            lessThanPivot.add(i)
        }
        if (i.substringAfter(" ") >= pivot.substringAfter(" ")) {
            moreThanPivot.add(i)
        }
    }

    return (quickSort(lessThanPivot) + pivot + quickSort(moreThanPivot)).toMutableList()
}

fun hashTable(directory: MutableList<String>, findList: List<String>) {
    println("Start searching (hash table)...")
    var startTime = System.currentTimeMillis()
    val map = createHashMap(directory)
    val creatingTime = System.currentTimeMillis() - startTime

    startTime = System.currentTimeMillis()
    var peopleFound = 0
    for (toFind in findList) {
        val index = createHashKey(toFind, directory, containsNumber = false)
        loop@ for (name in map[index]) {
            if (toFind == name.substringAfter(" ")) {
                peopleFound++
                break@loop
            }
        }
    }
    val searchingTime = System.currentTimeMillis() - startTime

    println(
        "Found $peopleFound / ${findList.size} entries. Time taken: ${timeToString(0, creatingTime + searchingTime)}"
    )
    println("Creating time: ${timeToString(0, creatingTime)}")
    println("Searching time: ${timeToString(0, searchingTime)}")
    println()
}

fun createHashMap(directory: MutableList<String>): MutableList<MutableList<String>> {
    val map = MutableList(directory.size) { mutableListOf<String>() }
    for (name in directory) {
        map[createHashKey(name, directory)].add(name)
    }
    return map
}

fun createHashKey(string: String, array: List<Any>, containsNumber: Boolean = true): Int {
    var charactersSum = 0
    if (containsNumber) {
        for (char in string.substringAfter(" ")) charactersSum += char.toInt()
    } else {
        for (char in string) charactersSum += char.toInt()
    }
    return charactersSum % array.size
}