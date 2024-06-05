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
class ReservationTimeControllerTest {

    @Autowired
    private TokenProvider tokenProvider;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    public void initReservation() {
        RestAssured.port = randomServerPort;
    }

    @DisplayName("전체 예약 시간 정보를 조회한다.")
    @Test
    void getReservationTimesTest() {
        RestAssured.given().log().all()
                .cookie("token", createUserAccessToken())
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(8));
    }

    private String createUserAccessToken() {
        return tokenProvider.createToken(3L, MemberRole.USER);
    }
}
