With the native profile active, you can invoke the native:compile goal to trigger native-image compilation:

> mvn -Pnative native:compile

To build the image, you can run the spring-boot:build-image goal with the native profile active:

> mvn -Pnative spring-boot:build-image

Once you have run the appropriate build command, a Docker image should be available. You can start your application using docker run:

> docker run --rm -p 8080:8080 docker.io/library/spring-native:0.0.1-SNAPSHOT

```
You should see output similar to the following:

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::  (v3.2.0)
....... . . .
....... . . . (log output here)
....... . . .
........ Started MyApplication in 0.08 seconds (process running for 0.095)
```

more

https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/native-image.html#native-image