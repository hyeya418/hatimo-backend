package com.hatimo.heartemotion.hello;

import com.hatimo.heartemotion.config.SecurityConfig;
import com.hatimo.heartemotion.domain.hello.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
@AutoConfigureMockMvc(addFilters = false) // 시큐리티 필터 끄기
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 헬로API는_성공_응답을_반환한다() throws Exception {
        mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("요청이 성공적으로 처리되었습니다."))
                .andExpect(jsonPath("$.data[0]").value("Hello, Hatimo!"));
    }

    @Test
    void 에러가_발생하면_정해진_에러형식으로_응답된다() throws Exception {
        mockMvc.perform(get("/api/v1/hello/error"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("사용자를 찾을 수 없습니다."))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void 유효성_검증에_실패하면_정해진_에러형식으로_응답된다() throws Exception {
        String requestBody = """
                    {
                        "password": ""
                    }
                """;

        mockMvc.perform(post("/api/v1/hello/validate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.message").value("입력값이 유효하지 않습니다."))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
