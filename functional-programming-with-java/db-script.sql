DROP PROCEDURE create_tables;
DELIMITER $$
CREATE PROCEDURE create_tables ( )
BEGIN
  DECLARE counter BIGINT DEFAULT 0;

  DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
	`user_id` int(11) unsigned NOT NULL AUTO_INCREMENT, 
	`gender` enum('MALE' , 'FEMALE') NOT NULL,
	`first_name` varchar(50) NOT NULL, 
	`last_name` varchar(50) NOT NULL, 
	`date_of_birth` date,
	`active`  boolean DEFAULT true,
	`create_datetime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
	`create_user` varchar(32) NOT NULL ,
	`update_count` int(11) DEFAULT NULL DEFAULT 0,
	`update_user` varchar(32) NOT NULL ,
	`update_datetime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
	PRIMARY KEY (`user_id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
  
  
END$$
DELIMITER ;

        
-- begin

DROP PROCEDURE populate_table;
DELIMITER $$
CREATE PROCEDURE populate_table ( in noOfUsers int(11) )
BEGIN
  DECLARE counter BIGINT DEFAULT 0;

  loop_out: LOOP
    SET counter=counter+1;

    IF counter > noOfUsers THEN
      LEAVE loop_out;
    END IF;

  		INSERT INTO user
			(first_name, last_name, gender, date_of_birth, create_user, update_user)  
		values 
		( 	
			SUBSTRING( CAST( md5(rand()) AS CHAR), 1, 3), 
			SUBSTRING( CAST( md5(rand()) AS CHAR), 1, 4) , 
			elt(rand() * 2 + 0.5, 'MALE', 'FEMALE'), 
			concat( floor(rand() * 70 ) + 1950, '-', floor(rand() * 11 ) + 1 , '-' , floor(rand() * 25 ) + 1)
			, 'testuser', 'testuser'
		) ;
        
  END LOOP loop_out;
END$$
DELIMITER ;

-- end

call create_tables();
call populate_table(100);

select * from user;