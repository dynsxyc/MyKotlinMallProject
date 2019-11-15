package com.zhongjiang.net

import com.zhongjiang.net.http.HttpMethod
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        var request = HttpRequestProvider().getHttpRequest(URI.create("https://www.imooc.com"),HttpMethod.GET)
        var response = request.execute()
        var content=""
        var reader = BufferedReader(InputStreamReader(response.getBody()))
            println(reader.readText())
        response.close()
    }
}
