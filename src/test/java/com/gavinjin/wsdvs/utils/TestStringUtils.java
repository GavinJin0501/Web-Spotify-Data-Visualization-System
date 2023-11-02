package com.gavinjin.wsdvs.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestStringUtils {
    @Test
    public void testIsAnyBlank() {
        assert StringUtils.isAnyBlank();
        assert !StringUtils.isAnyBlank("aaa");
        assert StringUtils.isAnyBlank("aaa", null);
        assert StringUtils.isAnyBlank("aaa", "");
    }
}
