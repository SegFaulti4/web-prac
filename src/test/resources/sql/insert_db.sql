TRUNCATE forum_message CASCADE;
ALTER SEQUENCE forum_message_message_id_seq RESTART WITH 1;
TRUNCATE thread CASCADE;
TRUNCATE forum_partition CASCADE;
TRUNCATE forum_user CASCADE;

INSERT INTO forum_user (username, passwd, status, userrole) VALUES
        ('123', '123', '123', 'MODERATOR'),
        ('Doom Slayer', 'iddqd', 'rip and tear', 'COMMON'),
        ('The Dark Lord', 'idkfa', 'ripped and teared', 'MODERATOR'),
        ('Khan Maykr', 'orb', 'dead', 'BANNED'),
        ('Imp', '666', '*angry noises*', 'COMMON'),
        ('Cacodemon', '666', '*meatball noises*', 'COMMON'),
        ('Mancubus', '666', '*fat noises*', 'BANNED'),
        ('Cyberdemon', '666', 'horny', 'BANNED');

INSERT INTO forum_partition (partition_name, created_by, general_access) VALUES
        ('Earth', 'The Dark Lord', true),
        ('Events', '123', true),
        ('Hell', 'The Dark Lord', true),
        ('Mars', 'The Dark Lord', true),
        ('Fortress of Doom', 'Khan Maykr', false);

INSERT INTO thread (partition_name, thread_name, created_by) VALUES
        ('Earth', 'Hell on Earth', 'Khan Maykr'),
        ('Earth', 'Exultia', 'The Dark Lord'),
        ('Earth', 'Super Gore Nest', 'Khan Maykr'),
        ('Hell', 'Nekravol', 'The Dark Lord'),
        ('Mars', 'E1M1', '123'),
        ('Mars', 'E1M5', '123'),
        ('Mars', 'E1M9', '123');

INSERT INTO forum_message(partition_name, thread_name, username, message_txt) VALUES
        ('Earth', 'Hell on Earth', 'Khan Maykr', 'You get it?) Hell, Earth and then... Hell on Earth)) Is it funny?)'),
        ('Earth', 'Hell on Earth', 'Doom Slayer', 'Not really.'),
        ('Earth', 'Exultia', '123', 'What is this word even suppose to mean?'),
        ('Earth', 'Exultia', 'The Dark Lord', 'Your puny mind can not comprehend it'),
        ('Earth', 'Exultia', '123', 'Yeah yeah nvm'),
        ('Earth', 'Super Gore Nest', 'Cacodemon', 'Is anyone alive?'),
        ('Earth', 'Super Gore Nest', 'Doom Slayer', 'Not really.'),
        ('Hell', 'Nekravol', '123', 'Same question..'),
        ('Mars', 'E1M1', 'Imp', 'Good times)'),
        ('Mars', 'E1M1', 'Doom Slayer', 'Not really.');

INSERT INTO activity(partition_name, thread_name, username) VALUES
        ('Earth', 'Hell on Earth', 'Khan Maykr'),
        ('Earth', 'Hell on Earth', 'Doom Slayer'),
        ('Earth', 'Exultia', '123'),
        ('Earth', 'Exultia', 'The Dark Lord'),
        ('Earth', 'Super Gore Nest', 'Cacodemon'),
        ('Earth', 'Super Gore Nest', 'Doom Slayer'),
        ('Hell', 'Nekravol', '123'),
        ('Mars', 'E1M1', 'Imp'),
        ('Mars', 'E1M1', 'Doom Slayer');
