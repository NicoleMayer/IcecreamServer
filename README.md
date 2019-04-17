# IcecreamServer
## Overview

## API list

### login

url example: `/login?phone=111&username=111&password=111`

| name | type | description |
| - | - | - |
| phone | parameter | not necessary |
| username |  parameter | not necessary |
| password |  parameter | necessary |
| status    | return | login success / login fail |

### register
| name | type | description |
| - | - | - |
| phone | parameter | necessary |
| username |  parameter | necessary |
| password |  parameter | necessary |
| status    | return | User saved / Phone Number already registered / Username has already been used / Empty username / username too short / username too long / Empty password / password too short / password too long|


## Tests
inside `test/com/icecream/server/controller`
### UserControllerTest
Use `RestTemplate` to test the function of login and register. 
I write 5 tests for login and my results are correct. 
I write 9 tests for register and find some bugs. 

## Next plans
* [ ] use token to keep login state
* [ ] design api returns in a systematic way (maybe use a json format)

