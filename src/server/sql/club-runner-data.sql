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
		('Joe', 'Shmoe', 'jshmoe@test.com', '$2y$10$.mBh/DskuVz10H4rquo93ehFLOUKO1dLTk.edf8c2itNPTX6X4Pz2', '0'),
        ('Mary', 'Doe', 'mdoe@test.com', '$2y$10$f8N1eOiqgMef//5ATeEzwOywG7riGXy.YdLjPf0KhKkhANvbV.I6q', '0'),
        ('John', 'Sean', 'jsean@test.com', '$2y$10$P0JrejTc9twAlPNwMgDj4OaQ.Dzkp3/x/Gwt1JZy3BHFRWQecpH8S', '0'),
        ('Susan', 'Sean', 'ssean@test.com', '$2y$10$xmNcbA3/fYiChqgQ22dyp.HtxAeWeEMAzVbMY3HD18Xhw.bSXDpIq', '0'),
        ('Admin', 'Jr.', 'ajr@test.com', '$2y$10$7mMaVt6s3W01rhzTnYN8YeDvFeTvCTy3wOV6iOalXz1x0dRvx5Rlq', '0'),
        ('Admin', 'Sr.', 'asr@test.com', '$2y$10$7mMaVt6s3W01rhzTnYN8YeDvFeTvCTy3wOV6iOalXz1x0dRvx5Rlq', '0');

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
        
insert into `run` (`timestamp`, `address`, `description`, `club_id`, `user_id`, `max_capacity`, `run_status_id`, `latitude`, `longitude`)
	values
		('2022-02-03 07:00', '123 Elm', 'A quick run', 1, 1, 23, 2, 41.981832, -87.623177),
        ('2022-02-11 15:00', '2345 Birch', 'A quick run', 2, 1, 23, 2, 41.882832, -87.623177),
        ('2022-03-07 13:00', '156783 Oak', null, 1, 1, 23, 2, 41.891832, -87.623177),
        ('2022-02-01 16:00', '54633 Pine', null, 2, 1, 23, 2, 41.581832, -87.623177),
        ('2022-02-04 17:00', '77723 Dogg', 'A quick run', 1, 1, 23, 2, 41.481832, -87.623177),
        
		('2022-02-07 16:15', '456 Flower', 'A quick run', 1, 5, 10, 1, 41.891832, -87.633177),
        ('2022-02-06 13:15', '77456 Flower', null, 2, 5, 10, 1, 41.892832, -87.633177),
        ('2022-02-01 20:15', '46 Flower', 'A quick run', 1, 5, 10, 1, 41.894832, -87.633177),
        ('2022-02-03 09:15', '7786 Flower', 'A quick run', 2, 5, 10, 1, 41.893832, -87.633177),
        ('2022-02-05 02:15', '956 Flower', 'A quick run', 1, 5, 10, 1, 41.891732, -87.633177),
        
		('2022-02-04 09:15', '000 Shrub', 'A quick run', 2, 3, 8, 3, 41.191832, -87.933177),
        ('2022-02-04 14:15', '7769 Shrub', null, 2, 3, 8, 3, 41.221832, -87.933177),
        ('2022-02-04 18:15', '5589 Shrub', 'A quick run', 2, 3, 8, 3, 41.331832, -87.933177),
        ('2022-02-04 22:15', '2389 Shrub', null, 2, 3, 8, 3, 41.661832, -87.933177);

insert into `runner` (`run_id`, `user_id`)
	values
		(1, 1),
		(2, 5),
		(3, 3),
		(3, 4),
		(2, 3);