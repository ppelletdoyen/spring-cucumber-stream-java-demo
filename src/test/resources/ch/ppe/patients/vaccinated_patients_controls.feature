Feature:  Vaccinated patients controls

Scenario: At least one patient must been vaccinated against tetanus
  Given the patients list exist
  When I want to know if at least one patient has been vaccinated against tetanus
  Then a vaccinated patient against tetanus must exist
  
 Scenario: All patients must be vaccinated against rabies
  Given all the 5 patients must exist
  When I want to verify that all patients are vaccinated against rabies
  Then all patients are vaccinated against rabies
  
 Scenario: Only 4 of the patients must be vaccinated against whooping cough
  Given all the 5 patients must exist
  When I want to verify that 4 patients are vaccinated against whooping cough
  Then 4 patients are vaccinated against whooping cough