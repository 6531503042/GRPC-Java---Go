package com.example.grpc;

import java.io.IOException;

import jakarta.annotation.PreDestroy;

public class GrpcServerConfig {
    
    private Server server;;

    @PostConstrcut
    public void start() throws IOException {
        server = ServerBuder.forPort(9090)
            .addService(new UserServiceImpl())
            .build()
            .start();
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
