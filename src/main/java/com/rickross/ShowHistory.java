package com.rickross;

import com.google.protobuf.util.JsonFormat;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.workflowservice.v1.GetWorkflowExecutionHistoryRequest;
import io.temporal.api.workflowservice.v1.GetWorkflowExecutionHistoryResponse;
import io.temporal.serviceclient.WorkflowServiceStubs;

/*
 * Environment variables to use
 * TEMPORAL_CERT_PATH
 * TEMPORAL_KEY_PATH
 * TEMPORAL_NAMESPACE
 * TEMPORAL_ADDRESS
 *
 * Be sure you pass in the workflow ID and Run ID as parameters to the application.
 */
public class ShowHistory {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Required arguments: workflow-id run-id");
            System.exit(0);
        }

        String workflowId = args[0];
        String runId = args[1];
        System.out.format("Attempting to show workflow history for workflow ID %s, run ID %s",
                workflowId, runId);

        try {
            //WorkflowClient client = TemporalClient.get();
            WorkflowServiceStubs service = TemporalClient.getWorkflowServiceStubs();

            // retrieve the workflow execution
            WorkflowExecution execution = WorkflowExecution.newBuilder()
                    .setWorkflowId(workflowId)
                    .setRunId(runId)
                    .build();

            // build a execution history request
            GetWorkflowExecutionHistoryRequest request =
                    GetWorkflowExecutionHistoryRequest.newBuilder()
                            .setNamespace(ServerInfo.getNamespace())
                            .setExecution(execution)
                            .build();

            GetWorkflowExecutionHistoryResponse result =
                    service.blockingStub().getWorkflowExecutionHistory(request);
            String jsonHistory = JsonFormat.printer().print(result.getHistory());
            System.out.println("History as JSON: ");
            System.out.println(jsonHistory);

            System.out.println("Complete!");
        }
        catch(Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
}
