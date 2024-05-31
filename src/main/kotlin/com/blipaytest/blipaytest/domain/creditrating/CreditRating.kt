package com.blipaytest.blipaytest.domain.creditrating

import com.blipaytest.blipaytest.enums.CreditRatingStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.math.BigInteger
import java.util.UUID

@Entity
@Table(name = "credit_ratings")
class CreditRating(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "document", nullable = false)
    var document: String,

    @Column(name = "score", nullable = false)
    var score: BigInteger,

    @Transient
    var age: BigDecimal
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "external_id", nullable = false)
    var externalId: UUID = UUID.randomUUID()

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: CreditRatingStatus = analyseCredit()

    private fun analyseCredit(): CreditRatingStatus {
        return if (age >= CREDIT_MINIMUM_AGE && score >= CREDIT_MINIMUM_SCORE)
                CreditRatingStatus.APPROVED
            else
                CreditRatingStatus.DENIED
    }

    companion object{
        val CREDIT_MINIMUM_AGE = BigDecimal(18)
        val CREDIT_MINIMUM_SCORE = BigDecimal(200).toBigInteger()
    }
}