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

insert into `user` (`first_name`, `last_name`, `email`, `password_hash`, `disabled`)
	values 
		('Joe', 'Shmoe', 'jshmoe@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0'),
        ('Mary', 'Doe', 'mdoe@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0'),
        ('John', 'Sean', 'jsean@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0'),
        ('Susan', 'Sean', 'ssean@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0'),
        ('Admin', 'Jr.', 'ajr@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0'),
        ('Admin', 'Sr.', 'asr@test.com', '$2y$10$XzTc3zTtwys82Cs0UbzRZeSU99QQz7xtwggQ/ldD9jAoKiU9q7oQu', '0');
        
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
        
insert into `run` (`timestamp`, `address`, `club_id`, `user_id`, `max_capacity`, `run_status_id`, `latitude`, `longitude`)
	values
		('2022-02-03 13:00', '123 Elm', 1, 1, 23, 2, 41.881832, -87.623177),
		('2022-02-04 13:15', '456 Flower', 1, 5, 10, 1, 41.891832, -87.633177),
		('2022-02-04 13:15', '789 Shrub', 2, 3, 8, 3, 41.991832, -87.933177);

insert into `runner` (`run_id`, `user_id`)
	values
		(1, 1),
		(2, 5),
		(3, 3),
		(3, 4),
		(2, 3);
        
end //
delimiter ;