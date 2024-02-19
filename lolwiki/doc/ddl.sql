
-- 구단
CREATE TABLE `club` (
	`club_no`        INT         NOT NULL COMMENT '구단번호', -- 구단번호
	`foundation`     DATE        NOT NULL COMMENT '창단', -- 창단
	`belong_to`      VARCHAR(20) NOT NULL COMMENT '소속리그', -- 소속리그
	`name`           VARCHAR(20) NOT NULL COMMENT '팀명', -- 팀명
	`parent_company` VARCHAR(20) NULL     COMMENT '모기업', -- 모기업
	`ceo`            VARCHAR(20) NULL     COMMENT 'CEO', -- CEO
	`leader`         VARCHAR(20) NULL     COMMENT '단장', -- 단장
	`color`          VARCHAR(20) NULL     COMMENT '팀컬러', -- 팀컬러
	`league_no`      INT         NOT NULL COMMENT '리그번호' -- 리그번호
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

-- 경기 기록
CREATE TABLE `record` (
	`player_no`     INT NOT NULL COMMENT '선수번호', -- 선수번호
	`worlds`        INT NOT NULL COMMENT '월즈', -- 월즈
	`msi`           INT NOT NULL COMMENT 'msi', -- msi
	`league_spring` INT NOT NULL COMMENT '스프링', -- 스프링
	`league_summer` INT NOT NULL COMMENT '써머' -- 써머
)
COMMENT '경기 기록';

-- 경기 기록
ALTER TABLE `record`
	ADD CONSTRAINT `PK_record` -- 경기 기록 기본키
	PRIMARY KEY (
	`player_no` -- 선수번호
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
	`kor_server_id` VARCHAR(20) NULL     COMMENT '한국서버아이디', -- 한국서버아이디
	`roaster_no`    INT         NULL     COMMENT '로스터 번호' -- 로스터 번호
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

-- 솔로 랭크
CREATE TABLE `solo_rank` (
	`season`    INT         NOT NULL COMMENT 'season', -- season
	`player_no` INT         NOT NULL COMMENT '선수번호', -- 선수번호
	`game_id`   VARCHAR(20) NOT NULL COMMENT '계정', -- 계정
	`best`      VARCHAR(20) NULL     COMMENT '최고', -- 최고
	`final`     VARCHAR(20) NULL     COMMENT '최종' -- 최종
)
COMMENT '솔로 랭크';

-- 솔로 랭크
ALTER TABLE `solo_rank`
	ADD CONSTRAINT `PK_solo_rank` -- 솔로 랭크 기본키
	PRIMARY KEY (
	`season` -- season
	);

-- SNS
CREATE TABLE `sns` (
	`instagram` VARCHAR(20) NULL COMMENT '인스타그램', -- 인스타그램
	`facebook`  VARCHAR(20) NULL COMMENT '페이스북', -- 페이스북
	`x`         VARCHAR(20) NULL COMMENT '엑스', -- 엑스
	`player_no` INT         NULL COMMENT '선수번호', -- 선수번호
	`club_no`   INT         NULL COMMENT '구단번호', -- 구단번호
	`league_no` INT         NULL COMMENT '리그번호' -- 리그번호
)
COMMENT 'SNS';

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
	`name`            VARCHAR(20) NULL     COMMENT '약칭', -- 약칭
	`full_name`       VARCHAR(20) NOT NULL COMMENT '정식명칭', -- 정식명칭
	`foundation_date` DATE        NOT NULL COMMENT '출범연도', -- 출범연도
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

ALTER TABLE league CHANGE foundation_date foundation_year VARCHAR(20);

ALTER TABLE league CHANGE full_name full_name VARCHAR(50);

ALTER TABLE league CHANGE foundation_date foundation_date VARCHAR(20);

ALTER TABLE `league`
	MODIFY COLUMN `league_no` INT NOT NULL AUTO_INCREMENT COMMENT '리그번호';

-- 로스터
CREATE TABLE `roaster` (
	`roaster_no` INT         NOT NULL COMMENT '로스터번호', -- 로스터번호
	`season_no`  INT         NOT NULL COMMENT '시즌번호', -- 시즌번호
	`director`   VARCHAR(20) NULL     COMMENT '감독', -- 감독
	`coach`      VARCHAR(20) NULL     COMMENT '코치', -- 코치
	`captain`    VARCHAR(20) NULL     COMMENT '주장' -- 주장
)
COMMENT '로스터';

-- 로스터
ALTER TABLE `roaster`
	ADD CONSTRAINT `PK_roaster` -- 로스터 기본키
	PRIMARY KEY (
	`roaster_no` -- 로스터번호
	);

ALTER TABLE `roaster`
	MODIFY COLUMN `roaster_no` INT NOT NULL AUTO_INCREMENT COMMENT '로스터번호';

ALTER TABLE roaster CHANGE coach coach VARCHAR(50);
-- 시즌별 기록
CREATE TABLE `record_of_season` (
	`season`    INT NOT NULL COMMENT '시즌', -- 시즌
	`player_no` INT NOT NULL COMMENT '선수번호' -- 선수번호
)
COMMENT '시즌별 기록';

-- 시즌별 기록
ALTER TABLE `record_of_season`
	ADD CONSTRAINT `PK_record_of_season` -- 시즌별 기록 기본키
	PRIMARY KEY (
	`season`,    -- 시즌
	`player_no`  -- 선수번호
	);

-- 선수사진
CREATE TABLE `player_pic` (
	`player_no` INT          NULL COMMENT '선수번호', -- 선수번호
	`path`      VARCHAR(250) NULL COMMENT '경로' -- 경로
)
COMMENT '선수사진';

-- 서명사진
CREATE TABLE `sign` (
	`player_no` INT          NULL COMMENT '선수번호', -- 선수번호
	`path`      VARCHAR(250) NULL COMMENT '경로' -- 경로
)
COMMENT '서명사진';

-- 시즌
CREATE TABLE `season` (
	`season_no` INT NOT NULL COMMENT '시즌번호', -- 시즌번호
	`club_no`   INT NULL     COMMENT '구단번호' -- 구단번호
)
COMMENT '시즌';

-- 시즌
ALTER TABLE `season`
	ADD CONSTRAINT `PK_season` -- 시즌 기본키
	PRIMARY KEY (
	`season_no` -- 시즌번호
	);

-- 스폰서
CREATE TABLE `sponsor` (
	`name`      VARCHAR(50) NOT NULL COMMENT '스폰서명', -- 스폰서명
	`league_no` INT         NOT NULL COMMENT '리그번호' -- 리그번호
)
COMMENT '스폰서';

-- 구단
ALTER TABLE `club`
	ADD CONSTRAINT `FK_league_TO_club` -- 리그 -> 구단
	FOREIGN KEY (
	`league_no` -- 리그번호
	)
	REFERENCES `league` ( -- 리그
	`league_no` -- 리그번호
	);

-- 경기 기록
ALTER TABLE `record`
	ADD CONSTRAINT `FK_player_TO_record` -- 선수 -> 경기 기록
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- 선수
ALTER TABLE player CHANGE kor_server_id kor_server_id VARCHAR(50);
ALTER TABLE `player`
	ADD CONSTRAINT `FK_roaster_TO_player` -- 로스터 -> 선수
	FOREIGN KEY (
	`roaster_no` -- 로스터 번호
	)
	REFERENCES `roaster` ( -- 로스터
	`roaster_no` -- 로스터번호
	);

-- 솔로 랭크
ALTER TABLE `solo_rank`
	ADD CONSTRAINT `FK_player_TO_solo_rank` -- 선수 -> 솔로 랭크
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- SNS
ALTER TABLE `sns`
	ADD CONSTRAINT `FK_player_TO_sns` -- 선수 -> SNS
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- SNS
ALTER TABLE `sns`
	ADD CONSTRAINT `FK_club_TO_sns` -- 구단 -> SNS
	FOREIGN KEY (
	`club_no` -- 구단번호
	)
	REFERENCES `club` ( -- 구단
	`club_no` -- 구단번호
	);

-- SNS
ALTER TABLE `sns`
	ADD CONSTRAINT `FK_league_TO_sns` -- 리그 -> SNS
	FOREIGN KEY (
	`league_no` -- 리그번호
	)
	REFERENCES `league` ( -- 리그
	`league_no` -- 리그번호
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
	ADD CONSTRAINT `FK_season_TO_roaster` -- 시즌 -> 로스터
	FOREIGN KEY (
	`season_no` -- 시즌번호
	)
	REFERENCES `season` ( -- 시즌
	`season_no` -- 시즌번호
	);

-- 시즌별 기록
ALTER TABLE `record_of_season`
	ADD CONSTRAINT `FK_player_TO_record_of_season` -- 선수 -> 시즌별 기록
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
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

-- 서명사진
ALTER TABLE `sign`
	ADD CONSTRAINT `FK_player_TO_sign` -- 선수 -> 서명사진
	FOREIGN KEY (
	`player_no` -- 선수번호
	)
	REFERENCES `player` ( -- 선수
	`player_no` -- 선수번호
	);

-- 시즌
ALTER TABLE `season`
	ADD CONSTRAINT `FK_club_TO_season` -- 구단 -> 시즌
	FOREIGN KEY (
	`club_no` -- 구단번호
	)
	REFERENCES `club` ( -- 구단
	`club_no` -- 구단번호
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