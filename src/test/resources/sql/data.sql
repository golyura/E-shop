INSERT INTO product (id, name, price, quantity)
VALUES (1, 'milk', 5, 100),
       (2, 'bread', 7, 150),
       (3, 'meat', 20, 55);
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));

INSERT INTO product_locales (product_id, lang, description)
VALUES ((SELECT id FROM product WHERE name = 'milk'), 'en', 'Milk'),
       ((SELECT id FROM product WHERE name = 'milk'), 'ru', 'Молоко'),
       ((SELECT id FROM product WHERE name = 'bread'), 'en', 'Bread'),
       ((SELECT id FROM product WHERE name = 'bread'), 'ru', 'Хлеб'),
       ((SELECT id FROM product WHERE name = 'meat'), 'en', 'Meat'),
       ((SELECT id FROM product WHERE name = 'meat'), 'ru', 'Мясо');

INSERT INTO customer (id, birth_date, firstname, role, email)
VALUES (1, '1990-01-10', 'Ivan', 'ADMIN', 'ivan@gmail.com'),
       (2, '1995-10-19', 'Petr', 'CUSTOMER', 'petr@gmail.com'),
       (3, '2001-12-23', 'Sveta', 'CUSTOMER', 'sveta@gmail.com'),
       (4, '1984-03-14', 'Vlad', 'CUSTOMER', 'vlad@gmail.com'),
       (5, '1984-03-14', 'Kate', 'ADMIN', 'kate@gmail.com');
SELECT SETVAL('customer_id_seq', (SELECT MAX(id) FROM customer));


INSERT INTO purchase (id, purchase_date, purchase_closing_date, customer_id, status)
VALUES (1, '1990-01-10', '1990-02-10', (SELECT id FROM customer WHERE email = 'ivan@gmail.com'), 'DONE'),
       (2, '1991-02-20', '1992-02-10', (SELECT id FROM customer WHERE email = 'ivan@gmail.com'), 'DONE'),
       (3, '2000-02-10', '2025-02-10', (SELECT id FROM customer WHERE email = 'ivan@gmail.com'), 'IN_PROCESS'),
       (4, '2020-01-10', '2021-02-10', (SELECT id FROM customer WHERE email = 'petr@gmail.com'), 'DONE'),
       (5, '2021-02-20', '2022-02-10', (SELECT id FROM customer WHERE email = 'petr@gmail.com'), 'DONE'),
       (6, '2022-01-10', '2023-02-10', (SELECT id FROM customer WHERE email = 'sveta@gmail.com'), 'IN_PROCESS'),
       (7, '1991-01-10', '1992-02-10', (SELECT id FROM customer WHERE email = 'sveta@gmail.com'), 'DONE'),
       (8, '1995-02-20', '1996-02-10', (SELECT id FROM customer WHERE email = 'vlad@gmail.com'), 'DONE'),
       (9, '2000-01-10', '2025-02-10', (SELECT id FROM customer WHERE email = 'kate@gmail.com'), 'IN_PROCESS');
SELECT SETVAL('purchase_id_seq', (SELECT MAX(id) FROM purchase));

INSERT INTO purchase_item (purchase_id, product_id, product_count)
VALUES ((SELECT id FROM purchase WHERE purchase_date = '1990-01-10'),
        (SELECT id FROM product WHERE name = 'milk'), 2),
       ((SELECT id FROM purchase WHERE purchase_date = '1991-02-20'),
        (SELECT id FROM product WHERE name = 'bread'), 3),
       ((SELECT id FROM purchase WHERE purchase_date = '2000-02-10'),
        (SELECT id FROM product WHERE name = 'meat'), 1),
       ((SELECT id FROM purchase WHERE purchase_date = '2020-01-10'),
        (SELECT id FROM product WHERE name = 'bread'), 5),
       ((SELECT id FROM purchase WHERE purchase_date = '2021-02-20'),
        (SELECT id FROM product WHERE name = 'meat'), 6),
       ((SELECT id FROM purchase WHERE purchase_date = '2022-01-10'),
        (SELECT id FROM product WHERE name = 'meat'), 8),
       ((SELECT id FROM purchase WHERE purchase_date = '1991-01-10'),
        (SELECT id FROM product WHERE name = 'bread'), 9);


INSERT INTO chat (name)
VALUES ('dmdev'),
       ('java'),
       ('database');

INSERT INTO customer_chat(customer_id, chat_id)
VALUES ((SELECT id FROM customer WHERE email = 'ivan@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM customer WHERE email = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM customer WHERE email = 'sveta@gmail.com'), (SELECT id FROM chat WHERE name = 'dmdev')),
       ((SELECT id FROM customer WHERE email = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM customer WHERE email = 'sveta@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM customer WHERE email = 'vlad@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM customer WHERE email = 'kate@gmail.com'), (SELECT id FROM chat WHERE name = 'java')),
       ((SELECT id FROM customer WHERE email = 'petr@gmail.com'), (SELECT id FROM chat WHERE name = 'database')),
       ((SELECT id FROM customer WHERE email = 'kate@gmail.com'), (SELECT id FROM chat WHERE name = 'database'));
