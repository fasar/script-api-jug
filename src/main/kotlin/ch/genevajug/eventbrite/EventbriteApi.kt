package ch.genevajug.eventbrite


data class Event(
        var id: String?,
        var name: MultipartText,
        var summary: String,
        var description: MultipartText,
        var url: String?,
        var start: DateTimeTz,
        var end: DateTimeTz,
        var created: DateTimeTz?,
        var changed: DateTimeTz?,
        var published: DateTimeTz?,
        var status: StatusEnunm,
        var currency: String?,
        var online_event: Boolean?,
        var hide_start_date: Boolean?,
        var hide_end_date: Boolean?,
        // Private Fields Bellow
        var listed: Boolean?,
        var shareable: Boolean?,
        var invite_only: Boolean?,
        var show_remaining: Boolean?,
        var capacity: Int?,
        var capacity_is_custom: Boolean?,
        // Expansions
        var logo_id: String?,
        var venue_id: String?,
        var organizer_id: String?,
        var format_id: String?,
        var ticket_availability: Int?,
        var publish_settings: String?
) {
}

enum class StatusEnunm {
    draft, live, started, ended, completed, canceled

}

data class MultipartText(
        var http: String,
        var text: String?
) {

}

data class DateTimeTz(
        val timezone: String,
        val utc: String
) {

}


data class Venue(
        var name: String,
        var address: Address,
        var id: String,
        var age_restriction: String,
        var capacity: Integer
) {
}

data class Address(
        var address_1: String,
        var address_2: String,
        var city: String,
        var region: String,
        var postal_code: String,
        var country: String,
        var latitude: String,
        var longitude: String
) {

}
