package com.example.ozakharc.redditclient.model

import com.example.ozakharc.redditclient.api.response.BaseResponse

interface RepositoryManager {

    fun onSuccessCommentsResponse(baseResponse: BaseResponse, permalink: String)

    fun isCommentsInDB(permalink: String): Boolean
}