-- 구단
CREATE TABLE `club` (
	`club_no`    INT         NOT NULL COMMENT '구단번호', -- 구단번호
	`foundation` DATE        NOT NULL COMMENT '창단', -- 창단
	`belong`     VARCHAR(20) NOT NULL COMMENT '소속리그', -- 소속리그
	`full_name`  VARCHAR(50) NOT NULL COMMENT '팀명', -- 팀명
	`name`       VARCHAR(20) NULL     COMMENT '약칭', -- 약칭
	`leader`     VARCHAR(20) NULL     COMMENT '단장', -- 단장
	`color`      VARCHAR(20) NULL     COMMENT '팀컬러', -- 팀컬러
	`league_no`  INT         NOT NULL COMMENT '리그번호' -- 리그번호
)
COMMENT '구단';

-- 구단
ALTER TABLE `club`
	ADD CONSTRAINT `PK_club` -- 구단 기본키
	PRIMARY KEY (
	`club_no` -- 구단번호
	);

ALTER TABLE `club`
	MODIFY COLUMN `club_no` INT NOT NULL AUTO_INCREMENT COMMENT '구단번호';

-- 대회우승
CREATE TABLE `roaster_contest` (
	`contest_no` INT NOT NULL COMMENT '대회번호', -- 대회번호
	`roaster_no` INT NOT NULL COMMENT '로스터번호' -- 로스터번호
)
COMMENT '대회우승';

-- 대회우승
ALTER TABLE `roaster_contest`
	ADD CONSTRAINT `PK_roaster_contest` -- 대회우승 기본키
	PRIMARY KEY (
	`contest_no`, -- 대회번호
	`roaster_no`  -- 로스터번호
	);

-- 선수
CREATE TABLE `player` (
	`player_no`     INT         NOT NULL COMMENT '선수번호', -- 선수번호
	`kor_name`      VARCHAR(5)  NOT NULL COMMENT '이름', -- 이름
	`eng_name`      VARCHAR(50) NOT NULL COMMENT '영문이름', -- 영문이름
	`game_id`       VARCHAR(50) NOT NULL COMMENT '아이디', -- 아이디
	`birth`         DATE        NOT NULL COMMENT '출생', -- 출생
	`nationality`   VARCHAR(10) NOT NULL COMMENT '국적', -- 국적
	`debut`         DATE        NOT NULL COMMENT '데뷔', -- 데뷔
	`position`      VARCHAR(20) NOT NULL COMMENT '포지션', -- 포지션
	`kor_server_id` VARCHAR(50) NULL     COMMENT '한국서버아이디' -- 한국서버아이디
)
COMMENT '선수';

-- 선수
ALTER TABLE `player`
	ADD CONSTRAINT `PK_player` -- 선수 기본키
	PRIMARY KEY (
	`player_no` -- 선수번호
	);

ALTER TABLE `player`
	MODIFY COLUMN `player_no` INT NOT NULL AUTO_INCREMENT COMMENT '선수번호';

-- SNS
CREATE TABLE `sns` (
	`sns_no` VARCHAR(20) NOT NULL COMMENT 'SNS번호', -- SNS번호
	`name`   VARCHAR(20) NULL     COMMENT 'SNS이름' -- SNS이름
)
COMMENT 'SNS';

-- SNS
ALTER TABLE `sns`
	ADD CONSTRAINT `PK_sns` -- SNS 기본키
	PRIMARY KEY (
	`sns_no` -- SNS번호
	);

-- SNS 유니크 인덱스
CREATE UNIQUE INDEX `UIX_sns`
	ON `sns` ( -- SNS
		`name` ASC -- SNS이름
	);

-- 별명
CREATE TABLE `nickname` (
	`player_no` INT          NOT NULL COMMENT '선수번호', -- 선수번호
	`name`      VARCHAR(100) NULL     COMMENT '별명', -- 별명
	`content`   TEXT         NULL     COMMENT '내용' -- 내용
)
COMMENT '별명';

-- 별명
ALTER TABLE `nickname`
	ADD CONSTRAINT `PK_nickname` -- 별명 기본키
	PRIMARY KEY (
	`player_no` -- 선수번호
	);

-- 리그
CREATE TABLE `league` (
	`league_no`       INT         NOT NULL COMMENT '리그번호', -- 리그번호
	`full_name`       VARCHAR(50) NOT NULL COMMENT '정식명칭', -- 정식명칭
	`name`            VARCHAR(20) NULL     COMMENT '약칭', -- 약칭
	`foundation_date` VARCHAR(20) NOT NULL COMMENT '출범연도', -- 출범연도
	`region`          VARCHAR(20) NOT NULL COMMENT '지역', -- 지역
	`main_agent`      VARCHAR(20) NULL     COMMENT '운영주체', -- 운영주체
	`slogan`          VARCHAR(50) NULL     COMMENT '표어', -- 표어
	`partner`         VARCHAR(50) NULL     COMMENT '공식파트너', -- 공식파트너
	`stadium`         VARCHAR(20) NULL     COMMENT '경기장', -- 경기장
	`recent_champ`    VARCHAR(20) NOT NULL COMMENT '최근우승팀', -- 최근우승팀
	`most_champ`      VARCHAR(20) NOT NULL COMMENT '최다우승팀', -- 최다우승팀
	`most_player`     VARCHAR(20) NOT NULL COMMENT '최다우승자' -- 최다우승자
)
COMMENT '리그';

