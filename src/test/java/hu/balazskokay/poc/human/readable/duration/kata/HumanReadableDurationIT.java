package hu.balazskokay.poc.human.readable.duration.kata;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.BDDAssertions.then;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HumanReadableDurationIT {

    private static final String DURATION_ENDPOINT = "/duration?durationInSeconds=";

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    class PositiveTests {

        @Test
        void givenValidDuration_whenConvertDurationToText_thenReturnsReadableFormat() {
            // Given
            final var durationInSeconds = 3661;

            // When
            final var response = restTemplate.getForEntity(DURATION_ENDPOINT + durationInSeconds, String.class);

            // Then
            then(response)
                    .isNotNull()
                    .extracting("body")
                    .isEqualTo("1 hour, 1 minute and 1 second");
        }

        @Test
        void givenZeroDuration_whenConvertDurationToText_thenReturnsNow() {
            // Given
            final var durationInSeconds = 0;

            // When
            final var response = restTemplate.getForEntity(DURATION_ENDPOINT + durationInSeconds, String.class);

            // Then
            then(response)
                    .isNotNull()
                    .extracting("body")
                    .isEqualTo("now");
        }

    }

    @Nested
    class NegativeTests {

        @Test
        void givenNegativeDuration_whenConvertDurationToText_thenThrowsException() {
            // Given
            final var durationInSeconds = -1;

            // When
            final var response = restTemplate.getForEntity(DURATION_ENDPOINT + durationInSeconds, String.class);

            // Then
            then(response)
                    .isNotNull()
                    .extracting("status")
                    .isEqualTo(HttpStatus.BAD_REQUEST);

            then(response.getBody())
                    .contains("Negative numbers are not allowed for duration");
        }

        @Test
        void givenAlphabeticalInput_whenConvertDurationToText_thenThrowsException() {
            // Given
            final var durationInSeconds = "abc";

            // When
            final var response = restTemplate.getForEntity(DURATION_ENDPOINT + durationInSeconds, String.class);

            // Then
            then(response)
                    .isNotNull()
                    .extracting("status")
                    .isEqualTo(HttpStatus.BAD_REQUEST);

            then(response.getBody())
                    .contains("Invalid input type for duration. Please provide a valid integer");
        }

    }
}
