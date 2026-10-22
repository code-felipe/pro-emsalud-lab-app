# Pro-Emsalud

Comprehensive management system for a clinical laboratory, developed as a full-stack application with **Angular**, **Spring Boot**, and **MySQL**.

Pro-Emsalud was designed to improve and digitize the workflow of a clinical laboratory, reducing manual tasks, dependence on Excel documents, and the costs associated with printing and physically managing information.

> **Real-world project:** developed to support the operations of a clinical laboratory / small business in Guatemala.

---

## 📋 Description

Pro-Emsalud centralizes the laboratory's operational information in a single application, allowing patients, tests, results, services, and sales to be managed from a web interface.

The system aims to replace manual and scattered processes with a structured digital workflow based on the business domain model.

The main goals of the project include:

- Digitizing the storage and retrieval of information.
- Reducing the use of Excel documents to record and query data.
- Significantly lowering the costs associated with printing.
- Centralizing patient history.
- Maintaining data integrity and consistency.
- Making it easier to search and query information.
- Generating digital reports.
- Providing strategic information for decision-making.
- Preparing the system to run locally in environments with limited connectivity.

---

## 🎯 Project Impact

One of the main goals of Pro-Emsalud was to reduce dependence on physical processes and standalone documents.

### Key Results

- Approximately **85%** reduction in printing costs.
- Progressive elimination of Excel files as the main mechanism for storing and querying data.
- Centralization of patient and result information.
- Faster access to patient history.
- Reduction of manual tasks related to searching for and organizing information.
- Availability of metrics and visualizations to analyze business performance.

The project is aimed at solving real operational needs, not just serving as a technical demonstration.

---

## 🏗️ Architecture

The application uses a full-stack architecture that separates the frontend, backend, and persistence layers.

```text
┌──────────────────────────────┐
│          Frontend            │
│          Angular             │
│                              │
│  Dashboard                   │
│  Patients                    │
│  Tests                       │
│  Results                     │
│  Services                    │
│  Queries                     │
│  Reports                     │
└──────────────┬───────────────┘
               │
               │ HTTP / REST
               ▼
┌──────────────────────────────┐
│           Backend            │
│         Spring Boot          │
│                              │
│  Controllers                 │
│  Services                    │
│  Repositories                │
│  DTOs / Projections          │
│  Validations                 │
│  Error handling              │
│  Business rules              │
└──────────────┬───────────────┘
               │
               │ JPA / Hibernate
               ▼
┌──────────────────────────────┐
│           MySQL              │
│                              │
│  Persistence                 │
│  Data integrity              │
│  Relationships               │
│  History                     │
└──────────────────────────────┘
```

---

## 🛠️ Technologies

### Frontend

- Angular
- TypeScript
- HTML5
- CSS3
- Bootstrap
- Bootstrap Icons
- ng2-charts
- Chart.js
- RxJS

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API
- Bean Validation
- JPQL
- Projections
- Pageable

### Database

- MySQL

### Document Generation

- PDF
- jsPDF
- html2canvas

### Integrations

- WhatsApp Desktop

---

## ✨ Main Features

### 👤 Patient Management

The system allows administrators to manage patient information and maintain a history associated with the operations performed within the laboratory.

Features include:

- Patient registration.
- Patient lookup.
- Search by identifiers.
- Information updates.
- Patient-associated history.
- Status management.
- Record activation and deactivation.

### 🧪 Test and Result Management

Allows the management of the tests performed by the laboratory and the storage of the corresponding results.

The system maintains the relationships between:

```text
Patient
   │
   └── Orders
          │
          └── Results
```

This makes it possible to query a patient's result history and keep the information organized according to the domain model.

---

## 🗃️ Persistence and Data Integrity

The application uses Spring Data JPA and Hibernate to handle persistence.

The following are implemented:

- Relationships between entities.
- Integrity constraints.
- Queries using JPQL.
- Projections for specific queries.
- Grouped queries.
- Pagination using Pageable.
- Status management.
- Validations.
- Soft delete.
- Record activation and deactivation.

The goal is to avoid unnecessary deletion of information and to maintain the historical consistency of the data.

