Feature: Patients service features
 
Scenario: Calling patients service
  Given patients service exists
  When I call patients service
  Then it should have been a success