-- 리그
ALTER TABLE `league`
	ADD CONSTRAINT `PK_league` -- 리그 기본키
	PRIMARY KEY (
	`league_no` -- 리그번호
	);

ALTER TABLE `league`
	MODIFY COLUMN `league_no` INT NOT NULL AUTO_INCREMENT COMMENT '리그번호';

ALTER TABLE league CHANGE foundation_date foundation_year VARCHAR(20);

-- 로스터
CREATE TABLE `roaster` (
	`roaster_no` INT         NOT NULL COMMENT '로스터번호', -- 로스터번호
	`club_no`    INT         NULL     COMMENT '구단번호', -- 구단번호
	`season`     INT         NULL     COMMENT '시즌', -- 시즌
	`director`   VARCHAR(20) NULL     COMMENT '감독', -- 감독
	`coach`      VARCHAR(50) NULL     COMMENT '코치' -- 코치
)
COMMENT '로스터';

-- 로스터
ALTER TABLE `roaster`
	ADD CONSTRAINT `PK_roaster` -- 로스터 기본키
	PRIMARY KEY (
	`roaster_no` -- 로스터번호
	);

-- 로스터 유니크 인덱스
CREATE UNIQUE INDEX `UIX_roaster`
	ON `roaster` ( -- 로스터
		`club_no` ASC, -- 구단번호
		`season` ASC   -- 시즌
	);

ALTER TABLE `roaster`
	MODIFY COLUMN `roaster_no` INT NOT NULL AUTO_INCREMENT COMMENT '로스터번호';

-- 선수사진
CREATE TABLE `player_pic` (
	`player_no` INT          NULL COMMENT '선수번호', -- 선수번호
	`path`      VARCHAR(250) NULL COMMENT '경로' -- 경로
)
COMMENT '선수사진';

-- 스폰서
CREATE TABLE `sponsor` (
	`league_no` INT         NOT NULL COMMENT '리그번호', -- 리그번호
	`name`      VARCHAR(50) NOT NULL COMMENT '스폰서명' -- 스폰서명
)
COMMENT '스폰서';

-- 선수SNS
CREATE TABLE `player_sns` (
	`player_no` INT          NOT NULL COMMENT '선수번호', -- 선수번호
	`sns_no`    VARCHAR(20)  NOT NULL COMMENT 'SNS번호', -- SNS번호
	`url`       VARCHAR(100) NULL     COMMENT 'URL' -- URL
)
COMMENT '선수SNS';

-- 선수SNS
ALTER TABLE `player_sns`
	ADD CONSTRAINT `PK_player_sns` -- 선수SNS 기본키
	PRIMARY KEY (
	`player_no`, -- 선수번호
	`sns_no`     -- SNS번호
	);

-- 구단SNS
CREATE TABLE `club_sns` (
	`club_no` INT          NOT NULL COMMENT '구단번호', -- 구단번호
	`sns_no`  VARCHAR(20)  NOT NULL COMMENT 'SNS번호', -- SNS번호
	`url`     VARCHAR(100) NULL     COMMENT 'URL' -- URL
)
COMMENT '구단SNS';

-- 구단SNS
ALTER TABLE `club_sns`
	ADD CONSTRAINT `PK_club_sns` -- 구단SNS 기본키
	PRIMARY KEY (
	`club_no`, -- 구단번호
	`sns_no`   -- SNS번호
	);

-- 리그SNS
CREATE TABLE `league_sns` (
	`league_no` INT          NOT NULL COMMENT '리그번호', -- 리그번호
	`sns_no`    VARCHAR(20)  NOT NULL COMMENT 'SNS번호', -- SNS번호
	`url`       VARCHAR(100) NULL     COMMENT 'URL' -- URL
)
COMMENT '리그SNS';

-- 리그SNS
ALTER TABLE `league_sns`
	ADD CONSTRAINT `PK_league_sns` -- 리그SNS 기본키
	PRIMARY KEY (
	`league_no`, -- 리그번호
	`sns_no`     -- SNS번호
	);

-- 로스터소속선수
CREATE TABLE `roaster_player` (
	`roaster_no` INT     NOT NULL COMMENT '로스터번호', -- 로스터번호
	`player_no`  INT     NOT NULL COMMENT '선수번호', -- 선수번호
	`is_captain` BOOLEAN NOT NULL COMMENT '주장여부' -- 주장여부
)
COMMENT '로스터소속선수';

