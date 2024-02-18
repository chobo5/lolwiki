-- League
-- lck
INSERT INTO league(name, full_name, foundation_date, region,
main_agent, slogan, stadium, recent_champ, most_champ, most_player)
VALUES('lck', 'League of Legends Champions Korea', '2012년', 'kor',
 'LCK 유한회사', '전설을 만들어 갑니다, LCK WE MAKE LEGENDS', 'LoL PARK LCK Arena', 'Gen.G(2023 Summer)', 'T1(10회)', 'Faker(10회)');

--Club
--Dplus Kia
INSERT INTO club(foundation, belong_to, name, parent_company, ceo, leader, color, league_no)
VALUES("2017-05-28", 'LCK', 'Dplus KIA', null, '이유영 NO1', '이유영 NO1', 'Black, White', 1);

--Season
INSERT INTO season(season_no, club_no) VALUES (2023, 1);
INSERT INTO season(season_no, club_no) VALUES (2024, 1);

--Roaster
INSERT INTO roaster(season_no, director, coach, captain) VALUES (2024, '이재민(Zefa)', '박준형(Bubbling),김상수(Ssong)', '허수(ShowMaker)');

--Player
INSERT INTO player(kor_name, eng_name, game_id, birth, nationality, debut, position, kor_server_id, roaster_no)
VALUES ('허수', 'Heo Su', 'ShowMaker', "2000-07-22", 'kor', "2017-11-22", '미드', 'DK ShowMaker, DWG KIA', 1);