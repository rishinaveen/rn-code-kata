#### Objective

Process an input file with driving records and generate a report containing each driver with total miles driven and average speed ordered by most miles (rounded to nearest integer) driven to least.

#### Assumptions

+ Input file located in classpath (if run in a IDE or from a container) / root of run-from-here folder and file name passed as an argument to main program.  

#### Package modeling

Feature based instead of traditional layer based. Feature here is driver history. Another feature would have it's own package.
 
#### Primary interfaces

+ Reader (to read and convert the input file into java Driver/Trip objects). We are using a file reader here.
+ Processor (to establish the relationships between Driver and Trip objects, calculate total miles and speed)
+ Writer (to write java DrivingHistory objects to output file). We are writing to console and log file as well.

#### Test cases (TDD appraoch)

1. Test if input file has some input
2. Test if command types are expected types
        Driver Dan
        Trip Dan ...
3. Test wrong/invalid input file. Could be third type of command, wrong time format, miles format, non-space delimiter, more than one space delimiter, etc,.
    
    Input:
    
        Dan Driver
        Alex Driver
        Bob Driver
        Dan Trip ...
    
    Output:
    
        Invalid input file. Verify the input file.

4. Test if Start and End times are not past midnight
5. Test if Start time is always before End time
6. Test if trips with avg. speed < 5mph or > 100mph are discarded
7. Test if rounding miles and miles per hour are rounded to the nearest integer
8. Test the sort order in the report with most miles to least
9. Test driver report generated from driver history file
       
    Input: 
       
        Driver Dan
        Driver Alex
        Driver Bob
        Trip Dan 07:15 07:45 17.3
        Trip Dan 06:12 06:32 21.8
        Trip Alex 12:01 13:16 42.0
       
    Expected output:
       
        Alex: 42 miles @ 34 mph
        Dan: 39 miles @ 47 mph
        Bob: 0 miles

#### Implementation steps - high-level
1. Read the input file
2. Process the records
3. Generate the report

#### Challenges
1. Running a jar file from command line and being able to pass a file name located in classpath turned out to be a very complex process.

    Solution:
           
        using makeItRunnable task from Gradle which will extract the fatJar (w/ dependencies) into run-from-here folder and test fromm there.
        
#### Project architecture
+ Project is built with Java 8 with huge usage of streams api.
+ Project is based on Gradle as it is more customizable, configurable and light weight
+ Used a few dependencies such as lombok, log4j, yaml,. etc to make life easier
+ Used a fatJar plugin to build the final jar with dependencies included for easier program execution
+ Used JUnit dependency for testing the project

#### Building and running/testing the project
+ Issue the below command at CLI

        gradle makeItRunnable
        
    this should trigger a full build with test case execution, generate a fatJar with all dependencies, extract the fatJar into a folder for easy testing
    
+ For a report on programmatic test executions, checkout the index.html page at build/reports/tests/test/
    
+ For testing, navigate to run-from-here folder post-MakeItRunnable command and issue the following command at CLI

        java com.rn.TaskExecutor input.txt
        
+ For testing a new input file, put the new file at the root of run-from-here folder and repeat the above step with new file name.
