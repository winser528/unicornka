package com.fit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class UnicornApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(UnicornApplication.class, args);
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = emptyToDefault(System.getenv("POST"), run.getEnvironment().getProperty("server.port"));

        log.info("\n---------------------------------------------------------\n" +
                "Application Admin is running! Access URLs:\n\t" +
                String.format("Local: \t\thttp://localhost:%s/\n\tExternal:\t%s://%s:%s/", port, "http", ip, port) +
                "\n-----------------页面请部署 admin-web----------------------");
    }

    private static String emptyToDefault(String str, String defaultStr) {
        return (str == null || str.isEmpty()) ? defaultStr : str;
    }
}
