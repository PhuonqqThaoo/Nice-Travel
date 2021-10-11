use master
go
if db_id('nice_travel') is not null
drop database nice_travel
go

create database nice_travel
go

use  nice_travel
go

create table roles(
	id int  primary key identity,
	role nvarchar(20) not null
)
go 

create table Account(
	id				int				primary key identity,
	username		varchar(20)		not null unique,
	[password]		varchar(255)	not null,
	email			varchar(100)	not null unique,
	role_Id			int				foreign key references roles(id),
	is_deleted		bit				not null default 0
)
go

create table AccountDetail(
	id_account		int				primary key identity,
	fullname		nvarchar(225)	null,
	gender			bit				not null,
	[address]			nvarchar(225)	null,
	phone			varchar(20)		not null,
	img				varchar(225)	null,
	id_card			varchar(50)		not null,
	created_date		datetime		not null default getdate(),
	constraint FK_Account  foreign key(id_account) references Account(id)
)
go

create table travelTypes(
	id				int				primary key identity,
	type			nvarchar(225)	not null,
	[description]	nvarchar(225)	null,
	slug			varchar(255)	not null,
	is_deleted		bit				not null default 0
)
go

create table travel(
	id				int				primary key identity,
	[name]			nvarchar(225)	not null,
	typeId			int				foreign key references travelTypes(id),
	departure_place	nvarchar(225)	not null,
	place			nvarchar(225)	not null,
	price			decimal(12,3)	not null,
	img				varchar(225)	null,
	created_date		datetime		not null default getdate(),
	startDate		datetime		not null,
	endDate			datetime		not null,
	quantity		int				not null,
	[hour]			int				not null,
	slug			varchar(255)	not null,
	is_deleted		bit				not null default 0
)
go

create table travelDetail(
	id				int				primary key identity,
	[time]			varchar(50)		not null, 
	[description]	nvarchar(max)	null,
	travel_Id		int				foreign key references travel(id),
	is_deleted		bit				not null default 0
)
go

create table ageType(
	id 				int 			primary key identity,
	[description]	nvarchar(225)	not null,
	is_deleted		bit				not null default 0
)
go

create table priceDetail(
	id 				int 			primary key identity,
	price 			decimal(12,3)	not null,
	travel_Id 		int 			foreign key references travel(id),
	ageId 			int  			foreign key references ageType(id),
	is_deleted		bit				not null default 0
)
go 

create table booking(
	id				int 			primary key identity,
	account_Id		int 			foreign key references Account(id),
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
	booking_Id		int 			foreign key references booking(id),
	travel_Id		int 			foreign key references travel(id),
	price			decimal(12,3)	not null,
)
go

create table payment(
	id				int 			primary key identity,
	booking_Id 		int 			foreign key references booking(id),
	payTime 		datetime		not null,
	totalPrice		decimal(12,3)	not null,
)
GO

-- Insert Roles
SET IDENTITY_INSERT [dbo].[roles] ON
INSERT INTO roles(id, role) VALUES(1, 'ADMIN')
INSERT INTO roles (id, role) VALUES(2, 'STAFF')
INSERT INTO roles (id, role) VALUES(3, 'USER')
SET IDENTITY_INSERT [dbo].[roles] OFF

GO

