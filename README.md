# redis_docker_springboot_testing
Just a testing project for running a Redis inside a docker container + Spring Boot app to connect and test TLL on entities + expired listeners.


### Enable Redis Entry Timeout Notification
This should be automatic via config file, but If I dont get it working, ssh into container (docker exec -it container_name /bin/bash) and execute:
`redis-cli config set notify-keyspace-events Ex`