package com.example.ozakharc.redditclient.model


import com.example.ozakharc.redditclient.App
import com.example.ozakharc.redditclient.api.response.BaseResponse

object RepositoryManagerImpl : RepositoryManager {

    override fun onSuccessCommentsResponse(baseResponse: BaseResponse, permalink: String) {
        val children = baseResponse.data.children
        if (children.size > 0) {
            for (child in children) {
                val comment = ItemComment()
                comment.comment = child.childDta.body
                comment.permalink = permalink
                comment.id = child.childDta.id
                App.instance!!.getAppDb().itemCommentDao().insert(comment)
            }
        }
    }

    override fun isCommentsInDB(permalink: String): Boolean {
        val dao: ItemCommentDao = App.instance?.getAppDb()!!.itemCommentDao()
        var comments: List<ItemComment> = dao.searchSavedComments(permalink)
        return !comments.isEmpty()
    }
}