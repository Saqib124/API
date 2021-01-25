package com.intigral.api.steps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.intigral.api.utilities.Helper.schema_base_path;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BaseSteps extends ScenarioSteps {

    private EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

    /*Base url for all the API*/
    String base_url = environmentVariables.getProperty("base_url");

    /*Contains the API response, Present in base class so that all children can access the response from anywhere*/
    Response response;

    /**
     * Validate the json schema from locally saved schema.
     *
     * @param  filePath  contains the schema file name
     */
    public void validateJsonSchema(String filePath) throws FileNotFoundException, JsonProcessingException {
        String schemaPath = schema_base_path + filePath + ".json"; //concatenate the file name with base file path

        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(schemaPath));
        JsonObject object = JsonParser.parseReader(bufferedReader).getAsJsonObject();

        ResponseBody body = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

        JsonNode json = objectMapper.readTree(body.asString());
        JsonSchema schema = schemaFactory.getSchema(object.toString());
        Set<ValidationMessage> validationResult = schema.validate(json);
        boolean isSchemaCorrect = true;

        if (validationResult.isEmpty()) {
            System.out.println("no validation errors :-)");
        } else {
            AtomicReference<String> issue = new AtomicReference<>("");
            validationResult.forEach(vm -> issue.set(issue + vm.getMessage() + "\n"));
            Serenity.recordReportData().withTitle("Issue found for:").andContents(String.valueOf(issue));
            isSchemaCorrect = false;
        }
        assertThat("Schema validation is failing", isSchemaCorrect, is(true));
    }
}
