## Step 31: Data initialization with data.sql

One of the things with the in-memory database is the fact that each time the data gets a refresh. So when we start the application each time there is no data, there is no starting data which is present. One of the ways you can fix that is by going to source/main/resources and adding a file called data.sql. So here I can insert the data that I would want at this time.

```
insert into TODO
values (10001, 'Learn Spring Boot', false, sysdate(), 'in28minutes');
insert into TODO
values (10002, 'Learn Angular JS', false, sysdate(), 'in28minutes');
insert into TODO
values (10003, 'Learn To Dance', false, sysdate(), 'in28minutes');
```

So whenever you need some data in the application, when you're using an in-memory database, you can create a date.sql file and put it in here. If you want the data to be created in the main application, it can be in the src/main/resources or if you really want to use this data when you're writing a unit test, you can actually put it in src/test/resources as well. 

**Resume**

In this step, we looked at how to have some starting data when we're using an in-memory database like h2.

## Complete Code Example

##### /src/main/resources/data.sql

```sql
insert into TODO
values (10001, 'Learn Spring Boot', false, sysdate(), 'in28minutes');
insert into TODO
values (10002, 'Learn Angular JS', false, sysdate(), 'in28minutes');
insert into TODO
values (10003, 'Learn To Dance', false, sysdate(), 'in28minutes');
```