---

## ♻️ Soft Delete

Although the system supports deletion, it does so safely, using soft delete mechanisms for certain records.

Instead of physically deleting information from the database, records can be moved to an inactive state.

This makes it possible to:

- Preserve historical information.
- Prevent accidental data loss.
- Maintain existing relationships.
- Recover records when needed.
- Separate active records from inactive ones.

---

## 🔎 Search and Filters

The main entities have query and filtering mechanisms.

Among them:

- Search by primary key.
- Entity-specific queries.
- Filters.
- Pagination.
- Sorting.
- Grouped queries.
- Queries for metrics and reports.

This removes the need to manually go through large amounts of information.

---

## ⚠️ Validations and Error Handling

The application implements structured error handling in both the frontend and the backend.

HTTP requests can return detailed information about:

- Validation errors.
- Resources not found.
- Invalid data.
- Conflicts.
- Persistence errors.
- Internal server errors.

The goal is to give users clear messages and to make diagnosis easier during application maintenance.

---

## 📄 Report Generation

Pro-Emsalud can generate reports in PDF format.

This makes it easier to:

- Deliver results.
- Store documents digitally.
- Print when necessary.
- Share information with patients.

Documents are generated from the frontend using content generation and capture tools.

---

## 💬 WhatsApp Integration

The system includes integration with WhatsApp Desktop to make it easier to send information to patients.

The flow lets users reuse the information generated by the system and communicate with the patient without having to manually copy data between different applications.

---

## 📊 Dashboard and Metrics

Pro-Emsalud includes a dashboard geared toward the laboratory's operational and commercial analysis.

The following metrics can currently be viewed:

- Total sales.
- Current sales.
- Number of results.
- Number of patients.
- Most requested tests.
- Sales by period.
- Results by period.

Charts are also used to visualize trends.

### Example Information Flow

```text
TestResult
    │
    ├── Test
    │
    ├── Unit Price
    │
    └── Created At
          │
          ▼
      Grouping
          │
          ▼
       Metrics
          │
          ▼
       Dashboard
```

These metrics provide information that can be used to make future decisions about the state and performance of the business.

---

## 📈 Strategic Queries

The backend uses specific queries to obtain aggregated information without having to load all records into memory.

Examples:

- Result counts.
- Sales totals.
- Sales grouped by period.
- Most requested tests.
- Results by test.
- General laboratory metrics.

For specific queries, JPQL projections are used, reducing the amount of data transferred between the database and the application.

---

## 📑 Pagination

Queries that can produce large amounts of data use pagination mechanisms.

For example, the ranking of most requested tests uses `Pageable` to limit the results:

```java
Pageable pageable = PageRequest.of(0, 5);
```

This makes it possible to retrieve only the records needed for certain views, such as the Top 5 most requested tests.

---

## 🧩 Domain-Driven Design

The application is built around the entities and relationships specific to the business.

The model covers concepts such as:

```text
Patient
   │
   ├── Orders
   │       │
   │       └── Results
   │
   └── History

Orders
   │
   └── Results
           │
           └── Sales information
```

This ensures that the system's operations are tied to real laboratory processes and not just to generic storage structures.

---

## 🔐 Security

Authentication and authorization mechanisms were implemented to restrict access to the application and protect the system's features.

The solution allows user access to be managed according to the implemented security mechanisms, contributing to the protection of information and the controlled use of the available features.

### Coming Soon

- User authentication.
- Role-based authorization.
- Endpoint protection.
- Feature-level access control.
- Session/token management.
- Protection of sensitive information.

---

## 💾 Automatic Backups

Database backup management is part of the system's information protection measures.

Since this is a local installation, it is important to have mechanisms that preserve the data and make recovery easier in case of equipment failure or data loss.

Note: Backups are stored in a previously configured folder, and the client decides through the application whether they want automatic or manual backups.

The goal is to allow:

- Automatic creation of local backups.
- Configuration of execution intervals.
- Retention of multiple versions.
- Identification of the backup date and status.
- Recovery from errors or data loss.

This feature is especially geared toward local installations.

---

## 🖥️ Local Installation

