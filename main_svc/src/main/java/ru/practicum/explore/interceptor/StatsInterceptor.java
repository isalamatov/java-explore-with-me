package ru.practicum.explore.interceptor;

import feign.Feign;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.practicum.explore.client.StatsClient;
import ru.practicum.explore.stats.dto.EndpointHitDto;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class StatsInterceptor implements HandlerInterceptor {
    @Value("${stats.server.addr}")
    private String statsServerAddr;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String serviceIdentifier = "ewm-main-service";

    private final List<String> controlledEndpoints = List.of("/events");

    private StatsClient statsClient;

    @PostConstruct
    private void postConstruct() {
        this.statsClient = Feign.builder()
                .encoder(new Encoder.Default())
                .target(StatsClient.class, initServerAddr());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        for (String controlledEndpoint : controlledEndpoints) {
            if (uri.startsWith(controlledEndpoint)) {
                informStatsServer(serviceIdentifier, request);
            }
        }
        return true;
    }

    public void informStatsServer(String serviceIdentifier, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ipAddr = request.getRemoteAddr();
        String timestamp = LocalDateTime.now().format(formatter);
        EndpointHitDto endpointHitDto = new EndpointHitDto(null, serviceIdentifier, uri, ipAddr, timestamp);
        String body = endpointHitDto.toString();
        log.info("Sending endpoint hit DTO: {}", body);
        try {
            statsClient.hit(body);
        } catch (Exception exception) {
            log.info("Some problems were experienced while sending statistics data: '\n' {}", exception.getMessage());
        }
    }

    private String initServerAddr() {
        if (statsServerAddr != null && !statsServerAddr.isBlank()) {
            return statsServerAddr;
        } else {
            log.info("Statistics server address not defined");
            throw new RuntimeException("Statistics server address not defined");
        }
    }
}
