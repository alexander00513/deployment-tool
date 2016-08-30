@echo off

set JAVA=C:\Program Files\Java\jdk1.7.0_75

if "x%JAVA%" == "x" (
  set JAVA=java
) else (
  set JAVA="%JAVA%\bin\java"
)

"%JAVA%" -jar deployment-tool-0.0.1-SNAPSHOT-jar-with-dependencies.jar