The application is installed and running on the client's computer as a local deployment. This approach was chosen to reduce dependence on a permanent Internet connection, due to the frequent connectivity problems the client experienced.

Although Angular and Spring Boot are commonly used to build web applications, their architecture allows both components to run in a local environment, without needing an Internet connection for operations performed on the machine itself.

### Packages

A `.jar` file and a `.bat` file were provided to run and launch the system.

The architecture includes:

```text
┌─────────────────────────┐
│      Local application  │
│                         │
│  Angular / Frontend     │
│          │              │
│          ▼              │
│  Spring Boot / Backend  │
│          │              │
│          ▼              │
│       MySQL             │
└─────────────────────────┘
```
This reduces dependence on a permanent Internet connection.

---

## 🚧 Roadmap

### Completed

- [x] Full-stack architecture.
- [x] Frontend with Angular.
- [x] Backend with Spring Boot.
- [x] Persistence with MySQL.
- [x] Domain model.
- [x] Patient management.
- [x] Test management.
- [x] Result management.
- [x] Patient history.
- [x] Soft delete.
- [x] Record activation/deactivation.
- [x] Validations.
- [x] Structured HTTP error handling.
- [x] Search and filters.
- [x] Queries using JPQL.
- [x] Projections.
- [x] Pagination.
- [x] PDF report generation.
- [x] WhatsApp Desktop integration.
- [x] Metrics dashboard.
- [x] Sales visualization.
- [x] Results visualization.
- [x] Most requested tests.
- [x] Charts and trends.
- [x] Automatic backups.
- [x] Backup versioning system.
- [x] Backup interval configuration.
- [x] Authentication.
- [x] Authorization.
- [x] Roles and permissions.
- [x] Endpoint protection.
- [x] Packaging for local installation.
- [x] Backend distribution as a `.jar`.

---

## 📁 General Structure

A conceptual structure of the project is:

```text
pro-emsalud/
│
├── frontend/
│   └── Angular
│       ├── components/
│       ├── pages/
│       ├── services/
│       ├── models/
│       └── ...
│
├── backend/
│   └── Spring Boot
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── projection/
│       └── ...
│
└── database/
    └── MySQL
```

---

## 🔄 General Operation Flow

An example of the flow of a query is:

```text
User
   │
   ▼
Angular
   │
   │ HTTP Request
   ▼
Spring Boot Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   │ JPA / Hibernate
   ▼
MySQL
   │
   ▼
Repository
   │
   ▼
Service
   │
   ▼
Controller
   │
   │ HTTP Response
   ▼
Angular
   │
   ▼
User
```

This approach separates responsibilities and makes the system easier to maintain.

---

## 🧠 Key Technical Highlights

The project represents hands-on experience working with:

- Full-stack development.
- Client-server architecture.
- REST API development.
- Angular and TypeScript.
- Java and Spring Boot.
- Spring Data JPA.
- Hibernate.
- MySQL.
- Relational modeling.
- JPQL.
- Projections.
- Pagination.
- Data validation.
- Error handling.
- Soft delete.
- Frontend-backend integration.
- Document generation.
- Data visualization.
- Aggregate queries.
- Integration with external applications.
- Responsive design.
- Development driven by real business needs.
- Constant meetings with the client.

---

## 🚀 Project Goal

The goal of Pro-Emsalud is not just to digitize existing processes, but to build a platform that can evolve alongside the laboratory's needs.

The architecture is designed to allow the progressive addition of new features, including security, backups, local installation, and additional analysis tools.

The project seeks to combine:

```text
Business need
        +
Automation
        +
Data integrity
        +
User experience
        +
Strategic information
        =
Comprehensive management system
```

---

## 📌 Project Status

- **Status:** Under active development
- **Type:** Full-stack application for a real business environment

**Main stack:**

- Angular
- Spring Boot
- Java
- MySQL
- Hibernate / JPA
- TypeScript
- REST API
- Chart.js

---

## 👨‍💻 Author

Developed as a software project aimed at solving real operational needs and digitizing processes for a clinical laboratory.

The project represents hands-on experience in the development, integration, and maintenance of a full-stack application used in a real-world environment.