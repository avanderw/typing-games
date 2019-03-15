Feature: Typing
  Scenario: Good input
    Given the text "the cow jumped over the moon"
    When I type "the cow ju"
    Then the tracked index should be 10

  Scenario: Bad input
    Given the text "the cow jumped over"
    When I type "thehco cowkguy"
    Then the tracked index should be 7