-- Insert Account
SET IDENTITY_INSERT [dbo].[Account] ON
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (1, 'admin', '123', 'nice_travel@gmail.com', 1, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (2, 'staff', '123', 'staff@gmail.com', 2, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (3, 'user', '123', 'user@gmail.com', 3, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (4, 'tuanpc', '123', 'tuanpc@gmail.com', 1, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (5, 'thaonpt', '123', 'thaontp@gmail.com', 1, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (6, 'nhatta', '123', 'nhatta@gmail.com', 1, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (7, 'danhp', '123', 'danhp@gmail.com', 1, 0)
INSERT INTO Account (id, username, password, email, role_Id, is_deleted) VALUES (8, 'thangvv', '123', 'thangvv@gmail.com', 1, 0)
SET IDENTITY_INSERT [dbo].[Account] OFF
GO

-- Insert AccountDetail
SET IDENTITY_INSERT [dbo].[AccountDetail] ON
INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (1, N'admin', 0, N'Tây Ninh', '0394008704', 'tuanpc.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (2, N'staff', 0, N'TP.HCM', '0123456789', 'staff.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (3, N'user', 1, N'TP.HCM', '0987682198', 'user.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (4, N'Phạm Công Tuấn', 0, N'Tây Ninh', '0394008704', 'tuanpc.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (5, N'Nguyễn Thị Phương Thảo', 0, N'Bình Định', '0928871872', 'htaontp.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (6, N'Trần Anh Nhật', 0, N'Quãng Ngãi', '0928871872', 'nhatta.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (7, N'Huỳnh Phước Dân', 0, N'Tây Ninh', '0928871872', 'danhp.png', '0987654321', '2021-10-09')

INSERT INTO AccountDetail (id_account, fullname, gender, address, phone, img, id_card, created_date)
	VALUES (8, N'Võ Văn Thắng', 0, N'TP.HCM', '0928871872', 'htaontp.png', '0987654321', '2021-10-09')
SET IDENTITY_INSERT [dbo].[AccountDetail] OFF
GO

-- Insert travelTypes
SET IDENTITY_INSERT [dbo].[travelTypes] ON
INSERT INTO travelTypes (id, type, description, slug, is_deleted) VALUES (1, N'Tour gia đình', N'Tour du lịch giành cho gia đình', 'tour-gia-dinh', 0)
INSERT INTO travelTypes (id, type, description, slug, is_deleted) VALUES (2, N'Tour tron goi', N'Tour du lịch trọn gói!', 'tour-tron-goi', 0)
SET IDENTITY_INSERT [dbo].[travelTypes] OFF
GO

-- Insert travel
SET IDENTITY_INSERT [dbo].[travel] ON
INSERT INTO travel (id, [name], typeId,departure_place, place, price, img, created_date, startDate, endDate, quantity, [hour], slug, is_deleted)
	VALUES (1, N'Phan Thiết - Lâu Đài Rượu Vang - Bàu Trắng - Bảo Tàng Làng Chài Xưa', 1, N'Sân Bay Tân Sơn Nhất', N'Tân Bình', 13960000, 'phan-thiet-ld-bt.png', '2021-10-09', '2021-10-10', '2021-10-15', 4, 10, 'phan-thiet-10-10', 0)
SET IDENTITY_INSERT [dbo].[travel] OFF

--INSERT travelDetail
SET IDENTITY_INSERT [dbo].[travelDetail] ON
INSERT INTO travelDetail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (1, '10:00', N'Mũi Né, Phan Thiết nổi tiếng với những bãi cát trắng trải dài trên mặt biển xanh biếc và những hàng dừa cao vút, đặc biệt tới đây bạn sẽ được thưởng thức những món ăn ngon và nổi tiếng của người vùng đất ven biển vô cùng xinh đẹp này. Nơi đây còn nổi tiếng là khu nghỉ dưỡng bởi hệ thống khách sạn và resort ven biển, là nơi nghỉ ngơi cuối tuần cho cả gia đình đầy thú vị với một số thắng cảnh như Bàu Sen – Bàu Trắng, Suối Hồng, Đồi Cát Bay, hay muốn tìm hiểu về lịch sử có thể tìm về trường Dục Thanh, thành phố Phan Thiết… Hãy đồng hành cùng Vietravel khám phá hành trình thú vị này bạn nhé.', 1, 0)
SET IDENTITY_INSERT [dbo].[travelDetail] OFF
GO

-- INSERT ageType
SET IDENTITY_INSERT [dbo].[ageType] ON
INSERT INTO ageType (id, description, is_deleted) VALUES (1, N'Người lớn', 0)
INSERT INTO ageType (id, description, is_deleted) VALUES (2, N'Trẻ em', 0)
INSERT INTO ageType (id, description, is_deleted) VALUES (3, N'Trẻ nhỏ', 0)
INSERT INTO ageType (id, description, is_deleted) VALUES (4, N'Em bé', 0)
SET IDENTITY_INSERT [dbo].[ageType] OFF
GO

-- INSERT priceDetail
SET IDENTITY_INSERT [dbo].[priceDetail] ON
INSERT INTO priceDetail (id, price, travel_Id, ageId, is_deleted) VALUES (1, 13960000, 1, 1, 0)
SET IDENTITY_INSERT [dbo].[priceDetail] OFF
GO

-- INSERT booking
SET IDENTITY_INSERT [dbo].[booking] ON
INSERT INTO booking (id, account_Id, createdDate, [address], phone, totalPrice, payBoolean, isDeleted)
	VALUES (1, 1, '2021-10-09', 'TP.HCM', '0394008704', 13960000, 1, 0)
SET IDENTITY_INSERT [dbo].[booking] OFF
GO

-- INSERT bookingDetail
SET IDENTITY_INSERT [dbo].[bookingDetail] ON
INSERT INTO bookingDetail (id, booking_Id, travel_Id, price) VALUES (1, 1, 1, 13960000)
SET IDENTITY_INSERT [dbo].[bookingDetail] OFF
GO

-- INSERT payment
SET IDENTITY_INSERT [dbo].[payment] ON
INSERT INTO payment (id, booking_Id, payTime, totalPrice) VALUES (1, 1, '2021-10-09 10:00:00', 13960000)
SET IDENTITY_INSERT [dbo].[payment] OFF
GO