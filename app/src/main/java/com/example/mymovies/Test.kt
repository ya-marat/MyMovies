package com.example.mymovies

fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val newList = list.takeLast(9)

    newList.forEach { print(it) }
}