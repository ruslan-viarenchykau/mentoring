@echo off

set /P option="Choose an option (1-5): "
call :CASE_%option%
if ERRORLEVEL 1 goto ENDPOINT

:CASE_1
set gc_type_option=-XX:+UseSerialGC
set initial_heap_size=6m
set maximum_heap_size=18m
set young_generation=2m
set start_permanent_generation=20m
set max_permanent_generation=30m
goto SETUP_FINISHED

:CASE_2
set gc_type_option=-XX:+UseParallelGC
set initial_heap_size=3m
set maximum_heap_size=12m
set young_generation=1m
set start_permanent_generation=20m
set max_permanent_generation=20m
goto SETUP_FINISHED

:CASE_3
set gc_type_option=-XX:+UseParallelOldGC
set initial_heap_size=9m
set maximum_heap_size=18m
set young_generation=3m
set start_permanent_generation=40m
set max_permanent_generation=40m
goto SETUP_FINISHED

:CASE_4
set gc_type_option=-XX:+UseConcMarkSweepGC
set threads=2
set initial_heap_size=6m
set maximum_heap_size=18m
set young_generation=2m
set start_permanent_generation=20m
set max_permanent_generation=30m
set specific_opts="-XX:ParallelGCThreads=%threads%"
goto SETUP_FINISHED

:CASE_5
set gc_type_option=-XX:+UseG1GC
set initial_heap_size=4m
set maximum_heap_size=16m
set young_generation=2m
set start_permanent_generation=12m
set max_permanent_generation=18m
goto SETUP_FINISHED

:SETUP_FINISHED
call javac Main.java
@echo on

call java -cp ..\..\..\..\..\ -Xms%initial_heap_size% -Xmx%maximum_heap_size% -XX:NewSize=%young_generation% -XX:MaxNewSize=%young_generation% -XX:PermSize=%start_permanent_generation% -XX:MaxPermSize=%max_permanent_generation% %gc_type_option% %specific_opts% com.ruslan.mentoring.GarbageCollection.task01.Main

@echo off
:ENDPOINT
pause