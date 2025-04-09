package com.hatimo.heartemotion.user;

import com.hatimo.heartemotion.domain.user.jwt.JwtProvider;
import com.hatimo.heartemotion.domain.user.model.User;
import com.hatimo.heartemotion.domain.user.service.AuthService;
import com.hatimo.heartemotion.domain.user.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private KakaoLoginService kakaoLoginService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void 카카오_로그인_성공시_응답에_쿠키와_환영메시지를_포함한다() throws Exception {
        // given
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setNickname("지혜");

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(kakaoLoginService.loginWithKakao("code123")).thenReturn(mockUser);
        when(jwtProvider.createToken(1L)).thenReturn("mock-token");

        // when
        ResponseEntity<String> result = authService.loginWithKakao("code123", response);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).contains("지혜");

        ArgumentCaptor<String> cookieCaptor = ArgumentCaptor.forClass(String.class);
        verify(response).addHeader(eq("Set-Cookie"), cookieCaptor.capture());

        String cookieHeader = cookieCaptor.getValue();
        assertThat(cookieHeader).contains("access_token=mock-token");
        assertThat(cookieHeader).contains("HttpOnly");
    }

    @Test
    void 카카오_로그인_실패시_UNAUTHORIZED_응답을_반환한다() throws Exception {
        // given
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(kakaoLoginService.loginWithKakao("bad-code"))
                .thenThrow(new RuntimeException("인증 실패"));

        // when
        ResponseEntity<String> result = authService.loginWithKakao("bad-code", response);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(401);
        assertThat(result.getBody()).contains("❌ 카카오 로그인 실패");
    }
}
