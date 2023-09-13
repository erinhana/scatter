INSERT INTO users (first_name, last_name, email_address, password)
values ('Erin', 'Hanafin', 'erin.hanafin@unosquare.com', 'password');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Test2', 'User2', 'testuser1000@test.com', 'Password1');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Glenn', 'ONeill', 'glennoneill@test.com', 'Password2');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Amber', 'Dean', 'amberdean@test.com', 'Password3');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Cillian', 'Murphy', 'cillianmurphy@test.com', 'Password4');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Justin', 'Bieber', 'justinbieber@test.com', 'Password5');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Taylor', 'Swift', 'taylorswift@test.com', 'Password6');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Van', 'McCann', 'vanmccann@test.com', 'Password7');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Ariana', 'Grande', 'arianagrande@test.com', 'Password8');

INSERT INTO users (first_name, last_name, email_address, password)
values ('Lana', 'Rey', 'lanarey@test.com', 'Password9');


INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (1, 'Collect Prescription', '2023-10-02', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (2, 'Walk Dog', '2023-10-02', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (3, 'Food Shop', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (4, 'Study', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (5, 'Change Sheets', '2023-09-20', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (6, 'Put on Wash', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (7, 'Get Petrol', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (8, 'Do Homework', '2023-09-12', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (9, 'Go For A Run', '2023-09-15', now(), now());

INSERT INTO todos (user_id, description, deadline, created_at, updated_at, completed_at)
values (10, 'Feed Cat', '2023-09-11', now(), now());



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
values (1, 'Distraction');

INSERT INTO blocker_types (description)
values (2, 'Obstacle');

INSERT INTO blockers (user_id, title, description, created_at, updated_at, blocker_type_id)
values (1, 'No study guide', 'Lost study guide', now(), now(), 1);

INSERT INTO activity_blockers (blocker_id, activity_id, time_spent)
values (1, 1, 15);




-- blocker_id INTEGER NOT NULL,
--     activity_id INTEGER NOT NULL,
--     time_spent INTEGER NOT NULL,





