# Quiz & Examination Management System 🎓
*(Pure Core Java: OOP + Collections Framework + Exception Handling)*

A complete, robust, console-based **Quiz & Examination Management System** designed strictly using **Core Java** principles.

---

## 🚫 Technology Compliance & Restrictions

This project strictly adheres to the requested technological boundaries:
- **NO Database** (No MySQL, JDBC, Hibernate, JPA)
- **NO Web / Enterprise Frameworks** (No Spring, Spring Boot, Servlets, JSP)
- **NO GUI** (No JavaFX, Swing, AWT)
- **NO File I/O** (No Files, JSON, XML, Serialization to disk)
- **NO Multithreading**
- **NO External Libraries** (No Maven dependencies, only standard JDK classes)

> **In-Memory Storage**: All data lives purely in RAM using the **Java Collections Framework** (`ArrayList`, `HashMap`, `HashSet`) and clears when the application exits.

---

## 📁 Package Structure

```text
src/
├── main/
│   └── Main.java                      <- Console UI & Application Driver
│
├── model/
│   ├── User.java                      <- Abstract Base Class (Encapsulation, Abstraction)
│   ├── Admin.java                     <- Admin subclass (Inheritance, Polymorphism)
│   ├── Student.java                   <- Student subclass (Composition with Results & HashSet)
│   ├── Question.java                  <- Question Model with 4 options and answer checking
│   ├── Quiz.java                      <- Quiz Model (Composition with ArrayList<Question>)
│   └── Result.java                    <- Score & Percentage Evaluation Model
│
├── service/
│   ├── AdminService.java              <- CRUD for Questions, Quizzes, and Student view
│   ├── StudentService.java            <- Student Registration, Login, and Results view
│   ├── QuizService.java               <- Quiz Retrieval and Validation logic
│   └── ResultService.java             <- Quiz Grading & Result Calculation
│
├── repository/
│   └── DataStore.java                 <- In-Memory Central Store (ArrayList, HashMap, HashSet)
│
├── exception/
│   ├── InvalidLoginException.java     <- Thrown on bad username or password
│   ├── QuestionNotFoundException.java <- Thrown when Question ID is missing
│   ├── QuizNotFoundException.java     <- Thrown when Quiz ID is missing
│   ├── DuplicateUserException.java    <- Thrown when username already exists
│   ├── InvalidAnswerException.java    <- Thrown when user enters non A/B/C/D option
│   └── NoQuestionsException.java      <- Thrown when Quiz contains no questions
│
└── test/
    └── SystemTest.java                <- Automated Verification Test Suite
```

---

## 💡 Concept Mapping & Explanations

### 1. Object-Oriented Programming (OOP)
| Concept | Implementation in Code |
| :--- | :--- |
| **Encapsulation** | All entity fields are `private` with explicit constructors, getters, and setters across `User`, `Question`, `Quiz`, and `Result`. |
| **Abstraction** | `abstract class User` enforces the template method `abstract void displayProfile()` for all derived user types. |
| **Inheritance** | Base class `User` is extended by `Admin` and `Student` (`class Admin extends User`, `class Student extends User`). |
| **Polymorphism** | Method overriding: `displayProfile()` behaves differently for `Admin` (prints Department) and `Student` (prints Course & Quizzes taken). |
| **Composition** | `Quiz` contains an `ArrayList<Question>` ("Quiz HAS-A Questions"); `Student` contains an `ArrayList<Result>` ("Student HAS-A Results"). |

---

### 2. Java Collections Framework
| Collection | Usage in Project | Why this Collection? |
| :--- | :--- | :--- |
| **`ArrayList<T>`** | `ArrayList<Question>`, `ArrayList<Student>`, `ArrayList<Quiz>`, `ArrayList<Result>` | Provides an ordered, dynamic list that maintains sequential insertion order for questions, quizzes, and historical results. |
| **`HashMap<K, V>`** | `HashMap<Integer, Question>` and `HashMap<Integer, Quiz>` in `DataStore` | Provides instant **$O(1)$ constant time** lookup of questions and quizzes using their integer ID as the key. |
| **`HashMap<Integer, Character>`** | `studentAnswers` in `Main.java` and `ResultService.java` | Maps each `Question ID` directly to the student's selected `Answer Character` ('A', 'B', 'C', 'D'). |
| **`HashSet<String>`** | `DataStore.usernames` | Guarantees uniqueness of usernames across the entire application in **$O(1)$ time** during registration. |
| **`HashSet<Integer>`** | `Student.attemptedQuizIds` | Prevents a student from taking the exact same quiz more than once by checking whether the quiz ID is already present. |

