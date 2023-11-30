package com.example.coe.controllers.integration.extensions;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public class DockerComposeExtension implements BeforeAllCallback {

    private DockerComposeContainer environment;

    @Override
    public void beforeAll(ExtensionContext context) {
        environment = new DockerComposeContainer(new File("../../docker-compose.yml"))
                .withExposedService("db", 5432, Wait.forListeningPort())
                .waitingFor("flyway_1", Wait.forLogMessage(".*Successfully applied.*", 1));
        environment.start();
    }
}
