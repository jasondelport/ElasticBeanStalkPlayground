mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=prod"    
mvn clean package -P prod    
java -jar target/demo.jar     




https://aws.amazon.com/blogs/database/choosing-the-right-dynamodb-partition-key/

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.CoreComponents.html

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-sort-keys.html

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-query-scan.html





