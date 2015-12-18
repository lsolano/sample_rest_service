# sample_rest_service
Simple RESTful Web service using JAX-RS (using jersey implemantation) on top of the Grizzly container.

## File Structure
* `/ui` Rails application used as client for the service
* `/api` Java (JAX-RS) applicaion with jersey and Grizzly

## Data model (schema)
Department => `{ "id": 99, "name": "Talent Management", "description": "Department description" }`

## Main Controller
sample_rest_service/ui/demo-rest/app/controllers/departments_controller.rb

### Actions
* index
* show
* create
* update
* set_department
