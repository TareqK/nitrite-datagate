# Nitrite-Datagate-API

An API to synchronize datagate clients with the datagate server  
This api follows the json-rpc 2.0 specification. More information available at http://www.jsonrpc.org/specification.

<strong>Version 1.0</strong>

---

- [authenticate](#authenticate)
- [subscribe](#subscribe)
- [unsubscribe](#unsubscribe)
- [change](#change)
- [getCollectionData](#getCollectionData)
- [getChangesSince](#getChangesSince)

---

<a name="authenticate"></a>

## authenticate

Authenticates the current session

### Description

Authenticates the user using the provided credentials and creates a new session.

### Parameters

| Name            | Type   | Description                           |
| --------------- | ------ | ------------------------------------- |
| params          | object |                                       |
| params.username |        | Username of the user to authenticate. |
| params.password |        | Password of the user to authenticate. |

### Result

| Name                  | Type    | Description                                |
| --------------------- | ------- | ------------------------------------------ |
| result                | object  |                                            |
| result?.authenticated | boolean | Whether the credentials are correct or not |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "authenticate",
  "params": {
    "username": "test",
    "password": "test"
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "result": {
    "authenticated": true
  }
}
```

<a name="subscribe"></a>

## subscribe

Subscribes to changes on a collection. Can return multiple responses, depending on if there are multiple updates

### Parameters

| Name              | Type   | Description                                |
| ----------------- | ------ | ------------------------------------------ |
| params            | object |                                            |
| params.collection |        | The name of the collection to subscribe to |

### Result

| Name               | Type   | Description                                              |
| ------------------ | ------ | -------------------------------------------------------- |
| result             | object |                                                          |
| result.changeItems | array  | An array of change items for this change list            |
| result.collection  | string | The name of the collectiont that this change list is for |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "subscribe",
  "params": {
    "collection": "test"
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "result": {
    "changeItems": [null]
  }
}
```

<a name="unsubscribe"></a>

## unsubscribe

Unsubscribes to changes on a collection

### Parameters

| Name              | Type   | Description                                    |
| ----------------- | ------ | ---------------------------------------------- |
| params            | object |                                                |
| params.collection |        | The name of the collection to unsubscribe from |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "unsubscribe",
  "params": {
    "collection": "test"
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890"
}
```

<a name="change"></a>

## change

Applies Changes to a collection

### Parameters

| Name                          | Type   | Description                                              |
| ----------------------------- | ------ | -------------------------------------------------------- |
| params                        | object |                                                          |
| params.changeList             | object |                                                          |
| params.changeList.changeItems | array  | An array of change items for this change list            |
| params.changeList.collection  | string | The name of the collectiont that this change list is for |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "change",
  "params": {
    "changeList": {
      "changeItems": [null]
    }
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890"
}
```

<a name="getCollectionData"></a>

## getCollectionData

Gets all the data of a collection

### Parameters

| Name              | Type   | Description                       |
| ----------------- | ------ | --------------------------------- |
| params            | object |                                   |
| params.collection | string | The name of the collection to get |

### Result

| Name               | Type   | Description                                              |
| ------------------ | ------ | -------------------------------------------------------- |
| result             | object |                                                          |
| result.changeItems | array  | An array of change items for this change list            |
| result.collection  | string | The name of the collectiont that this change list is for |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "getCollectionData",
  "params": {
    "collection": "test"
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "result": {
    "changeItems": [null]
  }
}
```

<a name="getChangesSince"></a>

## getChangesSince

Gets all changes on a collection since a certain timestamp

### Parameters

| Name              | Type    | Description                                           |
| ----------------- | ------- | ----------------------------------------------------- |
| params            | object  |                                                       |
| params.collection | string  | The name of the collection to get                     |
| params?.timestamp | integer | Time in the nearest millisecond since the last update |

### Result

| Name               | Type   | Description                                              |
| ------------------ | ------ | -------------------------------------------------------- |
| result             | object |                                                          |
| result.changeItems | array  | An array of change items for this change list            |
| result.collection  | string | The name of the collectiont that this change list is for |

### Examples

#### Request

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "method": "getChangesSince",
  "params": {
    "collection": "test",
    "timestamp": 19021723721
  }
}
```

#### Response

```json
{
  "jsonrpc": "2.0",
  "id": "1234567890",
  "result": {
    "changeItems": [null]
  }
}
```
