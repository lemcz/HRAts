--import.sql file
INSERT INTO USER_ACCOUNT (id, email, enabled, name, password, user_role, date_entered, date_modified) VALUES (1,'admin', true,'Administrator','$2a$10$dPzXWg5TBgTjXmF5VlhXcOB9rVn8O8O.KC6z9W46lsLyMV0h7NOyi','ROLE_ADMIN', '1999-01-08', '1999-01-08');
INSERT INTO ACTIVITY_TYPE_LKP (id, name) VALUES (1,'Call'),(2,'Email'),(3,'Meeting'),(4,'Other'),(5,'Call (Talked)'),(6,'Call (LVM)'), (7,'Call (Missed)'), (8, 'Create'), (9, 'Update');
INSERT INTO CONTRACT_TYPE_LKP (id, name) VALUES (1,'B2B'),(2,'Contract'),(3,'Hire');
INSERT INTO STATUS_TYPE_LKP (id, name) VALUES (1,'No contact'),(2,'Contacted'),(3,'Qualifying'),(4,'Submitted'),(5,'Interviewing'),(6,'Offered'),(7,'Client Declined'),(8,'Placed'),(9,'No Status'),(10,'Not in Consideration'),(11,'Candidate Responded');

INSERT INTO SECTOR (id, name) VALUES (1,'IT'),(2,'Services'),(3,'Healthcare');
