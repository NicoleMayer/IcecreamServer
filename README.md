# IcecreamServer
## Overview

It uses Spring Boot framework to realize HTTP connection and database connection.

## API list

### login

URL example: `http://server_ip/login?phone=123&password=123`

| Name | Type | Description |
| - | - | - |
| phone | parameter | necessary |
| password |  parameter | necessary |
| status | return | not registered / password wrong / valid |

### register

URL example: `http://server_ip/register?phone=123&phoneNumber=123&password=123`

| Name | Type | Description |
| - | - | - |
| phone | parameter | necessary |
| phoneNumber |  parameter | necessary |
| password |  parameter | necessary |
| status    | return | already registered / valid |


## Tests
inside `test/com/icecream/server/controller`
### UserControllerTest
Use `RestTemplate` to test the function of login and register. 
I write 5 tests for login and my results are correct. 
I write 9 tests for register and find some bugs. 

## Next plans
* [ ] Use token to keep login state
* [ ] Design api returns in a systematic way (maybe use a Json format)
* [ ] Use post and encryption to increase security

