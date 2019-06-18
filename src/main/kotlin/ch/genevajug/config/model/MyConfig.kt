package ch.genevajug.config.model

data class MyConfig(
        var github: GithubConfig,
        var eventbrite: EventbriteConfig
) {}


data class GithubConfig(
        var token: String,
        val owner: String,
        val repo: String,
        var host: String,
        var port: Int,
        var ssl: Boolean,
        val disableConnexion: Boolean
) {}


data class EventbriteConfig(
        var token: String
) {
}