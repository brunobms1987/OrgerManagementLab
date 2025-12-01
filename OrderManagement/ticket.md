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




# Descrição
Como usuário admin, quero ser capaz de adicionar novos artigos

# Critérios de Aceitação
## Cenário: Botão criar novo artigo

**Dado** que estou logado como usuário admin  
**E** estou na página de lista de artigos  
**Então** vejo um botão "Criar artigo"  

## Cenário: Modal de novo artigo

**Dado** que estou logado como usuário admin  
**E** estou na página de lista de artigos  
**E** clico no botão "Criar artigo"  
**Então** vejo um modal com um formulário onde posso inserir todos os dados do artigo (exceto id)  
**E** o modal tem um botão cancelar e um botão enviar  
**Quando** clico no botão cancelar, o modal fecha sem salvar o artigo  
**Quando** clico no botão enviar, o modal fecha e o artigo é salvo   

## Cenário: Usuário não-admin

**Dado** que estou logado como usuário não-admin  
**E** estou na página de lista de artigos  
**Então** não vejo o botão "Criar artigo"

# Informações Técnicas
Por favor, oriente-se pelo código existente e padrões e mantenha-se o mais próximo possível.
Adicione um teste de requisição e um teste de serviço para a nova criação de artigo.