package com.intigral.api.steps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.intigral.api.utilities.Helper;
import io.restassured.response.ResponseBody;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import java.io.FileNotFoundException;
import java.util.List;


public class HealthCheckSteps extends BaseSteps {

    /**
     * Trigger the API call using SerenityRest.
     * SerenityRest is wrapper over Rest Assured API handler.
     *
     * @param  apiKey  contains API key
     */
    @Step
    public void triggerAPIRequest(String apiKey){
        response = SerenityRest.get(base_url,apiKey);
    }

    @Step
    public void validateStatusCode (String statusCode){
//        ResponseBody body = response.getBody();
//        System.out.println("Response Body is: " + body.asString());
        Assert.assertEquals( "Status code mismatch: ",Integer.parseInt(statusCode) , response.getStatusCode());
    }

    /**
     * Validate the any particular key in each node of the json.
     *
     * @param  property  contains the json key in format e.g 'abc-xyz' where abc: parentKey xyz: ChildKey
     */
    @Step
    public void validateJsonProperty(String property){
        String[] parts = property.split("-"); //promotions-promotionId
        JSONArray responseArray = getJsonArrayFromTheResponse(parts[0]);
        Assert.assertTrue(responseArray.length()!=0);
        for(Object item: responseArray ){
            JSONObject object = (JSONObject)item;
            Assert.assertTrue(parts[0] + " json array don't contains key " + parts[1],object.has(parts[1]));
        }
    }

    /**
     * Validate that each LocalizedTexts should contain ar and en values.
     *
     * @param  property  contains the json key in format e.g 'promotions-localizedTexts-ar' where promotions: parentKey,
     *                   localizedTexts: childKey, ar: subChildKey
     */
    @Step
    public void validateLocalizedTextsPropertyProperty(String property) {
        String[] parts = property.split("-");
        JSONArray responseArray = getJsonArrayFromTheResponse(parts[0]);
        for(Object item: responseArray ){
            JSONObject object = (JSONObject)item;
            JSONObject localizedObj = (JSONObject)object.get(parts[1]);
            Assert.assertTrue("Key:"+ parts[2] + " not found under " + parts[1],localizedObj.has(parts[2]));
        }
    }

    /**
     * Validate that PropertyType contains only boolen value .
     *
     * @param  property  contains the json key in format e.g 'promotions-showPrice' where promotions: parentKey,
     *                   showPrice: childKey
     */
    @Step
    public void checkPropertyTypeIsBoolen(String property) {
        String[] parts = property.split("-");

        JSONArray responseArray = getJsonArrayFromTheResponse(parts[0]);
        for(Object item: responseArray ){
            JSONObject object = (JSONObject)item;
            boolean typeCheck = object.get(parts[1]) instanceof Boolean;
            Assert.assertTrue(parts[1] + " dataType is not Boolean",typeCheck);
        }
    }

    /**
     * Validate that PropertyType contains only String value .
     *
     * @param  property  contains the json key in format e.g 'promotions-showPrice' where promotions: parentKey,
     *                   showPrice: childKey
     */
    @Step
    public void checkPropertyTypeIsString(String property) {
        String[] parts = property.split("-");
        JSONArray responseArray = getJsonArrayFromTheResponse(parts[0]);

        boolean typeCheck = true;
        for(Object item: responseArray ){
            JSONObject object = (JSONObject)item;
            typeCheck = object.get(parts[1]) instanceof String;
            if (!typeCheck)
                break;
        }
        Assert.assertTrue(parts[1] +" dataType is not String", typeCheck);
    }

    /**
     * Validate that ProgramType value in each object of json array.
     *
     * @param  property  contains the json key in format e.g 'promotions-promoType' where promotions: parentKey,
     *                   promoType: childKey
     * @param  programType contains of all programs e.g EPISODE, MOVIE, SERIES, SEASON
     */
    @Step
    public void validateProgramType(String property, List<String> programType) {
        String[] parts = property.split("-");
        JSONArray responseArray = getJsonArrayFromTheResponse(parts[0]);

        for(Object item: responseArray ){
            JSONObject object = (JSONObject)item;
            String type = object.get(parts[1]).toString();
            Assert.assertTrue("Program: '" + type+ "' is invalid programType",Helper.containsCaseInsensitive(type,programType));
        }
    }

    /**
     * Validate that error objects.
     *
     * @param  keys  contains the json key in format e.g 'error-message' where error: parentKey,
     *                   promoType: message
     * @param  code contains the value which we want to validate in the response
     */
    @Step
    public void validateErrorTheResponseValue(String keys, String code) {
        String[] parts = keys.split("-");
        ResponseBody body = response.getBody();
        String jsonString = body.asString();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject parent = (JSONObject)jsonObject.get(parts[0]);
        Object child = parent.get(parts[1]);
        String value = child.toString();
        Assert.assertTrue(value.equalsIgnoreCase(code));
    }

    /**
     * Validate that ThatRequestId should never be empty in error.
     *
     * @param  keys  contains the json key in format e.g 'error-message' where error: parentKey,
     *                   promoType: message
     */
    @Step
    public void validateThatRequestIdIsNotNull(String keys) {
        String[] parts = keys.split("-");
        ResponseBody body = response.getBody();
        String jsonString = body.asString();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject parent = (JSONObject)jsonObject.get(parts[0]);
        Object child = parent.get(parts[1]);
        String value = child.toString();
        Assert.assertTrue(!value.isEmpty());
    }

    @Step
    public void validateJsonSchema(String filePath) throws FileNotFoundException, JsonProcessingException {
        super.validateJsonSchema(filePath);
    }

    /**
     * Validate that image urls are valid or not.
     *
     * @param  keys  contains the json key in format e.g 'promotions-images-url' where promotions: parentKey,
     *                   images: childKey, url: subChildKey
     */
    @Step
    public void validateImageUrl(String keys) {
        String[] parts = keys.split("-");
        JSONArray array = getJsonArrayFromTheResponse(parts[0]);
        for(Object obj1: array ){
            JSONObject imageObj = (JSONObject)obj1;
            JSONArray imagesList = (JSONArray)imageObj.get(parts[1]);
            for(Object obj2: imagesList ){
                JSONObject urlObj = (JSONObject)obj2;
                String urlStr = urlObj.get(parts[2]).toString();
                Assert.assertTrue(Helper.urlValidator(urlStr));
            }
        }
    }

    /*--------------------------------------------------Json Parsing--------------------------------------------------*/
    /**
     * Parse the API response.
     *
     * @param  headNode  contains the key against which json array is saved
     * @return JSONArray which contains all the nodes
     */
    private JSONArray getJsonArrayFromTheResponse(String headNode){
        ResponseBody body = response.getBody();
        String jsonString = body.asString();
        JSONObject jsonObject = new JSONObject(jsonString);
        Assert.assertTrue("Response don't contains key: "+ headNode,jsonObject.has(headNode));
        Object resultObj = jsonObject.get(headNode);
        Assert.assertTrue(resultObj instanceof JSONArray);
        return (JSONArray) resultObj;
    }

}
