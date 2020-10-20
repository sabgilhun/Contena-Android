package com.sabgil.contena.domain.model

data class ReportResult(
    val userId: String,
    val postId: Long,
    val contents: String,
    val reported: Boolean
)