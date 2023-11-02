package com.gavinjin.wsdvs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gavinjin.wsdvs.mapper")
@SpringBootApplication
public class WebSpotifyDataVisualizationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSpotifyDataVisualizationSystemApplication.class, args);
    }

}
