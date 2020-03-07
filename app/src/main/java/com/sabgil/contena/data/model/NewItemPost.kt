package com.sabgil.contena.data.model

data class NewItemPost(
    val postId: Long,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val newItemList: List<SummaryNewItem>
)