# library-manager
The project is REST-API built using Spring Boot and Angular.
Application allows to search for books/authors and order copies.
There is also an admin panel that can be accessed only by authorized users.
> Project is not finished yet

## technologies
- Spring Boot
- Angular
- MySQL
- Firebase
- HTML, CSS, Bootstrap

## features
- login/register
- searching for books/authors
- books filtering by dynamically loaded categories
- pagination
- book copies borrow system
- user list (activating/deactivating, deleting..)
- Create/Read/Delete operations for copies, books, authors and categories

## Security
Project provides authentication and authorization with JWT Spring Security.
There are parts of application available for not logged users, and parts that require specific
permissions.
> User types:
- not logged in
- unauthorized
- authorized
- admin
> Example JWT token payload:
<img src="https://user-images.githubusercontent.com/64193740/106105103-b6a67f00-6143-11eb-97e4-d6558c57a28a.png" width=250>

Security has some drawbacks. Application uses Firebase (just for the image storage) but it's not secured yet. It lacks validation between angular and firebase - it will be provided in the future.
Also I would like to move roles and permissions from java Enums to database.

## erd diagram
<img src="https://user-images.githubusercontent.com/64193740/106116162-e2306600-6151-11eb-82e8-52fa1c0a3818.png" width=700>

## preview

<img src="https://user-images.githubusercontent.com/64193740/106126574-cdf26600-615d-11eb-9989-d407e561a12a.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106126775-1c076980-615e-11eb-8401-eef86f1c180c.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106126806-26296800-615e-11eb-8268-66582c947ade.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106126813-275a9500-615e-11eb-8552-096112f40a2b.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106126999-612b9b80-615e-11eb-909f-c40ed6749dd4.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106128078-63dac080-615f-11eb-97bf-0b7b09bb51cc.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106127007-638df580-615e-11eb-9a0d-b4a07640b371.png" width=800>
<img src="https://user-images.githubusercontent.com/64193740/106127148-8ae4c280-615e-11eb-8c49-fe6dd47c68d8.png" width=800>
