# JWT Auth server
This is simple JWT Auth Server.

## Example Of Rest API
### Commands 
Register:

&nbsp;&nbsp; **URL** : `/api/auth/register`

&nbsp;&nbsp; **Method** : `POST`

&nbsp;&nbsp; **Body** :
```json
    {
        "username": "user1",
        "email": "user1@gmail.com",
        "password": "1"
    }
```

&nbsp;&nbsp; **Response Body (JWT_Token)** :
```json
    User registered successfully
```

Login:

&nbsp;&nbsp; **URL** : `/api/auth/login`

&nbsp;&nbsp; **Method** : `POST`

&nbsp;&nbsp; **Body** :
```json
    {
        "username": "user1",
        "password": "1"
    }
```

&nbsp;&nbsp; **Response Body (JWT_Token)** :
```json
    {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTc4MzMzOTAwLCJpYXQiOjE1NzgyMzM5MDB9.N01PscrwkVXmIi9L5WDi5jR_OpHy3Xy893tES3nZRQY",
        "tokenType": "Bearer"
    }
```

### Query
Get User Info:

&nbsp;&nbsp; **URL** : `/api/users/{username}`

&nbsp;&nbsp; **Header** : `Authorization: Bearer [JWT_Tocker]`

&nbsp;&nbsp; **Method** : `GET`
