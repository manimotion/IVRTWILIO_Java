Feature: Phone Call Handling

  Scenario Outline: Handle gather result with digit <digit>
    Given the ivr system is operational
    When a call is made with digit "<digit>"
    Then the response should contain "<message>"

    Examples:
      | digit | message                                                                      |
      | 1     | How do you know if a bee is using your phone? The line will be .             |
      | 2     | Thanks for listening to our music! Have a great day!                         |
      | 3     | Press or say One for information about appliances, Two for customer service. |

