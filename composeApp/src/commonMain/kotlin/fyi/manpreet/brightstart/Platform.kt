package fyi.manpreet.brightstart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform