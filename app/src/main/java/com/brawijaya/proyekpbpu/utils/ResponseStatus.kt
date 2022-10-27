package com.brawijaya.proyekpbpu.utils

enum class ResponseStatus(val stat : Int) {
    BAD_REQUEST(401),
    FORBIDDEN(403),
    NOT_FOUND(404)
}