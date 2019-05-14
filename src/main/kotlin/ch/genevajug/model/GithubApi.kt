package ch.genevajug.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.Instant

enum class StatusRes {
    queued, building, built, errored
}

data class ErrorBuildRes (
    var message: String?
){}

data class PagesBuildRes(
    var url: String,
    var status: StatusRes,
    var error: ErrorBuildRes,
    var commit: String,
    var duration: Int,
    var createdAt: Instant,
    var updatedAt: Instant,
    var pusher: UserRes
){}

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRes (
    var login: String,
    var id: Int,
    var nodeId: String,
    var avatarUrl: String,
    var gravatarId: String,
    var url: String,
    var htmlUrl: String,
    var type: String,
    var followingUrl: String,
    var siteAdmin: Boolean
){}