-- 로스터소속선수
ALTER TABLE `roaster_player`
	ADD CONSTRAINT `PK_roaster_player` -- 로스터소속선수 기본키
	PRIMARY KEY (
	`roaster_no`, -- 로스터번호
	`player_no`   -- 선수번호
	);

-- 대회
CREATE TABLE `contest` (
	`contest_no` INT         NOT NULL COMMENT '대회번호', -- 대회번호
	`name`       VARCHAR(50) NULL     COMMENT '대회명' -- 대회명
)
COMMENT '대회';

-- 대회
ALTER TABLE `contest`
	ADD CONSTRAINT `PK_contest` -- 대회 기본키
	PRIMARY KEY (
	`contest_no` -- 대회번호
	);

-- 구단
ALTER TABLE `club`
	ADD CONSTRAINT `FK_league_TO_club` -- 리그 -> 구단
	FOREIGN KEY (
	`league_no` -- 리그번호
	)
	REFERENCES `league` ( -- 리그
	`league_no` -- 리그번호
	);

-- 대회우승
ALTER TABLE `roaster_contest`
	ADD CONSTRAINT `FK_contest_TO_roaster_contest` -- 대회 -> 대회우승
	FOREIGN KEY (
	`contest_no` -- 대회번호
	)
	REFERENCES `contest` ( -- 대회
	`contest_no` -- 대회번호
	);

-- 대회우승
ALTER TABLE `roaster_contest`
	ADD CONSTRAINT `FK_roaster_TO_roaster_contest` -- 로스터 -> 대회우승
	FOREIGN KEY (
	`roaster_no` -- 로스터번호
	)
	REFERENCES `roaster` ( -- 로스터
	`roaster_no` -- 로스터번호
	);

-- 별명
ALTER TABLE `nickname`
	ADD CONSTRAINT `FK_player_TO_nickname` -- 선수 -> 별명
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- 로스터
ALTER TABLE `roaster`
	ADD CONSTRAINT `FK_club_TO_roaster` -- 구단 -> 로스터
	FOREIGN KEY (
	`club_no` -- 구단번호
	)
	REFERENCES `club` ( -- 구단
	`club_no` -- 구단번호
	);

-- 선수사진
ALTER TABLE `player_pic`
	ADD CONSTRAINT `FK_player_TO_player_pic` -- 선수 -> 선수사진
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- 스폰서
ALTER TABLE `sponsor`
	ADD CONSTRAINT `FK_league_TO_sponsor` -- 리그 -> 스폰서
	FOREIGN KEY (
	`league_no` -- 리그번호
	)
	REFERENCES `league` ( -- 리그
	`league_no` -- 리그번호
	);

-- 선수SNS
ALTER TABLE `player_sns`
	ADD CONSTRAINT `FK_player_TO_player_sns` -- 선수 -> 선수SNS
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- 선수SNS
ALTER TABLE `player_sns`
	ADD CONSTRAINT `FK_sns_TO_player_sns` -- SNS -> 선수SNS
	FOREIGN KEY (
	`sns_no` -- SNS번호
	)
	REFERENCES `sns` ( -- SNS
	`sns_no` -- SNS번호
	);

-- 구단SNS
ALTER TABLE `club_sns`
	ADD CONSTRAINT `FK_club_TO_club_sns` -- 구단 -> 구단SNS
	FOREIGN KEY (
	`club_no` -- 구단번호
	)
	REFERENCES `club` ( -- 구단
	`club_no` -- 구단번호
	);

-- 구단SNS
ALTER TABLE `club_sns`
	ADD CONSTRAINT `FK_sns_TO_club_sns` -- SNS -> 구단SNS
	FOREIGN KEY (
	`sns_no` -- SNS번호
	)
	REFERENCES `sns` ( -- SNS
	`sns_no` -- SNS번호
	);

-- 리그SNS
ALTER TABLE `league_sns`
	ADD CONSTRAINT `FK_sns_TO_league_sns` -- SNS -> 리그SNS
	FOREIGN KEY (
	`sns_no` -- SNS번호
	)
	REFERENCES `sns` ( -- SNS
	`sns_no` -- SNS번호
	);

-- 리그SNS
ALTER TABLE `league_sns`
	ADD CONSTRAINT `FK_league_TO_league_sns` -- 리그 -> 리그SNS
	FOREIGN KEY (
	`league_no` -- 리그번호
	)
	REFERENCES `league` ( -- 리그
	`league_no` -- 리그번호
	);

-- 로스터소속선수
ALTER TABLE `roaster_player`
	ADD CONSTRAINT `FK_roaster_TO_roaster_player` -- 로스터 -> 로스터소속선수
	FOREIGN KEY (
	`roaster_no` -- 로스터번호
	)
	REFERENCES `roaster` ( -- 로스터
	`roaster_no` -- 로스터번호
	);

-- 로스터소속선수
ALTER TABLE `roaster_player`
	ADD CONSTRAINT `FK_player_TO_roaster_player` -- 선수 -> 로스터소속선수
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);