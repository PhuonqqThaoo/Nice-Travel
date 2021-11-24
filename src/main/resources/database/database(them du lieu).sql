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
	fullname		nvarchar(225)	null,
	[password]		varchar(255)	not null,
	email			varchar(100)	not null unique,
	gender			bit				null,
	[address]		nvarchar(225)	null,
	phone			varchar(20)		null,
	img				varchar(225)	null,
	id_card			varchar(50)		null unique,
	role_Id			int				foreign key references roles(id) default 3,
	created_date	datetime		not null default getdate(),
	verification_code varchar(64)   ,
	is_enable		bit				not null default 0,
	provider		varchar(15)		null
)
go

/*create table Account_detail(
	id_account		int				primary key ,
	fullname		nvarchar(225)	null,
	gender			bit				not null,
	[address]		nvarchar(225)	null,
	phone			varchar(20)		not null,
	img				varchar(225)	null,
	id_card			varchar(50)		not null,
	constraint FK_Account  foreign key(id_account) references Account(id)
)
go */

create table travel_types(
	id				int				primary key identity,
	[type]			nvarchar(225)	not null,
	[description]	nvarchar(225)	null,
	slug			varchar(255)	not null,
	is_deleted		bit				not null default 0
)
go

create table travel(
	id				int				primary key identity,
	[name]			nvarchar(225)	not null,
	[type_id]		int				foreign key references travel_types(id),
	departure_place	nvarchar(225)	not null,
	place			nvarchar(225)	not null,
	price			decimal(12,3)	not null,
	img				varchar(225)	null,
	created_date	datetime		not null default getdate(),
	[start_date]	datetime		not null,
	end_date		datetime		not null,
	quantity		int				not null,
	[hour]			int				not null,
	slug			varchar(255)	not null,
	is_deleted		bit				not null default 0,
	quantity_new    int             not null default 0
)
go

create table travel_detail(
	id				int				primary key identity,
	[time]			nvarchar(100)		not null, 
	[description]	nvarchar(max)	null,
	travel_Id		int				foreign key references travel(id),
	is_deleted		bit				not null default 0
)
go

create table age_type(
	id 				int 			primary key identity,
	[description]	nvarchar(225)	not null,
	is_deleted		bit				not null default 0
)
go

create table price_detail(
	id 				int 			primary key identity,
	price 			decimal(12,3)	not null,
	travel_Id 		int 			foreign key references travel(id),
	age_id 			int  			foreign key references age_type(id),
	is_deleted		bit				not null default 0
)
go 

create table booking(
	id				int 			primary key identity,
	account_Id		int 			foreign key references Account(id),
	created_date	datetime		not null default getdate(),
	[address]		nvarchar(225)	null,
	phone			varchar(20)		not null,
	total_price		decimal(12,3)	not null,
	pay_boolean		bit				not null default 0,
	is_deleted		bit				not null default 0
)
go

create table booking_detail(
	id				int 			primary key identity,
	booking_Id		int 			foreign key references booking(id),
	travel_Id		int 			foreign key references travel(id),
	price			decimal(12,3)	not null,
	quantity		int				not null
)
go

