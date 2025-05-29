# Traineeship Management Application

This Java-based web application supports the management of university traineeships by allowing interaction between students, companies, professors, and the traineeship committee.

---

## Contributors

- Ilias Gratsias
- Dimitrios Pagonis
- Dimitrios Tzalokostas

## Description

This project was developed as part of the university course on software engineering and involves the full-stack implementation of a traineeship management system.

It enables:
- Companies to publish available traineeship positions.
- Students to apply, track, and log their traineeships.
- Professors to supervise and evaluate assigned students.
- Committee members to assign traineeships and professors using customizable strategies.

## Course Overview

- **Course**: MYY803 - Software Engineering
- **Instructor**: Mr. Aposotolos Zarras
- **Semester Spring 2025** 
- **University of Ioannina**


---

## Technologies

- **Java 17+**
- **Spring Boot**
- **MySQL**
- **JUnit & Mockito** for testing
- **Eclipse** as the main IDE

---

## Features by Role

### Students
- Register and log in
- Create and update personal profile (skills, interests, location)
- Apply for traineeships
- Fill in a logbook

### Companies
- Manage company profile
- Publish traineeship positions
- View assigned students
- Evaluate student performance

### Professors
- Manage academic profile
- View assigned students
- Evaluate both students and companies

### Committee Members
- View applicants
- Assign traineeships based on:
  - Jaccard similarity (interests vs topics)
  - Location match
  - Combination of both
- Assign professors based on:
  - Topic interest match (Jaccard)
  - Minimum supervision load
- Monitor and finalize evaluations

---

## Project Structure

```
traineeApp/
├── .git/                   # Git metadata
├── .idea/, .classpath, ...# IDE files
├── traineeApp/            # Source code
│   ├── controllers/
│   ├── services/
│   ├── models/
│   ├── repositories/
│   └── ...
├── resources/             # application.properties, SQL scripts, etc.
├── tests/                 # Unit and integration tests
├── README.md              # Project documentation
└── Report.pdf             # Project report
```

---

## Setup

1. Clone the repository
2. Configure MySQL credentials in `application.properties`
3. Build and run the project using your preferred IDE (e.g. Eclipse)

```bash
./mvnw spring-boot:run
```

4. Access the app at `http://localhost:8080`

---

## Documentation
Detailed technical explanations and code walkthroughs are available in [Report.pdf](./Report.pdf).

## 📌 License

Educational use only. © 2025 University of Ioannina

---

## 🙏 Special Thanks

I would like to sincerely thank my collaborators, Ilias Gratsias and Dimitrios Pagonis, for their valuable contribution and support throughout the project.

This project was developed as part of the **Software Engineering** course using a **Scrum** approach. Functional and non-functional requirements were defined in the official document:  
📄 `RequirementsDefinition-2025-v18-02-25.pdf`
