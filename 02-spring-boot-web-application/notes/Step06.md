## Step 06: DispatcherServlet and Spring MVC Flow

Understand the importance of DispatcherServlet.

Spring MVC Request Flow

- DispatcherServlet receives HTTP Request.
- DispatcherServlet identifies the right Controller based on the URL.
- Controller executes Business Logic.
- Controller returns a) Model b) View Name Back to DispatcherServlet.
- DispatcherServlet identifies the correct view (ViewResolver).
- DispatcherServlet makes the model available to view and executes it.
- DispatcherServlet returns HTTP Response Back.
- Flow : http://docs.spring.io/spring-framework/docs/2.0.8/reference/images/mvc.png

**Notes**

```
- Any Request is processed by the Dispatcher Servlet => so Dispatcher Servlet is a **Front Controller**.
- Dispatcher Servlet search for some Controller mapped to the URL give it and if there is something that corresponds, redirects the flow to the Controller that match.
- Controller puts the data in the Model and specified the name of the VIew responsible for render such data, after that give it back the control to the DispatcherServlet
- Dispatcher Servlet process the data give it from the Controller (Model and name of the View) and redirects to the ViewResolver
- ViewResolver it's the attendant to find the resource that maps with the name of the View provided by the Dispatcher Servlet => ViewResolver search in application.properties
- If ViewResolver find the View that matches, provide the location of the resource to the Dispatcher Servlet
- Dispatcher Servlet makes the Model available to the View
- View executes the Model and send the response to the DispatcherServlet in HTML format.
- Dispatcher Servlet returns the response of the View directly to the web browser through HTTP.
```

![MVC Flow](/home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application/images/flow.png  "Flow")