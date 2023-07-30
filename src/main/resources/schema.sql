CREATE TABLE IF NOT EXISTS end_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL,
    registered_at DATETIME
);

CREATE TABLE IF NOT EXISTS university (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    city VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    age INTEGER NOT NULL,
    gender VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    university_id BIGINT,
    FOREIGN KEY (university_id) REFERENCES university (id)
);

CREATE TABLE IF NOT EXISTS course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    instructor VARCHAR(255),
    credit_hours INTEGER NOT NULL,
    university_id BIGINT,
    FOREIGN KEY (university_id) REFERENCES university (id)
);

CREATE TABLE IF NOT EXISTS student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_pre_requisite (
    course_id BIGINT NOT NULL,
    course_pre_requisite_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, course_pre_requisite_id),
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (course_pre_requisite_id) REFERENCES course (id)
);

CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    genre VARCHAR(200),
    published_date DATE,
    publisher VARCHAR(100),
    page_number INTEGER,
    course_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES course (id)
);

