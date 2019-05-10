package ch.genevajug

import com.fasterxml.jackson.annotation.JsonProperty

data class MyConfig(
        var github: GithubConfig
) {}

data class GithubConfig (
        var token: String,
        var host: String,
        var port: Int,
        var ssl: Boolean,
        val noConnect: Boolean
){}


