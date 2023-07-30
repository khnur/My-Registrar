INSERT INTO end_user (username, password, role, registered_at)
VALUES ('admin', '$2a$12$BO709/LgaeJi4N2Q0wlEo.5NetvwKO9hZgJYfnDhuQmQWeziyFgh.', 'ROLE_ADMIN', current_timestamp);

INSERT INTO university (name, country, city)
VALUES ('NU', 'KAZAKHSTAN', 'Astana');


INSERT INTO student (first_name, last_name, birth_date, age, gender, email, courses_taking)
VALUES ('Nurzhan', 'Dope', '1995-08-25', 26, 'Male', 'nurzhan.doe@onelab.kz', 2);

INSERT INTO student (first_name, last_name, birth_date, age, gender, email, courses_taking)
VALUES ('Someone', 'Dope', '1995-08-25', 26, 'Male', 'someone.doe@onelab.kz', 2);

INSERT INTO student (first_name, last_name, birth_date, age, gender, email, courses_taking)
VALUES ('anyone', 'Dope', '1995-08-25', 26, 'Male', 'anyone.doe@onelab.kz', 0);


UPDATE student SET university_id = 1 WHERE student.id = 1;
UPDATE student SET university_id = 1 WHERE student.id = 2;
UPDATE student SET university_id = 1 WHERE student.id = 3;


INSERT INTO course (name, department, instructor, credit_hours, university_id)
VALUES ('Theory of Computation', 'Computer Science', 'Ben Tyler', 6, 1);

INSERT INTO course (name, department, instructor, credit_hours, university_id)
VALUES ('Algo', 'Computer Science', 'Ben Tyler', 6, 1);

INSERT INTO course (name, department, instructor, credit_hours, university_id)
VALUES ('Java', 'Computer Science', 'Asset', 6, 1);

INSERT INTO course (name, department, instructor, credit_hours, university_id)
VALUES ('Python', 'Computer Science', 'Ben Tyler', 6, 1);

INSERT INTO course (name, department, instructor, credit_hours, university_id)
VALUES ('Prolog', 'Computer Science', 'Ben Tyler', 6, 1);


INSERT INTO book (name, author, genre, published_date, publisher, page_number)
VALUES ('Spring 6', 'Robert', 'Programming', '2020-07-23', 'Sun', 1411);

INSERT INTO book (name, author, genre, published_date, publisher, page_number)
VALUES ('Spring Philosophy', 'Gosling', 'Programming', '2020-07-23', 'Sun', 1411);

INSERT INTO book (name, author, genre, published_date, publisher, page_number)
VALUES ('Python Crash', 'Martin', 'Programming', '2017-07-23', 'Sun', 1411);


UPDATE book SET course_id = 3 WHERE book.id = 1;
UPDATE book SET course_id = 3 WHERE book.id = 2;
UPDATE book SET course_id = 4 WHERE book.id = 3;

INSERT INTO course_pre_requisite (course_id, course_pre_requisite_id)
VALUES (2, 3);
INSERT INTO course_pre_requisite (course_id, course_pre_requisite_id)
VALUES (2, 4);

INSERT INTO student_course (student_id, course_id)
VALUES (1, 1);
INSERT INTO student_course (student_id, course_id)
VALUES (1, 2);
INSERT INTO student_course (student_id, course_id)
VALUES (2, 3);
INSERT INTO student_course (student_id, course_id)
VALUES (2, 4);