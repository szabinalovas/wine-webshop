
insert into customer (customer_name, country, postal_code, city, address, email, phone, credit_card_number, credit_card_expiry_date) values ('Dianna Brickner', 'Croatia', '32235', 'Bapska', '7595 Golf View Parkway', 'dbrickner0@vk.com', '(414) 3416616', '5002352672035948', '2023-04'),
                                                                                                                                            ('Briggs Hargreves', 'Russia', '356003', 'Novoaleksandrovsk', '00 Maple Circle', 'bhargreves1@webnode.com', '(794) 2592094', '5100131826418502', '2024-08'),
                                                                                                                                            ('Sallyann Yeomans', 'Indonesia', '67456', 'Pancalan', '93 Logan Crossing', 'syeomans2@webnode.com', '(117) 3068369', '5100142863635247', '2023-10'),
                                                                                                                                            ('Kristal Dallmann', 'United Kingdom', 'CH48', 'Wirral', '30733 Maple Wood Alley', 'kdallmann3@hostgator.com', '(500) 1293366', '5007660511636733', '2025-09'),
                                                                                                                                            ('Margalo Dunstan', 'China', '82334', 'Yanmen', '52 Fuller Point', 'mdunstan4@surveymonkey.com', '(850) 7128626', '5108755864001796', '2022-12'),
                                                                                                                                            ('Perceval Naul', 'Philippines', '7505', 'Bakung', '7 Summer Ridge Place', 'pnaul5@feedburner.com', '(553) 1289659', '5048376694069581', '2025-03'),
                                                                                                                                            ('Philomena Boyett', 'Brazil', '13990-000', 'Espírito Santo do Pinhal', '89442 Coolidge Street', 'pboyett6@alibaba.com', '(611) 8242224', '5100133187649923', '2025-04'),
                                                                                                                                            ('Quinta Pym', 'Panama', '345687', 'Bejuco', '74 Springs Court', 'qpym7@hostgator.com', '(659) 8400330', '5100148344592949', '2024-11'),
                                                                                                                                            ('Angelia Sheran', 'Brazil', '92500-000', 'Guaíba', '7 Forest Circle', 'asheran8@hubpages.com', '(378) 8075051', '5100172509915331', '2024-03'),
                                                                                                                                            ('Brion Hitschke', 'Colombia', '413067', 'Hobo', '16 Eagle Crest Plaza', 'bhitschke9@oakley.com', '(366) 1843437', '5048372734943968', '2024-06');

insert into category (category_type) values ('red'), ('white'), ('rosé');

insert into product (product_name, vintage, product_description, price, quantity_in_stock, category_id) values
                                                                                                            ('LAJVER Premium Sauvignon Blanc', 2021, 'It’s a modern Sauvignon, with a rich nose of tropical fruit and citrus fruit, cut grass, lively acidity and a subtly mineral finish on the complex and delicate palate',
                                                                                                             3400, 30, 2),
                                                                                                            ('RUPPERT Siller "A Beszélő Virágok Kertje"', 2021, 'It has an exciting colour that is reminiscent of deep raspberry, made in the spirit of freshness and fruitiness. Aromas of red fruits, strawberries, sour cherries and cherries, minimal tannins, a juicy palate and refreshing acidity.',
                                                                                                             2150, 12, 3),
                                                                                                            ('KREINBACHER Öreg Tőkék Bora', 2019, null,
                                                                                                             3900, 43, 2),
                                                                                                            ('SEBESTYÉN Mozaik Bikavér', 2019, 'It’s a medium-bodied wine that’s easy to drink and love, with a slightly higher price tag.',
                                                                                                             3850, 23, 1),
                                                                                                            ('RUPPERT A Nyúl', 2021, 'It’s tasty, rich and substantial, complemented with the lively acids that characterise the 2021 vintage, the purity of Olaszrizling, as well as the fruitiness and aromatic character of Királyleányka.',
                                                                                                             2150, 28, 2),
                                                                                                            ('RUPPERT Kandúr Cuvée', 2020, 'The wine has a ripe character, a soft palate that is dense and yet easy-to-drink, with aromas of blackberry and blackcurrant.',
                                                                                                             2200, 15, 1),
                                                                                                            ('HEIMANN Mammaróza Rozé Cuvée', 2021, 'It has a deep, intense rosé colour, lots of fresh fruit, fuller body than usual and more juice.',
                                                                                                             2250, 10, 3),
                                                                                                            ('FIGULA Zenit & More', 2021, 'Zenit and the others, in this case Pinot Gris (Szürkebarát in Hungarian) and a touch of Irsai Olivér, present themselves with vibrant acids, full aromas, a wide range of fruits and flowers.',
                                                                                                             2250, 34, 2),
                                                                                                            ('VÉCSI Furmint', 2019, 'It was fermented in tanks, and the larger part was aged in steel, complemented with a bit of used barrel. A gleaming, pale yellow colour, with fresh, floral notes on the nose with juicy green apple',
                                                                                                             3400, 45, 2),
                                                                                                            ('VÉCSI Furmint', 2017, 't’s a harmonious, light, fresh fruit bomb with melon and peach. The sweetness of the 39 grams of sugar, with a medium-length, rich palate and a refreshingly mineral finish.',
                                                                                                             3700, 35, 2),
                                                                                                            ('VÉCSI Szamorodni', 2012, 'This szamorodni speciality made from shrivelled bunches with some botrytis, grown on the rhyolite and loess soil of the Vécsi vineyard, is one of his first works.',
                                                                                                             6700, 10, 2),
                                                                                                            ('VÉCSI Hárslevelű', 2018, 'Owing to the ripe, thick and tasty raw material; the 14.5% alcohol content; and the 7 grams of natural residual sugar – the long palate is full with a creamy structure, which is also lively, keeping the taster constantly alert.',
                                                                                                             8900, 10, 2);

insert into payment (payment_date, payment_status, payment_type) values ('2022-04-28', 'COMPLETED', 'CARD_PAYMENT');
insert into payment (payment_status, payment_type) values ('PENDING', 'CASH_ON_DELIVERY');
insert into payment (payment_status, payment_type) values ('PENDING', 'APPLE_PAY');
insert into payment (payment_status, payment_type) values ('CANCELLED', 'CARD_PAYMENT');

insert into cart (total, customer_id, payment_id) values (5850, 4, 1);
insert into cart (total, customer_id, payment_id) values (7250, 6, 2);
insert into cart (total, customer_id, payment_id) values (3400, 7, 3);
insert into cart (total, customer_id, payment_id) values (10600, 1, 4);

insert into product_cart (product_id, cart_id) values (5, 1);
insert into product_cart (product_id, cart_id) values (10, 1);
insert into product_cart (product_id, cart_id) values (11, 4);
insert into product_cart (product_id, cart_id) values (3, 4);
insert into product_cart (product_id, cart_id) values (9, 3);
insert into product_cart (product_id, cart_id) values (1, 2);
insert into product_cart (product_id, cart_id) values (4, 2);

