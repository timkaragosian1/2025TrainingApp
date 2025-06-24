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
    suspend fun getTodoList(): List<TodoDto> = client.get("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/getlist").body()
    suspend fun removeTodoItem(id:String) = client.post("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/removeitem?id=$id")
    suspend fun postItem(todoItem:TodoDto) = client.post("https://y7fx79maa9.execute-api.us-east-2.amazonaws.com/apikey/flowapp/removeitem?todo=${todoItem.todo}&completed=${todoItem.completed}&timestamp=${todoItem.timestamp}")
    suspend fun completeItem(id:String) = client.post("")
}