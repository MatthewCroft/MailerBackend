package com.croft.mailerbackend.ml;

import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntime;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntimeClient;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointRequest;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointResult;
import com.croft.mailerbackend.exceptions.BARTException;
import com.croft.mailerbackend.models.ModelRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.amazonaws.regions.Regions.US_EAST_1;

@Service
public class BARTService {
    private AmazonSageMakerRuntime sageMakerRuntime = AmazonSageMakerRuntimeClient.builder()
            .withRegion(US_EAST_1)
            .build();

    private String modelName = "huggingface-pytorch-inference-2024-05-23-00-15-19-731";

    public String summarizeText(String text) throws BARTException {
        ModelRequest article = new ModelRequest(text);

        String articleJson;
        try {
            articleJson = new ObjectMapper().writeValueAsString(article);
        } catch (JsonProcessingException exception) {
            throw new BARTException("Unable to process article into json");
        }

        InvokeEndpointRequest request = new InvokeEndpointRequest()
                .withEndpointName(modelName)
                .withContentType(MediaType.APPLICATION_JSON_VALUE)
                .withBody(ByteBuffer.wrap(articleJson.getBytes()));

        InvokeEndpointResult result = sageMakerRuntime.invokeEndpoint(request);
        return StandardCharsets.UTF_8.decode(result.getBody()).toString();
    }
}
