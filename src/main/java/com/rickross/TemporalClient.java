package com.rickross;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.net.ssl.SSLException;

public class TemporalClient {
    public static WorkflowServiceStubs getWorkflowServiceStubs()
            throws FileNotFoundException, SSLException {
        WorkflowServiceStubsOptions.Builder workflowServiceStubsOptionsBuilder =
                WorkflowServiceStubsOptions.newBuilder();

        if (!ServerInfo.getCertPath().equals("") && !"".equals(ServerInfo.getKeyPath())) {
            InputStream clientCert = new FileInputStream(ServerInfo.getCertPath());

            InputStream clientKey = new FileInputStream(ServerInfo.getKeyPath());

            workflowServiceStubsOptionsBuilder.setSslContext(
                    SimpleSslContextBuilder.forPKCS8(clientCert, clientKey).build());
        }

        // For temporal cloud this would likely be ${namespace}.tmprl.cloud:7233
        String targetEndpoint = ServerInfo.getAddress();
        // Your registered namespace.

        workflowServiceStubsOptionsBuilder.setTarget(targetEndpoint);

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        if (!ServerInfo.getAddress().equals("localhost:7233")) {
            // if not local server, then use the workflowServiceStubsOptionsBuilder
            service = WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptionsBuilder.build());
        }

        return service;
    }

    public static WorkflowClient get() throws FileNotFoundException, SSLException {
        // TODO support local server
        // Get worker to poll the common task queue.
        // gRPC stubs wrapper that talks to the local docker instance of temporal service.
        // WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        WorkflowServiceStubs service = getWorkflowServiceStubs();

        WorkflowClientOptions.Builder builder = WorkflowClientOptions.newBuilder();

        System.out.println("<<<<SERVER INFO>>>>:\n " + ServerInfo.getServerInfo());
        WorkflowClientOptions clientOptions = builder.setNamespace(ServerInfo.getNamespace()).build();

        // client that can be used to start and signal workflows
        WorkflowClient client = WorkflowClient.newInstance(service, clientOptions);
        return client;
    }
}

