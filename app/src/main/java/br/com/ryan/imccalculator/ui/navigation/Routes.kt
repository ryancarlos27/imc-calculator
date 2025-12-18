package br.com.ryan.imccalculator.ui.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object History : Route("history")
    data object Settings : Route("settings") // NOVO

    data object Detail : Route("detail/{id}") {
        fun create(id: Long) = "detail/$id"
    }
    data object Help : Route("help")

}
