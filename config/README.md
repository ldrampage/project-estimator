# guide
config folder
    - contains the application.yml
    - you can change the port if you need a custom port

project-estimator-0.0.1.jar
    - contains the business logic    

project-estimator-run.bat
    - script to run the project-estimator-0.0.1.jar

how to use
    - double click run.bat
    - use postman or curl to trigger the endpoint

http method: POST
endpoint: /api/pe/calculate
request payload:

{
  "employees": {
    "id_1": {
      "name": "Lyndon",
      "workHours": 4,
      "vlDates": ["2026-06-25"]
    },
    "id_2": {
      "name": "Karl",
      "workHours": 7,
      "vlDates": ["2026-06-25", "2026-06-29"]
    },
    "id_3": {
      "name": "Vic",
      "workHours": 5,
      "vlDates": ["2026-06-25", "2026-06-29"]
    },
  },
  "projectStartDt": "2026-06-20",   
  "estimatedWorkHours": 200.25,
  "holidaysAlongTheWay": ["2026-06-26","2026-06-24"]
}

