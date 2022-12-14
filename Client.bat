@echo off
@ ECHO.
@ ECHO.
@ ECHO.              
@ ECHO  客服端程序
@ ECHO.
pause
echo  正在启动客服端中，请稍等......
cmd /k "cd "src" && javac -encoding utf-8 Client.java && java Client "
echo 客服端结束
echo. & pause 