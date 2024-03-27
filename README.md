# Practice_JAVA_2024

<span style="color:red">I run the server and commands in <b>Windows</b> and don't test that in <b>Linux</b></span>  

## Run the server:

For run the server **cd** to "*~\Practice_JAVA_2024\mail*" and write: **./mvnw clean spring-boot:run**

## Command:

<span style="color:red">Command work only in <b>cmd</b> and don't work in <b>powershell</b></span>

**If you have** *Python* **you can write"** *| python -m json.tool* **"at the end of each command to prettify json output in** *cmd* 

### User:

Create user - *curl -X POST localhost:8080/create_user -H "Content-type:application/json" -d "{\"username\": \"your-username\", \"email\": \"your-email\"}"*

Update user - *curl -X PUT localhost:8080/update_user/{id} -H "Content-type:application/json" -d "{\"username\": \"your-new-username\", \"email\": \"your-new-email\"}"*

Update user username - *curl -X PUT localhost:8080/update_user/username/{id} -H "Content-type:application/json" -d your-new-username*

Update user email - *curl -X PUT localhost:8080/update_user/email/{id} -H "Content-type:application/json" -d your-new-email*

Delete user - *curl -X DELETE localhost:8080/delete_user/{id}*

Get all users (pageable) - *curl -X GET localhost:8080/get_users/{page}*

Get user by username - *curl -X GET localhost:8080/get_user/username/{username}*

Get user by email - *curl -X GET localhost:8080/get_user/email/{email}*

### Send letters:

REST call to one user - *curl -X POST localhost:8080/send_email/{sender_email}/{user_to_send_id} -H "Content-type:application/json" -d "{\"subject\": \"subject-of-your-email\", \"body\": \"body-of-your-email\"}"*

Send email for all users use cron expression:

Create cron - *curl -X POST localhost:8080/create_cron/{sender_email}/{schedule_name} -H "Content-type:application/json" -d "{\"letter\": {\"subject\": \"subject-of-your-email\", \"body\": \"body-of-your-email\"}, \"cron\": {\"expression\": \"your-cron-expression\"}}"*

Update cron expression - *curl -X PUT localhost:8080/update_cron/{cron_id} -H "Content-type:application/json" -d "{\"expression\": \"your-new-cron-expression\"}"*

Delete cron - *curl -X DELETE localhost:8080/delete_cron/{cron_id}*

Get all crons (pageable) - *curl -X GET localhost:8080/get_crons/{page}*

### Log:

Get logs (pageable) - *curl -X GET localhost:8080/get_logs/{page}*