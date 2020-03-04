## Step 02.2: Part 2 Understanding HTTP Request Flow

In this step, what we want to understand is what is happening in the background of our requests.

When I type http://localhost:8080/login, what's happening, what's a request, what's a response, what kind of request methods is going in. So that's basically what we want to understand in this particular theory part.

In the web browser, If I type http://localhost:8080/login, I get this "Hello World Modified". When I type this URL, how is data going back to this server? So the server is getting the request, If you look at your Console Tab in Eclipse, you would see that in the log the server says *o.s.web.servlet.DispatcherServlet  GET "/login", parameters={}*

```
Console output:

[2m2020-03-04 06:42:50.154[0;39m [32m INFO[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Completed initialization in 5 ms
[2m2020-03-04 06:42:50.160[0;39m [32mDEBUG[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m GET "/login", parameters={}
[2m2020-03-04 06:42:50.163[0;39m [32mDEBUG[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36ms.w.s.m.m.a.RequestMappingHandlerMapping[0;39m [2m:[0;39m Mapped to com.imh.springboot.web.controller.LoginController#loginMessage()
[2m2020-03-04 06:42:50.181[0;39m [32mDEBUG[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mm.m.a.RequestResponseBodyMethodProcessor[0;39m [2m:[0;39m Using 'text/html', given [text/html, application/xhtml+xml, image/webp, image/apng, application/xml;q=0.9, application/signed-exchange;v=b3;q=0.9, */*;q=0.8] and supported [text/plain, */*, text/plain, */*, application/json, application/*+json, application/json, application/*+json]
[2m2020-03-04 06:42:50.182[0;39m [32mDEBUG[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mm.m.a.RequestResponseBodyMethodProcessor[0;39m [2m:[0;39m Writing ["Hello World"]
[2m2020-03-04 06:42:50.187[0;39m [32mDEBUG[0;39m [35m9881[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Completed 200 OK
```

What is happening in GET "/login", parameters={}. How is the request getting created? If we do right-click in the web browser > Inspect Element > Network monitor. If I click that you can see what's happening in the background. What's happening is when I type http://localhost:8080/login, a request is created, the request is send to this server. 

```
Web browser:

#General	
1 requests
172 B transferred
11 B resources
Finish: 8 ms
DOMContentLoaded: 24 ms
Load: 24 ms

Request URL: http://localhost:8080/login
Request Method: GET
Status Code: 200 
Remote Address: [::1]:8080
Referrer Policy: no-referrer-when-downgrade

#Response Headers
Connection: keep-alive
Content-Length: 11
Content-Type: text/html;charset=UTF-8
Date: Wed, 04 Mar 2020 05:25:22 GMT
Keep-Alive: timeout=60

#Request Headers
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: gzip, deflate, br
Accept-Language: es-ES,es;q=0.9
Cache-Control: max-age=0
Connection: keep-alive
Host: localhost:8080
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: none
Sec-Fetch-User: ?1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) snap Chromium/80.0.3987.122 Chrome/80.0.3987.122 Safari/537.36
```

If you look the data, The request is sent to which URL? Request URL: http://localhost:8080/login, what method is used ? Request Method: GET, etc. Later, if we do right-click in the web browser > Inspect Element > Network monitor > Response tab, what we get back is the next.

```
Hello World
```

So, we are getting a Request, and we are getting a response back. When you type do something on the browser, the browser creates a request, it goes to the server, the server takes the request, handles it, sends a response back, and the response would be processed by the browser and formated in a beautiful HTML way. So that's basically the entire flow.

One important thing that we would discuss a lot about later as well, is the request method. When you just type something in the URL, then you're sending a request method called GET. So Get is the kindest of the simplest of the request methods.

In this step, we learn about how to create a simple URL and mapped it to a Java method. We used @Controller, @RequestMapping, @Response body, and also we understood a high level of how request and response work. 
