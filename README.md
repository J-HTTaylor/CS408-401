### weekly assignment collecter
The following program will show a given canvas' user any assignments that need to be completed for the following week, provides their due date and a link to their submission page.
It automatically ommits all assignments that have been submitted at least once.
Each set of incomplete assignments are automaically sorted by the user's currently enrolled courses which can be selected and searched via the Combo Box at the top left of the page.
The only exception to this rule is anyting that makes use of an external tool to get scores/ submission such as 'ZyBooks'.
This prevents any assignment being overlooked.
This also allows a user to give their focus on courses that they know take more effort/time to complete rather than just seeing all assignments and doing the closest one. 
For example a 5 min quiz is a lot less demanding/stressful that a large project that needs more time. This program helps give emphasis.

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

You will need to replace the application.properties.example file with application.properties while replacing the boilerplate CANVAS_BASE_URL and CANVAS_API_TOKEN with your own token and url address. Otherwise this will not talk to the website correctly.

JDK 21+ is needed to fully run this program with no issues
Maven is not needed to be pre-installed due to the use of a maven wrapper exisitng.

The following APis used and their usage in the program is below:

| Api Endpoint | Purpose | Data retrived |
| -------- | -------- | -------- |
| GET /api/v1/users/self/courses?enrollment_state=active | Lists all of the curently enrolled courses for a given user| <b>name</b> - name of the course.|
| GET /api/v1/users/self/upcoming_events | Used to get all upcoming assignments and events assigned to a given user| <b>Context_name</b> - gets the name of the course used to filter to a given course <br><br><b>assignment{has_submitted_submissions}</b> - used to see if any file has been submitted by the user<br><br><b>assignment{external_tool_tag_attributes}</b> - used to check if an external tool is used to create/provide a subimssion or grade<br><br><b>all_day_date</b> - used for getting the due date of the assignment<br><br><b>html_url</b> - used to get a link to the assignment page used by the button.


### Running the Application
There are two ways to run the application :  using `mvn spring-boot:run` from the terminal while in the folder or by running the `Application` class directly from your IDE.

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
$ curl -H "Authorization: Bearer YOUR_CANVAS_TOKEN"https://boisestatecanvas.instructure.com/api" $

If it returns a html doctype then the api and token has no issues.

### Reflection
This project was one of the biggest coinfidence knocks that I have received in quite a while. 
Luckily Vaadin provides an empty project skeleton that ensures that it at least runs to a page saying "hello world". This lead me into an area of false confidence.
Thinking that I could very quickly just add a call and it would be done. I had seen what an API call could look like prior to this but seeing one and trying to impliment it are two very different things.
Creating and testing that a api call has been called correctly via the use of curl was not an issue. What was a major issue was getting the API call and spring to correctly communicate.
Unlike with most java programs calling 'new' for a cross file class such as 'CanvasAPiCalls canvasApi' actually break the entire code.
Normally it will just call the class and methods across the files however spirng breaks theconnection and causes the url and token to become null and void.
This in turn broke all api calls and gave no returning values. It also caused me to spend an entire day trying to debug it with another couple hours fixing my VScode.
Somehow I fully broke my VScode environment and could not run simple hello world files. 
Don't ask me how because I have no idea how i managed to do this, the only thing that I do know is that I had to do a clean install if VScode to fix it.

Another smaller challenge that I faced was nested perameters and permissions issues.
Firstly the permission issues. So as a user I am not alloed to see the course id for any given course due to a lack of permissions.
There were two ways to fix this. The first would be to hard code values into the perameter but this would only work for my courses not all so it is not feasble.
The other way was to case match the courses between 2 different API call areas. This was more work but it allowed me to ensure all courses would be filterd correctly.
Nested perameters are the other challenge I faced. Granted it does sound simple in theory, it actually was a bigger pain than expected.
I needed to see submissions made for an assignment and if it used an external tool.
Instead of being a straightforward list of parameters and use to .get() method I had to use .path() to effectly trace the correct route to the parameter while allowing the perameter to not exist without throwing errors.

If I had more time to work on this project there are 2 areas I would focus on. The first is the UI and the second is including another level of api calls.
I'd spend more time on the UI due to ther fact that it currently is very basic . It works but doesn't look very good. 
Wjile the main concern is getting a working product, the appearance of the program is a big aspect that should not be overlooked.
Secondly the levels of api calls. As it stands there is only 1 level of API calls which is to Canvas. 
If given enough time I would like to be able to get the API calls for the external tools used for submissions so that I could check if assignments that make use of these tools have actually been started or completed to a sfficent level rather than just blanketly includling all of them.