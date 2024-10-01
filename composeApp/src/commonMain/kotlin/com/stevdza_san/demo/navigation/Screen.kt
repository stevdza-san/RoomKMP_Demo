package com.stevdza_san.demo.navigation

const val BOOK_ID_ARG = "bookId"

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object Details: Screen(route = "details_screen/{$BOOK_ID_ARG}") {
        fun passId(id: Int) = "details_screen/$id"
    }
    data object Manage: Screen(route = "manage_screen/{$BOOK_ID_ARG}") {
        fun passId(id: Int = -1) = "manage_screen/$id"
    }
}