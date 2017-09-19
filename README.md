# naspolycms
# Database script: cms_academic.sql - Attached to a mail sent to you

# Database setup instruction

1. Create a database in a mysql database and name it cms_academic. Then export this script into the cms_academics database.
2. Run the following sql script against the database, in the sql command editor, to insert STUDENT and FACULTY role and you are done setting up the database:

INSERT INTO `cms_academic`.`role` (`role_id`, `rolename`) VALUES (1, 'STUDENT');
INSERT INTO `cms_academic`.`role` (`role_id`, `rolename`) VALUES (2, 'FACULTY');

Although, the database script provided has been seeded with roles. So the above step 2 is not required.

# Application setup
1. Download the source code and import into the eclipse IDE as maven project.
2. Build the project while having your internet connection active so that all the project dependencies can be downloaded
3. Change database.username and database.password to your mysql database username and password respectively 
   in the database.properties file. This will ensure that the application can be connected to the cms_academic database.
4. Deploy and run the applicaiton on a tomcat server 7.0.72

