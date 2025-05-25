package hu.balazskokay.poc.human.readable.duration.kata.core.port.in;

import org.jetbrains.annotations.NotNull;

public interface FormatDurationToReadableUseCase {

    String formatDuration(@NotNull final Integer durationInSeconds);

}
