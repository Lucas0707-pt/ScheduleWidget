Feature: Open pages
  The pages should appear when we click on the respective menu options

  # Assumes the user has previously logged in in the app
  # and selected the option "manter sessão iniciada".
  # A custom step is used to check that the user is logged in.


  Scenario: Access the "Horário" page
    Given I am logged in
    And I open the drawer
    And I tap the "key_Horário" button
    Then I expect the text "Horário" to be present

  Scenario: Access the "Mapa de Exames" page
    Given I am logged in
    And I open the drawer
    And I tap the "key_Mapa de Exames" button
    Then I expect the text "Exames" to be present

  Scenario: Access the "Autocarros" page
    Given I am logged in
    And I open the drawer
    And I tap the "key_Autocarros" button
    Then I expect the text "Autocarros" to be present

  Scenario: Submit a bug report
    Given I am logged in
    And I open the drawer
    And I tap the "key_Bugs e Sugestões" button
    And I fill the "key_bug_form_Título" field with "Texto 1"
    And I fill the "key_bug_form_Descrição" field with "Texto 2"
    And I pause for 1 seconds
    And I tap the "key_bug_consent" button
    And I pause for 1 seconds
    And I tap the "key_bug_form_submit" button
    And I pause for 1 seconds
    Then I expect the text "Texto 1" to be absent