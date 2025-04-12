# lang_practice_tracker
 An API for tracking language practice.

## Running Locally
1. Download the repository.
2. Use `./gradlew bootRun` to start the application.

## Usage
### Creating
Endpoint: `/studysession`

Example CURL:
```
curl --location 'http://localhost:8080/studysession' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "this is an example study session",
    "resourceLink": "https://www.youtube.com/@comprehensiblechinese/playlists",
    "resourceMaterial": "VIDEO",
    "timeSpent": "PT30M",
    "method": "COMPREHENSIVE_INPUT",
    "studyDate": "2025-04-12"
}'
```

### Reading
#### Get All 
Endpoint: `/studysession`

Example CURL:
```
curl --location 'http://localhost:8080/studysession'
```

#### Get Study Session By Id
Endpoint: `/studysession/{id}/`

Example CURL:
```
curl --location 'http://localhost:8080/studysession/62cd3e21-47bc-45d0-b4d7-fc409e4dbd6e/'
```

### Updating
Endpoint: `/studysession/{id}/`

Example CURL:
```
curl --location --request PUT 'http://localhost:8080/studysession/62cd3e21-47bc-45d0-b4d7-fc409e4dbd6e/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "this is an example study session, but edited",
    "resourceLink": "https://www.youtube.com/@comprehensiblechinese/playlists",
    "resourceMaterial": "VIDEO",
    "timeSpent": "PT30M",
    "method": "COMPREHENSIVE_INPUT",
    "studyDate": "2025-04-12",
    "status": "PRESENT"
}'
```

### Deleting
Endpoint: `/studysession{id}/`

Example CURL:
```
curl --location --request DELETE 'http://localhost:8080/studysession/62cd3e21-47bc-45d0-b4d7-fc409e4dbd6e/'
```

### Postman Collection
> [!WARNING]
> NOT IMPLEMENTED YET

### More Usage Information
resourceMaterial, method, and status are enums that take specific values. Here's an outline of valid values for each:

| Enum | Valid Input |
| --- | --- |
| resourceMaterial | ACTIVE_STUDYING, COMPREHENSIVE_INPUT, PASSIVE_INPUT |
| method | TEXTBOOK, WORKBOOK, WRITING_BOOK, FLASHCARDS, ANKI, VIDEO, CLASS, TUTORING, MOBILE_APP, WEBSITE |
| status | PRESENT, DELETED |

## Tests
> [!WARNING]
> NOT IMPLEMENTED YET
