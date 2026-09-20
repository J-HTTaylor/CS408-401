### weekly assignment collecter
The following program will show a given canvas' user any assignments that need to be completed for the following week, provides their due date and a link to their submission page.
It automatically ommits all assignments that have been submitted at least once.
Each set of incomplete assignments are automaically sorted by the user's currently enrolled courses which can be selected and searched via the Combo Box at the top left of the page.
The only exception to this rule is anyting that makes use of an external tool to get scores/ submission such as 'ZyBooks'.
This prevents any assignment being overlooked.

### Setup instructions
to ensure a correct installation ofthe program clone the entire repo from github with the command
$ git clone "https://github.com/J-HTTaylor/CS408-401"$

The following files should be cloned over:
- .github/workflows/pr-validation.yml
- .mvm/wrapper/maven-wrapper.jar
- .mvm/wrapper/maven-wrapper.properties
- .mvm/wrapper/MarvenWrapperDownloader.java
- /src/main/fronted/index.html
- /src/main/java/com/example/application/Application.java
- /src/main/java/com/example/application/CanvasAPiCalls.java
- /src/main/java/com/example/application/MainView.java
- /src/main\resources/META-INF/resources/icons/icon.png
- /src/main/resources/META-INF/resources/styles.css
- /src/main/resources/META-INF/resources/application.properties.example (this is used instead of the usual .env file)
- .gitignore
- .gitpod.Dockerfile
- .gitpod.yml
- mvnw
- mvnw.cmd
- pom.xml
- README.md

JDK 21+ is needed to fully run this program with no issues
Maven is not needed to be pre-installed due to the use of a maven wrapper exisitng.

The following APis used and their usage in the program is below:

| Api Endpoint | Purpose | Data retrived |
| -------- | -------- | -------- |
| GET /api/v1/users/self/courses?enrollment_state=active | Lists all of the curently enrolled courses for a given user| <b>name<b> - name of the course.|
| GET /api/v1/users/self/upcoming_events | Used to get all upcoming assignments and events assigned to a given user| <b>Context_name<b> - gets the name of the course used to filter to a given course <br><b>assignment{has_submitted_submissions}<b> - used to see if any file has been submitted by the user<br><b>assignment{external_tool_tag_attributes}<b> - used to check if an external tool is used to create/provide a subimssion or grade<br><b>all_day_date<b> - used for getting the due date of the assignment<br><b>html_url<b> - used to get a link to the assignment page used by the button.


### Running the Application
There are two ways to run the application :  using `mvn spring-boot:run` or by running the `Application` class directly from your IDE.

You can use any IDE of your preference,but we suggest Eclipse or Intellij IDEA.
Below are the configuration details to start the project using a `spring-boot:run` command. Both Eclipse and Intellij IDEA are covered.

#### Eclipse
- Right click on a project folder and select `Run As` --> `Maven build..` . After that a configuration window is opened.
- In the window set the value of the **Goals** field to `spring-boot:run` 

Once configurations are set clicking `Run` will start the application

#### Intellij IDEA
- On the right side of the window, select Maven --> Plugins--> `spring-boot` --> `spring-boot:run` goal

Clicking on the green run button will start the application.

After the application has started, you can view your it at http://localhost:8080/ in your browser.

If you want to run the application locally in the production mode, use `package` and `java -jar target/spring-skeleton-1.0-SNAPSHOT.jar` commands instead.

### Debugging Token
To double check that the api works correctly you can use the following bash command:
$ curl -H "Authorization: Bearer YOUR_CANVAS_TOKEN"https://boisestatecanvas.instructure.com/api"

If it returns a html doctype then the api and token has no issues.