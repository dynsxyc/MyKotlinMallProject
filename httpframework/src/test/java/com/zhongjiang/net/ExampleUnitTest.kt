package com.zhongjiang.net

import com.zhongjiang.net.service.IApiProvider
import com.zhongjiang.net.service.IRequest
import com.zhongjiang.net.service.IResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
//        var request = HttpRequestProvider().getHttpRequest(URI.create("https://www.imooc.com"),HttpMethod.GET)
//        var response = request.execute()
//        var reader = BufferedReader(InputStreamReader(response.getBody()))
//            println(reader.readText())
//        response.close()
        IApiProvider.test("https://getman.cn/echo", mutableMapOf(Pair("name","张三丰"),Pair("age","99")),object : IResponse<Any>() {
            override fun success(request: IRequest, data: Any) {
                            println("success $data")
            }

            override fun fail(errorCode: Int, errorMessage: String) {
                println("fail ")
            }
        })

    }
}
