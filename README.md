# project-estimator
Estimates the end date of a project

#use case

Http method: POST
API endpoint: /api/pe/calculate
Port: 8090
Payload:

{
  "employees": {
    "id_1": {
      "name": "emp1",
      "workHours": 3,
      "vlDates": ["2026-06-25"]
    },
    "id_2": {
      "name": "emp2",
      "workHours": 7,
      "vlDates": ["2026-06-25", "2026-06-29"]
    },
    "id_3": {
      "name": "emp3",
      "workHours": 7,
      "vlDates": ["2026-06-25", "2026-06-29"]
    },
    "id_4": {
      "name": "emp4",
      "workHours": 7,
      "vlDates": []
    },
    "id_5": {
      "name": "emp5",
      "workHours": 7,
      "vlDates": []
    }
  },
  "projectStartDt": "2026-06-20",
  "estimatedWorkHours": 200.25,
  "holidaysAlongTheWay": ["2026-06-26","2026-06-24"]
}
