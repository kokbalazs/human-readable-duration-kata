package hu.balazskokay.poc.human.readable.duration.kata.api.controller;

import hu.balazskokay.poc.human.readable.duration.kata.core.port.in.FormatDurationToReadableUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DurationController {

    private final FormatDurationToReadableUseCase formatDurationToReadableUseCase;

    @GetMapping("/duration")
    public String convertDurationToText(@NotNull final Integer durationInSeconds) {
        log.info("Received request to convert duration: {}", durationInSeconds);
        return formatDurationToReadableUseCase.formatDuration(durationInSeconds);
    }

}
