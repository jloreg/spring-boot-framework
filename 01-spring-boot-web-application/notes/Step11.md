## Step 11: Architecture of Web Applications

What You Will Learn during this Step:

- Discuss about Architecture of web applications

In this step, we'll take a small pause to take in the big picture of the architecture of web applications.

So we'll take a high-level picture of how web applications are typically developed. What you're seeing on the screen, are the typical layers in a typical JavaEE application, you have a web layer, you have a business layer, you have a data layer, and you have an integration layer.

Typically applications get the data from the database, to talk to the database we use a data layer, and to talk to other applications we use an integration layer. Let's say we are managing Todo's not in our own database, but we want to talk to a Todo management application that manages a List. In that kind of situation, I would need to talk to the services which are offered by that Todo management application. Let's say I want to get the current values of currency that I will use, I need to integrate with other systems, so the integration layer helps me to integrate with other systems. 

The business layer in any typical application would be the one which has all the business logic for that specific application. This layer talks to the data layer, talk to the integration layer, get all the data it needs to calculate stuff around that, and have all the business logic in here in the business layer. So these are typically the important background layer. 

Once you have the business logic, you want to actually expose either Web services on top of them, or you'd want to have applications using them, either you're showing them in a web application on a screen, or you are exposing REST services or SOAP services to the outside world. All that kind of logic typically is in the web layer. **So the Web layer is the one which exposes all the business logic that you have to the outside world**.

![ ](/home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application/images/javaearchitecture.png  "JavaEE Architecture")

In the MVC model, M or Model is the Business, Data and Integration layer. The V or View which is the .jsp pages as far as we are concerned right now is in the Web layer. And C or Controller is in the Web layer too. So now, The V and the C of the MVC architecture are now in the web layer. That's kind of the typical architecture of any JavaEE application.

Model = Business, Data, and Integration model.
View = Web layer
Controller = Web layer

If we look at the typical framework choices in web applications, Servlet JSP is one of the basic ways of developing web application. Most of the applications use an MVC framework of the kind of Struts or Spring MVC. Spring MVC is quite the most popular MVC framework and that is what we are using in this specific application as well.

![ ](/home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application/images/webframeworks.png  "Web Frameworks")

As far as the View is concerned you have multiple options. You have just JSP/EL/JSTL. EL & JSTL make it easy to display data-bind to binding and stuff in a .jsp. The other options are Freemarker and Velocity templates, and JSF. So these are kind of the options for the View. From the view, you are exposing RESTful WebServices which are consumed from AngularJS. Those are typically the things which typically are exposed from a web layer of a typical web application. Let's dig further into the Model1 and into the Model2 architectures which are popular in the web layer.

The Model 1 architecture is basically from the browser, you send the data requests directly to a JSP. What we are doing is from the browser we are sending it to the controller, and the controller sends it to the JSP. One of the first architectures for web applications where, from the browser, the request was directly was sent to the JSP, so it was not sent to the controller but it was sent directly to the JSP. So the request went to the JSP, JSP handles that request and it would redirect to the next JSP, there was no concept of a Servlet then, so all that we had was JSP pages. This was one of the first architectures which were used to developing web applications. Obviously these have a lot of problems because JSP becomes huge. So JSP has all the controller logic, all the view logic, and another period of time, also a lot of business logic, so this application became unmaintainable. 

From there came in the Model 2 architecture. From the Browser the request goes to the Servlet, so from the browser like when you submit a request on the browser, the request goes to the Servlet, the Servlet would talk to the business logic, would finalize the Model, and make it available to view. And then the View would be rendered to the browser, and the next request from the browser might go to a different Servlet. With this kind of architecture, we had a lot of popular frameworks come in like Struts, for example, such one had this Model 2 architecture. From the browser, you redirected the request directly to the Servlets which are on the server. 

Model 2 with FrontController architecture, is an evolution on top of the model to architecture, so from the browser, we always send a request to a single controller. This controller is called a FrontController, for example in SpringMVC this is called DispatcherServlet, so DispatcherServlet it is nothing but a FrontController.

So what happens is all the requests go to the FrontController. So whether you are sending a /login request or a /list-todos request it will always go first to the DispatcherServlet, and from DispatcherServlet, the dispatcher said says "OK,  /login => go to LoginController, /list-todos => go to TodoController", based on the URL different controller decide which controller to go to. Once controllers return the data back, it decides which view to render, and it would send the response back to the browser. So all data would be going through the different controller in a Model 2 architecture with FrontController.

![ ](/home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application/images/model2withfrontcontroller.png  "Model2 with FrontController architecture")

Typically this is the most famous architecture with MVC applications. The reason is the FrontController becomes the central point of the entire application, so you can implement things like security and all that kind of stuff at a single place. So if I want to log every request, I can add it to FrontController, if I want to implement security around all the URLs, I can do that in a different controller, so all the centralized logic I can start implementing it in a different controller.

The idea behind this step was to give you an overview of the typical architecture of Java web applications. While we want to get our hands dirty it's very important for you to get the big picture of how things are organized.

So we looked at the different layers in a typical web application with business data and integration, we looked at different framework choices that are available in a web layer.

We looked at the Model 1 architecture where they were only just JSP and JSP's became huge, and then we looked Model 2 architecture where the request directly went to different Servlets at different points in time. The last thing which we looked was Model 2 with FrontController architecture where all the requests from the browser first go to a FrontController, and FrontControl then decide which controller to call.