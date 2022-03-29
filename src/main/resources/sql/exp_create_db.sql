CREATE TYPE usrrole AS ENUM ('banned', 'common', 'moderator');

CREATE TABLE attachment (
	attachment_id SERIAL PRIMARY KEY,
	file_paths text[]
);

CREATE TABLE forum_user (
	username text PRIMARY KEY,
	passwd text NOT NULL,
	status text,
	userrole usrrole NOT NULL,
	avatar integer REFERENCES attachment(attachment_id) ON DELETE SET NULL ON UPDATE CASCADE,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE forum_partition (
	partition_name text PRIMARY KEY,
	created_by text REFERENCES forum_user(username) ON DELETE RESTRICT ON UPDATE CASCADE NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	general_access boolean DEFAULT TRUE NOT NULL
);

CREATE TABLE thread (
	thread_name text NOT NULL,
	partition_name text REFERENCES forum_partition(partition_name) ON DELETE RESTRICT ON UPDATE CASCADE NOT NULL,
	created_by text REFERENCES forum_user(username) ON DELETE RESTRICT ON UPDATE CASCADE NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (thread_name, partition_name)
);

CREATE TABLE forum_message (
	num integer NOT NULL,
	thread_name text NOT NULL,
	partition_name text NOT NULL,
	reply_to integer,
	username text REFERENCES forum_user(username) ON DELETE RESTRICT ON UPDATE CASCADE NOT NULL,
	attachment_id integer REFERENCES attachment(attachment_id) ON DELETE SET NULL ON UPDATE CASCADE,
	message_txt text NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (num, thread_name, partition_name),
	FOREIGN KEY (reply_to, thread_name, partition_name) REFERENCES forum_message(num, thread_name, partition_name),
	FOREIGN KEY (thread_name, partition_name) REFERENCES thread(thread_name, partition_name)
);

CREATE TABLE activity (
	username text REFERENCES forum_user(username) NOT NULL,
	thread_name text NOT NULL,
	partition_name text NOT NULL,
	message_count integer DEFAULT 1 NOT NULL,
	PRIMARY KEY (username, thread_name, partition_name),
	FOREIGN KEY (thread_name, partition_name) REFERENCES thread(thread_name, partition_name)
);
