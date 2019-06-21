package ch.genevajug.eventbrite

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


typealias Decimal = String
typealias DateTime = String
typealias LocalDatetime = String
typealias CountryCode = String
typealias CurrencyCode = String


open class PaginedObj(pagination: Pagination){

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Pagination(
        var object_count:Int,
        var page_number:Int,
        var page_size:Int,
        var page_count:Int,
        var continuation:String?,
        var has_more_items: Boolean = false
){}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Categories(
        var resource_uri:String,
        var id:String,
        var name:String,
        var short_name:String,
        var short_name_localized:String
){}

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoriesList (
        var pagination: Pagination,
        var categories: Array<Categories>
) : PaginedObj(pagination) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventList (
        var pagination: Pagination,
        var events: Array<Event>
) : PaginedObj(pagination) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
        var id: String?,
        var name: MultipartText,
        var summary: String,
        var description: MultipartText,
        var url: String?,
        var start: DateTimeTz,
        var end: DateTimeTz,
        var created: DateTime?,
        var changed: DateTime?,
        var published: DateTime?,
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class MultipartText(
        var http: String?,
        var text: String?
) { }

@JsonIgnoreProperties(ignoreUnknown = true)
data class DateTimeTz(
        val timezone: String,
        val utc: String,
        val local: String
) { }


@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(
        var name: String,
        var address: Address,
        var id: String,
        var age_restriction: String,
        var capacity: Integer
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
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
