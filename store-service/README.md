# Store Service
## Module notes

The vendor service encompasses 1 models: 
**Inventory**
**Shelf**
**ReturnedItems**

### Inventory Model
The inventory model houses all received inventory from suppliers.
It creates a clear picture of what has been received and when.

#### **Routes**
* GET /api/store/inventory/ (List all inventory items)
* POST /api/store/inventory/?filter=lastWeek (Items received in the last week)
* POST /api/store/inventory/create/ (Receive Items to store)
* POST http://localhost:8080/api/store/inventory/return-spoilt-item/{inventoryId} (Return item to vendor)

### Shelf Model
Contains History of all items on shelf (Store floor) /  Inventory items that can be sold
on store floor.
Once an item has been listed in inventory, it can then be dispatched
to the store floor.

You can view or interact with items on the shelf using the following 
routes.
#### **Routes**
* GET /api/store/shelves/ (History of all items on shelf)
* GET /api/store/shelves/?filter=today (Items Shelved today)
* POST /api/store/shelves/add-to-shelf?quantity=10&inventoryId=2 (Move inventory to shelf)
* POST /api/store/shelves/return-spoilt-item/{shelfId} (Return item from shelf to vendor)

### Returned Items
This are the defective products that have been returned to the vendor
Items can be returned either from:
* Supermarket floor (Shelf)
* Store (Inventory)


#### **Routes**
* GET /api/store/returned/ (List all vendors)
* GET /api/store/returned/?filter=month (Returned last month)