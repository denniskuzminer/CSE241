create table branch (
	address varchar(20),
	teller-type varchar(20),
	primary key (address) );

create table account (
	acc_id char(5),
	min_balance numeric(8,2),
	type varchar(8),
	rate numeric(3, 5),
	balance numeric(8,2),
	penalty_fee numeric(8, 2), 
	primary key (acc_id),
	check (type in ('Checking', 'Savings') ) 
	check ( ( penalty_fee > 0 and type = 'Savings' ) or ( penalty_fee = 0 and type = 'Checking' ) ), 
	check ( type = 'Checking' and min_balance = 0 ) );

create table loan (
	loan_id char(5),
	amount numeric(8, 2),
	type varchar(9),
	monthly_payment numeric(8, 2),
	rate numeric(3, 5),
	primary key (loan_id),
	check (type in ('Mortgage', 'Unsecured') ) );

create table card ( 
	card_number char(16),
	type varchar(6),
	expiration_date char(5) not null,
	rate numeric(3, 5),
	limit numeric(8, 2),
	running_balance numeric(8, 2),
	balance_due numeric(8, 2),
	primary key (card_number), 
	check (type in ( 'Credit', 'Debit') ) );

create table transaction ( 
	trans_id char(5),
	acc_id,
	amount numeric(8, 2),
	time timestamp,
	--SET time_zone='+00:00';
	type varchar(20),
	primary key (trans_id),
	foreign key (acc_id) references account(acc_id),
	check (type in (
	'Withdrawls', 'Deposits', 'Fund Transfers', 'Purchases', 'Loan Payments', 'Card Payments' 
	) ) );

create table customer (
	cust_id char(5),
	name varchar(20) not null,
	address varchar(20),
	phone varchar(12),
	primary key (cust_id) );

--Connector tables

--Loan/Customer table
create table borrow (
	cust_id char(5),
	loan_id char(5),
	primary key (loan_id),
	foreign key (cust_id) references customer(cust_id),
	foreign key (loan_id) references loan(loan_id) );

--Customer/Account/Card table
create table customer_card (
	cust_id char(5),
	card_number char(16),
	acc_id char(5),
	primary key (cust_id, card_number, acc_id),
	foreign key (cust_id) references customer(cust_id),
	foreign key (card_number) references card(card_number),
	foreign key (acc_id) references account(acc_id) );

create table account_loan (
	acc_id char(5),
	loan_id char(5),
	primary key (loan_id),
	foreign key (acc_id) references account(acc_id),
	foreign key (loan_id) references loan(loan_id) );

create table account_branch (
	acc_id char(5),
	address varchar(20),
	primary key (acc_id),
	foreign key (acc_id) references account(acc_id),
	foreign key (address) references branch(address) );

create table loan_branch (
	loan_id char(5),
	address varchar(20),
	primary key (loan_id),
	foreign key (loan_id) references loan(loan_id),
	foreign key (address) references branch(address) );





