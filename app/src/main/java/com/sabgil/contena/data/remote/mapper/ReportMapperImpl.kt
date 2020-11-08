package com.sabgil.contena.data.remote.mapper

import com.sabgil.contena.data.remote.dto.PostReportResponse
import com.sabgil.contena.domain.model.ReportResult
import javax.inject.Inject

class ReportMapperImpl @Inject constructor() : ReportMapper {

    override fun toReportResult(from: PostReportResponse): ReportResult =
        ReportResult(
            userId = from.userId,
            postId = from.postId,
            contents = from.contents,
            reported = from.reported
        )
}