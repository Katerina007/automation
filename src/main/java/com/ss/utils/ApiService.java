package com.ss.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.parsing.Parser;

public class ApiService {
    private SSLMode sslMode;

    public enum SSLMode {
        IGNORED,
        CHECKING_SSL_ENABLED
    }

    /**
     *  Constructor for class ApiService with default IGNORED SSL certificate mode
     * @param serverURI base server URI for RestAssure
     * @param port API port for RestAssure
     * @param basePath API base path for RestAssure
     */
    public ApiService(String serverURI, int port, String basePath) {
        RestAssured.baseURI = serverURI;
        RestAssured.port = port;
        RestAssured.basePath = basePath;
        RestAssured.defaultParser = Parser.JSON;
        setSSLMode(SSLMode.IGNORED);
    }

    /**
     *  Constructor for class ApiService
     * @param serverURI base server URI for RestAssure
     * @param port API port for RestAssure
     * @param basePath API base path for RestAssure
     * @param sslMode SSLMode IGNORED - SSL certificate is ignored, CHECKING_SSL_ENABLED - Checking SSL certificate is enabled
     */
    public ApiService(String serverURI, int port, String basePath, SSLMode sslMode) {
        this(serverURI, port, basePath);
        setSSLMode(sslMode);
    }

    /**
     * Set Rest Assured to ignore SSL certificate
     * @param sslMode enum with SSL mode: IGNORED, CHECKING_SSL_ENABLED
     */
    public void setSSLMode(SSLMode sslMode) {
        switch (sslMode) {

            case IGNORED:
                RestAssured.config = RestAssured.config().sslConfig(
                        new SSLConfig().relaxedHTTPSValidation());
                break;

            case CHECKING_SSL_ENABLED:
                RestAssured.config = RestAssured.config()
//                TODO need add path to actual SSL certificate and password
                        .sslConfig(new SSLConfig().keystore("path to certificate", "password"));
                break;

        }
    }

    /**
     * Get SSL mode of RestAssured
     * @return SSLMode IGNORED - SSL certificate is ignored, CHECKING_SSL_ENABLED - Checking SSL certificate is enabled
     */
    public SSLMode getSSLMode() {
        return sslMode;
    }
}
