package hu.balazskokay.poc.human.readable.duration.kata.core.service;

import hu.balazskokay.poc.human.readable.duration.kata.core.port.in.FormatDurationToReadableUseCase;
import hu.balazskokay.poc.human.readable.duration.kata.core.port.in.NegativeNumberException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
final class DurationMappingService implements FormatDurationToReadableUseCase {

    @Override
    public String formatDuration(@NotNull final Integer durationInSeconds) {
        log.debug("Mapping duration to readable format: {}", durationInSeconds);
        validateDuration(durationInSeconds);
        return format(durationInSeconds);
    }

    private void validateDuration(@NotNull final Integer durationInSeconds) {
        log.trace("Validating durationInSeconds: {}", durationInSeconds);
        if (durationInSeconds < 0) {
            throw new NegativeNumberException("Duration must be non-negative");
        }
    }

    private String format(@NotNull final Integer durationInSeconds) {
        log.trace("Converting duration to readable format: {}", durationInSeconds);
        if (durationInSeconds == 0) {
            return "now";
        }

        final var seconds = durationInSeconds % 60;
        final var minutes = (durationInSeconds / 60) % 60;
        final var hours = (durationInSeconds / (60 * 60)) % 24;
        final var days = (durationInSeconds / (60 * 60 * 24)) % 365;
        final var years = durationInSeconds / (60 * 60 * 24 * 365);

        final var parts = new ArrayList<>();
        if (years > 0) {
            parts.add(createPart(years, "year"));
        }
        if (days > 0) {
            parts.add(createPart(days, "day"));
        }
        if (hours > 0) {
            parts.add(createPart(hours, "hour"));
        }
        if (minutes > 0) {
            parts.add(createPart(minutes, "minute"));
        }
        if (seconds > 0) {
            parts.add(createPart(seconds, "second"));
        }

        return concatParts(parts);
    }

    private String createPart(final Integer value, final String unit) {
        return value + " " + unit + (value > 1 ? "s" : "");
    }

    private String concatParts(ArrayList<Object> parts) {
        log.trace("Concatenating parts: {}", parts);
        final var result = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                result.append(i == parts.size() - 1 ? " and " : ", ");
            }
            result.append(parts.get(i));
        }
        return result.toString();
    }
}
