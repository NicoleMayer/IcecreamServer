# IcecreamServer
## Overview

It uses Spring Boot framework to realize HTTP connection and database connection.

## API list

### login
POST
URL example: `http://server_ip/signin`

| Name | Type | Description |
| - | - | - |
| User | parameter | necessary |
| status | return | 0 "can't find phone number" |
| status | return | 1 "wrong password" |
| status | return | 2 "login succeed" |

### register
POST
URL example: `http://server_ip/signup`

| Name | Type | Description |
| - | - | - |
| user | parameter | necessary |
| status    | return | 0 "register succeed" |

### verify if phone number exists
POST
URL example: `http://server_ip/before-register`

| Name | Type | Description |
| - | - | - |
| user | parameter | necessary |
| status    | return |0 "phone number is null" |
| status    | return |1 "phone number already exists" |
| status    | return |2 "phone number doesn't exist" |

### list user-collected channels
GET
URL example: `http://server_ip/list/feeds`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| status    | return |0 "wrong token" |
| status    | return |1 "user not find" |
| status    | return |2 "succeed" |

### articles for a subscribed channel
GET
URL example: `http://server_ip/list/feed/{id}/articles`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| id | parameter | channel id |
| status    | return |0 "wrong token" |
| status    | return |1 "user not find" |
| status    | return |2 "feed not find" |
| status    | return |3 "succeed" |

### articles for all subscribed channels
GET
URL example: `http://server_ip/list/feed/all/articles`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| status    | return |0 "wrong token" |
| status    | return |1 "user not find" |
| status    | return |2 "succeed" |

### article for a given article id
GET
URL example: `http://server_ip/list/article/{id}`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| id | parameter | article id |
| status    | return |0 "wrong token" |
| status    | return |1 "article not find" |
| status    | return |2 "article find succeed" |

### subscribe a new channel
GET
URL example: `http://server_ip/addChannel`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| rssFeedEntity | parameter | basic info about the new channel |
| status    | return |0 "wrong token" |
| status    | return |1 "user not find" |
| status    | return |2 "add failed" |
| status    | return |2 "add succeed" |


### unsubscribe a channel
GET
URL example: `http://server_ip/deleteChannel/{id}`

| Name | Type | Description |
| - | - | - |
| token | parameter | check the user |
| id | parameter | channel id |
| rssFeedEntity | parameter | basic info about the new channel |
| id | parameter | channel id |
| status    | return |0 "wrong token" |
| status    | return |1 "channel not find" |
| status    | return |2 "delete failed" |
| status    | return |2 "delete succeed" |


## Tests
inside `test/com/icecream/server/controller`
### UserControllerTest
Use `RestTemplate` to test the function of login and register. 
I write 5 tests for login and my results are correct. 
I write 9 tests for register and find some bugs. 

## Next plans
* [X] Use token to keep login state
* [X] Design api returns in a systematic way (maybe use a Json format)
* [X] Use post and encryption to increase security
* [X] Write junit test for rss part
* [X] Some weird logic about update a channel and subscirbe channel
  * determine if the sources are given by the app and user can't add a customized rss feed

