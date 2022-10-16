package com.fagerland.freeleashadminapi.toggle.domain

import javax.persistence.Embeddable

@Embeddable
class ToggleConstraint(
    var field: String
)