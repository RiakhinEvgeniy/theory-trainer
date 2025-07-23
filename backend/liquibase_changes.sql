[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------< com.evgeniy.riakhin:backend >---------------------
[INFO] Building backend 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- liquibase:4.29.2:updateSQL (default-cli) @ backend ---
[INFO] ------------------------------------------------------------------------
[INFO] Parsing Liquibase Properties File
[INFO]   File: src/main/resources/liquibase.properties
[INFO] ------------------------------------------------------------------------
[INFO] ####################################################
##   _     _             _ _                      ##
##  | |   (_)           (_) |                     ##
##  | |    _  __ _ _   _ _| |__   __ _ ___  ___   ##
##  | |   | |/ _` | | | | | '_ \ / _` / __|/ _ \  ##
##  | |___| | (_| | |_| | | |_) | (_| \__ \  __/  ##
##  \_____/_|\__, |\__,_|_|_.__/ \__,_|___/\___|  ##
##              | |                               ##
##              |_|                               ##
##                                                ## 
##  Get documentation at docs.liquibase.com       ##
##  Get certified courses at learn.liquibase.com  ## 
##                                                ##
####################################################
Starting Liquibase at 12:55:48 using Java 21.0.2 (version 4.29.2 #3683 built at 2024-08-29 16:45+0000)
[INFO] Output SQL Migration File: C:\Projects\theory-trainer\backend\target\liquibase\migrate.sql
[INFO] Parsing Liquibase Properties File src/main/resources/liquibase.properties for changeLog parameters
[INFO] Executing on Database: jdbc:postgresql://localhost:5432/theory-training
[INFO] Successfully acquired change log lock
[INFO] Reading resource: db/changelog/changes/25-06-2025-17-51-initial-db-create-table-users.xml
[INFO] Reading resource: db/changelog/changes/26-06-2025-15-33-00-add-all-tables.xml
[INFO] Reading resource: db/changelog/changes/28-06-2025-11-18-00-initial-users.xml
[INFO] Reading resource: db/changelog/changes/28-06-2025-12-16-00-add-user-by-maven.xml
[INFO] Reading from databasechangelog
[INFO] Database is up to date, no changesets to execute
[INFO] Reading from databasechangelog
[INFO] Successfully released change log lock
[INFO] Successfully released change log lock
[INFO] Command execution complete
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.322 s
[INFO] Finished at: 2025-06-28T12:55:50+02:00
[INFO] ------------------------------------------------------------------------
