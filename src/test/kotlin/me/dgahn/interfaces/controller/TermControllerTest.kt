package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.TermCreator
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
import me.dgahn.util.objectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("TermControllerTest 테스트")
@WebMvcTest(TermController::class)
@AutoConfigureRestDocs
class TermControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var termCreator: TermCreator

    @DisplayName("[POST] /api/v1/terms/agreement")
    @Test
    fun `간편 연결 대상인지 확인할 수 있다`() {
        val url = "api/v1/terms/agreement"
        val request = TermFixture.TERM_AGREEMET_REQUEST

        every { termCreator.createTerm(request.registrationNumber, request.agreedType) } returns Unit

        val requestFields = listOf(
            PayloadDocumentation.fieldWithPath("registrationNumber").description("사업자 등록 번호"),
            PayloadDocumentation.fieldWithPath("agreedType").description("데이터 제공 여부"),
        )

        val documentId = "post/$url"
        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .post("/$url")
                    .content(objectMapper.writeValueAsBytes(request))
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
                            .summary("약관 동의 내역 저장 API")
                            .description("약관에 대한 동의 내역을 저장하는 API")
                            .requestFields(requestFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }
}
