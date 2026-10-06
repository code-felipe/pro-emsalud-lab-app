# Pro-Emsalud

A comprehensive management system for a clinical laboratory, built as a full-stack application with **Angular**, **Spring Boot**, and **MySQL**.

Pro-Emsalud was designed to improve and digitize a clinical laboratory's workflow, reducing manual tasks, reliance on Excel documents, and the costs associated with printing and managing physical records.

> **Real-world project:** developed to support the operations of a clinical laboratory/small business in Guatemala.

---

## 📋 Overview

Pro-Emsalud centralizes the laboratory's operational information in a single application, allowing patients, tests, results, services, and sales to be managed from a web interface.

The system aims to replace manual, scattered processes with a digital, structured workflow based on the business domain model.

The main goals of the project include:

- Digitize the storage and retrieval of information.
- Reduce the use of Excel documents for recording and querying data.
- Significantly lower printing-related costs.
- Centralize patient history.
- Maintain data integrity and consistency.
- Make searching and querying information easier.
- Generate digital reports.
- Provide strategic information for decision-making.
- Prepare the system to run locally in environments with limited connectivity.

---

## 🎯 Project Impact

One of the main goals of Pro-Emsalud was to reduce dependence on physical processes and standalone documents.

### Key results

- Approximately **85%** reduction in printing costs.
- Gradual elimination of Excel files as the primary mechanism for storing and querying data.
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
│  Validation                  │
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

The system allows administering patient information and maintaining a history associated with the operations performed within the laboratory.

Features include:

- Patient registration.
- Patient lookup.
- Search by identifiers.
- Information updates.
- Patient-associated history.
- Status handling.
- Activation and deactivation of records.

### 🧪 Test and Result Management

Allows managing the tests performed by the laboratory and storing the corresponding results.

The system maintains the relationships between:

```text
Patient
   │
   └── Results
          │
          └── Test
```

This makes it possible to query a patient's result history and keep information organized according to the domain model.

---

## 🗃️ Persistence and Data Integrity

The application uses Spring Data JPA and Hibernate to handle persistence.

The following are implemented:

- Relationships between entities.
- Integrity constraints.
- JPQL queries.
- Projections for specific queries.
- Grouped queries.
- Pagination using Pageable.
- Status handling.
- Validation.
- Soft delete.
- Activation and deactivation of records.

The goal is to avoid unnecessary deletion of information and to maintain the historical consistency of the data.

---

## ♻️ Soft Delete

The system uses soft delete mechanisms for certain records.

Instead of physically deleting information from the database, records can be moved to an inactive state.

This allows for:

- Preserving historical information.
- Preventing accidental data loss.
- Maintaining existing relationships.
- Restoring records when necessary.
- Separating active records from inactive ones.

---

## 🔎 Search and Filters

The main entities provide querying and filtering mechanisms.

These include:

- Search by primary key.
- Entity-specific queries.
- Filters.
- Pagination.
- Sorting.
- Grouped queries.
- Queries for metrics and reports.

This removes the need to manually sift through large amounts of information.

---

## ⚠️ Validation and Error Handling

The application implements structured error handling in both the frontend and the backend.

HTTP requests can return detailed information about:

- Validation errors.
- Resources not found.
- Invalid data.
- Conflicts.
- Persistence errors.
- Internal server errors.

The goal is to give users clear messages and make diagnosis easier during application maintenance.

---

## 📄 Report Generation

Pro-Emsalud can generate reports in PDF format.

This makes it easier to:

- Deliver results.
- Store documents digitally.
- Print when necessary.
- Share information with patients.

Document generation is performed from the frontend using content generation and capture tools.

---

## 💬 WhatsApp Integration

The system includes integration with WhatsApp Desktop to make it easier to send information to patients.

The flow lets staff use the information generated by the system and communicate with the patient without having to manually copy data between different applications.

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

### Example information flow

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

These metrics provide information that can be used to make future decisions about the state and behavior of the business.

---

## 📈 Strategic Queries

The backend uses specific queries to obtain aggregated information without having to load all records into memory.

Examples:

- Result counts.
- Sales totals.
- Sales grouped by period.
- Most requested tests.
- Results per test.
- General laboratory metrics.

JPQL projections are used for specific queries, reducing the amount of data transferred between the database and the application.

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
   ├── Results
   │       │
   │       └── Test
   │
   └── History

Test
   │
   └── Results
           │
           └── Sales information
```

This ensures the system's operations are tied to real laboratory processes and not just generic storage structures.

---

## 🔐 Security

Authentication- and authorization-based security is planned as one of the next phases of the project.

### Coming soon

- User authentication.
- Role-based authorization.
- Endpoint protection.
- Feature-level access control.
- Session/token management.
- Protection of sensitive information.

---

## 💾 Automatic Backups

A feature planned for an upcoming release is automatic database backups.

The goal is to enable:

- Automatic creation of local backups.
- Configurable execution intervals.
- Retention of multiple versions.
- Identification of backup date and status.
- Recovery from errors or data loss.

This feature will be especially geared toward local installations.

---

## 🖥️ Local Installation

Because the system is intended for an environment where the Internet connection may be unstable, a local installation mode is planned.

### Coming soon

The goal is to provide a package that allows the system to be installed directly on the client's machine.

The planned architecture includes:

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

The plan is to package the necessary components to simplify local installation and execution, including the backend as a JAR and an installable Windows application as an `.exe`.

This will reduce dependence on a permanent Internet connection.

---

## 🚧 Roadmap

### Completed

- [x] Full-stack architecture.
- [x] Angular frontend.
- [x] Spring Boot backend.
- [x] MySQL persistence.
- [x] Domain model.
- [x] Patient management.
- [x] Test management.
- [x] Result management.
- [x] Patient history.
- [x] Soft delete.
- [x] Record activation/deactivation.
- [x] Validation.
- [x] Structured HTTP error handling.
- [x] Search and filters.
- [x] JPQL queries.
- [x] Projections.
- [x] Pagination.
- [x] PDF report generation.
- [x] WhatsApp Desktop integration.
- [x] Metrics dashboard.
- [x] Sales visualization.
- [x] Results visualization.
- [x] Most requested tests.
- [x] Charts and trends.

### In progress

- [ ] Automatic backups.
- [ ] Backup versioning system.
- [ ] Backup interval configuration.
- [ ] Authentication.
- [ ] Authorization.
- [ ] Roles and permissions.
- [ ] Endpoint protection.
- [ ] Packaging for local installation.
- [ ] `.exe` installer.
- [ ] Backend distribution as a `.jar`.
- [ ] Optimization for limited-connectivity environments.

---

## 📁 General Structure

A conceptual project structure is:

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

An example of a query flow is:

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
- Frontend–backend integration.
- Document generation.
- Data visualization.
- Aggregate queries.
- Integration with external applications.
- Responsive design.
- Development driven by real business needs.
- Client mettings.

---

## 🚀 Project Goal

The goal of Pro-Emsalud is not merely to digitize existing processes, but to build a platform that can evolve alongside the laboratory's needs.

The architecture is designed to allow new features to be added progressively, including security, backups, local installation, and additional analysis tools.

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
