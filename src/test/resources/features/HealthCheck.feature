Feature: Intigral Rest API Automation

  @Regression @HealthCheck
  Scenario Outline: Validate success response json schema
    Given User trigger API with api key as "<key>"
    Then  User validate the API status code is "<code>"
    And   Validate the json response with json schema save at "<path>"
    Examples:
      | key                     | code  | path               |
      | GDMSTGExy0sVDlZMzNDdUyZ | 200   | successJsonSchema  |
      | invalid                 | 403   | failureJsonSchema  |


  @Regression @urlValidator
  Scenario: Validate image array contains valid image url
    Given User trigger API with api key as "GDMSTGExy0sVDlZMzNDdUyZ"
    Then  User validate the API status code is "200"
    And   User validate image url from node "promotions-images-url"


  @Regression @Task_1
  Scenario: Validate the JSON response as per following and add assertions respectively
    Given User trigger API with api key as "GDMSTGExy0sVDlZMzNDdUyZ"
    Then  User validate the API status code is "200"
    And   User validate the json properties
      | promotions-promotionId    |
      | promotions-orderId        |
      | promotions-promoArea      |
      | promotions-promoType      |
      | promotions-showPrice      |
      | promotions-showText       |
      | promotions-localizedTexts |
    And  User validate that "promotions-showPrice" value is boolen
    And  User validate that "promotions-showText" value is boolen
    And  User validate localizedTexts property with
      | promotions-localizedTexts-ar |
      | promotions-localizedTexts-en |

  @Regression @Task_2
  Scenario: Perform assertions on promotionId and programType
    Given User trigger API with api key as "GDMSTGExy0sVDlZMzNDdUyZ"
    And   User validate the API status code is "200"
    Then  User validate that "promotions-promotionId" value is any string value
    Then  User validate that "promotions-promoType" is
          | EPISODE |
          | MOVIE   |
          | SERIES  |
          | SEASON  |

  @Regression @Task_3
  Scenario: Validate the response for a request with invalid apikey value
    Given User trigger API with api key as "invalid"
    And   User validate the API status code is "403"
    Then  User validate that "error-code" is "8001"
    Then  User validate that "error-message" is "invalid api key"
    Then  User validate that "error-requestId" is not null

