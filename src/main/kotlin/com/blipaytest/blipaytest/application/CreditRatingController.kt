package com.blipaytest.blipaytest.application

import com.blipaytest.blipaytest.domain.creditrating.CreditRatingService
import org.springframework.data.domain.PageImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid

@RestController
@RequestMapping("api/v1/credit-rating")
class CreditRatingController(
    private val creditRatingService: CreditRatingService
) {

    @PostMapping
    fun analyseCredit(
        @RequestBody
        @Valid
        analyseCreditRequest: AnalyseCreditRequest
    ): ResponseEntity<AnalyseCreditResponse> {
        val creditRating = creditRatingService.analyseCredit(
            analyseCreditRequest.name,
            analyseCreditRequest.age,
            analyseCreditRequest.monthIncome,
            analyseCreditRequest.city,
            analyseCreditRequest.document
        )

        val result = AnalyseCreditResponse(creditRating)

        return ResponseEntity.ok(result)
    }

    @GetMapping
    fun listCreditRating(
        @RequestParam("page")
        page: Int?,

        @RequestParam("size")
        size: Int?
    ): ResponseEntity<PageImpl<AnalyseCreditResponse>> {
        val creditRatingPage = creditRatingService.listCreditRatings(page, size)

        val result = PageImpl(
            creditRatingPage.content.map { AnalyseCreditResponse(it) },
            creditRatingPage.pageable,
            creditRatingPage.totalElements
        )

        return ResponseEntity.ok(result)
    }
}