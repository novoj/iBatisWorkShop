INSERT INTO user values (1, 'novoj', 'Jan', 'Novotný', '1978-05-05', 1, 0, '00012', null, null);
INSERT INTO user values (2, 'humr', 'Petr', 'Humorný', '1980-11-08', 1, 0, '00016', null, null);
INSERT INTO user values (3, 'fik', 'Max', 'Fikejs', '1969-02-01', 1, 0, '00018', null, null);
INSERT INTO user values (4, 'rodrigez', 'Roman', 'Geňa', '1981-09-17', 0, 1, null, 'Fischer Scientific', '129837281');
INSERT INTO user values (5, 'igen', 'Ivan', 'Polák', '1977-05-05', 1, 1, null, 'Telefonica O2', '4563342132');
INSERT INTO user values (6, 'dolar', 'Dominik', 'Linhart', '1988-09-21', 1, 0, '00012', null, null);
INSERT INTO user values (7, 'janko', 'Jan', 'Lašák', '1982-07-02', 1, 1, null, 'Moeller', '2132443345');
INSERT INTO user values (8, 'dominator', 'Dominik', 'Hašek', '1973-04-01', 0, 1, null, 'Moeller', '2132443345');
INSERT INTO user values (9, 'veskic', 'Martin', 'Veska', '1979-07-01', 1, 0, '00006', null, null);
INSERT INTO user values (10, 'jety', 'Pavel', 'Jetenský', '1978-08-20', 1, 0, null, 'Telefonica O2', '4563342132');

INSERT INTO role values (1, 'standardUser', 'Standardní uživatel');
INSERT INTO role values (2, 'administrator', 'Administrátor');
INSERT INTO role values (3, 'powerUser', 'Technická správa');

INSERT INTO userRole values (1, 1, 3);
INSERT INTO userRole values (2, 2, 2);
INSERT INTO userRole values (3, 3, 2);
INSERT INTO userRole values (4, 4, 1);
INSERT INTO userRole values (5, 5, 1);
INSERT INTO userRole values (6, 6, 2);
INSERT INTO userRole values (7, 7, 1);
INSERT INTO userRole values (8, 8, 1);
INSERT INTO userRole values (9, 9, 2);
INSERT INTO userRole values (10, 10, 3);

INSERT INTO productGroup values (1, 'HDD', 'HARDWARE');
INSERT INTO productGroup values (2, 'Operační systémy', 'SOFTWARE');
INSERT INTO productGroup values (3, 'Monitory', 'HARDWARE');
INSERT INTO productGroup values (4, 'Vývojová prostředí', 'SOFTWARE');

INSERT INTO tag values (1, 'Samsung');
INSERT INTO tag values (2, 'Lenovo');
INSERT INTO tag values (3, 'HP');
INSERT INTO tag values (4, 'SSD');
INSERT INTO tag values (5, 'SATA');
INSERT INTO tag values (6, 'Microsoft');
INSERT INTO tag values (7, 'Apple');
INSERT INTO tag values (8, 'JetBrains');
INSERT INTO tag values (9, 'Oracle Sun');
INSERT INTO tag values (10, 'Java');
INSERT INTO tag values (11, '.NET');

INSERT INTO product values (1, 'Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive', 1);
INSERT INTO product values (2, 'Samsung HDD 750GB Samsung SpinPointF1 32MB SATAII RAID 3RZ', 1);
INSERT INTO product values (3, 'Samsung HDD 160GB Samsung SpinPoint F1 SATAII/300 3RZ', 1);
INSERT INTO product values (4, 'Lenovo ThinkPad 64GB Solid State Disk', 1);
INSERT INTO product values (5, 'HP 120GB 3G SATA 2.5in QR MDL SSD', 1);
INSERT INTO product values (6, 'Apple Mac OS X Snow Leopard 10.6 Family Pack', 2);
INSERT INTO product values (7, 'Microsoft OEM Windows 7 Home Premium 32-bit Czech', 2);
INSERT INTO product values (8, 'Microsoft OEM Windows 7 Professional 64-bit Czech', 2);
INSERT INTO product values (9, 'Microsoft OEM Windows 7 Ultimate 32-bit Czech', 2);
INSERT INTO product values (10, 'Samsung SyncMaster XL30', 3);
INSERT INTO product values (11, 'Samsung SyncMaster 305T PLUS', 3);
INSERT INTO product values (12, 'Apple Cinema Display 30" HD', 3);
INSERT INTO product values (13, 'Lenovo L2251x', 3);
INSERT INTO product values (14, 'Lenovo L2250p', 3);
INSERT INTO product values (15, 'Eclipse', 4);
INSERT INTO product values (16, 'Netbeans', 4);
INSERT INTO product values (17, 'IntelliJ Idea', 4);
INSERT INTO product values (18, 'Visual Studio', 4);

INSERT INTO productTag values  (1, 1, 2);
INSERT INTO productTag values  (2, 1, 5);
INSERT INTO productTag values  (3, 2, 1);
INSERT INTO productTag values  (4, 2, 5);
INSERT INTO productTag values  (5, 3, 1);
INSERT INTO productTag values  (6, 3, 5);
INSERT INTO productTag values  (7, 4, 2);
INSERT INTO productTag values  (8, 4, 4);
INSERT INTO productTag values  (9, 5, 3);
INSERT INTO productTag values  (10, 5, 4);
INSERT INTO productTag values  (11, 6, 7);
INSERT INTO productTag values  (12, 7, 6);
INSERT INTO productTag values  (13, 8, 6);
INSERT INTO productTag values  (14, 9, 6);
INSERT INTO productTag values  (15, 10, 1);
INSERT INTO productTag values  (16, 11, 1);
INSERT INTO productTag values  (17, 12, 7);
INSERT INTO productTag values  (18, 13, 2);
INSERT INTO productTag values  (19, 14, 2);
INSERT INTO productTag values  (20, 15, 10);
INSERT INTO productTag values  (21, 16, 10);
INSERT INTO productTag values  (22, 16, 9);
INSERT INTO productTag values  (23, 17, 10);
INSERT INTO productTag values  (24, 17, 8);
INSERT INTO productTag values  (25, 18, 6);
INSERT INTO productTag values  (26, 18, 11);