INSERT INTO users (first_name, last_name, email_address, password)
values ('Erin', 'Hanafin', 'erin.hanafin@unosquare.com', '$2a$10$PKd49c8XioEyktW3avfew.px6DNRjaPlUy1oudoXFaFiXjSf8pa9G');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Test2', 'User2', 'testuser1000@test.com', '$2a$10$emnDH3VjGJECH2a7E10qe.nuynW//BYHuX2YoVU1ty74g32UrV1P6');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Glenn', 'ONeill', 'glennoneill@test.com', '$2a$10$bbt6/sr5XFaXl7wRYXdlM.Buhcgr8zXwUkwA9s2AlLK96o9tP0IXC');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Amber', 'Dean', 'amberdean@test.com', '$2a$10$IR8Rvyi4JWpmgwMLR41cS.MO5/m53NZCGNx/6jif5PjoRLe71k74W');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Cillian', 'Murphy', 'cillianmurphy@test.com', '$2a$10$C6orefZXbFMwRlG2tNIjWeOCsauAuKI9bxAb7PzrjFb0ASSlPgJme');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Justin', 'Bieber', 'justinbieber@test.com', '$2a$10$FSGcJdV7neXsLrkWjwmrR.3bOEnYOpU6lgKlISphWPrawR9QgCt2O');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Taylor', 'Swift', 'taylorswift@test.com', '$2a$10$9jDY9XNftjeSmb3QYRkUk.W0iA77g.8m26dJd0o.K0IUh8Rc2fjhS');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Van', 'McCann', 'vanmccann@test.com', '$2a$10$Y8X9vngH.6sDgULtuF6PwO9//LRX7/j.epOH6DrfD8Go3vtU3rJje');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Ariana', 'Grande', 'arianagrande@test.com', '$2a$10$IhmXfBvH3EkgrHu/Q4aM2uM5vA/4D.N6XXHjD7bH8uqkZXTktNwJm');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Lana', 'Rey', 'lanarey@test.com', '$2a$10$/DJNUzEuuxV0KYexHwYF0.cfksSCGCsss.vki6PCsaHwbke51EPNm');


INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (1, 'Collect Prescription', '2023-10-02', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (1, 'Walk Dog', '2023-10-02', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (1, 'Food Shop', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (2, 'Study', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (3, 'Change Sheets', '2023-09-20', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (4, 'Put on Wash', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (5, 'Get Petrol', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (6, 'Do Homework', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (7, 'Go For A Run', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (8, 'Feed Cat', '2023-09-11', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (9, 'Bake a Cake', '2023-09-11', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (10, 'Gym Session', '2023-09-11', now(), now());



INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (1, 'Collected prescription from Chemists', 'Collect Prescription', now(), now(), 60);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (2, 'Walked dog', 'Took dog to the park', now(), now(), 60);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (3, 'Bought food shop', 'Went to Tesco and did food shop', now(), now(), 120);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (4, 'Studied', 'Studied 5/10 chapters', now(), now(), 180);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (5, 'Change sheets', 'Stripped bed', now(), now(), 30);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (6, 'Put on wash', 'Washed bedding', now(), now(), 60);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (7, 'Get Petrol', 'Went to garage', now(), now(), 60);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (8, 'Homework', 'Did science homework', now(), now(), 15);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (9, 'Run', 'Ran in the park', now(), now(), 30);

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (10, 'Fed Cat', 'Fed cat in morning', now(), now(), 5);


INSERT INTO blocker_types (description)
values ('Distraction');

INSERT INTO blocker_types (description)
values ('Obstacle');

INSERT INTO blockers (user_id, title, description, created_at, updated_at, blocker_type_id)
values (1, 'Misplaced car keys', 'No spare set', now(), now(), 1), (2, 'Bad weather', 'No waterproof coat for the dog', now(), now(), 2);


INSERT INTO activity_blockers (user_id, blocker_id, activity_id, time_spent)
values (1, 1, 1, 15), (1, 2, 2, 10);




-- blocker_id INTEGER NOT NULL,
--     activity_id INTEGER NOT NULL,
--     time_spent INTEGER NOT NULL,





