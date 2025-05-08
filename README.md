# Spring 6 REST MVC

This is a Spring Boot REST MVC application that demonstrates RESTful API development using Spring 6.

## Overview

This project provides a simple RESTful API for managing resources like beers and customers.

## Features

- RESTful API endpoints
- CRUD operations
- Spring MVC architecture
- Exception handling

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`

## API Documentation

### Beer API

- GET /api/v1/beer - List all beers
- GET /api/v1/beer/{id} - Get a beer by ID
- POST /api/v1/beer - Create a new beer
- PUT /api/v1/beer/{id} - Update a beer
- DELETE /api/v1/beer/{id} - Delete a beer

### Customer API

- GET /api/v1/customer - List all customers
- GET /api/v1/customer/{id} - Get a customer by ID
- POST /api/v1/customer - Create a new customer
- PUT /api/v1/customer/{id} - Update a customer
- DELETE /api/v1/customer/{id} - Delete a customer

## License

This project is licensed under the MIT License - see the LICENSE file for details.