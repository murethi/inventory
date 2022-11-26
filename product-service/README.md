# PRODUCTS SERVICE
## Module notes
I created the product service to house all products that are available 
in the supermarket.
The product service encompasses 2 models: **Variants** and **Products**

Products allow us to group different items into an easy to manage object
In an ideal scenario this would be category>product>variants 
``` Potato crisps > Tropical heat Crisps > 50GMS ```
I will then use the variants model to connect to the store


Routes
* GET /api/products/ (List all products)
* POST /api/products/create (Create product)
* GET /api/products/view/{id} (View product)
* PUT /api/products/update/{id} (Update product)
* DELETE /products/delete/{id} (Delete product)
#### Variants

* GET /api/products/variants/create/{productId}
* PUT /api/products/variants/update/{id}
* DELETE /api/products/variants/delete/{id}
