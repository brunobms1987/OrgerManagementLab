## Start the application
To run the application locally run
`docker compose up` (or start the containers manually the way you usually run docker)
This will start the database.

Then start the Java application like you would normally do.
To start the frontend run `ng serve` inside of the OrderManagementFrontend folder.

The frontend will be available at http://localhost:4200

## General Information
The frontend is really bare bones. Don't worry about that. In our real projects we actually care heavily about the UX,
but for this sample application styling does not matter. So just make sure the features work, no need to spend time on styling.

## Users
Authentification for this application was simplified to a http basic auth.
There are two users:

- normal user
  - username: user
  - password: password
- admin user
  - username: admin
  - password: password

The normal user is only allowed to read the data. The admin user can read and write.

## re-generate frontend services
There is a script to automatically create services to access the backend api from the openapi description
of the backend api.
If you make any changes to the api that you want to use in the frontend, you have to re-generate them.
To do that, start the Java application and then inside of the OrderManagementFrontend folder run

the powershell script `generate-frontend-services.ps1`
or run the bash script `generate-frontend-services.sh`

depending on your operating system.

## Finishing up
When you are done, please make one commit with all your changes, zip the Project and send it to us.
