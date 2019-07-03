package ch.genevajug.github.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

enum class StatusRes {
    queued, building, built, errored
}

data class ErrorBuildRes (
    var message: String?
)

data class PagesBuildRes(
        var url: String,
        var status: StatusRes,
        var error: ErrorBuildRes,
        var commit: String,
        var duration: Int,
        @JsonProperty("created_at")  var createdAt: Instant,
        @JsonProperty("updated_at")  var updatedAt: Instant,
        var pusher: UserRes
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRes (
        var login: String,
        var id: Int,
        @JsonProperty("node_id") var nodeId: String,
        @JsonProperty("avatar_url") var avatarUrl: String,
        @JsonProperty("gravatar_id") var gravatarId: String,
        var url: String,
        @JsonProperty("html_url") var htmlUrl: String,
        var type: String,
        @JsonProperty("following_url") var followingUrl: String,
        @JsonProperty("site_admin") var siteAdmin: Boolean = false
)

data class Repository (
        var name: String? = null
)

data class Contributor(
        var login: String? = null
)