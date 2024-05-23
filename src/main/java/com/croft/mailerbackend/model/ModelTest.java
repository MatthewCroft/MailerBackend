package com.croft.mailerbackend.model;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.sagemaker.AmazonSageMakerClientBuilder;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntime;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntimeClient;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointRequest;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointResult;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static com.amazonaws.regions.Regions.US_EAST_1;

@RestController(value = "/model")
public class ModelTest {
    AmazonSageMakerRuntime test = AmazonSageMakerRuntimeClient.builder()
            .withRegion(US_EAST_1)
            .build();

    class ModelRequest {
        String inputs;

        public ModelRequest(String inputs) {
            this.inputs = inputs;
        }

        public String getInputs() {
            return inputs;
        }

        public void setInputs(String inputs) {
            this.inputs = inputs;
        }
    }

    @GetMapping(path = "/summary")
    public String summarizeText(@RequestParam(value = "text", required = false) String body) throws Exception {
        ModelRequest article = new ModelRequest("The tower is 324 metres (1,063 ft) tall, about the same height as an 81-storey building, and the tallest structure in Paris. Its base is square, measuring 125 metres (410 ft) on each side. During its construction, the Eiffel Tower surpassed the Washington Monument to become the tallest man-made structure in the world, a title it held for 41 years until the Chrysler Building in New York City was finished in 1930. It was the first structure to reach a height of 300 metres. Due to the addition of a broadcasting aerial at the top of the tower in 1957, it is now taller than the Chrysler Building by 5.2 metres (17 ft). Excluding transmitters, the Eiffel Tower is the second tallest free-standing structure in France after the Millau Viaduct.");
        String articleJson = new ObjectMapper().writeValueAsString(article);
        InvokeEndpointRequest request = new InvokeEndpointRequest()
                .withEndpointName("huggingface-pytorch-inference-2024-05-23-00-15-19-731")
                .withContentType("application/json")
                .withBody(ByteBuffer.wrap(articleJson.getBytes()));

        InvokeEndpointResult result = test.invokeEndpoint(request);

        return StandardCharsets.UTF_8.decode(result.getBody()).toString();
    }
}
