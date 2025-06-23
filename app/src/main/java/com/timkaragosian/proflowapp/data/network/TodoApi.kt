package com.timkaragosian.proflowapp.data.network

import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class TodoApi(
    private val client: HttpClient = HttpClientProvider.instance,
    private val strings: FlowAppResourceProvider,
) {
    suspend fun getTodoList(): List<TodoDto> = client.get(strings.string(R.string.gettodolist_url)).body()

    suspend fun removeTodoItem(id:String) = client.post("${strings.string(R.string.removeitem_url)}?id=$id")

    suspend fun postItem(todoItem:TodoDto) = client.post("${strings.string(R.string.postitem_url)}?todo=${todoItem.todo}&completed=${todoItem.completed}&timestamp=${todoItem.timestamp}")
}