package com.intigral.api.bindings;

import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The class HealthCheck is a glu between cucumbers file and steps.
 * Calling methods of HealthCheckSteps and making code more readable and understandable
 */
public class HealthCheck extends BaseBinding{

    @Given("User trigger API with api key as {string}")
    public void triggerAPIWithApiKeyAs(String apiKey) {
        healthCheckSteps.triggerAPIRequest(apiKey);
    }

    @Then("User validate the API status code is {string}")
    public void userValidateTheAPIStatusCodeIs(String statusCode) {
        healthCheckSteps.validateStatusCode(statusCode);
    }

    @And("User validate the json properties")
    public void userValidateTheJsonProperties(DataTable properties) {
        List<String> details = properties.asList(String.class);
        for (String property : details) {
            healthCheckSteps.validateJsonProperty(property);
        }
    }

    @And("User validate localizedTexts property with")
    public void userValidateLocalizedTextsPropertyWith(DataTable properties) {
        List<String> details = properties.asList(String.class);
        for (String property : details) {
            healthCheckSteps.validateLocalizedTextsPropertyProperty(property);
        }
    }

    @And("User validate that {string} value is boolen")
    public void userValidateThatValueIsBoolen(String property) {
        healthCheckSteps.checkPropertyTypeIsBoolen(property);
    }


    @Then("User validate that {string} value is any string value")
    public void userValidateThatValueIsAnyStringValue(String property) {
        healthCheckSteps.checkPropertyTypeIsString(property);
    }

    @Then("User validate that programType is")
    public void userValidateThatProgramTypeIs(DataTable programTable) {
    }

    @Then("User validate that {string} is")
    public void userValidateThatIs(String property, DataTable programTable) {
        List<String> details = programTable.asList(String.class);
        healthCheckSteps.validateProgramType(property, details);
    }

    @Then("User validate that {string} is {string}")
    public void userValidateThatIs(String keys, String code) {
        healthCheckSteps.validateErrorTheResponseValue(keys,code);
    }

    @Then("User validate that {string} is not null")
    public void userValidateThatIsNotNull(String keys) {
        healthCheckSteps.validateThatRequestIdIsNotNull(keys);
    }

    @And("Validate the json response with json schema save at {string}")
    public void validateTheJsonResponseWithJsonSchemaSaveAt(String filePath) throws FileNotFoundException, JsonProcessingException {
        healthCheckSteps.validateJsonSchema(filePath);
    }

    @And("User validate image url from node {string}")
    public void userValidateImageUrlFromNode(String keys) {
        healthCheckSteps.validateImageUrl(keys);
    }
}
