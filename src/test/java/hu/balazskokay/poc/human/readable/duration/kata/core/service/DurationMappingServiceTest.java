package hu.balazskokay.poc.human.readable.duration.kata.core.service;

import hu.balazskokay.poc.human.readable.duration.kata.core.port.in.NegativeNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class DurationMappingServiceTest {

    private static final int ONE_HOUR = 60 * 60;
    private static final int ONE_DAY = 60 * 60 * 24;
    private static final int ONE_YEAR = 60 * 60 * 24 * 365;

    private final DurationMappingService durationMappingService = new DurationMappingService();

    @ParameterizedTest
    @MethodSource("validDurationStream")
    void givenDuration_shouldReturnExpectedReadableDuration_whenValidInput(
            final Integer durationInSeconds,
            final String expectedReadableDuration
    ) {
        // When
        final var actualReadableDuration = durationMappingService.formatDuration(durationInSeconds);

        // Then
        then(actualReadableDuration).isEqualTo(expectedReadableDuration);
    }

    private static Stream<Arguments> validDurationStream() {
        return Stream.of(
                Arguments.of(0, "now"),
                Arguments.of(1, "1 second"),
                Arguments.of(2, "2 seconds"),
                Arguments.of(60, "1 minute"),
                Arguments.of(61, "1 minute and 1 second"),
                Arguments.of(62, "1 minute and 2 seconds"),
                Arguments.of(120, "2 minutes"),
                Arguments.of(121, "2 minutes and 1 second"),
                Arguments.of(ONE_HOUR, "1 hour"),
                Arguments.of(ONE_HOUR + 61, "1 hour, 1 minute and 1 second"),
                Arguments.of(ONE_HOUR + 62, "1 hour, 1 minute and 2 seconds"),
                Arguments.of(ONE_HOUR * 2, "2 hours"),
                Arguments.of(ONE_HOUR * 2 + 1, "2 hours and 1 second"),
                Arguments.of(ONE_HOUR * 4 + 129, "4 hours, 2 minutes and 9 seconds"),
                Arguments.of(ONE_DAY, "1 day"),
                Arguments.of(ONE_DAY * 2, "2 days"),
                Arguments.of(ONE_DAY + 1, "1 day and 1 second"),
                Arguments.of(ONE_DAY + 61, "1 day, 1 minute and 1 second"),
                Arguments.of(ONE_DAY + 2, "1 day and 2 seconds"),
                Arguments.of(ONE_YEAR, "1 year"),
                Arguments.of(ONE_YEAR * 34, "34 years"),
                Arguments.of(ONE_YEAR + 1, "1 year and 1 second"),
                Arguments.of(ONE_YEAR + (20 * 60) + 35, "1 year, 20 minutes and 35 seconds"),
                Arguments.of(ONE_YEAR + 74 * ONE_DAY + 11 * ONE_HOUR + 20 * 60 + 35, "1 year, 74 days, 11 hours, 20 minutes and 35 seconds"),
                Arguments.of(Integer.MAX_VALUE, "68 years, 35 days, 3 hours, 14 minutes and 7 seconds")
        );
    }

    @Test
    void givenNegativeDuration_shouldThrowNegativeNumberException() {
        // Given
        final int negativeDuration = -1;

        // When / Then
        thenThrownBy(() -> durationMappingService.formatDuration(negativeDuration))
                .isInstanceOf(NegativeNumberException.class);
    }

}