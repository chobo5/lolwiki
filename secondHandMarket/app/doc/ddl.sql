-- 상품
DROP TABLE IF EXISTS goods;

-- 유저
DROP TABLE IF EXISTS user;

-- 상품사진
DROP TABLE IF EXISTS goods_photo;

-- 상품
CREATE TABLE goods (
	no INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	price INT NOT NULL,
	spec VARCHAR(500) NULL,
	user_no INT NOT NULL,
	reg_date DATE NULL
);

-- 상품
ALTER TABLE goods
	ADD CONSTRAINT PK_goods -- 상품 기본키
	PRIMARY KEY (
	no -- 상품 번호
	);

ALTER TABLE goods
	MODIFY COLUMN no INT NOT NULL AUTO_INCREMENT(1,1);

-- 유저
CREATE TABLE user (
	no INT NOT NULL,
	nickname VARCHAR(50) NOT NULL,
	phone_no VARCHAR(20) NOT NULL,
	password VARCHAR(50) NOT NULL,
	photo VARCHAR(250) NULL
);

-- 유저
ALTER TABLE user
	ADD CONSTRAINT PK_user -- 유저 기본키
	PRIMARY KEY (
	no -- 회원 번호
	);

ALTER TABLE user MODIFY password VARCHAR(200);

-- 유저 유니크 인덱스
CREATE UNIQUE INDEX UIX_user
	ON user ( -- 유저
				nickname ASC, -- 회원 아이디
				phone_no ASC  -- 회원 연락처
	);

ALTER TABLE user
	MODIFY COLUMN no INT NOT NULL AUTO_INCREMENT(1,1);

-- 상품사진
CREATE TABLE goods_photo (
	no INT NOT NULL,
	path VARCHAR(100) NULL,
	goods_no INT NOT NULL
);

-- 상품사진
ALTER TABLE goods_photo
	ADD CONSTRAINT PK_goods_photo -- 상품사진 기본키
	PRIMARY KEY (
	no -- 사진 번호
	);

ALTER TABLE goods_photo
	MODIFY COLUMN no INT NOT NULL AUTO_INCREMENT;

-- 상품
ALTER TABLE goods
	ADD CONSTRAINT FK_user_TO_goods -- 유저 -> 상품
	FOREIGN KEY (
	user_no -- 회원 번호
	)
	REFERENCES user ( -- 유저
	no -- 회원 번호
	);

-- 상품사진
ALTER TABLE goods_photo
	ADD CONSTRAINT FK_goods_TO_goods_photo -- 상품 -> 상품사진
	FOREIGN KEY (
	goods_no -- 상품 번호
	)
	REFERENCES goods ( -- 상품
	no -- 상품 번호
	);