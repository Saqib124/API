[**Cucumber**](https://cucumber.io/) **-JVM:** Cucumber is a tool based on Behavior Driven Development (BDD) framework which is used to write acceptance tests for web application. It allows automation of functional validation in easily readable and understandable format (like plain English) to Business Analysts, Developers, Testers, etc.

[**Serenity**](http://www.thucydides.info/#/) **:** Serenity BDD helps you write better, more effective automated acceptance tests, and use these acceptance tests to produce world-class test reports and living documentation

## Points to take care for “Feature files”:
**Avoid long descriptions**
Features should have a short and sensible title and description. Features with long descriptions can be 
-   boring to read
-   Put off stakeholders.

Generally features should be a sentence describing the scope and context.

### Chose a single format all across your features
Decide which formats for you are going to use for writing features. You should choose an appropriate format and keep it consistent across your feature files. So new person to the project can easily understand the feature and context.

####  Template

Feature: [One line describing the story]
[Optional — Feature description]

Scenario: [One line describing the scenario]

Given [context]

And [some more context]…

When [event]

Then [outcome]

And [another outcome]…


##Example
Scenario: Buy last coffee

Given there are 1 coffees left in the machine

And I have deposited 1$

When I press the coffee button

Then I should be served a coffee

## Points to take care for “Background”
Whenever possible, keep using background for the shared steps which are required for every scenario in the feature file.

## Keep your background short
Background reduces the repetitive steps and proper use of background can avoid lots of duplication in the feature file. The background step should not be longer than 4 lines and it should be acting as pre-requisite for each scenario within the feature file. Whilst reading the scenarios in the feature files users may not keep the background in mind so let it be something very generic e.g. “Given user has logged in”

## Don’t include technical stuff in the background

We should avoid using technical language or actions in the background step. For example, we shouldn’t have a background step that starts/stops  [Apache](http://en.wikipedia.org/wiki/Apache_HTTP_Server), clears the cache, truncates database etc. as user don’t care about these. When you need to do technical actions, these and can be implemented in the step definitions or in the before hook instead.

**Good Example:**
Background: Brand is available
Given BBC TV brand is available to public or coming soon

**Bad Example:**
Background: Brand is available
Given group entity for the brand pid available in the PIPS datastore.

## Never tag the background
You should never tag the background. We can tag scenarios and features but not the background.

## Never use a background for feature files which contain only one scenario.
It doesn’t make sense to use background for the features, which has only one scenario as we can include the details in the GIVEN step directly.

## Don’t use both background and before hook
Sometimes there might be duplication of the same context where we have before hook in the code and background in the feature file. Make sure you have used either background or before hook and not both.

## Points to take care for “Scenarios and steps”:
Gherkin provides a way to use a ‘scenario’ to deal with single examples, and ‘scenario outline’ to deal with multiple examples.

## Think of Scenario Or Scenario Outline
Decide how many examples we think of for the particular scenario, if we have a scenario that can have only one example then we can use ‘scenario’ otherwise we need use ‘scenario outline’ to cover many examples using a table. Good use of scenario outlines will not only avoid duplication of steps but also makes scenarios more readable and maintainable.

## Keep scenarios short by hiding implementation details
Avoid long and descriptive scenarios with lots of ‘AND’’ steps as this makes scenarios brittle as well as less readable. We should not include implementation details in the steps but we should have enough description to understand the context of the scenario. Never use technical details or the implementation details in the scenario steps like using  [XPath](http://en.wikipedia.org/wiki/XPath)  or CSS selector in the step.

## Use Given-When-Then in the right order
We should write scenarios with GIVEN-WHEN-THEN sequence with proper use of AND and BUT.

Always start the scenario with the GIVEN step even you have used GIVEN in the ‘BACKGROUND’ section. Scenarios become less readable if we miss the order of GIVEN-WHEN-THEN.

## Use declarative steps rather than imperative

Imperative steps mean unnecessarily exposing lots of internal details of the system. An example of the imperative steps would be a login scenario as follows:

**Good Example (Declarative):**

Scenario: Login

Given I have logged into the system

Then I should see the ‘Welcome’ message

As you can see, using declarative steps in the scenario makes it shorter and more readable.

**Bad Example (Imperative):**

Scenario: Login

Given I am on the login page

When I fill “username” with “ABC”

And I fill password with “XYZ”

And I checked the “Remember Me” checkbox

And I click on the “Submit” button

Then I should log into the system

And I should see then ‘Welcome’ message

Instead of writing too many steps like this, we can write couple of declarative steps as mentioned in “Good Example” section.

## Use only key examples in the scenario outline
Using a scenario outline gives us the opportunity to include a table in the scenario and check many examples. Although this is a good approach for covering many scenarios at a time, we need to use only the most useful examples. We should not use more than 10 examples in a scenario outline.

## Make Scenarios Atomic
Scenario should run independently without any dependencies on other scenarios. This will help us debug faster when something goes wrong.

## Avoid Conjunctive steps
Gherkin has an ‘AND’ step and we can make use of it. Avoid steps which include the word ‘and’ not at the start of the step. E.g:

**Example of Conjuctive steps**
Scenario: Homepage
Given I am on the homepage and scrolled page down

## Cover both the happy and non-happy paths
Scenarios should cover both happy and non-happy paths. A happy path is a straightforward user journey while a non-happy path covers different edge cases around the happy path, including invalid inputs etc.

Make sure that scenarios which we implement should have business values. (ROI)

## Be DRY: Refactor and Reuse Step Definitions
Especially look for the opportunity to make reusable step definitions that are not feature specific. As a project proceeds, you should be accumulating a library of step definitions. Ideally, you will end up with step definitions that can be used across projects.

## Points to take care for “Tags”:
Tagging allows us to organize features and scenarios in the BDD project. If the feature files are across many different directories then tags help to filter specific scenarios and features. For example, if we have a set of scenarios for BBC Radio then we could tag those scenarios by using “@radio” tag. That way, whenever we want to see all of the scenarios for BBC Radio, we can easily search for the @radio tag.

## How to decide name of the tag
In order to filter the scenarios by categories, we need to use smart tag names. Here are few suggestions for tagging
Use only relevant tags when there is a necessity else managing tags will become add-on task and overhead.)
Frequency of Execution: @daily, @hourly, @nightly

Dependencies: @database, @fixtures, @local, @proxy

Progress: @wip, @todo, @implemented, @blocked

Level: @functional, @acceptance, @smoke, @sanity

Environment: @integration, @test, @stage, @live

## Tag your feature smartly

We should be aware of the fact that we can tag individual scenarios, so decide what value would be added by tagging an entire ‘feature’. It may be useful in some instances.

For example, you could tag a feature with the story number in a bug tracking system like JIRA.Ex:   
@Jira-story #PROJ-33

## Don’t tag a scenario with the same tag used for tagging feature
If you tag an entire feature then any scenarios within the feature automatically inherit this tag. Don’t use too many unnecessary tags in the project, as this will overcomplicated your feature files.

## Remove @wip tag once you finished working on it.
Manage work in progress scenarios by using the @wip tag. Make sure you ignore the @wip tag for the scenarios running on continuous integration.

**References:**
-   [https://blog.grandcentrix.net/gherkin-guidelines-and-best-practices/](https://blog.grandcentrix.net/gherkin-guidelines-and-best-practices/)
-   [http://docs.behat.org/en/latest/guides/1.gherkin.html](http://docs.behat.org/en/latest/guides/1.gherkin.html)
-   [http://www.bbc.co.uk/blogs/internet/entries/ff14236d-098a-3565-b678-ff4ba5776a5f](https://blog.engineyard.com/2009/15-expert-tips-for-using-cucumber)
-   [https://blog.engineyard.com/2009/15-expert-tips-for-using-cucumber](https://blog.engineyard.com/2009/15-expert-tips-for-using-cucumber)
