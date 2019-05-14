# Java User Group Script Api

To run this application create a Token in Github and create an application.json file.

## Github Token

Go to Github > User Settings > Developer settings > Personal access tokens >

Create a new token.


## application.json File

Create a file conf/application.json

```json
{
  "config": {
    "github": {
      "token": "YOUR TOKEN HERE",
    }
  }
}
```


And you can go with the application.


## Developping mode

When you are in dev mode, you can add these options to VM to disable the caching system:

* -Dvertxweb.environment=dev -Dvertx.disableFileCaching=true

