Feature: Phone Call Handling subMenu

  Scenario Outline: Handle gather result with digit <digit>
    Given the ivr system is operational
    When a call is made with digit three and submenu digit "<digit>"
    Then the response should contain "<message>"

    Examples:
      | digit | message                                                                                                                                                 |
      | 1     | Thanks for your interest in our appliances. We offer a wide range of high-quality products.                                                             |
      | 2     | Our customer service team is here to assist you. Please hold for the next available representative. |
      | 5     | Sorry, that's not a valid option. Please try again.                                                                                                     |