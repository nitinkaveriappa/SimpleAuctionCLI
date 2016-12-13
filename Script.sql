drop trigger checkBidValidity;
drop trigger updateAvgRating;
drop table bid cascade constraints;
drop table completed_transaction cascade constraints;
drop table listing cascade constraints;
drop table belongs_to cascade constraints;
drop table category cascade constraints;
drop table product cascade constraints;
drop table buyer cascade constraints;
drop table seller cascade constraints;
drop table customer cascade constraints;



create table customer
(
  id integer not null,
  name varchar2(50) not null,
  age integer not null,
  gender char(10) not null,
  join_date date not null,
  avg_rating integer,
  primary key (id)
);


create table seller
(
  seller_id integer not null,
  primary key (seller_id),
  foreign key (seller_id) references customer(id) ON DELETE CASCADE
);


create table buyer
(
  buyer_id integer not null,
  primary key (buyer_id),
  foreign key (buyer_id) references customer(id) ON DELETE CASCADE
);


create table product
(
  pid integer not null,
  pname varchar2(100) not null,
  primary key (pid)
);


create table category
(
  label varchar2(50) not null,
  primary key (label)
);


create table belongs_to
(
  pid integer not null,
  label varchar2(50) not null,
  primary key (pid, label),
  foreign key (pid) references product(pid) ON DELETE CASCADE,
  foreign key (label) references category(label) ON DELETE CASCADE
);


create table listing
(
  listing_id integer not null,
  condition char(10) check(condition in ('new','used')),
  status char(10) check(status in ('active','completed')),
  start_bid integer not null,
  start_time date not null,
  end_time date not null,
  pid integer not null,
  seller_id integer not null,
  primary key (listing_id),
  foreign key (pid) references product(pid) ON DELETE CASCADE,
  foreign key (seller_id) references seller(seller_id) ON DELETE CASCADE
);


create table completed_transaction
(
  price integer not null,
  rating_buyer_to_seller integer,
  rating_seller_to_buyer integer,
  seller_id integer not null,
  buyer_id integer,
  listing_id integer not null,
  PRIMARY KEY (listing_id),
  FOREIGN KEY (seller_id) REFERENCES seller(seller_id) ON DELETE CASCADE,
  FOREIGN KEY (buyer_id) REFERENCES buyer(buyer_id) ON DELETE CASCADE,
  FOREIGN KEY (listing_id) REFERENCES listing(listing_id) ON DELETE CASCADE
);


create table bid
(
  bid_no integer not null,
  amount integer not null,
  listing_id integer not null,
  buyer_id integer not null,
  bid_time date not null,
  primary key (bid_no, amount, listing_id),
  foreign key (listing_id) references listing(listing_id) ON DELETE CASCADE,
  foreign key (buyer_id) references buyer(buyer_id) ON DELETE CASCADE
);


CREATE TRIGGER checkBidValidity
BEFORE INSERT ON bid
FOR EACH ROW
DECLARE
bid_amt INTEGER;
st_bid INTEGER;
seller INTEGER;
deadline date;
invalid_bid Exception;
invalid_seller Exception;
invalid_time Exception;
BEGIN
SELECT MAX(amount) INTO bid_amt 
FROM bid WHERE listing_id=:NEW.listing_id;
SELECT end_time into deadline
FROM listing WHERE listing_id=:NEW.listing_id;
SELECT start_bid INTO st_bid
FROM listing WHERE listing_id=:NEW.listing_id;
SELECT seller_id INTO seller
FROM listing WHERE listing_id = :NEW.listing_id;

IF :NEW.bid_time > deadline THEN
RAISE invalid_time;
END IF;

IF :NEW.buyer_id = seller THEN
RAISE invalid_Seller;
END IF;

