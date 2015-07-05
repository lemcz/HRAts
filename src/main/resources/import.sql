--import.sql file
INSERT INTO USER_ACCOUNT (id, email, enabled, name, password, user_role, date_entered, date_modified) VALUES (1,'admin','true','Administrator','admin','ROLE_ADMIN', '1999-01-08', '1999-01-08');
INSERT INTO ACTIVITY_TYPE_LKP (id, name) VALUES (1,'Call'),(2,'Email'),(3,'Meeting'),(4,'Other'),(5,'Call (Talked)'),(6,'Call (LVM)'),(7,'Call (Missed)');
INSERT INTO CONTRACT_TYPE_LKP (id, name) VALUES (1,'B2B'),(2,'Contract'),(3,'Hire');
INSERT INTO STATUS_TYPE_LKP (id, name) VALUES (1,'No contact'),(2,'Contacted'),(3,'Qualifying'),(4,'Submitted'),(5,'Interviewing'),(6,'Offered'),(7,'Client Declined'),(8,'Placed'),(9,'No Status'),(10,'Not in Consideration'),(11,'Candidate Responded');

INSERT INTO SECTOR (id, name) VALUES (1,'IT'),(2,'Services'),(3,'Healthcare');

INSERT INTO candidate (id, email, name, phonenumber) VALUES (1,'JNowak@gmail.com','Jan Nowak','12345678');
INSERT INTO candidate (id, email, name, phonenumber) VALUES (2,'MBia³ek@wykop.pl','Micha³ Bia³ek','12345678');
INSERT INTO candidate (id, email, name, phonenumber) VALUES (3,'ANowak@buziaczek.pl','Anna Nowak','12345678');
INSERT INTO candidate (id, email, name, phonenumber) VALUES (4,'RKluska@onet.pl','Roman Kluska','12345678');
INSERT INTO candidate (id, email, name, phonenumber) VALUES (5,'SJobs@apple.com','Steve Jobs','12345678');
INSERT INTO candidate (id, email, name, phonenumber) VALUES (6,'BGates@microsoft.com','Bill Gates','12345678');
