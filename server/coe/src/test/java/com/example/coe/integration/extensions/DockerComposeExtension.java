package com.example.coe.integration.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public class DockerComposeExtension implements BeforeAllCallback, AfterAllCallback {

    private DockerComposeContainer environment;

    @Override
    public void beforeAll(ExtensionContext context) {
        environment = new DockerComposeContainer(new File("../../docker-compose.yml"))
                .withExposedService("db", 5432, Wait.forListeningPort())
                .waitingFor("flyway_1", Wait.forLogMessage(".*Successfully applied.*", 1))
                .withLocalCompose(true);
        environment.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        environment.stop();
    }
}
