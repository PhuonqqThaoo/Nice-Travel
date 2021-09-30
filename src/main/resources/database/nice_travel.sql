use master;

create database nice_travel;
go

use  nice_travel
go

create table roles(
	id int  primary key identity,
	role nvarchar(20) not null
)
go

create table account(
	id				int				primary key identity,
	username		varchar(20)		not null unique,
	password		varchar(255)	not null,
	email			varchar(100)	not null unique,
	roleId			int				foreign key references roles(id),
	isDeleted		bit				not null default 0
)
go

create table accountDetail(
	id				int				primary key identity,
	idAccount		int				foreign key references Account(id),
	fullname		nvarchar(225)	null,
	gender			bit				not null,
	[address]		nvarchar(225)	null,
	phone			varchar(20)		not null,
	img				varchar(225)	null,
	idCard			varchar(50)		not null,
	createdDate		datetime		not null default getdate(),
)
go

create table travelTypes(
	id				int				primary key identity,
	type			nvarchar(225)	not null,
	[description]	nvarchar(225)	null,
	slug			varchar(255)	not null,
	isDeleted		bit				not null default 0
)
go


create table travel(
	id				int				primary key identity,
	[name]			nvarchar(225)	not null,
	typeId			int				foreign key references travelTypes(id),
	departurePlace	nvarchar(225)	not null,
	place			nvarchar(225)	not null,
	price			decimal(12,3)	not null,
	img				varchar(225)	null,
	createdDate		datetime		not null default getdate(),
	startDate		datetime		not null,
	endDate			datetime		not null,
	quantity		int				not null,
	[hour]			int				not null,
	slug			varchar(255)	not null,
	isDeleted		bit				not null default 0
)
go

create table travelDetail(
	id				int				primary key identity,
	[time]			varchar(50)		not null, 
	[description]	nvarchar(225)	null,
	travelId		int				foreign key references travel(id),
	isDeleted		bit				not null default 0
)
go

create table ageType(
	id int primary key identity,
	[description]	nvarchar(225)	not null,
	isDeleted		bit				not null default 0
)
go

create table priceDetail(
	id 				int 			primary key identity,
	price 			decimal(12,3)	not null,
	travelId 		int 			foreign key references travel(id),
	ageId 			int  			foreign key references ageType(id),
	isDeleted		bit				not null default 0
)
go

create table booking(
	id				int				primary key identity,
	accountId		int 			foreign key references Account(id),
	createdDate		datetime		not null default getdate(),
	[address]		nvarchar(225)	null,
	phone			varchar(20)		not null,
	totalPrice		decimal(12,3)	not null,
	payBoolean		bit				not null default 0,
	isDeleted		bit				not null default 0
)
go

create table bookingDetail(
	id				int 			primary key identity,
	bookingId		int 			foreign key references booking(id),
	travelId		int 			foreign key references travel(id),
	price			decimal(12,3)	not null
)
go

create table payment(
	id				int				primary key identity,
	bookingId 		int 			foreign key references booking(id),
	payTime 		datetime		not null,
	totalPrice		decimal(12,3)	not null
)
