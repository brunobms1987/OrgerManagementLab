# Description
As admin user I want to be able to add new articles

# Acceptance Criteria
## Scenario: Create new article button

**Given** I am logged in as admin user  
**And** I am on the article list page  
**Then** i see a button "Create article"  

## Scenario: New article modal

**Given** I am logged in as admin user  
**And** I am on the article list page  
**And** I click on the "Create article" button  
**Then** I see a modal with a form where i can enter all article data (except id)  
**And** the modal has a cancel and a submit button  
**When** clicking on the cancel button, the modal closes without saving the article  
**When** clicking on the submit button, the modal closes and the article is saved   

## Scenario: Non-Admin user

**Given** I am logged in as non-admin user  
**And** I am on the article list page  
**Then** I do not see the "Create article" button

# Technical information
Please orientate yourself on the existing code and patterns and stay as close as possible.
Add a request and a service test for the new article creation.
