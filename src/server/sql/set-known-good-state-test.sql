delimiter //
create procedure set_known_good_state()
begin

set foreign_key_checks = 0;

truncate table `member`;
truncate table `runner`;
truncate table `run_status`;
truncate table `club`;
truncate table `user`;
truncate table `run`;

set foreign_key_checks = 1;

insert into `club` (`name`, `description`)
	values 
		('Milwaukee Walkers', 'A weekend walking club based in Milwaukee, WI.');
        
insert into `club` (`name`)
	values
		('New York Runners');

insert into `user` (`first_name`, `last_name`, `email`, `password`)
	values 
		('Joe', 'Shmoe', 'jshmoe@test.com', '123abcd'),
        ('Mary', 'Doe', 'mdoe@test.com', 'asdfqwerty!'),
        ('John', 'Sean', 'jsean@test.com', 'password'),
        ('Susan', 'Sean', 'ssean@test.com', 'birthday'),
        ('Admin', 'Jr.', 'ajr@test.com', 'supersecure'),
        ('Admin', 'Sr.', 'asr@test.com', 'supersecure');
        
insert into `member` (`user_id`, `club_id`, `isAdmin`)
	values 
		(1, 1, 0),
        (2, 2, 0),
        (3, 1, 0),
        (3, 2, 1),
        (5, 1, 1),
        (6, 2, 1);
        
insert into `run_status` (`status`)
	values
		('Pending Approval'),
        ('Approved'),
        ('Cancelled');
        
insert into `run` (`date`, `address`, `club_id`, `user_id`, `max_capacity`, `start_time`, `run_status_id`, `latitude`, `longitude`)
	values
		('2022-02-03', '123 Elm', 1, 1, 23, "13:00", 2, 41.881832, -87.623177),
		('2022-02-04', '456 Flower', 1, 5, 10, "13:15", 1, 41.891832, -87.633177),
		('2022-02-04', '789 Shrub', 2, 3, 8, "13:15", 3, 41.991832, -87.933177);

insert into `runner` (`run_id`, `user_id`)
	values
		(1, 1),
		(2, 5),
		(3, 3),
		(3, 4),
		(2, 3);
        
end //
delimiter ;