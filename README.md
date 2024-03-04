# Realtor-WEB API Development using Ktor Server

## Introduction
This repository contains the source code for the Realtor-WEB API development using the Ktor server framework. It provides endpoints for managing categories, houses, and contacts in a real estate application.

## Features
- Create, read, update, and delete (CRUD) operations for categories, houses, and contacts.
- Flexible API endpoints for managing real estate data.
- Easy-to-use interface for interacting with the API.

## Technologies Used
- [Ktor](https://ktor.io/): A Kotlin framework for building asynchronous servers and clients.
- [SQLite](https://www.sqlite.org/index.html): A lightweight database engine.
- [JSON](https://www.json.org/json-en.html): A lightweight data-interchange format.

## Installation
1. Clone the repository: `git clone https://github.com/yourusername/Realtor-WEB.git`
2. Navigate to the project directory: `cd Realtor-WEB`
3. Run the application: `./gradlew run`

## Endpoints

### Category Endpoints
- **POST /v1/category**: Create a new category.
- **GET /v1/category**: Retrieve all categories.
- **GET /v1/category/{id}**: Retrieve a specific category by ID.
- **PUT /v1/category/{id}**: Update a specific category by ID.
- **DELETE /v1/category/{id}**: Delete a specific category by ID.

### House Endpoints
- **POST /v1/house**: Create a new house listing.
- **GET /v1/house**: Retrieve all house listings.
- **GET /v1/house/{id}**: Retrieve a specific house listing by ID.
- **PUT /v1/house/{id}**: Update a specific house listing by ID.
- **DELETE /v1/house/{id}**: Delete a specific house listing by ID.

### Contact Endpoints
- **POST /v1/contact**: Create a new contact entry.
- **GET /v1/contact**: Retrieve all contact entries.
- **GET /v1/contact/{id}**: Retrieve a specific contact entry by ID.
- **PUT /v1/contact/{id}**: Update a specific contact entry by ID.
- **DELETE /v1/contact/{id}**: Delete a specific contact entry by ID.

## License
This project is licensed under the [MIT License](LICENSE).