IF :NEW.amount <= bid_amt AND :NEW.amount<st_bid THEN
RAISE invalid_bid;
END IF;
EXCEPTION
WHEN invalid_time THEN
RAISE_APPLICATION_ERROR(-20001, 'Invalid bid time');
WHEN invalid_bid THEN
RAISE_APPLICATION_ERROR(-20001, 'Invalid bid amount!');
WHEN invalid_seller THEN
RAISE_APPLICATION_ERROR(-20001, 'Invalid bidder!');
END;
/


CREATE TRIGGER updateAvgRating
BEFORE INSERT ON completed_transaction
FOR EACH ROW 

DECLARE
seller_sum INTEGER := 0;
buyer_sum INTEGER := 0;
num INTEGER := 0;

BEGIN

SELECT count(*) INTO num FROM completed_transaction WHERE seller_id=:NEW.seller_id or buyer_id=:NEW.seller_id;
SELECT SUM(rating_buyer_to_seller) INTO seller_sum FROM completed_transaction WHERE seller_id=:NEW.seller_id;
SELECT SUM(rating_seller_to_buyer) INTO buyer_sum FROM completed_transaction WHERE buyer_id=:NEW.seller_id;

IF seller_sum IS NOT NULL OR buyer_sum IS NOT NULL THEN
UPDATE customer
SET avg_rating = ((seller_sum + buyer_sum + :NEW.rating_buyer_to_seller)/(num+1))
WHERE id=:NEW.seller_id;
END IF;

IF seller_sum IS NULL OR buyer_sum IS NULL THEN
UPDATE customer
SET avg_rating = ((:NEW.rating_seller_to_buyer))
WHERE id=:NEW.seller_id;
END IF;

SELECT count(*) INTO num FROM completed_transaction WHERE seller_id=:NEW.buyer_id or buyer_id=:NEW.buyer_id;
SELECT SUM(rating_buyer_to_seller) INTO seller_sum FROM completed_transaction WHERE seller_id=:NEW.buyer_id;
SELECT SUM(rating_seller_to_buyer) INTO buyer_sum FROM completed_transaction WHERE buyer_id=:NEW.buyer_id;

IF seller_sum IS NOT NULL OR buyer_sum IS NOT NULL THEN
UPDATE customer
SET avg_rating = ((seller_sum + buyer_sum + :NEW.rating_seller_to_buyer)/(num+1))
WHERE id=:NEW.buyer_id;
END IF;

IF seller_sum IS NULL OR buyer_sum IS NULL THEN
UPDATE customer
SET avg_rating = ((:NEW.rating_seller_to_buyer))
WHERE id=:NEW.buyer_id;
END IF;

END;
/


insert into customer values (1,'tara', 25, 'F', TO_DATE( '02-SEP-2016', 'DD-MON-YYYY' ), NULL);
insert into customer values (2,'james', 40, 'M', TO_DATE( '11-NOV-2016', 'DD-MON-YYYY' ), NULL);
insert into customer values (3,'debra', 30, 'F', TO_DATE( '10-OCT-2016', 'DD-MON-YYYY' ), 2);
insert into customer values (4,'ally', 35, 'F', TO_DATE( '01-NOV-2016', 'DD-MON-YYYY' ), 5);
insert into customer values (5,'ron', 50, 'M', TO_DATE( '31-AUG-2016', 'DD-MON-YYYY' ), 3);
insert into customer values (6,'lauren', 28, 'F', TO_DATE( '23-OCT-2016', 'DD-MON-YYYY' ), NULL);
insert into customer values (7,'ravi', 32, 'M', TO_DATE( '12-NOV-2016', 'DD-MON-YYYY' ), 1);
insert into customer values (8,'ricky', 27, 'M', TO_DATE( '17-JUN-2016', 'DD-MON-YYYY' ), NULL);
insert into customer values (9,'karthik', 60, 'M', TO_DATE( '29-JUL-2016', 'DD-MON-YYYY' ), 1);
insert into customer values (10,'amulya', 50, 'F', TO_DATE( '01-SEP-2016', 'DD-MON-YYYY' ), NULL);


