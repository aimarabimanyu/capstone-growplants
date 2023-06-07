# GrownPlants-API

# Documentation
- Base URL : https://grow-plants-ruca2dm4pa-et.a.run.app

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
- Body: JSON

```json
{
  "email": "string",
  "password": "string",
}
```

## Predict

- Method: POST
- Url: /api/v1/predict
- Header: Bearer token
- Body: `multipart/form-data`

| KEY | VALUE | 
| --------------- | --------------- | 
| file | file image | 

- Response :
```json
{
  "error": "boolean",
  "message": "string",
  "data": {
     "predicted_disease": "string",
     "articles": [
         {
           "imageUrl": "string",
           "name": "string",
           "category": "string",
           "description": "string",
           "prescription": ["string"],
           "prevention": ["string"]
         }
     ]
  }
```

## Profile

### Get Profile

- Method: GET
- Url: /api/v1/users/profile
- Header: Bearer token
- Response :
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
- Body: `multipart/form-data`

| KEY | VALUE | 
| --------------- | --------------- | 
| file | file image | 
| name | "string" | 
| description  | "string" | 
| prescription  | ["string"] | 
| prevention  | ["string"] | 
| category  | "string" | 

- Response :
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
- Response :

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
