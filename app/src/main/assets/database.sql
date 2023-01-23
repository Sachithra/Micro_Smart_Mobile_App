CREATE TABLE PRODUCTS_CATEGORIES(
product_id INT,
product TEXT
);

CREATE TABLE PRODUCTS(
product_id INT,
product_description TEXT,
category_id INT
);

CREATE TABLE PRODUCT_PRICES(
product_id int,
price_id int,
product_price DOUBLE,
stocks TEXT
);

CREATE TABLE CUSTOMERS(
customer_id INTEGER PRIMARY KEY NOT NULL,
username TEXT,
phone TEXT,
age INTEGER,
gender TEXT
)