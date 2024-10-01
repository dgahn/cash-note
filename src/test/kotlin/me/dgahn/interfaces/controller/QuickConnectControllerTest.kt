package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.QuickConnectChecker
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("QuickConnectControllerTest 테스트")
@WebMvcTest(QuickConnectController::class)
@AutoConfigureRestDocs
class QuickConnectControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var quickConnectChecker: QuickConnectChecker

    @DisplayName("[GET] /api/v1/quick-connect/availability")
    @Test
    fun `간편 연결 대상인지 확인할 수 있다`() {
        val url = "api/v1/quick-connect/availability"
        val registrationNumber = "123-45-67890"

        every { quickConnectChecker.check(registrationNumber) } returns true

        val queryParams = listOf(
            RequestDocumentation.parameterWithName("registrationNumber").description("사업자등록번호"),
        )
        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("result").description("간편 연결 대상 여부"),
        )

        val documentId = "get/$url"
        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url")
                    .queryParam("registrationNumber", registrationNumber)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"),
            ).andExpect(
                MockMvcResultMatchers
                    .status()
                    .isOk(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    ResourceDocumentation.resource(
                        ResourceSnippetParametersBuilder()
                            .tag("CashNote API")
                            .summary("간편 연결 대상 여부 조회 API")
                            .description("간편 연결 대상 여부를 공동체 서버를 통해서 조회합니다.")
                            .queryParameters(*queryParams.toTypedArray())
                            .responseFields(responseFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }
}
