package ch.genevajug

import com.fasterxml.jackson.annotation.JsonProperty

data class MyConfig(
        @JsonProperty("github")
        var github: GithubConfig
) {}

data class GithubConfig (
        @JsonProperty("token")
        var token: String,
        @JsonProperty("host")
        var host: String,
        @JsonProperty("port")
        var port: String,
        @JsonProperty("ssl")
        var ssl: Boolean
){}
