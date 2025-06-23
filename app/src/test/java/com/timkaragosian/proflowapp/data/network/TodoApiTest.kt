package com.timkaragosian.proflowapp.data.network

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(AndroidJUnit4::class)
class TodoApiTest {
    @Test
    fun todoApiParsesResponse() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    [{"id":"0","todo":"first to do","completed":false,"timestamp":0}]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine){
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val resourceProvider = FlowAppResourceProvider(ApplicationProvider.getApplicationContext())

        val api = TodoApi(client, strings = resourceProvider)
        val dto = api.getTodoList()

        assertEquals("0", dto[0].id)
        assertEquals("first to do", dto[0].todo)
        assertFalse(dto[0].completed)
        assertEquals(0, dto[0].timestamp)
    }
}