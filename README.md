# My Registrar
My Registrar is a Java application that allows you to manage students, courses, and books through a command-line interface (CLI). It provides functionality to create entities, retrieve information about entities, assign entities to each other, and exit the application.

### Getting Started
To run the application, follow these steps:

- Clone the repository or download the project files.

- Open the project in your preferred Java development environment (e.g., IntelliJ IDEA, Eclipse).

- Build the project to resolve dependencies and compile the code.

- Run the CLI class, which serves as the entry point for the application.

### Features
The My Registrar application offers the following features:

#### Create Entity(s):

- Create a student: You can create a student by providing the required details, such as first and last names.
- Create a course: You can create a course by providing the required details such as name and university.
- Create a book: You can create a book by providing the required details such as name and author.

#### Get Entity(s):
- Get student(s): You can retrieve student information in various ways, such as getting all students, searching by first name, last name, or both.
- Get course(s): You can retrieve course information in different ways, such as getting all courses, searching by name, university, or both.
- Get book(s): You can retrieve book information by searching by name, author, or both.

#### Assign Entity(s):
- Assign a student to courses: You can assign a student to one or more courses by providing the student's first name, last name, and the name(s) of the course(s).
- Assign a course to books: You can assign one or more books to a course by providing the course name and the name(s) of the book(s).
  
#### Exit:
- Exit the application: You can exit the application by selecting this option from the main menu.
#### Dependencies
The My Registrar application uses the following dependencies:

- Spring Framework: The application leverages Spring for dependency injection and database interaction.
- Spring Data JPA: It uses Spring Data JPA to handle database operations and entity relationships.
- Lombok: Lombok is used to reduce boilerplate code by generating getters, setters, constructors, etc.
- PostgreSQL Database: The application utilizes an PostgreSQL in-memory database for data storage during runtime.

#### Data Models
The application has the following data models:
- Student: represents a student with attributes such as ID, first name, last name, etc.
- Course: represents a course with attributes such as ID, name, university, etc.
- Book: represents a book with attributes such as ID, name, author, etc.
- Registration: represents the association between a student and a course, along with additional details such as registration time.

The data model establishes relationships between entities, such as:
- One-to-Many: A student can be associated with multiple registrations (courses), and a course can have multiple registrations (students).
- Many-to-One: A book can be associated with one course, and a course can have multiple books.
