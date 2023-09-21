Feature: Youtube Test

  @Appium
  Scenario Outline: Youtube Userflow
    Given Launch the Youtube App "<udid>" and "<URL>"
    When User Click on Search to Search for an Video
    Then User Play the Video
    Examples:
      | udid | URL |
      | RZ8NA0Z723M | https://dev-in-blr-0.headspin.io:3012/v0/b048fc345f604fe9ad9c3fa2e9234b6f/wd/hub|