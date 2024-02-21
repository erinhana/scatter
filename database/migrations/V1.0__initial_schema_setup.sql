CREATE TABLE users (
	id serial CONSTRAINT users_pk PRIMARY KEY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    email_address VARCHAR (100) NOT NULL UNIQUE,
    password VARCHAR (100) NOT NULL
);

CREATE TABLE todos (
    id serial CONSTRAINT todos_pk PRIMARY KEY,
    user_id INTEGER NOT NULL,
    description TEXT,
    deadline DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP NULL,
    CONSTRAINT todos_users_user_id_fk FOREIGN KEY (user_id)
    REFERENCES users (id)

);

CREATE TABLE activities (
    id serial CONSTRAINT activity_pk PRIMARY KEY,
    todo_id INTEGER NOT NULL,
    title TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    time_spent INTEGER NOT NULL,
    CONSTRAINT activity_todos_todo_id_fk FOREIGN KEY (todo_id)
    REFERENCES todos (id)

);

CREATE TABLE blocker_types (
	id serial PRIMARY KEY,
    description TEXT
);

CREATE TABLE blockers (
    id serial CONSTRAINT blocker_pk PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    blocker_type_id INTEGER NOT NULL,
    CONSTRAINT blockers_blocker_types_blocker_type_id_fk FOREIGN KEY (blocker_type_id)
    REFERENCES blocker_types (id), 
    CONSTRAINT blockers_users_user_id_fk FOREIGN KEY (user_id)
    REFERENCES users (id) 

);

CREATE TABLE activity_blockers (
    id serial CONSTRAINT activity_blocker_pk PRIMARY KEY,
    blocker_id INTEGER NOT NULL,
    activity_id INTEGER NOT NULL,
    time_spent INTEGER NOT NULL,
    CONSTRAINT activity_blockers_blockers_blocker_id_fk FOREIGN KEY (blocker_id)
    REFERENCES blockers (id), 
    CONSTRAINT activty_blockers_activities_activity_id_fk FOREIGN KEY (activity_id)
    REFERENCES activities (id)

);





   
    

