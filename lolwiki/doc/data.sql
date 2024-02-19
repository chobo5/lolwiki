-- League
-- lck
INSERT INTO league(full_name, name, foundation_year, region,
main_agent, slogan, partner, stadium, recent_champ, most_champ, most_player)
VALUES('League of Legends Champions Korea', 'lck', '2012년', 'kor',
 'LCK 유한회사', '전설을 만들어 갑니다, LCK WE MAKE LEGENDS', '우리은행', 'LoL PARK LCK Arena', 'Gen.G(2023 Summer)', 'T1(10회)', 'Faker(10회)');

--Club
--Dplus Kia
INSERT INTO club(foundation, belong, full_name, name, leader, color, league_no)
VALUES("2017-05-28", 'LCK', 'Dplus KIA', 'DK', '이유영 NO1', 'Black, White', 1);

--Roaster
INSERT INTO roaster(club_no, season, director, coach) VALUES (1, 2024, '이재민(Zefa)', '박준형(Bubbling),김상수(Ssong)');

--Roaster_Player 관계 테이블
INSERT INTO roaster_player(roaster_no, player_no, is_captain) VALUES(1, 1, true);

--Player
INSERT INTO player(kor_name, eng_name, game_id, birth, nationality, debut, position, kor_server_id)
VALUES ('허수', 'Heo Su', 'ShowMaker', "2000-07-22", 'kor', "2017-11-22", '미드', 'DK ShowMaker, DWG KIA');