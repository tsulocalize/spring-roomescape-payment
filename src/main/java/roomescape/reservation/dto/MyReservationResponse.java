package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.WaitingWithRank;

public record MyReservationResponse(
        Long id,
        String theme,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time,
        String status
) {
    public static MyReservationResponse from(final Reservation reservation) {
        return new MyReservationResponse(
                reservation.getId(),
                reservation.getTheme().getName().getValue(),
                reservation.getDate().getValue(),
                reservation.getTime().getStartAt(),
                "예약"
        );
    }

    public static MyReservationResponse from(final WaitingWithRank waitingWithRank) {
        return new MyReservationResponse(
                waitingWithRank.getWaiting().getId(),
                waitingWithRank.getWaiting().getReservation().getTheme().getName().getValue(),
                waitingWithRank.getWaiting().getReservation().getDate().getValue(),
                waitingWithRank.getWaiting().getReservation().getTime().getStartAt(),
                waitingWithRank.getRank() + "번째 예약대기"
        );
    }
}
