package com.blipaytest.blipaytest.domain.creditrating

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRatingRepository: JpaRepository<CreditRating, Long> {
}