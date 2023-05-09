# scatter
Repository for Productivity App- COE Project

## Problem Definition 


The purpose of this application is to help those who are described as being scatter-brained, it aims to assist people with organisation, time management and tracking their day-to-day life. Everyday life involves many tasks, obstacles, and distractions in which this application can assist the users with.


## Priorities 

### Must Have:

1. A user must be able to register an account
2. A user must be able to log in
3. A user must be able to log out
4. A user must be able to deactivate an account
5. A user must be able to add, edit, delete and update to-do items
6. Manage a list of obstacles
7. Add, edit, delete obstacles to/from to-do items
8. Manage a list of distractions
9. Add, edit, delete distractions to/from to-do items


### Should Have:

1. Tracking carried out on a day-to-day basis (tracking the individual entries of time spent on a task) 


### Could Have:

1. A user would be able to view a list of to-do trends
2. Could be a responsive website and therefore could be used on mobile
3. A user should be able to reset a password

### Will Not Have:

1. A user will be able to connect to existing Apple/Android applications 
2. A user will be able to send user push notifications/reminders



```mermaid
%%{init: {'theme':'default'}}%%
erDiagram
   
    USERS ||--o{ TODOS : manages
    USERS ||--o{ BLOCKERS : "encounters"
    BLOCKERS ||--o{ACTIVITIES : "affects"
    TODOS ||--o{ACTIVITIES : "contains"
    ```

### Entity Relationship Diagram

```mermaid
%%{init: {'theme':'default'}}%%
erDiagram
    Users ||--o{ Todos  : manages
    Users {
        int id pk 
        string first_name
        string last_name
        string email_address
        varchar(100) password

    }
    Users ||--o{ Blockers : face
    Blockers {
        int id pk 
        int user_id fk
        string title
        string description 
        datetime created_at
        datetime updated_at
        int blocker_type_id
    }
    Blockers ||--|{ Activity_blockers : create

    Blocker_types ||--|{ Blockers : are

    Blocker_types {
        int id pk 
        string description 
        
    }

    Users ||--o{ Todos : manages
    Todos {
        int id pk
        int user_id fk
        string description
        datetime deadline
        datetime created_at
        datetime updated_at
        datetime completed_at

    }
    Todos ||--|{ Activities : contains
    Activities {
        int id pk 
        int todo_id fk
        string title
        string description 
        datetime created_at
        datetime updated_at
        int time_spent

    }
    Activities ||--|{Activity_blockers : face
    Activity_blockers {
        int id pk 
        int blocker_id fk
        int activity_id fk
        int time_spent

    }
 ```


