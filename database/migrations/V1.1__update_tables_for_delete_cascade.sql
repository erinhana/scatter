ALTER TABLE activity_blockers
   DROP CONSTRAINT activity_blockers_blockers_blocker_id_fk
 , ADD  CONSTRAINT activity_blockers_blockers_blocker_id_fk
   FOREIGN KEY (blocker_id) REFERENCES blockers (id) ON DELETE CASCADE;