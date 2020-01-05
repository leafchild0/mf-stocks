# JWT Auth server
This is simple JWT Auth Server.

## Example Of Rest API
### Commands 
Sing In:

&nbsp;&nbsp; **URL** : `/api/auth/signin`

&nbsp;&nbsp; **Method** : `POST`

&nbsp;&nbsp; **Body** :
```json
    {
        "username": "1",
        "password": "1"
    }
```

&nbsp;&nbsp; **Response Body (JWT_Tocker)** :
```json
    {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTc4MzMzOTAwLCJpYXQiOjE1NzgyMzM5MDB9.N01PscrwkVXmIi9L5WDi5jR_OpHy3Xy893tES3nZRQY",
        "tokenType": "Bearer"
    }
```

### Query
Get User Info:

&nbsp;&nbsp; **URL** : `/api/users/{user_id}`

&nbsp;&nbsp; **Header** : `Authorization: Bearer [JWT_Tocker]`

&nbsp;&nbsp; **Method** : `GET`
