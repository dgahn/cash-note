package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
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
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("CommunityMockControllerTest 테스트")
@WebMvcTest(CommunityMockController::class)
@AutoConfigureRestDocs
class CommunityMockControllerTest : AbstractRestDocControllerTest() {
    @DisplayName("[GET] /has-business")
    @Test
    fun `간편 연결 대상인지 확인할 수 있다`() {
        val url = "has-business"
        val registrationNumber = "123-45-67890"

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
                            .tag("공동체 Mock API")
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

    @DisplayName("[POST] /register-data-communication")
    @Test
    fun `약관 동의 내역을 저장할 수 있다`() {
        val url = "register-data-communication"
        val request = TermFixture.TERM_AGREEMET_REQUEST

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
                            .tag("공동체 Mock API")
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
