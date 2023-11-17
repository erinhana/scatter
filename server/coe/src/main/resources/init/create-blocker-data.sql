INSERT INTO users (first_name, last_name, email_address, password)
values ('Erin', 'Hanafin', 'erin.hanafin@unosquare.com', '$2a$10$PKd49c8XioEyktW3avfew.px6DNRjaPlUy1oudoXFaFiXjSf8pa9G');

INSERT INTO blocker_types (description)
values ('Distraction');

INSERT INTO blockers (title, description, created_at, updated_at, user_id, blocker_type_id)
values ('No study guide', 'Lost study guide', now(), now(), 1, 1);