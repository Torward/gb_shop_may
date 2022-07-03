package ru.gb.gbapimay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ConfigurationProperties(prefix = "gb.api")
public class GbApiProperties {
    private Connection connection;
    private EndPoint endPoint;

    @Getter
    @Setter
    public static class EndPoint{
        private String ManufacturerUrl;
    }

    @Getter
    @Setter
    public static class Connection {
        private long period;
        private long maxPeriod;
        private int maxAttempts;
        long connectTimeout;
        TimeUnit connectTimeoutUnit;
        long readTimeout;
        TimeUnit readTimeoutUnit;
        boolean followRedirects;
    }
}
