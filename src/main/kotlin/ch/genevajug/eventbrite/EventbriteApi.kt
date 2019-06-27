package ch.genevajug.eventbrite

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javax.validation.constraints.NotNull


typealias Decimal = String
typealias DateTime = String
typealias LocalDatetime = String
typealias CountryCode = String
typealias CurrencyCode = String
typealias Id = String


enum class StatusEnunm {
    draft, live, started, ended, completed, canceled
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class MultipartText(
        @NotNull
        var html: String?,
        var text: String?
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
data class DateTimeTz(
        var timezone: String,
        var utc: String,
        var local: String
) {}


open class PaginedObj(pagination: Pagination) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Pagination(
        var object_count: Int,
        var page_number: Int,
        var page_size: Int,
        var page_count: Int,
        var continuation: String?,
        var has_more_items: Boolean = false
) {}


@JsonIgnoreProperties(ignoreUnknown = true)
data class Attendee(
        var id: Id,
        var created: DateTime?,
        var changed: DateTime?,
        var ticket_class_id: String?,
        var variant_id: String?,
        var ticket_class_name: String?,
        var quantity: String?,
        var checked_in: Boolean?,
        var cancelled: Boolean?,
        var refunded: Boolean?,
        var status: String?,
        var order_id: String?,
        var guestlist_id: String?,
        var invited_by: String?,
        var delivery_method: String?,
        var event_id: String?,
        var event: Event?
) {}


@JsonIgnoreProperties(ignoreUnknown = true)
data class Categories(
        var resource_uri: String,
        var id: Id,
        var name: String,
        var short_name: String,
        var short_name_localized: String
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoriesList(
        var pagination: Pagination,
        var categories: Array<Categories>
) : PaginedObj(pagination) {
}

@JsonDeserialize()
@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
        var id: Id,
        var name: MultipartText,
        var summary: String?,
        var description: MultipartText?,
        var url: String?,
        var start: DateTimeTz,
        var end: DateTimeTz?,
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
        var ticket_availability: TicketAvailability?,
        var publish_settings: String?
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventsList(
        var pagination: Pagination,
        var events: List<Event>
) : PaginedObj(pagination) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TicketAvailability(
        var has_available_tickets: Boolean?,
        var minimum_ticket_price: PriceTicket?,
        var maximum_ticket_price: PriceTicket?,
        var is_sold_out: Boolean?,
        var start_sales_date: DateTimeTz?,
        var waitlist_available: Boolean?
) {
}


@JsonIgnoreProperties(ignoreUnknown = true)
data class PriceTicket(
        var currency: String?,
        var value: Int?,
        var major_value: String?,
        var display: String?
) {
}


@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        var name: String,
        var first_name: String,
        var last_name: String,
        var is_public: Boolean,
        var image_id: String,
        var emails: List<Email>
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Email(
        var email: String,
        var verified: Boolean,
        var primary: Boolean
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(
        var id: Id,
        var name: String,
        var address: Address,
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
