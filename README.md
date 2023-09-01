# Blog REST API

### To run this project on your device you have to:

- [x] Install java (20)
- [x] Install env - I made it in IntelIj, but it doesn't matter
- [x] create DB and connect to it (in application.properties file)

Hibernate should create tables automatically 
#### Hope you enjoy ;D

Application is secured with JWT token,
so after you create user
(on link http://localhost:8080/api/v1/auth/register)

you need to copy auto-generated token and paste it whenever you want to use a resource

__Only admin users can create new categories and posts__

Roles ROLE_ADMIN and ROLE_USER are created automatically, but new user default role is ROLE_USER

if you want to create a user with admin rights, you have to change their role manually (in DB: change role_id in table users_roles)

## CLOTHE

| STATUS | LINKS                                 | REQUEST CODE  | RETURN TYPE                     | PARAMETERS        | ACCESS |
|--------|---------------------------------------|---------------|---------------------------------|-------------------|--------|
| GET    | `/api/v1/posts/{id}`                  | 200 (OK)      | `ResponseEntity<PostDto>`       | `long`            | USER   |
| POST   | `/api/v1/posts`                       | 201 (Created) | `ResponseEntity<PostDto>`       | `PostDto`         | ADMIN  |
| PUT    | `/api/v1/posts/{id}`                  | 200 (OK)      | `ResponseEntity<PostDto>`       | `long`, `PostDto` | ADMIN  |
| DELETE | `/api/v1/posts/{id}`                  | 200 (OK)      | `String`                        | `long`            | ADMIN  |
| GET    | `/api/v1/posts?parameters`            | 200 (OK)      | `PostResponse`                  | -                 | USER   |
| GET    | `/api/v1/posts/category/{categoryId}` | 200 (OK)      | `ResponseEntity<List<PostDto>>` | `long`            | USER   |


## COMMENT

| STATUS | LINKS                                           | REQUEST CODE  | RETURN TYPE                  | PARAMETERS                        | ACCESS |
|--------|-------------------------------------------------|---------------|------------------------------|-----------------------------------|--------|
| GET    | `/api/v1//posts/{postId}/comments`              | 200 (OK)      | `List<CommentDto>`           | `long`                            | USER   |
| GET    | `/api/v1//posts//{postId}/comments/{commentId}` | 200 (OK)      | `ResponseEntity<CommentDto>` | `long`, `long`                    | USER   |
| POST   | `/api/v1/posts/{postId}/comments`               | 201 (Created) | `ResponseEntity<CommentDto>` | `long`, `CommentDto`, `Principal` | USER   |
| PUT    | `/api/v1/posts/{postId}/comments/{commentId}`   | 200 (OK)      | `ResponseEntity<CommentDto>` | `long`,`long`, `CommentDto`       | USER   |
| DELETE | `/api/v1/posts/{postId}/comments/{commentId}`   | 200 (OK)      | `String`                     | `long`, `long`                    | USER   |


## CATEGORY

| STATUS | LINKS                             | REQUEST CODE  | RETURN TYPE                         | PARAMETERS            | ACCESS |
|--------|-----------------------------------|---------------|-------------------------------------|-----------------------|--------|
| GET    | `/api/v1/categories`              | 200 (OK)      | `ResponseEntity<List<CategoryDto>>` | -                     | USER   |
| POST   | `/api/v1/categories`              | 201 (Created) | `ResponseEntity<CategoryDto>`       | `CategoryDto`         | ADMIN  |
| PUT    | `/api/v1/categories/{categoryId}` | 200 (OK)      | `ResponseEntity<CategoryDto>`       | `long`, `CategoryDto` | ADMIN  |
| DELETE | `/api/v1/categories/{categoryId}` | 200 (OK)      | `String`                            | `long`                | ADMIN  |
| GET    | `/api/v1/categories/{categoryId}` | 200 (OK)      | `ResponseEntity<CategoryDto>`       | `long`                | USER   |

## AUTH

| STATUS | LINKS                   | REQUEST CODE  | RETURN TYPE                       | PARAMETERS    | ACCESS |
|--------|-------------------------|---------------|-----------------------------------|---------------|--------|
| POST   | `/api/v1/auth/login`    | 200 (OK)      | `ResponseEntity<JWTAuthResponse>` | `LoginDto`    | USER   |
| POST   | `/api/v1/auth/register` | 201 (Created) | `ResponseEntity<String>`          | `RegisterDto` | USER   |


## Documentation
__http://localhost:8080/swagger-ui/index.html__