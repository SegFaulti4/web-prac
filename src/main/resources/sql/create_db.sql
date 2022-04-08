DROP TABLE IF EXISTS activity CASCADE;
DROP TABLE IF EXISTS forum_message CASCADE;
DROP TABLE IF EXISTS thread CASCADE;
DROP TABLE IF EXISTS forum_partition CASCADE;
DROP TABLE IF EXISTS forum_user CASCADE;

CREATE TABLE forum_user (
	username 		text 		PRIMARY KEY,
	passwd 		    text 		NOT NULL,
	status 		    text,
	userrole 		text 	    NOT NULL,
	created_at 		timestamp 	DEFAULT CURRENT_TIMESTAMP
						        NOT NULL
);

CREATE TABLE forum_partition (
	partition_name 	text 		PRIMARY KEY,
	created_by 		text 		REFERENCES forum_user(username) 
						        ON DELETE SET NULL
						        ON UPDATE CASCADE,
	created_at 		timestamp 	DEFAULT CURRENT_TIMESTAMP
						        NOT NULL,
	general_access 	boolean 	DEFAULT TRUE 
						        NOT NULL
);

CREATE TABLE thread (
	thread_name 	text 		NOT NULL,
	partition_name 	text 		REFERENCES forum_partition(partition_name)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
                                NOT NULL,
	created_by 		text 		REFERENCES forum_user(username)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE,
	created_at 		timestamp   DEFAULT CURRENT_TIMESTAMP
                                NOT NULL,
						
	PRIMARY KEY (thread_name, partition_name)
);

CREATE TABLE forum_message (
	message_id 		SERIAL 	    PRIMARY KEY,
	thread_name 	text 		NOT NULL,
	partition_name 	text 		NOT NULL,
	reply_to 		integer	    REFERENCES forum_message(message_id)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE,
	username 		text 		REFERENCES forum_user(username)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE,
	message_txt 	text 		NOT NULL,
	created_at 		timestamp 	DEFAULT CURRENT_TIMESTAMP 
						        NOT NULL,

	FOREIGN KEY (thread_name, partition_name) 		REFERENCES thread(thread_name, partition_name)
                                                    ON DELETE CASCADE
                                                    ON UPDATE CASCADE
);

CREATE TABLE activity (
	username 		text 		REFERENCES forum_user(username) 
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
                                NOT NULL,
    thread_name 	text 		NOT NULL,
	partition_name 	text 		NOT NULL,
	
	PRIMARY KEY (username, thread_name, partition_name),
	FOREIGN KEY (thread_name, partition_name) 		REFERENCES thread(thread_name, partition_name)
                                                    ON DELETE CASCADE
                                                    ON UPDATE CASCADE
);
