package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.InitCardTransactionHistorySyncService
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("SyncTestControlle 테스트")
@WebMvcTest(SyncTestController::class)
@AutoConfigureRestDocs
class SyncTestControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var initSyncService: InitCardTransactionHistorySyncService

    @DisplayName("[GET] /sync-init")
    @Test
    fun `최초 싱크 init 테스트 API를 호출할 수 있다`() {
        val url = "sync-init"
        every { initSyncService.sync() } returns Unit

        val documentId = "get/$url"
        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url")
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
                            .tag("Sync Test API")
                            .summary("최초 Sync 테스트 API")
                            .description("최초 Sync 테스트를 위한 API")
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("[GET] /sync-daily")
    @Test
    fun `데일리 싱크 init 테스트 API를 호출할 수 있다`() {
        val url = "sync-daily"
        every { initSyncService.sync() } returns Unit

        val documentId = "get/$url"
        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url")
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
                            .tag("Sync Test API")
                            .summary("데일리 Sync 테스트 API")
                            .description("데일리 Sync 테스트를 위한 API")
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }
}
