# ShowHistory 
Example Java app to display the event history for a given workflow and run id.

## Build Instructions
```shell
./gradlew build
```

## To Run

To execute this application, set your environment variables to point to your Temporal Server. For example:

```shell
export TEMPORAL_NAMESPACE=yournamespace
export TEMPORAL_ADDRESS=yournamespace.tmprl.cloud:7233
export TEMPORAL_CERT_PATH=/path/to/cert.pem
export TEMPORAL_KEY_PATH=/path/to/key.key
```
Then run the application like this:

```shell
java -jar build/libs/showhistory-1.0-SNAPSHOT.jar <workflow-id> <run-id>
```

