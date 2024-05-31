package com.blipaytest.blipaytest.application

import com.blipaytest.blipaytest.domain.creditrating.CreditRating
import com.blipaytest.blipaytest.enums.CreditRatingStatus
import java.math.BigInteger
import java.util.UUID

data class AnalyseCreditResponse(
    val name: String,
    val document: String,
    val score: BigInteger,
    val status: CreditRatingStatus,
    val id: UUID
){
    constructor(creditRating: CreditRating): this(
        creditRating.name,
        creditRating.document,
        creditRating.score,
        creditRating.status,
        creditRating.externalId
    )
}
