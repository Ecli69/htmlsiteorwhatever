// Task5.kt — Мини-проект: библиотека треков

interface Playable {
    fun play(): String
    fun stop(): String = "Воспроизведение остановлено"
}

sealed class MediaItem : Playable {
    data class Song(
        val title: String,
        val artist: String,
        val durationSec: Int,
        val genre: String
    ) : MediaItem() {
        override fun play(): String = "Играет: $title — $artist [$genre]"
    }

    data class Podcast(
        val title: String,
        val host: String,
        val durationSec: Int,
        val episodeNumber: Int
    ) : MediaItem() {
        override fun play(): String = "Подкаст $episodeNumber: $title — ведущий $host"
    }

    data class Audiobook(
        val title: String,
        val author: String,
        val durationSec: Int,
        val chapter: Int
    ) : MediaItem() {
        override fun play(): String = "Аудиокнига $author — $title, глава $chapter"
    }
}

object PlaylistManager {
    fun createDefaultPlaylist(): List<MediaItem> = listOf(
        MediaItem.Song("Bohemian Rhapsody", "Queen", 354, "Rock"),
        MediaItem.Song("Imagine", "John Lennon", 183, "Pop"),
        MediaItem.Podcast("Kotlin Weekly", "Иван Петров", 2400, 42),
        MediaItem.Podcast("Разговорный жанр", "Анна Смирнова", 1800, 7),
        MediaItem.Audiobook("Мастер и Маргарита", "Михаил Булгаков", 36000, 1),
        MediaItem.Audiobook("1984", "Джордж Оруэлл", 28800, 5)
    )

    fun findByTitle(playlist: List<MediaItem>, query: String): List<MediaItem> {
        val lowerQuery = query.lowercase()
        return playlist.filter { item ->
            val title = when (item) {
                is MediaItem.Song -> item.title
                is MediaItem.Podcast -> item.title
                is MediaItem.Audiobook -> item.title
            }
            title.lowercase().contains(lowerQuery)
        }
    }
}

fun main() {
    val playlist = PlaylistManager.createDefaultPlaylist()

    println("=== Воспроизведение плейлиста ===")
    for (item in playlist) {
        println(item.play())
        println(item.stop())
    }

    val totalSeconds = playlist.sumOf { item ->
        when (item) {
            is MediaItem.Song -> item.durationSec
            is MediaItem.Podcast -> item.durationSec
            is MediaItem.Audiobook -> item.durationSec
        }
    }
    val totalMinutes = totalSeconds / 60.0
    println("\nОбщая длительность: ${"%.2f".format(totalMinutes)} мин.")

    println("\n=== Только песни ===")
    playlist.filterIsInstance<MediaItem.Song>().forEach { println(it) }

    // Дополнительное задание
    println("\n=== Поиск по подстроке ===")
    for (query in listOf("kotlin", "и", "1984")) {
        println("Запрос \"$query\":")
        val found = PlaylistManager.findByTitle(playlist, query)
        if (found.isEmpty()) println("  ничего не найдено")
        else found.forEach { println("  ${it::class.simpleName}: ${it.play()}") }
    }
}