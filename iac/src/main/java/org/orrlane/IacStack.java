package org.orrlane;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.LoggingFormat;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.constructs.Construct;

import java.util.Map;
import java.util.stream.Collectors;

public class IacStack extends Stack {
    public IacStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public IacStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

//        this.getNode().tryGetContext()

        Map<String, String> env = System.getenv().entrySet().stream()
                .filter(entry -> entry.getKey().equals("HONEYCOMB_KEY"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Function function = Function.Builder.create(this, "HelloWorldFunction")
                .runtime(Runtime.PROVIDED_AL2023)
                .code(Code.fromAsset(functionPath()))
                .handler("NOP")
                .timeout(Duration.seconds(10))
                .memorySize(2048)
                .logRetention(RetentionDays.ONE_WEEK)
                .loggingFormat(LoggingFormat.JSON)
                .tracing(Tracing.ACTIVE)
                .architecture(Architecture.X86_64)
                .environment(env)
                .build();

/*
        .proxy(false)
*/
        LambdaRestApi api = LambdaRestApi.Builder.create(this, "HelloWorldApi")
                .handler(function)
                .build();
/*
        Resource helloResource = api.getRoot().addResource("demo");
        helloResource.addMethod("GET");
*/

        CfnOutput.Builder.create(this, "DemoTestApiUrl")
                .exportName("DemoTestApiUrl")
                .value(api.getUrl())
                .build();
    }

    public static String functionPath() {
        /**
         * CodeUri: ../app/target/app-0.0.0-main-SNAPSHOT-native-zip.zip
         * CodeUri: ../app/build/distributions/app-custom-0.0.0-main-SNAPSHOT.zip
         */
        return "../app/build/function.zip";
    }

}
