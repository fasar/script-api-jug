package ch.genevajug.model

data class MyConfig(
        var github: GithubConfig
) {}

data class GithubConfig (
        var token: String,
        val owner: String,
        val repo: String,
        var host: String,
        var port: Int,
        var ssl: Boolean,
        val disableConnexion: Boolean
){}


