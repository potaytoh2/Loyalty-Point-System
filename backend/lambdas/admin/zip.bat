@echo off
powershell -Command "cd %~dp0/maven; ./mvnw package `"-Dmaven.test.skip`" ; if ($?) {cd ..; Compress-Archive -Update maven/target/admin-0.0.1-SNAPSHOT.jar function.zip}"