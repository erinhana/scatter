ALTER TABLE activity_blockers 
    DROP CONSTRAINT activty_blockers_activities_activity_id_fk
  , ADD CONSTRAINT activty_blockers_activities_activity_id_fk
    FOREIGN KEY (activity_id) REFERENCES activities (id) ON DELETE CASCADE; 

