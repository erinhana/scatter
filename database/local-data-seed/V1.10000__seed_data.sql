INSERT INTO users (first_name, last_name, email_address, password)
values ('Erin', 'Hanafin', 'erin.hanafin@unosquare.com', 'password');

INSERT INTO todos (user_id, description, deadline, created_at, updated_at)
values (1, 'test description', '2023-10-02', now(), now());

INSERT INTO activities (todo_id, title, description, created_at, updated_at, time_spent)
values (1, 'activity title', 'activity description', now(), now(), 15);

INSERT INTO blockers (user_id, title, description, created_at, updated_at, blocker_type_id)
values (1, 'blocker title', 'blocker description', now(), now(), 1);

INSERT INTO activity_blockers (blocker_id, activity_id, time_spent)
values (1, 1, 15);




-- blocker_id INTEGER NOT NULL,
--     activity_id INTEGER NOT NULL,
--     time_spent INTEGER NOT NULL,





