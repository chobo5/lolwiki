    select c.name,p.game_id,p.kor_name,p.kor_server_id,p.position
        from player p,roaster r,season s,club c
        where p.roaster_no=r.roaster_no
        and r.season_no=2024
        and s.season_no=2024
        and s.club_no=c.club_no
        order by c.name desc;