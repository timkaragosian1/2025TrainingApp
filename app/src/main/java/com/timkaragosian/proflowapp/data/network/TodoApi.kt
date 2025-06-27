package com.timkaragosian.proflowapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class TodoApi(
    private val client: HttpClient = HttpClientProvider.instance,
) {
    suspend fun getTodoList(): List<TodoDto> = client.get("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/getlist").body()
    suspend fun removeTodoItem(id:String) = client.post("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/removeitem?id=$id")
    suspend fun postItem(todoItem:TodoDto) = client.post("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/postitem?todo=${todoItem.todo}&completed=${todoItem.completed}&timestamp=${todoItem.timestamp}")
    suspend fun completeItem(id:String) = client.post("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/completeitem?id=$id")
}