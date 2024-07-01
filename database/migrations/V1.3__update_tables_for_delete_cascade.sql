ALTER TABLE todos
    DROP CONSTRAINT todos_users_user_id_fk
  , ADD CONSTRAINT todos_users_user_id_fk
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE; 

ALTER TABLE blockers
    DROP CONSTRAINT blockers_users_user_id_fk
  , ADD CONSTRAINT blockers_users_user_id_fk
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE; 

ALTER TABLE activities
    DROP CONSTRAINT activity_todos_todo_id_fk
  , ADD CONSTRAINT activity_todos_todo_id_fk
    FOREIGN KEY (todo_id) REFERENCES todos (id) ON DELETE CASCADE; 