insert into product values(111, 'macbook air');
insert into product values(222, 'surface pro');
insert into product values(333, 'iphone 6s');
insert into product values(444, 'wireless mouse');
insert into product values(555, 'television');
insert into product values(666, 'sweater');
insert into product values(777, 'dress');
insert into product values(888, 'necklace');
insert into product values(999, 'earrings');
insert into product values(112, 'suitcase');


insert into category values ('electronics');
insert into category values ('clothing');
insert into category values ('luggage');
insert into category values ('accessories');


insert into belongs_to values(111,'electronics');
insert into belongs_to values(222,'electronics');
insert into belongs_to values(333,'electronics');
insert into belongs_to values(444,'electronics');
insert into belongs_to values(555,'electronics');
insert into belongs_to values(666,'clothing');
insert into belongs_to values(777,'clothing');
insert into belongs_to values(888,'accessories');
insert into belongs_to values(999,'accessories');
insert into belongs_to values(112,'luggage');


insert into seller values(10);
insert into seller values(7);
insert into seller values(5);
insert into seller values(1);


insert into listing values(1,'used', 'active', 700, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('20-NOV-2016','DD-MON-YYYY'), 222, 10);
insert into listing values(2,'used', 'active', 500, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('20-NOV-2016','DD-MON-YYYY'), 333, 7);
insert into listing values(3,'used', 'active', 600, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('20-NOV-2016','DD-MON-YYYY'), 555, 5);
insert into listing values(4,'new', 'completed', 50, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('20-NOV-2016','DD-MON-YYYY'), 444, 5);
insert into listing values(5,'used', 'active', 80, TO_DATE('16-NOV-2016','DD-MON-YYYY'), TO_DATE('24-NOV-2016','DD-MON-YYYY'), 666, 7);
insert into listing values(6,'used', 'active', 150, TO_DATE('15-NOV-2016','DD-MON-YYYY'), TO_DATE('29-NOV-2016','DD-MON-YYYY'), 777, 10);
insert into listing values(7,'used', 'active', 1500, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('20-NOV-2016','DD-MON-YYYY'), 888, 1);
insert into listing values(8,'used', 'completed', 70, TO_DATE('01-SEP-2016','DD-MON-YYYY'), TO_DATE('20-SEP-2016','DD-MON-YYYY'), 999, 10);
insert into listing values(9,'used', 'active', 200, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('30-NOV-2016','DD-MON-YYYY'), 112, 7);
insert into listing values(10,'used', 'active', 1000, TO_DATE('12-NOV-2016','DD-MON-YYYY'), TO_DATE('01-DEC-2016','DD-MON-YYYY'), 111, 5);


insert into buyer values(2);
insert into buyer values(3);
insert into buyer values(4);
insert into buyer values(6);
insert into buyer values(8);
insert into buyer values(9);


insert into bid values(1, 701, 1, 2,to_date('12-NOV-2016 09:50','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(2, 705, 1, 8,to_date('18-NOV-2016 19:50','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(3, 710, 1, 4,to_date('15-NOV-2016 20:50','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(4, 601, 3, 2,to_date('13-NOV-2016 05:30','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(5, 610, 3, 6,to_date('16-NOV-2016 15:00','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(6, 620, 3, 3,to_date('19-NOV-2016 21:45','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(7, 700, 3, 8,to_date('17-NOV-2016 22:33','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(8, 80, 8, 6,to_date('03-SEP-2016 12:33','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(9, 85, 8, 4,to_date('13-SEP-2016 22:33','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));
insert into bid values(10, 90, 8, 9,to_date('09-SEP-2016 20:00','DD-MON-YYYY HH24:MI','NLS_DATE_LANGUAGE=AMERICAN'));


insert into completed_transaction values(0, NULL, NULL, 5, NULL, 4);
insert into completed_transaction values(90, 5, 5, 10, 9, 8);