---

### 3. Exception Handling
All custom exceptions extend `java.lang.Exception` (checked exceptions):
1. **`InvalidLoginException`**: Thrown when admin or student username/password does not match.
2. **`DuplicateUserException`**: Thrown during student registration if username is already in `DataStore.usernames`.
3. **`QuestionNotFoundException`**: Thrown when updating or deleting a question with an invalid ID.
4. **`QuizNotFoundException`**: Thrown when a student selects an invalid Quiz ID.
5. **`NoQuestionsException`**: Thrown if an admin creates a quiz with 0 questions and a student attempts to start it.
6. **`InvalidAnswerException`**: Thrown when a student enters anything other than 'A', 'B', 'C', or 'D'. Handled gracefully to re-prompt without crashing.

---

## 🚀 Pre-Loaded In-Memory Sample Data

To make testing immediate and effortless, `DataStore.initSampleData()` automatically pre-populates:

- **Admin Account**:
  - Username: `admin`
  - Password: `admin123`
- **Student Account**:
  - Username: `sarthak`
  - Password: `pass123`
- **Questions**: 15 Comprehensive Core Java questions (IDs 101 to 115) covering:
  - OOP (Inheritance, Polymorphism, Encapsulation, Abstraction, Interfaces)
  - Collections Framework (`List`, `Set`, `Map`, `TreeSet`, `HashMap`, `Collections.sort()`, initial capacities)
  - Exception Handling (`Throwable` hierarchy, `try-catch-finally`, checked vs unchecked, `throw` vs `throws`)
- **Pre-loaded Quizzes**:
  1. Quiz ID `1`: *"Java Fundamentals"* (6 Questions)
  2. Quiz ID `2`: *"Java OOP & Collections Mastery"* (9 Questions)
- **On-Demand Random Quiz**:
  - Generates a custom randomized practice test on the fly picking any desired number of random questions from the 15-question bank!
  - Supports question shuffling on any quiz attempt using `Collections.shuffle()`.

---

## 💻 Commands to Run

### Option 1: PowerShell (Windows)
```powershell
.\run.ps1
```
*(Or compile and execute manually:)*
```powershell
javac -d bin (Get-ChildItem -Path src -Recurse -Filter *.java).FullName
java -cp bin main.Main
```

### Option 2: Command Prompt (CMD / Batch)
```cmd
run.bat
```

### Run Automated 8-Point Verification Suite
```cmd
test.bat
```
*(Or: `java -cp bin test.SystemTest`)*

---

## 📋 Sample Console Execution

```text
========================================
       QUIZ & EXAMINATION SYSTEM        
========================================

1. Admin Login
2. Student Registration
3. Student Login
4. Exit

Enter your choice: 3

--- STUDENT LOGIN ---
Enter Username: sarthak
Enter Password: pass123

Login Successful! Welcome, Sarthak.

========================================
            STUDENT DASHBOARD           
========================================

1. View Available Quizzes
2. Start Quiz
3. View My Results
4. View Profile
5. Logout

Enter your choice: 2
====================================
         AVAILABLE QUIZZES          
====================================
Quiz ID   : 1
Quiz Name : Java Fundamentals
Questions : 6
------------------------------------
Enter Quiz ID: 1

========================================
 Starting Quiz: Java Fundamentals
 Total Questions: 6
 Please enter A, B, C, or D for each answer.
========================================

================================
Question 1 of 6
================================

Which keyword is used for inheritance in Java?

A. implements
B. extends
C. inherits
D. super

Enter your answer: B
...
================================
             RESULT             
================================
Student: Sarthak
Quiz: Java Fundamentals

Total Questions : 6
Correct Answers : 6
Wrong Answers   : 0
Score           : 6/6
Percentage      : 100.0%

Status          : PASS
================================
```
