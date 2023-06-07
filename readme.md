# GrownPlants-API

# Documentation

## Sign Up

- Method: POST
- Url: /api/v1/auth/signup
- Body: JSON

```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

## Sign In

- Method: POST
- Url: /api/v1/auth/signin
- - Body: JSON

```json
{
  "email": "string",
  "password": "string"
}
```

## Profile

### Get Profile

- Method: GET
- Url: /api/v1/users/profile
- Header: Bearer token

```json
{
  "error": "boolean",
  "message": "string",
  "data": [
    {
      "name":"string",
      "email": "string",
      "password": "string",
      "createdAt": "string",
      "imageUrl": "string",
    }
  ]
}
```

## Articles

### Post Upload Article

- Method: POST
- Url: /api/v1/articles
- Header: Bearer token
- Body: form-data
Form Data
| KEY | VALUE |
| --------------- | --------------- |
| file| file image  |
| name  | "string" |
| description  | "string" |
| description  | "string" |
| description  | "string" |


```json
{
  "error": "boolean",
  "message": "string",
  "data": [
    {
      "imageUrl": "string",
      "name": "string",
      "category": "string",
      "description": "string",
      "prescription": ["string"],
      "prevention": ["string"],
    }
  ]
}
```

### Get Articles

- Method: GET
- Url: /api/v1/articles
- Header Authorization : Bearer token

```json
{
  "error": "boolean",
  "message": "string",
  "data": [
    {
      "imageUrl": "string",
      "name": "string",
      "category": "string",
      "description": "string",
      "prescription": ["string"],
      "prevention": ["string"],
    }
  ]
}
```
