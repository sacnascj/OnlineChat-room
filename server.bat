@echo off
@ ECHO.
@ ECHO.
@ ECHO.              
@ ECHO 服务器程序
@ ECHO.
pause
echo  正在启动服务器中，请稍等......
cmd /k "cd "src" && javac -encoding utf-8 Chat.java && java Chat "
echo 服务器结束！
echo. & pause 