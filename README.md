# OnyxScript: A Lightweight Web-Based Rich Text Note Editor


## Table of Contents

  - [About OnyxScript](https://www.google.com/search?q=%23about-onyxscript)
  - [Features](https://www.google.com/search?q=%23features)
  - [Technologies Used](https://www.google.com/search?q=%23technologies-used)
  - [Getting Started](https://www.google.com/search?q=%23getting-started)
      - [Prerequisites](https://www.google.com/search?q=%23prerequisites)
      - [Project Setup](https://www.google.com/search?q=%23project-setup)
      - [Running the Application](https://www.google.com/search?q=%23running-the-application)
  - [Usage](https://www.google.com/search?q=%23usage)
  - [Project Structure](https://www.google.com/search?q=%23project-structure)
  - [Contributing](https://www.google.com/search?q=%23contributing)
  - [License](https://www.google.com/search?q=%23license)
  - [Contact](https://www.google.com/search?q=%23contact)

-----

## About OnyxScript

OnyxScript is a simple, web-based rich text note editor designed for users to create, save, and manage their personal notes. It offers a clean interface with a sidebar for note navigation and a main editor area that supports basic text formatting. All notes are securely stored in a database, associated with individual user accounts.

-----

## Features

  * **User Authentication:** Secure login and signup functionality.
  * **Rich Text Editor:** Create and edit notes with basic formatting (bold, italic, underline) using a `contenteditable` div and `document.execCommand()`.
  * **Dynamic Note Creation:** Easily create new notes with a dedicated button.
  * **Automatic Title Generation:** Note titles are automatically extracted from the first line or heading of the note content (using Jsoup on the backend).
  * **Real-time Saving:** Notes are saved to the database, ensuring your content is persistent.
  * **Note Listing Sidebar:** View all your notes in a convenient sidebar, clickable to load their content into the editor.
  * **Active Note Highlighting:** The currently selected note in the sidebar is visually highlighted.
  * **User Profiles:** A dedicated profile page for logged-in users.
  * **Logout Functionality:** Securely end user sessions.

-----

## Technologies Used

  * **Backend:**
      * **Spring Boot:** Framework for building robust, stand-alone, production-grade Spring applications.
      * **Spring Data JPA:** For easy database interaction and object-relational mapping.
      * **Maven:** Dependency management and build automation.
      * **Jsoup:** Java HTML parser for extracting note titles from HTML content on the backend.
  * **Frontend:**
      * **Thymeleaf:** Server-side templating engine for rendering dynamic HTML.
      * **HTML5 & CSS3:** For structuring and styling the web pages.
      * **JavaScript (Vanilla JS):** For client-side interactivity, AJAX calls, and editor logic.
      * **Bootstrap 4.5.2:** (Used for basic styling and layout through CDN).
  * **Database:**
      * **H2 Database:** An in-memory relational database for development and testing. (Can be easily swapped for MySQL, PostgreSQL, etc., for production).

-----

## Getting Started

Follow these instructions to set up and run OnyxScript on your local machine.

### Prerequisites

  * **Java Development Kit (JDK) 17 or higher** (e.g., OpenJDK 17).
  * **Maven 3.6.3 or higher**.
  * An IDE like **IntelliJ IDEA** or **Eclipse** (recommended for Spring Boot development).

### Project Setup

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/Abimeena27/OnyxScript.git
    cd OnyxScript
    ```

2.  **Open in your IDE:**

      * Import the project as a Maven project into your preferred IDE. Your IDE should automatically download the necessary dependencies.

3.  **Database Configuration (H2 - Development):**
    The project is configured to use an in-memory H2 database by default, which means you don't need to set up a separate database server for development. The database will be created and populated on application startup.
    You can access the H2 console at `http://localhost:8080/h2-console` after the application starts (JDBC URL: `jdbc:h2:mem:testdb`, Username: `sa`, Password: `     ` (empty)).

### Running the Application

There are several ways to run the Spring Boot application:

1.  **Using Maven:**

    ```bash
    mvn spring-boot:run
    ```

2.  **From your IDE:**
    Locate the `OSAppApplication.java` file (e.g., `src/main/java/com/OnyxStorm/OSApp/OSAppApplication.java`) and run it as a Java Application.

Once the application starts, open your web browser and go to:
[http://localhost:8080/](https://www.google.com/search?q=http://localhost:8080/)

-----

## Usage

1.  **Register:** On the login page, click "Sign Up" to create a new user account.
2.  **Login:** Use your registered credentials to log in.
3.  **Home Page:**
      * On the left sidebar, you'll see a list of your notes.
      * Click on a note title to load its content into the main editor area.
      * Click the **`+`** button in the sidebar to create a new, blank note.
      * Type in the editor area. Use `Ctrl+B`, `Ctrl+I`, `Ctrl+U` for basic formatting.
      * Type `#`, `##`, or `###` followed by a space at the beginning of a line to create H1, H2, or H3 headings respectively.
      * Type `-` followed by a space to create a list item.
      * Click the **"Save"** button to persist your changes or save a new note. The title will be automatically generated from the first line of content.
4.  **Profile:** Click the user icon in the navbar to view your profile details.
5.  **Logout:** Click "Logout" in the navbar to end your session.

-----

## Project Structure

```
OnyxScript/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── OnyxStorm/
│   │   │           └── OSApp/
│   │   │               ├── Controller/
│   │   │               │   └── MainController.java  # Handles web requests, login, signup, notes (save, fetch)
│   │   │               ├── Model/
│   │   │               │   ├── Notes.java         # Entity for notes (id, user_id, title, context)
│   │   │               │   └── Users.java         # Entity for users (id, email, password)
│   │   │               ├── Repository/
│   │   │               │   ├── NotesRepository.java # JPA repository for Notes CRUD operations
│   │   │               │   └── UsersRepository.java # JPA repository for Users CRUD operations
│   │   │               ├── Service/
│   │   │               │   ├── NotesService.java    # Business logic for Notes
│   │   │               │   └── UsersService.java    # Business logic for Users
│   │   │               └── OSAppApplication.java  # Main Spring Boot application entry point
│   │   └── resources/
│   │       ├── static/                          # Static web resources (CSS, JS, images)
│   │       │   ├── css/
│   │       │   │   └── styleHome.css            # Custom CSS for the home page
│   │       │   ├── images/
│   │       │   │   └── notebook.ico
│   │       │   │   └── user.png
│   │       │   └── js/
│   │       │       └── <any_custom_js_files.js>
│   │       ├── templates/                       # Thymeleaf HTML templates
│   │       │   ├── home.html                    # Main note editor page
│   │       │   ├── login.html                   # Login/Signup page
│   │       │   └── profile.html                 # User profile page
│   │       └── application.properties           # Spring Boot configuration (database, ports)
├── .gitignore                                   # Specifies intentionally untracked files
├── pom.xml                                      # Maven project object model file
└── README.md                                    # This file
```

-----

## Contributing

Contributions are welcome\! If you find a bug or have a feature request, please open an issue. If you'd like to contribute code, please fork the repository and submit a pull request.

-----

## License

This project is open-source and available under the [MIT License](LICENSE.md).
*(Create a `LICENSE.md` file in your root directory if you don't have one)*

-----
