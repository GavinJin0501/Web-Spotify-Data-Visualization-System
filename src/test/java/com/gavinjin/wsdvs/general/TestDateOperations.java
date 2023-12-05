package com.gavinjin.wsdvs.general;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class TestDateOperations {
    @Test
    void testTimeStamp() throws Exception {
        Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse("2023-03-12T02:58:02Z")).truncatedTo(ChronoUnit.MILLIS);
        Timestamp timestamp = Timestamp.from(instant);
        System.out.println(timestamp);
    }
}