create table payment(
	id				int 			primary key identity,
	booking_Id 		int 			foreign key references booking(id),
	pay_time 		datetime		not null,
	total_price		decimal(12,3)	not null,
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
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (1, 'admin','Nguyen Admin', '$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'nice_travel@gmail.com',1,N'123 Hòa Bình','0987463739','user.png','2155098989', 1, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (2, 'staff','Staff', '$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'staff@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','2155098945', 2, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (3, 'user', 'User','$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'user@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','234589874', 3, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (4, 'tuanpc',N'Phạm Công Tuấn', '$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'tuanpc@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','215589766', 1, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (5, 'thaonpt',N'Nguyễn Thị Phương Thảo', '$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'thaontp@gmail.com',1,N'123 Hòa Bình','0987463739','user.png','215508066', 1, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (6, 'nhatta', N'Trần Anh Nhật','$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'nhatta@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','2155098689', 1, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (7, 'danhp',N'Huỳnh Phước Dân' ,'$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'danhp@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','2155298989', 1, '2021-10-09', 0)
INSERT INTO Account (id, username, fullname, password, email, gender, address, phone, img, id_card, role_Id,created_date, is_enable) VALUES (8, 'thangvv',N'Võ Văn Thắng', '$2a$12$yB4oIy./c3WAWa8a9C1Z6.RVdpzGGawwWK34hGnNRUN8iTR.sL8ZG', 'thangvv@gmail.com',0,N'123 Hòa Bình','0987463739','user.png','2154098989', 1, '2021-10-09', 0)


SET IDENTITY_INSERT [dbo].[Account] OFF
GO

-- Insert AccountDetail



-- Insert travelTypes
SET IDENTITY_INSERT [dbo].[travel_types] ON
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (1, N'TP. Hồ Chí Minh', N'Tour du lịch khu vực TP. HCM', 'tour-hcm', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (2, N'Bà Rịa - Vũng Tàu', N'Tour du lịch khu vực Vũng Tàu', 'tour-vung-tau', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (3, N'Phú Quốc', N'Tour du lịch khu vực Phú Quốc', 'tour-phu-quoc', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (4, N'Đà Lạt', N'Tour du lịch khu vực Đà Lạt', 'tour-da-lat', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (5, N'Cần Thơ', N'Tour du lịch khu vực Cần Thơ', 'tour-can-tho', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (6, N'Phú Yên', N'Tour du lịch khu vực Phú Yên', 'tour-phu-yen', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (7, N'Phan Thiết', N'Tour du lịch khu vực Phan Thiết', 'tour-phan-thiet', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (8, N'Đà Nẵng', N'Tour du lịch khu vực Đà Nẵng', 'tour-da-nang', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (9, N'Hà Nội', N'Tour du lịch khu vực Hà Nội', 'tour-ha-noi', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (10, N'Quảng Nam', N'Tour du lịch khu vực Quảng Nam', 'tour-quang-nam', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (11, N'Huế', N'Tour du lịch khu vực Huế', 'tour-hue', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (12, N'Long An', N'Tour du lịch khu vực Long An', 'tour-long-an', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (13, N'Buôn Ma Thuột', N'Tour du lịch khu vực BMT', 'tour-buon-ma-thuot', 0)
INSERT INTO travel_types (id, type, description, slug, is_deleted) VALUES (14, N'Lào Cai', N'Tour du lịch khu vực Lào Cai', 'tour-lao-cai', 0)
SET IDENTITY_INSERT [dbo].[travel_types] OFF
GO

-- Insert travel
SET IDENTITY_INSERT [dbo].[travel] ON
INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (1, N'Phan Thiết - Lâu Đài Rượu Vang - Bàu Trắng - Bảo Tàng Làng Chài Xưa', 7, N'TP. Hồ Chí Minh',
	N'TP. Hồ Chí Minh', 13960000, 'phan-thiet-ld-bt.png', '2021-10-09', '2021-10-10', '2021-10-15', 4, 10, 'phan-thiet-10-10', 0,4)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (2, N'Đà Lạt - Kim Ngân Hills - Quê Garden', 4, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 18760000,
	'dalat.jpg', '2021-10-09', '2021-10-10', '2021-10-15', 10, 10, 'da-lat-tour', 0,10)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (3, N'Vũng Tàu "Biển Hát " - Nhà Úp Ngược - Bảo Tàng Bà Rịa', 2, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 16740000,
	'vungtau.jpg', '2021-10-09', '2021-10-10', '2021-10-15', 10, 10, 'vung-tau-tour', 0,10)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (4, N'Miền Tây - Tiền Giang - Cần Thơ - Sóc Trăng - Bạc Liêu - Cà Mau - Đất Mũi', 5, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 3990000,
	'cantho.jpg', '2021-10-09', '2021-12-02', '2021-12-06', 10, 10, 'can-tho-tour',0, 10)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (5, N'Phú Yên - Quy Nhơn', 6, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 5190000,
	'phuyen.jpg', '2021-10-09', '2021-12-09', '2021-12-12', 10, 10, 'phu-yen-tour',0, 9)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (6, N'Long An - Khám Phá Con Đường Xuyên Rừng Tràm Tân Lập', 12, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 7160000,
	'long-an.jpg', '2021-11-25', '2021-11-25', '2021-11-26', 20, 10, 'long-an-tour',0, 7)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (7, N'Buôn Ma Thuột - Thác Dray Nur - KDL Sinh Thái KoTam - Bảo Tàng Thế Giới Cà Phê', 13, N'TP. Hồ Chí Minh', N'TP. HCM', 2390000,
	'buon-ma-thuot.jpg', '2021-11-26', '2021-11-26', '2021-11-29', 15, 10, 'bmt-tour',0, 2)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (8, N'Sapa - Hàm Rồng - Fansipan - Cát Cát - Secret Garden - thiên đường sống ảo giữa lòng phố núi', 14, N'Hà Nội', N'Hà Nội', 3290000,
	'sa-pa.jpg', '2021-11-25', '2021-11-26', '2021-11-29', 20, 8, 'sa-pa-tour',0, 4)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (22, N'Đà Nẵng - Sơn Trà - Hội An - Suối khoáng nóng Thần Tài - Rừng dừa bảy mẫu - Đà Nẵng ', 8, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 3990000,
	'da-nang.jpg', '2021-11-25', '2021-12-10', '2021-12-13', 15, 3, 'da-nang-tour',0, 9)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (9, N'Huế - La Vang - Động Phong Nha & Động Thiên Đường - Bà Nà - Cầu Vàng - Hội An - Đà Nẵng',11, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 6590000,
	'hue.jpg', '2021-11-29', '2021-12-08', '2021-12-13', 15, 5, 'hue-tour',0, 9)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (11, N'Tham quan Safari World & Thỏa Thích Vui Chơi Tại Vinwonder - Trải nghiệm dịch vụ Nghỉ dưỡng VinHoliday Phú Quốc',3, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 7090000,
	'phu-quoc2.jpg', '2021-12-10', '2021-12-10', '2021-12-13', 10, 19, 'phu-quoc-tour',0, 6)

	
INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (12, N'Khám Phá Trải Nghiệm Vinpearl Nam Hội An ',10, N'Đà Nẵng', N'Đà Nẵng', 2000000,
	'quang-nam.jpg', '2021-11-25', '2021-12-01', '2021-12-02', 10, 7, 'quang-nam-tour',0, 4)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (13, N'Hà Nội - Bái Đính - Tràng An - Hạ Long - Yên Tử',9, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 8390000,
	'ha-noi.jpg', '2021-11-25', '2021-12-04', '2021-12-08', 10, 5, 'ha-noi-tour',0, 7)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (14, N'Hà Nội - Sapa - Hạ Long - Bái Đính - Tràng An - Tuyệt tình cốc',9, N'Quy Nhơn', N'Bình Định', 8680000,
	'ha-noi-sa-pa.jpg', '2021-11-25', '2021-12-28', '2021-12-30', 10, 11, 'ha-noi-tour',0, 8)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (15, N'Đà Lạt - Du lịch canh nông kết hợp Trải nghiệm gia đình “Run with kids”',4, N'Đà Lạt', N'Đà Lạt', 3590000,
	'da-lat-gia-dinh.jpg', '2021-11-25', '2021-12-05', '2021-12-08', 10, 17, 'da-lat-tour',0, 8)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (16, N'Đà Lạt - Que Garden - Thác Datanla - Khu Du Lịch Kim Ngân Hills - Cà Phê Mê Linh',4, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 2990000,
	'da-lat-tourcity.jpg', '2021-11-25', '2021-12-04', '2021-12-08', 10, 17, 'da-lat-tour',0, 7)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (17, N'Phan Thiết - Mũi Né - Bàu Trắng ',7, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 3590000,
	'phan-thiet-mui-ne.jpg', '2021-11-25', '2021-12-26', '2021-12-29', 10, 12, 'phan-thiet-tour',0, 9)


INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (18, N'Long Xuyên – Vũng Tàu – Bến Du Thuyền Marina – Tượng Chúa Kito',2, N'Long Xuyên', N'An Giang', 1890000,
	'long-xuyen-vung-tau.jpg', '2021-11-25', '2021-11-27', '2021-12-29', 15, 12, 'vung-tau-tour',0, 9)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (19, N'Buôn Ma Thuột - Hồ Lắk - Buôn Kôtam - Bảo Tàng Thế Giới Cà Phê',13, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 3590000,
	'bmt-ca-phe.jpg', '2021-11-25', '2021-12-02', '2021-12-06', 10, 14, 'bmt-tour',0, 9)

INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (20, N'Củ Chi(TP. Hồ Chí Minh) - Nông Trang Xanh - Địa Đạo vùng Đất Thép',1, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 699000,
	'cu-chi.jpg', '2021-11-25', '2021-11-28', '2021-11-29', 10, 14, 'hcm-tour',0, 9)

	
INSERT INTO travel (id, [name], type_id,departure_place, place, price, img, created_date, [start_date], end_date, quantity, [hour], slug, is_deleted,quantity_new)
	VALUES (21, N'Hành trình xanh Cần Giờ (Tàu cao tốc) - Khám phá KDL Sinh thái Dần Xây - Vàm Sát [TP. Hồ Chí Minh]',1, N'TP. Hồ Chí Minh', N'TP. Hồ Chí Minh', 1790000,
	'tau-hcm.jpeg', '2021-11-25', '2021-12-12', '2021-12-13', 10, 9, 'hcm-tour',0, 9)
SET IDENTITY_INSERT [dbo].[travel] OFF

--INSERT travelDetail
SET IDENTITY_INSERT [dbo].[travel_detail] ON
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (1, N'Ngày 1 - TP.HỒ CHÍ MINH - MŨI NÉ - LÂU ĐÀI RƯỢU VANG Số bữa ăn: 03 (Ăn sáng, trưa, tối)', N'Quý khách tập trung tại Công ty Vietravel (190 Pasteur, Phường Võ Thị Sáu, Quận 3, TP.Hồ Chí Minh) khởi hành đi Phan Thiết. Quý khách ăn sáng trên cung đường đi. Đến Phan Thiết đoàn tham quan: Lâu đài Rượu Vang RD: nghe giới thiệu về quy trình sản xuất, đóng chai và thưởng thức một trong những loại rượu vang hảo hạng nổi tiếng thế giới được xuất xứ từ Thung Lũng Napa (Mỹ). Nhận phòng tự do nghỉ ngơi, tắm biển hồ bơi tại khách sạn/resort. Buổi chiều xe đưa đoàn đi tham quan: Bảo tàng Ngọc Trai Mũi Né: khu trưng bày được giới thiệu một cách nghệ thuật và sang trọng theo từng chủng loại, màu sắc và kích cỡ. Từ các bộ sưu tập ngọc trai để nguyên cho đến các loại trang sức đính ngọc trai như: vòng đeo cổ, mặt dây chuyền, nhẫn, bông tai, lắc, đồng hồ đính ngọc trai…. Sau khi ăn tối, Quý khách tự do dạo phố khám phá Mũi Né về đêm,…Nghỉ đêm tại Mũi Né. ', 1, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (2, N'Ngày 2 - MŨI NÉ - BÀU TRẮNG Số bữa ăn:02 (Ăn sáng, trưa)', N'Quý khách dùng bữa sáng tại khách sạn. Sau đó, đoàn đi tham quan: Bàu Trắng: hay còn được gọi với cái tên khác Bàu Sen bởi trong hồ khi vào mùa hè sen nở sẽ rất đẹp, phủ kín cả một vùng hồ…. Quý khách dùng bữa trưa. Chiều tự do nghỉ ngơi, tắm biển hồ bơi tại khách sạn/resort,…hoặc xe đưa đoàn tham quan: Trung tâm Bùn Khoáng Mũi Né: Tận hưởng cảm giác tắm khoáng thư giãn, ngoài ra Quý khách còn có thể tắm bùn hoặc massage là một liệu pháp làm đẹp từ thiên nhiên giúp cải thiện làn da tươi trẻ, mịn màng (chi phí tự túc). Buổi tối, Quý khách tự do dạo phố ăn chiều tự túc. Nghỉ đêm tại Mũi Né. ', 1, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (3, N'Ngày 3 - MŨI NÉ – LÀNG CHÀI XƯA – TP HỒ CHÍ MINH Số bữa ăn:02 (Ăn sáng, trưa)', N'Quý khách dùng bữa sáng và tự do tắm biển. Đến giờ trả phòng, đoàn đi tham quan: Làng Chài Xưa Mũi Né: với lịch sử 300 năm cái nôi của nghề làm nước mắm, trải nghiệm cảm giác lao động trên đồng muối, đi trên con đường rạng xưa, thăm phố cổ Phan Thiết, , thăm nhà lều của hàm hộ nước mắm xưa, đắm chìm cảm xúc trong biển 3D và thích thú khi đi chợ làng chài xưa với bàn tính tay, bàn cân xưa thú vị,… Mua sắm đặc sản Phan Thiết sạch tại Organik Farm - Hello Muine (chi phí tự túc) - nước mắm rin nguyên chất, nước mắm Nhật Shiitake, mực một nắng, khô cá dứa, nước cốt thanh long… về làm quà cho người thân và bạn bè. Chiều đoàn về đến TP.Hồ Chí Minh, xe đưa về điểm đón ban đầu. Chia tay Quý khách và kết thúc chương trình du lịch. Lưu ý: Đảm bảo nguyên tắc 5K, khử khuẩn. Vận chuyển không quá 50% số ghế, không quá 20 khách/1 xe (bao gồm cả tài xế), tuân thủ ngồi giãn cách trên xe và đeo khẩu trang suốt quá trình di chuyển. Thẻ xanh covid (khách đã tiêm vaccine ngừa covid đủ liều, thời gian hoàn tất mũi 2 trước 14 ngày và không quá 12 tháng), khách là FO đã khỏi bệnh (có giấy chứng nhận của Sở Y Tế và không quá 06 tháng). Xét nghiệm covid theo yêu cầu của từng tỉnh vào thời điểm đặt dịch vụ. Vui lòng liên hệ với nhân viên tư vấn để biết thêm chi tiết.', 1, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (4, N'Ngày 1 - TP.HỒ CHÍ MINH - BẢO LỘC - THÁC ĐAM B’RI - ĐÀ LẠT (Ăn sáng, trưa, tối)', N'Quý khách tập trung tại Công ty Vietravel (190 Pasteur, Phường Võ Thị Sáu, Quận 3, TP.Hồ Chí Minh) khởi hành đi Đà Lạt. Quý khách ăn sáng trên cung đường đi. Đến Bảo Lộc đoàn tham quan: Thác Đam B’ri: được coi là ngọn thác lớn nhất vùng đất Lâm Đồng với chiều cao 60 m tạo thành 2 dòng chảy cao thấp rất hùng vĩ. Khu du lịch thác Đamb ri còn là một quần thể du lịch bao gồm khu vui chơi, hồ, thác và rừng nguyên sinh rộng hàng trăm hecta với khí hậu trong mát của rừng nguyên sinh Nam Tây Nguyên. Sau khi dùng bữa trưa, đoàn tiếp tục hành trình đến với thành phố ngàn hoa. Buổi tối, Quý khách tự do dạo chợ đêm Đà Lạt hay thưởng thức bánh tráng nướng và ly sữa đậu nành nóng trong tiết trời se lành. Nghỉ đêm tại Đà Lạt.', 2, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (5, N'Ngày 2 - ĐÀ LẠT - ĐỒI CHÈ CẦU ĐẤT - KHU DU LỊCH KIM NGÂN HILLS (Ăn sáng, trưa, tối)', N'Đoàn khởi hành tham quan: Ga Xe Lửa Đà Lạt: nhà ga cổ kính nhất Việt Nam và Đông Dương, có phong cách kiến trúc độc đáo với ba mái hình chóp cách điệu như ba đỉnh núi Langbiang và nhà rông Tây Nguyên. Đồi Chè Cầu Đất: trong bầu không khí trong lành của những đồi chè xanh bát ngát gần 100 năm tuổi, Quý khách tản bộ khám phá Đồi chè Oolong ở độ cao 1.650 mét và hồ nước màu ngọc bích: tham quan thực tế đồi chè Oolong, cảm nhận nguồn nguyên liệu xanh tạo nên thương hiệu của Cầu Đất Farm. Di chuyển vào Cầu Đất Farm Tea & Coffee - được thiết kế theo xu hướng không gian mở hiện đại. Quý khách trực tiếp tìm hiểu các bước để có một ấm trà ngon, thưởng thức hương vị tinh tế từ các sản phẩm trà được trồng tại nông trại Cầu Đất. Tại khu vực không gian triển lãm, Quý khách có thể tìm hiểu và lựa chọn những sản phẩm từ trà, café về làm quà tặng đầy ý nghĩa cho người thân, bạn bè (chi phí quà tặng tự túc). Buổi chiều, đoàn tiếp tục tham quan: Khu Du Lịch Kim Ngân Hills: nổi bật với vườn thú kết hợp với không gian cà phê view rừng thông ấn tượng, xen kẻ là vườn hoa Xác Pháo, Thạch Thảo, Cẩm Tú Cầu để du khách thỏa thích check-in. Vườn thú Kim Ngân Hills đang nuôi giữ và bảo tồn nhiều loài động vật độc lạ như lạc đà Alpaca, hươu sao, ngựa Ponny, cừu Valis, chó Corgi... Ngoài ra, nơi đây còn sở hữu khu vực nuôi chim lớn nhất tỉnh Lâm Đồng với nhiều loài chim như yến phụng, sáo, cu đất, két.... Tiếp tục, du khách khám phá khu “Ký ức thời bao cấp” gợi cảm xúc hoài niệm về một thời quá khứ.Buổi tối, Quý khách tự do thưởng thức café ngắm Đà Lạt lung linh về đêm hoặc tản bộ dọc bờ hồ Xuân Hương. Nghỉ đêm tại Đà Lạt.', 2, 0)
	INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (6, N'Ngày 3 - ĐÀ LẠT - QUÊ GARDEN - TP. HỒ CHÍ MINH (Ăn sáng, trưa)', N'Sau khi ăn sáng xe đưa đoàn tham quan:Quê Garden: nằm dưới chân đèo Mimosa, khu vườn mê mẩn người xem với cánh đồng hoa rộng lớn cùng nhiều loài hoa nổi tiếng tại Đà Lạt khoe sắc. Đặc biệt Quê Garden tự hào là khu vườn bonsai lá kim lớn nhất Việt Nam, những chậu cây bonsai vừa to vừa đẹp, được chăm sóc và uốn nắn kỹ lưỡng vô cùng đẹp mắt. Ngoài ra, hồ cá koi với view nhìn ra đồi thông là điểm check in cực chất mà du khách không thể bỏ qua. Chiều đoàn về đến TP.Hồ Chí Minh, xe đưa về điểm đón ban đầu. Chia tay Quý khách và kết thúc chương trình du lịch. Ghi chú: Điểm tham quan có thể sắp xếp lại cho phù hợp mà vẫn bảo đảm đầy đủ nội dung của từng chương trình. Lưu ý: Đảm bảo nguyên tắc 5K, khử khuẩn. Vận chuyển không quá 50% số ghế, không quá 20 khách/1 xe (bao gồm cả tài xế), tuân thủ ngồi giãn cách trên xe và đeo khẩu trang suốt quá trình di chuyển. Thẻ xanh covid (khách đã tiêm vaccines ngừa covid đủ liều, thời gian hoàn tất mũi 2 trước 14 ngày và không quá 12 tháng), khách là FO đã khỏi bệnh (có giấy chứng nhận của Sở Y Tế và không quá 06 tháng). Xét nghiệm covid theo yêu cầu của từng tỉnh vào thời điểm đặt dịch vụ. Vui lòng liên hệ với nhân viên tư vấn để biết thêm chi tiết.', 2, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (7, N'Ngày 1 - TP. HỒ CHÍ MINH - VŨNG TÀU “BIỂN HÁT” (Ăn sáng, trưa, tối)', N'Quý khách tập trung tại Vietravel (190 Pasteur, Phường Võ Thị Sáu, Quận 3) khởi hành đi Bà Rịa - Vũng Tàu theo tuyến đường cao tốc TP. Hồ Chí Minh - Long Thành. Đến TP. Vũng Tàu, đoàn tham quan: Nhà Úp Ngược: có 7 phòng chụp ảnh cực chất, được trang trí với màu sắc bắt mắt và nội thất trong nhà đều đảo ngược từ bàn ghế, đồ ăn, xe cộ, giường ngủ đến bồn rửa mặt, máy giặt… Bảo tàng Bà Rịa - Vũng Tàu: một công trình rộng lớn được thiết kế bởi kiến trúc sư Đỗ Anh Dũng gồm 4 tầng chính và 3 tầng tháp, nhìn từ xa như một con tàu trắng đang vươn mình ra đại dương. Với khối lượng tư liệu, hiện vật đồ sộ về Bà Rịa Vũng Tàu được sắp xếp bài bản cùng ánh sáng, công nghệ dựng cảnh hiện đại, lôi cuốn trong từng bối cảnh dẫn lối du khách xuyên suốt câu chuyện từ thời Tiền - Sơ sử đến ngày nay. Mỗi gian phòng hiện ra như một trang lịch sử sống động đưa người xem như ngược dòng lịch sử hòa trong khung cảnh các thời kỳ, nổi bật trong số đó là các gian phòng trưng bày chuyên đề như di tích nhà tù Côn Đảo, cổ vật Hòn Cau, Khu căn cứ Minh Đạm, ngành nghề truyền thống của người dân địa phương qua các giai đoạn,… Đoàn nhận phòng khách sạn theo tiêu chuẩn 4 sao để nghỉ ngơi. Buổi chiều, Quý khách có thể hòa mình vào làn nước biển mát lạnh, nằm thư giãn trên bãi cát dài để tắm mình dưới ánh nắng mặt trời lúc chiều tà, vui đùa cùng những con sóng vỗ rì rào bên bờ biển thoai thoải. Buổi tối, Quý khách tự do thưởng thức những món hải sản phong phú, tươi ngon. Nghỉ đêm tại Vũng Tàu.', 3, 0)
INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (8, N'Ngày 2 - VŨNG TÀU - TP. HỒ CHÍ MINH (Ăn sáng, trưa)', N'Quý khách tự do tắm biển hoặc nghỉ ngơi thư giãn tại khách sạn.Sau khi trả phòng và dùng bữa trưa, đoàn khởi hành về TP. Hồ Chí Minh trên đường dừng chân tham quan: Chợ Bà Rịa: mua sắm các loại đặc sản vùng biển về làm quà cho người thân và gia đình. Vạn Phật Quang Đại Tòng Lâm Tự: một ngôi đại tự bao gồm nhiều quần thể kiến trúc quy mô như tịnh thất và trường Phật học. Chùa còn sở hữu những kỷ lục như Ngôi chùa có tượng Di Lặc Bồ Tát nguyên khối bằng đá hoa cương lớn nhất; Ngôi chùa có tượng Phật nhiều nhất… Xe đưa đoàn về điểm đến ban đầu, kết thúc chuyến du lịch. Lưu ý: Đảm bảo nguyên tắc 5K, khử khuẩn. Vận chuyển không quá 50% số ghế, không quá 20 khách/1 xe (bao gồm cả tài xế), tuân thủ ngồi giãn cách trên xe và đeo khẩu trang suốt quá trình di chuyển. Thẻ xanh covid (khách đã tiêm vaccine ngừa covid đủ liều, thời gian hoàn tất mũi 2 trước 14 ngày và không quá 12 tháng), khách là FO đã khỏi bệnh (có giấy chứng nhận của Sở Y Tế và không quá 06 tháng). Xét nghiệm covid theo yêu cầu của từng tỉnh vào thời điểm đặt dịch vụ. Vui lòng liên hệ với nhân viên tư vấn để biết thêm chi tiết.', 3, 0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (9,N'Ngày 1 - TP. HỒ CHÍ MINH - CẦN THƠ - CỒN SƠN (Ăn Sáng, trưa, tối)',N'Xe và hướng dẫn viên Vietravel đón Quý khách tại 190 Pasteur, Quận 3, Tp.HCM khởi hành đi miền Tây theo tuyến cao tốc Trung Lương ngắm nhìn những cánh đồng lúa và màu xanh vườn tược hai bên đường. 

Đến bến đò Cô Bắc (Cần Thơ), Quý khách đi đò qua sông tham quan cụm du lịch cộng đồng Cồn Sơn - Ngôi làng du lịch cộng đồng độc đáo giữa lòng phố thị cùng những trải nghiệm đặc biệt:

- Tham quan mô hình nuôi cá lồng bè trên sông Hậu: nơi bảo tồn hơn 10 loài cá nước ngọt quý hiếm, du khách được giao lưu trao đổi với người dân địa phương về kỹ thuật nuôi cá lồng bè trên sông Hậu. Thưởng thức sản phẩm làm từ cá thát lát cườm. Đặc biệt, khách tham quan còn được tận mắt chiêm ngưỡng “cá cung thủ” và trải nghiệm Massage chân bằng cá có vảy.
- Quý khách cùng nhau thưởng thức bữa cơm gia đình - Mỗi nhà một món ngon với "thực đơn bay" đặc trưng chỉ có ở Cồn Sơn.
- Trải nghiệm học cách vót đũa và đan võng: nghề truyền thống thủ công đang dần mai một nhưng vẫn còn được lưu giữ tại đây. Đặc biệt được trải nghiệm làm khuôn và thưởng thức bánh phu thê miền Tây (giải nhất bánh ngon Nam Bộ 2015).
- Rảo bước trên bờ đê, thả hồn vào không gian yên tĩnh, đoàn dừng chân tại vườn trái cây đặc sắc theo mùa: nhãn xuồng, chôm chôm, vú sữa, ổi, bưởi… (Mỗi mùa mỗi trái).
- Như được trở về với tuổi thơ từ trải nghiệm làm bánh dân gian: bánh lá mít/ bánh kẹp cuốn/ cốm nổ qua sự hiện dẫn tận tình của các nghệ nhân chất phác. Ngoài thưởng thức những món bánh từ chính tay mình làm ra, du khách còn được dùng thử món bánh xèo miền Tây đặc sắc.
 
Quay trở về TP. Cần Thơ, đoàn tiếp tục tham quan: 
- Nhà Cổ Bình Thủy: một trong những ngôi nhà cổ đẹp nhất Miền Tây, hiện thờ họ Dương, được xây dựng lần đầu tiên vào năm 1870 và tôn tạo lại vào giai đoạn đầu thế kỷ 20.

Sau khi dùng bữa tối tại TP. Cần Thơ, Quý khách tự do dạo phố đêm khám phá “Tây Đô” lung linh sắc màu: ngắm cảnh Cầu Cần Thơ về đêm; Check-in Cầu Tình Yêu - thiết kế uốn lượn hình chữ S cùng hai đài sen và hệ thống đèn led rực rỡ sắc màu; Chụp ảnh tại Chùa Ông cổ kính; Khám phá Chợ Đêm Ninh Kiều và Con Đường Bích Họa Cần Thơ Xưa Và Nay - tái hiện nét đẹp văn hóa của người dân Cần Thơ nói riêng, vùng Đồng bằng sông Cửu Long nói chung.

Nghỉ đêm tại Cần Thơ.',4,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (10,N'Ngày 2 - CẦN THƠ - CHỢ NỔI CÁI RĂNG - BẠC LIÊU - CÀ MAU (Ăn sáng, trưa, tối)',N'Đoàn trả phòng và khởi hành ra Bến Ninh Kiều, xuống thuyền du ngoạn trên sông Cần Thơ, nghe giới thiệu về Chợ thủy sản nước ngọt Cần Thơ; trải nghiệm Chợ nổi Cái Răng - một hình thức họp chợ đặc trưng tại các ngã sông của vùng đồng bằng sông Cửu Long, chợ nổi quy tụ hàng trăm ghe xuồng, tạo thành nơi mua bán, giao thương các loại trái cây, đặc sản sầm uất trên sông nước. Chợ Nổi Cái Răng đã được công nhận Di Sản Văn Hóa Phi Vật Thể Quốc Gia.
Khởi hành đi Bạc Liệu đoàn tham quan:
- Nhà công tử Bạc Liêu: do kỹ sư người Pháp thiết kế vào năm 1919, đây được xem là ngôi biệt thự bề thế nhất ở Nam kỳ lục tỉnh lúc bấy giờ. Đoàn nghe kể về giai thoại vàng son một thời của cậu Ba Huy - Người được mệnh danh là “Công tử Bạc Liêu”.
 
Sau khi ăn trưa, đoàn khởi hành đi Cà Mau. Trên đường đi, Đoàn dừng chân tham quan Nhà thờ Tắc Sậy - Nơi an nghỉ của Linh mục Trương Bửu Diệp được nhiều người biết đến với lòng sùng mộ, nơi đây gắn liền với những câu chuyện cảm động về cuộc đời của cha Trương Bửu Diệp. 
Đến Cà Mau, đoàn tham quan Lâm Viên 19/05 với vườn chim trong thành phố và khu tưởng niệm Chủ Tịch Hồ Chí Minh. 
Nghỉ đêm ở Cà Mau.',4,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (11,N'Ngày 3 - CÀ MAU - ĐẤT MŨI - BẠC LIÊU - CÁNH ĐỒNG ĐIỆN GIÓ (Ăn sáng, trưa, tối)',N'Đoàn trả phòng và khởi hành đến Đất Mũi, di chuyển bằng cano len lỏi trong rừng đước, khám phá khu rừng ngập mặn lớn thứ hai thế giới với hệ sinh thái đa dạng, mang vẻ đẹp hoang dại cùng bầu không khí trong lành.
Tại Đất Mũi, đoàn tham quan chinh phục Cột cờ Đất Mũi - Mô phỏng theo kiến trúc Cột cờ Hà Nội, khẳng định chủ quyền lãnh thổ dân tộc, gắn bó Bắc Nam một nhà. Từ trên kỳ đài cao hơn 41 m, du khách có thể thưởng ngoạn bãi bồi đất mũi, vẻ đẹp hùng vĩ của rừng đước bạt ngàn và biển đông mênh mông.
 
Quý khách dùng bữa trưa tại khu du lịch cộng đồng Hoàng Hôn với những món ăn đặc sản địa phương: cá thòi lòi, vọp, hàu, cua Cà Mau…. Tại đây, Quý khách được trải nghiệm mặc áo bà ba, nón tai bèo và khăn rằn, trải nghiệm các hoạt động như chèo xuồng ba lá, câu cá trên vuông, đi cầu khỉ... 
Đoàn khởi hành đi Bạc Liêu, tham quan:
- Chùa Xiêm Cán: với sắc vàng lộng lẫy cùng thiết kế nhiều hoa văn tỉ mỉ mang đậm dấu ấn Khmer Nam Bộ. Ngoài ra Quý khách còn được nghe thuyết pháp giới thiệu về phật giáo Nam Tông và nét đẹp văn hóa kiến trúc Khmer. 
- Cánh Đồng Điện Gió: chiêm ngưỡng vẻ đẹp những tua-bin gió khổng lồ cao hơn 80 m sừng sững trên nền trời xanh với ý nghĩa tạo một nguồn năng lượng sạch vô tận trên mặt biển, giảm thiểu tác động gây hiệu ứng nhà kính… 
Nghỉ đêm ở Bạc Liêu.',4,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (12,N'Ngày 4 - BẠC LIÊU - SÓC TRĂNG - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Sau bữa sáng, Quý khách tham quan:
- Quảng Trường Hùng Vương: hình ảnh cây đàn kìm cách điệu (cao 18,6 m) nhạc cụ không thể thiếu trong đờn ca tài tử cải lương, biểu tượng văn hóa của tỉnh Bạc Liêu. 
- Khu lưu niệm nghệ thuật Đờn Ca Tài Tử Nam Bộ và cố nhạc sĩ Cao Văn Lầu: một công trình khái quát về thân thế, sự nghiệp của cố nhạc sĩ Cao Văn lầu, ghi nhận công lao và tôn vinh những đóng góp to lớn của ông cho nghệ thuật Đờn ca tài tử Nam bộ và là người sản sinh ra bản Dạ cổ Hoài lang bất hủ.
Đoàn khởi hành đi Sóc Trăng, tham quan hai ngôi chùa nổi tiếng: 
- Chùa Sà Lôn: còn được gọi là "chùa Chén Kiểu" do thể hiện được nét văn hóa độc đáo của người Khmer cùng cách thiết kế sáng tạo từ hàng vạn mảnh chén được ốp trên các công trình kiến trúc của chùa.
- Chùa Wat Pătum Wôngsa Som Rông - Một quần thể kiến trúc Phật giáo rộng lớn, gồm nhiều công trình như: Chánh điện, ngôi bảo tháp “vạn người mê” đẹp tựa chùa Arun tại Thái Lan,… Đặc biệt, tượng Phật Thích Ca nhập niết bàn uy nghiêm với kích thước chiều dài  63 mét…
Đoàn quay về TP. Hồ Chí Minh, kết thúc chương trình tại điểm đón ban đầu.',4,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (13,N'Ngày 1 - TP. HỒ CHÍ MINH - PHÚ YÊN Số bữa ăn: 02 (Ăn trưa, tối)',N'Quý khách tập trung tại Cột 5, ga đi trong nước, Sân bay Tân Sơn Nhất, hướng dẫn viên Vietravel hỗ trợ làm thủ tục cho đoàn đáp chuyến bay đi Phú Yên. Đến sân bay Tuy Hòa - Phú Yên, hướng dẫn viên đón Quý khách đi tham quan:
-  Gành Đá Dĩa: thắng cảnh độc nhất vô nhị của tỉnh Phú Yên và của Quốc Gia.
-  Nhà Thờ Mằng Lăng: nơi còn lưu giữ cuốn kinh thánh bằng chữ Quốc Ngữ đầu tiên của Việt Nam. 
-  Trên đường đi Quý khách chiêm ngưỡng cảnh đẹp Đầm Ô Loan trông như đôi cánh Phượng Hoàng dang rộng giữa mây trời và núi ngàn.
Đoàn ăn trưa, sau đó khởi hành tham quan:
-  Mũi Đại Lãnh: hay còn gọi là Mũi Điện (điểm cực đông của tổ quốc) 
-  Bãi Môn: một bãi biển vẫn còn khá hoang sơ, có hình vầng trăng khuyết với đường bờ biển dài khoảng 400 m, độ dốc thoai thoải, cát trắng mịn, nước trong vắt như pha lê
-  Ngắm nhìn Vịnh Vũng Rô: nằm tiếp giáp với Vịnh Vân Phong, nơi cập bến của những chuyến tàu không số với huyền thoại đường Hồ Chí Minh trên biển. 
Đoàn ăn tối và tự do tham quan thành phố Tuy Hòa về đêm. Nghỉ đêm tại Tuy Hòa.',5,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (14,N'Ngày 2 - PHÚ YÊN - QUY NHƠN Số bữa ăn: 03 (Ăn sáng, trưa, tối)',N'Dùng bữa sáng tại khách sạn. Quý khách trả phòng, đoàn khởi hành đi tham quan:
- Quảng Đức Xưa: được chiêm ngưỡng những căn nhà gỗ cổ xưa được chạm khắc thủ công tỉnh xảo với những di vật, cổ vật bài trí hài hòa, đặc biệt là bộ sưu tập gốm Quảng Đức. Đến với Quảng Đức Xưa còn là đến với không gian làng nghề truyền thống và những món ăn dân giã. Du khách có thể thử dệt chiếu, nhâm nhi một tách trà ngon hay chọn cho mình một chiếc bánh đậm vị quê.
- Eo Gió: nơi vẫn còn giữ được cho mình vẻ đẹp tự nhiên đầy hoang sơ. Đứng từ trên cao nhìn xuống, Eo Gió như một bức tranh sơn thủy hữu tình với trời, mây, nước biển núi ôm nhau vừa gợi cảm, vừa hoang sơ đầy quyến rũ
- Khu du lịch Ghềnh Ráng Tiên Sa, viếng Mộ Hàn Mặc Tử: nằm dựa lưng vào núi, nhìn bao quát cả dải bờ biển Quy Nhơn chạy dài trước mặt, hút trọn tầm mắt một phần thành phố Quy Nhơn
Đoàn ăn tối, nhận phòng và tự do khám phá thành phố về đêm với các quán cà phê nổi tiếng tại Quy Nhơn: Surf Bar, S- Blue Café, Mộc Trà Café… hoặc thưởng thức hải sản tại phố ẩm thực Xuân Diệu, khu hồ sinh thái nổi tiếng với món: bọ biển, cua Huỳnh Đế và các loại ốc đặc trưng của vùng biển Quy Nhơn (chi phí tự túc). Nghỉ đêm tại Quy Nhơn.',5,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (15,N'Ngày 3 - QUY NHƠN - TP. HỒ CHÍ MINH Số bữa ăn: 01 (Ăn sáng)',N'Dùng bữa sáng tại khách sạn. Quý khách trả phòng. Xe đưa đoàn đi tham quan:   
- Bảo Tàng Quang Trung: viếng Điện thờ Tây Sơn Tam Kiệt, chiêm ngưỡng cây me 300 tuổi, Giếng nước xưa….
- Chùa Thiên Hưng: nơi hiện đang lưu giữ Ngọc Xá Lợi của Phật Tổ Thích Ca Mâu Ni, được xem là một trong những ngôi chùa nổi tiếng bậc nhất tại Bình Định, điểm tham quan không thể bỏ qua.
Sau đó, xe đưa đoàn ra sân bay Phù Cát - Quy Nhơn, đáp chuyến bay về TP.Hồ Chí Minh. Chia tay Quý khách và kết thúc chương trình tại sân bay Tân Sơn Nhất.',5,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (16,N'Ngày 1 - TP.HCM - LONG AN - LÀNG NỔI TÂN LẬP “Khám Phá Rừng Tràm Bí Ẩn”: Số bữa ăn: 1 bữa trưa',N'Quý khách tập trung tại Vietravel (190 Pasteur, P.Võ Thị Sáu, Q.3), khởi hành đến huyện Mộc Hóa - Long An, tham quan Làng Nổi Tân Lập với những trải nghiệm đặc biệt hấp dẫn:

Dạo bước khám phá nét đẹp hoang sơ khu rừng tràm dọc theo hai bên cung đường bê-tông xuyên rừng dài và đẹp nhất Long An, con đường dẫn bước chân Quý khách đến với cảnh quan cầu chữ X, hồ Bán Nguyệt, tháp Quan Sát cao 18 mét - nơi du khách có thể phóng tầm mắt nhìn toàn cảnh vùng Đồng Tháp Mười.
Khám phá rừng tràm bằng tour xuồng Ba Lá: mang đến cho du khách một trải nghiệm và cảm xúc chân thật nhất về vùng sông nước. Xuôi mái chèo ngồi trên xuồng cùng với các cô nhân viên mặc bộ bà ba truyền thống kiêm hướng dẫn viên, Quý khách sẽ có cơ hội tận hưởng khung cảnh chim trời cá nước mênh mông đan xen với đồng Súng phủ đầy mặt nước. Chiếc xuồng sẽ len lỏi trên những con rạch nhỏ xuyên rừng tràm rất yên bình và tĩnh lặng, với 30 phút cho một cuộc trải nghiệm ngồi tham quan trên xuồng bạn sẽ trở về với thiên nhiên đầy xúc cảm và không còn gì tuyệt diệu hơn thế!
Đoàn dùng bữa trưa với các món ăn dân dã miền Tây trong không gian xanh mát như: Cá lóc nướng trui cuộn lá sen non, Ốc bươu nướng tiêu xanh,…
Đoàn khởi hành về lại TP.Hồ Chí Minh, Quý khách kết thúc hành trình tại điểm đón ban đầu.',6,0)

	INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (17,N'Ngày 1 - TP.HỒ CHÍ MINH - BUÔN MA THUỘT - THÁC DRAYNUR (Ăn sáng, trưa, chiều)',N'Quý khách tập trung tại công ty Vietravel (190 Pasteur, Q3, TP. Hồ Chí Minh). Đoàn khởi hành đi Buôn Ma Thuột. Trên đường đi Quý khách dừng chân dùng bữa sáng, ngắm đường Trường Sơn huyền thoại (Quốc Lộ 14) nghe thuyết minh về các địa danh Đồng Xoài, Sóc Bom Bo, Bù Đăng,…
Sau khi ăn trưa, đoàn khởi hành tham quan:
- Thác Dray Nur: một thắng cảnh đẹp nhờ sự kết hợp giữa hai dòng sông Krông Nô và Krông A Na tạo nên thác nước hùng vĩ như một bức tường thành khổng lồ.
**Vì lý do cung đường hạn chế di chuyển đối với xe lớn, nếu đoàn đi xe trên 35 chỗ thì sẽ chuyển sang tham quan Thác Dray Sap.
- Thác Dray Sap: cao khoảng 50 m, trải dài gần 100 m xứng đáng là một trong ba ngọn thác đẹp nhất nằm trên thượng nguồn sông Sê Rê Pốk. Theo tiếng Ê Đê, Dray Sap nghĩa là thác khói vì việc lượng hơi nước khổng lồ của dòng chảy bao phủ cả khu vực, khiến ngọn thác càng trở nên bí ẩn, hoang sơ. Đoàn check-in Khu nhà nấm khổng lồ đặc biệt tại thác.
 
Quý khách nhận phòng khách sạn nghỉ ngơi. Buổi tối, Quý khách tự do hòa mình vào nhịp sống của người dân phố núi về đêm như thưởng thức “ly cà phê Ban Mê” trứ danh ngắm phố lên đèn hay ghé chợ đêm Ngã Sáu thưởng thức ẩm thực đường phố như bún đỏ, bún riêu, bún giò, canh cá dằm… hoặc đơn giản là ngồi nhâm nhi ly sữa nóng trong tiết trời se lạnh.
Nghỉ đêm tại Buôn Ma Thuột.',7,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (18,N'Ngày 2 - BUÔN ĐÔN - KDL SINH THÁI KOTAM (Ăn sáng, trưa, chiều)',N'Sau bữa sáng, đoàn tham quan;
- Buôn Đôn: Quý khách có cơ hội ngắm cảnh hùng vĩ của rừng nguyên sinh Quốc gia Yok Đôn, tham quan cầu treo và thưởng ngoạn cảnh sông Sê Rê Pốk, nhà sàn cổ 120 năm của người Lào, tìm hiểu truyền thuyết Vua săn voi, thăm buôn làng của người dân tộc Êđê, M’Nông, Khmer…
- Chùa Sắc Tứ Khải Đoan (ngôi chùa cuối cùng ở Việt Nam được phong sắc tứ của chế độ phong kiến): ngôi chùa cổ được thiết kế theo lối kiến trúc nhà rường Huế kết hợp hài hòa với phong cách hiện đại.
Buổi chiều, Quý khách tham quan:
- Đường Sách Buôn Ma Thuột: có 23 gian hàng chia thành các khu vực đọc sách, uống cà phê, đồ lưu niệm, bao gồm 20 bức tường bích họa miêu tả về cảnh đẹp, các câu chuyện sử thi, hoạt động sản xuất cùng đời sống văn hóa của người đồng bào Ê Đê.
- KDL Sinh Thái Kotam: tìm hiểu nét đẹp từ những phong tục bản địa của người Êđê giữa mênh mông đại ngàn và thưởng thức những món ăn mang đậm văn hóa Tây Nguyên.
Sau bữa ăn chiều,Quý khách tự do tham quan Quảng Trường 10/3 - Không gian sinh hoạt địa phương nhộn nhịp của người dân phố núi vào mỗi tối.
Nghỉ đêm tại Buôn Ma Thuột.',7,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (19,N'Ngày 3 - BUÔN MA THUỘT - BẢO TÀNG CÀ PHÊ THẾ GIỚI - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Sau bữa sáng, Qúy khách trả phòng khách sạn. Trên đường về Quý Khách ghé tham quan: 
- Bảo Tàng Cà Phê Thế Giới: hiện vật trưng bày trong bảo tàng được sưu tầm từ khắp nơi trên thế giới. Đặc biệt trong đó là hơn 10.000 vật dụng liên quan đến cà phê qua nhiều thời kỳ lịch sử và văn hoá trên thế giới của Bảo tàng cà phê thế giới Jens Burg, một trong những điểm du lịch đặc sắc của thành phố Hamburg (Đức). Bảo tàng được xây dựng nương theo kiến trúc không gian nhà dài quen thuộc đặc trưng của vùng đất Tây Nguyên linh thiêng, những đường cong đa hình và uyển chuyển được giao thoa với nhau tạo nên hình khối kiến trúc đặc biệt (vé vào bên trong bảo tàng - chi phí tự túc).
Xe đưa Quý khách khởi hành về lại TP. Hồ Chí Minh. Ăn trưa trên cung đường về. Chia tay Quý khách và kết thúc chương trình du lịch.',7,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (20,N'Ngày 1 - HÀ NỘI – LÀO CAI – Ô QUY HỒ (Ăn trưa, tối)',N'05h00  Xe và hướng dẫn viên đón đoàn tại Vietravel số 03 Hai Bà Trưng, Hoàn Kiếm, khởi hành đi Sapa.
Trên đường đi quý khách tham quan:
• Đền ông Hoàng Bảy - Đền Ông Hoàng Bảy hay còn gọi là đền Bảo Hà là ngôi đền linh thiêng gắn với truyền thuyết về Ông Hoàng Bảy. Nơi đây đang dần trở thành một địa điểm du lịch tâm linh nổi tiếng nằm trên con đường đến với Sa Pa, Lào Cai. Nơi đây được rất nhiều người biết đến bởi sự linh thiêng kỳ lạ
Quý khách ăn trưa, tiếp tục di chuyển đến Sapa, quý khách tham quan:
• Đỉnh đèo Ô Quy Hồ: một trong “Tứ đại đỉnh đèo” phía Bắc (không bao gồm vé tham quan Thác Bạc, Cầu Kính rồng mây, Cổng Trời...) 
• Núi Hàm Rồng: ngắm cảnh và chụp ảnh thị trấn Sapa trong sương, thăm vườn Lan 1-2 khoe sắc, vườn Lê, vườn Táo Mèo, Vườn hoa Trung Tâm, Hòn Đá Gãy, Cổng Trời, Đầu Rồng, Hòn Cá Sấu, Khu Thiên Thách Lâm, Hòn Phật Bà, Sân Mây, Tháp truyền hình... 
Ăn tối tại nhà hàng. Sau đó, quý khách tự do thăm quan:
• Nhà Thờ Đá Sapa - dấu ấn kiến trúc của người Pháp còn lại vẹn toàn nhất tại Sa Pa, tự do thưởng thức đặc sản vùng cao như: thịt lợn cấp nách nướng, trứng nướng, rượu táo mèo, giao lưu với người dân tộc Mông, Dao…
Nghỉ đêm tại Sa Pa.',8,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (21,N'Ngày 2 - SAPA –FANSIPAN HÙNG VĨ – BẢN CÁT CÁT (Ăn sáng)',N'Sáng: đoàn ăn sáng tại khách sạn. Sau đó:
• 07:00 Đoàn đến tham quan khu du lịch Fansipan Legend – tại đây quý khách có cơ hội trải nghiệm Tàu hỏa leo núi Mường Hoa (chi phí tự túc) hiện đại nhất Việt Nam với tổng chiều dài gần 2000m, thưởng ngoạn bức tranh phong cảnh đầy màu sắc của cánh rừng nguyên sinh, thung lũng Mường Hoa. Chinh phục đỉnh núi Fansipan với độ cao 3.143m hùng vĩ bằng cáp treo (chi phí cáp treo tự túc). 
• Lễ Phật tại chùa Trình hay cầu phúc lộc, bình an cho gia đình tại Bích Vân Thiền Tự trong hệ thống cảnh quan tâm linh trên đỉnh Fansipan. 
Đoàn ăn trưa tại nhà hàng, chiều tham quan :
• Bản Cát Cát - đường vào bản quanh co một bên là thung lũng Mường Hoa với những thửa ruộng bậc thang màu mỡ được tổ điểm bởi màu xanh của ngô và lúa, thăm những nếp nhà của người Mông, Dao, Giáy, Quý khách sẽ không khỏi ngỡ ngàng trước vẻ đẹp bình dị, mộc mạc.  
Ăn tối tại nhà hàng. Tự do tham quan Sapa về đêm, Quý khách có thể thưởng thức khoai nướng, trứng nướng – mang đậm tính đặc sắc vùng miền.
Nghỉ đêm tại Sa Pa.',8,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (22,N'Ngày 3 - SECRET GARDEN - HÀ NỘI (Ăn sáng, trưa)',N'Sáng, quý khách ăn sáng, trả phòng khách sạn sau đó tham quan : 
• Secret Garden - điểm sống ảo mới toanh giữa lòng phố núi Sapa đang làm mưa làm gió với ‘giường trên không’ và ‘nhà bong bóng’. Hãy nghĩ đến cảnh bạn sẽ được nằm ườn trên chăn bông bồng bềnh, view ‘khách sạn ngàn sao’, có thể nằm ngắm trời ngắm mây, nhìn thẳng ra những dãy núi mờ ảo trong mây. Bể bơi vô cực, xích đu vô cực hay tổ chim cũng là điểm cực ‘chất’ ở Secret Garden . Và chưa hết! Nếu bạn muốn chụp một bộ ảnh với những concept như là vườn địa đàng hay kỳ nghỉ ở Địa Trung Hải, hãy đến ngay sân café ở đây, khoảng sân này được thiết kế cực kỳ tỉ mỉ, khiến bạn sẽ cực kỳ lúng túng không biết nên chụp góc nào đẹp nhất bởi góc nào cũng đều đẹp.
Trưa: đoàn ăn trưa tại nhà hàng. 
Sau đó, Quý khách lên xe trở về Hà Nội. 
Về đến Hà Nội, chia tay Quý khách. Kết thúc chương trình.',8,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (23,N'Ngày 1 - TP. HCM - ĐÀ NẴNG - SUỐI KHOÁNG NÓNG THẦN TÀI (Ăn trưa, tối)',N'Quý khách tập trung tại sân bay Tân Sơn Nhất, hướng dẫn viên Vietravel hỗ trợ đoàn làm thủ tục đón chuyến bay đi Đà Nẵng. Đến nơi, đoàn khởi hành tham quan:

Bảo tàng Điêu Khắc Chăm: nơi đang lưu trữ và trưng bày hơn 400 tác phẩm và 2000 hiện vật đại diện cho hầu hết các phong cách nghệ thuật điêu khắc đã hình thành trong lịch sử phát triển rực rỡ của vương quốc Champa.
Về khách sạn nghỉ ngơi, chiều đoàn tiếp tục tham quan Suối khoáng nóng Thần Tài:

Thư giãn tại Huyệt Long Hồ: tọa lạc ngay giữa đồi Thanh Long – Bạch Hổ là một hồ nước được bao bọc bởi những khối đá tự nhiên. Ngay chính giữa hồ có một hòn đá nguyên khối chính là huyệt của rồng (long huyệt), là nơi xuất lộ khí,  là nơi khởi nguồn cho tất cả những gì tinh túy nhất, cũng chính là nơi mà nguồn nước được phát lộ.
Khu công viên nước: gồm hồ tạo sóng, khu tổ chim, đường trượt cầu vòng,...
Ngoài ra tham quan tượng Phật Di Lặc, Động Long Tiên, Thiềm Thừ Tài Lộc, tản bộ trên con đường ven suối xanh mát, dạo bước trên con đường hoa lan đầy sắc màu hay thử thách trong các trò chơi gam thực tế ảo.
Buổi tối, Quý khách tự do khám phá thành phố về đêm như khám phá những cây cầu biểu tưởng của thành phố Đà Nẵng (cầu Rồng, cầu Sông Hàn), cầu tình yêu.... Nghỉ đêm tại Đà Nẵng.',22,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (24,N'Ngày 2 - ĐÀ NẴNG - HỘI AN - RỪNG DỪA BẢY MẪU (Ăn sáng, trưa, tối)',N'Xe đưa đoàn khởi hành đi Hội An, tham quan:
Rừng Dừa Bảy Mẫu: trên những chiếc thuyền thúng len lỏi vào những con kênh nhỏ trong rừng dừa, tìm hiểu về rừng dừa Bảy Mẫu. Quý khách được tận mắt xem người dân địa phương quăng chài trên sông để đánh bắt cá và có thể tự tay trải nghiệm kỹ năng quăng chài lưới của mình. Sau đó, thưởng thức màn lắc thúng chém nước điêu luyện của nghệ nhân.
Buổi chiều, đoàn tham quan:
Phố cổ Hội An: tham quan chùa Cầu, nhà cổ Phùng Hưng, hội quán Phước Kiến, cơ sở Thủ Công Mỹ Nghệ… Tự do dạo phố đèn lồng nhiều màu sắc và mua những món quà lưu niệm của địa phương, thử tài trong các trò chơi dân gian như đập niêu, hát bài chòi.
Xe đưa đoàn về lại thành phố Đà Nẵng nghỉ đêm.',22,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (25,N'Ngày 3 - ĐÀ NẴNG - NGŨ HÀNH SƠN - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Sau khi dùng bữa sáng tại khách sạn, xe đưa đoàn tham quan:

Danh thắng Ngũ Hành Sơn: tham quan chùa Linh Ứng, động Tàng Chơn, động Hoa Nghiêm, chùa Non Nước, làng đá Mỹ Nghệ Non Nước.
Dùng bữa trưa, xe đưa đoàn ra sân bay Đà Nẵng đón chuyến bay về sân bay Tân Sơn Nhất. Chia tay đoàn và kết thúc chương trình du lịch.',22,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (26,N'Ngày 1 - TP. HỒ CHÍ MINH - HUẾ Số bữa ăn: 02 (Ăn trưa, chiều)',N'Quý khách tập trung tại Ga đi trong nước, sân bay Tân Sơn Nhất, hướng dẫn viên Vietravel hỗ trợ làm thủ tục cho đoàn đáp chuyến bay đi Huế. Tại sân bay Phú Bài - Huế, xe và hướng dẫn viên đón đoàn đi tham quan:  
- Đại Nội: hoàng cung xưa của 13 vị vua triều Nguyễn, tham quan Ngọ Môn, Điện Thái Hòa, Tử Cấm Thành, Thế Miếu, Hiển Lâm Các, Cửu Đình, Bảo tàng Cổ Vật cung đình Huế. 
- Chùa Thiên Mụ: ngôi chùa được xem là cổ nhất ở Huế và là nơi lưu giữ nhiều cổ vật quý giá không chỉ về mặt lịch sử mà còn cả về nghệ thuật
- Lăng Tự Đức: nơi có phong cảnh sơn thủy hữu tình và được cho là một trong những lăng tẩm có kiến trúc đẹp nhất của các vua chúa nhà Nguyễn 
- Dạo Phố Đêm: chụp hình tại Cầu Đi Bộ Sàn Gỗ Lim trên Sông Hương điểm tham quan mới siêu hót tại Huế, ,cầu Trường Tiền, tranh thêu XQ, tự do thưởng thức các món đường phố xứ Huế, ngắm nhìn thuyền rồng ngược xuôi bên bến Tòa Khâm văng vẳng âm vang điệu hò Huế, khám phá khu phố Tây sôi động về đêm.
Về lại khách sạn, nhận phòng tự do nghỉ ngơi. Nghỉ đêm tại Huế',9,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (27,N'Ngày 2 - HUẾ - ĐỘNG PHONG NHA - ĐỘNG THIÊN ĐƯỜNG Số bữa ăn: 03 (Ăn sáng, trưa, chiều)',N'Ăn sáng tại khách sạn. Trả phòng, Quý khách khởi hành đi Quảng Bình tham quan:
- Động Phong Nha: một nhánh trong quần thề di sản thiên nhiên thế giới, được xem như chốn thần tiên bởi hệ thống núi đá vôi và sông ngầm dài nhất thế giới,…  
- Động Thiên Đường: như là hoàng cung dưới lòng đất, hang động đá vôi đẹp và kỳ ảo, có độ dài kỷ lục,… 
Buổi tối Quý khách tự do nghỉ ngơi, dạo phố khám phá thành phố Đồng Hới về đêm. Nghỉ đêm tại Quảng Bình.',9,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (28,N'Ngày 3 - QUẢNG BÌNH - LA VANG - ĐÀ NẴNG Số bữa ăn: 03 (Ăn sáng, trưa, chiều)',N'Ăn sáng tại khách sạn. Quý khách khởi hành về Đà Nẵng, trên đường đi dừng tham quan:
- Thành cổ Quảng Trị: nghe hướng dẫn viên thuyết minh Khu phi quân sự DMZ và Cầu Hiền Lương Sông Bến Hải (Vĩ tuyến 17)
- Thánh địa La Vang: một trong bốn tiểu vương cung thánh đường La Mã tại Việt Nam
- Đầm Lập An: ngắm cảnh mây bồng bềnh trên những chóp núi bao bọc quanh đầm, trước khi đi xuyên Hầm Hải Vân đến Đà Nẵng 
Buổi tối Quý khách tự túc đi dạo phố thưởng ngoạn cảnh đẹp của Đà Nẵng về đêm, ngắm nhìn Cầu Rồng, Cầu Tình Yêu, Cầu Trần Thị Lý, Trung Tâm Thương Mại, Khu phố ẩm thực, Café - Bar - Disco…. Nghỉ đêm tại Đà Nẵng.',9,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (29,N'Ngày 4 - ĐÀ NẴNG - BÀ NÀ (CẦU VÀNG) - HỘI AN Số bữa ăn: 02 (Ăn sáng, chiều)',N'Ăn sáng tại khách sạn. Xe đưa Quý khách đi tham quan: 
- Khu du lịch Bà Nà Suối Mơ (Chi phí cáp treo & ăn trưa tự túc): trải nghiệm tuyến cáp treo một dây dài nhất thế giới. Lên đỉnh núi, Quý khách tự do tận hưởng không khí se lạnh của “Đà Lạt tại miền Trung’’ tham quan  Chùa Linh Ứng, vườn hoa Le Jardin D’Amour, khu tâm linh mới của Bà Nà viếng Đền Lĩnh Chúa Linh Từ, khu vui chơi Fantasy Park với nhiều trò chơi hấp dẫn, đi dạo chụp hình tại Cầu Vàng điểm tham quan mới siêu hot tại Bà Nà, Ăn trưa tại Bà Nà tự túc. Tự do vui chơi đến giờ xuống cáp.   
- Phố Cổ Hội An: Chùa Cầu, dạo phố đèn lồng đầy màu sắc, cảm nhận sự yên bình, cổ kính, lãng mạn phố cổ, tự do thả hoa đăng cầu bình an trên Sông Hoài,… 
Đoàn về lại Đà Nẵng, nhận phòng tự do nghỉ ngơi. Nghỉ đêm tại Đà Nẵng',9,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (30,N'Ngày 5 - ĐÀ NẴNG - SƠN TRÀ - TP.HỒ CHÍ MINH Số bữa ăn: 02 (Ăn sáng, trưa)',N'Ăn sáng tại khách sạn. Xe đưa Quý khách đi tham quan: 
- Bãi biển Mỹ Khê: một trong những bãi biển quyến rũ nhất hành tinh, Quý khách tự do dạo biển, chụp hình. 
- Bán Đảo Sơn Trà – viếng Chùa Linh Ứng: nơi đây có tượng Phật Quan Thế Âm cao nhất Việt Nam. Đứng nơi đây, Quý khách sẽ được chiêm ngưỡng toàn cảnh thành phố, núi rừng và biển đảo Sơn Trà một cách hoàn hảo nhất
- Ngũ Hành Sơn: Động Tàng Chơn, Động Hoa Nghiêm, Chùa Non Nước, Làng Đá Mỹ Nghệ Non Nước,..
Xe đưa đoàn ra sân bay Đà Nẵng đón chuyến bay trở về Tp.Hồ Chí Minh. Chia tay đoàn và kết thúc chương trình du lịch tại sân bay Tân Sơn Nhất.',9,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (31,N'Ngày 1 - TP. HỒ CHÍ MINH - PHÚ QUỐC - GRAND WORLD (Ăn chiều)',N'Quý khách tập trung tại Sân bay Tân Sơn Nhất, ga đi Trong Nước. Hướng dẫn viên làm thủ tục cho Quý khách chuyến bay chiều phút đi Phú Quốc. Xe đón đoàn tại sân bay đưa về khách sạn nhận phòng nghỉ ngơi tại Vinholidays - Chuỗi khách sạn thông minh được thiết kế hiện đại, dịch vụ linh hoạt cùng các tiện nghi quốc tế. Buổi tối, Quý khách có thể tự do khám phá một số hạng mục tại khu Grand World như: công trình tre, công viên nghệ thuật đương đại thuộc Open Park…; tản bộ bên dòng “kênh đào Venice” và nhìn ngắm những chiếc thuyền Gondola, khu phố shophouse lộng lẫy sắc màu, cổng lâu đài tráng lệ, ba cây cầu vòm bán nguyệt...; Thưởng thức bữa tiệc ánh sáng đỉnh cao với show “Sắc màu Venice”
(show diễn dự kiến diễn ra từ ngày 12/11/2021, có thể thay đổi tùy theo tình hình thực tế)
Nghỉ đêm tại Vinholidays.',11,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (32,N'Ngày 2 - VƯỜN THÚ BÁN HOANG DÃ VINPEARL SAFARI - VUI CHƠI TẠI CÔNG VIÊN VINWONDERS',N'Sau khi dùng bữa sáng tại VinHolidays, Quý khách sẽ đến khám phá Vinpearl Safari Phú Quốc - Vườn thú hoang dã đầu tiên tại Việt Nam: với quy mô 180 ha, hơn 130 loài động vật quý hiếm, du khách thưởng thức các chương trình biểu diễn, chụp ảnh với động vật; trải nghiệm vườn thú mở trong rừng tự nhiên, gần gũi và thân thiện với con người.
Buổi chiều, Quý khách tham quan Khu vui chơi giải trí VinWonders - du khách sẽ lạc vào “Thế Giới Cổ Tích” thông qua những trò chơi tương tác lần đầu tiên xuất hiện tại Việt Nam với 6 chủ đề khác nhau: Khu Cảm Giác Mạnh - Thế Giới Phiêu Lưu; Khu Công Viên Nước - Thế Giới Lốc Xoáy; Khu Lâu Đài Trung Tâm - Châu Âu Trung Cổ; Khu Cổ Tích - Thế Giới Diệu Kỳ; Khu Viking - Ngôi Làng Bí Mật.
Nghỉ đêm tại VinHolidays.',11,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (33,N'Ngày 3 - PHÚ QUỐC - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Dùng buffet sáng tại VinHolidays. Đoàn trả phòng khách sạn.  Quý khách khởi hành đi tham quan:
- Dinh Cậu: biểu tượng văn hoá và tín ngưỡng của đảo Phú Quốc. Nơi ngư dân địa phương gởi gắm niềm tin cho một chuyến ra khơi đánh bắt đầy ắp cá khi trở về. Sau đó, đoàn viếng Dinh Bà Thủy Long Thánh Mẫu - Thờ Thần Nữ Kim Giao, người phụ nữ được người dân Phú Quốc rất mực tôn kính vì có công khai phá huyện đảo này.
- Tham quan và mua sắm đặc sản nổi tiếng tại Vườn Tiêu; Nhà thùng nước mắm; Đặc sản khô; Cơ sở sản xuất ngọc trai Phú Quốc - trưng bày các trang sức bằng ngọc trai chính hiệu được nuôi cấy tại Phú Quốc. 
Sau khi tham quan, xe đưa Quý khách đi ăn trưa và ra sân bay Phú Quốc đáp chuyến bay trở về TP. Hồ Chí Minh. Chia tay Quý khách và kết thúc chương trình du lịch tại sân bay Tân Sơn Nhất.',11,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (34,N'Ngày 1 - ĐÀ NẴNG - VINPEARL LAND NAM HỘI AN',N'7:00 Xe của Vietravel sẽ tới các điểm hẹn để đón Quý khách và bắt đầu hành trình vi vu Vinpearl Land Nam Hội An, cách trung tâm thành phố Đà Nẵng khoảng 45 km
Xe đến cổng khu vui chơi VinPearl Land Nam Hội An. Trong lúc đợi làm thủ tục qua trạm soát vé. Quý khách có thời gian để check-in “sống ảo” bên ngoài cổng.    
Sau khi qua trạm soát vé quý khách sẽ tham quan các điểm sau:
Bến cảng giao thoa: Bến cảng lấy cảm hứng từ thương cảng Hội An, nằm uốn lượng theo con sông. Tại đây, quý khách sẽ được chiêm ngưỡng dãy kiến trúc cổ đậm chất Việt Nam
Đại lộ giấc mơ: Tổ hợp các công trình mang phong cách kiến trúc phương Tây hiện đại. Những ngôi nhà đầy màu sắc, đổ mái đúng chất “dancing house”những ngôi nhà biết nhảy múa.
Mô hình thành Cổ Loa: Công trình sừng sững như một chứng minh lịch sử dân tộc
Quý khách tiếp tục hành trình tour Vinpearl Land 1 ngày với việc ngồi trên thuyền suôi theo dòng nước tham quan vườn thú River Safari và chiêm ngưỡng đời sống của các loài động vật hoang dã cùng các loài cá thể hai bên bờ như ngựa vằn, huowu cao, cổ, tê giác, linh dương…
Đoàn tiếp tục di chuyển đến Đảo dân gian – nơi tái hiện nét văn hóa của cả 3 miền Bắc – Trung – Nam. Khám phá làng nghề work shop thủ công như: Làng gốm Bát Tràng, nghề làm lồng đèn Hội An, làng quạt Chàng Sơn…
Quý khách đến sân khấu Thủy Đình thưởng thức hoạt động văn nghệ dân gian như múa rối nước hay dân ca quan họ tại Đình Làng Việt.
Khởi đầu hành trình buổi chiều, đoàn tham quan các địa điểm còn lại của Vinpearl Land Nam Hội An như Đồi Ước nguyện và tham quan Khu vui chơi trong nhà – nơi được biết đến như thế giới trò chơi đa dạng và sôi động. Tại đây có thể tham gia các trò chơi điện tử hay dạo chơi vườn cổ tích, xem phim 5D.
Tiếp tục, Quý khách sẽ được thỏa sức trải nghiệm các trò chơi cảm giác mạnh hàng đầu thế giới như: Tàu lượn siêu tốc, máng trượt vạn hoa, vòng đua tốc độ, cú rơi thế kỷ, cây day văng… đảm bảo quý khách sẽ có những phút giây thư giãn thật thoải mái.
Sau khi thỏa thích tại Khu vui chơi trong nhà, được thử sức với các trò chơi cảm giác mạnh. Quý khách có thể ghé Công viên nước để hòa mình vào dòng nước mát lành, giải trí bên sông Lười, đường trượt phao gia đình, bể bơi trẻ em…
16:30 Quý khách tập trung ra cổng lên xe về lại Đà Nẵng. Kết thúc tour VinPearl Land Nam Hội An. Xe đưa quý khách về điểm đón ban đầu, chào tạm biệt và hẹn gặp lại.',12,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (35,N'Ngày 1 - TP.HỒ CHÍ MINH – SÂN BAY NỘI BÀI (HÀ NỘI) – NINH BÌNH (Ăn trưa, tối)',N'Quý khách tập trung tại sân bay Tân Sơn Nhất (ga nội địa), hướng dẫn viên hỗ trợ khách làm thủ tục đáp chuyến bay đi Hà Nội. Đến nơi, đoàn khởi hành đi Ninh Bình, đến nơi tham quan:
- Chùa Bái Đính: là một quần thể chùa với nhiều kỷ lục Việt Nam như chùa có diện tích lớn nhất, tượng phật bằng đồng lớn nhất, nhiều tượng Phật La Hán nhất.
Nghỉ đêm tại Ninh Bình.',13,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (36,N'Ngày 2 - NINH BÌNH – HÀ NỘI (Ăn sáng, trưa, tối)',N'Ăn sáng, Xe đưa quý khách tham quan:
- Khu Du Lịch Tràng An: du khách lên thuyền truyền thống đi tham quan thắng cảnh hệ thống núi đá vôi hùng vĩ và các thung lũng ngập nước, thông với nhau bởi các dòng suối tạo nên các hang động ngập nước quanh năm. Điểm xuyến trong khhông gian hoang sơ, tĩnh lặng là hình ảnh rêu phong, cổ kính của các mái đình, đền, phủ nằm nép mình dưới chân các dãy núi cao.
Đoàn khởi hành về Hà Nội, tham quan: 
- Văn Miếu: nơi thờ Khổng Tử và các bậc hiền triết của Nho Giáo, Quốc Tử Giám - trường đại học đầu tiên của Việt Nam, tìm về cội nguồn lịch sử của các vị Nho học.
- Xe đưa quý khách dạo 1 vòng Hồ Hoàn Kiếm ngắm Tháp Rùa, Đền Ngọc Sơn, Cầu Thê Húc.
Nghỉ đêm tại Hà Nội.',13,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (37,N'Ngày 3 - HÀ NỘI – HẠ LONG (Ăn sáng, trưa, tối)',N'Ăn sáng và trả phòng khách sạn. Quý khách khởi hành đi tham quan: 
- Lăng Hồ Chủ Tịch (không viếng vào thứ 2, thứ 6 hàng tuần và giai đoạn bảo trì định kì hàng năm 15/6 – 15/8): tham quan và tìm hiểu cuộc đời và sự nghiệp của vị cha già dân tộc tại Nhà Sàn Bác Hồ, Bảo Tàng Hồ Chí Minh, Chùa Một Cột. 
Đoàn khởi hành đi Hạ Long theo đường cao tốc, đến bến tàu xuống thuyền tham quan:
- Vịnh Hạ Long: thắng cảnh thiên nhiên tuyệt đẹp và vô cùng sống động, được UNESCO công nhận là di sản thiên nhiên Thế giới năm 1994. 
- Động Thiên Cung: chốn thiên đường dưới hạ giới bởi vẻ đẹp lộng lẫy bởi những lớp thạch nhũ và những luồng ánh sáng lung linh.
- Từ trên tàu ngắm nhìn các hòn đảo lớn nhỏ trong Vịnh Hạ Long: Hòn Gà Chọi, Hòn Lư Hương... 
Nghỉ đêm tại Hạ Long.',13,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (38,N'Ngày 4 - HẠ LONG – YÊN TỬ – SÂN BAY NỘI BÀI – TP.HỒ CHÍ MINH (Ăn sáng, trưa)',N'Quý khách tham quan:
- Cầu Bãi Cháy: kiến trúc thanh mảnh, hiện đại, cầu Bãi Cháy đem lại một nét kiến trúc mới và lãng mạn góp phần tô điểm và tôn thêm vẻ đẹp cho Vịnh Hạ Long.
- Bảo tàng Quảng Ninh (tham quan và chụp ảnh bên ngoài bảo tàng): một trong những điểm tham quan ấn tượng trong du khách với lối kiến trúc độc đáo và những hiện vật được trưng bày trong đây.
- Núi Yên Tử: thắng cảnh thiên nhiên còn lưu giữ nhiều di tích lịch sử với mệnh danh “đất tổ Phật giáo Việt Nam”. Tham quan Chùa Hoa Yên (chi phí cáp treo tự túc), Tháp Tổ.  
Khởi hành về Hà Nội, trên đường ghé Hải Dương mua đặc sản: bánh đậu xanh, bánh khảo.                          Xe đưa đoàn ra sân bay Nội Bài đáp chuyến bay về Tp.Hồ Chí Minh. Chia tay Quý khách và kết thúc chương trình du lịch tại sân bay Tân Sơn Nhất. ',13,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (39,N'Ngày 1 - QUY NHƠN – NỘI BÀI (HÀ NỘI) – SAPA (Ăn trưa, tối)',N'Quý khách tập trung tại sân bay Phù Cát. Hướng dẫn viên làm thủ tục cho đoàn đáp chuyến bay đi Hà Nội.
Xe Vietravel đón đoàn tại sân bay Nội Bài, khởi hành đi Sa Pa theo cung đường cao tốc hiện đại và dài nhất Việt Nam. Trên đường đi, Đoàn tham quan:
- Cửa khẩu biên giới Việt - Trung “Lào Cai- Hà Khẩu”
- Mua sắm tại chợ Cốc Lếu - Trung tâm thương mại lớn nhất, của thành phố nói riêng và Tỉnh Lào Cai nói chung. Nơi đây bày bán đa dạng đủ các loại mặt hàng từ thủ công mỹ nghệ, tranh nghệ thuật phong cảnh đến quần áo…sẽ là một điểm mua sắm tuyệt vời với du khách.
Đến Sapa, Quý khách nhận phòng và ăn tối.
Buổi tối Quý khách tự do dạo phố, ngắm nhà thờ Đá Sapa, tự do thưởng thức đặc sản vùng cao như: thịt lợn cắp nách nướng, trứng nướng, rượu táo mèo, giao lưu với người dân tộc vùng cao. 
Nghỉ đêm tại Sapa.',14,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (40,N'Ngày 2 - SA PA – FANSIPAN HÙNG VĨ (Ăn sáng, trưa, tối)',N'Trả phòng khách sạn, Quý khách tham quan:
- Bản Cát Cát đẹp như một bức tranh giữa vùng phố cổ Sapa, nơi đây thu hút du khách bởi cầu treo, thác nước, guồng nước và những mảng màu hoa mê hoặc du khách khi lạc bước đến đây.
- Thăm những nếp nhà của người Mông, Dao, Giáy trong bản, du khách sẽ không khỏi ngỡ ngàng trước vẻ đẹp bình dị, mộc mạc. 
Xe đưa đoàn ra ga Sapa, Quý khách trải nghiệm đến khu du lịch Fansipan Legend bằng Tàu hỏa leo núi Mường Hoa hiện đại nhất Việt Nam với tổng chiều dài gần 2000m, thưởng ngoạn bức tranh phong cảnh đầy màu sắc của cánh rừng nguyên sinh, thung lũng Mường Hoa.
- Tham quan tiểu cảnh Vườn tre, Chiêm bái chùa Trình – Bảo An Thiền Tư hoặc tự do mua sắm…
- Chinh phục đỉnh núi Fansipan với độ cao 3.143m hùng vĩ bằng cáp treo và cầu phúc lộc, bình an cho gia đình tại Bích Vân Thiền Tự hay tự thưởng cho mình ly ca cao nóng tại Café Du Soleil – Quán cà phê cao nhất Đông Dương.
- Sau đó, Quý khách tham quan Núi Hàm Rồng thăm vườn Lan khoe sắc, vườn hoa Trung Tâm, ngắm Núi Phanxipăng hùng vĩ, Cổng Trời, Đầu Rồng Thạch Lâm, Sân Mây. 
Nghỉ đêm tại Sapa.',14,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (41,N'Ngày 1 - CITY TOUR ĐÀ LẠT (Ăn trưa, tối)',N'Xe và hướng dẫn viên đón khách tại điểm hẹn, khởi hành tham quan:

Kim Ngân Hill: nổi bật với vườn thú kết hợp với không gian cà phê view rừng thông ấn tượng, xen kẻ là vườn hoa Xác Pháo, Thạch Thảo, Cẩm Tú Cầu để du khách thỏa thích check-in. Vườn thú Kim Ngân Hills đang nuôi giữ và bảo tồn nhiều loài động vật độc lạ như lạc đà Alpaca, hưu sao, ngựa Ponny, cừu Valis, chó Corgi... Ngoài ra, nơi đây còn sở hữu khu vực nuôi chim lớn nhất tỉnh Lâm Đồng với nhiều loài chim như yến phụng, sáo, cu đất, két.... 
Ăn trưa. Quý khách tiếp tục hành trình tham quan:

Fairytale Land: đến đây du khách như lạc vào khu vườn cổ tích của những chú lùn Hobbiton, điểm xuyến trong khu vườn là những ngôi nhà độc đáo và đầy sắc màu, những bức vẽ trên tường đầy lôi cuốn và những thảm hoa nhỏ không kém phần quyến rũ. Dạo một vòng quanh khu vườn đừng quên rảo bước vào hầm rượu vang với hơn 10.000 chai đang được lưu trữ dưới hầm.
Ăn tối, Quý khách về lại khách sạn nghỉ ngơi, tự do tham quan.
Nghỉ đêm tại Đà Lạt.',15,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (42,N'Ngày 2 - THAM GIA CHƯƠNG TRÌNH “RUN WITH KIDS” (Ăn sáng, trưa, tối)',N'Quý khách ăn sáng, sau đó di chuyển đến khu cắm trại, bắt đầu chương trình trải nghiệm “Run with Kids”.
Với mục đích:

Tạo ra môi trường trải nghiệm thú vị cho các bé cùng gia đình thông qua các hoạt động như: dựng lều, chuẩn bị một số món ăn nhẹ, tham gia trò chơi, vượt qua các thử thách,…
Giúp bé có thêm các bạn mới, tự tin giao tiếp.
Tạo mối quan hệ gắn kết hơn giữa các thành viên.
Sinh hoạt, vui chơi trong một trường thân thiện, an toàn.
Ăn tối. Quý khách về khách sạn nghỉ ngơi.
Nghỉ đêm tại Đà Lạt.',15,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (43,N'Ngày 3 - THAM QUAN VƯỜN NÔNG SẢN ĐÀ LẠT (Ăn sáng, trưa)',N'Sáng: Quý khách ăn sáng, trả phòng khách sạn sau đó di chuyển tham quan:

Vườn Nông sản: tại đây Quý khách sẽ được tham quan vườn rau củ theo mùa, với diện tích rộng và đa dạng các loại rau, củ, quả,… sẽ mang đến cho gia đình cùng các bé có những trải nghiệm thú vị, các bé có thể thoải mái khám phá và tìm hiểu nhiều hơn về các loại rau củ, cách canh tác và trồng các loại cây một cách thực tế.
Ăn trưa. Sau đó, Quý khách sẽ tiếp tục đến tham quan, tìm hiểu về lịch sử, kiến trúc độc đáo tại:

Dinh I: từng là tổng hành dinh của vua Bảo Đại mang kiến trúc Pháp cổ điển, nổi bật với lối vào là con đường lát đá với hàng tràm cao vút đan vào nhau, hệ thống vườn ngự uyển, đài phun nước kết hợp hài hòa và rừng thông xanh mát reo trong gió. Tất cả tạo nên một tổng thể sang trọng và cổ kính.
Xe và hướng dẫn viên đưa Quý khách đến Quảng trường Lâm Viên, chụp hình lưu niệm với biểu tượng của Đà Lạt: Bông Atiso và Hoa Dã Quỳ.
Kết thúc chương trình, chia tay Quý khách và hẹn gặp lại ở những hành trình sau.',15,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (44,N'Ngày 1 - TP. HỒ CHÍ MINH - ĐÀ LẠT - QUÊ GARDEN (Ăn sáng, trưa, tối)',N'Quý khách tập trung tại Vietravel (190 Pasteur, phường Võ Thị Sáu, quận 3, TP.HCM), xe đưa đoàn khởi hành đi Đà Lạt. Đến nơi, đoàn tham quan:
•    Quê Garden: nằm dưới chân đèo Mimosa, khu vườn mê mẩn người xem với cánh đồng hoa rộng lớn cùng nhiều loài hoa nổi tiếng tại Đà Lạt khoe sắc. Đặc biệt Quê Garden tự hào là khu vườn bonsai lá kim lớn nhất Việt Nam, những chậu cây bonsai vừa to vừa đẹp, được chăm sóc và uốn nắn kỹ lưỡng vô cùng đẹp mắt. Ngoài ra, hồ cá koi với view nhìn ra đồi thông là điểm check in cực chất mà du khách không thể bỏ qua.
Đoàn nhận phòng nghỉ ngơi. Buổi tối, Quý khách tự do thưởng thức café ngắm hồ Xuân Hương về đêm. Nghỉ đêm tại Đà Lạt.',16,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (45,N'Ngày 2 - ĐÀ LẠT - KIM NGÂN HILLS - THÁC DATANLA (Ăn sáng, trưa, tối)',N'Đà Lạt chào đón du khách với du khách với không khí lạnh tràn về, sau khi dùng bữa sáng, xe đưa đoàn tham quan:
•    Kim Ngân Hills: nổi bật với vườn thú kết hợp với không gian cà phê view rừng thông ấn tượng, xen kẻ là vườn hoa Xác Pháo, Thạch Thảo, Cẩm Tú Cầu để du khách thỏa thích check-in. Vườn thú Kim Ngân Hills đang nuôi giữ và bảo tồn nhiều loài động vật độc lạ như lạc đà Alpaca, hươu sao, ngựa Ponny, cừu Valis, chó Corgi... Ngoài ra, nơi đây còn sở hữu khu vực nuôi chim lớn nhất tỉnh Lâm Đồng với nhiều loài chim như yến phụng, sáo, cu đất, két.... Tiếp tục, du khách khám phá khu “Ký ức thời bao cấp” gợi cảm xúc hoài niệm về một thời quá khứ.
•    Thác Datanla: chinh phục thử thách với dịch vụ máng trượt ống để tham quan thác nước luôn hấp dẫn du khách bởi vẻ hoang sơ quyến rũ của thiên nhiên và cảnh sắc nơi đây (chi phí mướng trượt tự túc).
Buổi tối Quý khách tự do tản bộ tham quan chợ đêm Đà Lạt về đêm. Nghỉ đêm tại Đà Lạt',16,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (46,N'Ngày 3 - ĐÀ LẠT - PHÂN VIỆN SINH HỌC - CÁNH ĐỒNG HOA CẨM TÚ CẦU (Ăn sáng, trưa, ăn tối tự túc)',N'Sau khi dùng bữa sáng tại khách sạn, xe đưa đoàn tham quan:
•    Phân Viện Sinh Học: được biết đến như 1 tọa độ sống ảo mới tại Đà Lạt với góc ảnh bên những bức tường cổ kính. Không chỉ vậy, đến đây, du khách còn được tìm hiểu về môi trường sống, những nguồn gen quý qua hiện vật vô cùng phong phú, khu trưng bày xương, sừng, mô hình của các loài động vật quý hiếm như khủng long, voi, hổ, gấu, vượn, … hay tìm hiểu về sự vận hành của vũ trụ.
•    Gallery Chocolate: với nhiều không gian trưng bày được thiết kế bởi màu sắc, tạo hình và hương vị đặc biệt từ nguồn nguyên liệu của Chocotea. Ấn tượng nhất là không gian nghệ thuật với 9 tác phẩm được tạo tác công phu từ chocolate như: “Bàn tiệc hoàng gia”, Cây Mai Anh Đào hay tác phẩm “Suối Hoa” tuyệt mỹ, … Du khách dùng thử chocolate để cảm nhận hương vị khác biệt từ hương đến sắc.
•    Cánh đồng hoa cẩm tú cầu: ghi điểm trong mắt du khách nhờ vào khung cảnh thơ mộng xung quanh. Xa xa là rừng thông thơ mộng, còn ở dưới chân cầu vàng là cả cánh đồng rộng lớn được phủ kín bởi sắc trắng phớt xanh của hoa cẩm tú cầu, nổi bần bật trên nền xanh thẫm của lá.
Buổi tối Quý khách tự do tản bộ thưởng thức sữa đậu nành và bánh tráng nướng tại khu phố Tăng Bạt Hổ. Nghỉ đêm tại Đà Lạt',16,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (47,N'Ngày 4 - ĐÀ LẠT - MÊ LINH COFFEE - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Sau khi dùng bữa sáng tại khách sạn, xe đưa đoàn tham quan:
•    Mê Linh Coffee: được thiết kế với không gian mở có một tầm nhìn trọn vẹn 360 độ hướng rẫy cà phê bạt ngàn... Đặc biệt, thưởng thức một ly cà phê chồn nguyên chất với hương vị đậm đà đặc trưng của chất xạ hương (chi phí tự túc). Ngoài ra, Quý khách có thể check in những phiên bản thu nhỏ nhưng không kém phần sống động của những công trình nổi tiếng như cổng trời Bali, cổng trời Linh Quy Pháp Ấn, nấc thang lên thiên đường, tổ chim...
Đoàn dừng tại Bảo Lộc dùng cơm trưa, sau đó khởi hành về điểm đón ban đầu. Chia tay đoàn và kết thúc chương trình du lịch.',16,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (48,N'Ngày 1 - TP.HỒ CHÍ MINH - MŨI NÉ - LÂU ĐÀI RƯỢU VANG Số bữa ăn: 03 (Ăn sáng, trưa, tối)',N'Quý khách tập trung tại Công ty Vietravel (190 Pasteur, Phường 6, Quận 3, TP.Hồ Chí Minh) khởi hành đi Phan Thiết. Quý khách ăn sáng trên cung đường đi. Đến Phan Thiết đoàn tham quan:
- Lâu đài Rượu Vang RD: nghe giới thiệu về quy trình sản xuất, đóng chai và thưởng thức một trong những loại rượu vang hảo hạng nổi tiếng thế giới được xuất xứ từ Thung Lũng Napa (Mỹ). 
Nhận phòng tự do nghỉ ngơi, tắm biển hồ bơi tại khách sạn/resort. Buổi chiều xe đưa đoàn đi tham quan:
- Bảo tàng Ngọc Trai Mũi Né: khu trưng bày được giới thiệu một cách nghệ thuật và sang trọng theo từng chủng loại, màu sắc và kích cỡ. Từ các bộ sưu tập ngọc trai để nguyên cho đến các loại trang sức đính ngọc trai như: vòng đeo cổ, mặt dây chuyền, nhẫn, bông tai, lắc, đồng hồ đính ngọc trai….
Sau khi ăn tối, Quý khách tự do dạo phố khám phá Mũi Né về đêm,…Nghỉ đêm tại Mũi Né.',17,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (49,N'Ngày 2 - MŨI NÉ - BÀU TRẮNG Số bữa ăn:02 (Ăn sáng, trưa)',N'Quý khách dùng bữa sáng tại khách sạn. Sau đó, đoàn đi tham quan:
- Bàu Trắng: hay còn được gọi với cái tên khác Bàu Sen bởi trong hồ khi vào mùa hè sen nở sẽ rất đẹp, phủ kín cả một vùng hồ…. 
Quý khách dùng bữa trưa. Chiều tự do nghỉ ngơi, tắm biển hồ bơi tại khách sạn/resort,…hoặc xe đưa đoàn tham quan:
- Trung tâm Bùn Khoáng Mũi Né: Tận hưởng cảm giác tắm khoáng thư giãn, ngoài ra Quý khách còn có thể tắm bùn hoặc massage là một liệu pháp làm đẹp từ thiên nhiên giúp cải thiện làn da tươi trẻ, mịn màng (chi phí tự túc).
Buổi tối, Quý khách tự do dạo phố ăn chiều tự túc. Nghỉ đêm tại Mũi Né. ',17,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (50,N'Ngày 3 - MŨI NÉ – LÀNG CHÀI XƯA – TP HỒ CHÍ MINH Số bữa ăn:02 (Ăn sáng, trưa)',N'Quý khách dùng bữa sáng và tự do tắm biển. Đến giờ trả phòng, đoàn đi tham quan: 
- Làng Chài Xưa Mũi Né: với lịch sử 300 năm cái nôi của nghề làm nước mắm, trải nghiệm cảm giác lao động trên đồng muối, đi trên con đường rạng xưa, thăm phố cổ Phan Thiết, , thăm nhà lều của hàm hộ nước mắm xưa, đắm chìm cảm xúc trong biển 3D và thích thú khi đi chợ làng chài xưa với bàn tính tay, bàn cân xưa thú vị,…
- Mua sắm đặc sản Phan Thiết sạch tại Organik Farm - Hello Muine (chi phí tự túc) - nước mắm rin nguyên chất, nước mắm Nhật Shiitake, mực một nắng, khô cá dứa, nước cốt thanh long… về làm quà cho người thân và bạn bè. 
Chiều đoàn về đến TP.Hồ Chí Minh, xe đưa về điểm đón ban đầu. Chia tay Quý khách và kết thúc chương trình du lịch.',17,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (51,N'Ngày 1 - Long Xuyên –Vũng Tàu (Ăn ba bữa)',N'Quý khách tập trung tại chi nhánh Long Xuyên (99 – 101 Nguyễn Huệ, Mỹ Long), xe và hướng dẫn viên đón và đưa đoàn đi Vũng Tàu. Đến nơi, Quý khách tham quan: 
 
Tượng chúa Kitô nằm trên đỉnh núi Nhỏ, thành phố Vũng Tàu. Tượng được dựng vào năm 1972, cao 32m, đứng giang hai tay, mặt hướng ra biển là một sự nổi bật hài hòa trong không gian khoáng đạt của vùng núi non và biển cả nơi đây. 
 
Bạch Dinh nằm ở phía nam núi Lớn, trên pháo đài Phước Thắng cổ xưa, cao gần 30m so với mực nước biển. Từ tiền sảnh Bạch Dinh nhìn xuống, du khách sẽ có cảm giác như đang ở tầng lầu của một cao ốc xây dựng trên mặt nước biển, có thể dõi tầm mắt bao quát cả trung tâm thành phố Vũng Tàu.
 
Xe đưa Quý khách đi tắm biển thư giãn sau một tuần làm việc mệt mõi.    
Buổi tối, Quý khách tự do dạo phố biển và thưởng thức hải sản (chi phí tự túc). Nghỉ đêm tại Vũng Tàu. ',18,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (52,N'Ngày 2 - Vũng Tàu – Bà Rịa – Long Xuyên (Ăn sáng, trưa)',N'Ăn sáng tại khách sạn, Xe đưa Quý khách đi tham quan Bến du thuyền Marina: nổi tiếng với cảnh đẹp như trời Tây, quý khách được chiêm ngưỡng cảnh biển trời tươi đẹp bên dòng sông Dinh.
 
Quý khách khởi hành về Long Xuyên, trên đường đi đoàn dừng chân tại Bò Sữa Long Thành - mua sắm những đặc sản cho bản thân và quà tặng người thân, bạn bè
 
Xe đưa đoàn về điểm đến ban đầu, kết thúc chuyến du lịch.',18,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (53,N'Ngày 1 - TP. HỒ CHÍ MINH - BUÔN MA THUỘT - THÁC DRAY NUR (Ăn sáng, trưa, chiều)',N'Quý khách tập trung tại công ty Vietravel (190 Pasteur, Q3, TP.Hồ Chí Minh). Đoàn khởi hành đi Buôn Ma Thuột, theo QL14 qua các địa danh Đồng Xoài, Sóc Bom Bo, Bù Đăng, đường Hồ Chí Minh… Đoàn dừng chân tham quan:
- Thác Dray Nur: một thắng cảnh đẹp nhờ sự kết hợp giữa hai dòng sông Krông Nô và Krông A Na, thác như một bức thành khổng lồ, hùng tráng giữa một vùng hoa nước long lanh.
**Vì lý do cung đường hạn chế di chuyển đối với xe lớn, nếu đoàn đi xe trên 35 chỗ thì sẽ chuyển sang tham quan Thác Dray Sap.
- Thác Dray Sap: cao khoảng 50 m, trải dài gần 100 m xứng đáng là một trong ba ngọn thác đẹp nhất nằm trên thượng nguồn sông Sê Rê Pốk. Theo tiếng Ê Đê, Dray Sap nghĩa là thác khói vì việc lượng hơi nước khổng lồ của dòng chảy bao phủ cả khu vực, khiến ngọn thác càng trở nên bí ẩn, hoang sơ. Đoàn check-in Khu nhà nấm khổng lồ đặc biệt tại thác.
 
Đến TP. Buôn Ma Thuột, đoàn tiếp tục khám phá:
- Bảo tàng thế giới Cà Phê: đây là một tổ hợp bao gồm các không gian trưng bày bảo tàng, không gian triển lãm, không gian thư viện ánh sáng, không gian thưởng lãm cà phê, không gian hội thảo… các không gian này được kết nối với các không gian mang tính mở trong công viên cà phê. Đoàn chụp hình bên ngoài khuôn viên của bảo tàng với những góc chụp ảo diệu (vé vào bên trong bảo tàng - chi phí tự túc).
Buổi tối, Quý khách tự do hòa mình vào nhịp sống của người dân phố núi về đêm như thưởng thức “ly cà phê Ban Mê” trứ danh ngắm phố lên đèn hay ghé chợ đêm Ngã Sáu thưởng thức ẩm thực đường phố như bún đỏ, bún riêu, bún giò, canh cá dằm… hoặc đơn giản là ngồi nhâm nhi ly sữa nóng trong tiết trời se lạnh.
Nghỉ đêm tại Buôn Ma Thuột.',19,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (54,N'Ngày 2 - BUÔN MA THUỘT - BẢN ĐÔN - KDL SUỐI ONG (Ăn sáng, trưa, chiều)',N'Sau bữa sáng, đoàn tham quan;
- Buôn Đôn: Quý khách sẽ có cơ hội ngắm cảnh hùng vĩ của rừng nguyên sinh Quốc gia Yok Đôn, tham quan cầu treo và ngắm cảnh sông Sê Rê Pốk, nhà sàn cổ 120 năm của người Lào, tìm hiểu truyền thuyết Vua săn voi, thăm buôn làng của người dân tộc: Lào, Khơme, Êđê, M’Nông....
- Chùa Khải Đoan: một điểm tham quan du lịch hấp dẫn đồng thời cũng là di tích lịch sử văn hóa của tỉnh Đắk Lắk và thành phố Buôn Mê Thuột.
Ăn trưa, Quý khách nghỉ ngơi. Buổi chiều, Quý khách tham quan:
- KDL Suối Ong: kiến trúc được lấy cảm hứng từ "loài ong" nên các thiết kế từ các ô gạch đến khu trưng bày mật ong đều được tạo dựng từ hình lục giác của tổ ong. Du khách thỏa thích sáng tạo với những tiểu cảnh như Hồ Cá Koi, Khu vườn Mê Cung, Núi Đá Nhân Tạo,... Ngoài ra, Quý khách còn có thể tìm hiểu quy trình lấy mật ong tại đây.
Nghỉ đêm tại Buôn Ma Thuột.',19,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (55,N'Ngày 3 - BUÔN MA THUỘT - HỒ LẮK - KDL SINH THÁI KOTAM (Ăn sáng, trưa, chiều)',N'Sau bữa sáng, xe đưa Quý khách tham quan:
- Hồ Lắk: khám phá vẻ đẹp tự nhiên của hồ nước ngọt khổng lồ bằng thuyền gỗ độc mộc mang đậm dấu ấn Tây Nguyên (chi phí tự túc),
- Buôn Jun: nơi hiếm hoi còn giữ được nét văn hóa nguyên sơ của đồng bào M’nông, in đậm nhất là dấu ấn của những nếp nhà dài phên nứa, cột gỗ.
- KDL Sinh Thái Kotam: khuôn viên rộng với phức hợp nhiều tiểu cảnh mang lại không gian xanh, trong lành nhưng không kém phần huyền ảo. Không chỉ vậy, Quý khách còn được dịp tìm hiểu nét đẹp từ những phong tục bản địa của người Êđê giữa mênh mông đại ngàn hay thưởng thức những món ăn mang đậm văn hóa Tây Nguyên.
Tiếp tục, xe đưa đoàn tham quan và mua đặc sản tại chợ Buôn Ma Thuột. Sau bữa ăn chiều, Quý khách tự do dạo phố đêm. 
Nghỉ đêm tại Buôn Ma Thuột.',19,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (56,N'Ngày 4 - BUÔN MA THUỘT - TP. HỒ CHÍ MINH (Ăn sáng, trưa)',N'Sau bữa sáng, Quý khách trả phòng khách sạn. Đoàn tiếp tục tham quan:
- Buôn Ako DHong: hay còn gọi là buôn Cô Thôn vẫn gìn giữ nét sinh hoạt truyền thống lâu đời cho đến nay. Du khách sẽ có cảm giác bình yên như ở nhà với nét mộc mạc, độc đáo của buôn làng người Ê Đê từ hệ thống nhà dài của người dân, nhà trưng bày dệt thổ Cẩm, cồng chiềng, trực tiếp xem đồng bào dệt thủ công các mặt hàng thổ cẩm độc đáo.
- Vườn Café Rêu Phong: được dịp tận mắt xem vườn ca cao, loại trái cây được trồng chủ yếu để lấy hạt làm nên các sản phẩm kinh tế cao trên thị trường hiện nay đó là bột ca cao và chocolate. Ngoài ra, Quý khách sẽ được thưởng thức đặc sản của quán “sinh tố ca cao” được làm từ cơm của trái ca cao, vị chua chua ngọt ngọt, một thức uống giải nhiệt lạ miệng nhưng cũng không kém phần hấp dẫn.
Xe đưa Quý khách khởi hành về lại TP. Hồ Chí Minh. Ăn trưa trên cung đường về, chia tay Quý khách và kết thúc chương trình du lịch.',19,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (57,N'Ngày 1 - NÔNG TRANG XANH - ĐỊA ĐẠO CỦ CHI Số bữa ăn: 1 bữa ( trưa)',N'Quý khách tập trung tại Vietravel (190 Pasteur, Phường Võ Thị Sáu, Quận 3, TP.HCM) khởi hành đi Củ Chi - Một vùng đất kiên cường trong chiến tranh cách trung tâm TP. HCM khoảng 50 km. “Củ Chi đất thép” không chỉ nổi tiếng với chiến công lịch sử hào hùng, ngày nay Quý khách đến Củ Chi còn được trải nghiệm mô hình tiêu biểu cho sản xuất nông nghiệp sạch và du lịch sinh thái tại Nông Trang Xanh - Tìm về cảnh thôn quê mộc mạc:

Khám phá quy trình trồng nấm sạch: cũng như được tận tay tham gia thu hoạch nấm.

Khu chuồng trại chăn nuôi: tự tay hái lá cho dê, cừu ăn và thử tài khéo léo vắt sữa bò.

Vườn rau hữu cơ sạch: áp dụng trồng theo công nghệ sinh học, không có hóa chất, thuốc trừ sâu.

Lưu lại bức ảnh đáng nhớ trước vẻ đẹp của những vườn hoa sao nhái hoặc cánh đồng hoa hướng dương với sắc vàng rực rỡ đang thi nhau khoe sắc (tùy theo mùa) 

Tiếp tục hành trình, Quý khách khám phá một trong mười công trình ngầm ngoạn mục nhất thế giới - Địa Đạo Củ Chi với những trải nghiệm đặc biệt:

Xem những thước phim tư liệu quý về vùng giải phóng những năm 60.

Thử cảm giác chui địa đạo với độ dài 15m, lắng nghe các câu chuyện về cuộc sống người dân “Đất Thép” trong bom đạn (tùy thuộc vào tình hình thực tế).

Đặc biệt, du khách sẽ được tận mắt chứng kiến một phát minh hết sức sáng tạo trong việc giấu khói của bếp Hoàng Cầm và thưởng thức món ăn của du kích năm xưa mà giờ đây đã trở thành đặc sản: khoai mì chấm muối đậu.

Thăm Đền Bến Dược: nơi tưởng niệm những anh hùng chiến đấu đã hy sinh trên vùng đất Sài Gòn - Gia Định trong hai cuộc kháng chiến chống Pháp - Mỹ.

Hòa mình vào không gian làng quê của khu tái hiện vùng giải phóng Củ Chi (1961 - 1972), xem Sa bàn và phim 3D mô phỏng diễn biến đánh bại trận càn Cedar Falls vào vùng Tam Giác Sắt Củ Chi

Tham quan các mô hình trải nghiệm như: cấy lúa, giã gạo, bắt cá, xay lúa, đan lát, đặc biệt là thực hành nghề làm bánh tráng truyền thống của người dân Củ Chi.

Tạm biệt Củ Chi, khởi hành về lại trung tâm TP. HCM, đoàn dừng chân Quán Vườn Cau - thưởng thức nước mía sầu riêng và củ mì hấp nước cốt dừa thơm ngon (tùy vào tình hình thực tế, chi phí nước uống tự túc).

Xe đưa Quý khách về điểm đón ban đầu và kết thúc chương trình.',20,0)

INSERT INTO travel_detail (id, [time], [description], travel_Id, is_deleted) 
	VALUES (58,N'Ngày 1 - BUỔI SÁNG: BẾN BẠCH ĐẰNG - CẦN GIỜ - VÀM SÁT (Ăn trưa)',N'Hướng dẫn viên đón Quý khách tại bến Tàu Cao Tốc Bạch Đằng - Số 10B ĐườngTôn Đức Thắng, Phường Bến Nghé, Quận 1, làm thủ tục lên tàu.

Tàu khởi hành đến Cần Giờ - được mệnh danh là “lá phổi xanh” của Thành phố. Quý khách sẽ được chiêm ngưỡng cảnh đẹp Sài Gòn từ một góc nhìn mới, chuyển dần cảm xúc khi tàu nhẹ nhàng dẫn lối vào không gian của những cánh rừng ngập mặn.

Quý khách tham quan Khu du lịch sinh thái Dần Xây và tham gia các hoạt động trải nghiệm:

Nghe giới thiệu về quá trình khôi phục và phát triển hệ sinh thái rừng ngập mặn Cần Giờ.
Chèo thuyền Kayak len lỏi giữa rừng ngập mặn, khám phá hệ thực vật đặc trưng của rừng ngập mặn (chi phí tự túc, hoạt động tùy theo con nước và phù hợp khách biết bơi với độ tuổi từ 20 - 50 tuổi)
Thỏa thích “sống ảo” cùng con đường bê tông băng rừng, rêu phong, uốn lượn, cây cối che phủ dọc hai bên rợp bóng mát, phía dưới là hệ sinh thái ngập nước với rễ cây mọc tua tủa kích thích sự tò mò của du khách.
Quý khách lên ca nô và di chuyển hướng ra bến tàu đến trung tâm Khu Du Lịch Sinh Thái Vàm Sát , thưởng ngoạn cảnh quan sông nước đặc trưng tại Cần Giờ; cũng như tìm hiểu các hoạt động đánh bắt thủy sản của ngư dân Cần Giờ: thả lưới, đóng đáy, nuôi hàu,…

Dùng bữa trưa tại Khu du lịch Vàm Sát với những món ăn đặc sắc của vùng rừng Đước như: Gỏi lá kìm, Vịt nước mặn nướng lu, Tôm hấp, chè dừa nước,… 

Sau bữa trưa, quý khách tiếp tục hành trình bằng xe điện  tham quan:

Khu Bảo tồn Chim: Với số lượng trên 20.000 con bao gồm 26 loài khác nhau, trong đó có một số loài nằm trong sách đỏ như: Giang Sen, Bồ Nông, Già Đẩy… Quý khách có thể nhìn thấy sự náo nhiệt của khu du lịch Vàm Sát với hàng ngàn cánh chim nước rợp cả vùng trời.
Khu bảo tồn cá sấu Hoa Cà: tìm hiểu về quy trình ấp trứng, nuôi dưỡng và bảo tồn cá sấu, đặc biệt là tham gia trò chơi cảm giác mạnh “Du thuyền câu Cá Sấu”.
Ngắm nhìn đàn khỉ đuôi dài sống hoang dã tự nhiên, rất tinh nghịch.
Chinh phục Tháp Tang Bồng cao 26m: chiêm ngưỡng vẻ đẹp toàn cảnh rừng ngập mặn từ trên cao với cảnh sắc thiên nhiên đẹp như tranh vẽ.
Khám phá con đường bê tông xuyên rừng rêu phong uốn lượn, cây cối che phủ dọc hai bên đường rợp bóng mát, phía dưới là hệ sinh thái ngập nước với rễ cây mọc tua tủa kích thích sự tò mò của Quý khách hay thử thách với chiếc cầu treo, tham quan đàn Nai rừng hiền hoà thân thiện,…
Chia tay Vàm Sát, đoàn lên ca nô tiếp tục đi Đầm Dơi: cảm nhận nét yên ả, thả hồn theo những nhịp chèo của xuồng ba lá len lỏi sâu vào rừng Đước tham quan đàn dơi nghệ quý hiếm và trải nghiệm trò chơi câu cua biển vui nhộn.

Đoàn lên ca nô và làm thủ tục lên về trung tâm TP. Hồ Chí Minh. Ngắm cảnh bình yên sông nước thoáng mát, hữu tình sẽ trở thành một trong những kỷ niệm khó quên và khoảnh khắc đó cũng thay lời chào của Cần Giờ gửi đến Quý khách.

Tàu về đến Bến Bạch Đằng. Chia tay quý khách và kết thúc chương trình nhiều trải nghiệm thú vị cùng Vietravel.',21,0)

SET IDENTITY_INSERT [dbo].[travel_detail] OFF
GO

-- INSERT ageType
SET IDENTITY_INSERT [dbo].[age_type] ON
INSERT INTO age_type (id, description, is_deleted) VALUES (1, N'Người lớn', 0)
INSERT INTO age_type (id, description, is_deleted) VALUES (2, N'Trẻ em', 0)
INSERT INTO age_type (id, description, is_deleted) VALUES (3, N'Trẻ nhỏ', 0)
INSERT INTO age_type (id, description, is_deleted) VALUES (4, N'Em bé', 0)
SET IDENTITY_INSERT [dbo].[age_type] OFF
GO

-- INSERT priceDetail
SET IDENTITY_INSERT [dbo].[price_detail] ON
INSERT INTO price_detail (id, price, travel_Id, age_id, is_deleted) VALUES (1, 13960000, 1, 1, 0)
SET IDENTITY_INSERT [dbo].[price_detail] OFF
GO

-- INSERT booking
SET IDENTITY_INSERT [dbo].[booking] ON
INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (1, 1, '2021-10-09', 'TP.HCM', '0394008704', 13960000, 1, 0)

INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (2, 3, '2021-11-20', 'TP.HCM', '00354389483', 12500000, 1, 0)

INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (3, 6, '2021-11-19', 'TP.HCM', '04234332432', 8900000, 1, 0)


INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (4, 8, '2021-11-19', 'TP.HCM', '0434034535', 5000000, 1, 0)

INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (5, 7, '2021-11-10', 'Long An', '0896453413', 2250000, 0, 0)

INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (6, 7, '2021-10-30', 'Long An', '0896453413', 5590000, 1, 0)

INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (7, 5, '2021-10-15', 'TP.HCM', '0324034355', 1250000, 0, 0)


INSERT INTO booking (id, account_Id, created_date, [address], phone, total_price, pay_boolean, is_deleted)
	VALUES (8, 5, '2021-11-02', 'TP.HCM', '0324034355', 9950000, 0, 0)

SET IDENTITY_INSERT [dbo].[booking] OFF
GO

-- INSERT bookingDetail
SET IDENTITY_INSERT [dbo].[booking_detail] ON
INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (1, 1, 1, 13960000,2)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (2, 2, 11, 12500000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (3, 3, 18, 8900000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (4, 4, 7, 5000000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (5, 8, 6, 9950000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (6, 7, 5, 1250000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (7, 5, 11, 2250000,1)

INSERT INTO booking_detail (id, booking_Id, travel_Id, price,quantity) VALUES (8, 6, 11, 5590000,1)

SET IDENTITY_INSERT [dbo].[booking_detail] OFF
GO

-- INSERT payment
SET IDENTITY_INSERT [dbo].[payment] ON
INSERT INTO payment (id, booking_Id, pay_time, total_price) VALUES (1, 1, '2021-10-09 10:00:00', 13960000)

INSERT INTO payment (id, booking_Id, pay_time, total_price) VALUES (2, 2, '2021-11-20 10:00:00', 12500000)

INSERT INTO payment (id, booking_Id, pay_time, total_price) VALUES (3, 4, '2021-11-19 10:00:00', 5000000)

INSERT INTO payment (id, booking_Id, pay_time, total_price) VALUES (4, 3, '2021-11-19 10:00:00', 8900000)

INSERT INTO payment (id, booking_Id, pay_time, total_price) VALUES (5, 6, '2021-10-30 12:30:00', 5590000)

SET IDENTITY_INSERT [dbo].[payment] OFF
GO

CREATE PROC sp_getTotalPriceOneMonth
(
	@month varchar(2),
	@year varchar(4)
)
AS BEGIN 
	DECLARE @result varchar(20)
	SET @result = (SELECT SUM(o2.price) AS 'totalprice'
					FROM booking o1 inner join booking_detail o2 on o1.id = o2.booking_Id
					WHERE MONTH(o1.created_date) = @month AND YEAR(o1.created_date) = @year)
	IF @result IS NULL BEGIN SET @result = '0' END
	SELECT @result
END

;
GO
-- getOrderInDay
CREATE PROC sp_getOrderInDay
AS BEGIN 
	SELECT count(id) from booking where Day(created_date) = DAY(GETDATE()) and
										Month(created_date) = Month(GETDATE()) and 
										Year(created_date) = Year(GETDATE()) 
END
GO
-- getRevenueInDay 
CREATE PROC sp_getRevenueInDay
AS BEGIN 
	select sum(price) 
	from booking_detail a inner join booking b on (a.booking_Id = b.id)
	where Day(created_date) = DAY(GETDATE()) and
		  Month(created_date) = Month(GETDATE()) and 
		  Year(created_date) = Year(GETDATE()) 
END
GO
-- getRevenue

CREATE PROC sp_getRevenue
AS BEGIN 
	select sum(price) 
	from booking_detail a inner join booking b on (a.booking_Id = b.id)
	where  Year(created_date) = Year(GETDATE()) 
END	
GO
-- getTotalUserLastMonth

CREATE PROC sp_getTotalUserLastMonth
AS BEGIN 
	select count(id) 
	from account 
	where  Month(created_date) = Month(GETDATE()) -1 and 
		   Year(created_date) = Year(GETDATE()) and 
		   role_Id = 3
END
GO
-- getTotalUserCurrentMonth

CREATE PROC sp_getTotalUserCurrentMonth
AS BEGIN 
	select count(id) 
	from account 
	where  Month(created_date) = Month(GETDATE()) and 
		   Year(created_date) = Year(GETDATE()) and 
		   role_Id = 3
END
GO
-- số lupwjng phần trăm bán ra 
CREATE PROC sp_getTotalSold
AS BEGIN 
	select name, quantity, quantity-quantity_new, (100- ((convert(float,quantity_new) / convert(float,quantity)) * 100)) as phanTram 
	from travel 
	order by phanTram desc
END
GO

CREATE PROC sp_getLastRevenue
AS BEGIN 
	select sum(price) 
	from booking_detail a inner join booking b on (a.booking_Id = b.id)
	where  Year(created_date) = Year(GETDATE()) -1
END	
GO


