@echo on
set /p DB=Introduce la url de la base de datos (si es local introducir localhost) 
set /p Port=Introduce el puerto de la base de datos (3306 en la mayoria de casos) 
set /p User=Introduce el usuario de la base de datos (root o con permisos de root) 
set /p Password=Introduce la contrasena 


mysql -u %User% -p%Password% -e "source testapp.sql"