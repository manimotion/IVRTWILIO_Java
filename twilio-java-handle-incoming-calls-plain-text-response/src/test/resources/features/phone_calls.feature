Feature: Phone Call Handling

  Scenario: Handle incoming call
    Given the ivr system is operational
    When a call is made
    Then the response should contain "Hello. Press or say One for a joke, Two for some music, or Three for information about our products."
    And the response should contain "Thanks for calling, have a great day"

  Scenario: Handle gather result with digit One
    Given the ivr system is operational
    When a call is made with digit "1"
    Then the response should contain "How do you know if a bee is using your phone? The line will be buzzy."

  Scenario: Handle gather result with digit Two
    Given the ivr system is operational
    When a call is made with digit "2"
    Then the response should contain "Thanks for listening to our music! Have a great day!"

  Scenario: Handle gather result with digit Three
    Given the ivr system is operational
    When a call is made with digit "3"
    Then the response should contain "Press or say One for information about appliances, Two for customer service."

  Scenario: Handle product submenu with digit One
    Given the ivr system is operational
    When a call is made with digit three and submenu digit "1"
    Then the response should contain "Thanks for your interest in our appliances. We offer a wide range of high-quality products."

  Scenario: Handle product submenu with digit Two
    Given the ivr system is operational
    When a call is made with digit three and submenu digit "2"
    Then the response should contain "Our customer service team is here to assist you. Please hold for the next available representative."

  Scenario: Handle invalid product submenu option
    Given the ivr system is operational
    And use an invalid submenu option like 5
    Then the response should contain "Sorry, that's not a valid option. Please try again."