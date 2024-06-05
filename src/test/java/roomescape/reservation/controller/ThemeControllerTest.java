package roomescape.reservation.controller;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import roomescape.auth.token.TokenProvider;
import roomescape.member.model.MemberRole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ThemeControllerTest {

    @Autowired
    private TokenProvider tokenProvider;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    public void initReservation() {
        RestAssured.port = randomServerPort;
    }

    @DisplayName("전체 테마 정보를 조회한다.")
    @Test
    void getThemesTest() {
        RestAssured.given().log().all()
                .cookie("token", createUserAccessToken())
                .when().get("/themes")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(15));
    }

    private String createUserAccessToken() {
        return tokenProvider.createToken(3L, MemberRole.USER);
    }
}
