@echo off
echo Starting Balance Tracker Server...
start cmd /k "cd /d C:\Users\DELL\OneDrive\Documents\MyJava\balance-tracker && mvn spring-boot:run"

timeout /t 10 >nul

echo Opening browser...
start chrome http://localhost:8080/balance

exit
